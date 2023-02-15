/*
 * (@)# ExtnNoAdmnController.java
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.usr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.usr.service.ExtnNoAdmnService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 그룹 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExtnNoAdmn
 */
@Controller
@RequestMapping(value = "/user/extn-no-admn")
public class ExtnNoAdmnController {

    @Resource
    private ExtnNoAdmnService extnNoAdmnService;

    /**
     * 아이피 및 내선번호 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExtnNoList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

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

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addPagingParam(hmParam);

            int nTotal = extnNoAdmnService.getExtnNoCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            List<Map<String, Object>> mList = extnNoAdmnService.getExtnNoList(hmParam);
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
    /*
    @RequestMapping(value = "/list")
    public ModelAndView getExtnNoList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = extnNoAdmnService.getExtnNoCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = extnNoAdmnService.getExtnNoList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 아이피 및 내선번호 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveExtnNo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam = listInDs.get(0);
            }

            // 사용하는 경우 중복 체크
            String sExtnNoAdmnId = StringUtils.defaultString((String) hmParam.get("extn_no_admn_id"));
            String sCntrCd = StringUtils.defaultString((String) hmParam.get("cntr_cd"));
            String sUseYn = StringUtils.defaultString((String) hmParam.get("use_yn"));
            if ("Y".equals(sUseYn)) {
                // 1. user_id 체크
                String strUser_id = StringUtils.defaultString((String) hmParam.get("user_id"));
                //String sOldIpAddr = StringUtils.defaultString((String) hmParam.get("old_ip_addr"));
                if (!strUser_id.equals("")) {
                    Map<String, String> mSearchParam = new HashMap<String, String>();
                    mSearchParam.put("cntr_cd", sCntrCd);
                    mSearchParam.put("use_yn",    sUseYn);
                    mSearchParam.put("user_id",   strUser_id);
                    if(sExtnNoAdmnId != null){
                        mSearchParam.put("extn_no_admn_id",   sExtnNoAdmnId);
                    }
                    int total = extnNoAdmnService.getExtnNoCount(mSearchParam);
                    if (total > 0) {
                        szErrorCode ="-1";
                        szErrorMsg = "상담원 ID가 중복되었습니다.";

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                        return modelAndView;
                    }
                }

                // 2. 내선번호 중복 체크
                String sTlphExtnNo = StringUtils.defaultString((String) hmParam.get("tlph_extn_no"));
                String sOldTlphExtnNo = StringUtils.defaultString((String) hmParam.get("old_tlph_extn_no"));
                if (!sOldTlphExtnNo.equals(sTlphExtnNo)) {
                    Map<String, String> mSearchParam = new HashMap<String, String>();
                    mSearchParam.put("cntr_cd", sCntrCd);
                    mSearchParam.put("use_yn",    sUseYn);
                    mSearchParam.put("tlph_extn_no",       sTlphExtnNo);
                    if(sExtnNoAdmnId != null){
                        mSearchParam.put("extn_no_admn_id",   sExtnNoAdmnId);
                    }
                    int nTotal = extnNoAdmnService.getExtnNoCount(mSearchParam);
                    if (nTotal > 0) {
                        szErrorCode ="-1";
                        szErrorMsg = "내선번호가 중복되었습니다.";

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                        return modelAndView;
                    }
                }
        }

        // IP/내선정보 저장
        ParamUtils.addSaveParam(hmParam);

        if (hmParam.get("page_indx") == null) {
            sExtnNoAdmnId = extnNoAdmnService.insertExtnNo(hmParam);
        } else {
            extnNoAdmnService.updateExtnNo(hmParam);
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
    @RequestMapping(value = "/save")
    public ModelAndView saveExtnNo(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 사용하는 경우 중복 체크
        String sCntrCd = StringUtils.defaultString((String) pmParam.get("cntr_cd"));
        String sUseYn = StringUtils.defaultString((String) pmParam.get("use_yn"));
        if ("Y".equals(sUseYn)) {
            // 1. IP 중복 체크
            String sIpAddr = StringUtils.defaultString((String) pmParam.get("ip_addr"));
            String sOldIpAddr = StringUtils.defaultString((String) pmParam.get("old_ip_addr"));
            if (!sOldIpAddr.equals(sIpAddr)) {
                Map<String, String> mSearchParam = new HashMap<String, String>();
                mSearchParam.put("cntr_cd", sCntrCd);
                mSearchParam.put("use_yn",    sUseYn);
                mSearchParam.put("ip_addr",   sIpAddr);
                int total = extnNoAdmnService.getExtnNoCount(mSearchParam);
                if (total > 0) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg("IP Address가 중복되었습니다.");

                    modelAndView.addObject(oResult);
                    return modelAndView;
                }
            }

            // 2. 내선번호 중복 체크
            String sTlphExtnNo = StringUtils.defaultString((String) pmParam.get("tlph_extn_no"));
            String sOldTlphExtnNo = StringUtils.defaultString((String) pmParam.get("old_tlph_extn_no"));
            if (!sOldTlphExtnNo.equals(sTlphExtnNo)) {
                Map<String, String> mSearchParam = new HashMap<String, String>();
                mSearchParam.put("cntr_cd", sCntrCd);
                mSearchParam.put("use_yn",    sUseYn);
                mSearchParam.put("tlph_extn_no",       sTlphExtnNo);
                int nTotal = extnNoAdmnService.getExtnNoCount(mSearchParam);
                if (nTotal > 0) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg("내선번호가 중복되었습니다.");

                    modelAndView.addObject(oResult);
                    return modelAndView;
                }
            }
        }

        // IP/내선정보 저장
        ParamUtils.addSaveParam(pmParam);

        String sExtnNoAdmnId = StringUtils.defaultString((String) pmParam.get("extn_no_admn_id"));
        if (pmParam.get("page_indx") == null) {
            sExtnNoAdmnId = extnNoAdmnService.insertExtnNo(pmParam);
        } else {
            extnNoAdmnService.updateExtnNo(pmParam);
        }
        oResult.setData(extnNoAdmnService.getExtnNo(sExtnNoAdmnId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
     */
    /**
     * 아이피 및 내선번호 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExtnNo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                hmParam = listInDs.get(0);
            }

        String sSelectCheck = StringUtils.defaultString((String) hmParam.get("selectcheck"));
        hmParam.put("selectcheck", sSelectCheck.split(","));

        extnNoAdmnService.deleteExtnNo(hmParam);

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
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExtnNo(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", sSelectCheck.split(","));

        extnNoAdmnService.deleteExtnNo(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
}