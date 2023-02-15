package powerservice.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import egovframework.com.cmm.service.EgovProperties;

/**
 * Nice 평가정보 신용등급 조회 관련 유틸 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2009. 4. 8.
 * @프로그램ID <<프로그램 아이디>>
 */

public class NiceCreditUtil {
	
    /**
     * 애큐원 연동을 위해 필요한 신용평가 등급을 가졌는지 검사
     *
     * @param lSyGubun_RK0400_000
     *            CB스코어(등급)
     * @param lSyGubun_RK0400_700
     *            서브프라임 스코어(등급)
     * @param mapSumItem
     *            채울 문자
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */
    public static boolean hasNiceCreditForAquon(long lSyGubun_RK0400_000, long lSyScore_RK0400_000, Map<String, Object> mapSumItem)
    {	
    	boolean bRet = false;
    	
    	String sB12000200 = CommonUtils.nvls((String)mapSumItem.get("B12000200")); // (신용관리대상/공공정보)미해제등록 총 건수 1건이상    	
    	if ( !"".equals(sB12000200) && NiceCreditUtil.parseInt(sB12000200) > 0) {
    		return bRet;
    	}
    	
    	String sB22000200 = CommonUtils.nvls((String)mapSumItem.get("B22000200")); // (채무불이행-신용정보사)미해제등록 총 건수 1건이상
    	if ( !"".equals(sB22000200) && NiceCreditUtil.parseInt(sB22000200) > 0) {
    		return bRet;
    	}
    	
    	String sBE0000020 = CommonUtils.nvls((String)mapSumItem.get("BE0000020")); // (채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상
    	if ( !"".equals(sBE0000020) && NiceCreditUtil.parseInt(sBE0000020) > 0) {
    		return bRet;
    	}
    	
    	String sBS0000051 = CommonUtils.nvls((String)mapSumItem.get("BS0000051")); // 희망모아 신용회복지원 대상 구분 1,2
    	sBS0000051 = String.valueOf(NiceCreditUtil.parseInt(sBS0000051));
    	if ( "1".equals(sBS0000051) || "2".equals(sBS0000051) ) {
    		return bRet;
    	}
    	
    	String sBS0000784 = CommonUtils.nvls((String)mapSumItem.get("BS0000784")); // 상록수 신용회복지원 구분 1,2
    	sBS0000784 = String.valueOf(NiceCreditUtil.parseInt(sBS0000784));
    	if ( "1".equals(sBS0000784) || "2".equals(sBS0000784) ) {
    		return bRet;
    	}
    	
    	String sCRT000005 = CommonUtils.nvls((String)mapSumItem.get("CRT000005")); // 파산면책 등록 유무 1
    	sCRT000005 = String.valueOf(NiceCreditUtil.parseInt(sCRT000005));
    	if ( "1".equals(sCRT000005) ) {
    		return bRet;
    	}
    	
    	String sCRT000006 = CommonUtils.nvls((String)mapSumItem.get("CRT000006")); // 개인회생 등록 유무 1
    	sCRT000006 = String.valueOf(NiceCreditUtil.parseInt(sCRT000006));
    	if ( "1".equals(sCRT000006) ) {
    		return bRet;
    	}
    	
    	String sCRT000009 = CommonUtils.nvls((String)mapSumItem.get("CRT000009")); // 실종공시최고 유무 1
    	sCRT000009 = String.valueOf(NiceCreditUtil.parseInt(sCRT000009));
    	if ( "1".equals(sCRT000009) ) {
    		return bRet;
    	}
    	
    	String sCRT000010 = CommonUtils.nvls((String)mapSumItem.get("CRT000010")); // 실종선고 유무 1
    	sCRT000010 = String.valueOf(NiceCreditUtil.parseInt(sCRT000010));
    	if ( "1".equals(sCRT000010) ) {
    		return bRet;
    	}
    	
    	String sCRT000012 = CommonUtils.nvls((String)mapSumItem.get("CRT000012")); // 부재선고 유무 1
    	sCRT000012 = String.valueOf(NiceCreditUtil.parseInt(sCRT000012));
    	if ( "1".equals(sCRT000012) ) {
    		return bRet;
    	}
    	
    	String sCRT000013 = CommonUtils.nvls((String)mapSumItem.get("CRT000013")); // 금치산선고 유무 1
    	sCRT000013 = String.valueOf(NiceCreditUtil.parseInt(sCRT000013));
    	if ( "1".equals(sCRT000013) ) {
    		return bRet;
    	}
    	
    	String sCRT000015 = CommonUtils.nvls((String)mapSumItem.get("CRT000015")); // 한정치산선고 유무 1
    	sCRT000015 = String.valueOf(NiceCreditUtil.parseInt(sCRT000015));
    	if ( "1".equals(sCRT000015) ) {
    		return bRet;
    	}
    	
    	String sCRT000017 = CommonUtils.nvls((String)mapSumItem.get("CRT000017")); // 국적상실 유무 1
    	sCRT000017 = String.valueOf(NiceCreditUtil.parseInt(sCRT000017));
    	if ( "1".equals(sCRT000017) ) {
    		return bRet;
    	}
    	
    	String sCRT000019 = CommonUtils.nvls((String)mapSumItem.get("CRT000019")); // 국적이탈 유무 1
    	sCRT000019 = String.valueOf(NiceCreditUtil.parseInt(sCRT000019));
    	if ( "1".equals(sCRT000019) ) {
    		return bRet;
    	}
    	
    	String sCRT000023 = CommonUtils.nvls((String)mapSumItem.get("CRT000023")); // 파산선고 유무 1
    	sCRT000023 = String.valueOf(NiceCreditUtil.parseInt(sCRT000023));
    	if ( "1".equals(sCRT000023) ) {
    		return bRet;
    	}
    	
    	String sCRT000025 = CommonUtils.nvls((String)mapSumItem.get("CRT000025")); // 면책선고 유무 1
    	sCRT000025 = String.valueOf(NiceCreditUtil.parseInt(sCRT000025));
    	if ( "1".equals(sCRT000025) ) {
    		return bRet;
    	}
    	
    	String sCRT000027 = CommonUtils.nvls((String)mapSumItem.get("CRT000027")); // 개인회생개시 유무 1
    	sCRT000027 = String.valueOf(NiceCreditUtil.parseInt(sCRT000027));
    	if ( "1".equals(sCRT000027) ) {
    		return bRet;
    	}
    	
    	String sAS0000138 = CommonUtils.nvls((String)mapSumItem.get("AS0000138")); // 외국인 구분(0:외국인, 1:내국인) 0
    	sAS0000138 = String.valueOf(NiceCreditUtil.parseInt(sAS0000138));
    	if ( "0".equals(sAS0000138) ) {
    		return bRet;
    	}
    	
    	if ( lSyScore_RK0400_000 >= 650 ) { // CB스코어 평점
			return true;
		}
    	
    	/*
    	if ( lSyGubun_RK0400_000 >= 1 && lSyGubun_RK0400_000 <= 6 ) { // CB스코어 등급
			return true;
		}
		
		if ( 7 == lSyGubun_RK0400_000 ) { // CB스코어 등급
			if ( lSyGubun_RK0400_700 >= 1 && lSyGubun_RK0400_700 <= 4 ) { // 서브프라임 스코어
				return true;
			}
		}
    	 * */
   
    	return false;
    }
    
