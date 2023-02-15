/*
 * (@)# AcsController.java
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sys.service.AcsService;
import powerservice.core.access.AccessValidator;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 프로그램 접근관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
 * @프로그램ID Acs
 */
@Controller
@RequestMapping(value = "/syst/acs")
public class AcsController {

    @Autowired
    private AccessValidator accesseValidator;

    @Resource
    private AcsService acsService;

    /**
     * 프로그램 접근 IP 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getAcsList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            int nTotal = acsService.getAcsCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            ParamUtils.addPagingParam(hmParam);
            List<Map<String, Object>> mList = acsService.getAcsList(hmParam);
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
    public ModelAndView getAcsList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = acsService.getAcsCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = acsService.getAcsList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 프로그램 접근 IP 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveAcs(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCntrCd = StringUtils.defaultString((String) hmParam.get("cntr_cd"));
            String sUseYn = StringUtils.defaultString((String) hmParam.get("use_yn"));
            if ("Y".equals(sUseYn)) {
                // 1. IP 중복 체크
                String sIpAddr = StringUtils.defaultString((String) hmParam.get("prms_ip_addr"));
                String sOldIpAddr = StringUtils.defaultString((String) hmParam.get("old_prms_ip_addr"));
                if (!sOldIpAddr.equals(sIpAddr)) {
                    Map<String, String> mSearchParam = new HashMap<String, String>();
                    mSearchParam.put("cntr_cd", sCntrCd);
                    mSearchParam.put("use_yn",    sUseYn);
                    mSearchParam.put("prms_ip_addr",   sIpAddr);
                    int total = acsService.getAcsDuplicateCount(mSearchParam);
                    if (total > 0) {
                        szErrorCode ="-1";
                        szErrorMsg = "IP Address가 중복되었습니다.";

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                        return modelAndView;
                    }
                }

            }

            // IP 저장
            ParamUtils.addSaveParam(hmParam);

            String sAcsAdmnId = StringUtils.defaultString((String) hmParam.get("prms_ip_addr"));

            if (hmParam.get("page_indx") == null) {
                acsService.insertAcs(hmParam);
            } else {
                acsService.updateAcs(hmParam);
            }

            // 접근 IP 리스트 메모리 로드
            accesseValidator.loadPgmAcsIpList(true);
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
    public ModelAndView saveAcs(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 사용하는 경우 중복 체크
        String sCntrCd = StringUtils.defaultString((String) pmParam.get("cntr_cd"));
        String sUseYn = StringUtils.defaultString((String) pmParam.get("use_yn"));
        if ("Y".equals(sUseYn)) {
            // 1. IP 중복 체크
            String sIpAddr = StringUtils.defaultString((String) pmParam.get("prms_ip_addr"));
            String sOldIpAddr = StringUtils.defaultString((String) pmParam.get("old_prms_ip_addr"));
            if (!sOldIpAddr.equals(sIpAddr)) {
                Map<String, String> mSearchParam = new HashMap<String, String>();
                mSearchParam.put("cntr_cd", sCntrCd);
                mSearchParam.put("use_yn",    sUseYn);
                mSearchParam.put("prms_ip_addr",   sIpAddr);
                int total = acsService.getAcsDuplicateCount(mSearchParam);
                if (total > 0) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg("IP Address가 중복되었습니다.");

                    modelAndView.addObject(oResult);
                    return modelAndView;
                }
            }

        }

        // IP 저장
        ParamUtils.addSaveParam(pmParam);

        String sAcsAdmnId = StringUtils.defaultString((String) pmParam.get("prms_ip_addr"));

        if (pmParam.get("page_indx") == null) {
            acsService.insertAcs(pmParam);
        } else {
            acsService.updateAcs(pmParam);
        }
        oResult.setData(acsService.getAcs(sAcsAdmnId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

    /**
     * 프로그램 접근 IP 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteAcs(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            acsService.deleteAcs(hmParam);

            // 접근 IP 리스트 메모리 로드
            accesseValidator.loadPgmAcsIpList(true);
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
    public ModelAndView deleteAcs(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", sSelectCheck.split(","));

        acsService.deleteAcs(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

}