/*
 * (@)# ConsScrtDtlServiceImpl.java
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.ConsScrtDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담스크립트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/21
 * @프로그램ID ConsScrtDtl
 */

@Service
public class ConsScrtDtlServiceImpl extends EgovAbstractServiceImpl implements ConsScrtDtlService {

    @Resource
    public ConsScrtDtlDAO consScrtDtlDAO;

    /**
     * 상담스크립트 정보의 검색 수를 반환한다
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 정보의 검색 수
     * @throws Exception
     */
    public int getConsScrtCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) consScrtDtlDAO.getConsScrtCount(pmParam);
    }

    /**
     * 상담스크립트 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsScrtList(Map<String, ?> pmParam) throws Exception {
        return consScrtDtlDAO.getConsScrtList(pmParam);
    }

    /**
     * 상담스크립트 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getConsScrt(Map<String, ?> pmParam) throws Exception {
        return consScrtDtlDAO.getConsScrt(pmParam);
    }

    /**
     * 상담스크립트 정보를 등록한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public String insertConsScrt(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = consScrtDtlDAO.insertConsScrt(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("cons_scrt_id");
        }
        return sKey;
    }

    /**
     * 상담스크립트 정보를 수정한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int updateConsScrt(Map<String, ?> pmParam) throws Exception {
        return consScrtDtlDAO.updateConsScrt(pmParam);
    }

    /**
     * 상담스크립트 정보를 삭제한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int deleteConsScrt(Map<String, ?> pmParam) throws Exception {
        return consScrtDtlDAO.deleteConsScrt(pmParam);
    }

}
