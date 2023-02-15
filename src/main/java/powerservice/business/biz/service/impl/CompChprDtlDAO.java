/*
 * (@)# CompChprDtlDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 담당자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
 * @프로그램ID ChprBasi
 */
@Repository
public class CompChprDtlDAO extends EgovAbstractMapper {

    /**
	 * 고객사의 담당자정보를 등록한다.
	 *
	 * @param pmParam 고객 정보
	 * @throws Exception
	 */
	public int insertCscoChprDtl(Map<String, ?> pmParam) throws Exception {
		return insert("CompChprDtlMap.insertCscoChprDtl", pmParam);
	}
	
   /**
	* 고객사의 담당자정보를 등록한다.
	*
	* @param pmParam 고객 정보
	* @throws Exception
	*/
	public int insertCprtCompChprDtl(Map<String, ?> pmParam) throws Exception {
		  return insert("CompChprDtlMap.insertCprtCompChprDtl", pmParam);
	}

    /**
     * 고객사/협력사의 담당자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getCompChprCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CompChprDtlMap.getCompChprCount", pmParam);
    }

    /**
     * 고객사/협력사의 담당자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCompChprList(Map<String, ?> pmParam) throws Exception {
        return selectList("CompChprDtlMap.getCompChprList", pmParam);
    }

    /**
     * 고객사/협력사의 담당자 정보를 검색한다.
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCompChpr(Map<String, ?> pmParam) throws Exception {
        return selectOne("CompChprDtlMap.getCompChprList", pmParam);
    }

    /**
     * 고객사 담당자정보을 삭제한다.
     *
     * @param pmParam 고객사담당자정보
     * @throws Exception
     */
    public int deleteCscoChprDtl(Map<String, ?> pmParam) throws Exception {
        return delete("CompChprDtlMap.deleteCscoChprDtl", pmParam);
    }
    
    /**
     * 협력사 담당자정보을 삭제한다.
     *
     * @param pmParam 협력사담당자정보
     * @throws Exception
     */
    public int deleteCprtCompChprDtl(Map<String, ?> pmParam) throws Exception {
        return delete("CompChprDtlMap.deleteCprtCompChprDtl", pmParam);
    }
}
