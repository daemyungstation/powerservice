/*
 * (@)# CalbDtlHstrService.java
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

import powerservice.business.cns.service.CalbDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 콜백 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CalbDtlHstr
 */
@Service
public class CalbDtlServiceImpl extends EgovAbstractServiceImpl implements CalbDtlService {

    @Resource
    public CalbDtlDAO calbDtlDAO;

    @Resource
    private CalbDtlHstrDAO calbDtlHstrDAO;

    public int getCalbDtlCount(Map<String, ?> pmParam) throws Exception {
        return calbDtlDAO.getCalbDtlCount(pmParam);
    }

    /**
     * 콜백 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDtlList(Map<String, ?> pmParam) throws Exception {
        return calbDtlDAO.getCalbDtlList(pmParam);
    }

    /**
     * 콜백 정보(1건)를 검색한다.
     *
     * @param 검색조건
     * @return 콜백 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCalbDtl(Map<String, ?> pmParam) throws Exception {
        return calbDtlDAO.getCalbDtl(pmParam);
    }

    /**
     * 콜백 정보를 수정한다.
     *
     * @param pmParam 콜백 정보
     * @throws Exceptionz
     */
    public int updateCalbDtl(Map<String, ?> pmParam) throws Exception {
        int nResult = calbDtlDAO.updateCalbDtl(pmParam);

        // 콜백 처리인 경우 이력 추가
        if (nResult > 0 && "operation".equals((String) pmParam.get("oper_typ"))) {
            calbDtlHstrDAO.insertCalbDtlHstr(pmParam);
        }

        return nResult;
    }

    /**
     * 상담사별 콜백 미처리 건수를 검색한다.
     *
     * @return 상담사별 콜백 미처리 건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoCalbDtlCount() throws Exception {
        return calbDtlDAO.getTodoCalbDtlCount();
    }

}
