/*
 * (@)# DlwProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 입금마감현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author BIJ
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwUplusDAO extends EgovAbstractMapper {

    /**
     * 제품의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getUplusMainCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwUplusMap.getUplusMainCount", pmParam);
    }
    
    /**
     * 제품 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getUplusMainList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwUplusMap.getUplusMainList", pmParam);
    }
    
    /**
     * 제품 검색
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public List<Map<String, Object>> getUplusPopList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwUplusMap.getUplusPopList", pmParam);
    }
    
    public int existsUplusListData(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwUplusMap.existsUplusListData", pmParam);
    }

    public int insertUplusList(Map<String, ?> pmParam) throws Exception {
        return insert("DlwUplusMap.insertUplusList", pmParam);
    }
}
