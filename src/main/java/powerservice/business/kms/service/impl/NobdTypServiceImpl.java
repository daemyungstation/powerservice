/*
 * (@)# NobdTypService.java
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

import powerservice.business.kms.service.NobdTypService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 게시판 유형 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/17
 * @프로그램ID NobdTyp
 */

@Service
public class NobdTypServiceImpl extends EgovAbstractServiceImpl implements NobdTypService {

    @Resource
    public NobdTypDAO nobdTypDAO;

    /**
     * 게시판 유형 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 유형 정보의 검색 수
     * @throws Exception
     */
    public int getNobdTypCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) nobdTypDAO.getNobdTypCount(pmParam);
    }

    /**
     * 게시판 유형 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdTypList(Map<String, ?> pmParam) throws Exception {
        return nobdTypDAO.getNobdTypList(pmParam);
    }

    /**
     * 게시판 유형 정보를 검색한다.
     *
     * @param 게시판 유형 ID
     * @return 게시판 유형 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getNobdTyp(Map<String, ?> pmParam) throws Exception {
        return nobdTypDAO.getNobdTyp(pmParam);
    }

    /**
     * 게시판 유형 정보를 등록한다.(ajax)
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public String insertNobdTyp(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = nobdTypDAO.insertNobdTyp(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("nobd_typ_id");
        }
        return sKey;
    }

    /**
     * 게시판 유형 정보를 수정한다.(ajax)
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public int updateNobdTyp(Map<String, ?> pmParam) throws Exception {
        return nobdTypDAO.updateNobdTyp(pmParam);
    }

    /**
     * 게시판 유형(에디트,파일,댓글) 권한 유무 설정하기 위해 검색한다.
     *
     * @return 게시판 유형 유무 권한 리스트
     */
    public List<Map<String, Object>> getChkFg()throws Exception {
        return nobdTypDAO.getChkFg();
    }

    /**
     * 게시판 유형 정보를 삭제한다.
     *
     * @param pmParam 게시판 유형 정보
     * @throws Exception
     */
    public int deleteNobdTyp(Map<String, Object> pmParam) throws Exception {
        return nobdTypDAO.deleteNobdTyp(pmParam);
    }

}
