/*
 * (@)# RprtDAO.java
 *
 * @author 김은지
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 통계 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltTyp
 */
@Repository
public class EvltItemBankDAO extends EgovAbstractMapper {

    /**
     * 평가항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보의 검색 수
     * @throws Exception
     */
    public int getEvltItemBankCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltItemBankMap.getEvltItemBankCount", pmParam);
    }

    /**
     * 평가항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltItemBankList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltItemBankMap.getEvltItemBankList", pmParam);
    }

    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int modifyEvltItemBankOrd(Map<String, ?> pmParam) throws Exception {
        return update("EvltItemBankMap.modifyEvltItemBankOrd", pmParam);
    }
    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertEvltItemBank(Map<String, ?> pmParam) throws Exception {
        return insert("EvltItemBankMap.insertEvltItemBank", pmParam);
    }

    /**
     * 평가항목 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보
     * @throws Exception
     */
    public Map<String, Object> getEvltItemBank(Map<String, ?>pmParam) throws Exception {
        return selectOne("EvltItemBankMap.getEvltItemBankList", pmParam);
    }

    /**
     * 평가항목 정보를 수정한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int updateEvltItemBank(Map<String, ?> pmParam) throws Exception {
        return update("EvltItemBankMap.updateEvltItemBank", pmParam);
    }


    /**
     * 평가항목 정보를 삭제한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int deleteEvltItemBank(Map<String, ?> pmParam) throws Exception {
        return delete("EvltItemBankMap.deleteEvltItemBank", pmParam);
    }

    /**
     * 삭제할 평가유형 ID정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypIdList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltItemBankMap.getEvltTypIdList", pmParam);
    }

    /**
     * 삭제된 평가유형의 항목 정보의 정렬순서를 수정한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int updateEvltItemBankOrd(Map<String, ?> pmParam) throws Exception {
        return update("EvltItemBankMap.setEvltItemBankOrd", pmParam);
    }

    /**
     * 평가항목 정보를 복사한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertCopyEvltItemBank(Map<String, ?> pmParam) throws Exception {
        return insert("EvltItemBankMap.insertCopyEvltItemBank", pmParam);
    }
}
