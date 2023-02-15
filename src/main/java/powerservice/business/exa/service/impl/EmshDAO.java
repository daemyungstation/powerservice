/*
 * (@)# CoBaVlDAO.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/15
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

package powerservice.business.exa.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 시험지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID ExamPaper
 */

@Repository
public class EmshDAO extends EgovAbstractMapper {

    /**
     * 시험지 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험지 정보의 검색 수
     * @throws Exception
     */
    public int getEmshCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EmshMap.getEmshCount", pmParam);
    }

    /**
     * 시험지 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험지 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEmshList(Map<String, ?> pmParam) throws Exception {
        return selectList("EmshMap.getEmshList", pmParam);
    }

    /**
     * 시험지 정보를 등록한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int insertEmsh(Map<String, Object> pmParam) throws Exception {
        return insert("EmshMap.insertEmsh", pmParam);

    }

    /**
     * 시험지 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험지 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEmsh(Map<String, ?> pmParam) throws Exception {
        return selectOne("EmshMap.getEmshList", pmParam);
    }

    /**
     * 시험지 정보를 수정한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int updateEmsh(Map<String, Object> pmParam) throws Exception {
        return update("EmshMap.updateEmsh", pmParam);
    }

    /**
     * 시험지 정보를 수정한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int updateExamPrbmDtscVl(Map<String, Object> pmParam) throws Exception {
        return update("ExamPrbmMap.updateExamPrbmDtscVl", pmParam);
    }

    /**
     * 시험지 정보를 삭제한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int deleteExamPrbmItemByEmshId(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmItemMap.deleteExamPrbmItemByEmshId", pmParam);
    }

    /**
     * 시험지 정보를 삭제한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int deleteExamPrbm(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmMap.deleteExamPrbm", pmParam);
    }

    /**
     * 시험지 정보를 삭제한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int deleteEmsh(Map<String, Object> pmParam) throws Exception {
        return delete("EmshMap.deleteEmsh", pmParam);
    }

    /**
     * 시험지 정보를 복사한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int copyEmsh(Map<String, Object> pmParam) throws Exception {
        return insert("EmshMap.copyEmsh", pmParam);
    }

    /**
     * 시험지 정보를 복사한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmList(Map<String, Object> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmList", pmParam);
    }

    /**
     * 시험지 정보를 복사한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int copyExamPrbm(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmMap.copyExamPrbm", pmParam);
    }

    /**
     * 시험지 정보를 복사한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int copyExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmItemMap.copyExamPrbmItem", pmParam);
    }
}
