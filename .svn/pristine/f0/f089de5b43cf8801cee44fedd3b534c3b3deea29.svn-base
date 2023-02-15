/*
 * (@)# ConsTypDAO.java
 *
 * @author 차건호
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 상담유형 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ConsTyp
 */
@Repository
public class ConsTypDAO extends EgovAbstractMapper {

    /**
     * 상담유형 정보 트리 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypTree(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsTypMap.getConsTypTree", pmParam);
    }

    /**
     * 상담유형 정보 트리 리스트를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypTree2(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsTypMap.getConsTypTree2", pmParam);
    }

    /**
     * 상담유형 정보의 검색 수를 반환한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 정보의 검색 수
     * @throws Exception
     */
    public int getConsTypTotalCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsTypMap.getConsTypTotalCount", pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTyp(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsTypMap.getConsTyp", pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getConsTyp2(Map<String, ?> pmParam) throws Exception {
        return selectOne("ConsTypMap.getConsTyp", pmParam);
    }

    /**
     * 상담유형 정보의 검색 수를 반환한다.(팝업)
     *
     * @param pmParam 검색 조건
     * @return 상담유형 정보의 검색 수
     * @throws Exception
     */
    public int getConsTypCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsTypMap.getConsTypCount", pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsTypMap.getConsTypList", pmParam);
    }

    /**
     * 상담유형 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 상담유형 순서의 중복 건수
     * @throws Exception
     */
    public int getConsTypDupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsTypMap.getConsTypDupCount", pmParam);
    }

    /**
     * 상담유형 정보를 삭제한다.
     *
     * @param param 상담유형 정보
     * @throws Exception
     */
    public int deleteConsTyp(Map<String, ?> pmParam) throws Exception {
        return delete("ConsTypMap.deleteConsTyp", pmParam);
    }

    /**
     * 상담유형 정보를 등록 및 수정한다.
     *
     * @param pmParam 상담유형 정보
     * @throws Exception
     */
    public int mergeConsTyp(Map<String, ?> pmParam) throws Exception {
        return insert("ConsTypMap.mergeConsTyp", pmParam);
    }

    /**
     * 상담유형 순서를 수정한다.
     *
     * @param pmParam 상담유형 정보
     * @throws Exception
     */
    public int updateConsTypOrd(Map<String, ?> pmParam) throws Exception {
        return update("ConsTypMap.updateConsTypOrd", pmParam);
    }

    /**
     * 상담이력에 해당 상담유형 등록 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 상담유형 건수
     * @throws Exception
     */
    public int getConsConsTypCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsTypMap.getConsConsTypCount", pmParam);
    }

    /**
     * 상담유형 순서의 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getConsTypMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsTypMap.getConsTypMaxOrder", pmParam);
    }

    /**
     * 상담유형 - 엔컴CNSL_DTL GUBN값 조회
     *
     * @param pmParam 검색 조건
     * @return 상담유형 엔컴구분값
     * @throws Exception
     */
    public String getConsTypGubn(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("ConsTypMap.getConsTypGubn", pmParam);
    }

}
