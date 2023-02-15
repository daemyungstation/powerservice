/*
 * (@)# DlwCondController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//2018.03.16 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwRcrtCntrService;
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
 * 서면계약서 전산 관리
 * @author 김주용
 * @date 20230130
 * @프로그램ID
 */
@Controller
@RequestMapping(value = "/dlw/rcrtCntr")
public class DlwRcrtCntrController {

    @Resource
    private DlwRcrtCntrService DlwRcrtCntrService;

    @Resource
    private CommonService commonService;

    /**
     * 대명라이프웨이 모집인계약서출력 - 일괄출력 목록을 검색한다.
     * 
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getJoinList")
    public ModelAndView getCertfList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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
                String excel_fg = (String)mapInVar.get("excel_fg");

                //세션
                ParamUtils.addSaveParam(hmParam);
                
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                if (!"Y".equals(excel_fg)) {
                    int nTotal = DlwRcrtCntrService.getRcrtCntrCount(hmParam);
                    mapOutVar.put("tc_certf", nTotal);

                    List<Map<String, Object>> mList = DlwRcrtCntrService.getRcrtCntrList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
            }

            //2018.03.16 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명스테이션 모집인계약서
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 
     * 
     */
    @RequestMapping(value = "/getAccntContract")
    public ModelAndView getAccntContract(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        String sAccntNo = (String) mapInVar.get("accnt_no").toString();
        String sOutFileNm = "";
                        
        // 파일 위치
        String sSendFileDirectoryPath = "/app/powerservice/common/dm_sign/";
        
        //String sSendFileDirectoryPath = "C://Users/daemyung/Desktop/DLCC/file/"; // 로컬에서 테스트 수행시
        

        BufferedReader br = null;
        InputStreamReader isr = null;
        RandomAccessFile rafile = null;
        
        try
        {
        	File fileSendFileDirectoryPath = new File(sSendFileDirectoryPath);
        	String[] arrSendFileList = fileSendFileDirectoryPath.list();
        	
        	for(int idx = 0; idx < arrSendFileList.length; idx++)
        	{
        		String sFileName = arrSendFileList[idx];
        		
        		if(sFileName.indexOf(sAccntNo) >= 0) {
        			sOutFileNm = sFileName;
        		}
        		        		
    			Map<String,Object> rowData = new HashMap<String, Object>();
    			
    			rafile = new RandomAccessFile(sSendFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
    			long pos = rafile.length() - 2;

    			while(true)
    			{
    				rafile.seek(pos);

    				if (rafile.readByte() == '\n')
    				{
    					break;
    				}
        			
    				pos--;
    			}        		
    			rafile.seek(pos + 1);        		
    			rafile.close();        		
        	}
        	
        	mapOutVar.put("gv_file_nm", sOutFileNm);
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }
        finally
        {
        	
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/FileUpload")
    public void insertFile2(final HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pmParam) throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);


        List<FileItem> items = upload.parseRequest(request);
        FileItem item;

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();
        
        BufferedWriter out = null;
        
        MultipartFile mFile;
        
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
            pmParam.put("file_cntn", aFileCntn);     // 파일내용
            pmParam.put("file_size_vl", nFileSize);  // 파일크기
            ParamUtils.addSaveParam(pmParam);

            int nRow = ds.newRow();
            ds.set(nRow, "file_nm", sFileNm);
            ds.set(nRow, "file_size_vl", nFileSize);

            resData.addDataSet(ds);
            resVarList.add("ErrorCode", "0");
            resVarList.add("ErrorMsg", sFileNm); //sFileId
            
            /*경로*/
            File f = new File((new StringBuilder("/media/dmrecord1/digitalsign/sForm/dm_sign/")).toString(), sFileNm);       // 서버용
            //File f = new File((new StringBuilder("C:\\app\\pdf\\")).toString(), sFileNm);  // 로컬용
                       
            f.createNewFile();

            item.write(f);                        
                        
        	pmParam.put("file_nm", sFileNm);  
        	pmParam.put("accnt_no", sFileNm.substring(0, 12));              	
        	DlwRcrtCntrService.insertFile(pmParam);            	
                                    
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
}