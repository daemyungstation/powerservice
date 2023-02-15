/*
 * (@)# DlwCnctRtamtController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/05
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DlwCnctRtamtService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.excel.ExcelImportRowHandler;
import powerservice.core.util.excel.ExcelImporter;
import powerservice.core.util.excel.ExcelValidator;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명라이프웨이 해약환급금 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/05
 * @프로그램ID DlwCnctRtamt
 */
@Controller
@RequestMapping(value = "/dlw-syst/cnct-rtamt")
public class DlwCnctRtamtController {

    @Resource
    private DlwCnctRtamtService DlwCnctRtamtService;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 대명라이프웨이 해약환급금(Master) 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst/list")
    public ModelAndView getResnAmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = DlwCnctRtamtService.getResnAmtCount(hmParam);
                mapOutVar.put("tc_cnctRtamt", nTotal);

                List<Map<String, Object>> mList = DlwCnctRtamtService.getResnAmtList(hmParam);
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
     * 대명라이프웨이 해약환급금 (Detail) 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtl/list")
    public ModelAndView getResnAmtDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = DlwCnctRtamtService.getResnAmtDetailCount(hmParam);
                mapOutVar.put("tc_cnctRtamt", nTotal);

                List<Map<String, Object>> mList = DlwCnctRtamtService.getResnAmtDetailList(hmParam);
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
     * 대명라이프웨이 해약환급금 목록을 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtl/insert")
    public ModelAndView insertResnAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");   // 리스트
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // 정보..
            if (listInDs2.size() > 0) {
                hmParam = listInDs2.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String ckResult = DlwCnctRtamtService.checkResnFg(hmParam);
                if("T".equals(ckResult)) {
                    DlwCnctRtamtService.insertResnAmtHd(hmParam);
                    for (int i = 0; i < listInDs.size(); i++) {
                        Map<String, Object> listProp = listInDs.get(i);
                        hmParam.put("resn_seq", listProp.get("resn_seq"));
                        String resn_amt = (String)listProp.get("resn_amt");
                        hmParam.put("resn_amt", resn_amt.replaceAll(",", ""));
                        DlwCnctRtamtService.insertResnAmtDt(hmParam);
                    }
                    mapOutVar.put("result_msg", "success");
                } else {
                    mapOutVar.put("result_msg", "fail");
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
     * 대명라이프웨이 해약환급금 목록을 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtl/update")
    public ModelAndView updateResnAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");   // 리스트
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // 정보..
            if (listInDs2.size() > 0) {
                hmParam = listInDs2.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String ckResult = "";
                if(((String)hmParam.get("model_cl_cd")).equals((String)hmParam.get("org_model_cl_cd"))
                        && ((String)hmParam.get("app_dt")).equals((String)hmParam.get("org_app_dt"))) {
                    ckResult = "T";
                } else {
                    ckResult = DlwCnctRtamtService.checkResnFg(hmParam);
                }
                if("T".equals(ckResult)) {
                    DlwCnctRtamtService.updateResnAmtHd(hmParam);
                    for (int i = 0; i < listInDs.size(); i++) {
                         Map<String, Object> listProp = listInDs.get(i);
                         int rowType = ((Integer) listProp.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                         if (rowType == DataSet.ROW_TYPE_UPDATED) {
                             hmParam.put("resn_seq", listProp.get("resn_seq"));
                             String resn_amt = (String)listProp.get("resn_amt");
                             hmParam.put("resn_amt", resn_amt.replaceAll(",", ""));
                             DlwCnctRtamtService.updateResnAmtDt(hmParam);
                         }
                    }
                    mapOutVar.put("result_msg", "success");
                } else {
                    mapOutVar.put("result_msg", "fail");
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
     * 엑셀파일로 부터 해약환급금 정보를 추출한다.
     *
     * @param pmParam Map<String, Object>
     * @param pFileList List<MultipartFile>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView uploadExcelFile(@RequestParam Map<String, Object> pmParam,final HttpServletRequest request) throws Exception{
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
        colList.add(colIndex++, "prod_cd");
        colList.add(colIndex++, "seq");
        colList.add(colIndex++, "resn_amt");
        colList.add(colIndex++, "app_dt");
        colList.add(colIndex++, "model_cl_cd");
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

                    resultMsg += ExcelValidator.validate(row, "seq", ExcelValidator.FILTER_NUMBER);
                    resultMsg += ExcelValidator.validate(row, "resn_amt", ExcelValidator.FILTER_NUMBER);

                    //row.put("custclscd", custclscd);
                    ParamUtils.addSaveParam(row);

                    return savefg;
                }
            });

            importer.process(is);

            if (excelList.size() > 0) {
                String prod_cd = CommonUtils.checkNull((String)excelList.get(0).get("prod_cd"));
                String app_dt = CommonUtils.checkNull((String)excelList.get(0).get("app_dt"));
                String model_cl_cd = CommonUtils.checkNull((String)excelList.get(0).get("model_cl_cd"));

                int succCnt = 0;
                int errCnt = 0;
                int totCnt = 0;

                //if("".equals(prod_cd) || "".equals(app_dt) || "".equals(model_cl_cd)) {
                // 모델분류코드 없이 엑셀 업로드 가능하도록 수정 - 2019.01.11 정승철
                if("".equals(prod_cd) || "".equals(app_dt)) {
                    oResult.setErrMsg("엑셀 업로드를 실패했습니다. (필수사항 누락)");
                    oResult.setResult(ActionResultType.ERROR);
                } else {
                    Map<String, Object> hmParam = new HashMap<String, Object>();
                    hmParam.put("prod_cd", prod_cd);
                    hmParam.put("app_dt", app_dt.replaceAll("-", ""));
                    hmParam.put("model_cl_cd", model_cl_cd);
                    hmParam.put("reg_man", excelList.get(0).get("rgsr_id"));
                    hmParam.put("upd_man", excelList.get(0).get("rgsr_id"));
                    hmParam.put("use_yn", "Y");

                    String ckResult = DlwCnctRtamtService.checkResnFg(hmParam);

                    if (!"T".equals(ckResult)) {
                        //중복일 경우 기존내역 삭제
                        DlwCnctRtamtService.deleteResnAmtD(hmParam);
                        DlwCnctRtamtService.deleteResnAmtM(hmParam);
                    }

                    //마스터등록
                    DlwCnctRtamtService.insertResnAmtHd(hmParam);

                    for (int i = 0; i < excelList.size(); i++) {
                        Map<String, Object> excelMap = new HashMap();
                        excelMap = (HashMap)excelList.get(i);
                        String seq = CommonUtils.checkNull((String)excelMap.get("seq"));
                        String resn_amt = CommonUtils.checkNull((String)excelMap.get("resn_amt"));
                        if ("".equals(seq) || "".equals(resn_amt)) {
                            errCnt++;
                        } else {
                            hmParam.put("resn_seq", seq);
                            hmParam.put("resn_amt", resn_amt);
                            //상세등록
                            DlwCnctRtamtService.insertResnAmtDt(hmParam);
                            succCnt++;
                        }
                    }
                }
            } else {
                oResult.setErrMsg("엑셀 업로드 할 내역이 없습니다.");
                oResult.setResult(ActionResultType.ERROR);
            }
        }


        modelAndView.addObject(oResult);
        return modelAndView;
    }


}