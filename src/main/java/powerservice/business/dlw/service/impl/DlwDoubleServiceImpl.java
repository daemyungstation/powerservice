/*
 * (@)# DlwProdServiceImpl.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwPayCloseService;
import powerservice.business.dlw.service.DlwDoubleService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Service
public class DlwDoubleServiceImpl extends EgovAbstractServiceImpl implements DlwDoubleService {


    @Resource
    public DlwDoubleDAO dlwDoubleDAO;


    /**
     * 다구좌 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectDoubleList(Map<String, Object> pmParam) throws Exception {
        return dlwDoubleDAO.selectDoubleList(pmParam);
    }
    
    /**
     * 다구좌 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectDoubleSrchList(Map<String, Object> pmParam) throws Exception {
        return dlwDoubleDAO.selectDoubleSrchList(pmParam);
    }
    
    /**
    *
    * 다구좌 등록
    *
    * @param pmParam 검색 조건
    * @return 
    * @throws Exception
    */
   public int saveDouble(Map<String, ?> pmParam) throws Exception {
       return dlwDoubleDAO.saveDouble(pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwDoubleKey(Map<String, Object> pmParam) throws Exception {
       return dlwDoubleDAO.getDlwDoubleKey(pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getAccntNoDbl(Map<String, Object> pmParam) throws Exception {
       return dlwDoubleDAO.getAccntNoDbl(pmParam);
   }
   
   public int getAccntNoDblCount(Map<String, Object> pmParam) {
       return dlwDoubleDAO.getAccntNoDblCount(pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwPayInfo(Map<String, Object> pmParam) throws Exception {
       return dlwDoubleDAO.getDlwPayInfo(pmParam);
   }
   
   /**
    * Card 정보 업데이트
    * 
    * 
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public void updateCardAccnt(Map<String, ?> pmParam) throws Exception {

	   dlwDoubleDAO.updateCardAccnt(pmParam);
   }    
   
   /**
    * Cms 정보 업데이트
    * 
    * 
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public void updateCmsAccnt(Map<String, ?> pmParam) throws Exception {

	   dlwDoubleDAO.updateCmsAccnt(pmParam);
   }
   
   /**
    * 다구좌 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwDoubleTripleChk(Map<String, Object> pmParam) throws Exception {
       return dlwDoubleDAO.getDlwDoubleTripleChk(pmParam);
   }
   
   /**
   *
   * 다구좌 삭제
   *
   * @param pmParam 검색 조건
   * @return 
   * @throws Exception
   */
  public int deleteDouble(Map<String, ?> pmParam) throws Exception {
      return dlwDoubleDAO.deleteDouble(pmParam);
  }
}
