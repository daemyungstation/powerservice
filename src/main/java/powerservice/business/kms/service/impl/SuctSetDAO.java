/*
 * (@)# SuctSetDao.java
 *
 * @author 차건호
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
 * 퀵링크 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID SuctSet
 */

@Repository
public class SuctSetDAO extends EgovAbstractMapper {

    /**
     * 퀵링크 정보를 등록한다.
     *
     * @param pmParam 퀵링크 정보
     * @throws Exception
     */
    public int insertSuctSet(Map<String, ?> pmParam) throws Exception {
        return insert("SuctSetMap.insertSuctSet", pmParam);
    }

    /**
     * 퀵링크 중복된 순서를 자동 증가한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 정보의 검색 건수
     * @throws Exception
     */
    public int updateQLinkSetOrder(Map<String, ?> pmParam) throws Exception {
        return update("SuctSetMap.updateQLinkSetOrder", pmParam);
    }

    /**
     * 퀵링크 정보를 수정한다.
     *
     * @param pmParam 퀵링크 정보
     * @throws Exception
     */
    public int updateSuctSet(Map<String, ?> pmParam) throws Exception {
        return update("SuctSetMap.updateSuctSet", pmParam);
    }

    /**
     * 퀵링크 정보를 수정한다.
     *
     * @param pmParam 퀵링크 정보
     * @throws Exception
     */
    public int updateSuctUnused(Map<String, ?> pmParam) throws Exception {
        return update("SuctMap.updateSuctUnused", pmParam);
    }

    /**
     * 퀵링크 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 정보의 검색 건수
     * @throws Exception
     */
    public int getSuctSetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctSetMap.getSuctSetCount", pmParam);
    }

    /**
     * 퀵링크 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSuctSetList(Map<String, ?> pmParam) throws Exception {
        return selectList("SuctSetMap.getSuctSetList", pmParam);
    }

    /**
     * 퀵링크 정보를 검색한다.
     *
     * @param String 퀵링크 ID
     * @return 퀵링크 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSuctSet(Map<String, ?> pmParam) throws Exception {
        return selectOne("SuctSetMap.getSuctSetList", pmParam);
    }

    /**
     * 퀵링크 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 순서 최대값
     * @throws Exception
     */
    public int getSuctSetMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctSetMap.getSuctSetMaxOrder", pmParam);
    }

    /**
     * 퀵링크 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 순서의 중복 건수
     * @throws Exception
     */
    public int getSuctSetDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctSetMap.getSuctSetDuplicateCount", pmParam);
    }

    /**
     * 퀵링크셋 정보를 삭제한다.
     *
     * @param pmParam 퀵링크셋 아이디
     * @throws Exception
     */
    public int deleteSuctSet(Map<String, ?> pmParam) throws Exception {
        return delete("SuctSetMap.deleteSuctSet", pmParam);
    }

    /**
     * 퀵링크 정보를 삭제한다.
     *
     * @param pmParam 퀵링크셋 아이디
     * @throws Exception
     */
    public int deleteSuct(Map<String, ?> pmParam) throws Exception {
        return delete("SuctMap.deleteSuct", pmParam);
    }


}
