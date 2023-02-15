/*
 * (@)# VctnAplcDtlDAO.java
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

package powerservice.business.sch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnScdlDtl
 */
@Repository
public class VctnScdlDtlDAO extends EgovAbstractMapper {

    /**
     * 휴가일정 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가일정 수
     * @throws Exception
     */
    public String getVctnScdlDtlNmprCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("VctnScdlDtlMap.getVctnScdlDtlNmprCnt", pmParam);

    }

    /**
     * 휴가일정 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return휴가일정 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnScdlDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("VctnScdlDtlMap.getVctnScdlDtlList", pmParam);
    }


    /**
     * 휴가일정 정보를 삭제한다.
     *
     * @pmParam pmParam 근무 스케줄 정보
     * @throws Exception
     */
    public int deleteVctnScdlDtl(Map<String, Object> pmParam) throws Exception {
        return delete("VctnScdlDtlMap.deleteVctnScdlDtl", pmParam);
    }

    /**
     * 근무 스케줄 정보를 삽입한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int insertVctnScdlDtl(Map<String, ?> pmParam) throws Exception {
        return insert("VctnScdlDtlMap.insertVctnScdlDtl", pmParam);
    }

}
