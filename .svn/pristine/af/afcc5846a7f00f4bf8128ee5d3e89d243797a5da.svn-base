/*
 * (@)# BasVlDAO.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 기준값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BasVl
 */
@Repository
public class BasVlDAO extends EgovAbstractMapper {

    /**
     * 기준값 정보를 등록한다.
     *
     * @param pmParam 기준값 정보
     * @throws Exception
     */
    public int insertBasVl(Map<String, ?> pmParam) {
        return insert("BasVlMap.insertBasVl", pmParam);
    }

    /**
     * 기준값 정보를 수정한다.
     *
     * @param pmParam 기준값 정보
     * @throws Exception
     */
    public int updateBasVl(Map<String, ?> pmParam) {
        return update("BasVlMap.updateBasVl", pmParam);
    }

    /**
     * 기준값 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 기준값 정보의 검색 건수
     * @throws Exception
     */
    public int getBasVlCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("BasVlMap.getBasVlCount", pmParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 기준값 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBasVlList(Map<String, ?> pmParam) {
        return selectList("BasVlMap.getBasVlList", pmParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param sId 기준값ID
     * @return 기준값 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getBasVl(Map<String, ?> pmParam) throws Exception {
        return selectOne("BasVlMap.getBasVlList", pmParam);
    }

    public int updateMonthUnpy(Map<String, ?> pmParam) {
        return update("BasVlMap.updateMonthUnpy", pmParam);
    }

    /**
     * 기준값 필터 목록에 따라 기준값 정보를 검색한다.
     *
     * @param psItemList 검색 조건
     * @return 기준값 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getBasVlListByStringArray(Map<String, ?> pmParam) throws Exception {
        return selectList("BasVlMap.getBasVlListByStringArray", pmParam);
    }

}
