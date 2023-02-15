/*
 * (@)# ExccConsExmpController.java
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

package powerservice.business.cns.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ExccConsExmpService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 우수상담사례 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/15
 * @프로그램ID ExccConsExmp
 */
@Controller
@RequestMapping(value = "/knowledge/excc-cons-exmp")
public class ExccConsExmpController {

    @Resource
    private ExccConsExmpService exccConsExmpService;

    @Resource
    private FileService fileService;

    /**
     * 우수상담사례 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExccConsExmpList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Boolean bMainFlag = Boolean.valueOf((String)hmParam.get("mine_fg")).booleanValue();

            if (bMainFlag != null && bMainFlag) {
                UserSession user = (UserSession) SessionUtils.getLoginUser();

                hmParam.put("rgsr_id", user.getUserid());
            }

            // 통합검색인 경우 표시여부 "Y" 건 조회
            String sTotlSrchYn = StringUtils.defaultString((String) hmParam.get("totl_srch_yn"));
            if ("Y".equals(sTotlSrchYn)) {
                hmParam.put("expe_yn", "Y");
            }

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
            /*
            String sBzptDivYn = StringUtils.defaultString((String) hmParam.get("bzpt_div_yn"));
            if ("Y".equals(sBzptDivYn)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }
            */

            ParamUtils.addCenterParam(hmParam);

            int nTotal = exccConsExmpService.getExccConsExmpCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> list = exccConsExmpService.getExccConsExmpList(hmParam);
            listMap.setRowMaps(list);
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
     * 우수상담사례 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView updateExccConsExmp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

            // 중복처리방지 TOKEN 체크
            /* if (!TokenManager.checkSubmitToken(oResult, pmParam)) {
                modelAndView.addObject(oResult);
                return modelAndView;
            }*/

            String sExccConsId = StringUtils.defaultString((String) hmParam.get("excc_cons_id"));

            // 에디터 내용 태그 제거한 추가 텍스트 컬럼 저장
            String sExccConsHtmlCntn = StringUtils.defaultString((String) hmParam.get("excc_cons_html_cntn"));
            if ("".equals(sExccConsHtmlCntn)) {
                hmParam.put("excc_cons_cntn", "");
            } else {
                hmParam.put("excc_cons_cntn", CommonUtils.stripTag(sExccConsHtmlCntn));
            }


            ParamUtils.addSaveParam(hmParam);

            if ("".equals(sExccConsId)) { // 등록인 경우
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
                /*
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_div", sTeamCd);
                } else {
                    hmParam.put("bzpt_div", "999999");
                }
                */

                sExccConsId = exccConsExmpService.insertExccConsExmp(hmParam);
            } else {
                exccConsExmpService.updateExccConsExmp(hmParam);
            }

            Map<String, Object> mData = exccConsExmpService.getExccConsExmp(sExccConsId);
            DataSetMap dsList = new DataSetMap();

            dsList.setRowMaps(mData);
            mapOutVar.put("sExccConsId", sExccConsId);
            mapOutDs.put("ds_output", dsList);

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
     * 우수상담상세정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getExccConsExmp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sExccConsId = (String) hmParam.get("excc_cons_id");

            // FAQ 상세정보 조회
            Map<String, Object> mData2 = exccConsExmpService.getExccConsExmp(sExccConsId);

            DataSetMap dsMap = new DataSetMap();

            dsMap.setRowMaps(mData2);
            mapOutDs.put("ds_output", dsMap);

            // 첨부파일 조회
            hmParam.put("rltn_item_id",sExccConsId);
            //List<Map<String, Object>> list = fileService.getFileList(hmParam);
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
     * 우수상담사례 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExccConsExmp(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        // 중복처리방지 TOKEN 체크
        /*if (!TokenManager.checkSubmitToken(oResult, pmParam)) {
            modelAndView.addObject(oResult);
            return modelAndView;
        }*/
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            String sSelectChk = StringUtils.defaultString((String) mapInVar.get("selectcheck"));

            if(sSelectChk != null && sSelectChk != ""){
                // 여러건일때
                hmParam.put("selectcheck", sSelectChk.split(","));
            } else{
                // 1건일때
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                hmParam = listInDs.get(0);
                String sExccConsId = StringUtils.defaultString((String) hmParam.get("excc_cons_id"));
                hmParam.put("rltn_item_id", sExccConsId);
            }
            exccConsExmpService.deleteExccConsExmp(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        return modelAndView;
    }

}