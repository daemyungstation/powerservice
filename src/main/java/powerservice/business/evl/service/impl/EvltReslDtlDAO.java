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
public class EvltReslDtlDAO extends EgovAbstractMapper {

    /**
     * 평가결과(상담별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 정보의 검색 수
     * @throws Exception
     */
    public int getEvltReslDtlCountByCons(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltReslDtlMap.getEvltReslDtlCountByCons", pmParam);
    }

    /**
     * 평가결과(상담별) 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltReslDtlListByCons(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltReslDtlMap.getEvltReslDtlListByCons", pmParam);
    }

    /**
     * 평가결과 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 정보의 검색 수
     * @throws Exception
     */
    public int getEvltReslDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltReslDtlMap.getEvltReslDtlCount", pmParam);
    }

    /**
     * 평가결과 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltReslDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltReslDtlMap.getEvltReslDtlList", pmParam);
    }

    /**
     * 평가수행-평가지 상세정보(1건)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가지 상세정보
     * @throws Exception
     */
    public Map<String, Object> getQaResultSheet(Map<String, ?> pmParam) throws Exception {
        return selectOne("EvltReslDtlMap.getQaResultSheet", pmParam);
    }

    /**
     * 평가수행-평가지 상세정보(1건)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가지 상세정보
     * @throws Exception
     */
    public Map<String, Object> getQaResultSheetId(Map<String, ?> pmParam) throws Exception {
        return selectOne("EvltReslDtlMap.getQaResultSheet", pmParam);
    }


    /**
     * 평가지 결과정보를 등록한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public int insertEvltReslItemDtl(Map<String, ?> pmParam) throws Exception {
        return insert("EvltReslDtlMap.insertEvltReslItemDtl", pmParam);
    }

    /**
     * 평가지 결과정보를 등록한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public int insertEvltReslDtl(Map<String, ?> pmParam) throws Exception {
        return insert("EvltReslDtlMap.insertEvltReslDtl", pmParam);
    }

    /**
     * 평가지 결과정보를 수정한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public int updateEvltReslDtl(Map<String, ?> pmParam) throws Exception {
        return update("EvltReslDtlMap.updateEvltReslDtl", pmParam);
    }

    /**
     * 평가지 결과정보를 삭제한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public int deleteEvltReslItemDtl(Map<String, ?> pmParam) throws Exception {
        return delete("EvltReslDtlMap.deleteEvltReslItemDtl", pmParam);
    }


}
