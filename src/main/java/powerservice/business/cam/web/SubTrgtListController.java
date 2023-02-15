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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cam.service.DfctDataService;
import powerservice.business.cam.service.ExtcMstTrgtService;
import powerservice.business.cam.service.SubTrgtListService;
import powerservice.business.cam.service.TrgtListService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 서브타겟리스트 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/05/10
 * @프로그램ID TrgtCustAlct
 */

@Controller
@RequestMapping(value="/cmpg/sub-trgt-list")
public class SubTrgtListController {

    @Resource
    private SubTrgtListService subTrgtListService;

    @Resource
    private TrgtListService trgtListService;

    @Resource
    private DfctDataService dfctDataService;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ExtcMstTrgtService extcMstTrgtService;



    /**
     * 서브타겟 리스트를 검색한다. --!
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getSubTrgtList(XPlatformMapDTO xpDto, Model mode) throws Exception {
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
            hmParam = srchInDs.get(0);

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = subTrgtListService.getSubTrgtListCount(hmParam);
            List<Map<String, Object>> mTrgtCustAlctList = subTrgtListService.getSubTrgtList(hmParam);

            listMap.setRowMaps(mTrgtCustAlctList);

            mapOutVar.put("total_count_sub", nTotal);
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
     * 서브타겟 리스트를 (단일건) 저장한다.. --! >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/one/save")
    @ResponseBody
    public ModelAndView saveOneSubTrgt(XPlatformMapDTO xpDto, Model mode) throws Exception {
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
                ParamUtils.addCenterParam(hmParam);

                //단일건을 저장한다.
                subTrgtListService.insertOneTrgtCust(hmParam);

                //총추출건수를 위해 검색한다.
                Map<String, Object> mTrgtList = trgtListService.getTrgtList((String)hmParam.get("trgt_list_id"));

                //총추출건수 가져온다
                mapOutVar.put("total_cust_data_extc_cnt", mTrgtList.get("cust_data_extc_cnt"));
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
     * 서브타겟 리스트를 저장한다.. --! >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveSubTrgtList(XPlatformMapDTO xpDto, Model mode) throws Exception {
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
                String sSubTrgtListId = StringUtils.defaultString((String) hmParam.get("sub_trgt_list_id")); //서브타겟ID
                String sTrgtListId = StringUtils.defaultString((String) hmParam.get("trgt_list_id"));	     //메인타겟ID
                String sCmpg_typ_cd2 = StringUtils.defaultString((String) hmParam.get("cmpg_typ_cd2"));      //캠페인 유형2
                String sResnAdd= StringUtils.defaultString((String) hmParam.get("resn_add"));				 //해약추가 등록 여부 20190107

                ParamUtils.addSaveParam(hmParam);
                ParamUtils.addCenterParam(hmParam);



                if (sSubTrgtListId.equals("")) { //신규
                	DataSetMap listInDataSet = (DataSetMap)mapInDs.get("ds_extc_input");
                    sSubTrgtListId = subTrgtListService.insertSubTrgtList(hmParam ,listInDataSet);
                } else  { //수정
                	if (sCmpg_typ_cd2.equals("1700") && sResnAdd.equals("Y")){
                		DataSetMap listInDataSet = (DataSetMap)mapInDs.get("ds_extc_input");
                		subTrgtListService.insertSubTrgtList_Add(hmParam ,listInDataSet); //신규가 아닌 추가 서브 대상자 등록


                	}else{
                		subTrgtListService.updateSubTrgtList(hmParam);
                	}
                }

                //총추출건수를 위해 검색한다.
                Map<String, Object> mTrgtList = trgtListService.getTrgtList((String)hmParam.get("trgt_list_id"));

                //총추출건수 가져온다
                mapOutVar.put("total_cust_data_extc_cnt", mTrgtList.get("cust_data_extc_cnt"));
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
     * 캠페인의 서브타겟을 삭제한다. --! >>
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView getCampaignAssignum(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            subTrgtListService.deleteSubTrgtList(hmParam);

            //총추출건수를 위해 검색한다.
            Map<String, Object> mTrgtList = trgtListService.getTrgtList((String)hmParam.get("trgt_list_id"));

            //총추출건수 가져온다
            mapOutVar.put("total_cust_data_extc_cnt", mTrgtList.get("cust_data_extc_cnt"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
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
     * 캠페인별 대상목록 현황을 조회한다.
     *
     * 개발일시 : 2016.07.14
     * 개 발 자 : 정용재
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/stat/list")
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

            int nTotal = subTrgtListService.getSubTrgtStatByCmpgCount(hmParam);;
            List<Map<String, Object>> mList = subTrgtListService.getSubTrgtStatByCmpgList(hmParam);

            listMap.setRowMaps(mList);
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

}