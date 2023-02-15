package powerservice.business.req.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletRequestMock;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletResponseMock;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.web.ConsController;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwEmplService;
import powerservice.business.req.service.ReqCustService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.INICISPay;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
@RequestMapping(value = "/req/reqCust")
public class ReqCustController {

    @Resource
    public BasVlService basVlService;

    @Resource
    private ReqCustService reqCustService;

    @Resource
    private DlwEmplService dlwEmplService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private ConsController consController;

    @Resource
    private DlwCustService dlwCustService;

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
    public ModelAndView getReqCustDlwWdrwListbyReqbit(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String strIniCardYn = "N"; // 이니시스카드 청구여부
            String strReqday = "";    // card 청구 여부

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx   " + hmParam);


            //산출 가능한지 체크
            List<Map<String, Object>> mCheckList = reqCustService.getWdrwReqCheck(hmParam);

            if (mCheckList.size() > 0){
                Map chkMap = mCheckList.get(0);

                strHolyday = chkMap.get("CHK_HOL").toString();
                strCmsYn = chkMap.get("CHK_CMS").toString();
                strCardYn = chkMap.get("CHK_CARD").toString();
                strCardNauthYn = chkMap.get("CHK_CARD_NAUTH").toString();
                strIniCardYn = chkMap.get("CHK_INI_CARD").toString();
            }

            //휴일이 아닌 경우 조회
            if (strHolyday.equals("N")){
                List<Map<String, Object>> mList = reqCustService.getDlwWdrwListByReqbit(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            mapOutVar.put("holy_yn", strHolyday);
            mapOutVar.put("cms_yn", strCmsYn);
            mapOutVar.put("card_yn", strCardYn);
            mapOutVar.put("card_nauth_yn", strCardNauthYn);
            mapOutVar.put("card_ini_yn", strIniCardYn);

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
     * 결과 데이터건수 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchReqResultCount")
    public ModelAndView getReqCustReqResultCount(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = reqCustService.getReqResultCount(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
    public ModelAndView saveReqCustTempWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            reqCustService.saveTempWdrw(hmParam);

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
    @RequestMapping(value = "/special-wdrw")
    public ModelAndView saveReqCustSpecialWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            reqCustService.saveSpecialWdrw(hmParam);

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
     * 파일변환 (** 결과반영)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/convertFile")
    public ModelAndView reqCustConvertFile(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();

            //InetAddress inet = InetAddress.getLocalHost();
            //String uip = inet.getHostAddress();

            //세션
            hmParam.put("req_day", mapInVar.get("req_day")); // 청구일
            hmParam.put("pay_mthd", mapInVar.get("pay_mthd")); // 납입방법
            hmParam.put("pay_kind", "01"); // 결과 종류 (01 : 파일, 02 : 실시간 카드 결제)
            hmParam.put("deposit_day", mapInVar.get("deposit_day"));
            hmParam.put("auth_yn", mapInVar.get("auth_yn").toString());     // 유승인 무승인
            hmParam.put("ini_yn", mapInVar.get("ini_sys_yn").toString());     // 이니시스 여부

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("emple_no", hmParam.get("rgsr_id"));

            System.out.println(hmParam);

        try{
                System.out.println("변환 진행 중....");
                //입금등록 전에 청구 테이블 결과 값 업데이트
                reqCustService.uptReqResultStat(hmParam);

                //업데이트 완료 후 입금 등록
                reqCustService.insertMemPayData(hmParam);


                //파일변환 (** 결과반영)
           }  catch(Exception e) {
               e.printStackTrace();
           }

            //DlwPayService.convertFile(hmParam);

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
    public ModelAndView reqCustNauthCancelWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = reqCustService.getDayCardNauthCount(hmParam);

            if(nTotal > 0)
            {
                // 취소건 산출
                reqCustService.updateNauthCancelCalc(hmParam);
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

    /**
     * 카드 임시건 +정기건 파일 작성 (NEW)
     * 임동진
     * 20180927
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/create-file/card")
    public ModelAndView reqCustCreateFileCard(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam.put("ini_yn", "N"); // 이니시스카드

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                /* ※ 카드 일련번호 생성 업데이트
                 * 파일 작성 시 일련번호를 보내야 하기 때문에 일련번호 생성 후 청구 데이터에 업데이트 후
                 * 청구 데이터의 일련번호 순서 대로 파일 작성 해야 함
                 */
                reqCustService.uptReqResultKey(hmParam);

                //파일 작성 할 대상자 조회
                List<Map<String, Object>> mList = reqCustService.getWdrwReqList(hmParam);

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
                        strbuf.append(CommonUtils.fillEmpVal(8, (String)pmParam.get("RESULT_KEY").toString().substring(0,8), "R", "0")+",");
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
                reqCustService.insertReqWdrw(hmParam);

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
    public ModelAndView reqCustCreateFileCms(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            List<Map<String, Object>> mComnList = reqCustService.getWdrwDlwCmsComnInfo(hmParam);
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
            reqCustService.uptReqResultKey(hmParam);

            //파일 작성 할 대상자 조회
            List<Map<String, Object>> mList = reqCustService.getWdrwReqList(hmParam);

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

                    s = (new StringBuilder("R")).append(pmParam.get("result_key").toString().substring(0,8)).append(useInstCd).append(CommonUtils.fillEmpVal(7, (String)pmParam.get("ichae_cd"), "L", "0")).append(CommonUtils.fillEmpVal(16, (String)pmParam.get("ichae_no"), "L", " ")).append(CommonUtils.fillEmpVal(13, String.valueOf(pmParam.get("pay_amt")), "R", "0")).append(CommonUtils.fillEmpVal(13, (String)pmParam.get("birth"), "L", " ")).append(CommonUtils.fillEmpVal(5, "", "L", " ")).append(CommonUtils.fillEmpVal(8, CommonUtils.transByteForBankFillIn(String.valueOf(pmParam.get("bank_filin_brkdn")).trim()), "L", "　")).append(CommonUtils.fillEmpVal(2, "", "L", " ")).append(CommonUtils.fillEmpVal(20, (String)pmParam.get("accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(5, String.valueOf(pmParam.get("req_pay_no")), "L", " ")).append("1").append(CommonUtils.fillEmpVal(12, "", "L", " ")).append(CommonUtils.fillEmpVal(21, "", "L", " ")).toString();
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
            reqCustService.insertReqWdrw(hmParam);

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

    @RequestMapping(value = "/create-file/cardnauth")
    public ModelAndView reqCustCreateFileCardNAuth(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            reqCustService.uptReqNauthResultKey(hmParam);

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
                mListNauthReqFranCnt = reqCustService.getWdrwNauthReqFranCnt(hmParam);

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
                    String sFranchiUseArea = CommonUtils.filler_blank_right((String)mapNauthReqData.get("accnt_no") + (String)mapNauthReqData.get("result_key").toString().substring(0,10), 28, " ");

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
            reqCustService.insertReqWdrw(hmParam);

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

    @RequestMapping(value = "/create-file/iniCisCard")
    public ModelAndView reqCustCreateFileIniSysCard(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>(); // 파라미터
            Map<String, Object> pmParam = new HashMap<String, Object>(); // 이니시스 청구 Row 데이터

            invDt = (String)mapInVar.get("inv_dt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("pay_mthd", "06"); // 납입방법카드
            hmParam.put("pay_mthd_auth", "Y"); // 유,무승인여부
            hmParam.put("ini_yn", "Y"); // 이니시스카드
            hmParam.put("req_day", invDt); // 청구일자
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            /* ※ 카드 일련번호 생성 업데이트
             * 파일 작성 시 일련번호를 보내야 하기 때문에 일련번호 생성 후 청구 데이터에 업데이트 후
             * 청구 데이터의 일련번호 순서 대로 파일 작성 해야 함
             */
            reqCustService.uptReqResultKey(hmParam);

            //파일 작성 할 대상자 조회
            List<Map<String, Object>> mList = reqCustService.getWdrwReqList(hmParam);

            String mid = "DaemyuBIGI";
            String billGubun = "00";

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String today = formatter.format(cal.getTime());

            //fileName = (new StringBuilder(mid)).append("_").append(invDt).append(".REQ").toString();
            fileName = (new StringBuilder(mid)).append("_").append(today).append(".REQ").toString();

            File f = new File((new StringBuilder("/sftp_home/inicis/send/")).toString(), fileName);  	//운영
            //File f = new File((new StringBuilder("c://app/CARD/NICE/SEND/")).toString(), fileName); 	//로컬

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));

            strbuf = new StringBuilder();
            strbuf.append("H").append(",").append(today).append(",").append(mList.size()); // 파일의 1번째행은 헤더로서 "H,YYYYMMDD,건수" 의 형태를 가진다
            strbuf.append("\r\n");

            out.write(strbuf.toString());
            strbuf.delete(0, strbuf.length());

            for(int i = 0; mList.size() > i; i++)
            {
                pmParam = mList.get(i);
                pmParam.put("billGubun", billGubun);

                String sProdNm = ((String)pmParam.get("PROD_NM")).replaceAll(",", " ");
                String sMemNm = (String)pmParam.get("MEM_NM");

                /*파일 작성 */
                strbuf.append(CommonUtils.fillEmpVal(8, (String)pmParam.get("RESULT_KEY").toString().substring(0,8), "R", "0")+",");
                strbuf.append(CommonUtils.fillEmpVal(40, (String)pmParam.get("ICHAE_NO"), "L", " ")+",");
                strbuf.append(sProdNm + ",");
                strbuf.append(CommonUtils.stringValueOf(pmParam.get("PAY_AMT"))+",");
                strbuf.append((String)pmParam.get("ACCNT_NO")+",");

                if(sMemNm.length() > 20) {
                    strbuf.append(sMemNm.substring(0, 20)+",");
                } else {
                    strbuf.append(sMemNm+",");
                }

                strbuf.append((String)pmParam.get("BILL_GUBUN")+",");
                strbuf.append(invDt+",");
                strbuf.append((String)pmParam.get("BILL_KEY"));
                strbuf.append("\r\n");

                out.write(strbuf.toString());
                strbuf.delete(0, strbuf.length());

                if(i % 100 == 0)
                {
                    out.flush();
                }
            }

            out.close();

            reqCustService.insertReqWdrw(hmParam); // 청구 파일 작성 후 업데이트 처리

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

    /* ================================================================
     * 날짜 : 20181120
     * 이름 : 정승철
     * 추가내용 : CMS 업로드 처리 (** 결과등록)
     * ================================================================
     * */
    @RequestMapping(value = "/data_upload")
    public void reqCustUploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();


        try {
                // 예전 cms데이터 업로드
                //reqCustService.dataFileUpload(request, response, mResult);

                reqCustService.cmsDataFileUpload(request, response, mResult);

            } catch (EgovBizException e) {
                resVarList.add("ErrorCode", "-1");
                fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
                resVarList.add("ErrorMsg", fileNm + "|" + e.getMessage());
                e.printStackTrace();

            } catch (Exception e) {
                resVarList.add("ErrorCode", "-1");
                resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
                e.printStackTrace();
            }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/nicepayment/sendrecvlist")
    public ModelAndView reqCustSendFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        hmParam.put("charge_dt", stt_dt); // 송신월
        System.out.println("송신월 ::: " + stt_dt);

        // 세션
        //ParamUtils.addSaveParam(hmParam);
        //String reg_man = (String) hmParam.get("rgsr_id"); // 현재 세션 접속자 사번

        // 연결될 FTP 주소및 포트
        //FTPClient ftpClient = null;
        Session session = null;
        JSch jsch = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        //String sNicePaymentFtpIp =  "192.168.15.6"; // 테스트용 ip
        //int iNicePaymentFtpPort = 21;               // 테스트용 port
        //String sFTPLoginId = "DMDLCCFTP";           // 테스트용 접속 Id
        //String sFTPLoginPw = "31206love";           // 테스트용 접속 PassWord

        String sNicePaymentFtpIp = "121.133.126.8";   // 실제 ip
        int iNicePaymentFtpPort = 22;                 // 실제 port
        String sFTPLoginId = basVlService.getBasVlAsString("nice_ftp_access_id"); // 실제 접속 Id (dmlife000g)
        String sFTPLoginPw = basVlService.getBasVlAsString("nice_ftp_access_pw"); // 실제 접속 PassWord (eoaudqlffld1!)

        System.out.println("sFTPLoginId ::: " + sFTPLoginId + " sFTPLoginPw ::: " + sFTPLoginPw);
        // 저장할 파일 위치
        //String sSendFileDirectoryPath = "C://app/CARD/NICE/SEND/"; // 로컬테스트용
        //String sSendFileDirectoryPath = "/app/CARD/NICE/SEND/";
        String sSendFileDirectoryPath = "/home/dmlife000g/send";

        //String sFTPReceiveDirectoryPath = "/CARD/NICE/SEND_OK/";      // 로컬 테스트 FTP상의 Path
        String sFTPReceiveDirectoryPath = "/home/dmlife000g/send_ok"; // 개발/운영서버테스트용

        BufferedReader br = null;

        InputStreamReader isr = null;
        BufferedReader ftpBr = null;

        //File sendFileDirectory = new File(sSendFileDirectoryPath);
        //String[] sendFileList = sendFileDirectory.list();
        try
        {
            /* 대명스테이션 -> NICEPAYMENT 로 보낸 자료를 조회한뒤 정보를 조회 */
            /*
            for(int idx = 0; idx < sendFileList.length; idx++)
            {
                if(sendFileList[idx].indexOf(stt_dt) > 0)
                {
                    Map<String,Object> map = new HashMap<String,Object>();
                    File singleListFile = new File(sSendFileDirectoryPath + sendFileList[idx]);
                    br = new BufferedReader(new FileReader(singleListFile));
                    String line = null;
                    String sSendDate = null;
                    String sTotalCnt = null;

                    if((line = br.readLine()) != null)
                    {
                        sSendDate = line.split(",")[1];
                        sTotalCnt = line.split(",")[2];
                        System.out.println("읽은 데이터 일람 ::: " + line);
                    }

                    map.put("file_name", sendFileList[idx]);
                    map.put("count", sTotalCnt);
                    map.put("date", sSendDate);
                    map.put("file_type", "1");
                    map.put("recv_yn", "N");

                    mList.add(map);
                }
            }


            System.out.println("로컬 서버에 있는 데이터 일람 : " + mList.toString());
            */

            /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. START !!! (이 로직은 SFTP 접속시 사용) */
            jsch = new JSch();

            session = jsch.getSession(sFTPLoginId, sNicePaymentFtpIp, iNicePaymentFtpPort);
            session.setPassword(sFTPLoginPw);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.connect();

            //수신 디렉토리 접근
            channelSftp.cd(sSendFileDirectoryPath);

            //디렉토리 안의 목록 조회
            Vector<ChannelSftp.LsEntry> sendFileList = channelSftp.ls(sSendFileDirectoryPath);

            for(ChannelSftp.LsEntry entry : sendFileList)
            {
                String singleListFile = null;
                String line = null;
                String sRecvDate = null;
                String sTotalCnt = null;

                System.out.println(entry.getFilename());
                //if(!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)

                if(entry.getFilename().indexOf(stt_dt) > 0 && !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)
                {
                    System.out.println("조건에 맞는 파일명은 다음과 같습니다.(보낸파일) : " + entry.getFilename());

                    Map<String,Object> map = new HashMap<String,Object>();
                    ftpBr = new BufferedReader(new InputStreamReader(channelSftp.get(entry.getFilename())));

                    if((line = ftpBr.readLine()) != null)
                    {
                        sRecvDate = line.split(",")[1];
                        sTotalCnt = line.split(",")[2];
                        System.out.println("읽은 데이터 일람 ::: " + line);
                    }

                    map.put("file_name", entry.getFilename());
                    map.put("count", sTotalCnt);
                    map.put("date", sRecvDate);
                    map.put("file_type", "1");
                    map.put("recv_yn", "N");
                    mList.add(map);

                    ftpBr.close();
                    //isr.close();
                }
                else
                {
                    System.out.println("조건에 맞지 않습니다.");
                }
            }

            channelSftp.cd(sFTPReceiveDirectoryPath);
            Vector<ChannelSftp.LsEntry> recvFileList = channelSftp.ls(sFTPReceiveDirectoryPath);

            for(ChannelSftp.LsEntry entry : recvFileList)
            {
                String singleListFile = null;
                String line = null;
                String sRecvDate = null;
                String sTotalCnt = null;

                System.out.println(entry.getFilename());
                //if(!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)

                if(entry.getFilename().indexOf(stt_dt) > 0 && !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)
                {
                    System.out.println("조건에 맞는 파일명은 다음과 같습니다.(받은파일) : " + entry.getFilename());

                    Map<String,Object> map = new HashMap<String,Object>();
                    ftpBr = new BufferedReader(new InputStreamReader(channelSftp.get(entry.getFilename())));

                    if((line = ftpBr.readLine()) != null)
                    {
                        sRecvDate = line.split(",")[1];
                        sTotalCnt = line.split(",")[2];
                        System.out.println("읽은 데이터 일람 ::: " + line);
                    }

                    map.put("file_name", entry.getFilename());
                    map.put("count", sTotalCnt);
                    map.put("date", sRecvDate);
                    map.put("file_type", "2");
                    map.put("recv_yn", "N");
                    mList.add(map);

                    ftpBr.close();
                    //isr.close();
                }
                else
                {
                    System.out.println("조건에 맞지 않습니다.");
                }
            }

            /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. END !!! */

            System.out.println(mList.toString());
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if(br != null)
            {
                br.close();
            }
            if(isr != null)
            {
                isr.close();
            }
            if(ftpBr != null)
            {
                ftpBr.close();
            }
            if(channelSftp != null)
            {
                channelSftp.disconnect();
            }
            if(channel != null)
            {
                channel.disconnect();
            }
            if(session != null)
            {
                session.disconnect();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/nicepayment/sendnauthrecvlist")
    public ModelAndView reqCustSendnauthrecvlist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        String sSearchMonth =  (String) mapInVar.get("stt_dt").toString().substring(4,6);

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMM");
        cal.setTime(df.parse(stt_dt));
        cal.add(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        String sNextMonth = df.format(cal.getTime());
        String sParseNextMonth = sNextMonth.substring(4, 6);

        hmParam.put("charge_dt", stt_dt); // 송신월
        System.out.println("송신월 ::: " + stt_dt);


        // 저장할 파일 위치
        String sSendFileDirectoryPath = "/sftp_home/nicevan/send/";
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/";

//        String sSendFileDirectoryPath = "C://app/sftp_home/nicevan/send/"; // 로컬에서 테스트 수행시
//        String sRecvFileDirectoryPath = "C://app/sftp_home/nicevan/recv/"; // 로컬에서 테스트 수행시

        BufferedReader br = null;
        InputStreamReader isr = null;
        RandomAccessFile rafile = null;

        try
        {
            File fileSendFileDirectoryPath = new File(sSendFileDirectoryPath);
            String[] arrSendFileList = fileSendFileDirectoryPath.list();

            for(int idx = 0; idx < arrSendFileList.length; idx++)
            {
                String sFileName = arrSendFileList[idx];
                //String sTarget = sFileName.substring(10, 12);
                String sTarget = sFileName.substring(12, 14);
                System.out.println(sFileName);

                if(sTarget.equals(sSearchMonth))
                {
                    Map<String,Object> rowData = new HashMap<String, Object>();

                    rafile = new RandomAccessFile(sSendFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                    {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine  = rafile.readLine();
                    String sReqConut  = sLastLine.substring(11, 18);
                    String sCnclCount = sLastLine.substring(54, 61);
                    int    iReqConut  = Integer.parseInt(sReqConut);
                    int    iCnclCount = Integer.parseInt(sCnclCount);

                    rafile.close();

                    String sFilesub = "";

                    if("DM".equals(sFileName.substring(0, 2))){
                        sFilesub = sFileName.substring(10, 14);
                    } else {
                        sFilesub = sFileName.substring(12, 16);
                    }

                    String sSendDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFilesub;
                    Calendar calSendDt = Calendar.getInstance();
                    SimpleDateFormat sdfSendDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfSendDt.parse(sSendDt);
                    calSendDt.setTime(dateFileNameDay);

                    calSendDt.add(Calendar.DAY_OF_MONTH, +1);
                    String sSendDay = sdfSendDt.format(calSendDt.getTime());

                    rowData.put("file_name", sFileName);
                    rowData.put("count", iReqConut + iCnclCount);
                    rowData.put("date", sSendDay);
                    rowData.put("recv_yn", "");
                    rowData.put("file_type", "1");
                    mList.add(rowData);
                }
            }

            File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);
            String[] arrRecvFileList = fileRecvFileDirectoryPath.list();

            for(int idx = 0; idx < arrRecvFileList.length; idx++)
            {
                String sFileName = arrRecvFileList[idx];
                //String sTarget = sFileName.substring(3, 5); // 파일명 B191205.DMS 기준
                String sTarget = sFileName.substring(8, 10); // 파일명 NICEVAN_1225 기준

                if(sTarget.equals(sSearchMonth) || sTarget.equals(sParseNextMonth))
                {
                    Map<String,Object> rowData = new HashMap<String, Object>();

                    rafile = new RandomAccessFile(sRecvFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                    {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine = rafile.readLine();
                    String sReqConut = sLastLine.substring(2, 8);
                    int    iReqConut = Integer.parseInt(sReqConut);

                    String recvMMDD = sFileName.substring(8, 12); // 해당파일의 월일
                    Integer recvYYYY = Integer.parseInt(mapInVar.get("stt_dt").toString().substring(0, 4)); // 해당파일의 년도
                    Integer recvNextYYYY = recvYYYY + 1; // 다음해의 년도

                    //if(recvMMDD.equals("0101") || recvMMDD.equals("0102")) // 20191230 -> 20200101, 20191231 -> 20200102 에 대한 검색 조건 분기

                    if(recvMMDD.equals("0101"))
                    {
                        hmParam.put("req_day", recvNextYYYY.toString() + recvMMDD);
                    }
                    else
                    {
                        hmParam.put("req_day", recvYYYY.toString() + recvMMDD);
                    }

                    //hmParam.put("req_day", recvYYYY.toString() + recvMMDD);

                    /*
                    String sRecvDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFileName.substring(8, 12);
                    Calendar calRecvDt = Calendar.getInstance();
                    SimpleDateFormat sdfRecvDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfRecvDt.parse(sRecvDt);
                    calRecvDt.setTime(dateFileNameDay);

                    calRecvDt.add(Calendar.DAY_OF_MONTH, +2);
                    String sRecvDay = sdfRecvDt.format(calRecvDt.getTime());
                    */

                    rafile.close();

                    //int    iFranCnt  = DlwWdrwService.getWdrwNauthReqFranCount(hmParam);
                    //iReqConut = iReqConut - ((iFranCnt * 2) + 2); // iReqConut = 전체 레코드의 개수 - (요청한 프랜차이즈의 헤더와 테일을 제외한다. 그리고 start와 end를 제외한다.)

                    rowData.put("file_name", sFileName);
                    rowData.put("count", iReqConut);
                    rowData.put("date", ((String)hmParam.get("req_day")).substring(0,4) + recvMMDD);
                    //rowData.put("date", sRecvDay);
                    rowData.put("recv_yn", "");
                    rowData.put("file_type", "2");
                    mList.add(rowData);
                }
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {

        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 Card File List 수신리스트 다운로드 및 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/recvCardFileList")
    public ModelAndView reqCustRecvfilelist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList1 = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_month = (String) mapInVar.get("stt_month").toString().substring(0,6);
        String stt_dt    = (String) mapInVar.get("stt_dt").toString();
        System.out.println("송신월 ::: " + stt_month + " 산출기준일 ::: " + stt_dt);

        String sFTPFileDirectoryPath  = "/home/dmlife000g/billrslt/" + stt_month + "/";
        //String sRecvFileDirectoryPath = "C:\\app\\dmlife000g\\billrslt\\" + stt_month + "\\"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/billrslt/" + stt_month + "/"; // 서버에서 수행시

        Session session = null;
        JSch jsch = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        InputStream ftpIn = null;
        InputStreamReader ftpIsr= null;
        BufferedReader ftpBr = null;

        LineNumberReader reader  = null;

        String sNicePaymentFtpIp = "121.133.126.8";   // 실제 FTP 서버 IP
        int iNicePaymentFtpPort = 22;                 // 실제 FTP PORT
        String sFTPLoginId = basVlService.getBasVlAsString("nice_ftp_access_id"); // 실제 접속 Id (dmlife000g)
        String sFTPLoginPw = basVlService.getBasVlAsString("nice_ftp_access_pw"); // 실제 접속 PassWord (eoaudqlffld1!)

        System.out.println("sFTPLoginId ::: " + sFTPLoginId + " sFTPLoginPw ::: " + sFTPLoginPw);
        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        if(fileRecvFileDirectoryPath.exists() == false)
        {
            fileRecvFileDirectoryPath.mkdirs();
        }

        String[] asRecvFileList = fileRecvFileDirectoryPath.list();
        Vector<String> vRecvFileList = new Vector<String>();

        for(int idx = 0; idx < asRecvFileList.length; idx++)
        {
            vRecvFileList.add(asRecvFileList[idx]);
        }

        /* 이 부분에서 본서버(192.168.10.6X) 로 받아오지 않은 파일을 모두 받아옴 */
        try
        {
            jsch = new JSch();

            session = jsch.getSession(sFTPLoginId, sNicePaymentFtpIp, iNicePaymentFtpPort);
            session.setPassword(sFTPLoginPw);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.connect();

            //수신 디렉토리 접근
            channelSftp.cd(sFTPFileDirectoryPath);

            //디렉토리 안의 목록 조회
            Vector<ChannelSftp.LsEntry> sendFileList = channelSftp.ls(sFTPFileDirectoryPath);

            System.out.println("받은 파일 내역 : " + vRecvFileList.toString());
            System.out.println("FTP 파일 내역 : " + sendFileList.toString());
            for(ChannelSftp.LsEntry entry : sendFileList)
            {
                String ftpFileName = entry.getFilename();
                if(!ftpFileName.equals(".") && !ftpFileName.equals("..") && ftpFileName.indexOf(".") != 0)
                {
                    if(vRecvFileList.contains(ftpFileName) == false)
                    {
                        Map<String, Object> hmFileBasicInfo = new HashMap<String, Object>();
                        int nFileLineCnt = 0;

                        System.out.println("없는 파일이니 파일을 쓰겠습니다.");
                        StringBuffer sb = new StringBuffer();
                        ftpIn = channelSftp.get(ftpFileName);
                        ftpIsr = new InputStreamReader(ftpIn, "EUC-KR");
                        ftpBr = new BufferedReader(ftpIsr);

                        System.out.println("파일의 경로와이름은 : " + sRecvFileDirectoryPath + ftpFileName);
                        //ftpOut = new FileOutputStream(new File(sRecvFileDirectoryPath + ftpFileName));
                        //ftpOsw = new OutputStreamWriter(ftpOut);
                        //ftpBw = new BufferedWriter(ftpOsw);

                        String ftpLine = "";
                        System.out.println("파일을 쓰고 있습니다.");

                        while ((ftpLine = ftpBr.readLine()) != null)
                        {
                            //ftpBw.write(ftpLine);
                            //ftpBw.newLine();
                            sb.append(String.format("%s", ftpLine));
                            sb.append("\n");
                            nFileLineCnt++;
                        }

                        hmFileBasicInfo.put("req_month", stt_month);
                        hmFileBasicInfo.put("date", ftpFileName.substring(19,29).replaceAll("_", ""));
                        hmFileBasicInfo.put("file_name", ftpFileName);
                        hmFileBasicInfo.put("count", nFileLineCnt - 1);
                        hmFileBasicInfo.put("rslt_yn", "미반영");
                        hmFileBasicInfo.put("pay_mthd_auth", "Y");
                        hmFileBasicInfo.put("ini_yn", "N");
                        reqCustService.insertCardRecvFileList(hmFileBasicInfo);

                        CommonUtils.writeTextFile(sRecvFileDirectoryPath + ftpFileName, sb.toString(), "EUC-KR"); // MS949 / MS932 / UTF-8
                        System.out.println("파일을 모두 작성하였습니다.");
                        //ftpBw.flush();
                    }
                    else
                    {
                        System.out.println("이미 존재하는 파일입니다. 다운로드를 하지 않습니다.");
                    }
                }
                else
                {
                    System.out.println("기준에 맞지 않는데이터 입니다.");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            String exMessage = e.getMessage();
            System.out.println("에러메시지 ::: " + exMessage);
            if(exMessage.equals("No such file"))
            {
                szErrorMsg = "조회된 데이터가 없습니다.";
            }
            else
            {
                szErrorMsg = exMessage;
            }
        }
        finally
        {
            if(ftpBr != null)
            {
                ftpBr.close();
            }
            if(ftpIsr != null)
            {
                ftpIsr.close();
            }
            if(ftpIn != null)
            {
                ftpIn.close();
            }
            if(channelSftp != null)
            {
                channelSftp.quit();
            }
        }

        /* 본서버에 있는 파일을 읽어옴 */
        //File files = new File(sRecvFileDirectoryPath);
        //String[] aRecvFiles = files.list();

        try
        {
            hmParam.put("req_month", stt_month);
            hmParam.put("pay_mthd_auth", "Y");
            hmParam.put("ini_yn", "N");
            //hmParam.put("pay_mthd", "06");
            //hmParam.put("pay_kind", "01");

            mList = reqCustService.getCardRecvFileList(hmParam);


            for(int idx = 0; idx < mList.size(); idx++)
            {
                Map<String,Object> rowData = mList.get(idx);
                String sFileMakeDay = (String)rowData.get("date1");
                String sRsltYn = (String)rowData.get("rslt_yn");

                if(sRsltYn.equals("미반영"))
                {
                    if(!sFileMakeDay.equals(stt_dt))
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }

                mList1.add(rowData);
            }
            /*
            for(int idx = 0; idx < aRecvFiles.length; idx++)
            {
                String fileName = aRecvFiles[idx];
                String fileMakeDate = fileName.substring(19,29).replaceAll("_", "");

                Map<String,Object> rowData = new HashMap<String, Object>();
                reader  = new LineNumberReader(new FileReader(sRecvFileDirectoryPath + fileName));
                int lineCnt = 0;
                String readLine = "";

                while ((readLine = reader.readLine()) != null)
                {

                }

                lineCnt = reader.getLineNumber() - 1;

                reader.close();

                rowData.put("file_name", fileName);
                rowData.put("count", lineCnt);
                rowData.put("date", fileMakeDate);

                hmParam.put("req_day", fileMakeDate);
                hmParam.put("pay_mthd", "06");
                hmParam.put("pay_kind", "01");

                int isFileExistCnt = DlwPayService.getWdrwResultCount(hmParam);

                if(isFileExistCnt <= 0)
                {
                    if(stt_dt.equals(fileMakeDate))
                    {
                        rowData.put("rslt_yn", "미반영");
                    }
                    else
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }
                else
                {
                    rowData.put("rslt_yn", "반영완료");
                }

                if(lineCnt >= 1)
                {
                    mList.add(rowData);
                }
            }
            */
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }
        finally
        {
            if(reader != null)
            {
                reader.close();
            }
        }

        System.out.println(mList1.toString());
        listMap.setRowMaps(mList1);
        mapOutDs.put("ds_output", listMap);

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1! << 패스워드는 3달에 한번씩 변경된다.
     */
    @RequestMapping(value = "/insertCardFileList")
    public ModelAndView reqCustInsertAcquResultData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String sSearchAuthDt = (String) mapInVar.get("auth_dt").toString();
        String sFileName = (String) mapInVar.get("file_name").toString();
        hmParam.put("req_day", sSearchAuthDt);
        hmParam.put("pay_mthd", "06");
        hmParam.put("auth_yn", "Y");
        hmParam.put("ini_yn", "N");
        hmParam.put("pay_mthd_auth", "Y");
        hmParam.put("pay_kind", "01");

        //System.out.println("승인날짜 ::: " + sSearchAuthDt + ", 파일이름 ::: " + sFileName);

        //String sRecvFileDirectoryPath = "C:/app/dmlife000g/billrslt/" + sSearchAuthDt.substring(0, 6) + "/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/billrslt/" + sSearchAuthDt.substring(0, 6) + "/"; // 서버에서 수행시

        File files = new File(sRecvFileDirectoryPath, sFileName);
        //FileReader fr = null;
        //BufferedReader br = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
        String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

         /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND,";
        sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID, AUTH_YN)";
        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'CARD_AUTO',?, 'Y')";

        int iDataIdx = 0;
        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
            int isFileExist = reqCustService.getWdrwResultCount(hmParam);
            if(isFileExist <= 0)
            {
                is  = new FileInputStream(files);
                isr = new InputStreamReader(is, "EUC-KR");
                br  = new BufferedReader(isr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                    //System.out.println("라인 내용 ::: " + sLine);
                    //String sRecordGb = sLine.substring(0, 2);
                    sLine = sLine.replaceAll("\"", "");
                    String[] parseLine = sLine.split(",");

                    if(iDataIdx == 0)
                    {

                    }
                    else
                    {
                        Map<String,Object> rowDataDtl = new HashMap<String, Object>();

                        ParamUtils.addSaveParam(rowDataDtl);

                        String sAccntNo   = parseLine[7];                     // 회원번호 :: 7
                        String sReqDay    = parseLine[5].replaceAll("/", ""); // 청구/납입일자 :: 3
                        String sReqNo     = "1";                               // 납입회차 :: 파트장님 문의 (일단 1로 설정 함)
                        String sPayKind   = "01";                             // 01:파일, 02:실시간, 03:자유, 04:기타 (이 로직은 파일로 인한 반영임.)
                        String sTid = parseLine[2];                     // TID :: 2
                        String sResultCd  = parseLine[11];                    // 결과코드 :: 11
                        String sResultMsg = parseLine[12];                    // 결과메시지 :: 12
                        String sPayMthd   = "06";                             // 04:CMS, 06:Card (이로직은 카드로 인한 반영임.)
                        String sAuthDt    = parseLine[5].replaceAll("-", ""); // 승인일자 :: 5
                        String sAuthCd    = parseLine[6];                     // 승인번호 :: 6
                        String sIchaeCd   = parseLine[14];                    // 매입사 :: 14
                        String sResultKey   = ("0000000" + parseLine[16]).substring(("0000000" + parseLine[16]).length() -8,("0000000" + parseLine[16]).length())+ sReqDay + "06N";                    // 결과키 :: 16
                        String sEtc       = "";                               // 결과이슈사항(통장기재내역등)

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
                        preparedStatement.setString(13, sTid.trim());

                        //System.out.println("sReqDay ::: " + parseLine[5]);

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                    }
                    iDataIdx++;
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();

                reqCustService.updateCardRecvFileList(hmParam);
            }
            else
            {
                szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
            if(connection != null)
            {
                connection.rollback() ;
            }
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
            //System.out.println("iDataIdx :::: " + iDataIdx);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
            if(br != null)
            {
                br.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 Card File List(카드 무승인) 수신리스트 다운로드 및 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/recvCardNauthFileList")
    public ModelAndView reqCustRecvCardNauthFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList1 = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_month = (String) mapInVar.get("stt_month").toString().substring(0,6);
        String stt_dt    = (String) mapInVar.get("stt_dt").toString();
        String reqCount  = (String) mapInVar.get("req_count").toString();
        int iReqCount     = Integer.parseInt(reqCount);

        hmParam.put("req_month", stt_month);
        hmParam.put("pay_mthd_auth", "N");
        System.out.println("송신월 ::: " + stt_month + " 산출기준일 ::: " + stt_dt);

        //String sRecvFileDirectoryPath = "C://app/CARD/NICENAUTH/RECV/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/"; //서버에서 수행시

        /*
        InputStream ftpIn = null;
        InputStreamReader ftpIsr= null;
        BufferedReader ftpBr = null;
        */
        RandomAccessFile rafile = null;

        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        if(fileRecvFileDirectoryPath.exists() == false)
        {
            fileRecvFileDirectoryPath.mkdirs();
        }

        /* 이 부분에서 본서버(192.168.10.6X) 로 받아오지 않은 파일을 모두 받아옴 */
        try
        {
            String[] arrRecvFileList = fileRecvFileDirectoryPath.list();

            for(int idx = 0; idx < arrRecvFileList.length; idx++)
            {
                String sFileName = arrRecvFileList[idx];

                Map<String,Object> rowData = new HashMap<String, Object>();
                rowData.put("req_month", stt_month);
                rowData.put("file_name", sFileName);
                int iExist = reqCustService.getCardNauthRecvFileExist(rowData);

                if(iExist <= 0)
                {
                    rafile = new RandomAccessFile(sRecvFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                       {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine  = rafile.readLine();
                    String sReqConut  = sLastLine.substring(2, 8);
                    int    iReqConut  = Integer.parseInt(sReqConut);

                    String recvMMDD = sFileName.substring(8, 12); // 해당파일의 월일
                    Integer recvYYYY = Integer.parseInt(mapInVar.get("stt_dt").toString().substring(0, 4)); // 해당파일의 년도
                    Integer recvNextYYYY = recvYYYY + 1; // 다음해의 년도

                    //if(recvMMDD.equals("0101") || recvMMDD.equals("0102")) // 20191230 -> 20200101, 20191231 -> 20200102 에 대한 검색 조건 분기
                    /*
                    if(recvMMDD.equals("0101") || recvMMDD.equals("0102") || recvMMDD.equals("0103")) // 20191230 -> 20200101, 20191231 -> 20200102 에 대한 검색 조건 분기
                    {
                        hmParam.put("req_day", recvNextYYYY.toString() + recvMMDD);
                    }
                    else
                    {
                        hmParam.put("req_day", recvYYYY.toString() + recvMMDD);
                    }

                    //hmParam.put("req_day", recvYYYY.toString() + recvMMDD);
                    */

                    String sRecvDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFileName.substring(8, 12);
                    Calendar calRecvDt = Calendar.getInstance();
                    SimpleDateFormat sdfRecvDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfRecvDt.parse(sRecvDt);
                    calRecvDt.setTime(dateFileNameDay);

                    calRecvDt.add(Calendar.DAY_OF_MONTH, -1);
                    String sRecvDay = sdfRecvDt.format(calRecvDt.getTime());

                    rafile.close();

                    //int    iFranCnt  = DlwWdrwService.getWdrwNauthReqFranCount(hmParam);
                    //iReqConut = iReqConut - ((iFranCnt * 2) + 2); // iReqConut = 전체 레코드의 개수 - (요청한 프랜차이즈의 헤더와 테일을 제외한다. 그리고 start와 end를 제외한다.)

                    rowData.put("count", iReqConut);
                    //rowData.put("date", ((String)hmParam.get("req_day")).substring(0,4) + recvMMDD);
                    rowData.put("date", sRecvDay);
                    rowData.put("pay_mthd_auth", "N");
                    rowData.put("ini_yn", "N");
                    rowData.put("rslt_yn", "미반영");
                    reqCustService.insertCardRecvFileList(rowData);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            String exMessage = e.getMessage();
            System.out.println("에러메시지 ::: " + exMessage);
        }
        finally
        {
            if(rafile != null)
            {
                rafile.close();
            }
        }

        try
        {
            mList = reqCustService.getCardRecvFileList(hmParam);

            for(int idx = 0; idx < mList.size(); idx++)
            {
                Map<String,Object> rowData = mList.get(idx);
                String sFileMakeDay = (String)rowData.get("date1");

                int iRecvCount = Integer.parseInt(rowData.get("count").toString());
                String sRsltYn = (String)rowData.get("rslt_yn");
                //String sTargetDate = (String)rowData.get("target_date");

                if(sRsltYn.equals("미반영"))
                {
                    /*
                    if( (iRecvCount != iReqCount) || !stt_dt.equals(sTargetDate) )
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                    */

                    if( (iRecvCount != iReqCount) || !stt_dt.equals(sFileMakeDay) )
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }

                mList1.add(rowData);
            }
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }
        finally
        {

        }

        listMap.setRowMaps(mList1);
        mapOutDs.put("ds_output", listMap);

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1! << 패스워드는 3달에 한번씩 변경된다.
     */
    @RequestMapping(value = "/insertCardNauthFileList")
    public ModelAndView reqCustInsertCardNauthFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String sSearchAuthDt = (String) mapInVar.get("auth_dt").toString();
        String sFileName = (String) mapInVar.get("file_name").toString();
        hmParam.put("req_day", sSearchAuthDt);
        hmParam.put("pay_mthd", "06");
        hmParam.put("auth_yn", "N");
        hmParam.put("pay_kind", "01");
        hmParam.put("pay_mthd_auth", "N");

        //System.out.println("승인날짜 ::: " + sSearchAuthDt + ", 파일이름 ::: " + sFileName);

        //String sRecvFileDirectoryPath = "c://app/CARD/NICENAUTH/RECV/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/"; // 서버에서 수행시

        File files = new File(sRecvFileDirectoryPath, sFileName);
        //FileReader fr = null;
        //BufferedReader br = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
        String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

         /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND, RESULT_KEY, RESULT_CD, RESULT_MSG, ";
        sqlStatement += "PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID, AUTH_YN, BAS_CMIS_AMT, FRC_CMIS_AMT, ETC_CMIS_AMT, CUR_PAY_AMT, CANCEL_YN)";
        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'CARD_AUTO',?, 'N', TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?),?)";

        int iDataIdx = 0;
        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
            int isFileExist = reqCustService.getWdrwResultCount(hmParam);
            if(isFileExist <= 0)
            {
                is  = new FileInputStream(files);
                isr = new InputStreamReader(is, "EUC-KR");
                br  = new BufferedReader(isr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                    String sRecordGbn = sLine.substring(0, 2);

                    if(sRecordGbn.equals("60") || sRecordGbn.equals("61") || sRecordGbn.equals("62") || sRecordGbn.equals("63") ||
                       sRecordGbn.equals("64") || sRecordGbn.equals("65") || sRecordGbn.equals("66") || sRecordGbn.equals("67"))
                    {
                        String sAccntNo   = sLine.substring(90, 102);      // 회원번호
                        //String sReqDay    = "20" + sLine.substring(6, 12); // 청구/납입일자
                        String sReqDay    = "20" + sLine.substring(12, 18); // 청구/납입일자
                        String sReqNo     = "1";                           // 납입회차
                        String sPayKind   = "01";                          // 01:파일, 02:실시간, 03:자유, 04:기타 (이 로직은 파일로 인한 반영임.)
                        String sTid       = "00000000";                    // TID
                        String sResultCd  = sLine.substring(53, 55);       // 결과코드
                        String sResultMsg = "";                            // 결과메시지
                        String sPayMthd   = "06";                          // 04:CMS, 06:Card (이로직은 카드로 인한 반영임.)
                        String sAuthDt    = "20" + sLine.substring(12, 18);       // 승인일자
                        String sAuthCd    = sLine.substring(138, 148);     // 승인번호
                        String sIchaeCd   = "";                            // 매입사
                        //String sResultKey   = ("0000000" + sLine.substring(90, 98)).substring(("0000000" + sLine.substring(90, 98)).length() -8,("0000000" + sLine.substring(90, 98)).length()); // 결과키
                        String sResultKey = sLine.substring(102, 112) + sReqDay; 			// 결과키
                        String sEtc       = "";                            // 결과이슈사항(통장기재내역등)
                        String sBasCmisAmt = sLine.substring(78, 90);
                        String sFrcCmisAmt = "0";
                        String sEtcCmisAmt = sLine.substring(68, 78);
                        String sCurPayAmt  = sLine.substring(151, 161);
                        String sCancelYn = "N";										// 결과 값이 취소인지 여부값
                        if (sRecordGbn.equals("67")){
                            sCancelYn = "Y";
                        }

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
                        preparedStatement.setString(13, sTid.trim());
                        preparedStatement.setString(14, sBasCmisAmt.trim());
                        preparedStatement.setString(15, sFrcCmisAmt.trim());
                        preparedStatement.setString(16, sEtcCmisAmt.trim());
                        preparedStatement.setString(17, sCurPayAmt.trim());
                        preparedStatement.setString(18, sCancelYn.trim());

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                    }
                    iDataIdx++;
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();

                reqCustService.updateCardRecvFileList(hmParam);
            }
            else
            {
                szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
            if(connection != null)
            {
                connection.rollback() ;
            }
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
            //System.out.println("iDataIdx :::: " + iDataIdx);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
            if(br != null)
            {
                br.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 이니시스 Card File List 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/recvIniCardFileList")
    public ModelAndView recvIniCardFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mListRst = new ArrayList<Map<String, Object>>();

        // 조회월을 받음.
        String stt_month = (String) mapInVar.get("stt_month").toString().substring(0,6);
        String stt_dt    = (String) mapInVar.get("stt_dt").toString();

        System.out.println("송신월 ::: " + stt_month + " 산출기준일 ::: " + stt_dt);

        //String sRecvFileDirectoryPath = "/sftp_home/inicis/recv/" + stt_month + "/"; // 서버에서 수행시
        String sRecvFileDirectoryPath = "/sftp_home/inicis/recv/";

        InputStream ftpIn = null;
        InputStreamReader ftpIsr= null;
        BufferedReader ftpBr = null;
        RandomAccessFile rafile = null;

        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        try
        {
            if(fileRecvFileDirectoryPath.exists() == false)
            {
                fileRecvFileDirectoryPath.mkdirs();
            }

            String[] asRecvFileList = fileRecvFileDirectoryPath.list();

            for(int rIdx = 0; rIdx < asRecvFileList.length; rIdx++)
            {
                Map<String, Object> rowData = new HashMap<String, Object>();
                String sRecvFileName = asRecvFileList[rIdx];
                rowData.put("file_name", sRecvFileName);
                mList = reqCustService.getIniCardRecvFileConfirm(rowData);

                if(mList.size() <= 0)
                {
                    rafile = new RandomAccessFile(sRecvFileDirectoryPath + sRecvFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                    {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine  = rafile.readLine();
                    String sReqConut  = sLastLine.substring(0, 8);
                    int    iReqConut  = Integer.parseInt(sReqConut);

                    rowData.put("pay_mthd_auth", "Y");
                    rowData.put("ini_yn", "Y");
                    rowData.put("req_month", stt_month);
                    rowData.put("date", sRecvFileName.substring(11,19));
                    rowData.put("count", iReqConut);
                    rowData.put("rslt_yn", "미반영");

                    rafile.close();
                    reqCustService.insertCardRecvFileList(rowData);
                }
            }

            hmParam.put("req_month", stt_month);
            hmParam.put("pay_mthd_auth", "Y");
            hmParam.put("ini_yn", "Y");
            mList = reqCustService.getCardRecvFileList(hmParam);

            for(int idx = 0; idx < mList.size(); idx++)
            {
                Map<String,Object> rowData = mList.get(idx);
                String sFileMakeDay = (String)rowData.get("date1");
                String sRsltYn = (String)rowData.get("rslt_yn");

                if(sRsltYn.equals("미반영"))
                {
                    if(!sFileMakeDay.equals(stt_dt))
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }

                mListRst.add(rowData);
            }
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }

        listMap.setRowMaps(mListRst);
        mapOutDs.put("ds_output", listMap);

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1! << 패스워드는 3달에 한번씩 변경된다.
     */
    @RequestMapping(value = "/insertIniCardFileList")
    public ModelAndView insertIniCardFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 조회월을 받음.
        String sSearchAuthDt = (String) mapInVar.get("auth_dt").toString();
        String sFileName = (String) mapInVar.get("file_name").toString();
        hmParam.put("req_day", sSearchAuthDt);
        hmParam.put("pay_mthd", "06");
        hmParam.put("auth_yn", "Y");
        hmParam.put("pay_mthd_auth", "Y");
        hmParam.put("ini_yn", "Y");
        hmParam.put("pay_kind", "01");

        //String sRecvFileDirectoryPath = "C:/app/dmlife000g/billrslt/" + sSearchAuthDt.substring(0, 6) + "/"; // 로컬에서 테스트 수행시
        //String sRecvFileDirectoryPath = "/sftp_home/inicis/recv/" + sSearchAuthDt.substring(0, 6) + "/"; // 서버에서 수행시

        String sRecvFileDirectoryPath = "/sftp_home/inicis/recv/"; // 서버에서 수행시

        File files = new File(sRecvFileDirectoryPath, sFileName);
        //FileReader fr = null;
        //BufferedReader br = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
        String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

         /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND,";
        sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID, AUTH_YN, INI_YN)";
        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'INI_AUTO',?, 'Y', 'Y')";

        int iDataIdx = 0;
        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
            int isFileExist = reqCustService.getWdrwResultCount(hmParam);
            if(isFileExist <= 0)
            {
                is  = new FileInputStream(files);
                isr = new InputStreamReader(is, "EUC-KR");
                br  = new BufferedReader(isr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                    //System.out.println("라인 내용 ::: " + sLine);
                    //String sRecordGb = sLine.substring(0, 2);
                    sLine = sLine.replaceAll("\"", "");
                    String[] parseLine = sLine.split(",");

                    if(iDataIdx == 0)
                    {

                    }
                    else
                    {
                        Map<String,Object> rowDataDtl = new HashMap<String, Object>();

                        ParamUtils.addSaveParam(rowDataDtl);

                        String sAccntNo   = parseLine[7];                     // 회원번호 :: 7
                        String sReqDay    = parseLine[3];                     // 청구/납입일자 :: 3
                        String sReqNo     = "1";                              // 납입회차 :: 파트장님 문의 (일단 1로 설정 함)
                        String sPayKind   = "01";                             // 01:파일, 02:실시간, 03:자유, 04:기타 (이 로직은 파일로 인한 반영임.)
                        String sTid       = parseLine[2];                     // TID :: 2
                        String sResultCd  = parseLine[11];                    // 결과코드 :: 11
                        String sResultMsg = parseLine[12];                    // 결과메시지 :: 12
                        String sPayMthd   = "06";                             // 04:CMS, 06:Card (이로직은 카드로 인한 반영임.)
                        String sAuthDt    = parseLine[5];                     // 승인일자 :: 5
                        String sAuthCd    = parseLine[6];                     // 승인번호 :: 6
                        String sIchaeCd   = parseLine[14];                    // 매입사 :: 14
                        String sResultKey = ("0000000" + parseLine[0]).substring(("0000000" + parseLine[0]).length() -8,("0000000" + parseLine[0]).length()) + sReqDay + "06Y"; // 결과키 :: 16
                        String sEtc       = "";                               // 결과이슈사항(통장기재내역등)

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
                        preparedStatement.setString(13, sTid.trim());

                        //System.out.println("sReqDay ::: " + parseLine[5]);

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                    }
                    iDataIdx++;
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();

                reqCustService.updateCardRecvFileList(hmParam);
            }
            else
            {
                szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
            if(connection != null)
            {
                connection.rollback() ;
            }
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
            //System.out.println("iDataIdx :::: " + iDataIdx);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
            if(br != null)
            {
                br.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/inicard/sendrecvlist")
    public ModelAndView sendFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        hmParam.put("charge_dt", stt_dt); // 송신월
        System.out.println("송신월 ::: " + stt_dt);

        String sSendIniCardFilePath = "/sftp_home/inicis/send/";
        String sRecvIniCardFilePath = "/sftp_home/inicis/recv/";

        File fileSendIniCard = new File(sSendIniCardFilePath);
        File fileRecvIniCard = new File(sRecvIniCardFilePath);

        RandomAccessFile rafile = null;

        try
        {
            String[] arrSendFileList = fileSendIniCard.list();

            for(int sendIdx = 0; sendIdx < arrSendFileList.length; sendIdx++)
            {
                String sFileName = arrSendFileList[sendIdx];

                if(sFileName.indexOf(stt_dt) > 0 &&  sFileName.indexOf(".") != 0)
                {
                    Map<String,Object> map = new HashMap<String,Object>();

                    rafile = new RandomAccessFile(sSendIniCardFilePath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                    {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine  = rafile.readLine();
                    String sReqConut  = sLastLine.substring(0, 8);
                    int    iReqConut  = Integer.parseInt(sReqConut);


                    map.put("file_name", sFileName);
                    map.put("count", iReqConut);
                    map.put("date", sFileName.substring(11, 19));
                    map.put("file_type", "1");
                    map.put("recv_yn", "N");
                    mList.add(map);

                    rafile.close();
                }
            }

            String[] arrRecvFileList = fileRecvIniCard.list();

            for(int sendIdx = 0; sendIdx < arrRecvFileList.length; sendIdx++)
            {
                String sFileName = arrRecvFileList[sendIdx];

                if(sFileName.indexOf(stt_dt) > 0 &&  sFileName.indexOf(".") != 0)
                {
                    Map<String,Object> map = new HashMap<String,Object>();

                    rafile = new RandomAccessFile(sRecvIniCardFilePath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                    long pos = rafile.length() - 2;

                    while(true)
                    {
                        rafile.seek(pos);

                        if (rafile.readByte() == '\n')
                        {
                            break;
                        }

                        pos--;
                    }

                    rafile.seek(pos + 1);

                    String sLastLine  = rafile.readLine();
                    String sReqConut  = sLastLine.substring(0, 8);
                    int    iReqConut  = Integer.parseInt(sReqConut);


                    map.put("file_name", sFileName);
                    map.put("count", iReqConut);
                    map.put("date", sFileName.substring(11, 19));
                    map.put("file_type", "2");
                    map.put("recv_yn", "N");
                    mList.add(map);

                    rafile.close();
                }
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if(rafile != null)
            {
                rafile.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 카드파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/carddownloadIniCardFile")
    public void carddownloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);

            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                //successPath = EgovProperties.getProperty("card.down.file.windows.successPath");
                successPath = "C:/sftp_home/inicis/send/";
            } else {
                //successPath = EgovProperties.getProperty("card.down.file.unix.successPath");
                successPath = "/sftp_home/inicis/send/";
            }

            /* List<Map<String, Object>> mList = DlwPayService.getCardSendMakeFileDt(hmParam);
            String sExtWriteCardDt = (String)mList.get(0).get("ext_write_card"); */

            String srcFilePath = successPath +"DaemyuBIGI_"+ yymmdd +".REQ";

            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }


            System.out.println(srcFilePath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();


            System.out.println("contentType : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();
        } catch (Exception e) {
            e.printStackTrace();
            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 출금이체의뢰내역(청구) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqList")
    public ModelAndView getReqCustWdrwReqList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMapCard = new DataSetMap();
        DataSetMap listMapCardNauth = new DataSetMap();
        DataSetMap listMapCMS = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = reqCustService.getWdrwReqListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = reqCustService.getPayWdrwReqList(hmParam);
                List<Map<String, Object>> mListCard = reqCustService.getCalcAndChargeCardSummary(hmParam);
                List<Map<String, Object>> mListCardNauth = reqCustService.getCalcAndChargeCardNauthSummary(hmParam);
                List<Map<String, Object>> mListCMS = reqCustService.getCalcAndChargeCMSSummary(hmParam);

                listMap.setRowMaps(mList);
                listMapCard.setRowMaps(mListCard);
                listMapCardNauth.setRowMaps(mListCardNauth);
                listMapCMS.setRowMaps(mListCMS);

                mapOutDs.put("ds_output", listMap);
                mapOutDs.put("ds_outputCard", listMapCard);
                mapOutDs.put("ds_outputCardNauth", listMapCardNauth);
                mapOutDs.put("ds_outputCMS", listMapCMS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 특수회원 조회
     * 정승철
     * 20181018
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchSpecialAcntList")
    public ModelAndView getReqCustSpecialAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            int nTotal = reqCustService.getCntSpecialAcntList(hmParam);

            List<Map<String, Object>> mList = reqCustService.getSpecialAcntList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            mapOutVar.put("tc_list", nTotal);

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
     * 산출특수회원 입력/수정/삭제
     * 정승철
     * 20181019
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/extSpecial/{crudType}")
    public ModelAndView reqCustCrudExtSpecial(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
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

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insert".equals(crudTyp)){
                    // 기존 특수회원인지 체크
                    int chkCnt = reqCustService.getChkSpecialAcntList(hmParam);

                    if(chkCnt == 0) {
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        reqCustService.insertExtSpecial(hmParam);
                    }
                    else {
                        mapOutVar.put("g_reason_msg", "해당 회원은 이미 특수회원으로 등록되어 있습니다.");  // 실패 메시지 return
                    }
                }
                else if("update".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    reqCustService.updateExtSpecial(hmParam);
                }
                else if("delete".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    reqCustService.deleteExtSpecial(hmParam);
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
     * 카드결과로그 조회
     * 정승철
     * 20181029
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-rltm-card-log/list")
    public ModelAndView getReqCustDlwRltmCardLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            if (!"Y".equals(excel_fg)) {
                int nTotal = 0;
                List<Map<String, Object>> mList = null;

                if("1".equals(hmParam.get("search_yy")))  //2022년
                {
                    nTotal = reqCustService.getReqRltmCardLog2022Count(hmParam);
                    mList = reqCustService.getReqRltmCardLog2022List(hmParam);
                }
                else if("2".equals(hmParam.get("search_yy")))  //2021년
                {
                    nTotal = reqCustService.getReqRltmCardLogOldCount(hmParam);
                    mList = reqCustService.getReqRltmCardLogOldList(hmParam);
                }

                else if("2023".equals(hmParam.get("search_yy")))  //2023년
                {
                    nTotal = reqCustService.getReqRltmCardLogCount(hmParam);
                    mList = reqCustService.getReqRltmCardLogList(hmParam);
                }


                else if("3".equals(hmParam.get("search_yy"))) //회원번호
                {
                    nTotal = reqCustService.getReqRltmCardLogAccntNoCount(hmParam);
                    mList = reqCustService.getReqRltmCardLogAccntNoList(hmParam);
                }


//                int nTotal = reqCustService.getReqRltmCardLogCount(hmParam);

                mapOutVar.put("tc_log", nTotal);
//                List<Map<String, Object>> mList = reqCustService.getReqRltmCardLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.11 접속 로그////////////////////////////////////////////////////////////////////////////////
                /*DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }*/
                //////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            listMap2.setRowMaps(hmParam);
            mapOutDs.put("ds_output_excel", listMap2);

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
    @RequestMapping(value = "/cust-acnt/list")
    public ModelAndView getReqCustDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                /* 임의등록에서 회원정보검색시 해피콜 조인구분 변수 추가 - 2018.02.13 */
                hmParam.put("hp_join_gb", mapInVar.get("hp_join_gb"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                Map<String, Object> mEmpl = dlwEmplService.getDlwEmplDtpt((String)hmParam.get("rgsr_id"));
                if (mEmpl != null) {
                    String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                    if ("1602".equals(sDeptCd)) {
                        hmParam.put("ns_yn", "Y");
                    } else {
                        hmParam.put("ns_yn", "N");
                    }
                }

                UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
                hmParam.put("paramAs", "EMP");

                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = reqCustService.getDlwCustAcntCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = reqCustService.getDlwCustAcntList(hmParam);
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
     * 입금 완료 된 실시간카드결제 내역을 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-realtime")
    public ModelAndView reqCustUpdateRealtimeCardCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");
            hmParam.put("pay_kind", "02");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타)

            System.out.println(hmParam);

            Map <String, Object> result = new HashMap();

            //이니시스 결제 취소 프로세스
            if (hmParam.get("ini_yn").equals("Y")){
                result = reqCustService.updateRealtimeInicisCardCancel(hmParam);
            } else {
                result = reqCustService.updateRealtimeCardCancel(hmParam);
            }

            mapOutVar.put("rslt_cd", result.get("result_code"));
            mapOutVar.put("rslt_nm", result.get("result_msg"));

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
     * 입금 완료 된 실시간카드결제 내역을 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancelTransInicisCardCancel")
    public ModelAndView saveTransInicisCardCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);

            hmParam.put("tid", hmParam.get("result_key"));
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");
            hmParam.put("pay_kind", "05");  // 자유 (01:파일, 02:실시간, 03:자유, 04:기타, 05:전환)

            System.out.println(hmParam);

            Map <String, Object> result = new HashMap();

            result = reqCustService.saveTransInicisCardCancel(hmParam);

            mapOutVar.put("rslt_cd", result.get("result_code"));
            mapOutVar.put("rslt_nm", result.get("result_msg"));

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
     * 대명라이프웨이 나이스페이 실시간카드결제 내역을 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pymn-canc-nice")
    public ModelAndView reqCustUpdateDlwPymnCancNicepay(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            /*36회 가맹점 변경건으로 인한 이슈 처리 중 취소 시 BKKYdmlife001m기준으로 가져왔던 bid를 맞추기 위해 아래 string ADD*/
            hmParam.put("bid", "CNCL" + hmParam.get("tid"));

            Map <String, Object> result = new HashMap();
            result = reqCustService.updateDlwPymnCancNicepay(hmParam);


            saveConsDlwRtCard(result);


            mapOutVar.put("rslt_cd", result.get("result_code"));
            mapOutVar.put("rslt_nm", result.get("result_msg"));

            if (result.get("pay_result_msg") == null) {
                mapOutVar.put("rslt_msg", result.get("result_msg"));
            } else {
                mapOutVar.put("rslt_msg", result.get("pay_result_msg"));
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
     * 상담 정보를 등록/수정한다. (대명 - 카드결제 시)
     *
     * @param pmParam Map<String, Object>
     * @return pmParam Map<String, Object>
     * @throws Exception
     */
    public Map <String, Object> saveConsDlwRtCard( Map <String, Object> pmParam) throws Exception {
        String cnslMngCon = "[실시간] 카드 결제";
        String cnslDtlCon = "";

        String type = (String)pmParam.get("type");

        if ("card".equals((String)pmParam.get("type"))) {
            cnslDtlCon = (new StringBuilder("[실시간]카드결제일 : ")).append((String)pmParam.get("pay_day")).append("  카드번호 : ").append((String)pmParam.get("card_no")).append("  결제금액 : ").append((String)pmParam.get("pay_amt")).toString();
        } else if ("card_fail".equals(type)){
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 실패").toString();
        } else if ("card_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 오류").toString();
        } else if ("cancel".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소").toString();
        } else if("cancel_fail".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 실패").toString();
        } else if("cancel_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 오류").toString();
        } else {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 기타 오류").toString();
        }

        pmParam.put("cons_memo", cnslMngCon);
        pmParam.put("cnsl_con", cnslDtlCon);

        Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(pmParam);

        //회원정보 없을경우 상담저장 안함
        if (mCust != null && mCust.size() > 0) {
            consController.saveConsdlw(pmParam);
        }

        return pmParam;
    }

    /**
     * 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqAccntEventConfirm")
    public ModelAndView getReqCustWdrwReqAccntEventConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String accnt_no = (String)mapInVar.get("accnt_no");

            if (accnt_no != null || accnt_no != "" || !accnt_no.equalsIgnoreCase(""))
            {
                mList = reqCustService.getWdrwReqAccntEventConfirm(accnt_no);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 고객 자유결제 NICE 전문 발송
     * 대상 테이블 :
     * ================================================================
     * */
    @RequestMapping(value = "/sendCancelNicePayment")
    public ModelAndView reqCustSendCancelNicePayment(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, Object> hmParam3 = new HashMap<String, Object>();
        Map<String, Object> hmParam4 = new HashMap<String, Object>();

        InetAddress address = InetAddress.getLocalHost();
        HttpServletRequestMock request = new HttpServletRequestMock();
        HttpServletResponseMock response = new HttpServletResponseMock();
        NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
        NicePayWEB nicePayWEB = new NicePayWEB();
        WebMessageDTO result = new WebMessageDTO();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        String sReturnVal = "0";
        String sReturnMassage = "";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap sendInDs1 = (DataSetMap)mapInDs.get("ds_input");        // 회원기본취소정보

            System.out.println(sendInDs1);

            if (sendInDs1 != null && sendInDs1.size() > 0)
            {
                hmParam1 = sendInDs1.get(0);
                ParamUtils.addSaveParam(hmParam1);

                httpRequestWrapper.addParameter("CancelAmt", (String)hmParam1.get("pay_amt"));	// 취소 금액 설정
                httpRequestWrapper.addParameter("MID", "dmlifeon1m");						// 상점 아이디 설정
                httpRequestWrapper.addParameter("CancelIP", address.getHostAddress());		// 취소자 IP 설정
                httpRequestWrapper.addParameter("CancelMsg","전환결제 취소");				// 취소 사유 설정
                httpRequestWrapper.addParameter("CancelPwd", "1111");						// 취소 패스워드 설정
                httpRequestWrapper.addParameter("TID", (String)hmParam1.get("result_key"));	// 취소 TID
                httpRequestWrapper.addParameter("PartialCancelCode", "0");    				// 부분 취소 유무 (1: 부분 취소 사용, 0 : 미사용)

                String sPayLogFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam1.get("cntr_cd"));

                nicePayWEB.setParam("NICEPAY_LOG_HOME", sPayLogFilePath + "/web_site/nicelog");	//로그 디렉토리
                nicePayWEB.setParam("APP_LOG", "0");										//이벤트로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicePayWEB.setParam("EVENT_LOG", "1");										//어플리케이션로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicePayWEB.setParam("EncFlag", "S");										//암호화플래그 설정(N: 평문, S:암호화)
                nicePayWEB.setParam("SERVICE_MODE", "CL0");									//서비스모드 설정(결제 서비스 : PY0 , 취소 서비스 : CL0)

                result = nicePayWEB.doService(httpRequestWrapper, response);	//결제취소 요청

                String sResultCode = result.getParameter("ResultCode");						// 결과코드 (정상 :2001 , 그 외 에러)
                String sResultMsg  = result.getParameter("ResultMsg");						// 결과메시지
                String sCancelDate = result.getParameter("CancelDate");						// 취소 날짜
                String sCancelTime = result.getParameter("CancelTime");						// 취소 시간
                String sCancelAmt  = result.getParameter("CancelAmt");						// 취소 가격

                System.out.println("--------------------------------------------");
                System.out.println("결과			: " + sResultCode);
                System.out.println("결과 메시지		: " + sResultMsg);
                System.out.println("취소 날짜		: " + sCancelDate);
                System.out.println("취소 가격		: " + sCancelAmt);
                System.out.println("--------------------------------------------");

                hmParam2.put("pay_cancel_day", sCancelDate);
                hmParam2.put("cncl_tm", sCancelTime);
                hmParam2.put("pay_cancel_note", "전환결재 취소");
                hmParam2.put("emple_no", (String)hmParam1.get("rgsr_id"));
                hmParam2.put("pay_cancel_cd", "01");

                hmParam2.putAll(hmParam1);

                if("2001".equals(sResultCode))
                {
                    sReturnVal = "1";
                    sReturnMassage = sResultMsg;

                    reqCustService.sendCancelNicePayment(hmParam2);
                }
                else
                {
                    sReturnVal = "-1";
                    sReturnMassage = sResultMsg;
                }
            }

            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", sReturnVal);
            mapOutVar.put("returnMassage", sReturnMassage);

            /*
            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCdList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", 1);
            */

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
     * 카드 무승인 취소시 취소대상 등록
     * 20191209
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-nauthpay")
    public ModelAndView insertNauthPayCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));

            reqCustService.insertNauthPayCancel(hmParam);

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
     * 카드 무승인 취소 리스트 조회
     * 20191209
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-nauthpay-list")
    public ModelAndView getReqCustNauthPayCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);

            System.out.println("xxxxxxx " + hmParam);
            List<Map<String, Object>> mList = reqCustService.getNauthPayCancel(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 무승인 취소를 취소한다.
     * 김주용
     * 20191216
     */
    @RequestMapping(value = "/cancel-nauthpay-delete")
    public ModelAndView cancelNauthpayDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            System.out.println("xxxxxxx " + hmParam);
            reqCustService.cancelNauthpayDelete(hmParam);

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
     * 무승인 환불처리를 한다
     * 김주용
     * 20191216
     */
    @RequestMapping(value = "/cancel-nauthpay-refund")
    public ModelAndView reqCustCancelNauthpayRefund(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            System.out.println("xxxxxxx " + hmParam);
            reqCustService.cancelNauthpayRefund(hmParam);

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
     * 회원별 카드,CMS 결과데이터 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-rltm-card-log/getWdrwAccntLogList")
    public ModelAndView getReqCustWdrwAccntLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                List<Map<String, Object>> mList = reqCustService.getWdrwAccntLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 고객청구환불관리 리스트검색(메인화면 로직)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberRefundList")
    public ModelAndView getReqCustGasuMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = 0;
            List<Map<String, Object>> mList = null;

            if("1".equals(hmParam.get("search_yy")))  //2022년
            {
                nTotal = reqCustService.getMemberRefundList2022Count(hmParam);
                mList = reqCustService.getMemberRefund2022List(hmParam);
            }
            else if("2".equals(hmParam.get("search_yy")))  //2021년
            {
                nTotal = reqCustService.getMemberRefundListOldCount(hmParam);
                mList = reqCustService.getMemberRefundOldList(hmParam);
            }
            else if("2023".equals(hmParam.get("search_yy")))  //2023년
            {
                nTotal = reqCustService.getMemberRefundListCount(hmParam);
                mList = reqCustService.getMemberRefundList(hmParam);
            }
            else if("3".equals(hmParam.get("search_yy"))) //회원번호
            {
                nTotal = reqCustService.getMemberRefundListAccntNoCount(hmParam);
                mList = reqCustService.getMemberRefundAccntNoList(hmParam);
            }



            mapOutVar.put("nTotalListCnt", nTotal);


            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 고객-구좌 정보를 검색한다.(메인화면 로직)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getDlwCustAccntList")
    public ModelAndView getReqCustDlwPayCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                /* 임의등록에서 회원정보검색시 해피콜 조인구분 변수 추가 - 2018.02.13 */
                hmParam.put("hp_join_gb", mapInVar.get("hp_join_gb"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                Map<String, Object> mEmpl = reqCustService.getEmplDtptList((String)hmParam.get("rgsr_id"));
                if (mEmpl != null) {
                    String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                    if ("1602".equals(sDeptCd)) {
                        hmParam.put("ns_yn", "Y");
                    } else {
                        hmParam.put("ns_yn", "N");
                    }
                }

                UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();

                hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
                hmParam.put("paramAs", "EMP");

                String dataAthrQury = reqCustService.getDataAthrFunc(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = reqCustService.getDlwCustAccntListCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = reqCustService.getDlwCustAccntList(hmParam);
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
     * 회원의 환불회차정보를 검색한다.
     * 정승철
     * 20181127
     */
    @RequestMapping(value = "/getRefundReqNoOfAccnt")
    public ModelAndView getReqCustRefundReqNoOfAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = reqCustService.getRefundReqNoOfAccnt(hmParam);
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
     * 대명라이프웨이 청구환불 환불정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMemberRefundMng")
    public ModelAndView reqCustDeleteGasuMng(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                reqCustService.deleteMemberRefundMng(hmParam);

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
     * 대명라이프웨이 청구환불 환불정보를 저장한다.(팝업화면)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mergeMemberRefundDtl")
    public ModelAndView reqCustMergeGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String mode = (String)hmParam.get("mode");

                System.out.println("mode :: " + mode);
                if ("update".equals(mode)) {
                    reqCustService.updateMemberRefundDtl(hmParam);
                } else {
                    reqCustService.insertMemberRefundDtl(hmParam);
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
     * 대명라이프웨이 청구환불 환불정보를 삭제한다.(팝업화면)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMemberRefundDtl")
    public ModelAndView reqCustDeleteGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("gasu_seq", mapInVar.get("gasu_seq"));
            hmParam.put("seq", mapInVar.get("seq"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            reqCustService.deleteMemberRefundDtl(hmParam);

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
     * (CMS) 환불반영
     * 정승철
     * 20181127
     */
    @RequestMapping(value = "/updateByCmsRefundProcess")
    public ModelAndView reqCustUpdatePayByCmsRefundProcess(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));

                reqCustService.updateByCmsRefundProcess(hmParam);
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
    public ModelAndView getReqAccntBaseInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                int nTotal = reqCustService.getCntDlwAccntBaseInfo(hmParam);
                mapOutVar.put("tc_Acnt", nTotal);

                // (임의등록) 회원정보 카운트 및 조회팝업 여부에 따라 분기 조회

                // 1. 임의등록 회원조회시 1건인 경우
                if ("N".equals(popup_yn) && nTotal == 1) {
                    List<Map<String, Object>> mList = reqCustService.getDlwAccntBaseInfo(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                // 2. 임의등록 회원조회시 2건 이상인 경우 팝업조회 (** 이름 등으로 검색시)
                else if(!"N".equals(popup_yn)) { // (임의등록) 회원정보 조회팝업
                    List<Map<String, Object>> mList = reqCustService.getDlwAccntBaseInfo_popup(hmParam);
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
     * 산출 회원정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-acnt-info")
    public ModelAndView getReqWdrwAcntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = reqCustService.getDlwWdrwAcntInfo(hmParam);
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
    public ModelAndView reqCustAddWdrwTemp(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            strHolyday = reqCustService.getHolidayChk(hmParam);

            if (strHolyday.equals("N") ) {
                reqCustService.addWdrwTemp(hmParam); // 휴일이 아닌 경우 임의등록 처리
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
     * 청구관리 가상계좌 청구
     * 임동진
     * 20181029
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/virtual/ext")
    public ModelAndView reqCustInsertVrtlAccntWdrwExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        //가상계좌 대상자 입력 리스트
        Map<String, Object> resultParam = new HashMap<String, Object>();

        // 가상계좌 전송 결과 맵
        Map<String, Object> resultMap = new HashMap<String, Object>();

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
            List<Map<String, Object>> resultList = reqCustService.getVirtualExtList(hmParam);
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
                intResult = reqCustService.insertVirtualMemData(hmParam);
            }
            // 가상계좌 정보 전송 class
            //resultMap = reqCustService.InicisVirtualProc(hmParam);

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
    public ModelAndView reqCustDelWdrwTemp(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                reqCustService.delWdrwTemp(hmParam);
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
     * 임의등록 실시간 카드 결제 (NEW)
     * 임동진
     * 20181012
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/realtime-batch")
    public ModelAndView ReqCustRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                List<Map<String, Object>> MemList = reqCustService.getRealTimeReqList(hmParam);
                MemParam = MemList.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                hmParam.put("menu_gbn", "0003");
                hmParam.put("dc_no", String.valueOf(MemParam.get("card_quota")));
                hmParam.put("card_quota", String.valueOf(MemParam.get("card_quota")));
                hmParam.put("pay_amt", String.valueOf(MemParam.get("pay_amt")));
                hmParam.put("gbn_batch", "batch");

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

                // 이니시스 결제 프로세스
                if (MemParam.get("ini_yn").equals("Y")){
                    resultParam = reqCustService.RealTimeInicisReqProc(hmParam);
                } else {
                    resultParam = reqCustService.RealTimeReqProc(hmParam);
                }

                String result_Cd = (String)resultParam.get("result_cd");
                String result_Msg = (new StringBuilder(String.valueOf(result_Cd))).append(" : ").append((String)resultParam.get("result_msg")).toString();
                String auth_Cd = (String)resultParam.get("auth_cd"); // 승인번호
                String auth_Dt = (String)resultParam.get("auth_dt"); // 승인일자
                String strTid = (String)resultParam.get("tid");		 // 카드 결과 tid값
                String ichaeCd = (String)resultParam.get("card_cd"); // 이체카드

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
                        reqCustService.insertReqWdrwResult(hmParam);

                        //실시간 결제 성공 시 입금 적용 (결과가 오류 시 승인번호 있으면 승인된걸로 인정)
                        if ("3001".equals(result_Cd) || "00".equals(result_Cd)) {
                            reqCustService.insertMemPayData(hmParam);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        szErrorCode = "-1";
                        szErrorMsg  = "결과 처리 시 오류가 발생 하였습니다.";
                    }

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

    /**
     * 자유 결제 (실시간 카드) (NEW)
     * 정승철
     * 20181031
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/free-realtime")
    public ModelAndView ReqCustFreeRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            //String menu_gbn = "0005"; // 자유
            String pmResultCd = "";
            String pmResultMsg = "";
            //hmParam.put("menu_gbn", menu_gbn);

            hmParam.put("pay_mthd", "06");  // 카드
            hmParam.put("pay_kind", "03");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타)

            try
            {
                // 자유금액 결제시도
                resultMap = reqCustService.billPayFreeProc(hmParam);
                
                synchronized (this){
	                String result_cd = (String)resultMap.get("result_cd");
	                String result_msg = (String)resultMap.get("result_msg");
	                String tid = (String)resultMap.get("tid");
	                String auth_dt = (String)resultMap.get("auth_dt");
	                String auth_cd = (String)resultMap.get("auth_cd");
	                String card_cd = (String)resultMap.get("card_cd");
	
	                hmParam.put("result_key", tid);
	                hmParam.put("result_cd", result_cd);
	                hmParam.put("result_msg", result_msg);
	                hmParam.put("auth_cd", auth_cd);
	                hmParam.put("auth_dt", auth_dt);
	                hmParam.put("card_cd", card_cd);
	
	                if("3001".equals(result_cd)){
	                    hmParam.put("del_fg", "C");
	                    pmResultCd = "00";
	                    reqCustService.insertFreeCardResult(hmParam);
	                }
	                else {
	                    hmParam.put("del_fg", "F");
	                    pmResultCd = "01";
	                }
	
	                resultMap.put("result_msg", (new StringBuilder(String.valueOf(result_cd))).append(" : ").append(result_msg).toString());
	                resultMap.put("result_cd", result_cd);
	                pmResultMsg = result_msg;
                }
            }
            catch(Exception e)
            {
                resultMap.put("result_cd", "01");
                resultMap.put("result_msg", "[Exception]개발자문의");
                pmResultCd = "01";
                pmResultMsg = (String)resultMap.get("result_msg");
            }

            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

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
     * 자유 결제 (이니시스)
     * 임동진
     * 20200507
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/free-inicis-realtime")
    public ModelAndView ReqCustFreeInicisRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String sInputType = (String)mapInVar.get("input_type");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            String pmResultCd = "";
            String pmResultMsg = "";

            hmParam.put("pay_mthd", "06");  // 카드
            if(sInputType != null && sInputType.equals("transInicis"))
            {
                hmParam.put("pay_kind", "05");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타, 05:전환)
            }
            else
            {
                hmParam.put("pay_kind", "03");
            }

            try
            {
                // 자유금액 결제시도
                resultMap = reqCustService.billPayInicisFreeProc(hmParam);

                pmResultCd = (String)resultMap.get("result_cd");
                pmResultMsg = (String)resultMap.get("result_msg");

                if("00".equals(pmResultCd)){
                    hmParam.put("del_fg", "C");
                    hmParam.put("result_key", resultMap.get("result_key"));
                    hmParam.put("auth_dt", resultMap.get("auth_dt"));
                    hmParam.put("auth_cd", resultMap.get("auth_cd"));
                         hmParam.put("card_cd", resultMap.get("card_cd"));

                    reqCustService.insertFreeCardResult(hmParam);

                    pmResultCd = "00";
                }
                else{
                    hmParam.put("del_fg", "F");
                    pmResultCd = "01";
                }
            }
            catch(Exception e) {
                resultMap.put("result_cd", "01");
                resultMap.put("result_msg", "[Exception]개발자문의");
                pmResultCd = "01";
                pmResultMsg = (String)resultMap.get("result_msg");
            }

            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

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
     * 회차별 자유 결제 (이니시스)
     * 임동진
     * 20200923
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/free-req-realtime")
    public ModelAndView ReqCustFreeReqRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            String pmResultCd = "";
            String pmResultMsg = "";

            hmParam.put("pay_mthd", "06");  // 카드
            hmParam.put("pay_kind", "03");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타)
            hmParam.put("email", "abc@daemyung.com");  // 이메일

            try
            {
                /***********************************************************************************
                 * 자유결제에서 넘어온 카드정보로 빌키를 생성 한다.
                 ***********************************************************************************/
                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                inicisPay.setData("mid", "daemyungT1");
                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("moid", (String)hmParam.get("accnt_no"));
                inicisPay.setData("goodName", "상품명");
                inicisPay.setData("buyerName", "구매자명");
                inicisPay.setData("buyerEmail", "abc@daemyung.com");
                inicisPay.setData("buyerTel", "010-1234-1234");
                inicisPay.setData("cardNumber", (String)hmParam.get("card_no"));
                inicisPay.setData("cardExpire", (String)hmParam.get("exp_year")+(String)hmParam.get("exp_mon") );
                inicisPay.setData("regNo", (String)hmParam.get("card_idn_no"));

                inicisPay.authBillkey();

                String resultCode = inicisPay.getData("resultCode");
                String resultMsg = inicisPay.getData("resultMsg");
                String tid = inicisPay.getData("tid");
                String billKey = inicisPay.getData("billKey");

                /***********************************************************************************
                /***********************************************************************************/

                // 빌키 정상 생성 여부
                if(!"00".equals(resultCode) && (!"5012".equals(resultCode))) {
                    //정상결과 아닐경우 결과코드 출력
                    pmResultCd = "01";
                    pmResultMsg = resultMsg;
                } else {

                    /***********************************************************************************
                     * 정상적으로 빌키 생성된 데이터 임의등록
                     ***********************************************************************************/
                    // 빌키 등록
                    hmParam.put("ichae_no", billKey);

                    // 정상일 경우 청구별 자유결제 등록
                    reqCustService.addWdrwFreeTemp(hmParam); // 자유결제 시 당일 임의등록 처리

                    /***********************************************************************************
                     * 임의 등록된 데이터 실시간 승인 처리
                     ***********************************************************************************/

                    //산출 된 회원 정보 가져오기
                    List<Map<String, Object>> MemList = reqCustService.getRealTimeReqFreeList(hmParam);

                    //실시간 결제 결과 정보 MAP
                    Map<String, Object> resultParam = new HashMap<String, Object>();

                    String strPayamt = "";
                    int iSuccess = 0;
                    int iFail =0;

                    for (int i = 0; i < MemList.size(); i++) {
                        hmParam.putAll(MemList.get(i));

                        String strpayamt = String.valueOf(hmParam.get("pay_amt"));
                        hmParam.put("pay_amt", strpayamt);

                        resultParam = reqCustService.RealTimeInicisReqProc(hmParam);

                        pmResultCd = (String)resultParam.get("result_cd");
                        pmResultMsg = (String)resultParam.get("result_msg");

                        if (pmResultCd != null && pmResultCd.equals("00")){

                            //강제적으로 자유 -> 실시간임의등록으로 변경 함(동일 함수를 태우기 위함)
                            hmParam.put("pay_kind", "02");
                            hmParam.put("pay_no", hmParam.get("req_pay_no"));
                            hmParam.put("result_key", (String)resultParam.get("tid"));
                            hmParam.put("auth_cd", (String)resultParam.get("auth_cd"));
                            hmParam.put("auth_dt", (String)resultParam.get("auth_dt"));
                            hmParam.put("etc", "회차별 자유결제");

                            //실시간 카드 결과데이터 인서트 및 성공 시 청구 데이터 업데이트 (DEL_FG = 'C')
                            reqCustService.insertReqWdrwResult(hmParam);

                            //실시간 결제 성공 시 입금 적용 (결과가 오류 시 승인번호 있으면 승인된걸로 인정)
                            reqCustService.insertMemPayData(hmParam);

                            iSuccess = iSuccess + 1;
                        } else {
                            iFail = 1;
                             // 청구 자유결제 시 한건이라도 승인 이슈 시 잔여 청구회차 삭제
                            reqCustService.delWdrwFreeTemp(hmParam);
                            break;
                        }
                    }

                    pmResultCd = "완료";
                    if (iFail ==1){
                        pmResultMsg = String.valueOf(iSuccess) + "건 성공 / 마지막 실패 사유 [" +pmResultMsg+"]";

                        //성공건 외에 전부 삭제 프로세스

                    } else {
                        pmResultMsg = String.valueOf(iSuccess) + "건 성공";
                    }
                }
            }
            catch(Exception e) {
                resultMap.put("result_cd", "01");
                resultMap.put("result_msg", "[Exception]개발자문의");
                pmResultCd = "01";
                pmResultMsg = (String)resultMap.get("result_msg");
            }


            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

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
     * 빌키 자동 생성 (대량건)
     * 임동진
     * 20200923
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/BillkeyCreate")
    public ModelAndView ReqCustBillkeyAutoCreate(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            String Expire_Date= "";
            String Cardbirth = "";

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam.putAll(listInDs.get(i));
                hmParam.put("email", "abc@daemyung.com");  // 이메일

                Expire_Date = (String)hmParam.get("expire_date");
                Cardbirth = (String)hmParam.get("card_birth");

                /***********************************************************************************
                 * 자유결제에서 넘어온 카드정보로 빌키를 생성 한다.
                 ***********************************************************************************/
                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                inicisPay.setData("mid", "daemyungT1");
                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("moid", (String)hmParam.get("accnt_no"));
                inicisPay.setData("goodName", "상품명");
                inicisPay.setData("buyerName", "구매자명");
                inicisPay.setData("buyerEmail", "abc@daemyung.com");
                inicisPay.setData("buyerTel", "010-1234-1234");
                inicisPay.setData("cardNumber", (String)hmParam.get("card_no"));
                inicisPay.setData("cardExpire", Expire_Date );
                inicisPay.setData("regNo", Cardbirth);

                inicisPay.authBillkey();

                String resultCode = inicisPay.getData("resultCode");
                String resultMsg = inicisPay.getData("resultMsg");
                String tid = inicisPay.getData("tid");
                String billKey = inicisPay.getData("billKey");

                //카드사 코드
                String strCardCd= inicisPay.getData("cardCode");

                //체크카드 여부 (0:신용, 1:체크, 2:기프트카드)
                String strCheckFlg = inicisPay.getData("checkFlg");

                String strResultMsg = strCardCd + "_" + strCheckFlg;

                /***********************************************************************************
                /***********************************************************************************/

                // 빌키 정상 생성 여부

                if("00".equals(resultCode)) {
                    hmParam.put("ini_bid", billKey);  // 빌키
                    hmParam.put("memo", "성공_" + strResultMsg);  // 메시지
                    reqCustService.insertBillkeyMakeData(hmParam);
                } else {
                    hmParam.put("ini_bid", "없음");  // 빌키
                    hmParam.put("memo", resultMsg);  // 메시지
                }
                System.out.println("xxxxxxxxx > : " + hmParam);
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
     * 이니시스 일자별 결과 받은 데이터건수 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/InicisResultCnt")
    public ModelAndView InicisResultCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 매입일 param
            hmParam.put("acqu_dt", mapInVar.get("stt_dt"));

            int intResultCnt = reqCustService.getInicisResultCount(hmParam);

            mapOutVar.put("resultCnt", intResultCnt);

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
     * 이니시스 매입결과 업로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/InicisResultUpload")
    public ModelAndView InicisResultUpload(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 매입일 param
            String strSelDt = (String)mapInVar.get("stt_dt");

            //String postURL = "https://devniniweb.inicis.com/mall/cr/urlsvc/UrlSendCalCulDaemyung.jsp?urlid=ezwel00001&passwd=3435&date="+ strSelDt +"&flgurl=31";
            String postURL = "https://iniweb.inicis.com/mall/cr/urlsvc/UrlSendCalCulDaemyung.jsp?urlid=DaemyuBIGI&passwd=1111&date="+ strSelDt +"&flgurl=31";

            HttpPost post = new HttpPost(postURL);
            HttpClient client = new DefaultHttpClient();
            HttpResponse responsePOST = client.execute(post);

            BufferedReader reader = new BufferedReader(new  InputStreamReader(responsePOST.getEntity().getContent()), 2048);

            int intReadData = 0;

            if (responsePOST != null) {
                //StringBuilder sb = new StringBuilder();
                String sline;

                String dataGbn = "";   // 데이터 구분 (HEADER, DATA, TAIL등)
                String sResultCd = "";   // 문제없는 전문인지 확인
                String sResultMsg = "";   // 결과 메세지

                String sAuthDt = "";
                String sAcquDt = "";
                String sCancelDt = "";
                String sAccntNo= "";
                String sTid = "";
                String sCardCd = "";
                String sRawData = "";
                String iPurAmt = "";
                String iFeeAmt = "";
                String iPaidAmt = "";
                String sAuthCd = "";

                String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
                String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
                String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
                String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

                Connection connection = null;
                PreparedStatement preparedStatement = null ;

                String sqlStatement = "INSERT INTO LF_DMUSER.TB_ACQU_INICIS_LIST (AUTH_DT,ACQU_DT,ACCNT_NO,TID,CARD_CD,"
                        + "DATA_PUR_PAY,DATA_ETC_FEE,DATA_AMOT_PAID,CANCEL_DT,RAW_DATA,REG_MAN,REG_DM,AUTH_CD)  "
                        + "VALUES(?,?,SUBSTR(?,0,12),?,?,?,?,?,SUBSTR(?,0,8),?,'AUTO_INICI',SYSDATE,?)";

                Class.forName(sAccessClassName);
                connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                connection.setAutoCommit(false);

                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setQueryTimeout(1000);

                while ((sline = reader.readLine()) != null) {

                    String[] parseLine = sline.split("\\|",-1);	//마지막 배열 데이터가 비어있을때 -1

                    //읽어온 데이터가 HEADER인지 TAIL인지 판단 하여 DATA만 INSERT 가능하게
                    dataGbn = parseLine[0].toString();

                    //header
                    if (dataGbn.equals("H")){
                        //읽어온 데이터가 정상인지 확인
                        sResultCd = parseLine[21].toString();
                        sResultMsg = parseLine[22].toString();
                    }

                    //데이터가 정상인 대상자만 인서트 처리 프로세스를 태운다
                    if(sResultCd.equals("00")){
                        //dataline
                        if (dataGbn.equals("B")){
                            sAuthDt = parseLine[3].toString().substring(0,8);
                            sTid = parseLine[5].toString();
                            sAccntNo = parseLine[6].toString();
                            sAcquDt = parseLine[12].toString();
                            sCancelDt = parseLine[8].toString();
                            sCardCd = parseLine[4].toString();
                            sRawData = sline.toString();
                            iPurAmt = parseLine[9].toString();
                            iFeeAmt = parseLine[10].toString();
                            iPaidAmt = parseLine[11].toString();
                            sAuthCd = parseLine[13].toString();

                            preparedStatement.setString(1, sAuthDt.trim());
                            preparedStatement.setString(2, sAcquDt.trim());
                            preparedStatement.setString(3, sAccntNo.trim());
                            preparedStatement.setString(4, sTid.trim());
                            preparedStatement.setString(5, sCardCd.trim());
                            preparedStatement.setString(6, iPurAmt.trim());
                            preparedStatement.setString(7, iFeeAmt.trim());
                            preparedStatement.setString(8, iPaidAmt.trim());
                            preparedStatement.setString(9, sCancelDt.trim());
                            preparedStatement.setString(10, sRawData.trim());
                            preparedStatement.setString(11, sAuthCd.trim());

                            // addBatch에 담기
                            preparedStatement.addBatch();

                            // 파라미터 Clear
                            preparedStatement.clearParameters() ;

                            // OutOfMemory를 고려하여 만건 단위로 커밋
                            if( (intReadData % 500) == 0) {
                                // Batch 실행
                                preparedStatement.executeBatch();

                                // Batch 초기화
                                preparedStatement.clearBatch();

                                // 커밋
                                connection.commit();
                            }
                            intReadData++;

                        }
                    }else{
                        break;
                    }
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                reader.close();
                preparedStatement.close();
                connection.close();

                if(sResultCd.equals("00")){
                    mapOutVar.put("result_Cd", "00");
                    mapOutVar.put("result_Msg", sResultMsg);
                } else {
                    mapOutVar.put("result_Cd", "FF");
                    mapOutVar.put("result_Msg", sResultMsg);
                }

                reader.close();

            } else {

                mapOutVar.put("result_Cd", "FF");
                mapOutVar.put("result_Msg", "웹연동에 실패하였습니다.(전산담당 문의)");
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
     * 대명라이프웨이 가상계좌 산출이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/clct-hstr/list")
    public ModelAndView getDlwCardCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = reqCustService.getDlwVrtlAcntClctCount(hmParam);

            mapOutVar.put("tc_log", nTotal);

            List<Map<String, Object>> mList = reqCustService.getDlwVrtlAcntCsmm(hmParam);
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
     * 대명라이프웨이 가상계좌 NICE 전송  (강제마감처리    입금액을   0 원으로 처리!!! )
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-cancelsend")
    public ModelAndView cancelsendNiceVrtlAccntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            /****************************************************************************************/
            /****************************************************************************************/

            System.out.println(listInDs.size());
            CommonUtils.printLog(listInDs.get(0));
            int totCnt = 0;
            int sucCnt = 0;
            int errCnt = 0;

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                hmParam.put("yymmdd", mapInVar.get("yymmdd"));
                hmParam.put("inv_dt", mapInVar.get("inv_dt"));
                hmParam.put("inv_time", mapInVar.get("inv_time"));
                hmParam.put("wdrw_req_amt_cancel","555");  //(강제마감처리    입금액을   0 원으로 처리!!!   500이하는 가상계좌 산출 불가능  !!!  555 셋팅!!)

                try {
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
                    httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt_cancel")));
                    httpRequestWrapper.addParameter("VbankExpDate", (String)hmParam.get("inv_dt"));
                    httpRequestWrapper.addParameter("VbankExpTime", (String)hmParam.get("inv_time"));
                    httpRequestWrapper.addParameter("Moid", (String)hmParam.get("mem_no"));

                    httpRequestWrapper.addParameter("MID", "dmlife004m");
                    httpRequestWrapper.addParameter("EncodeKey", "Kw61T06XZpKe1SuwJn+hUBnLNwYrqiQWSDR/Xa8/Ua6ZpBnLgGUOkAgzPTFVqn52kyBZSn1y5ANLlCG+6kyoIA==");
                   // httpRequestWrapper.addParameter("MID", "nictest04m");
                   // httpRequestWrapper.addParameter("EncodeKey", "b+zhZ4yOZ7FsH8pm5lhDfHZEb79tIwnjsdA0FBXh86yLc6BJeFVrZFXhAoJ3gEWgrWwN+lJMV0W4hvDdbe4Sjw==");

                   //httpRequestWrapper.addParameter("MallIP", "61.39.175.220");
                    httpRequestWrapper.addParameter("MallIP", "183.111.69.174");
                    //httpRequestWrapper.addParameter("MallIP", "210.114.225.219");
                    NicePayWEB nicepayWEB = new NicePayWEB();

                    ParamUtils.addCenterParam(hmParam);
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

                    /*
                    System.out.println("--------------------------------------------");
                    System.out.println((new StringBuilder("결과                   : ")).append(resultCode).toString());
                    System.out.println((new StringBuilder("결과 메시지        : ")).append(resultMsg).toString());
                    System.out.println((new StringBuilder("거래번호(TID) : ")).append(tid).toString());
                    System.out.println("--------------------------------------------");
                    */
                    hmParam.put("result_cd", resultCode);
                    hmParam.put("result_msg", resultMsg);
                    hmParam.put("tid", tid);
                    if("4120".equals(resultCode)) {
                        sucCnt++;
                    } else {
                        errCnt++;
                    }
                    reqCustService.updateNiceVrtlAccntWdrwReq(hmParam);
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
     * 대명라이프웨이 가상계좌 정보를 강제마감처리한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/clct-hstr-cls")
    public ModelAndView updateDlwPymnCancNicepay(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String seq = StringUtils.defaultString((String) hmParam.get("seq"));
                String expirydt = StringUtils.defaultString((String) hmParam.get("expiry_dt"));
                String vrtlaccntno = StringUtils.defaultString((String) hmParam.get("vrtl_accnt_no"));
                String bankcd = StringUtils.defaultString((String) hmParam.get("bank_cd"));

                hmParam.put("seq", seq);
                hmParam.put("expiry_dt", expirydt);
                hmParam.put("vrtl_accnt_no", vrtlaccntno);
                hmParam.put("bank_cd", bankcd);
          // 체크된것만 복사해서 넘김  불필요!!
         //       if (rowType == DataSet.ROW_TYPE_UPDATED){
                reqCustService.deleteDlwVrtlAcntClct(hmParam);
           //     }
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

            List<Map<String, Object>> mList = reqCustService.getVirtualReqList(hmParam);
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
                    reqCustService.updateVirtualReqSendtoNice(hmParam);
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
                    reqCustService.updateVirtualExtDelete(hmParam);
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
            int x = reqCustService.updateCompulsionWdrdList(hmParam);

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
     * 가상계좌 청구 회원 이니시스 전송 및 가상계좌 채번 프로세스
     * 임동진
     * 20200518
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value =  "/virtual/inicis-send")
    public ModelAndView sendInicisVrtlReqList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int sucCnt = 0;
            int errCnt = 0;

            //세션
            ParamUtils.addSaveParam(hmParam);

            hmParam = listInDs.get(0);
            try {
                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 inicisPay.setData("mid", "daemyungT1");

                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("moid", (String)hmParam.get("accnt_no"));
                inicisPay.setData("goodName", "가상계좌취합상품");
                inicisPay.setData("buyerName", (String)hmParam.get("mem_nm"));
                inicisPay.setData("buyerEmail", "lifeway@daemyung.com");
                inicisPay.setData("buyerTel", "15888511");
                inicisPay.setData("price", (String)hmParam.get("pay_amt"));
                inicisPay.setData("bankCode", (String)hmParam.get("ichae_cd"));
                inicisPay.setData("dtInput", (String)hmParam.get("end_day"));
                inicisPay.setData("tmInput", "2359");
                inicisPay.setData("nmInput", (String)hmParam.get("mem_nm"));

                inicisPay.formVacct();

                String resultCode = inicisPay.getData("resultCode");
                String resultMsg = inicisPay.getData("resultMsg");
                String tid = inicisPay.getData("tid");
                String vacct = inicisPay.getData("vacct");

                hmParam.put("result_cd", resultCode);
                hmParam.put("result_msg", resultMsg);
                hmParam.put("tid", tid);
                hmParam.put("vtr_acc_no", vacct);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                if("00".equals(resultCode)) {
                    reqCustService.insertVirtualMain(hmParam);
                    mapOutVar.put("result", "success");
                    mapOutVar.put("vtr_acc_no", vacct);	//전송 후 문자 popup에 가상계좌번호 입력
                } else {
                     mapOutVar.put("result", "fail내역 : [" + resultMsg + "]");
                }
            } catch(Exception e) {
                e.printStackTrace();
                mapOutVar.put("result", "fail");
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
     * 가상계좌 청구 회원 이니시스 취소 전송 및 청구 삭제 프로세스
     * 임동진
     * 20200518
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/virtual/inicis_delete")
    public ModelAndView updateVirtualInicisDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);

            hmParam = listInDs.get(0);

            if (hmParam.get("dpst_stat").equals("0002")){
                reqCustService.updateVirtualMain(hmParam);
                return modelAndView;
            }

            INICISPay inicisPay = new INICISPay();
             inicisPay.setInicisKey("hmPLjIdyekyylSx9");
             inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
             inicisPay.setData("mid", "daemyungT1");
            inicisPay.setData("clientIp", "127.0.0.1");
            inicisPay.setData("tid",(String)hmParam.get("result_key"));

            inicisPay.vacctCancel();

            String resultCode = inicisPay.getData("resultCode");
            String resultMsg = inicisPay.getData("resultMsg");
            String tid = inicisPay.getData("tid");

            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            if("00".equals(resultCode)) {
                mapOutVar.put("result", "success");
                reqCustService.updateVirtualMain(hmParam);

            } else {
                 mapOutVar.put("result", "fail내역 : [" + resultMsg + "]");
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

    /* 파일 업로드 */
    @RequestMapping(value = "/extChk-upload")
    public void uploadEctChk(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            reqCustService.uploadEctChk(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /** ================================================================
     * 날짜 : 20201014
     * 이름 : 송준빈
     * 추가내용 : BillKey 자동생성 리스트
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.CARD_MEM
     * ================================================================
     * */
    @RequestMapping(value = "/billkey/getCreateBillKeyList")
    public ModelAndView getEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = reqCustService.getCreateBillKeyCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);

                List<Map<String, Object>> mList = reqCustService.getCreateBillKeyList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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

    /** ===============================================================================
     * 장기할부 자유 결제 (이니시스)
     * 임동진
     * 20210602
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     *  ===============================================================================
     */
    @RequestMapping(value = "/longterm-inicis-realtime")
    public ModelAndView ReqCustLongtermInicisRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            String pmResultCd = "";
            String pmResultMsg = "";

            hmParam.put("pay_mthd", "06");  // 카드
            hmParam.put("pay_kind", "10");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타,10:장기할부)

            System.out.println(hmParam);

            // 장기할부 결제 시작
            resultMap = reqCustService.billPayInicisLongTermProc(hmParam);

            pmResultCd = (String)resultMap.get("result_cd");
            pmResultMsg = (String)resultMap.get("result_msg");

            if("00".equals(pmResultCd)){
                hmParam.put("del_fg", "C");
                hmParam.put("result_key", resultMap.get("result_key"));
                hmParam.put("auth_dt", resultMap.get("auth_dt"));
                hmParam.put("auth_cd", resultMap.get("auth_cd"));
                  hmParam.put("card_cd", resultMap.get("card_cd"));

                  // 청구 데이터 입력
                reqCustService.insertFreeCardResult(hmParam);

                // 장기할부 데이터 등록 (납입 완료 등)
                reqCustService.insertLongTermData(hmParam);

                //상담 로그 등록
                String CardNo = (String)hmParam.get("card_no");
                String CardMasking =CardNo.toString().substring(0,4) + "-****-" + CardNo.toString().substring(CardNo.length() -4, CardNo.length());
                String cnslDtlCon ="";
                cnslDtlCon = (new StringBuilder("  카드번호 : ")).append(CardMasking).append("  결제금액 : ").append((String)hmParam.get("pay_amt")).toString();
                hmParam.put("cons_memo", "장기할부결제완료");
                hmParam.put("cnsl_con", cnslDtlCon);

                consController.saveConsdlw(hmParam);
            }

            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

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
     * 환불관리정보 팝업 조회
     * 김주용
     * 20210909
     */
    @RequestMapping(value = "/getRefundReqPopList")
    public ModelAndView getRefundReqPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
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


                System.out.println("xxxxxxxxxxxxxxxxxx>" + hmParam);

                List<Map<String, Object>> mList = reqCustService.getRefundReqPopList(hmParam);
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
     * 파산/회생회원 리스트
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getCustBankRupList")
    public ModelAndView getCustExceptionList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            int nTotal = reqCustService.getCustExceptionCount(hmParam);

            List<Map<String, Object>> mList = reqCustService.getCustExceptionList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            mapOutVar.put("tc_list", nTotal);

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
     * 파산회상 관리crud
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/CustBankRup/{crudType}")
    public ModelAndView reqCustExceptionMng(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
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

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insert".equals(crudTyp)){
                    // 기존 특수회원인지 체크
                    //int chkCnt = reqCustService.getChkSpecialAcntList(hmParam);
                    int chkCnt = reqCustService.getChkCustExceptionList(hmParam);

                    if(chkCnt == 0) {
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        //reqCustService.insertExtSpecial(hmParam);
                        reqCustService.insertCustException(hmParam);
                    }
                    else {
                        mapOutVar.put("g_reason_msg", "해당 회원은 이미 특수회원으로 등록되어 있습니다.");  // 실패 메시지 return
                    }
                }
                else if("update".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    //reqCustService.updateExtSpecial(hmParam);
                    reqCustService.updateCustException(hmParam);
                }
                else if("delete".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    //reqCustService.deleteExtSpecial(hmParam);
                    reqCustService.deleteCustException(hmParam);
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
     * 회생 변제 관리 조회
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getBankRupSetup")
    public ModelAndView getBankRupSetup(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("mst_seq", mapInVar.get("mst_seq")); // 청구일

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList = reqCustService.getBankRupSetup(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 파산회상 관리crud
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertBankRup")
    public ModelAndView insertBankRup(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                reqCustService.insertBankRup(hmParam);
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
     * 파산회상 관리crud
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateBankRup")
    public ModelAndView updateBankRup(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                reqCustService.updateBankRup(hmParam);
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
     * 파산회상 관리crud
     * 김주용
     * 20220516
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteBankRup")
    public ModelAndView deleteBankRup(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                reqCustService.deleteBankRup(hmParam);
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



    /** ===============================================================================
     * 장기할부 자유 결제 (이니시스)(복사)
     * 김주용
     * 20220822
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     *  ===============================================================================
     */
    @RequestMapping(value = "/longterm-inicis-newType-ealtime")
    public ModelAndView ReqCustNewTypeLongtermInicisRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            String pmResultCd = "";
            String pmResultMsg = "";

            hmParam.put("pay_mthd", "06");  // 카드
            hmParam.put("pay_kind", "10");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타,10:장기할부)

            System.out.println(hmParam);

            // 장기할부 결제 시작
            resultMap = reqCustService.billPayInicisNewTypeLongTermProc(hmParam);

            pmResultCd = (String)resultMap.get("result_cd");
            pmResultMsg = (String)resultMap.get("result_msg");

            if("00".equals(pmResultCd)){
                hmParam.put("del_fg", "C");
                hmParam.put("result_key", resultMap.get("result_key"));
                hmParam.put("auth_dt", resultMap.get("auth_dt"));
                hmParam.put("auth_cd", resultMap.get("auth_cd"));
                  hmParam.put("card_cd", resultMap.get("card_cd"));

                  // 청구 데이터 입력
                reqCustService.insertFreeCardResult(hmParam);

                // 장기할부 데이터 등록 (납입 완료 등)
                reqCustService.insertNewTypeLongTermData(hmParam);

                //상담 로그 등록
                String CardNo = (String)hmParam.get("card_no");
                String CardMasking =CardNo.toString().substring(0,4) + "-****-" + CardNo.toString().substring(CardNo.length() -4, CardNo.length());
                String cnslDtlCon ="";
                cnslDtlCon = (new StringBuilder("  카드번호 : ")).append(CardMasking).append("  결제금액 : ").append((String)hmParam.get("pay_amt")).toString();
                hmParam.put("cons_memo", "장기할부결제완료");
                hmParam.put("cnsl_con", cnslDtlCon);

                consController.saveConsdlw(hmParam);
            }

            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

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
}