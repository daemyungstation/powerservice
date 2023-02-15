/*
 * (@)# NobdController.java
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
import powerservice.business.kms.service.NobdService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 게시판 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Nobd
 */
@Controller
@RequestMapping(value = "/knowledge/nobd")
public class NobdController {

    @Resource
    private NobdService nobdService;

    @Resource
    private FileService fileService;

    @Resource
    private BasVlService basVlService;

    /**
     * 게시판 정보(목록)를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    public ModelAndView getNobdList(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathTyp) throws Exception {
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

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            // 내 등록건
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            Boolean bMineflag = Boolean.valueOf((String)hmParam.get("mine_flag")).booleanValue();
            if (bMineflag != null && bMineflag) {
                hmParam.put("rgsr_id", oLoginUser.getUserid());
            }


            // 업체구분 조회
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
            /*
            String sBzptDivYn = StringUtils.defaultString((String) hmParam.get("bzpt_div_yn"));
            if ("Y".equals(sBzptDivYn)) {
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }
            */

           if ("top10".equals(psPathTyp)) {  // 메인화면, 상담사 화면에서 사용
               hmParam.put("use_yn", "Y");
               hmParam.put("orderBy", "amnt_dttm");
               hmParam.put("orderDirection", "desc");
               hmParam.put("startLine", "1");
               hmParam.put("endLine", "11");
            }

            int nTotal = nobdService.getNobdCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = nobdService.getNobdList(hmParam);
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
     * 게시판 상세정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getNobd(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sNobdId = (String) hmParam.get("nobd_id");

            // FAQ 상세정보 조회
            Map<String, Object> mNobd = nobdService.getNobd(hmParam);

            DataSetMap dsNotice = new DataSetMap();

            dsNotice.setRowMaps(mNobd);

            mapOutDs.put("ds_output", dsNotice);

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
     * 게시판 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveNobd(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            ParamUtils.addSaveParam(hmParam);

            /*String sNobdTypId = StringUtils.defaultString((String) hmParam.get("nobd_typ_id"));

            if("".equals(sNobdTypId)) {
                sNobdTypId = basVlService.getBasVlAsString("nobd_typ_id_basi");
                hmParam.put("nobd_typ_id", sNobdTypId);
            }*/

            String sNobdId = StringUtils.defaultString((String) hmParam.get("nobd_id"));
            // 에디터 내용 태그 제거한 추가 텍스트 컬럼 저장
            String sNobdHtmlCntn = StringUtils.defaultString((String) hmParam.get("nobd_html_cntn"));
            if ("".equals(sNobdHtmlCntn)) {
                hmParam.put("nobd_cntn", "");
            } else {
                hmParam.put("nobd_cntn", CommonUtils.stripTag(sNobdHtmlCntn));
            }

            if ("".equals(sNobdId)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
                /*
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_div", sTeamCd);
                } else {
                    hmParam.put("bzpt_div", "999999");
                }*/

                sNobdId = nobdService.insertNobd(hmParam);
            } else {
                nobdService.updateNobd(hmParam);
            }

            mapOutVar.put("sNobdId", sNobdId);

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
     * 게시판 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteNobd(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sSelectChk = StringUtils.defaultString((String) mapInVar.get("selectcheck"));

            if(sSelectChk != null && sSelectChk != ""){
                // 여러건일때
                hmParam.put("selectcheck", sSelectChk.split(","));
            } else{
                // 1건일때
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                hmParam = listInDs.get(0);
                String sNobdId = StringUtils.defaultString((String) hmParam.get("nobd_id"));
                hmParam.put("rltn_item_id", sNobdId);
            }

            nobdService.deleteNobd(hmParam);
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