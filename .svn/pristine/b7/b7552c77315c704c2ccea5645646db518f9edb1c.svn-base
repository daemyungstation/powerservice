/*
 * (@)# DlwCondServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
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

package powerservice.business.shopsend.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCondService;
import powerservice.business.shopsend.service.ShopSendService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * @프로그램ID DlwCondServiceImpl
 */
@Service
public class ShopSendServiceImpl extends EgovAbstractServiceImpl implements ShopSendService {

    @Resource
    public ShopSendDAO ShopSendDAO;

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 cash history를 전송
     * ================================================================
     * */
    public void insertshopSendCashHistory() throws Exception {
        Map<String, ?> pmParam = null;
        System.out.println("[ShopSendServiceImpl.insertshopSendCashHistory] batch start!!!!!");
        ShopSendDAO.insertshopSendCashHistory(pmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 member history를 전송
     * ================================================================
     * */
    public void insertshopSendMemberHistory() throws Exception {
        Map<String, ?> pmParam = null;
        System.out.println("[ShopSendServiceImpl.insertshopSendMemberHistory] batch start!!!!!");
        ShopSendDAO.insertshopSendMemberHistory(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20180612
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 Point Payment 를 전송
     * ================================================================
     * */
    public void insertshopSendPointPayment() throws Exception {
        Map<String, ?> pmParam = null;
        System.out.println("[ShopSendServiceImpl.insertshopSendPointPayment] batch start!!!!!");
        ShopSendDAO.insertshopSendPointPayment(pmParam);
    }    
}