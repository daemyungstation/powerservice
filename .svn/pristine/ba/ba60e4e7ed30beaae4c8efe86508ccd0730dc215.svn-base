/*
 * (@)# XlsController.java
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

package powerservice.business.sys.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.sys.service.XlsService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.CompressUtil;
import powerservice.common.util.DateUtil;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 엑셀 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Xls
 */
@Controller
@RequestMapping(value = "/syst/xls")
public class XlsController {

    private final Logger LOGGER = LoggerFactory.getLogger(XlsController.class);

    @Autowired
    private ServletContext ctx;

    @Resource
    private XlsService xlsService;

    /**
     * 엑셀 정보를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getXlsList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int total = xlsService.getXlsCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = xlsService.getXlsList(pmParam);

        oData.setTotal(total);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 엑셀 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String sXlsId = StringUtils.defaultString((String) pmParam.get("xls_id"));

        ParamUtils.addSaveParam(pmParam);

        if (oPageIndx == null) {
            // 중복 확인
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("xls_id", sXlsId);
            int nTotal = xlsService.getXlsCount(mSearchParam);
            if (nTotal > 0 ) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 엑셀ID가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
            mSearchParam = null;

            xlsService.insertXls(pmParam);
        } else {
            xlsService.updateXls(pmParam);
        }

        oResult.setData(xlsService.getXls(sXlsId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 엑셀 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        xlsService.deleteXls(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 엑셀을 요청한다
     *
     * @param xpDto XPlatformMapDTO
     * @param poSession HttpSession
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/request")
    public ModelAndView requestXls(XPlatformMapDTO xpDto, HttpSession poSession) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> mParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        Map<String, Object> mapInVar     = xpDto.getInVariableMap();
        Map<String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map<String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

        if (listInDs != null && listInDs.size() > 0) {
            mParam = listInDs.get(0);
        }

        long nSttTm = System.currentTimeMillis();
        LOGGER.info("###### EXCEL - " + nSttTm + " ###### START");

        // 엑셀다운로드 중복 방지용 세션
        synchronized (poSession) {
            if (poSession.getAttribute("EXCEL_SESSION") != null) {
                LOGGER.info("###### EXCEL - " + nSttTm + " ###### ERROR [진행중인 엑셀다운로드가 있습니다.]");

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "진행중인 엑셀다운로드가 있습니다.");
                return modelAndView;
            }
            poSession.setAttribute("EXCEL_SESSION", "Y");
        }

        // 사용자 세션 확인
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        if (oLoginUser == null) {
            poSession.removeAttribute("EXCEL_SESSION");

            LOGGER.info("###### EXCEL - " + nSttTm + " ###### ERROR [로그인 사용자 정보를 찾을 수 없습니다.]");

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "로그인 사용자 정보를 찾을 수 없습니다.");
            return modelAndView;
        }

        // 엑셀 파라미터 처리
        String sUserId  = oLoginUser.getUserid();
        String sLgnId  = oLoginUser.getLoginid();
        String sUserNm  = oLoginUser.getUsernm();

        String sBigGridYn = !StringUtils.defaultString((String) mapInVar.get("bigGrid_yn")).equals("")? StringUtils.defaultString((String) mapInVar.get("bigGrid_yn")) :  "N";
        String sMsSqlYn = !StringUtils.defaultString((String) mapInVar.get("msSql_yn")).equals("")? StringUtils.defaultString((String) mapInVar.get("msSql_yn")) :  "N";
        String sXlsId = StringUtils.defaultString((String) mapInVar.get("xls_id"));
        String sListQuery = StringUtils.defaultString((String) mapInVar.get("list_query"));
        String sCntQuery = StringUtils.defaultString((String) mapInVar.get("cnt_query"));

        LOGGER.info("###### sListQuery - " + sListQuery + "sCntQuery - " + sCntQuery);

        mParam.put("ms_sql_yn", sMsSqlYn);

        // 엑셀 쿼리 확인
        if ("".equals(sListQuery) || "".equals(sCntQuery)) {
            poSession.removeAttribute("EXCEL_SESSION");

            LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### ERROR [조회 쿼리 정보를 찾을 수 없습니다.]");

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "조회 쿼리 정보를 찾을 수 없습니다.");
            return modelAndView;
        }

        LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### user_nm [" + sUserNm + "]");
        LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### xls_id [" + sXlsId + "]");
        LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### list_qury_id [" + sListQuery + "]");
        LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### cscnt_qury_id [" + sCntQuery + "]");
        LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### param " + mParam);

        if (sXlsId.equals("SI010100")){ // 공제 선수금 엑셀반출시 시트명 고정
            mParam.put("xls_nm", "21_00");
        }else{
            mParam.put("xls_nm", "download" + sXlsId);
        }

        mParam.put("list_qury_id", sListQuery);
        mParam.put("cscnt_qury_id", sCntQuery);

        String sRealPath = ctx.getRealPath("");
        String sTempFileName = sRealPath + "/WEB-INF/xls/" + sXlsId + ".xlsx";
        CommonUtils.printLog("sTempFileName:" + sTempFileName);
        CommonUtils.printLog("ms_sql_yn:" + sMsSqlYn);
        mParam.put("realPath",     sRealPath);
        mParam.put("tempFileName", sTempFileName);

        String sTargetPath     = sRealPath + "/WEB-INF/temp/" + sUserId;
        String sTargetFileName = sXlsId;

        mParam.put("targetPath", sTargetPath);
        mParam.put("targetFileName", sTargetPath + "/" + sTargetFileName);

        // 템플릿 파일 확인
        if (!(new File(sTempFileName)).exists()) {
            poSession.removeAttribute("EXCEL_SESSION");

            LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### ERROR [요청한 템플릿 파일을 찾을 수 없습니다.]");

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "요청한 템플릿 파일을 찾을 수 없습니다.");
            return modelAndView;
        }

        File oTransFile = null; // 전송 파일
        File[] oFileList = null; // 생성 파일 리스트
        File oTargetPathFile = null; // 작업 폴더

        try {
            // 작업 폴더 생성
            oTargetPathFile = new File(sTargetPath);
            if (!oTargetPathFile.getParentFile().exists()) {
                oTargetPathFile.getParentFile().mkdir();
            }
            if (!oTargetPathFile.exists()) {
                oTargetPathFile.mkdir();
            }

            // 이전 작업 파일 삭제
            oFileList = oTargetPathFile.listFiles();
            if (oFileList != null) {
                for (int i = 0; i < oFileList.length; i++) {
                    oFileList[i].delete();
                }
            }

            // 엑셀 파일 출력 건수 확인
            int nTotalCnt = xlsService.getXlsDataCount(mParam);

            LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### total_cscnt [" + nTotalCnt + "] big_grid_yn [" + sBigGridYn + "]");
            if (nTotalCnt > 0) {
                mParam.put("totalCnt", nTotalCnt);
            } else {
                poSession.removeAttribute("EXCEL_SESSION");

                LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### ERROR [출력할 데이터가 없습니다.]");

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "출력할 데이터가 없습니다.");
                return modelAndView;
            }


            // xlsService.downloadXls(mParam);


            // 엑셀 파일 생성
            if ("Y".equals(sBigGridYn)) { // 대용량 엑셀
                List<Map<String, Object>> lmColumn = new ArrayList<Map<String, Object>>();
                DataSetMap dsColumn = (DataSetMap)mapInDs.get("ds_column");

                for (int i=0; i<dsColumn.size(); i++) {
                    lmColumn.add(dsColumn.get(i));
                }

                // CommonUtils.printLog(lmColumn);
                xlsService.downloadXlsBigGrid(mParam, lmColumn);

            } else if (nTotalCnt > 0) { // 양식 엑셀
                xlsService.downloadXls(mParam);
            }

            // 생성 파일 확인
            String sFilePath = "";
            oFileList = oTargetPathFile.listFiles();
            if (oFileList.length == 1) { // 대상 파일이 1개인 경우
                // 엑셀 파일 선택
                oTransFile = oFileList[0];
                if (oTransFile != null && oTransFile.exists()) {
                    sFilePath = sUserId + "/" + oTransFile.getName();
                }
            } else if (oFileList.length > 1) { // 대상 파일이 여러개인 경우
                // 파일 압축
                (new CompressUtil()).zip(oTargetPathFile, "UTF-8", false);
                // 압축 파일 선택
                oTransFile = new File(oTargetPathFile.getParent() + "/" + oTargetPathFile.getName() + ".zip");
                if (oTransFile != null && oTransFile.exists()) {
                    sFilePath = oTransFile.getName();
                }
            }
            long nJobTm = System.currentTimeMillis() - nSttTm;
            LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### END total_cscnt [" + nTotalCnt + "] file_path [" + sFilePath + "] (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ")");

            mapOutVar.put("_gsServerExcelFileNm", sFilePath);
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        } catch (Exception exception) {
            poSession.removeAttribute("EXCEL_SESSION");

            LOGGER.info("###### EXCEL - " + nSttTm + " - " + sLgnId + " ###### ERROR [처리중 오류가 발생했습니다.]");
            exception.printStackTrace();

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "처리중 오류가 발생했습니다.");
            return modelAndView;
        }

        poSession.removeAttribute("EXCEL_SESSION");

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        return modelAndView;
    }

    /**
     * 엑셀을 다운로드한다
     *
     * @param pmParam Map<String, Object>
     * @param poSession HttpSession
     * @param poResponse HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public ModelAndView downloadXls(@RequestParam Map<String, Object> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        // 파일명 확인
        String sFilePath = StringUtils.defaultString((String) pmParam.get("file_path"));
        if ("".equals(sFilePath)) {
            poResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        File oTransFile = null; // 전송 파일
        FileInputStream oInputStream = null;
        ServletOutputStream oOutputStream = null;

        String sRealPath = ctx.getRealPath("");
        String sTargetFileName = sRealPath + "/WEB-INF/temp/" + sFilePath;

        try {
            // 파일 확인
            oTransFile = new File(sTargetFileName);
            if (oTransFile == null || !oTransFile.exists()) {
                poResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            // 파일 전송
            StringBuffer sContentDisposition = new StringBuffer();
            if (sFilePath.endsWith(".xlsx")) { // 대상 파일이 1개인 경우
                sContentDisposition.append("attachment;fileName=\"");
                sContentDisposition.append("XLSX_");
                sContentDisposition.append(DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM));
                sContentDisposition.append(".xlsx");
                sContentDisposition.append("\";");

                poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                poResponse.setContentType("application/x-msexcel");
                poResponse.setContentLength(((Long) oTransFile.length()).intValue());
            } else if (sFilePath.endsWith(".zip")) { // 대상 파일이 여러개인 경우
                sContentDisposition.append("attachment;fileName=\"");
                sContentDisposition.append("XLSX_");
                sContentDisposition.append("_");
                sContentDisposition.append(DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM));
                sContentDisposition.append(".zip");
                sContentDisposition.append("\";");

                poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                poResponse.setContentType("application/octet-stream");
                poResponse.setContentLength(((Long) oTransFile.length()).intValue());
            }

            oInputStream = new FileInputStream(oTransFile);
            oOutputStream = poResponse.getOutputStream();

            int nReadByte = 0;
            byte[] oByteArray = new byte[2048];
            while ((nReadByte = oInputStream.read(oByteArray)) != -1) {
                oOutputStream.write(oByteArray, 0, nReadByte);
            }
            oOutputStream.flush();
            oOutputStream.close();
            oOutputStream = null;
            oInputStream.close();
            oInputStream = null;
        } catch (Exception exception) {
            poResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (oOutputStream != null) {
                oOutputStream.close();
                oOutputStream = null;
            }
            if (oInputStream != null) {
                oInputStream.close();
                oInputStream = null;
            }

            // 1. 작업 파일 삭제
            File oTargetPathFile = null; // 사용자 작업 폴더
            if (oTransFile != null && oTransFile.exists()) {
                // 1.1. 사용자 작업 폴더 저장
                oTargetPathFile = oTransFile.getParentFile();
                // 1.2. 작업 파일 삭제
                oTransFile.delete();
                oTransFile = null;
            }
            // 2. 압축 파일인 경우 사용자 작업 폴더 재설정 (temp => 사용자 작업 폴더)
            if (oTargetPathFile != null && oTargetPathFile.exists() && "temp".equals(oTargetPathFile.getName())) {
                sTargetFileName = sRealPath + "/WEB-INF/temp/" + sFilePath.substring(0, sFilePath.indexOf('.'));
                oTargetPathFile = new File(sTargetFileName);
            }
            // 3. 사용자 작업 폴더 삭제
            if (oTargetPathFile != null && oTargetPathFile.exists()) {
                // 3.1. 사용자 작업 폴더 내부 파일 리스트 삭제
                File[] oFileList = oTargetPathFile.listFiles();
                if (oFileList != null) {
                    for (int i = 0; i < oFileList.length; i++) {
                        oFileList[i].delete();
                    }
                    oFileList = null;
                }
                // 3.2. 사용자 작업 폴더 삭제
                oTargetPathFile.delete();
                oTargetPathFile = null;
            }
        }

        return null;
    }

    /*
    public ModelAndView downloadXls(@RequestParam Map<String, Object> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        ModelAndView modelAndView = null;

        File oTransFile = null; // 전송 파일
        File[] oFileList = null; // 생성 파일 리스트
        File oTargetPathFile = null; // 작업 폴더
        FileInputStream oInputStream = null;
        ServletOutputStream oOutputStream = null;

        // 엑셀다운로드 중복 방지용 세션
        synchronized (poSession) {
            if (poSession.getAttribute("EXCEL_SESSION") != null) {
                modelAndView = new ModelAndView("common/xls-down-result");
                modelAndView.addObject("result", "error_progress");
                return modelAndView;
            } else {
                poSession.setAttribute("EXCEL_SESSION", "Y");
            }
        }

        // 세션 확인
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        if (oLoginUser == null) {
            poSession.removeAttribute("EXCEL_SESSION");

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error_session");
            return modelAndView;
        }

        // 엑셀 파라미터 처리
        String sXlsParam = StringUtils.defaultString((String) pmParam.get("xls_param"));
        ObjectMapper oJsonMapper = ObjectMapperFactory.getInstance();
        Map<String, Object> mExcelParam = oJsonMapper.readValue(sXlsParam, new TypeReference<Map<String, Object>>() {});
        pmParam.putAll(mExcelParam);
        mExcelParam = null;

        String sUserId  = oLoginUser.getUserid();
        String sLgnId  = oLoginUser.getLoginid();
        String sUserNm  = oLoginUser.getUsernm();
        String sBigGridYn  = StringUtils.defaultString((String) pmParam.get("big_grid_yn"));
        String sXlsId  = StringUtils.defaultString((String) pmParam.get("xls_id"));
        String sXlsNm  = "";
        String sListQuryId = "";
        String sCscntQuryId = "";

        if ("".equals(sXlsId)) {
            poSession.removeAttribute("EXCEL_SESSION");

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error_xlsid");
            return modelAndView;
        }

        // 엑셀 정보 조회
        Map<String, Object> mExcel = xlsService.getXls(sXlsId);
        if (mExcel == null) {
            poSession.removeAttribute("EXCEL_SESSION");

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error_xlsid");
            return modelAndView;
        }

        sXlsNm  = StringUtils.defaultString((String) mExcel.get("xls_nm"));
        sListQuryId = StringUtils.defaultString((String) mExcel.get("list_qury_id"));
        sCscntQuryId = StringUtils.defaultString((String) mExcel.get("cscnt_qury_id"));

        mExcel = null;

        pmParam.put("xls_nm", sXlsNm);
        pmParam.put("list_qury_id", sListQuryId);
        pmParam.put("cscnt_qury_id", sCscntQuryId);

        // 엑셀 쿼리 확인
        if ("".equals(sListQuryId) || "".equals(sCscntQuryId)) {
            poSession.removeAttribute("EXCEL_SESSION");

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error_sqlmapid");
            return modelAndView;
        }

        String sRealPath = ctx.getRealPath("");
        String sTempFileName = sRealPath + "/WEB-INF/xls/" + sXlsId + ".xlsx";
        pmParam.put("realPath",     sRealPath);
        pmParam.put("tempFileName", sTempFileName);

        String sTargetPath     = sRealPath + "/WEB-INF/temp/" + sUserId;
        String sTargetFileName = sXlsNm;

        LOGGER.info("###### EXCEL - " + sLgnId + " ###### START");
        LOGGER.info("###### EXCEL - " + sLgnId + " ###### user_nm [" + sUserNm + "]");
        LOGGER.info("###### EXCEL - " + sLgnId + " ###### xls_id [" + sXlsId + "]");
        LOGGER.info("###### EXCEL - " + sLgnId + " ###### xls_nm [" + sXlsNm + "]");
        LOGGER.info("###### EXCEL - " + sLgnId + " ###### list_qury_id [" + sListQuryId + "]");
        LOGGER.info("###### EXCEL - " + sLgnId + " ###### cscnt_qury_id [" + sCscntQuryId + "]");

        pmParam.put("targetPath", sTargetPath);
        pmParam.put("targetFileName", sTargetPath + "/" + sTargetFileName);

        // 템플릿 파일 확인
        if (!(new File(sTempFileName)).exists()) {
            poSession.removeAttribute("EXCEL_SESSION");

            LOGGER.error("###### EXCEL - " + sLgnId + " ###### ERROR [tempfile]");

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error_tempfile");
            return modelAndView;
        }

        // 작업 폴더 생성
        oTargetPathFile = new File(sTargetPath);
        if (!oTargetPathFile.getParentFile().exists()) {
            oTargetPathFile.getParentFile().mkdir();
        }
        if (!oTargetPathFile.exists()) {
            oTargetPathFile.mkdir();
        }

        long nStartTime = System.currentTimeMillis();

        try {
            // 이전 작업 파일 삭제
            oFileList = oTargetPathFile.listFiles();
            if (oFileList != null) {
                for (int i = 0; i < oFileList.length; i++) {
                    oFileList[i].delete();
                }
            }

            // 엑셀 파일 출력 건수 확인
            int nTotalCnt = xlsService.getXlsDataCount(pmParam);
            LOGGER.info("###### EXCEL - " + sLgnId + " ###### total_cscnt [" + nTotalCnt + "] big_grid_yn [" + sBigGridYn + "]");
            if (nTotalCnt > 0) {
                pmParam.put("totalCnt", nTotalCnt);
            } else {
                poSession.removeAttribute("EXCEL_SESSION");

                LOGGER.error("###### EXCEL - " + sLgnId + " ###### ERROR [empty]");

                modelAndView = new ModelAndView("common/xls-down-result");
                modelAndView.addObject("result", "error_empty");
                return modelAndView;
            }

            // 엑셀 파일 생성
            if (nTotalCnt > 1000 || "Y".equals(sBigGridYn)) { // 대용량 엑셀
                xlsService.downloadXlsBigGrid(pmParam);

                sBigGridYn = "Y";
            } else if (nTotalCnt > 0) { // 양식 엑셀
                xlsService.downloadXls(pmParam);

                sBigGridYn = "N";
            }

            LOGGER.info("###### EXCEL - " + sLgnId + " ###### END big_grid_yn [" + sBigGridYn + "] (" + (System.currentTimeMillis() - nStartTime) + "ms)");

            // 생성 엑셀 파일 확인
            StringBuffer sContentDisposition = new StringBuffer();
            oFileList = oTargetPathFile.listFiles();
            if (oFileList.length == 1) { // 대상 파일이 1개인 경우
                oTransFile = oFileList[0];

                sContentDisposition.append("attachment;fileName=\"");
                sContentDisposition.append(URLEncoder.encode(sXlsNm, "UTF-8").replaceAll("\\+", "%20"));
                sContentDisposition.append("_");
                sContentDisposition.append(DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM));
                sContentDisposition.append(".xlsx");
                sContentDisposition.append("\";");

                poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                poResponse.setContentType("application/x-msexcel");
                poResponse.setContentLength(((Long) oTransFile.length()).intValue());
            } else if (oFileList.length > 1) { // 대상 파일이 여러개인 경우
                // 파일 압축
                (new CompressUtil()).zip(oTargetPathFile, "UTF-8", false);

                oTransFile = new File(oTargetPathFile.getParent() + "/" + oTargetPathFile.getName() + ".zip");

                sContentDisposition.append("attachment;fileName=\"");
                sContentDisposition.append(URLEncoder.encode(sXlsNm, "UTF-8").replaceAll("\\+", "%20"));
                sContentDisposition.append("_");
                sContentDisposition.append(DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM));
                sContentDisposition.append(".zip");
                sContentDisposition.append("\";");

                poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                poResponse.setContentType("application/octet-stream");
                poResponse.setContentLength(((Long) oTransFile.length()).intValue());
            }

            // 파일 전송
            if (oTransFile != null) {
                oInputStream = new FileInputStream(oTransFile);
                oOutputStream = poResponse.getOutputStream();

                int nReadByte = 0;
                byte[] oByteArray = new byte[2048];
                while ((nReadByte = oInputStream.read(oByteArray)) != -1) {
                    oOutputStream.write(oByteArray, 0, nReadByte);
                }
                oOutputStream.flush();
                oOutputStream.close();
                oOutputStream = null;
                oInputStream.close();
                oInputStream = null;
            }
        } catch (Exception exception) {
            LOGGER.error("###### EXCEL - " + sLgnId + " ###### ERROR [error]");

            exception.printStackTrace();

            modelAndView = new ModelAndView("common/xls-down-result");
            modelAndView.addObject("result", "error");
            modelAndView.addObject("resultMessage", exception.getMessage());
        } finally {
            if (oOutputStream != null) {
                oOutputStream.close();
            }
            if (oInputStream != null) {
                oInputStream.close();
            }

            // 작업 파일 삭제
            if (oTransFile != null && oTransFile.exists()) {
                oTransFile.delete();
            }
            // 작업 폴더 삭제
            if (oTargetPathFile != null && oTargetPathFile.exists()) {
                oFileList = oTargetPathFile.listFiles();
                if (oFileList != null) {
                    for (int i = 0; i < oFileList.length; i++) {
                        oFileList[i].delete();
                    }
                }

                oTargetPathFile.delete();
            }
        }

        poSession.removeAttribute("EXCEL_SESSION");

        return modelAndView;
    }
    */

    /**
     * 엑셀 다운로드 진행을 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download-check")
    public ModelAndView checkXls(XPlatformMapDTO xpDto, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            mapOutVar.put("excel_down_fg", (poSession.getAttribute("EXCEL_SESSION") == null ? "N" : "Y"));

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