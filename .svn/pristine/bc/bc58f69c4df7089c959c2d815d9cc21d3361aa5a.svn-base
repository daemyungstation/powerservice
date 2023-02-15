/*
 * (@)# DlwCondDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 Cond를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * @프로그램ID DlwCondDAO
 */
@Repository
public class DlwCondDAO extends EgovAbstractMapper {

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
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getClList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCondMap.getClList", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getInqCondQry(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCondMap.getInqCondQry", pmParam);
    }

    /**
     * 종합 회원 현황 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCondMemCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCondMap.getCondMemCount", pmParam);
    }

   /**
    * 종합 회원 현황
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> getCondMemList(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwCondMap.getCondMemList", pmParam);
   }

   /**
    * 종합 입금현황 건수
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getPayCount(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwCondMap.getPayCount", pmParam);
   }

  /**
   * 종합 입금현황
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getPayList(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getPayList", pmParam);
  }

  /**
   * 증서출력 구분
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getCertfCond(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getCertfCond", pmParam);
  }

  /**
   * 연체현황 - 상품구분기준
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getDelayPayByProd(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getDelayPayByProd", pmParam);
  }

  /**
   * 연체현황 - 전체실적(실납입)
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getRealPay(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getRealPay", pmParam);
  }

  /**
   * 연체현황 - 채권구분기준
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getDelayPayByBond(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getDelayPayByBond", pmParam);
  }

  /**
   * 실적현황
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getAcrsProd(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getAcrsProd", pmParam);
  }

  /**
   * 실적현황 - batch 데이터
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getAcrsProdByBatch(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getAcrsProdByBatch", pmParam);
  }

  /**
   * 연체현황 데이터 삽입
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public void insertUserProdInfoMonth(Map<String, ?> pmParam) throws Exception {
      insert("DlwCondMap.insertUserProdInfoMonth", pmParam);
  }
  /**
   * 실적현황 데이터 삽입
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public void insertUserProdInfo(Map<String, ?> pmParam) throws Exception {
      insert("DlwCondMap.insertUserProdInfo", pmParam);
  }

  /**
   * 연체현황 기준월 데이터
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getBasMonth(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getBasMonth", pmParam);
  }

  /**
   * 입금현황(new) - 회차별 건수
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public int getPayMonthCount(Map<String, ?> pmParam) throws Exception {
      return selectOne("DlwCondMap.getPayMonthCount", pmParam);
  }

  /**
   * 입금현황(new) - 회차별
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getPayMonthList(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getPayMonthList", pmParam);
  }
  /**
   * 입금현황(new) - 회원별 건수
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public int getPayMonthByMemCount(Map<String, ?> pmParam) throws Exception {
      return selectOne("DlwCondMap.getPayMonthByMemCount", pmParam);
  }

  /**
   * 입금현황(new) - 회원별
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getPayMonthByMemList(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getPayMonthByMemList", pmParam);
  }

  /** ================================================================
   * 날짜 : 20190213
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 엑셀파일 배치일자 조회
   * 대상 테이블 :
   * ================================================================
   * */
  public List<Map<String, Object>> getMbidExcelBatchDay(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getMbidExcelBatchDay", pmParam);
  }

  /** ================================================================
   * 날짜 : 20190213
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 엑셀파일 리스트
   * 대상 테이블 :
   * ================================================================
   * */
  public List<Map<String, Object>> getMbidExcelList(Map<String, ?> pmParam) throws Exception {
      return selectList("DlwCondMap.getMbidExcelList", pmParam);
  }

  /** ================================================================
   * 날짜 : 20181211
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 File 목록 INSERT
   * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
   * ================================================================
   * */
  public int insertMbidCsvFileList(Map<String, ?> pmParam) throws Exception {
      return insert("DlwCondMap.insertMbidCsvFileList", pmParam);
  }

  /** ================================================================
   * 날짜 : 20181214
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 단문 메세지(SMS) 전송 대상 INSERT
   * 대상 테이블 : LF_KAKAO.SMS_MSG, LF_DMUSER.TB_MBID_CSV_FILE_PURIFY
   * ================================================================
   * */
  public int insertMbidBatchSmsSend(Map<String, ?> pmParam) throws Exception {
      return insert("DlwCondMap.insertMbidBatchSmsSend", pmParam);
  }

  /** ================================================================
   * 날짜 : 20181214
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 장문 메세지(LMS) 전송 대상 INSERT
   * 대상 테이블 : LF_KAKAO.MMS_MSG, LF_DMUSER.TB_MBID_CSV_FILE_PURIFY
   * ================================================================
   * */
  public int insertMbidBatchMmsSend(Map<String, ?> pmParam) throws Exception {
      return insert("DlwCondMap.insertMbidBatchMmsSend", pmParam);
  }

  /** ================================================================
   * 날짜 : 20181214
   * 이름 : 송준빈
   * 추가내용 : 일일청구기준데이터 단문,장문 메세지(SMS,LMS) 전송 이력정보 INSERT
   * 대상 테이블 : PS_WILLVI.TB_CHAT_SNDG_HSTR_NEW, LF_DMUSER.TB_MBID_CSV_FILE_PURIFY
   * ================================================================
   * */
  public int insertMbidBatchMsgSendHist(Map<String, ?> pmParam) throws Exception {
      return insert("DlwCondMap.insertMbidBatchMsgSendHist", pmParam);
  }

  /** ================================================================
   * 날짜 : 20190131
   * 이름 : 송준빈
   * 추가내용 : 고객연체 히스토리 데이터 적재
   * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_HSTR
   * ================================================================
   * */
 public int insertMemberYencheHstr(Map<String, Object> pmParam) {
	 return insert("DlwCondMap.insertMemberYencheHstr", pmParam);
 }

 /** ================================================================
  * 날짜 : 20190131
  * 이름 : 송준빈
  * 추가내용 : 고객연체 히스토리 데이터 적재
  * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_HSTR
  * ================================================================
  * */
 public int insertAlowBasicInfo(Map<String, Object> pmParam) {
	 return insert("DlwCondMap.insertAlowBasicInfo", pmParam);
 }

 /**
  * 해당 월 마감 된 데이터 수 조회
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int getCloseDataCount(Map<String, ?> pmParam) throws Exception {
     return selectOne("DlwCondMap.getCloseDataCount", pmParam);
 }

 /** ================================================================
  * 날짜 : 20190507
  * 추가내용 :  해당 월 수수료 마감
  * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
  * ================================================================
  * */
 public int insertCloseDataComm(Map<String, ?> pmParam) {
	 return insert("DlwCondMap.insertCloseDataComm", pmParam);
 }

 /** ================================================================
  * 날짜 : 20190507
  * 추가내용 :  해당 월 수당 마감
  * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
  * ================================================================
  * */
 public int insertCloseDataAlow(Map<String, ?> pmParam) {
	 return insert("DlwCondMap.insertCloseDataAlow", pmParam);
 }
 
 /**
  * 수당, 수수료 산출 여부 확인
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getAlowCalcMgmtExtList(Map<String, Object> pmParam) {
	 return selectList("DlwCondMap.getAlowCalcMgmtExtList", pmParam);
 }

 /**
  * 수당, 수수료 산출 여부 등록
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int insertAlowCalcMgmtExtList(Map<String, Object> pmParam) {
	 return insert("DlwCondMap.insertAlowCalcMgmtExtList", pmParam);
 }

}
