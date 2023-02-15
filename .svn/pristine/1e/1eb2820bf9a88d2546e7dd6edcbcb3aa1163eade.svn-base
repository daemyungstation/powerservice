/*
 * (@)# DlwEmplDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
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
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwEmpl
 */
@Repository
public class DlwshowDAO extends EgovAbstractMapper {


    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    public int getDlwshowCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwshowMap.getDlwshowCount", pmParam);
    }
    
    public int getDlwmagazineCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwshowMap.getDlwmagazineCount", pmParam);
    }
    
    
      public List<Map<String, Object>> getDlwshowList(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwshowMap.getshowList", pmParam);
      }
      public List<Map<String, Object>> getDlwmagazineList(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwshowMap.getDlwmagazineList", pmParam);
      }
      
      
      public int getDlwshownmCount(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getDlwshownmCount", pmParam);
      }
      public List<Map<String, Object>> getDlwshownmList(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwshowMap.getshownmList", pmParam);
      }

      public int insertshow(Map<String, ?> pmParam) throws Exception {
          return insert("DlwshowMap.insertshow", pmParam);
      }

      public List<Map<String, Object>> getDlwshowselect(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwshowMap.getDlwshowselect", pmParam);
      }
      public int showupdate(Map<String, ?> pmParam) throws Exception {
          return update("DlwshowMap.showupdate", pmParam);
      }
      public int getshowChk1(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk1", pmParam);
      }
      public int getshowChk2(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk2", pmParam);
      }
      public int getshowChk3(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk3", pmParam);
      }
      public int getshowChk4(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk4", pmParam);
      }
      public int getshowChk5(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk5", pmParam);
      }
      public int getshowChk6(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk6", pmParam);
      }
      public int getshowChk7(Map<String, ?> pmParam) throws Exception {
          return (Integer) selectOne("DlwshowMap.getshowChk7", pmParam);
      }

      public int updateshowsave(Map<String, ?> pmParam) throws Exception {
          return update("DlwshowMap.updateshowsave", pmParam);
      }
      
      public int insertmagazinesave(Map<String, ?> pmParam) throws Exception {
          return insert("DlwshowMap.insertmagazinesave", pmParam);
      }
      
      public int del_magazine_all(Map<String, ?> pmParam) throws Exception {
          return delete("DlwshowMap.del_magazine", pmParam);
      }
      
      public int updatemagazine_magam(Map<String, ?> pmParam) throws Exception {
          return update("DlwshowMap.updatemagazine_magam", pmParam);
      }
}
