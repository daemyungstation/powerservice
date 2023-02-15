/*
 * (@)# DlwRcrtCntrDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
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
 * 대명라이프웨이 모집인계약서 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
 * @프로그램ID DlwRcrtCntrDAO
 */
@Repository
public class DlwRcrtCntrDAO extends EgovAbstractMapper {

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
     * 모집인계약서 출력 정보 수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getRcrtCntrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwRcrtCntrMap.getRcrtCntrCount", pmParam);
    }

    /**
     * 모집인계약서 출력 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRcrtCntrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwRcrtCntrMap.getRcrtCntrList", pmParam);
    }
    
    /**
     * 파일 정보를 입력한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int insertFile(Map<String, ?> pmParam) throws Exception {
        return insert("DlwRcrtCntrMap.insertFile", pmParam);
    }

}
