/*
 * (@)# SrvrInfoController.java
 *
 * @author 이희철
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

package powerservice.business.sys.web;

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
import powerservice.business.sys.service.SrvrInfoService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.web.security.TokenManager;

/**
 * 서버 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID SrvrInfo
 */
@Controller
@RequestMapping(value = "/syst/srvr-info")
public class SrvrInfoController {

    @Resource
    private SrvrInfoService srvrInfoService;

    /**
     * 서버 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView get(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            if (hmParam.get("combo_yn") == null) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            int nTotal = srvrInfoService.getSrvrInfoCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            ParamUtils.addPagingParam(hmParam);
            List<Map<String, Object>> mList = srvrInfoService.getSrvrInfoList(hmParam);
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
    public ModelAndView get(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = srvrInfoService.getSrvrInfoCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mDataList = srvrInfoService.getSrvrInfoList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mDataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 서버 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Object oPageIndx = hmParam.get("page_indx");
            String sSrvrId = StringUtils.defaultString((String) hmParam.get("srvr_id"));

            ParamUtils.addSaveParam(hmParam);

            Map<String, String> mSearchParam = new HashMap<String, String>();
            mSearchParam.put("srvr_id", sSrvrId);

            if (oPageIndx == null) {
                // 서버ID 중복 확인
                int nCount = srvrInfoService.getSrvrInfoCount(mSearchParam);
                if (nCount > 0 ) {

                    szErrorCode ="-1";
                    szErrorMsg = "동일한 서버ID가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                srvrInfoService.insertSrvrInfo(hmParam);
            } else {
                srvrInfoService.updateSrvrInfo(hmParam);
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
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 중복처리방지 TOKEN 체크
        if (!TokenManager.checkSubmitToken(oResult, pmParam)) {
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        Object oPageIndx = pmParam.get("page_indx");
        String sSrvrId = StringUtils.defaultString((String) pmParam.get("srvr_id"));

        ParamUtils.addSaveParam(pmParam);

        Map<String, String> mSearchParam = new HashMap<String, String>();
        mSearchParam.put("srvr_id", sSrvrId);

        if (oPageIndx == null) {
            // 서버ID 중복 확인
            int nCount = srvrInfoService.getSrvrInfoCount(mSearchParam);
            if (nCount > 0 ) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 서버ID가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            srvrInfoService.insertSrvrInfo(pmParam);
        } else {
            srvrInfoService.updateSrvrInfo(pmParam);
        }

        oResult.setData(srvrInfoService.getSrvrInfo(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 서버 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView delete(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            srvrInfoService.deleteSrvrInfo(hmParam);

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
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 중복처리방지 TOKEN 체크
        if (!TokenManager.checkSubmitToken(oResult, pmParam)) {
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        String sSrvrId = StringUtils.defaultString((String) pmParam.get("srvr_id"));
        if ("".equals(sSrvrId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("서버ID가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        srvrInfoService.deleteSrvrInfo(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

}