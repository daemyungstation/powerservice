/*
 * (@)# ExamPrbmDAO.java
 *
 * @author 이희철
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
 * 시험문제항목를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID ExamPrbmitem
 */

@Repository
public class ExamPrbmItemDAO extends EgovAbstractMapper {

     /**
     * 시험문제항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmItemCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmItemMap.getExamPrbmItemCount", pmParam);
    }

    /**
     * 시험문제항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmItemList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmItemMap.getExamPrbmItemList", pmParam);
    }

    /**
     * 시험문제항목 정보를 저장한다.
     *
     * @param param 시험문제항목 정보
     * @throws Exception
     */
    public int insertExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmItemMap.insertExamPrbmItem", pmParam);
    }

    /**
     * 시험문제항목 정보를 저장한다.
     *
     * @param param 시험문제항목 정보
     * @throws Exception
     */
    public int updateExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmItemMap.updateExamPrbmItem", pmParam);
    }

    /**
     * 시험문제항목 정보를 삭제한다.
     *
     * @param param 시험문제항목 정보
     * @throws Exception
     */
    public int deleteExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmItemMap.deleteExamPrbmItem", pmParam);
    }


    /**
     * 시험문제항목 응시 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmItemAnsrList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmItemMap.getExamPrbmItemAnsrList", pmParam);
    }

    /**
     * 시험문제항목 항목정보를 수정한다 (순서수정)
     *
     * @param pmParam 시험문제항목 정보
     * @throws Exception
     */
    public int updateExamPrbmItemExpeSqnc(Map<String, ?> pmParam) throws Exception {
        return update("ExamPrbmItemMap.updateExamPrbmItemExpeSqnc", pmParam);
    }
}
