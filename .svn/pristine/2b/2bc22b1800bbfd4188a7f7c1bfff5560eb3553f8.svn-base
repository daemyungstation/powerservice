/*
 * (@)# CntrDAO.java
 *
 * @author 정용재
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

package powerservice.business.usr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 센터 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cntr
 */
@Repository
public class CntrDAO extends EgovAbstractMapper {

    /**
     * 센터 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 센터 정보의 검색 수
     * @throws Exception
     */
    public int getCntrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CntrMap.getCntrCount", pmParam);
    }

    public int getCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CntrMap.getCount", pmParam);
    }

    /**
     * 센터 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 센터 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCntrList(Map<String, ?> pmParam) throws Exception {
        return selectList("CntrMap.getCntrList", pmParam);
    }

    public Map<String, Object> getCntr(Map<String, ?> pmParam) throws Exception {
        return selectOne("CntrMap.getCntrList", pmParam);
    }


    /**
     * 센터 정보를 등록한다.
     *
     * @param pmParam 센터 정보
     * @throws Exception
     */
    public int insertCntr(Map<String, ?> pmParam) throws Exception {
        return insert("CntrMap.insertCntr", pmParam);
    }

    /**
     * 센터 정보를 수정한다.
     *
     * @param param 기준값 정보
     * @throws Exception
     */
    public int updateCntr(Map<String, ?> pmParam) throws Exception {
        return update("CntrMap.updateCntr", pmParam);
    }

    /**
     * 센터 순서를 수정한다.
     *
     * @param param 기준값 정보
     * @throws Exception
     */
    public int updateCntrOrder(Map<String, ?> pmParam) throws Exception {
        return update("CntrMap.updateCntrOrder", pmParam);
    }

    /**
     * 센터 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCntrMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CntrMap.getCntrMaxOrder", pmParam);
    }

    /**
     * 센터 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 센터 순서의 중복 건수
     * @throws Exception
     */
    public int getCntrDuplicateOrderCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CntrMap.getCntrDuplicateOrderCount", pmParam);
    }

}
