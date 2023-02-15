/*
 * (@)# ChatSndgHstrDAO.java
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/05/19
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

package powerservice.business.chn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class ChatSndgHstrDAO extends EgovAbstractMapper {

    /**
     * SMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 정보
     * @throws Exception
     */
    public int insertSendSms(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSendSms", pmParam);
    }
	
    /**
     * SMS 문자메세지를 전송한다. (시스원 문자전용)
     *
     * @param pmParam 문자 정송 정보
     * @throws Exception
     */
    public int insertSysoneSmsSend(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSysoneSmsSend", pmParam);
    }
    
    /**
     * LMS 문자메세지를 전송한다. (시스원 문자전용)
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSysoneLmsSend(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSysoneLmsSend", pmParam);
    }


    /**
     * MMS 첨부파일 insert
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertMmsFile(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertMmsFile", pmParam);
    }

    /**
     * MMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSendMms(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSendMms", pmParam);
    }

    /**
     * LMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSendLms(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSendLms", pmParam);
    }

    /**
     * 동보 SMS Detail 저장
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSmtClient(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertSmtClient", pmParam);
    }

    /**
     * 동보 MMS Detail 저장
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertMmtClient(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertMmtClient", pmParam);
    }

    /**
     * 동보발송시 MsgStatus 변경
     *
     * @param pmParam 문자메세지 sequence 정보
     * @throws Exception
     */
    public int updateMsgStatus(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.updateMsgStatus", pmParam);
    }

    /**
     * 문자 전송 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자 전송 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ChatSndgHstrMap.getChatSndgHstrCount", pmParam);
    }

    /**
     * 문자 전송 이력 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception {
        return selectList("ChatSndgHstrMap.getChatSndgHstrList", param);
    }


    /**
     * 문자 전송 이력 정보를 검색한다.(1건)
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public Map<String, Object> getChatSndgHstr(Map<String, ?> param) throws Exception {
        return selectOne("ChatSndgHstrMap.getChatSndgHstrList", param);
    }


    /**
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertChatSndgHstr(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertChatSndgHstr", pmParam);
    }

    /**
     * 문자전송 이력 엑셀 정보를 저장한다.
     *
     * @param pmParam 문자전송 이력 엑셀 정보
     * @throws Exception
     */
    public int mergeChatSndgHstrExcel(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.mergeChatSndgHstrExcel", pmParam);
    }

    /**
     * Infobank에서 수신한 SMS 수신거부 전화번호 목록을 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 수신거부 전화번호 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getInfobankSmsRejectList(Map<String, Object> param) throws Exception {
        return selectList("ChatSndgHstrMap.getInfobankSmsRejectList", param);
    }

    /**
     * cj 올리브네트웍스
     * SMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 정보
     * @throws Exception
     */
    public int insertnewSendSms(Map<String, ?> pmParam) throws Exception {

    	String ls_chat_type = (String)pmParam.get("chat_msg_typ_cd");
    	String ls_button_gubun = (String)pmParam.get("button_gubun");

    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
    	System.out.println(ls_chat_type);

    	if  ("KAKAO".equals(ls_chat_type) ) {      /////알림톡  ls_chat_type ='KAKAO'

    		 return insert("ChatSndgHstrMap.insertnewSendkakao", pmParam);


    	} else {
    		//System.out.println("문자");
    		return insert("ChatSndgHstrMap.insertnewSendSms", pmParam);
    		//return 0;
    	}
    }

    /**
     * cj 올리브네트웍스
     * LMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertnewSendLms(Map<String, ?> pmParam) throws Exception {
    	String ls_chat_type = (String)pmParam.get("chat_msg_typ_cd");
    	String ls_button_gubun = (String)pmParam.get("button_gubun");

    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
    	System.out.println(ls_chat_type);

    	if  ("KAKAO".equals(ls_chat_type) ) {      /////알림톡  ls_chat_type ='KAKAO'

   		 return insert("ChatSndgHstrMap.insertnewSendkakao", pmParam);


   	} else {
   		//System.out.println("문자");
   	  return insert("ChatSndgHstrMap.insertnewSendLms", pmParam);
   		//return 0;
   	}


    }
    /**
     *  cj 올리브네트웍스
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertChatnewSndgHstr(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertChatnewSndgHstr", pmParam);
    }

    /**
     * cj 올리브네트웍스
     * 일괄 sms등록
     *
     * @param pmParam 문자 전송
     * @throws Exception
     */
    public int insertnewSmtClient(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertnewSmtClient", pmParam);
    }

    /**
     * cj 올리브네트웍스
     * 일괄 lMS 등록
     *
     * @param pmParam 문자 전송 정보
     * @throws Exception
     */
    public int insertnewMmtClient(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.insertnewMmtClient", pmParam);
    }

    /**
     * cj 올리브네트웍스
     * 일괄발송시 Status 변경
     *
     * @param pmParam 문자메세지 sequence 정보
     * @throws Exception
     */
    public int updatenewMsgStatus(Map<String, ?> pmParam) throws Exception {
       return insert("ChatSndgHstrMap.updatenewMsgStatus", pmParam);
    }

    /* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
	public int getSmsVrtlSendListTotalCnt(Map<String, ?> pmParam) {
		return (Integer) selectOne("ChatSndgHstrMap.getSmsVrtlSendListTotalCnt", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
	public List<Map<String, Object>> getSmsVrtlSendList(Map<String, ?> pmParam) {
		return selectList("ChatSndgHstrMap.getSmsVrtlSendList", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가전송
     * ================================================================
     * */
	public int insertVrtlMsSend(Map<String, ?> pmParam) {
		return insert("ChatSndgHstrMap.insertVrtlMsSend", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
	public int getSmsRealSendListTotalCnt(Map<String, ?> pmParam) {
		return (Integer) selectOne("ChatSndgHstrMap.getSmsRealSendListTotalCnt", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
	public List<Map<String, Object>> getSmsRealSendList(Map<String, ?> pmParam) {
		return selectList("ChatSndgHstrMap.getSmsRealSendList", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(SMS)
     * ================================================================
     * */
	public int insertRealMsSmsSend(Map<String, ?> pmParam) {
		return insert("ChatSndgHstrMap.insertRealMsSmsSend", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(LMS)
     * ================================================================
     * */
	public int insertRealMsLmsSend(Map<String, ?> pmParam) {
		return insert("ChatSndgHstrMap.insertRealMsLmsSend", pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20190312
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(Kakao)
     * ================================================================
     * */
	public int insertRealMsKaKaoSend(Map<String, ?> pmParam) {
		return insert("ChatSndgHstrMap.insertRealMsKaKaoSend", pmParam);
	}

	/* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송 히스토리
     * ================================================================
     * */
    public int insertChatMultiSndgHstr(Map<String, ?> pmParam) {
    	return insert("ChatSndgHstrMap.insertChatMultiSndgHstr", pmParam);
    }

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가접수테이블 업데이트
     * ================================================================
     * */
    public int updateChatMultiSndg(Map<String, Object> pmParam) {
    	return update("ChatSndgHstrMap.updateChatMultiSndg", pmParam);
    }


    /**
     * SMS일괄 엑셀 업로드 건별 insert
     *
     * @param pmParam 검색 조건
     * @return insert 결과
     * @throws Exception
     */
    public int insertSms(Map<String, ?> pmParam) throws Exception {
        return insert("ChatSndgHstrMap.insertSms", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 조회
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public List<Map<String, Object>> getMsgExtSched(Map<String, ?> pmParam) {
		return selectList("ChatSndgHstrMap.getMsgExtSched", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록유효성체크
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public int chkMsgExtSched(Map<String, ?> pmParam) {
		return selectOne("ChatSndgHstrMap.chkMsgExtSched", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public int insertMsgExtSched(Map<String, ?> pmParam) {
		return insert("ChatSndgHstrMap.insertMsgExtSched", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 삭제
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
	public int deleteMsgExtSched(Map<String, ?> pmParam) {
		return delete("ChatSndgHstrMap.deleteMsgExtSched", pmParam);
	}
}
