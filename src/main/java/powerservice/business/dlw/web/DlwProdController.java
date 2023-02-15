/*
 * (@)# DlwProdController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/15
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwProdService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.FileZipCompress;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwProd
 */
@Controller
@RequestMapping(value = "/dlw/prod")
public class DlwProdController {

    private final Logger log = LoggerFactory.getLogger(DlwProdController.class);

    @Resource
    private DlwProdService dlwProdService;

    @Resource
    private CommonService commonService;

    /**
     * 상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getDlwProdList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));
            String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getDlwProdCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                List<Map<String, Object>> mList = dlwProdService.getDlwProdList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 상품조회_상품상세정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtpt")
    public ModelAndView getDlwProdDtpt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sProdCd = StringUtils.defaultString((String) mapInVar.get("prod_cd"));
            String sPayType = StringUtils.defaultString((String) mapInVar.get("pay_type"));

            if ("".equals(sProdCd)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "상품코드가 없습니다.");

                return modelAndView;
            }

            hmParam.put("prod_cd", sProdCd);
            hmParam.put("pay_type", sPayType);

            Map<String, Object> mDtpt = dlwProdService.getDlwProdDtpt(hmParam);
            dtptMap.setRowMaps(mDtpt);
            mapOutDs.put("ds_output", dtptMap);

            if ("002".equals(sPayType)) {
                List<Map<String, Object>> mList = dlwProdService.getDlwProductDtl(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output_list", listMap);
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
     * 상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/membership/list")
    public ModelAndView getOCBCardCodeList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            List<Map<String, Object>> mList = dlwProdService.getOCBCardCodeList(hmParam);
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
     * 상품상세분류(모델 MST) 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/model-mst/list")
    public ModelAndView getModelMstInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            List<Map<String, Object>> mList = dlwProdService.getModelMstInfo(hmParam);
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
     * 상품상세분류(모델 DTL) 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/model-dtl/list")
    public ModelAndView getModelDtlInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            List<Map<String, Object>> mList = dlwProdService.getModelDtlInfo(hmParam);
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
     * 상품상세분류(모델 DTL) 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prod-kind/list")
    public ModelAndView getProdKindList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            List<Map<String, Object>> mList = dlwProdService.getProdKindList(hmParam);
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
     * 신규상품등록 및 수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newprod")
    public ModelAndView newProdins(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("-------------------------- 등록/수정 컨트롤러 입장");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int iCudCnt = 0;

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsDetail = (DataSetMap)mapInDs.get("ds_input2");


            Object gubun = (Object)mapInVar.get("p_gubun");

            CommonUtils.printLog("========================================= p_gubun" + gubun );

            if (srchInDs != null && srchInDs.size() > 0) {

                hmParam = srchInDs.get(0);

                if(gubun.equals("new")){

                    //상품코드 확인
                    int nTotal = dlwProdService.getDlwProdNewCODE(hmParam);

                   if (nTotal < 1) {
                       System.out.println("* * * * 등록 * * *");
                       ParamUtils.addSaveParam(hmParam);
                       hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
                       dlwProdService.insertProdNew(hmParam);  // 등록

                       if (null != dsDetail && dsDetail.size() > 0) {
                              Map<String, Object> hmParam1 = dsDetail.get(0);
                              CommonUtils.printLog(hmParam1);



                           for (int i=0; i<dsDetail.size(); ++i) {
                               Map mDetail = dsDetail.get(i);
                               ParamUtils.addSaveParam(mDetail);
                               mDetail.put("reg_man", (String)mDetail.get("rgsr_id"));
                               iCudCnt += dlwProdService.insertProdDtl(mDetail);  // 상세정보등록
                           }
                       }

                   } else {
                        szErrorCode = "-1";
                        szErrorMsg  = "상품코드 중복 오류!!! 상품코드 확인 후 등록해주세요.";

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
                        return modelAndView;

                   }


                } else {
                    System.out.println("* * * * 수정 * * *");
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
                    
                    dlwProdService.updateProdChng(hmParam);  // 수정
                    dlwProdService.deleteProdDtl(hmParam);  // 삭제

                    if (null != dsDetail && dsDetail.size() > 0) {
                        for (int i=0; i<dsDetail.size(); ++i) {
                            Map mDetail = dsDetail.get(i);

                            ParamUtils.addSaveParam(mDetail);
                            mDetail.put("reg_man", (String)mDetail.get("rgsr_id"));

                            int rowType = ((Integer) mDetail.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                            if (rowType != DataSet.ROW_TYPE_DELETED) {
                                iCudCnt += dlwProdService.insertProdDtl(mDetail);  // 상세정보등록
                            }
                        }
                    }
                }
                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 거래처 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
 /*   @RequestMapping(value = "/newprod")
    public ModelAndView saveCustmr(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mOut = new HashMap<>();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        try {
            int cnt = dlwProdService.saveProd(xpDto, mOut);

            String prod_cd = CommonUtils.nvls((String)mOut.get("prod_cd"));
            mapOutVar.put("fv_prod_cd2", prod_cd);

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
*/


