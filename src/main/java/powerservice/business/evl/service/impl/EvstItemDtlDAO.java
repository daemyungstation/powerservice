/*
 * (@)# RprtDAO.java
 *
 * @author 김은지
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 평가지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvstDtl
 */
@Repository
public class EvstItemDtlDAO extends EgovAbstractMapper {

    /**
     * 평가항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보의 검색 수
     * @throws Exception
     */
    public int getEvstItemDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("EvstItemDtlMap.getEvstItemDtlCount", pmParam);
    }

    /**
     * 평가항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstItemDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvstItemDtlMap.getEvstItemDtlList", pmParam);
    }

    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return insert("EvstItemDtlMap.insertEvstItemDtl", pmParam);
    }

    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int updateEvstDtl(Map<String, ?> pmParam) throws Exception {
        return update("EvstDtlMap.updateEvstDtl", pmParam);
    }

    /**
     * 평가유형 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertEvltTyp(Map<String, ?> pmParam) throws Exception {
        return insert("EvstItemDtlMap.insertEvltTyp", pmParam);
    }

    /**
     * 평가항목 정보를 삭제한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int deleteEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return delete("EvstItemDtlMap.deleteEvstItemDtl", pmParam);
    }

    /**
     * 평가유형 정보를 삭제한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int deleteEvltTyp(Map<String, Object> pmParam) throws Exception {
        return delete("EvstItemDtlMap.deleteEvltTyp", pmParam);
    }

    /**
     * 평가항목 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertCopyEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return insert("EvstItemDtlMap.insertCopyEvstItemDtl", pmParam);
    }

    /**
     * 평가유형 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertCopyEvltTyp(Map<String, ?> pmParam) throws Exception {
        return insert("EvstItemDtlMap.insertCopyEvltTyp", pmParam);
    }

    /**
     * 평가지 항목정보를 조회한다
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("EvstItemDtlMap.getEvstItemDtl", pmParam);
    }

    /**
     * 평가지 항목정보를 수정한다 (순서수정)
     *
     * @param pmParam 설문항목 정보
     * @throws Exception
     */
    public int updateEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return update("EvstItemDtlMap.updateEvstItemDtl", pmParam);
    }
}
