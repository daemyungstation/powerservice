/*
 * (@)# ChatSndgHstrServiceImpl.java
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/05/19
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.chn.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.chn.service.ChatSndgHstrService;
import powerservice.business.sys.service.impl.FileDAO;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.DateUtil;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;


//import antlr.StringUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 문자 전송 이력 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2014/07/16
 * @프로그램ID ChatSndgHstr
 */
@Service
public class ChatSndgHstrServiceImpl extends EgovAbstractServiceImpl implements ChatSndgHstrService {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatSndgHstrServiceImpl.class);

    @Resource 
    public ChatSndgHstrDAO chatSndgHstrDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 문자메세지를 전송한다.(시스원 업체)
     *
     * @param pmParam 문자메세지 목록
     * @throws Exception
     */
    public String insertSysoneSmsSend(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        //메시지 전송타입 0:SMS, 2:MMS, 3:LMS
        String sServiceType = (String) pmParam.get("service_type");

        if("0".equals(sServiceType)){//SMS
            nResult = chatSndgHstrDAO.insertSysoneSmsSend(pmParam);
        }else if("2".equals(sServiceType)){//MMS
            chatSndgHstrDAO.insertMmsFile(pmParam);
            nResult = chatSndgHstrDAO.insertSendMms(pmParam);
        }else{//LMS
            nResult = chatSndgHstrDAO.insertSysoneLmsSend(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_pr");
        }
        return sKey;
    }
    

    /**
     * 문자메세지를 전송한다.
     *
     * @param pmParam 문자메세지 목록
     * @throws Exception
     */
    public String insertSend(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        //메시지 전송타입 0:SMS, 2:MMS, 3:LMS
        String sServiceType = (String) pmParam.get("service_type");

        if("0".equals(sServiceType)){//SMS
            nResult = chatSndgHstrDAO.insertSendSms(pmParam);
        }else if("2".equals(sServiceType)){//MMS
            chatSndgHstrDAO.insertMmsFile(pmParam);
            nResult = chatSndgHstrDAO.insertSendMms(pmParam);
        }else{//LMS
            nResult = chatSndgHstrDAO.insertSendLms(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_pr");
        }
        return sKey;
    }

    /**
     * 동보발송시 Detail 저장
     *
     * @param pmParam 문자메세지 동보 목록 
     * @throws Exception
     */
    public String insertSendDetail(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        //메시지 전송타입 0:SMS, 2:MMS, 3:LMS
        String sServiceType = (String) pmParam.get("service_type");

        if("0".equals(sServiceType)){//SMS
            nResult = chatSndgHstrDAO.insertSmtClient(pmParam);
        }else{//MMS
            nResult = chatSndgHstrDAO.insertMmtClient(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_seq");
        }
        return sKey;
    }
    
    /**
     * 동보발송시 Detail 저장 (시스원 문자)
     *
     * @param pmParam 문자메세지 동보 목록 
     * @throws Exception
     */
    public String insertSysoneSendDetail(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        //메시지 전송타입 0:SMS, 2:MMS, 3:LMS
        String sServiceType = (String) pmParam.get("service_type");

        if("0".equals(sServiceType)){//SMS
            nResult = chatSndgHstrDAO.insertSysoneSmsSend(pmParam);
        }else{//MMS
            nResult = chatSndgHstrDAO.insertSysoneLmsSend(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_seq");
        }
        return sKey;
    }

    /**
     * 동보발송시 MsgStatus 변경
     *
     * @param pmParam 문자메세지 sequence 정보
     * @throws Exception
     */
    public void updateMsgStatus(Map<String, Object> pmParam) throws Exception {
        chatSndgHstrDAO.updateMsgStatus(pmParam);
    }

    /**
     * 문자 전송 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자 전송 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) chatSndgHstrDAO.getChatSndgHstrCount(pmParam);
    }

    /**
     * 문자 전송 이력 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception {
        return chatSndgHstrDAO.getChatSndgHstrList(param);
    }

    /**
     * 문자 전송 이력 정보를 검색한다.(1건)
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatSndgHstr(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("chat_sndg_hstr_id", psId);

        return chatSndgHstrDAO.getChatSndgHstrList(mParam);
    }


    /**
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public String insertChatSndgHstr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        chatSndgHstrDAO.insertChatSndgHstr(pmParam);

        return sKey;
    }
/*    public String insertChatSndgHstr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = chatSndgHstrDAO.insertChatSndgHstr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("chat_sndg_hstr_id");

            // 첨부파일 업데이트
            updateFile(pmParam);
        }

        return sKey;
    }*/

    /**
     * 문자전송 이력 엑셀 정보를 등록한다.
     *
     * @param pmParam 문자전송 이력 엑셀 정보
     * @throws Exception
     */
    public int mergeChatSndgHstrExcelList(DataSetMap pmInDsList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (int i = 0; i <  pmInDsList.size(); i++) {
            Map<String, Object> mInDs = pmInDsList.get(i);
            mInDs.put("cntr_cd", pmParam.get("cntr_cd"));
            mInDs.put("rgsr_id", pmParam.get("rgsr_id"));
            mInDs.put("amnd_id", pmParam.get("amnd_id"));
            nResult += chatSndgHstrDAO.mergeChatSndgHstrExcel(mInDs);
        }

        return nResult;
    }


    /**
     * 첨부파일에 문자메세지 ID를 업데이트 한다.
     *
     * @param pmParam 문자메세지 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        pmParam.put("rltn_item_id", pmParam.get("chat_sndg_hstr_id"));

        // 파일 삭제
        fileDAO.deleteFile(pmParam);

        // 첨부파일 업데이트
        @SuppressWarnings("unchecked")
        List<String> fileList = (ArrayList<String>) pmParam.get("fileIds");

        if (fileList != null && fileList.size() > 0) {
            nResult += fileDAO.updateFile(pmParam);
        }

        return nResult;
    }


    /**
     * 문자시스템 전송결과 데이터를 동기화 한다.
     *
     * @throws Exception
     */
    public void updateChatSndgHstrResl() throws Exception {
        // 실행조건 설정 (스케줄러 실행일자 변경 시 일 데이터 동기화)
        Map<String, String> mParam = new HashMap<String, String>();
        String sCurYm = DateUtil.currentTimeToString(DateUtil.SDF_YYYYMM); // 현재년월
        String sCurHm = DateUtil.currentTimeToString(DateUtil.SDF_HHMM); // 현재시분
        mParam.put("srch_ym", sCurYm);
        if ("23:55".equals(sCurHm)) {
            mParam.put("full_srch_yn", "Y"); // 1일 데이터 동기화
        } else {
            mParam.put("full_srch_yn", "N"); // 15분 데이터 동기화
        }

        long nSttTm = System.currentTimeMillis();
        LOGGER.info("============ updateChatSndgHstrResl [" + sCurYm + " " + sCurHm + " " + mParam.get("full_srch_yn") + "] start ============");

        try {
            chatSndgHstrDAO.getSqlSession().select("ChatSndgHstrMap.getEmLogList", mParam, new chatSndgHstrRowHandler());

        } catch (Exception exception) {
            LOGGER.error("============ updateChatSndgHstrResl SEARCH ERROR ==> " + exception.getMessage());
        }
     /**
      * cj 문자 결과 동기화
      * */
        try {
            chatSndgHstrDAO.getSqlSession().select("ChatSndgHstrMap.newgetEmLogList", mParam, new newchatSndgHstrRowHandler());

        } catch (Exception exception) {
            LOGGER.error("============ updateChatSndgHstrResl SEARCH ERROR ==> " + exception.getMessage());
        }



        long nJobTm = System.currentTimeMillis() - nSttTm;
        LOGGER.info("============ updateChatSndgHstrResl end [" + sCurYm + " " + sCurHm + "] (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ") ============");

        mParam = null;
    }

    protected class chatSndgHstrRowHandler implements ResultHandler {
        public void handleResult(ResultContext oContext) {
            try {
                chatSndgHstrDAO.getSqlSession().insert("ChatSndgHstrMap.updateChatSndgHstrResl", oContext.getResultObject());
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage() + "============ updateChatSndgHstrResl UPDATE ERROR ==> " + oContext.getResultObject());
            }
        }
    }

    /**
     * cj 문자 결과 동기화
     * */
    protected class newchatSndgHstrRowHandler implements ResultHandler {
        public void handleResult(ResultContext oContext) {
            try {
                chatSndgHstrDAO.getSqlSession().insert("ChatSndgHstrMap.updatenewChatSndgHstrResl", oContext.getResultObject());
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage() + "============ updateChatSndgHstrResl UPDATE ERROR ==> " + oContext.getResultObject());
            }
        }
    }

    /**
     * Infobank에서 수신한 SMS 수신거부 전화번호 목록을 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 수신거부 전화번호 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getInfobankSmsRejectList(Map <String, DataSetMap> mapInDs) throws Exception {
        //조회조건
        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> lst = new ArrayList<>();

        if (srchInDs != null && srchInDs.size() > 0) {
            ArrayList<String> lstCell = new ArrayList<>();

            for (int i=0; i<srchInDs.size(); ++i) {
                hmParam = srchInDs.get(i);
                lstCell.add((String)hmParam.get("clph_no"));
            }

            HashMap<String, Object> mapParam = new HashMap<>();
            mapParam.put("lstCellNo", lstCell);

            lst = chatSndgHstrDAO.getInfobankSmsRejectList(mapParam);
        }

        return lst;
    }

    /**
     * cj올비브네트웍스
     * 문자메세지를 전송한다.
     *
     * @param pmParam 문자메세지 목록
     * @throws Exception
     */
    public String insertnewSend(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        //메시지 전송타입 0:SMS, 2:MMS, 3:LMS
        String sServiceType = (String) pmParam.get("service_type");

        if("0".equals(sServiceType)){//SMS
            pmParam.put("kakao_gubun", "SMS");
            nResult = chatSndgHstrDAO.insertnewSendSms(pmParam);
       // }
        //else if("2".equals(sServiceType)){//MMS                  mms 사용안함.

       //     chatSndgHstrDAO.insertMmsFile(pmParam);
        //    nResult = chatSndgHstrDAO.insertSendMms(pmParam);
        }else{//LMS
            pmParam.put("kakao_gubun", "LMS");
            nResult = chatSndgHstrDAO.insertnewSendLms(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_pr");
        }
        return sKey;
    }

    /**
     *  cj올비브네트웍스
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public String insertChatnewSndgHstr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        chatSndgHstrDAO.insertChatnewSndgHstr(pmParam);

        return sKey;
    }


    /**
     *
     *
     *
     * */
    public void insertnewallsend(DataSetMap listInDs,DataSetMap listInDs2, String sServiceType) throws Exception {
         Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Date d = new Date();

        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String strMonth = month.format(d);
        String strDay = day.format(d);

        if (listInDs != null && listInDs.size() > 0) {
            hmParam = listInDs.get(0);
        }



        for(int i=0; i<listInDs2.size(); i++){
            hmParam2 = listInDs2.get(i);
            int nResult = 0;
            System.out.println("ttttt");
            CommonUtils.printLog(hmParam2);
            if("1".equals(hmParam2.get("rowCheck"))){



               if("0".equals(sServiceType)){//SMS
                   hmParam2.put("recipient_num", (String)hmParam2.get("clph_no"));   /// 수신번호
                //   hmParam2.put("replace_name", (String)hmParam2.get("cust_nm"));   /// 고객명
                   hmParam2.put("replace_cd", (String)hmParam2.get("cust_id"));   /// 고객코드
                   hmParam2.put("callback",  (String) hmParam.get("callback"));   /// 발신번호
                 //  hmParam2.put("content",  (String) hmParam.get("content"));   /// 메세지
                   hmParam2.put("reserve_yn", (String)hmParam.get("reserve_yn"));   /// 예약시간
                   hmParam2.put("status", "9");   /// 발성상태 일괄발송은  임의 9 값으로 셋팅 후  한거번에 1로 변경.
                   hmParam2.put("reserve_date",  (String) hmParam.get("reserve_date"));   /// 예약시간
                   hmParam2.put("service_type", sServiceType);
                   hmParam2.put("kakao_gubun", "SMS");
                   hmParam2.put("chat_msg_typ_cd",(String) hmParam.get("chat_msg_typ_cd"));  // 메세지유형
                   hmParam2.put("button_gubun",(String) hmParam.get("button_gubun"));  //  버튼타입

                   hmParam2.put("button_nm",(String) hmParam.get("button_nm"));  //  버튼명칭
                   hmParam2.put("button_url",(String) hmParam.get("button_url"));  //  버튼url
                   hmParam2.put("tmp_cd",(String) hmParam.get("tmp_cd"));  // 템프코드


                   /**
                    * 가변데이터 정리...
                    *
                    * */
                   String  ls_content ="";
                   ls_content  =  (String) hmParam.get("content");
                   ls_content  =  ls_content.replace("#{회원명}", (String)hmParam2.get("cust_nm"));
                   /******************************************************************************
                    * 2017.09.20 김준호
                    * 동보 발송시 월일 가변데이터 추가
                    ********************************************************************************/
                   ls_content  =  ls_content.replace("#{월}", strMonth);
                   ls_content  =  ls_content.replace("#{일}", strDay);
                   // 일괄발송시 회원번호가 없기 때문에... 아래 가변데이터는 모두 빈값으로 처리...
                   //  만약 회원번호를  가져올경우.. 쿼리로 데이터 가져와서 처리해야함.
                   ls_content  =  ls_content.replace("#{회원번호}", "");
                   ls_content  =  ls_content.replace("#{상담사명}", "");
                   ls_content  =  ls_content.replace("#{납입회차}", "");
                   ls_content  =  ls_content.replace("#{연체회차}", "");
                   ls_content  =  ls_content.replace("#{회원 연락처}", "");

                   hmParam2.put("content",  ls_content   );   /// 메세지


                //   System.out.println("aaaa");
                //   CommonUtils.printLog(hmParam2);

                   nResult = chatSndgHstrDAO.insertnewSendSms(hmParam2);

                }else{//lms
                    hmParam2.put("subject", (String)hmParam.get("subject"));   /// 제목
                //	hmParam2.put("content",  (String) hmParam.get("content"));   /// 메세지
                    hmParam2.put("recipient_num", (String)hmParam2.get("clph_no"));   /// 수신번호
                    hmParam2.put("replace_name", (String)hmParam2.get("cust_nm"));   /// 고객명
                    hmParam2.put("reserve_yn", (String)hmParam.get("reserve_yn"));    /// 예약시간
                    hmParam2.put("callback",  (String) hmParam.get("callback"));   /// 발신번호
                    hmParam2.put("reserve_date",  (String) hmParam.get("reserve_date"));   /// 예약시간

                    hmParam2.put("status", "9");   /// 발성상태 일괄발송은  임의 9 값으로 셋팅 후  한거번에 1로 변경.
                    hmParam2.put("service_type", sServiceType);
                    hmParam2.put("kakao_gubun", "LMS");
                    hmParam2.put("chat_msg_typ_cd",(String) hmParam.get("chat_msg_typ_cd"));  // 메세지유형
                     hmParam2.put("button_gubun",(String) hmParam.get("button_gubun"));  //  버튼타입

                    hmParam2.put("button_nm",(String) hmParam.get("button_nm"));  //  버튼명칭
                   hmParam2.put("button_url",(String) hmParam.get("button_url"));  //  버튼url
                   hmParam2.put("tmp_cd",(String) hmParam.get("tmp_cd"));  // 템프코드



                   /**
                    * 가변데이터 정리...제목..
                    */
                   String ls_subject ="";
                   ls_subject  =  (String) hmParam.get("subject");
                   ls_subject  =  ls_subject.replace("#{회원명}", (String)hmParam2.get("cust_nm"));
                   ls_subject  =  ls_subject.replace("#{회원번호}", "");
                   ls_subject  =  ls_subject.replace("#{상담사명}", "");
                   ls_subject  =  ls_subject.replace("#{납입회차}", "");
                   ls_subject  =  ls_subject.replace("#{연체회차}", "");
                   ls_subject  =  ls_subject.replace("#{회원 연락처}", "");
                   hmParam2.put("subject",  ls_subject   );   /// 메세지

                   /**
                       * 가변데이터 정리...메세지..
                       *
                       * */
                       String  ls_content ="";
                       ls_content  =  (String) hmParam.get("content");
                       ls_content  =  ls_content.replace("#{회원명}", (String)hmParam2.get("cust_nm"));
                       /******************************************************************************
                        * 2017.09.20 김준호
                        * 동보 발송시 월일 가변데이터 추가
                        ********************************************************************************/
                       ls_content  =  ls_content.replace("#{월}", strMonth);
                       ls_content  =  ls_content.replace("#{일}", strDay);

                       // 일괄발송시 회원번호가 없기 때문에... 아래 가변데이터는 모두 빈값으로 처리...
                       //  만약 회원번호를  가져올경우.. 쿼리로 데이터 가져와서 처리해야함.
                       ls_content  =  ls_content.replace("#{회원번호}", "");
                       ls_content  =  ls_content.replace("#{상담사명}", "");
                       ls_content  =  ls_content.replace("#{납입회차}", "");
                       ls_content  =  ls_content.replace("#{연체회차}", "");
                       ls_content  =  ls_content.replace("#{회원 연락처}", "");
                       hmParam2.put("content",  ls_content   );   /// 메세지

                    System.out.println("bbbb");
                   System.out.println(ls_content);
                //   CommonUtils.printLog(hmParam2);

                    //nResult = chatSndgHstrDAO.insertnewMmtClient(hmParam2);
                    nResult = chatSndgHstrDAO.insertnewSendLms(hmParam2);
                }



                String sCustId = StringUtils.defaultString((String)hmParam2.get("cust_id"));
                String sCustNm = StringUtils.defaultString((String)hmParam2.get("cust_nm"));
                String sClphNo = StringUtils.defaultString((String)hmParam2.get("clph_no"));
                String sMtPr = "" ;
                if (nResult > 0) {
                    sMtPr = (String)hmParam2.get("mt_pr");
                }

                ParamUtils.addSaveParam(hmParam);

                hmParam.put("mem_no", sCustId);
                hmParam.put("mem_nm", sCustNm);
                hmParam.put("recipient_num", sClphNo);
                hmParam.put("sndg_no", sMtPr);
                hmParam.put("sndg_sqnc", 0);
                chatSndgHstrDAO.insertChatnewSndgHstr(hmParam);

                //chatSndgHstrService.updateMsgStatus(hmParam);

               // chatSndgHstrService.insertChatSndgHstr(hmParam);
            }
        }


        hmParam2.put("service_type", sServiceType);   /////   0sms 3lms  4알림톡

        hmParam2.put("chat_msg_typ_cd",(String) hmParam.get("chat_msg_typ_cd"));  // 메세지유형

        System.out.println("ccccccccccccccccccc");
        CommonUtils.printLog(hmParam2);


        /////일괄 전송방식=1 및  1롤 업데이트
        chatSndgHstrDAO.updatenewMsgStatus(hmParam2);

    }

    /* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
	public int getSmsVrtlSendListTotalCnt(Map<String, ?> pmParam) throws Exception {
		return chatSndgHstrDAO.getSmsVrtlSendListTotalCnt(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
	public List<Map<String, Object>> getSmsVrtlSendList(Map<String, ?> pmParam) throws Exception {
		return chatSndgHstrDAO.getSmsVrtlSendList(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가전송
     * ================================================================
     * */
	public int insertVrtlMsSend(Map<String, ?> pmParam) {
		return chatSndgHstrDAO.insertVrtlMsSend(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
	public int getSmsRealSendListTotalCnt(Map<String, ?> pmParam) throws Exception {
		return chatSndgHstrDAO.getSmsRealSendListTotalCnt(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
	public List<Map<String, Object>> getSmsRealSendList(Map<String, ?> pmParam) throws Exception {
		return chatSndgHstrDAO.getSmsRealSendList(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(SMS)
     * ================================================================
     * */
	public int insertRealMsSmsSend(Map<String, ?> pmParam) {
		return chatSndgHstrDAO.insertRealMsSmsSend(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(LMS)
     * ================================================================
     * */
	public int insertRealMsLmsSend(Map<String, ?> pmParam) {
		return chatSndgHstrDAO.insertRealMsLmsSend(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20190312
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(Kakao)
     * ================================================================
     * */
	public int insertRealMsKaKaoSend(Map<String, ?> pmParam) {
		return chatSndgHstrDAO.insertRealMsKaKaoSend(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송 히스토리
     * ================================================================
     * */
    public int insertChatMultiSndgHstr(Map<String, ?> pmParam) {
    	return chatSndgHstrDAO.insertChatMultiSndgHstr(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가접수테이블 업데이트
     * ================================================================
     * */
    public int updateChatMultiSndg(Map<String, Object> pmParam) {
    	return chatSndgHstrDAO.updateChatMultiSndg(pmParam);
    }

    /* 회원번호 엑셀 업로드 로직 */
    public void uploadToSms(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

        String tempDir = System.getProperty("java.io.tmpdir");
        //LOGGER.info("---uploadToSms 서비스 - 1");

        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        int fileCnt			= 0;

        Map<String, Object> mParam = null;

        // 엑셀파일 워크북 객체 생성
        XSSFWorkbook workbook = null;

        // 시트 지정
        XSSFSheet sheet = null;

        // 로우
        XSSFRow xrow = null;

        // cell
        XSSFCell xcell = null;

        List<Map<String,Object>> lst = new ArrayList<>();
        Map<String,Object> mRow = new HashMap<>();

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {

      	  	mParam = new HashMap<>();

        	// 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
            	fileCnt++;
                sKey = (String)enm.nextElement();

                LOGGER.debug("sKey : " + sKey);

                File upfile = multipartRequest.getFile(sKey);

                LOGGER.debug("upfile.length() : " +upfile.length());

                if (upfile.exists()) {
                	LOGGER.debug("file exists");
                } else {
                	LOGGER.debug("file does not exists");
                }

                // 엑셀파일 워크북 객체 생성
                workbook = new XSSFWorkbook(new FileInputStream(upfile));

                sheet = workbook.getSheetAt(0);

                Row row;
                Cell cell;
                String value;
                int i=0, j;
                boolean essential = false;
                String str_essential = "";
                String strRegman = "";
                int iAccntCnt = 0 ;  //회원번호 여부 체크용

                int rows = sheet.getPhysicalNumberOfRows();

                if (rows > 10005){
                    throw new EgovBizException("업로드정보가 10000건이 넘습니다. 전산팀에 업로드 요청 부탁드립니다.!!");
                }

				ParamUtils.addSaveParam(hmParam);

				strRegman = hmParam.get("rgsr_id").toString();

                LOGGER.debug("Excel Rows : " + rows);

                for (i=0; i<rows; i++){
                	xrow = sheet.getRow(i);
                	int cells = xrow.getPhysicalNumberOfCells();

                	if (i < 1) continue;

                 	for(j=0;j<cells;j++){
                  		xcell = xrow.getCell(j);

                  		value = "";

	            		switch (xcell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								value = "" + xcell.getBooleanCellValue();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								value = "" + xcell.getNumericCellValue();
								break;
							case Cell.CELL_TYPE_STRING:
								value = "" + xcell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_BLANK:
								value = " ";
								break;
							default:
								value = "" + xcell.getStringCellValue();
								break;
						}

						if (null != value) {
							value = value.trim();
						}

						mRow.put("rgsr_id", strRegman);

						//LOGGER.debug("Excel B : " + value);

						switch (j) {
                        case 0:
                        	mRow.put("mem_no", value);
                        	break;
                        case 1:
                        	mRow.put("accnt_no", value);
                        	break;
                        case 2:
                        	mRow.put("cell", value);
                        	break;
                        case 3:
                        	mRow.put("send_reason", value);
                        	break;
                        case 4:
                        	mRow.put("msg", value);
                        	break;

                        default:
                        	break;
                        }


                 	}

					lst.add(mRow);
					listMap.setRowMaps(lst);
					hmParam = listMap.get(0);

					//System.out.println(hmParam);

					saveSms(hmParam);

                }

                upfile.delete();
            }

            if (fileCnt < 1) {
          	  throw new EgovBizException("업로드된 파일이 없습니다.");
            }

        } catch (FileNotFoundException ex) {

        	ex.printStackTrace();

            throw ex;
        } catch (IOException ex) {

        	ex.printStackTrace();

            throw ex;
        } finally {
        }

    }
    /**
     * SMS일괄 엑셀 업로드 건별
     *
     * @param pmParam 물류 정보
     * @throws Exception
     */
    public String saveSms(Map<String, Object> pmParam) throws Exception {
        String sAccntno = StringUtils.defaultString((String) pmParam.get("accnt_no"));

        //CommonUtils.printLog(pmParam);
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 11111: " + sAccntno);

        chatSndgHstrDAO.insertSms(pmParam);

        return sAccntno;
    }

    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 조회
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public List<Map<String, Object>> getMsgExtSched(Map<String, ?> pmParam)throws Exception {
		return chatSndgHstrDAO.getMsgExtSched(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록유효성체크
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public int chkMsgExtSched(Map<String, ?> pmParam) throws Exception {
		return chatSndgHstrDAO.chkMsgExtSched(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public void insertMsgExtSched(Map<String, ?> pmParam) throws Exception {
		chatSndgHstrDAO.insertMsgExtSched(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 삭제
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public void deleteMsgExtSched(Map<String, ?> pmParam) throws Exception {
		chatSndgHstrDAO.deleteMsgExtSched(pmParam);
	}

}
