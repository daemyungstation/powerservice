/*
 * (@)# NobdTypService.java
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
 * 게시판 유형 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/17
 * @프로그램ID NobdTyp
 */

@Repository
public class NobdTypDAO extends EgovAbstractMapper {

    /**
     * 게시판 유형 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 유형 정보의 검색 수
     * @throws Exception
     */
    public int getNobdTypCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("NobdTypMap.getNobdTypCount", pmParam);
    }

    /**
     * 게시판 유형 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdTypList(Map<String, ?> pmParam) throws Exception {
        return selectList("NobdTypMap.getNobdTypList", pmParam);
    }

    /**
     * 게시판 유형 정보를 검색한다.
     *
     * @param 게시판 유형 ID
     * @return 게시판 유형 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getNobdTyp(Map<String, ?> pmParam) throws Exception {
        return selectOne("NobdTypMap.getNobdTypList", pmParam);
    }

    /**
     * 게시판 유형 정보를 등록한다.(ajax)
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public int insertNobdTyp(Map<String, ?> pmParam) throws Exception {
        return insert("NobdTypMap.insertNobdTyp", pmParam);
    }

    /**
     * 게시판 유형 정보를 수정한다.(ajax)
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public int updateNobdTyp(Map<String, ?> pmParam) throws Exception {
        return update("NobdTypMap.updateNobdTyp", pmParam);
    }

    /**
     * 게시판 유형(에디트,파일,댓글) 권한 유무 설정하기 위해 검색한다.
     *
     * @return 게시판 유형 유무 권한 리스트
     */
    public List<Map<String, Object>> getChkFg()throws Exception {
        return selectList("NobdTypMap.getChkFg");
    }

    /**
     * 게시판 유형 정보를 삭제한다.
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public int deleteNobdTyp(Map<String, Object> pmParam) throws Exception {
        return delete("NobdTypMap.deleteNobdTyp", pmParam);
    }

}
