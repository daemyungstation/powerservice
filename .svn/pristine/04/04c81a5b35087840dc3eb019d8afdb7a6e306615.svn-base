/*
 * (@)# DlwPayDAO.java
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

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwPayDAO extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     *
     * @param sqlSession DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /**
     * 결과 데이터 건수조회
     * 정승철
     * 20181015
    */
    public List<Map<String, Object>> getReqResultCount(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getReqResultCount", pmParam);
    }


    /**
     * 결과등록
     * 정승철
     * 20181016
    */
    public int insertResultInfo(List<Map<String, Object>> pmParam) throws Exception {
           CommonUtils.printLog("파라미터 확인(DAO) : " + pmParam);

           return update("DlwPayMap.insertResultInfo", pmParam);

    }


    /**
     * 파일변환 (** 결과반영)
     * 정승철
     * 20181016
    */
    public List<Map<String, Object>> convertFile(Map<String, ?> pmParam) throws Exception {
        System.out.println("=====DAO 파라미터 확인 : " + pmParam);

        return selectList("DlwPayMap.convertFile", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getWdrwReqListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getWdrwReqList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181026
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) Card 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCardSummary(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCalcAndChargeCardSummary", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191223
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) Card무승인 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCardNauthSummary(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCalcAndChargeCardNauthSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181026
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) CMS 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCMSSummary(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCalcAndChargeCMSSummary", pmParam);
    }


    /**
     * 특수회원 카운트조회
     * 정승철
     * 20181019
    */
    public int getCntSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getCntSpecialAcntList", pmParam);
    }

    /**
     * 특수회원 조회
     * 정승철
     * 20181018
    */
    public List<Map<String, Object>> getSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getSpecialAcntList", pmParam);
    }

    /**
     * 특수회원 입력체크
     * 정승철
     * 20181113
    */
    public int getChkSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getChkSpecialAcntList", pmParam);
    }

    /**
     * 특수회원 입력
     * 정승철
     * 20181019
    */
    public int insertExtSpecial(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertExtSpecial", pmParam);
    }

    /**
     * 특수회원 수정
     * 정승철
     * 20181019
    */
    public int updateExtSpecial(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateExtSpecial", pmParam);
    }

    /**
     * 특수회원 삭제
     * 정승철
     * 20181019
    */
    public int deleteExtSpecial(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteExtSpecial", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no) {
        return selectOne("DlwPayMap.getWdrwReqAccntConfirm", accnt_no);
    }

    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) {
        return selectList("DlwPayMap.getWdrwReqAccntEventConfirm", accnt_no);
    }

    /** ================================================================
     * 날짜 : 20181023
     * 이름 : 임동진
     * 추가내용 : 결과 데이터 확인 후 청구 테이블 결과 업데이트 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int uptReqResultStat(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.uptReqResultStat", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181023
     * 이름 : 임동진
     * 추가내용 : 결과 데이터 확인 후 청구 테이블 결과 업데이트 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int uptReqResultStat_R(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.uptReqResultStat_R", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181025
     * 이름 : 송준빈
     * 추가내용 : Card File List 수신리스트 결과 반영 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
     * ================================================================
     * */
    public int getWdrwResultCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getWdrwResultCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181029
     * 이름 : 정승철
     * 추가내용 : Card 결과로그 List 카운트 조회
     * ================================================================
     * */
    public int getDlwRltmCardLogCount(Map<String, Object> pmParam) { 
        return selectOne("DlwPayMap.getDlwRltmCardLogCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181029
     * 이름 : 정승철
     * 추가내용 : Card 결과로그 List 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwRltmCardLogList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getDlwRltmCardLogList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 자유결제 카드 결과데이터 INSERT
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
     * ================================================================
     * */
    public int insertFreeCardResult(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertFreeCardResult", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 자유결제 카드결과를 산출관리쪽에 UPDATE
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int updateReqWdrw(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateReqWdrw", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 실시간 카드결제 (자유결제) - 대상회원 상태 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getAccntStatByFreeCard(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getAccntStatByFreeCard", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 빌키생성 정보 조회
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getBillKeyCrtnInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getBillKeyCrtnInfo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 빌키 MID 와 KEY 조회
     * ================================================================
     * */
    public String getAccntMid(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getAccntMid", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181101
     * 이름 : 송준빈
     * 추가내용 : 두구좌 상품관리 현황 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getDoubleAccntListCount(Map<String, Object> pmParam) {
         return selectOne("DlwPayMap.getDoubleAccntListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181101
     * 이름 : 송준빈
     * 추가내용 : 두구좌 상품관리 현황 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getDoubleAccntList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getDoubleAccntList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181107
     * 이름 : 임동진
     * 추가내용 : 실시간 카드 결제 취소 완료 시 결과 반영
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public int updateCardResult(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateCardResult", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181113
     * 이름 : 송준빈
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwLogList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMainWdrwLogList", pmParam);
    }


    /** ================================================================
     * 날짜 : 20181114
     * 이름 : 정승철
     * 추가내용 : 결제 취소 완료 시 결과 반영 (CMS)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public int insertCmsResultByPayCancel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertCmsResultByPayCancel", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181116
     * 이름 : 정승철
     * 추가내용 : 결제 취소 완료 시 산출관리 반영
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int updateWdrwReqByPayCancel(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateWdrwReqByPayCancel", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181116
     * 이름 : 정승철
     * 추가내용 : CMS 환불완료시 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     *               LF_DMUSER.PAY_MNG
     *               LF_DMUSER.PAY_MNG_DTL
     *               LF_DMUSER.PAY_MNG_DTL1
     * ================================================================
     * */
    public int updateCmsPayCancel(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateCmsPayCancel", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181120
     * 이름 : 정승철
     * 추가내용 : 결제 취소시 가수금으로 등록
     * 대상 테이블 : LF_DMUSER.GASU_AMT_REG
     * ================================================================
     * */
    public int insertGasuAmtByPayCancel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertGasuAmtByPayCancel", pmParam);
    }



    /** ================================================================
     * 날짜 : 20181122
     * 이름 : 정승철
     * 추가내용 : 결제 취소 완료 시 결과 반영 (카드)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public int updateCardResultByPayCancel(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateCardResultByPayCancel", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181123
     * 이름 : 정승철
     * 추가내용 : 환불등록
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_FUND
     * ================================================================
     * */
    public int insertRefundByPayCancel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertRefundByPayCancel", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 결제 취소 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int insertNauthPayCancel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertNauthPayCancel", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 결제 취소건 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public List<Map<String, Object>> getNauthPayCancel(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getNauthPayCancel", pmParam);
    }

    /** ================================================================
     * 날짜 : 메인
     * 이름 : /dlw/cust/cust-acnt/list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int getMemberRefundListCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getMemberRefundListCount", pmParam);
    }

    public List<Map<String, Object>> getMemberRefundList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberRefundList", pmParam);
    }

    /** ================================================================
     * 날짜 : 메인
     * 이름 : /dlw/cms/gasu-dtl/list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */

    /**
     * 사원 상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 상세정보
     * @throws Exception
     */
    public Map<String, Object> getEmplDtptList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getEmplDtptList", pmParam);
    }
    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보 목록
     * @throws Exception
     */
    public String getDataAthrFunc(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getDataAthrFunc", pmParam);
    }
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAccntListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwPayMap.getDlwCustAccntListCount", pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAccntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getDlwCustAccntList", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-dtl/list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    /**
     *  가수금 환불 내역조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMemberRefundDetail(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberRefundDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-dtl/merge
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    /**
     *  가수 환불 상세 내역 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateMemberRefundDtl", pmParam);
    }

    /**
     *  가수 환불 상세 내역 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberRefundDtl", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-dtl/delete
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */

    /**
     *  가수금 환불 상세 내역 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.deleteMemberRefundDtl", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-mng/merge
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */

    /**
     *  가수 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMemberRefundMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberRefundMng", pmParam);
    }

    /**
     *  가수 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemberRefundMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.updateMemberRefundMng", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-mng/delete
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    /**
     *  가수 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteMemberRefundMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.deleteMemberRefundMng", pmParam);
    }

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw-syst/cd/cd-list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */

    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCodeListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwPayMap.getDlwCodeListCount", pmParam);
    }

    /**
     * 코드 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCodeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getDlwCodeList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181126
     * 이름 : 송준빈
     * 추가내용 : 해약,행사시 환불테이블 Insert 구문
     * 대상 테이블 : TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public int insertMemberReqRefund(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberReqRefund", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181127
     * 이름 : 정승철
     * 추가내용 : 회원의 환불회차정보를 검색
     * 대상 테이블 : TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getRefundReqNoOfAccnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getRefundReqNoOfAccnt", pmParam);
    }

    /**
     *  가상계좌 강제 입금등록
     *  임동진
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */

    public int updateVirtualDirectInsert(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateVirtualDirectInsert", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181204
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int getMbidCsvFileListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMbidCsvFileListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181204
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMbidCsvFileList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMbidCsvFileList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181204
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 다운로드시행자 업데이트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int updateDownLoadEmplFileList(Map<String, ?> pmParam) {
        return update("DlwPayMap.updateDownLoadEmplFileList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181204
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 다운로드시행자 이력정보 등록
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_DNDL_HIST
     * ================================================================
     * */
    public int insertDownLoadEmplHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertDownLoadEmplHist", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181213
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getCsvFileBatchDay(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getCsvFileBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181213
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int insertCsvFileBatchDay(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertCsvFileBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181213
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 처리일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int deleteCsvFileBatchDay(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteCsvFileBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181213
     * 이름 : 송준빈
     * 추가내용 : 일일청구기준데이터 유효성 체크
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int validationInsertCsvFile(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.validationInsertCsvFile", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20180407
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출시 전월 결과반영 여부 확인
     * 대상 테이블 : TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public int getPreMonthResultReflect(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getPreMonthResultReflect", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출 초기화
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int updateInitUplusPrepaymentData(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateInitUplusPrepaymentData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int insertUplusPrepaymentData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepaymentData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int getUplusPrepaymentDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getUplusPrepaymentDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusPrepaymentDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusPrepaymentDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190318
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 산출 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusPrepaymentDataListCalc(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusPrepaymentDataListCalc", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190118
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 파일 전송후 상태값 업데이트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int updateUplusPrepaymentDataList(Map<String, Object> pmParam) throws Exception {
        return update("DlwPayMap.updateUplusPrepaymentDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 미선납회원 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusNonPrepaymentMember(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusNonPrepaymentMember", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 KB_NO 가져오기
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getCurrntKbNo(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCurrntKbNo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 미선납회원 이관작업
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int insertUplusNonPrepaymentMember(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusNonPrepaymentMember", pmParam);
    }

    /** ================================================================
     * 날짜 : 20181220
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 미선납회원 이관작업후 업데이트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int updateUplusNonPrepaymentMember(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateUplusNonPrepaymentMember", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190117
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 마스터 데이터 입력
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public int insertUplusPrepaymentMst(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepaymentMst", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190118
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 전송한 파일 목록 조회(결과 미도착)
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public List<Map<String, Object>> getSendFileUplusPrepaymentList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getSendFileUplusPrepaymentList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190118
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 수신된 파일마스터 저장
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public int insertUplusPrepaymentRecvLog(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepaymentRecvLog", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190118
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 수신된 날짜의 데이터의 수신상태를 수신상태로 전환
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public int sendUplusPrepaymentFileUpdate(Map<String, Object> pmParam) {
        return update("DlwPayMap.sendUplusPrepaymentFileUpdate", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190118
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 해당월에 송/수신된 데이터 모두 보기
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public List<Map<String, Object>> getAllUplusPrepaymentList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getAllUplusPrepaymentList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 데이터 결과 반영
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int resultUplusPrepaymentDtlUpdate(Map<String, Object> pmParam) {
        return update("DlwPayMap.resultUplusPrepaymentDtlUpdate", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 결과반영 MASTER 상태 변경
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
    public int resultUplusPrepaymentMstUpdate(Map<String, Object> pmParam) {
        return update("DlwPayMap.resultUplusPrepaymentMstUpdate", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 결과가 오지 않은 데이터 업데이트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int noResultUplusPrepaymentFileChange(Map<String, Object> pmParam) {
        return update("DlwPayMap.noResultUplusPrepaymentFileChange", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191105
     * 이름 : 송준빈
     * 추가내용 : 해당 결과 파일의 정산 년월 확인
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_MST
     * ================================================================
     * */
	public List<Map<String, Object>> getUplusTargetCalcMonth(Map<String, Object> pmParam) {
		return selectList("DlwPayMap.getUplusTargetCalcMonth", pmParam);
	}

	/** ================================================================
     * 날짜 : 20191105
     * 이름 : 송준빈
     * 추가내용 : LGU+ 결과데이터 요청 결과 반영 
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
	public int updateResultReflectionRequestData(Map<String, Object> pmParam) {
		return update("DlwPayMap.updateResultReflectionRequestData", pmParam);
	}

    /** ================================================================
     * 날짜 : 20190102
     * 이름 : 송준빈
     * 추가내용 : 고객미납관리(NEW) 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MNG
     * ================================================================
     * */
    public int getMemberYencheMngDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberYencheMngDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190102
     * 이름 : 송준빈
     * 추가내용 : 고객미납관리(NEW) 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberYencheMngDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberYencheMngDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190121
     * 이름 : 송준빈
     * 추가내용 : 고객미납관리(NEW) 청구실행
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MNG
     * ================================================================
     * */
    public int insertMemberYencheMngDataList(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertMemberYencheMngDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190305
     * 이름 : 송준빈
     * 추가내용 : 고객미납이력(NEW) 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_HSTR
     * ================================================================
     * */
    public int getMemberYencheHstrDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberYencheHstrDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190305
     * 이름 : 송준빈
     * 추가내용 : 고객미납이력(NEW) 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_HSTR
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberYencheHstrDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberYencheHstrDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int getMemberMangiExtDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberMangiExtDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiExtDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberMangiExtDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 등록 여부 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int getMemberMangiExtConfirmCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberMangiExtConfirmCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiExtConfirmList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberMangiExtConfirmList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 엑셀 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int insertUserExcelUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertUserExcelUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190115
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 상품목록 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int insertProdExcelUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertProdExcelUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 발주,송장정보 엑셀 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int mergeOrderExcelUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.mergeOrderExcelUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 상품등록 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiGiftset(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberMangiGiftset", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 상품등록 팝업 등록
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    public int insertMemberMangiGiftset(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberMangiGiftset", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 상품등록 팝업 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    public int deleteMemberMangiGiftset(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteMemberMangiGiftset", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원정보 수정
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int updateMemberMangiExtDataList(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateMemberMangiExtDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190115
     * 이름 : 송준빈
     * 추가내용 : 만기연장 상품등록 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiProdDetail(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberMangiProdDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190116
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 상품 팝업 입력
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int insertMemberMangiProdDetail(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberMangiProdDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190116
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 상품 팝업 수정
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int updateMemberMangiProdDetail(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateMemberMangiProdDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190116
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 상품 팝업 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int deleteMemberMangiProdDetail(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteMemberMangiProdDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public int getPerfMainDataCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getPerfMainDataCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerfMainDataList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getPerfMainDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 :  장례이행보증현황 추가
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public int insertPerfMainDataList(Map<String, ?> pmParam) {
        return insert("DlwPayMap.insertPerfMainDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 수정
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public int updatePerfMainDataList(Map<String, ?> pmParam) {
        return update("DlwPayMap.updatePerfMainDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 삭제
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public int deletePerfMainDataList(Map<String, ?> pmParam) {
        return delete("DlwPayMap.deletePerfMainDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerfMainConfirmList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getPerfMainConfirmList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190225
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 (해약, 행사) 실지급금액 조회
     * 대상 테이블 : LF_DMUSER.TB_PERF_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerfMainPayAmt(String pmParam) {
        return selectList("DlwPayMap.getPerfMainPayAmt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190226
     * 이름 : 송준빈
     * 추가내용 : 증서출력-일괄출력 팝업 조회조건 Data 가져오기
     * 대상 테이블 : LF_DMUSER.CL_COND_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getCodeClCdDataset(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getCodeClCdDataset", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190226
     * 이름 : 송준빈
     * 추가내용 : 증서출력-일괄출력 증서상품등록 팝업 조회
     * 대상 테이블 : LF_DMUSER.CL_COND_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getClCondData(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getClCondData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190226
     * 이름 : 송준빈
     * 추가내용 : 증서출력-일괄출력 증서상품등록 팝업 등록
     * 대상 테이블 : LF_DMUSER.CL_COND_DATA
     * ================================================================
     * */
    public int insertClCondData(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertClCondData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190226
     * 이름 : 송준빈
     * 추가내용 : 증서출력-일괄출력 증서상품등록 팝업 삭제
     * 대상 테이블 : LF_DMUSER.CL_COND_DATA
     * ================================================================
     * */
    public int deleteClCondData(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteClCondData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190319
     * 이름 : 송준빈
     * 추가내용 : 카드 청구 파일 다운로드시 기준 파일 가져오기
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_EXT_CHECK
     * ================================================================
     * */
    public List<Map<String, Object>> getCardSendMakeFileDt(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCardSendMakeFileDt", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191212
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 청구 파일 다운로드시 기준 파일 가져오기
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_EXT_CHECK
     * ================================================================
     * */
    public List<Map<String, Object>> getCardNauthSendMakeFileDt(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCardNauthSendMakeFileDt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190401
     * 이름 : 송준빈
     * 추가내용 : 회원 정보 파일 리스트 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_UPLOAD_TEMP
     * ================================================================
     * */
    public int deleteRegTempData(Map<String, Object> pmParam) throws Exception {
        return delete("DlwPayMap.deleteRegTempData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190401
     * 이름 : 송준빈
     * 추가내용 : 회원 정보 파일 리스트 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_UPLOAD_TEMP
     * ================================================================
     * */
    public int insertMemberExcelUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertMemberExcelUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190401
     * 이름 : 송준빈
     * 추가내용 : 회원 입금정보 업로드 리스트 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_UPLOAD_TEMP
     * ================================================================
     * */
    public int getMemberUploadDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberUploadDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190401
     * 이름 : 송준빈
     * 추가내용 : 회원 입금정보 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_UPLOAD_TEMP
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberUploadDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberUploadDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 송준빈
     * 추가내용 : 고객 미납 월마감 전체 집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getTotalMemberList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getTotalMemberList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 송준빈
     * 추가내용 : 고객 미납 월마감 결합 집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getRelMemberList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getRelMemberList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 리스트 INSERT
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public int insertCardRecvFileList(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertCardRecvFileList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 리스트 조회
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getCardRecvFileList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getCardRecvFileList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 (무승인) 저장 여부
     * 대상 테이블 : LF_DMUSER.TB_CARDNAUTH_RECV_FILE_LIST
     * ================================================================
     * */
    public int getCardNauthRecvFileExist(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getCardNauthRecvFileExist", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 :  일일청구 카드 회신 데이터 리스트 UPDATE
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public int updateCardRecvFileList(Map<String, ?> pmParam) {
        return update("DlwPayMap.updateCardRecvFileList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 조회
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowBasicInfo(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getAlowBasicInfo", pmParam);
    }

    /** ================================================================
      * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 조회 전체 총합
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowBasicInfoSummary(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getAlowBasicInfoSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회수
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    public int getAlowBasicInfoDetailCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getAlowBasicInfoDetailCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowBasicInfoDetail(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getAlowBasicInfoDetail", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회 리스트 전체 총합
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowBasicInfoDetailSummary(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getAlowBasicInfoDetailSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190423
     * 이름 : 송준빈
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int getMonthAlowCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getMonthAlowCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190423
     * 이름 : 송준빈
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getMonthAlowList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getMonthAlowList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190423
     * 이름 : 송준빈
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int getAlowDetailCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getAlowDetailCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190423
     * 이름 : 송준빈
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowDetailList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getAlowDetailList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190423
     * 이름 : 송준빈
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getMonthAlowListSummary(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getMonthAlowListSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (회원별수당) 조회수
     * 대상 테이블 :
     * ================================================================
     * */
    public int getMemberSpecFeesCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getMemberSpecFeesCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (회원별수당) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberSpecFeesList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getMemberSpecFeesList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (회원별수당) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberSpecFeesSummary(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getMemberSpecFeesSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (사원별수당) 조회수
     * 대상 테이블 :
     * ================================================================
     * */
    public int getEmplSpecFeesCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getEmplSpecFeesCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (사원별수당) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getEmplSpecFeesList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getEmplSpecFeesList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (회원별수당) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getEmplSpecFeesSummary(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getEmplSpecFeesSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (이월환수) 조회수
     * 대상 테이블 :
     * ================================================================
     * */
    public int getCarryOverRefundCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getCarryOverRefundCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (이월환수) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getCarryOverRefundList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getCarryOverRefundList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190425
     * 이름 : 송준빈
     * 추가내용 : 회원별수당조회 (회원별수당) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getCarryOverRefundSummary(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getCarryOverRefundSummary", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191023
     * 이름 : 김주용
     * 추가내용 : 회원별수당조회 (재무현황) 조회수
     * 대상 테이블 :
     * ================================================================
     * */
    public int getFinancialStatusCount(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getFinancialStatusCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20191023
     * 이름 : 김주용
     * 추가내용 : 회원별수당조회 (재무현황) 리스트
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getFinancialStatusList(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getFinancialStatusList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190503
     * 이름 : 송준빈
     * 추가내용 : 수당/수수료 배치일자 조회
     * 대상 테이블 : LF_DMUSER.TB_FEES_CALC_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getFeesCalcBatchDay(Map<String, ?> pmParam) {
        return selectList("DlwPayMap.getFeesCalcBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190503
     * 이름 : 송준빈
     * 추가내용 : 수당/수수료 배치일자 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_FEES_CALC_BATCH_DAY
     * ================================================================
     * */
    public int getChkFeesCalcBatchDay(Map<String, ?> pmParam) {
        return selectOne("DlwPayMap.getChkFeesCalcBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190503
     * 이름 : 송준빈
     * 추가내용 : 수당/수수료 배치일자 추가
     * 대상 테이블 : LF_DMUSER.TB_FEES_CALC_BATCH_DAY
     * ================================================================
     * */
    public int insertFeesCalcBatchDay(Map<String, ?> pmParam) {
        return insert("DlwPayMap.insertFeesCalcBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황 배치일자 조회
     * 대상 테이블 : LF_DMUSER.TB_FINANCE_CALC_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getFinanceCalcBatchDay(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getFinanceCalcBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황 배치일자 등록
     * 대상 테이블 : LF_DMUSER.TB_FINANCE_CALC_BATCH_DAY
     * ================================================================
     * */
    public int insertFinanceCalcBatchDay(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertFinanceCalcBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황 배치일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_FINANCE_CALC_BATCH_DAY
     * ================================================================
     * */
    public int deleteFinanceCalcBatchDay(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteFinanceCalcBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황 배치일자 유효성체크
     * 대상 테이블 : LF_DMUSER.TB_FINANCE_CALC_BATCH_DAY
     * ================================================================
     * */
    public int validationInsertFinanceCalcBatch(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.validationInsertFinanceCalcBatch", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황데이터 엑셀파일 배치일자 조회
     * 대상 테이블 :
     * ================================================================
     * */
    public List<Map<String, Object>> getFinanceExcelBatchDay(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getFinanceExcelBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191101
     * 이름 : 김주용
     * 추가내용 : 재무현황 배치일자 등록
     * 대상 테이블 : LF_DMUSER.TB_FINANCE_CALC_BATCH_DAY
     * ================================================================
     * */
    public int updateFinanceCalcBatchDay(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.updateFinanceCalcBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190503
     * 이름 : 송준빈
     * 추가내용 : 수당/수수료 배치일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_FEES_CALC_BATCH_DAY
     * ================================================================
     * */
    public int deleteFeesCalcBatchDay(Map<String, ?> pmParam) {
        return delete("DlwPayMap.deleteFeesCalcBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190522
     * 이름 : 김주용
     * 추가내용 : 만기연장 상품조회 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiGiftsetList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberMangiGiftsetList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190508
     * 이름 : 정승철
     * 추가내용 : 수당수수료산출 상세데이터 입력
     * 대상 테이블 : TB_ALOW_BASIC_INFO
     * =================================================================
     * */
    public int insertAlowBasicInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertAlowBasicInfo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190508
     * 이름 : 정승철
     * 추가내용 : 수당수수료산출 상세데이터 수정
     * 대상 테이블 : TB_ALOW_BASIC_INFO
     * =================================================================
     * */
    public int updateAlowBasicInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwPayMap.updateAlowBasicInfo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190509
     * 이름 : 정승철
     * 추가내용 : 수당수수료산출 상세데이터 삭제
     * 대상 테이블 : TB_ALOW_BASIC_INFO
     * =================================================================
     * */
    public int deleteAlowBasicInfo(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteAlowBasicInfo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190508
     * 이름 : 정승철
     * 추가내용 : 수당수수료산출 상세데이터 히스토리 저장
     * 대상 테이블 : TB_ALOW_BASIC_INFO_HISTORY
     * =================================================================
     * */
    public int insertAlowBasicInfoHistory(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertAlowBasicInfoHistory", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190510
     * 이름 : 정승철
     * 추가내용 : 수당수수료 수기등록 중복체크
     * 대상 테이블 : TB_ALOW_BASIC_INFO
     * =================================================================
     * */
    public String chkAlowDupl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.chkAlowDupl", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190514
     * 이름 : 정승철
     * 추가내용 : 수당수수료 최종마감년월 조회
     * 대상 테이블 : ALOW_DA_DTL_DUMMY
     * =================================================================
     * */
    public String getAlowLastCloseYyyymm(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.getAlowLastCloseYyyymm", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 조회수
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int getUplusPrepayHoldDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getUplusPrepayHoldDataListCount", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusPrepayHoldDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusPrepayHoldDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int insertUplusPrepayHoldData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepayHoldData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 삭제
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int deleteUplusPrepayHoldDataList(Map<String, Object> pmParam) {
    	return delete("DlwPayMap.deleteUplusPrepayHoldDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 해제시 산출대상에 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int insertUplusHoldPrepaymentMember(Map<String, Object> pmParam) {
    	return delete("DlwPayMap.insertUplusHoldPrepaymentMember", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190610
     * 이름 : 김주용
     * 추가내용 : 만기연장 수수료 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_AMT
     * ================================================================
     * */
    public int getMemberMangiAmtDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getMemberMangiAmtDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190610
     * 이름 : 김주용
     * 추가내용 : 만기연장 수수료 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_AMT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiAmtDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getMemberMangiAmtDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190610
     * 이름 : 김주용
     * 추가내용 : 만기연장 수수료 산출 초기화
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_AMT
     * ================================================================
     * */
    public int updateMemberMangiAmtDataList(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateMemberMangiAmtDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190610
     * 이름 : 김주용
     * 추가내용 : 만기연장 수수료 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_AMT
     * ================================================================
     * */
    public int insertMemberMangiAmtDataList(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertMemberMangiAmtDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 사원별 수당관리 마스터 데이터
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getAlowEmpleMstList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getAlowEmpleMstList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 사원별 수당관리 디테일 데이터 (담당고객)
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getAlowEmpleDtlList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getAlowEmpleDtlList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 고객별 수당관리 마스터 데이터
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getAlowAccntNoMstList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getAlowAccntNoMstList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 고객별 수당관리 디테일 데이터
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getAlowAccntNoDtlList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getAlowAccntNoDtlList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190618
     * 이름 : 김주용
     * 추가내용 : 만기연장 관리회원 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int deleteMemberMangiExtDataList(Map<String, ?> pmParam) throws Exception {
        return delete("DlwPayMap.deleteMemberMangiExtDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190620
     * 이름 : 김주용
     * 추가내용 : 만기연장 관리회원 배송 엑셀 업로드
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int updateUserExcelDeliveryUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.updateUserExcelDeliveryUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190703
     * 이름 : 송준빈
     * 추가내용 : 수수료산출 상세 데이터2 조회수
     * 대상 테이블 : LF_DMUSER.TB_ALOW_PAY_INFO
     * ================================================================
     * */
	public int getAlowDetail2Count(Map<String, Object> pmParam) {
		return selectOne("DlwPayMap.getAlowDetail2Count", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190703
     * 이름 : 송준빈
     * 추가내용 : 수수료산출 상세 데이터2 리스트
     * 대상 테이블 : LF_DMUSER.TB_ALOW_PAY_INFO
     * ================================================================
     * */
	public List<Map<String, Object>> getAlowDetail2(Map<String, Object> pmParam) {
		return selectList("DlwPayMap.getAlowDetail2", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190708
     * 이름 : 김주용
     * 추가내용 : 만기연장회원에 대한 해약데이터 조회
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getMangiResnList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getMangiResnList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190708
     * 이름 : 김주용
     * 추가내용 : 만기연장회원 해약건에 대해 취소처리
     * 대상 테이블 :
     * ================================================================
     * */
    public int updateMemberMangiDataList(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateMemberMangiDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191213
     * 이름 : 김주용
     * 추가내용 : 해약,행사시 무승인 환불테이블 Insert 구문
     * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int insertNauthPayCancelResnEvnt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.insertNauthPayCancelResnEvnt", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191216
     * 이름 : 김주용
     * 추가내용 : 무승인 등록 취소
     * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int cancelNauthpayDelete(Map<String, Object> pmParam) {
        return update("DlwPayMap.cancelNauthpayDelete", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20191216
     * 이름 : 김주용
     * 추가내용 : 무승인 환불
     * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int cancelNauthpayRefund(Map<String, Object> pmParam) {
        return update("DlwPayMap.cancelNauthpayRefund", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200109
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 해제시 산출대상에 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
	public int insertFailUplusHoldData(Map<String, Object> pmParam) {
		return insert("DlwPayMap.insertFailUplusHoldData", pmParam);
	}

	/** ================================================================
     * 날짜 : 20200110
     * 이름 : 송준빈
     * 추가내용 : 산출시 KB_NO가 변경된 고객을 자동으로 리스트에 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
	public List<Map<String, Object>> getUplusHoldReleaseDataList(Map<String, ?> pmParam) {
		return selectList("DlwPayMap.getUplusHoldReleaseDataList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200110
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 해제시 산출대상에 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int insertUplusHoldPrepaymentMemberCalc(Map<String, Object> pmParam) {
    	return delete("DlwPayMap.insertUplusHoldPrepaymentMemberCalc", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200227
     * 이름 : 송준빈
     * 추가내용 : 회원별 카드,CMS 결과데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwAccntLogList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwPayMap.getWdrwAccntLogList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200409
     * 이름 : 김주용
     * 추가내용 :  일일청구 카드 무승인 결과메시지 UPDATE
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public int updateNauthResult(Map<String, ?> pmParam) {
        return update("DlwPayMap.updateNauthResult", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200622
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int existUplusPrepayHoldData(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwPayMap.existUplusPrepayHoldData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220126
     * 이름 : 임동진
     * 추가내용 : 만기연장 회원 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiInfo(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwPayMap.getMemberMangiInfo", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220207
     * 이름 : 임동진
     * 추가내용 : 만기연장 관리회원 등록 후 해약 정보 변경
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_DTL
     * ================================================================
     * */
    public int updateResnInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwPayMap.updateResnInfo", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220207
     * 이름 : 김주용
     * 추가내용 : U+ 선납전송관리 보류고객 일회성 데이터 삭제
     * 대상 테이블 : LF_DMUSER.TMP_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int deleteUplusPrepayHoldNewTmpData(Map<String, ?> pmParam) {
        return delete("DlwPayMap.deleteUplusPrepayHoldNewTmpData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220207
     * 이름 : 김주용
     * 추가내용 : U+ 선납전송관리 보류고객 등록
     * 대상 테이블 : LF_DMUSER.TMP_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int insertUplusPrepayHoldNewTmpData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepayHoldNewTmpData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220207
     * 이름 : 김주용
     * 추가내용 : U+ 선납전송관리 보류고객 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int insertUplusPrepayHoldNewData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusPrepayHoldNewData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220816
     * 이름 : 김주용
     * 추가내용 : 캐시백 산출 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_CASHBACK_LIST
     * ================================================================
     * */
    public int getUplusCashbackDataListCount(Map<String, Object> pmParam) {
        return selectOne("DlwPayMap.getUplusCashbackDataListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20220816
     * 이름 : 김주용
     * 추가내용 : 캐시백 산출 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_CASHBACK_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusCashbackDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusCashbackDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220816
     * 이름 : 김주용
     * 추가내용 : U+ 캐시백 산출 초기화
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_CASHBACK_LIST
     * ================================================================
     * */
    public int updateInitUplusCashbackData(Map<String, Object> pmParam) {
        return update("DlwPayMap.updateInitUplusCashbackData", pmParam);
    }

    /** ================================================================
     * 날짜 : 20220816
     * 이름 : 김주용
     * 추가내용 : U+ 캐시백 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_CASHBACK_LIST
     * ================================================================
     * */
    public int insertUplusCashbackData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertUplusCashbackData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220816
     * 이름 : 김주용
     * 추가내용 : 캐시백 보류고객 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_CASHBACK_HOLD_LIST
     * ================================================================
     * */
    public int insertgetUplusCashbackHoldNewData(Map<String, Object> pmParam) {
        return insert("DlwPayMap.insertgetUplusCashbackHoldNewData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 해제시 산출대상에 등록
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public int insertUplusHoldCashbackMember(Map<String, Object> pmParam) {
    	return delete("DlwPayMap.insertUplusHoldCashbackMember", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 삭제
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public int deleteUplusCashbackHoldDataList(Map<String, Object> pmParam) {
    	return delete("DlwPayMap.deleteUplusCashbackHoldDataList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190529
     * 이름 : 송준빈
     * 추가내용 : U+ 선납전송관리 보류고객 리스트
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PRYPAY_HOLD_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getUplusCashbackHoldDataList(Map<String, Object> pmParam) {
        return selectList("DlwPayMap.getUplusCashbackHoldDataList", pmParam);
    }
}