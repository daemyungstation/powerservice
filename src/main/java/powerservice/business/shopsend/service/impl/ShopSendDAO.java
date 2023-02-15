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

package powerservice.business.shopsend.service.impl;

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
public class ShopSendDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */

    /*
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    */

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 cash history를 전송
     * ================================================================
     * */
    public void insertshopSendCashHistory(Map<String, ?> pmParam) throws Exception {
        insert("ShopSendMap.insertshopSendCashHistory", pmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 member history를 전송
     * ================================================================
     * */
    public void insertshopSendMemberHistory(Map<String, ?> pmParam) throws Exception {
        insert("ShopSendMap.insertshopSendMemberHistory", pmParam);
    }

    /* ================================================================
     * 날짜 : 20180612
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰연동테이블에 Point Payment 를 전송
     * ================================================================
     * */
    public void insertshopSendPointPayment(Map<String, ?> pmParam) throws Exception {
        insert("ShopSendMap.insertshopSendPointPayment", pmParam);
    }
}
