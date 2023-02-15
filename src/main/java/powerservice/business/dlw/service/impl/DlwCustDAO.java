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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
 * @프로그램ID DlwCust
 */
@Repository
public class DlwCustDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 고객-제품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-제품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustPrdtList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwCustPrdtList", pmParam);
    }

    /**
     * 고객정보를 등록한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertMember(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCustMap.insertMember", pmParam);
    }

    /**
     * 고객정보를 수정한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateMember(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.updateMember", pmParam);
    }
    /**
     * 웹디비고객정보를 회원명을 수정한다
     *
     * @param pmParam 고객정보
     * @throws Exception
      */
    public void updateMember_web(Map<String, ?> pmParam) throws Exception {
           update("DlwCustMap.updateMember_web", pmParam);
    }




    /**
     * 고객 상세정보를 조회한다.(MEM_PROD_ACCNT)
     *
     * @param pmParam 고객정보
     * @return 고객상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustPrdtDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getDlwCustPrdtList", pmParam);
    }

    /**
     * 고객 상세정보를 조회한다.(MEMBER, 단건)
     *
     * @param pmParam 고객정보
     * @return 고객상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwMemberInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getDlwMemberInfo", pmParam);
    }

    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getDlwCustAcntCount", pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwCustAcntList", pmParam);
    }

    /**
     * 온라인 가입회원 구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCmsInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwOnlineMemberCmsInfo", pmParam);
    }

    /**
     * 온라인 가입회원(결합상품) 구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineSSMemberCmsInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwOnlineSSMemberCmsInfo", pmParam);
    }

    /**
     * 온라인 가입회원 구좌번호로 카드정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCardInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwOnlineMemberCardInfo", pmParam);
    }

    /**
     * 온라인 가입회원(결합상품) 구좌번호로 카드정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineSSMemberCardInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getDlwOnlineSSMemberCardInfo", pmParam);
    }

    /**
     * 납입정보(부가서비스)등록 회원을 체크한다.
     *
     * @param pmParam 고객정보
     * @return 회원정보
     * @throws Exception
     */
    public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getBugaSrvcMemChk", pmParam);
    }

    /**
     * 부가서비스(CMS/CARD) 신청구분을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 신청구분 코드(1:신청, 3:해지, 7:임의해지)
     * @throws Exception
     */

    public String getBugaSrvcAppCl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getBugaSrvcAppCl", pmParam);
    }

    /**
     * idn_no나 ci값으로 회원명을 가져와 온라인상의 이름과 비교해 같을 경우에만 erp회원 등록
     *
     * @param pmParam 검색 조건
     * @return 회원명
     * @throws Exception
     */
    public String getErpMemNm(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwCustMap.getErpMemNm", pmParam);
    }


    /*
     * 2016. 06. 10 최현식
     * PS5 MEMBER Table 삭제에 따른 추가
     * */

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getCustBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getCustBasiCount", pmParam);
    }

    /**
     * 업체용 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getCompUseListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getCompUseListCount", pmParam);
    }

    /**
     * 고객해지 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnAuthListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getResnAuthListCount", pmParam);
    }

    /**
     * 채권추심 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCreditMainListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getResnCreditMainListCount", pmParam);
    }

    /**
     * 채권추심 입금 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCreditpayMainListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getResnCreditpayMainListCount", pmParam);
    }



    /**
     * 직권해지 대상자 조회 count
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnTargetListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwCustMap.getResnTargetListCount", pmParam);
    }

    /**
     * 직권해지 대상자 조회
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnTargetList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getResnTargetList", pmParam);
    }

    /**
     * 채권추심 대상자 조회
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getResnCreditList", pmParam);
    }

    /**
     * 직권해지 대상자 등록
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertTargetList(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.insertTargetList", pmParam);
    }

    /**
     * 채권추심 대상자 등록
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertCreditList(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.insertCreditList", pmParam);
    }

    /**
    *
    * 직권해지 설정일 업데이트
    *
    * @param pmParam 해약정보
    * @return 해약정보
    * @throws Exception
    */
   public int updateResnSenddt(Map<String, ?> pmParam) throws Exception {
       return update("DlwCustMap.updateResnSenddt", pmParam);
   }

   /**
   *
   * 채권추심 삭제
   *
   * @param pmParam 해약정보
   * @return 해약정보
   * @throws Exception
   */
  public int delResnCredit(Map<String, ?> pmParam) throws Exception {
      return update("DlwCustMap.delResnCredit", pmParam);
  }




   /**
    * 고객 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 고객 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getCustBasiList(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwCustMap.getCustBasiList", pmParam);
   }

   /**
    * 고객 정보를 검색한다.  (관리업무 - 고객정보관리)
    *
    * @param pmParam 검색 조건
    * @return 고객 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getCustBasiList2(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwCustMap.getCustBasiList2", pmParam);
   }

    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCompUseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getCompUseList", pmParam);
    }

    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAuthList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getResnAuthList", pmParam);
    }

    /**
     * 채권추심 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getResnCreditMainList", pmParam);
    }

    /**
     * 채권추심 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditpayMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getResnCreditpayMainList", pmParam);
    }

    /**
     * 고객 정보를 검색한다. (상담테이블 설정)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasiConsInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getCustBasiConsInfo", pmParam);
    }

    /**
     * 고객 정보를 검색한다. (상담테이블 설정)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasi(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getCustBasi", pmParam);
    }

    /**
     * OCB정보를 수정한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateMemberOCB(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.updateMemberOCB", pmParam);
    }

    /**
     * 고객 정보를 검색한다. (상담이력테이블 저장)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getDlwMemberDtpt(String pMemNo) throws Exception {
        return selectOne("DlwCustMap.getDlwMemberDtpt", pMemNo);
    }

    /**
     * 온라인정보로 고유번호 확인
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public Map<String, Object> getMemInfoByOnln(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCustMap.getMemInfoByOnln", pmParam);
    }

    /**
     * 양도양수 정보 등록
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertYdysMemTrans(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.insertYdysMemTrans", pmParam);
    }

    /**
     * 회원상품정보 고유번호 변경
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateYdysMemProd(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.updateYdysMemProd", pmParam);
    }

    /**
     * 상담내역 고유번호 변경
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateYdysClsl(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.updateYdysClsl", pmParam);
    }

    /**
     * 정보변경 내역 변경
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateYdysChng(Map<String, ?> pmParam) throws Exception {
        return update("DlwCustMap.updateYdysChng", pmParam);
    }

    /**
     * 고객별 실적통계 결과를 보여준다.
     *
     * @param pmParam 검색 조건
     * @return 고객-제품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAnalResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCustMap.getAnalResultList", pmParam);
    }



    /* ================================================================
     * 날짜 : 20180403
     * 이름 : 김찬영
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 양도로 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception { // DAO
        return update("DlwCustMap.updateResnMemberState", pmParam);
    }

}
