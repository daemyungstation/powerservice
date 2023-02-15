/*
 * (@)# CustUnusMemoServiceImpl.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.CustBasiService;
import powerservice.business.cns.service.CustUnusMemoService;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CustUnusMemo
 */
@Service
public class CustUnusMemoServiceImpl extends EgovAbstractServiceImpl implements CustUnusMemoService {

    @Resource
    public CustUnusMemoDAO custUnusMemoDAO;

    /**
     * 고객 특이메모 정보를 등록한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public String insertCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = custUnusMemoDAO.insertCustUnusMemo(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cust_unus_memo_id");
        }

        return sKey;
    }

    /**
     * 고객 특이메모 정보를 수정한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public int updateCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return custUnusMemoDAO.updateCustUnusMemo(pmParam);
    }

    /**
     * 고객 특이메모 정보를 삭제한다.
     *
     * @param pmParam 고객 특이메모 정보
     * @throws Exception
     */
    public int deleteCustUnusMemo(Map<String, ?> pmParam) throws Exception {
        return custUnusMemoDAO.deleteCustUnusMemo(pmParam);
    }

    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) custUnusMemoDAO.getCustUnusMemoCount(pmParam);
    }

    /**
     * 고객 특이메모 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCustUnusMemoList(Map<String, ?> pmParam) throws Exception {
        return custUnusMemoDAO.getCustUnusMemoList(pmParam);
    }

    /**
     * 고객 특이메모 정보를 검색한다.
     *
     * @param psId 고객 특이메모 ID
     * @return 고객 특이메모 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustUnusMemo(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("cust_unus_memo_id", psId);

        return custUnusMemoDAO.getCustUnusMemo(mParam);
    }

    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) custUnusMemoDAO.getCustUnusMemoCnt(pmParam);
    }

    /**
     * 특이고객 엑셀업로드
     *
     */
    public int insertSpecialAccntExcelList(DataSetMap pmInDsList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (int i = 0; i <  pmInDsList.size(); i++) {
            Map<String, Object> mInDs = pmInDsList.get(i);
            mInDs.put("cntr_cd", pmParam.get("cntr_cd"));
            mInDs.put("rgsr_id", pmParam.get("rgsr_id"));
            mInDs.put("amnd_id", pmParam.get("amnd_id"));
            nResult += custUnusMemoDAO.insertSpecialAccntExcel(mInDs);
        }

        return nResult;
    }

}
