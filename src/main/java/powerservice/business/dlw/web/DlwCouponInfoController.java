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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwCouponInfoService;
import powerservice.business.dlw.service.impl.EventMonitorDao;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import powerservice.core.util.excel.ExcelImportRowHandler;
import powerservice.core.util.excel.ExcelImporter;
import powerservice.core.util.excel.ExcelValidator;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

import powerservice.business.dlw.service.DlwProdVasService;

//2018.02.02 로그 추가
import powerservice.business.common.service.CommonService;

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
@RequestMapping(value = "/dlw/couponInfo")
public class DlwCouponInfoController {


    @Resource
    private DlwCouponInfoService dlwCouponInfoService;


    @Resource
    private DlwProdVasService dlwProdVasService;

    @Resource
    private CommonService commonService;


    /**
     * 쿠폰 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/selectCouponInfoList")
    public ModelAndView selectCouponInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            ParamUtils.addCenterParam(hmParam);

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            List<Map<String, Object>> mList = dlwCouponInfoService.selectCouponInfoList(hmParam);
            if (mList != null && mList.size() > 0) {
                Map pMap2 = mList.get(0);

//System.out.println("selectProdOptSvcMstList total_cnt===>"+pMap2.get("total_cnt"));
                mapOutVar.put("total_cnt", pMap2.get("total_cnt"));
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.02.02 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////


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
     * 쿠폰현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/selectCouponStatList")
    public ModelAndView selectCouponStatList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            ParamUtils.addCenterParam(hmParam);

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            List<Map<String, Object>> mList = dlwCouponInfoService.selectCouponStatList(hmParam);
            if (mList != null && mList.size() > 0) {
                Map pMap2 = mList.get(0);

//System.out.println("selectProdOptSvcMstList total_cnt===>"+pMap2.get("total_cnt"));
                mapOutVar.put("total_cnt", pMap2.get("total_cnt"));
            }

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
     * 쿠폰정보 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/selectCouponHist")
    public ModelAndView selectCouponHist(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            List<Map<String, Object>> mList = dlwCouponInfoService.selectCouponHist(hmParam);
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
     * 상품별 부가서비스 수정 화면 정보조회 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/selectCouponDetail")
    public ModelAndView selectUpdateList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap detailMap = new DataSetMap();

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

            Map<String, Object> mDtpt = dlwCouponInfoService.selectCouponDetail(hmParam);
            detailMap.setRowMaps(mDtpt);
            mapOutDs.put("ds_output", detailMap);

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
     * 쿠폰정보 MST 등록/수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCouponInfoMst")
    public ModelAndView saveCouponInfoMst(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }
            String msg = "";
            dlwCouponInfoService.saveCouponInfoMst(hmParam);  // 등록

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
     * 쿠폰정보 DTL 등록/수정
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCouponInfoDtl")
    public ModelAndView saveCouponInfoDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }
            String msg = "";
            dlwCouponInfoService.saveCouponInfoDtl(hmParam);  // 등록

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
     * 쿠폰정보 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCouponInfoMst")
    public ModelAndView deleteCouponInfoMst(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }
            dlwCouponInfoService.deleteCouponInfoMst(srchInDs);  // 등록

            //2018.02.02 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 엑셀파일로 부터 쿠폰 정보를 추출한다.
     *
     * @param pmParam Map<String, Object>
     * @param pFileList List<MultipartFile>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cpExcelUpload")
    public ModelAndView cpExcelUpload(@RequestParam Map<String, Object> pmParam,final HttpServletRequest request) throws Exception{
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        // Ajax 오류 처리자 설정
        ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        oServletRequestAttribute.getRequest().setAttribute("bAjaxExceptionHandler", "true");

        ActionResult oResult = new ActionResult();

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        FileItem file;
        file = items.get(0);
        InputStream is = file.getInputStream();
        List<String> colList = new ArrayList<String>();
        int colIndex = 0;

        colList.add(colIndex++, "coupon_tp");
        colList.add(colIndex++, "coupon_no1");
        colList.add(colIndex++, "coupon_no2");
        colList.add(colIndex++, "isu_dt");

        String result_code = "";
        String result_msg = "";
        List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();
        UserSession uSession = (UserSession) SessionUtils.getLoginUser();

        if (file != null) {//file 널이 아닐경우
            ParamUtils.addCenterParam(pmParam);
            //엑셀
            ExcelImporter importer = new ExcelImporter(colList, excelList);
            //엑셀을 DB import
            importer.setHanlder(new ExcelImportRowHandler() {
                @Override
                public boolean handleRow(Map<String, Object> row, int rowNum) {
                    boolean savefg = true;
                    String resultMsg = "";

                    if (row.toString().equals("{}")){
                        return false;
                    }

                    resultMsg += ExcelValidator.validate(row, "isu_dt", ExcelValidator.FILTER_NUMBER);
                    resultMsg += ExcelValidator.validate(row, "send_dt", ExcelValidator.FILTER_NUMBER);

                    //row.put("custclscd", custclscd);
                    ParamUtils.addSaveParam(row);

                    return savefg;
                }
            });

            importer.process(is);

            if (excelList.size() > 0) {

                Map<String, Object> hmParam = null;
                String ckResult = "";

                ParamUtils.addSaveParam(pmParam); //rgsr_id 셋팅

                int succCnt = 0;
                int errCnt = 0;
                int totCnt = 0;
                int dupCnt = 0;

                for (int i = 0; i < excelList.size(); i++) {
                    hmParam = new HashMap<String, Object>();

                    String coupon_tp = CommonUtils.checkNull((String)excelList.get(i).get("coupon_tp"));
                    String coupon_no1 = CommonUtils.checkNull((String)excelList.get(i).get("coupon_no1"));
                    String coupon_no2 = CommonUtils.checkNull((String)excelList.get(i).get("coupon_no2"));
                    String isu_dt = CommonUtils.checkNull((String)excelList.get(i).get("isu_dt"));

                    hmParam.put("coupon_tp", coupon_tp);
                    hmParam.put("coupon_no1", coupon_no1);
                    hmParam.put("coupon_no2", coupon_no2);
                    hmParam.put("isu_dt", isu_dt);


                    if("".equals(coupon_tp) || "".equals(coupon_no1) ) {
                        result_code = "-1";
                        result_msg = "(필수사항 누락)에러 쿠폰종류번호:"+coupon_tp+",쿠폰번호1:"+coupon_no1;

                        errCnt++;

                        hmParam.put("err_cl", "SV");
                        hmParam.put("err_val1", coupon_tp);
                        hmParam.put("err_val2", coupon_no1);
                        hmParam.put("err_val3", coupon_no2);

                        hmParam.put("err_text", result_msg);

//                        dlwVatSvcService.insertExcelUploadErr(hmParam);

                        uSession.setTempCode(result_code);
                        uSession.setTempMsg(result_msg);
                        SessionUtils.setLoginUser(uSession);

                        throw new Exception (result_msg);

                    } else {

                        hmParam.put("coupon_no", coupon_no1);
                        dupCnt = Integer.parseInt(dlwCouponInfoService.selectDupCouponNo(hmParam));
                        if (dupCnt > 0  ) { // 중복인 경우
                            result_code = "-1";
                            result_msg = "중복에러==> 쿠폰종류번호:"+coupon_tp+",쿠폰번호1:"+coupon_no1;

                            dupCnt++;
                            hmParam.put("err_cl", "SV");
                            hmParam.put("err_val1", coupon_tp);
                            hmParam.put("err_val2", coupon_no1);
                            hmParam.put("err_val3", coupon_no2);

                            hmParam.put("err_text", result_msg);

                            uSession.setTempCode(result_code);
                            uSession.setTempMsg(result_msg);
                            SessionUtils.setLoginUser(uSession);

                            throw new Exception (result_msg);
                        }

                        hmParam.put("coupon_no", coupon_no2);
                        dupCnt = Integer.parseInt(dlwCouponInfoService.selectDupCouponNo(hmParam));
                        if (dupCnt > 0  ) { // 중복인 경우
                            result_code = "-1";
                            result_msg = "중복에러==> 쿠폰종류번호:"+coupon_tp+",쿠폰번호2:"+coupon_no2;

                            dupCnt++;
                            hmParam.put("err_cl", "SV");
                            hmParam.put("err_val1", coupon_tp);
                            hmParam.put("err_val2", coupon_no1);
                            hmParam.put("err_val3", coupon_no2);

                            hmParam.put("err_text", result_msg);

                            uSession.setTempCode(result_code);
                            uSession.setTempMsg(result_msg);
                            SessionUtils.setLoginUser(uSession);

                            throw new Exception (result_msg);
                        }

                        hmParam.put("dml_mode", "insert");
                        dlwCouponInfoService.saveCouponInfoMst(hmParam);
                        succCnt++;

                    }

                } //for loop end

                System.out.println("dupCnt:"+dupCnt);
                System.out.println("succCnt:"+succCnt);
                System.out.println("errCnt:"+errCnt);

                if(errCnt == 0 && dupCnt == 0){
                    result_code = "0";
                    result_msg = "업로드를 완료하였습니다.\n"+"등록건수:"+succCnt+"건, 중복:"+dupCnt+"건"+", 오류:"+errCnt+"건";
                }

                oResult.setErrMsg(result_msg);
                oResult.setResult(ActionResultType.OK);

            } else {
                result_code = "-1";
                result_msg = "엑셀 업로드 할 내역이 없습니다.";
                oResult.setErrMsg(result_msg);
                oResult.setResult(ActionResultType.ERROR);
            }

            uSession.setTempCode(result_code);
            uSession.setTempMsg(result_msg);
            SessionUtils.setLoginUser(uSession);

        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 쿠폰정보 업로드 결과 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/cpExcelResult")
    public ModelAndView cpExcelResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            UserSession uSession = (UserSession) SessionUtils.getLoginUser();

            mapOutVar.put("tc_copon_excel_upload_rcd", uSession.getTempCode());
            mapOutVar.put("tc_copon_excel_upload_rmsg", uSession.getTempMsg());

            uSession.setTempCode("");
            uSession.setTempMsg("");
            SessionUtils.setLoginUser(uSession);


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

}