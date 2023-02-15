/*
 * (@)# TotalschDAO.java
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 통합검색 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/01
 * @프로그램ID Totalsch
 */

@Repository
public class TotalschDAO extends EgovAbstractMapper {

    /**
     * FAQ 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getFaqCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("TotalschMap.getFaqCount", mpParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqList(Map<String, ?> mpParam) throws Exception {
        return selectList("TotalschMap.getFaqList", mpParam);
    }

    /**
     * FAQ 정보를 검색한다.(1건)
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getFaqView(Map<String, ?> mpParam) throws Exception {
        update("TotalschMap.updateFaqViewCnt", mpParam.get("faq_id"));

        return selectOne("TotalschMap.getFaqView", mpParam);
    }

    /**
     * 공지사항 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getAncmMtrDtlCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("TotalschMap.getAncmMtrDtlCount", mpParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmMtrDtlList(Map<String, ?> mpParam) throws Exception {
        return selectList("TotalschMap.getAncmMtrDtlList", mpParam);
    }

    /**
     * 공지사항 정보를 검색한다.(1건)
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getAncmMtrDtlView(Map<String, ?> mpParam) throws Exception {
        update("TotalschMap.updateAncmMtrDtlViewCnt", mpParam.get("ancm_mtr_id"));

        return selectOne("TotalschMap.getAncmMtrDtlView", mpParam);
    }

    /**
     * 게시판 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getNobdCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("TotalschMap.getNobdCount", mpParam);
    }

    /**
     * 게시판 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdList(Map<String, ?> mpParam) throws Exception {
        return selectList("TotalschMap.getNobdList", mpParam);
    }

    /**
     * 게시판 정보를 검색한다.(1건)
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getNobdView(Map<String, ?> mpParam) throws Exception {
        update("TotalschMap.updateNobdViewCnt", mpParam.get("nobd_id"));

        return selectOne("TotalschMap.getNobdView", mpParam);
    }

    /**
     * 상담스크립트 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getConsScrtCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("TotalschMap.getConsScrtCount", mpParam);
    }

    /**
     * 상담스크립트 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsScrtList(Map<String, ?> mpParam) throws Exception {
        return selectList("TotalschMap.getConsScrtList", mpParam);
    }

    /**
     * 상담스크립트 정보를 검색한다.(1건)
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public Map<String, Object> getConsScrtView(Map<String, ?> mpParam) throws Exception {
        return selectOne("TotalschMap.getConsScrtView", mpParam);
    }



}
