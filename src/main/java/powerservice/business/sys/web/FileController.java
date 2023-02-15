/*
 * (@)# FileController.java
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

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sys.service.FileService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.ColumnHeader;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataTypes;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 파일 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID File
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private ServletContext ctx;

    @Resource
    private FileService fileService;

    //responseBodyConverter는 결과를 출력시에 강제로 UTF-8로 설정하는 역할을 하며
    //characterEncodingFilter는 POST 요청시에 한글이 깨지는 문제를 보완해줍니다.
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    /**
     * 파일 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            String sRltnItemId = StringUtils.defaultString((String) hmParam.get("rltn_item_id"));

            List<Map<String, Object>> list = null;
            if (!"".equals(sRltnItemId)) {

                //list = fileService.getFileList(sRltnItemId);
                list = fileService.getFileList(sRltnItemId);
                listMap.setRowMaps(list);
            }

            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        } catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 파일 정보를 검색한다. (
     * (ATCH_TYP_CD 30인 것도 조회)
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list-all")
    public ModelAndView getFileListAllByMap(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sRltnItemId = StringUtils.defaultString((String) pmParam.get("rltn_item_id"));

        List<Map<String, Object>> list = null;
        if (!"".equals(sRltnItemId)) {
            //list = fileService.getFileList(sRltnItemId);
            list = fileService.getFileListAllByMap(pmParam);
        }

        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    @RequestMapping(value = "/download")
    public void downloadFile(@RequestParam Map<String, String> pmParam, HttpServletResponse response) throws Exception {
        ServletOutputStream servletOutputStream = null;

        try {
            HashMap<String, Object> fileContentsMap = (HashMap<String, Object>) fileService.getFile(pmParam);

            if (fileContentsMap != null) {
                String sFileNm = (String) fileContentsMap.get("file_nm");

                sFileNm = URLEncoder.encode(sFileNm, "utf-8").replaceAll("\\+", "%20");

                response.setHeader("Content-Disposition", "attachment;filename=" + sFileNm + ";");
                response.setContentType("Content-type: application/octet-stream");

                byte[] aFileCntn = (byte[]) fileContentsMap.get("file_cntn");
                int nFileSize = aFileCntn.length;

                servletOutputStream = response.getOutputStream();
                response.setContentLength(nFileSize);
                servletOutputStream.write(aFileCntn, 0, nFileSize);
                servletOutputStream.flush();
            }
        } catch(Exception except) {
            String sExceptNm = except.getClass().getName();

            if ("org.apache.catalina.connector.ClientAbortException".equals(sExceptNm)) {
                //톰캣으로 실행시 Exception에러남 서버에서는 이상없음
            } else {
                except.printStackTrace();
                throw except;
            }
        } finally {
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
        }
    }

    @RequestMapping(value = "/insert")
    public void insertFile2(final HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pmParam) throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);


        List<FileItem> items = upload.parseRequest(request);
        FileItem item;

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            DataSet ds = new DataSet("Dataset00");
            ds.addColumn(new ColumnHeader("file_id", DataTypes.STRING));
            ds.addColumn(new ColumnHeader("file_nm", DataTypes.STRING));
            ds.addColumn(new ColumnHeader("file_size_vl", DataTypes.LONG));

            item = items.get(0);

            String sFileNm = item.getName();
            long nFileSize = item.getSize();
            byte[] aFileCntn = new byte[8192];

            aFileCntn = getFileBinaryData(item);

            sFileNm = sFileNm.substring(sFileNm.lastIndexOf("\\") + 1);	// 불필요한 패스는 제거
            pmParam.put("file_nm", sFileNm);   // 파일명
            pmParam.put("file_cntn", aFileCntn);     // 파일내용
            pmParam.put("file_size_vl", nFileSize);  // 파일크기
            ParamUtils.addSaveParam(pmParam);

            String sFileId = fileService.insertFile(pmParam);

            int nRow = ds.newRow();
            ds.set(nRow, "file_id", sFileId);
            ds.set(nRow, "file_nm", sFileNm);
            ds.set(nRow, "file_size_vl", nFileSize);

            resData.addDataSet(ds);
            resVarList.add("ErrorCode", "0");
            resVarList.add("ErrorMsg", sFileId);
        } catch(Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    public byte[] getFileBinaryData(FileItem item) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream is = null;

        FileInputStream fis = null;
        BufferedInputStream input = null;

        try {
            is = item.getInputStream();

            byte[] buff = new byte[8152];
            int len = -1;
            while ((len = is.read(buff)) != -1){  // 512 길이만큼읽어와 버퍼에 저장
                output.write(buff, 0, len); // 버퍼에 지정길이만큼 읽어서 쓴다
            }
        } catch(Exception except) {
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return output.toByteArray();
    }



    /**
     * 파일 정보를 삭제한다.
     *
     * @param sFileId String
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteFile(@RequestParam("fileid") String sFileId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("common/file-upload-response");

        // Ajax 오류 처리자 설정
        ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        oServletRequestAttribute.getRequest().setAttribute("bAjaxExceptionHandler", "true");

        // 삭제할 파일의 ID만 다시 넘겨줌. 실제 파일은 해당 글을 저장할때 삭제한다.
        modelAndView.addObject("file_id", sFileId);

        return modelAndView;
    }


    /**
     * 파일 정보를 삭제한다.
     *
     * @param sFileId String
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete/real")
    public ModelAndView deleteFileReal(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            //조회조건
            DataSetMap delInDs = (DataSetMap)mapInDs.get("ds_input");
            for(int i=0; delInDs.size() > i; i++ ){
                hmParam = delInDs.get(i);
                String sFileId = (String)hmParam.get("file_id");

                fileService.deleteFile(sFileId);
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 파일을 업로드한다.
     *
     * @param pmParam Map<String, Object>
     * @param poFile MultipartFile
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/imagebrowser/insert")
    public ModelAndView insertEditorImageFile(@RequestParam Map<String, Object> pmParam, @RequestParam("Filedata") MultipartFile poFile, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("common/image-browser-response");

        if (poFile != null && !poFile.isEmpty()) {
            ParamUtils.addSaveParam(pmParam);

            String sFileNm = poFile.getOriginalFilename();
            byte[] aFileCntn = poFile.getBytes();
            long nFileSize = poFile.getSize();

            pmParam.put("rltn_tbl_nm", pmParam.get("reltblnm"));	// 관계테이블명
            pmParam.put("atch_typ_cd", "30");      					// 첨부유형코드
            pmParam.put("file_nm", sFileNm);         				// 파일명
            pmParam.put("file_cntn", aFileCntn);     				// 파일내용
            pmParam.put("file_size_vl", nFileSize);  				// 파일크기
            String sFileId = fileService.insertFile(pmParam);

            Image oImage = (new ImageIcon(aFileCntn)).getImage();

            modelAndView.addObject("file_id", sFileId);
            modelAndView.addObject("width", oImage.getWidth(null));
            modelAndView.addObject("height", oImage.getHeight(null));
        }

        return modelAndView;
    }

    /**
     * 이미지파일을 업로드한다. (클립보드로부터 붙여넣기)
     *
     * @param pmParam Map<String, Object>
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/imagebrowser/pasteinsert")
    public ModelAndView insertEditorImageFilePaste(@RequestParam Map<String, Object> pmParam, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("common/image-browser-response");

        ParamUtils.addCenterParam(pmParam);

        byte[] aFileCntn = Base64.decodeBase64(pmParam.get("imagedata").toString());

        pmParam.put("atch_typ_cd", "EDT");         // 첨부유형코드 - 에디터이미지
        pmParam.put("file_nm", "pasteimage.png");  // 파일명
        pmParam.put("file_cntn", aFileCntn);       // 파일내용
        pmParam.put("file_size_vl", 10);           // 파일크기

        String sFileId = fileService.insertFile(pmParam);

        Image oImage = (new ImageIcon(aFileCntn)).getImage();

        modelAndView.addObject("file_id", sFileId);
        modelAndView.addObject("width", oImage.getWidth(null));
        modelAndView.addObject("height", oImage.getHeight(null));

        return modelAndView;
    }

    /**
     * 템플릿파일을 다운로드한다.
     *
     * @param pmParam Map<String, Object>
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download-template")
    public void downLoadTemplateFile(@RequestParam Map<String, String> pmParam, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        String sFileNm = StringUtils.defaultString((String) pmParam.get("file_nm"));

        if (sFileNm.equals("")) {
            return;
        }

        try {
            File oTemplateFile = new File(ctx.getRealPath("") + "/common/file/" + sFileNm);

            fileInputStream = new FileInputStream(oTemplateFile);
            byte[] aTemplateFileCntn = new byte[(int) oTemplateFile.length()];
            fileInputStream.read(aTemplateFileCntn);

            response.setHeader("Content-Disposition", "attachment;filename=" + sFileNm + ";");
            response.setContentType("Content-type: application/octet-stream");
            servletOutputStream = response.getOutputStream();
            response.setContentLength((int) oTemplateFile.length());
            servletOutputStream.write(aTemplateFileCntn);
        } catch (Exception except) {
            String sExceptNm = except.getClass().getName();

            if ("org.apache.catalina.connector.ClientAbortException".equals(sExceptNm)) {
                //톰캣으로 실행시 Exception에러남 서버에서는 이상없음
            } else {
                except.printStackTrace();
                throw except;
            }
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 파일다운로드
     *
     * @param request HttpServletRequest
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/encom/download.do")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String fileCode = request.getParameter("fileCode");
    	String fileName = request.getParameter("fileName");
    	String dir 		= request.getParameter("dir");
    	
    	if (null == fileCode) fileCode = "";
    	if (null == fileName) fileName = "";
    	if (null == dir) dir = "";
    	
    	System.out.println("=============== /encom/download.do ==================");
    	System.out.println("fileCode : " + fileCode);
    	System.out.println("fileName : " + fileName);    	
    	System.out.println("dir : " + dir);    	
    	
    	
    	
    	
    	
    	
    	if(fileCode.indexOf("..") != -1 || fileCode.indexOf("/") != -1
    			|| dir.indexOf("..") != -1 || dir.equals("/")) {
    		System.out.println("다운로드 할 수 없습니다.");
    		return;
    	} else if(!dir.startsWith("/upload")
//    			&& !dir.startsWith("/IMAGE/APPFM/")
//    			&& !dir.startsWith("/CSV")
//    			&& !dir.startsWith("/CMS")
//    			&& !dir.startsWith("/CARD")
    			&& !dir.startsWith("/nas_rec")
//    			&& !dir.startsWith("/KSMAC")
//    			&& !dir.startsWith("/web_site/Template")
//    			&& !dir.startsWith("/web_site/FileDownload")
    			) {
    		
    		System.out.println("다운로드 할 수 없습니다.(path)");
    		return;
    	} else {
    	
    		ServletOutputStream out = response.getOutputStream();
    		
    		/*******************************************************/
        	/* 테스트 코드 시작 */
        	/*******************************************************/
    		File file222 = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert333.jpg");
    		
    		response.setContentType("Content-type: application/octet-stream;");    		
    		response.setHeader("Content-Disposition","attachment;filename="+fileCode+";");
	 		response.setHeader("Content-Transfer-Encoding","binary;");
	  		int filesize = (int)file222.length();
	 		response.setContentLength( (int)filesize );
	  		response.setHeader("Pragma","no-cache;");
	 		response.setHeader("Expires","-1;");
	 
	 		byte b[] = new byte[4096];
				
	 		BufferedInputStream fin2 = new BufferedInputStream(new FileInputStream(file222));
	 
	 		try
	 		{
	  			int read=0;
	  			while((read = fin2.read(b)) != -1 )
	  			{
	  				out.write(b,0,read);
	  			}
	 		}
	 		catch(Exception e){
	  			// out.println(e);
	 		}
	 		finally
	 		{
	  			if(out != null)out.close();
	  			if(fin2 != null)fin2.close();
	 		}
    		
    		return;
        	/*******************************************************/
        	/* 테스트 코드 끝 - 테스트 끝나면 지우고 아래 주석을 풀어야 한다.*/
        	/*******************************************************/
    		
    		
    		
