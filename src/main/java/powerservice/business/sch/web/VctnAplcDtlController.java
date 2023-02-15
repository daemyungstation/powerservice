/*
 * (@)# VctnAplcDtlController.java
 *
 * @author 배윤정
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

package powerservice.business.sch.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.EmilFxfrService;
import powerservice.business.kms.service.EmilSendService;
import powerservice.business.sch.service.ScdlDtlService;
import powerservice.business.sch.service.VctnAplcDtlService;
import powerservice.business.sch.service.VctnScdlDtlService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.FileService;
import powerservice.business.usr.service.UserService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 근무유형 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnAplcDtl
 */
@Controller
@RequestMapping(value = {"/mypage/vctn-aplc-dtl", "/scdl/vctn-aplc-dtl"})
public class VctnAplcDtlController {

    @Resource
    private VctnAplcDtlService vctnAplcDtlService;

    @Resource
    private VctnScdlDtlService vctnScdlDtlService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private EmilFxfrService emilFxfrService;

    @Resource
    private FileService fileService;

    @Resource
    private EmilSendService emilSendService;

    @Resource
    private UserService userService;

    @Resource
    private ScdlDtlService scdlDtlService;

    /**
     * 휴가요청 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    public ModelAndView getVctnAplcDtlList(@PathVariable("pageType") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        if ("usr".equals(psPathType)) {
            pmParam.put("user_id", oLoginUser.getUserid());
        } else {
            pmParam.put("appr_id", oLoginUser.getUserid());
        }

        int nTotal = vctnAplcDtlService.getVctnAplcDtlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = vctnAplcDtlService.getVctnAplcDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가요청 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/save/{pagetype}")
    public ModelAndView saveVctnAplcDtl(@PathVariable("pagetype") String psPathType,@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sVctnAplcId = StringUtils.defaultString((String) pmParam.get("vctn_aplc_id"));
        String sVctnDspsStatCd = StringUtils.defaultString((String) pmParam.get("vctn_dsps_stat_cd"));

        String sUserId = "";
        String sTeamCd = "";

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        if ("usr".equals(psPathType)) {
            sUserId = oLoginUser.getUserid();
            sTeamCd = oLoginUser.getTeamcd();
        } else {
            sUserId = StringUtils.defaultString((String) pmParam.get("user_id"));
            sTeamCd = StringUtils.defaultString((String) pmParam.get("team2_cd"));
        }

        // 일정등록 관련
        List<Map<String, Object>> mVctnList = new ArrayList<Map<String,Object>>();

        // 휴가일자 유효성 체크
        ParamUtils.addSaveParam(pmParam);
        if ("10".equals(sVctnDspsStatCd) || "20".equals(sVctnDspsStatCd)) {
            List<Map<String, Object>> mdataVctnDtDtlList = (List<Map<String, Object>>) pmParam.get("vctn_dt_dtl_list");
            if (mdataVctnDtDtlList != null) {
                for (Map<String, Object> mRow : mdataVctnDtDtlList) {
                    String sVctnDt = StringUtils.defaultString((String) mRow.get("vctn_dt"));
                    String sVctnDtMsg = "";
                    if (sVctnDt != "") {
                        sVctnDtMsg = sVctnDt.substring(0, 4) + "년 " + sVctnDt.substring(4, 6) + "월 " + sVctnDt.substring(6, 8) + "일";
                    }

                    mRow.put("user_id", sUserId);
                    mRow.put("team_cd", sTeamCd);

                    // 01. 오늘 이후의 날짜 선택 체크.
                    Date dVctnDt = new SimpleDateFormat("yyyyMMdd").parse(sVctnDt);
                    Date dToday = new Date();

                    if (dToday.compareTo(dVctnDt) > 0) {
                        oResult.setResult(ActionResultType.ERROR);
                        oResult.setErrMsg("휴가일자는 오늘 이후로 선택하세요.");

                        modelAndView.addObject(oResult);
                        return modelAndView;
                    }

                    // 02. 휴가 신청할때 휴가인원 배정건수 체크
                    ActionResult oVctnScdlDtlResult = getVctnScdlDtlCountPrivate(mRow);

                    Map<String, Integer> oVctnScdlDtlData = (Map<String, Integer>) oVctnScdlDtlResult.getData();

                    int nApproveCnt = oVctnScdlDtlData.get("approveCnt");
                    int nMaxCnt = oVctnScdlDtlData.get("maxCnt");

                    if (nMaxCnt == 0 || nApproveCnt >= nMaxCnt) {
                        oResult.setResult(ActionResultType.ERROR);
                        if ("10".equals(sVctnDspsStatCd)) {
                            oResult.setErrMsg(sVctnDtMsg + "은 휴가신청이 마감 되었습니다.\n휴가일자를 변경하세요.");
                        } else {
                            oResult.setErrMsg(sVctnDtMsg + "은 휴가승인이 마감 되었습니다.");
                        }

                        modelAndView.addObject(oResult);
                        return modelAndView;
                    }

                    // 03. 이미 신청한 날짜인지 체크
                    if ("10".equals(sVctnDspsStatCd)) {
                        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
                        mSearchParam.put("user_id", sUserId);
                        mSearchParam.put("vctn_dsps_stat_cd_list", "'10','20'");
                        mSearchParam.put("vctn_dt", sVctnDt);
                        mSearchParam.put("expt_vctn_aplc_id", sVctnAplcId);


                        int nCount = vctnAplcDtlService.getApproveCount(mSearchParam);
                        if (nCount > 0) {
                            oResult.setResult(ActionResultType.ERROR);
                            oResult.setErrMsg(sVctnDtMsg + "에는 이미 휴가를 신청 하셨습니다.\n휴가일자를 변경하세요.");

                            modelAndView.addObject(oResult);
                            return modelAndView;
                        }
                    }

                    // '승인'인 경우 일정등록 관련정보 셋팅
                    if ("20".equals(sVctnDspsStatCd)) {
                        Map<String, Object> mVctnInfoParam = new HashMap<String, Object>();
                        if (mRow.get("vctn_dycnt_cd").equals("10")) {	// 휴가일수가 1일인 경우...
                            mVctnInfoParam.put("aldy_flag", 1);	// 종일 표시
                            mVctnInfoParam.put("scdl_stt_dttm", sVctnDt + "000000");
                            mVctnInfoParam.put("scdl_end_dttm", sVctnDt + "000000");
                        } else {
                            mVctnInfoParam.put("aldy_flag", 0);
                            // 필요시 특정시간 저장
                            mVctnInfoParam.put("scdl_stt_dttm", sVctnDt + "000000");
                            mVctnInfoParam.put("scdl_end_dttm", sVctnDt + "000000");
                        }
                        mVctnInfoParam.put("scdl_typ_cd", "V");
                        mVctnInfoParam.put("scdl_titl", pmParam.get("vctn_kind_nm") + " :: " + mRow.get("vctn_dycnt_nm"));
                        mVctnInfoParam.put("scdl_cntn", "* 휴가사유 : " + pmParam.get("vctn_rsn") + "\n* 승인자 : " + pmParam.get("appr_nm"));
                        mVctnInfoParam.put("scdl_chpr_id", sUserId);
                        mVctnList.add(mVctnInfoParam);
                    }
                }
            } else { // 휴가일자와 휴가일수가 등록되지 않은 경우.
                oResult.setResult(ActionResultType.ERROR);

                oResult.setErrMsg("휴가일자와 휴가일수를 입력하세요.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
        }

        // 휴가등록
        if ("".equals(sVctnAplcId)) {
            pmParam.put("user_id", sUserId);

            sVctnAplcId = vctnAplcDtlService.insertVctnAplcDtl(pmParam);
        } else {
            // 04. 수정(Update)전에 해당 휴가정보의 처리여부확인
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("vctn_aplc_id", sVctnAplcId);
            mSearchParam.put("vctn_dsps_stat_cd", "10");		// 10 = 신청

            int nCount = vctnAplcDtlService.getVctnAplcDtlCount(mSearchParam);
            if (nCount == 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("신청하신 휴가는 처리되어 수정할 수 없습니다.\n다시 조회하세요.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            // DB에 저장되어있는 상태정보가 여전히 '신청'인 경우에만 수정가능
            vctnAplcDtlService.updateVctnAplcDtl(pmParam);

            // 삭제할 휴가일자가 있는 경우 삭제진행
            Map<String, Object> mParam = new HashMap<String, Object>();
            List<String> mdataVctnDtDtlDeleteList = (List<String>) pmParam.get("vctn_dt_dtl_delete_list");
            if (mdataVctnDtDtlDeleteList.size() > 0) {
                String[] sVctnDtDtlDeleteList = new String[mdataVctnDtDtlDeleteList.size()];
                sVctnDtDtlDeleteList = mdataVctnDtDtlDeleteList.toArray(sVctnDtDtlDeleteList);

                mParam.put("selectcheck", sVctnDtDtlDeleteList);
                mParam.put("vctn_aplc_id", sVctnAplcId);

                vctnAplcDtlService.deleteVctnDtDtl(mParam);
            }

            // '승인'처리 진행시 일정에 '휴가'로 등록
            if ("20".equals(sVctnDspsStatCd)) {
                Map<String, Object> mVctnParam = new HashMap<String, Object>();
                ParamUtils.addSaveParam(mVctnParam);

                scdlDtlService.saveScdlDtl(mVctnList, mVctnParam);
            }
        }

        /*********************************************************
         *	휴가신청 메일전송
         *********************************************************/
        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("vctn_aplc_id", sVctnAplcId);
        Map<String, Object> mVctnAplcDtl = vctnAplcDtlService.getVctnAplcDtl(mSearchParam);
        Map<String, Object> mtSearchParam = new HashMap<String, Object>();

