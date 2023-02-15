/*
 * (@)# TeamController.java
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

package powerservice.business.usr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.usr.service.TeamService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 그룹 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Team
 */
@Controller
@RequestMapping(value = "/user/team")
public class TeamController {

    @Resource
    private TeamService teamService;

    /**
     * 그룹을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getTeamList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            //int nTotal = teamService.getTeamCount(hmParam);
            List<Map<String, Object>> mList = teamService.getTeamList(hmParam);
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
    public ModelAndView getTeamList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = teamService.getTeamCount(pmParam);
        List<Map<String, Object>> mList = teamService.getTeamList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 그룹 트리 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tree")
    public ModelAndView getTeamTree(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mList = teamService.getTeamTree(hmParam);
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
    public ModelAndView getTeamTree(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = teamService.getTeamTree(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 그룹 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-order")
    public ModelAndView getTeamMaxOrder(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(teamService.getTeamMaxOrder(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 그룹 순서 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getTeamDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);

        int nTotal = teamService.getTeamDuplicateCount(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 그룹 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveTeam(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addSaveParam(hmParam);

            Map<String, Object> searchParam = new HashMap<String, Object>();
            searchParam.put("team_cd", hmParam.get("team_cd"));
            searchParam.put("cntr_cd", hmParam.get("cntr_cd"));

            if (hmParam.get("page_indx") == null) {    // 페이지번호가 없으면 등록, 있으면 수정
                int cnt = teamService.getTeamCount(searchParam);

                if (cnt > 0) {
                    szErrorCode ="-1";
                    szErrorMsg = "동일한 그룹 코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }
                teamService.insertTeam(hmParam);
            } else {
                teamService.updateTeam(hmParam);
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
    public ModelAndView saveTeam(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("team_cd", pmParam.get("team_cd"));
        searchParam.put("cntr_cd", pmParam.get("cntr_cd"));

        if (pmParam.get("page_indx") == null) {    // 페이지번호가 없으면 등록, 있으면 수정
            int cnt = teamService.getTeamCount(searchParam);

            if (cnt > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 그룹 코드가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
            teamService.insertTeam(pmParam);
        } else {
            teamService.updateTeam(pmParam);
        }

        oResult.setData(teamService.getTeam(searchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 그룹 콤보박스 정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/drop-down-list")
    public ModelAndView searchCselTypeForDropDownList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("hgrn_team_cd",mapInVar.get("hgrn_team_cd"));
            hmParam.put("use_yn",mapInVar.get("use_yn"));
            hmParam.put("team_all_flag",mapInVar.get("team_all_flag"));

            ParamUtils.addCenterParam(hmParam);


           // Map<String, Object> searchParam = ParamUtils.convertDropDownParam(hmParam);

            hmParam.put("orderBy", "team_sqnc");
            hmParam.put("orderDirection", "asc");

            int nTotal = teamService.getTeamCount(hmParam);
            List<Map<String, Object>> mList = teamService.getTeamList(hmParam);

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
    }

}