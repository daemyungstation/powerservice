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
public class DlwNewTypePayMngDAO extends EgovAbstractMapper {

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
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no) {
        return selectOne("DlwNewTypePayMngMap.getWdrwReqAccntConfirm", accnt_no);
    }
    
    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMng(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwNewTypePayMngMap.insertPayMng", pmParam);
        } else {
            return update("DlwNewTypePayMngMap.updatePayMng", pmParam);
        }
    }
    
    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMngDtl(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwNewTypePayMngMap.insertPayMngDtl", pmParam);
        } else {
            return update("DlwNewTypePayMngMap.updatePayMngDtl", pmParam);
        }
    }
    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMngDtl1(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwNewTypePayMngMap.insertPayMngDtl1", pmParam);
        } else {
            return update("DlwNewTypePayMngMap.updatePayMngDtl1", pmParam);
        }
    }
    
    /**
     * 대명라이프웨이 납입일자가 같은 전표 갯수 조회
     *
     * @param pmParam 검색 조건
     * @return 전표 갯수
     * @throws Exception
     */
    public List<Map<String, Object>> getDCAmtCnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getDCAmtCnt", pmParam);
    }
    
    /**
     * * 대명라이프웨이 DC금 seq 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
     public int deleteDCAmt(Map<String, ?> pmParam) throws Exception {
         return delete("DlwNewTypePayMngMap.deleteDCAmt", pmParam);
     }

     /**
      * 대명라이프웨이 가입일자 - 1회입금일자로  수정
      *
      * @param pmParam 검색 조건
      * @return
      * @throws Exception
      */
     public int updateJoinDt(Map<String, ?> pmParam) throws Exception {
         return update("DlwNewTypePayMngMap.updateJoinDt", pmParam);
     }
     
     /**
      * 대명라이프웨이 DC금 seq 수정
      *
      * @param pmParam 검색 조건
      * @return
      * @throws Exception
      */
     public int updateDCAmtSeq(Map<String, ?> pmParam) throws Exception {
         return insert("DlwNewTypePayMngMap.updateDCAmtSeq", pmParam);
     }
    
    /**
     * 대명라이프웨이 변경삭제내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertReqUpdDelInf(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypePayMngMap.insertReqUpdDelInf", pmParam);
    }
    
    /**
     * 대명라이프웨이 입금전표 상태 조회
     *
     * @param pmParam 검색 조건
     * @return 전표 갯수
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngStat(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngStat", pmParam);
    }
    
    /**
     * 대명라이프웨이 입금 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return prod_cl
     * @throws Exception
     */
    public String getpayNewYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypePayMngMap.getpayNewYnChk", pmParam);
    }
    
    /**
     * 대명라이프웨이 부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return  부가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getBugaInfo", pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 추가 부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypePayMngMap.getPayAmtDtl1ByPayCnt", pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypePayMngMap.getPayAmtDtlByPayCnt", pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypePayMngMap.getPayAmtByPayCnt", pmParam);
    }
    
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypePayMngMap.getDlwCustAcntCount", pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getDlwCustAcntList", pmParam);
    }
    
    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 할부금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngDtlBugaInfo", pmParam);
    }

    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 추가금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngDtl1BugaInfo", pmParam);
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getRefundList", pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getRefundDtlList", pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getRefundDtl1List", pmParam);
    }
    
    /**
     *  입금전표조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngList", pmParam);
    }

    /**
     *  입금전표조회-결합상품할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngDtlList", pmParam);
    }

    /**
     *  입금전표조회-결합상품추가부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngDtl1List", pmParam);
    }
    
    /**
     *  입금전표조회-총금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayTotalList", pmParam);
    }

    /**
     *  입금전표조회-상품정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getProductDtl", pmParam);
    }

    /**
     * 회원번호로 상품코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypePayMngMap.getProdCdByAccntNo", pmParam);
    }
    
    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypePayMngMap.getPayMngBugaInfo", pmParam);
    }
}