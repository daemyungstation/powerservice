/*
 * (@)# ScrtDao.java
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

package powerservice.business.cam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import powerservice.core.bean.ActionResult;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 스크립트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID Scrt
 */

@Repository
public class ScrtDAO extends EgovAbstractMapper {

    /**
     * 스크립트 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 정보의 검색 수
     * @throws Exception
     */
    public int getScrtCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("ScrtMap.getScrtCount", pmParam);
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getScrtList(Map<String, ?> pmParam) throws Exception {
        return selectList("ScrtMap.getScrtList", pmParam);
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getScrtView(Map<String, ?> pmParam) throws Exception {
        return selectOne("ScrtMap.getScrtView", pmParam);
    }

    /**
     * 스크립트 정보를 등록한다.
     *
     * @param pmParam 스크립트 정보
     * @throws Exception
     */
    public int insertScrt(Map<String, Object> pmParam) throws Exception {
        return insert("ScrtMap.insertScrt", pmParam);
    }

    /**
     * 스크립트 정보를 수정한다.
     *
     * @param pmParam 스크립트 정보
     * @throws Exception
     */
    public int updateScrt(Map<String, Object> pmParam) throws Exception {
        return update("ScrtMap.updateScrt", pmParam);
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param String 스크립트 ID
     * @return 스크립트 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getScrt(Map<String, Object> pmParam) throws Exception {
        return selectOne("ScrtMap.getScrtList", pmParam);
    }

    /**
     * 스크립트 정보를 삭제한다.
     *
     * @param param 스크립트 정보
     * @throws Exception
     */
    public int deleteScrt(Map<String, Object> pmParam) throws Exception {
        return delete("ScrtMap.deleteScrt", pmParam);
    }

    /**
     * 스크립트 미리보기정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getScrtPreview(Map<String, ?> pmParam) throws Exception {
        return selectOne("ScrtMap.getScrtPreview", pmParam);
    }

    /**
     * 첨부파일에 릴레이션 아이디를 업데이트 한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        return update("FileMap.updateFile", pmParam);
    }

}
