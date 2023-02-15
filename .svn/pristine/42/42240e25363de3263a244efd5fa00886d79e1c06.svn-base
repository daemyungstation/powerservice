/*
 * (@)# DlwPayController.java
 */

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DlwDeductionService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
@RequestMapping(value = "/dlw/deduc")
public class DlwDeductionController {

    @Resource
    private DlwDeductionService DlwDeductionService;

    /** ================================================================
     * 날짜 : 20190123
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/list/getGongjeDayExt")
    public ModelAndView getMemberMangiExtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
                String sSvcId = (String)hmParam.get("svc_id");

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                System.out.println("@@@@@@@@@@@@@ 공제 조회 파라미터 : " + hmParam);

                // 공제 전송여부 카운트조회
                int retVal = DlwDeductionService.getGongjeSendCnt(hmParam);
                System.out.println("@@@@@@ retVal : " + retVal);
                mapOutVar.put("retVal", retVal);


                int nTotal = DlwDeductionService.getGongjeDayExtCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwDeductionService.getGongjeDayExtList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    @RequestMapping(value = "/getGongjeExtDt")
    public ModelAndView getGongjeExtDt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

                List<Map<String, Object>> mList = DlwDeductionService.getGongjeExtDt(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 엑셀(일반 또는 선수금) 다운
     * ================================================================
     * */
    @RequestMapping(value = "/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yyyymmdd = CommonUtils.nvls(request.getParameter("yyyymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yyyymmdd", yyyymmdd);

            String gubun = CommonUtils.nvls(request.getParameter("gubun"));
            System.out.println("################### 구분 : " + gubun);

            String downPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                if(gubun.equals("basic")) downPath = EgovProperties.getProperty("gongje.basicData.windows.basePath"); // 일반
                else if(gubun.equals("pay")) downPath = EgovProperties.getProperty("gongje.payData.windows.basePath"); // 선수금
            } else {
                if(gubun.equals("basic")) downPath = EgovProperties.getProperty("gongje.basicData.unix.basePath"); // 일반
                else if(gubun.equals("pay")) downPath = EgovProperties.getProperty("gongje.payData.unix.basePath"); // 선수금
            }
            System.out.println("====downPath : " + downPath);

            String srcFilePath = "";
            if(gubun.equals("basic")) srcFilePath = downPath + "DMLIFE_" + yyyymmdd + ".xlsx"; // 일반
            else if(gubun.equals("pay")) srcFilePath = downPath + "DMLIFE_PAY_" + yyyymmdd + "_001.xlsx"; // 선수금

            System.out.println("====srcFilePath : " + srcFilePath);

