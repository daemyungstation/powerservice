/*
 * (@)# DlwResnDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
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
 * 해약 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
 * @프로그램ID DlwResn
 */
@Repository
public class DlwNewTypeResnDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 해약정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 해약 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 해약 등록 체크 : 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return 행사 등록 여부
     * @throws Exception
     */
    public int getNewTypeEventChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeEventChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 해약 처리 유무
     * @throws Exception
     */
    public int getNewTypeResnChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeResnChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 세무 처리 유무
     * @throws Exception
     */
    public int getNewTypeTaxtChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeTaxtChk", pmParam);
    }

    /**
     * 해약 등록 체크 : CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getNewTypeCmsChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeCmsChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 콜센터 처리유무
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getNewTypecallcenterChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypecallcenterChk", pmParam);
    }




    /**
     * 해약 등록 체크 : 해약 시 CMS청구내역 파일 중 (WDRW_REQ) 신청전(STAT = '01'),신청중(STAT = '02')인 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeCmsReqCnt", pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSelectProdCl(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwNewTypeResnMap.getNewTypeSelectProdCl", pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSelectJoinType(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwNewTypeResnMap.getNewTypeSelectJoinType", pmParam);
    }

    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeResnGubn(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeResnGubn", pmParam);
    }

    /**
     * CMS정보 조회
     *
     * @param pmParam 검색 조건
     * @return CMS정보
     * @throws Exception
     */
    public Map<String, Object> getNewTypeCmsInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeResnMap.getNewTypeCmsInfo", pmParam);
    }

    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeResnCount", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnList", pmParam);
    }

    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getNewTypeResnDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeResnMap.getNewTypeResnDtpt", pmParam);
    }

    /**
     * 해약시 미지급 포인트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 해약시 미지급 포인트
     * @throws Exception
     */
    public String getNewTypeEmartIPoint(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwNewTypeResnMap.getNewTypeEmartIPoint", pmParam);
    }

    /**
     * 가수금 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가수금 정보
     * @throws Exception
     */
    public String getNewTypeResnGasuAmt(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwNewTypeResnMap.getNewTypeResnGasuAmt", pmParam);
    }

    /**
     *
     * 해약 송금일 업데이트
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateNewTypeResnSenddt(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.updateNewTypeResnSenddt", pmParam);
    }

    /**
     * 서류 제출 기한 마감 수정
     * 해약 처리 정보를 수정
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateNewTypeResnProc(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.updateNewTypeResnProc", pmParam);
    }

    /**
     * 해약 수정
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateNewTypeResn(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.updateNewTypeResn", pmParam);
    }

    /**
     * 해약 등록
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int insertNewTypeResn(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeResnMap.insertNewTypeResn", pmParam);
    }

    /**
     * 해약 newYn 체크
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public String getNewTypeResnNewYnChk(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwNewTypeResnMap.getNewTypeResnNewYnChk", pmParam);
    }

    /**
     * 해약 삭제
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int resnNewTypeDel(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.resnNewTypeDel", pmParam);
    }


    /**
     * 해약현황 통계 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 통계 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnStat1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnStat1List", pmParam);
    }
    public List<Map<String, Object>> getNewTypeResnStat2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnStat2List", pmParam);
    }
    public List<Map<String, Object>> getNewTypeResnStat3List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnStat3List", pmParam);
    }

    /**
     * 결제방법 조회
     *
     * @param pmParam 검색 조건
     * @return 결제방법코드
     * @throws Exception
     */
    public String getNewTypePayMethodCd(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwNewTypeResnMap.getNewTypePayMethodCd", pmParam);
    }

    /**
     * 매출입현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBuyChulList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeBuyChulList", pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeAccPurList", pmParam);
    }


    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public Integer getNewTypeMemPurCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeMemPurCount", pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMemPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeMemPurList", pmParam);
    }

    /**
     * 매입현황상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매입현황상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBuyDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeBuyDtlList", pmParam);
    }

    /**
     * (중간마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTmpMemPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeTmpMemPurList", pmParam);
    }

    /**
     * (최종마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (최종마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMemPurMgList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeMemPurMgList", pmParam);
    }

    /**
     * (최종마감) 매출입현황 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccPurMgList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeAccPurMgList", pmParam);
    }

    /**
     * 회원별매출입 기본데이터 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return 회원별매출입 기본데이터 카운트 조회
     * @throws Exception
     */
    public Integer getNewTypeCntMemPurList(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeCntMemPurList", pmParam);
    }

    /**
     * 회원별매출입 중간마감 카운트 조회
     *
     * @param pmParam
     * @return 회원별매출입 중간마감 카운트 조회
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.srchNewTypeCntAccntPurSale", pmParam);
    }

    /**
     * 회원별매출입 중간마감 카운트 조회 (** 회원번호 조건 제외)
     *
     * @param pmParam
     * @return 회원별매출입 중간마감 카운트 조회 (** 회원번호 조건 제외)
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSale2(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.srchNewTypeCntAccntPurSale2", pmParam);
    }

    /**
     * 회원별매출입 최종마감 카운트 조회
     *
     * @param pmParam
     * @return 회원별매출입 최종마감 카운트 조회
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.srchNewTypeCntAccntPurSaleMg", pmParam);
    }

    /**
     * 매입매출 최종마감일 조회
     *
     * @param pmParam
     * @return 매입매출 최종마감일 조회
     * @throws Exception
     */
    public String srchNewTypeMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwNewTypeResnMap.srchNewTypeMgDtAccntPurSaleMg", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 입력
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 입력
     * @throws Exception
     */
    public int saveNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeResnMap.saveNewTypeAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 수정
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 수정
     * @throws Exception
     */
    public int updateNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.updateNewTypeAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 삭제
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 삭제
     * @throws Exception
     */
    public int deleteNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.deleteNewTypeAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 최종마감처리
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 최종마감처리
     * @throws Exception
     */
    public int saveNewTypeAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeResnMap.saveNewTypeAccntPurSaleMg", pmParam);
    }

    /**
     * 회원별 해약환급금 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectNewTypeResnAmtAccnt(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.selectNewTypeResnAmtAccnt", pmParam);
    }

    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public long selectNewTypeResnAmtSum(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwNewTypeResnMap.selectNewTypeResnAmtSum", pmParam);
    }

    /**
     * 회원별 해약환급금 마감
     *
     * @param
     * @return
     * @throws Exception
     */
    public int callNewTypeUPResnAmt(Map<String, Object> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.callNewTypeUPResnAmt", pmParam);
    }

    /**
     * 회원별 해약환급금 마감해지
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteNewTypeResnAmt(Map<String, Object> pmParam) throws Exception {
        return update("DlwNewTypeResnMap.deleteNewTypeResnAmt", pmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateNewTypeResnMemberState(Map<String, ?> pmParam) throws Exception { // DAO
        return update("DlwNewTypeResnMap.updateNewTypeResnMemberState", pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam) {
		return selectList("DlwNewTypeResnMap.getNewTypeWdrwReqConfirm", pmParam); 
	}

	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeCodeBank(String sCdNm) {
		return selectList("DlwNewTypeResnMap.getNewTypeCodeBank", sCdNm); 
	}
	
	/**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeResnNewCount", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnNewList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnNewList", pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnNewCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeResnMap.getNewTypeResnNewCount2", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnNewList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeResnMap.getNewTypeResnNewList2", pmParam);
    }

    /** ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public String getNewTypeMangiDate(Map<String, ?> pmParam) {
		return (String) selectOne("DlwNewTypeResnMap.getNewTypeMangiDate", pmParam);
	}
}
