/*
 * (@)# DlwProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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

import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 입금마감현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author BIJ
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwSlipDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상품정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwSlipMap.selectSlipResnList", pmParam);
    }
    
    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnCloseList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwSlipMap.selectSlipResnCloseList", pmParam);
    }
    
    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnDetailList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwSlipMap.selectSlipResnDetailList", pmParam);
    }
    
    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnDetailCloseList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwSlipMap.selectSlipResnDetailCloseList", pmParam);
    }
    
    /**
    *
    * 해약전표마감데이터
    *
    * @param pmParam 해약정보
    * @return 해약정보
    * @throws Exception
    */
   public int updateResnSlipClose(Map<String, ?> pmParam) throws Exception {
       return update("DlwSlipMap.updateResnSlipClose", pmParam);
   }
    
    /**
    *
    * 해약전표마감데이터
    *
    * @param pmParam 해약정보
    * @return 해약정보
    * @throws Exception
    */
   public int updateResnDetail(Map<String, ?> pmParam) throws Exception {
       return update("DlwSlipMap.updateResnDetail", pmParam);
   }

   /**
    * 해약 구분(해약/청약)
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getSlipResnCloseChk(Map<String, ?> pmParam) throws Exception {
       return (Integer) selectOne("DlwSlipMap.getSlipResnCloseChk", pmParam);
   }
   
   /**
    * 카드전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardList(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwSlipMap.selectSlipCardList", pmParam);
   }
   
   /**
    * 카드 마감 구분
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getSlipCardCloseChk(Map<String, ?> pmParam) throws Exception {
       return (Integer) selectOne("DlwSlipMap.getSlipCardCloseChk", pmParam);
   }
   
   /**
    * 해약전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardDetailList(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwSlipMap.selectSlipCardDetailList", pmParam);
   }
   
   /**
    * 해약전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardDetailCloseList(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwSlipMap.selectSlipCardDetailCloseList", pmParam);
   }
   
   /**
    * 카드 마감된 승인 데이터 CNT
    *
    * @param pmParam 검색 조건
    * @return 
    * @throws Exception
    */
   public int SlipCardDetailCloseCnt(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwSlipMap.SlipCardDetailCloseCnt", pmParam);
   }
   
   /**
    * 카드 실시간 승인 데이터 CNT
    *
    * @param pmParam 검색 조건
    * @return 
    * @throws Exception
    */
   public int SlipCardDetailCnt(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwSlipMap.SlipCardDetailCnt", pmParam);
   }
   
   /**
    * 카드취소전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardCancelList(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwSlipMap.selectSlipCardCancelList", pmParam);
   }
   
   /**
    *
    * 카드전표마감데이터
    *
    * @param pmParam 해약정보
    * @return 해약정보
    * @throws Exception
    */
   public int updateCardDetail(Map<String, ?> pmParam) throws Exception {
       return update("DlwSlipMap.updateCardDetail", pmParam);
   }
   /**
    *
    * 카드전표마감데이터
    *
    * @param pmParam 해약정보
    * @return 해약정보
    * @throws Exception
    */
   public int updateCardSlipClose(Map<String, ?> pmParam) throws Exception {
       return update("DlwSlipMap.updateCardSlipClose", pmParam);
   }
}
