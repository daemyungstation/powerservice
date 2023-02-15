/*
 * (@)# TrgtCustAlctController.java
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

package powerservice.business.cam.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cam.service.DpmsReslHstrService;
import powerservice.business.cam.service.ExtcMstTrgtService;
import powerservice.business.cam.service.TrgtCustAlctService;
import powerservice.business.cam.service.impl.TrgtCustDAO;
import powerservice.business.cns.service.CtiCrncHstrService;
import powerservice.business.onln.service.ExtcTrgtService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.usr.service.impl.UserDAO;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.21 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 캠페인대상고객할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID TrgtCustAlct
 */

@Controller
@RequestMapping(value="/cmpg/trgt-cust-alct")
public class TrgtCustAlctController {

    @Resource
    private TrgtCustAlctService trgtCustAlctService;

    @Resource
    private DpmsReslHstrService dpmsReslHstrService;

    @Resource
    private CtiCrncHstrService ctiCrncHstrService;

    @Resource
    public BasVlService basVlService;

    @Resource
    private CommonService commonService;

   // @Resource
   // public ExtcTrgtDAO extcTrgtDAO;

    @Resource
    public ExtcTrgtService extcTrgtService;


    @Resource
    public TrgtCustDAO trgtCustDAO;

    @Resource
    public UserDAO userDAO;

    @Resource
    public ExtcMstTrgtService extcMstTrgtService;

