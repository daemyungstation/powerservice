/*
 * (@)# MainController.java
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
package powerservice.business.main.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.evl.service.EvltPlanService;
import powerservice.business.evl.service.EvltTypBankService;
import powerservice.business.kms.service.ConsTypService;
import powerservice.business.sch.service.WrkTypCdService;
import powerservice.business.sys.service.AthrService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.CdService;
import powerservice.business.sys.service.HrchCdService;
import powerservice.business.sys.service.PgmAthrService;
import powerservice.business.sys.service.SrvrInfoService;
import powerservice.business.usr.service.CntrService;
import powerservice.business.usr.service.TeamService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.Constants;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 메인화면
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID Main
 */

@Controller
public class MainController {

    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Resource
    private AthrService athrService;

    @Resource
    private PgmAthrService pgmAthrService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private CdService cdService;

    @Resource
    private HrchCdService hrchCdService;

    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;

    @Resource
    private CntrService cntrService;

    @Resource
    private SrvrInfoService srvrInfoService;

    @Resource
    private WrkTypCdService wrkTypCdService;

    @Resource
    private ConsTypService consTypService;

    @Resource
    private EvltPlanService evltPlanService;

    @Resource
    private EvltTypBankService evltTypBankService;

    @Resource
    Environment env;

    // @Resource
    // private EvltPlanService evltPlanService;

    // @Resource
    // private CmpgService campaignService;

    /**
     * 메인화면을 오픈한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/{psPageType}")
    public ModelAndView openMain(@PathVariable("psPageType") String psPageType, @RequestParam Map<String, String> pmParam) throws Exception {
        ModelAndView oModelAndView = new ModelAndView();

        /**
         * 변경일자 : 2015-11-11
         * 변 경 자 : 정      훈
         * 변경이유 : 제품코드에따른 업무 추가
         * 추후이슈 : 제품관리와의 연계후 하드코딩 추후 수정
         * 요 청 자 : 우달식 이사님
         */
        String sViewName = "";
        String sGdsCd = "";
        if ("usr".equals(psPageType)) { // 상담사
            sViewName = "/main/main-usr";
            sGdsCd = "1";
        } else if ("admin".equals(psPageType)) { // 관리자
            sViewName = "/main/main-admin";
            sGdsCd = "2";
        } else if ("n2cons".equals(psPageType)){ // 2차상담
            sViewName = "/main/main-admin";
            sGdsCd = "3";
        } else if ("web".equals(psPageType)){ // 웹상담
            sViewName = "/main/main-admin";
            sGdsCd = "4";
        }
        oModelAndView.setViewName(sViewName);

        // 사용자 프로그램 권한 처리

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        Map<String, String> mSearchParam = new HashMap<String, String>();
        mSearchParam.put("athr_cd", (String) oLoginUser.getAuthoritycd());
        mSearchParam.put("cntr_cd", oLoginUser.getCentercd());
        mSearchParam.put("gds_cd", sGdsCd);
        mSearchParam.put("menu_flag", "Y");
        List<Map<String, Object>> mPgmAthrList = pgmAthrService.getPgmAthrList(mSearchParam);

