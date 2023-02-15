/*
 * (@)# SampleController.java
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

package powerservice.business.sample.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.common.web.CommonController;
import powerservice.business.sample.service.SampleService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.crypto.SHA256;

import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * (테스트)샘플정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 홍길동
 * @version 1.0
 * @date 2016/01/02
 * @프로그램ID Sample
 */

@Controller
public class SampleController {

    private final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private SampleService sampleService;

    /**
     * (테스트)샘플정보를 검색한다.
     *
     * @param  XPlatformMapDTO xpDto, Model model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/list")
    public ModelAndView getSampleList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("sample_name", mapInVar.get("sample_name"));
            hmParam.put("sample_dept", mapInVar.get("sample_dept"));
            hmParam.put("sample_age",  mapInVar.get("sample_age"));

            List<Map<String, Object>> listSample = sampleService.getSampleList(hmParam);

            dsMap.setRowMaps(listSample);
            mapOutDs.put("ds_dm_sample", dsMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        System.out.println("[SampleController.java] getSampleList => modelAndView : " + modelAndView);

        return modelAndView;
    }

    /**
     * (테스트)샘플정보를 등록한다.
     *
     * @param  XPlatformMapDTO xpDto, Model model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/save")
    public ModelAndView saveSample(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            System.out.println("[SampleController.java] saveSample => listInDs.size : " + listInDs.size());

            for (int i = 0; i < listInDs.size(); i++) {
                Map hmParam = listInDs.get(i);
                hmParam.put("reg_user_id", "ADMIN");
                hmParam.put("mod_user_id", "ADMIN");
                hmParam.put("cntr_cd", "DMCC");

                System.out.println("[SampleController.java] saveSample => hmParam : " + hmParam);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out.println("[SampleController.java] saveSample => DataSet.ROW_TYPE_INSERTED : " + DataSet.ROW_TYPE_INSERTED + ", DataSet.ROW_TYPE_UPDATED : " + DataSet.ROW_TYPE_UPDATED + ", DataSet.ROW_TYPE_DELETED : " + DataSet.ROW_TYPE_DELETED + ", rowType : " + rowType);

                if ( rowType == DataSet.ROW_TYPE_INSERTED ){
                    sampleService.insertSample(hmParam);
                }else if (rowType == DataSet.ROW_TYPE_UPDATED){
                    sampleService.updateSample(hmParam);
                }else if (rowType == DataSet.ROW_TYPE_DELETED){
                    sampleService.deleteSample(hmParam);
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

        System.out.println("[SampleController.java] saveSample => modelAndView : " + modelAndView);

        return modelAndView;
    }
    
    /**
     * NICE Safe-Key 발급안내 SMS 발송 - 화면에서 SMS발송 커맨드 호출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/checkplus_send_result")
    public ModelAndView sendSafeKeyIssueSms(HttpServletRequest request, Model model) throws Exception {
    	ModelAndView mav = new ModelAndView(); 
        mav.setViewName("common/checkplus_send_result");
        return mav;
    }
    
    /**
     * 프로시져를 이용한 목록 조회 샘플 쿼리
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/getListByProc")
    public ModelAndView getListByProc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        
        DataSetMap listMap = new DataSetMap();        
        List<Map<String, Object>> mRslt = null;
        
        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        
        try {
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Map hmParam = new HashMap<String, Object>();
            hmParam.put("mem_nm", "홍길동");
            System.out.println("----------- 1");
        	mRslt = sampleService.getListByProc(hmParam);
        	System.out.println("----------- 2");
        	System.out.println("mRslt.size() : " + mRslt.size());
        	
        	listMap.setRowMaps(mRslt);
            
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
     * 프로시져를 이용한 목록 조회 샘플 쿼리
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/getListByProc2")
    public ModelAndView getListByProc2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        
        DataSetMap listMap = new DataSetMap();        
        List<Map<String, Object>> mRslt = null;
        
        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        
        try {
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Map hmParam = new HashMap<String, Object>();
            hmParam.put("prod_cd", "AA");
            System.out.println("----------- 1");
        	mRslt = sampleService.getListByProc2(hmParam);
        	System.out.println("----------- 2");
        	System.out.println("mRslt.size() : " + mRslt.size());
        	
        	listMap.setRowMaps(mRslt);
            
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
     * 샘플데이터 PRODUCT 조회 - 데이터를 엑셀파일로 만들고 엑셀파일을 다운로드한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/getProductList")
    public void getProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        int iSheetIdx = 0;
        int iExcelRowNo = 0;

        try {
        	
        	String sProdCd = CommonUtils.nvls(request.getParameter("prod_cd"));
        	hmParam.put("prod_cd", sProdCd);
        	log.debug("sProdCd : " + sProdCd);
            
            List<Map<String, Object>> mList = sampleService.getProductList(hmParam);
            
            String sTmpDir = System.getProperty("java.io.tmpdir");
            String xlFileNm = sTmpDir + "filename.xlsx";
            log.debug(">xlFileNm : " + xlFileNm);
            
            File xfile = new File(xlFileNm);
            
            WritableWorkbook workbook = Workbook.createWorkbook(xfile);
            
            WritableSheet sheet = workbook.createSheet("21_0" + String.valueOf(iSheetIdx), iSheetIdx);

            // 해당 셀에 데이터를 입력한다.            
            int i, j;
            iExcelRowNo = 0;
            Map<String, Object> mRow = null;
            for (i = 0; i < mList.size(); i++) {
            	
            	mRow = mList.get(i);
            	
            	iExcelRowNo++;
            	
//            	if (iExcelRowNo % 100 == 0) {
//            		log.debug(">i : " + iExcelRowNo);
//            	}
            	
            	j = 0;
            	sheet.addCell(new Label(j++, iExcelRowNo, String.valueOf(mRow.get("rnum"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("prod_cd"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("prod_nm"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("mon_pay_amt"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("expr_no"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("prod_amt"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("prod_point"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("reg_dm"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("reg_man"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("del_fg"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("prod_cl"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("sale_cl"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("sale_close_day"))));
            	sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("prod_save_point"))));
            	
            	if (iExcelRowNo >= 60000) {
            		iSheetIdx++;
            		sheet = workbook.createSheet("21_0" + String.valueOf(iSheetIdx), iSheetIdx);
            		iExcelRowNo = 0;
            	}
            }
            
            
            /* 핑드명 */
            for (i=0; i<workbook.getSheetNames().length; i++) {
            	sheet = workbook.getSheet(i);
            			
            	j=0;
	    		sheet.addCell(new Label(j++, 0, "NO"));
	    		sheet.addCell(new Label(j++, 0, "PROD_CD"));
	            sheet.addCell(new Label(j++, 0, "PROD_NM"));
	            sheet.addCell(new Label(j++, 0, "MON_PAY_AMT"));
	            sheet.addCell(new Label(j++, 0, "EXPR_NO"));
	            sheet.addCell(new Label(j++, 0, "PROD_AMT"));
	            sheet.addCell(new Label(j++, 0, "PROD_POINT"));
	            sheet.addCell(new Label(j++, 0, "REG_DM"));
	            sheet.addCell(new Label(j++, 0, "REG_MAN"));
	            sheet.addCell(new Label(j++, 0, "DEL_FG"));
	            sheet.addCell(new Label(j++, 0, "PROD_CL"));
	            sheet.addCell(new Label(j++, 0, "SALE_CL"));
	            sheet.addCell(new Label(j++, 0, "SALE_CLOSE_DAY"));
	            sheet.addCell(new Label(j++, 0, "PROD_SAVE_POINT"));         	
            }