    /**
     * 상품정보 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delprod")
    public ModelAndView delProduct(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();


            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog(hmParam);

                dlwProdService.deleteProd(hmParam);  // 삭제


                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);

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
     * 상품상세정보 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delProdDtl")
    public ModelAndView delProdDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        System.out.println("삭제 컨트롤러");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            CommonUtils.printLog("========================================= 삭제닷");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog(hmParam);


                    dlwProdService.deleteProdDtl(hmParam);


                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
               // mapOutDs.put("ds_output", listMap);
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
     * 상품별 세부분류 콤보 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/proddtl")
    public ModelAndView getProdDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            List<Map<String, Object>> mList = dlwProdService.getProdDtlList(hmParam);
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
     * 상품별 상세분류 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/classprodlist")
    public ModelAndView getClassProdList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            //String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));
            String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getClassProdCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwProdService.getClassProdList(hmParam);
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
     * 상품별 세부분류 선택 행 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/rowproddel")
    public ModelAndView getRowProdDel(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for (int i=0; i<srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);
                System.out.println("===================================>>>>> rowproddel <<<<<<<<<<<");
                CommonUtils.printLog(hmParam);
                dlwProdService.getRowProdDel(hmParam);
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
     * 상품별 상세분류 등록
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newprodkind")
    public ModelAndView newProdKindins(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog(hmParam);


                dlwProdService.insertProdKindNew(hmParam);  // 등록

                //dlwProdService.updateProdChng(hmParam);  // 수정

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 상품별 모델 분류 기초 코드 관리 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/modelprodlist")
    public ModelAndView getProdModelList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            //String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));
            String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getProdModelCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwProdService.getProdModelList(hmParam);
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
     * 기간별 상품 의전원가 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/periodprodcost")
    public ModelAndView getProdProdCost(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getPeriodProdCostCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwProdService.getPeriodProdCost(hmParam);
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
     * 기간별 상품 의전원가 등록
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newperiodprodcost")
    public ModelAndView newPeriodProdCost(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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
                System.out.println("===================================>>>>> 의전원가 등록 <<<<<<<<<<<");
                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());


                String flag = StringUtils.defaultString((String) mapInVar.get("flag"));
                hmParam.put("flag", flag);

                CommonUtils.printLog(hmParam);

                /* 체크 1 : 해당되는 기존 데이터가 있는지 체크 */
                /* 체크 2 : 기존 데이터의 이전 날짜로 입력하는지 체크 */
                /* 체크 3 : 신규 데이터인지 체크 */
                String chkFG_1 = dlwProdService.checkPeriodProdCost1(hmParam);  // 입력가능여부 체크1
                String chkFG_2 = dlwProdService.checkPeriodProdCost2(hmParam);  // 입력가능여부 체크2
                String chkFG_3 = dlwProdService.checkPeriodProdCost3(hmParam);  // 입력가능여부 체크3


                System.out.println("@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @");
                System.out.println("해당되는 기존 데이터가 있는지 체크 : " + chkFG_1);
                System.out.println("기존 데이터의 이전 날짜로 입력하는지 체크 : " + chkFG_2);
                System.out.println("신규 데이터인지 체크 : " + chkFG_3);
                System.out.println("@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @");