    public static String requestReplace (String paramValue, String gubun) {
        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}
        	
        	result = paramValue;
            
        }
        return result;
    }
    
    public static String getNiceCreditSumSrvItemNm (String sCode) {
        
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("B12000200", "(신용관리대상/공공정보)미해제등록 총 건수 1건이상");
        hm.put("B22000200", "(채무불이행-신용정보사)미해제등록 총 건수 1건이상");
        hm.put("BE0000020", "(채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상");
        hm.put("BS0000051", "희망모아 신용회복지원 대상 구분 1,2");
        hm.put("BS0000784", "상록수 신용회복지원 구분 1,2");
        hm.put("CRT000005", "파산면책 등록 유무 1");
        hm.put("CRT000006", "개인회생 등록 유무 1");
        hm.put("CRT000009", "실종공시최고 유무 1");
        hm.put("CRT000010", "실종선고 유무 1");
        hm.put("CRT000012", "부재선고 유무 1");
        hm.put("CRT000013", "금치산선고 유무 1");
        hm.put("CRT000015", "한정치산선고 유무 1");
        hm.put("CRT000017", "국적상실 유무 1");
        hm.put("CRT000019", "국적이탈 유무 1");
        hm.put("CRT000023", "파산선고 유무 1");
        hm.put("CRT000025", "면책선고 유무 1");
        hm.put("CRT000027", "개인회생개시 유무 1");
        hm.put("AS0000138", "외국인 구분(0:외국인, 1:내국인) 0");
        
        String sCodeNm = hm.get(sCode);
        if (null == sCodeNm) {
        	sCodeNm = "";
        }
        
        return sCodeNm;
    }
    
    public static String parseNiceCreditSumSrvItemVal (String sCode, String sVal) {
    	
        String sRet = "";
        
    	if ("B12000200".equals(sCode)) { // (신용관리대상/공공정보)미해제등록 총 건수 1건이상    		            
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("B22000200".equals(sCode)) { // (채무불이행-신용정보사)미해제등록 총 건수 1건이상
    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("BE0000020".equals(sCode)) { // (채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상}    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("BS0000051".equals(sCode)) { // 희망모아 신용회복지원 대상 구분 1,2    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("BS0000784".equals(sCode)) { // 상록수 신용회복지원 구분 1,2    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000005".equals(sCode)) { // 파산면책 등록 유무 1    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000006".equals(sCode)) { // 개인회생 등록 유무 1                           		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000009".equals(sCode)) { // 실종공시최고 유무 1    			
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000010".equals(sCode)) { // 실종선고 유무 1                            			
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000012".equals(sCode)) { // 부재선고 유무 1                            			
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000013".equals(sCode)) { // 금치산선고 유무 1                              		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000015".equals(sCode)) { // 한정치산선고 유무 1                            		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000017".equals(sCode)) { // 국적상실 유무 1                                		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000019".equals(sCode)) { // 국적이탈 유무 1                                		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000023".equals(sCode)) { // 파산선고 유무 1              		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000025".equals(sCode)) { // 면책선고 유무 1                                		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("CRT000027".equals(sCode)) { // 개인회생개시 유무 1          		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
    	if ("AS0000138".equals(sCode)) { // 외국인 구분(0:외국인, 1:내국인) 0    		
    		sRet = String.valueOf(Integer.parseInt(sVal));
    	}
        
        return sRet;
    }
    
    public static void setNiceCommonRequest(String trGbnCd, String sCurrDthms, String[] sArrData) throws Exception {
    	
    	if (sArrData.length < 14 || sCurrDthms.length() != 14) {
    		throw new Exception("요청전문 공통부가 잘못되었습니다.");
    	}
    	
    	sArrData[ 0] = CommonUtils.lpad("000", 10, "0")			; // Transaction code
    	sArrData[ 1] = CommonUtils.rpad("NICEIF", 9, " ")		; // 전문그룹코드                
    	sArrData[ 2] = "0200"									; // 거래종별코드
    	sArrData[ 3] = trGbnCd									; // 거래구분코드
    	sArrData[ 4] = "B"										; // 송수신Flg
    	sArrData[ 5] = "503"									; // 단말기구분
    	sArrData[ 6] = CommonUtils.lpad("", 4, " ")				; // 응답코드                
    	sArrData[ 7] = CommonUtils.rpad("DMYUNG02", 9, " ")		; // UserID            
    	
    	sArrData[ 8] = CommonUtils.lpad("0023454001", 10, " ")	; // 기관전문 관리번호
    	
    	sArrData[ 9] = sCurrDthms								; // 기관전문 전송시간
    	sArrData[10] = CommonUtils.lpad("", 10, " ")			; // NICE 전문 관리번호
    	sArrData[11] = CommonUtils.lpad("", 14, " ")			; // NICE 전문 전송시간
    	
    	if ("1F003".equals(trGbnCd)) { // 신용등급조회
    		sArrData[12] = CommonUtils.lpad("F500000004000005", 16, " ")	; // Primary Bitmap
    	} else {
    		sArrData[12] = CommonUtils.lpad("", 16, " ")					; // 공란
    	}
    	
    	// 조회동의사유
    	if ("1F003".equals(trGbnCd)) { // 신용등급조회
    		sArrData[13] = CommonUtils.lpad("1", 1, " ");
    	} else {
    		sArrData[13] = CommonUtils.lpad("", 1, " "); 
    	}
    	
    	
//    	for (int i=0; i<14; ++i) {
//    		System.out.println("공통부 길이 : <" + i + "> : " + sArrData[i].getBytes().length);
//    	}
    }
    
    /**
     * 전문의 Transaction code 값을 설정한다.
     *
     * @param sReqData
     *            요청전문
     * @return 요청전문 - 변경된 Transaction code를 포함한 전문 
     */
    public static String setTrCd(String sReqData) throws Exception {
    	int len = sReqData.length() - 10;
    	String sExt = sReqData.substring(10);
    	sReqData = CommonUtils.lpad(String.valueOf(len), 10, "0") + sExt;
    	return sReqData;
    }
    
    public static Map<String, Object> getSafeKeySmsIssueResult(Map<String, Object> hmParam) {
    	
    	String sSafekey 		= ""; // Safe-Key    	
    	String sResCode 		= ""; // 요청결과 - Safe-Key 발급과 신용정보 조회 결과
    	String sIssueDthms 		= ""; // Safe-Key 발급일시
    	String sAuthRsltCode	= "";    	
    	String sAuthType		= "";    	
    	
    	Map<String, Object> hmResult = null;
    	
    	Socket socket = null;
    	BufferedWriter bw = null;
    	
    	String sSafeKeySrvIp 	= EgovProperties.getProperty("nice.safekey.server.ip");
    	int iSafeKeySrvPort  	= Integer.valueOf(EgovProperties.getProperty("nice.safekey.server.port"));
    	String sSafeKeySrvType	= EgovProperties.getProperty("nice.safekey.server.type");
    	
    	if (null != sSafeKeySrvType && "test".equals(sSafeKeySrvType)) {
        	sSafeKeySrvIp 	= "192.168.15.167"; // 개발자 PC
        	iSafeKeySrvPort = 51096; 
    	}
        
    	
    	String sCurrDthms 	= DateUtil.getCurrDthms();
		String sMemNm 		= CommonUtils.nvls((String)hmParam.get("mem_nm"));
		String sSex 		= CommonUtils.nvls((String)hmParam.get("sex"));
		String sCell 		= CommonUtils.nvls((String)hmParam.get("cell"));
		String sBrthMonDay 	= CommonUtils.nvls((String)hmParam.get("brth_mon_day"));
		String sReqDthms 	= CommonUtils.nvls((String)hmParam.get("req_dthms"));
		String sReqSeq		= CommonUtils.nvls((String)hmParam.get("req_seq"));
		
		if ("".equals(sMemNm) || "".equals(sSex) || "".equals(sCell) || "".equals(sBrthMonDay) || "".equals(sReqSeq)) {
			return null;
		}
		
		
		System.out.println("--------------- 1");
		
		ArrayList<String> lstRecvData = new ArrayList<String>();
		
		try {
			CommonUtils.printLog(hmParam);
			
			String sArrData[] = new String[18];
			
			// 요청전문의 공통부를 설정한다.
	    	NiceCreditUtil.setNiceCommonRequest("76011", sCurrDthms, sArrData);
	    	
	    	sArrData[14] = "1"; 								// 개인구분 "1"로 고정
	    	sArrData[15] = sReqDthms.substring(0,8); 			// 요청일자 - 선택 입력사항
	    	sArrData[16] = sReqSeq; 							// 요청고유번호
	    	sArrData[17] = CommonUtils.lpad("", 61, " "); 		// 공란
	    	
	    	StringBuffer sbReqData = new StringBuffer();
	    	for (int j=0; j<sArrData.length; ++j) {
	           	sbReqData.append(sArrData[j]);
	        }
	    	
	    	System.out.println("--------------- 2");
	    	
	    	System.out.println("sSafeKeySrvIp : " + sSafeKeySrvIp);
	    	System.out.println("iSafeKeySrvPort : " + iSafeKeySrvPort);
	    	
	    	try {
	            socket = new Socket(sSafeKeySrvIp, iSafeKeySrvPort);
	    	}
	        catch(IOException e)
	        {
		        e.printStackTrace();                    
		        throw new Exception("Safe-Key 수신을 위한 연결에 오류가 있습니다.");
	        }
	    	
	    	bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "EUC-KR"));		
			bw.write( NiceCreditUtil.setTrCd(sbReqData.toString()) );
			
			lstRecvData.add(">요청데이터 : " + NiceCreditUtil.setTrCd(sbReqData.toString()));
			
			String recvRecord = "";
			
			
			System.out.println("--------------- 3");
			
			if(socket.isBound())
	        {
				bw.flush();
				
				System.out.println("--------------- 4");
	                
				InputStream in = null;
				byte buffer[] = new byte[10000];
	                
				in = socket.getInputStream();
	                
				int bytesRead = in.read(buffer);
	                
				lstRecvData.add("> bytesRead : " + bytesRead); 
				socket.close();
				bw.close();				
				
				if (bytesRead < 1) {
					return null;
				}
				
				System.out.println("--------------- 5");
				
				recvRecord = new String(buffer, 0, bytesRead);
	                
				lstRecvData.add("> recvRecord : " + recvRecord);
				lstRecvData.add("> recvRecord.length() : " + recvRecord.length());
	                
				ByteDataReader bdr = new ByteDataReader(buffer, bytesRead);
				bdr.setTrim(true); // 값을 읽을때 trim 할지 여부
	                
				lstRecvData.add("Transaction Code : " 		+ bdr.get(10)	);
				lstRecvData.add("전문그룹코드 : " 			+ bdr.get(9)	);
				lstRecvData.add("거래종별코드 : " 			+ bdr.get(4)	);
				lstRecvData.add("거래구분코드 : " 			+ bdr.get(5)	);
				lstRecvData.add("송수신Flag : " 			+ bdr.get(1)	);
				lstRecvData.add("단말기구분 : " 			+ bdr.get(3)	);
	                
				sResCode = bdr.get(4);
				lstRecvData.add("응답코드 : " 				+ sResCode);
				
				lstRecvData.add("User ID : " 				+ bdr.get(9)	);
				lstRecvData.add("기관전문 관리번호 : " 		+ bdr.get(10)	);
				lstRecvData.add("기관전문 전송시간 : " 		+ bdr.get(14)	);
				lstRecvData.add("NICE 전문 관리번호 : " 	+ bdr.get(10)	);
				lstRecvData.add("NICE 전문 전송시간 : " 	+ bdr.get(14)	);
				lstRecvData.add("공란 : " 					+ bdr.get(16)	);
				lstRecvData.add("조회동의사유 : " 			+ bdr.get(1)	);
				
				lstRecvData.add("개인구분 : " 				+ bdr.get(1)	);
				lstRecvData.add("요청일자 : " 				+ bdr.get(8)	);
				lstRecvData.add("요청고유번호 : " 			+ bdr.get(30)	);
				
				lstRecvData.add("생년월일 : " 				+ bdr.get(8)		);
				lstRecvData.add("성별 : " 					+ bdr.get(1)		);
				lstRecvData.add("성명 : " 					+ bdr.get(40)		);
				lstRecvData.add("휴대폰번호 : " 			+ bdr.get(11)		);
				lstRecvData.add("요청일시 : " 				+ bdr.get(14)		);
				
				sAuthRsltCode = bdr.get(4);
				lstRecvData.add("인증결과 : " 				+ sAuthRsltCode			); // 0000=정상
				
				sSafekey = bdr.get(13);
				lstRecvData.add("Safe-Key : " 				+ sSafekey			);
				
				sAuthType = bdr.get(1);
				lstRecvData.add("인증구분 : " 				+ sAuthType			);
				
				lstRecvData.add("동의구분1 : " 				+ bdr.get(10)		);
				lstRecvData.add("동의구분2 : " 				+ bdr.get(10)		);
				lstRecvData.add("동의구분3 : " 				+ bdr.get(10)		);
				
				sIssueDthms = bdr.get(14);
				lstRecvData.add("발급일시 : " 				+ sIssueDthms		);
				
				lstRecvData.add("CI : " 					+ bdr.get(88)		);
				lstRecvData.add("이메일 : " 				+ bdr.get(100)		);
				lstRecvData.add("동의구분4 : " 				+ bdr.get(10)		);
				lstRecvData.add("공란 : " 					+ bdr.get(27)		);
				System.out.println("--------------- 6");
				
				for (int i=0; i<lstRecvData.size(); ++i) {
					System.out.println(lstRecvData.get(i).toString());
				}
	                
	        } else {
	        	System.out.println("--------------- 7");
	        	
	        	//throw new Exception("Safe-Key 발급 서버로부터 응답을 받지 못했습니다.");
	        	System.out.println("Safe-Key 발급 서버로부터 응답을 받지 못했습니다.");
	        }
			
			System.out.println("--------------- 8");
			
			if ( !"".equals(sAuthRsltCode) ) 
			{	
				hmResult = new HashMap<String, Object>();				
				hmResult.put("safekey"			, sSafekey);
				hmResult.put("issue_dthms"		, sIssueDthms);
				hmResult.put("return_code"		, sAuthRsltCode);
				hmResult.put("req_dthms"		, sReqDthms);
				hmResult.put("req_seq"			, sReqSeq);
				
				hmResult.put("auth_type"		, sAuthType);
				hmResult.put("auth_result_code"	, sAuthRsltCode);
			}
			
			System.out.println("--------------- 9");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return hmResult;
		
    }
    
    public static Map<String, Object> getNiceSafeKey(Map<String, ?> pmParam) {
    	
    	Map<String, Object> hmResult = new HashMap<String, Object>();
    	
    	String sSafekey 	= ""; // Safe-Key    	
    	String sResCode		= ""; // 요청결과
    	
    	Socket socket 		= null;
    	BufferedWriter bw 	= null;
    	
    	ArrayList<String> lstRecvData = new ArrayList<String>();
    	
    	String sSafeKeySrvIp 	= EgovProperties.getProperty("nice.safekey.server.ip");
    	int iSafeKeySrvPort  	= Integer.valueOf(EgovProperties.getProperty("nice.safekey.server.port"));
    	String sSafeKeySrvType	= EgovProperties.getProperty("nice.safekey.server.type");
    	
    	if (null != sSafeKeySrvType && "test".equals(sSafeKeySrvType)) {
        	sSafeKeySrvIp 	= "192.168.15.167"; // 개발자 PC
        	iSafeKeySrvPort = 51096; 
        }
    	
    	lstRecvData.add("> NiceCreditUtil.getNiceSafekey called..");
    	
    	try {
    		
    		System.out.println("------------- 파라미터 -----------------");
        	CommonUtils.printLog(pmParam);
        	
        	String sCell 		= CommonUtils.nvls((String) pmParam.get("cell")).replaceAll("-", "");
        	String sBrthMonDay 	= CommonUtils.nvls((String) pmParam.get("brth_mon_day"));
        	String sSex 		= CommonUtils.nvls((String) pmParam.get("sex"));
        	String sMemNm 		= CommonUtils.nvls((String) pmParam.get("mem_nm"));
        	String sAccntNo 	= CommonUtils.nvls((String) pmParam.get("accnt_no")); // 회원번호
        	String sMemNo 		= CommonUtils.nvls((String) pmParam.get("mem_no")); // 고유번호
        	    
        	if ("0001".equals(sSex)) sSex = "1";
        	else sSex = "0";
                
        	lstRecvData.add("sAccntNo : " + sAccntNo);
        	
    		String sCurrDthms = DateUtil.getCurrDthms();
            
        	String sArrData[] = new String[19];    	

        	// 요청전문의 공통부를 설정한다.
        	NiceCreditUtil.setNiceCommonRequest("76030", sCurrDthms, sArrData);
        	
        	sArrData[14] = sBrthMonDay								; // 생년월일
        	sArrData[15] = CommonUtils.utf8ToEuckr(sMemNm, 40);
        	lstRecvData.add("-*-sArrData[15] : " + sArrData[15]);
        	
        	sArrData[16] = CommonUtils.lpad(sCell, 11, " ")			; // 휴대폰번호
        	sArrData[17] = sSex										; // 성별
        	sArrData[18] = CommonUtils.lpad("", 90, " ")			; // 공란
                
        	StringBuffer sbReqData = new StringBuffer();
        	for (int i=0; i<sArrData.length; ++i) {
               	sbReqData.append(sArrData[i]);
            }
                
        	try {
        		socket = new Socket(sSafeKeySrvIp, iSafeKeySrvPort);
        	}
            catch(IOException e)
            {
            	System.out.println("Safe-Key 수신을 위한 연결에 오류가 있습니다.");
            	System.out.println(e.getMessage());
    	        e.printStackTrace();
    	        return null;
            }
                
    		bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "EUC-KR"));		
    		bw.write( NiceCreditUtil.setTrCd(sbReqData.toString()) );
    		            
    		String recvRecord = "";
                
    		if(socket.isBound())
            {
                	
    			bw.flush();
                    
    			InputStream in = null;
    			byte buffer[] = new byte[10000];
                    
    			in = socket.getInputStream();
                    
    			int bytesRead = in.read(buffer);
                    
    			lstRecvData.add("> bytesRead : " + bytesRead); 
    			socket.close();
    			bw.close();
    			
    			recvRecord = new String(buffer, 0, bytesRead);
                    
    			lstRecvData.add("> recvRecord : " + recvRecord);
    			lstRecvData.add("> recvRecord.length() : " + recvRecord.length());
                    
    			ByteDataReader bdr = new ByteDataReader(buffer, bytesRead);
    			bdr.setTrim(true); // 값을 읽을때 trim 할지 여부
                    
    			lstRecvData.add("Transaction Code : " + bdr.get(10));
    			lstRecvData.add("전문그룹코드 : " + bdr.get(9));
    			lstRecvData.add("거래종별코드 : " + bdr.get(4));
    			lstRecvData.add("거래구분코드 : " + bdr.get(5));
    			lstRecvData.add("송수신Flag : " + bdr.get(1));
    			lstRecvData.add("단말기구분 : " + bdr.get(3));
                    
    			sResCode = bdr.get(4);
    			lstRecvData.add("응답코드 : " + sResCode);
    			
    			lstRecvData.add("User ID : " + bdr.get(9));
    			lstRecvData.add("기관전문 관리번호 : " + bdr.get(10));
    			lstRecvData.add("기관전문 전송시간 : " + bdr.get(14));
    			lstRecvData.add("NICE 전문 관리번호 : " + bdr.get(10));
    			lstRecvData.add("NICE 전문 전송시간 : " + bdr.get(14));
    			lstRecvData.add("공란 : " + bdr.get(16));
    			lstRecvData.add("조회동의사유 : " + bdr.get(1));
    			lstRecvData.add("생년월일 : " + bdr.get(8));
    			lstRecvData.add("성명 : " + bdr.get(40));
                    
    			lstRecvData.add("휴대폰번호 : " + bdr.get(11));
    			
    			sSafekey = bdr.get(13);
    			lstRecvData.add("Safe-Key : " + sSafekey);
    			
    			lstRecvData.add("성별 : " + bdr.get(1));
    			lstRecvData.add("공란 : " + bdr.get(77));
    			
    			/*******************************************************************************/
    			/* 응답결과 생성 */
    	        /*******************************************************************************/
    			hmResult.put("accnt_no"		, sAccntNo		); // 회원번호
    			hmResult.put("mem_no"		, sMemNo		); // 회원번호
    			hmResult.put("name"			, sMemNm		);
    			hmResult.put("cell"			, sCell			);
    			hmResult.put("brth_mon_day"	, sBrthMonDay	);
    			hmResult.put("sex"			, sSex			);
    			hmResult.put("safekey"		, sSafekey		);
    			hmResult.put("return_code"	, sResCode		); // 응답코드 - safekey 발급과 신용등급 조회 결과
                    
            } else {
            	// throw new Exception("Safe-Key 발급 서버로부터 응답을 받지 못했습니다.");
            }
    		
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		return null;
    	}
    	
    	for (int i=0; i<lstRecvData.size(); ++i) {
			System.out.println(lstRecvData.get(i).toString());
		}
            
		
    	
        return hmResult;
		
    }
    
    public static List<Map<String, Object>> getSafekeyIssueStatusCodeList () {
    	List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
    	
    	HashMap<String, String> map = new HashMap<String, String>();
    	
    	map.put("0C002", "SMS/EMAIL 미접속 유효기간(24시간) 이내");
    	map.put("0C003", "SMS/EMAIL 미접속 유효기간(24시간) 경과");
    	map.put("10000", "정상 발급");
    	map.put("1C001", "본인인증 미수행");
    	map.put("1C002", "발급신청 미수행");
    	map.put("1C003", "발급신청 기간 만료");
    	map.put("13002", "실명오류");
    	map.put("13003", "자료없음(실명확인)");
    	map.put("13050", "정보도용차단 설정");
    	map.put("20000", "인증성공");
    	map.put("2C001", "본인인증 미수행");
    	map.put("2C002", "발급신청 미수행");
    	map.put("2C003", "발급신청 기간 만료");
    	map.put("20001", "인증불일치(사용자인증, 인증번호 불일치)");
    	map.put("20002", "대상자없음(이통사고객아님)");
    	map.put("20003", "기타인증오류(이통사로부터 정의되지 않은 결과코드 수신)");
    	map.put("20004", "성명불일치");
    	map.put("20006", "법인사용자");
    	map.put("20007", "선불폰사용자");
    	map.put("20008", "서비스중지상태");
    	map.put("20011", "유효하지 않은 응답 SEQ");
    	map.put("20012", "유효하지 않은 인증정보(주민번호, 휴대폰번호, 이통사)");
    	map.put("20013", "암호화 데이터 처리오류");
    	map.put("20014", "암호화 프로세스 오류");
    	map.put("20015", "암호화 데이터 오류");
    	map.put("20016", "복호화 프로세스 오류");
    	map.put("20017", "복호화 데이터 오류");
    	map.put("20018", "이통사 통신오류");
    	map.put("20019", "데이터베이스 오류");
    	map.put("20020", "유효하지 않은 제휴사 코드");
    	map.put("20021", "중단된 제휴사 코드");
    	map.put("20022", "휴대폰인증 사용이 불가한 제휴사 코드");
    	map.put("20031", "인증번호 확인 실패(해당 데이터 없음)");
    	map.put("20032", "인증번호 확인 실패(주민번호 불일치)");
    	map.put("20033", "인증번호 확인 실패(요청SEQ 불일치)");
    	map.put("20034", "인증번호 확인 실패(기 처리건)");
    	map.put("29000", "사용자인증,단말기인증 실패");
    	map.put("29001", "시간초과");
    	map.put("29002", "자료없음");
    	map.put("29004", "주민번호 없음");
    	map.put("29008", "단말기 정보조회시 이통사쪽 서버에러");
    	map.put("29009", "사용자 정보조회시 이통사쪽 서버에러");
    	map.put("29401", "이통사에서 데이터 읽어오기 실패");
    	map.put("29402", "이통사 에러코드 리턴");
    	map.put("29501", "인증실패(MCP 인증 실패)");
    	map.put("29801", "DB관련오류");
    	map.put("29901", "프로토콜 전체 사이즈오류");
    	map.put("29902", "프로토콜 데이터 사이즈오류");
    	map.put("29903", "프로토콜 규약 위반 오류");
    	map.put("29904", "요청 폰번호(MIN,MDN),주민번호,통신사 길이틀림");
    	map.put("29905", "폰번호 조회실패");
    	map.put("29906", "잘못된 통신사 INPUT");
    	map.put("29907", "NPDB 조회로 이동통신사알수없는경우");
    	map.put("29908", "LGT사용자 정보조회 실패");
    	map.put("29097", "인증번호 입력 3회 오류");
    	map.put("29999", "정의되지 않은 오류");
    	map.put("23002", "실명오류");
    	map.put("23003", "자료없음(실명확인)");
    	map.put("23050", "정보도용차단 설정");
    	map.put("30000", "승인");
    	map.put("3C001", "본인인증 미수행");
    	map.put("3C002", "발급신청 미수행");
    	map.put("3C003", "발급신청 기간 만료");
    	map.put("30001", "주민/사업자번호틀림");
    	map.put("30002", "카드사 사용제한");
    	map.put("30019", "승인");
    	map.put("30120", "인터넷인증오류");
    	map.put("30157", "사용개시등록요망(1588-4500)");
    	map.put("30189", "은행시스템작업중");
    	map.put("30194", "거래은행전화요망(1588-4500)");
    	map.put("301C3", "탈회카드사용불가");
    	map.put("301C5", "비밀번호미등록");
    	map.put("301C6", "일시불거래안됨");
    	map.put("301C7", "거래제한사용안됨");
    	map.put("301I9", "카드사전화요망");
    	map.put("301K2", "카드발급상태이상");
    	map.put("301K3", "주민/사업자번호틀림");
    	map.put("301K8", "카드사전화요망");
    	map.put("30249", "인증오류횟수초과1588-1788");
    	map.put("30264", "일시불거래제한회원요청");
    	map.put("30271", "신규발급카드사용요망");
    	map.put("302VA", "이용불가제휴카드");
    	map.put("30414", "카드번호오류");
    	map.put("30441", "분실도난카드");
    	map.put("30455", "비밀번호상이");
    	map.put("30457", "해당카드거래불가");
    	map.put("30475", "비밀번호오류초과");
    	map.put("304A4", "주민번호오류");
    	map.put("304A5", "주민비밀번호오류");
    	map.put("304A6", "인증불가가맹점");
    	map.put("30618", "회원정보오류");
    	map.put("30810", "가맹점번호부적당");
    	map.put("30916", "기프트카드서비스불가");
    	map.put("30973", "비밀번호횟수초과");
    	map.put("31280", "허가된거래아님");
    	map.put("312C4", "미교부카드");
    	map.put("33064", "카드확인(비밀번호,유효기간)");
    	map.put("34111", "시스템에러");
    	map.put("34873", "서비스시간종료");
    	map.put("34949", "1분후재조회요망");
    	map.put("37002", "카드사연락요망");
    	map.put("37004", "도난/분실");
    	map.put("37005", "거래정지");
    	map.put("37083", "카드번호오류");
    	map.put("37091", "유효기간입력오류");
    	map.put("37452", "거래정지카드");
    	map.put("37575", "미등록가맹점");
    	map.put("37576", "CVC/CVV검증오류");
    	map.put("37577", "MS훼손카드");
    	map.put("38037", "카드번호오류");
    	map.put("38310", "비밀번호오류");
    	map.put("38311", "주민등록번호상이");
    	map.put("38312", "사업자번호상이");
    	map.put("38314", "유효기간만료");
    	map.put("38323", "거래정지카드");
    	map.put("38324", "거래정지카드");
    	map.put("38326", "사용한도초과");
    	map.put("38330", "주민비밀번호틀림");
    	map.put("38350", "도난/분실");
    	map.put("38373", "카드사로문의요망");
    	map.put("38381", "외환카드전산오류");
    	map.put("38384", "CALL1566-6900");
    	map.put("38392", "CALL1588-8300");
    	map.put("38397", "비밀번호등록요망");
    	map.put("38398", "사용제한가맹점");
    	map.put("38399", "카드수령등록요망");
    	map.put("38410", "서비스미적용회원");
    	map.put("38418", "카드인증제한");
    	map.put("38423", "인증대상카드아님");
    	map.put("38521", "거래불가카드");
    	map.put("38532", "시스템장애");
    	map.put("38833", "신카드사용요망");
    	map.put("39000", "체크카드는인증이제한됩니다.");
    	map.put("39001", "신한카드는인증이제한됩니다.");
    	map.put("39004", "거래번호확인(1)");
    	map.put("33002", "실명오류");
    	map.put("33003", "자료없음(실명확인)");
    	map.put("33050", "정보도용차단 설정");
    	map.put("40000", "인증성공");
    	map.put("4C001", "본인인증 미수행");
    	map.put("4C002", "발급신청 미수행");
    	map.put("4C003", "발급신청 기간 만료");
    	map.put("41000", "대칭키 복호화에 실패하였습니다");
    	map.put("42000", "올바른 인증서가 아닙니다");
    	map.put("42001", "허용되지 않는 정책의 인증서입니다");
    	map.put("42002", "사용자 본인확인 검사에 실패하였습니다");
    	map.put("42003", "인증서 유효성 검사 실패");
    	map.put("49999", "정의되지 않은 오류");
    	map.put("43002", "실명오류");
    	map.put("43003", "자료없음(실명확인)");
    	map.put("43050", "정보도용차단 설정");
    	
    	Set<String> keySet = map.keySet();
    	Iterator<String> iter = keySet.iterator();
    	Map mapThis = null;
    	
    	while (iter.hasNext()) {
    		String sKey = iter.next();
    		mapThis = new HashMap<String, Object>();
    		mapThis.put("cd", sKey);
    		mapThis.put("cd_nm", map.get(sKey));
    		lst.add(mapThis);
    	}
    	
    	return lst;
    }
    
    public static int parseInt(String sVal) {
        
        if (null==sVal) {
        	return 0;
        }
        if (sVal.trim().length() < 1) {
        	return 0;
        }
        
        return Integer.parseInt(sVal); 
    }
    
}
