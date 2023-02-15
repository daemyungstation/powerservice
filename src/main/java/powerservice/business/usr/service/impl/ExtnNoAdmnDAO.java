/*
 * (@)# ExtnNoAdmnDAO.java
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
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExtnNoAdmn
 */
@Repository
public class ExtnNoAdmnDAO extends EgovAbstractMapper {

    /**
     * 아이피 및 내선번호 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 아이피 및 내선번호 정보의 검색 수
     * @throws Exception
     */
    public int getExtnNoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExtnNoAdmnMap.getExtnNoCount", pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 아이피 및 내선번호 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtnNoList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtnNoAdmnMap.getExtnNoList", pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 아이피 및 내선번호 정보
     * @throws Exception
     */
    public Map<String, Object> getExtnNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExtnNoAdmnMap.getExtnNoList", pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 등록한다.
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public int insertExtnNo(Map<String, ?> pmParam) throws Exception {
        return insert("ExtnNoAdmnMap.insertExtnNo", pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 수정한다.
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public int updateExtnNo(Map<String, ?> pmParam) throws Exception {
        return update("ExtnNoAdmnMap.updateExtnNo", pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 삭제한다.
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public int deleteExtnNo(Map<String, ?> pmParam) throws Exception {
        return delete("ExtnNoAdmnMap.deleteExtnNo", pmParam);
    }

    /**
     * 아이피로 내선번호 정보를 검색한다.
     *
     * @param pmParam 아이피
     * @return 내선번호 정보
     * @throws Exception
     */
    public Map<String, Object> getExtnNoByIpAddr(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExtnNoAdmnMap.getExtnNoByIpAddr", pmParam);
    }
}