                // 기존 데이터에 속하거나 이전 날짜로 입력하지 않는 데이터
                // 또는 상품의 신규 데이터를 등록하는 경우
                // =============================================================================> 입력 가능
                if( ("T".equals(chkFG_1) && "T".equals(chkFG_2)) || "T".equals(chkFG_3) )
                {
                    dlwProdService.insertPeriodProdCost(hmParam);  // INSERT
                    dlwProdService.updatePeriodProdCost(hmParam);  // 기존의 상품 마지막 데이터 UPDATE
                    mapOutVar.put("result_msg", "success");
                } else if("F".equals(chkFG_1)){  // ============================================> 입력 불가능 : 적용일 사이에 이미 등록된 데이터가 있습니다.
                    mapOutVar.put("result_msg", "fail");
                } else if("F".equals(chkFG_2)){  // ============================================> 입력 불가능 : 이전 데이터는 입력할 수 없습니다.
                    mapOutVar.put("result_msg", "fail2");
                }

                mapOutDs.put("ds_output", listMap);
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
     * 기간별 상품 의전원가 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/rowperiodprodcostdel")
    public ModelAndView getRowPeriodProdCostDel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i=0; i<srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);
                System.out.println("===================================>>>>> 의전원가 삭제 <<<<<<<<<<<");
                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());

                String flag = StringUtils.defaultString((String) mapInVar.get("flag"));
                hmParam.put("flag", flag);

                CommonUtils.printLog(hmParam);

                dlwProdService.getRowPeriodProdCostDel(hmParam); // DEL_YN = 'Y' 로 UPDATE
                dlwProdService.updatePeriodProdCost(hmParam);    // 기존의 상품 마지막 데이터 UPDATE
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
     * 양도양수 리스트 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getTransfList")
    public ModelAndView getTransfList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList = dlwProdService.getTransfList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.20 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 상품모델구분코드 등록 및 수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dmlProdMolelClCd")
    public ModelAndView dmlProdMolelClCd(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("-------------------------- 등록/수정 컨트롤러 입장");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int iCudCnt = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsDetail = (DataSetMap)mapInDs.get("ds_input2");

            ParamUtils.addSaveParam(mParam);

            Object gubun = (Object)mapInVar.get("p_gubun");

            CommonUtils.printLog("========================================= p_gubun" + gubun );
            String sModelClCd = "";
            if (srchInDs != null && srchInDs.size() > 0) {

                hmParam = srchInDs.get(0);

                if(gubun.equals("new")){

                    //상품코드 확인
                    int nTotal = dlwProdService.getDlwProdNewCODE(hmParam);

                   if (nTotal < 1) {
                       System.out.println("* * * * 등록 * * *");
                       sModelClCd = dlwProdService.getModelClCd(hmParam);  // 등록

                       CommonUtils.printLog("1========================================= sModelClCd===>" + sModelClCd );
                       CommonUtils.printLog("1========================================= rgsr_id===>" + mParam.get("rgsr_id") );

                      // pmParam.put("result", "success");
                       hmParam.put("model_cl_cd",sModelClCd);
                       hmParam.put("rgsr_id",mParam.get("rgsr_id"));

                       dlwProdService.insertProdModelClCd(hmParam);  // 등록
//                       dlwProdService.insertProdNew(hmParam);  // 등록

                       if (null != dsDetail && dsDetail.size() > 0) {
                              Map<String, Object> hmParam1 = dsDetail.get(0);
                              CommonUtils.printLog(hmParam1);

                           for (int i=0; i<dsDetail.size(); ++i) {
                               Map mDetail = dsDetail.get(i);
                               ParamUtils.addSaveParam(mDetail);

                               mDetail.put("model_cl_cd",sModelClCd);
                               mDetail.put("rgsr_id",mParam.get("rgsr_id"));

                               iCudCnt += dlwProdService.insertProdKind(mDetail);  // 상세정보등록
                           }
                       }

                   } else {
                        szErrorCode = "-1";
                        szErrorMsg  = "상품코드 중복 오류!!! 상품코드 확인 후 등록해주세요.";

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
                        return modelAndView;

                   }


                } else {
                    System.out.println("* * * * 수정 * * *");
                    sModelClCd = (String)hmParam.get("model_cl_cd");
                    hmParam.put("rgsr_id",mParam.get("rgsr_id"));

                    dlwProdService.updateProdModelClCd(hmParam);  // 수정

                    dlwProdService.deleteProdKind(hmParam);  // 삭제

                    if (null != dsDetail && dsDetail.size() > 0) {
                        Map<String, Object> hmParam1 = dsDetail.get(0);
                        CommonUtils.printLog(hmParam1);

                         for (int i=0; i<dsDetail.size(); ++i) {
                             Map mDetail = dsDetail.get(i);
                             ParamUtils.addSaveParam(mDetail);

                             mDetail.put("model_cl_cd",sModelClCd);
                             mDetail.put("rgsr_id",mParam.get("rgsr_id"));
                             iCudCnt += dlwProdService.insertProdKind(mDetail);  // 상세정보등록
                         }
                     }


                }
                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 상품모델정보  등록 및 수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dmlProddll_update")
    public ModelAndView getdmlProddll_update(XPlatformMapDTO xpDto, Model model) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int iCudCnt = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsDetail = (DataSetMap)mapInDs.get("ds_input2");


            String seq = StringUtils.defaultString((String) mapInVar.get("seq"));

            ParamUtils.addSaveParam(mParam);


             if (srchInDs != null && srchInDs.size() > 0) {

                hmParam = srchInDs.get(0);

                hmParam.put("p_seq",seq) ;

               dlwProdService.updateProdtl(hmParam);  // 수정

       //        dlwProdService.deleteProdtl(hmParam);  // 삭제

                    if (null != dsDetail && dsDetail.size() > 0) {
                        Map<String, Object> hmParam1 = dsDetail.get(0);


                         for (int i=0; i<dsDetail.size(); ++i) {

                             Map mDetail = dsDetail.get(i);
                             ParamUtils.addSaveParam(mDetail);
                             mDetail.put("p_seq",seq);
                       //      mDetail.put("model_cl_cd",sModelClCd);
                             mDetail.put("rgsr_id",mParam.get("rgsr_id"));

                             //CommonUtils.printLog(hmParam1);

                             if ("insert".equals(mDetail.get("gubun"))  ) {
                                  dlwProdService.insertProdtl(mDetail);  // 모델명인서트
                             } else {
                                  dlwProdService.updateProd_dtl(mDetail);  // 모델명업데이트
                             }
                         }
                           //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
             //   mapOutDs.put("ds_output", listMap);
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
     * 상품모델구분코드 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getProdMolelClCd")
    public ModelAndView getProdMolelClCd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sModelClCd = StringUtils.defaultString((String) mapInVar.get("model_cl_cd"));

            if ("".equals(sModelClCd)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "상품코드가 없습니다.");

                return modelAndView;
            }

            hmParam.put("model_cl_cd", sModelClCd);

            Map<String, Object> mDtpt = dlwProdService.getProdMolelClCd(hmParam);
            dtptMap.setRowMaps(mDtpt);
            mapOutDs.put("ds_output", dtptMap);

            List<Map<String, Object>> mList = dlwProdService.getProdKind(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output_list", listMap);

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
     * 상품별 모델정보 관리.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getProdDtl")
    public ModelAndView getProdDtl11(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String seq = StringUtils.defaultString((String) mapInVar.get("seq"));

            if ("".equals(seq)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "상품이 없습니다.");

                return modelAndView;
            }

            hmParam.put("p_seq", seq);

            List<Map<String, Object>> mList1 = dlwProdService.getProddtl(hmParam);
            dtptMap.setRowMaps(mList1);
            mapOutDs.put("ds_output", dtptMap);

            List<Map<String, Object>> mList = dlwProdService.getProdmodeldtl(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output_list", listMap);

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
     * 상품모델구분코드 정보 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delProdMolelClCd")
    public ModelAndView delProdMolelClCd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog(hmParam);

                dlwProdService.deleteProdModelClCd(hmParam);  // 상품모델구분코드 삭제

                dlwProdService.deleteProdKind(hmParam);  // 결합상품 종류 관리 삭제

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
     * 상품모델구분코드 정보 삭제(DEL_FG='Y')
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delFgProdMolelClCd")
    public ModelAndView delFgProdMolelClCd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog(hmParam);

                dlwProdService.deleteFgProdModelClCd(hmParam);  // 상품모델구분코드 삭제

                dlwProdService.deleteFgProdKind(hmParam);  // 결합상품 종류 관리 삭제

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
     * 회원별 녹취 확인 리스트(기존 녹취-엔컴)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recbf-list")
    public ModelAndView getRecBeforeList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getRecBeforeCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwProdService.getRecBeforeList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.05 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 파일관리 리스트(기존 녹취-엔컴)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/fileupload-list")
    public ModelAndView getFileUploadList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            String tab_id = (String) hmParam.get("tab_id");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            if(tab_id.equals("0")){
                List<Map<String, Object>> mList = dlwProdService.getFileUploadList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }else{
                List<Map<String, Object>> mList = dlwProdService.getUploadFailList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.05 접속 로그////////////////////////////////////////////////////////////////////////////////
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

    @RequestMapping(value = "/uploadToNas")
    public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";
        String errMsg = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadToNas 컨트롤러 - 1");
            dlwProdService.uploadToNas(request, response, mResult);
            log.debug("uploadToNas 컨트롤러 - 2");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            errMsg = CommonUtils.nvls((String)mResult.get("errMsg"));

            if (!"".equals(errMsg)) {
                resVarList.add("ErrorCode"	, "-1");
                resVarList.add("ErrorMsg"	, fileNm + "|" + errMsg);
            }
            else { // 업로드 성공
                resVarList.add("ErrorCode"	, szErrorCode);
                resVarList.add("ErrorMsg"	, fileNm + "|" + szErrorMsg);
            }

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", fileNm + "|" + e.getMessage());

            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", fileNm + "|업로드 중 오류가 발생하였습니다.");

            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }

    /**
     * 녹취 파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWavFile")
    public void getWavFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String seq = CommonUtils.nvls(request.getParameter("seq"));
            hmParam = new HashMap<>();
            hmParam.put("seq", seq);
            List<Map<String, Object>> mList = dlwProdService.getFileUploadList(hmParam);

            if (null == mList || mList.size() < 1) {
                throw new EgovBizException("데이터베이스에 파일 정보가 존재하지 않습니다.");
            }

            mRow = mList.get(0);
            upload_cl = CommonUtils.nvls((String)mRow.get("upload_cl"));
            file_nm = CommonUtils.nvls((String)mRow.get("file_nm"));
            reg_man = CommonUtils.nvls((String)mRow.get("reg_man"));
            reg_dm = CommonUtils.nvls((String)mRow.get("reg_dm")).substring(0,8);

            String[] fileClArr 		= { "01_CREDIT", "02_REC", "03_MEMCERTF", "04_TRANSCREDIT", "05_DILIVERY" };
            String[] fileClToArr	= { "0001", "0002", "0003", "0004", "0005" };

            String successPath 		= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("life.rec.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("life.rec.file.unix.successPath");
            }

            String srcFilePath = successPath + reg_man + "/" + reg_dm + "/" + CommonUtils.lpad(upload_cl, 4, "0") + "/" + file_nm;

            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }

            log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 녹취파일 일괄다운로드 압축파일 생성
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mkRecZip")
    public ModelAndView mkRecZip(XPlatformMapDTO xpDto, Model model, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap dtptMap = new DataSetMap();
        List<Map<String,Object>> mList = new ArrayList<>();

        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> mRow = new HashMap<String, Object>();

        String user_ip = request.getRemoteAddr();

        String emple_no = "";

        String successPath = "";
        String zipPath = "";

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        StringBuilder sb = new StringBuilder();

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        boolean zipResult = false;
        boolean bTmp = false;

        try {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("life.rec.file.windows.successPath");
                zipPath 	= EgovProperties.getProperty("life.rec.file.windows.zipPath");
            } else {
                successPath = EgovProperties.getProperty("life.rec.file.unix.successPath");
                zipPath 	= EgovProperties.getProperty("life.rec.file.unix.zipPath");
            }

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_list = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input2");

            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);
            }

            emple_no = CommonUtils.nvls(oLoginUser.getUserid());
            hmParam.put("emple_no", emple_no);
            ParamUtils.addSaveParam(hmParam);

            log.info("ds_list.size() : " + ds_list.size());

            if (null != ds_list && ds_list.size() > 0) {

                FileZipCompress fzc = new FileZipCompress();

                for (int i=0; i<ds_list.size(); ++i) {
                    mRow = ds_list.get(i);

                    sb.delete(0, sb.length());

                    // sb.append("/web_site/HSFactoring/UPLOAD_AFTER/");

                    sb.append(successPath); // 끝에 경로 구분자 있음
                    sb.append((String)mRow.get("reg_man") + "/");
                    sb.append((String)mRow.get("reg_dm") + "/");
                    sb.append((String)mRow.get("file_cl") + "/");
                    sb.append((String)mRow.get("file_nm"));

                    log.info("sb.toString() : " + sb.toString());

                    bTmp = fzc.append(sb.toString());
                }

                if (fzc.getFileCount() < 1) {
                    throw new EgovBizException("파일이 존재하지 않습니다.");
                }

                zipResult = fzc.zip(zipPath, emple_no);

                if (zipResult) {
                    File file = new File(fzc.getZipFilePath());

                    hmParam.put("user_ip"		, user_ip);
                    hmParam.put("file_dir"		, file.getParent().replace("\\", "/"));
                    hmParam.put("file_nm"		, file.getName());
                    commonService.insertFileDown(hmParam);

                    log.info("commonService.insertFileDown 직후 hmParam - no, download_key 확인 필요");
                    CommonUtils.printLog(hmParam);

                    mList.add(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);

                } else {
                    throw new EgovBizException("파일을 압축하는 동안 오류가 발생하였습니다.");
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

        } catch (EgovBizException e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
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
     * 압축파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String file_dir = "";
        String file_nm = "";

        try {
            String no = CommonUtils.nvls(request.getParameter("no"));
            String download_key = CommonUtils.nvls(request.getParameter("download_key"));
            String user_ip = request.getRemoteAddr();

            hmParam = new HashMap<>();
            hmParam.put("no", no);
            hmParam.put("download_key", download_key);
            hmParam.put("user_ip", user_ip);

            log.info("downloadFile> -------- 1");

            List<Map<String, Object>> mList = commonService.selectFileDown(hmParam);

            if (null == mList || mList.size() < 1) {
                throw new EgovBizException("잘못된 접근입니다.");
            }
            log.info("downloadFile> -------- 2");

            mRow = mList.get(0);
            file_dir = CommonUtils.nvls((String)mRow.get("file_dir"));
            file_nm = CommonUtils.nvls((String)mRow.get("file_nm"));

            File xDownFile = new File(file_dir, file_nm);

            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }
            log.info("downloadFile> -------- 3");

            String contentType = request.getContentType();
            log.debug("contentType : " + contentType);

            if (contentType == null) {
                log.info("downloadFile> -------- 4");
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                log.info("downloadFile> -------- 5");
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
            log.info("downloadFile> -------- 6");
            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            log.info("downloadFile> -------- 7");

            outs.flush();
            outs.close();

            int iCudCnt = commonService.updateFileDownDthms(hmParam);

            xDownFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 업로드 녹취 파일 삭제
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hsUploadDataDelete")
    public ModelAndView hsUploadDataDelete(XPlatformMapDTO xpDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            int iCudCnt = dlwProdService.hsUploadDataDelete(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
     /**
     * 상품모델명 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delprodmodeldtl")
    public ModelAndView delprodmodeldtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            String p_seq = StringUtils.defaultString((String) mapInVar.get("seq"));
            String p_model_cd = StringUtils.defaultString((String) mapInVar.get("model_cd"));


            hmParam.put("p_seq", p_seq);
            hmParam.put("p_model_cd", p_model_cd);
//            CommonUtils.printLog(hmParam);
            dlwProdService.deleteProdtl(hmParam);  //

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
     * qa 녹취리스트 가져오기
     * @param xpDto
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nlist")
    public ModelAndView getprodnlist(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                CommonUtils.printLog(hmParam);
                          //List<Map<String, Object>> mList = ctiCrncHstrService.getCtiCrncHstrList(hmParam);
                List<Map<String, Object>> mList = dlwProdService.getprodnList(hmParam);

                //mapOutVar.put("tc_ctiHstr", nTotal);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 모니터링  녹취리스트 가져오기
     * @param xpDto
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitoringlist")
    public ModelAndView getmonitoringlist(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                          //List<Map<String, Object>> mList = ctiCrncHstrService.getCtiCrncHstrList(hmParam);
                List<Map<String, Object>> mList = dlwProdService.getmonitoringlist(hmParam);

                //mapOutVar.put("tc_ctiHstr", nTotal);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 모니터랑녹취삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deletemonitoring_rec")
    public ModelAndView deletemonitoring_rec(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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

        //        CommonUtils.printLog(hmParam);

                dlwProdService.deletemonitoring_rec(hmParam);  // 모니터링 녹취 삭제

             //   CommonUtils.printLog(hmParam);
                List<Map<String, Object>> mList = dlwProdService.getmonitoringlist(hmParam);


            //    CommonUtils.printLog(mList);
                //mapOutVar.put("tc_ctiHstr", nTotal);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

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
     * 모니터링 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deletemonitoring")
    public ModelAndView deletemonitoring(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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

                dlwProdService.deletemonitoring(hmParam);  // 모니터링 삭제

//                List<Map<String, Object>> mList = dlwProdService.getmonitoringlist(hmParam);


  //              CommonUtils.printLog(mList);
                //mapOutVar.put("tc_ctiHstr", nTotal);
    //            listMap.setRowMaps(mList);
      //          mapOutDs.put("ds_output", listMap);

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
     * 매입코드정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/purchaselist")
    public ModelAndView getDlwProdpurchaselist(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

         //   String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));
          //  String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

       //     if (!"Y".equals(sExcelYn)) {
        //        // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
      //      }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getDlwpurchaseCount(hmParam);

            mapOutVar.put("tc_cnt", nTotal);

          //  if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
            List<Map<String, Object>> mList = dlwProdService.getDlwpurchaseList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
       //     }

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
     * 매입코드관리  등록  //  수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/purchasesave")
    public ModelAndView newProdpurchasesave(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
            }
                ParamUtils.addCenterParam(hmParam);


                int nTotal = dlwProdService.getDlwpurchaseCount(hmParam);

                if (nTotal > 0) {
                    ///   등록된 매입코드관리입니다 업데이트 진행
                    //CommonUtils.printLog(hmParam);
                     dlwProdService.updatepurchaseChng(hmParam);  // 수정

                } else {
                    ///   신규 매입 코드   인서트 진행
                       dlwProdService.insertpurchaseNew(hmParam);  // 등록
                }


//                CommonUtils.printLog(hmParam);


                //dlwProdService.updateProdChng(hmParam);  // 수정

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
               // mapOutDs.put("ds_output", listMap);


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
     * 매입코드삭제
     *
     *
     * */


    @RequestMapping(value = "/purchasedelete")
    public ModelAndView purchasedelete(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();




                dlwProdService.deletepurchase(mapInDs);  // 삭제


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
     * 상품모델 매입매출가 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/classprodpricelist")
    public ModelAndView getClassProdPriceList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            //String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));
            String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwProdService.getClassProdPriceCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwProdService.getClassProdPriceList(hmParam);
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
     * 상품모델 매입매출가 등록, 수정, 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newprodmodelprice")
    public ModelAndView newProdModelPrice(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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
                System.out.println("===================================>>>>> 상품모델 매입매출가 등록 <<<<<<<<<<<");
                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());


                String flag = StringUtils.defaultString((String) mapInVar.get("flag"));
                hmParam.put("flag", flag);

                CommonUtils.printLog(hmParam);
                System.out.println("============================================== flag : " + flag);
                mapOutVar.put("result_msg", "success");

                if("insert".equals(flag) || "add".equals(flag)) { // 입력

                    String chkFG_1 = dlwProdService.chkProdModelPrice1(hmParam);  // 입력가능여부 체크1
                    String chkFG_2 = dlwProdService.chkProdModelPrice2(hmParam);  // 입력가능여부 체크2

                    if("T".equals(chkFG_1) && "T".equals(chkFG_2)) {
                        dlwProdService.insertProdModelPrice(hmParam);  // 신규 INSERT
                        dlwProdService.updateProdModelPrice2(hmParam);  // 기존 데이터가 있으면 종료일 UPDATE (** 입력적용일-1)
                    } else if("F".equals(chkFG_1)) {
                        mapOutVar.put("result_msg", "fail_1");
                    } else if("F".equals(chkFG_2)) {
                        mapOutVar.put("result_msg", "fail_2");
                    }
                }
                else if("update".equals(flag)) {                   // 수정 ( 매입처 또는 매입가 또는 매출가 )
                    dlwProdService.updateProdModelPrice(hmParam);
                }
                else if("delete".equals(flag)) {                   // 삭제 후, 수정 (** 기존 데이터가 있으면 종료일 '99991231'로 UPDATE
                    dlwProdService.deleteProdModelPrice(hmParam);
                    dlwProdService.updateProdModelPrice2(hmParam);
                }

                mapOutDs.put("ds_output", listMap);
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

    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    @RequestMapping(value = "/getProductMstList")
    public ModelAndView getProductMstList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                int nTotal = dlwProdService.getProductMstListCount(hmParam);
                List<Map<String, Object>> mList1 = dlwProdService.getProductMstList(hmParam);

                listMap1.setRowMaps(mList1);

                mapOutVar.put("nTotalListCntMst", nTotal);
                mapOutDs.put("ds_output", listMap1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/getProductNoDataList")
    public ModelAndView getProductNoDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                
                int nTotal = dlwProdService.getProductNoDataListCount(hmParam);
                List<Map<String, Object>> mList1 = dlwProdService.getProductNoDataList(hmParam);

                listMap1.setRowMaps(mList1);

                mapOutVar.put("nTotalListCntDtl", nTotal);
                mapOutDs.put("ds_output", listMap1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차 재등록
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/insertProductNoDataList")
    public ModelAndView insertProductNoDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                List<Map<String, Object>> mList1 = new ArrayList<Map<String,Object>>(); // PRODUCT_DTL 정보
                String sProdCd = (String)hmParam.get("prod_cd"); 
                String sPayType = (String)hmParam.get("pay_type"); // 001 : 정기형, 002 : 부정기형
                int sExprNo = Integer.parseInt((String)hmParam.get("expr_no"));
            	int sMonPayAmt = Integer.parseInt((String)hmParam.get("mon_pay_amt"));
                
                if(sPayType != null && sPayType.equals("001"))
                {
                	dlwProdService.deleteProductNoDataList(hmParam);
                	
                	for(int idx = 1; idx <= sExprNo; idx++)
                	{
                		Map<String, Object> productNoDataInsertRow = new HashMap();
                		
                		productNoDataInsertRow.put("prod_cd", sProdCd);
                		productNoDataInsertRow.put("no", idx);
                		productNoDataInsertRow.put("amt", sMonPayAmt);
                		productNoDataInsertRow.put("rel_amt", 0);
                		productNoDataInsertRow.put("add_amt", 0);
                		
                		dlwProdService.insertProductNoDataList(productNoDataInsertRow);
                	}
                }
                else if (sPayType != null && sPayType.equals("002"))
                {
                	mList1 = dlwProdService.getProductDtlList(hmParam);
                	
                	dlwProdService.deleteProductNoDataList(hmParam);
                	
                	if (mList1.size() <= 0) // 결합상품이나 상품별 회차기본정보 데이터가 존재하지 않는 경우
                    {
                		for(int idx = 1; idx <= sExprNo; idx++)
                    	{
                    		Map<String, Object> productNoDataInsertRow = new HashMap();
                    		
                    		productNoDataInsertRow.put("prod_cd", sProdCd);
                    		productNoDataInsertRow.put("no", idx);
                    		productNoDataInsertRow.put("amt", sMonPayAmt);
                    		productNoDataInsertRow.put("rel_amt", 0);
                    		productNoDataInsertRow.put("add_amt", 0);
                    		
                    		dlwProdService.insertProductNoDataList(productNoDataInsertRow);
                    	}
                    }
                	else // 결합상품이고 상품별 회차기본정보 데이터가 존재하는 경우
                	{
                		for(int mIdx = 0; mIdx < mList1.size(); mIdx++)
                        {
                        	Map<String, Object> rowData = mList1.get(mIdx);
                        	
                        	int nStNo = Integer.parseInt(String.valueOf(rowData.get("st_no"))); // 시작회차
                        	int nEndNo = Integer.parseInt(String.valueOf(rowData.get("end_no"))); // 종료회차
                        	int nAmt = Integer.parseInt(String.valueOf(rowData.get("month_pay_amt")));
                        	int nRelAmt = Integer.parseInt(String.valueOf(rowData.get("allt_amt")));
                        	int nAddAmt = Integer.parseInt(String.valueOf(rowData.get("add_amt")));
                        	
                        	for(int dIdx = nStNo; dIdx <= nEndNo; dIdx++)
                        	{
                        		Map<String, Object> productNoDataInsertRow = new HashMap();
                        		
                        		productNoDataInsertRow.put("prod_cd", sProdCd);
                        		productNoDataInsertRow.put("no", dIdx);
                        		productNoDataInsertRow.put("amt", nAmt);
                        		productNoDataInsertRow.put("rel_amt", nRelAmt);
                        		productNoDataInsertRow.put("add_amt", nAddAmt);
                        		
                        		dlwProdService.insertProductNoDataList(productNoDataInsertRow);
                        	}
                        }
                	}
                }
                else
                {
                	 szErrorCode = "-1";
                     szErrorMsg = "청구방법이 등록되지 않은 상품입니다. \n상품정보 화면에서 [청구방법]항목을 저장하여 주십시오.";
                     modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                     modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                     return modelAndView;
                }
                                
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output", listMap1);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
}