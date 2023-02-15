/*
 * (@)# AncmMtrDtlController.java
 *
 * @author 차건호
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
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.AncmMtrDtlService;
import powerservice.business.kms.service.AncmTrgtAthrService;
import powerservice.business.kms.service.AncmTrgtTeamService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 공지사항 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID AncmMtrDtl
 */
@Controller
@RequestMapping(value = "/knowledge/ancm-mtr-dtl")
public class AncmMtrDtlController {

    @Resource
    private AncmMtrDtlService ancmMtrDtlService;

    @Resource
    private AncmTrgtAthrService ancmTrgtAthrService;

    @Resource
    private AncmTrgtTeamService ancmTrgtTeamSercvice;

    @Resource
    private FileService fileService;

    /**
     * (테스트)샘플정보를 검색한다.
     *
     * @param  XPlatformMapDTO xpDto, Model model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    public ModelAndView getAncmMtrDtlList(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathTyp) throws Exception {
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

            //System.out.println("mapInVar.toString() => " + mapInVar.toString());
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

            // 세션정보 설정
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

            if (psPathTyp.indexOf("usr") > -1) { // 메인화면, 상담사 화면에서 사용
                hmParam.put("use_yn", "Y");

                //hmParam.put("athr_cd", oLoginUser.getAuthoritycd());
                //hmParam.put("team_cd", oLoginUser.getTeamcd());
            } else { // 관리자 화면에서 사용
                Boolean bMainFlag = Boolean.valueOf((String)hmParam.get("main_flag")).booleanValue();
                if (bMainFlag != null && bMainFlag) {
                    hmParam.put("rgsr_id", oLoginUser.getUserid());
                }
            }

            int nTotal = ancmMtrDtlService.getAncmMtrDtlCount(hmParam);
            mapOutVar.put("tc_ancm_mtr", nTotal);

            List<Map<String, Object>> mList = ancmMtrDtlService.getAncmMtrDtlList(hmParam);
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

    /**
     * 공지사항 정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getAncmMtrDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAncmMtrId = (String)hmParam.get("ancm_mtr_id");
            String sPageType = (String)hmParam.get("pageType");
            String sModYn = (String)hmParam.get("mod_yn");

            // 상담사 화면에서 조회할때만 조회수 업데이트
            if (!"admin".equals(sPageType)) {
                // 조회수 업데이트
                ancmMtrDtlService.updateAncmMtrDtlViewCnt(sAncmMtrId);
            }

            DataSetMap dsNotice = new DataSetMap();
            Map<String, Object> mNotice = ancmMtrDtlService.getAncmMtrDtl(hmParam);
            dsNotice.setRowMaps(mNotice);
            mapOutDs.put("ds_output", dsNotice);

            DataSetMap dsAthr = new DataSetMap();
            DataSetMap dsTeam = new DataSetMap();

            if ("Y".equals(sModYn)) {
                List<Map<String, Object>> athrList = ancmTrgtAthrService.getAncmTrgtAthrList(sAncmMtrId);
                dsAthr.setRowMaps(athrList);

                List<Map<String, Object>> teamList = ancmTrgtTeamSercvice.getAncmTrgtTeamList(sAncmMtrId);
                dsTeam.setRowMaps(teamList);
            }

            mapOutDs.put("ds_athrList", dsAthr); //권한
            mapOutDs.put("ds_teams", dsTeam); //팀
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

    /**
     * 공지사항 정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView insertAncmMtrDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
               hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);

            String sAncmMtrId =  StringUtils.defaultString((String) hmParam.get("ancm_mtr_id"));

            // 에디터 내용 태그 제거한 추가 텍스트 컬럼 저장
            String sAncmMtrHtmlCntn =  StringUtils.defaultString((String) hmParam.get("ancm_mtr_html_cntn"));
            if ("".equals(sAncmMtrHtmlCntn)) {
                hmParam.put("ancm_mtr_cntn", "");
            } else {
                hmParam.put("ancm_mtr_cntn", CommonUtils.stripTag(sAncmMtrHtmlCntn));
            }

            if ( "".equals(sAncmMtrId)){
                sAncmMtrId =  ancmMtrDtlService.insertAncmMtrDtl(hmParam);
            } else{
                ancmMtrDtlService.updateAncmMtrDtl(hmParam);
            }

            mapOutVar.put("sAncmMtrId", sAncmMtrId);

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

    /**
     * 공지사항 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteAncmMtrDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sSelectChk = (String) mapInVar.get("selectcheck");
            hmParam.put("selectcheck", sSelectChk.split(","));

            ancmMtrDtlService.deleteAncmMtrDtl(hmParam);

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

}