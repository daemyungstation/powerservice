/*
 * (@)# NmsgDtlDAO.java
 *
 * @author 김은지
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
 * 쪽지 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID NmsgDtl
 */
@Repository
public class NmsgDtlDAO extends EgovAbstractMapper {

    /**
     * 쪽지 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 정보의 검색 건수
     * @throws Exception
     */
    public int getNmsgDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("NmsgDtlMap.getNmsgDtlCount", pmParam);
    }

    /**
     * 쪽지 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNmsgDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("NmsgDtlMap.getNmsgDtlList", pmParam);
    }

    /**
     * 쪽지 정보를 등록한다.
     *
     * @param pmParam 쪽지정보
     * @throws Exception
     */
    public int insertNmsgDtl(Map<String, Object> pmParam) throws Exception {
        return insert("NmsgDtlMap.insertNmsgDtl", pmParam);
    }

    /**
     * 쪽지 정보를 삭제한다.
     *
     * @param pmParam 쪽지 정보
     * @throws Exception
     */
    public int deleteNmsgDtl(Map<String, ?> pmParam) throws Exception {
        return delete("NmsgDtlMap.deleteNmsgDtl", pmParam);
    }

    /**
     * 쪽지 정보를 보관한다.
     *
     * @param pmParam 쪽지 정보
     * @throws Exception
     */
    public int updateNmsgDtlKpngYn(Map<String, ?> pmParam) throws Exception {
        return update("NmsgDtlMap.updateNmsgDtlKpngYn", pmParam);
    }

    /**
     * 쪽지 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 정보
     * @throws Exception
     */
    public Map<String, Object> getNmsgDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("NmsgDtlMap.getNmsgDtlList", pmParam);
    }

    /**
    * 쪽지 받은 날짜, 시간을 저장한다.
    *
    * @param pmParam 쪽지 정보
    * @throws Exception
    */
   public int updateNmsgRecpDttm(Map<String, ?> pmParam) throws Exception {
       return update("NmsgDtlMap.updateNmsgRecpDttm", pmParam);
   }

   /**
    * 쪽지 받은 날짜, 시간을 저장한다.
    *
    * @param pmParam 쪽지 정보
    * @throws Exception
    */
   public int updateNmsgImpnYn(Map<String, ?> pmParam) throws Exception {
       return update("NmsgDtlMap.updateNmsgImpnYn", pmParam);
   }

   /**
    * 쪽지 수신 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 쪽지 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getRecnList(Map<String, ?> pmParam) throws Exception {
       return selectList("NmsgDtlMap.getRecnList", pmParam);
   }

   /**
    * 전광판에서 사용할 새 쪽지 카운트를 가져온다.
    *
    * @return 쪽지 리스트
    * @throws Exception
    */
   public List<Map<String, Object>> getNewNmsgDtlCount() throws Exception {
       return selectList("NmsgDtlMap.getNewNmsgDtlCount");
   }

}
