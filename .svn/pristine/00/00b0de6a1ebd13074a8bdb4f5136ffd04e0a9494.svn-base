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
import powerservice.business.dlw.service.DlwVatSvcService;
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
@RequestMapping(value = "/dlw/vatSvc")
public class DlwVatSvcController {

    @Resource
    private DlwVatSvcService dlwVatSvcService;

    @Resource
    private DlwCouponInfoService dlwCouponInfoService;

    @Resource
    private CommonService commonService;


    /**
     * 부가서비스 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getVatSvcList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String excel_fg = (String)mapInVar.get("excel_fg");

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);

                if (!"Y".equals(excel_fg)) {
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }


            List<Map<String, Object>> mList = dlwVatSvcService.getVatSvcList(hmParam);
            if (mList != null && mList.size() > 0) {
                Map pMap2 = mList.get(0);
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
     * 엑셀파일로 부터 쿠폰 정보를 추출한다.
     *
     * @param pmParam Map<String, Object>
     * @param pFileList List<MultipartFile>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/coponExcelUpload")
    public ModelAndView coponExcelUpload(@RequestParam Map<String, Object> pmParam,final HttpServletRequest request) throws Exception{
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

        colList.add(colIndex++, "accnt_no");
        colList.add(colIndex++, "optsvc_seq");
        colList.add(colIndex++, "isu_dt");
        colList.add(colIndex++, "isu_no");
        colList.add(colIndex++, "invoice_no");
        colList.add(colIndex++, "addressee");
        colList.add(colIndex++, "send_dt");
        colList.add(colIndex++, "addr_note");
        colList.add(colIndex++, "no");

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

                    String accnt_no = CommonUtils.checkNull((String)excelList.get(i).get("accnt_no"));
                    String optsvc_seq = CommonUtils.checkNull((String)excelList.get(i).get("optsvc_seq"));
                    String isu_dt = CommonUtils.checkNull((String)excelList.get(i).get("isu_dt"));
                    String isu_no = CommonUtils.checkNull((String)excelList.get(i).get("isu_no"));
                    String invoice_no = CommonUtils.checkNull((String)excelList.get(i).get("invoice_no"));
                    String addressee = CommonUtils.checkNull((String)excelList.get(i).get("addressee"));
                    String send_dt = CommonUtils.checkNull((String)excelList.get(i).get("send_dt"));
                    String addr_note = CommonUtils.checkNull((String)excelList.get(i).get("addr_note"));
                    String no = CommonUtils.checkNull((String)excelList.get(i).get("no"));

                    hmParam.put("accnt_no", accnt_no);
                    hmParam.put("optsvc_seq", optsvc_seq);
                    hmParam.put("isu_dt", isu_dt);
                    hmParam.put("isu_no", isu_no);
                    hmParam.put("invoice_no", invoice_no);
                    hmParam.put("addressee", addressee);
                    hmParam.put("send_dt", send_dt);
                    hmParam.put("addr_note", addr_note);
                    hmParam.put("no", 		no);
                    hmParam.put("rgsr_id", pmParam.get("rgsr_id"));
                    hmParam.put("cntr_cd", pmParam.get("cntr_cd"));
                    hmParam.put("rsps_dept_cd", uSession.getTeamcd());   // 담당부서

                    if("".equals(accnt_no) || "".equals(optsvc_seq)  || "".equals(isu_no) ) {
                        result_code = "-1";
                        result_msg = "(필수사항 누락)에러 회원번호:"+accnt_no+",쿠폰번호:"+isu_no+",서비스코드:"+optsvc_seq;

                        errCnt++;

                        hmParam.put("err_cl", "SV");
                        hmParam.put("err_val1", accnt_no);
                        hmParam.put("err_val2", isu_no);
                        hmParam.put("err_val3", optsvc_seq);

                        hmParam.put("err_text", result_msg);

                        dlwVatSvcService.insertExcelUploadErr(hmParam);

                        uSession.setTempCode(result_code);
                        uSession.setTempMsg(result_msg);
                        SessionUtils.setLoginUser(uSession);

                        throw new Exception (result_msg);

                    } else {
                        String sDupFlag = dlwVatSvcService.selectDupIsuNo(hmParam);


                        if ("duplication".equals(sDupFlag)  ) { // 중복인 경우
                            result_msg = "중복에러==> 회원번호:"+accnt_no+",쿠폰번호:"+isu_no+",서비스코드:"+optsvc_seq;

                            dupCnt++;
                            hmParam.put("err_cl", "SV");
                            hmParam.put("err_val1", accnt_no);
                            hmParam.put("err_val2", isu_no);
                            hmParam.put("err_val3", optsvc_seq);

                            hmParam.put("err_text", result_msg);

                            dlwVatSvcService.insertExcelUploadErr(hmParam);

                            uSession.setTempCode(result_code);
                            uSession.setTempMsg(result_msg);
                            SessionUtils.setLoginUser(uSession);

                            throw new Exception (result_msg);

                        } else if ("success".equals(dlwVatSvcService.uploadExcelSvcIsuHist(hmParam))  ) { // 중복이 아닌경우
                            succCnt++;
                        }else{
                            result_msg = "에러=> 회원번호:"+accnt_no+",쿠폰번호:"+isu_no+",서비스코드:"+optsvc_seq;

                            errCnt++;
                            hmParam.put("err_cl", "SV");
                            hmParam.put("err_val1", accnt_no);
                            hmParam.put("err_val2", isu_no);
                            hmParam.put("err_val3", optsvc_seq);
                            hmParam.put("err_text", result_msg);

                            dlwVatSvcService.insertExcelUploadErr(hmParam);

                            uSession.setTempCode(result_code);
                            uSession.setTempMsg(result_msg);
                            SessionUtils.setLoginUser(uSession);

                            throw new Exception (result_msg);
                        }

                    }

                } //for loop end

                System.out.println("dupCnt:"+dupCnt);
                System.out.println("succCnt:"+succCnt);
                System.out.println("errCnt:"+errCnt);

                result_code = "0";
                result_msg = "업로드를 완료하였습니다.\n"+"등록건수:"+succCnt+"건, 중복:"+dupCnt+"건"+", 오류:"+errCnt+"건";

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

//            System.out.println("결과 등록 ######################>:");


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

    @RequestMapping(value = "/coponExcelResult")
    public ModelAndView coponExcelResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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



    /**
     * 엑셀파일로 부터 우편번호 업로드 정보를 추출한다.
     *
     * @param pmParam Map<String, Object>
     * @param pFileList List<MultipartFile>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/zipcodeExcelUpload")
    public ModelAndView zipcodeExcelUpload(@RequestParam Map<String, Object> pmParam,final HttpServletRequest request) throws Exception{
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

        colList.add(colIndex++, "accnt_no");
        colList.add(colIndex++, "optsvc_seq");
        colList.add(colIndex++, "invoice_no");
        colList.add(colIndex++, "addressee");
        colList.add(colIndex++, "send_dt");
        colList.add(colIndex++, "addr_note");

        String result_code = "";
        String result_msg = "";
        List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();

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

                    resultMsg += ExcelValidator.validate(row, "send_dt", ExcelValidator.FILTER_NUMBER);

                    //row.put("custclscd", custclscd);
                    ParamUtils.addSaveParam(row);

                    return savefg;
                }
            });

            importer.process(is);

            if (excelList.size() > 0) {

                Map<String, Object> hmParam = new HashMap<String, Object>();
                String ckResult = "";


                ParamUtils.addSaveParam(pmParam); //rgsr_id 셋팅


                int succCnt = 0;
                int errCnt = 0;
                int totCnt = 0;
                int dupCnt = 0;

                for (int i = 0; i < excelList.size(); i++) {
                    hmParam = new HashMap<String, Object>();

                    String accnt_no = CommonUtils.checkNull((String)excelList.get(i).get("accnt_no"));
                    String optsvc_seq = CommonUtils.checkNull((String)excelList.get(i).get("optsvc_seq"));
                    String invoice_no = CommonUtils.checkNull((String)excelList.get(i).get("invoice_no"));
                    String addressee = CommonUtils.checkNull((String)excelList.get(i).get("addressee"));
                    String send_dt = CommonUtils.checkNull((String)excelList.get(i).get("send_dt"));
                    String addr_note = CommonUtils.checkNull((String)excelList.get(i).get("addr_note"));

                    hmParam.put("accnt_no", accnt_no);
                    hmParam.put("optsvc_seq", optsvc_seq);
                    hmParam.put("invoice_no", invoice_no);
                    hmParam.put("addressee", addressee);
                    hmParam.put("send_dt", send_dt);
                    hmParam.put("addr_note", addr_note);
                    hmParam.put("rgsr_id", pmParam.get("rgsr_id"));

                    if("".equals(accnt_no) || "".equals(optsvc_seq)  || "".equals(invoice_no) ) {
                        result_code = "-1";
                        result_msg  = ("엑셀 업로드를 실패했습니다. (필수사항 누락)");
                        errCnt++;

                        hmParam.put("err_cl", "SV");
                        hmParam.put("err_val1", accnt_no);
                        hmParam.put("err_text", result_msg);

                        dlwVatSvcService.insertExcelUploadErr(hmParam);

                    } else {

                        if ("success".equals(dlwVatSvcService.uploadExcelSvcPostInfo(hmParam))  ) {
                            succCnt++;
                        }else{
                            errCnt++;
                            hmParam.put("err_cl", "SV");
                            hmParam.put("err_val1", accnt_no);
                            hmParam.put("err_text", "The error");

                            dlwVatSvcService.insertExcelUploadErr(hmParam);
                        }

                    }

                } //for loop end

                result_code = "0";
                result_msg = "업로드를 완료하였습니다.\n"+"등록건수:"+succCnt+"건, 오류:"+errCnt+"건";

                oResult.setErrMsg(result_msg);
                oResult.setResult(ActionResultType.OK);

            } else {
                result_code = "-1";
                result_msg = "엑셀 업로드 할 내역이 없습니다.";
                oResult.setErrMsg(result_msg);
                oResult.setResult(ActionResultType.ERROR);
            }

            UserSession uSession = (UserSession) SessionUtils.getLoginUser();
            uSession.setTempCode(result_code);
            uSession.setTempMsg(result_msg);
            SessionUtils.setLoginUser(uSession);

        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 우편정보 업로드 결과 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/zipcodeExcelResult")
    public ModelAndView zipcodeExcelResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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


    /**
     * 부가서비스 발급
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/regSvcIsuHist")
    public ModelAndView regSvcIsuHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> pmParam = new HashMap<String, Object>();
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
            UserSession uSession = (UserSession) SessionUtils.getLoginUser();

            ParamUtils.addSaveParam(pmParam);
            int succCnt = 0;
            int errCnt = 0;


            if (srchInDs != null && srchInDs.size() > 0) {

                String msg = "";
                String sOptSvcCd = "";
                String sDmlMode = "";
                Map<String, Object> hmMap = null;
                for (int i = 0; i < srchInDs.size(); i++) {
                    hmParam = srchInDs.get(i);
                    hmMap = new HashMap<String, Object>();

                    hmParam.put("reg_man", oLoginUser.getUserid());
                    hmParam.put("rgsr_id", pmParam.get("rgsr_id"));
                    hmParam.put("cntr_cd", pmParam.get("cntr_cd"));
                    hmParam.put("rsps_dept_cd", uSession.getTeamcd());   // 담당부서

                    sOptSvcCd = String.valueOf(hmParam.get("opt_svc_cd"));  //쿠폰종류
                    sDmlMode = String.valueOf(hmParam.get("dml_mode"));  //발급/재발급 구분

                    CommonUtils.printLog(hmParam);

                    if("insert".equals(sDmlMode)){ //신규발급
                        if("0012".equals(sOptSvcCd)){ //대명투어몰(10만원)
                            msg = dlwVatSvcService.regSvcIsuHist(hmParam);

                            if ("dupNo".equals(msg)) {
                                hmMap.put("dupNo", "dupNo");
                                hmMap.put("succCnt", Integer.valueOf(succCnt));
                            }

                            if (!"0".equals(msg)){
                                succCnt += Integer.valueOf(String.valueOf(msg)).intValue();
                            }else {
                                errCnt++;
                            }
                        }else{ //대명투어몰(10만원) 제외한 모든 쿠폰
                            dlwVatSvcService.newRegSvcIsuHist(hmParam);
                        }
                    }else{ //재발급
                        dlwVatSvcService.deleteSvcIsuHist(hmParam);

                        if("0012".equals(sOptSvcCd)){ //대명투어몰(10만원)
                            msg = dlwVatSvcService.regSvcIsuHist(hmParam);

                            if ("dupNo".equals(msg)) {
                                hmMap.put("dupNo", "dupNo");
                                hmMap.put("succCnt", Integer.valueOf(succCnt));
                            }

                            if (!"0".equals(msg)){
                                succCnt += Integer.valueOf(String.valueOf(msg)).intValue();
                            }else {
                                errCnt++;
                            }
                        }else{ //대명투어몰(10만원) 제외한 모든 쿠폰
                            hmParam.put("coupon_inh_no", hmParam.get("org_isu_no")); //키셋팅
                            hmParam.put("use_yn", "N");
                            hmParam.put("note_cd", "07"); //재발급자동회수
                            hmParam.put("recov_dt", hmParam.get("isu_dt")); //발급일을 회수일로 셋팅
                            hmParam.put("upd_id", hmParam.get("reg_man")); //
                            dlwCouponInfoService.updateCouponInfoDtlUseYn(hmParam);

                            hmParam.put("coupon_inh_no", hmParam.get("org_isu_no")); //쿠폰고유번호 셋팅
                            hmParam.put("use_yn", "N"); //쿠폰고유번호 셋팅
                            //mst 미사용으로 변경
                            dlwCouponInfoService.updateCouponInfoMstUseYn(hmParam);
                            //회수 후 신규발급과 똑같은 프로세스
                            dlwVatSvcService.newRegSvcIsuHist(hmParam);
                        }
                    }
                }

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
                //mapOutDs.put("ds_output", listMap);

                hmMap.put("succCnt", succCnt);
                hmMap.put("errCnt", errCnt);


                mapOutVar.put("ds_output", hmMap);

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
     * 쿠폰 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/couponDelete")
    public ModelAndView couponDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("========================================= couponDelete start");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

/*
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
 */

