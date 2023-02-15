/*
 * (@)# VocDtlDAO.java
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
 * VOC 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VocDtl
 */
@Repository
public class VocDtlDAO extends EgovAbstractMapper {

    /**
     * VOC 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 정보의 검색 수
     * @throws Exception
     */
    public int getVocDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("VocDtlMap.getVocDtlCount", pmParam);
    }

    /**
     * VOC 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("VocDtlMap.getVocDtlList", pmParam);
    }

    /**
     * VOC 정보를 검색한다. (1건)
     *
     * @param pmParam VOC아이디
     * @return VOC 정보
     * @throws Exception
     */
    public Map<String, Object> getVocDtl(Map<String, ?> pmParam)  throws Exception {
        return selectOne("VocDtlMap.getVocDtlList", pmParam);
    }

    /**
     * VOC 정보를 등록한다.
     *
     * @param pmParam VOC 정보
     * @throws Exception
     */
    public int insertVocDtl(Map<String, ?> pmParam) throws Exception {
        return insert("VocDtlMap.insertVocDtl", pmParam);
    }

    /**
     * VOC 이력 정보를 등록한다.
     *
     * @param pmParam VOC 정보
     * @throws Exception
     */
    public int insertVocDtlHstr(Map<String, ?> pmParam) throws Exception {
        return insert("VocDtlMap.insertVocDtlHstr", pmParam);
    }

    /**
     * VOC 정보를 수정한다.
     *
     * @param pmParam VOC 정보
     * @throws Exception
     */
    public int updateVocDtl(Map<String, ?> pmParam) throws Exception {
        return update("VocDtlMap.updateVocDtl", pmParam);
    }

    /**
     * VOC 해피콜 처리 이력 정보를 등록한다.
     *
     * @param pmParam VOC 정보
     * @throws Exception
     */
    public int insertVocHpclDspsHstr(Map<String, ?> pmParam) throws Exception {
        return insert("VocDtlMap.insertVocHpclDspsHstr", pmParam);
    }


    /**
     * 상담사별 VOC 해피콜 미처리 건수를 검색한다.
     *
     * @return 상담사별 VOC 해피콜 미처리 건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoVocHpclDspsCount() throws Exception {
        return selectList("VocDtlMap.getTodoVocHpclDspsCount");
    }


    /**
     * VOC 해피콜 처리 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 해피콜 처리 정보의 검색 수
     * @throws Exception
     */
    public int getVocHpclDspsHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("VocDtlMap.getVocHpclDspsHstrCount", pmParam);
    }

    /**
     * VOC 해피콜 처리 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 해피콜 처리 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocHpclDspsHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("VocDtlMap.getVocHpclDspsHstrList", pmParam);
    }


    // 삭제 데이터
    public int deletevoc(Map<String, ?> pmParam) throws Exception {
        return update("VocDtlMap.deletevoc", pmParam);
    }

}
