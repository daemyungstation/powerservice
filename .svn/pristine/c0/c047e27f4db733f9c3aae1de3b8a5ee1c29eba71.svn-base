/*
 * (@)# DlwEvntDAO.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class DlwEvntDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public int getEventCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEventCount", pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEventList", pmParam);
    }

    /**
     * 대명라이프웨이 행사통계
     *
     * @param pmParam 검색 조건
     * @return 행사 통계
     * @throws Exception
     */
    public List<Map<String, Object>> getEventStats(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEventStats", pmParam);
    }

    /**
     * 대명라이프웨이 행사 상세 정보
     *
     * @param pmParam 검색 조건
     * @return 행사 상세 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDetailEvent(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getDetailEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사 매니저 등록? 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtMngrRegList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtMngrRegList", pmParam);
    }

    /**
     * 대명라이프웨이 CP 직영, 외주 구분 조회
     *
     * @param pmParam 검색 조건
     * @return CP 직영, 외주 구분
     * @throws Exception
     */
    public String getCpGubun(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCpGubun", pmParam);
    }

    /**
     * 대명라이프웨이 직영 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirMst", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례용품) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirGdsList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(현장발주) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirGdsListB", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례식장) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirGdsListC", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(협력업체) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirGdsListD", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(기타(용품/인력)) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirGdsListE", pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출1 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirAddSale1List", pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirAddSale2List", pmParam);
    }

    /**
     * 대명라이프웨이 직영 일반경비 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirNormlCostList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirPayList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptDirPayInfo", pmParam);
    }

    /**
     * 대명라이프웨이 외주 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutMst", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutDtl1List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutDtl2List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutDtl4List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutDtl5List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRptOutPayInfo", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhMvYn(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getWhMvYn", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtCash(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getPayAmtCash", pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getFunrlHallCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getFunrlHallCount", pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFunrlHallList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getFunrlHallList", pmParam);
    }

    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvent(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvent(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateEvent", pmParam);
    }

    /**
     * 대명라이프웨이 마감데이터 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCloseDataSaveYnChk", pmParam);
    }

    /**
     * 대명라이프웨이 행사순번 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtSeq(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtSeq", pmParam);
    }

    /**
     * 대명라이프웨이 취소 내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCnclReg(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertCnclReg", pmParam);
    }

    /**
     * 대명라이프웨이 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getEventCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEventCheck", pmParam);
    }

    /**
     * 대명라이프웨이 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getResnCheck", pmParam);
    }

    /**
     * 대명라이프웨이 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getTaxtCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getTaxtCheck", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCmsCheck", pmParam);
    }

    /**
     * 대명라이프웨이 해약 시 CMS청구내역 신청전,신청중 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCmsReqCnt", pmParam);
    }

    /**
     * 대명라이프웨이 상품구분 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getProdCl", pmParam);
    }

    /**
     * 대명라이프웨이 jointype 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getJoinType(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getJoinType", pmParam);
    }

    /**
     * 대명라이프웨이 입금 총납입부금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntSearch(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAccntSearch", pmParam);
    }

    /**
     * 대명라이프웨이 행사 삭제 (MEM_PROD_ACCNT)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvent(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEvtNewYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEvtNewYnChk", pmParam);
    }

    /**
     * 대명라이프웨이 행사보고서 생성여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getIsEvtRptGen(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getIsEvtRptGen", pmParam);
    }

    /**
     * 대명라이프웨이 행사/해약 시 결합상품 할부원금 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCheckB2bPay(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCheckB2bPay", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getDlwManrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getDlwManrCount", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwMngrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getDlwMngrList", pmParam);
    }

    /**
     * 거래처 장례식장 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getFunrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getFunrCount", pmParam);
    }

    /**
     * 거래처 장례식장 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFunrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getFunrList", pmParam);
    }

    /**
     * 거래처 매입업체 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCustmrPopCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCustmrPopCount", pmParam);
    }

    /**
     * 거래처 매입업체 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustmrPopList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getCustmrPopList", pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCustmrOutCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getCustmrOutCount", pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustmrOutList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getCustmrOutList", pmParam);
    }

    /**
     * 행사자 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateevntMngr(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateevntMngr", pmParam);
    }

    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertevntMngr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertevntMngr", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntMngrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntMngrList", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 기초 수당자료 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntMngrPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntMngrPayList", pmParam);
    }

    /**
     * 행사자 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteRowMngr(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteRowMngr", pmParam);
    }

    /**
     * 품목 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getGoodsCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getGoodsCount", pmParam);
    }

    /**
     * 품목 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGoodsList", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab1JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab1JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab1(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab2JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab2JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab2(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab2(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab3JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab3JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab3(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab3(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab4JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab4JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab4(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab4(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab5JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab5JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab5(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab5(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab6JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab6JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab6(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab6(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab7JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab7JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab7(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab7(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab8JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab8JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab8(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab8(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab9JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getTab9JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab9(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateJiktab9", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab9(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertJiktab9", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab1(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab2(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab3(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab4(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab5(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab6(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab7(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab8(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab9(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsTab9", pmParam);
    }

    /**
     * 카드수수료 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCardFeeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getCardFeeList", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 관련 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds1(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsOutds1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds2(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsOutds2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds3(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsOutds3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds4(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteGoodsOutds4", pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getAnnlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getAnnlCount", pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAnnlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAnnlList", pmParam);
    }

    /**
     * 지부 콤보박스 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBranchList", pmParam);
    }

    /**
     * 행사정산 입금정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayData(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntPayData", pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntPayDtl", pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회(회차별)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntPayDtlCnt", pmParam);
    }

    /**
     * 행사보고서(직영) 상세 조회(입금정보 포함)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntRptMagam(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateEvntRptMagam", pmParam);
    }

    /**
     * 행사보고서(직영) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateEvntOutRptDtlMagam", pmParam);
    }

    /**
     * 행사보고서(외주) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteEvntOutRptDtl", pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertUpdBitHis(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertUpdBitHis", pmParam);
    }

    /**
     * 회원상태 변경(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemprodKstbit(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateMemprodKstbit", pmParam);
    }

    /**
     * 회원상태 변경(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateMemprodKstbit_cncl", pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertUpdBitHis_cncl", pmParam);
    }

    /**
     * 사업장내 창고이동 물품리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMoveGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getMoveGoodsList", pmParam);
    }

    /**
     * 사업장내 창고이동 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMoveGoodsMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getMoveGoodsMst", pmParam);
    }

    /**
     * 창고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhmvNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getWhmvNo", pmParam);
    }

    /**
     * 입고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhinNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getWhinNo", pmParam);
    }

    /**
     * 출고창고 코드 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhcd(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getWhcd", pmParam);
    }

    /**
     * 출고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhoutNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getWhoutNo", pmParam);
    }

    /**
     * 창고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhmvNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getSinWhmvNo", pmParam);
    }

    /**
     * 입고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhinNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getSinWhinNo", pmParam);
    }

    /**
     * 출고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhoutNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getSinWhoutNo", pmParam);
    }

    /**
     * 출고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhOutMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhOutMst", pmParam);
    }

    /**
     * 입고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhinMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhinMst", pmParam);
    }

    /**
     * 창고이동 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhmvMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhmvMst", pmParam);
    }

    /**
     * 출고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhOutDtl", pmParam);
    }

    /**
     * 입고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhinDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhinDtl", pmParam);
    }

    /**
     * 창고이동 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertWhmvDtl", pmParam);
    }

    /**
     * 창고이동 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhmvMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhmvMst", pmParam);
    }

    /**
     * 창고이동 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhmvDtl", pmParam);
    }

    /**
     * 출고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhOutMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhOutMst", pmParam);
    }

    /**
     * 출고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhOutDtl", pmParam);
    }

    /**
     * 입고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhinMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhinMst", pmParam);
    }

    /**
     * 입고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhinDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteWhinDtl", pmParam);
    }

    /**
     * 입금마감 팝업 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getPayTotalList", pmParam);
    }

    /**
     * 장법 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal1", pmParam);
    }

    /**
     * 신규유지 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal2", pmParam);
    }

    /**
     * 지부 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal3", pmParam);
    }

    /**
     * 지역 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal4", pmParam);
    }

    /**
     * 직영CP 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal5", pmParam);
    }

    /**
     * 종교 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal6(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntAnal6", pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회건수)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getAnnlCnclCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getAnnlCnclCount", pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회리스트)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAnnlCnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAnnlCnclList", pmParam);
    }

    /**
     * 지부별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntCncl1", pmParam);
    }

    /**
     * 취소사유별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntCncl2", pmParam);
    }

    /**
     * 소요시간별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntCncl3", pmParam);
    }

    /**
     * 물품 및 서비스제공현황 조회 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEventSrvProd1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEventSrvProd1", pmParam);
    }

    /**
     * CP등록 여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getEvntCpChkCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEvntCpChkCount", pmParam);
    }

    /**
     * 입금마감 - 상조부금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvntPaymng", pmParam);
    }

    /**
     * 입금마감 - 결합금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymngDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvntPaymngDtl", pmParam);
    }

    /**
     * 입금마감 - 추가금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvntPaymngDtl1", pmParam);
    }


    /**
     * 의전행사 사진 분류 - 행사회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPicSort(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntPicSort", pmParam);
    }

    /**
     * 의전행사 사진 분류 - 분류/미분류회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPicList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntPicList", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int disconncectAccnt(Map<String, Object> pmParam) throws Exception {
        return insert("DlwEvntMap.disconncectAccnt", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int conncectAccnt(Map<String, Object> pmParam) throws Exception {
        return insert("DlwEvntMap.conncectAccnt", pmParam);
    }

    /**
     * 의전행사 사진 삭제
     *
     * @param
     * @return
     * @throws Exception
     */
    public int deleteEvtPic(Map<String, Object> pmParam) throws Exception {
        return insert("DlwEvntMap.deleteEvtPic", pmParam);
    }

    /**
     * 의전행사 사진 삭제(행사일련번호로 삭제) - 행사취소 시 사용
     *
     * @param
     * @return
     * @throws Exception
     */
    public int deleteEvtPicByEventSeq(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.deleteEvtPicByEventSeq", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int insertEvtPicInfo(Map<String, Object> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvtPicInfo", pmParam);
    }
    
    
    public int insertEvtPicInfoNew(Map<String, Object> pmParam) throws Exception {
        return insert("DlwEvntMap.insertEvtPicInfoNew", pmParam);
    }
    
    
    public List<Map<String, Object>> getAccntMemNo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAccntMemNo", pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntBrchAnal1", pmParam);
    }

    /**
     * 상품분류명/지부별 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getbranchQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getbranchQury", pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntBrchAnal2", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntEmplAnal1", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEmplQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEmplQury", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntEmplAnal2", pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntBrchCncl1", pmParam);
    }

    /**
     * 행사취소분석(상품분류별/지부별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getbranchQuryCncl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getbranchQuryCncl", pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntBrchCncl2", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntEmplCncl1", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEmplQuryCncl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getEmplQuryCncl", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntEmplCncl2", pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getMngrIpCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getMngrIpCount", pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMngrIpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getMngrIpList", pmParam);
    }

    /**
     * 장례용품 재고현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getStockClCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getStockClCount", pmParam);
    }

    /**
     * 장례용품 재고현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockClList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getStockClList", pmParam);
    }

    /**
     * 추가매출 - 직영 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAddSalesInList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAddSalesInList", pmParam);
    }

    /**
     * 추가매출 - 외주 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAddSalesOutList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getAddSalesOutList", pmParam);
    }
    
    /**
     * 추가매출 - 장지 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFuneralList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getFuneralList", pmParam);
    }

    /**
     * 일자별 발주현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getOrderAnalCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getOrderAnalCount", pmParam);
    }

    /**
     * 일자별 발주현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOrderAnalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getOrderAnalList", pmParam);
    }

    /**
     * 행사잔금처리현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntRemainAmtList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntRemainAmtList", pmParam);
    }

    /**
     * 행사잔금처리현황 비고 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int RemainBigoUpdate(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.RemainBigoUpdate", pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getStockDayCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getStockDayCount", pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockDayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getStockDayList", pmParam);
    }

    /**
     * SMS 전송 시 결제정보 조회(현금영수증 정보)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getsmsPayMent(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getsmsPayMent", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList1", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList2", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList3", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 앰블런스
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList4", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 유골함
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList5", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList_Head", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList_func", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 장례식장(금액) 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_func2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdList_func2", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSrvProdList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getSrvProdList_qry", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdListIn(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdListIn", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdAnal1", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdAnal2", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdAnal3", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장례식장(CP)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdAnal4", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 월별분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getSrvProdAnal5", pmParam);
    }

    /**
     * 월별 지부별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMonthBrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getMonthBrList", pmParam);
    }

    /**
     * 월별 창고별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMonthWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getMonthWhList", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsIpList_Head", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsIpList_func", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getGdsIpList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getGdsIpList_qry", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsIpList", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsUseList_Head", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsUseList_func", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getGdsUseList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getGdsUseList_qry", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getGdsUseList", pmParam);
    }

    /**
     * CP별/일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCostList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getCostList", pmParam);
    }

    /**
     * 회원별 일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCostMemList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getCostMemList", pmParam);
    }

    /**
     * 손익분석집계표 - 행사별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBenefitList1", pmParam);
    }

    /**
     * 손익분석집계표 - 월별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBenefitList2", pmParam);
    }

    /**
     * 손익분석집계표 - 지부별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBenefitList3", pmParam);
    }

    /**
     * 손익분석집계표 - CP별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBenefitList4", pmParam);
    }

    /**
     * 손익분석집계표 - 외주업체별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getBenefitList5", pmParam);
    }

    /**
     * 재고마감 - 품목별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getStockGoodsList", pmParam);
    }

    /**
     * 재고마감 - 창고별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getStockWhList", pmParam);
    }

    /**
     * 재고마감 - 마감취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteStockMg(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteStockMg", pmParam);
    }

    /**
     * 재고마감 - 마감저장
     * 재고관리데이터 insert
     * @param pmParam
     * @throws Exception
     */
    public int insertStockMg(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.insertStockMg", pmParam);
    }

    /**
     * 발주번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getOrdNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getOrdNo", pmParam);
    }

    /**
     * 발주번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinOrdNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getSinOrdNo", pmParam);
    }

    /**
     * 발주 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteOrdMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteOrdMst", pmParam);
    }

    /**
     * 발주 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteOrdDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.deleteOrdDtl", pmParam);
    }

    /**
     * 발주 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOrdMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOrdMst", pmParam);
    }

    /**
     * 발주 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOrdDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertOrdDtl", pmParam);
    }

    /**
     * 대체용품 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateRepGoods(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMap.updateRepGoods", pmParam);
    }

    /**
     * 대체용품 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRepGoods(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.insertRepGoods", pmParam);
    }

    /**
     * 대체용품 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteRepGoods(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMap.deleteRepGoods", pmParam);
    }

    /**
     * 대체용품 회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRepList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtRepList", pmParam);
    }

    /**
     * 대체용품현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getRepGoodsCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMap.getRepGoodsCount", pmParam);
    }

    /**
     * 대체용품현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRepGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getRepGoodsList", pmParam);
    }

    /**
     * 행사유지현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtUjiList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvtUjiList", pmParam);
    }

    /**
     * 행사문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     */
    public List<Map<String, Object>> getEvntChatInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntChatInfo", pmParam);
    }

    /**
     * 행사 등록시 해당 회원번호로 행사테이블 조회
     *
     * @param hmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int countEvent(Map<String, Object> hmParam) throws Exception {
        return selectOne("DlwEvntMap.countEvent", hmParam);
    }
    /**
     * 문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntcjChatInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMap.getEvntcjChatInfo", pmParam);
    }
    /**
     * 알림톡 구동시 가상계좌 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEventcjChatVrtlInfo(Map<String, Object> hmParam) {
        return selectList("DlwEvntMap.getEventcjChatVrtlInfo", hmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateEventMemberState(Map<String, ?> pmParam) throws Exception { // DAO
        return update("DlwEvntMap.updateEventMemberState", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam) {
		return selectList("DlwEvntMap.getWdrwReqConfirm", pmParam);
	}
	
	public int existsRptDtlConfirm(Map<String, ?> pmParam) {
		return selectOne("DlwEvntMap.existsRptDtlConfirm", pmParam);
	} 
	
	public int existsOutRptDtlConfirm(Map<String, ?> pmParam) {
		return selectOne("DlwEvntMap.existsOutRptDtlConfirm", pmParam);
	} 
}
