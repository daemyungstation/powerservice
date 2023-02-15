/*
 * (@)# DlwWdrwController.java
 */

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletRequestMock;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletResponseMock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.web.ConsController;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwWdrwService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
//2018.01.05 로그 추가
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
@RequestMapping(value = "/dlw/wdrw")
public class DlwWdrwController {

    @Resource
    private DlwWdrwService DlwWdrwService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private ConsController consController;

    @Resource
    public BasVlService basVlService;

    @Resource
    private CommonService commonService;
    

    /**
     * (상세구분별) 산출 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-by-reqbit/list")
    public ModelAndView getDlwWdrwListbyReqbit(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String strHolyday = "N";   // 휴일여부
            String strCmsYn = "N";     // cms 청구 여부
            String strCardYn = "N";    // card 유승인 청구 여부
            String strCardNauthYn = "N"; // card 무승인 청구 여부
            String strReqday = "";    // card 청구 여부

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx   " + hmParam);


            //산출 가능한지 체크
            List<Map<String, Object>> mCheckList = DlwWdrwService.getWdrwReqCheck(hmParam);

            if (mCheckList.size() > 0){
                Map chkMap = mCheckList.get(0);

                strHolyday = chkMap.get("CHK_HOL").toString();
                strCmsYn = chkMap.get("CHK_CMS").toString();
                strCardYn = chkMap.get("CHK_CARD").toString();
                strCardNauthYn = chkMap.get("CHK_CARD_NAUTH").toString();
            }

            //휴일이 아닌 경우 조회
            if (strHolyday.equals("N")){
                List<Map<String, Object>> mList = DlwWdrwService.getDlwWdrwListByReqbit(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            mapOutVar.put("holy_yn", strHolyday);
            mapOutVar.put("cms_yn", strCmsYn);
            mapOutVar.put("card_yn", strCardYn);
            mapOutVar.put("card_nauth_yn", strCardNauthYn);


            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 임시건 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/temp-wdrw")
    public ModelAndView saveTempWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun")); // 산출구분 (Card 또는 CMS)
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("req_day", mapInVar.get("req_day")); // 청구일

            // 임시건 산출
            DlwWdrwService.saveTempWdrw(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 산출 회원정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-acnt-info")
    public ModelAndView getDlwWdrwAcntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                List<Map<String, Object>> mList = DlwWdrwService.getDlwWdrwAcntInfo(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 임의등록
     * 정승철
     * 20181015
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-temp/add")
    public ModelAndView addWdrwTemp(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        // 휴일여부
        String strHolyday;

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            // 임의등록 전 휴일 및 청구완료 체크
            strHolyday = DlwWdrwService.getHolidayChk(hmParam);

            if (strHolyday.equals("N") ) {
                DlwWdrwService.addWdrwTemp(hmParam); // 휴일이 아닌 경우 임의등록 처리
            } else if (strHolyday.equals("H") ) {
                mapOutVar.put("g_result_msg", "입력하신 청구일은 휴일입니다."); // 휴일인 경우 메시지 리턴
            } else if (strHolyday.equals("S") ) {
                mapOutVar.put("g_result_msg", "입력하신 청구일의 CMS가 이미 청구 완료 하였습니다."); // 휴일인 경우 메시지 리턴
            } else if (strHolyday.equals("C") ) {
                mapOutVar.put("g_result_msg", "입력하신 청구일의 카드가 이미 청구 완료 하였습니다."); // 휴일인 경우 메시지 리턴
            }


            // 추후 보완예정 - 2018.10.01
            // 회원정보 있으면 상담저장
            //Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(hmParam);
            //if (mCust != null && mCust.size() > 0) {
            //    consController.saveConsdlw(hmParam);
            //}

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 임의삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-temp/delete")
    public ModelAndView delWdrwTemp(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                // 임의삭제
                DlwWdrwService.delWdrwTemp(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acnt-base-info")
    public ModelAndView getDlwAccntBaseInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                // 회원정보 조회팝업 여부
                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                // 회원정보 카운트 조회
                int nTotal = DlwWdrwService.getCntDlwAccntBaseInfo(hmParam);
                mapOutVar.put("tc_Acnt", nTotal);

                // (임의등록) 회원정보 카운트 및 조회팝업 여부에 따라 분기 조회

                // 1. 임의등록 회원조회시 1건인 경우
                if ("N".equals(popup_yn) && nTotal == 1) {
                    List<Map<String, Object>> mList = DlwWdrwService.getDlwAccntBaseInfo(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                // 2. 임의등록 회원조회시 2건 이상인 경우 팝업조회 (** 이름 등으로 검색시)
                else if(!"N".equals(popup_yn)) { // (임의등록) 회원정보 조회팝업
                    List<Map<String, Object>> mList = DlwWdrwService.getDlwAccntBaseInfo_popup(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }

            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 청구회차에 따른 청구금액을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/get-inv-amt/byPayCnt")
    public ModelAndView getDlwInvAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            int inv_cnt = 0;
            int inv_amt = 0;

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("gunsu", mapInVar.get("inv_gunsu"));

            inv_cnt = DlwWdrwService.getDlwLastPayNo(hmParam);
            hmParam.put("inv_cnt",inv_cnt);

            int totalAmt = 0;
            boolean flag = false;
            totalAmt = DlwWdrwService.getInvAmt(hmParam);

            if(totalAmt <= 0) {
                totalAmt = 0;
            }

            mapOutVar.put("mon_pay_amt", totalAmt);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 카드 임시건 +정기건 파일 작성 (NEW)
     * 임동진
     * 20180927
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/create-file/card")
    public ModelAndView createFileCard(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
               /* ************************************************************************************** */

                String invDt;
                String payMthd;
                String fileName;
                StringBuilder strbuf;
                BufferedWriter out;

                Map<String, Object> hmParam = new HashMap<String, Object>();
                Map<String, Object> pmParam = new HashMap<String, Object>();  //CARD 청구 MAP

                //청구 일자 넘어옴
                invDt = (String)mapInVar.get("inv_dt");

                //납입 방법 카드
                hmParam.put("pay_mthd", "06");
                hmParam.put("pay_mthd_auth", "Y");
                hmParam.put("req_day", invDt);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                /* ※ 카드 일련번호 생성 업데이트
                 * 파일 작성 시 일련번호를 보내야 하기 때문에 일련번호 생성 후 청구 데이터에 업데이트 후
                 * 청구 데이터의 일련번호 순서 대로 파일 작성 해야 함
                 */
                DlwWdrwService.uptReqResultKey(hmParam);

                //파일 작성 할 대상자 조회
                List<Map<String, Object>> mList = DlwWdrwService.getWdrwReqList(hmParam);

                String mid = "dmlife000g";          // id가 dmlife001m 에서 dmlife000g 로 변경되었음.
                String billGubun = "00"; 			// bill 구분  초기화

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String today = formatter.format(cal.getTime());

                FileInputStream fis = null;
                BufferedInputStream bis = null;

                JSch jsch = null;
                Session session = null;
                Channel channel = null;
                ChannelSftp channelSftp = null;

                fileName = (new StringBuilder(mid)).append("_").append(today).append(".REQ").toString(); 	// 파일 이름체계가 dmlife001m20180801.txt 에서 dmlife000g_20180724.REQ 로 변경되었음.

