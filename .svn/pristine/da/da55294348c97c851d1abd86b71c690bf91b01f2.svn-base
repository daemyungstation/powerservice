/*
 * (@)# TargetcustDao.java
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
 * 대상고객리스트 추출고객 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID  Targetcust
 */

@Repository
public class TrgtCustDAO extends EgovAbstractMapper {

       /**
     * 대상고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrgtCustMap.getTrgtCustCount", pmParam);
    }

    /**
     * 대상고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustMap.getTrgtCustList", pmParam);
    }

    /**
     * 대상고객 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 리스트
     * @throws Exception
     */
    public Map<String, Object> getTrgtCust(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrgtCustMap.getTrgtCustList", pmParam);
    }

    /**
     * 대상리스트 정보를 삭제한다.(고객)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCust(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustMap.deleteTrgtCust", pmParam); // 대상고객 삭제
    }

    /**
     * 대상 정보를 저장한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     *//*
    public int insertTrgtCustByDB(Map<String, ?> pmParam) throws Exception {
        return insert("TrgtCustMap.insertTrgtCustByDB", pmParam);
    }*/


    /**
     * 대상리스트 정보를 삭제한다.(소분류리스트)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    /*public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception {
         return delete("TrgtCustMap.deleteTrgtCustByTrgtList", pmParam); // 대상고객 삭제
    }
*/
    /**
     * 옵션에 따른 대상리스트 정보를 삭제한다.(엑셀 업로드 시 사용)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCustByExcel(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustMap.deleteTrgtCustByExcel", pmParam); // 대상고객 삭제
    }

    /**
     * 온라인건을 조회한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> onlineId(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustMap.onlineId", pmParam);
    }


    /**
     * 대상고객 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 이력  정보의 검색 수
     * @throws Exception
     */
    public int getTrgtCustHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrgtCustMap.getTrgtCustHstrCount", pmParam);
    }

    /**
     * 대상고객 이력  정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 이력  리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustMap.getTrgtCustHstrList", pmParam);
    }

}
