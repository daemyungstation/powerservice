/*
 * (@)# TrctChprAdmnController.java
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

package powerservice.business.usr.web;

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

import powerservice.business.usr.service.TrctChprAdmnService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.14 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 이관담당자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID TrctChprAdmn
 */
@Controller
@RequestMapping(value = "/user/trct-chpr-admn")
public class TrctChprAdmnController {

    @Resource
    private TrctChprAdmnService trctChprAdmnService;

    @Resource
    private CommonService commonService;

    /**
     * 이관담당자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getTrctChprAdmnList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = trctChprAdmnService.getTrctChprAdmnCount(hmParam);
                mapOutVar.put("tc_trctChrpAdmn", nTotal);

                List<Map<String, Object>> mList = trctChprAdmnService.getTrctChprAdmnList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 이관담당자 정보를 수정한다.
     *
     * @param pmModelList List<Map<String, Object>>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveTrctChprAdmn(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for(int i =0; listInDs.size() > i; i++){
                hmParam = listInDs.get(i);

                String sRowCheck = StringUtils.defaultString((String) hmParam.get("_chk"));

                if (sRowCheck.equals("1")) {
                    ParamUtils.addSaveParam(hmParam);
                    trctChprAdmnService.saveTrctChprAdmn(hmParam);
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
     * 이관담당자 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save-user")
    public ModelAndView saveTrctChprAdmnListsaveEmsh(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for(int i =0; listInDs.size() > i; i++){
                hmParam = listInDs.get(i);
                /*수정자 : 정훈연구원
                요청자 : 정성안팀장
                수정일시 : 2016-08-16
                수정이유 : 이관박스 담당자 추가 X (파라미터명 변경)
                문의사항시 요청자에게 연락주세요*/
                String sRowCheck = StringUtils.defaultString((String) hmParam.get("_chk"));
                if("1".equals(sRowCheck)){
                    String sTrctBoxId = StringUtils.defaultString((String) hmParam.get("trct_box_id"));
                    if (sTrctBoxId != null) {
                        ParamUtils.addSaveParam(hmParam);
                        trctChprAdmnService.insertTrctChprAdmn(hmParam);
                    }
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
/*
    @RequestMapping(value = "/save-user")
    public ModelAndView saveTrctChprAdmnList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sTrctBoxId = StringUtils.defaultString((String) pmParam.get("trct_box_id"));
        if (sTrctBoxId != null) {
            ParamUtils.addSaveParam(pmParam);
            trctChprAdmnService.insertTrctChprAdmn(pmParam);
        }
        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

    /**
     * 이관담당자 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/delete")
    public ModelAndView deleteTrctChprAdmn(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sSelectCheck = StringUtils.defaultString((String) hmParam.get("selectcheck"));
            hmParam.put("selectcheck", sSelectCheck.split(","));
            ParamUtils.addCenterParam(hmParam);

            trctChprAdmnService.deleteTrctChprAdmn(hmParam);


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
    public ModelAndView deleteTrctChprAdmn(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        trctChprAdmnService.deleteTrctChprAdmn(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

    /**
     * 이관담당자 정보를 검색한다. (이관박스현황에서 사용)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trns-list")
    public ModelAndView getTrnsUserForTrnsbox(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        List<Map<String, Object>> mdataList = trctChprAdmnService.getTrnsUserForTrnsbox(pmParam);

        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}