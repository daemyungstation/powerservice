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

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwDeductionDAO extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     *
     * @param sqlSession DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 리스트 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeDayExtCount(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.getGongjeDayExtCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 리스트 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeDayExtList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    /*public int insertGongjeDayExt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwDeductionMap.insertGongjeDayExt", pmParam);
    }*/
    public int insertGongjeDayExt() throws Exception {
        return insert("DlwDeductionMap.insertGongjeDayExt");
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (신규/타사) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeDList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (해약) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeRList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeRList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (정보변경) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeUList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeUList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (양도양수) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeTList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeTList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (행사) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeEList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeEList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 (선수금) 엑셀생성 데이터
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getPayAccnt(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getPayAccnt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 산출스케줄 카운트조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeSched(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.chkGongjeSched", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 산출 스케줄 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeExtDt(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeExtDt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 정승철
     * 추가내용 : 공제 전송여부 카운트조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeSendCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwDeductionMap.getGongjeSendCnt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwDeductionMap.chkGongjeExtSched", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190408
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크 (** 공제결과 미처리 조회)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched_rsProcYn() throws Exception {
        return selectOne("DlwDeductionMap.chkGongjeExtSched_rsProcYn");
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 저장
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int saveGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        return insert("DlwDeductionMap.saveGongjeExtSched", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int delGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        return delete("DlwDeductionMap.delGongjeExtSched", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 진행중인 공제데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeSendingList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeSendingList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제구분별 최종결과반영 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeTypeLastResultList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeTypeLastResultList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과반영
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int getGongjeDayExtResultCount(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.getGongjeDayExtResultCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과반영
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int insertGongjeFileUpload(Map<String, ?> pmParam) throws Exception {
        return insert("DlwDeductionMap.insertGongjeFileUpload", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190213
     * 이름 : 임동진
     * 추가내용 : 공제 결과 업데이트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int uptGongResultStat(Map<String, ?> pmParam) throws Exception {
        return update("DlwDeductionMap.uptGongResultStat", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int getGongjeDayExtSendHistCount(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.getGongjeDayExtSendHistCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtSendHist(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeDayExtSendHist", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 신고 현황 데이터 (ASIS) 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int getGongjeAsisDataCount(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.getGongjeAsisDataCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataList(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeAsisDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 :  공제 데이터 전송이력 (ASIS) 추가
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int insertGongjeAsisDataList(Map<String, ?> pmParam) {
        return insert("DlwDeductionMap.insertGongjeAsisDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190313
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트 정상고객 총금액
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataListSummary(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeAsisDataListSummary", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 수정
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int updateGongjeAsisDataList(Map<String, ?> pmParam) {
        return update("DlwDeductionMap.updateGongjeAsisDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int deleteGongjeAsisDataList(Map<String, ?> pmParam) {
        return delete("DlwDeductionMap.deleteGongjeAsisDataList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 기존 누적 선수금 조회
     * 대상 테이블 : LF_DMUSER.GONGJE_MG
     * ================================================================
     * */
    public String getGongjeBefTotalAmt(Map<String, ?> pmParam) {
        return selectOne("DlwDeductionMap.getGongjeBefTotalAmt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 선수금 및 구분별 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeMonthReport(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getGongjeMonthReport", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 구분별 카운트조회
     * ================================================================
     * */
    public List<Map<String, Object>> getCntGongjeMonthReport(Map<String, ?> pmParam) {
        return selectList("DlwDeductionMap.getCntGongjeMonthReport", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190311
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 마감처리
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public int insertGongjeClose(Map<String, ?> pmParam) {
        return insert("DlwDeductionMap.insertGongjeClose", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190312
     * 이름 : 정승철
     * 추가내용 : 공제 마감처리할 정산년월 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public String getNextGongjeCloseYYYYMM() {
        return selectOne("DlwDeductionMap.getNextGongjeCloseYYYYMM");
    }

    /** ================================================================
     * 날짜 : 20190321
     * 이름 : 정승철
     * 추가내용 : 정상적인 공제 ASIS-DATA 총 금액 및 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getSumGongjeAsisData() {
        return selectList("DlwDeductionMap.getSumGongjeAsisData");
    }

    /** ================================================================
     * 날짜 : 20190403
     * 이름 : 송준빈
     * 추가내용 : 공제 마감 처리후 ASIS의 정보를 ORIGIN으로 이관
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ORIGIN
     * ================================================================
     * */
    public int insertGongjeAsisToOrigin(Map<String, ?> pmParam) {
        return insert("DlwDeductionMap.insertGongjeAsisToOrigin", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 정승철
     * 추가내용 : 공제결과 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int deleteGongjeResult() throws Exception {
        return delete("DlwDeductionMap.deleteGongjeResult");
    }

}