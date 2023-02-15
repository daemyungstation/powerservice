/*
 * (@)# NobdTypController.java
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.kms.service.NobdTypService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 게시판을 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/17
 * @프로그램ID NobdTyp
 */

@Controller
@RequestMapping(value = "/knowledge/nobd-typ")
public class NobdTypController {

    @Resource
    private NobdTypService nobdTypService;

    /**
     * 게시판 정보를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getNobdTypList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            int nTotal = nobdTypService.getNobdTypCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mNobdTypList = nobdTypService.getNobdTypList(hmParam);
            listMap.setRowMaps(mNobdTypList);
            mapOutDs.put("ds_output", listMap);

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
     * 게시판 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveNobdTyp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sNobdTypId = StringUtils.defaultString((String) hmParam.get("nobd_typ_id"));
            Object oPageIndx = hmParam.get("page_indx");


            ParamUtils.addSaveParam(hmParam);


            Map<String, String> mSearchParam = new HashMap<String, String>();
            mSearchParam.put("nobd_typ_id", sNobdTypId);

            if (oPageIndx == null) {
                // 서버ID 중복 확인
                int nCount = nobdTypService.getNobdTypCount(mSearchParam);
                if (nCount > 0 ) {
                    szErrorCode = "-1";
                    szErrorMsg = "동일한 게시판 코드가 존재합니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                sNobdTypId = nobdTypService.insertNobdTyp(hmParam);
            } else {
                nobdTypService.updateNobdTyp(hmParam);
            }
            mSearchParam.put("nobd_typ_id", sNobdTypId);

            Map<String, Object> mData = nobdTypService.getNobdTyp(mSearchParam);
            DataSetMap dsNotice = new DataSetMap();

            dsNotice.setRowMaps(mData);
            mapOutDs.put("ds_output", dsNotice);

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
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "delete")
    @ResponseBody
    public ModelAndView deleteNobdTyp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //String sSelectCk = StringUtils.defaultString((String) hmParam.get("selectcheck"));
            //hmParam.put("selectcheck", sSelectCk.split(","));

            nobdTypService.deleteNobdTyp(hmParam);
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
     * 게시판 등록관리의 정보에 따른 게시판 화면을 호출한다.
     *
     * @param psViewType String
     * @param psNobdId String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view/{viewType}/{nobdId}")
    public ModelAndView viewNobdByNobdTypRoleCd(@PathVariable("viewType") String psViewType, @PathVariable("nobdId") String psNobdId, @RequestParam Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        String sViewName = "";
        if ("admin".equals(psViewType)) {  // 관리업무 > 상담자료관리 > 게시판관리
            sViewName = "/knowledge/nobd/nobd";
        } else if ("init-admin".equals(psViewType)) {  // 관리업무 > 센터현황 > 게시판
            sViewName = "/main/div/admin-nobd-div";
        } else if ("init-usr".equals(psViewType)) {  // 상담업무 > 초화면 > 게시판
            sViewName = "/main/div/init-nobd-div";
        } else if ("list-popup".equals(psViewType)) {  // 공통화면 > 게시판 목록 팝업
            modelAndView.addObject("searchValue", pmParam.get("searchValue"));

            sViewName = "/knowledge/nobd/nobd-list-popup";
        } else if ("view-popup".equals(psViewType)) {  // 공통화면 > 게시판 상세 팝업
            modelAndView.addObject("nobd_id", pmParam.get("nobd_id"));
            modelAndView.addObject("searchValue", pmParam.get("searchValue"));

            sViewName = "/knowledge/nobd/nobd-view-popup";
        }

        if (!"all".equals(psNobdId)) {
            Map<String, Object> mSearchParam = new HashMap<String, Object>();
            mSearchParam.put("nobd_typ_id", psNobdId);
            modelAndView.addObject("nobdTyp", nobdTypService.getNobdTyp(mSearchParam));
        }

        modelAndView.setViewName(sViewName);

        return modelAndView;
    }

}