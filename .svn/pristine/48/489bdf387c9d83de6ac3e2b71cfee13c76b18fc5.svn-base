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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwAccntRecordService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.FileZipCompress;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class DlwAccntRecordController {
	
	@Resource
	private DlwAccntRecordService dlwAccntRecordService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
    
	/** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/info/getFileUploadList")
    public ModelAndView getFileUploadList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (srchInDs != null && srchInDs.size() > 0) 
            {
                hmParam = srchInDs.get(0);
                String sSvcId = (String) hmParam.get("tab_id");

                if(sSvcId.equals("tp_accntRecList"))
                {
                    List<Map<String, Object>> mList = dlwAccntRecordService.getFileUploadList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
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
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/info/deleteUploadRecData")
    public ModelAndView hsUploadDataDelete(XPlatformMapDTO xpDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try 
        {
            int iCudCnt = dlwAccntRecordService.deleteUploadRecData(xpDto);

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
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 녹취파일 일괄다운로드 압축파일 생성
     * 대상 테이블 : LF_DMUSER.FILE_DOWN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/info/mkRecZip")
    public ModelAndView mkRecZip(XPlatformMapDTO xpDto, Model model, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap dtptMap = new DataSetMap();
        
        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        List<Map<String,Object>> mList = new ArrayList<>();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> mRow = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        StringBuilder sb = new StringBuilder();

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        String sUserIp = request.getRemoteAddr();
        String sEmpleNo = "";
        String successPath = "";
        String zipPath = "";
        boolean zipResult = false;
        boolean bTmp = false;

        try 
        {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) 
            {
                successPath = "C:\\\\media\\\\dmrecord1\\\\record_data\\\\";
                zipPath = "C:\\\\media\\\\dmrecord1\\\\record_data\\\\";
            } 
            else 
            {
                successPath = "/media/dmrecord1/record_data/";
                zipPath = "/media/dmrecord1/record_data/";
            }

            DataSetMap ds_list = (DataSetMap)mapInDs.get("ds_input");

            sEmpleNo = CommonUtils.nvls(oLoginUser.getUserid());
            hmParam.put("emple_no", sEmpleNo);
            ParamUtils.addSaveParam(hmParam);

            if (null != ds_list && ds_list.size() > 0) 
            {
                FileZipCompress fzc = new FileZipCompress();

                for (int i=0; i < ds_list.size(); ++i) 
                {
                	sb.delete(0, sb.length());
                    mRow = ds_list.get(i);
                    
                    String sAccntNo = String.valueOf(mRow.get("accnt_no"));
                    String sFoldFullName = String.valueOf(mRow.get("record_seq"));
                    String sFoldYear = sFoldFullName.substring(0, 4);
                    String sFoldMonth = sFoldFullName.substring(4, 6);
                    String sFoldDay = sFoldFullName.substring(6, 8);
                    String sFoldHHMISS = sFoldFullName.substring(8, 14);
                    
                    if (osName.indexOf("windows") >= 0) 
                    {
                    	sb.append(successPath);
                        sb.append(sFoldYear + "\\\\");
                        sb.append(sFoldMonth + "\\\\");
                        sb.append(sFoldDay + "\\\\");
                        sb.append(sAccntNo + "_" + sFoldFullName);
                    } 
                    else 
                    {
                    	sb.append(successPath);
                        sb.append(sFoldYear + "/");
                        sb.append(sFoldMonth + "/");
                        sb.append(sFoldDay + "/");
                        sb.append(sAccntNo + "_" + sFoldFullName);
                    }
                    
                    bTmp = fzc.append(sb.toString());
                }

                if (fzc.getFileCount() < 1) 
                {
                    throw new EgovBizException("파일이 존재하지 않습니다.");
                }

                zipResult = fzc.zip(zipPath, sEmpleNo);

                if (zipResult) 
                {
                    File file = new File(fzc.getZipFilePath());

                    hmParam.put("user_ip" , sUserIp);
                    hmParam.put("file_dir", file.getParent().replace("\\", "/"));
                    hmParam.put("file_nm", file.getName());
                    commonService.insertFileDown(hmParam);

                    mList.add(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);

                } 
                else 
                {
                    throw new EgovBizException("파일을 압축하는 동안 오류가 발생하였습니다.");
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

        } 
        catch (EgovBizException e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
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
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 청취및 단건 다운로드 기능
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/info/getWavFile")
    public void getWavFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try 
        {
            String sAccntNo = CommonUtils.nvls(request.getParameter("accnt_no"));
            String sRecordSeq = CommonUtils.nvls(request.getParameter("record_seq"));
            
            hmParam = new HashMap<>();
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("record_seq", sRecordSeq);
            
            List<Map<String, Object>> mList = dlwAccntRecordService.getFileUploadList(hmParam);

            if (null == mList || mList.size() < 1) 
            {
                throw new EgovBizException("데이터베이스에 파일 정보가 존재하지 않습니다.");
            }

            String successPath = "";
            String srcFilePath = "";
            String osName = System.getProperty("os.name").toLowerCase();
            
            if (osName.indexOf("windows") >= 0) 
            {
                successPath = "C:\\\\media\\\\dmrecord1\\\\record_data\\\\";
            } 
            else 
            {
                successPath = "/media/dmrecord1/record_data/";
            }
            
            mRow = mList.get(0);
            
            String sFoldYear = sRecordSeq.substring(0, 4);
            String sFoldMonth = sRecordSeq.substring(4, 6);
            String sFoldDay = sRecordSeq.substring(6, 8);
            String sFoldHHMISS = sRecordSeq.substring(8, 14);
            
            if (osName.indexOf("windows") >= 0) 
            {
            	srcFilePath = successPath + "\\\\" + sFoldYear + "\\\\" + sFoldMonth + "\\\\" + sFoldDay + "\\\\" + sAccntNo + "_" + sRecordSeq;
            }
            else 
            {
            	srcFilePath = successPath + sFoldYear + File.separator + sFoldMonth + File.separator + sFoldDay + File.separator + sAccntNo + "_" + sRecordSeq;
            }

            File xDownFile = new File(srcFilePath);
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
        }
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 다건압축 다운로드
     * 대상 테이블 : LF_DMUSER.FILE_DOWN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/info/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String file_dir = "";
        String file_nm = "";

        try 
        {
            String no = CommonUtils.nvls(request.getParameter("no"));
            String download_key = CommonUtils.nvls(request.getParameter("download_key"));
            String user_ip = request.getRemoteAddr();

            hmParam = new HashMap<>();
            hmParam.put("no", no);
            hmParam.put("download_key", download_key);
            hmParam.put("user_ip", user_ip);

            List<Map<String, Object>> mList = commonService.selectFileDown(hmParam);

            if (null == mList || mList.size() < 1) 
            {
                throw new EgovBizException("잘못된 접근입니다.");
            }

            mRow = mList.get(0);
            file_dir = CommonUtils.nvls((String)mRow.get("file_dir"));
            file_nm = CommonUtils.nvls((String)mRow.get("file_nm"));

            File xDownFile = new File(file_dir, file_nm);

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

            int iCudCnt = commonService.updateFileDownDthms(hmParam);

            xDownFile.delete();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/accntRecord/pop/uploadRecData")
    public void uploadRecData(HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String sFileNm = "";
        String sErrMsg = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try 
        {
        	dlwAccntRecordService.uploadRecData(request, response, mResult);

        	sFileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
        	sErrMsg = CommonUtils.nvls((String)mResult.get("errMsg"));

            if (!"".equals(sErrMsg)) 
            {
                resVarList.add("ErrorCode"	, "-1");
                resVarList.add("ErrorMsg"	, sFileNm + "|" + sErrMsg);
            }
            else 
            {
                resVarList.add("ErrorCode"	, szErrorCode);
                resVarList.add("ErrorMsg"	, sFileNm + "|" + szErrorMsg);
            }
        } 
        catch (EgovBizException e) 
        {
            resVarList.add("ErrorCode", "-1");

            sFileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", sFileNm + "|" + e.getMessage());

            e.printStackTrace();
        } 
        catch (Exception e) 
        {
            resVarList.add("ErrorCode", "-1");

            sFileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", sFileNm + "|업로드 중 오류가 발생하였습니다.");

            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }
}