        // 이메일 템플릿 아이디
        String sEmailtpid = "";
        String sVctnDspsStatnm = "";
        if ("10".equals(sVctnDspsStatCd)) {			// '신청'
            sEmailtpid = basVlService.getBasVlAsString("vctn_aplc_emailtpid");
            sVctnDspsStatnm = "신청";
        } else if ("20".equals(sVctnDspsStatCd)) {	// '승인'
            sEmailtpid = basVlService.getBasVlAsString("vctn_aprv_emailtpid");
            sVctnDspsStatnm = "승인";
        } else {									// '반려'
            sEmailtpid = basVlService.getBasVlAsString("vctn_rjct_emailtpid");
            sVctnDspsStatnm = "반려";
        }
        mtSearchParam.put("emil_fxfr_id", sEmailtpid);

        Map<String, Object> mEmailtemplate = emilFxfrService.getEmilFxfr(mtSearchParam);

        if(mEmailtemplate != null){
            Map<String, Object> mEmailParam = new HashMap<String, Object>();

            ParamUtils.addCenterParam(mEmailParam);

            // 승인자 이메일주소가 없는 경우 조회해옴
            if (StringUtils.defaultString((String) pmParam.get("emil_addr")).equals("")) {
                ParamUtils.addCenterParam(pmParam);
                pmParam.put("user_id", pmParam.get("appr_id"));
                Map<String, Object> mApprUserInfo = userService.getUser(pmParam);
                mEmailParam.put("emil_addr", mApprUserInfo.get("emil_addr"));
            } else {
                mEmailParam.put("emil_addr", pmParam.get("emil_addr"));
            }
            mEmailParam.put("emil_titl",  mEmailtemplate.get("emil_titl"));
            mEmailParam.put("emil_html_cntn", mEmailtemplate.get("emil_html_cntn"));

            // 공통 커스텀 태그 파라미터 전달
            mEmailParam.put("vctn_kind_nm",  mVctnAplcDtl.get("vctn_kind_nm"));							//<휴가종류>
            mEmailParam.put("user_nm",  mVctnAplcDtl.get("user_nm"));									//<성명>
            mEmailParam.put("vctn_dt",  mVctnAplcDtl.get("vctn_dt"));									//<휴가기간>
            mEmailParam.put("vctn_dycnt_cd_sum",  mVctnAplcDtl.get("vctn_dycnt_cd_sum"));				//<휴가일수>
            mEmailParam.put("clph_no",  mVctnAplcDtl.get("clph_no"));									//<연락처>
            mEmailParam.put("vctn_rsn",  mVctnAplcDtl.get("vctn_rsn"));									//<휴가사유>

            // 추가 커스텀 태그 파라미터 전달
            if ("10".equals(sVctnDspsStatCd)) {		// '신청'
            } else if ("20".equals(sVctnDspsStatCd)) {	// '승인'
            } else {	// '반려'
                mEmailParam.put("vctn_rjct_rsn",  mVctnAplcDtl.get("vctn_rjct_rsn"));					//<반려사유>
            }

            // 이메일 템플릿 첨부파일 리스트 조회(rltn_item_id)
            mtSearchParam.put("rltn_item_id", mtSearchParam.get("emil_fxfr_id"));
            mtSearchParam.put("rltn_tbl_nm", "TB_EMIL_FXFR_ADMN");

            List<Map<String, Object>> fileList = fileService.getFileListWithBLOB(mtSearchParam);
            mEmailParam.put("fileList", fileList);

            // EMAIL발송
            emilSendService.sendEmail(mEmailParam);
        } else {
            oResult.setErrMsg("등록된 " + sVctnDspsStatnm + "이메일템플릿이 없습니다.\n" + sVctnDspsStatnm + "처리 완료 후 이메일이 발송되지 않았습니다.");
        }
        /********************************************************/

