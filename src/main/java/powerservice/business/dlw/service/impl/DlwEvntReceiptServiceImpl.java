/*
 * (@)# DlwEvntService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
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

package powerservice.business.dlw.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwEvntReceiptService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.DmWebSenderService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Service
public class DlwEvntReceiptServiceImpl extends EgovAbstractServiceImpl implements DlwEvntReceiptService {

    @Resource
    public DlwEvntReceiptDAO DlwEvntReceiptDAO;

    @Resource
    public DmWebSenderService dmWebSenderService;
    
    @Resource
    public BasVlService basVlService;

    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public int getEventReceiptCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventReceiptCount(pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventReceiptList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventReceiptList(pmParam);
    }

    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventReceipt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertEventReceipt(pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEventReceipt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateEventReceipt(pmParam);
    }
    
    /**
     * 대명라이프웨이 행사삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEventReceipt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deleteEventReceipt(pmParam);
    }
    
    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventReceiptComplete(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertEventReceiptComplete(pmParam);
    }
    
    /**
     * 행사 등록시 해당 회원번호로 행사테이블 조회
     *
     * @param hmParam 검색 조건
     * @return
     * @throws Exception
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     */
    public int countEventReceipt(Map<String, Object> hmParam)  throws Exception{
        return DlwEvntReceiptDAO.countEventReceipt(hmParam);
    }
    
    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertevntMngr(Map<String, ?> pmParam) throws Exception {
    	int retCnt = 0;
    	//행사자 등록
    	DlwEvntReceiptDAO.insertevntMngr(pmParam);

    	//행사자 등록 중에 지도사(cp)가 등록되면 APP 푸시 알림

    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > retCnt : " + retCnt);
    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > pmParam.get(job_duty) : " + pmParam.get("job_duty"));

    	if (pmParam.get("job_duty").equals("0001")){
    		//sndPushAlertMain(pmParam); //행사접수시 푸시알림을 보내기 때문에 주석처리
    	}

        return retCnt;
    }
    
    /* ================================================================
     * 날짜 : 20190328
     * 이름 : 임동진
     * 추가내용 : 푸시 알림 전송
     * ================================================================
     * */
    public int sndPushAlertMain(Map<String, ?> pmParam) throws Exception {
    	Map<String, Object> mList = new HashMap<String, Object>();
    	List<Map<String, Object>> listSinfo = DlwEvntReceiptDAO.getSendPushInfo(pmParam);

    	if(listSinfo.size() != 0){
	    	mList= listSinfo.get(0);

	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("SEND_MSG"));
	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("REAL_EVT_MNGR_CD"));
	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("MNG_BRANCH_CD"));

	     	JSONObject objList = new JSONObject();
	     	JSONArray arrList = new JSONArray();

	     	String strMsg = mList.get("SEND_MSG").toString();
	     	String strMainCp = mList.get("REAL_EVT_MNGR_CD").toString();
	     	String strMngCp = mList.get("MNG_BRANCH_CD").toString();
	     	
	     	
	     	// 운영반영시 김주용 아이디로 푸시알림 테스트후 김주용,임동진파트장님 주석처리 그외 주석 열고 다시 반영 
	     	//arrList.put("2021000069"); //김주용
	     	//arrList.put("2019000062"); //임동진
	     	
	     	arrList.put(strMainCp);    //진행 cp
	     	arrList.put(strMngCp); 	   //지부장
	     	arrList.put("2017000093"); //김기래
	     	arrList.put("2017000094"); //손희영
	     	arrList.put("2017000096"); //하경수
	     	arrList.put("2017000097"); //유성원
	     	arrList.put("2017000103"); //김지연
	     	arrList.put("2018000170"); //모숙현
	     	arrList.put("2022000071"); //김종걸

	     	objList.put("message", strMsg);
	     	objList.put("target", arrList);
	     	
	     	String basVl = basVlService.getBasVlAsString("push_url");

	     	URL url = new URL(basVl);
	     	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     	conn.setDoOutput(true);
	     	conn.setRequestMethod("POST");
	     	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		    String param = "reqData="+ URLEncoder.encode(objList.toString(), "UTF-8");

	     	//전송
	     	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
	 		osw.write(param);
	 		osw.flush();

	 		System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> PARAM: " + param);

	 		//응답
	 		BufferedReader br = null;
	 		br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

	 		String line = null;
	 		while ((line = br.readLine()) != null){
	 			System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> RESULT: " + line);
	 		}

	 		osw.close();
	 		br.close();
	 		conn.disconnect();
    	}

        return 0;
    }
    
    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventSeq(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventSeq(pmParam);
    }
    
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) DlwEvntReceiptDAO.getDlwCustAcntCount(pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getDlwCustAcntList(pmParam);
    }
    
    /**
     * 대명라이프웨이 행사취소사유 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventCancelList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventCancelList(pmParam);
    }

    /**
     * 대명라이프웨이 행사취소사유 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventCancel(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertEventCancel(pmParam);
    }
    
    /**
     * 대명라이프웨이 지역 공통코드 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getComCd(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getComCd(pmParam);
    }
    
    /**
     * 대명라이프웨이 행사자 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getManagerList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getManagerList(pmParam);
    }
    
    /**
     * 행사접수완료
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> eventReceiptComplete(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.eventReceiptComplete(pmParam);
    }
    
    /**
     * 대명라이프웨이 콜현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventCallList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventCallList(pmParam);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public int getEventMainCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventMainCount(pmParam);
    }

    public List<Map<String, Object>> getEventMainList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventMainList(pmParam);
    }
    
    public List<Map<String, Object>> getEventDetailList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventDetailList(pmParam);
    }
    
    public List<Map<String, Object>> getSuppliesClsfcDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getSuppliesClsfcDtlList(pmParam);
    }
    
    public List<Map<String, Object>> getAddSales1List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getAddSales1List(pmParam);
    }
    
    public List<Map<String, Object>> getAddSales2List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getAddSales2List(pmParam);
    }
    
    public List<Map<String, Object>> getPaymentInfoList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getPaymentInfoList(pmParam);
    }
    
    public int updateEventBasicInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateEventBasicInfo(pmParam);
    }
    
    public int insertEventBasicInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertEventBasicInfo(pmParam);
    }
    
    public int updateCancelEvent(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateCancelEvent(pmParam);
    }
    
    public int updateSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateSuppliesClsfcDtl(pmParam);
    }
    
    public int insertSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertSuppliesClsfcDtl(pmParam);
    }
    
    public int deleteSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deleteSuppliesClsfcDtl(pmParam);
    }
    
    public int updateAddSales1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateAddSales1(pmParam);
    }
    
    public int insertAddSales1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertAddSales1(pmParam);
    }
    
    public int deleteAddSales1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deleteAddSales1(pmParam);
    }
    
    public int updateAddSales2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateAddSales2(pmParam);
    }
    
    public int insertAddSales2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertAddSales2(pmParam);
    }
    
    public int deleteAddSales2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deleteAddSales2(pmParam);
    }
    
    public int updatePaymentInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updatePaymentInfo(pmParam);
    }
    
    public int insertPaymentInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertPaymentInfo(pmParam);
    }
    
    public int deletePaymentInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deletePaymentInfo(pmParam);
    }
    
    public int confirmRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.confirmRegFunrlOutsrcMst(pmParam);
    }
    
    public int insertRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertRegFunrlOutsrcMst(pmParam);
    }
    
    public List<Map<String, Object>> getEventMngrList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getEventMngrList(pmParam);
    }
    
    public List<Map<String, Object>> getMngrPayList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getMngrPayList(pmParam);
    }
    
    public List<Map<String, Object>> getMemConsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getMemConsList(pmParam);
    }
    
    public List<Map<String, Object>> getProtocolMngrHistList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getProtocolMngrHistList(pmParam);
    }
    
    public int insertMemConsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.insertMemConsList(pmParam);
    }
    
    public List<Map<String, Object>> getMemberAccntDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getMemberAccntDtlList(pmParam);
    }
    
    public List<Map<String, Object>> getPayTotList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getPayTotList(pmParam);
    }
    
    public int updateEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.updateEventMngrInfo(pmParam);
    }
    
    public int insertEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        int returnVal = 0;
        
        DlwEvntReceiptDAO.insertEventMngrInfo(pmParam);
        
        if (pmParam.get("job_duty").equals("0001"))
        {
            //returnVal = sendPushAlarm(pmParam);
        }
        
        return returnVal;
    }
    
    public int sendPushAlarm(Map<String, ?> pmParam) throws Exception {
        Map<String, Object> mList = new HashMap<String, Object>();
        List<Map<String, Object>> listSinfo = dmWebSenderService.getSendPushInfo(pmParam);

        if(listSinfo.size() != 0)
        {
            mList= listSinfo.get(0);

            JSONObject objList = new JSONObject();
            JSONArray arrList = new JSONArray();

            String strMsg = mList.get("SEND_MSG").toString();
            String strMainCp = mList.get("REAL_EVT_MNGR_CD").toString();
            String strMngCp = mList.get("MNG_BRANCH_CD").toString();

            arrList.put(strMainCp);    //진행 cp
            arrList.put(strMngCp);        //지부장
            //arrList.put("2019000062"); //임동진
            arrList.put("2017000093"); //김기래
            arrList.put("2017000094"); //손희영
            arrList.put("2017000096"); //하경수
            arrList.put("2017000097"); //유성원
            arrList.put("2017000103"); //김지연
            arrList.put("2018000170"); //모숙현
            arrList.put("2022000071"); //김종걸

            objList.put("message", strMsg);
            objList.put("target", arrList);
             
            String basVl = basVlService.getBasVlAsString("push_url");

            URL url = new URL(basVl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            String param = "reqData="+ URLEncoder.encode(objList.toString(), "UTF-8");

            //전송
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(param);
            osw.flush();

            //응답
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null)
            {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> RESULT: " + line);
            }

            osw.close();
            br.close();
            conn.disconnect();
             
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
    public int deleteEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.deleteEventMngrInfo(pmParam);
    }
    
    public int getCustAccntCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getCustAccntCount(pmParam);
    }

    public List<Map<String, Object>> getCustAccntList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getCustAccntList(pmParam);
    }
    
    public int getProtocolSynthesisCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getProtocolSynthesisCount(pmParam);
    }

    public List<Map<String, Object>> getProtocolSynthesisList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntReceiptDAO.getProtocolSynthesisList(pmParam);
    }

	public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam) {
		return DlwEvntReceiptDAO.getBranchList(pmParam);
	}
}
