/*
 * (@)# ExtcQuryItemDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대상고객추출조건 상세항목을 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID ExtcQuryItem
 */

@Repository
public class ExtcQuryItemDAO extends EgovAbstractMapper {

    /**
     * 대상고객추출조건 상세항목을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 상세항목
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcQuryItemList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcQuryItemMap.getExtcQuryItemList", pmParam);
    }

    /**
     * 대상고객추출조건 상세항목(상담유형)을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 상세항목
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcQuryItemConsTypList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcQuryItemMap.getExtcQuryItemConsTypList", pmParam);
    }

    /**
     * 대상고객추출조건 상세항목(제품유형)을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 상세항목
     * @throws Exception
     */
    public List<Map<String, Object>> getSegmentcondMtrlclsList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcQuryItemMap.getSegmentcondMtrlclsList", pmParam);
    }

    /**
     * 대상고객추출조건 상세항목(상품)을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 상품
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcQuryItemProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcQuryItemMap.getExtcQuryItemProdList", pmParam);
    }

    /**
     * 대상고객추출조건 상세항목을 등록한다.
     *
     * @param pmParam 대상고객추출조건 상세항목 정보
     * @throws Exception
     */
    public int insertExtcQuryItem(Map<String, Object> pmParam) throws Exception {
        return insert("ExtcQuryItemMap.insertExtcQuryItem", pmParam);
    }

    /**
     * 대상고객추출조건 상세항목을 삭제한다.
     *
     * @param pmParam 평가계획 정보
     * @throws Exception
     */
    public int deleteExtcQuryItem(Map<String, ?> pmParam) throws Exception {
        return delete("ExtcQuryItemMap.deleteExtcQuryItem", pmParam);
    }
}
