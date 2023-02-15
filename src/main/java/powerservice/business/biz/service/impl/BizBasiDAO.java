/*
 * (@)# BizBasiDAO.java
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

package powerservice.business.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 사업원장 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BizBasi
 */
@Repository
public class BizBasiDAO extends EgovAbstractMapper {

	/**
     * 사업원장 정보를 등록한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
	public int insertBizBasi(Map<String, ?> pmParam) {
		return insert("BizBasiMap.insertBizBasi", pmParam);
    }

	/**
     * 사업원장 정보를 수정한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
    public int updateBizBasi(Map<String, ?> pmParam) {
        return update("BizBasiMap.updateBizBasi", pmParam);
    }
    
    /**
     * 사업원장의 담당자수, 영업활동건수, 영업이슈건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int checkInfoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("BizBasiMap.checkInfoCnt", pmParam);
    }

    /**
     * 사업원장 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 정보의 검색 건수
     * @throws Exception
     */
    public int getBizBasiCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("BizBasiMap.getBizBasiCount", pmParam);
    }

    /**
     * 사업원장 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBizBasiList(Map<String, ?> pmParam) {
        return selectList("BizBasiMap.getBizBasiList", pmParam);
    }

    /**
     * 사업원장 정보를 검색한다.
     *
     * @param sId 사업ID
     * @return 사업원장 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getBizBasi(Map<String, ?> pmParam) throws Exception {
        return selectOne("BizBasiMap.getBizBasiList", pmParam);
    }
    
    
    /**
     * 사업원장 이력정보를 등록한다.
     *
     * @param pmParam 사업원장 이력정보
     * @throws Exception
     */
    public int insertBizBasiHstr(Map<String, ?> pmParam) throws Exception {
        return insert("BizBasiMap.insertBizBasiHstr", pmParam);
    }
    
    /**
     * 사업원장 정보를 삭제한다.
     *
     * @param pmParam 사업원장 이력정보
     * @throws Exception
     */
    public int deleteBizBasi(Map<String, Object> pmParam) throws Exception {
        return delete("BizBasiMap.deleteBizBasi", pmParam);
    }
    
	/**
     * 내관심사업 정보를 저정한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
    public int mergeCncrBizDtl(Map<String, ?> pmParam) {
        return update("BizBasiMap.mergeCncrBizDtl", pmParam);
    }
    
    /**
     * 내관심사업 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 정보의 검색 건수
     * @throws Exception
     */
    public int getCncrBizCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("BizBasiMap.getCncrBizCount", pmParam);
    }
    
    /**
     * 내관심사업 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCncrBizList(Map<String, ?> pmParam) {
        return selectList("BizBasiMap.getCncrBizList", pmParam);
    }
    
    /**
     * 내관심사업 정보를 삭제한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int deleteCncrBiz(Map<String, ?> pmParam) throws Exception {
        return delete("BizBasiMap.deleteCncrBiz", pmParam);
    }
}
