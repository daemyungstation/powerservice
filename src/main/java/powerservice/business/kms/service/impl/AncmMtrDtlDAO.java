/*
 * (@)# AncmMtrDtlDAO.java
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
 * 공지사항 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID AncmMtrDtl
 */
@Repository
public class AncmMtrDtlDAO extends EgovAbstractMapper {

    /**
     *  공지사항 정보를 입력한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    public int insertAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        return insert("AncmMtrDtlMap.insertAncmMtrDtl", pmParam);
    }

    /**
     *  공지사항 정보를 수정한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    public int updateAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        return update("AncmMtrDtlMap.updateAncmMtrDtl", pmParam);
    }

    /**
     * 공지사항 조회수를 업데이트한다.
     *
     * @param 공지사항 ID
     * @throws Exception
     */
    public int updateAncmMtrDtlViewCnt(String psAncmMtrId) throws Exception {
        return update("AncmMtrDtlMap.updateAncmMtrDtlViewCnt", psAncmMtrId);
    }

    /**
     * 공지사항 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getAncmMtrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AncmMtrDtlMap.getAncmMtrDtlCount", pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmMtrDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("AncmMtrDtlMap.getAncmMtrDtlList", pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보
     * @throws Exception
     */
    public Map<String, Object> getAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("AncmMtrDtlMap.getAncmMtrDtlList", pmParam);
    }

    /**
     * 공지사항 정보를 삭제한다.(1건)
     *
     * @param pmParam 공지사항 ID
     * @throws Exception
     */
    public int deleteAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        return delete("AncmMtrDtlMap.deleteAncmMtrDtl", pmParam);
    }

}
