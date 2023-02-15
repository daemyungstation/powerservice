/*
 * (@)# ConsTrctHstrController.java
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

package powerservice.business.cns.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.BswrDmndDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.14 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 업무요청내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BswrDmndDtl
 */
@Controller
@RequestMapping(value = "/cons/bswr-dmnd-dtl")
public class BswrDmndDtlController {

    @Resource
    private BswrDmndDtlService bswrDmndDtlService;

    @Resource
    private CommonService commonService;

    /**
     * 이관업무 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dsps/{pageType}")
    public ModelAndView getDpmsReslDsps(@PathVariable("pageType") String pageType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        Map<String, Object> oData = null;

        pmParam.put("pageType",pageType);
        oData = bswrDmndDtlService.getDpmsReslDsps(pmParam);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 이관대상목록 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trct-cons-list/{pagetype}")
    public ModelAndView getTrctConsList(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
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

            UserSession oUser = (UserSession) SessionUtils.getLoginUser();

            ParamUtils.addCenterParam(hmParam);

            // 이관상담처리(담당자) - 본인 담당 이관상담만 조회
            if ("usr".equals(psPathType)) {
                hmParam.put("trct_chpr_id", oUser.getUserid());
            } else if ("todo".equals(psPathType)) {
                hmParam.put("trct_actp_id", oUser.getUserid());
            }

            // 경로 구분 저장
            hmParam.put("path_typ", psPathType);

            int nTotal = bswrDmndDtlService.getTrctConsCount(hmParam);
            mapOutVar.put("tc_trctCons", nTotal);

            List<Map<String, Object>> mList = bswrDmndDtlService.getTrctConsList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////


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
     * 이관상담 이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trct-cons-hstr/{pagetype}")
    public ModelAndView getTrctConsHstr(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 경로 구분 저장
            hmParam.put("path_typ", psPathType);

            int nTotal = bswrDmndDtlService.getTrctConsHstrCount(hmParam);
            mapOutVar.put("tc_trctHstr", nTotal);

            List<Map<String, Object>> mList = bswrDmndDtlService.getTrctConsHstr(hmParam);
            listMap.setRowMaps(mList);
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
     * 이관상담 정보를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveBswrDmndDsps(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            ParamUtils.addSaveParam(hmParam);

            int nBswrDmndDtl = bswrDmndDtlService.saveBswrDmndDsps(hmParam);

            if(nBswrDmndDtl == -1){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "BSWR");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "이미 접수중인 이관 입니다.");
                    return modelAndView;
            }else if(nBswrDmndDtl == -3){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "BSWR");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "처리완료건 처리불가건은 수정이 불가능 합니다.");

                return modelAndView;
            }
            Map<String, Object> mList  = bswrDmndDtlService.getTrctCons(hmParam);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 이관상담 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteBswrDmnd(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            bswrDmndDtlService.deleteBswrDmnd(hmParam);

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 이관상담 차트 데이터를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chart-list")
    public ModelAndView getTrctConsChartWeeksList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        pmParam.put("trct_chpr_id", oLoginUser.getUserid());

        oResult.setData(bswrDmndDtlService.getTrctConsChartWeeksList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 해당 이관상담의 담당자를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/check-trct-chpr")
    public ModelAndView checkTrctChpr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        oResult.setData(bswrDmndDtlService.checkTrctChpr(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 이관상담 처리상태 콤보박스 리스트를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/drop-down-list")
    public ModelAndView getTrctConsDropDownList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        Map<String, Object> searchParam = ParamUtils.convertDropDownParam(pmParam);

        int total = bswrDmndDtlService.getTrctConsStatCount(searchParam);
        List<Map<String, Object>> list = bswrDmndDtlService.getTrctConsStatList(searchParam);

        oData.setTotal(total);
        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}