/*
 * (@)# FcbkDAO.java
 *
 * @author 유오성
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

package powerservice.business.chn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 페이스북 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 유오성
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Fcbk
 */
@Repository
public class FcbkDAO extends EgovAbstractMapper {

    /**
     * 페이스북 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("FcbkMap.getFcbkCount", pmParam);
    }

    /**
     * 페이스북 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkList(Map<String, ?> pmParam) throws Exception {
        return selectList("FcbkMap.getFcbkList", pmParam);
    }

    /**
     * 페이스북 정보(1건)를 검색한다.
     *
     * @param pmParam 페이스북 ID
     * @return 페이스북 정보
     * @throws Exception
     */
    public Map<String, Object> getFcbk(Map<String, ?> pmParam) throws Exception {
        return selectOne("FcbkMap.getFcbkList", pmParam);
    }

    /**
     * 페이스북 댓글 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkCmmnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("FcbkMap.getFcbkCmmnCount", pmParam);
    }

    /**
     * 페이스북 댓글 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkCmmnList(Map<String, ?> pmParam) throws Exception {
        return selectList("FcbkMap.getFcbkCmmnList", pmParam);
    }

    /**
     * 페이스북 댓글 댓글 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 목록
     * @throws Exception
     */
    public Map<String, Object> getFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        return selectOne("FcbkMap.getFcbkCmmnList", pmParam);
    }

    /**
     * 페이스북 사진 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkPhtoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("FcbkMap.getFcbkPhtoCount", pmParam);
    }

    /**
     * 페이스북 사진 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkPhtoList(Map<String, ?> pmParam) throws Exception {
        return selectList("FcbkMap.getFcbkPhtoList", pmParam);
    }

    /**
     * 페이스북 사진 댓글 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 목록
     * @throws Exception
     */
    public Map<String, Object> getFcbkPhto(Map<String, ?> pmParam) throws Exception {
        return selectOne("FcbkMap.getFcbkPhtoList", pmParam);
    }

    /**
     * 포스트 댓글 정보를 등록한다.
     *
     * @param pmParam 포스트 댓글 정보
     * @throws Exception
     */
    public int insertFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        return insert("FcbkMap.insertFcbkCmmn", pmParam);
    }

    /**
     * 페이스북 댓글 정보를 수정한다.
     *
     * @param pmParam 포스트 댓글 정보
     * @throws Exception
     */
    public int updateFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        return update("FcbkMap.updateFcbkCmmn", pmParam);
    }

    /**
     * 포스트 사진 정보를 저장한다.
     *
     * @param pmParam 포스트 사진 정보
     * @throws Exception
     */
    public int mergeFcbkPhto(Map<String, ?> pmParam) throws Exception {
        return update("FcbkMap.mergeFcbkPhto", pmParam);
    }

    /**
     * 포스트 댓글 정보를 저장한다.
     *
     * @param pmParam 포스트 댓글 정보
     * @throws Exception
     */
    public int mergeFaceCmmnbook(Map<String, ?> pmParam) throws Exception {
        return update("FcbkMap.mergeFaceCmmnbook", pmParam);
    }

    /**
     * 포스트 정보를 저장한다.
     *
     * @param pmParam 포스트 정보
     * @throws Exception
     */
    public int mergeFcbk(Map<String, ?> pmParam) throws Exception {
        return update("FcbkMap.mergeFcbk", pmParam);
    }

}
