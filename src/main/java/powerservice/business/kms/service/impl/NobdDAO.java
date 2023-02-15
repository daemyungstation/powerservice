/*
 * (@)# NobdDAO.java
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
 * 게시판 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Nobd
 */
@Repository
public class NobdDAO extends EgovAbstractMapper {

    /**
     * 게시판 정보를 등록한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public int insertNobd(Map<String, ?> pmParam) throws Exception {
        return insert("NobdMap.insertNobd", pmParam);
    }

    /**
     * 게시판 정보를 수정한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public int updateNobd(Map<String, ?> pmParam) throws Exception {
        return update("NobdMap.updateNobd", pmParam);
    }

    /**
     * 게시판 정보를 삭제한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public int deleteNobd(Map<String, ?> pmParam) throws Exception {
        // 게시물 삭제
        return delete("NobdMap.deleteNobd", pmParam);
    }

    /**
     * 게시판 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 정보의 검색 수
     * @throws Exception
     */
    public int getNobdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("NobdMap.getNobdCount", pmParam);
    }

    /**
     * 게시판 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdList(Map<String, ?> pmParam) throws Exception {
        return selectList("NobdMap.getNobdList", pmParam);
    }

    /**
     * 게시판 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 리스트 (1건)
     * @throws Exception
     */
    public Map<String, Object> getNobd(Map<String, ?> pmParam) throws Exception {
        return selectOne("NobdMap.getNobdView", pmParam);
    }

}
