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
public class ChprBasiDAO extends EgovAbstractMapper {

    /**
     * 담당자 정보를 등록한다.
     *
     * @param pmParam 담당자 관리 정보
     * @throws Exception
     */
   public int insertChprBasi(Map<String, ?> pmParam) throws Exception {
         return insert("ChprBasiMap.insertChprBasi", pmParam);
    }


    /**
     *담당자 정보를 수정한다.
     *
     * @param pmParam 담당자 관리 정보
     * @throws Exception
     */
   public int updateChprBasi(Map<String, ?> pmParam) throws Exception {
        return update("ChprBasiMap.updateChprBasi", pmParam);
    }


    /**
     * 담당자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getChprBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ChprBasiMap.getChprBasiCount", pmParam);
    }


    /**
     * 담당자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChprBasiList(Map<String, ?> pmParam) throws Exception {
        return selectList("ChprBasiMap.getChprBasiList", pmParam);
    }


    /**
     * 담당자 정보를 검색한다.
     *
     * @param pmParam ChprID
     * @return 담당자 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getChprBasi(Map<String, ?> pmParam) throws Exception {
        return selectOne("ChprBasiMap.getChprBasiList", pmParam);
    }

    /**
     * 담당자 정보(팝업)의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getChprBasiPopupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ChprBasiMap.getChprBasiPopupCount", pmParam);
    }


    /**
     * 담당자 정보(팝업)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChprBasiPopupList(Map<String, ?> pmParam) throws Exception {
        return selectList("ChprBasiMap.getChprBasiPopupList", pmParam);
    }



}