        oResult.setData(mVctnAplcDtl);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가요청 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteVctnAplcDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sVctnAplcId = StringUtils.defaultString((String) pmParam.get("vctn_aplc_id"));

        // 휴가신청 상태인지 조회
        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("vctn_aplc_id", sVctnAplcId);
        mSearchParam.put("vctn_dsps_stat_cd", "10");

        int nCount = vctnAplcDtlService.getVctnAplcDtlCount(mSearchParam);
        if (nCount == 0) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("신청하신 휴가는 이미 처리되어 삭제할 수 없습니다.\n다시 조회하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        vctnAplcDtlService.deleteVctnDtDtl(pmParam);	// 휴가일자 삭제
        vctnAplcDtlService.deleteVctnAplcDtl(pmParam);	// 휴가요청 삭제

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가일정 인원수 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vctn-scd-count")
    public ModelAndView getVctnScdlDtlCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = getVctnScdlDtlCountPrivate(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가일정 인원수 조회
     *
     * @param pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    private ActionResult getVctnScdlDtlCountPrivate(@RequestBody Map<String, Object> pmParam) throws Exception {
        ActionResult oResult = new ActionResult();
        Map<String, Integer> oData = new HashMap<String, Integer>();

        String sVctnAplcId = StringUtils.defaultString((String) pmParam.get("vctn_aplc_id"));
        String sVctnDt = StringUtils.defaultString((String) pmParam.get("vctn_dt"));
        String sTeamCd = StringUtils.defaultString((String) pmParam.get("team_cd"));

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();

        // 휴가 승인 건수 조회
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        mSearchParam.put("team2_cd", sTeamCd);
        mSearchParam.put("vctn_dsps_stat_cd", "20");
        mSearchParam.put("vctn_dt", sVctnDt);
        mSearchParam.put("expt_vctn_aplc_id", sVctnAplcId);
        int nApproveCnt = vctnAplcDtlService.getApproveCount(mSearchParam);

        // 일정관리 > 휴가인원배정에서 배정된 휴가 인원수
        mSearchParam.put("vctn_yrmn", sVctnDt.substring(0, 6));
        mSearchParam.put("vctn_dy", sVctnDt.substring(6, 8));
        mSearchParam.put("team_cd", oLoginUser.getTeamcd());
        int nMaxCnt = vctnScdlDtlService.getVctnScdlDtlNmprCnt(mSearchParam);

        oData.put("approveCnt", nApproveCnt);
        oData.put("maxCnt", nMaxCnt);

        oResult.setData(oData);

        return oResult;
    }

    /**
     * 휴가일자를 조회한다.
     *
     * @param psPathType
     * @param pmParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vctn-dt-dtl/list")
    public ModelAndView getVctnDtDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = vctnAplcDtlService.getVctnDtDtlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = vctnAplcDtlService.getVctnDtDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 사용자의 휴가정보를 검색한다.
     *
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vctn-info-view/{pageType}")
    public ModelAndView getVctnInfoView(@PathVariable("pageType") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        if ("usr".equals(psPathType)) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            pmParam.put("user_id", oLoginUser.getUserid());
        }

        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = vctnAplcDtlService.getVctnInfoView(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가일자 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
/*    @RequestMapping(value = "/vctn-dt-dtl/delete")
    public ModelAndView deleteVctnDtDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sVctnAplcId = StringUtils.defaultString((String) pmParam.get("vctn_aplc_id"));

        // 휴가신청 상태인지 조회
        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("vctn_aplc_id", sVctnAplcId);
        mSearchParam.put("vctn_dsps_stat_cd", "10");

        int nCount = vctnAplcDtlService.getVctnAplcDtlCount(mSearchParam);
        if (nCount == 0) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("신청하신 휴가는 이미 처리되어 삭제할 수 없습니다.\n다시 조회하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        vctnAplcDtlService.deleteVctnDtDtl(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }*/
}