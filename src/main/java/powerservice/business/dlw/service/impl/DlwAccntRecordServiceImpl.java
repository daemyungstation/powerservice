package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwAccntRecordService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.DateUtil;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class DlwAccntRecordServiceImpl extends EgovAbstractServiceImpl implements DlwAccntRecordService {

	@Resource
    public DlwAccntRecordDAO dlwAccntRecordDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwAccntRecordServiceImpl.class);
    
	/** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception {
        return dlwAccntRecordDAO.getFileUploadList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public int deleteUploadRecData(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

        int iCudCnt = 0;
        Map<String, Object> mRow = null;

        try 
        {
            String successPath 		= "";
            String fullPath 		= "";

            if (listInDs != null && listInDs.size() > 0) 
            {
            	String osName = System.getProperty("os.name").toLowerCase();
                
                if (osName.indexOf("windows") >= 0) 
                {
                    successPath = "C:\\\\media\\\\dmrecord1\\\\record_data\\\\";
                } 
                else 
                {
                    successPath = "/media/dmrecord1/record_data/";
                }

                for (int idx = 0; idx < listInDs.size(); ++idx) 
                {
                    mRow = listInDs.get(idx);
                    ParamUtils.addSaveParam(mRow);
                    iCudCnt += dlwAccntRecordDAO.deleteUploadRecData(mRow);
                    
                    String sAccntNo = String.valueOf(mRow.get("accnt_no"));
                    String sFoldFullName = String.valueOf(mRow.get("record_seq"));
                    String sFoldYear = sFoldFullName.substring(0, 4);
                    String sFoldMonth = sFoldFullName.substring(4, 6);
                    String sFoldDay = sFoldFullName.substring(6, 8);
                    String sFoldHHMISS = sFoldFullName.substring(8, 14);
                    
                    if (osName.indexOf("windows") >= 0) 
                    {
                    	fullPath = successPath + "\\\\" + sFoldYear + "\\\\" + sFoldMonth + "\\\\" + sFoldDay + "\\\\" + sAccntNo + "_" + sFoldFullName;
                    }
                    else 
                    {
                    	fullPath = successPath + sFoldYear + File.separator + sFoldMonth + File.separator + sFoldDay + File.separator + sAccntNo + "_" + sFoldFullName;
                    }

                    File file = new File(fullPath);
                    if (file.exists()) 
                    {
                    	file.delete();
                        log.info("hsFactoringFileBatchUpload> 파일삭제함 ");
                    } 
                    else 
                    {
                        log.info("hsFactoringFileBatchUpload> 파일삭제 못함 ");
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            throw e;
        }
        return iCudCnt;
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드
     * 대상 테이블 : LF_DMUSER.EMPLOYEE, LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public void uploadRecData(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception 
    {
    	String successPath = "";
        String errorPath = "";
        String sEmpleNo = CommonUtils.nvls(request.getParameter("emple_no"));
        String sAccntNo = CommonUtils.nvls(request.getParameter("accnt_no"));

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0) 
        {
        	successPath = "C:\\\\media\\\\dmrecord1\\\\record_data\\\\";
        } 
        else 
        {
        	successPath = "/media/dmrecord1/record_data/";
        }

        String tempDir = System.getProperty("java.io.tmpdir");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024 * 1024 * 20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";
        
        try 
        {   
        	int sTotFileCnt = 0;
            Map<String, Object> hmParam = new HashMap<String, Object>();
            
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("emple_no", sEmpleNo);
            String sAuthYn = dlwAccntRecordDAO.getUploadAuthConfirm(hmParam);
            
            while (enm.hasMoreElements())
            {
            	sTotFileCnt++;
                sKey = (String)enm.nextElement();
                
                File uploadfiles = multipartRequest.getFile(sKey);
                String sUploadFileName = new String(uploadfiles.getName().getBytes("iso-8859-1"), "utf-8");

                String[] arrNameParsing = sUploadFileName.split("\\.");
                
                if ("".equals(sEmpleNo) || !"Y".equals(sAuthYn)) 
                {
                	mResuslt.put("file_nm", sUploadFileName );
                    mResuslt.put("errMsg", "업로드 권한이 없습니다.");
                    throw new EgovBizException("업로드 권한이 없습니다.");
                }
                
                if (arrNameParsing.length != 2) 
                {
                	mResuslt.put("file_nm", sUploadFileName );
                    mResuslt.put("errMsg", "확장자가 없는 파일은 업로드 할수 없습니다.");
                    throw new EgovBizException("확장자가 없는 파일은 업로드 할수 없습니다.");
                }
                
                String sFileName = arrNameParsing[0].trim();
                String sFileExt = arrNameParsing[1].toLowerCase();

                if ((!"wav".equals(sFileExt)) && (!"ogg".equals(sFileExt)) && (!"mp3".equals(sFileExt))) 
                {
                	mResuslt.put("file_nm", sUploadFileName );
                    mResuslt.put("errMsg", "지원하지 않는 파일형식입니다.(허용된 확장자 : wav, ogg, mp3)");
                    throw new EgovBizException("지원하지 않는 파일형식입니다.(허용된 확장자 : wav, ogg, mp3)");
                }

                String[] sFileNameCons = sFileName.split("_");

                if (sFileNameCons.length <= 1) 
                {
                	mResuslt.put("file_nm", sUploadFileName);
                    mResuslt.put("errMsg", "파일명이 잘못되었습니다.");
                    throw new EgovBizException("파일명이 잘못되었습니다.");
                }
                
                String sRecYYYYMMDDHHMISS = sFileNameCons[0];
                String sRecMemo = CommonUtils.nvls(sFileNameCons[1]);
                
                if (sAccntNo.length() != 12 || sRecYYYYMMDDHHMISS.length() != 14) 
                {
                	mResuslt.put("file_nm", sUploadFileName);
                    mResuslt.put("errMsg", "파일명의 형식이 잘못되었습니다. 파일이름은 [녹취기준번호14자리]_[녹취메모] 입니다.");
                    throw new EgovBizException("파일명의 형식이 잘못되었습니다. 파일이름은 [녹취기준번호14자리]_[녹취메모] 입니다.");
                }
                
                hmParam.put("record_seq", sRecYYYYMMDDHHMISS + "." + sFileExt);
                int iCnt = dlwAccntRecordDAO.getRecFileDuplicateConfirm(hmParam);

                if (iCnt > 0) 
                {
                	mResuslt.put("file_nm", sUploadFileName );
                    mResuslt.put("errMsg", "이미 등록되어 있는 파일입니다.");
                    throw new EgovBizException("이미 등록되어 있는 파일입니다.");
                }
                
                String srcFilePath = "";
                String sRecYYYY = sRecYYYYMMDDHHMISS.substring(0, 4);
                String sRecMM = sRecYYYYMMDDHHMISS.substring(4, 6);
                String sRecDD = sRecYYYYMMDDHHMISS.substring(6, 8);
                String sHHMISS = sRecYYYYMMDDHHMISS.substring(8, 14);
                
                if (osName.indexOf("windows") >= 0) 
                {
                	srcFilePath = successPath + "\\\\" + sRecYYYY + "\\\\" + sRecMM + "\\\\" + sRecDD + "\\\\" + sAccntNo + "_" + sRecYYYYMMDDHHMISS + "." + sFileExt;
                }
                else 
                {
                	srcFilePath = successPath + sRecYYYY + File.separator + sRecMM + File.separator + sRecDD + File.separator + sAccntNo + "_" + sRecYYYYMMDDHHMISS + "." + sFileExt;
                }
                
                Map<String, Object> hmUploadFileInfo = new HashMap<String, Object>();
                hmUploadFileInfo.put("accnt_no", sAccntNo);
                hmUploadFileInfo.put("recort_dt", sRecYYYY + sRecMM + sRecDD);
                hmUploadFileInfo.put("record_src", srcFilePath);
                hmUploadFileInfo.put("record_seq", sRecYYYYMMDDHHMISS + "." + sFileExt);
                hmUploadFileInfo.put("record_memo", sRecMemo);

                int result = 0;
                
                result = dlwAccntRecordDAO.insertRecUploadFile(hmUploadFileInfo);

                if (result > 0)
                {
                	File fileParentPath = null;
                	
                	if (osName.indexOf("windows") >= 0) 
                    {
                		fileParentPath = new File(successPath + "\\\\" + sRecYYYY + "\\\\" + sRecMM + "\\\\" + sRecDD + "\\\\");
                		
                		if (!fileParentPath.exists()) 
                		{
                			fileParentPath.mkdirs();
                        }
                    } 
                    else 
                    {
                    	fileParentPath = new File(successPath + sRecYYYY + File.separator + sRecMM + File.separator + sRecDD + File.separator);
                    	
                    	if (!fileParentPath.exists()) 
                    	{
                    		fileParentPath.mkdirs();
                        }
                    }
                	
                	FileOutputStream newFileOs = new FileOutputStream(new File(srcFilePath));
                    FileUtils.copyFile(uploadfiles, newFileOs);
                    uploadfiles.delete();
                    newFileOs.close();
                    
                    //mResuslt.put("file_nm", sAccntNo + "_" + sRecYYYYMMDDHHMISS + "." + sFileExt);
                    mResuslt.put("file_nm", sUploadFileName);
                }
                else
                {
                	mResuslt.put("file_nm", sUploadFileName);
                    mResuslt.put("errMsg", "데이터인서트 실패!!!");
                }
            }

            if (sTotFileCnt < 1) 
            {
                throw new EgovBizException("업로드된 파일이 없습니다.");
            }

        } 
        catch (FileNotFoundException ex) 
        {
            ex.printStackTrace();
            throw ex;
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
            throw ex;
        }
    }
}
