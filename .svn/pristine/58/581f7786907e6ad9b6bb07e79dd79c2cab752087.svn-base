/*
 * (@)# FaqDtlController.java
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.FaqDtlService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * FAQ 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/14
 * @프로그램ID FaqDtl
 */

@Controller
@RequestMapping(value = "/knowledge/faq-dtl")
public class FaqDtlController {

    @Resource
    private FaqDtlService faqDtlService;

    @Resource
    private FileService fileService;

    /**
     * FAQ 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    @ResponseBody
    public ModelAndView getFaqList(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathType) throws Exception {
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


            // 상담사 화면에서 조회하는경우
            if ("usr".equals(psPathType)) {
                hmParam.put("open_stat_cd", "Y");
            }

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            Boolean bMineflag = Boolean.valueOf((String)hmParam.get("main_flag")).booleanValue();
            if (bMineflag != null && bMineflag) {
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

            int nTotal = faqDtlService.getFaqCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            ParamUtils.addCenterParam(hmParam);
            List<Map<String, Object>> mList = faqDtlService.getFaqList(hmParam);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * FAQ 정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @param ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public ModelAndView getFaq(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sFaqId = (String) hmParam.get("faq_id");
            String sPageType = (String) hmParam.get("page_typ");

            // 상담사 화면에서 조회할때만 조회수 업데이트
            if (!"admin".equals(sPageType)) {
                // 조회수 업데이트
                faqDtlService.updateInqCnt(sFaqId);
            }
            DataSetMap dsNotice = new DataSetMap();
            // FAQ 상세정보 조회
            Map<String, Object> mData = faqDtlService.getFaq(hmParam);
            dsNotice.setRowMaps(mData);
            mapOutDs.put("ds_output", dsNotice);

            // 첨부파일 조회
            //hmParam.put("rltn_item_id", sFaqId);
            //List<Map<String, Object>> mList = fileService.getFileList(hmParam);

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
     * FAQ 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveFaq(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);


                ParamUtils.addSaveParam(hmParam);

                String sFaq_id = StringUtils.defaultString((String) hmParam.get("faq_id"));

                // 에디터 내용 태그 제거한 추가 텍스트 컬럼 저장
                String sFaqDtlHtmlCntn = StringUtils.defaultString((String) hmParam.get("faq_ansr_html_cntn"));
                if ("".equals(sFaqDtlHtmlCntn)) {
                    hmParam.put("faq_ansr_cntn", "");
                } else {
                    hmParam.put("faq_ansr_cntn", CommonUtils.stripTag(sFaqDtlHtmlCntn));
                }

                ParamUtils.addSaveParam(hmParam);

                if ("".equals(sFaq_id)) {
                    hmParam.put("faq_chng_cntn", "신규등록");
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    String sAthrCd = oLoginUser.getAuthoritycd();
                    String sTeamCd = oLoginUser.getTeamcd();
                    if (sAthrCd.contains("TM")) {
                        hmParam.put("bzpt_div", sTeamCd);
                    } else {
                        hmParam.put("bzpt_div", "999999");
                    }

                    sFaq_id = faqDtlService.insertFaq(hmParam);
                } else {
                    faqDtlService.updateFaq(hmParam);
                }

                Map mParam  = new HashMap<String, Object>();
                mParam.put("faq_id", sFaq_id);

                Map<String, Object> mData = faqDtlService.getFaq(mParam);
                // FAQ 상세정보 조회
                DataSetMap dsNotice = new DataSetMap();

                dsNotice.setRowMaps(mData);
                mapOutDs.put("ds_output", dsNotice);
                mapOutVar.put("faq_id",sFaq_id);

            }
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
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
     * FAQ 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteFaq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();

            String sSelectChk = (String) mapInVar.get("selectcheck");
            hmParam.put("selectcheck", sSelectChk.split(","));

            faqDtlService.deleteFaq(hmParam);

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