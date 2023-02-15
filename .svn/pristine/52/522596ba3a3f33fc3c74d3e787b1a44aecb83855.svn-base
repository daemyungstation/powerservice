/*
 * (@)# CompChprDtlServiceImpl.java
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/08/05
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

package powerservice.business.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.biz.service.CompChprDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 담당자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/08/05
 * @프로그램ID CompChprDtl
 */
@Service
public class CompChprDtlServiceImpl extends EgovAbstractServiceImpl implements CompChprDtlService {

    @Resource
    public CompChprDtlDAO compChprDtlDAO;

    /**
     * 고객사의 담당자 정보를 등록한다.
     *
     * @param pmParam 담당자 정보
     * @throws Exception
     */
    public String insertCscoChprDtl(Map<String, ?> pmParam) throws Exception {
    	String sKey = "";

        int nResult = compChprDtlDAO.insertCscoChprDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("biz_id");
        }
        return sKey;
    }

    /**
     * 협력사의 담당자 정보를 등록한다.
     *
     * @param pmParam 담당자 정보
     * @throws Exception
     */
    public String insertCprtCompChprDtl(Map<String, ?> pmParam) throws Exception {
    	String sKey = "";

        int nResult = compChprDtlDAO.insertCprtCompChprDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("biz_id");
        }
        return sKey;
    }

    /**
     * 고객사/협력사의 담당자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 정보의 검색 건수
     * @throws Exception
     */
    public int getCompChprCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) compChprDtlDAO.getCompChprCount(pmParam);
    }

    /**
     * 고객사/협력사의 담당자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 정보 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getCompChprList(Map<String, ?> pmParam) throws Exception {
        return compChprDtlDAO.getCompChprList(pmParam);
    }

    /**
     * 고객사/협력사의 담당자 정보를 검색한다.
     *
     * @param psId 고객ID
     * @return 담당자 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCompChpr(Map<String, ?> pmParam) throws Exception {
        return compChprDtlDAO.getCompChpr(pmParam);
    }
    
    /**
     *  고객사 담당자정보을 삭제한다.
     *
     * @param pmParam  고객사 담당자정보
     * @throws Exception
     */
    public int deleteCscoChprDtl(Map<String, Object> pmParam) throws Exception {
        return compChprDtlDAO.deleteCscoChprDtl(pmParam);
    }
    
	/**
	 *  협력사 담당자정보을 삭제한다.
	 *
	 * @param pmParam  협력사 담당자정보
	 * @throws Exception
	 */
	public int deleteCprtCompChprDtl(Map<String, Object> pmParam) throws Exception {
	    return compChprDtlDAO.deleteCprtCompChprDtl(pmParam);
	}
}
