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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwOverallStatusService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class DlwOverallStatusController {
	
	@Resource
	private DlwOverallStatusService dlwOverallStatusService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/ext/getOverallStatusList")
    public ModelAndView getOverallStatusList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

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

                int nTotal = dlwOverallStatusService.getOverallStatusCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = dlwOverallStatusService.getOverallStatusList(hmParam);
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
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일청구기준데이터 다운로드
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST, LF_DMUSER.TB_MBID_CSV_DNDL_HIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/ext/overallStatusDownLoadFile")
    public void overallStatusDownLoadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try
        {
            String sExtDt    = CommonUtils.nvls(request.getParameter("ext_dt"));
            String sFileType = CommonUtils.nvls(request.getParameter("file_type"));
            String sFilePath = CommonUtils.nvls(request.getParameter("file_path"));

            hmParam.put("ext_dt", sExtDt);
            hmParam.put("file_type", sFileType);
            hmParam.put("file_path", sFilePath);
            ParamUtils.addSaveParam(hmParam);

            File xDownFile = new File(sFilePath);
            if (!xDownFile.exists())
            {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

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
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try
            {
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

                dlwOverallStatusService.updateOverallStatusDownLoadEmplFileList(hmParam);
                dlwOverallStatusService.insertOverallStatusDownLoadEmplHist(hmParam);
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

            outs.flush();
            outs.close();
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
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/pop/getOverallStatusBatchDay")
    public ModelAndView getOverallStatusBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = dlwOverallStatusService.getOverallStatusBatchDay(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/pop/insertOverallStatusBatchDay")
    public ModelAndView insertOverallStatusBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            int chkExtReqDt = dlwOverallStatusService.validationOverallStatusFile(hmParam);

            if(chkExtReqDt == 0)
            {
                dlwOverallStatusService.insertOverallStatusBatchDay(hmParam);
            }
            else if(chkExtReqDt > 0)
            {
                mapOutVar.put("xInsertFailMsg", "해당 파일은 이미 등록되어 있습니다.");
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
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/pop/deleteOverallStatusBatchDay")
    public ModelAndView deleteOverallStatusBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            dlwOverallStatusService.deleteOverallStatusBatchDay(hmParam);

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
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 배치처리 테스트용 컨트롤러
     *     (실제로는 배치로 처리되는 부분으로서 운영으로 반영시엔 이부분을 삭제한다.)
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY, LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/overallStatus/batch/batchOverallStatusFileMake")
    public ModelAndView batchOverallStatusFileMake(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            int nResult = dlwOverallStatusService.batchOverallStatusFileMake();
            mapOutVar.put("nBatchSuccessCode", nResult);
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
}