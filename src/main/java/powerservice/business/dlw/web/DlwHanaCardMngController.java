package powerservice.business.dlw.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter; 
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwHanaCardMngService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SftpManager;
import powerservice.core.util.SessionUtils;

@Controller
@RequestMapping(value = "/dlw/hanamng")
public class DlwHanaCardMngController {


    @Resource
    private DlwHanaCardMngService dlwHanaCardMngService;

    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 전송 관리
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/getHanaList") 
    public ModelAndView getHanaSendList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = dlwHanaCardMngService.getHanaSendList(hmParam);
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 전송관련 로컬 파일 정보
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/getFileList")
    public ModelAndView getHanaFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");    	

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
            Map<String, Object> inParam = new HashMap<String, Object>();    
            
            
            if (listInDs.size() > 0)
            {
            	inParam = listInDs.get(0);
            	                        
	            DataSetMap rtnDs = new DataSetMap();    
	            String strLocalPath = inParam.get("send_path").toString();
	            	            
				File dir = new File(strLocalPath);	    			    	           	        			   
				File[] fileNameList = dir.listFiles();
				   			           	        	        
			    for(File curFile : fileNameList) { 
			    	if(!curFile.isDirectory()){
				        System.out.println(curFile.getPath()); 
			           	Map<String, Object> hmParam = new HashMap<String, Object>();    
			           	            	         	                      	
		            	String strFileNm = curFile.getName();
		            	
		            	Date d=new Date(curFile.lastModified());
		            	String strFileDt = d.toString();            	
		            	String strSize = String.valueOf(curFile.length()); 
		          		hmParam.put("file_nm", strFileNm);
		        		hmParam.put("file_date", strFileDt);
		        		hmParam.put("file_size", strSize);
		        		
		        		//ftp받은 자료인지 확인
		        		if(strFileNm.substring(0,1).equals("X")){
		        			hmParam.put("new_yn", "N");
		        		} else {
		        			hmParam.put("new_yn", "Y");        			
		        		}
		            	
		        		rtnDs.add(hmParam);   
			    	}
			    }
			               
	            mapOutDs.put("ds_output", rtnDs);
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 전송관련 FTP정보
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/getFtpList")
    public ModelAndView getHanaFtpList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	DataSetMap listInDs = new DataSetMap();        

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap rtnDs = new DataSetMap();    
                               
            final SftpManager SftpManager = new SftpManager();
            
            String sHost = "localhost";
            String sUserName = "790585";
            Integer sPort = 22;
            String sPW = "1111111111";
            String sFtpPath = "/ftp";
                        
    		// 접속
            SftpManager.init(sHost, sUserName, sPW, sPort, null );
            
            //파일여부
            //System.out.println("xxxxxxxx> : " + SftpManager.equals("aaa.rcv"));
            
          //파일 리스트            
            listInDs = SftpManager.FtpFileList(sFtpPath);
            
            if(listInDs.size() > 0){            	            
	            for(int i=0; i< listInDs.size(); i++){
	            	
	            	Map<String, Object> hmParam = new HashMap<String, Object>();            	                      	
	            	String strFileNm = listInDs.get(i).get("file_name").toString();
	            	String strFileDt = listInDs.get(i).get("file_info").toString();
	            	int strSize = strFileNm.length();
	            	
	            	//ftp에서 폴더는 제외함
	            	if(strFileNm.substring(strSize - 3).equals("rcv") || strFileNm.substring(strSize - 3).equals("ved") || strFileNm.substring(strSize - 3).equals("snd")){
	            		hmParam.put("file_nm", strFileNm);
	            		hmParam.put("file_date", strFileDt);
	            		
	            		//ftp받은 자료인지 확인
	            		if(strFileNm.substring(strSize - 3).equals("rcv")){
	            			hmParam.put("new_yn", "Y");
	            		} else {
	            			hmParam.put("new_yn", "N");
	            		}   
	            		rtnDs.add(hmParam);          
	            	}	            		            		            	  	
	            }
            }
            //FTP연결종료    
            SftpManager.disconnection();
            mapOutDs.put("ds_output", rtnDs);
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 재수신하기
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/fileReRecieve")
    public ModelAndView getFileReRecieve(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            Map<String, Object> inParam = new HashMap<String, Object>();                               
            
            if (listInDs.size() > 0)
            {
            	inParam = listInDs.get(0);
	            String strLocalPath = inParam.get("send_path").toString();
	            String strRecvGbn = mapInVar.get("recvGbn").toString();	           
	                        
	            /*#######################################
	             * 하나카드 발급정보 파일 데이터 리딩
	            ####################################### */
	            if(strRecvGbn.equals("S01")){
	            	listMap = saveHana001FileRecieve(strLocalPath);  //데일리 카드 발급 데이터 
	            } else {
	            	listMap = saveHana002FileRecieve(strLocalPath);  //데일리 카드 발급 데이터
	            }	            
	              	            
	            /*#######################################
	             * 하나카드 발급정보 데이터 등록
	            ####################################### */
	            if(listMap.size() > 0){
	            	saveHanaDataRecieve(listMap, strLocalPath);
	            }
            }
            mapOutVar.put("nTotalListCnt", listMap.size());
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 FTP에서 받은 발급신청DB저장
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    public DataSetMap saveHana001FileRecieve(String filePath) throws Exception {
    	
    	DataSetMap dataParam  = new DataSetMap();
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        
        try
        {    	   			
			File dir = new File(filePath);	        	           	        
			   
			File[] fileNameList = dir.listFiles(new FilenameFilter() { 			        
		        @Override 
		        public boolean accept(File dir, String name) { 
		             return name.startsWith("S01"); 
		        }
		    });
			           	        	        
		    for(File curFile : fileNameList) { 
		        System.out.println(curFile.getPath()); 

	            //BufferedReader reader = new BufferedReader(new FileReader(files[i]),,"UTF-8");        
	            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(curFile.getPath()), "EUC-KR"));
	            Map<String, Object> paramData = new HashMap<String, Object>(); //FILE 이력 MAP
	            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
	            
	            String str = ""; 						//한줄문자
	            String strFirstWord = "";   //첫글자 (H:헤더, D:데이터, T:TAIL)
	            String sInputDate = "";     // 전송일
	            String sInputTime = "";     // 전송시간 
	            int iLine = 0;					// 라인수
	            int iEndLind = 0;				// 최종라인수            
	            String strPhone = "";		// 전화번호
	            String strAccntNo = "";		// 회원번호
	            int iAmt = 0;					// 혜택금액
                            
	            /* ####################################################################################
	             * 받은 DATA 발급 DB등록
	             * #################################################################################### */    
            
	            while ((str = reader.readLine()) != null) {	            		            	
	            	Map<String, Object> pData = new HashMap<String, Object>(); //DATA이력 MAP
	            	strFirstWord = str.substring(0,1);            	
	            	switch(strFirstWord)
	            	{
		            	case "H" :
		            		System.out.println("in header");
		            		sInputDate = getString(str,17,8);
		            		sInputTime = getString(str,25,6);		            		
		                break;
		                
		            	case "D" :
		            		System.out.println("in data");	            			            		
		            		pData.put("card_seq", sInputDate + getString(str,5,6));		// 카드순번 
		            		pData.put("hana_no", getString(str,99,11).trim());		// 하나발급번호	
		            		pData.put("ci_val", getString(str,11,88));						// ci
		            		pData.put("card_no", getString(str,178,16).trim());		// 카드번호		
		            		pData.put("expire_dt", getString(str,194,6).trim());		// 유효일
		            		pData.put("mem_nm",getString(str,118,40).trim());		// 고객명
		            		pData.put("card_birth", getString(str,158,8).trim());		// 카드생년월일 
		            		pData.put("cell", getString(str,166,12).trim());				// 전화번호
		            		pData.put("card_dt", getString(str,110,8).trim());		// 카드발급일
		            		pData.put("card_stat", getString(str,200,1));		// 카드상태 (발급, 해지)
		            		pData.put("card_trans", getString(str,201,1));		// 자동이체
		            		pData.put("send_dt", sInputDate);					// 전송일
		            		pData.put("send_time", sInputTime);				// 전송시간	            		
		            		pData.put("reg_man",oLoginUser.getUserid());	// 등록자
		            		
		            		System.out.println("NAME: " + getString(str,118,40).trim());
		            		System.out.println("BIRTH: " + getString(str,158,8).trim());
		            		System.out.println("expire_dt: " + getString(str,194,16).trim());
		            		
		                	dlwHanaCardMngService.insertHanaMember(pData);  // 등록
		                break;
		                
		            	case "T" :
		            		System.out.println("in tail");
		            		iEndLind = Integer.parseInt(getString(str,11,10));
		            		System.out.println("iEndLind : " + iEndLind);
		                break;            	
	            	}         	                	
	            }             
	           reader.close();
	                    
	           /* ####################################################################################
	            * 받은 파일에 대한 이력 정보 전달 준비 
	            * #################################################################################### */              
	           
	           paramData.put("fileInfo",  "하나카드 발급 정보 수신데이터");           
	           paramData.put("fileNm",  curFile.getName());
	           paramData.put("rowCnt",  iEndLind);
	           paramData.put("sendDt",  sInputDate);
	           paramData.put("sendLoc", "IN");
	           paramData.put("sendStat", "99");
	                                    
	           dataParam.add(paramData);	           
	        }
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
    	return dataParam;
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 FTP에서 받은 전월실적 대상자 
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    public DataSetMap saveHana002FileRecieve(String filePath) throws Exception {
    	
    	DataSetMap dataParam  = new DataSetMap();
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        
        try
        {    	   			
			File dir = new File(filePath);	        	           	        
			   
			File[] fileNameList = dir.listFiles(new FilenameFilter() { 			        
		        @Override 
		        public boolean accept(File dir, String name) { 
		             return name.startsWith("S02"); 
		        }
		    });
			           	        	        
		    for(File curFile : fileNameList) { 
		        System.out.println(curFile.getPath()); 

	            //BufferedReader reader = new BufferedReader(new FileReader(files[i]),,"UTF-8");        
	            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(curFile.getPath()), "EUC-KR"));
	            Map<String, Object> paramData = new HashMap<String, Object>(); //FILE 이력 MAP
	            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
	            
	            String str = ""; 						//한줄문자
	            String strFirstWord = "";   //첫글자 (H:헤더, D:데이터, T:TAIL)
	            String sInputDate = "";     // 전송일
	            String sInputTime = "";
	            int iEndLind = 0;				// 최종라인수            
                            
	            /* ####################################################################################
	             * 받은 DATA 발급 DB등록
	             * #################################################################################### */    
            
	            while ((str = reader.readLine()) != null) {	            		            	
	            	Map<String, Object> pData = new HashMap<String, Object>(); //DATA이력 MAP
	            	strFirstWord = str.substring(0,1);            	
	            	switch(strFirstWord)
	            	{
		            	case "H" :
		            		System.out.println("in header");
		            		sInputDate = getString(str,17,8);
		            		sInputTime = getString(str,25,6);		            		
		                break;
		                
		            	case "D" :
		            		System.out.println("in data");	            			            		
		            		pData.put("card_seq", sInputDate + getString(str,5,6));						// 카드순번 		            		
		            		pData.put("ci_val", getString(str,11,88));											// ci
		            		pData.put("hana_no", getString(str,99,11).trim());								// 하나발급번호	
		            		pData.put("pay_mon", getString(str,110,6).trim());								// 할인기준월		
		            		pData.put("pay_amt", Integer.parseInt(getString(str,116,15).trim()));		// 선납할인금액 (하나머니)
		            				            		
		                	dlwHanaCardMngService.insertHanaPayments(pData);  // 등록
		                break;
		                
		            	case "T" :
		            		System.out.println("in tail");
		            		iEndLind = Integer.parseInt(getString(str,11,10));
		            		System.out.println("iEndLind : " + iEndLind);
		                break;            	
	            	}         	                	
	            }             
	           reader.close();
	                    
	           /* ####################################################################################
	            * 받은 파일에 대한 이력 정보 전달 준비 
	            * #################################################################################### */              
	           
	           paramData.put("fileInfo",  "하나카드 전월실적 정보 수신데이터");           
	           paramData.put("fileNm",  curFile.getName());
	           paramData.put("rowCnt",  iEndLind);
	           paramData.put("sendDt",  sInputDate);
	           paramData.put("sendLoc", "IN");
	           paramData.put("sendStat", "00");
	                                    
	           dataParam.add(paramData);	           
	        }
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
    	return dataParam;
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드에서 전송된 데이터 DB등록
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    public DataSetMap saveHanaDataRecieve(DataSetMap pDs, String strLocalPath) throws Exception {
    	DataSetMap dataParam  = new DataSetMap();
    	Map<String, Object> hmParam = new HashMap<String, Object>(); //DATA이력 MAP
    	UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
    	  //하나카드 발급정보 이력 저장
        for(int i = 0; i<pDs.size(); i++){               	
        	hmParam.put("send_dt",pDs.get(i).get("sendDt"));
        	hmParam.put("file_nm",pDs.get(i).get("fileNm"));
        	hmParam.put("send_loc",pDs.get(i).get("sendLoc"));
        	hmParam.put("send_stat",pDs.get(i).get("sendStat"));
        	hmParam.put("file_info",pDs.get(i).get("fileInfo"));
        	hmParam.put("row_cnt",pDs.get(i).get("rowCnt"));
        	hmParam.put("pay_mon",pDs.get(i).get("sendDt").toString().substring(0, 6));
        	hmParam.put("reg_man",oLoginUser.getUserid());	// 등록자
                            	                                	            	            	            	            
        	dlwHanaCardMngService.insertHanaRecieveHistory(hmParam);  // 등록
        	
        	// 수신파일명 변경 -> 결과 받은 파일을 변경해서 추가 갱신이 안되도록 처리
            File file = new File(strLocalPath + pDs.getMapValue(i,"fileNm"));
            File newFile = new File(strLocalPath + "X" +pDs.getMapValue(i,"fileNm"));
                            
            file.renameTo(newFile);
        }     	
    	return dataParam;
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드FTP에서 파일가져오기(신규파일만)
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/ftpFileRecieve")
    public ModelAndView getFtpFileRecieve(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");    	

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
        	Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            
            String sLocalPath = "/sftp_home/hanacard/send/";
            String strFileNm = mapInVar.get("file_nm").toString();
            
            ftpManager("IN", sLocalPath, strFileNm);
        	
            String rtnMsg = "";
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드발급회원정보
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     */
    @RequestMapping(value = "/getHanaMemInfo")
    public ModelAndView getHanaMemList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                String strSendDt = hmParam.get("send_dt").toString().substring(0,6);
                hmParam.put("send_dt", strSendDt);
                
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

                int nTotal = dlwHanaCardMngService.getHanaMemCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = dlwHanaCardMngService.getHanaMemList(hmParam);                
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드혜택정보관리
     * 대상 테이블 : LF_DMUSER.TB_HANA_PAYMENTS
     * ================================================================
     */
    @RequestMapping(value = "/getHanaPayInfo")
    public ModelAndView getHanaPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                String strSendDt = hmParam.get("send_dt").toString().substring(0,6);
                hmParam.put("send_dt", strSendDt);
                
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
                
                // 하나혜택(선납)자 조회 카운트
                int nTotal = dlwHanaCardMngService.getHanaPayCount(hmParam);                
                
                // 하나혜택(선납)자 조회 리스트
                List<Map<String, Object>> mList = dlwHanaCardMngService.getHanaPayList(hmParam);                
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                
               // 하나혜택(선납) 현 상태값
                String strHanaStat = dlwHanaCardMngService.getHanaPayProcessStat(hmParam);
                
                //플랫폼 전송
                mapOutVar.put("nTotalListCnt", nTotal);
                mapOutVar.put("strProcStat", strHanaStat);
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 대명에서 하나카드로 결과값 전송 (첫번째 선납대상)
     * 대상 테이블 : LF_DMUSER.TB_HANA_PAYMENTS
     * ================================================================
     */
    @RequestMapping(value = "/sendPayFtp")
    public ModelAndView setHanaFileCreate(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            String fileName ="";	//전송 파일명
            String strSendDt = "";    
            StringBuilder strbuf;
            int totCnt = 0;
            String sLocalPath = "C:\\app\\LGUPLUS\\";
            //String sLocalPath = "/sftp_home/hanacard/send/";
            
            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                
                strSendDt = hmParam.get("send_dt").toString().substring(0,6);
                totCnt = Integer.parseInt(hmParam.get("tot_cnt").toString());
                hmParam.put("send_dt", strSendDt);
            }
            
            if (totCnt <= 0) {       
            	 throw new Exception("대상정보가 없습니다.[IT 문의 바랍니다.]");
            }
                                  	            	                                               
            /**############################################################################################
             * 하나카드 선납혜택 첫번째 파일생성             
             * ############################################################################################*/
            
            // 날짜형식가져오기            ​
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
            
            //오늘날짜 및 시간 가져오기
            Date now = new Date();            
            String nowDate = sdf1.format(now);
            String nowTime = sdf2.format(now);
            
            fileName = (new StringBuilder("DAEMB03_HACPD")).append("_").append(nowDate).toString();

            //File f = new File((new StringBuilder("/sftp_home/inicis/send/")).toString(), fileName);  	//운영
            File localFile = new File((new StringBuilder(sLocalPath)).toString(), fileName); 	//로컬

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localFile), "EUC-KR"));

            strbuf = new StringBuilder();
            strbuf.append("H").append("0000000000").append("DAE").append("HAC").append(nowDate).append(nowTime).append(CommonUtils.fillEmpVal(269,"", "L", " ")); // 파일의 1번째행은 헤더로서 "H,YYYYMMDD,건수" 의 형태를 가진다
            
            strbuf.append("\r\n");
            out.write(strbuf.toString());
            strbuf.delete(0, strbuf.length());
            	            
            // 하나카드 선납대상자 조회
            List<Map<String, Object>> mList = dlwHanaCardMngService.getHanaPayList(hmParam);
            Map<String, Object> pmParam = new HashMap<String, Object>();
            	            
            for(int i = 0; mList.size() > i; i++){	            			            
                pmParam = mList.get(i);
                String strPaySeq = (pmParam.get("pay_seq").toString().substring(8, 14));
                	                
                strbuf.append("D").append(CommonUtils.fillEmpVal(10,strPaySeq, "R", "0")).append(pmParam.get("ci_val")).append(pmParam.get("hana_no")).append(pmParam.get("pay_mon"));
                strbuf.append(CommonUtils.fillEmpVal(15,pmParam.get("pay_amt").toString(), "R", "0")).append(pmParam.get("target_yn")).append(CommonUtils.fillEmpVal(168,"", "L", " "));
                
                strbuf.append("\r\n");
                out.write(strbuf.toString());
                strbuf.delete(0, strbuf.length());
                
                if(i % 100 == 0)
                {
                    out.flush();
                }
            }
            	            
            strbuf.append("T").append("9999999999").append(CommonUtils.fillEmpVal(10,Integer.toString(totCnt), "R", "0")).append(CommonUtils.fillEmpVal(279,"", "L", " "));            
            out.write(strbuf.toString());            
            out.close();	   
            
            /**############################################################################################
             * 하나카드 선납혜택 여부(1st) 히스토리 저장           
             * ############################################################################################*/
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        	hmParam.put("send_dt",nowDate);
        	hmParam.put("pay_mon",strSendDt);
        	hmParam.put("file_nm", fileName);
        	hmParam.put("send_loc","OUT");
        	hmParam.put("send_stat","00");
        	hmParam.put("file_info", "하나카드 혜택(선납) 정보 발신 데이터");
        	hmParam.put("row_cnt",totCnt);
        	hmParam.put("reg_man",oLoginUser.getUserid());	// 등록자
                        	            	
        	dlwHanaCardMngService.insertHanaRecieveHistory(hmParam);  // 등록
            
            /*
            File dir = new File(sLocalPath);
            FileFilter filter = new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().startsWith("HAC");
                }
            };

            File files[] = dir.listFiles(filter);            
            int cntFiles = files.length;            
            if (cntFiles <= 0) {       
            	throw new Exception("대상파일이 삭제되거나 없습니다.[IT 문의 바랍니다.]");
            }

            
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            
            for(int i=0; i<files.length; i++){
            	
            	//ftpManager("OUT", sLocalPath,  files[i].getName());  //FTP전송
            	
            	hmParam.put("send_dt",nowDate);
            	hmParam.put("pay_mon",strSendDt);
            	hmParam.put("file_nm",files[i].getName());
            	hmParam.put("send_loc","OUT");
            	hmParam.put("send_stat","00");
            	hmParam.put("file_info", "하나카드 혜택(선납) 정보 발신 데이터");
            	hmParam.put("row_cnt",totCnt);
            	hmParam.put("reg_man",oLoginUser.getUserid());	// 등록자
                            	            	
            	dlwHanaCardMngService.insertHanaRecieveHistory(hmParam);  // 등록
            }
            */                        	        
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
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : FTP관리 프로세스
     * ================================================================
     */
    public void ftpManager(String pmLoc, String pmlocalPath,  String pmFileNm) throws Exception {
    	
        final SftpManager SftpManager = new SftpManager();
        
        String sHost = "localhost";
        String sUserName = "790585";
        Integer sPort = 22;
        String sPW = "1111111111";
        String sFtpPath = "/ftp";
        String sLocalPath = pmlocalPath;
        String strFileNm = pmFileNm;
        String rtnMsg = "";
        
		// 접속
        SftpManager.init(sHost, sUserName, sPW, sPort, null );
    	
        // IN : [FTP -> LOCAL]
        if (pmLoc == "IN"){        	                
	        //파일여부
	        if(SftpManager.exists(sFtpPath, strFileNm)){
	        	//파일다운로드 
	        	SftpManager.FtpDownload(sFtpPath, strFileNm, sLocalPath + strFileNm);
	        	
	        	//다운로드 받은 파일명 변경하기 (파일명.rec -> 파일명.recved
	        	SftpManager.FtpFileRename (sFtpPath, strFileNm, strFileNm + "ed");            	
	        } 
        } else {
            // IN : [LOCAL -> FTP]
        	
        	// 파일 넘겨 받고 
            File pmFile = new File(pmlocalPath + pmFileNm);      
            
        	//FTP 전송
        	SftpManager.FtpUpload(sFtpPath,  pmFile);
        	
        	// 발송파일명 변경 -> 결과 받은 파일을 변경해서 추가 갱신이 안되도록 처리            
            File newFile = new File(pmlocalPath + "X" +pmFileNm);                           
            pmFile.renameTo(newFile);
        }
        
        //FTP연결종료    
        SftpManager.disconnection();        
    }
    

	/**
	 * 문자열을 바이트 단위로 substring하기
	 *
	 * new String(str.getBytes(), 0, endBytes) 코드를 사용하면
	 * 한글 바이트에 딱 맞춰 자르지 않는 경우 깨지는 문제가 있어서 따로 메서드를 만들었다.
	 *
	 * UTF-8 기준 한글은 3바이트, 알파벳 대소문자나 숫자 및 띄어쓰기는 1바이트로 계산된다.
	 *
	 */
	
	public  String getString(String str, int sPoint, int length) throws Exception{
	    String EncodingLang = "euc-kr";
	    byte[] bytes = str.getBytes("euc-kr");

	    byte[] value = new byte[length];

	    if(bytes.length < sPoint + length){
	        throw new Exception("Length of bytes is less. length : " + bytes.length + " sPoint : " + sPoint + " length : " + length);
	    }

	    for(int i = 0; i < length; i++){
	        value[i] = bytes[sPoint + i];
	    }

	    return new String(value, EncodingLang).trim();
	}

}