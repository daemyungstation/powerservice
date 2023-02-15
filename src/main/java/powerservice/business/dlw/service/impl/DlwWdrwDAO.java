/*
 * (@)# DlwWdrwDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwWdrwDAO extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     *
     * @param sqlSession DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /* (상세구분별) 산출 조회 */
    public List<Map<String, Object>> getDlwWdrwListByReqbit(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getDlwWdrwListByReqbit", pmParam);
    }

    /* 전체 임시건 산출 */
    public int saveImsiWdrw(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.saveImsiWdrw", pmParam);
    }

    /* CMS 임시건 산출 */
    public int saveCmsTempWdrw(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.saveCmsTempWdrw", pmParam);
    }

    /* 카드 임시건 산출 */
    public int saveCardTempWdrw(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.saveCardTempWdrw", pmParam);
    }

    /* 산출 회원정보 조회 */
    public List<Map<String, Object>> getDlwWdrwAcntInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getDlwWdrwAcntInfo", pmParam);
    }

    /* 임의등록 */
    public int addWdrwTemp(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.addWdrwTemp", pmParam);
    }

    /* 임의삭제 */
    public int delWdrwTemp(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.delWdrwTemp", pmParam);
    }





    /**
     * 신규출금회차 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getnew_inv_no(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getnew_inv_no", pmParam);
    }



    /**
     * INV_NO 조회
     *
     * @param pmParam 검색 조건
     * @return  INV_NO
     * @throws Exception
     */
    public List<Map<String, Object>> getInvNo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getInvNo", pmParam);
    }

    /**
     * 고객정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getDlwAccntBaseInfo", pmParam);
    }

    /**
     * 고객정보 카운트 조회
     * 정승철
     * 20181113
     */
    public int getCntDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getCntDlwAccntBaseInfo", pmParam);
    }

    /**
     * 고객정보 조회(팝업용)
     * 정승철
     * 20181113
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo_popup(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getDlwAccntBaseInfo_popup", pmParam);
    }


    /**
     * 대명라이프웨이 CMS 구좌별 최종납입회차 조회
     *
     * @param pmParam 검색 조건
     * @return 최종납입회차
     * @throws Exception
     */
    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getDlwLastPayNo", pmParam);
    }

    /**
     * 대명라이프웨이 납입액 조회
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getInvAmt", pmParam);
    }

    /**
     * CARD 파일 작성 기초 리스트
     * 임동진
     * 20180927
    */
    public List<Map<String, Object>> getWdrwReqList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwReqList", pmParam);
    }
    
    /**
     * CARD 무승인 파일 작성 취소 리스트
     * 송준빈
     * 20191211
    */
    public List<Map<String, Object>> getWdrwReqCnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwReqCnclList", pmParam);
    }

    /**
     * 청구데이터 INSERT
     * 임동진
     * 20181004
    */
    public int insertReqWdrw(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertReqWdrw", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 공통정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwDlwCmsComnInfo", pmParam);
    }

    /**
     * 산출 가능 일자 체크
     * 임동진
     * 20181005
    */
    public List<Map<String, Object>> getWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwReqCheck", pmParam);
    }

    /**
     * 산출마감체크 조회
     * 정승철
     * 20181012
    */
    public List<Map<String, Object>> getWdrwExtCheck(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwExtCheck", pmParam);
    }

    /**
     * 산출마감체크 저장
     * 정승철
     * 20181012
    */
    public int saveExtChk(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.saveExtChk", pmParam);
    }

    /**
     * 산출마감체크 삭제
     * 정승철
     * 20181012
    */
    public int delExtChk(Map<String, ?> pmParam) throws Exception {
        return delete("DlwWdrwMap.delExtChk", pmParam);
    }

    /**
     * 산출마감 입력유효성체크
     * 정승철
     * 20181012
    */
    public int chkWdrwExt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.chkWdrwExt", pmParam);
    }

    /**
     * 휴일여부 체크
     * 정승철
     * 20181012
    */
    public String getHolidayChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getHolidayChk", pmParam);
    }

    /**
     * 실시간 결제 대상 회원 정보
     * 임동진
     * 20181012
    */
    public List<Map<String, Object>> getRealTimeReqList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getRealTimeReqList", pmParam);
    }

    /**
     * 카드 / CMS 결과데이터 인서트 (실시간, 파일배치)
     * 임동진
     * 20181015
    */
    public int insertReqWdrwResult(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertReqWdrwResult", pmParam);
    }

    /**
     * 성공한 데이터 회원 입금 데이터 insert
     * 임동진
     * 20181016
    */
    public int insertMemPayData(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertMemPayData", pmParam);
    }

    /**
     * 실시간 결제 시 가입일자 및 KSTBIT값 변경
     * 임동진
     * 20181018
    */
    public int uptMemJoinStatus(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.uptMemJoinStatus", pmParam);
    }

    /**
     * CMS파일 작성 시 RESULT키 업데이트 처리  향후 CMS 결과 데이터와 조인
     * 임동진
     * 20181023
    */
    public int uptReqResultKey(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.uptReqResultKey", pmParam);
    }

    /**
     * 가상계좌 청구된 회원 리스트
     * 임동진
     * 20181029
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVirtualReqList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getVirtualReqList", pmParam);
    }

    /**
     * 가상계좌 산출 대상  리스트
     * 임동진
     * 20181029
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVirtualExtList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getVirtualExtList", pmParam);
    }

    /**
     * 가상계좌 산출 INSERT
     * 임동진
     * 20181030
    */
    public int insertVirtualMemData(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertVirtualMemData", pmParam);
    }

    /**
     * 가상계좌 산출데이터 청구 등록
     * 임동진
     * 20181030
    */
    public int insertVirtualReq(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertVirtualReq", pmParam);
    }

    /**
    * 가상계좌 전송 전  청구 여부 확인
    * 임동진
    * 20181101
    */
    public List<Map<String, Object>> getVirtualReqYn(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getVirtualReqYn", pmParam);
    }

    /**
     * 가상계좌 NICE 전송 후 TID업데이트 혹은 실패 처리
     * 임동진
     * 20181101
     */
    public int updateVirtualReqSendtoNice(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.updateVirtualReqSendtoNice", pmParam);
    }

    /**
     * 가상계좌 산출 현황 조회 LIST
     * 임동진
     * 20181105
     */
    public List<Map<String, Object>> getVirtualReqResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getVirtualReqResultList", pmParam);
    }

    /**
     * 가상계좌 산출 현황 조회 COUNT
     * 임동진
     * 20181105
     */
    public int getVirtualReqResultCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getVirtualReqResultCnt", pmParam);
    }

    /**
     * 가상계좌 청구 중 강제 취소
     * 임동진
     * 20181105
     */
    public int updateVirtualReqDelete(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.updateVirtualReqDelete", pmParam);
    }

    /**
     * 가상계좌 산출 대상자 삭제
     * 임동진
     * 20181105
     */
    public int updateVirtualExtDelete(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.updateVirtualExtDelete", pmParam);
    }

    /**
     * 입금데이터 INSERT
     * 임동진
     * 20181109
    */
    public int insertFilePayData(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertFilePayData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181211
     * 이름 : 송준빈
     * 추가내용 : 청구강제마감 업데이트
     * 대상 테이블 : TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int updateCompulsionWdrdList(Map<String, Object> pmParam) {
        return update("DlwWdrwMap.updateCompulsionWdrdList", pmParam);
    }

    /**
     * 특수 산출
     * 정승철
     * 20181211
    */
    public int saveSpecialWdrw(Map<String, ?> pmParam) throws Exception {
        return update("DlwWdrwMap.saveSpecialWdrw", pmParam);
    }

    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 resultKey추출 (가맹점 번호 기준)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     */
    public int uptReqNauthResultKey(Map<String, ?> pmParam) {
    	return update("DlwWdrwMap.uptReqNauthResultKey", pmParam);
	}
    
	/** ================================================================
	 * 날짜 : 20191202
	 * 이름 : 송준빈
	 * 추가내용 : 카드 무승인 데이터 개수 추출 (가맹점 번호 기준)
	 * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
	 * ================================================================
	 */
	public List<Map<String, Object>> getWdrwNauthReqFranCnt(Map<String, ?> pmParam) {
		return selectList("DlwWdrwMap.getWdrwNauthReqFranCnt", pmParam);
	}
	
	/** ================================================================
	 * 날짜 : 20191202
	 * 이름 : 송준빈
	 * 추가내용 : 카드 무승인 데이터 추출
	 * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
	 * ================================================================
	 */
	public List<Map<String, Object>> getWdrwNauthReqList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwWdrwMap.getWdrwNauthReqList", pmParam);
    }
	
	/** ================================================================
	 * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 개수 추출 (청구일자기준)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
	 * ================================================================
	 */
	public List<Map<String, Object>> getWdrwNauthReqTotal(Map<String, ?> pmParam) {
		return selectList("DlwWdrwMap.getWdrwNauthReqTotal", pmParam);
	}
	
	/** ================================================================
	 * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
	 * ================================================================
	 */
	public int updateNauthCancelCalc(Map<String, ?> pmParam) throws Exception {
		return update("DlwWdrwMap.updateNauthCancelCalc", pmParam);
	}
	
	/** ================================================================
	 * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 프랜차이즈 개수 추출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
	 * ================================================================
	 */
	public int getWdrwNauthReqFranCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwWdrwMap.getWdrwNauthReqFranCount", pmParam);
    }
	
	/** ================================================================
	 * 날짜 : 20200226
	 * 이름 : 송준빈
	 * 추가내용 : 가상계좌 산출데이터 청구 등록 NEW
	 * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
	 * ================================================================
	 * */
    public int insertVirtualReqRefac(Map<String, ?> pmParam) throws Exception {
        return insert("DlwWdrwMap.insertVirtualReqRefac", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getDayCardNauthCount(Map<String, Object> pmParam) {
        return selectOne("DlwWdrwMap.getDayCardNauthCount", pmParam);
    }
}