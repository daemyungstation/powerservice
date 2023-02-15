/*
 * (@)# AncmTrgtTeamDAO.java
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 공지사항-상담그룹 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID AncmTrgtTeam
 */
@Repository
public class AncmTrgtTeamDAO extends EgovAbstractMapper {

    /**
     * 공지사항-상담그룹 정보를 검색한다.
     *
     * @param String 공지사항 ID
     * @return 공지사항-상담그룹 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmTrgtTeamList(String sAncmMtrId) throws Exception {
        return selectList("AncmTrgtTeamMap.getAncmTrgtTeamList", sAncmMtrId);
    }

    /**
     *  공지사항-상담그룹 정보를 입력한다.
     *
     * @param pmParam 공지사항-상담그룹 정보
     * @throws Exception
     */
    public int insertAncmTrgtTeam(Map<String, ?> pmParam) throws Exception {
        return insert("AncmTrgtTeamMap.insertAncmTrgtTeam", pmParam);
    }

    /**
     *  공지사항-상담그룹 정보를 삭제한다.
     *
     * @param pmParam 공지사항-상담그룹 정보
     * @throws Exception
     */
    public int deleteAncmTrgtTeam(Map<String, ?> pmParam) throws Exception {
        return delete("AncmTrgtTeamMap.deleteAncmTrgtTeam", pmParam);
    }

}
