/*
 * (@)# RecrncResrDtlService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.RecrncResrDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 예약콜 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID RecrncResrDtl
 */
@Service
public class RecrncResrDtlServiceImpl extends EgovAbstractServiceImpl implements RecrncResrDtlService {

    @Resource
    public RecrncResrDtlDAO recrncResrDtlDAO;

    /**
     * 예약콜 정보를 등록한다.
     *
     * @param pmParam 예약콜 정보
     * @throws Exception
     */
    public String insertRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = recrncResrDtlDAO.insertRecrncResrDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("consno");
        }
        return sKey;
    }

    /**
     * 예약콜 정보를 수정한다.
     *
     * @param pmParam 예약콜 정보
     * @throws Exception
     */
    public int updateRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.updateRecrncResrDtl(pmParam);
    }

    /**
     * 예약콜 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 정보의 검색 건수
     * @throws Exception
     */
    public int getRecrncResrDtlCount(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.getRecrncResrDtlCount(pmParam);
    }

    /**
     * 예약콜 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getRecrncResrDtlList(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.getRecrncResrDtlList(pmParam);
    }

    /**
     * 예약콜 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 예약콜 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.getRecrncResrDtl(pmParam);
    }


    /**
     * 예약콜 TO-DO 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoRecrncResrDtlList(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.getTodoRecrncResrDtlList(pmParam);
    }

    /**
     * 예약콜 TO-DO 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 예약콜 TO-DO 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTodoRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return recrncResrDtlDAO.getTodoRecrncResrDtl(pmParam);
    }

}
