/*
 * (@)# AncmTrgtAthrService.java
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

import powerservice.business.kms.service.AncmTrgtAthrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공지사항-권한 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID AncmTrgtAthr
 */
@Service
public class AncmTrgtAthrServiceImpl extends EgovAbstractServiceImpl implements AncmTrgtAthrService {

    @Resource
    public AncmTrgtAthrDAO ancmTrgtAthrDAO;

    /**
     * 공지사항-권한 정보를 검색한다.
     *
     * @param String 공지사항 ID
     * @return 공지사항-권한 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmTrgtAthrList(String sAncmMtrId) throws Exception {
        return ancmTrgtAthrDAO.getAncmTrgtAthrList(sAncmMtrId);
    }

    /**
     *  공지사항-권한 정보를 입력한다.
     *
     * @param pmParam 공지사항-권한 정보
     * @throws Exception
     */
    public int insertAncmTrgtAthr(Map<String, ?> pmParam) throws Exception {
        return ancmTrgtAthrDAO.insertAncmTrgtAthr(pmParam);
    }

    /**
     * 공지사항-권한 정보를 삭제한다.(1건)
     *
     * @param pmParam 공지사항-권한 ID
     * @throws Exception
     */
    public int deleteAncmTrgtAthr(Map<String, ?> pmParam) throws Exception {
        return ancmTrgtAthrDAO.deleteAncmTrgtAthr(pmParam);
    }

}
