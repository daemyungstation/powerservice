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
public class DlwNewTypeEvntDAO extends EgovAbstractMapper {

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
    public int getNewTypeEventCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEventCount", pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEventList", pmParam);
    }

    /**
     * 대명라이프웨이 행사통계
     *
     * @param pmParam 검색 조건
     * @return 행사 통계
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventStats(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEventStats", pmParam);
    }

    /**
     * 대명라이프웨이 행사 상세 정보
     *
     * @param pmParam 검색 조건
     * @return 행사 상세 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeDetailEvent(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeDetailEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사 매니저 등록? 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtMngrRegList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtMngrRegList", pmParam);
    }

    /**
     * 대명라이프웨이 CP 직영, 외주 구분 조회
     *
     * @param pmParam 검색 조건
     * @return CP 직영, 외주 구분
     * @throws Exception
     */
    public String getNewTypeCpGubun(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCpGubun", pmParam);
    }

    /**
     * 대명라이프웨이 직영 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirMst", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례용품) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirGdsList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(현장발주) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirGdsListB", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례식장) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirGdsListC", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(협력업체) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirGdsListD", pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(기타(용품/인력)) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirGdsListE", pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출1 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirAddSale1List", pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirAddSale2List", pmParam);
    }

    /**
     * 대명라이프웨이 직영 일반경비 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirNormlCostList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirPayList", pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptDirPayInfo", pmParam);
    }

    /**
     * 대명라이프웨이 외주 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutMst", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutDtl1List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutDtl2List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutDtl4List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutDtl5List", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRptOutPayInfo", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhMvYn(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeWhMvYn", pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypePayAmtCash(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypePayAmtCash", pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeFunrlHallCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeFunrlHallCount", pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeFunrlHallList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeFunrlHallList", pmParam);
    }

    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeEvent", pmParam);
    }

    /**
     * 대명라이프웨이 마감데이터 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCloseDataSaveYnChk", pmParam);
    }

    /**
     * 대명라이프웨이 행사순번 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtSeq(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtSeq", pmParam);
    }

    /**
     * 대명라이프웨이 취소 내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeCnclReg(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeCnclReg", pmParam);
    }

    /**
     * 대명라이프웨이 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeEventCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEventCheck", pmParam);
    }

    /**
     * 대명라이프웨이 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeResnCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeResnCheck", pmParam);
    }

    /**
     * 대명라이프웨이 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeTaxtCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeResnCheck", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCmsCheck", pmParam);
    }

    /**
     * 대명라이프웨이 해약 시 CMS청구내역 신청전,신청중 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCmsReqCnt", pmParam);
    }

    /**
     * 대명라이프웨이 상품구분 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeProdCl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeProdCl", pmParam);
    }

    /**
     * 대명라이프웨이 jointype 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeJoinType(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeJoinType", pmParam);
    }

    /**
     * 대명라이프웨이 입금 총납입부금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccntSearch(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAccntSearch", pmParam);
    }

    /**
     * 대명라이프웨이 행사 삭제 (MEM_PROD_ACCNT)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeEvent", pmParam);
    }

    /**
     * 대명라이프웨이 행사 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEvtNewYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEvtNewYnChk", pmParam);
    }

    /**
     * 대명라이프웨이 행사보고서 생성여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeIsEvtRptGen(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeIsEvtRptGen", pmParam);
    }

    /**
     * 대명라이프웨이 행사/해약 시 결합상품 할부원금 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeCheckB2bPay(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCheckB2bPay", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeDlwManrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeDlwManrCount", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeDlwMngrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeDlwMngrList", pmParam);
    }

    /**
     * 거래처 장례식장 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeFunrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeFunrCount", pmParam);
    }

    /**
     * 거래처 장례식장 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeFunrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeFunrList", pmParam);
    }

    /**
     * 거래처 매입업체 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCustmrPopCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCustmrPopCount", pmParam);
    }

    /**
     * 거래처 매입업체 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCustmrPopList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeCustmrPopList", pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCustmrOutCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeCustmrOutCount", pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCustmrOutList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeCustmrOutList", pmParam);
    }

    /**
     * 행사자 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeevntMngr(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeevntMngr", pmParam);
    }

    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeevntMngr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeevntMngr", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntMngrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntMngrList", pmParam);
    }

    /**
     * 대명라이프웨이 행사자 기초 수당자료 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntMngrPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntMngrPayList", pmParam);
    }

    /**
     * 행사자 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeRowMngr(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeRowMngr", pmParam);
    }

    /**
     * 품목 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeGoodsCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeGoodsCount", pmParam);
    }

    /**
     * 품목 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGoodsList", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab1JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab1JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab1(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab2JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab2JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab2(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번쩨TAB (현장발주) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab2(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab3JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab3JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab3(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab3(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab4JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab4JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab4(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab4(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab5JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab5JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab5(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab5(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab6JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab6JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab6(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab6(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab7JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab7JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab7(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab7(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab8JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab8JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab8(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab8(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab9JikList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeTab9JikList", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab9(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeJiktab9", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab9(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeJiktab9", pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab1(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab1", pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab2(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab2", pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab3(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab3", pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab4(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab4", pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab5(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab5", pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab6(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab6", pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab7(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab7", pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab8(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab8", pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab9(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsTab9", pmParam);
    }

    /**
     * 카드수수료 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCardFeeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeCardFeeList", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 관련 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOutsrc_1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOutsrc_2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOutsrc_3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOutsrc_4", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds1(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsOutds1", pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds2(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsOutds2", pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds3(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsOutds3", pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds4(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeGoodsOutds4", pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeAnnlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeAnnlCount", pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAnnlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAnnlList", pmParam);
    }

    /**
     * 지부 콤보박스 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBranchList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBranchList", pmParam);
    }

    /**
     * 행사정산 입금정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayData(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntPayData", pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntPayDtl", pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회(회차별)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntPayDtlCnt", pmParam);
    }

    /**
     * 행사보고서(직영) 상세 조회(입금정보 포함)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntRptMagam(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeEvntRptMagam", pmParam);
    }

    /**
     * 행사보고서(직영) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(직영) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeEvntRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeEvntOutRptDtl", pmParam);
    }

    /**
     * 행사보고서(외주) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeEvntOutRptDtlMagam", pmParam);
    }

    /**
     * 행사보고서(외주) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeEvntOutRptDtl", pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeUpdBitHis(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeUpdBitHis", pmParam);
    }

    /**
     * 회원상태 변경(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeMemprodKstbit(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeMemprodKstbit", pmParam);
    }

    /**
     * 회원상태 변경(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeMemprodKstbit_cncl", pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeUpdBitHis_cncl", pmParam);
    }

    /**
     * 사업장내 창고이동 물품리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMoveGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeMoveGoodsList", pmParam);
    }

    /**
     * 사업장내 창고이동 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMoveGoodsMst(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeMoveGoodsMst", pmParam);
    }

    /**
     * 창고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhmvNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeWhmvNo", pmParam);
    }

    /**
     * 입고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhinNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeWhinNo", pmParam);
    }

    /**
     * 출고창고 코드 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhcd(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeWhcd", pmParam);
    }

    /**
     * 출고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhoutNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeWhoutNo", pmParam);
    }

    /**
     * 창고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhmvNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeSinWhmvNo", pmParam);
    }

    /**
     * 입고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhinNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeSinWhinNo", pmParam);
    }

    /**
     * 출고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhoutNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeSinWhoutNo", pmParam);
    }

    /**
     * 출고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhOutMst", pmParam);
    }

    /**
     * 입고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhinMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhinMst", pmParam);
    }

    /**
     * 창고이동 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhmvMst", pmParam);
    }

    /**
     * 출고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhOutDtl", pmParam);
    }

    /**
     * 입고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhinDtl", pmParam);
    }

    /**
     * 창고이동 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeWhmvDtl", pmParam);
    }

    /**
     * 창고이동 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhmvMst", pmParam);
    }

    /**
     * 창고이동 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhmvDtl", pmParam);
    }

    /**
     * 출고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhOutMst", pmParam);
    }

    /**
     * 출고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhOutDtl", pmParam);
    }

    /**
     * 입고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhinMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhinMst", pmParam);
    }

    /**
     * 입고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeWhinDtl", pmParam);
    }

    /**
     * 입금마감 팝업 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypePayTotalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypePayTotalList", pmParam);
    }

    /**
     * 장법 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal1", pmParam);
    }

    /**
     * 신규유지 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal2", pmParam);
    }

    /**
     * 지부 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal3", pmParam);
    }

    /**
     * 지역 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal4", pmParam);
    }

    /**
     * 직영CP 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal4", pmParam);
    }

    /**
     * 종교 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal6(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntAnal6", pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회건수)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeAnnlCnclCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeAnnlCnclCount", pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회리스트)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAnnlCnclList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAnnlCnclList", pmParam);
    }

    /**
     * 지부별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntCncl1", pmParam);
    }

    /**
     * 취소사유별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntCncl2", pmParam);
    }

    /**
     * 소요시간별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntCncl3", pmParam);
    }

    /**
     * 물품 및 서비스제공현황 조회 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventSrvProd1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEventSrvProd1", pmParam);
    }

    /**
     * CP등록 여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeEvntCpChkCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEvntCpChkCount", pmParam);
    }

    /**
     * 입금마감 - 상조부금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvntPaymng", pmParam);
    }

    /**
     * 입금마감 - 결합금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymngDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvntPaymngDtl", pmParam);
    }

    /**
     * 입금마감 - 추가금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvntPaymngDtl1", pmParam);
    }


    /**
     * 의전행사 사진 분류 - 행사회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPicSort(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntPicSort", pmParam);
    }

    /**
     * 의전행사 사진 분류 - 분류/미분류회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPicList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntPicList", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int disconncectNewTypeAccnt(Map<String, Object> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.disconncectNewTypeAccnt", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int conncectNewTypeAccnt(Map<String, Object> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.conncectNewTypeAccnt", pmParam);
    }

    /**
     * 의전행사 사진 삭제
     *
     * @param
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvtPic(Map<String, Object> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.deleteNewTypeEvtPic", pmParam);
    }

    /**
     * 의전행사 사진 삭제(행사일련번호로 삭제) - 행사취소 시 사용
     *
     * @param
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvtPicByEventSeq(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.deleteNewTypeEvtPicByEventSeq", pmParam);
    }

    /**
     * sss
     *
     * @param
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvtPicInfo(Map<String, Object> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvtPicInfo", pmParam);
    }
    
    
    public int insertNewTypeEvtPicInfoNew(Map<String, Object> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeEvtPicInfoNew", pmParam);
    }
    
    
    public List<Map<String, Object>> getNewTypeAccntMemNo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAccntMemNo", pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntBrchAnal1", pmParam);
    }

    /**
     * 상품분류명/지부별 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypebranchQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypebranchQury", pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntBrchAnal2", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntEmplAnal1", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEmplQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEmplQury", pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntEmplAnal2", pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntBrchCncl1", pmParam);
    }

    /**
     * 행사취소분석(상품분류별/지부별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypebranchQuryCncl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypebranchQuryCncl", pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntBrchCncl2", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplCncl1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntEmplCncl1", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEmplQuryCncl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeEmplQuryCncl", pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplCncl2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntEmplCncl2", pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeMngrIpCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeMngrIpCount", pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMngrIpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeMngrIpList", pmParam);
    }

    /**
     * 장례용품 재고현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeStockClCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeStockClCount", pmParam);
    }

    /**
     * 장례용품 재고현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockClList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeStockClList", pmParam);
    }

    /**
     * 추가매출 - 직영 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAddSalesInList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAddSalesInList", pmParam);
    }

    /**
     * 추가매출 - 외주 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAddSalesOutList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeAddSalesOutList", pmParam);
    }

    /**
     * 일자별 발주현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeOrderAnalCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeOrderAnalCount", pmParam);
    }

    /**
     * 일자별 발주현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOrderAnalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeOrderAnalList", pmParam);
    }

    /**
     * 행사잔금처리현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntRemainAmtList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntRemainAmtList", pmParam);
    }

    /**
     * 행사잔금처리현황 비고 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int RemainNewTypeBigoUpdate(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.RemainNewTypeBigoUpdate", pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeStockDayCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeStockDayCount", pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockDayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeStockDayList", pmParam);
    }

    /**
     * SMS 전송 시 결제정보 조회(현금영수증 정보)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypesmsPayMent(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypesmsPayMent", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList1", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList2", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList3", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 앰블런스
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList4", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 유골함
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList5", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList_Head", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList_func", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 장례식장(금액) 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_func2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdList_func2", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSrvProdList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeSrvProdList_qry", pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdListIn(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdListIn", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdAnal1", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdAnal2", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdAnal3", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장례식장(CP)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdAnal4", pmParam);
    }

    /**
     * 물품 제공현황 분석 - 월별분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeSrvProdAnal5", pmParam);
    }

    /**
     * 월별 지부별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMonthBrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeMonthBrList", pmParam);
    }

    /**
     * 월별 창고별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMonthWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeMonthWhList", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsIpList_Head", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsIpList_func", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeGdsIpList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeGdsIpList_qry", pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsIpList", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList_Head(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsUseList_Head", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList_func(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsUseList_func", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeGdsUseList_qry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeGdsUseList_qry", pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeGdsUseList", pmParam);
    }

    /**
     * CP별/일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCostList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeCostList", pmParam);
    }

    /**
     * 회원별 일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCostMemList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeCostMemList", pmParam);
    }

    /**
     * 손익분석집계표 - 행사별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBenefitList1", pmParam);
    }

    /**
     * 손익분석집계표 - 월별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBenefitList2", pmParam);
    }

    /**
     * 손익분석집계표 - 지부별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBenefitList3", pmParam);
    }

    /**
     * 손익분석집계표 - CP별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList4(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBenefitList4", pmParam);
    }

    /**
     * 손익분석집계표 - 외주업체별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList5(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeBenefitList5", pmParam);
    }

    /**
     * 재고마감 - 품목별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeStockGoodsList", pmParam);
    }

    /**
     * 재고마감 - 창고별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeStockWhList", pmParam);
    }

    /**
     * 재고마감 - 마감취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeStockMg(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeStockMg", pmParam);
    }

    /**
     * 재고마감 - 마감저장
     * 재고관리데이터 insert
     * @param pmParam
     * @throws Exception
     */
    public int insertNewTypeStockMg(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.insertNewTypeStockMg", pmParam);
    }

    /**
     * 발주번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeOrdNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeOrdNo", pmParam);
    }

    /**
     * 발주번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinOrdNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeSinOrdNo", pmParam);
    }

    /**
     * 발주 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeOrdMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeOrdMst", pmParam);
    }

    /**
     * 발주 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.deleteNewTypeOrdDtl", pmParam);
    }

    /**
     * 발주 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOrdMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOrdMst", pmParam);
    }

    /**
     * 발주 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeOrdDtl", pmParam);
    }

    /**
     * 대체용품 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeEvntMap.updateNewTypeRepGoods", pmParam);
    }

    /**
     * 대체용품 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.insertNewTypeRepGoods", pmParam);
    }

    /**
     * 대체용품 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeEvntMap.deleteNewTypeRepGoods", pmParam);
    }

    /**
     * 대체용품 회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRepList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtRepList", pmParam);
    }

    /**
     * 대체용품현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeRepGoodsCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.getNewTypeRepGoodsCount", pmParam);
    }

    /**
     * 대체용품현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeRepGoodsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeRepGoodsList", pmParam);
    }

    /**
     * 행사유지현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtUjiList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvtUjiList", pmParam);
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
    public List<Map<String, Object>> getNewTypeEvntChatInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntChatInfo", pmParam);
    }

    /**
     * 행사 등록시 해당 회원번호로 행사테이블 조회
     *
     * @param hmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int countNewTypeEvent(Map<String, Object> hmParam) throws Exception {
        return selectOne("DlwNewTypeEvntMap.countNewTypeEvent", hmParam);
    }
    /**
     * 문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntcjChatInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeEvntMap.getNewTypeEvntcjChatInfo", pmParam);
    }
    /**
     * 알림톡 구동시 가상계좌 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventcjChatVrtlInfo(Map<String, Object> hmParam) {
        return selectList("DlwNewTypeEvntMap.getNewTypeEventcjChatVrtlInfo", hmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateNewTypeEventMemberState(Map<String, ?> pmParam) throws Exception { // DAO
        return update("DlwNewTypeEvntMap.updateNewTypeEventMemberState", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam) {
		return selectList("DlwNewTypeEvntMap.getNewTypeWdrwReqConfirm", pmParam);
	} 
}
