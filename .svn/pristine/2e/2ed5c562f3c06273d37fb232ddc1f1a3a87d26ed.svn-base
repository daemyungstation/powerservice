/*
 * (@)# PersInfoPcustCnsnDao.java
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

package powerservice.business.cns.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 콜백 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CalbDtl
 */
@Repository
public class PersInfoPcusCnsnDAO extends EgovAbstractMapper {

    /**
     * 고객 개인정보 활용동의 정보를 등록한다.
     *
     * @param pmParam 고객 개인정보 활용동의 정보
     * @throws Exception
     */
    public int insertPersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception {
        return insert("PersInfoPcusCnsnMap.insertPersInfoPcusCnsn", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보를 등록한다.(이력)
     *
     * @param pmParam 고객 개인정보 활용동의 정보
     * @throws Exception
     */
    public int insertPersInfoPcusCnsnHstr(Map<String, ?> pmParam) throws Exception {
        return insert("PersInfoPcusCnsnMap.insertPersInfoPcusCnsnHstr", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보를 수정한다.
     *
     * @param pmParam 고객 개인정보 활용동의 정보
     * @throws Exception
     */
    public int updatePersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception {
        return update("PersInfoPcusCnsnMap.updatePersInfoPcusCnsn", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보를 수정한다.
     *
     * @param pmParam 고객 개인정보 활용동의 정보
     * @throws Exception
     */
    public int mergePersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception {
        return update("PersInfoPcusCnsnMap.mergePersInfoPcusCnsn", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 개인정보 활용동의 정보의 검색 건수
     * @throws Exception
     */
    public int getPersInfoPcusCnsnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PersInfoPcusCnsnMap.getPersInfoPcusCnsnCount", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPersInfoPcusCnsnList(Map<String, ?> pmParam) throws Exception {
        return selectList("PersInfoPcusCnsnMap.getPersInfoPcusCnsnList", pmParam);
    }

    /**
     * 고객 개인정보 활용동의 정보를 검색한다.
     *
     * @param psId 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception {
        return selectOne("PersInfoPcusCnsnMap.getPersInfoPcusCnsnList", pmParam);
    }

    /**
     * 개인정보 활용동의 스크립트 상세정보를 검색한다.
     *
     * @param 없음
     * @return 스크립트 상세정보
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusSrctDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("PersInfoPcusCnsnMap.getPersInfoPcusSrctHstrList", pmParam);
    }

    /**
     * 개인정보 활용동의 스크립트 정보를 수정한다.
     *
     * @param pmParam 개인정보 활용동의 스크립트 정보
     * @throws Exception
     */
    public int updatePersInfoPcusSrct(Map<String, ?> pmParam) throws Exception {
        return update("PersInfoPcusCnsnMap.updatePersInfoPcusSrct", pmParam);
    }

    /**
     * 개인정보 활용동의 스크립트 이력 정보를 등록한다.
     *
     * @param pmParam 개인정보 활용동의 스크립트 이력 정보
     * @throws Exception
     */
    public int insertPersInfoPcusSrctHstr(Map<String, ?> pmParam) throws Exception {
        return insert("PersInfoPcusCnsnMap.insertPersInfoPcusSrctHstr", pmParam);
    }

    /**
     * 개인정보 활용동의 스크립트 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 개인정보 활용동의 스크립트 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getPersInfoPcusSrctHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PersInfoPcusCnsnMap.getPersInfoPcusSrctHstrCount", pmParam);
    }

    /**
     * 개인정보 활용동의 스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 개인정보 활용동의 스크립트 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPersInfoPcusSrctHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("PersInfoPcusCnsnMap.getPersInfoPcusSrctHstrList", pmParam);
    }

}
