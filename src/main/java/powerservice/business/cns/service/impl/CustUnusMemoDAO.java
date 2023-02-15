/*
 * (@)# CustUnusMemoDAO.java
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

package powerservice.business.cns.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 고객 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CustUnusMemo
 */
@Repository
public class CustUnusMemoDAO extends EgovAbstractMapper {

    /**
     * 고객 특이메모 정보를 등록한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public int insertCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return insert("CustUnusMemoMap.insertCustUnusMemo", pmParam);
    }

    /**
     * 고객 특이메모 정보를 수정한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public int updateCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return update("CustUnusMemoMap.updateCustUnusMemo", pmParam);
    }

    /**
     * 고객 특이메모 정보를 삭제한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public int deleteCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return delete("CustUnusMemoMap.deleteCustUnusMemo", pmParam);
    }

    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CustUnusMemoMap.getCustUnusMemoCount", pmParam);
    }

    /**
     * 고객 특이메모 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCustUnusMemoList(Map<String, ?> pmParam) throws Exception {
        return selectList("CustUnusMemoMap.getCustUnusMemoList", pmParam);
    }

    /**
     * 고객 특이메모 정보를 검색한다.
     *
     * @param psId 고객 특이메모 ID
     * @return 고객 특이메모 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return selectOne("CustUnusMemoMap.getCustUnusMemoList", pmParam);
    }

    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CustUnusMemoMap.getCustUnusMemoCnt", pmParam);
    }

    /**
     * 특이고객관리 엑셀 업로드
     */
    public int insertSpecialAccntExcel(Map<String, ?> pmParam) throws Exception {
        return insert("CustUnusMemoMap.insertSpecialAccntExcel", pmParam);
    }

}
