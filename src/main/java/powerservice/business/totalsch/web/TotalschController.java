/*
 * (@)# TotalschController.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/01
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

package powerservice.business.totalsch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.BkmkInfoService;
import powerservice.business.sys.service.FileService;
import powerservice.business.totalsch.service.TotalschService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 통합검색 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/01
 * @프로그램ID Totalsch
 */

@Controller
@RequestMapping(value = "/total-sch")
public class TotalschController {

    @Resource
    private TotalschService totalschService;

    @Resource
    private BkmkInfoService bkmkInfoService;

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    /**
     * 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/{dataType}/list/{pageType}")
    public ModelAndView getTotalschList(@PathVariable("dataType") String psDataType, @PathVariable("pageType") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

         //System.out.println("psDataType >> " + psDataType);
         //System.out.println("psPathType >> " + psPathType);

        ParamUtils.addPagingParam(pmParam);

        pmParam.put("cntr_cd", "CCA");

        CommonUtils.printLog(pmParam);

        int total = 0;
        List<Map<String, Object>> list = null;


        switch(psDataType) {
        case "faq":
            pmParam.put("open_stat_cd", "Y");

            total = totalschService.getFaqCount(pmParam);
            list = totalschService.getFaqList(pmParam);
            break;

        case "ancm-mtr-dtl":
            pmParam.put("use_yn", "Y");

            total = totalschService.getAncmMtrDtlCount(pmParam);
            list = totalschService.getAncmMtrDtlList(pmParam);
            break;

        case "nobd":
            total = totalschService.getNobdCount(pmParam);
            list = totalschService.getNobdList(pmParam);
            break;

        case "cons-scrt":
            pmParam.put("use_yn", "Y");

            total = totalschService.getConsScrtCount(pmParam);
            list = totalschService.getConsScrtList(pmParam);
            break;
        }

        oData.setTotal(total);
        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 상세정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/{dataType}/view")
    public ModelAndView getTotalschView(@PathVariable("dataType") String psDataType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> mData = new HashMap<String, Object>();

        System.out.println("psDataType >> " + psDataType);

        ParamUtils.addPagingParam(pmParam);
        pmParam.put("cntr_cd", "CCA");

        Map<String, Object> mView = null;
        Map<String, Object> mFileParam = new HashMap<String, Object>();

        switch(psDataType) {
        case "faq":
            mView = totalschService.getFaqView(pmParam);

            mFileParam.put("rltn_tbl_nm", "TB_FAQ_DTL");
            mFileParam.put("rltn_item_id", (String) pmParam.get("faq_id"));
            break;

        case "ancm-mtr-dtl":
            mView = totalschService.getAncmMtrDtlView(pmParam);

            mFileParam.put("rltn_tbl_nm", "TB_ANCM_MTR_DTL");
            mFileParam.put("rltn_item_id", (String) pmParam.get("ancm_mtr_id"));
            break;

        case "nobd":
            mView = totalschService.getNobdView(pmParam);

            mFileParam.put("rltn_tbl_nm", "TB_NOBD_DTL");
            mFileParam.put("rltn_item_id", (String) pmParam.get("nobd_id"));
            break;

        case "cons-scrt":
            mView = totalschService.getConsScrtView(pmParam);

            mFileParam.put("rltn_tbl_nm", "TB_CONS_SCRT_DTL");
            mFileParam.put("rltn_item_id", (String) pmParam.get("cons_scrt_id"));
            break;
        }

        // 첨부파일 조회
        List<Map<String, Object>> fileList = fileService.getFileList(mFileParam);

        mData.put("viewData", mView);
        mData.put("fileList", fileList);

        oResult.setData(mData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 즐겨찾기 정보를 검색한다.(통합검색)
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list-user/{pageType}")
    public ModelAndView getBkmkInfoListUser(@PathVariable("pageType") String pathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sBkmkDivCd = "";

        if ("useful".equals(pathType)) {			// 유용한 페이지
            sBkmkDivCd = "10";
        } else if ("related".equals(pathType)) {	// 관련 페이지
            sBkmkDivCd = "20";
        } else if ("indi".equals(pathType)) {	// 개인 페이지
            sBkmkDivCd = "30";
        }

        pmParam.put("bkmk_div_cd", sBkmkDivCd);
        pmParam.put("use_yn", "Y");
        pmParam.put("orderBy", "expe_sqnc");
        pmParam.put("orderDirection", "asc");

        // System.out.println("BKMKINFO PARAM :"+pmParam);

        List<Map<String, Object>> mList = bkmkInfoService.getBkmkInfoList(pmParam);

        oData.setTotal(mList.size());
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 즐겨찾기 정보를 등록한다.(통합검색 > 즐겨찾기 관리)
     *
     * @param pmParam List<Map<String, Object>>
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save-tot-sch")
    public ModelAndView saveBkmkInfoForTotSch(@RequestBody List<Map<String, Object>> pModels, @RequestParam Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if (pModels != null && pModels.size() > 0) {

            // 사용자 정보 조회
            Map<String, Object> mUser = userService.getUser(pmParam);

            Map<String, Object> mParam = new HashMap<String, Object>();
            // Add SaveParam
            mParam.put("rgsr_id", mUser.get("user_id"));
            mParam.put("rgsr_nm", mUser.get("user_nm"));
            mParam.put("amnd_id", mUser.get("user_id"));
            mParam.put("amnd_nm", mUser.get("user_nm"));
            mParam.put("cntr_cd", mUser.get("cntr_cd"));

            mParam.put("team2_cd", mUser.get("team_cd"));
            mParam.put("user_id", mUser.get("user_id"));

            for (Map<String, Object> mModel : pModels) {
                String sBkmkId = StringUtils.defaultString((String) mModel.get("bkmk_id"));

                mParam.put("bkmk_nm", mModel.get("bkmk_nm"));
                mParam.put("bkmk_addr", mModel.get("bkmk_addr"));
                mParam.put("bkmk_div_cd", "30");
                mParam.put("use_yn", "Y");

                if ("".equals(sBkmkId)) {
                    mParam.put("dup_fg", true);
                    mParam.put("expe_sqnc", 1);
                    mParam.put("old_bkmkinfo_ord", 999);

                    bkmkInfoService.insertBkmkInfo(mParam);
                } else {
                    mParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                    mParam.put("bkmk_id", mModel.get("bkmk_id"));

                    bkmkInfoService.updateBkmkInfo(mParam);
                }
            }
        }
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 즐겨찾기 정보를 삭제한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteBkmkInfo(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        bkmkInfoService.deleteBkmkInfo(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}
