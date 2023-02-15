/*
 * (@)# ToDoDAO.java
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

package powerservice.business.cns.service.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * TO-DO 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ToDo
 */
@Repository
public class TodoDAO extends EgovAbstractMapper {

    /**
     * TO-DO 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return TO-DO 리스트
     * @throws Exception
     */
    public Map<String, Object> getTodoCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("TodoMap.getTodoCount", pmParam);
    }

}
