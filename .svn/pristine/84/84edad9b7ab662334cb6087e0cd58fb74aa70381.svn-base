/*
 * (@)# NobdAnsrService.java
 *
 * @author 차건호
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

import powerservice.business.kms.service.NobdAnsrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 게시판 댓글 관리를 한다
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/20
 * @프로그램ID NobdAnsr
 */

@Service
public class NobdAnsrServiceImpl extends EgovAbstractServiceImpl implements NobdAnsrService {

    @Resource
    public NobdAnsrDAO nobdAnsrDAO;

    /**
     * 게시판 댓글 정보를 등록한다.
     *
     * @param pmParam 게시판 댓글 정보
     * @throws Exception
     */
    public String insertNobdAnsr(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int sResult = nobdAnsrDAO.insertNobdAnsr(pmParam);

        if (sResult > 0) {
            sKey = (String) pmParam.get("nobd_ansr_id");
        }
        return sKey;
    }

    /**
     * 게시판 댓글 정보를 수정한다.
     *
     * @param pmParam 게시판 댓글 정보
     * @throws Exception
     */
    public int updateNobdAnsr(Map<String, ?> pmParam) throws Exception {
        return nobdAnsrDAO.updateNobdAnsr(pmParam);
    }

    /**
     * 게시판 댓글 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 댓글 정보의 검색 건수
     * @throws Exception
     */
    public int getNobdAnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) nobdAnsrDAO.getNobdAnsrCount(pmParam);
    }

    /**
     * 게시판 댓글 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 댓글 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdAnsrList(Map<String, ?> pmParam) throws Exception {
        return nobdAnsrDAO.getNobdAnsrList(pmParam);
    }

    /**
     * 게시판 댓글 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 댓글 목록
     * @throws Exception
     */
    public Map<String, Object> getNobdAnsr(Map<String, ?> pmParam) throws Exception {
        return nobdAnsrDAO.getNobdAnsr(pmParam);
    }

    /**
     * 게시판 댓글 정보를 삭제한다.
     *
     * @param 게시판 댓글 ID
     * @throws Exception
     */
    public int deleteNobdAnsr(Map<String, Object> pmParam) throws Exception {
        return nobdAnsrDAO.deleteNobdAnsr(pmParam);
    }

}
