/*
 * (@)# EmilRecpDAO.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/06/16
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

package powerservice.business.mta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/10/26
 * @프로그램ID CoBaVl
 */
@Repository
public class EmilRecpDAO extends EgovAbstractMapper {

    /**
     * 컬럼 기본값 관리의 검색 수를 반환한다. (15.04.15)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getEmilRecpCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("EmilRecpMap.getEmilRecpCount", pmParam);
    }

    /**
     * 컬럼 기본값 관리를 검색한다. (15.04.15)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEmilRecpList(Map<String, ?> pmParam) throws Exception {
        return selectList("EmilRecpMap.getEmilRecpList", pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 검색한다. (15.04.15)
     *
     * @param id 컬럼 기본값
     * @return 컬럼 기본값 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEmilRecp(Map<String, ?> pmParam) throws Exception {
        return selectOne("EmilRecpMap..getEmilRecpList", pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 등록한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public int insertEmilRecp(Map<String, ?> pmParam) throws Exception {
        return insert("EmilRecpMap.insertEmilRecp", pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 수정한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public int updateEmilRecp(Map<String, ?> pmParam) throws Exception {
        return update("EmilRecpMap.updateEmilRecp", pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 수정한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public int updateEmilRecpCntn(Map<String, ?> pmParam) throws Exception {
        return update("EmilRecpMap.updateEmilRecpCntn", pmParam);
    }
}
