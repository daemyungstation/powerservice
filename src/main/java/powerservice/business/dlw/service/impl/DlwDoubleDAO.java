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
public class DlwDoubleDAO extends EgovAbstractMapper {

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
     * 다구좌 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectDoubleList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwDoubleMap.selectDoubleList", pmParam);
    }
    
    /**
     * 다구좌 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectDoubleSrchList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwDoubleMap.selectDoubleSrchList", pmParam);
    }
    
    /**
    *
    * 다구좌 등록
    *
    * @param pmParam 
    * @return 
    * @throws Exception
    */
   public int saveDouble(Map<String, ?> pmParam) throws Exception {
       return update("DlwDoubleMap.saveDouble", pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwDoubleKey(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwDoubleMap.getDlwDoubleKey", pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getAccntNoDbl(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwDoubleMap.getAccntNoDbl", pmParam);
   }
   
   public int getAccntNoDblCount(Map<String, Object> pmParam) {
       return selectOne("DlwDoubleMap.getAccntNoDblCount", pmParam);
  }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwPayInfo(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwDoubleMap.getDlwPayInfo", pmParam);
   }
   
   /**
    * CARD 변경 정보 업데이트 
    * 
    * 
   */
   public int updateCardAccnt(Map<String, ?> pmParam) throws Exception {
       return update("DlwDoubleMap.updateCardAccnt", pmParam);
   }    
   
   /**
    * CMS 변경 정보 업데이트 
    * 
    * 
   */
   public int updateCmsAccnt(Map<String, ?> pmParam) throws Exception {
       return update("DlwDoubleMap.updateCmsAccnt", pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwDoubleTripleChk(Map<String, Object> pmParam) throws Exception {
       return selectList("DlwDoubleMap.getDlwDoubleTripleChk", pmParam);
   }
   
   /**
   *
   * 다구좌 삭제
   *
   * @param pmParam 
   * @return 
   * @throws Exception
   */
  public int deleteDouble(Map<String, ?> pmParam) throws Exception {
      return update("DlwDoubleMap.deleteDouble", pmParam);
  }
}
