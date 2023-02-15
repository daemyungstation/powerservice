/*
 * (@)# CmpgAlctController.java
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

import powerservice.business.cam.service.CmpgAlctService;
import powerservice.business.cam.service.ExtcMstTrgtService;
import powerservice.business.cam.service.TrgtCustAlctService;
import powerservice.business.cam.service.impl.TrgtCustDAO;
import powerservice.business.onln.service.impl.ExtcTrgtDAO;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.usr.service.impl.UserDAO;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 캠페인할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID CmpgAlct
 */

@Controller
@RequestMapping(value="/cmpg/cmpg-alct")
public class CmpgAlctController {

    @Resource
    private CmpgAlctService cmpgAlctService;

    @Resource
    private TrgtCustAlctService trgtCustAlctService;

    @Resource
    public BasVlService basVlService;

    @Resource
    public ExtcTrgtDAO extcTrgtDAO;

    @Resource
    public TrgtCustDAO trgtCustDAO;

    @Resource
    public UserDAO userDAO;

    @Resource
    public ExtcMstTrgtService extcMstTrgtService;



    /**
     * 캠페인할당 정보를 검색한다. 사용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public ModelAndView getCmpgAlctList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            ParamUtils.addSaveParam(hmParam);

            int nTotal = cmpgAlctService.getCmpgAlctCount(hmParam);
            List<Map<String, Object>> mCmpgAlctList = cmpgAlctService.getCmpgAlctList(hmParam);

            mapOutVar.put("total_count_assing", nTotal);
            listMap.setRowMaps(mCmpgAlctList);
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
     * 캠페인할당 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/save")
    @ResponseBody
    public ModelAndView saveCmpgAlct(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs != null && listInDs.size() > 0) {
                Map<String, Object> mParam = new HashMap<String, Object>();
                ParamUtils.addSaveParam(mParam);
                ParamUtils.addCenterParam(mParam);
                mParam.put("cmpg_id",mapInVar.get("cmpg_id"));
                mParam.put("sub_trgt_list_id",mapInVar.get("sub_trgt_list_id"));
                cmpgAlctService.insertCmpgAlct(listInDs, mParam);
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
     * 캠페인할당 정보를 삭제한다. 사용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/delete")
    @ResponseBody
    public ModelAndView deleteCampaignAssign(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String[] sUserIds = StringUtils.defaultString((String) mapInVar.get("selectcheck")).split(",");
            String sCmpgId = StringUtils.defaultString((String) mapInVar.get("cmpg_id"));
            String sTrgtListId =StringUtils.defaultString((String) mapInVar.get("trgt_list_id"));

            String sCmpgDivCd = StringUtils.defaultString((String)mapInVar.get("cmpg_div_cd"));
            String sCmpgTypCd = StringUtils.defaultString((String)mapInVar.get("cmpg_typ_cd"));


            Map<String, Object> mParam = new HashMap<String, Object>();
            mParam.put("selectcheck",mapInVar.get("selectcheck"));
            mParam.put("cmpg_id", mapInVar.get("cmpg_id"));
            mParam.put("sub_trgt_list_id",mapInVar.get("sub_trgt_list_id"));
            mParam.put("trgt_list_id" , mapInVar.get("trgt_list_id"));
            mParam.put("cmpg_div_cd" ,mapInVar.get("cmpg_div_cd"));
            mParam.put("cmpg_typ_cd" ,mapInVar.get("cmpg_typ_cd"));

            int nErrCnt = cmpgAlctService.deleteCmpgAlct(mParam);


            if (nErrCnt > 0) {
                szErrorCode = "-2";
                szErrorMsg="배정건수가 0이 아닌 상담사는 삭제할 수 없습니다.";

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
                return modelAndView;
            }

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

    /**
     * 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/change/cnsr")
    @ResponseBody
    public ModelAndView updateChangeCnsr(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sCmpgDivCd = StringUtils.defaultString((String)mapInVar.get("cmpg_div_cd"));
            String sCmpgTypCd = StringUtils.defaultString((String)mapInVar.get("cmpg_typ_cd"));

            String sCmpgId = StringUtils.defaultString((String) mapInVar.get("cmpg_id"));
            String sTrgtListId =StringUtils.defaultString((String) mapInVar.get("trgt_list_id"));

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
               for(int j = 0; srchInDs.size() > j ; j++){
                    hmParam = srchInDs.get(j);
                    hmParam.put("cnsr_id", StringUtils.defaultString((String)mapInVar.get("cnsr_id")));
                    hmParam.put("user_id", StringUtils.defaultString((String)mapInVar.get("user_id")));
                    hmParam.put("sCmpgDivCd",sCmpgDivCd);
                    hmParam.put("sCmpgTypCd",sCmpgTypCd);
                    hmParam.put("sCmpgId",sCmpgId);
                    hmParam.put("sTrgtListId",sTrgtListId);

                    hmParam.put("save_fg", "Y");
                    ParamUtils.addCenterParam(hmParam);
                    ParamUtils.addSaveParam(hmParam);

                    trgtCustAlctService.updateChangeCnsr(hmParam);

                    modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                    modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        return modelAndView;
    }

    /**
     * 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/main/list")
    @ResponseBody
    public ModelAndView getCmpgMainInfoList(XPlatformMapDTO xpDto, Model mode) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);
            UserSessionCore user = SessionUtils.getLoginUser();
            hmParam.put("cnsr_id", user.getUserid());


            List<Map<String, Object>> mCmpgAlctList = cmpgAlctService.getMainCmpgAlctList(hmParam);
            dsMap.setRowMaps(mCmpgAlctList);
            mapOutDs.put("datas", dsMap);

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