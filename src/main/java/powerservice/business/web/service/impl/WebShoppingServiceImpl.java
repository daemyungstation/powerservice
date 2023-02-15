/*
 * (@)# WebConsServiceImpl.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
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

package powerservice.business.web.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.web.service.WebConsService;
import powerservice.business.web.service.WebShoppingService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
 * @프로그램ID WebCons
 */

@Service
public class WebShoppingServiceImpl extends EgovAbstractServiceImpl implements WebShoppingService {

    @Resource
    public WebShoppingDAO webShoppingDAO;

   /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. START */

   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
    * ================================================================
    * */
   public List<Map<String, Object>> getMemberBasicList(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getMemberBasicList(pmParam);
   }


   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 사용/취소금액 조회
    * ================================================================
    * */
   public List<Map<String, Object>> getMemberBasicListUseAmt(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getMemberBasicListUseAmt(pmParam);
   }

   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
    * ================================================================
    * */
   public int insertMemberBasicAmt(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.insertMemberBasicAmt(pmParam);
   }

   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
    * ================================================================
    * */
   public int updateResnMemberState(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.updateResnMemberState(pmParam);
   }

   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 행사여부 확인
    * ================================================================
    * */
   public List<Map<String, Object>> getResnMemberStateEvent(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getResnMemberStateEvent(pmParam);
   }

   /* ================================================================
    * 날짜 : 20171221
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부 확인
    * ================================================================
    * */
   public List<Map<String, Object>> getResnMemberStateRescission(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getResnMemberStateRescission(pmParam);
   }
   /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. END */


   /* ================================================================
    * 날짜 : 20171222
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금정보 전체 조회
    * ================================================================
    * */
   public List<Map<String, Object>> getMemberBasicAllAmtInfomation(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getMemberBasicAllAmtInfomation(pmParam);
   }

   /* ================================================================
    * 날짜 : 20171227
    * 이름 : 송준빈
    * 추가내용 : 쇼핑몰적립금사용금정보 건수 조회
    * ================================================================
    * */
   public int getTotalCount(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getTotalCount(pmParam);
   }
   
   /* ================================================================
    * 날짜 : 20220418
    * 이름 : 김주용
    * 추가내용 : 쇼핑몰집계
    * ================================================================
    * */
   public List<Map<String, Object>> getReadyCashTotalList(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getReadyCashTotalList(pmParam);
   }
   
   /* ================================================================
    * 날짜 : 20220418
    * 이름 : 김주용
    * 추가내용 : 쇼핑몰집계
    * ================================================================
    * */
   public List<Map<String, Object>> getReadyCashList(Map<String, ?> pmParam) throws Exception {
       return webShoppingDAO.getReadyCashList(pmParam);
   }

}
