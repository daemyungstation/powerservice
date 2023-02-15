/*
 * (@)# CdSetController.java
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
import powerservice.business.sys.service.CdSetService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 서버 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CdSet
 */
@Controller
@RequestMapping(value = "/syst/cd-set")
public class CdSetController {

    @Resource
    private CdSetService cdSetService;

    /**
     * 코드셋 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView searchCdSet(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
            String Test344 = String.valueOf(hmParam.get("type_cd"));

            System.out.println("====>" + Test344);
            //int nTotal = cdSetService.getCdSetCount(pmParam);

            ParamUtils.addPagingParam(hmParam);
            List<Map<String, Object>> mList = cdSetService.getCdSetList(hmParam);
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
    public ModelAndView searchCdSet(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = cdSetService.getCdSetCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = cdSetService.getCdSetList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 코드셋 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/com_save")
    public ModelAndView Com_save(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCdSetCd = StringUtils.defaultString((String) hmParam.get("cd_set_cd"));

            ParamUtils.addSaveParam(hmParam);

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("cd_set_cd_full", sCdSetCd);

            if (oPageIndx == null) {
                // 신규등록일때 코드셋코드 중복체크
                int nCount = cdSetService.getComCdSetCount(mSearchParam);
                if (nCount > 0 ) {
                    szErrorCode ="-1";
                    szErrorMsg = "동일한 코드셋코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                cdSetService.insertComCdSet(hmParam);
            } else {
                cdSetService.updateComCdSet(hmParam);
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
     * 코드셋 정보를 등록/수정한다.
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
            String sCdSetCd = StringUtils.defaultString((String) hmParam.get("cd_set_cd"));

            ParamUtils.addSaveParam(hmParam);

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("cd_set_cd_full", sCdSetCd);

            if (oPageIndx == null) {
                // 신규등록일때 코드셋코드 중복체크
                int nCount = cdSetService.getCdSetCount(mSearchParam);
                if (nCount > 0 ) {
                    szErrorCode ="-1";
                    szErrorMsg = "동일한 코드셋코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                cdSetService.insertCdSet(hmParam);
            } else {
                cdSetService.updateCdSet(hmParam);
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

        Object oPageIndx = pmParam.get("page_indx");
        String sCdSetCd = StringUtils.defaultString((String) pmParam.get("cd_set_cd"));

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("cd_set_cd_full", sCdSetCd);

        if (oPageIndx == null) {
            // 신규등록일때 코드셋코드 중복체크
            int nCount = cdSetService.getCdSetCount(mSearchParam);
            if (nCount > 0 ) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 코드셋코드가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            cdSetService.insertCdSet(pmParam);
        } else {
            cdSetService.updateCdSet(pmParam);
        }

        oResult.setData(cdSetService.getCdSet(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

}