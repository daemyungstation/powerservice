/*
 * (@)# TrgtCustController.java
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

import powerservice.business.bean.UserSession;
import powerservice.business.cam.service.SubTrgtListService;
import powerservice.business.cam.service.TrgtCustService;
import powerservice.business.cam.service.TrgtListService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.21 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 대상고객리스트 추출고객 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID  Targetcust
 */

@Controller
@RequestMapping(value = "/campaign/trgt-cust")
public class TrgtCustController {

    @Resource
    private TrgtCustService targetcustService;

    @Resource
    private SubTrgtListService subTrgtListService;

    @Resource
    private TrgtListService trgtListService;

    @Resource
    private CommonService commonService;


    /**
     * 대상고객 정보를 검색한다. --!
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public ModelAndView getTrgtCustList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = targetcustService.getTrgtCustCount(hmParam);
            List<Map<String, Object>> mTrgtCustList = targetcustService.getTrgtCustList(hmParam);
            mapOutVar.put("total_count", nTotal);
            listMap.setRowMaps(mTrgtCustList);
            mapOutDs.put("ds_output", listMap);

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
     * 대상고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/view")
    @ResponseBody
    public ModelAndView getTrgtCust(XPlatformMapDTO xpDto, Model mode) throws Exception {
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
             ParamUtils.addCenterParam(hmParam);

             Map<String, Object> mTrgtCust = targetcustService.getTrgtCust(hmParam);

             listMap.setRowMaps(mTrgtCust);

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
     * 대상고객  정보를 삭제한다. >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView delete(XPlatformMapDTO xpDto, Model mode) throws Exception {
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

            String sTrgtListId = StringUtils.defaultString((String) mapInVar.get("trgt_list_id"));
            String sSubTrgtListId = StringUtils.defaultString((String) mapInVar.get("sub_trgt_list_id"));

            String sCmpgTypCd = StringUtils.defaultString((String) mapInVar.get("cmpg_typ_cd"));
            String sCmpgTypCd2 = StringUtils.defaultString((String) mapInVar.get("cmpg_typ_cd2"));
            hmParam.put("cmpg_typ_cd" ,sCmpgTypCd);
            hmParam.put("cmpg_typ_cd2" ,sCmpgTypCd2);


            //조회조건
            DataSetMap deleteInDs = (DataSetMap)mapInDs.get("ds_input");

            for(int i = 0; deleteInDs.size() > i ; i++ ){
                hmParam =deleteInDs.get(i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                ParamUtils.addCenterParam(hmParam);
                if (rowType == DataSet.ROW_TYPE_DELETED){ //삭제
                    //삭제
                    hmParam.put("cmpg_typ_cd" ,sCmpgTypCd);
                    targetcustService.deleteTrgtCust(hmParam);

                    //2017.12.21 접속 로그////////////////////////////////////////////////////////////////////////////////
                    DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                    if (listLogIn.size() > 0) {
                        hmParam = listLogIn.get(0);
                        commonService.insertLog(hmParam);
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////
                }
            }

            Map<String, Object> pmParam = new HashMap<String, Object>();

            ParamUtils.addCenterParam(pmParam);
            pmParam.put("updateOption", "extract");
            pmParam.put("sub_trgt_list_id",sSubTrgtListId);
            pmParam.put("trgt_list_id",sTrgtListId);

            // 추출갯수 수정한다.
            subTrgtListService.updateSubTrgtList(pmParam);

            //추출갯수 수정한다.
            trgtListService.updateTrgtList(pmParam);

            //총추출건수를 위해 검색한다.
            Map<String, Object> mTrgtList = trgtListService.getTrgtList((String)pmParam.get("trgt_list_id"));

            //총추출건수 가져온다
            mapOutVar.put("total_cust_data_extc_cnt", mTrgtList.get("cust_data_extc_cnt"));

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
     * 대상고객 이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/hstr-list")
    public ModelAndView getTrgtCustHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 조회조건
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

            // "TM" 사용자 권한인 경우 캠페인 수행 팀 조회 제한
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            if (oLoginUser == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "사용자 로그인 정보가 없습니다.");

                return modelAndView;
            }
            String sAthrCd = oLoginUser.getAuthoritycd();
            String sTeamCd = oLoginUser.getTeamcd();
            if (sAthrCd.startsWith("TM")) {
                hmParam.put("cmpg_prfm_team_cd", sTeamCd);
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = targetcustService.getTrgtCustHstrCount(hmParam);
            List<Map<String, Object>> mTrgtCustList = targetcustService.getTrgtCustHstrList(hmParam);
            mapOutVar.put("tc_trgt_cust_hstr", nTotal);
            listMap.setRowMaps(mTrgtCustList);
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

}