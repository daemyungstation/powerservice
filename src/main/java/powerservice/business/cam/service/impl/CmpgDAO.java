/*
 * (@)# CmpgDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID Cmpg
 */

@Repository
public class CmpgDAO extends EgovAbstractMapper {

    /**
     * 캠페인 정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 정보의 검색 수
     * @throws Exception
     */
    public int getCmpgCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CmpgMap.getCmpgCount", pmParam);
    }

    /**
     * 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgMap.getCmpgList", pmParam);
    }

    /**
     * 캠페인 정보를 등록한다. 사용
     *
     * @param param 캠페인 정보
     * @throws Exception
     */
    public int insertCmpg(Map<String, Object> pmParam) throws Exception {
        return insert("CmpgMap.insertCmpg", pmParam);
    }


    public int deleteTgtCustAlctByUpdateCmpg(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustAlctMap.deleteTgtCustAlctByUpdateCmpg", pmParam); // 대상고객할당 삭제
    }

    /**
     * 캠페인 정보를 검색한다.
     *
     * @param String 캠페인 ID
     * @return 캠페인 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCmpg(Map<String, Object> pmParam) throws Exception {
        return selectOne("CmpgMap.getCmpgList", pmParam);
    }

    /**
     * 사용자별 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgListByUserId(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgMap.getCmpgListByUserId", pmParam);
    }

    /**
     * 캠페인 정보를 수정한다. 사용
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public int updateCmpg(Map<String, Object> pmParam) throws Exception {
         return update("CmpgMap.updateCmpg", pmParam);
    }

    /**
     * 캠페인 정보를 삭제한다. 사용
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public int deleteCmpg(Map<String, Object> pmParam) throws Exception {
        return delete("CmpgMap.deleteCmpg", pmParam); // 캠페인 정보 삭제
    }

    //사용
    public int deleteTgtCustAlctByCmpgId(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustAlctMap.deleteTgtCustAlctByCmpgId", pmParam); // 대상고객할당 삭제
    }

    /**
     * 캠페인할당 정보를 캠페인ID 기준으로 삭제한다. 사용
     *
     * @param pmParamHash 캠페인할당 정보
     * @throws Exception
     */
    public int deleteCmpgAlctbyCmpgId(Map<String, Object> pmParam) throws Exception {
        return delete("CmpgAlctMap.deleteCmpgAlctbyCmpgId", pmParam);
    }
    /**
     * 캠페인 콤보박스 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> searchCmpgList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgMap.searchCmpgList", pmParam);
    }

}
