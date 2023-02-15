/*
 * (@)# FaqDtlDAO.java
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * FAQ 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/14
 * @프로그램ID FaqDtl
 */

@Repository
public class FaqDtlDAO extends EgovAbstractMapper {

    /**
     * FAQ 정보를 등록한다.
     *
     * @param mpParam FAQ 정보
     * @throws Exception
     */
    public int insertFaq(Map<String, Object> mpParam) throws Exception {
        return insert("FaqDtlMap.insertFaq", mpParam);
    }

    /**
     * FAQ 요청 정보를 등록한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     *
     */
    public int insertFaqFromFaqDmnd(Map<String, Object> pmParam) throws Exception {
        return insert("FaqDtlMap.insertFaqFromFaqDmnd", pmParam);
    }

    /**
     * FAQ 정보를 수정한다.
     *
     * @param mpParam FAQ 정보
     * @throws Exception
     */
    public int updateFaq(Map<String, Object> mpParam) throws Exception {
        return update("FaqDtlMap.updateFaq", mpParam);
    }

    /**
     * 첨부파일에 FAQ요청 아이디를 업데이트 한다.
     *
     * @param mpParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> mpParam) throws Exception {
        return update("FileMap.updateFile", mpParam);
    }

    /**
     * FAQ 조회수를 업데이트한다.
     *
     * @param FAQ ID
     * @throws Exception
     */
    public int updateInqCnt(String faq_id) throws Exception {
        return update("FaqDtlMap.updateInqCnt", faq_id);
    }

    /**
     * FAQ 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getFaqCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("FaqDtlMap.getFaqCount", mpParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqList(Map<String, ?> mpParam) throws Exception {
        return selectList("FaqDtlMap.getFaqList", mpParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param FAQ ID
     * @return FAQ 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getFaq(Map<String, ?> mpParam) throws Exception {
        return selectOne("FaqDtlMap.getFaqListView", mpParam);
    }

    /**
     * FAQ 정보를 삭제한다.
     *
     * @param mpParam FAQ ID
     * @throws Exception
     */
    public int deleteFaq(Map<String, ?> mpParam) throws Exception {
        return delete("FaqDtlMap.deleteFaq", mpParam);
    }

}
