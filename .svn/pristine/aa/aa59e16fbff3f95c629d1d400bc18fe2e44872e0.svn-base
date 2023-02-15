/*
 * (@)# ExccConsExmpDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 우수상담사례 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/15
 * @프로그램ID ExccConsExmp
 */

@Repository
public class ExccConsExmpDAO extends EgovAbstractMapper {

    /**
     * 우수상담사례 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 우수상담사례 정보의 검색 수
     * @throws Exception
     */
    public int getExccConsExmpCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExccConsExmpMap.getExccConsExmpCount", pmParam);
    }

    /**
     * 우수상담사례 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 우수상담사례 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExccConsExmpList(Map<String, Object> pmParam) throws Exception {
        return selectList("ExccConsExmpMap.getExccConsExmpList", pmParam);
    }

    /**
     * 우수상담사례 정보(1건)를 검색한다.
     *
     * @param 우수 상담 ID
     * @return 우수상담사례 정보
     * @throws Exception
     */
    public Map<String, Object> getExccConsExmp(Map<String, ?>pmParam) throws Exception {
        return selectOne("ExccConsExmpMap.getExccConsExmpView", pmParam);
    }

    /**
     * 우수상담사례 정보를 등록한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int insertExccConsExmp(Map<String, Object> pmParam) throws Exception {
        return insert("ExccConsExmpMap.insertExccConsExmp", pmParam);
    }

    /**
     * 우수상담사례 정보를 수정한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int updateExccConsExmp(Map<String, Object> pmParam) throws Exception {
        return update("ExccConsExmpMap.updateExccConsExmp", pmParam);
    }

    /**
     * 첨부파일에 게시판 아이디를 업데이트 한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int deleteFile(Map<String, Object> pmParam) throws Exception {
        return delete("FileMap.deleteFile", pmParam);
    }
    /**
     * 첨부파일에 게시판 아이디를 업데이트 한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        return update("FileMap.updateFile", pmParam);
    }

    /**
     * 우수상담사례 정보를 삭제한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int deleteExccConsExmp(Map<String, Object> pmParam) throws Exception {
        return delete("ExccConsExmpMap.deleteExccConsExmp", pmParam);
    }

    /**
     * QA추천콜-우수상담사례 정보를 등록한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int insertEvltExccConsExmp(Map<String, Object> pmParam) throws Exception {
        return insert("ExccConsExmpMap.insertQaExccConsExmp", pmParam);
    }

}
