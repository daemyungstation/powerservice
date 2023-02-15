/*
 * (@)# FaqDmndController.java
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

package powerservice.business.kms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.FaqDmndService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.21 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * FAQ 요청 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/13
 * @프로그램ID FaqDmnd
 */

@Controller
@RequestMapping(value = {"/mypage/faq-dmnd", "/knowledge/faq-dmnd"})
public class FaqDmndController {

    @Resource
    private FaqDmndService faqDmndService;

    @Resource
    private FileService fileService;

    @Resource
    private CommonService commonService;

    /**
     * FAQ 요청 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    @ResponseBody
    public ModelAndView getFaqDmndList(@PathVariable("pageType") String pathType, XPlatformMapDTO xpDto, Model model) throws Exception {
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

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

            if ("admin".equals(pathType)) {
                hmParam.put("admin_fg", true);
            } else {
                hmParam.put("rgsr_id", oLoginUser.getUserid());
            }

            String sBzptDivYn = StringUtils.defaultString((String) hmParam.get("bzpt_div_yn"));
            if ("Y".equals(sBzptDivYn)) {
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = faqDmndService.getFaqDmndCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = faqDmndService.getFaqDmndList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        } catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * FAQ 요청정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public ModelAndView getFaqDmnd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            Map hmParam = listInDs.get(0);

            ParamUtils.addCenterParam(hmParam);

            // FAQ 상세정보 조회
            Map<String, Object> mData = faqDmndService.getFaqDmnd(hmParam);

            // 첨부파일 조회
            hmParam.put("rltn_item_id", (String) hmParam.get("faq_dmnd_id"));
            List<Map<String, Object>> mList = fileService.getFileList(hmParam);

            DataSetMap dsList = new DataSetMap();
            dsList.setRowMaps(mData);
            mapOutDs.put("ds_output", dsList);
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }catch (Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * FAQ 요청 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/{pageType}")
    @ResponseBody
    public ModelAndView saveFaqReq(@PathVariable("pageType") String pathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                String sFaq_dmnd_id = StringUtils.defaultString((String) hmParam.get("faq_dmnd_id"));


                // 에디터 내용 태그 제거한 추가 텍스트 컬럼 저장
                String sFaqAnsrHtmlCntn = StringUtils.defaultString((String) hmParam.get("faq_ansr_html_cntn"));
                if ("".equals(sFaqAnsrHtmlCntn)) {
                    hmParam.put("faq_ansr_cntn", "");
                } else {
                    hmParam.put("faq_ansr_cntn", CommonUtils.stripTag(sFaqAnsrHtmlCntn));
                }

                ParamUtils.addSaveParam(hmParam);

                if ("".equals(sFaq_dmnd_id)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    String sAthrCd = oLoginUser.getAuthoritycd();
                    String sTeamCd = oLoginUser.getTeamcd();
                    if (sAthrCd.contains("TM")) {
                        hmParam.put("bzpt_div", sTeamCd);
                    } else {
                        hmParam.put("bzpt_div", "999999");
                    }

                    sFaq_dmnd_id = faqDmndService.insertFaqDmnd(hmParam);
                } else{

                    hmParam.put("pathType", pathType);
                    faqDmndService.updateFaqDmnd(hmParam);
                }

                Map mParam  = new HashMap<String, Object>();
                mParam.put("faq_dmnd_id", sFaq_dmnd_id);

                Map<String, Object> mData =faqDmndService.getFaqDmnd(mParam);
                DataSetMap dsView = new DataSetMap();

                dsView.setRowMaps(mData);
                mapOutDs.put("ds_output", dsView);
            }

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
     * FAQ 상태를 변경한다
     *
     * @param pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/update-stat")
    @ResponseBody
    public ModelAndView updateFaqDmndStat(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        String sFaq_dmnd_id = StringUtils.defaultString((String) pmParam.get("faq_dmnd_id"));

        ParamUtils.addSaveParam(pmParam);

        pmParam.put("faq_dsps_stat_cd", "30");    // 확인중 상태로 변경

        int nResultCnt = faqDmndService.updateFaqDmndStat(pmParam);

        if (nResultCnt == 0) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("처리 권한이 없습니다.");
        } else {
            Map<String, Object> mParam = ParamUtils.getCenterParam();
            mParam.put("faq_dmnd_id", sFaq_dmnd_id);

            oResult.setData(faqDmndService.getFaqDmnd(mParam));
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * FAQ 요청 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteFaqDmnd(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sFaq_dmnd_id = StringUtils.defaultString((String) hmParam.get("faq_dmnd_id"));

            faqDmndService.deleteFaqDmnd(sFaq_dmnd_id);

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
    @ResponseBody
    public ModelAndView deleteFaqDmnd(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sFaq_dmnd_id = StringUtils.defaultString((String) pmParam.get("faq_dmnd_id"));

        faqDmndService.deleteFaqDmnd(sFaq_dmnd_id);

        modelAndView.addObject(oResult);
        return modelAndView;
    }*/

}