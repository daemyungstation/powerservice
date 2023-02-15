/*
 * (@)# CmpgAlctDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID CmpgAlct
 */

@Repository
public class CmpgAlctDAO extends EgovAbstractMapper {

       /**
     * 캠페인할당 정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인할당 정보의 검색 수
     * @throws Exception
     */
    public int getCmpgAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("CmpgAlctMap.getCmpgAlctCount", pmParam);
    }

    public int getChkAlct(Map<String, Object> mParam) throws Exception {
        return (Integer) selectOne("CmpgAlctMap.getChkAlct", mParam);
    }

    /**
     * 캠페인할당 정보를 검색한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgAlctList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgAlctMap.getCmpgAlctList", pmParam);
    }
    /**
     * 나에게 할당된 캠페인 조회
     * .
     *
     * @param pmParam 검색 조건
     * @return 캠페인할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getMainCmpgAlctList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgAlctMap.getMainCmpgAlctList", pmParam);
    }

    /**
     * 캠페인할당 정보를 등록한다.(ajax)
     *
     * @param pmParam 캠페인할당 정보
     * @throws Exception
     */
    public int insertCmpgAlct(Map<String, Object> pmParam) throws Exception {
        return insert("CmpgAlctMap.insertCmpgAlct", pmParam);
    }

    /**
     * 캠페인할당 정보를 캠페인ID 기준으로 삭제한다.
     *
     * @param pmParamHash 캠페인할당 정보
     * @throws Exception
     */
    public int deleteCmpgAlctbyCmpgId(HashMap<String, Object> pmParamHash) throws Exception {
        return delete("CmpgAlctMap.deleteCmpgAlctbyCmpgId", pmParamHash);
    }

    /**
     * 캠페인할당 정보를 삭제한다.
     *
     * @param pmParam 캠페인할당 정보
     * @throws Exception
     */
    public int deleteCmpgAlct(Map<String, Object> pmParam) throws Exception {
        return delete("CmpgAlctMap.deleteCmpgAlct", pmParam);
    }

}
