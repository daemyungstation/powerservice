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
import powerservice.business.dlw.service.DlwSlipService;
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
public class DlwSlipServiceImpl extends EgovAbstractServiceImpl implements DlwSlipService {


    @Resource
    public DlwSlipDAO dlwSlipDAO;


    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnList(Map<String, Object> pmParam) throws Exception {
        return dlwSlipDAO.selectSlipResnList(pmParam);
    }
    
    /**
     * 해약전표 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnCloseList(Map<String, Object> pmParam) throws Exception {
        return dlwSlipDAO.selectSlipResnCloseList(pmParam);
    }
    
    /**
     * 해약전표 발급현황 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnDetailList(Map<String, Object> pmParam) throws Exception {
        return dlwSlipDAO.selectSlipResnDetailList(pmParam);
    }
    
    /**
     * 해약전표 발급현황 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectSlipResnDetailCloseList(Map<String, Object> pmParam) throws Exception {
        return dlwSlipDAO.selectSlipResnDetailCloseList(pmParam);
    }
    
    /**
    *
    * 해약전표마감데이터
    *
    * @param pmParam 검색 조건
    * @return 해약 정보
    * @throws Exception
    */
   public int updateResnSlipClose(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.updateResnSlipClose(pmParam);
   }
    
    /**
    *
    * 해약전표마감데이터
    *
    * @param pmParam 검색 조건
    * @return 해약 정보
    * @throws Exception
    */
   public int updateResnDetail(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.updateResnDetail(pmParam);
   }
   
   /**
    * 
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getSlipResnCloseChk(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.getSlipResnCloseChk(pmParam);
   }
   
   /**
    * 카드전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardList(Map<String, Object> pmParam) throws Exception {
       return dlwSlipDAO.selectSlipCardList(pmParam);
   }
   
   /**
    * 
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getSlipCardCloseChk(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.getSlipCardCloseChk(pmParam);
   }
   
   /**
    * 카드전표 발급현황 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardDetailList(Map<String, Object> pmParam) throws Exception {
       return dlwSlipDAO.selectSlipCardDetailList(pmParam);
   }
   
   /**
    * 카드전표 발급현황 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardDetailCloseList(Map<String, Object> pmParam) throws Exception {
       return dlwSlipDAO.selectSlipCardDetailCloseList(pmParam);
   }
   
   /**
    * 카드 마감된 승인 데이터 CNT
    *
    * @param pmParam 검색 조건
    * @return 실시간카드결제 로그정보의 건수
    * @throws Exception
    */
   public int SlipCardDetailCloseCnt(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.SlipCardDetailCloseCnt(pmParam);
   }
   
   /**
    * 카드 실시간 승인 데이터 CNT
    *
    * @param pmParam 검색 조건
    * @return 실시간카드결제 로그정보의 건수
    * @throws Exception
    */
   public int SlipCardDetailCnt(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.SlipCardDetailCnt(pmParam);
   }
   
   /**
    * 카드취소전표 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 상품 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> selectSlipCardCancelList(Map<String, Object> pmParam) throws Exception {
       return dlwSlipDAO.selectSlipCardCancelList(pmParam);
   }
   
   /**
    *
    * 카드전표마감데이터
    *
    * @param pmParam 검색 조건
    * @return 해약 정보
    * @throws Exception
    */
   public int updateCardDetail(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.updateCardDetail(pmParam);
   }
   
   /**
    *
    * 카드전표마감데이터
    *
    * @param pmParam 검색 조건
    * @return 해약 정보
    * @throws Exception
    */
   public int updateCardSlipClose(Map<String, ?> pmParam) throws Exception {
       return dlwSlipDAO.updateCardSlipClose(pmParam);
   }
}
