/*
 * (@)# DlwEmplServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
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

package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwEmplService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwEmpl
 */
@Service
public class DlwEmplServiceImpl extends EgovAbstractServiceImpl implements DlwEmplService {

    @Resource
    public DlwEmplDAO dlwEmplDAO;

    /**
     * 사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwEmplCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwEmplDAO.getDlwEmplCount(pmParam);
    }

    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwEmplList(Map<String, Object> pmParam) throws Exception {
    	pmParam = stringReplaceChk(pmParam, "orderBy"); //유효성검사
        return dlwEmplDAO.getDlwEmplList(pmParam);
    }

    /**
     * 사원 상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwEmplDtpt(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("emple_no", psParam);
        return dlwEmplDAO.getDlwEmplDtpt(pmParam);
    }

    /**
     * 사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보의 검색 건수
     * @throws Exception
     */
    public int getEmplCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwEmplDAO.getEmplCount(pmParam);
    }

    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEmplList(Map<String, ?> pmParam) throws Exception {
        return dlwEmplDAO.getEmplList(pmParam);
    }

    	
    /**
     * 문자열 유효성 검사
     *
     * @param pmParam 
     * @return 
     * @throws Exception
     */
    public Map<String, Object> stringReplaceChk(Map<String, Object> pmParam,String key) throws Exception {
    	
    	String strKey = key;
    	String strChk = (String)pmParam.get(strKey);
    	if(!"".equals(strChk)) {
    		strChk = strChk.toLowerCase();
    		strChk = strChk.replace("'", "");
    		strChk = strChk.replace(";", "");
    		strChk = strChk.replace("--", "");
    		strChk = strChk.replace("|", "");
    		strChk = strChk.replace("+", "");
    		strChk = strChk.replace("'", "");
    		strChk = strChk.replace("\\", "");
    		strChk = strChk.replace("/", "");
    		strChk = strChk.replace("select", "");
    		strChk = strChk.replace("update", "");
    		strChk = strChk.replace("delete", "");
    		strChk = strChk.replace("insert", "");
    		strChk = strChk.replace("where", "");
    		strChk = strChk.replace("from", "");
    	}
    	
    	pmParam.put(strKey, strChk);
    	return pmParam;
    }
}
