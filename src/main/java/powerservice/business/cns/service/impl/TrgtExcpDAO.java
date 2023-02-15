/*
 * (@)# TrgtExcpDAO.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 연체대상제외 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
 * @프로그램ID PS030700
 *
 */
@Repository
public class TrgtExcpDAO extends EgovAbstractMapper {

    /**
     * 연체대상제외 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return TrgtExcp 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtExcpCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtExcpMap.getTrgtExcpCount", pmParam);
    }

    /**
     * 연체대상제외 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 연체대상제외 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtExcpList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtExcpMap.getTrgtExcpList", pmParam);
    }

    /**
     * 연체대상제외 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return insert 결과
     * @throws Exception
     */
    public int insertTrgtExcp(Map<String, ?> pmParam) throws Exception {
        return insert("TrgtExcpMap.insertTrgtExcp", pmParam);
    }

    /**
     * 연체대상제외 정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return TrgtExcp 리스트
     * @throws Exception
     */
    public int updateTrgtExcp(Map<String, ?> pmParam) throws Exception {
        return update("TrgtExcpMap.updateTrgtExcp", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 정승철
     * 추가내용 : 연체대상제외 정보삭제
     * 대상 테이블 : PS_WILLVI.TB_TRGT_EXCP
     * ================================================================
     * */
    public int deleteTrgtExcp(Map<String, ?> pmParam) throws Exception {
        return update("TrgtExcpMap.deleteTrgtExcp", pmParam);
    }
}
