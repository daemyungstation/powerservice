/*
 * (@)# CompBasiDAO.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/29
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
 * 고객사/협력사 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
 * @프로그램ID <PS-UI-DS01>
 */
@Repository
public class CompBasiDAO extends EgovAbstractMapper {


    /**
     * 고객사/협력사를 등록한다.
     *
     * @param pmParam 고객사/협력사 정보
     * @throws Exception
     */
   public int insertCompBasi(Map<String, ?> pmParam) throws Exception {
       return insert("CompBasiMap.insertCompBasi", pmParam);
    }


    /**
     * 고객사/협력사를 수정한다.
     *
     * @param pmParam 고객사/협력사 정보
     * @throws Exception
     */
   public int updateCompBasi(Map<String, ?> pmParam) throws Exception {
        return update("CompBasiMap.updateCompBasi", pmParam);
    }


    /**
     * 고객사/협력사의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보의 검색 건수
     * @throws Exception
     */
    public int getCompBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CompBasiMap.getCompBasiCount", pmParam);
    }


    /**
     * 고객사/협력사 를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCompBasiList(Map<String, ?> pmParam) throws Exception {
        return selectList("CompBasiMap.getCompBasiList", pmParam);
    }


    /**
     * 고객사/협력사 를 검색한다.
     *
     * @param pmParam Comp ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCompBasi(Map<String, ?> pmParam) throws Exception {
        return selectOne("CompBasiMap.getCompBasiList", pmParam);
    }


    /**
     * 고객사/협력사 팝업의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보의 검색 건수
     * @throws Exception
     */
    public int getCompBasiPopupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CompBasiMap.getCompBasiPopupCount", pmParam);
    }


    /**
     * 고객사/협력사 팝업 목록를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCompBasiPopupList(Map<String, ?> pmParam) throws Exception {
        return selectList("CompBasiMap.getCompBasiPopupList", pmParam);
    }



}
