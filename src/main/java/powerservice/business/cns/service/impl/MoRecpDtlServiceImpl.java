/*
 * (@)# MoRecpDtlServiceImpl.java
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

import powerservice.business.cns.service.MoRecpDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID MoRecpDtl
 */
@Service
public class MoRecpDtlServiceImpl extends EgovAbstractServiceImpl implements MoRecpDtlService {

    @Resource
    public MoRecpDtlDAO moRecpDtlDAO;

    /*
     * MO수신 정보를 삭제한다.
     *
     * @param pmParam MO수신 정보
     * @throws Exception
     */
    public int deleteMoRecpDtl(Map<String, ?> pmParam) throws Exception {
        return moRecpDtlDAO.deleteMoRecpDtl(pmParam);
    }

    /*
     * MO수신 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return MO수신 정보의 검색 건수
     * @throws Exception
     */
    public int getMoRecpDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) moRecpDtlDAO.getMoRecpDtlCount(pmParam);
    }

    /*
     * MO수신 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMoRecpDtlList(Map<String, ?> pmParam) throws Exception {
        return moRecpDtlDAO.getMoRecpDtlList(pmParam);
    }

    /*
     * MO수신 정보(팝업, 1건)를 검색한다.
     *
     * @param pmParam MO수신ID
     * @return MO수신 정보(팝업, 1건)
     * @throws Exception
     */
    public Map<String, Object> getMoRecpDtl(Map<String, ?> pmParam) throws Exception {
        return moRecpDtlDAO.getMoRecpDtl(pmParam);
    }



    /*
     * MO 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return MO 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getMoConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) moRecpDtlDAO.getMoConsCount(pmParam);
    }

    /*
     * MO 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return MO 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMoConsList(Map<String, ?> pmParam) throws Exception {
        return moRecpDtlDAO.getMoConsList(pmParam);
    }

}
