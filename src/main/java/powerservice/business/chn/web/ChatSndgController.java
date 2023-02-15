/*
 * (@)# ChatSndgController.java
 *
 * @author 배윤정
 * @version 1.0
 * @date 2013/04/01
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.chn.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.chn.service.ChatSndgHstrService;
import powerservice.business.sys.web.BasVlController;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * MO발송 이력을 관리한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2014/10/06
 * @프로그램ID SmsMsg
 */
@Controller
@RequestMapping(value = "/chnl/chat-sndg")
public class ChatSndgController {

    @Resource
    private ChatSndgHstrService chatSndgHstrService;

    @Resource
    private BasVlController basicValueService;
    /**
     * 문자메세지를 전송한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/unpy/send")
    @ResponseBody
    public ModelAndView sendUnpyMsg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            String sMtPr = chatSndgHstrService.insertSend(hmParam);

            //동보 여부
            String sBroadcastYn = StringUtils.defaultString((String)hmParam.get("broadcast_yn"));

            if("Y".equals(sBroadcastYn)){//동보일때 Detail저장 후 MsgStatus 변경
                String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));

                hmParam.put("sndg_no", sMtPr);
                for(int i=0; i<listInDs2.size(); i++){
                    hmParam2 = listInDs2.get(i);

                    hmParam2.put("mt_pr", sMtPr);
                    hmParam2.put("service_type", sServiceType);

                    String sMtSeq = chatSndgHstrService.insertSendDetail(hmParam2);
                    //동보일때 TB_CHAT_SNDG_HSTR 이력저장
                    String sCustId = StringUtils.defaultString((String)hmParam2.get("cust_id"));
                    String sCustNm = StringUtils.defaultString((String)hmParam2.get("cust_nm"));
                    String sClphNo = StringUtils.defaultString((String)hmParam2.get("clph_no"));

                    hmParam.put("sndg_sqnc", sMtSeq);
                    hmParam.put("mem_no", sCustId);
                    hmParam.put("mem_nm", sCustNm);
                    hmParam.put("recipient_num", sClphNo);

                    chatSndgHstrService.insertChatSndgHstr(hmParam);
                }

                hmParam.put("mt_pr", sMtPr);
                chatSndgHstrService.updateMsgStatus(hmParam);
            }else{//동보아닐때 TB_CHAT_SNDG_HSTR 이력저장
                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrService.insertChatSndgHstr(hmParam);
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 문자메세지를 전송한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/send")
    @ResponseBody
    public ModelAndView sendSingleMsg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            String sMtPr = chatSndgHstrService.insertSend(hmParam);

            //동보 여부
            String sBroadcastYn = StringUtils.defaultString((String)hmParam.get("broadcast_yn"));
            //System.out.println("동보발송여부 == :"+sBroadcastYn);

            if("Y".equals(sBroadcastYn)){//동보일때 Detail저장 후 MsgStatus 변경
                String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));

                hmParam.put("sndg_no", sMtPr);
                for(int i=0; i<listInDs2.size(); i++){
                    hmParam2 = listInDs2.get(i);

                    if("1".equals(hmParam2.get("rowCheck"))){
                        hmParam2.put("mt_pr", sMtPr);
                        hmParam2.put("service_type", sServiceType);

                        String sMtSeq = chatSndgHstrService.insertSendDetail(hmParam2);
                        //동보일때 TB_CHAT_SNDG_HSTR 이력저장
                        String sCustId = StringUtils.defaultString((String)hmParam2.get("cust_id"));
                        String sCustNm = StringUtils.defaultString((String)hmParam2.get("cust_nm"));
                        String sClphNo = StringUtils.defaultString((String)hmParam2.get("clph_no"));
                        hmParam.put("sndg_sqnc", sMtSeq);
                        hmParam.put("mem_no", sCustId);
                        hmParam.put("mem_nm", sCustNm);
                        hmParam.put("recipient_num", sClphNo);
                        chatSndgHstrService.insertChatSndgHstr(hmParam);
                    }
                }

                hmParam.put("mt_pr", sMtPr);
                chatSndgHstrService.updateMsgStatus(hmParam);
            }else{//동보아닐때 TB_CHAT_SNDG_HSTR 이력저장
                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrService.insertChatSndgHstr(hmParam);
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 문자메세지를 전송한다. (시스원 전송)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/send_sysone")
    @ResponseBody
    public ModelAndView sendSysoneSingleMsg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0"; 
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }
            String sMtPr = "";
            ParamUtils.addSaveParam(hmParam); 

            //동보 여부(엑셀 업로드 여부)
            String sBroadcastYn = StringUtils.defaultString((String)hmParam.get("broadcast_yn"));
            //문자길이가 SMS or LMS 구분
            String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));
            
            if("Y".equals(sBroadcastYn)){
                //엑셀 업로드로 문자 발송 프로세스      
            	
            	String sSubject= StringUtils.defaultString((String)hmParam.get("subject"));
            	String sCallback = StringUtils.defaultString((String)hmParam.get("callback"));
            	String sReserveYn = StringUtils.defaultString((String)hmParam.get("reserve_yn"));
            	String sReserveDt = StringUtils.defaultString((String)hmParam.get("reserve_date"));
            	String sContents = StringUtils.defaultString((String)hmParam.get("content"));
            	
            	 for(int i=0; i<listInDs2.size(); i++){
            		 hmParam2 = listInDs2.get(i);
            		 
                     if("1".equals(hmParam2.get("rowCheck"))){
                         hmParam2.put("service_type", sServiceType);
                         hmParam2.put("subject", sSubject);
                         hmParam2.put("callback", sCallback);
                         hmParam2.put("reserve_yn", sReserveYn); 
                         hmParam2.put("reserve_date", sReserveDt); 
                         hmParam2.put("content", sContents);
                         
                         String sClphNo = StringUtils.defaultString((String)hmParam2.get("clph_no"));
                         hmParam2.put("recipient_num", sClphNo); 
                         
                         String sMtSeq = chatSndgHstrService.insertSysoneSendDetail(hmParam2);
                         //동보일때 TB_CHAT_SNDG_HSTR 이력저장
                         String sCustId = StringUtils.defaultString((String)hmParam2.get("cust_id"));
                         String sCustNm = StringUtils.defaultString((String)hmParam2.get("cust_nm"));

                         hmParam.put("sndg_sqnc", sMtSeq);
                         hmParam.put("mem_no", sCustId);
                         hmParam.put("mem_nm", sCustNm);
                         hmParam.put("recipient_num", sClphNo);
                         chatSndgHstrService.insertChatSndgHstr(hmParam);
                     }
            	 }
            } else {
                //단건 문자 발송 프로세스            	
                sMtPr = chatSndgHstrService.insertSysoneSmsSend(hmParam);        	 
                chatSndgHstrService.insertChatSndgHstr(hmParam);
            }
            
            System.out.println("동보발송여부 == :"+sBroadcastYn);
            System.out.println("전송SEQ == :"+sMtPr);
            System.out.println("전송sServiceType == :"+sServiceType);
            
            /*
            if("Y".equals(sBroadcastYn)){//동보일때 Detail저장 후 MsgStatus 변경
                String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));

                hmParam.put("sndg_no", sMtPr);
                for(int i=0; i<listInDs2.size(); i++){
                    hmParam2 = listInDs2.get(i);

                    if("1".equals(hmParam2.get("rowCheck"))){
                        hmParam2.put("mt_pr", sMtPr);
                        hmParam2.put("service_type", sServiceType);

                        String sMtSeq = chatSndgHstrService.insertSendDetail(hmParam2);
                        //동보일때 TB_CHAT_SNDG_HSTR 이력저장
                        String sCustId = StringUtils.defaultString((String)hmParam2.get("cust_id"));
                        String sCustNm = StringUtils.defaultString((String)hmParam2.get("cust_nm"));
                        String sClphNo = StringUtils.defaultString((String)hmParam2.get("clph_no"));
                        hmParam.put("sndg_sqnc", sMtSeq);
                        hmParam.put("mem_no", sCustId);
                        hmParam.put("mem_nm", sCustNm);
                        hmParam.put("recipient_num", sClphNo);
                        chatSndgHstrService.insertChatSndgHstr(hmParam);
                    }
                }

                hmParam.put("mt_pr", sMtPr);
                chatSndgHstrService.updateMsgStatus(hmParam);
            }else{//동보아닐때 TB_CHAT_SNDG_HSTR 이력저장
                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrService.insertChatSndgHstr(hmParam);
            }
            */

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 문자메세지를 전송한다. (가상계좌)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vrtl-send")
    @ResponseBody
    public ModelAndView sendMsgVrtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                for (int i = 0; i < listInDs.size(); i++) {
                     hmParam = listInDs.get(i);

                     ParamUtils.addSaveParam(hmParam);

                     hmParam.put("recipient_num", hmParam.get("clph_no"));
                     String sContent = (String)hmParam.get("content");
                     hmParam.put("content", sContent.replaceAll("\r", "\r\n"));

                     String sMtPr = chatSndgHstrService.insertSend(hmParam);

                     hmParam.put("sndg_no", sMtPr);
                     hmParam.put("sndg_sqnc", 0);
                     chatSndgHstrService.insertChatSndgHstr(hmParam);

                }
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /*
    *//**
     * 문자메세지를 전송한다.(공통 팝업).
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     *//*
    @RequestMapping(value = "/send")
    @ResponseBody
    public ActionResult sendChatMsg(@RequestBody Map<String, Object> pmParam) throws Exception {
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        String sChatSndgHstrId = chatSndgHstrService.insertChatSndgHstr(pmParam);

        oResult.setData(chatSndgHstrService.getChatSndgHstr(sChatSndgHstrId));
        return oResult;
    }

    *//**
     * 메세지 일괄발송 엑셀 정보를 읽는다.
    *
    * @param param
    * @param response
    * @throws Exception
    *//*
    @RequestMapping(value = "/read-sms-send-all")
    public ModelAndView readSmsSendAllExcel(@RequestParam Map<String, Object> param, @RequestParam("files") List<MultipartFile> files) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/excel-upload-response");

        // Ajax 오류 처리자 설정
        ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        oServletRequestAttribute.getRequest().setAttribute("bAjaxExceptionHandler", "true");

        List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();

        MultipartFile file = files.get(0);
        int totalcnt = 0;
        String errMsg = "";

        if (file != null && !file.isEmpty()) {
            List<String> colList = new ArrayList<String>();
            int colIndex = 0;
            colList.add(colIndex++, "cselprocno");
            colList.add(colIndex++, "cust_id");
            colList.add(colIndex++, "cust_nm");
            colList.add(colIndex++, "clph_no");

            ExcelImporter importer = new ExcelImporter(colList, excelList); // 엑셀추출테이블

            importer.setHanlder(new ExcelImportRowHandler() {
                @Override
                public boolean handleRow(Map<String, Object> row, int rowNum) {
                    boolean savefg = true;
                    String resultMsg = "";

                    if (row.toString().equals("{}"))
                        return false;

                    resultMsg += ExcelValidator.validate(row, "clph_no", ExcelValidator.FILTER_REQUIRE | ExcelValidator.FILTER_PHONE);
                    row.put("page_indx", (rowNum));

                    if (!resultMsg.equals("")) {
                        savefg = false;
                        row.put("errMsg", "rownum:" + (rowNum) + ", errorMsg:" + resultMsg);
                    }

                    return savefg;
                }
            });

            totalcnt = importer.process(file.getInputStream());
            errMsg = importer.getErrMsg();

        }
        modelAndView.addObject("result", excelList);
        modelAndView.addObject("totalcnt", totalcnt);
        modelAndView.addObject("errMsg", errMsg);

        return modelAndView;
    }

    *//**
     * 메세지를 일괄전송한다.
     *
     * @param param Map<String, String>
     * @return ActionResult
     * @throws Exception
     *//*
    @RequestMapping(value = "/send-all")
    @ResponseBody
    public ActionResult saveSendAllSms(@RequestBody Map<String, Object> param) throws Exception {
        ActionResult result = new ActionResult();

        String[] targetlist = StringUtils.defaultString((String) param.get("selectcheck")).split(",");
        String[] customernmlist = StringUtils.defaultString((String) param.get("cust_nm")).split(",");
        String[] cselprocnolist = StringUtils.defaultString((String) param.get("cselprocno")).split(",");
        String[] hpnolist = StringUtils.defaultString((String) param.get("clph_no")).split(",");
        String smssendmsg = StringUtils.defaultString((String)param.get("chat_msg_cntn"));
        String centercd = StringUtils.defaultString((String)param.get("cntr_cd"));
        String messagesendtypecd = StringUtils.defaultString((String)param.get("chat_sndg_div_cd"));
        String senderid = StringUtils.defaultString((String)param.get("sndg_chpr_id"));
        String centerrstvno = StringUtils.defaultString((String)param.get("cntR_rprs_tlno"));
        String smsmsgwithtag = StringUtils.defaultString((String)param.get("chat_msg_cntn_tag"));
        String reservefg = StringUtils.defaultString((String)param.get("resr_yn"));
        String reservedt = StringUtils.defaultString((String)param.get("resr_dttm"));

        @SuppressWarnings("unchecked")
        List<String> fileIds = (List<String>) param.get("fileIds");

        if (targetlist != null) {
            for (int i=0; i<targetlist.length; i++) {
                String customerid = targetlist[i];
                String customernm = customernmlist[i];
                String cselprocno = cselprocnolist[i];
                String hpno = hpnolist[i];

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("cmpg_id", "1");
                map.put("cust_id", customerid);
                map.put("cust_nm", customernm);
                map.put("cselprocno", cselprocno);
                map.put("clph_no", hpno);
                map.put("chat_msg_cntn", smssendmsg);
                map.put("fileIds", fileIds);
                map.put("cntr_cd", centercd);
                map.put("chat_sndg_div_cd", messagesendtypecd);
                map.put("sndg_chpr_id", senderid);
                map.put("cntR_rprs_tlno", centerrstvno);
                map.put("chat_msg_cntn_tag", smsmsgwithtag);
                map.put("resr_yn", reservefg);
                map.put("resr_dttm", reservedt);

                sendChatMsg(map);
            }
        }

        return result;
    }
    */


