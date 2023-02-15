/*
 * (@)# UserController.java
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

package powerservice.business.usr.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.sch.service.DllzDtlService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.usr.service.ExtnNoAdmnService;
import powerservice.business.usr.service.LgnHstrService;
import powerservice.business.usr.service.SessInfoService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.Constants;
import powerservice.common.util.DateUtil;
import powerservice.common.util.ExcelUtils;
import powerservice.common.util.converter.ajax.JSONUtil;
import powerservice.core.bean.ActionResult;
import powerservice.core.license.LicenseState;
import powerservice.core.license.LicenseValidator;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import powerservice.core.util.crypto.SHA256;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 사용자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID User
 */

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LicenseValidator licenseValidator;

    @Autowired
    private ServletContext ctx;

    @Resource
    private UserService userService;

    @Resource
    private DllzDtlService dllzDtlService;

    @Resource
    private LgnHstrService lgnHstrService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private SessInfoService sessInfoService;

    @Resource
    private ExtnNoAdmnService extnNoAdmnService;


    /**
     * 사용자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/list")
    public ModelAndView getUserList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //조회조건
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

            ParamUtils.addCenterParam(hmParam);

            int nTotal = userService.getUserCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = userService.getUserList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
     * 사용자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/newtypelist")
    public ModelAndView getNewTypeUserList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //조회조건
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

            ParamUtils.addCenterParam(hmParam);

            int nTotal = userService.getUserCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = userService.getNewTypeUserList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
     * 사용자 상태정보를 저장한다.
     *
     * @param psOperType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chnl/chnl-stat-info/save")
    public ModelAndView saveCalbDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sChnlTypCd = StringUtils.defaultString((String) mapInVar.get("chnl_typ_cd"));
            String sChnlStatCd = StringUtils.defaultString((String) mapInVar.get("chnl_stat_cd"));

            hmParam.put("chnl_typ_cd", sChnlTypCd);
            hmParam.put("chnl_stat_cd", sChnlStatCd);

            ParamUtils.addSaveParam(hmParam);
            userService.mergeChnlStatInfo(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
     * 사용자 상태 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/status-list")
    public ModelAndView getUserStatusList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //조회조건
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

            ParamUtils.addCenterParam(hmParam);

            int nTotal = userService.getUserCount(hmParam);
            mapOutVar.put("tc_user", nTotal);

            List<Map<String, Object>> mList = userService.getUserStatusList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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

    /**
     * 사용자 DropDownList 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = {"/user/user/drop-down-list"})
    public ModelAndView getUserListForDropDownList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //Map<String, Object> mSearchParam = ParamUtils.convertDropDownParam(pmParam);

            Map<String, Object> mSearchParam= new HashMap<String, Object>() ;
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                mSearchParam = srchInDs.get(0);

                //mSearchParam.put("hgrn_team_cd",mapInVar.get("hgrn_team_cd"));
                //mSearchParam.put("authoritycds",mapInVar.get("authoritycds"));
                ParamUtils.addCenterParam(mSearchParam);
                //System.out.println("mapInVar.toString() => " + mapInVar.get("authoritycds").toString());

                int nTotal = userService.getUserCount(mSearchParam);
                List<Map<String, Object>> mList = userService.getUserList(mSearchParam);


                dsMap.setRowMaps(mList);
                mapOutVar.put("total_count", nTotal);
                mapOutDs.put("datas", dsMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
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
     * 사용자상세 정보(1건)를 검색한다.
     *
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mypage/user/view")
    public ModelAndView getUser(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap dsMap = new DataSetMap();

        //에러코드및 메시지
        String m_ErrorCode="0";
        String m_ErrorMsg="OK";

        try {
            Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();

            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("user_id", oUserSession.getUserid());

            dsMap.setRowMaps(userService.getUser(mSearchParam));
            outDataset.put("ds_output", dsMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();

            m_ErrorCode = "-1";
            m_ErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, m_ErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, m_ErrorMsg);

        return modelAndView;
    }

    /**
     * 사용자상세 정보(1건)를 검색한다
     *
     * @param Map<String, Object> 사용자아이디
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/view")
    public ModelAndView getUser(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();

        ParamUtils.addCenterParam(pmParam);

        String sUserId = StringUtils.defaultString((String) pmParam.get("user_id"));

        if (!"".equals(sUserId)) {
            pmParam.put("user_id", pmParam.get("user_id"));
        } else {
            pmParam.put("user_id", oUserSession.getUserid());
        }

        oResult.setData(userService.getUser(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 사용자ID 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/duplicate-count")
    public ModelAndView getLoginIdIdDuplicateCount(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            String sLgnId = StringUtils.defaultString((String) hmParam.get("lgn_id"));

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("lgn_id", sLgnId);

            int nTotal = userService.getUserCount(mSearchParam);
            mapOutVar.put("check_cnt", nTotal);

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
    @RequestMapping(value = "/user/user/duplicate-count")
    public ModelAndView getLoginIdIdDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sLgnId = StringUtils.defaultString((String) pmParam.get("lgn_id"));

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("lgn_id", sLgnId);

        int nTotal = userService.getUserCount(mSearchParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 사용자 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = {"/user/user/save", "/mypage/user/save"})
    public ModelAndView saveUser(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

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

                String sUserId = StringUtils.defaultString((String) hmParam.get("user_id"));
                String sBit = "";

                ParamUtils.addSaveParam(hmParam);
                /*
                // 담당업무
                @SuppressWarnings("unchecked")
                List<String> sJobTypeCdList = (List<String>) hmParam.get("jobtypecdlist");
                String sJobTypeCd = "";

                if (sJobTypeCdList != null && sJobTypeCdList.size() > 0) {
                    for (int i = 0; i < sJobTypeCdList.size(); i++) {
                        sJobTypeCd += sJobTypeCdList.get(i);

                        if (i < sJobTypeCdList.size() - 1) {
                            sJobTypeCd += ",";
                        }
                    }
                }
                hmParam.put("cons_bswr_typ_cd", sJobTypeCd);
                 */
                if ("".equals(sUserId)) {
                    // 사용자 신규등록시에는 초기비밀번호로 생성함
                    String sLoginPw = basVlService.getBasVlAsString("init_password", (String) hmParam.get("cntr_cd"));
                    hmParam.put("pwd", SHA256.digest(sLoginPw));
                    hmParam.put("sbit", 'N');
                    sUserId = userService.insertUser(hmParam);
                } else {
                    hmParam.put("sbit", 'U');
                    userService.updateUser(hmParam);
                }

                Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
                mSearchParam.put("user_id", sUserId);

                listMap.setRowMaps(userService.getUser(mSearchParam));
                mapOutDs.put("ds_output", listMap);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
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
    @RequestMapping(value = {"/user/user/save", "/mypage/user/save"})
    public ModelAndView saveUser(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        String sUserId = StringUtils.defaultString((String) pmParam.get("user_id"));

        ParamUtils.addSaveParam(pmParam);

        // 담당업무
        @SuppressWarnings("unchecked")
        List<String> sJobTypeCdList = (List<String>) pmParam.get("jobtypecdlist");
        String sJobTypeCd = "";

        if (sJobTypeCdList != null && sJobTypeCdList.size() > 0) {
            for (int i = 0; i < sJobTypeCdList.size(); i++) {
                sJobTypeCd += sJobTypeCdList.get(i);

                if (i < sJobTypeCdList.size() - 1) {
                    sJobTypeCd += ",";
                }
            }
        }
        pmParam.put("cons_bswr_typ_cd", sJobTypeCd);

        if ("".equals(sUserId)) {
            // 사용자 신규등록시에는 초기비밀번호로 생성함
            String sLoginPw = basVlService.getBasVlAsString("init_password", (String) pmParam.get("cntr_cd"));
            pmParam.put("pwd", SHA256.digest(sLoginPw));

            sUserId = userService.insertUser(pmParam);
        } else {
            userService.updateUser(pmParam);
        }

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("user_id", sUserId);

        oResult.setData(userService.getUser(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 비밀번호를 변경한다
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/password/{pageType}")
    public ModelAndView updateUserPassword(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathType) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        //에러코드및 메시지
        String m_ErrorCode="0";
        String m_ErrorMsg="OK";

        try {
            Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();

            List<Map<String, Object>> lsInput = inDataset.get("ds_input").getRowMaps();
            if (lsInput != null && lsInput.size() > 0) {
                Map<String, Object> pmParam = lsInput.get(0);

                if ("init".equals(psPathType)) { // 비밀번호 초기화
                    String sInitPassword = basVlService.getBasVlAsString("init_password", (String) pmParam.get("cntr_cd"));
                    pmParam.put("pwd", SHA256.digest(sInitPassword));
                    pmParam.put("initfg", "Y");
                    pmParam.put("athn_falr_tmcnt", null);
                } else { // 비밀번호 변경
                    String sLgnId = StringUtils.defaultString((String) pmParam.get("lgn_id"));
                    String sCntrCd = StringUtils.defaultString((String) pmParam.get("cntr_cd"));

                    Map<String, Object> mSearchParam = new HashMap<String, Object>();
                    if ("".equals(sCntrCd)) {
                        mSearchParam = ParamUtils.getCenterParam();
                    } else {
                        mSearchParam.put("cntr_cd", sCntrCd);
                    }
                    mSearchParam.put("lgn_id", sLgnId.toUpperCase());

                    // 아이디 검사
                    int nCount = userService.getUserCount(mSearchParam);
                    if (nCount == 0) {
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, Constants.NOT_EXIST_USERID);

                        return modelAndView;
                    }

                    // 인증실패횟수 검사
                    List<Map<String, Object>> mUserList = (List<Map<String, Object>>) userService.getUserList(mSearchParam);
                    if (mUserList == null || mUserList.size() == 0) {
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, Constants.NOT_EXIST_USERID);

                        return modelAndView;
                    } else {
                        Map<String, Object> mUser = mUserList.get(0);

                        int nAthnFalrTmcnt = Integer.parseInt((String.valueOf(mUser.get("athn_falr_tmcnt"))));
                        int nLoginLockCnt = Integer.parseInt(basVlService.getBasVlAsString("login_lock_cnt", (String) mSearchParam.get("cntr_cd")));

                        if (nAthnFalrTmcnt >= nLoginLockCnt) {
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, nLoginLockCnt + "회 비밀번호 오류로 계정이 잠금설정되었습니다.");

                            return modelAndView;
                        }

                        // 비밀번호 검사
                        String sPwd = StringUtils.defaultString((String) pmParam.get("pwd"));
                        mSearchParam.put("pwd", SHA256.digest(sPwd));

                        nCount = userService.getUserCount(mSearchParam);

                        if (nCount == 0) {
                            m_ErrorCode = "-1";
                            m_ErrorMsg = "비밀번호 " + (nAthnFalrTmcnt + 1) + "회 오류입니다.\n" + nLoginLockCnt + "회 오류시 계정이 잠금설정됩니다.";

                            pmParam.put("athn_falr_tmcnt", (nAthnFalrTmcnt + 1));
                            pmParam.put("pwd", null);
                        } else {
                        	//String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{10,20}$";
                        	String pattern1 = "^.*(?=^.{10,20}$)(?=.*[A-Z])(?=.*[!@#$%^*?+=-]).*$";
                        	
                        	boolean regex = Pattern.matches(pattern1, (String)pmParam.get("pwd_change1"));
                        	
                        	if(regex == false) {
                        		modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "영대문자(A-Z),특수문자(!@#$%^*?+=-)를 포함하여 10~20자리로 입력하세요.");

                                return modelAndView;
                        	}                        	
                        	
                            pmParam.put("athn_falr_tmcnt", null);
                            pmParam.put("pwd", SHA256.digest((String)pmParam.get("pwd_change1")));
                        }

                        pmParam.put("cntr_cd", mUser.get("cntr_cd"));
                        pmParam.put("user_id", mUser.get("user_id"));
                    }
                }

                userService.updateUserPassword(pmParam);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
            } else {
                m_ErrorCode = "-1";
                m_ErrorMsg = "사용자 정보가 없습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();

            m_ErrorCode = "-1";
            m_ErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, m_ErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, m_ErrorMsg);

        return modelAndView;
    }
    /*@RequestMapping(value = "/user/user/password/{pageType}")
    public ModelAndView updateUserPassword(@PathVariable("pageType") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if ("init".equals(psPathType)) { // 비밀번호 초기화
            String sInitPassword = basVlService.getBasVlAsString("init_password", (String) pmParam.get("cntr_cd"));
            pmParam.put("pwd", SHA256.digest(sInitPassword));
            pmParam.put("initfg", "Y");
            pmParam.put("athn_falr_tmcnt", null);
        } else { // 비밀번호 변경
            String sLgnId = StringUtils.defaultString((String) pmParam.get("lgn_id"));
            String sCntrCd = StringUtils.defaultString((String) pmParam.get("cntr_cd"));

            Map<String, Object> mSearchParam = new HashMap<String, Object>();
            if ("".equals(sCntrCd)) {
                mSearchParam = ParamUtils.getCenterParam();
            } else {
                mSearchParam.put("cntr_cd", sCntrCd);
            }
            mSearchParam.put("lgn_id", sLgnId.toUpperCase());

            // 아이디 검사
            int nCount = userService.getUserCount(mSearchParam);
            if (nCount == 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg(Constants.NOT_EXIST_USERID);

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            // 인증실패횟수 검사
            List<Map<String, Object>> mUserList = (List<Map<String, Object>>) userService.getUserList(mSearchParam);
            if (mUserList == null || mUserList.size() == 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg(Constants.NOT_EXIST_USERID);

                modelAndView.addObject(oResult);
                return modelAndView;
            } else {
                Map<String, Object> mUser = mUserList.get(0);

                int nAthnFalrTmcnt = Integer.parseInt((String.valueOf(mUser.get("athn_falr_tmcnt"))));
                int nLoginLockCnt = Integer.parseInt(basVlService.getBasVlAsString("login_lock_cnt", (String) mSearchParam.get("cntr_cd")));

                if (nAthnFalrTmcnt >= nLoginLockCnt) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg(nLoginLockCnt + "회 비밀번호 오류로 계정이 잠금설정되었습니다.");

                    modelAndView.addObject(oResult);
                    return modelAndView;
                }

                // 비밀번호 검사
                String sPwd = StringUtils.defaultString((String) pmParam.get("pwd"));
                mSearchParam.put("pwd", SHA256.digest(sPwd));

                nCount = userService.getUserCount(mSearchParam);
                if (nCount == 0) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg("비밀번호 " + (nAthnFalrTmcnt + 1) + "회 오류입니다.\n" + nLoginLockCnt + "회 오류시 계정이 잠금설정됩니다.");

                    pmParam.put("athn_falr_tmcnt", (nAthnFalrTmcnt + 1));
                    pmParam.put("pwd", null);
                } else {
                    pmParam.put("athn_falr_tmcnt", null);
                    pmParam.put("pwd", SHA256.digest((String)pmParam.get("pwd_change1")));
                }

                pmParam.put("cntr_cd", mUser.get("cntr_cd"));
                pmParam.put("user_id", mUser.get("user_id"));
            }
        }

        userService.updateUserPassword(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }*/


    /**
     * 로그인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap dsMap = new DataSetMap();

        //에러코드및 메시지
        String m_ErrorCode="0";
        String m_ErrorMsg="OK";

        try {
            Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();

            UserSession oUserSession = new UserSession();

            List<Map<String, Object>> lsInput = inDataset.get("ds_input").getRowMaps();
            if (lsInput != null && lsInput.size() > 0) {
                Map<String, Object> mParam = lsInput.get(0);

                String sLgnid = StringUtils.defaultString((String) mParam.get("lgn_id"));
                String sPwd = StringUtils.defaultString((String) mParam.get("pwd"));
                String sCntrCd = StringUtils.defaultString((String) mParam.get("cntr_cd"));
                String sGdsCd = StringUtils.defaultString((String) mParam.get("gds_cd"));
                
                ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                String sIpAddr = oServletRequestAttribute.getRequest().getRemoteAddr();

                mParam.put("lgn_id", sLgnid.toUpperCase());

                // 00. 라이센스 검사
                LOGGER.info("isExpiredLicense [" + licenseValidator.isExpiredLicense() + "]");
                LicenseState oLicenseStatusCode = licenseValidator.getLicenseState();
                if (oLicenseStatusCode == LicenseState.EXPIRED) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스 유효기간이 만료하였습니다.");

                    return modelAndView;
                } else if (oLicenseStatusCode == LicenseState.NOT_FOUND) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스가 존재하지 않습니다(개발팀에 문의하세요).");

                    return modelAndView;
                } else if (oLicenseStatusCode == LicenseState.INVAID_KEY) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스 키가 올바르지 않습니다.");

                    return modelAndView;
                } else if (oLicenseStatusCode == LicenseState.INVALID_IP) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스 IP가 올바르지 않습니다.");

                    return modelAndView;
                } else if (oLicenseStatusCode == LicenseState.INVALID_SITE) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스 사이트가 올바르지 않습니다.");

                    return modelAndView;
                } else if (oLicenseStatusCode == LicenseState.INVALID_CENTER_COUNT) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스에서 지정한 센터 수를 초과하였습니다.");

                    return modelAndView;
                }
                
                if("".equals(sLgnid)) {
                	modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "사용자아이디를 입력하세요!!");

                    return modelAndView;
                }
                if("".equals(sPwd)) {
                	modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "비밀번호를 입력하세요!!");

                    return modelAndView;
                }


                // 01. 사용자 아이디 존재여부 확인
                mParam.put("pwd", "");
                Map<String, Object> mUser = userService.getUser(mParam);

                if (mUser == null) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "사용자 아이디가 존재하지 않습니다.");

                    return modelAndView;
                }

                String sUserId = StringUtils.defaultString((String) mUser.get("user_id"));

                // 00. 라이센스 사용자수 검사
                int nLicenseUserCount = licenseValidator.getLicenseUserCount();
                int nTodayLoginUserCount = licenseValidator.getTodayLoginUserCount(sUserId);
                LOGGER.info("TodayLoginUserCount [" + nTodayLoginUserCount + "/" + nLicenseUserCount + "]");
                if (!"ADMIN".equals(sUserId) && nTodayLoginUserCount >= nLicenseUserCount) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "라이센스에서 지정한 사용자 수를 초과하였습니다.");

                    return modelAndView;
                }

                // 02. 잠금 확인
                int nConfirmFailCnt = Integer.parseInt((String.valueOf(mUser.get("athn_falr_tmcnt"))));
                int nLoginLockCnt = Integer.parseInt(basVlService.getBasVlAsString("login_lock_cnt", (String) mParam.get("cntr_cd")));

                if (nConfirmFailCnt >= nLoginLockCnt) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, nLoginLockCnt + "회 비밀번호오류로 계정이 잠금설정되었습니다.\n관리자에게 문의하세요.");

                    return modelAndView;
                }

                // 03. 비밀번호 검사
                mParam.put("pwd", SHA256.digest(sPwd));

                mUser = userService.getUser(mParam);

                if (mUser == null) {
                    nConfirmFailCnt = nConfirmFailCnt + 1;
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");

                    if (nConfirmFailCnt >= nLoginLockCnt) {
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, nLoginLockCnt + "회 비밀번호오류로 계정이 잠금설정되었습니다.\n관리자에게 문의하세요.");
                    } else {
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, "비밀번호 " + nConfirmFailCnt + "회 오류입니다.\n" + nLoginLockCnt + "회 오류시 계정이 잠금설정됩니다.");
                    }

                    Map<String, Object> mSaveParam = new HashMap<String, Object>();
                    mSaveParam.put("user_id", sUserId);
                    mSaveParam.put("cntr_cd", mParam.get("cntr_cd"));
                    mSaveParam.put("athn_falr_tmcnt", nConfirmFailCnt);

                    userService.updateUserPassword(mSaveParam);

                    return modelAndView;
                }

                // 04. 임시비밀번호 검사(비밀번호 수정일시가 없으면 임시비밀번호임)
                String sPwdAmntDttm = StringUtils.defaultString((String) mUser.get("pwd_amnt_dttm"));

                if ("".equals(sPwdAmntDttm)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "비밀번호가 초기화 되었습니다. 비밀번호를 변경해 주세요.");

                    return modelAndView;
                }

                // 05. 비밀번호 변경일(기준값 : 90일)이 지난 비밀번호 체크
                String sPwdAmntYn = StringUtils.defaultString((String) mUser.get("pwd_amnt_yn"));

                if ("Y".equals(sPwdAmntYn)) {
                    String sPwCheckDay = basVlService.getBasVlAsString("pw_check_day", (String) mParam.get("cntr_cd"));

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "비밀번호가 " + sPwCheckDay + "일 이상 변경되지 않았습니다.\n비밀번호를 변경해 주세요.");

                    return modelAndView;
                }

                // 06. 사용자 상태 검사
                String sUserStatCd = StringUtils.defaultString((String) mUser.get("user_stat_cd"));

                if ("20".equals(sUserStatCd)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "퇴사한 상담사입니다. 로그인 할 수 없습니다.");
                    return modelAndView;
                } else if ("30".equals(sUserStatCd)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "휴직한 상담사입니다. 로그인 할 수 없습니다.");
                    return modelAndView;
                }

                // 07. 업무권한 검사
                String sAthrGdsCd = StringUtils.defaultString((String) mUser.get("athr_gds_cd"));
                String[] sAthrGdsCdList = sAthrGdsCd.split(",");

                boolean bHasAuthority = false;

                for (String sProductcd: sAthrGdsCdList) {
                    if (sProductcd.equals(sGdsCd)) {
                        bHasAuthority = true;
                        break;
                    }
                }

                if (!bHasAuthority) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "업무 권한이 없습니다. 다른 업무를 선택하고 다시 로그인 하세요.");
                    return modelAndView;
                }
                
                // 권한이 시스템 관리자(AT01)인 경우의 접속 IP검사
                String sAthrCd = StringUtils.defaultString((String) mUser.get("athr_cd"));
                String sSystemAdminAccessIp = StringUtils.defaultString(basVlService.getBasVlAsString("system_admin_access_ip", (String) mUser.get("cntr_cd")));
                
                if(sSystemAdminAccessIp.equals("") == false)
                {
                	if(sAthrCd.equals("AT01") == true)
                    {
                		int iAccessAlowFlag = 0;
                    	String[] arrSystemAdminAccessIpList = sSystemAdminAccessIp.split(",");
                    	
                    	for(int idx = 0; idx < arrSystemAdminAccessIpList.length; idx++)
                    	{
                    		String sSystemAdminAccessAlowIp = arrSystemAdminAccessIpList[idx];
                    		
                    		if(sIpAddr.indexOf(sSystemAdminAccessAlowIp) == 0)
                    		{
                    			iAccessAlowFlag = 1;
                    		}
                    	}
                    	
                    	if(iAccessAlowFlag == 0)
                    	{
                    		modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    		modelAndView.addObject(XPlatformConstant.ERROR_MSG, "시스템관리자가 접근할수 없는 IP대역입니다.");
                    		return modelAndView;
                    	}
                    }
                }

                // 08. 중복로그인 검사
                Map<String, Object> mSsessInfo = sessInfoService.getSessInfo((String)mUser.get("user_id"), sCntrCd);
                if (mSsessInfo != null) {
                    String sDelSessFg = StringUtils.defaultString((String) inVar.get("del_sess_yn"));
                    if (!"Y".equals(sDelSessFg)) {
                        String sSessLinkDttm = StringUtils.defaultString((String) mSsessInfo.get("sess_link_dttm"));
                        if (sSessLinkDttm.length() == 14) {
                            sSessLinkDttm = DateUtil.convertString(sSessLinkDttm, DateUtil.SDF_YYYYMMDDHHMMSS, DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
                        }
                        String sErrorMessage = " 아래 정보로 해당 아이디가 이미 로그인 되어 있습니다."
                                + "\n\n **********************************************************"
                                + "\n 로그인 시간 : " + sSessLinkDttm
                                + "\n 접속 IP : " + StringUtils.defaultString((String) mSsessInfo.get("trml_ip_addr"))
                                + "\n 내선번호 : " + StringUtils.defaultString((String) mSsessInfo.get("extn_no"))
                                + "\n CTI ID : " + StringUtils.defaultString((String) mSsessInfo.get("cti_id"))
                                + "\n **********************************************************"
                                + "\n\n 이전 연결을 끊고 로그인 하시겠습니까?";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, m_ErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, sErrorMessage);
                        return modelAndView;
                    }
                    mSsessInfo = null;
                }

                // 07. 정상 로그인 처리
                oUserSession.setUserid((String) mUser.get("user_id"));
                oUserSession.setUsernm((String) mUser.get("user_nm"));
                oUserSession.setAuthoritycd((String) mUser.get("athr_cd"));
                oUserSession.setEmployeeid((String) mUser.get("empl_no"));
                oUserSession.setExt((String) mUser.get("extn_no"));
                oUserSession.setCtiid((String) mUser.get("cti_id"));
                oUserSession.setAcdgroup((String) mUser.get("acd_grop_no"));
                oUserSession.setPtygroup((String) mUser.get("pty_grop_no"));

                oUserSession.setCtiusefg((String) mUser.get("cti_use_yn"));
                oUserSession.setCentercd((String) mUser.get("cntr_cd"));
                oUserSession.setCenternm((String) mUser.get("cntr_nm"));
                oUserSession.setTeamcd((String) mUser.get("team_cd"));
                oUserSession.setHgrnteamcd((String) mUser.get("hgrn_team_cd"));

                //oUserSession.setMessengerusefg((String) mUser.get("messengerusefg"));
                oUserSession.setRecip((String) mUser.get("rec_ip_addr"));
                oUserSession.setDualmonitorfg((String) mUser.get("dual_mont_yn"));
                //oUserSession.setCenterabusefg((String) mUser.get("centerabusefg"));
                //oUserSession.setCenterabgusefg((String) mUser.get("centerabgusefg"));
                //oUserSession.setLeftctrl((String) mUser.get("leftctrl"));
                //oUserSession.setMainusefg((String) mUser.get("mainusefg"));
                //oUserSession.setChanneltype((String) mUser.get("channeltype"));
                oUserSession.setLoginid((String) mUser.get("lgn_id"));
                //oUserSession.setAutoreadyfg((String) mUser.get("autoreadyfg"));
                //oUserSession.setCtiip((String) mUser.get("cti_ip"));
                //oUserSession.setCtiport((String) mUser.get("ctiport"));
                //oUserSession.setCenterrecip((String) mUser.get("centerrecip"));
                oUserSession.setLogfg((String) mUser.get("actv_log_yn"));
                //oUserSession.setResidentnofg((String) mUser.get("residentnofg"));
                //oUserSession.setAutosavefg((String) mUser.get("autosavefg"));
                //oUserSession.setObcounselfg((String) mUser.get("obcounselfg"));
                //oUserSession.setLoginstatus((String) mUser.get("loginstatus"));
                oUserSession.setJobtypecd((String) mUser.get("cons_bswr_typ_cd"));
                //oUserSession.setJobchnltypecd((String) mUser.get("jobchnltypecd"));
                //oUserSession.setMultiid((String) mUser.get("multiid"));
                //oUserSession.setMultiautofg((String) mUser.get("multiautofg"));
                oUserSession.setTeamnm((String) mUser.get("team_nm"));
                oUserSession.setBzptdiv((String) mUser.get("bzpt_div"));
                oUserSession.setFileid((String) mUser.get("file_id"));
                oUserSession.setRoleList((String) mUser.get("role"));
                oUserSession.setEncodt((String) mUser.get("enco_dt"));
                oUserSession.setSinglemail((String) mUser.get("emil_addr"));
                oUserSession.setLogindt(DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS));
                oUserSession.setProductcd(sGdsCd);

                for (String productcd: sAthrGdsCdList) {
                    if ("1".equals(productcd)) {
                        oUserSession.setHasUserAuth(true);
                    }

                    if ("2".equals(productcd)) {
                        oUserSession.setHasAdminAuth(true);
                    }

                    /**
                     * 변경일자 : 2015-11-11
                     * 변 경 자 : 정      훈
                     * 변경이유 : 제품코드에따른 업무 추가
                     * 추후이슈 : 제품관리와의 연계후 하드코딩 추후 수정
                     * 요 청 자 : 우달식 이사님
                     */

                    if ("3".equals(productcd)) { // 2차상담
                        oUserSession.setHasN2consAdminAuth(true);
                    }

                    if ("4".equals(productcd)) { // 웹상담
                        oUserSession.setHasWebAdminAuth(true);
                    }
                }

                // 사용자 IP로 내선번호 조회
                if ("Y".equals(oUserSession.getCtiusefg())) {
                    Map<String, Object> mExtnNoAdmn = extnNoAdmnService.getExtnNoByIpAddr(sIpAddr);
                	//Map<String, Object> mExtnNoAdmn = extnNoAdmnService.getExtnNoByIpAddr(sUserId);
                    if (mExtnNoAdmn != null) {
                        oUserSession.setExt((String) mExtnNoAdmn.get("tlph_extn_no"));
                        mUser.put("extn_no", (String) mExtnNoAdmn.get("tlph_extn_no"));
                        mExtnNoAdmn = null;
                    }
                }




                // 사용자 세션 생성
                SessionUtils.setLoginUser(oUserSession);

                // 세션정보 테이블 저장
                Map<String, Object> mUpdateParam = ParamUtils.getCenterParam();
                //ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

                mUpdateParam.put("user_id", (String) oUserSession.getUserid());
                mUpdateParam.put("was_sess_cntn", oServletRequestAttribute.getRequest().getSession().getId());
                mUpdateParam.put("trml_ip_addr", sIpAddr);
                mUpdateParam.put("extn_no", oUserSession.getExt());
                mUpdateParam.put("cti_id", oUserSession.getCtiid());
                mUpdateParam.put("lgn_typ_cd", "LOGIN");
                mUpdateParam.put("cntr_cd", oUserSession.getCentercd());

                sessInfoService.insertSessInfo(mUpdateParam);

                // 로그인시 근태 등록
                dllzDtlService.insertDllzDtlForLogin(mUpdateParam);

                // 로그인 이력정보-
                mUpdateParam.put("ip_addr", sIpAddr);

                lgnHstrService.insertLgnHstr(mUpdateParam);

                // 비밀번호 오류 횟수 초기화
                mUpdateParam.put("athn_falr_tmcnt", "0");

                userService.updateUserPassword(mUpdateParam);
                
                // 마지막 로그인 시간 업데이트
                userService.updateLastLoginDttm(mUpdateParam);
                
                dsMap.setRowMaps(mUser);

                // 사용자 세션ID 저장
                ctx.setAttribute("USER-SESSION-ID-" + oUserSession.getLoginid(), oServletRequestAttribute.getRequest().getSession().getId());
            } else {
                m_ErrorCode = "-1";
                m_ErrorMsg = "로그인 사용자 정보가 없습니다.";
            }

            outDataset.put("ds_output", dsMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();

            m_ErrorCode = "-1";
            m_ErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, m_ErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, m_ErrorMsg);

        return modelAndView;
    }
    /*@RequestMapping(value = "/login")
    public ModelAndView login(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sLgnid = StringUtils.defaultString((String) pmParam.get("lgn_id"));
        String sPwd = StringUtils.defaultString((String) pmParam.get("pwd"));
        String sCntrCd = StringUtils.defaultString((String) pmParam.get("cntr_cd"));
        String sGdsCd = StringUtils.defaultString((String) pmParam.get("gds_cd"));    // 제품권한

        String sDelSessFg = StringUtils.defaultString((String) pmParam.get("delSessFg"));

        UserSession oUserSession = new UserSession();

        Map<String, Object> mSearchParam = new HashMap<String, Object>();

        if ("".equals(sCntrCd)) {
            mSearchParam = ParamUtils.getCenterParam();
        } else {
            mSearchParam.put("cntr_cd", sCntrCd);
        }
        mSearchParam.put("lgn_id", sLgnid.toUpperCase());


        // 00. 라이센스 검사
        LOGGER.info("isExpiredLicense [" + licenseValidator.isExpiredLicense() + "]");
        LicenseState oLicenseStatusCode = licenseValidator.getLicenseState();
        if (oLicenseStatusCode == LicenseState.EXPIRED) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스 유효기간이 만료하였습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (oLicenseStatusCode == LicenseState.NOT_FOUND) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스가 존재하지 않습니다(개발팀에 문의하세요).");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (oLicenseStatusCode == LicenseState.INVAID_KEY) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스 키가 올바르지 않습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (oLicenseStatusCode == LicenseState.INVALID_IP) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스 IP가 올바르지 않습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (oLicenseStatusCode == LicenseState.INVALID_SITE) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스 사이트가 올바르지 않습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (oLicenseStatusCode == LicenseState.INVALID_CENTER_COUNT) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스에서 지정한 센터 수를 초과하였습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 01. 사용자 아이디 존재여부 확인
        Map<String, Object> mUser = userService.getUser(mSearchParam);

        if (mUser == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("사용자 아이디가 존재하지 않습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        String sUserId = StringUtils.defaultString((String) mUser.get("user_id"));

        // 00. 라이센스 사용자수 검사
        int nLicenseUserCount = licenseValidator.getLicenseUserCount();
        int nTodayLoginUserCount = licenseValidator.getTodayLoginUserCount(sUserId);
        LOGGER.info("TodayLoginUserCount [" + nTodayLoginUserCount + "/" + nLicenseUserCount + "]");
        if (!"ADMIN".equals(sUserId) && nTodayLoginUserCount >= nLicenseUserCount) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("라이센스에서 지정한 사용자 수를 초과하였습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 02. 잠금 확인
        int nConfirmFailCnt = Integer.parseInt((String.valueOf(mUser.get("athn_falr_tmcnt"))));
        int nLoginLockCnt = Integer.parseInt(basVlService.getBasVlAsString("login_lock_cnt", (String) mSearchParam.get("cntr_cd")));

        if (nConfirmFailCnt >= nLoginLockCnt) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg(nLoginLockCnt + "회 비밀번호오류로 계정이 잠금설정되었습니다.\n관리자에게 문의하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 03. 비밀번호 검사
        mSearchParam.put("pwd", SHA256.digest(sPwd));

        mUser = userService.getUser(mSearchParam);

        if (mUser == null) {
            nConfirmFailCnt = nConfirmFailCnt + 1;
            oResult.setResult(ActionResultType.ERROR);

            if (nConfirmFailCnt >= nLoginLockCnt) {
                oResult.setErrMsg(nLoginLockCnt + "회 비밀번호오류로 계정이 잠금설정되었습니다.\n관리자에게 문의하세요.");
            } else {
                oResult.setErrMsg("비밀번호 " + nConfirmFailCnt + "회 오류입니다.\n" + nLoginLockCnt + "회 오류시 계정이 잠금설정됩니다.");
            }

            Map<String, Object> mSaveParam = new HashMap<String, Object>();
            mSaveParam.put("user_id", sUserId);
            mSaveParam.put("cntr_cd", mSearchParam.get("cntr_cd"));
            mSaveParam.put("athn_falr_tmcnt", nConfirmFailCnt);

            userService.updateUserPassword(mSaveParam);

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 04. 임시비밀번호 검사(비밀번호 수정일시가 없으면 임시비밀번호임)
        String sPwdAmntDttm = StringUtils.defaultString((String) mUser.get("pwd_amnt_dttm"));

        if ("".equals(sPwdAmntDttm)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("비밀번호가 초기화 되었습니다. 비밀번호를 변경해 주세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 05. 비밀번호 변경일(기준값 : 90일)이 지난 비밀번호 체크
        String sPwdAmntYn = StringUtils.defaultString((String) mUser.get("pwd_amnt_yn"));

        if ("Y".equals(sPwdAmntYn)) {
            String sPwCheckDay = basVlService.getBasVlAsString("pw_check_day", (String) mSearchParam.get("cntr_cd"));

            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("비밀번호가 " + sPwCheckDay + "일 이상 변경되지 않았습니다.\n비밀번호를 변경해 주세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 06. 사용자 상태 검사
        String sUserStatCd = StringUtils.defaultString((String) mUser.get("user_stat_cd"));

        if ("20".equals(sUserStatCd)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("퇴사한 상담사입니다. 로그인 할 수 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        } else if ("30".equals(sUserStatCd)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("휴직한 상담사입니다. 로그인 할 수 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 07. 업무권한 검사
        String sAthrGdsCd = StringUtils.defaultString((String) mUser.get("athr_gds_cd"));
        String[] sAthrGdsCdList = sAthrGdsCd.split(",");

        boolean bHasAuthority = false;

        for (String sProductcd: sAthrGdsCdList) {
            if (sProductcd.equals(sGdsCd)) {
                bHasAuthority = true;
                break;
            }
        }

        if (!bHasAuthority) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("업무 권한이 없습니다. 다른 업무를 선택하고 다시 로그인 하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 08. 중복로그인 검사
        Map<String, Object> mSsessInfo = sessInfoService.getSessInfo((String)mUser.get("user_id"), sCntrCd);
        if (mSsessInfo != null) {
            if (!"Y".equals(sDelSessFg)) {
                String sSessLinkDttm = StringUtils.defaultString((String) mSsessInfo.get("sess_link_dttm"));
                if (sSessLinkDttm.length() == 14) {
                    sSessLinkDttm = DateUtil.convertString(sSessLinkDttm, DateUtil.SDF_YYYYMMDDHHMMSS, DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
                }
                String sErrorMessage = " 아래 정보로 해당 아이디가 이미 로그인 되어 있습니다."
                        + "\n\n **********************************************************"
                        + "\n 로그인 시간 : " + sSessLinkDttm
                        + "\n 접속 IP : " + StringUtils.defaultString((String) mSsessInfo.get("trml_ip_addr"))
                        + "\n 내선번호 : " + StringUtils.defaultString((String) mSsessInfo.get("extn_no"))
                        + "\n CTI ID : " + StringUtils.defaultString((String) mSsessInfo.get("cti_id"))
                        + "\n **********************************************************"
                        + "\n\n 이전 연결을 끊고 로그인 하시겠습니까?";
                oResult.setResult(ActionResultType.UNKNOWN);
                oResult.setErrMsg(sErrorMessage);

                modelAndView.addObject(oResult);
                return modelAndView;
            }
            mSsessInfo = null;
        }


        // 07. 정상 로그인 처리
        oUserSession.setUserid((String) mUser.get("user_id"));
        oUserSession.setUsernm((String) mUser.get("user_nm"));
        oUserSession.setAuthoritycd((String) mUser.get("athr_cd"));
        oUserSession.setEmployeeid((String) mUser.get("empl_no"));
        oUserSession.setExt((String) mUser.get("extn_no"));
        oUserSession.setCtiid((String) mUser.get("cti_id"));
        oUserSession.setAcdgroup((String) mUser.get("acd_grop_no"));
        oUserSession.setCtiusefg((String) mUser.get("cti_use_yn"));
        oUserSession.setCentercd((String) mUser.get("cntr_cd"));
        oUserSession.setCenternm((String) mUser.get("cntr_nm"));
        oUserSession.setTeamcd((String) mUser.get("team_cd"));
        oUserSession.setHgrnteamcd((String) mUser.get("hgrn_team_cd"));

        //oUserSession.setMessengerusefg((String) mUser.get("messengerusefg"));
        oUserSession.setRecip((String) mUser.get("rec_ip_addr"));
        oUserSession.setDualmonitorfg((String) mUser.get("dual_mont_yn"));
        //oUserSession.setCenterabusefg((String) mUser.get("centerabusefg"));
        //oUserSession.setCenterabgusefg((String) mUser.get("centerabgusefg"));
        //oUserSession.setLeftctrl((String) mUser.get("leftctrl"));
        //oUserSession.setMainusefg((String) mUser.get("mainusefg"));
        //oUserSession.setChanneltype((String) mUser.get("channeltype"));
        oUserSession.setLoginid((String) mUser.get("lgn_id"));
        //oUserSession.setAutoreadyfg((String) mUser.get("autoreadyfg"));
        //oUserSession.setCtiip((String) mUser.get("cti_ip"));
        //oUserSession.setCtiport((String) mUser.get("ctiport"));
        //oUserSession.setCenterrecip((String) mUser.get("centerrecip"));
        oUserSession.setLogfg((String) mUser.get("actv_log_yn"));
        //oUserSession.setResidentnofg((String) mUser.get("residentnofg"));
        //oUserSession.setAutosavefg((String) mUser.get("autosavefg"));
        //oUserSession.setObcounselfg((String) mUser.get("obcounselfg"));
        //oUserSession.setLoginstatus((String) mUser.get("loginstatus"));
        oUserSession.setJobtypecd((String) mUser.get("cons_bswr_typ_cd"));
        //oUserSession.setJobchnltypecd((String) mUser.get("jobchnltypecd"));
        //oUserSession.setMultiid((String) mUser.get("multiid"));
        //oUserSession.setMultiautofg((String) mUser.get("multiautofg"));
        oUserSession.setTeamnm((String) mUser.get("team_nm"));
        oUserSession.setFileid((String) mUser.get("file_id"));
        oUserSession.setRoleList((String) mUser.get("role"));
        oUserSession.setEncodt((String) mUser.get("enco_dt"));
        oUserSession.setSinglemail((String) mUser.get("emil_addr"));
        oUserSession.setLogindt(DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS));
        oUserSession.setProductcd(sGdsCd);

        for (String productcd: sAthrGdsCdList) {
            if ("1".equals(productcd)) {
                oUserSession.setHasUserAuth(true);
            }

            if ("2".equals(productcd)) {
                oUserSession.setHasAdminAuth(true);
            }

            *//**
             * 변경일자 : 2015-11-11
             * 변 경 자 : 정      훈
             * 변경이유 : 제품코드에따른 업무 추가
             * 추후이슈 : 제품관리와의 연계후 하드코딩 추후 수정
             * 요 청 자 : 우달식 이사님
             * *//*

            if ("3".equals(productcd)) { // 2차상담
                oUserSession.setHasN2consAdminAuth(true);
            }

            if ("4".equals(productcd)) { // 웹상담
                oUserSession.setHasWebAdminAuth(true);
            }
        }

        // 사용자 세션 생성
        SessionUtils.setLoginUser(oUserSession);

        // 세션정보 테이블 저장
        Map<String, Object> mUpdateParam = ParamUtils.getCenterParam();
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        mUpdateParam.put("user_id", (String) oUserSession.getUserid());
        mUpdateParam.put("was_sess_cntn", servletRequestAttribute.getRequest().getSession().getId());
        mUpdateParam.put("trml_ip_addr", servletRequestAttribute.getRequest().getRemoteAddr());
        mUpdateParam.put("extn_no", oUserSession.getExt());
        mUpdateParam.put("cti_id", oUserSession.getCtiid());
        mUpdateParam.put("lgn_typ_cd", "LOGIN");
        mUpdateParam.put("cntr_cd", oUserSession.getCentercd());

        sessInfoService.insertSessInfo(mUpdateParam);

        // 로그인시 근태 등록
        dllzDtlService.insertDllzDtlForLogin(mUpdateParam);

        // 로그인 이력정보-
        mUpdateParam.put("ip_addr", servletRequestAttribute.getRequest().getRemoteAddr());

        lgnHstrService.insertLgnHstr(mUpdateParam);

        // 비밀번호 오류 횟수 초기화
        mUpdateParam.put("athn_falr_tmcnt", "0");

        userService.updateUserPassword(mUpdateParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }*/

    /**
     * 로그아웃시에 근태를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        //에러코드및 메시지
        String m_ErrorCode = "0";
        String m_ErrorMsg = "OK";

        UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
        String userid = oUserSession.getUserid();			// 로그인한 상담사의 user ID

        Map<String, Object> mUpdateParam = ParamUtils.getCenterParam();
        mUpdateParam.put("user_id", userid);

        dllzDtlService.updateDllzDtlForLogout(mUpdateParam);

        // 로그온 이력정보
        mUpdateParam.put("lgn_typ_cd", "LOGOUT");

        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        mUpdateParam.put("ip_addr", servletRequestAttribute.getRequest().getRemoteAddr());

        lgnHstrService.insertLgnHstr(mUpdateParam);

        // 사용자 세션삭제
        SessionUtils.setLoginUser(null);

        // 세션정보 테이블 수정
        mUpdateParam.put("was_sess_cntn", servletRequestAttribute.getRequest().getSession().getId());
        mUpdateParam.put("trml_ip_addr", servletRequestAttribute.getRequest().getRemoteAddr());
        mUpdateParam.put("extn_no", oUserSession.getExt());
        mUpdateParam.put("cti_id", oUserSession.getCtiid());

        sessInfoService.insertSessInfo(mUpdateParam);

        // return new ModelAndView("logout");

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, m_ErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, m_ErrorMsg);

        return modelAndView;
    }


    /**
     * 사용자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user/user/search-list")
    public ModelAndView getUserList2(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            //조회조건
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

            String sUserIds = StringUtils.defaultString((String)hmParam.get("userids"));
            String sAnswUserIds = StringUtils.defaultString((String)hmParam.get("answuserids"));

            if (!"".equals(sUserIds)) {
                hmParam.put("userids", sUserIds.split(",").length > 0 ? sUserIds.split(",") : null);
            }

            if (!"".equals(sAnswUserIds)) {
                hmParam.put("answuserids", sAnswUserIds.split(","));
            }

            int nTotal = userService.getUserCount(hmParam);
            mapOutVar.put("total_count", nTotal);
            List<Map<String, Object>> mList = userService.getUserList2(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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


    /**
    * 회의실 견적서/인보이스 엑셀 다운로드를 한다.
    * @pmParam pmParam Map<String, Object>
    * @pmParam poSession HttpSession
    * @pmParam poResponse HttpServletResponse
    * @return ModelAndView
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/user/excel-down")
   public ModelAndView excelDown(@RequestParam Map<String, Object> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
       ModelAndView oModelAndView = new ModelAndView("common/xls-down-result");
       boolean resultfg = true;

       System.out.println("## START EXCEL DOWN");

      UserSession oUser = (UserSession) SessionUtils.getLoginUser();
      String sXlsTemplate = StringUtils.defaultString((String) pmParam.get("xls_template"));
      String sXlsParam = URLDecoder.decode(StringUtils.defaultString((String) pmParam.get("xls_param")),"utf-8");

      // 파라미터 파싱 작업
      Map<String, Object> mXlsParam = JSONUtil.covertJsonToMap(sXlsParam);
      if ((boolean)mXlsParam.get("resultfg") == false) {
          poSession.removeAttribute("EXCEL_SESSION");

          oModelAndView.addObject("result", "error");
          oModelAndView.addObject("resultMessage", mXlsParam.get("resultmsg"));
          resultfg = false;
          return oModelAndView;
      }
      Map<String, Object> mSearchParam = (Map<String, Object>)mXlsParam.get("result");
      ParamUtils.addCenterParam(mSearchParam);

      // 엑셀 다운로드 준비
      Map<String, String> mFileData = new HashMap<String, String>();
      String sErrCd = ExcelUtils.prepareExcelDown(ctx, mFileData, sXlsTemplate, LOGGER);
      if (sErrCd != null) {
          poSession.removeAttribute("EXCEL_SESSION");

          oModelAndView.addObject("result", sErrCd);
          resultfg = false;
          return oModelAndView;
      }

      try {
          // ========== 엑셀 데이터 처리 - 시작 ==========
          List<String> lsSheetNm = new ArrayList<String>();
          List<Map<String, Object>> lsExcelData = null;

          System.out.println("#mSearchParam:" + mSearchParam);
          // 사용자 정보 입력
          lsExcelData = userService.getUserList(mSearchParam);
          for (Map<String, Object> user : lsExcelData) {
              lsSheetNm.add(user.get("user_nm").toString());
          }
          // ========== 엑셀 데이터 처리 - 종료 ==========

          // 엑셀 파일 전송
          ExcelUtils.transFileMultiSheet(poSession, mFileData, lsExcelData, lsSheetNm, poResponse);
      } catch (Exception exception) {
          poSession.removeAttribute("EXCEL_SESSION");
          exception.printStackTrace();

          oModelAndView.addObject("result", "error");
          oModelAndView.addObject("resultMessage", exception.getMessage());

          LOGGER.error("###### EXCEL - " + oUser.getLoginid() + " ###### ERROR [error]");
          resultfg = false;
      }

      poSession.removeAttribute("EXCEL_SESSION");
      LOGGER.info("###### EXCEL - " + oUser.getLoginid() + " ###### END");

      if (false == resultfg) {
          return oModelAndView;
      } else {
          return null;
      }
   }


   /**
    * 사용자 정보를 검색한다.
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception
    */
   @RequestMapping(value = "/user/user/getUserListForMemo")
   public ModelAndView getUserListForMemo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
           //조회조건
           DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
           if (srchInDs != null && srchInDs.size() > 0) {
               hmParam = srchInDs.get(0);
           }

           String sUserIds = StringUtils.defaultString((String)hmParam.get("userids"));
           String sAnswUserIds = StringUtils.defaultString((String)hmParam.get("answuserids"));

           if (!"".equals(sUserIds)) {
               hmParam.put("userids", sUserIds.split(",").length > 0 ? sUserIds.split(",") : null);
           }

           if (!"".equals(sAnswUserIds)) {
               hmParam.put("answuserids", sAnswUserIds.split(","));
           }

           int nTotal = userService.getUserCount(hmParam);
           mapOutVar.put("total_count", nTotal);
           List<Map<String, Object>> mList = userService.getUserListForMemo(hmParam);
           listMap.setRowMaps(mList);
           mapOutDs.put("ds_output", listMap);

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
   /**
    * 직무관리 사용자 정보를 검색한다.
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception TB_EVAL_ADMIN
    */
   @RequestMapping(value = "/user/user/evallist")
   public ModelAndView getevalUserList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

           //조회조건
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

           ParamUtils.addCenterParam(hmParam);

           int nTotal = userService.getevalUserCount(hmParam);
           mapOutVar.put("tc_cnt", nTotal);

           List<Map<String, Object>> mList = userService.getevalUserList(hmParam);
           listMap.setRowMaps(mList);
           mapOutDs.put("ds_output", listMap);

           modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
           modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
    * 직무관리 사용자 저장
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception TB_EVAL_ADMIN
    */
   @RequestMapping(value = "/user/user/evalsave")
   public ModelAndView getevalsave(XPlatformMapDTO xpDto, Model model) throws Exception {
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


           String ls_sbit =  (String) mapInVar.get("sbit") ;
           String ls_yymm = (String) mapInVar.get("yymm") ;


           DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

           // 저장 시
           if (listInDs.size() > 0 )  {
               if ("1".equals(ls_sbit)) {
                   userService.evalsave(mapInDs);
               }
           }

           // 마감
           if ("2".equals(ls_sbit)) {
               hmParam.put("sbit", ls_sbit);
               hmParam.put("yymm", ls_yymm);
               ParamUtils.addSaveParam(hmParam);
               hmParam.put("upd_man", hmParam.get("rgsr_id"));
               userService.evalmsave(hmParam);
           }

           // 마감취소
           if ("3".equals(ls_sbit)) {
               hmParam.put("sbit", "1");
               hmParam.put("yymm", ls_yymm);
               ParamUtils.addSaveParam(hmParam);
               hmParam.put("upd_man", hmParam.get("rgsr_id"));
               userService.evalmsave(hmParam);
           }


           modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
           modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
    * 직무관리조회 정보를 검색한다.
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception TB_EVAL_ADMIN
    */
   @RequestMapping(value = "/user/user/search_evallist")
   public ModelAndView getsearchevalUserList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

           //조회조건
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

           ParamUtils.addCenterParam(hmParam);

           int nTotal = userService.getsearchevalUserCount(hmParam);
           mapOutVar.put("tc_cnt", nTotal);

           List<Map<String, Object>> mList = userService.getsearchevalUserList(hmParam);
           listMap.setRowMaps(mList);
           mapOutDs.put("ds_output", listMap);

           modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
           modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
    * 직무관리 사용자 삭제
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception TB_EVAL_ADMIN
    */
   @RequestMapping(value = "/user/user/delevalsave")
   public ModelAndView getdelevalsave(XPlatformMapDTO xpDto, Model model) throws Exception {
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


           String ls_sbit =  (String) mapInVar.get("sbit") ;
           String ls_yymm = (String) mapInVar.get("yymm") ;


           DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");


                   userService.delevalsave(mapInDs);




           modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
           modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

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
    * 사용자 DropDownList 정보를 검색한다.
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception
    */
   @RequestMapping(value = {"/user/user/srch-form-list"})
   public ModelAndView getUserListForSrchFormList(XPlatformMapDTO xpDto, Model model) throws Exception {
       ModelAndView modelAndView = new ModelAndView("xplatformMapView");
       DataSetMap dsMap = new DataSetMap();

       // 에러코드및 메시지
       String szErrorCode = "0";
       String szErrorMsg  = "OK";

       try {
           Map <String, Object> mapInVar     = xpDto.getInVariableMap();
           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
           Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

           //Map<String, Object> mSearchParam = ParamUtils.convertDropDownParam(pmParam);

           Map<String, Object> mSearchParam= new HashMap<String, Object>() ;
//           DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
//           if (srchInDs != null && srchInDs.size() > 0) {
//               mSearchParam = srchInDs.get(0);

               
               ParamUtils.addCenterParam(mSearchParam);
               
               UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
               
               mSearchParam.put("user_id", oUserSession.getUserid());
               mSearchParam.put("file_nm",mapInVar.get("file_nm"));

               //int nTotal = userService.getUserCount(mSearchParam);
               int nTotal = userService.getUserFormCount(mSearchParam);
               //List<Map<String, Object>> mList = userService.getUserList(mSearchParam);


               //dsMap.setRowMaps(mList);
               mapOutVar.put("form_count", nTotal);
               //mapOutDs.put("datas", dsMap);
//           }

           modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
           modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
       } catch (Exception e) {
           e.printStackTrace();
           szErrorCode = "-1";
           szErrorMsg  = e.getMessage();
       }

       modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
       modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

      return modelAndView;
   }

}