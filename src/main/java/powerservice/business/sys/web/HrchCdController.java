/*
 * (@)# HrchCdController.java
 *
 * @author 박의준
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

import powerservice.business.sys.service.HrchCdService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 계층코드 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID HrchCd
 */
@Controller
@RequestMapping(value = "/syst/hrch-cd")
public class HrchCdController {

    @Resource
    private HrchCdService hrchCdService;

    /**
     * 계층유형코드를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getHrchCdList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addPagingParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            //int nTotal = hrchCdService.getHrchCdCount(hmParam);
            List<Map<String, Object>> mList = hrchCdService.getHrchCdList(hmParam);
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
    public ModelAndView getHrchCdList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = hrchCdService.getHrchCdCount(pmParam);
        List<Map<String, Object>> mList = hrchCdService.getHrchCdList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 계층유형코드 트리 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tree")
    public ModelAndView getHrchCdTree(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addPagingParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mList = hrchCdService.getHrchCdTree(hmParam);
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
    @RequestMapping(value = "/tree")
    public ModelAndView getHrchCdTree(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = hrchCdService.getHrchCdTree(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 계층유형코드 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-sequence")
    public ModelAndView getHrchCdMaxSequence(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(hrchCdService.getHrchCdMaxSequence(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 계층유형코드 순서 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getHrchCdDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);

        int nTotal = hrchCdService.getHrchCdDuplicateCount(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 계층유형코드 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveHrchCd(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sHrchCd = StringUtils.defaultString((String) hmParam.get("hrch_cd"));
            String sHrchTypCd = StringUtils.defaultString((String) hmParam.get("hrch_typ_cd"));

            ParamUtils.addSaveParam(hmParam);

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("hrch_cd", sHrchCd);
            mSearchParam.put("hrch_typ_cd", sHrchTypCd);

            if (oPageIndx == null) { // 등록
                int nCount = hrchCdService.getHrchCdCount(mSearchParam);
                if (nCount > 0) {
                    szErrorCode ="-1";
                    szErrorMsg = "동일한 계층유형코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                hrchCdService.insertHrchCd(hmParam);
            } else { // 수정
                hrchCdService.updateHrchCd(hmParam);
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
    public ModelAndView saveHrchCd(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String sHrchCd = StringUtils.defaultString((String) pmParam.get("hrch_cd"));
        String sHrchTypCd = StringUtils.defaultString((String) pmParam.get("hrch_typ_cd"));

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("hrch_cd", sHrchCd);
        mSearchParam.put("hrch_typ_cd", sHrchTypCd);

        if (oPageIndx == null) { // 등록
            int nCount = hrchCdService.getHrchCdCount(mSearchParam);
            if (nCount > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 계층유형코드가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            hrchCdService.insertHrchCd(pmParam);
        } else { // 수정
            hrchCdService.updateHrchCd(pmParam);
        }

        oResult.setData(hrchCdService.getHrchCd(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

    /**
     * 계층유형코드 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteHrchCd(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hrchCdService.deleteHrchCd(hmParam);

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
    public ModelAndView deleteHrchCd(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        hrchCdService.deleteHrchCd(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

    /**
     * 계층유형코드 DropDownList 정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = {"/drop-down-list"})
    public ModelAndView getHrchCdListForDropDownList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("hrch_typ_cd",mapInVar.get("hrch_typ_cd"));
            hmParam.put("use_yn","Y");

            hmParam.put("orderBy", "hrch_cd_sqnc");
            hmParam.put("orderDirection", "asc");

            //Map<String, Object> mSearchParam = ParamUtils.convertHrchCdDropDownListParam(pmParam);
            ParamUtils.addCenterParam(hmParam);

            int nTotal = hrchCdService.getHrchCdCount(hmParam);
            List<Map<String, Object>> mList = hrchCdService.getClscdDropDownList(hmParam);

            dsMap.setRowMaps(mList);
            mapOutVar.put("total_count", nTotal);
            mapOutDs.put("datas", dsMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    };

}