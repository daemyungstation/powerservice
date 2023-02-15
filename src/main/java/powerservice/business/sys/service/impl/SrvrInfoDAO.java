/*
 * (@)# SrvrInfoDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 서버 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID SrvrInfo
 */
@Repository
public class SrvrInfoDAO extends EgovAbstractMapper {

    /**
     * 서버 정보를 등록한다.
     *
     * @param pmParam 서버 정보
     * @throws Exception
     */
    public int insertSrvrInfo(Map<String, ?> pmParam) throws Exception {
        return insert("SrvrInfoMap.insertSrvrInfo", pmParam);
    }

    /**
     * 서버 정보를 수정한다.
     *
     * @param pmParam 서버 정보
     * @throws Exception
     */
    public int updateSrvrInfo(Map<String, ?> pmParam) throws Exception {
    	return update("SrvrInfoMap.updateSrvrInfo", pmParam);
    }

    /**
     * 서버 정보를 삭제한다.
     *
     * @param pmParam 서버 정보
     * @throws Exception
     */
    public int deleteSrvrInfo(Map<String, ?> pmParam) throws Exception {
    	return delete("SrvrInfoMap.deleteSrvrInfo", pmParam);
    }

    /**
     * 서버 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 서버 정보의 검색 건수
     * @throws Exception
     */
    public int getSrvrInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SrvrInfoMap.getSrvrInfoCount", pmParam);
    }

    /**
     * 서버 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 서버 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvrInfoList(Map<String, ?> pmParam) throws Exception {
        return selectList("SrvrInfoMap.getSrvrInfoList", pmParam);
    }

    /**
     * 서버 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 서버 목록(1건)
     * @throws Exception
     */
    public Map<String, Object> getSrvrInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("SrvrInfoMap.getSrvrInfoList", pmParam);
    }

    /**
     * 서버IP로 서버ID 정보를 검색한다.
     *
     * @param pmParam 서버IP
     * @return 서버ID 정보
     * @throws Exception
     */
    public Map<String, Object> getSrvrInfoBySrvrIpAddr(Map<String, ?> pmParam) throws Exception {
        return selectOne("SrvrInfoMap.getSrvrInfoBySrvrIpAddr", pmParam);
    }
	
}
