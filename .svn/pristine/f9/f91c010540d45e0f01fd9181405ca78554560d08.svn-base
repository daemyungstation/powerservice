/*
 * (@)# XlsDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 엑셀 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Xls
 */
@Repository
public class XlsDAO extends EgovAbstractMapper {

    /**
     * 엑셀 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 정보의 검색 수
     * @throws Exception
     */
    public int getXlsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("XlsMap.getXlsCount", pmParam);
    }

    /**
     * 엑셀 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsList(Map<String, ?> pmParam) throws Exception {
        return selectList("XlsMap.getXlsList", pmParam);
    }

    /**
     * 엑셀 정보를 검색한다.
     *
     * @param pmParam 엑셀ID
     * @return 엑셀 1건
     * @throws Exception
     */
    public Map<String, Object> getXls(Map<String, ?> pmParam) throws Exception {
        return selectOne("XlsMap.getXlsList", pmParam);
    }

    /**
     * 엑셀 정보를 등록한다.
     *
     * @param pmParam 엑셀 정보
     * @throws Exception
     */
    public int insertXls(Map<String, ?> pmParam) throws Exception {
        return insert("XlsMap.insertXls", pmParam);
    }

    /**
     * 엑셀 정보를 수정한다.
     *
     * @param pmParam 엑셀항목 정보
     * @throws Exception
     */
    public int updateXls(Map<String, ?> pmParam) throws Exception {
        return update("XlsMap.updateXls", pmParam);
    }

    /**
     * 엑셀 정보를 삭제한다.
     *
     * @param pmParam 엑셀 정보
     * @throws Exception
     */
    public int deleteXls(Map<String, ?> pmParam) throws Exception {
        return delete("XlsMap.deleteXls", pmParam);
    }

}
