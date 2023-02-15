/*
 * (@)# DlwOnlnCustDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.onln.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 온라인 회원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
 * @프로그램ID DlwOnlnCust
 */
@Repository
public class SmartLifePSDAO extends EgovAbstractMapper {
	
    public int insertSmartLifeList(Map<String, ?> pmParam) throws Exception {
        return insert("SmartLifePSMap.insertSmartLifeList", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SmartLifePSMap.getTrgtAlctCustCount", pmParam);
    }
	
	/**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtAlctCustList(Map<String, ?> pmParam) throws Exception {
        return selectList("SmartLifePSMap.getTrgtAlctCustList", pmParam);
    }
    
    /**
     * 캠페인 대상고객의 상담사를 변경
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateChangeCnsr(Map<String, ?> pmParam) throws Exception {
        return update("SmartLifePSMap.updateChangeCnsr", pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 할당한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustAssign(Map<String, Object> pmParam) throws Exception {
        return insert("SmartLifePSMap.insertTgtCustAlct", pmParam);
    }
    
    public int getChkAlct(Map<String, Object> mParam) throws Exception {
        return (Integer) selectOne("SmartLifePSMap.getChkAlct", mParam);
    }
	
	/**
     * 캠페인할당 정보를 등록한다.(ajax)
     *
     * @param pmParam 캠페인할당 정보
     * @throws Exception
     */
    public int insertCmpgAlct(Map<String, Object> pmParam) throws Exception {
        return insert("SmartLifePSMap.insertCmpgAlct", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTrgtCustAlctCount", pmParam);
    }
}