            workbook.write();
            
            // workbook 을 닫는다.
            workbook.close();
            
            File xDownFile = new File(xlFileNm);
            String contentType = request.getContentType();  
            response.setContentType("x-msdownload");  
            
            if (contentType == null) {  
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)  
                    response.setContentType("doesn/matter;");  
                else  
                    response.setContentType("application/octet-stream");  
            } else {  
                response.setContentType(contentType);  
            }
            
            response.setHeader("Content-Transfer-Encoding:", "binary");  
            String sDisplayFileName = "상품목록.xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");  
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
            
            try{  
                if (xDownFile.exists()) {
                	xDownFile.delete();
                }
            }catch(Exception e){}      
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 비밀번호 암호화
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/getPassword")
    public void getPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pwd = request.getParameter("pwd");
        try {
        	String str = SHA256.digest(pwd);
        	
        	
        	response.setContentType("text/plain");
        	PrintWriter writer = response.getWriter();        
        	writer.println(pwd + " => " + str);
        	writer.println("\n");
        	
        	URL aURL = new URL(request.getRequestURL().toString());
        	writer.println("host => " + aURL.getHost() + "\n");
        	
        	writer.close();
              
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
    }
    
    /**
     * 비밀번호 암호화
     * - http://localhost:8080/powerservice/sample/insertTestPlSql
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sample/insertTestPlSql")
    public void insertTestPlSql(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pwd = request.getParameter("pwd");
        try {
        	Map<String, Object> hmParam = new HashMap<>();
        	hmParam.put("si", "서울특");
        	sampleService.insertTestPlSql(hmParam);
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
    }
    
    @RequestMapping(value = "/sample/uploadFile", method=RequestMethod.POST)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println("------------------------- 1");
    	
    	String chkType = request.getHeader("Content-Type");
    	
    	String emple_no 	= CommonUtils.nvls(request.getParameter("emple_no"));
    	String cp_cell 		= CommonUtils.nvls(request.getParameter("cp_cell"));
    	String pic_cl_cd 	= CommonUtils.nvls(request.getParameter("pic_cl_cd"));
    	
    	//System.out.println(chkType);

    	System.out.println("------------------------- 2.1");
    	//Content-Type이 null 일때 리턴
    	if( chkType == null ) {
    		return;
    	}
        
    	int maxSize = 1024 * 1024 * 1024;
    	
    	String savePath = "C:\\egovframework\\acuon\\life\\tmp";
    	File saveFolder = new File(savePath);		
		if (!saveFolder.exists() || saveFolder.isFile())
		{
		    saveFolder.mkdirs();
		}
    	
    	// MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
    	MultipartRequest multi = new MultipartRequest(request, System.getProperty("java.io.tmpdir"));
    	Enumeration files = multi.getFileNames(); // 파일명 모두 얻기
		
		System.out.println("------------------------- 2.2");
		
		SimpleDateFormat smt = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		Calendar cal = Calendar.getInstance();
		String currTime = smt.format(cal.getTime());
		System.out.println("currTime : " + currTime);
		String fileName="";
		File currFile = null;
		File newFile = null;
		String contentType = null;
		String fileExt = "";
		String saveAs = "";
		long fileSize = 0;
		String saveAsFileNm = "";
		int i=0, pos=-1;
		while (files.hasMoreElements()) {			
			String name 	= (String)files.nextElement();
			fileName 		= multi.getFilesystemName(name);
			contentType 	= multi.getContentType(name);
			currFile 		= multi.getFile(name);
			fileSize 		= currFile.length();
			
			pos				= fileName.lastIndexOf(".");
			if (pos >= 0) {
				fileExt = currFile.getName().substring(pos+1).toLowerCase();
			}
			
			saveAsFileNm	= emple_no + "_" + cp_cell + "_" + currTime + "_" + pic_cl_cd + "." + fileExt;
			saveAs			= savePath + "\\" + saveAsFileNm;
			newFile 		= new File(saveAs);
			moveFile(currFile, newFile);
			
			System.out.println("fileExt : "+fileExt);
			System.out.println("name : "+name);
			System.out.println("fileName : "+fileName);
			System.out.println("contentType : "+contentType);
			System.out.println("fileSize : "+fileSize);
			System.out.println("saveAs : "+saveAs);
			
			i++;
		}
		
		System.out.println("------------------------- 2");
		
		ServletOutputStream os = response.getOutputStream();
		os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
		os.println("<Parameters>");
		os.println("<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>");
		os.println("<Parameter id=\"ErrorMsg\" type=\"string\">OK|" + saveAsFileNm + "|" + currTime + "</Parameter>");
		os.println("</Parameters>");
		os.println("</Root>");
		os.close();
    }
    
    private void moveFile(File source, File dest) throws IOException {

        InputStream input = null;
        OutputStream output = null;

        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}