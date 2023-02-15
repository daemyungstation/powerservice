/*
 * (@)# DlvDAO.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
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
package powerservice.business.chn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 물류 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
 * @프로그램ID PS450400
 *
 */
@Repository
public class DlvDAO extends EgovAbstractMapper {

    /**
     * 회원번호의 증서가 있는지 알기위해 회원번호 개수
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    public int getDlvAccntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlvMap.getDlvAccntCount", pmParam);
    }

    /**
     * 물류 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    public int getDlvCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlvMap.getDlvCount", pmParam);
    }

    /**
     * 물류 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlvMap.getDlvList", pmParam);
    }

    /**
     * 물류 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return insert 결과
     * @throws Exception
     */
    public int insertDlv(Map<String, ?> pmParam) throws Exception {
        return insert("DlvMap.insertDlv", pmParam);
    }

    /**
     * 물류 정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 리스트
     * @throws Exception
     */
    public int updateDlv(Map<String, ?> pmParam) throws Exception {
        return update("DlvMap.updateDlv", pmParam);
    }

    /**
     * 물류 정보를 삭제한다.
     *
     * @param pmParam 검색 조건
     * @return 삭제 결과
     * @throws Exception
     */
    public int deleteDlv(Map<String, ?> pmParam) throws Exception {
        return delete("DlvMap.deleteDlv", pmParam);
    }




    /**
     * 물류상세 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    /*
    public int getDlvDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlvMap.getDlvDtlCount", pmParam);
    }
    */

    /**
     * 물류상세 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류상세 리스트
     * @throws Exception
     */
    /*
    public List<Map<String, Object>> getDlvDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlvMap.getDlvDtlList", pmParam);
    }
    */

    /**
     * 물류상세 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return insert 결과
     * @throws Exception
     */
    public int insertDlvDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlvMap.insertDlvDtl", pmParam);
    }

    /**
     * 물류상세 정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 리스트
     * @throws Exception
     */
    public int updateDlvDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlvMap.updateDlvDtl", pmParam);
    }

    /**
     * 물류상세 정보를 삭제한다.
     *
     * @param pmParam 검색 조건
     * @return 삭제 결과
     * @throws Exception
     */
    public int deleteDlvDtl(Map<String, ?> pmParam) throws Exception {
        return delete("DlvMap.deleteDlvDtl", pmParam);
    }

}
