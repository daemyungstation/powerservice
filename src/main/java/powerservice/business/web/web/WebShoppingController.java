/*
 * (@)# WebConsController.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.web.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.bean.UserSession;
import powerservice.business.web.service.WebShoppingService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/20
 * @프로그램ID WebCons
 */

@Controller
@RequestMapping(value="/web/shopping")
public class WebShoppingController {

    @Resource
    private WebShoppingService webShoppingService ;

    @Autowired
    private ServletContext ctx;

    /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. START */

    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getMemberBasicList")
    public ModelAndView getMemberBasicList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
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

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList01 = webShoppingService.getMemberBasicList(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output01", listMap01);

            List<Map<String, Object>> mList02 = webShoppingService.getMemberBasicListUseAmt(hmParam);
            listMap02.setRowMaps(mList02);
            mapOutDs.put("ds_output02", listMap02);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
     * ================================================================
     * */
    @RequestMapping(value = "/insertMemberBasicAmt")
    public ModelAndView insertMemberBasicAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            hmParam.put("sMem_no", strMemberNo);
            hmParam.put("sAccnt_no", sAccountNo);
            hmParam.put("nSend_amt", sSendAmt);

            ParamUtils.addSaveParam(hmParam);

            System.out.println("123456" + hmParam.toString());
            webShoppingService.insertMemberBasicAmt(hmParam);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 신규회원 전송
     * ================================================================
     * */
    @RequestMapping(value = "/insertMemberBasicNew")
    public ModelAndView insertMemberBasicNew(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("P_MEM_NO", strMemberNo);
            hmParam.put("P_ACCNT_NO", sAccountNo);
            hmParam.put("P_STATE", "0");
            hmParam.put("P_CREATE_ID", oUser.getUserid());
            System.out.println("데이터 넘어오나???" + hmParam);

            webShoppingService.updateResnMemberState(hmParam);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부/행사여부 확인
     * ================================================================
     * */
    @RequestMapping(value = "/getResnMemberState")
    public ModelAndView getResnMemberState(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("P_MEM_NO", strMemberNo);
            hmParam.put("P_ACCNT_NO", sAccountNo);
            hmParam.put("P_STATE", "0");
            System.out.println("데이터 넘어오나???" + hmParam);

            List<Map<String, Object>> mList01 = webShoppingService.getResnMemberStateEvent(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output01", listMap01);

            List<Map<String, Object>> mList02 = webShoppingService.getResnMemberStateRescission(hmParam);
            listMap02.setRowMaps(mList02);
            mapOutDs.put("ds_output02", listMap02);

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

    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
    @RequestMapping(value = "/updateResnMemberState")
    public ModelAndView updateResnMemberState(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                hmParam.put("P_STATE", hmParam.get("state"));
                hmParam.put("P_CREATE_ID", oUser.getUserid());
                System.out.println("데이터 넘어오나???" + hmParam);
                int count = webShoppingService.updateResnMemberState(hmParam);

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

    /* ================================================================
     * 날짜 : 20171222
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금정보 전체 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getMemberBasicAllAmtInfomation")
    public ModelAndView getMemberBasicAllAmtInfomation(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
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

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            int nTotal = webShoppingService.getTotalCount(hmParam);
            mapOutVar.put("tc_TotalRows", nTotal);

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
                //{ROW_TYPE=0, pageNo=1, endNum=101, startNum=1, reqCount=100}
            }

            List<Map<String, Object>> mList01 = webShoppingService.getMemberBasicAllAmtInfomation(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output01", listMap01);

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
     * 날짜 : 20220418
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰집계
     * ================================================================
     * */
    @RequestMapping(value = "/getReadyCashTotalList")
    public ModelAndView getReadyCashTotalList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String strSttMonth = (String)mapInVar.get("stt_month");

            hmParam.put("stt_month", strSttMonth);
            
            List<Map<String, Object>> mList01 = webShoppingService.getReadyCashTotalList(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output01", listMap01);
            
            List<Map<String, Object>> mList02 = webShoppingService.getReadyCashList(hmParam);
            listMap02.setRowMaps(mList02);
            mapOutDs.put("ds_output02", listMap02);

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


    /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. END */

}