//    		dir = "/home" + dir; // jcy
//    		
//    		File fFile = new File(dir,fileCode);
//    		String strClient = request.getHeader("user-agent");
//    		if (null == strClient) {
//    			strClient = "";
//    		}
//    	
//    		if(fFile.exists())
//    		{
//    	 		if(strClient.indexOf("MSIE 5.5") != -1)
//    	 		{
//    	  			response.setContentType("doesn/matter;");
//    	 		}
//    	 		else
//    	 		{
//    	  			response.setContentType("Content-type: application/octet-stream;");
//    	 		}
//    	 		response.setHeader("Content-Disposition","attachment;filename="+fileCode+";");
//    	 		response.setHeader("Content-Transfer-Encoding","binary;");
//    	  		int filesize = (int)fFile.length();
//    	 		response.setContentLength( (int)filesize );
//    	  		response.setHeader("Pragma","no-cache;");
//    	 		response.setHeader("Expires","-1;");
//    	 
//    	 		byte b[] = new byte[4096];
//    				
//    	 		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fFile));
//    	 
//    	 		try
//    	 		{
//    	  			int read=0;
//    	  			while((read = fin.read(b)) != -1 )
//    	  			{
//    	  				out.write(b,0,read);
//    	  			}
//    	 		}
//    	 		catch(Exception e){
//    	  			// out.println(e);
//    	 		}
//    	 		finally
//    	 		{
//    	  			if(out != null)out.close();
//    	  			if(fin != null)fin.close();
//    	 		}
//    		}
    		
    		
    		
    		
    		
    	}
    }
}