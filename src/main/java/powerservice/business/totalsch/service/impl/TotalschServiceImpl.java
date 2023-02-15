/*
 * (@)# TotalschServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/01
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

package powerservice.business.totalsch.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.totalsch.service.TotalschService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 통합검색 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/01
 * @프로그램ID Totalsch
 */

@Service
public class TotalschServiceImpl extends EgovAbstractServiceImpl implements TotalschService {

    @Resource
    public TotalschDAO totalSchDAO;

    /**
     * FAQ 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getFaqCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) totalSchDAO.getFaqCount(pmParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqList(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getFaqList(pmParam);
    }

    /**
     * FAQ 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getFaqView(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getFaqView(pmParam);
    }

    /**
     * 공지사항 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getAncmMtrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) totalSchDAO.getAncmMtrDtlCount(pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmMtrDtlList(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getAncmMtrDtlList(pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getAncmMtrDtlView(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getAncmMtrDtlView(pmParam);
    }

    /**
     * 게시판 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getNobdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) totalSchDAO.getNobdCount(pmParam);
    }

    /**
     * 게시판 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdList(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getNobdList(pmParam);
    }

    /**
     * 게시판 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getNobdView(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getNobdView(pmParam);
    }

    /**
     * 상담스크립트 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getConsScrtCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) totalSchDAO.getConsScrtCount(pmParam);
    }

    /**
     * 상담스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsScrtList(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getConsScrtList(pmParam);
    }

    /**
     * 상담스크립트 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getConsScrtView(Map<String, ?> pmParam) throws Exception {
        return totalSchDAO.getConsScrtView(pmParam);
    }
}
