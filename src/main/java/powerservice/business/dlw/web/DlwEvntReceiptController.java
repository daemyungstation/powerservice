/*
 * (@)# DlwEvntController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwEmplService;
import powerservice.business.dlw.service.DlwEvntReceiptService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.dlw.service.DlwPayService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.web.DmWebSenderController;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Controller
@RequestMapping(value = "/dlw/evnt/receipt")
public class DlwEvntReceiptController {

    @Resource
    private DlwEvntService DlwEvntService;
    
    @Resource
    private DlwEvntReceiptService DlwEvntReceiptService;
    
    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;
    
    @Resource
    private ConsService consService;
    
    @Resource
    private DlwEmplService dlwEmplService;

    @Resource
    private DlwCmsService DlwCmsService;

    @Resource
    private DlwPayService dlwPayService;

    @Resource
    private DmWebSenderController dlwWebSenderController;
    
    @Resource
    private DlwMallLinkedService DlwMallLinkedService;
    
    /**
     * 대명라이프웨이 행사 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEventReceiptList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                // 세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int nTotal = DlwEvntReceiptService.getEventReceiptCount(hmParam);
                mapOutVar.put("tc_evt", nTotal);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getEventReceiptList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 행사 정보를 입력한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertEventReceipt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("close_cl", "0002");
                hmParam.put("close_dt", hmParam.get("event_comp_day"));

                String result_msg = "";
                /***************************************
                 *  2017.07.27 김준호
                 *  관리업무>행사조회>모니터링>모니터링 결과 보고서
                 *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
                 ***************************************/
                
                int count = DlwEvntReceiptService.countEventReceipt(hmParam);
                if (count == 0) {
                	int result = DlwEvntReceiptService.insertEventReceipt(hmParam);
                } else {
                	szErrorCode = "-1";
                	szErrorMsg = "등록된 회원이 존재합니다.";
                }
                
                if (!"".equals(CommonUtils.checkNull((String)hmParam.get("cp_emple_no")))) {
                	hmParam.put("evt_seq", "9999999"); //해약접수단계에선 행사가 존재하지 않아 기본세팅해준다.
                	DlwEvntReceiptService.sndPushAlertMain(hmParam);
                }
                
                /*
                 * if (result <= 0) { result_msg = "error"; } else {
                 * result_msg = "success"; }
                 */
                if (!"".equals(CommonUtils.checkNull((String)hmParam.get("mem_no")))) {
                	saveConsdlw(hmParam, "1");
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사 정보를 수정한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    public ModelAndView updateEventReceipt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sCpChk = (String) mapInVar.get("cp_chk");

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String result_msg = "";
                
                int result = DlwEvntReceiptService.updateEventReceipt(hmParam);
                
                if (!"".equals(CommonUtils.checkNull((String)hmParam.get("cp_emple_no"))) && "Y".equals(sCpChk)) {
                	hmParam.put("evt_seq", "9999999"); //해약접수단계에선 행사가 존재하지 않아 기본세팅해준다.
                	DlwEvntReceiptService.sndPushAlertMain(hmParam);
                }
                
                if (!"".equals(CommonUtils.checkNull((String)hmParam.get("mem_no")))) {
              	    saveConsdlw(hmParam, "1");
                }

            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 행사 정보를 삭제한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteEventReceipt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String result_msg = "";

                int result = DlwEvntReceiptService.deleteEventReceipt(hmParam);
                
                if (!"".equals(CommonUtils.checkNull((String)hmParam.get("mem_no")))) {
                  	saveConsdlw(hmParam, "2");
                  }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 행사 정보를 입력한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertComplete")
    public ModelAndView insertEventReceiptComplete(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
            	hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("close_cl", "0002");
                hmParam.put("close_dt", hmParam.get("event_comp_day"));

                String result_msg = "";
                /***************************************
                 *  2017.07.27 김준호
                 *  관리업무>행사조회>모니터링>모니터링 결과 보고서
                 *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
                 ***************************************/

                int count = DlwEvntService.countEvent(hmParam);
                if (count == 0) {

                    if ("N".equals(closeDataSaveYnChk(hmParam))) {
                        mapOutVar.put("edt_yn", "N");
                    } else {
                        //int result = DlwEvntReceiptService.insertEventReceiptComplete(hmParam);
                    	DlwEvntReceiptService.eventReceiptComplete(hmParam);
                        /*
                         * if (result <= 0) { result_msg = "error"; } else {
                         * result_msg = "success"; }
                         */
//                        List<Map<String, Object>> mList = DlwEvntService.getEvtSeq(hmParam);

//                        listMap.setRowMaps(mList);
//                        mapOutDs.put("ds_output", listMap);
//                        mapOutVar.put("result_msg", result_msg);

                        /* ================================================================
                         * 날짜 : 20171226
                         * 이름 : 송준빈
                         * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사대기로 바꿈
                         * ================================================================
                         * */

                        hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                        hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                        hmParam.put("P_STATE", "3");
                        System.out.println("insert인풋데이터 :: " +hmParam );

//                        int result1 = DlwEvntService.updateEventMemberState(hmParam);
//                        int result1 = DlwMallLinkedService.updateResnMallState(hmParam);

                        /* ================================================================
                         * 날짜 : 20181114
                         * 이름 : 송준빈
                         * 추가내용 : 청구가 존재하는 회원인지 확인후 청구가 존재한다면 가수금 테이블에 insert
                         * ================================================================
                         * */
                        List<Map<String, Object>> mList1 = DlwEvntService.getWdrwReqConfirm(hmParam);
                        if (mList1.size() > 0)
                        {
                        	System.out.println("행사로 인한 환불금 등록");
                        	mList1.get(0).put("auto_name", "evnt");
                            dlwPayService.insertMemberReqRefund(mList1.get(0));
                        	
                        	if("N".equals(mList1.get(0).get("AUTH_YN"))) {
                        		System.out.println("행사로 인한 환불금 무승인 등록");
                                //dlwPayService.insertNauthPayCancelResnEvnt(mList1.get(0));
                        	}
                        }
                        
                        List<Map<String, Object>> mList2 = DlwEvntReceiptService.getEventSeq(hmParam);
                        if (mList2.size() > 0)
                        {
                        	hmParam2 = mList2.get(0);
                        	hmParam.put("evt_seq", hmParam2.get("seq"));
                        }
                        //행사자 등록 IMPL에 푸시알림까지 전송 - 211101 프로시저로 이동
                        //DlwEvntReceiptService.insertevntMngr(hmParam); // 등록
                        
                        if (!"".equals(CommonUtils.checkNull((String)hmParam.get("mem_no")))) {
                          	saveConsdlw(hmParam, "3");
                          }
                    }
                }else{
                    result_msg = "error";
                    mapOutVar.put("result_msg", result_msg);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 마감데이터 확인
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public String closeDataSaveYnChk(Map<String, Object> pmParam)
            throws Exception {
        String edtYn = "";
        edtYn = DlwEvntService.getCloseDataSaveYnChk(pmParam);
        return edtYn;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public void saveConsdlw(Map <String, Object> pmParam, String psChk) throws Exception {
        String sConsno = "";
        String sAccntNo = StringUtils.defaultString((String)pmParam.get("accnt_no")); // 회원번호

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        //인우상담 관련
        // 담당자 정보 저장
        pmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());
        pmParam.put("chpr_id", oLoginUser.getUserid());
        ParamUtils.addSaveParam(pmParam);
        pmParam.put("cons_memo_tit", pmParam.get("cons_memo"));
        pmParam.put("cons_memo_cntn", pmParam.get("cnsl_con"));
        pmParam.put("cons_dspsmddl_dtpt_cd", "10"); //상담처리중 - 일반
        pmParam.put("cons_stat_cd", "20"); //상담상태 - 처리완료
        pmParam.put("clnt_nm", pmParam.get("mem_nm"));
        pmParam.put("acpg_chnl_cd", "10");
        pmParam.put("consno_sqno", "1");
        pmParam.put("conc_yn", "N");

        //대명상담 관련
        pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
        pmParam.put("cnsl_cd", "01"); // COM_CD_GRP 테이블 기타

        pmParam.put("rgsr_id", oLoginUser.getUserid());
        pmParam.put("amnd_id", oLoginUser.getUserid());
        pmParam.put("cnsl_man", oLoginUser.getUserid());
        
        pmParam.put("cons_typ1_cd", "CT01060000" );
        pmParam.put("cons_typ2_cd", "CT01060100");
        
        if("1".equals(psChk)) {
        	pmParam.put("cons_typ3_cd", "CT01060101"); //행사접수
        	pmParam.put("cons_memo_tit", "행사접수 진행중");
//        	pmParam.put("cons_memo_cntn", "");
        	pmParam.put("cons_memo_cntn", "접수일시:"+pmParam.get("receipt_day")+" "+pmParam.get("receipt_day_h")+":"+pmParam.get("receipt_day_m")
        			                     +" 사망일시:"+pmParam.get("deathday")+" CP명:"+pmParam.get("cp_emple_nm")+" 현재위치:"+pmParam.get("loc")
        			                     +" 참고사항:"+pmParam.get("rmk"));
        } else if("2".equals(psChk)) {
        	pmParam.put("cons_typ3_cd", "CT01060102"); //접수취소
        	pmParam.put("cons_memo_tit", "행사접수 취소");
        	pmParam.put("cons_memo_cntn", "");
        } else if("3".equals(psChk)) {
        	pmParam.put("cons_typ3_cd", "CT01060104"); //접수완료
        	pmParam.put("cons_memo_tit", "행사접수 완료");
//        	pmParam.put("cons_memo_cntn", "");
        	pmParam.put("cons_memo_cntn", "접수일시:"+pmParam.get("receipt_day")+" "+pmParam.get("receipt_day_h")+":"+pmParam.get("receipt_day_m")
                    +" 사망일시:"+pmParam.get("deathday")+" CP명:"+pmParam.get("cp_emple_nm")+" 현재위치:"+pmParam.get("loc")
                    +" 참고사항:"+pmParam.get("rmk"));
        }
//        String sConsMemoTit = StringUtils.defaultString((String)pmParam.get("cons_memo_tit")); 		// 제목
//        String sConsMemoCntn = StringUtils.defaultString((String)pmParam.get("cons_memo_cntn")); 	// 내용
        // 회원번호가 없을 경우 기타문의 설정
        if ("".equals(sAccntNo)) {
            pmParam.put("accnt_no", "00000"); // 기타문의 lifeMngController.java 1199
        }

        // 신규등록
        if ("".equals(sConsno)) {
            pmParam.put("actp_id", oLoginUser.getUserid()); // 접수자 정보
            // 상담 등록
            sConsno = consService.insertCons(pmParam);
        }
    }
    
    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-acnt/list")
    public ModelAndView getDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                int nTotal = DlwEvntReceiptService.getDlwCustAcntCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = DlwEvntReceiptService.getDlwCustAcntList(hmParam);
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
     * 행사취소사유 검색
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEventCancelList")
    public ModelAndView getEventCancelList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getEventCancelList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 행사 취소사유를 입력한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertEventCancel")
    public ModelAndView insertEventCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
               	int result = DlwEvntReceiptService.insertEventCancel(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 지역 공통코드 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getComCd")
    public ModelAndView getComCd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getComCd(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 행사자 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getManagerList")
    public ModelAndView getManagerList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Object reg = (Object) mapInVar.get("reg");

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                
                if (reg == null || ((String) reg).trim().length() == 0) {

                } else {
                    if (reg.equals("CP")) {
                        hmParam.put("reg", reg);
                    }
                }

                List<Map<String, Object>> mList = DlwEvntReceiptService.getManagerList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 콜현황 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEventCallList")
    public ModelAndView getEventCallList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getEventCallList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    /* 김주용 개발 */
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping(value = "/getListEventMain")
    public ModelAndView getEvntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
                
                int nTotal = DlwEvntReceiptService.getEventMainCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);
                
                List<Map<String, Object>> mList = DlwEvntReceiptService.getEventMainList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
    
    @RequestMapping(value = "/getEventDetailList")
    public ModelAndView getEventDetailList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapEventDetailList = new DataSetMap();
        DataSetMap listMapSuppliesClsfcDtl = new DataSetMap();
        DataSetMap listMapAddSales1List = new DataSetMap();
        DataSetMap listMapAddSales2List = new DataSetMap();
        DataSetMap listMapPaymentInfoList = new DataSetMap();
        
        DataSetMap listMapMemConsList = new DataSetMap();
        DataSetMap listMapProtocolMngrHist = new DataSetMap();
        
        DataSetMap listMapEventMngrList = new DataSetMap();
        DataSetMap listMapMngrPayList = new DataSetMap();
        DataSetMap listMapMemberAccntDtlList = new DataSetMap();
        DataSetMap listMapPayTotList = new DataSetMap();
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
            String sEventPageId = (String) mapInVar.get("event_page_id");
            String sHistoryPageId = (String) mapInVar.get("history_page_id");
            String sExtInfoPageId = (String) mapInVar.get("ext_info_page_id");
            
            if (listInDs.size() > 0) 
            {
                hmParam = listInDs.get(0);
                
                /* 탭 제 1 영역 */
                if(sEventPageId.equals("tap_eventBasicInfo"))
                {
                    List<Map<String, Object>> mList = DlwEvntReceiptService.getEventDetailList(hmParam);
                    listMapEventDetailList.setRowMaps(mList);
                    mapOutDs.put("ds_outputEventDetailList", listMapEventDetailList);
                }
                else if(sEventPageId.equals("tap_supplyItem"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getSuppliesClsfcDtlList(hmParam);
                	listMapSuppliesClsfcDtl.setRowMaps(mList);
                    mapOutDs.put("ds_outputSuppliesClsfcDtlList", listMapSuppliesClsfcDtl);
                }
                else if(sEventPageId.equals("tap_addSales1"))
                {
                    List<Map<String, Object>> mList = DlwEvntReceiptService.getAddSales1List(hmParam);
                    listMapAddSales1List.setRowMaps(mList);                         
                    mapOutDs.put("ds_outputAddSales1List", listMapAddSales1List); 
                }
                else if(sEventPageId.equals("tap_addSales2"))
                {
                    List<Map<String, Object>> mList = DlwEvntReceiptService.getAddSales2List(hmParam);
                    listMapAddSales2List.setRowMaps(mList);                         
                    mapOutDs.put("ds_outputAddSales2List", listMapAddSales2List);
                }
                else if(sEventPageId.equals("tap_paymentInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getPaymentInfoList(hmParam);
                	listMapPaymentInfoList.setRowMaps(mList);
                    mapOutDs.put("ds_outputPaymentInfoList", listMapPaymentInfoList);
                }
                else if(sEventPageId.equals("tap_eventAlter"))
                {
                    
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
                
                /* 탭 제 2 영역 */
                if(sHistoryPageId.equals("tap_consHist"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getMemConsList(hmParam);
                    listMapMemConsList.setRowMaps(mList);
                    mapOutDs.put("ds_outputMemConsList", listMapMemConsList);
                }
                else if(sHistoryPageId.equals("tap_protocolMngrHist"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getProtocolMngrHistList(hmParam);
                	listMapProtocolMngrHist.setRowMaps(mList);
                	mapOutDs.put("ds_outputProtocolMngrHistList", listMapProtocolMngrHist);
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
                
                /* 탭 제 3 영역 */
                if(sExtInfoPageId.equals("tap_eventMngrInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getEventMngrList(hmParam);
                    listMapEventMngrList.setRowMaps(mList);
                    mapOutDs.put("ds_outputEventMngrList", listMapEventMngrList);
                    
                    List<Map<String, Object>> mList1 = DlwEvntReceiptService.getMngrPayList(hmParam);
                    listMapMngrPayList.setRowMaps(mList1);
                    mapOutDs.put("ds_outputMngrPayList", listMapMngrPayList);
                }
                else if(sExtInfoPageId.equals("tap_memAccntInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getMemberAccntDtlList(hmParam);
                    listMapMemberAccntDtlList.setRowMaps(mList);
                    mapOutDs.put("ds_outputMemberAccntDtlList", listMapMemberAccntDtlList);
                    
                    List<Map<String, Object>> mList1 = DlwEvntReceiptService.getPayTotList(hmParam);
                    listMapPayTotList.setRowMaps(mList1);
                    mapOutDs.put("ds_outputPayTotList", listMapPayTotList);
                }
                else if(sExtInfoPageId.equals("tap_etcInfo"))
                {
                	
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
    
    @RequestMapping(value = "/getEventTabList")
    public ModelAndView getEventTabList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapEventDetailList = new DataSetMap();
        DataSetMap listMapSuppliesClsfcDtl = new DataSetMap();
        DataSetMap listMapAddSales1List = new DataSetMap();
        DataSetMap listMapAddSales2List = new DataSetMap();
        DataSetMap listMapPaymentInfoList = new DataSetMap();
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
            String sEventPageId = (String) mapInVar.get("event_page_id");
            
            if (listInDs.size() > 0) 
            {
                hmParam = listInDs.get(0);
            	
                /* 탭 제 1 영역 */
                if(sEventPageId.equals("tap_eventBasicInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getEventDetailList(hmParam);
                	listMapEventDetailList.setRowMaps(mList);
                    mapOutDs.put("ds_outputEventDetailList", listMapEventDetailList);
                }
                else if(sEventPageId.equals("tap_supplyItem"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getSuppliesClsfcDtlList(hmParam);
                	listMapSuppliesClsfcDtl.setRowMaps(mList);
                    mapOutDs.put("ds_outputSuppliesClsfcDtlList", listMapSuppliesClsfcDtl);
                }
                else if(sEventPageId.equals("tap_addSales1"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getAddSales1List(hmParam);
                	listMapAddSales1List.setRowMaps(mList);
                    mapOutDs.put("ds_outputAddSales1List", listMapAddSales1List);
                }
                else if(sEventPageId.equals("tap_addSales2"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getAddSales2List(hmParam);
                	listMapAddSales2List.setRowMaps(mList);
                    mapOutDs.put("ds_outputAddSales2List", listMapAddSales2List);
                }
                else if(sEventPageId.equals("tap_paymentInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getPaymentInfoList(hmParam);
                	listMapPaymentInfoList.setRowMaps(mList);
                    mapOutDs.put("ds_outputPaymentInfoList", listMapPaymentInfoList);
                }
                else if(sEventPageId.equals("tap_eventAlter"))
                {
                	
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
    
    @RequestMapping(value = "/saveEventTabList")
    public ModelAndView savetEventTabList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String sSaveType = (String) mapInVar.get("save_type");
            String sSvcId = (String) mapInVar.get("svc_id");
            
            if (listInDs.size() > 0) 
            {
                Map<String, Object> hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                if(sSvcId.equals("tap_eventBasicInfo"))
                {                    
                	if (sSaveType.equals("update"))
                    {
                		DataSetMap listOldInDs = (DataSetMap)mapInDs.get("ds_old_input");
                		Map<String, Object> oldHmParam = listOldInDs.get(0);
                		ArrayList<String> fieldName = new ArrayList<String>();
                		String sConsMemoCntn = "";
                		
                		System.out.println("########NEW########" + hmParam.toString());
                		System.out.println("########OLD########" + oldHmParam.toString());

                		
                		for(Entry<String,Object> entry : hmParam.entrySet()) 
                		{
                			fieldName.add(entry.getKey());
                        }

                        for(int idx = 0; idx < fieldName.size(); idx++)
                        {
                        	String sFieldName = fieldName.get(idx);
                        	String sOldFieldVal = StringUtils.defaultString(String.valueOf(oldHmParam.get(sFieldName)));
                        	String sNewFieldVal = StringUtils.defaultString(String.valueOf(hmParam.get(sFieldName)));
                        	
                        	System.out.println("변경필드명 : " + sFieldName + ", 이전의값 : " + sOldFieldVal + ", 현재의값 : " + sNewFieldVal);
                        	
                        	if(!sOldFieldVal.equals(sNewFieldVal))
                        	{
                        		if(!sFieldName.equals("cntr_cd") && !sFieldName.equals("amnd_id") && !sFieldName.equals("amnd_nm") && !sFieldName.equals("rgsr_id") && !sFieldName.equals("rgsr_nm"))
                        		{
                        			if(sFieldName.equals("accnt_no")) { sFieldName = "회원번호"; }
                            		else if(sFieldName.equals("mem_nm")) { sFieldName = "회원명"; }
                            		else if(sFieldName.equals("brth_mon_day")) { sFieldName = "생년월일"; }
                            		else if(sFieldName.equals("cell")) { sFieldName = "연락처"; }
                            		else if(sFieldName.equals("accept_day")) { sFieldName = "접수일시"; }
                            		else if(sFieldName.equals("val8_h")) { sFieldName = "접수일시(시)"; }
                            		else if(sFieldName.equals("val8_m")) { sFieldName = "접수일시(분)"; }
                            		else if(sFieldName.equals("cadt")) { sFieldName = "소요시간"; }
                            		else if(sFieldName.equals("move_day")) { sFieldName = "출동일시"; }
                            		else if(sFieldName.equals("val9_h")) { sFieldName = "출동일시(시)"; }
                            		else if(sFieldName.equals("val9_m")) { sFieldName = "출동일시(분)"; }
                            		else if(sFieldName.equals("arrive_day")) { sFieldName = "도착일시"; }
                            		else if(sFieldName.equals("arr_h")) { sFieldName = "도착일시(시)"; }
                            		else if(sFieldName.equals("arr_m")) { sFieldName = "도착일시(분)"; }
                            		else if(sFieldName.equals("event_reg_day")) { sFieldName = "행사등록일"; }
                            		else if(sFieldName.equals("event_proc_day")) { sFieldName = "행사시작일"; }
                            		else if(sFieldName.equals("event_comp_day")) { sFieldName = "행사종료일"; }
                            		else if(sFieldName.equals("comp_h")) { sFieldName = "행사종료일(시)"; }
                            		else if(sFieldName.equals("comp_m")) { sFieldName = "행사종료일(분)"; }
                            		else if(sFieldName.equals("ent_day")) { sFieldName = "입관일"; }
                            		else if(sFieldName.equals("val7_h")) { sFieldName = "입관일(시)"; }
                            		else if(sFieldName.equals("val7_m")) { sFieldName = "입관일(분)"; }
                            		else if(sFieldName.equals("cfn_day")) { sFieldName = "발인일시"; }
                            		else if(sFieldName.equals("cfn_h")) { sFieldName = "발인일시(시)"; }
                            		else if(sFieldName.equals("cfn_m")) { sFieldName = "발인일시(분)"; }
                            		else if(sFieldName.equals("val1")) { sFieldName = "고인명"; }
                            		else if(sFieldName.equals("sex")) { sFieldName = "성별"; }
                            		else if(sFieldName.equals("agerng")) { sFieldName = "연령"; }
                            		else if(sFieldName.equals("die_reson")) { sFieldName = "사망사유"; }
                            		else if(sFieldName.equals("rel")) { sFieldName = "회원관계"; }
                            		else if(sFieldName.equals("brplot")) { sFieldName = "종교"; }
                            		else if(sFieldName.equals("loc")) { sFieldName = "현재위치"; }
                            		else if(sFieldName.equals("death_day")) { sFieldName = "사망일시"; }
                            		else if(sFieldName.equals("event_place_cl")) { sFieldName = "행사장소(장례식장)"; }
                            		else if(sFieldName.equals("funrl_hall")) { sFieldName = "행사장소(장례식장코드)"; }
                            		else if(sFieldName.equals("funrl_nm")) { sFieldName = "행사장소(장례식장명)"; }
                            		else if(sFieldName.equals("funrl_addr")) { sFieldName = "행사장소(장례식장상세주소)"; }
                            		else if(sFieldName.equals("val2")) { sFieldName = "상주명"; }
                            		else if(sFieldName.equals("val4")) { sFieldName = "상주연락처"; }
                            		else if(sFieldName.equals("erg_tel")) { sFieldName = "비상연락처"; }
                            		else if(sFieldName.equals("cemetery")) { sFieldName = "장지"; }
                            		else if(sFieldName.equals("note")) { sFieldName = "도우미(명)"; }
                            		else if(sFieldName.equals("funrl_proc_yn")) { sFieldName = "운구여부"; }
                            		else if(sFieldName.equals("bur_cre_cl")) { sFieldName = "매화탈코드"; }
                            		else if(sFieldName.equals("cremation_cd")) { sFieldName = "매화탈장소코드"; }
                            		else if(sFieldName.equals("cremation_nm")) { sFieldName = "매화탈장소명"; }
                            		else if(sFieldName.equals("cnl_emple_no")) { sFieldName = "취소접수자사번"; }
                            		else if(sFieldName.equals("cnl_emple_nm")) { sFieldName = "취소접수자명"; }
                            		else if(sFieldName.equals("cancl_reg_dm")) { sFieldName = "취소접수시간"; }
                            		else if(sFieldName.equals("cancel_accept_day")) { sFieldName = "취소접수일시"; }
                            		else if(sFieldName.equals("ca_tm")) { sFieldName = "취소접수일시(시)"; }
                            		else if(sFieldName.equals("ca_mm")) { sFieldName = "취소접수일시(분)"; }
                            		else if(sFieldName.equals("cnl_reason_cd")) { sFieldName = "행사취소사유"; }
                            		else if(sFieldName.equals("cnl_reson")) { sFieldName = "행사취소사유상세"; }
                            		else if(sFieldName.equals("cnl_work_yn")) { sFieldName = "출동중취소"; }
                            		else if(sFieldName.equals("cnl_result")) { sFieldName = "행사취소내용및결과"; }
                            		else { sFieldName = "기타"; }
                            		
                            		sConsMemoCntn = sConsMemoCntn + sFieldName + " : " + sOldFieldVal + " >> " + sNewFieldVal + ", ";	
                        		}
                        	}
                        }
                        
                        hmParam.put("cons_typ1_cd", "CT01060000");
                    	hmParam.put("cons_typ2_cd", "CT01060200");
                    	hmParam.put("cons_typ3_cd", "CT01060201");
                    	hmParam.put("cons_memo_cntn", sConsMemoCntn);
                        
                		DlwEvntReceiptService.updateEventBasicInfo(hmParam);
                		DlwEvntReceiptService.insertMemConsList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	hmParam.put("cons_typ1_cd", "CT01060000");
                    	hmParam.put("cons_typ2_cd", "CT01060200");
                    	hmParam.put("cons_typ3_cd", "CT01060201");
                    	hmParam.put("cons_memo_cntn", "행사현황상세 신규 등록");
                    	
                    	DlwEvntReceiptService.insertEventBasicInfo(hmParam);
                    	DlwEvntReceiptService.insertMemConsList(hmParam);
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                }
                else if(sSvcId.equals("tap_supplyItem"))
                {
                	Map mDataRow = null;
                	String sEventSeq = (String)mapInVar.get("seq");

                    for (int idx = 0; idx < listInDs.size(); idx++) 
                    {
                    	mDataRow = listInDs.get(idx);
                        ParamUtils.addSaveParam(mDataRow);

                        mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                        mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                        mDataRow.put("evt_seq", sEventSeq);
                        
                        int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                        {
                        	DlwEvntReceiptService.updateSuppliesClsfcDtl(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                        {
                        	int returnVal = DlwEvntReceiptService.confirmRegFunrlOutsrcMst(mDataRow);
                        	
                        	if(returnVal <= 0)
                        	{
                        		DlwEvntReceiptService.insertRegFunrlOutsrcMst(mDataRow);
                        	}
                        	
                        	DlwEvntReceiptService.insertSuppliesClsfcDtl(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                        {
                        	DlwEvntReceiptService.deleteSuppliesClsfcDtl(mDataRow);
                        }
                        else
                        {
                        	
                        }
                    }
                }
                else if(sSvcId.equals("tap_addSales1"))
                {
                	Map mDataRow = null;
                	String sEventSeq = (String)mapInVar.get("seq");

                    for (int idx = 0; idx < listInDs.size(); idx++) 
                    {
                    	mDataRow = listInDs.get(idx);
                        ParamUtils.addSaveParam(mDataRow);

                        mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                        mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                        mDataRow.put("evt_seq", sEventSeq);
                        
                        int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                        {
                        	DlwEvntReceiptService.updateAddSales1(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                        {
                        	int returnVal = DlwEvntReceiptService.confirmRegFunrlOutsrcMst(mDataRow);
                        	
                        	if(returnVal <= 0)
                        	{
                        		DlwEvntReceiptService.insertRegFunrlOutsrcMst(mDataRow);
                        	}
                        	
                        	DlwEvntReceiptService.insertAddSales1(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                        {
                        	DlwEvntReceiptService.deleteAddSales1(mDataRow);
                        }
                        else
                        {
                        	
                        }
                    }
                }
                else if(sSvcId.equals("tap_addSales2"))
                {
                	Map mDataRow = null;
                	String sEventSeq = (String)mapInVar.get("seq");

                    for (int idx = 0; idx < listInDs.size(); idx++) 
                    {
                    	mDataRow = listInDs.get(idx);
                        ParamUtils.addSaveParam(mDataRow);

                        mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                        mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                        mDataRow.put("evt_seq", sEventSeq);
                        
                        int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                        {
                        	DlwEvntReceiptService.updateAddSales2(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                        {
                        	int returnVal = DlwEvntReceiptService.confirmRegFunrlOutsrcMst(mDataRow);
                        	
                        	if(returnVal <= 0)
                        	{
                        		DlwEvntReceiptService.insertRegFunrlOutsrcMst(mDataRow);
                        	}
                        	
                        	DlwEvntReceiptService.insertAddSales2(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                        {
                        	DlwEvntReceiptService.deleteAddSales2(mDataRow);
                        }
                        else
                        {
                        	
                        }
                    }
                }
                else if(sSvcId.equals("tap_paymentInfo"))
                {
                	Map mDataRow = null;
                	String sEventSeq = (String)mapInVar.get("seq");

                    for (int idx = 0; idx < listInDs.size(); idx++) 
                    {
                    	mDataRow = listInDs.get(idx);
                        ParamUtils.addSaveParam(mDataRow);

                        mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                        mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                        mDataRow.put("evt_seq", sEventSeq);
                        
                        int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                        {
                        	DlwEvntReceiptService.updatePaymentInfo(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                        {
                        	int returnVal = DlwEvntReceiptService.confirmRegFunrlOutsrcMst(mDataRow);
                        	
                        	if(returnVal <= 0)
                        	{
                        		DlwEvntReceiptService.insertRegFunrlOutsrcMst(mDataRow);
                        	}
                        	
                        	DlwEvntReceiptService.insertPaymentInfo(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                        {
                        	DlwEvntReceiptService.deletePaymentInfo(mDataRow);
                        }
                        else
                        {
                        	
                        }
                    }
                }
                else if(sSvcId.equals("tap_eventAlter"))
                {
                	
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
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
    
    @RequestMapping(value = "/saveCancelEvent")
    public ModelAndView saveCancelEvent(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs.size() > 0) 
            {
                Map<String, Object> hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("cons_typ1_cd", "CT01060000");
            	hmParam.put("cons_typ2_cd", "CT01060200");
            	hmParam.put("cons_typ3_cd", "CT01060201");
            	hmParam.put("cons_memo_cntn", "행사 취소 등록");
            	
                DlwEvntReceiptService.updateCancelEvent(hmParam);
        		DlwEvntReceiptService.insertMemConsList(hmParam);
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
    
    
    @RequestMapping(value = "/getHistoryTabList")
    public ModelAndView getHistoryTabList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapMemConsList = new DataSetMap();
        DataSetMap listMapProtocolMngrHist = new DataSetMap();
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
            String sHistoryPageId = (String) mapInVar.get("history_page_id");
            
            if (listInDs.size() > 0) 
            {
                hmParam = listInDs.get(0);
            	
                /* 탭 제 2 영역 */
                if(sHistoryPageId.equals("tap_consHist"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getMemConsList(hmParam);
                    listMapMemConsList.setRowMaps(mList);
                    mapOutDs.put("ds_outputMemConsList", listMapMemConsList);
                }
                else if(sHistoryPageId.equals("tap_protocolMngrHist"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getProtocolMngrHistList(hmParam);
                	listMapProtocolMngrHist.setRowMaps(mList);
                	mapOutDs.put("ds_outputProtocolMngrHistList", listMapProtocolMngrHist);
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
    
    @RequestMapping(value = "/savetHistoryTabList")
    public ModelAndView saveIvrMgmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String sSaveType = (String) mapInVar.get("save_type");
            String sSvcId = (String) mapInVar.get("svc_id");
            
            if (listInDs.size() > 0) 
            {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                if(sSvcId.equals("tap_consHist"))
                {                    
                	DlwEvntReceiptService.insertMemConsList(hmParam);	
                }
                else if(sSvcId.equals("tap_protocolMngrHist"))
                {
                	/*
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateScriptMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	dlwIvrMgmtService.insertScriptMgmtExtList(hmParam);	
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                    */
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
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
    
    @RequestMapping(value = "/getExtInfoTabList")
    public ModelAndView getExtInfoTabList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapEventMngrList = new DataSetMap();
        DataSetMap listMapMngrPayList = new DataSetMap();
        DataSetMap listMapMemberAccntDtlList = new DataSetMap();
        DataSetMap listMapPayTotList = new DataSetMap();
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
            String sExtInfoPageId = (String) mapInVar.get("ext_info_page_id");
            
            if (listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);

            	if(sExtInfoPageId.equals("tap_eventMngrInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getEventMngrList(hmParam);
                    listMapEventMngrList.setRowMaps(mList);
                    mapOutDs.put("ds_outputEventMngrList", listMapEventMngrList);
                    
                    List<Map<String, Object>> mList1 = DlwEvntReceiptService.getMngrPayList(hmParam);
                    listMapMngrPayList.setRowMaps(mList1);
                    mapOutDs.put("ds_outputMngrPayList", listMapMngrPayList);
                }
                else if(sExtInfoPageId.equals("tap_memAccntInfo"))
                {
                	List<Map<String, Object>> mList = DlwEvntReceiptService.getMemberAccntDtlList(hmParam);
                    listMapMemberAccntDtlList.setRowMaps(mList);
                    mapOutDs.put("ds_outputMemberAccntDtlList", listMapMemberAccntDtlList);
                    
                    List<Map<String, Object>> mList1 = DlwEvntReceiptService.getPayTotList(hmParam);
                    listMapPayTotList.setRowMaps(mList1);
                    mapOutDs.put("ds_outputPayTotList", listMapPayTotList);
                }
                else if(sExtInfoPageId.equals("tap_etcInfo"))
                {
                	
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회 분류입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
    
    @RequestMapping(value = "/saveExtInfoTabList")
    public ModelAndView saveExtInfoTabList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String sSaveType = (String) mapInVar.get("save_type");
            String sSvcId = (String) mapInVar.get("svc_id");
            
            if (listInDs.size() > 0) 
            { 
            	Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
            	
                if(sSvcId.equals("tap_eventMngrInfo"))
                {
                	Map mDataRow = null;
                    String sEventSeq = (String)mapInVar.get("seq");

                    for (int idx = 0; idx < listInDs.size(); idx++) 
                    {
                    	mDataRow = listInDs.get(idx);
                        ParamUtils.addSaveParam(mDataRow);

                        mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                        mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                        mDataRow.put("evt_seq", sEventSeq);
                        
                        int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                        {
                        	DlwEvntReceiptService.updateEventMngrInfo(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                        {
                        	DlwEvntReceiptService.insertEventMngrInfo(mDataRow);
                        }
                        else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                        {
                        	DlwEvntReceiptService.deleteEventMngrInfo(mDataRow);
                        }
                        else
                        {
                        	
                        }
                    }

                }
                else if(sSvcId.equals("tap_memAccntInfo"))
                {
                	/*
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateScriptMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	dlwIvrMgmtService.insertScriptMgmtExtList(hmParam);	
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                    */
                }
                else if(sSvcId.equals("tap_etcInfo"))
                {
                	
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
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
    
    @RequestMapping(value = "/getProtocolMngrPhoto")
    public void getEvenPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try 
        {
            String sFileCode = CommonUtils.nvls(request.getParameter("fileCode"));
            String sBasePath = "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0)
            {
                sBasePath = EgovProperties.getProperty("life.protocolevent.photo.windows.basePath");
            }
            else
            {
                sBasePath = EgovProperties.getProperty("life.protocolevent.photo.unix.basePath");
            }

            String srcFilePath = sBasePath + "/" + sFileCode.substring(0, 6) + "/" + sFileCode;
            //String srcFilePath = "C:/" + sFileCode.substring(0, 6) + "/" + sFileCode;

            System.out.println("사진 탐색 path :: " + srcFilePath);
            if (osName.indexOf("windows") >= 0)
            {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists())
            {
                System.out.println("디스크에 실제 파일이 존재하지 않습니다.");
                //throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            if (contentType == null)
            {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                {
                    response.setContentType("doesn/matter;");
                }
                else
                {
                    response.setContentType("application/octet-stream");
                }
            }
            else
            {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            if (xDownFile.exists())
            {
                byte b[] = new byte[1024 * 4];
                BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
                BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

                try
                {
                    int read = 0;
                    while ((read = fin.read(b)) != -1)
                    {
                        outs.write(b,0,read);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (outs != null)
                    {
                        outs.close();
                    }
                    if (fin != null)
                    {
                        fin.close();
                    }
                }

                outs.flush();
                outs.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getCustAccntList")
    public ModelAndView getCustAccntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

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

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String sExcelFg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(sExcelFg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwEvntReceiptService.getCustAccntCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getCustAccntList(hmParam);
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
    
    @RequestMapping(value = "/getProtocolSynthesisList")
    public ModelAndView getProtocolSynthesisList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

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

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String sExcelFg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(sExcelFg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwEvntReceiptService.getProtocolSynthesisCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwEvntReceiptService.getProtocolSynthesisList(hmParam);
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
    
    @RequestMapping(value = "/getBranchList")
    public ModelAndView getBranchList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
                
                List<Map<String, Object>> mList = DlwEvntReceiptService.getBranchList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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
}