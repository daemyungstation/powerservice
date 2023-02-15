/*
 * (@)# EvltReslDtlController.java
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

package powerservice.business.evl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ExccConsExmpService;
import powerservice.business.evl.service.EvltAckdDtlService;
import powerservice.business.evl.service.EvltReslDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 평가유형을 관리를 한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author    최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvltReslDtl
 */
@Controller
@RequestMapping(value = "/evlt-resl-dtl")
public class EvltReslDtlController {

    @Resource
    private EvltReslDtlService evltReslDtlService;

    @Resource
    private ExccConsExmpService exccConsExmpService;

    @Resource
    private EvltAckdDtlService evltAckdDtlService;

    /**
     * 평가결과 정보(목록)를 검색한다.
     * MyPage - QA평가결과조회(상담별)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/cons/list")
    public ModelAndView getEvltReslDtlListByCons(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
            ParamUtils.addCenterParam(hmParam);

            String sUserYn = StringUtils.defaultString((String)hmParam.get("user_yn"));
            if ("Y".equals(sUserYn)) {
                UserSession user = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("trpr_id", user.getUserid());
            }

            //int nTotal = evltReslDtlService.getEvltReslDtlCountByCons(pmParam);
            List<Map<String, Object>> mList = evltReslDtlService.getEvltReslDtlListByCons(hmParam);
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
     * 평가결과 정보(목록)를 검색한다.
     * MyPage - QA평가결과조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEvltReslDtlList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);
            ParamUtils.addPagingParam(hmParam);

            int nTotal = evltReslDtlService.getEvltReslDtlCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            List<Map<String, Object>> mList = evltReslDtlService.getEvltReslDtlList(hmParam);
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
     * 평가수행-평가지 상세정보를 조회한다.(1건)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView view(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(param);
        oResult.setData(evltReslDtlService.getQaResultSheet(param));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 평가지 결과정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveEvltReslDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sEvltReslId = StringUtils.defaultString((String) hmParam.get("evlt_resl_id"));
            String sOldRcmdTrgtCallYn = StringUtils.defaultString((String) hmParam.get("old_rcmd_trgt_call_yn"));
            String sRcmdTrgtCallYn = StringUtils.defaultString((String) hmParam.get("rcmd_trgt_call_yn"));

            ParamUtils.addSaveParam(hmParam);
            UserSession user = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("evlp_id", user.getUserid());      // 평가자

            if ("".equals(sEvltReslId)) {
                sEvltReslId = evltReslDtlService.insertEvltReslDtl(hmParam);

                if ("Y".equals(sRcmdTrgtCallYn)) {
                    hmParam.put("bestcselid", sEvltReslId);  // 우수상담사례
                    exccConsExmpService.insertEvltExccConsExmp(hmParam);// 우수상담 등록
                }
            } else {
                evltReslDtlService.updateEvltReslDtl(hmParam);
                hmParam.put("bestcselid", sEvltReslId);  // 우수상담사례

                if ("N".equals(sOldRcmdTrgtCallYn) && "Y".equals(sRcmdTrgtCallYn)) {
                    exccConsExmpService.insertEvltExccConsExmp(hmParam);// 우수상담 등록
                }
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

    /**
     * 평가자승인(이의제기) 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/ackd-dtl-save")
    public ModelAndView saveEvltAckdDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sEvltAckdStatCd = StringUtils.defaultString((String) hmParam.get("evlt_ackd_stat_cd"));

            ParamUtils.addSaveParam(hmParam);
            UserSession user = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("evlp_id", user.getUserid());      // 평가자

            if ("20".equals(sEvltAckdStatCd)) {
                evltReslDtlService.updateEvltReslDtl(hmParam);
                //pmParam.put("bestcselid", sEvltReslId);    // 우수상담사례
            }
            evltAckdDtlService.updateEvltAckdDtl(hmParam);    // 승인관리 처리사유

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
}