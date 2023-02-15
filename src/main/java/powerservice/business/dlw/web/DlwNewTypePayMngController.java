/*
 * (@)# DlwCustController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.web;

import java.util.ArrayList;
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
import powerservice.business.cns.service.ConsService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwEmplService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.dlw.service.DlwNewTypePayMngService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명 고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value = "/dlw/newTypePayMng")
public class DlwNewTypePayMngController {


//    @Resource
//    private DlwCustService dlwCustService;
//
//    @Resource
//    private DlwCmsService dlwCmsService;

	@Resource
	private DlwNewTypePayMngService DlwNewTypePayMngService;
	
    @Resource
    private DlwEmplService dlwEmplService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private UserService userService;

    @Resource
    private ConsService consService;
    
    @Resource
    private DlwEvntService DlwEvntService;
    
    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-acnt/list")
    public ModelAndView getDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                /* 임의등록에서 회원정보검색시 해피콜 조인구분 변수 추가 - 2018.02.13 */
                hmParam.put("hp_join_gb", mapInVar.get("hp_join_gb"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                Map<String, Object> mEmpl = dlwEmplService.getDlwEmplDtpt((String)hmParam.get("rgsr_id"));
                if (mEmpl != null) {
                    String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                    if ("1602".equals(sDeptCd)) {
                        hmParam.put("ns_yn", "Y");
                    } else {
                        hmParam.put("ns_yn", "N");
                    }
                }

                UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
                hmParam.put("paramAs", "EMP");

                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = DlwNewTypePayMngService.getDlwCustAcntCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = DlwNewTypePayMngService.getDlwCustAcntList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
            }

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
     * 대명라이프웨이 입금/결합/상품/부가정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-mng")
    public ModelAndView getPayMngInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        DataSetMap listMap3 = new DataSetMap();
        DataSetMap listMap4 = new DataSetMap();
        DataSetMap listMap5 = new DataSetMap();
        DataSetMap listMap6 = new DataSetMap();
        DataSetMap listMap7 = new DataSetMap();
        DataSetMap listMap8 = new DataSetMap();
        DataSetMap listMap9 = new DataSetMap();
        DataSetMap listMap10 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String nResult = "";


            List<Map<String, Object>> mList = DlwNewTypePayMngService.getPayMngBugaInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            if (mList != null && mList.size() > 0) {
                List<Map<String, Object>> mDtlList = DlwNewTypePayMngService.getPayMngDtlBugaInfo(hmParam);
                List<Map<String, Object>> mDtl1List = DlwNewTypePayMngService.getPayMngDtl1BugaInfo(hmParam);

                //입금정보
                List<Map<String, Object>> mPayList = DlwNewTypePayMngService.getPayMngList(hmParam);
                List<Map<String, Object>> mPayDtlList = DlwNewTypePayMngService.getPayMngDtlList(hmParam);
                List<Map<String, Object>> mPayDtl1List = DlwNewTypePayMngService.getPayMngDtl1List(hmParam);

                //상품/입금정보
                List<Map<String, Object>> mPayTotalList = DlwNewTypePayMngService.getPayTotalList(hmParam);
                Map<String, Object> hmParam2 = new HashMap<String, Object>();
                String prod_cd = DlwNewTypePayMngService.getProdCdByAccntNo(hmParam);
                hmParam2.put("prod_cd", prod_cd);
                List<Map<String, Object>> mProdDtlList = DlwNewTypePayMngService.getProductDtl(hmParam2);

                //환불정보
                List<Map<String, Object>> mRefundList = DlwNewTypePayMngService.getRefundList(hmParam);
                List<Map<String, Object>> mRefundDtlList = DlwNewTypePayMngService.getRefundDtlList(hmParam);
                List<Map<String, Object>> mRefundDtl1List = DlwNewTypePayMngService.getRefundDtl1List(hmParam);

                listMap1.setRowMaps(mPayList);
                listMap2.setRowMaps(mPayDtlList);
                listMap3.setRowMaps(mPayDtl1List);
                listMap4.setRowMaps(mDtlList);
                listMap5.setRowMaps(mDtl1List);
                listMap6.setRowMaps(mRefundList);
                listMap7.setRowMaps(mRefundDtlList);
                listMap8.setRowMaps(mRefundDtl1List);
                listMap9.setRowMaps(mPayTotalList);
                listMap10.setRowMaps(mProdDtlList);
                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
                mapOutDs.put("ds_output3", listMap3);
                mapOutDs.put("ds_output4", listMap4);
                mapOutDs.put("ds_output5", listMap5);
                mapOutDs.put("ds_output6", listMap6);
                mapOutDs.put("ds_output7", listMap7);
                mapOutDs.put("ds_output8", listMap8);
                mapOutDs.put("ds_output9", listMap9);
                mapOutDs.put("ds_output10", listMap10);
            }

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
     * 납입건수에 따른 납입금액 계산
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt")
    public ModelAndView getPayAmtBytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwNewTypePayMngService.getPayAmtByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt", nResult);

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
     * 납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt-dtl")
    public ModelAndView getPayAmtDtlBytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwNewTypePayMngService.getPayAmtDtlByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt_dtl", nResult);

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
     * 납입건수에 따른 납입금액 계산 - 결합상품 추가부담금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt-dtl1")
    public ModelAndView getPayAmtDtl1BytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwNewTypePayMngService.getPayAmtDtl1ByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt_dtl1", nResult);

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
     * 대명라이프웨이 입금정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng")
    public ModelAndView mergePayMngReq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMng(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
            }

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
     * 대명라이프웨이 입금정보를 저장한다. - 결합상품할부원금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng-dtl")
    public ModelAndView mergePayMngDtlReq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMngDtl(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
            }

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
     * 대명라이프웨이 입금정보를 저장한다. - 결합상품추가부담금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng-dtl1")
    public ModelAndView mergePayMngDtl1Req(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMngDtl1(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
            }

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
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMng(Map <String, Object> pmParam)throws Exception {
        pmParam.put("resn_yn", "N");
        List<Map<String, Object>> bugaList = DlwNewTypePayMngService.getBugaInfo(pmParam);

        if(bugaList.size() > 0) {
            Map <String, Object> tmpMap = bugaList.get(0);
            if("Y".equals(tmpMap.get("resn_yn")))
            {
                pmParam.put("resn_yn", "Y");
                return pmParam;
            }
        }

        String newYn = "";
        if("D".equals((String)pmParam.get("cl1")) && "N".equals(DlwNewTypePayMngService.getpayNewYnChk(pmParam))) {
            pmParam.put("new_yn", "N");
            return pmParam;
        }
        String empleNo = (String)pmParam.get("emple_no");
        if(empleNo == null) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            empleNo = oLoginUser.getUserid();
        }
        String chk = closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo);
        if("N".equals(chk)) {
            pmParam.put("edt_yn", "N");
            return pmParam;
        } else {
        	DlwNewTypePayMngService.mergePayMng(pmParam);
            return pmParam;
        }
    }
    
    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMngDtl(Map <String, Object> pmParam)throws Exception {
          String empleNo = "";
          if((String)pmParam.get("emple_no") == null) {
              UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
              empleNo = oLoginUser.getUserid();
          } else {
              //empleNo = (String)pmParam.get("regMan");
              empleNo = (String)pmParam.get("emple_no");
          }

          if("N".equals(closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo))) {
              pmParam.put("edt_yn", "N");
              return pmParam;
          } else {
        	  DlwNewTypePayMngService.mergePayMngDtl(pmParam);
              return pmParam;
          }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMngDtl1(Map <String, Object> pmParam)throws Exception {
        String empleNo = "";
        if((String)pmParam.get("emple_no") == null) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            empleNo = oLoginUser.getUserid();
        } else {
            //empleNo = (String)pmParam.get("regMan");
            empleNo = (String)pmParam.get("emple_no");
        }

        if("N".equals(closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo))) {
            pmParam.put("edt_yn", "N");
            return pmParam;
        } else {
        	DlwNewTypePayMngService.mergePayMngDtl1(pmParam);
            return pmParam;
        }
    }
    
    /**
     * 대명라이프웨이 마감권한 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String closeDataSaveYnChk(String closeCl, String accntNo, String closeDt, String empleNo) throws Exception {
            String edt_yn = "";
            Map <String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("close_cl", closeCl);
            hmParam.put("accnt_no", accntNo);
            hmParam.put("close_dt", closeDt);
            hmParam.put("emple_no", empleNo);
            edt_yn = DlwEvntService.getCloseDataSaveYnChk(hmParam);
            return edt_yn;
    }
    
    /**
     * 출금이체의뢰내역(청구) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqAccntConfirm")
    public ModelAndView getWdrwReqAccntConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String accnt_no = (String)mapInVar.get("accnt_no");

            if (accnt_no != null || accnt_no != "" || !accnt_no.equalsIgnoreCase(""))
            {
                int nAccntCnt = DlwNewTypePayMngService.getWdrwReqAccntConfirm(accnt_no);
                mapOutVar.put("gv_wdrw_req_accnt", nAccntCnt); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
}