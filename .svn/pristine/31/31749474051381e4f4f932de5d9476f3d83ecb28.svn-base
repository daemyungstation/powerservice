/*
 * (@)# MemberAddrDtlServiceImpl.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.MemberAddrDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 고객 주소정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/26
 * @프로그램ID MemberAddrDtl
 */
@Service
public class MemberAddrDtlServiceImpl extends EgovAbstractServiceImpl implements MemberAddrDtlService {

    @Resource
    public MemberAddrDtlDAO memberAddrDtlDAO;

    /**
     * 고객 주소 정보를 등록한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public String insertMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = memberAddrDtlDAO.insertMemberAddrDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("mem_addr_id");

            // 고객 주소 이력 저장
            memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
        }

        return sKey;
    }

    /**
     * 고객 주소 정보를 수정한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int updateMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        // 변경 고객 주소 정보 존재 건수 조회
        int nExistCount = memberAddrDtlDAO.getMemberAddrDtlExistCount(pmParam);

        int nResult = memberAddrDtlDAO.updateMemberAddrDtl(pmParam);

        // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
        if (nResult > 0 && nExistCount == 0) {
            memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
        }

        return nResult;
    }

    /**
     * 고객 주소 정보를 삭제한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int deleteMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        int nResult = memberAddrDtlDAO.deleteMemberAddrDtl(pmParam);
        if (nResult > 0) {
            // 고객 주소 이력 삭제
            memberAddrDtlDAO.deleteMemberAddrDtlHstr(pmParam);
        }

        return nResult;
    }

    /**
     * 고객 주소 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 정보의 검색 건수
     * @throws Exception
     */
    public int getMemberAddrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) memberAddrDtlDAO.getMemberAddrDtlCount(pmParam);
    }

    /**
     * 고객 주소 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemberAddrDtlList(Map<String, ?> pmParam) throws Exception {
        return memberAddrDtlDAO.getMemberAddrDtlList(pmParam);
    }

    /**
     * 고객 주소 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 상세정보
     * @throws Exception
     */
    public Map<String, Object> getMemberAddrDtlDtpt(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("mem_addr_id", psParam);
        return memberAddrDtlDAO.getMemberAddrDtlDtpt(pmParam);
    }


    /**
     * 고객 주소 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getMemberAddrDtlHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) memberAddrDtlDAO.getMemberAddrDtlHstrCount(pmParam);
    }

    /**
     * 고객 주소 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 주소 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemberAddrDtlHstrList(Map<String, ?> pmParam) throws Exception {
        return memberAddrDtlDAO.getMemberAddrDtlHstrList(pmParam);
    }

}
