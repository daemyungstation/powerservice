/*
 * (@)# DlwOcbProdService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwOcbProdService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 OCB 상품정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
 * @프로그램ID DlwOcbProd
 */
@Service
public class DlwOcbProdServiceImpl extends EgovAbstractServiceImpl implements DlwOcbProdService {

    @Resource
    public DlwOcbProdDAO DlwOcbProdDAO;

    /**
     * 대명라이프웨이 OCB 적립 상품 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbProdCount(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbProdCount(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbProdList(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbProdList(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int insertOcbProd(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.insertOcbProd(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int updateOcbProd(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.updateOcbProd(pmParam);
    }

    /**
     * 대명라이프웨이 OCB이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbProdSaveCount(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbProdSaveCount(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbTransHistCount(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbTransHistCount(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 이력 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTransHistList(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbTransHistList(pmParam);
    }

    /**
     * 대명라이프웨이  OCB 상품 적립 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTransList(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.getOcbTransList(pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품 취소 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public List<Map<String, Object>> getOcbDelList(Map<String, ?> pmParam) throws Exception {
    	List<Map<String, Object>> getExtcTrgt = new ArrayList<Map<String,Object>>();

    	System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb> " + String.valueOf(pmParam.get("settype")));

    	switch(String.valueOf(pmParam.get("settype"))){
    	case "regdel" :
    		getExtcTrgt =  DlwOcbProdDAO.getOcbDelList(pmParam);
    		break;
    	case "savdel" :
    		getExtcTrgt =  DlwOcbProdDAO.getOcbSavDelList(pmParam);
    		break;
    	case "usedel" :
    		getExtcTrgt =  DlwOcbProdDAO.getOcbUseDelList(pmParam);
    		break;
    	}

    	return getExtcTrgt;
    }

    /**
     * 대명라이프웨이  OCB 상품 적립 전송 정보를 조회한다.
     * 임동진 수정
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public List<Map<String, Object>> getOcbAddList(Map<String, ?> pmParam) throws Exception {
    	List<Map<String, Object>> getExtcTrgt = new ArrayList<Map<String,Object>>();

    	System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb> " + String.valueOf(pmParam.get("settype")));

    	switch(String.valueOf(pmParam.get("settype"))){
    	case "sav" :
    		getExtcTrgt =  DlwOcbProdDAO.getOcbAddList(pmParam);
    		break;
    	case "use" :
    		getExtcTrgt =  DlwOcbProdDAO.getOcbUseList(pmParam);
    		break;
    	}

    	return getExtcTrgt;
    }

    /**
     * 대명라이프웨이 OCB 전송 이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public int getOcbTransHistCnt() throws Exception {
        return DlwOcbProdDAO.getOcbTransHistCnt();
    }

    /**
     * 대명라이프웨이 OCB 전송이력등록
     *
     * @param pmParam 검색 조건
     * @throws Exception
    */

    public int insertOcbTransHist(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.insertOcbTransHist(pmParam);
    }

    /**
     * 대명라이프웨이 OCB 전송이력 수정
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int updateOcbTransHist(Map<String, ?> pmParam) throws Exception {
        return DlwOcbProdDAO.updateOcbTransHist(pmParam);
    }
}
