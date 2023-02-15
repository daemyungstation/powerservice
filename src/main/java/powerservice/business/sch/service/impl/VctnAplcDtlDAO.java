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
 * @프로그램ID VctnAplcDtl
 */
@Repository
public class VctnAplcDtlDAO extends EgovAbstractMapper {

    /**
     * 휴가요청 정보를 등록한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public int insertVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return insert("VctnAplcDtlMap.insertVctnAplcDtl", pmParam);
    }

    /**
     * 휴가요청 정보를 수정한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public int updateVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return update("VctnAplcDtlMap.updateVctnAplcDtl", pmParam);
    }

    /**
     * 휴가요청 정보를 삭제한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public int deleteVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return delete("VctnAplcDtlMap.deleteVctnAplcDtl", pmParam);
    }

    /**
     * 휴가요청 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가요청 정보의 검색 건수
     * @throws Exception
     */
    public int getVctnAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("VctnAplcDtlMap.getVctnAplcDtlCount", pmParam);
    }

    /**
     * 휴가요청 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가요청 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnAplcDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("VctnAplcDtlMap.getVctnAplcDtlList", pmParam);
    }

    /**
     * 휴가요청 정보를 검색한다.
     *
     * @param pmParam 휴가신청ID
     * @return 휴가요청 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("VctnAplcDtlMap.getVctnAplcDtlList", pmParam);
    }

    /**
     * 요청한 휴가일수 정보의 검색수를 반환한다.
     *
     * @param pmParam
     * @return 요청한 휴가일수 정보의 검색 건수
     * @throws Exception
     */
    public int getVctnDtDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("VctnAplcDtlMap.getVctnDtDtlCount", pmParam);
    }

    /**
     * 요청한 휴가일수 정보를 검색한다.
     *
     * @param pmParam
     * @return 요청한 휴가일수 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnDtDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("VctnAplcDtlMap.getVctnDtDtlList", pmParam);
    }

    /**
     * 휴가일자 정보를 등록한다.
     *
     * @param pmParam 휴가일수 정보
     * @throws Exception
     */
    public int insertVctnDtDtl(Map<String, ?> pmParam) throws Exception {
        return insert("VctnAplcDtlMap.insertVctnDtDtl", pmParam);
    }

    /**
     * 휴가일자 정보를 수정한다.
     *
     * @param pmParam 휴가일수 정보
     * @throws Exception
     */
    public int updateVctnDtDtl(Map<String, ?> pmParam) throws Exception {
        return update("VctnAplcDtlMap.updateVctnDtDtl", pmParam);
    }

    /**
     * 휴가일자 정보를 삭제한다.
     *
     * @param pmParam 휴가일수 정보
     * @throws Exception
     */
    public int deleteVctnDtDtl(Map<String, ?> pmParam) throws Exception {
        return delete("VctnAplcDtlMap.deleteVctnDtDtl", pmParam);
    }

    /**
     * 특정일자에 승인된 휴가요청 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 특정일자에 승인된 휴가요청 검색 건수
     * @throws Exception
     */
    public int getApproveCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("VctnAplcDtlMap.getApproveCount", pmParam);
    }

    /**
     * 사용자의 휴가정보를 검색한다.
     *
     * @param id 사용자 아이디
     * @return 사용자의 휴가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnInfoView(Map<String, ?> pmParam) throws Exception {
        return selectList("VctnAplcDtlMap.getVctnInfoView", pmParam);
    }
}