            ParamUtils.addCenterParam(hmParam);
            int succCnt = 0;
            int errCnt = 0;

            CommonUtils.printLog(hmParam);

            if (srchInDs != null && srchInDs.size() > 0) {


                String msg = "";
                String sIsuCouponTp = "";
                Map<String, Object> hmMap = null;
                for (int i = 0; i < srchInDs.size(); i++) {
                    hmParam = srchInDs.get(i);
                    sIsuCouponTp = String.valueOf(hmParam.get("isu_coupon_tp"));
                    CommonUtils.printLog("========================================= isu_coupon_tp"+hmParam.get("isu_coupon_tp"));
                    CommonUtils.printLog("========================================= org_isu_no"+hmParam.get("org_isu_no"));
                    CommonUtils.printLog("========================================= coupon_dtl_no"+hmParam.get("coupon_dtl_no"));


                    hmParam.put("reg_man", oLoginUser.getUserid());
                    hmParam.put("upd_id", oLoginUser.getUserid());

                    dlwVatSvcService.deleteSvcIsuHist(hmParam);

                    if("KEY".equals(sIsuCouponTp)){
                        hmParam.put("coupon_inh_no", hmParam.get("org_isu_no")); //쿠폰고유번호 셋팅
                        hmParam.put("use_yn", "N"); //쿠폰고유번호 셋팅

                        //dtl 삭제
                        dlwCouponInfoService.deleteCouponInfoDtl(hmParam);
                        //mst 미사용으로 변경
                        dlwCouponInfoService.updateCouponInfoMstUseYn(hmParam);
                    }
                }
                hmMap = new HashMap<String, Object>();

                hmMap.put("succCnt", succCnt);
                hmMap.put("errCnt", errCnt);

                mapOutVar.put("ds_output", hmMap);

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
     * 부가서비스 무효화
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vatSvcInvalid")
    public ModelAndView vatSvcInvalid(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("========================================= couponDelete start");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

/*
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
 */

            ParamUtils.addCenterParam(hmParam);
            int succCnt = 0;
            int errCnt = 0;

            CommonUtils.printLog(hmParam);

            if (srchInDs != null && srchInDs.size() > 0) {

                CommonUtils.printLog("========================================= srchInDs.size()"+srchInDs.size());

                String msg = "";
                Map<String, Object> hmMap = null;
                for (int i = 0; i < srchInDs.size(); i++) {
                    hmParam = srchInDs.get(i);
                    hmMap = new HashMap<String, Object>();

                    hmParam.put("reg_man", oLoginUser.getUserid());

//                    CommonUtils.printLog(hmParam);
                    dlwVatSvcService.vatSvcInvalid(hmParam);

                }

                hmMap.put("succCnt", succCnt);
                hmMap.put("errCnt", errCnt);

                mapOutVar.put("ds_output", hmMap);

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
     * 상담 저장시에 부가서비스와 키 동기화 시켜 주는 로직 수행
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateCallCenterVatSvcHist")
    public ModelAndView updateCallCenterVatSvcHist(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);
            CommonUtils.printLog(hmParam);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                dlwVatSvcService.updateCallCenterVatSvcHist(hmParam);

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
     * 쿠폰 회수
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/couponRecovery")
    public ModelAndView couponRecovery(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("========================================= couponRecovery start");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

/*
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
 */

            ParamUtils.addCenterParam(hmParam);
            int succCnt = 0;
            int errCnt = 0;

            CommonUtils.printLog(hmParam);

            if (srchInDs != null && srchInDs.size() > 0) {


                String msg = "";
                String sIsuCouponTp = "";
                Map<String, Object> hmMap = null;
                for (int i = 0; i < srchInDs.size(); i++) {
                    hmParam = srchInDs.get(i);
                    sIsuCouponTp = String.valueOf(hmParam.get("isu_coupon_tp"));
                    CommonUtils.printLog("========================================= isu_coupon_tp"+hmParam.get("isu_coupon_tp"));
                    CommonUtils.printLog("========================================= org_isu_no"+hmParam.get("org_isu_no"));
                    CommonUtils.printLog("========================================= coupon_dtl_no"+hmParam.get("coupon_dtl_no"));

                    hmParam.put("reg_man", oLoginUser.getUserid());
                    hmParam.put("upd_id", oLoginUser.getUserid());

                    dlwVatSvcService.deleteSvcIsuHist(hmParam);

                    if("KEY".equals(sIsuCouponTp)){
                        hmParam.put("coupon_inh_no", hmParam.get("org_isu_no")); //키셋팅
                        hmParam.put("use_yn", "N");
                        //hmParam.put("note_cd", "07"); //재발급자동회수
                        hmParam.put("upd_id", hmParam.get("reg_man")); //
                        dlwCouponInfoService.updateCouponInfoDtlUseYn(hmParam);

                        hmParam.put("coupon_inh_no", hmParam.get("org_isu_no")); //쿠폰고유번호 셋팅
                        hmParam.put("use_yn", "N"); //쿠폰고유번호 셋팅
                        //mst 미사용으로 변경
                        dlwCouponInfoService.updateCouponInfoMstUseYn(hmParam);
                    }
                }
                hmMap = new HashMap<String, Object>();

                hmMap.put("succCnt", succCnt);
                hmMap.put("errCnt", errCnt);

                mapOutVar.put("ds_output", hmMap);

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


}