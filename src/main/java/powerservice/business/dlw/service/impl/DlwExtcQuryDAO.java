/*
 * (@)# DlwCmsDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;


@Repository
public class DlwExtcQuryDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 대상고객추출조건 쿼리검색
     *
     * @param String 추출조건 ID
     * @return 대상고객추출조건 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> chkExtcQury(Map<String, String> pmParam) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return selectOne("DlwExtcQuryMap.chkExtcQury", pmParam);
    }


}