            /*
            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }
            */

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");


            }

            String contentType = request.getContentType();
            System.out.println("contentType1 : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
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

            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 공제산출스케줄 등록
     * 정승철
     * 20190207
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/add-gongjeExtSched")
    public ModelAndView saveGongjeExtSched(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            // 공제산출스케줄 입력유효성체크
            int chkCnt_1 = DlwDeductionService.chkGongjeExtSched(hmParam);   // 공제 기산출일 등록여부 조회
            int chkCnt_2 = DlwDeductionService.chkGongjeExtSched_rsProcYn(); // 공제결과 미처리 조회

            if(chkCnt_1 == 0 && chkCnt_2 == 0)
                DlwDeductionService.saveGongjeExtSched(hmParam); // 공제산출스케줄 등록
            else if(chkCnt_1 > 0)
                mapOutVar.put("g_reason_msg", "해당 산출일은 이미 등록되어 있습니다.");  // 실패 메시지 return
            else if(chkCnt_2 > 0)
                mapOutVar.put("g_reason_msg", "결과처리 되지 않은 날이 존재합니다.");  // 실패 메시지 return

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 공제산출스케줄 삭제
     * 정승철
     * 20190207
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/del-gongjeExtSched")
    public ModelAndView delGongjeExtSched(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            // 공제산출스케줄 삭제
            DlwDeductionService.delGongjeExtSched(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 진행중인 공제데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/getGongjeSendingList")
    public ModelAndView getGongjeSendingList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

                List<Map<String, Object>> mList = DlwDeductionService.getGongjeSendingList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제구분별 최종결과반영 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/getGongjeTypeLastResultList")
    public ModelAndView getGongjeTypeLastResultList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
                hmParam.put("gdate", hmParam.get("srchDt"));
                List<Map<String, Object>> mList = DlwDeductionService.getGongjeTypeLastResultList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE_PAY 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    @RequestMapping(value = "/save/insertGongjeDmlifePayFileUpload")
    public void insertGongjeDmlifePayFileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        String totPramCnt = StringEscapeUtils.escapeHtml(request.getParameter("totPramCnt"));

        mResult.put("totPramCnt", totPramCnt);

        try
        {
            DlwDeductionService.insertGongjeDmlifePayFileUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        }
        catch (Exception e)
        {
            resVarList.add("ErrorCode", "-1");
            //resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    @RequestMapping(value = "/save/insertGongjeDmlifeFile")
    public void insertGongjeDmlifeFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        String totPramCnt = StringEscapeUtils.escapeHtml(request.getParameter("totPramCnt"));

        mResult.put("totPramCnt", totPramCnt);

        try
        {
            DlwDeductionService.insertGongjeDmlifeFileUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        }
        catch (Exception e)
        {
            resVarList.add("ErrorCode", "-1");
            //resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /** ================================================================
        나중에 지울거임 테스트
     * ================================================================
     * */
    @RequestMapping(value = "/testExt")
    public ModelAndView getGongjeExtTest(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

            int varAAA = DlwDeductionService.insertGongjeDayExt();
            DlwDeductionService.createExcel();
            //DlwDeductionService.createPayExcel();
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
     * 날짜 : 20190502
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 파일 업로드
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    @RequestMapping(value = "/resultFileUpload")
    public ModelAndView resultFileUpload(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sGDate = (String)mapInVar.get("g_date");
            String sGType = (String)mapInVar.get("g_type");
            String sGTotExtCnt = (String)mapInVar.get("g_tot_ext_cnt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("g_date", sGDate);             // 공제 산출일
            hmParam.put("g_type", sGType);             // 공제 타입 (M: 회원정보, P: 입금정보)
            hmParam.put("g_tot_ext_cnt", sGTotExtCnt); // 산출건수 총합
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            System.out.println("산출일 : " + sGDate + " 공제타입 : " + sGType + " 산출건수 총합 : " + sGTotExtCnt);


            String sResultFileUploadBasePath = "";
            String sFileYYYY = sGDate.substring(0, 4);
            String sFileMM = sGDate.substring(4, 6);
            String sMatchingFlag = "N";
            int sResultDataCnt = 0;

            String osName = System.getProperty("os.name").toLowerCase();

            if (osName.indexOf("windows") >= 0)
            {
                sResultFileUploadBasePath = EgovProperties.getProperty("gongje.resultupload.windows.basePath") + sFileYYYY + "/" + sFileMM + "/";
                sResultFileUploadBasePath = sResultFileUploadBasePath.replaceAll("/", "\\\\");
            }
            else
            {
                sResultFileUploadBasePath = EgovProperties.getProperty("gongje.resultupload.unix.basePath") + sFileYYYY + "/" + sFileMM + "/";
            }

            System.out.println("파일 탐색 폴더명 : " + sResultFileUploadBasePath);

            BufferedReader br = null;
            StringBuffer resultBuffer = new StringBuffer();
            File fBasicFile = new File(sResultFileUploadBasePath);
            String[] arrBaseFiles = fBasicFile.list();

            for(int idx = 0; idx < arrBaseFiles.length; idx++)
            {
                String sMatchFileName = arrBaseFiles[idx].substring(0, 8);

                if(sMatchFileName.equals(sGDate))
                {
                    sMatchingFlag = "Y"; // 해당 일자의 결과 데이터가 들어왔음.

                    br = new BufferedReader(new InputStreamReader(new FileInputStream(sResultFileUploadBasePath + arrBaseFiles[idx]), "UTF-8"));
                    String bfLine = "";

                    while((bfLine = br.readLine()) != null)
                    {
                        if (bfLine.length() != 0)
                        {
                            sResultDataCnt++;
                        }
                    }

                    br.close();

                    if (Integer.parseInt(sGTotExtCnt) !=  sResultDataCnt)
                    {
                        throw new EgovBizException("공제산출과 업로드 건수가 맞지 않습니다. " + sGTotExtCnt + "<>" + sResultDataCnt);
                    }

                    sResultDataCnt = 0;

                    String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
                    String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
                    String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
                    String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

                    System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                                       "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);

                    Connection connection = null;
                    PreparedStatement preparedStatement = null ;


                    String sqlStatement = "INSERT INTO LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT(GDATE, RESULT_KEY, ACCNT_NO, GUAR_NO, WORK_RESULT, ERR_MSG, GUBUN, CODE,";
                    sqlStatement += "MEMB_NO, LIMIT_VALUE, SEQ_NO, PAY_NO, PAY_TYPE, REG_MAN, REG_DM, UPD_MAN, UPD_DM, DEL_FG, FILE_TYPE)";
                    sqlStatement += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, '', '', 'N', ?)";

                    Class.forName(sAccessClassName);
                    connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                    connection.setAutoCommit(false);

                    preparedStatement = connection.prepareStatement(sqlStatement);
                    preparedStatement.setQueryTimeout(1000);

                    br = new BufferedReader(new InputStreamReader(new FileInputStream(sResultFileUploadBasePath + arrBaseFiles[idx]), "UTF-8"));
                    //결과 레코드 다음행 처리
                    while((bfLine = br.readLine()) != null)
                    {
                        if (bfLine.length() != 0)
                        {
                            String oddLine = bfLine.replaceAll("&RET&", "");
                            oddLine = oddLine.replaceAll("RET&", "");

                            String[] arrLineData = oddLine.split("&");

                            Map<String, Object> mRow = new HashMap<String, Object>();

                            for(int idxLine = 0; idxLine < arrLineData.length; idxLine++)
                            {
                                String[] sKeyValues = arrLineData[idxLine].split("=");
                                String sKeyString = sKeyValues[0];
                                String sValue = sKeyValues[1];

                                mRow.put(sKeyString, sValue);
                                ParamUtils.addSaveParam(mRow);
                            }

                            /*
                             DlwDeductionDAO.insertGongjeFileUpload(mRow);
                             totExtResultCount++;
                             */

                            String sGdate      = CommonUtils.nvls((String)mRow.get("RC_DT"));
                            String sResultKey  = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                            String sAccntNo    = CommonUtils.nvls((String)mRow.get("CONS_NO"));
                            String sGuarNo     = CommonUtils.nvls((String)mRow.get("GUAR_NO"));
                            String sWorkResult = CommonUtils.nvls((String)mRow.get("WORK_RESULT"));
                            String sErrMsg     = CommonUtils.nvls((String)mRow.get("ERR_MSG"));
                            String sGubun      = CommonUtils.nvls((String)mRow.get("GUBUN"));
                            String sCode       = CommonUtils.nvls((String)mRow.get("CODE"));
                            String sMembNo     = CommonUtils.nvls((String)mRow.get("MEMB_NO"));
                            String sLimitValue = CommonUtils.nvls((String)mRow.get("LIMIT_VALUE"));
                            String sSeqNo      = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                            String sPayNo      = CommonUtils.nvls((String)mRow.get("ADV_PAY_NUM"));
                            String sPayType    = CommonUtils.nvls((String)mRow.get("PAY_TYPE"));
                            String sRegMan     = CommonUtils.nvls((String)mRow.get("rgsr_id"));
                            String sFileType   = "M";

                            //납입일 경우 파일 형식을 P로 설정
                            if (sGubun.equals("21"))
                            {
                                sFileType = "P";
                            }

                            preparedStatement.setString(1, sGdate.trim());
                            preparedStatement.setString(2, sResultKey.trim());
                            preparedStatement.setString(3, sAccntNo.trim());
                            preparedStatement.setString(4, sGuarNo.trim());
                            preparedStatement.setString(5, sWorkResult.trim());
                            preparedStatement.setString(6, sErrMsg.trim());
                            preparedStatement.setString(7, sGubun.trim());
                            preparedStatement.setString(8, sCode.trim());
                            preparedStatement.setString(9, sMembNo.trim());
                            preparedStatement.setString(10, sLimitValue.trim());
                            preparedStatement.setString(11, sSeqNo.trim());
                            preparedStatement.setString(12, sPayNo.trim());
                            preparedStatement.setString(13, sPayType.trim());
                            preparedStatement.setString(14, sRegMan.trim());
                            preparedStatement.setString(15, sFileType);

                            // addBatch에 담기
                            preparedStatement.addBatch();

                            // 파라미터 Clear
                            preparedStatement.clearParameters() ;


                            // OutOfMemory를 고려하여 만건 단위로 커밋
                            if( (sResultDataCnt % 500) == 0)
                            {
                                // Batch 실행
                                preparedStatement.executeBatch();

                                // Batch 초기화
                                preparedStatement.clearBatch();

                                // 커밋
                                connection.commit();
                            }
                            sResultDataCnt++;
                        }
                    }
                    preparedStatement.executeBatch() ;
                    connection.commit() ;

                    br.close();
                    preparedStatement.close();
                    connection.close();

                }
                else
                {
                    System.out.println("해당일자의 산출 결과 데이터가 아닙니다. 다음 파일을 탐색합니다.");
                }
            }

            if (sMatchingFlag.equals("N"))
            {
                throw new EgovBizException("해당일자의 공제산출 결과 데이터가 도착하지 않았습니다.");
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

    /** ================================================================
     * 날짜 : 20190213
     * 이름 : 임동진
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    @RequestMapping(value = "/GconvertFile")
    public ModelAndView updateGongjeConvertFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();;

            //세션
            hmParam.put("g_date", mapInVar.get("g_date")); // 공제 산출일
            hmParam.put("g_type", mapInVar.get("g_type")); // 공제 타입 (M: 회원정보, P: 입금정보)

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("emple_no", hmParam.get("rgsr_id"));

            System.out.println(hmParam);

        try{
                //공제 산출 데이터 결과 값 업데이트
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> g_date : " +  mapInVar.get("g_date"));
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> g_type : " +  mapInVar.get("g_type"));
                DlwDeductionService.uptGongResultStat(hmParam);

           }  catch(Exception e) {
               e.printStackTrace();
           }

            //DlwPayService.convertFile(hmParam);

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
     * 날짜 : 20190123
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/list/getGongjeDayExtSendHist")
    public ModelAndView getGongjeDayExtSendHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwDeductionService.getGongjeDayExtSendHistCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwDeductionService.getGongjeDayExtSendHist(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 신고 현황 데이터 (ASIS) 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/asisData/getGongjeAsisDataList")
    public ModelAndView getGongjeAsisDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwDeductionService.getGongjeAsisDataCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList1 = DlwDeductionService.getGongjeAsisDataList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output1", listMap1);

                List<Map<String, Object>> mList2 = DlwDeductionService.getGongjeAsisDataListSummary(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
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
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 신고 현황 데이터 입력,수정,삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/asisData/{crudType}")
    public ModelAndView saveGongjeAsisDataList(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                hmParam.put("orderBy", "ACCNT_NO");
                hmParam.put("orderDirection", "ASC");
                ParamUtils.addSaveParam(hmParam);

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insertGongjeAsisDataList".equals(crudTyp))
                {
                    // 기존에 등록되어 있던 회원인지 체크
                    int chkCnt = DlwDeductionService.getGongjeAsisDataCount(hmParam);

                    if(chkCnt <= 0)
                    {
                        DlwDeductionService.insertGongjeAsisDataList(hmParam);
                    }
                    else
                    {
                        mapOutVar.put("xSaveReturnMsg", "해당 회원은 이미 공제신고 현황 데이터로 등록되어있는 회원입니다.");  // 실패 메시지 return
                    }
                }
                else if("updateGongjeAsisDataList".equals(crudTyp))
                {
                    DlwDeductionService.updateGongjeAsisDataList(hmParam);
                }
                else if("deleteGongjeAsisDataList".equals(crudTyp))
                {
                    DlwDeductionService.deleteGongjeAsisDataList(hmParam);
                }
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

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/getGongjeMonthReport")
    public ModelAndView getGongjeMonthReport(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                // 1. 공제 마감처리할 정산년월 조회
                String nCloseYYYYMM = DlwDeductionService.getNextGongjeCloseYYYYMM();
                hmParam.put("nCloseYYYYMM", nCloseYYYYMM);
                mapOutVar.put("nCloseYYYYMM", nCloseYYYYMM);

                System.out.println("@@@@@@@@@@@@@@@@@ 공제 월별 보고서 조회 파라미터 : " + hmParam);

                // 2. 공제 월별 보고서 선수금 및 구분별 조회
                List<Map<String, Object>> mList = DlwDeductionService.getGongjeMonthReport(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                // 2-1. 마감미처리 데이터인 경우 (** 조회일자와 다음 공제 마감년월 비교)
                int comp_yyyymm_1 = Integer.parseInt(hmParam.get("yyyymm").toString());       // 조회 정산년월
                int comp_yyyymm_2 = Integer.parseInt(hmParam.get("nCloseYYYYMM").toString()); // 공제 마감처리할 정산년월

                if( comp_yyyymm_1 >= comp_yyyymm_2 )
                {
                    // 공제 월별 보고서 구분별 카운트조회
                    List<Map<String, Object>> mList2 = DlwDeductionService.getCntGongjeMonthReport(hmParam);
                    listMap2.setRowMaps(mList2);
                    mapOutDs.put("ds_output2", listMap2);
                }
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
     * 날짜 : 20190311
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 마감처리
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    @RequestMapping(value = "/insertGongjeClose")
    public ModelAndView insertGongjeClose(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                System.out.println("@@@@@@@@@@@@@@@@@ 공제 월별 보고서 마감처리 데이터: " + hmParam);
                DlwDeductionService.insertGongjeClose(hmParam); // 공제마감처리
                DlwDeductionService.insertGongjeAsisToOrigin(hmParam);
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
     * 날짜 : 20190321
     * 이름 : 정승철
     * 추가내용 : 정상적인 공제 ASIS-DATA 총 금액 및 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/getSumGongjeAsisData")
    public ModelAndView getSumGongjeAsisData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> mList = DlwDeductionService.getSumGongjeAsisData();
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
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

    /**
     * 공제결과 삭제
     * 정승철
     * 20190410
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteGongjeResult")
    public ModelAndView deleteGongjeResult(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            // 공제결과 삭제
            DlwDeductionService.deleteGongjeResult();

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190516
     * 이름 : 송준빈
     * 추가내용 : 공제 월별 보고서 마감처리
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    @RequestMapping(value = "/resultFileDownload")
    public void resultFileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try
        {
            String sSrchDt = CommonUtils.nvls(request.getParameter("sSrchDt"));
            String sFileYYYY = sSrchDt.substring(0, 4);
            String sFileMM = sSrchDt.substring(4, 6);
            String sMatchingFlag = "N";

            String sResultFileDownloadBasePath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0)
            {
                sResultFileDownloadBasePath = EgovProperties.getProperty("gongje.resultupload.windows.basePath") + sFileYYYY + "/" + sFileMM + "/";
                sResultFileDownloadBasePath = sResultFileDownloadBasePath.replaceAll("/", "\\\\");
            }
            else
            {
                sResultFileDownloadBasePath = EgovProperties.getProperty("gongje.resultupload.unix.basePath") + sFileYYYY + "/" + sFileMM + "/";
            }

            System.out.println("공제데이터 parent path ::: " + sResultFileDownloadBasePath);

            File xDownFile = new File(sResultFileDownloadBasePath);
            if(xDownFile.exists() == false)
            {
                xDownFile.mkdirs();
            }

            String[] arrBaseFiles = xDownFile.list();

            for(int idx = 0; idx < arrBaseFiles.length; idx++)
            {
                String sMatchFileName = arrBaseFiles[idx].substring(0, 8);

                if(sMatchFileName.equals(sSrchDt))
                {
                    sMatchingFlag = "Y"; // 일치하는 데이터인 경우 다운로드를 수행하기 때문에 플래그를 Y로 해줌
                    
                    String contentType = request.getContentType();
                    System.out.println("contentType : " + contentType);

                    if (contentType == null)
                    {
                        if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                        {
                            response.setContentType("doesn/matter;");
                        }
                        else
                        {
                            response.setContentType("application/octet-stream");
                        }
                    }
                    else
                    {
                        response.setContentType(contentType);
                    }

                    response.setHeader("Content-Transfer-Encoding:", "binary");
                    response.setHeader("Content-Length", "" + sResultFileDownloadBasePath + arrBaseFiles[idx].length());
                    response.setHeader("Pragma", "no-cache;");
                    response.setHeader("Expires", "-1;");

                    byte b[] = new byte[1024 * 4];
                    BufferedInputStream fin = new BufferedInputStream(new FileInputStream(sResultFileDownloadBasePath + arrBaseFiles[idx]));
                    BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
                    
                    System.out.println("출력 파일명 :::: " + sResultFileDownloadBasePath + arrBaseFiles[idx]);

                    try
                    {
                        int read = 0;
                        while ((read = fin.read(b)) != -1)
                        {
                            outs.write(b,0,read);
                        }
                    }
                    catch (Exception e)
                    {

                    }
                    finally
                    {
                        if (outs != null)
                        {
                            outs.close();
                        }
                        if (fin != null)
                        {
                            fin.close();
                        }
                    }
                }
                else
                {
                	System.out.println("기준에 맞지 아니한 데이터 입니다. 다운로드를 하지 않고 다음 파일을 진행합니다.");
                }
            }
            
            if (sMatchingFlag.equals("N"))
            {
                EgovBizException e =  new EgovBizException("해당일자의 공제산출 결과 데이터가 도착하지 않았습니다.");
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                pw.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
                pw.println("<Parameters>");
                pw.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
                pw.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
                pw.println("</Parameters>");
                pw.println("</Root>");
                pw.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 공제 에이전트를 통한 파일전송
     * 정승철
     * 20190513
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sendGongjeFile")
    public ModelAndView sendGongjeFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            Socket socket = null;
            PrintWriter printWriter = null;

            try {
                //(대명개발서버 아이피)61.39.175.221안될시 127.0.0.1으로 호출
                //socket = new Socket("61.39.175.221", 8000); // 운영연계에이전트 IP , 포트
                //socket = new Socket("61.39.175.227", 8000); // 개발연계에이전트 IP , 포트

                //socket = new Socket("192.168.10.62", 8000); // 개발연계에이전트 IP , 포트
                socket = new Socket("192.168.220.52", 8000); // 운영연계에이전트 IP , 포트

                boolean result = socket.isConnected();
                //System.out.println("isConnected : " + result);

                if(result) System.out.println("===== 서버 연결 성공");
                else System.out.println("서버 연결 실패");

                printWriter = new PrintWriter(socket.getOutputStream());

                // 앞의숫자4자리(상조공제조합회원사코드 :  9111(대명테스트) ) + 공백 + 파일경로
                // 소켓호출 예시 => "9111 /app/gongje/send/singo_excel.xlsx"
                //String str = "9111 /app/gongje/send/singo_excel.xlsx";

                // 개발(DEV) ///////////////////////////////////////////////////////////
                //String str = "9111 /app/gongje/send/1040.xlsx";
                ////////////////////////////////////////////////////////////////////////

                // 운영(REAL) //////////////////////////////////////////////////////////
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar c1 = Calendar.getInstance();
                String sCurrTime = sdf.format(c1.getTime());
                System.out.println("@@@@ Today : " + sCurrTime);

                String str = "1040 /app/gongje/send/DMLIFE_" + sCurrTime + ".xlsx";
                ////////////////////////////////////////////////////////////////////////

                printWriter.print(str);
                printWriter.flush();

                System.out.println("■■완료 !■■");

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("■■최초 연결 실패 !■■");

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                if (printWriter != null){
                    printWriter.close();
                }
                if (socket != null){
                    try {
                        System.out.println("■■공제소켓닫힘 Finally ~~ !■■");
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("■■연결 실패 !■■");
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
}
