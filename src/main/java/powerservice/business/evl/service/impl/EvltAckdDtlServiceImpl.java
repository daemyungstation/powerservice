/*
 * (@)# EvltItemBankServiceImpl.java
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

package powerservice.business.evl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltAckdDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltItemBank
 */
@Service
public class EvltAckdDtlServiceImpl extends EgovAbstractServiceImpl implements EvltAckdDtlService {

    @Resource
    public EvltAckdDtlDAO evltAckdDtlDAO;

    /**
     * 평가자승인 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가자승인 정보의 검색 수
     * @throws Exception
     */
    public int getEvltAckdDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltAckdDtlDAO.getEvltAckdDtlCount(pmParam);
    }

    /**
     * 평가자승인 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가자승인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltAckdDtlList(Map<String, ?> pmParam) throws Exception {
        return evltAckdDtlDAO.getEvltAckdDtlList(pmParam);
    }

    /**
     * 평가자승인 정보를 등록한다.
     *
     * @param param 평가자승인 정보
     * @throws Exception
     */
    public String insertEvltAckdDtl(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nApprCnt = (Integer) evltAckdDtlDAO.getApprCnt(pmParam);

        if (nApprCnt == 0) {
            int nResult = evltAckdDtlDAO.insertEvltAckdDtl(pmParam);

            if (nResult > 0) {
                sKey = (String) pmParam.get("evlt_ackd_id");
            }
        }
        return sKey;
    }

    /**
     * 평가자승인 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가자승인 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvltAckdDtl(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("evlt_ackd_id", psId);

        return evltAckdDtlDAO.getEvltAckdDtl(mParam);
    }

    /**
     * 평가자승인 정보를 수정한다.
     *
     * @param param 평가자승인 정보
     * @throws Exception
     */
    public int updateEvltAckdDtl(Map<String, Object> pmParam) throws Exception {
        return evltAckdDtlDAO.updateEvltAckdDtl(pmParam);
    }

    /**
     * 이의제기 요청 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 이이제기요청의 검색 수
     * @throws Exception
     */
    public int getApprRequsetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltAckdDtlDAO.getApprRequsetCount(pmParam);
    }

}