    /**
     * 문자메세지를 전송한다.(cj올리브네트웍스)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newsend")
    @ResponseBody
    public ModelAndView sendnewSingleMsg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            //일괄 문자 발송
            String sBroadcastYn = StringUtils.defaultString((String)hmParam.get("broadcast_yn"));
            //System.out.println("동보발송여부 == :"+sBroadcastYn);

            if("Y".equals(sBroadcastYn)){//일괄 문자 발송
                String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));

                chatSndgHstrService.insertnewallsend(listInDs,listInDs2,sServiceType);

            }else{// 건별 문자 발송 후  TB_CHAT_SNDG_HSTR_NEW 이력저장

            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("status","1");
                String sMtPr = chatSndgHstrService.insertnewSend(hmParam);

                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrService.insertChatnewSndgHstr(hmParam);
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 문자메세지를 전송한다. (가상계좌)(cj올리브네트웍스)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vrtl-newsend")
    @ResponseBody
    public ModelAndView newsendMsgVrtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                for (int i = 0; i < listInDs.size(); i++) {
                     hmParam = listInDs.get(i);

                     ParamUtils.addSaveParam(hmParam);

                     hmParam.put("recipient_num", hmParam.get("clph_no"));
                     String sContent = (String)hmParam.get("content");
                     hmParam.put("content", sContent.replaceAll("\r", "\r\n"));

                     hmParam.put("status","1");
                     String sMtPr = chatSndgHstrService.insertnewSend(hmParam);

                     hmParam.put("sndg_no", sMtPr);
                     hmParam.put("sndg_sqnc", 0);
                     chatSndgHstrService.insertChatnewSndgHstr(hmParam);

                }
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * cj문자메세지를 전송한다.(캠페인문자발송..)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/unpy/newsend")
    @ResponseBody
    public ModelAndView newsendUnpyMsg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            //일괄 문자 발송
            String sBroadcastYn = StringUtils.defaultString((String)hmParam.get("broadcast_yn"));
            //System.out.println("동보발송여부 == :"+sBroadcastYn);

            if("Y".equals(sBroadcastYn)){//일괄 문자 발송
                String sServiceType = StringUtils.defaultString((String)hmParam.get("service_type"));
                System.out.println("aaaa");

                chatSndgHstrService.insertnewallsend(listInDs,listInDs2,sServiceType);

            }else{// 건별 문자 발송 후  TB_CHAT_SNDG_HSTR_NEW 이력저장

            	System.out.println("bbbbbb");

            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("status","1");
                String sMtPr = chatSndgHstrService.insertnewSend(hmParam);

                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrService.insertChatnewSndgHstr(hmParam);
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자List 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getSmsVrtlSendList")
    @ResponseBody
    public ModelAndView getSmsVrtlSendList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            int nTotal = chatSndgHstrService.getSmsVrtlSendListTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            List<Map<String, Object>> mList = chatSndgHstrService.getSmsVrtlSendList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가전송
     * ================================================================
     * */
    @RequestMapping(value = "/insertVrtlMsSend")
    public ModelAndView insertVrtlMsSend(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsListChatSndgInDs = (DataSetMap)mapInDs.get("ds_input");
            Map<String, Object> rowData = new HashMap<String, Object>();

            for(int idx = 0; idx < dsListChatSndgInDs.size(); idx++)
            {
            	rowData = dsListChatSndgInDs.get(idx);
            	ParamUtils.addSaveParam(rowData);
            	chatSndgHstrService.insertVrtlMsSend(rowData);
            }

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

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자List 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getSmsRealSendList")
    @ResponseBody
    public ModelAndView getSmsRealSendList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            ParamUtils.addSaveParam(hmParam);

            int nTotal = chatSndgHstrService.getSmsRealSendListTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            List<Map<String, Object>> mList = chatSndgHstrService.getSmsRealSendList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송
     * ================================================================
     * */
    @RequestMapping(value = "/insertRealMsSend")
    public ModelAndView insertRealMsSend(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsListChatSndgTemplate = (DataSetMap)mapInDs.get("ds_input");

            if (dsListChatSndgTemplate != null && dsListChatSndgTemplate.size() > 0)
            {
                hmParam = dsListChatSndgTemplate.get(0);
            }

            String sServiceType = (String)hmParam.get("service_type");
            String sContent     = null;

            if(sServiceType == "0" || sServiceType.equals("0")) // SMS 발송대상
            {
            	chatSndgHstrService.insertRealMsSmsSend(hmParam);
            	chatSndgHstrService.insertChatMultiSndgHstr(hmParam);
                chatSndgHstrService.updateChatMultiSndg(hmParam);
            }
            else if(sServiceType == "3" || sServiceType.equals("3")) // LMS 발송대상
            {
            	chatSndgHstrService.insertRealMsLmsSend(hmParam);
            	chatSndgHstrService.insertChatMultiSndgHstr(hmParam);
                chatSndgHstrService.updateChatMultiSndg(hmParam);
            }
            else if(sServiceType == "4" || sServiceType.equals("4")) // 카카오 발송대상
            {
            	chatSndgHstrService.insertRealMsKaKaoSend(hmParam);
            	chatSndgHstrService.insertChatMultiSndgHstr(hmParam);
                chatSndgHstrService.updateChatMultiSndg(hmParam);
            }

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

    /* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가전송
     * ================================================================
     * */
    @RequestMapping(value = "/sms_excel_upload")
    public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            chatSndgHstrService.uploadToSms(request, response, mResult);

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

    /*
    @RequestMapping(value = "/insertVrtlMsSendLargeCapacity")
    public ModelAndView insertVrtlMsSendLargeCapacity(XPlatformMapDTO xpDto, Model model) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MS_SEND_TEMP(MEM_NO, ACCNT_NO, MEM_SEQ, MEM_NM, CELL, CALLBACK, ";
        sqlStatement += "SUBJECT, CONTENT, PROD_CD, SDP_YN, CI_YN, SEX, AGE, TRUE_CNT, JOIN_DT, MARKT_AGR_YN, ";
        sqlStatement += "VRTL_SEND_STATE, REAL_SEND_STATE, VRTL_SEND_DTTM, REAL_SEND_DTTM, RGSR_ID) ";
        sqlStatement += "VALUES (?, ?, (SELECT NVL(MAX(MEM_SEQ) , 0) + 1 FROM LF_DMUSER.TB_MS_SEND_TEMP WHERE 1=1 AND MEM_NO = ? AND ACCNT_NO = ?), ";
        sqlStatement += "?, ?, '', (SELECT SUBSTR(?, 0, 10) FROM DUAL), ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Y', 'N', SYSDATE, '', ?)";

        try
        {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsListChatSndgInDs = (DataSetMap)mapInDs.get("ds_input");
            Map<String, String> rowData = new HashMap<String, String>();
            UserSessionCore oLoginUser = SessionUtils.getLoginUser();

            // 운영시 세팅 
            String ssDriverClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
            String ssUrl             = EgovProperties.getProperty("Globals.dmlifeway.Url");
            String ssUserName        = EgovProperties.getProperty("Globals.dmlifeway.UserName");
            String ssPassword        = EgovProperties.getProperty("Globals.dmlifeway.Password");

            System.out.println("서버프로퍼티 ::: " + ssDriverClassName + " ::: " + ssUrl + " ::: " + ssUserName + " ::: " + ssPassword);


            //개발시 세팅
            String sDriverClassName = "oracle.jdbc.driver.OracleDriver";

            //String sUrl             = "jdbc:oracle:thin:@61.39.175.227:1521/dmlife"; // "jdbc:oracle:thin:@192.168.10.61:1521:dmlife";
            String sUrl             = "jdbc:oracle:thin:@192.168.10.61:1521/dmlife";
            String sUserName        = "LF_DMUSER";
            String sPassword        = "dmuser123";

            System.out.println("개발프로퍼티 ::: " + sDriverClassName + " ::: " + sUrl + " ::: " + sUserName + " ::: " + sPassword);

            System.out.println("1. 커넥션 드라이버");
            Class.forName(sDriverClassName);
            System.out.println("2. 커넥션의 연결");
            connection = DriverManager.getConnection(sUrl, sUserName, sPassword);
            System.out.println("3. statement 동작");
            //connection = DriverManager.getConnection("jdbc:oracle:thin:@61.39.175.225:1521/dmlife1", "LF_DMUSER", "LF_DMUSER");

            preparedStatement = connection.prepareStatement(sqlStatement) ;

            System.out.println("4. 자료를 넣음.");
            for(int idx = 0; idx < dsListChatSndgInDs.size(); idx++)
            {
            	rowData = dsListChatSndgInDs.get(idx);
            	rowData.put("rgsr_id", oLoginUser.getUserid());
            	rowData.put("rgsr_nm", oLoginUser.getUsernm());
            	rowData.put("amnd_id", oLoginUser.getUserid());
            	rowData.put("amnd_nm", oLoginUser.getUsernm());
            	rowData.put("cntr_cd", oLoginUser.getCentercd());

                preparedStatement.setString(1, rowData.get("mem_no"));
                preparedStatement.setString(2, rowData.get("accnt_no"));
                preparedStatement.setString(3, rowData.get("mem_no"));
                preparedStatement.setString(4, rowData.get("accnt_no"));
                preparedStatement.setString(5, rowData.get("mem_nm"));
                preparedStatement.setString(6, rowData.get("cell"));
                preparedStatement.setString(7, rowData.get("content"));
                preparedStatement.setString(8, rowData.get("content"));
                preparedStatement.setString(9, rowData.get("prod_cd"));
                preparedStatement.setString(10, rowData.get("sdp_yn"));
                preparedStatement.setString(11, rowData.get("ci_yn"));
                preparedStatement.setString(12, rowData.get("sex"));
                preparedStatement.setString(13, rowData.get("age"));
                preparedStatement.setString(14, rowData.get("true_cnt"));
                preparedStatement.setString(15, rowData.get("join_dt"));
                preparedStatement.setString(16, rowData.get("markt_agr_yn"));
                preparedStatement.setString(17, rowData.get("rgsr_id"));

                // addBatch에 담기
                preparedStatement.addBatch();

                // 파라미터 Clear
                preparedStatement.clearParameters() ;


                // OutOfMemory를 고려하여 만건 단위로 커밋
                if( (idx % 10000) == 0){

                    // Batch 실행
                	preparedStatement.executeBatch() ;

                    // Batch 초기화
                	preparedStatement.clearBatch();

                    // 커밋
                	connection.commit() ;

                }
            }


            // 커밋되지 못한 나머지 구문에 대하여 커밋
            preparedStatement.executeBatch() ;
            connection.commit() ;

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

        }
        catch(Exception e)
        {
            try
            {
            	if(connection != null)
            	{
            		connection.rollback() ;
            	}
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }
        }finally{
            if (preparedStatement != null)
            {
            	try
            	{
            		preparedStatement.close();
            		preparedStatement = null;
            	}
            	catch(SQLException ex)
            	{

            	}
            }
            if (connection != null)
            {
            	try
            	{
            		connection.close();
            		connection = null;
            	}
            	catch(SQLException ex)
            	{

            	}
            }
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    */
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 조회
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    @RequestMapping(value = "/getMsgExtSched")
    public ModelAndView getMsgExtSched(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

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

                List<Map<String, Object>> mList = chatSndgHstrService.getMsgExtSched(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    @RequestMapping(value = "/insertMsgExtSched")
    public ModelAndView insertMsgExtSched(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int chkCnt = chatSndgHstrService.chkMsgExtSched(hmParam);

            if(chkCnt == 0)
            {
            	chatSndgHstrService.insertMsgExtSched(hmParam);
            }
            else if(chkCnt > 0)
            {
                mapOutVar.put("xSaveReturnMsg", "해당 전송일은 이미 등록되어 있습니다.");  // 실패 메시지 return
            }

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


    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 삭제
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    @RequestMapping(value = "/deleteMsgExtSched")
    public ModelAndView delGongjeExtSched(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            chatSndgHstrService.deleteMsgExtSched(hmParam);

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

}