/*
 * (@)# MemberAddrDtlDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/26
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
 * 고객 주소 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/26
 * @프로그램ID MemberAddrDtl
 */
@Repository
public class MemberAddrDtlDAO extends EgovAbstractMapper {

    /**
     * 고객 주소 정보를 등록한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int insertMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        return insert("MemberAddrDtlMap.insertMemberAddrDtl", pmParam);
    }

    /**
     * 고객 주소 정보를 수정한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int updateMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        return update("MemberAddrDtlMap.updateMemberAddrDtl", pmParam);
    }

    /**
     * 고객 주소 정보를 삭제한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int deleteMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        return delete("MemberAddrDtlMap.deleteMemberAddrDtl", pmParam);
    }

    /**
     * 고객 주소 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 정보의 검색 건수
     * @throws Exception
     */
    public int getMemberAddrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("MemberAddrDtlMap.getMemberAddrDtlCount", pmParam);
    }

    /**
     * 고객 주소 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemberAddrDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("MemberAddrDtlMap.getMemberAddrDtlList", pmParam);
    }

    /**
     * 고객 주소 상세정보를 조회한다.(MEM_PROD_ACCNT)
     *
     * @param pmParam 고객정보
     * @return 고객 주소 상세정보
     * @throws Exception
     */
    public Map<String, Object> getMemberAddrDtlDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("MemberAddrDtlMap.getMemberAddrDtlDtpt", pmParam);
    }

    /**
     * 고객 주소 정보를 등록한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int mergeMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        return insert("MemberAddrDtlMap.mergeMemberAddrDtl", pmParam);
    }

    /**
     * 변경 고객 주소 정보의 존재 건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 변경 고객 주소 정보의 존재 건수
     * @throws Exception
     */
    public int getMemberAddrDtlExistCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("MemberAddrDtlMap.getMemberAddrDtlExistCount", pmParam);
    }


    /**
     * 고객 주소 이력 정보를 등록한다.
     *
     * @param pmParam 고객 주소 이력 정보
     * @throws Exception
     */
    public int insertMemberAddrDtlHstr(Map<String, ?> pmParam) throws Exception {
        return insert("MemberAddrDtlMap.insertMemberAddrDtlHstr", pmParam);
    }

    /**
     * 고객 주소 이력 정보를 삭제한다.
     *
     * @param pmParam 고객 주소 이력 정보
     * @throws Exception
     */
    public int deleteMemberAddrDtlHstr(Map<String, ?> pmParam) throws Exception {
        return delete("MemberAddrDtlMap.deleteMemberAddrDtlHstr", pmParam);
    }

    /**
     * 고객 주소 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getMemberAddrDtlHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("MemberAddrDtlMap.getMemberAddrDtlHstrCount", pmParam);
    }

    /**
     * 고객 주소 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemberAddrDtlHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("MemberAddrDtlMap.getMemberAddrDtlHstrList", pmParam);
    }

}