    /**
     * 캠페인대상고객할당 정보를 등록/수정한다.(ajax) --!사용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/alct")
    @ResponseBody
    public ModelAndView insertTgtCustAssign(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("selectcheck",mapInVar.get("selectcheck"));
            hmParam.put("cmpg_id", mapInVar.get("cmpg_id"));
            hmParam.put("trgt_list_id",mapInVar.get("trgt_list_id"));
            hmParam.put("sub_trgt_list_id",mapInVar.get("sub_trgt_list_id"));
            hmParam.put("cmpg_div_cd",mapInVar.get("cmpg_div_cd"));
            hmParam.put("cmpg_typ_cd",mapInVar.get("cmpg_typ_cd"));
            hmParam.put("mode",mapInVar.get("mode"));
            hmParam.put("rdasnum",mapInVar.get("rdasnum"));
            hmParam.put("rdwdnum",mapInVar.get("rdwdnum"));

            String sMode = StringUtils.defaultString((String) hmParam.get("mode"));
            ParamUtils.addSaveParam(hmParam);

            if (sMode.equals("EQAS") || sMode.equals("RDAS")) {
                trgtCustAlctService.insertTgtCustAssign(hmParam);
            } else if (sMode.equals("EQWD") || sMode.equals("RDWD")) {
                trgtCustAlctService.deleteTgtcustassign(hmParam);
            }

            int alct_count = trgtCustAlctService.getAlctCount(hmParam);
            int totalassignnum = trgtCustAlctService.getTrgtCustAlctCount(hmParam);

            mapOutVar.put("gv_totalassignnum", totalassignnum);
            mapOutVar.put("gv_alct_count", alct_count);

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
     * 고객기준의 할당 사용 (선택할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/alct")
    @ResponseBody
    public ModelAndView saveTrgtCustAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id

            String sCmpgDivCd = StringUtils.defaultString((String)mapInVar.get("cmpg_div_cd")); //상위
            String sCmpgTypCd = StringUtils.defaultString((String)mapInVar.get("cmpg_typ_cd")); //하위


            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);
            hmParam.put("sCmpgDivCd",sCmpgDivCd);
            hmParam.put("sCmpgTypCd",sCmpgTypCd);

            trgtCustAlctService.insertSelectAlct(hmParam, listInDs);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            Map<String, Object> mParam = hmParam ;

            mParam.remove("trgt_cust_dtpt_id");
            mParam.remove("cnsr_id");

            int totalassignnum = trgtCustAlctService.getTrgtCustAlctCount(hmParam);

            mapOutVar.put("gv_totalassignnum", totalassignnum);

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
     * 고객기준의 할당 사용 (선택할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/newtypalct")
    @ResponseBody
    public ModelAndView saveNewTypeTrgtCustAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id
            
            int rtnCnt = 0;

            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);

            rtnCnt = trgtCustAlctService.insertNewTypeSelectAlct(hmParam, listInDs);
            
            System.out.println(">>>>>>>>>>>> " + rtnCnt);
            
            //분배한 대상자 숫자 결과
            mapOutVar.put("intProcCnt", rtnCnt);
            
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
     * 고객기준의 할당 사용 (수치할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/numalct")
    @ResponseBody
    public ModelAndView saveTrgtCustNumAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id

            String sCmpgDivCd = StringUtils.defaultString((String)mapInVar.get("cmpg_div_cd")); //상위
            String sCmpgTypCd = StringUtils.defaultString((String)mapInVar.get("cmpg_typ_cd")); //하위
            String sAlctNum = StringUtils.defaultString((String)  mapInVar.get("nAlctNum")); //하위

            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);
            hmParam.put("sCmpgDivCd",sCmpgDivCd);
            hmParam.put("sCmpgTypCd",sCmpgTypCd);
            hmParam.put("sAlctNum",sAlctNum);

            trgtCustAlctService.insertNumSelectAlct(hmParam,listInDs);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            Map<String, Object> mParam = hmParam ;

            mParam.remove("trgt_cust_dtpt_id");
            mParam.remove("cnsr_id");

            int totalassignnum = trgtCustAlctService.getTrgtCustAlctCount(hmParam);

            mapOutVar.put("gv_totalassignnum", totalassignnum);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());



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
     * 고객기준의 할당 사용 (수치할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/newtypenumalct")
    public ModelAndView saveNewTypeTrgtCustNumAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sAlctNum = StringUtils.defaultString((String)mapInVar.get("nAlctNum")); //하위

            hmParam.put("sCmpgId",sCmpgId);
            hmParam.put("sCnsrId",sCnsrId);            
            hmParam.put("sAlctNum",sAlctNum); // 수치할당 수

            trgtCustAlctService.insertNewTypeNumSelectAlct(hmParam,listInDs);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }


    /**
     * 고객별 아웃바운드 이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-hstr-list")
    @ResponseBody
    public ModelAndView getCustHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            int nTotal = trgtCustAlctService.getCustHstrCount(hmParam);
            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getCustHstrList(hmParam);

            mapOutVar.put("total_count", nTotal);
            listMap.setRowMaps(mTrgtCustAlctList);
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
     * 중복할당된 list 를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dup-list")
    @ResponseBody
    public ModelAndView getDupAlctCustList(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            ParamUtils.addCenterParam(hmParam);


            int nTotal = trgtCustAlctService.getDupAlctCount(hmParam);
            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getDupAlctList(hmParam);

            mapOutVar.put("total_count", nTotal);
            listMap.setRowMaps(mTrgtCustAlctList);
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
     * 상담사별 할당 대상고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getTrgtCustAlctList(XPlatformMapDTO xpDto, Model mode) throws Exception {
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
            ParamUtils.addPagingParam(hmParam);
            int nTotal = trgtCustAlctService.getTrgtCustAlctCount(hmParam);
            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getTrgtCustAlctList(hmParam);

            listMap.setRowMaps(mTrgtCustAlctList);

            mapOutVar.put("total_count", nTotal);
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
     * 캠페인 결과를 저장한다. >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ModelAndView updateCmpgResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                
                System.out.println("------------------------------------------------------------1");


                ParamUtils.addSaveParam(hmParam);
                UserSession user = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("user_id", user.getUserid());
                
                System.out.println("------------------------------------------------------------1");

                trgtCustAlctService.updateCmpgResult(hmParam); // 처리결과
                DataSetMap listInDsRecTm = (DataSetMap)mapInDs.get("ds_input_recTm");
                if (listInDsRecTm != null && listInDsRecTm.size() > 0) {
                    Map pmParam = listInDsRecTm.get(0);

                    // 녹취캠페인내역 저장
                    String cticallhistid = StringUtils.defaultString((String) pmParam.get("cti_crnc_dtl_id")); // CTI통화이력아이디
                    if (!"".equals(cticallhistid)) {
                        ParamUtils.addSaveParam(pmParam);
                        ParamUtils.addCenterParam(pmParam);
                        ctiCrncHstrService.mergeRecTmDtl(pmParam);
                    }
                }
                //Map<String, Object> mCmpg=trgtCustAlctService.getTrgtCustAlct(hmParam);

                //DataSetMap listMap = new DataSetMap();
                //listMap.setRowMaps(mCmpg);
                // mapOutDs.put("ds_output", listMap);
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
     * 캠페인 결과를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ctiid/update")
    @ResponseBody
    public ModelAndView updateCtiId(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                ParamUtils.addSaveParam(hmParam);
                UserSession user = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("user_id", user.getUserid());

                String sCtiId = trgtCustAlctService.updateCtiId(hmParam); // 처리결과

                if(sCtiId != null && sCtiId !="" ){
                    dpmsReslHstrService.insertDpmsReslHstr(hmParam); // 히스토리
                    Map<String, Object> mCtiId= new HashMap<String, Object>();
                    mCtiId.put("cti_crnc_dtl_id",sCtiId);

                    DataSetMap listMap = new DataSetMap();
                    listMap.setRowMaps(mCtiId);
                    mapOutDs.put("ds_output", listMap);
                    modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                    modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
                }
            }
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/list")
    @ResponseBody
    public ModelAndView getTrgtCustAlctMainList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getTrgtCustAlctCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getTrgtCustAlctList(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/newtypelist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctList(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 대상 고객별 할당 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/list")
    @ResponseBody
    public ModelAndView getTrgtAlctCustList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getTrgtAlctCustCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getTrgtAlctCustList(hmParam);

            mapOutVar.put("total_count_alct", nTotal);
            dsMap.setRowMaps(mTrgtCustAlctList);
            mapOutDs.put("ds_output", dsMap);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 대상 고객별 할당 리스트 NEWTYPE (20190726)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/newtypelist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtAlctCustList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtAlctCustCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtAlctCustList(hmParam);

            mapOutVar.put("total_count_alct", nTotal);
            dsMap.setRowMaps(mTrgtCustAlctList);
            mapOutDs.put("ds_output", dsMap);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 캠페인 대상리스트 고객아이디를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update-target-cust-customer-id")
    @ResponseBody
    public ModelAndView updateTrgtCustCustId(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                ParamUtils.addSaveParam(hmParam);
                UserSession user = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("user_id", user.getUserid());

                trgtCustAlctService.updateTrgtCustCustId(hmParam);

                Map<String, Object> mCmpg=trgtCustAlctService.getTrgtCustAlct(hmParam);

                DataSetMap listMap = new DataSetMap();
                listMap.setRowMaps(mCmpg);
                mapOutDs.put("ds_output", listMap);
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
     * 캠페인 처리현황(상담사별)을 조회한다.
     *
     * 개발일시 : 2016.0713
     * 개 발 자 : 정용재
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/cnsr-stat/list")
    @ResponseBody
    public ModelAndView getTrgtCustAlctByCnsr(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (!"Y".equals(mapInVar.get("excel")) && listInDs != null && listInDs.size() > 0) {
                Map<String, Object> pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            ParamUtils.addCenterParam(hmParam);

            int nTotal = 0;

            nTotal = trgtCustAlctService.getTrgtCustAlctByCnsrCount(hmParam);
            if(nTotal > 0){
                List<Map<String, Object>> mList = trgtCustAlctService.getTrgtCustAlctByCnsrList(hmParam);

                listMap.setRowMaps(mList);
                mapOutVar.put("total_count", nTotal);
                mapOutDs.put("ds_output", listMap);
            }else{
                List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
                mapOutVar.put("total_count", nTotal);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 캠페인 대상리스트 고객아이디를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dup-delete")
    @ResponseBody
    public ModelAndView deleteDupAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                trgtCustAlctService.deleteDupAlct(hmParam);
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/directlist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainDirectlist(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctDirectCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctDirectList(hmParam);
            
            trgtCustAlctService.updateDirectSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 고객기준의 할당 사용 (선택할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/directalct")
    @ResponseBody
    public ModelAndView saveNewTypeTrgtCustDirectAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id
            
            int rtnCnt = 0;

            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);

            rtnCnt = trgtCustAlctService.insertNewTypeSelectDirectAlct(hmParam, listInDs);
            
            System.out.println(">>>>>>>>>>>> " + rtnCnt);
            
            //분배한 대상자 숫자 결과
            mapOutVar.put("intProcCnt", rtnCnt);
            
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/directlist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainDirectList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctMainDirectCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctMainDirectList(hmParam);
            
            trgtCustAlctService.updateDirectSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/upluslist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainUpluslist(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctUplusCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctUplusList(hmParam);
            
            //trgtCustAlctService.updateUplusSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/uplusPoplist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainUplusPoplist(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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
            
            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctUplusPopList(hmParam);

            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/uplusPoplist2")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainUplusPoplist2(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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
            
            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctUplusPopList2(hmParam);

            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 고객기준의 할당 사용 (선택할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/uplusalct")
    @ResponseBody
    public ModelAndView saveNewTypeTrgtCustUplusAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id
            
            int rtnCnt = 0;

            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);

            rtnCnt = trgtCustAlctService.insertNewTypeSelectUplusAlct(hmParam, listInDs);
            
            System.out.println(">>>>>>>>>>>> " + rtnCnt);
            
            //분배한 대상자 숫자 결과
            mapOutVar.put("intProcCnt", rtnCnt);
            
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/upluslist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainUplusList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctMainUplusCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctMainUplusList(hmParam);
            
            //trgtCustAlctService.updateUplusSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * PDS연동 할당
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/pdsIntr")
    @ResponseBody
    public ModelAndView saveTrgtCustPdsIntr(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id -> PDS연동iD로 변경해야함
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id

            String sCmpgDivCd = StringUtils.defaultString((String)mapInVar.get("cmpg_div_cd")); //상위
            String sCmpgTypCd = StringUtils.defaultString((String)mapInVar.get("cmpg_typ_cd")); //하위
            
            String sPdsGubun = StringUtils.defaultString((String)mapInVar.get("pdsGubun")); //PDS구분


            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);
            hmParam.put("sCmpgDivCd",sCmpgDivCd);
            hmParam.put("sCmpgTypCd",sCmpgTypCd);
            hmParam.put("pdsGubun",sPdsGubun);

            //trgtCustAlctService.insertSelectAlct(hmParam, listInDs);
            trgtCustAlctService.insertSelectPdsIntr(hmParam, listInDs);

            //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            Map<String, Object> mParam = hmParam ;

            mParam.remove("trgt_cust_dtpt_id");
            mParam.remove("cnsr_id");

            int totalassignnum = trgtCustAlctService.getTrgtCustAlctCount(hmParam);

            mapOutVar.put("gv_totalassignnum", totalassignnum);

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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/alct/mangilist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainMangilist(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctMangiCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctMangilist(hmParam);
            
            //trgtCustAlctService.updateUplusSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
     * 고객기준의 할당 사용 (선택할당) >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/mangialct")
    @ResponseBody
    public ModelAndView saveNewTypeTrgtCustMangiAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sCnsrId = StringUtils.defaultString((String)mapInVar.get("user_id")); //변경할 id
            String sCmpgId = StringUtils.defaultString((String)mapInVar.get("cmpg_id")); //캠페인 id
            
            int rtnCnt = 0;

            hmParam.put("sCnsrId",sCnsrId);
            hmParam.put("sCmpgId",sCmpgId);

            rtnCnt = trgtCustAlctService.insertNewTypeSelectMangiAlct(hmParam, listInDs);
            
            System.out.println(">>>>>>>>>>>> " + rtnCnt);
            
            //분배한 대상자 숫자 결과
            mapOutVar.put("intProcCnt", rtnCnt);
            
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
     * 상담사별 할당 대상고객 정보를 검색한다.(메인)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/mangilist")
    @ResponseBody
    public ModelAndView getNewTypeTrgtCustAlctMainMangiList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
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

            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("user_id", user.getUserid());
            ParamUtils.addCenterParam(hmParam);

            int nTotal = trgtCustAlctService.getNewTypeTrgtCustAlctMainMangiCount(hmParam);


            List<Map<String, Object>> mTrgtCustAlctList = trgtCustAlctService.getNewTypeTrgtCustAlctMainMangiList(hmParam);
            
            //trgtCustAlctService.updateUplusSessionClose(hmParam);

            mapOutVar.put("total_count", nTotal);
            if(mTrgtCustAlctList.size() > 0){
                dsMap.setRowMaps(mTrgtCustAlctList);
                mapOutDs.put("ds_output", dsMap);
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
}