/*
 * (@)# DlvController.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
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
package powerservice.business.chn.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import powerservice.business.chn.service.DlvService;
import powerservice.business.dlw.service.DlwProdService;
import powerservice.business.dlw.web.DlwProdController;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.excel.ExcelImportRowHandler;
import powerservice.core.util.excel.ExcelImporter;
import powerservice.core.util.excel.ExcelValidator;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;


/**
 * 물류 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
 * @프로그램ID PS450400
 *
 */
@Controller
@RequestMapping(value = "/chnl/dlv")
public class DlvController {
    @Resource
    private DlvService dlvService;

    @Resource
    private DlwProdService dlwProdService;

    private final Logger log = LoggerFactory.getLogger(DlvController.class);


    /**
     * 물류 정보(목록)를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list")
    public ModelAndView getDlvList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = dlvService.getDlvCount(hmParam);;
            List<Map<String, Object>> mList = dlvService.getDlvList(hmParam);

            listMap.setRowMaps(mList);
            mapOutVar.put("total_count", nTotal);
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

    /**
     * 물류/물류상세 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/save")
    public ModelAndView saveDlv(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            Map<String, Object> hmParam = listInDs.get(0);
            ParamUtils.addSaveParam(hmParam);

            String sDlvId = dlvService.saveDlv(hmParam);

            mapOutVar.put("sDlvId", sDlvId);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
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
     * 물류 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delete")
    public ModelAndView deleteDlv(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            ParamUtils.addCenterParam(mapInVar);

            dlvService.deleteDlv(mapInVar);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
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
     * 물류상세 정보(목록)를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dtl/list")
    public ModelAndView getDlvDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            Map<String, Object> pMap = listInDs.get(0);
            hmParam.put("startLine", pMap.get("startNum"));
            hmParam.put("endLine", pMap.get("endNum"));


            ParamUtils.addCenterParam(hmParam);

            //int nTotal = dlvService.getDlvDtlCount(hmParam);
            //List<Map<String, Object>> mList = dlvService.getDlvDtlList(hmParam);

            //listMap.setRowMaps(mList);
            //mapOutVar.put("total_count", nTotal);
            //mapOutDs.put("ds_output", listMap);

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

    /**
     * 물류상세 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dtl/delete")
    public ModelAndView deleteDlvDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            ParamUtils.addCenterParam(mapInVar);

            dlvService.deleteDlvDtl(mapInVar);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
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
     * 엑셀파일로 부터 해약환급금 정보를 추출한다.
     *
     * @param pmParam Map<String, Object>
     * @param pFileList List<MultipartFile>
     * @return ModelAndView
     * @throws Exception
     */

    /*
    @RequestMapping(value = "/excel-upload")
    public ModelAndView uploadExcelFile(@RequestParam Map<String, Object> pmParam,final HttpServletRequest request) throws Exception{
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

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
        colList.add(colIndex++, "dlv_id");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "accnt_no");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "dlv_no");
        colList.add(colIndex++, "dlv_dsps_dt");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "");
        colList.add(colIndex++, "sndg_kind_cd");
        colList.add(colIndex++, "dlv_type_cd");
        colList.add(colIndex++, "dlv_stat_cd");
        colList.add(colIndex++, "dlv_dsps_rsn_cd");
        colList.add(colIndex++, "dsps_mthd_cd");
        colList.add(colIndex++, "dsps_rsn_cd");



        List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();

        if (file != null) {//file 널이 아닐경우
            //엑셀
            ExcelImporter importer = new ExcelImporter(colList, excelList, 5);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + excelList);

            //엑셀을 DB import
            importer.setHanlder(new ExcelImportRowHandler() {
                @Override
                public boolean handleRow(Map<String, Object> row, int rowNum) {
                    boolean savefg = true;
                    String resultMsg = "";

                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + row);

                    if (row.toString().equals("{}")){
                        return false;
                    }

                    Map<String, Object> mMemAccntInfo = null;

                    resultMsg += ExcelValidator.validate(row, "accnt_no", ExcelValidator.FILTER_REQUIRE);

                    if (resultMsg.equals("")) {
                        try {
                            mMemAccntInfo = dlwProdService.getMemProdAccntWithDlv(row.get("accnt_no").toString());
                        } catch (Exception e1) {
                            System.out.println("ERROR : 증서관리에서 회원번호 기준으로 엔컴정보 조회 오류! Accnt_no:" + row.get("accnt_no").toString());
                            e1.printStackTrace();
                        }

                        if (null != mMemAccntInfo && null != mMemAccntInfo.get("accnt_no")) {
                            row.put("mem_no", mMemAccntInfo.get("mem_no"));
                            row.put("mem_nm", mMemAccntInfo.get("mem_nm"));
                            row.put("csmm_prfl_no", mMemAccntInfo.get("resort_no"));

                            resultMsg += ExcelValidator.validate(row, "sndg_kind_cd", ExcelValidator.FILTER_REQUIRE | ExcelValidator.FILTER_NUMBER);
                            resultMsg += ExcelValidator.validate(row, "mem_nm", ExcelValidator.FILTER_REQUIRE);
                            resultMsg += ExcelValidator.validate(row, "mem_no", ExcelValidator.FILTER_REQUIRE);
                            resultMsg += ExcelValidator.validate(row, "dlv_type_cd", ExcelValidator.FILTER_REQUIRE | ExcelValidator.FILTER_NUMBER);
                            resultMsg += ExcelValidator.validate(row, "dlv_stat_cd", ExcelValidator.FILTER_REQUIRE | ExcelValidator.FILTER_NUMBER);
                        } else {
                            resultMsg += "해당 회원번호는 엔컴에서 조회되지 않음";
                        }
                    }


                    if (!resultMsg.equals("")) {
                        // savefg = false;
                        resultMsg = (rowNum+1) + "번째 행 => " + resultMsg;

                        try {
                            System.out.println("resultMsg >>" + resultMsg);
                            System.out.println("========= Row Num : " + (rowNum+1) + "===========");
                            CommonUtils.printLog(row);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    ParamUtils.addSaveParam(row);
                    row.put("resultMsg", resultMsg);

                    return savefg;
                }
            });

            importer.process(is);

            if (excelList.size() > 0) {
                for (Map<String, Object> excelData : excelList) {
                    if ("".equals(excelData.get("resultMsg"))) {
                        dlvService.saveDlv(excelData);
                    } else {
                        szErrorCode = "-1";
                        szErrorMsg = szErrorMsg.equals("OK") ? excelData.get("resultMsg").toString() : szErrorMsg + "\r\n" + excelData.get("resultMsg").toString();
                    }

                }

            } else {
                oResult.setErrMsg("엑셀 업로드 할 내역이 없습니다.");
                oResult.setResult(ActionResultType.ERROR);
            }
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;

    }
	*/
    @RequestMapping(value = "/excel-upload")
    public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadToNas 컨트롤러 - 1");
            dlvService.uploadToNas(request, response, mResult);
            log.debug("uploadToNas 컨트롤러 - 2");

            CommonUtils.printLog("씨팔");

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }
}
