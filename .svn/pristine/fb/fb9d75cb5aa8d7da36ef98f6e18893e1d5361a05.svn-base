/*
 * (@)# WebConsServiceImpl.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
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

package powerservice.business.web.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.impl.NiceCreditDAO;
import powerservice.business.web.service.WebNiceSenderService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
 * @프로그램ID WebCons
 */

@Service
public class WebNiceSenderServiceImpl extends EgovAbstractServiceImpl implements WebNiceSenderService {

    @Resource
    public WebNiceSenderDAO webNiceSenderDAO;
    @Resource
    public NiceCreditDAO niceCreditDAO;
    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 조회
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceInfo(Map<String, ?> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.getMemberNiceInfo() ::: " + pmParam.toString());
        return webNiceSenderDAO.getMemberNiceInfo(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int insertMemberNiceInfo(Map<String, ?> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.insertMemberNiceInfo() ::: " + pmParam.toString());
        return webNiceSenderDAO.insertMemberNiceInfo(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 업데이트 (고객생년월일)
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateMemberNiceInfo(Map<String, ?> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.updateMemberNiceInfo() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateMemberNiceInfo(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 정보 업데이트 (NICE에서 보내주는 데이터를 통하여 업데이트 됨)
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceSendStatement(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateNiceSendStatement() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateNiceSendStatement(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 SMS 발송 내역 조회 총 건수
     * 대상 테이블 : TB_MEMBER_NICE_INFO, TB_MEMBER_NICE_INFO_HIST, TB_TRGT_CUST_DTPT, TB_USER
     * ================================================================
     * */
    public int getTotalCount(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getTotalCount() ::: " + pmParam.toString());
        return webNiceSenderDAO.getTotalCount(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 SMS 발송 내역 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO, TB_MEMBER_NICE_INFO_HIST, TB_TRGT_CUST_DTPT, TB_USER
     * ================================================================
     * */
    public List<Map<String, Object>> getNiceSenderInfoList(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getNiceSenderInfoList() ::: " + pmParam.toString());
        return webNiceSenderDAO.getNiceSenderInfoList(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상고객 히스토리 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO_HIST
     * ================================================================
     * */
    public int insertMemberNiceInfoHist(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.insertMemberNiceInfoHist() ::: " + pmParam.toString());
        return webNiceSenderDAO.insertMemberNiceInfoHist(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getMemberNiceRetrieve() ::: " + pmParam.toString());
        return webNiceSenderDAO.getMemberNiceRetrieve(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 업데이트 (2차 방송시 수행됨, 고객의 고유번호와 회원번호가 업데이트됨)
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateMemberNiceInfo2(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateMemberNiceInfo2() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateMemberNiceInfo2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 (2차 방송시 수행됨, 고객의 고유번호와 회원번호가 업데이트됨)
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateMemberNiceInfo3(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateMemberNiceInfo3() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateMemberNiceInfo3(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서내용 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getCertfMng(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getCertfMng() ::: " + pmParam.toString());
        return webNiceSenderDAO.getCertfMng(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 해약율과 해약금 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getResnAmt(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getResnAmt() ::: " + pmParam.toString());
        return webNiceSenderDAO.getResnAmt(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 NICE SafeKey 조회 이력 저장
     * ================================================================
     * */
    public int insertNiceSafekeySearchHis(Map<String, Object> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.insertNiceSafekeySearchHis() ::: " + pmParam.toString());
        return niceCreditDAO.insertNiceSafekeySearchHis(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180302
     * 이름 : 송준빈
     * 추가내용 : NICE 1차완료/2차완료/1차취소/2차취소 여부 업데이트
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceFinishStatement(Map<String, Object> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.updateNiceFinishStatement() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateNiceFinishStatement(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 1차 전문 처리중 에러(Exception)시 해당 값 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int deleteExceptionHandlerNiceInfo(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.deleteExceptionHandlerNiceInfo() ::: " + pmParam.toString());
        return webNiceSenderDAO.deleteExceptionHandlerNiceInfo(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 1차 전문 처리중 에러(Exception)시 해당 값 초기화
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateExceptionHandlerTrgtCust(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateExceptionHandlerTrgtCust() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateExceptionHandlerTrgtCust(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 고유번호와 회원번호 초기화
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateExceptionHandlerNiceInfo2(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateExceptionHandlerNiceInfo2() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateExceptionHandlerNiceInfo2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 고유번호와 회원번호 초기화
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateExceptionHandlerTrgtCust2(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateExceptionHandlerTrgtCust2() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateExceptionHandlerTrgtCust2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180309
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서 이미지를 관리할 폴더를 만들기 위함임.
     * 대상 테이블 : MEM_PROD_ACCNT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceJoinDt(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getMemberNiceJoinDt() ::: " + pmParam.toString());
        return webNiceSenderDAO.getMemberNiceJoinDt(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180710
     * 이름 : 송준빈
     * 추가내용 : 가입후 재전송 회원 정보를 조회한다.
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public int updateResendNicePdfFile(Map<String, Object> pmParam) {
		System.out.println("WebNiceSenderServiceImpl.updateResendNicePdfFile() ::: " + pmParam);
		return webNiceSenderDAO.updateResendNicePdfFile(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 최근 애큐온 인증 여부 Y/N
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public List<Map<String, Object>> getAcuonLatelyAuth(Map<String, Object> pmParam) {
		System.out.println("WebNiceSenderServiceImpl.getAcuonLatelyAuth() ::: " + pmParam);
		return webNiceSenderDAO.getAcuonLatelyAuth(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public int insertMemberNiceInfoSecond(Map<String, ?> pmParam) throws Exception {
		System.out.println("WebNiceSenderServiceImpl.insertMemberNiceInfoSecond() ::: " + pmParam);
		return webNiceSenderDAO.insertMemberNiceInfoSecond(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
    public int updateNiceSendStatement2(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.updateNiceSendStatement2() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateNiceSendStatement2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve2(Map<String, Object> pmParam) {
        System.out.println("WebNiceSenderServiceImpl.getMemberNiceRetrieve2() ::: " + pmParam.toString());
        return webNiceSenderDAO.getMemberNiceRetrieve2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 1차완료/2차완료/1차취소/2차취소 여부 업데이트
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceFinishStatement2(Map<String, Object> pmParam) throws Exception {
        System.out.println("WebNiceSenderServiceImpl.updateNiceFinishStatement2() ::: " + pmParam.toString());
        return webNiceSenderDAO.updateNiceFinishStatement2(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명인증고객 관리 List
     * ================================================================
     * */
	public List<Map<String, Object>> getNiceAuthInformation(Map<String, Object> pmParam) {
		System.out.println("WebNiceSenderServiceImpl.getNiceAuthInformation() ::: " + pmParam.toString());
		return  webNiceSenderDAO.getNiceAuthInformation(pmParam);
	}

	/* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명가입고객 관리 List
     * ================================================================
     * */
	public List<Map<String, Object>> getNiceJoinInformation(Map<String, Object> pmParam) {
		System.out.println("WebNiceSenderServiceImpl.getNiceJoinInformation() ::: " + pmParam.toString());
		return  webNiceSenderDAO.getNiceJoinInformation(pmParam);
	}

	/* ================================================================
     * 날짜 : 20181015
     * 이름 : 송준빈
     * 추가내용 : 가입고객데이터 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
	public int updateNiceJoinData(Map<String, Object> pmParam) {
		return  webNiceSenderDAO.updateNiceJoinData(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20181015
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 해당 값 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
	public int deleteExceptionHandlerNiceInfoSecond(Map<String, Object> pmParam) {
		return webNiceSenderDAO.deleteExceptionHandlerNiceInfoSecond(pmParam);
	}
}

