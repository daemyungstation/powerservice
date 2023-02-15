/*
 * (@)# DlwConsProdDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
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
 * 상담-상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
 * @프로그램ID DlwConsProd
 */
@Repository
public class DlwConsProdDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상담-상품정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 상담-상품정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getDlwConsProdList", pmParam);
    }

    /**
     * 상담-상품 모델분류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델분류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getDlwModlMstInfo", pmParam);
    }

    /**
     * 상담-상품 모델명 정보를 검색한다.
     *
     * @return 모델명 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getDlwModlDtlInfo", pmParam);
    }

    /**
     * 상담-상품 상품종류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품종류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getDlwProdKindList", pmParam);
    }

    /**
     * 회원 상품정보를 검색한다.
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInq(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getProdInfoInq", pmParam);
    }

    /**
     * 회원 상품정보를 검색한다. (메인화면에서 해약금 조회 2018.01.30 김찬영
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getProdInfoInqNew", pmParam);
    }

    /**
     * 현재 회차 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 회차
     * @throws Exception
     */
    public int getNowMonthCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getNowMonthCount", pmParam);
    }

    /**
     * 해약환급금 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약환급금
     * @throws Exception
     */
    public int getResnAmt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getResnAmt", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getProdSrvcHistForMPAS", pmParam);
    }

    /**
     * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부가서비스 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getProdSrvcMstList", pmParam);
    }

    /**
     * 할인우대권을 체크한다.
     *
     * @param pmParam 검색 조건
     * @return 할인우대권 체크 결과
     * @throws Exception
     */
    public String validateDiscntPassNo(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.validateDiscntPassNo", pmParam);
    }

    /**
     * 특별할인
     *
     * @param pmParam 검색 조건
     * @return 특별할인
     * @throws Exception
     */
    public String selectNewChanGunsuDPM(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.selectNewChanGunsuDPM", pmParam);
    }

    /**
     * 회원의 리조트 정보 조회
     *
     * @param pmParam 검색 조건
     * @return 회원의 리조트 정보
     * @throws Exception
     */
    public Map<String, Object> getResortInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getResortInfo", pmParam);
    }

    /**
     * B2B회사 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보의 검색 건수
     * @throws Exception
     */
    public int getB2bCompCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getB2bCompCount", pmParam);
    }

    /**
     * B2B회사 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getB2bCompList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getB2bCompList", pmParam);
    }


    /**
     * 매입업체(세금계산서)회사 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPurCompList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getPurCompList", pmParam);
    }

    /**
     * 삼성매장 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 삼성매장 정보의 검색 건수
     * @throws Exception
     */
    public int getSmShopCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getSmShopCount", pmParam);
    }

    /**
     * 삼성매장 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 삼성매장 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSmShopList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getSmShopList", pmParam);
    }

    /**
     * 2구좌 가입 제한 체크
     *
     * @param pmParam 검색 조건
     * @return Y/N
     * @throws Exception
     */
    public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getNoSaleAccnt", pmParam);
    }

    /**
     * 카드 코드 조회
     *
     * @param pmParam 검색 조건
     * @return 카드 코드
     * @throws Exception
     */
    public String getCardCode(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getCardCode", psParam);
    }

    /**
     * 회원번호 생성
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public String createAccntNo(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.createAccntNo", psParam);
    }

    /**
     * 회원_상품_계좌 등록
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int insertMemProdAccnt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertMemProdAccnt", pmParam);
    }

    /**
     * 스마트라이프 상담 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertSmartLifeCnslMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertSmartLifeCnslMng", pmParam);
    }

    /**
     * 스마트라이프 상담 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemProdAccnt(Map<String, ?> pmParam) throws Exception {
     //   CommonUtils.printLog(pmParam);
        return update("DlwConsProdMap.updateMemProdAccnt", pmParam);

    }

    /**
     * 전자서명 상태값 [00(인증값)_00(상품계약서 상태값)]
     *
     * @param pmParam 검색 조건
     * @return 해피콜 승인여부
     * @throws Exception
     */
    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getNiceAuthStat", pmParam);
    }

    /**
     * 부가서비스 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertMemProdAccntSvc", pmParam);
    }

    /**
     * 부가서비스 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
        return delete("DlwConsProdMap.deleteMemProdAccntSvc", pmParam);
    }

    /**
     * 스마트라이프 카드정보 조회
     *
     * @param pmParam 검색 조건
     * @return 스마트라이프 카드정보
     * @throws Exception
     */
    public Map<String, Object> getSmartLifeCardInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getSmartLifeCardInfo", pmParam);
    }

    /**
     * 해피콜 등록여부
     *
     * @param pmParam 검색 조건
     * @return 해피콜 등록여부
     * @throws Exception
     */
    public int getHpCallCount(String psParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getHpCallCount", psParam);
    }

    /**
     * 해피콜 배정 등록
     *
     * @param pmParam 검색 조건
     * @return 해피콜 배정 등록
     * @throws Exception
     */
    public int insertHpCallInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertHpCallInfo", pmParam);
    }

    /**
     * 리조트 번호 조회
     *
     * @param psParam 회원번호
     * @return 리조트 번호
     * @throws Exception
     */
    public String getResortNo(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getResortNo", psParam);
    }

    /**
     * 조회 이력 저장
     *
     * @param pmParam 회원번호
     * @return
     * @throws Exception
     */
    public int insertSearchHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertSearchHist", pmParam);
    }

    /**
     * 변경 삭제 내역 저장
     *
     * @param pmParam 변경 삭제 내역
     * @return
     * @throws Exception
     */
    public int insertReqUpdDelInf(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertReqUpdDelInf", pmParam);
    }

    /**
     * CMS 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCmsWdrwReqChk(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getCmsWdrwReqChk", psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCardWdrwReqChk(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getCardWdrwReqChk", psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getPayChk(String psParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getPayChk", psParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int delFlagMemProdAccnt(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagMemProdAccnt", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int delFlagPayMng(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagPayMng", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int delFlagRescission(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagRescission", pmParam);
    }

   /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagCnclBrkdnMng(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagCnclBrkdnMng", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagEvent(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagEvent", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagTaxtProc(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagTaxtProc", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagCmsMemb(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagCmsMemb", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagMstrChgInf(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagMstrChgInf", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagGasuAmtReg(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagGasuAmtReg", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagDcAmtReg(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagDcAmtReg", pmParam);
    }

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagCardMemb(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsProdMap.delFlagCardMemb", pmParam);
    }

    /**
     * 발주정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 발주정보
     * @throws Exception
     */
    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getOdrgInfo", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> getTotCondQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getTotCondQury", pmParam);
    }

    /**
     * 해피콜 승인상태 체크
     *
     * @param pmParam 검색 조건
     * @return 해피콜 승인여부
     * @throws Exception
     */
    public String getHpclAckdStatChk(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwConsProdMap.getHpclAckdStatChk", pmParam);
    }

    /**
     * 해피콜 승인 등록
     *
     * @param pmParam 해피콜 승인정보
     * @return
     * @throws Exception
     */
    public int insertHpclAckd(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertHpclAckd", pmParam);
    }

    /**
     * 해피콜 승인 수정
     *
     * @param pmParam 해피콜 승인정보
     * @return
     * @throws Exception
     */
    public int updateHpclAckd(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.updateHpclAckd", pmParam);
    }

    /**
     * 해피콜 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해피콜 정보의 검색 건수
     * @throws Exception
     */
    public int getHpCallCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwConsProdMap.getHpCallCnt", pmParam);
    }

    /**
     * 해피콜 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해피콜 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHpCallList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsProdMap.getHpCallList", pmParam);
    }

    /**
     * 해피콜 이력 등록
     *
     * @param pmParam 해피콜 이력 정보
     * @return
     * @throws Exception
     */
    public int insertHpclHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsProdMap.insertHpclHist", pmParam);
    }
    /**
     * 온라인변환 상품 추가 전 모델코드조회
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public String getProdModelCd(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwConsProdMap.getProdModelCd", pmParam);
    }

    /**
    * 종합현황 조회
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> getTotCondListLv(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwConsProdMap.getTotCondListLv", pmParam);
   }


   /**
   * 상품모델마스터 정보 조회
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public Map<String, Object> getProdModelMst(Map<String, Object> pmParam) throws Exception {
      return selectOne("DlwConsProdMap.getProdModelMst", pmParam);
  }

  public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception {
      return (Integer) selectOne("DlwConsProdMap.getdeliveryCnt", pmParam);
  }

/**
 *  발주관리  설치장소 업데이트
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
 * */
  public int updatedelivery(Map<String, ?> pmParam) throws Exception {

      return update("DlwConsProdMap.updatedelivery", pmParam);

  }

  /******************************************************
   * 2017.09.20 김준호
   * 회원 고유번호로 해당 고객 등록 상품 갯수 확인
   * **************************************************/

  public List<Map<String, Object>> selectCountProd(Map<String, ?> pmParam) {
      return selectList("DlwConsProdMap.selectCountProd", pmParam);
  }

  /**
   * 두구좌 계정 정보저장
   *
   * @param pmParam 검색 조건
   * @return 회원번호
   * @throws Exception
   */
  public int insertMemTwinAccnt(Map<String, Object> pmParam) {
	  insert("DlwConsProdMap.insertMemTwinAccnt1", pmParam);
	  insert("DlwConsProdMap.insertMemTwinAccnt2", pmParam);
	  return 2;
  }
}