                File f = new File((new StringBuilder("/app/CARD/NICE/SEND/")).toString(), fileName);  	//운영
                //File f = new File((new StringBuilder("c://app/CARD/NICE/SEND/")).toString(), fileName); 	//로컬

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));

                strbuf = new StringBuilder();
                strbuf.append("H").append(",").append(today).append(",").append(mList.size()); // 파일의 1번째행은 헤더로서 "H,YYYYMMDD,건수" 의 형태를 가진다
                strbuf.append("\r\n");

                out.write(strbuf.toString());

                strbuf.delete(0, strbuf.length());

                //CommonUtils.printLog(mList);

                try
                {

                    String s = "";   //MAIN DATA 함수 초기화
                    for(int i = 0; mList.size() > i; i++)
                    {
                        pmParam = mList.get(i);
                        pmParam.put("billGubun", billGubun);

                        String sProdNm = ((String)pmParam.get("PROD_NM")).replaceAll(",", " ");

                        /*파일 작성 */
                        //strbuf.append(CommonUtils.fillEmpVal(8, String.valueOf(i + 1), "R", "0")+",");
                        strbuf.append(CommonUtils.fillEmpVal(8, (String)pmParam.get("RESULT_KEY"), "R", "0")+",");
                        strbuf.append(CommonUtils.fillEmpVal(30, (String)pmParam.get("ICHAE_NO"), "L", " ")+",");
                        strbuf.append(sProdNm + ",");
                        strbuf.append(CommonUtils.stringValueOf(pmParam.get("PAY_AMT"))+",");
                        strbuf.append((String)pmParam.get("ACCNT_NO")+",");
                        strbuf.append((String)pmParam.get("MEM_NM")+",");
                        strbuf.append((String)pmParam.get("BILL_GUBUN")+",");
                        strbuf.append(invDt);
                        strbuf.append("\r\n");

                        out.write(strbuf.toString());

                        strbuf.delete(0, strbuf.length());

                        if(i % 100 == 0)
                        {
                            out.flush();
                        }
                    }

                    out.close();

                    ParamUtils.addCenterParam(hmParam);

                    jsch = new JSch();

                    session = jsch.getSession(basVlService.getBasVlAsString("nice_ftp_access_id"), "121.133.126.8", 22);
                    session.setPassword(basVlService.getBasVlAsString("nice_ftp_access_pw")); // skdltmqlf1!

                    java.util.Properties config = new java.util.Properties();

                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);

                    session.connect();

                    // 6. sftp 채널을 연다.
                    channel = session.openChannel("sftp");
                    channel.connect();

                    channelSftp = (ChannelSftp)channel;
                    //channelSftp.connect();

                    //디렉토리 접근
                    channelSftp.cd("/home/dmlife000g/send");
                    //channelSftp.cd("/app/CARD/NICE/SEND/");

                    //디렉토리 안의 목록 조회
                    fis = new FileInputStream(f);
                    bis = new BufferedInputStream(fis, 1024*4);
                    channelSftp.put(bis,f.getName());

                    /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. END !!! */

                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(out != null){
                        out.close();
                    }
                    if(fis != null){
                        fis.close();
                    }
                    if(bis != null){
                        bis.close();
                    }
                    if(channelSftp != null){
                        channelSftp.disconnect();
                    }
                    if(channel != null){
                        channel.disconnect();
                    }
                    if(session != null){
                        session.disconnect();
                    }
                }

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                /******************************************************************************
                 * 청구 파일 작성 후 업데이트 처리
                 * AS-IS청구 테이블에는 데이터를 넣지 않는다
                 * 20181004
                 * 임동진
                *******************************************************************************/
                //CommonUtils.printLog(hmParam);
                DlwWdrwService.insertReqWdrw(hmParam);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }
    
    @RequestMapping(value = "/create-file/cardnauth")
    public ModelAndView createFileCardNAuth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String invDt;
            String fileName;
            StringBuilder strbuf;
            BufferedWriter out;

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> pmParam = new HashMap<String, Object>();  //CARD 청구 MAP

            //청구 일자 넘어옴
            invDt = (String)mapInVar.get("inv_dt");

            //납입 방법 카드이고 무승인 청구.
            hmParam.put("pay_mthd", "06");
            hmParam.put("pay_mthd_auth", "N");
            hmParam.put("req_day", invDt);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            /*
             *  20191230 임동진 수정     
             *  무승인 + 취소 RESULKEY 업데이트        
             */
            DlwWdrwService.uptReqNauthResultKey(hmParam);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String today = formatter.format(cal.getTime());
            
            String sInvDt = invDt;
            Calendar calInvDt = Calendar.getInstance();
            SimpleDateFormat sdfInvDt = new SimpleDateFormat("yyyyMMdd");
            Date dateFileNameDay = sdfInvDt.parse(sInvDt);
            calInvDt.setTime(dateFileNameDay);
            
            calInvDt.add(Calendar.DAY_OF_MONTH, -1);

            String sFileNameDay = sdfInvDt.format(calInvDt.getTime());
            
            /*
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String sFileNameDay = formatter.format(cal.getTime());
            */

            FileInputStream fis = null;
            BufferedInputStream bis = null;

            fileName = (new StringBuilder("DMSTATION_")).append(sFileNameDay.substring(4, 8)).append(".txt").toString(); 	// 카드 무승인시 청구일자 -1 로 FIX.

            File f = new File((new StringBuilder("/sftp_home/nicevan/send/")).toString(), fileName);  	    //운영, 로컬
            //File f = new File((new StringBuilder("c://app/CARD/NICENAUTH/SEND/")).toString(), fileName); 	//로컬

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));

            strbuf = new StringBuilder();

            try
            {
            	/******************************
                1. 스타트 레코드 생성부 Start 
                ******************************/
                String sSRRecodeGbn = "01"; // 레코드구분
                String sSRFileCreateDate = today.substring(2, 8); // 파일작성일
                String sSRDistriNum = "2208809321"; // 유통점사업자번호
                String sSRNiceNum = "2148174186"; // 나이스사업자번호
                
        		int iFranchisee_cnt = 0; 		//매입대상자 카운트
        		long lPay_amt = 0;				//매입금액
        		
        		int iFranchisee_cnt_c = 0; 		//취소대상자 카운트
        		long lPay_amt_c = 0;			//취소매금액
        		
        		int iFranchisee_cnt_a = 0; 		//전체 대상자 카운트
        		long lPay_amt_a = 0;			//전체매입금액        	
        		
        		int iFranchisee_cnt_c_a = 0; 	//전체 취소 대상자 카운트
        		long lPay_amt_c_a = 0;			//전체 취소 매입금액
        		
        		List<Map<String, Object>> mListNauthReqFranCnt = new ArrayList<Map<String, Object>>() ;        		
               
                String sSRLine = CommonUtils.filler_blank_right(sSRRecodeGbn + sSRFileCreateDate + sSRDistriNum + sSRNiceNum, 165, " "); // 스타트 레코드 데이터를 출력
               
                strbuf.append(sSRLine);
                strbuf.append("\r\n");

                out.write(strbuf.toString());

                strbuf.delete(0, strbuf.length());
                /******************************
                1.스타트 레코드 생성부 End
                ******************************/               
            	mListNauthReqFranCnt = DlwWdrwService.getWdrwNauthReqFranCnt(hmParam);
            	
       			System.out.println("전체건수>>>>>>>>>>>>>>>>>>>>>>>>>" +  mListNauthReqFranCnt.size());
            	
            	String sFranchiseeChange_Pre = "";		//가맹번호 변경 전 (이전)
            	String sFranchiseeChange_Last = "";    //가맹번호 변경 후 (최신)
            	
            	for(int idxFranCnt = 0; idxFranCnt < mListNauthReqFranCnt.size(); idxFranCnt++)
            	{
            		/**********************************
                     2. 헤더 레코드 생성부 Start
                     **********************************/
            	            		
            		String sBatchSegmentNum ="";
            		String sHRSaleDateFrom = "";
            		String sHRSaleDateTo = "";
            		            	
            		Map<String, Object> mapNauthReqData = mListNauthReqFranCnt.get(idxFranCnt);
            		sFranchiseeChange_Last = (String)mapNauthReqData.get("franchisee_no");
            		

            		// 가맹점번호가 없음 (최초 진입)
            		if (sFranchiseeChange_Pre.equals("")){            			          		            			
            			sFranchiseeChange_Pre = (String)mapNauthReqData.get("franchisee_no");
            			
                		String sHRRecodeGbn = "10"; // 레코드 구분
                		String sHRCurrencyCode = "410"; // 통화코드(410:원화,840:달러)
                		String sHRFileCreateDate = today.substring(2, 8); // 파일작성일
                		String sHRFranchiseeNo = CommonUtils.filler_blank_right(sFranchiseeChange_Pre, 10, " "); // 가맹점번호
                		String sHRFranchiseeGbnCode = "00"; // 점별구분코드
                		String sHRCardBuisNum = "2028145602"; // 카드사사업자번호 FIXME!!
                		sHRSaleDateFrom = invDt.substring(2, 8); // 매출일자From
                		sHRSaleDateTo = invDt.substring(2, 8); // 매출일자To
                		String sHRServiceGbn = "EDI"; // 서비스구분
                		String sHRFranchiseeNoExtend = "               "; // 확장가맹점번호(15자리)
                		
                		//int iNauthReqDataCnt = Integer.parseInt((String)mapNauthReqData.get("icnt")); // 해당헤더에있는데이터개수
                		//sBatchSegmentNum = CommonUtils.filler_blank_left(Integer.toString(idxFranCnt + 1), 4, "0");// BatchSegment번호                		
                		
                		String sHRLine = CommonUtils.filler_blank_right(sHRRecodeGbn + sHRCurrencyCode + sHRFileCreateDate + sHRFranchiseeNo + sHRFranchiseeGbnCode
                				+ sHRCardBuisNum + sHRSaleDateFrom + sHRSaleDateTo + sHRServiceGbn + sHRFranchiseeNoExtend, 165, " "); // 헤더 레코드 데이터를 출력
                		
                		strbuf.append(sHRLine);
                        strbuf.append("\r\n");                        
                        out.write(strbuf.toString());
                        strbuf.delete(0, strbuf.length());
                        
            		}
            		
            		sFranchiseeChange_Last = (String)mapNauthReqData.get("franchisee_no");            		            		       
            		
            		if (!sFranchiseeChange_Pre.equals(sFranchiseeChange_Last)){               			                   			
	            		/**********************************
	                    2. 헤더 END 레코드 생성부 Start
	                    **********************************/                   			
                      		String sTRRecodeGbn = "30"; // 레코드구분
                    		String sTRCurrencyCode = "410"; // 통화코드
                    		String sTRFileCreateDate = today.substring(2, 8); // 파일작성일
                    		String sTRBasiIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt), 7, "0"); // 일반건수합계
                    		String sTRBasiPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt), 12, "0"); // 일단순매출액합계
                    		String sTRBasiVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일반봉사료합계
                    		String sTRBasiPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt), 12, "0"); // 일반매출액합계
                    		
                    		String sTRCnclIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt_c), 7, "0"); // 취소일반건수합계
                    		String sTRCnclPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c), 12, "0"); // 취소일반순매출합계
                    		String sTRCnclVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반봉사료합계
                    		String sTRCnclPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c), 12, "0"); // 취소일반매출액합계
                    		
                    		String sTRPlanIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 할부건수합계
                    		String sTRPlanPaySum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 할부매출액합계
                    		String sTRPlanCnclIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 취소할부건수합계
                    		String sTRPlanCnclSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소할부매출액합계
                    		
                    		String sTRLine = CommonUtils.filler_blank_right(sTRRecodeGbn + sTRCurrencyCode + sTRFileCreateDate + sTRBasiIcnt + sTRBasiPurePayAmtSum
                    				+ sTRBasiVoluntAmtSum + sTRBasiPayAmtSum + sTRCnclIcnt + sTRCnclPurePayAmtSum + sTRCnclVoluntAmtSum + sTRCnclPayAmtSum + sTRPlanIcnt 
                    				+ sTRPlanPaySum + sTRPlanCnclIcnt + sTRPlanCnclSum, 165, " "); // 헤더 레코드 데이터를 출력
                    		
                    		strbuf.append(sTRLine);
                            strbuf.append("\r\n");
                            out.write(strbuf.toString());
                            strbuf.delete(0, strbuf.length());	
                            
                    		//초기화
                    		iFranchisee_cnt = 0;
                    		lPay_amt =0;
                    		iFranchisee_cnt_c = 0;
                    		lPay_amt_c = 0;                           		
                			sFranchiseeChange_Pre = (String)mapNauthReqData.get("franchisee_no");
                			
                		/**********************************
                        2. 신규 헤더 레코드 생성부 Start
                        **********************************/                			
                    		String sHRRecodeGbn = "10"; // 레코드 구분
                    		String sHRCurrencyCode = "410"; // 통화코드(410:원화,840:달러)
                    		String sHRFileCreateDate = today.substring(2, 8); // 파일작성일
                    		String sHRFranchiseeNo = CommonUtils.filler_blank_right(sFranchiseeChange_Last, 10, " "); // 가맹점번호
                    		String sHRFranchiseeGbnCode = "00"; // 점별구분코드
                    		String sHRCardBuisNum = "2028145602"; // 카드사사업자번호 FIXME!!
                    		sHRSaleDateFrom = invDt.substring(2, 8); // 매출일자From
                    		sHRSaleDateTo = invDt.substring(2, 8); // 매출일자To
                    		String sHRServiceGbn = "EDI"; // 서비스구분
                    		String sHRFranchiseeNoExtend = "               "; // 확장가맹점번호(15자리)
                    		
                    		//int iNauthReqDataCnt = Integer.parseInt((String)mapNauthReqData.get("icnt")); // 해당헤더에있는데이터개수
                    		//sBatchSegmentNum = CommonUtils.filler_blank_left(Integer.toString(idxFranCnt + 1), 4, "0");// BatchSegment번호
                    		                    		
                    		String sHRLine = CommonUtils.filler_blank_right(sHRRecodeGbn + sHRCurrencyCode + sHRFileCreateDate + sHRFranchiseeNo + sHRFranchiseeGbnCode
                    				+ sHRCardBuisNum + sHRSaleDateFrom + sHRSaleDateTo + sHRServiceGbn + sHRFranchiseeNoExtend, 165, " "); // 헤더 레코드 데이터를 출력
                    		
                    		strbuf.append(sHRLine);
                            strbuf.append("\r\n");                            
                            out.write(strbuf.toString());
                            strbuf.delete(0, strbuf.length());                            
            		}
            		
                    /**********************************
                    2. 헤더 레코드 생성부 End
                    **********************************/
            		
            		
         			/**********************************
                    3-1. 데이터 레코드 생성부 Start
                    **********************************/  
            		String sDRRecodeGbn = "";
            		if (!mapNauthReqData.get("nauth_bit").toString().equals("CC")){
            			sDRRecodeGbn = "11"; // 레코드구분(11:일반,12:일반취소,21:할부,22:할부취소)	
            			
            			//가맹점별 회원수 및 매출액 합계
            			iFranchisee_cnt = iFranchisee_cnt + 1;
            			lPay_amt = lPay_amt +Long.valueOf(mapNauthReqData.get("pay_amt").toString());  
            			
            			//전체 합계 금액
            			iFranchisee_cnt_a = iFranchisee_cnt_a +  1;
            			lPay_amt_a = lPay_amt_a + Long.valueOf(mapNauthReqData.get("pay_amt").toString()); 
            		} else {
            		    sDRRecodeGbn = "12"; // 레코드구분(11:일반,12:일반취소,21:할부,22:할부취소)	
            			//가맹점별 취소회원수 및 취소매출액 합계
            			iFranchisee_cnt_c = iFranchisee_cnt_c + 1;
            			lPay_amt_c = lPay_amt_c +Long.valueOf(mapNauthReqData.get("pay_amt").toString()); 
            			
            			//전체 합계 금액
            			iFranchisee_cnt_c_a = iFranchisee_cnt_c_a +  1;
            			lPay_amt_c_a = lPay_amt_c_a + Long.valueOf(mapNauthReqData.get("pay_amt").toString()); 
            		}
            		        			
        			String sDRBatchSegmentNum = mapNauthReqData.get("result_key").toString().substring(0,4); // BATCH번호
        			String sDRRecodeNum = CommonUtils.filler_blank_left(Integer.toString(idxFranCnt + 1), 6, "0"); // Recode번호
        			String sDRFloorGbn = "000"; // 층구분
        			String sDRIchaeNo = CommonUtils.filler_blank_right((String)mapNauthReqData.get("ichae_no"), 19, " "); // 카드번호
        			String sDRExpireDate = CommonUtils.nvl((String)mapNauthReqData.get("expire_date"), "0000"); // 유효기간
        			String sDRCvv = (String)mapNauthReqData.get("bill_gubun"); // CVV
        			//String sDRSaleDate = sHRSaleDateFrom; // 매출일자(취소일자)
        			String sDRSaleDate = invDt.substring(2, 8);
        			String sDRPreSaleDate = "000000"; // 당초매출일자
        			String sDRAuthCode = CommonUtils.filler_blank_right("", 10, " "); // 승인번호(일단 result_key로 해둠. 파트장님 확인이 필요한 사안.)
        			//String sDRAuthCode = CommonUtils.filler_blank_right((String)rowNauthReqFranData.get("result_key"), 10, " "); // 승인번호(일단 result_key로 해둠. 파트장님 확인이 필요한 사안.)
        			String sDRCurrencyCode = "410"; // 통화코드(410:원화,840:달러)
        			String sDRCurrencyGbn = "0"; // 통화코드(0:원화,2:달러)
        			String sDRPurePayAmt = CommonUtils.filler_blank_left(String.valueOf(mapNauthReqData.get("pay_amt")), 9, "0"); // 순매출액
        			String sDRVoluntAmt = "000000000"; //봉사료
        			String sDRPayAmt = CommonUtils.filler_blank_left(String.valueOf(mapNauthReqData.get("pay_amt")), 9, "0"); // 매출액
        			String sDRPaymentPlan = "00"; // 할부기간(일반매출:00)
        			String sDRMallNum = "      "; // 매장번호(6자리)
        			String sDRTradNum = "00000000"; // 거래번호
        			String sDRTradGbn = "00"; // 거래구분
        			String sFranchiUseArea = CommonUtils.filler_blank_right((String)mapNauthReqData.get("accnt_no") + (String)mapNauthReqData.get("result_key"), 28, " ");
        			
        			String sDRLine = CommonUtils.filler_blank_right(sDRRecodeGbn + sDRBatchSegmentNum + sDRRecodeNum + sDRFloorGbn + sDRIchaeNo
            				+ sDRExpireDate + sDRCvv + sDRSaleDate + sDRPreSaleDate + sDRAuthCode + sDRCurrencyCode + sDRCurrencyGbn 
            				+ sDRPurePayAmt + sDRVoluntAmt + sDRPayAmt + sDRPaymentPlan + sDRMallNum + sDRTradNum + sDRTradGbn + sFranchiUseArea, 165, " "); // 헤더 레코드 데이터를 출력
        			        			
            		strbuf.append(sDRLine);
                    strbuf.append("\r\n");

                    out.write(strbuf.toString());
                    strbuf.delete(0, strbuf.length());                  
            		
            	}
            	
        		/**********************************
                2. 헤더 END 레코드 생성부 Start
                **********************************/                   			
              		String sTRRecodeGbn = "30"; // 레코드구분
            		String sTRCurrencyCode = "410"; // 통화코드
            		String sTRFileCreateDate = today.substring(2, 8); // 파일작성일
            		String sTRBasiIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt), 7, "0"); // 일반건수합계
            		String sTRBasiPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt), 12, "0"); // 일단순매출액합계
            		String sTRBasiVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일반봉사료합계
            		String sTRBasiPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt), 12, "0"); // 일반매출액합계
            		
            		String sTRCnclIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt_c), 7, "0"); // 취소일반건수합계
            		String sTRCnclPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c), 12, "0"); // 취소일반순매출합계
            		String sTRCnclVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반봉사료합계
            		String sTRCnclPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c), 12, "0"); // 취소일반매출액합계
            		
            		String sTRPlanIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 할부건수합계
            		String sTRPlanPaySum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 할부매출액합계
            		String sTRPlanCnclIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 취소할부건수합계
            		String sTRPlanCnclSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소할부매출액합계
            		
            		String sTRLine = CommonUtils.filler_blank_right(sTRRecodeGbn + sTRCurrencyCode + sTRFileCreateDate + sTRBasiIcnt + sTRBasiPurePayAmtSum
            				+ sTRBasiVoluntAmtSum + sTRBasiPayAmtSum + sTRCnclIcnt + sTRCnclPurePayAmtSum + sTRCnclVoluntAmtSum + sTRCnclPayAmtSum + sTRPlanIcnt 
            				+ sTRPlanPaySum + sTRPlanCnclIcnt + sTRPlanCnclSum, 165, " "); // 헤더 레코드 데이터를 출력
            		
            		strbuf.append(sTRLine);
                    strbuf.append("\r\n");

                    out.write(strbuf.toString());

                    strbuf.delete(0, strbuf.length());	
                    
                    
            		/**********************************
                    2. END 레코드 생성부 Start
                    **********************************/    
                	
                    String sERRecodeGbn = "02"; // 레코드구분
                	String sERCurrencyCode = "000"; // 통화코드
                	String sERBatchSegmentTotNum = CommonUtils.filler_blank_left(Integer.toString(iFranchisee_cnt_a + iFranchisee_cnt_c_a), 6, "0");
                	String sERBasiIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt_a), 7, "0"); // 일반건수합계
            		String sERBasiPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_a), 12, "0"); // 일단순매출액합계
            		String sERBasiVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일반봉사료합계
            		String sERBasiPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_a), 12, "0"); // 일반매출액합계
            		String sERCnclIcnt = CommonUtils.filler_blank_left(String.valueOf(iFranchisee_cnt_c_a), 7, "0"); // 취소일반건수합계
            		String sERCnclPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c_a), 12, "0"); // 취소일반순매출합계
            		String sERCnclVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반봉사료합계
            		String sERCnclPayAmtSum = CommonUtils.filler_blank_left(String.valueOf(lPay_amt_c_a), 12, "0"); // 취소일반매출액합계
            		String sERPlanIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 할부건수합계
            		String sERPlanPaySum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 할부매출액합계
            		String sERPlanCnclIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 취소할부건수합계
            		String sERPlanCnclSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소할부매출액합계
                	
            		String sERLine = CommonUtils.filler_blank_right(sERRecodeGbn + sERCurrencyCode + sERBatchSegmentTotNum + sERBasiIcnt + sERBasiPurePayAmtSum
            				+ sERBasiVoluntAmtSum + sERBasiPayAmtSum + sERCnclIcnt + sERCnclPurePayAmtSum + sERCnclVoluntAmtSum + sERCnclPayAmtSum + sERPlanIcnt 
            				+ sERPlanPaySum + sERPlanCnclIcnt + sERPlanCnclSum, 165, " "); // 헤더 레코드 데이터를 출력
            		
            		strbuf.append(sERLine);
                    strbuf.append("\r\n");

                    out.write(strbuf.toString());

                    strbuf.delete(0, strbuf.length());
                    
            		/**********************************
                    2. END 레코드 생성부 End
                    **********************************/    
                
                /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. END !!! */

            }
            catch(IOException e) 
            {
                e.printStackTrace();
            }
            finally 
            {
                if(out != null)
                {
                    out.close();
                }
                if(fis != null)
                {
                    fis.close();
                }
                if(bis != null)
                {
                    bis.close();
                }
            }

            /******************************************************************************
             * 청구 파일 작성 후 업데이트 처리
             * AS-IS청구 테이블에는 데이터를 넣지 않는다
             * 20181004
             * 임동진
             *******************************************************************************/
            DlwWdrwService.insertReqWdrw(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/create-file/cms")
    public ModelAndView createFileCms(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
           /* ************************************************************************************** */

            String totalAmt;
            String useInstCd;
            String invDt;
            String payMthd;
            String fileName;
            StringBuffer strbuf;
            BufferedWriter out;

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> pmParam = new HashMap<String, Object>();

            //청구 일자 넘어옴
            invDt = (String)mapInVar.get("inv_dt");
            //납입 방법 CMS
            payMthd = "04";

            //CMS기초정보 가져오기
            List<Map<String, Object>> mComnList = DlwWdrwService.getWdrwDlwCmsComnInfo(hmParam);
            Map comnMap = mComnList.get(0);

            //월 CMS산출 한도 금액
            //String mon_wdrw_limit = (String)comnMap.get("mon_wdrw_limit");

            //CMS 스테이션 회사 코드
            useInstCd = (String)comnMap.get("use_inst_cl_cd");

            //CMS 통장 기재내역 앞의 문자
            //String bankFillIn = (String)comnMap.get("bank_filin_brkdn");

            //CMS 파일 다운 경로
            //String filePath = (String)comnMap.get("file_path");

            //CMS 청구 데이터 READ
            hmParam.put("req_day", invDt);
            hmParam.put("pay_mthd", payMthd);
            hmParam.put("pay_mthd_auth", "Y");
            totalAmt = (String)mapInVar.get("total_amt");

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            /** ※ CMS 일련번호 생성 업데이트
             * CMS의 경우 파일 작성 시 일련번호를 보내야 하기 때문에 일련번호 생성 후 청구 데이터에 업데이트 후
             * 청구 데이터의 일련번호 순서 대로 파일 작성 해야 함
            **/
            DlwWdrwService.uptReqResultKey(hmParam);

            //파일 작성 할 대상자 조회
            List<Map<String, Object>> mList = DlwWdrwService.getWdrwReqList(hmParam);

            fileName = (new StringBuilder("EB21")).append(invDt.substring(4, 8)).toString();
            strbuf = new StringBuffer();
            out = null;

            try
            {
                String s = "";
                s = (new StringBuilder("H00000000")).append(useInstCd).append(fileName).append(invDt.substring(2, 8)).append((String)comnMap.get("bank_op_brach_cd")).append(CommonUtils.fillEmpVal(16, (String)comnMap.get("bank_accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(94, "", "L", " ")).toString();
                strbuf.append(s);
                for(int i = 0; mList.size() > i; i++)
                {
                    //System.out.println("---------------> " + i );
                    pmParam = mList.get(i);

                    s = (new StringBuilder("R")).append(pmParam.get("result_key")).append(useInstCd).append(CommonUtils.fillEmpVal(7, (String)pmParam.get("ichae_cd"), "L", "0")).append(CommonUtils.fillEmpVal(16, (String)pmParam.get("ichae_no"), "L", " ")).append(CommonUtils.fillEmpVal(13, String.valueOf(pmParam.get("pay_amt")), "R", "0")).append(CommonUtils.fillEmpVal(13, (String)pmParam.get("birth"), "L", " ")).append(CommonUtils.fillEmpVal(5, "", "L", " ")).append(CommonUtils.fillEmpVal(8, CommonUtils.transByteForBankFillIn(String.valueOf(pmParam.get("bank_filin_brkdn")).trim()), "L", "　")).append(CommonUtils.fillEmpVal(2, "", "L", " ")).append(CommonUtils.fillEmpVal(20, (String)pmParam.get("accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(5, String.valueOf(pmParam.get("req_pay_no")), "L", " ")).append("1").append(CommonUtils.fillEmpVal(12, "", "L", " ")).append(CommonUtils.fillEmpVal(21, "", "L", " ")).toString();
                    strbuf.append(s);
                }

                s = (new StringBuilder("T99999999")).append(useInstCd).append(fileName).append(CommonUtils.fillEmpVal(8, String.valueOf(mList.size()), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(mList.size()), "R", "0")).append(CommonUtils.fillEmpVal(13, totalAmt, "R", "0")).append(CommonUtils.fillEmpVal(8, "", "L", "0")).append(CommonUtils.fillEmpVal(13, "", "L", "0")).append(CommonUtils.fillEmpVal(63, "", "L", " ")).append(CommonUtils.fillEmpVal(10, "", "L", " ")).toString();
                strbuf.append(s);

                /*CMS경로*/
                File f = new File((new StringBuilder("/app/CMS/9993083157/EB21/")).toString(), fileName);       // 서버용
                //File f = new File((new StringBuilder("c://app/CMS/9993083157/EB21/")).toString(), fileName);  // 로컬용

                f.createNewFile();

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));
                out.write(strbuf.toString());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            } finally {
                out.close();
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            /******************************************************************************
             * 청구 파일 작성 후 업데이트 처리
             * AS-IS청구 테이블에는 데이터를 넣지 않는다
             * 20181004
             * 임동진
            *******************************************************************************/
            CommonUtils.printLog(hmParam);
            DlwWdrwService.insertReqWdrw(hmParam);

            //hmParam.put("inv_dt", invDt);
            //DlwCmsService.updateCmsAppStateLrqnt(hmParam);
           /* ************************************************************************************** */


            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 산출마감체크 조회
     * 정승철
     * 20181012
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwExtCheck")
    public ModelAndView getWdrwExtCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwWdrwService.getWdrwExtCheck(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 산출마감체크 등록
     * 정승철
     * 20181012
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/add-extChk")
    public ModelAndView saveExtChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            // 휴일이 아닐 경우 NULL로 입력
            if("N".equals(hmParam.get("chk_hol"))) hmParam.put("chk_hol", "");

            // 산출마감 입력유효성체크
            int chkExtReqDt = DlwWdrwService.chkWdrwExt(hmParam);

            if(chkExtReqDt == 0)
                DlwWdrwService.saveExtChk(hmParam); // 산출마감체크 등록
            else if(chkExtReqDt > 0)
                mapOutVar.put("g_reason_msg", "해당 청구일은 이미 등록되어 있습니다.");  // 실패 메시지 return


            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 산출마감체크 삭제
     * 정승철
     * 20181012
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/del-extChk")
    public ModelAndView delExtChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            // 산출마감체크 삭제
            DlwWdrwService.delExtChk(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 임의등록 실시간 카드 결제 (NEW)
     * 임동진
     * 20181012
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/realtime-batch")
    public ModelAndView ReqRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                String billGubun = "00";
                //int batch_cnt = Integer.parseInt((String)mapInVar.get("batch_cnt"));

                // MAIN MAP (PARAMETER)
                Map<String, Object> hmParam = new HashMap<String, Object>();

                // 실시간 결제 회원 정보 MAP
                Map<String, Object> MemParam = new HashMap<String, Object>();

                //실시간 결제 결과 정보 MAP
                Map<String, Object> resultParam = new HashMap<String, Object>();

                //실시간 결제 해줄 대상자 정보 가져온다 (1건만 가져온다 향후 상황보고 여러건 결제 로직 처리)
                hmParam = listInDs.get(0);

                //산출 된 회원 정보 가져오기
                List<Map<String, Object>> MemList = DlwWdrwService.getRealTimeReqList(hmParam);
                MemParam = MemList.get(0);

                hmParam.put("menu_gbn", "0003");
                hmParam.put("dc_no", String.valueOf(MemParam.get("card_quota")));
                hmParam.put("card_quota", String.valueOf(MemParam.get("card_quota")));
                hmParam.put("pay_amt", String.valueOf(MemParam.get("pay_amt")));
                hmParam.put("gbn_batch", "batch");
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));
                //
                hmParam.put("req_day", String.valueOf(MemParam.get("req_day")));
                hmParam.put("no", String.valueOf(MemParam.get("req_pay_no")));
                hmParam.put("pay_no", String.valueOf(MemParam.get("req_pay_no")));
                hmParam.put("stat", String.valueOf(MemParam.get("stat")));
                hmParam.put("email", String.valueOf(MemParam.get("email")));
                hmParam.put("mem_nm", String.valueOf(MemParam.get("mem_nm")));
                hmParam.put("bid", String.valueOf(MemParam.get("bid")));
                hmParam.put("cell", String.valueOf(MemParam.get("cell")));
                hmParam.put("prod_nm", String.valueOf(MemParam.get("prod_nm")));
                hmParam.put("bill_key", String.valueOf(MemParam.get("bill_key")));
                hmParam.put("del_fg", "N");
                hmParam.put("pay_mthd", "06");
                hmParam.put("mode", "insert");

                //billgubun설정 (36회 혹은  24회시 구분값 변경하여 카드사 전달
                hmParam.put("billGubun", String.valueOf(MemParam.get("bill_gubun")));

                resultParam = DlwWdrwService.RealTimeReqProc(hmParam);

                String result_Cd = (String)resultParam.get("result_cd");
                String result_Msg = (new StringBuilder(String.valueOf(result_Cd))).append(" : ").append((String)resultParam.get("result_msg")).toString();
                String auth_Cd = (String)resultParam.get("auth_cd"); // 승인번호
                String auth_Dt = (String)resultParam.get("auth_dt"); // 승인일자
                String strTid = (String)resultParam.get("tid");		 // 카드 결과 tid값
                String ichaeCd = (String)resultParam.get("card_cd"); // 이체카드


                //String result_Cd = "3001";
                //String result_Msg = "성공";
                //String auth_Cd = "42057615"; // 승인번호
                //String auth_Dt = "181015170735"; // 승인일자
                //String strTid = "dmlife001m01161810151707341267";		 // 카드 결과 tid값
                //String ichaeCd = "06"; // 이체카드

                hmParam.put("pay_mthd", "06");			//납입방법 (04:CMS, 06:카드)
                hmParam.put("pay_kind", "02");  		//실시간 (01:파일, 02:실시간, 03:자유,04:기타)
                hmParam.put("result_cd", result_Cd); 		   //납입방법 (04:CMS, 06:카드)
                hmParam.put("result_msg", result_Msg); 	//결과 메세지
                hmParam.put("auth_cd", auth_Cd);
                hmParam.put("auth_dt", auth_Dt);
                hmParam.put("result_key", strTid);
                hmParam.put("ichae_cd", ichaeCd);

                try{
                        //실시간 카드 결과데이터 인서트 및 성공 시 청구 데이터 업데이트 (DEL_FG = 'C')
                        DlwWdrwService.insertReqWdrwResult(hmParam);

                        //실시간 결제 성공 시 입금 적용 (결과가 오류 시 승인번호 있으면 승인된걸로 인정)
                        if ("3001".equals(result_Cd)) {
                            DlwWdrwService.insertMemPayData(hmParam);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        szErrorCode = "-1";
                        szErrorMsg  = "결과 처리 시 오류가 발생 하였습니다.";
                    }

                //System.out.println("result_cd >>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + result_Cd);

                /*
                if ("3001".equals(result_Cd)) {
                    resultParam.put("result_cd", "00");
                    if("0001".equals(hmParam.get("menu_gbn"))) {
                        resultParam.put("mode", "insert");
                        //납입내역 저장
                        //mergePayMngAfterRTCard(hmParam);
                    }
                    //상담저장
                    //consController.saveConsdlw(hmParam);
                } else {
                    resultParam.put("result_cd", "01");
                    resultParam.put("result_msg", (new StringBuilder(String.valueOf(result_Cd))).append(" : ").append((String)resultParam.get("result_msg")).toString());
                }

                resultParam.put("menu_gbn", hmParam.get("menu_gbn"));
                */
                //로그 저장 (결과 업데이트)
                //DlwCardService.updateRTCardPayResult(resultMap);

                mapOutVar.put("result_cd", result_Cd);
                mapOutVar.put("result_msg", result_Msg);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("result_cd >>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + e.getMessage());

                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }
        
        /* ================================================================
         * 날짜 : 20181120
         * 이름 : 정승철
         * 추가내용 : CMS 업로드 처리 (** 결과등록)
         * ================================================================
         * */
        @RequestMapping(value = "/data_upload")
        public ModelAndView resultFileUpload(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try
            {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                String sGDate = (String)mapInVar.get("req_day");

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("g_date", sGDate);             // 공제 산출일
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                String sResultFileUploadBasePath = "";
                String sFileYYYY = sGDate.substring(0, 4);
                String sFileMM = sGDate.substring(4, 8);
                String sMatchingFlag = "N";
                int sResultDataCnt = 0;

                String osName = System.getProperty("os.name").toLowerCase();

                if (osName.indexOf("windows") >= 0)
                {
//                    sResultFileUploadBasePath = EgovProperties.getProperty("cms.rec.file.upload.basePath") + "/EB22" + sFileMM + "";
                	sResultFileUploadBasePath = "C:/app/CMS/9993083157/EB22/";
                    sResultFileUploadBasePath = sResultFileUploadBasePath.replaceAll("/", "\\\\");
                }
                else
                {
                    sResultFileUploadBasePath = "C:/app/CMS/9993083157/EB22/";
                }

                System.out.println("파일 탐색 폴더명 : " + sResultFileUploadBasePath);

                BufferedReader br = null;
                StringBuffer resultBuffer = new StringBuffer();
                File fBasicFile = new File(sResultFileUploadBasePath);
                String[] arrBaseFiles = fBasicFile.list();

                for(int idx = 0; idx < arrBaseFiles.length; idx++)
                {
                    String sMatchFileName = arrBaseFiles[idx].substring(4, 8);
                    
                    System.out.println("파일 탐색 폴더명2 : " +arrBaseFiles[idx].substring(0, 8));

                    if(sMatchFileName.equals(sFileMM))
                    {
                        sMatchingFlag = "Y"; // 해당 일자의 결과 데이터가 들어왔음.

                        br = new BufferedReader(new InputStreamReader(new FileInputStream(sResultFileUploadBasePath + arrBaseFiles[idx]), "UTF-8"));
                        String bfLine = "";

                        String strReqday = ""; //청구 일자

                        bfLine = br.readLine();

                        //결과 청구 일자 가져오기
                        strReqday = "20" + bfLine.substring(27,33);
                        
                        while((bfLine = br.readLine()) != null)
                        {
                            if (bfLine.length() != 0)
                            {
                                sResultDataCnt++;
                            }
                        }
                        
                        

                        br.close();

//                        if (Integer.parseInt(sGTotExtCnt) !=  sResultDataCnt)
//                        {
//                            throw new EgovBizException("CMS업로드 건수가 맞지 않습니다. " + sGTotExtCnt + "<>" + sResultDataCnt);
//                        }

                        sResultDataCnt = 0;

                        String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
                        String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
                        String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
                        String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

                        System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                                           "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);

                        Connection connection = null;
                        PreparedStatement preparedStatement = null ;


                        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND,";
                        sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID)";
                        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'CMS_AUTO',?)";

                        Class.forName(sAccessClassName);
                        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                        connection.setAutoCommit(false);

                        preparedStatement = connection.prepareStatement(sqlStatement);
                        preparedStatement.setQueryTimeout(1000);

                        br = new BufferedReader(new InputStreamReader(new FileInputStream(sResultFileUploadBasePath + arrBaseFiles[idx]), "euc-kr"));
                        
                        bfLine = br.readLine();
                        
//                      //결과 레코드 담기
                        bfLine = bfLine.substring(151);
//
//                        //결과 레코드 다음행 처리
//                        //fileLayout = bfLine.split("                     R");
//                        //fileLayout = bfLine.split("\nR");
                        int iStartDataByteIndex = 0;
                        int iExpressionDataLineLength = 142;
                        int iFileLayoutArrayIndex = 0;
                        String[] fileLayout	    = new String[50000];
                        
                        //결과 레코드 다음행 처리
                        while(bfLine != null)
                        {
                            if (bfLine.length() != 0)
                            {
                            	String sLineData = bfLine.substring(iStartDataByteIndex, iStartDataByteIndex + iExpressionDataLineLength);
                            	if(sLineData.substring(0, 8).equals("99999999"))
                      		   {
                      			   break;
                      		   }
                            	
                            	fileLayout[iFileLayoutArrayIndex] = sLineData;
                      		   
                      		   iStartDataByteIndex += iExpressionDataLineLength;
                            	
                      		 
                                String sAccntNo = fileLayout[iFileLayoutArrayIndex].substring(82,94);
                          	   	String sReqDay = strReqday;
                          	   	String sReqNo = "999";
                          	   	String sPayKind = "01";
                          	   	String sResultKey = fileLayout[iFileLayoutArrayIndex].substring(0,8);
                          	   	String sResultCd = fileLayout[iFileLayoutArrayIndex].substring(68,72);
                          	   	String sResultMsg = "CMS실패";
                          	   	String sPayMthd = "04";
                          	   	String sAuthDt = "";
                          	   	String sAuthCd = "";
                          	   	String sIchaeCd = fileLayout[iFileLayoutArrayIndex].substring(18,21);
                          	    String sIchaeNo = fileLayout[iFileLayoutArrayIndex].substring(25,41);
                          	    String sEtc = fileLayout[iFileLayoutArrayIndex].substring(72,80);
                          	   
                          	    preparedStatement.setString(1, sAccntNo.trim());
                                preparedStatement.setString(2, sReqDay.trim());
                                preparedStatement.setString(3, sReqNo.trim());
                                preparedStatement.setString(4, sPayKind.trim());
                                preparedStatement.setString(5, sResultKey.trim());
                                preparedStatement.setString(6, sResultCd.trim());
                                preparedStatement.setString(7, sResultMsg.trim());
                                preparedStatement.setString(8, sPayMthd.trim());
                                preparedStatement.setString(9, sAuthDt.trim());
                                preparedStatement.setString(10, sAuthCd.trim());
                                preparedStatement.setString(11, sIchaeCd.trim());
                                preparedStatement.setString(12, sEtc.trim());
                                preparedStatement.setString(13, sResultKey.trim());
                                
                                iFileLayoutArrayIndex++;

                                // addBatch에 담기
                                preparedStatement.addBatch();

                                // 파라미터 Clear
                                preparedStatement.clearParameters() ;


                                // OutOfMemory를 고려하여 만건 단위로 커밋
                                if( (sResultDataCnt % 500) == 0)
                                {
                                    // Batch 실행
                                    preparedStatement.executeBatch();

                                    // Batch 초기화
                                    preparedStatement.clearBatch();

                                    // 커밋
                                    connection.commit();
                                }
                                sResultDataCnt++;
                            }
                        }
                        preparedStatement.executeBatch() ;
                        connection.commit() ;

                        br.close();
                        preparedStatement.close();
                        connection.close();

                    }
                    else
                    {
                        System.out.println("해당일자의 산출 결과 파일이 없습니다.");
                    }
                }

                if (sMatchingFlag.equals("N"))
                {
                    throw new EgovBizException("해당일자의 결과 데이터가 없습니다.");
                }
                
                
                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 청구관리 가상계좌 산출 회원 조회
         * 임동진
         * 20181029
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/list")
        public ModelAndView getVirtualReqWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                List<Map<String, Object>> mList = DlwWdrwService.getVirtualReqList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.17 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    //commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 청구관리 가상계좌 청구
         * 임동진
         * 20181029
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/ext")
        public ModelAndView insertVrtlAccntWdrwExt(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();

            //가상계좌 대상자 입력 리스트
            Map<String, Object> resultParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                }

                int intResult = 0;

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                //가상계좌 산출 대상 리스트 조회
                List<Map<String, Object>> resultList = DlwWdrwService.getVirtualExtList(hmParam);
                for (int i = 0; i < resultList.size(); i++) {
                    resultParam = resultList.get(i);

                    hmParam.put("accnt_no", resultParam.get("ACCNT_NO")); 	//회원번호
                    hmParam.put("mem_no", resultParam.get("MEM_NO")); 		//고유번호
                    hmParam.put("pay_bit", resultParam.get("REQ_PAY_BIT")); //청구비트
                    hmParam.put("req_pay_no", resultParam.get("REQ_PAY_NO")); 	//청구회차
                    hmParam.put("req_gunsu", resultParam.get("REQ_GUNSU")); //청구 건수
                    hmParam.put("pay_amt", resultParam.get("PAY_AMT")); 	//청구 금액
                    hmParam.put("month_cnt", resultParam.get("MONTH_CNT")); //당월회차
                    hmParam.put("prod_cd", resultParam.get("PROD_CD")); 	//상품코드

                    hmParam.put("stat", resultParam.get("STAT")); 			//승인 상태
                    hmParam.put("kstbit", resultParam.get("KSTBIT")); 		//회원 상태
                    hmParam.put("true_cnt", resultParam.get("TRUE_CNT")); 	//실납입 회차
                    
                    
                    //가상계좌 청구 TB에 산출 적용
                    intResult = DlwWdrwService.insertVirtualMemData(hmParam);
                }
                
                if (intResult > 0 ){
                    //가상계좌 산출데이터 청구 등록
                    DlwWdrwService.insertVirtualReq(hmParam);
                }
                
                //임시저장목록 삭제
                //DlwVrtlAcntTestService.deleteTempVrtlAccntWdrwReq(hmParam);

                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    if (rowType == DataSet.ROW_TYPE_UPDATED){
                        //DlwVrtlAcntTestService.insertTempVrtlAccntWdrwReq(hmParam);
                    }
                }

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));
                //int result = DlwVrtlAcntTestService.insertVrtlAccntWdrwReq(hmParam);
                //hmParam.put("result", Integer.valueOf(result));
                hmParam.put("result", null); // 추가, 정출연 - 2016.10.13
                //DlwVrtlAcntTestService.insertVrtlAccntWdrwReq(hmParam);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 가상계좌 청구 회원 NICE 전송 및 상태값 변경 (성공 시 청구 테이블 등록)
         * 임동진
         * 20181029
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value =  "/virtual/nice-send")
        public ModelAndView sendNiceVrtlReqList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");


                /****************************************************************************************
                     * 가상계좌 NICE 전송 시 청구 회원인지 여부 확인  	             		             	*
                int ReqchkCnt = 0;
                List<Map<String, Object>> ReqChkList = DlwWdrwService.getVirtualReqYn(hmParam);
                ReqchkCnt = ReqChkList.size();

                if (ReqchkCnt > 0) {
                    throw new EgovBizException("청구 중인 회원이 존재 합니다. 확인 바랍니다.> " + hmParam.get("accnt_no"));
                }
                /****************************************************************************************/

                int totCnt = 0;
                int sucCnt = 0;
                int errCnt = 0;
                String paramFlag = "";

                paramFlag = mapInVar.get("pram_flag").toString();


                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                    try {

                        hmParam.put("yymmdd", mapInVar.get("yymmdd"));
                        hmParam.put("inv_dt", mapInVar.get("inv_dt"));
                        hmParam.put("inv_time", mapInVar.get("inv_time"));
                        hmParam.put("wdrw_req_amt_cancel","555");
                        hmParam.put("flag",paramFlag);

                        String mem_nm = (String)hmParam.get("mem_nm") != null ? (String)hmParam.get("mem_nm") : "";
                        if(!"".equals(mem_nm)) {
                            mem_nm = (new StringBuilder("(")).append(mem_nm).append(")").toString();
                        }

                        HttpServletRequestMock request = new HttpServletRequestMock();
                        HttpServletResponseMock response = new HttpServletResponseMock();

                        NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);

                        httpRequestWrapper.addParameter("VbankBankCode", (String)hmParam.get("bank_cd"));
                        httpRequestWrapper.addParameter("VbankNum", (String)hmParam.get("vrtl_accnt_no"));
                        httpRequestWrapper.addParameter("VBankAccountName", (new StringBuilder("대명라이프")).append(mem_nm).toString());

                        if (paramFlag != "del"){
                            hmParam.put("mon_pay_amt", mapInVar.get("mon_pay_amt"));
                            httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt")));
                        } else {
                            httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt_cancel")));
                        }

                        httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt")));
                        httpRequestWrapper.addParameter("VbankExpDate", (String)hmParam.get("inv_dt"));
                        httpRequestWrapper.addParameter("VbankExpTime", (String)hmParam.get("inv_time"));
                        httpRequestWrapper.addParameter("Moid", (String)hmParam.get("mem_no"));

                        httpRequestWrapper.addParameter("MID", "dmlife004m");
                        httpRequestWrapper.addParameter("EncodeKey", "Kw61T06XZpKe1SuwJn+hUBnLNwYrqiQWSDR/Xa8/Ua6ZpBnLgGUOkAgzPTFVqn52kyBZSn1y5ANLlCG+6kyoIA==");

                        httpRequestWrapper.addParameter("MallIP", "61.39.175.220");
                        //httpRequestWrapper.addParameter("MallIP", "210.114.225.219");

                        NicePayWEB nicepayWEB = new NicePayWEB();

                        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));

                        nicepayWEB.setParam("NICEPAY_LOG_HOME", sPayFilePath+"/web_site/nice_vrtl_log/send/");
                        nicepayWEB.setParam("APP_LOG", "1");
                        nicepayWEB.setParam("EVENT_LOG", "1");
                        nicepayWEB.setParam("EncFlag", "S");
                        nicepayWEB.setParam("SERVICE_MODE", "PY0");
                        nicepayWEB.setParam("Currency", "KRW");
                        nicepayWEB.setParam("PayMethod", "VBANKFVB01");

                        WebMessageDTO result = nicepayWEB.doService(httpRequestWrapper, response);
                        String resultCode = result.getParameter("ResultCode");
                        String resultMsg = result.getParameter("ResultMsg");
                        String tid = result.getParameter("TID");

                        hmParam.put("result_cd", resultCode);
                        hmParam.put("result_msg", resultMsg);
                        hmParam.put("tid", tid);

                        //세션
                        ParamUtils.addSaveParam(hmParam);
                        hmParam.put("emple_no", hmParam.get("rgsr_id"));
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        hmParam.put("upd_man", hmParam.get("rgsr_id"));

                        if("4120".equals(resultCode)) {
                            sucCnt++;
                        } else {
                            errCnt++;
                        }

                        //결과값 가상계좌 master tb update
                        DlwWdrwService.updateVirtualReqSendtoNice(hmParam);
                    } catch(Exception e) {
                        e.printStackTrace();
                        mapOutVar.put("result", "fail");
                    }
                }

                mapOutVar.put("result", "success");
                mapOutVar.put("totCnt", String.valueOf(totCnt));
                mapOutVar.put("sucCnt", String.valueOf(sucCnt));
                mapOutVar.put("errCnt", String.valueOf(errCnt));


                /****************************************************************************************/
                /****************************************************************************************/
                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 가상계좌 산출 이력 데이터 조회 리스트
         * 임동진
         * 20181105
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/req_result_list")
        public ModelAndView getVirtualReqResultList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = DlwWdrwService.getVirtualReqResultCnt(hmParam);

                mapOutVar.put("tc_log", nTotal);

                List<Map<String, Object>> mList = DlwWdrwService.getVirtualReqResultList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.18 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 가상계좌 산출 이력 중 강제 취소 작업
         * 임동진
         * 20181105
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/req_cancel")
        public ModelAndView updateVirtualReqDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";
            String paramFlag = "";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                paramFlag = mapInVar.get("pram_flag").toString();
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> : " + paramFlag);

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);

                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    String seq = StringUtils.defaultString((String) hmParam.get("seq"));
                    String expirydt = StringUtils.defaultString((String) hmParam.get("expiry_dt"));
                    String vrtlaccntno = StringUtils.defaultString((String) hmParam.get("vrtl_accnt_no"));
                    String bankcd = StringUtils.defaultString((String) hmParam.get("bank_cd"));

                    hmParam.put("seq", seq);
                    hmParam.put("expiry_dt", expirydt);
                    hmParam.put("vrtl_accnt_no", vrtlaccntno);
                    hmParam.put("bank_cd", bankcd);

                    DlwWdrwService.updateVirtualReqDelete(hmParam);
                }

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 가상계좌 산출 대상자 삭제
         * 임동진
         * 20181105
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/ext_delete")
        public ModelAndView updateVirtualExtDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);

                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    if (rowType == DataSet.ROW_TYPE_UPDATED){
                        DlwWdrwService.updateVirtualExtDelete(hmParam);
                    }
                }

                //2018.01.18 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 청구강제마감 업데이트
         * 송준빈
         * 20181211
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/virtual/updateCompulsionWdrdList")
        public ModelAndView getCompulsionWdrdList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try
            {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                System.out.println("파라미터 :: " + hmParam);
                int x = DlwWdrwService.updateCompulsionWdrdList(hmParam);

                //List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
                //listMap.setRowMaps(mList);
                //mapOutDs.put("ds_output", listMap);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /**
         * 특수 산출
         * 정승철
         * 20181211
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/special-wdrw")
        public ModelAndView saveSpecialWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("req_day", mapInVar.get("req_day")); // 청구일
                hmParam.put("pay_mthd", mapInVar.get("pay_mthd")); // 산출구분(04:CMS, 06:카드)

                // 특수 산출
                DlwWdrwService.saveSpecialWdrw(hmParam);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }
        
        /**
         * 특수 산출
         * 정승철
         * 20181211
         * @param pmParam Map<String, Object>
         * @return ModelAndView
         * @throws Exception
         */
        @RequestMapping(value = "/nauth-cancel-wdrw")
        public ModelAndView nauthCancelWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try 
            {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("req_day", mapInVar.get("req_day")); // 청구일
                hmParam.put("pay_mthd", mapInVar.get("pay_mthd")); // 산출구분(04:CMS, 06:카드)

                int nTotal = DlwWdrwService.getDayCardNauthCount(hmParam);
                
                if(nTotal > 0)
                {
                	// 취소건 산출
                    DlwWdrwService.updateNauthCancelCalc(hmParam);
                }
                else
                {
                	Calendar cal = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String today = formatter.format(cal.getTime());
                    
                	String sReqDay = (String)mapInVar.get("req_day");
                    Calendar calReqDay = Calendar.getInstance();
                    SimpleDateFormat sdfInvDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfInvDt.parse(sReqDay);
                    calReqDay.setTime(dateFileNameDay);
                    
                    calReqDay.add(Calendar.DAY_OF_MONTH, -1);

                    String sFileNameDay = sdfInvDt.format(calReqDay.getTime());
                    
                    String fileName = (new StringBuilder("DMSTATION_")).append(sFileNameDay.substring(4, 8)).append(".txt").toString(); 	// 카드 무승인시 청구일자 -1 로 FIX.
                    File f = new File((new StringBuilder("/sftp_home/nicevan/send/")).toString(), fileName);  	    //운영, 로컬
                    
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));
                    StringBuilder strbuf = new StringBuilder();
                    
                    String sSRRecodeGbn = "01"; // 레코드구분
                    String sSRFileCreateDate = today.substring(2, 8); // 파일작성일
                    String sSRDistriNum = "2208809321"; // 유통점사업자번호
                    String sSRNiceNum = "2148174186"; // 나이스사업자번호
                    
                    String sSRLine = CommonUtils.filler_blank_right(sSRRecodeGbn + sSRFileCreateDate + sSRDistriNum + sSRNiceNum, 165, " "); // 스타트 레코드 데이터를 출력
                    
                    strbuf.append(sSRLine);
                    strbuf.append("\r\n");
                    out.write(strbuf.toString());
                    strbuf.delete(0, strbuf.length());
                    
                    String sERRecodeGbn = "02"; // 레코드구분
                	String sERCurrencyCode = "000"; // 통화코드
                	String sERBatchSegmentTotNum = CommonUtils.filler_blank_left(String.valueOf("000000"), 6, "0");
                	String sERBasiIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 일반건수합계
            		String sERBasiPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일단순매출액합계
            		String sERBasiVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일반봉사료합계
            		String sERBasiPayAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 일반매출액합계
            		String sERCnclIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 취소일반건수합계
            		String sERCnclPurePayAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반순매출합계
            		String sERCnclVoluntAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반봉사료합계
            		String sERCnclPayAmtSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소일반매출액합계
            		String sERPlanIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 할부건수합계
            		String sERPlanPaySum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 할부매출액합계
            		String sERPlanCnclIcnt = CommonUtils.filler_blank_left(String.valueOf("0000000"), 7, "0"); // 취소할부건수합계
            		String sERPlanCnclSum = CommonUtils.filler_blank_left(String.valueOf("000000000000"), 12, "0"); // 취소할부매출액합계
                	
            		String sERLine = CommonUtils.filler_blank_right(sERRecodeGbn + sERCurrencyCode + sERBatchSegmentTotNum + sERBasiIcnt + sERBasiPurePayAmtSum
            				+ sERBasiVoluntAmtSum + sERBasiPayAmtSum + sERCnclIcnt + sERCnclPurePayAmtSum + sERCnclVoluntAmtSum + sERCnclPayAmtSum + sERPlanIcnt 
            				+ sERPlanPaySum + sERPlanCnclIcnt + sERPlanCnclSum, 165, " "); // 헤더 레코드 데이터를 출력
            		
            		strbuf.append(sERLine);
            		strbuf.append("\r\n");
                    out.write(strbuf.toString());
                    strbuf.delete(0, strbuf.length());
                    
                    out.close();
                }
                
                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }
   }

