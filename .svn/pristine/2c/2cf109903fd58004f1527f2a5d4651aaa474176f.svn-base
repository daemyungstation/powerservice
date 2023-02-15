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
public class DlwResnDAO extends EgovAbstractMapper {

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
    public int getEventChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getEventChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 해약 처리 유무
     * @throws Exception
     */
    public int getResnChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 세무 처리 유무
     * @throws Exception
     */
    public int getTaxtChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getTaxtChk", pmParam);
    }

    /**
     * 해약 등록 체크 : CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getCmsChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getCmsChk", pmParam);
    }

    /**
     * 해약 등록 체크 : 콜센터 처리유무
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getcallcenterChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getcallcenterChk", pmParam);
    }




    /**
     * 해약 등록 체크 : 해약 시 CMS청구내역 파일 중 (WDRW_REQ) 신청전(STAT = '01'),신청중(STAT = '02')인 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getCmsReqCnt", pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSelectProdCl(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwResnMap.getSelectProdCl", pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSelectJoinType(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwResnMap.getSelectJoinType", pmParam);
    }

    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnGubn", pmParam);
    }

    /**
     * CMS정보 조회
     *
     * @param pmParam 검색 조건
     * @return CMS정보
     * @throws Exception
     */
    public Map<String, Object> getCmsInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnMap.getCmsInfo", pmParam);
    }

    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnCount", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnList", pmParam);
    }

    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getResnDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnMap.getResnDtpt", pmParam);
    }

    /**
     * 해약시 미지급 포인트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 해약시 미지급 포인트
     * @throws Exception
     */
    public String getEmartIPoint(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwResnMap.getEmartIPoint", pmParam);
    }

    /**
     * 가수금 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가수금 정보
     * @throws Exception
     */
    public String getResnGasuAmt(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwResnMap.getResnGasuAmt", pmParam);
    }

    /**
     *
     * 해약 송금일 업데이트
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateResnSenddt(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.updateResnSenddt", pmParam);
    }

    /**
     * 서류 제출 기한 마감 수정
     * 해약 처리 정보를 수정
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateResnProc(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.updateResnProc", pmParam);
    }

    /**
     * 해약 수정
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int updateResn(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.updateResn", pmParam);
    }

    /**
     * 해약 등록
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int insertResn(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.insertResn", pmParam);
    }

    /**
     * 해약 newYn 체크
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public String getResnNewYnChk(Map<String, ?> pmParam) throws Exception {
        return (String)selectOne("DlwResnMap.getResnNewYnChk", pmParam);
    }

    /**
     * 해약 삭제
     *
     * @param pmParam 해약정보
     * @return 해약정보
     * @throws Exception
     */
    public int resnDel(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.resnDel", pmParam);
    }


    /**
     * 해약현황 통계 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 통계 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnStat1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnStat1List", pmParam);
    }
    public List<Map<String, Object>> getResnStat2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnStat2List", pmParam);
    }
    public List<Map<String, Object>> getResnStat3List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnStat3List", pmParam);
    }

    /**
     * 결제방법 조회
     *
     * @param pmParam 검색 조건
     * @return 결제방법코드
     * @throws Exception
     */
    public String getPayMethodCd(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwResnMap.getPayMethodCd", pmParam);
    }

    /**
     * 매출입현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBuyChulList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getBuyChulList", pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAccPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getAccPurList", pmParam);
    }


    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public Integer getMemPurCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getMemPurCount", pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getMemPurList", pmParam);
    }

    /**
     * 매입현황상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매입현황상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBuyDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getBuyDtlList", pmParam);
    }

    /**
     * (중간마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getTmpMemPurList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getTmpMemPurList", pmParam);
    }

    /**
     * (최종마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (최종마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getMemPurMgList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getMemPurMgList", pmParam);
    }

    /**
     * (최종마감) 매출입현황 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAccPurMgList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getAccPurMgList", pmParam);
    }

    /**
     * 회원별매출입 기본데이터 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return 회원별매출입 기본데이터 카운트 조회
     * @throws Exception
     */
    public Integer getCntMemPurList(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getCntMemPurList", pmParam);
    }

    /**
     * 회원별매출입 중간마감 카운트 조회
     *
     * @param pmParam
     * @return 회원별매출입 중간마감 카운트 조회
     * @throws Exception
     */
    public int srchCntAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.srchCntAccntPurSale", pmParam);
    }

    /**
     * 회원별매출입 중간마감 카운트 조회 (** 회원번호 조건 제외)
     *
     * @param pmParam
     * @return 회원별매출입 중간마감 카운트 조회 (** 회원번호 조건 제외)
     * @throws Exception
     */
    public int srchCntAccntPurSale2(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.srchCntAccntPurSale2", pmParam);
    }

    /**
     * 회원별매출입 최종마감 카운트 조회
     *
     * @param pmParam
     * @return 회원별매출입 최종마감 카운트 조회
     * @throws Exception
     */
    public int srchCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.srchCntAccntPurSaleMg", pmParam);
    }

    /**
     * 매입매출 최종마감일 조회
     *
     * @param pmParam
     * @return 매입매출 최종마감일 조회
     * @throws Exception
     */
    public String srchMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwResnMap.srchMgDtAccntPurSaleMg", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 입력
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 입력
     * @throws Exception
     */
    public int saveAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.saveAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 수정
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 수정
     * @throws Exception
     */
    public int updateAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.updateAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 삭제
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 삭제
     * @throws Exception
     */
    public int deleteAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.deleteAccntPurSale", pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 최종마감처리
     *
     * @param pmParam
     * @return 회원별 상품모델 매입매출 최종마감처리
     * @throws Exception
     */
    public int saveAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.saveAccntPurSaleMg", pmParam);
    }

    /**
     * 회원별 해약환급금 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectResnAmtAccnt(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwResnMap.selectResnAmtAccnt", pmParam);
    }

    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public long selectResnAmtSum(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwResnMap.selectResnAmtSum", pmParam);
    }

    /**
     * 회원별 해약환급금 마감
     *
     * @param
     * @return
     * @throws Exception
     */
    public int callUPResnAmt(Map<String, Object> pmParam) throws Exception {
        return update("DlwResnMap.callUPResnAmt", pmParam);
    }

    /**
     * 회원별 해약환급금 마감해지
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteResnAmt(Map<String, Object> pmParam) throws Exception {
        return update("DlwResnMap.deleteResnAmt", pmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception { // DAO
        return update("DlwResnMap.updateResnMemberState", pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam) {
		return selectList("DlwResnMap.getWdrwReqConfirm", pmParam); 
	}

	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getCodeBank(String sCdNm) {
		return selectList("DlwResnMap.getCodeBank", sCdNm); 
	}
	
	/**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnNewCount", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnNewList", pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnNewCount2", pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList2(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnNewList2", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public String getMangiDate(Map<String, ?> pmParam) {
		return (String) selectOne("DlwResnMap.getMangiDate", pmParam);
	}
	
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount3(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnNewCount3", pmParam);
    }
    
    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList3(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnNewList3", pmParam);
    }
    
    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getResnAccntInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnMap.getResnAccntInfo", pmParam);
    }
    
    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getResnDetailInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnMap.getResnDetailInfo", pmParam);
    }
    
    /**
     * 해약 전자서명 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getResnSignInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnSignInfo", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 신규 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.insertResnInfo", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 업데이트 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int updateResnInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.updateResnInfo", pmParam);
    }    
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 삭제 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int deleteResnInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwResnMap.deleteResnInfo", pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 레디캐시 실시간 조회
     * ================================================================
     * */
    public String getReadyCashRealAmt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnMap.getReadyCashRealAmt", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약등록 청구 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     */
    public List<Map<String, Object>> getResnReqInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnReqInfo", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220307
     * 이름 : 임동진
     * 추가내용 : 해약 환불 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnRefund(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.insertResnRefund", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 상담 로그 등록 
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnCounsel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwResnMap.insertResnCounsel", pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount0(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnNewCount0", pmParam);
    }
    
    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList0(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnNewList0", pmParam);
    }
    
    /**
     * 해약현황(전자서명) 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnSignCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnMap.getResnSignCount", pmParam);
    }
    
    /**
     * 해약현황(전자서명) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnSignList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnMap.getResnSignList", pmParam);
    }
}
