/*
 * (@)# RoleUserController.java
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

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.sys.service.RoleUserService;
import powerservice.business.usr.service.UserService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 역할 사용자 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID RoleUser
 */
@Controller
@RequestMapping(value = "/syst/role-user")
public class RoleUserController {

    @Resource
    private RoleUserService roleUserService;

    @Resource
    private UserService userService;

    /**
     * 역할 사용자 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView searchRoleUser(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            //int total = roleUserService.getRoleUserCount(pmParam);

            ParamUtils.addPagingParam(hmParam);
            List<Map<String, Object>> mList = roleUserService.getRoleUserList(hmParam);
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
    public ModelAndView searchRoleUser(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int total = roleUserService.getRoleUserCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = roleUserService.getRoleUserList(pmParam);

        oData.setTotal(total);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 역할 사용자를 저장한다.
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

            String sRoleCd = StringUtils.defaultString((String) hmParam.get("role_cd"));
            if ("".equals(sRoleCd)) {
                szErrorCode ="-1";
                szErrorMsg = "역할코드가 없습니다.";

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            String sRoleTrgtTypCdOld = StringUtils.defaultString((String) hmParam.get("role_trgt_typ_cd"));
            String sRoleTrgtIdOld = StringUtils.defaultString((String) hmParam.get("role_trgt_id"));
            String sRoleTrgtTypCd = null;
            String sRoleTrgtId = null;

            String sTeam1Cd = StringUtils.defaultString((String) hmParam.get("team1_cd"));
            String sTeam2Cd = StringUtils.defaultString((String) hmParam.get("team2_cd"));
            String sUserId = StringUtils.defaultString((String) hmParam.get("user_id"));

            if (!"".equals(sUserId)) {
                sRoleTrgtTypCd = "10";
                sRoleTrgtId = sUserId;
            } else if (!"".equals(sTeam2Cd)) {
                sRoleTrgtTypCd = "20";
                sRoleTrgtId = sTeam2Cd;
            } else if (!"".equals(sTeam1Cd)) {
                sRoleTrgtTypCd = "20";
                sRoleTrgtId = sTeam1Cd;
            } else {
                szErrorCode ="-1";
                szErrorMsg = "최소한 팀은 선택하세요.";

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;

            }

            ParamUtils.addSaveParam(hmParam);

            hmParam.put("role_trgt_typ_cd", sRoleTrgtTypCd);
            hmParam.put("role_trgt_id", sRoleTrgtId);
            hmParam.put("role_trgt_typ_cd_old", sRoleTrgtTypCdOld);
            hmParam.put("role_trgt_id_old", sRoleTrgtIdOld);

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("role_cd", sRoleCd);
            mSearchParam.put("role_trgt_typ_cd", sRoleTrgtTypCd);
            mSearchParam.put("role_trgt_id", sRoleTrgtId);

            if ("".equals(sRoleTrgtIdOld)) { // 등록
                // 중복 확인
                int nCount = roleUserService.getRoleUserCount(mSearchParam);
                if (nCount > 0 ) {
                    szErrorCode ="-1";
                    szErrorMsg = "동일한 역할코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;

                }

                roleUserService.insertRoleUser(hmParam);
            } else { // 수정
                roleUserService.updateRoleUser(hmParam);
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

        String sRoleCd = StringUtils.defaultString((String) pmParam.get("role_cd"));
        if ("".equals(sRoleCd)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("역할코드가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        String sRoleTrgtTypCdOld = StringUtils.defaultString((String) pmParam.get("role_trgt_typ_cd"));
        String sRoleTrgtIdOld = StringUtils.defaultString((String) pmParam.get("role_trgt_id"));
        String sRoleTrgtTypCd = null;
        String sRoleTrgtId = null;

        String sTeam1Cd = StringUtils.defaultString((String) pmParam.get("team1_cd"));
        String sTeam2Cd = StringUtils.defaultString((String) pmParam.get("team2_cd"));
        String sUserId = StringUtils.defaultString((String) pmParam.get("user_id"));

        if (!"".equals(sUserId)) {
            sRoleTrgtTypCd = "10";
            sRoleTrgtId = sUserId;
        } else if (!"".equals(sTeam2Cd)) {
            sRoleTrgtTypCd = "20";
            sRoleTrgtId = sTeam2Cd;
        } else if (!"".equals(sTeam1Cd)) {
            sRoleTrgtTypCd = "20";
            sRoleTrgtId = sTeam1Cd;
        } else {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("최소한 팀은 선택하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        ParamUtils.addSaveParam(pmParam);

        pmParam.put("role_trgt_typ_cd", sRoleTrgtTypCd);
        pmParam.put("role_trgt_id", sRoleTrgtId);
        pmParam.put("role_trgt_typ_cd_old", sRoleTrgtTypCdOld);
        pmParam.put("role_trgt_id_old", sRoleTrgtIdOld);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("role_cd", sRoleCd);
        mSearchParam.put("role_trgt_typ_cd", sRoleTrgtTypCd);
        mSearchParam.put("role_trgt_id", sRoleTrgtId);

        if ("".equals(sRoleTrgtIdOld)) { // 등록
            // 중복 확인
            int nCount = roleUserService.getRoleUserCount(mSearchParam);
            if (nCount > 0 ) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 역할코드가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            roleUserService.insertRoleUser(pmParam);
        } else { // 수정
            roleUserService.updateRoleUser(pmParam);
        }

        oResult.setData(roleUserService.getRoleUser(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/

    /**
     * 역할 사용자를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteRoleUser(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            roleUserService.deleteRoleUser(hmParam);

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
    public ModelAndView deleteRoleUser(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        roleUserService.deleteRoleUser(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 사용자 DropDownList 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = {"/drop-down-list"})
    public ModelAndView getUserListForDropDownList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        Map<String, Object> mSearchParam = ParamUtils.convertDropDownParam(pmParam);

        List<Map<String, Object>> mList = userService.getUserList(mSearchParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


}