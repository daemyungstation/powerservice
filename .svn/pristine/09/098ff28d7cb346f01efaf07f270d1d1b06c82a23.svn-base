/*
 * (@)# ConsScrtDtlDao.java
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * FAQ 요청 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/13
 * @프로그램ID ConsScrtDtl
 */

@Repository
public class ConsScrtDtlDAO extends EgovAbstractMapper {

    /**
     * 상담스크립트 정보의 검색 수를 반환한다
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 정보의 검색 수
     * @throws Exception
     */
    public int getConsScrtCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsScrtDtlMap.getConsScrtCount", pmParam);
    }

    /**
     * 상담스크립트 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsScrtList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsScrtDtlMap.getConsScrtList", pmParam);
    }

    /**
     * 상담스크립트 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getConsScrt(Map<String, ?> pmParam) throws Exception {
        return selectOne("ConsScrtDtlMap.getConsScrtView", pmParam);
    }

    /**
     * 상담스크립트 정보를 등록한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int insertConsScrt(Map<String, ?> pmParam) throws Exception {
        return insert("ConsScrtDtlMap.insertConsScrt", pmParam);
    }

    /**
     * 상담스크립트 정보를 수정한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int updateConsScrt(Map<String, ?> pmParam) throws Exception {
        return update("ConsScrtDtlMap.updateConsScrt", pmParam);
    }

    /**
     * 상담스크립트 정보를 삭제한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int deleteConsScrt(Map<String, ?> pmParam) throws Exception {
        return delete("ConsScrtDtlMap.deleteConsScrt", pmParam);
    }

}