        // 대분류 프로그램
        List<Map<String, Object>> mMajorPgmList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> mPgmAthr : mPgmAthrList) {
            if ("1".equals(String.valueOf(mPgmAthr.get("lvl_cnt")))) {
                mMajorPgmList.add(mPgmAthr);
            }
        }

        Map<String, List<Map<String, Object>>> mMiddlePgm = new HashMap<String, List<Map<String, Object>>>();
        Map<String, List<Map<String, Object>>> mMinorPgm = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> mMajorPgm : mMajorPgmList) {
            // 중분류 프로그램
            List<Map<String, Object>> mMiddlePgmList = new ArrayList<Map<String, Object>>();
            String sMajorPgmCd = (String) mMajorPgm.get("pgm_cd");
            for (Map<String, Object> mPgmAthr : mPgmAthrList) {
                if ("2".equals(String.valueOf(mPgmAthr.get("lvl_cnt"))) && sMajorPgmCd.equals((String) mPgmAthr.get("hgrn_pgm_cd"))) {
                    mMiddlePgmList.add(mPgmAthr);
                }
            }
            mMiddlePgm.put(sMajorPgmCd, mMiddlePgmList);

            for (int i = 0; i < mMiddlePgmList.size(); i++) {
                // 소분류 프로그램
                List<Map<String, Object>> mMinorPgmList = new ArrayList<Map<String, Object>>();
                String sMiddlePgmCd = (String) mMiddlePgmList.get(i).get("pgm_cd");
                for (Map<String, Object> mPgmAthr : mPgmAthrList) {
                    if ("3".equals(String.valueOf(mPgmAthr.get("lvl_cnt"))) && sMiddlePgmCd.equals((String) mPgmAthr.get("hgrn_pgm_cd"))) {
                        mMinorPgmList.add(mPgmAthr);
                    }
                }
                mMinorPgm.put(sMiddlePgmCd, mMinorPgmList);
            }
        }

        oModelAndView.addObject("majorList", mMajorPgmList);
        oModelAndView.addObject("middleList", mMiddlePgm);
        oModelAndView.addObject("minorList", mMinorPgm);
        //pagetyp : 제품코드 ,추가이유: 2015-11-11 변경 내용과 같은내용
        oModelAndView.addObject("pagetyp", sGdsCd);

        mPgmAthrList = null;

        return oModelAndView;
    }


    /**
     * 화면 코드 데이터를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/main/screen-data")
    @ResponseBody
    public ActionResult searchBasicvalue(@RequestBody Map<String, Object> pmParam) throws Exception {
        ActionResult oResult = new ActionResult();
        Map<String, Object> oData = new HashMap<String, Object>();

        if (pmParam.get("user") != null && (Boolean) pmParam.get("user")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("user_stat_cd", "10");
            mSearchParam.put("use_yn", "Y");
            mSearchParam.put("orderBy", "user_nm");
            mSearchParam.put("orderDirection", "asc");
            List<Map<String, Object>> mUserList = userService.getUserList(mSearchParam);
            oData.put("userList", mUserList);
        }

        if (pmParam.get("myCenterTeam") != null && (Boolean) pmParam.get("myCenterTeam")) {
            List<Map<String, Object>> mTeamList = teamService.getTeamList(ParamUtils.getCenterParam());
            oData.put("teamList", mTeamList);
        }
        else if (pmParam.get("team") != null && (Boolean) pmParam.get("team")) {
            List<Map<String, Object>> mTeamList = teamService.getTeamList(null);
            oData.put("teamList", mTeamList);
        }

        if (pmParam.get("cntr") != null && (Boolean) pmParam.get("cntr")) {
            List<Map<String, Object>> mCntrList = cntrService.getCntrList(null);
            oData.put("cntrList", mCntrList);
        }

        if (pmParam.get("serverList") != null && (Boolean) pmParam.get("serverList")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("orderBy", "srvr_id");
            mSearchParam.put("orderDirection", "asc");
            List<Map<String, Object>> mServerList = srvrInfoService.getSrvrInfoList(mSearchParam);
            oData.put("serverList", mServerList);
        }

        if (pmParam.get("wrkTyp") != null && (Boolean) pmParam.get("wrkTyp")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("orderBy", "wrk_typ_cd");
            mSearchParam.put("orderDirection", "asc");
            mSearchParam.put("use_yn", "Y");
            List<Map<String, Object>> mWrkTypList = wrkTypCdService.getWrkTypCdList(mSearchParam);
            oData.put("wrkTypList", mWrkTypList);
        }

        if (pmParam.get("authority") != null && (Boolean) pmParam.get("authority")) {
            List<Map<String, Object>> mCenterList = athrService.getAthrList(null);
            oData.put("authorityList", mCenterList);
        }

        if (pmParam.get("consTyp") != null && (Boolean) pmParam.get("consTyp")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("orderBy", "cons_typ_sqnc");
            mSearchParam.put("orderDirection", "asc");

            List<Map<String, Object>> mConsTypList = consTypService.getConsTyp(mSearchParam);
            oData.put("consTypList", mConsTypList);
        }

        if (pmParam.get("basVl") != null && (Boolean) pmParam.get("basVl")) {
            @SuppressWarnings("unchecked")
            List<String> sBasVlFilterList  = (List<String>) pmParam.get("basVlFilter");
            if (sBasVlFilterList != null && sBasVlFilterList.size() != 0) {
                List<Map<String, Object>> mBasVlList = basVlService.getBasVlList(sBasVlFilterList.toArray(new String[sBasVlFilterList.size()]));
                oData.put("basVlList", mBasVlList);
            }
        }

        if (pmParam.get("evltPlan") != null && (Boolean) pmParam.get("evltPlan")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            mSearchParam.put("evlp_id", oLoginUser.getUserid());
            mSearchParam.put("evlt_prgr_stat_cd", pmParam.get("evlt_prgr_stat_cd"));
            List<Map<String, Object>> mEvltPlanList = evltPlanService.getEvltPlanByEvlp(mSearchParam);

            oData.put("evltPlanList", mEvltPlanList);
        }


        if (pmParam.get("code") != null && (Boolean) pmParam.get("code")) {
            @SuppressWarnings("unchecked")
            List<String> sCdSetCdList  = (List<String>) pmParam.get("codeFilter");
            if (sCdSetCdList != null && sCdSetCdList.size() != 0) {
                List<Map<String, Object>> mCdList = cdService.getCdList(sCdSetCdList.toArray(new String[sCdSetCdList.size()]));
                oData.put("codeList", mCdList);
            }
        }

        if (pmParam.get("hrchCode") != null && (Boolean) pmParam.get("hrchCode")) {
            @SuppressWarnings("unchecked")
            List<String> sCodeSetCdList = (List<String>) pmParam.get("hrchCodeFilter");

            if (sCodeSetCdList != null && sCodeSetCdList.size() != 0) {
                List<Map<String, Object>> mHrchCdList = hrchCdService.getHrchCdListByStringArray(sCodeSetCdList.toArray(new String[sCodeSetCdList.size()]));
                oData.put("hrchCodeList", mHrchCdList);
            }
        }

        /*
        if (pmParam.get("campaign") != null && (Boolean)pmParam.get("campaign")) {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            mSearchParam.put("user_id", oLoginUser.getUserid());

            List<Map<String, Object>> mCampaignList = campaignService.getCampaignListByUserId(mSearchParam);

            oData.put("campaignList", mCampaignList);
        }
        */

        oResult.setData(oData);

        return oResult;
    }

    /**
     * HTTP 오류 페이지를 처리한다.
     *
     * @param psErrorCode String
     * @param poRequest HttpServletRequest
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/error-http/{HttpErrorCode}")
    public ModelAndView handleErrorPage(@PathVariable("HttpErrorCode") String psErrorCode, HttpServletRequest poRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        // poRequest.getAttribute("javax.servlet.error.poRequest_uri"));
        LOGGER.error("### [" + psErrorCode + "]");

        @SuppressWarnings("unchecked")
        Enumeration<Object> enumeration = poRequest.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String sHeader = String.valueOf(enumeration.nextElement());
            LOGGER.error("=== [" + sHeader + "] " + poRequest.getHeader(sHeader));
        }

        String sViewName = null;
        String sErrorMessage = null;

        if ("404".equals(psErrorCode)) {
             sViewName = "error/404";
             sErrorMessage = "\n요청 정보를 찾을 수 없습니다.";
        } else if ("500".equals(psErrorCode)) {
            sViewName = "error/500";
            sErrorMessage = "\n서버 내부 오류가 발생했습니다.";
        } else {
            sErrorMessage = "\nHTTP " + psErrorCode + " 오류가 발생했습니다.";
        }

        // Ajax 요청인 경우 ExceptionHandler 처리
        if ("XMLHttpRequest".equals(poRequest.getHeader("X-Requested-With")) ||
            "XMLHttpRequest".equals(poRequest.getHeader("x-poRequested-with"))) {
            // HTTP 오류코드 저장
            poRequest.setAttribute("HttpErrorCode", psErrorCode);

            // Exception 생성
            throw new Exception(sErrorMessage);
        }

        // 페이지 요청인 경우 해당 오류 페이지 반환
        modelAndView.setViewName(sViewName);
        return modelAndView;
    }

}
