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
 * 통계 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltTyp
 */
@Repository
public class EvltTypBankDAO extends EgovAbstractMapper {

    /**
     * 평가유형 트리 정보을 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypTree(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltTypBankMap.getEvltTypTree", pmParam);
    }

    /**
     * 평가유형 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가유형 정보의 검색 수
     * @throws Exception
     */
    public int getEvltTypCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("EvltTypBankMap.getEvltTypCount", pmParam);
    }

    /**
     * 평가유형 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltTypBankMap.getEvltTypList", pmParam);
    }

    /**
     * 평가유형 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가유형 정보
     * @throws Exception
     */
    public Map<String, Object> getEvltTypRoot(Map<String, ?> pmParam) throws Exception {
        return selectOne("EvltTypBankMap.getEvltTypRoot", pmParam);
    }

    /**
     * 평가유형 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가유형 정보
     * @throws Exception
     */
    public Map<String, Object> getEvltTypView(Map<String, ?> pmParam) throws Exception {
        return selectOne("EvltTypBankMap.getEvltTypView", pmParam);
    }


    /**
     * 평가유형 정보의 순서를 수정한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int updateEvltTypOrd(Map<String, ?> pmParam) throws Exception {
        return update("EvltTypBankMap.updateEvltTypOrd", pmParam);
    }

    /**
     * 평가유형 정보를 등록한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int insertEvltTyp(Map<String, ?> pmParam) throws Exception {
        return insert("EvltTypBankMap.insertEvltTyp", pmParam);
    }

    /**
     * 평가유형 정보를 수정한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int updateEvltTyp(Map<String, Object> pmParam) throws Exception {
        return update("EvltTypBankMap.updateEvltTyp", pmParam);
    }

    /**
     * 평가유형 정보를 삭제한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int deleteEvltTyp(Map<String, Object> pmParam) throws Exception {
        return delete("EvltTypBankMap.deleteEvltTyp", pmParam);
    }

    /**
     * 평가유형 DROPDOWNLIST
     *
     * @param param 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypDropdownList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltTypBankMap.getEvltTypDropdownList", pmParam);
    }
}
