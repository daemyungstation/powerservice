/*
 * (@)# DlwCustDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
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

package powerservice.business.gongje.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 공제정보를 조회한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 김준혁
 * @version 1.0
 * @date 2016/09/05
 * @프로그램ID Gongje
 */
@Repository
public class GongjeDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="gongSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 공제 - Temp테이블 삭제처리
     *
     * @param pmParam 검색 조건
     * @return 데이터삭제(초기화)
     * @throws Exception
     */
    public int getGongjeTempDelete() throws Exception {
        return delete("GongjeSinMap.getGongjeTempDelete");
    }

    /**
     * 공제-신규건 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 신규 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeNewInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeSinMap.getGongjeNewInsert_Temp", pmParam);
    }

    /**
     * 공제-신규건 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getGongjeNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeSinMap.getGongjeNewCount", pmParam);
    }

    /**
     * 공제-신규건 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeNewList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeSinMap.getGongjeNewList", pmParam);
    }

    /**
     * 공제-신규건 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 신규 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeNewInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeSinMap.getGongjeNewInsert", pmParam);
    }

    /**
     * 공제-해약건 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 해약 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeRessInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeRessMap.getGongjeRessInsert_Temp", pmParam);
    }

    /**
     * 공제-해약건 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 해약 신고 건수
     * @throws Exception
     */
    public int getGongjeRessCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeRessMap.getGongjeRessCount", pmParam);
    }

    /**
     * 공제-해약건 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 해약 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeRessList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeRessMap.getGongjeRessList", pmParam);
    }

    /**
     * 공제-해약건 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 해약 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeRessInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeRessMap.getGongjeRessInsert", pmParam);
    }

    /**
     * 공제-소비자정보 변경건 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 소비자정보 변경 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeChngInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeChngMap.getGongjeChngInsert_Temp", pmParam);
    }

    /**
     * 공제-소비자정보 변경건 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 소비자정보 변경신고 건수
     * @throws Exception
     */
    public int getGongjeChngCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeChngMap.getGongjeChngCount", pmParam);
    }

    /**
     * 공제-소비자정보 변경건 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 소비자정보 변경 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeChngList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeChngMap.getGongjeChngList", pmParam);
    }

    /**
     * 공제-소비자정보 변경건 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 소비자정보 변경 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeChngInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeChngMap.getGongjeChngInsert", pmParam);
    }

    /**
     * 공제-양도양수 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 양도양수 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeYdsInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeYdsMap.getGongjeYdsInsert_Temp", pmParam);
    }

    /**
     * 공제-양도양수 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 양도양수 건수
     * @throws Exception
     */
    public int getGongjeYdsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeYdsMap.getGongjeYdsCount", pmParam);
    }

    /**
     * 공제-양도양수 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 양도양수 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeYdsList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeYdsMap.getGongjeYdsList", pmParam);
    }

    /**
     * 공제-양도양수 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 양도양수 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeYdsInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeYdsMap.getGongjeYdsInsert", pmParam);
    }

    /**
     * 공제-행사 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 행사 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeHangsaInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeHangsaMap.getGongjeHangsaInsert_Temp", pmParam);
    }

    /**
     * 공제-행사 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 행사 건수
     * @throws Exception
     */
    public int getGongjeHangsaCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHangsaMap.getGongjeHangsaCount", pmParam);
    }

    /**
     * 공제-행사 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 행사 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHangsaList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHangsaMap.getGongjeHangsaList", pmParam);
    }

    /**
     * 공제-행사 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 행사 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeHangsaInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeHangsaMap.getGongjeHangsaInsert", pmParam);
    }

    /**
     * 공제-선수금 조회시 Temp Table Insert
     *
     * @param pmParam 검색 조건
     * @return 선수금 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjePayInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return insert("GongjePayMap.getGongjePayInsert_Temp", pmParam);
    }

    /**
     * 공제-선수급 납입 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 건수
     * @throws Exception
     */
    public int getGongjePayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjePayMap.getGongjePayCount", pmParam);
    }

    /**
     * 공제-선수급 납입 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePayList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjePayMap.getGongjePayList", pmParam);
    }

    /**
     * 공제-선수금 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 선수금 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjePayInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjePayMap.getGongjePayInsert", pmParam);
    }

    /**
     * 공제-선수급 취소 납입 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 건수
     * @throws Exception
     */
    public int getGongjePay_CnclCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjePayMap.getGongjePay_CnclCount", pmParam);
    }

    /**
     * 공제-선수급 취소 납입 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePay_CnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjePayMap.getGongjePay_CnclList", pmParam);
    }

    /**
     * 공제-선수금 취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 선수금 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjePay_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjePayMap.getGongjePay_CnclInsert", pmParam);
    }

    /**
     * 공제-해약 취소 납입 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeRess_CnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeRessMap.getGongjeRess_CnclList", pmParam);
    }

    /**
     * 공제-해약 취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 선수금 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeRess_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeRessMap.getGongjeRess_CnclInsert", pmParam);
    }

    /**
     * 공제-행사 취소 납입 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHangsa_CnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHangsaMap.getGongjeHangsa_CnclList", pmParam);
    }

    /**
     * 공제-행사 취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 선수금 히스토리 INSERT
     * @throws Exception
     */
    public int getGongjeHangsa_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return insert("GongjeHangsaMap.getGongjeHangsa_CnclInsert", pmParam);
    }

    /**
     * 공제 - 엑셀반출전 미전송 데이터 건수 체크(리턴)
     *
     * @param pmParam 검색 조건
     * @return 미전송 데이터 건수
     * @throws Exception
     */
    public int getGongjeExcelchkCount() throws Exception {
        return (Integer) selectOne("GongjeSendMap.getGongjeExcelchkCount", "");
    }

    /**
     * 공제 - 엑셀데이터(미전송) 삭제처리
     *
     * @param pmParam 검색 조건
     * @return 엑셀데이터 삭제(미전송)
     * @throws Exception
     */
    public int getGongjeExcelcancel() throws Exception {
        return delete("GongjeSendMap.getGongjeExcelcancel");
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(엑셀 -> 전송) 변경
     * @throws Exception
     */
    public int getGongjeNewUpdate(Map<String, ?> pmParam) throws Exception {
        return update("GongjeSendMap.getGongjeNewUpdate", pmParam);
    }

    /**
     * 공제 - (선수금) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(엑셀 -> 전송) 변경
     * @throws Exception
     */
    public int getGongjePayUpdate(Map<String, ?> pmParam) throws Exception {
        return update("GongjeSendMap.getGongjePayUpdate", pmParam);
    }

    /**
     * 공제 - (선수금취소) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(엑셀 -> 전송) 변경
     * @throws Exception
     */
    public int getGongjePayCnclUpdate(Map<String, ?> pmParam) throws Exception {
        return update("GongjeSendMap.getGongjePayCnclUpdate", pmParam);
    }

    /**
     * 공제-전송이력 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 건수
     * @throws Exception
     */
    public int getGongjeHistCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeHistCount", pmParam);
    }

    /**
     * 공제-전송이력 납입 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 선수급 납입 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHistList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeHistList", pmParam);
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리 데이터 건수 체크
     *
     * @param pmParam 검색 조건
     * @return 결과처리 데이터 건수 체크
     * @throws Exception
     */
    public int getGongjeResultchkCount() throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeResultchkCount", "");
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(전송 -> 처리) 결과처리
     * @throws Exception
     */
    public int getGongjeBaseResult() throws Exception {
        return update("GongjeHistMap.getGongjeBaseResult", "");
    }

    /**
     * 공제 - (선수금) 결과처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(전송 -> 처리) 결과처리
     * @throws Exception
     */
    public int getGongjeBaseResult_Pay() throws Exception {
        return update("GongjeHistMap.getGongjeBaseResult_Pay", "");
    }

    /**
     * (신규/해약/정보변경/양도양수/행사) 테이블 각 결과처리
     *
     * @param pmParam 검색 조건
     * @return 전송상태(전송 -> 처리) 결과처리
     * @throws Exception
     */
    // 회원테이블 반영
    public int getGongjeSinResult() throws Exception {
        return update("GongjeHistMap.getGongjeSinResult", "");
    }
    // 해약테이블 반영
    public int getGongjeRessResult() throws Exception {
        return update("GongjeHistMap.getGongjeRessResult", "");
    }
    // 변경테이블 반영
    public int getGongjeChngResult() throws Exception {
        return update("GongjeHistMap.getGongjeChngResult", "");
    }
    // 행사테이블 반영
    public int getGongjeHangsaResult() throws Exception {
        return update("GongjeHistMap.getGongjeHangsaResult", "");
    }
    // 해약/행사 성공시 회원정보 업데이트
    public int getGongjeMemProdUpdate() throws Exception {
        return update("GongjeHistMap.getGongjeMemProdUpdate", "");
    }
    // 양도양수테이블 반영
    public int getGongjeYdsResult() throws Exception {
        return update("GongjeHistMap.getGongjeYdsResult", "");
    }
    // 양도양수 공제번호 반영
    public int getGongjeYds_New_Result() throws Exception {
        return update("GongjeHistMap.getGongjeYds_New_Result", "");
    }
    // 공제전송내역 결과처리 비트/일시/처리사원 업데이트
    public int getGongjeHistResult() throws Exception {
        return update("GongjeHistMap.getGongjeHistResult", "");
    }
    // 공제결과처리내역 처리비트 업데이트
    public int getGongjeLastResult() throws Exception {
        return update("GongjeHistMap.getGongjeLastResult", "");
    }

    /**
     * 입금 테이블 결과반영
     *
     * @param pmParam 검색 조건
     * @return 공제신고비트::NEW_YN (Y -> N)
     * @throws Exception
     */
    // 입금 테이블 반영
    public int getGongjePayResult() throws Exception {
        return update("GongjeHistMap.getGongjePayResult", "");
    }

    /**
     * XML 결과 업로드
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    // 업로드 체크
    public int getGongjeUploadchkCount() throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeUploadchkCount", "");
    }
    // 업로드
    public int insertFile(List<Map<String, Object>> pmParam) throws Exception {
        return insert("GongjeHistMap.insertFile", pmParam);
    }
    // 공제진행내역 비트 업데이트
    public int getGongjeUploadResult(Map<String, ?> hmParam) throws Exception {
        return update("GongjeHistMap.getGongjeUploadResult", hmParam);
    }

    /**
     * 선수금 엑셀 다운 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 엑셀 다운 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePayExcel(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjePayMap.getGongjePayList", pmParam);
    }

    /**
     * 월별보고서 마감자료 선수금 조회
     *
     * @param pmParam 검색 조건
     * @return 엑셀 다운 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeMagamPay(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeMagamPay", pmParam);
    }

    /**
     * 월별보고서 마감자료 회원 조회
     *
     * @param pmParam 검색 조건
     * @return 엑셀 다운 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeMagamMem(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeMagamMem", pmParam);
    }

    /**
     * 월별 보고서 신규내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getReportNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getReportNewCount", pmParam);
    }

    /**
     * 월별 보고서 신규내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getReportNewList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getReportNewList", pmParam);
    }

    /**
     * 월별 보고서 해약내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getReportResCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getReportResCount", pmParam);
    }

    /**
     * 월별 보고서 해약내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getReportResList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getReportResList", pmParam);
    }

    /**
     * 월별 보고서 행사내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getReportEvntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getReportEvntCount", pmParam);
    }

    /**
     * 월별 보고서 행사내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getReportEvntList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getReportEvntList", pmParam);
    }

    /**
     * 월별 보고서 기타환불내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getReportCnclCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getReportCnclCount", pmParam);
    }

    /**
     * 월별 보고서 기타 환불내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getReportCnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getReportCnclList", pmParam);
    }

    /**
     * 월별 보고서 CMS 이체내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getGongjeReportCmsPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeReportCmsPayCount", pmParam);
    }

    /**
     * 월별 보고서 CMS 이체내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportCmsPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeReportCmsPayList", pmParam);
    }

    /**
     * 월별 보고서 현금 등 기타내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getGongjeReportOtPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeReportOtPayCount", pmParam);
    }

    /**
     * 월별 보고서 현금 등 기타내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportOtPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeReportOtPayList", pmParam);
    }

    /**
     * 월별 보고서 누적 선수금내역 건수를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 건수
     * @throws Exception
     */
    public int getGongjeReportTotalPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GongjeHistMap.getGongjeReportTotalPayCount", pmParam);
    }

    /**
     * 월별 보고서 누적 선수금내역 정보를 조회
     *
     * @param pmParam 검색 조건
     * @return 신규 신고 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportTotalPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("GongjeHistMap.getGongjeReportTotalPayList", pmParam);
    }

}
