/*
 * (@)# SampeService.java
 *
 * @author 홍길동
 * @version 1.0
 * @date 2016/01/02
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

package powerservice.business.sample.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.common.service.impl.CommonServiceImpl;
import powerservice.business.dlw.service.impl.DlwSampleDAO;
import powerservice.business.sample.service.SampleService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * (테스트)샘플정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 홍길동
 * @version 1.0
 * @date 2016/01/02
 * @프로그램ID Sampe
 */
@Service
public class SampleServiceImpl extends EgovAbstractServiceImpl implements SampleService {
	
	private final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    public SampleDAO sampleDAO;
    
    @Resource
    public DlwSampleDAO dlwSampleDAO;
    

    /**
     * (테스트)샘플정보를 입력한다.
     *
     * @param pmParam (테스트)샘플정보
     * @return
     * @throws Exception
     */
    public String insertSample(Map<String, Object> pmParam) throws Exception {
        String szKey = "";
        int nResult = sampleDAO.insertSample(pmParam);
        if (nResult > 0) {
            szKey = (String) pmParam.get("sample_name");
        }
        return szKey;
    }

    /**
     * (테스트)샘플정보를 수정한다.
     *
     * @param pmParam (테스트)샘플정보
     * @throws Exception
     */
    public int updateSample(Map<String, Object> pmParam) throws Exception {
        int nResult = sampleDAO.updateSample(pmParam);
        return nResult;
    }

    /**
     * (테스트)샘플정보를 삭제한다.
     *
     * @param pmParam (테스트)샘플정보
     * @throws Exception
     */
    public int deleteSample(Map<String, Object> pmParam) throws Exception {
        int nResult = sampleDAO.deleteSample(pmParam);
        return nResult;
    }

    /**
     * (테스트)샘플정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return (테스트)샘플정보의 검색 수
     * @throws Exception
     */
    public int getSampleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) sampleDAO.getSampleCount(pmParam);
    }

    /**
     * (테스트)샘플정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return (테스트)샘플정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSampleList(Map<String, ?> pmParam) throws Exception {
        return sampleDAO.getSampleList(pmParam);
    }
    
    /**
     * 프로시져를 이용한 목록 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송목록
     * @throws Exception
     */
    public List<Map<String, Object>> getListByProc(Map<String, Object> pmParam) throws Exception {
        return sampleDAO.getListByProc(pmParam);
    }
    
    /**
     * 프로시져를 이용한 목록 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송목록
     * @throws Exception
     */
    public List<Map<String, Object>> getListByProc2(Map<String, Object> pmParam) throws Exception {
        return dlwSampleDAO.getListByProc2(pmParam);
    }
    
    /**
     * 샘플데이터 PRODUCT 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProductList(Map<String, Object> pmParam) throws Exception {
        return sampleDAO.getProductList(pmParam);
    }
    
    /**
     * PLSQL 블럭 실행 테스트
     *
     * @param 
     * @return 
     * @throws Exception
     */
    public void insertTestPlSql(Map<String, Object> pmParam) throws Exception {
        sampleDAO.insertTestPlSql(pmParam);
    }
}
