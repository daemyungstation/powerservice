/*
 * (@)# ConsTypService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.impl.DlwConsDAO;
import powerservice.business.kms.service.ConsTypService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service
public class ConsTypServiceImpl extends EgovAbstractServiceImpl implements ConsTypService {

    @Resource
    public ConsTypDAO consTypDAO;

    @Resource
    public DlwConsDAO dlwConsDAO;

    /**
     * 상담유형 정보 트리 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypTree(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypTree(pmParam);
    }

    /**
     * 상담유형 정보 트리 리스트를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypTree2(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypTree2(pmParam);
    }

    /**
     * 상담유형 정보의 검색 수를 반환한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 정보의 검색 수
     * @throws Exception
     */
    public int getConsTypTotalCount(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypTotalCount(pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTyp(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTyp(pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @Param pmParam 검색 조건
     * @return 상담유형 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getConsTyp2(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTyp2(pmParam);
    }

    /**
     * 상담유형 정보의 검색 수를 반환한다.(팝업)
     *
     * @param pmParam 검색 조건
     * @return 상담유형 정보의 검색 수
     * @throws Exception
     */
    public int getConsTypCount(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypCount(pmParam);
    }

    /**
     * 상담유형 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형 리스트
     * @throws Exception
     */
    public  List<Map<String, Object>> getConsTypList(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypList(pmParam);
    }

    /**
     * 상담유형 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 상담유형 순서의 중복 건수
     * @throws Exception
     */
    public int getConsTypDupCount(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypDupCount(pmParam);
    }

    /**
     * 상담유형 정보를 삭제한다.
     *
     * @param param 상담유형 정보
     * @throws Exception
     */
    public int deleteConsTyp(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.deleteConsTyp(pmParam);
    }

    /**
     * 상담유형 정보를 등록 및 수정한다.
     *
     * @param pmParam 상담유형 정보
     * @throws Exception
     */
    public int mergeConsTyp(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDuplFg = Boolean.valueOf((String)pmParam.get("dplc_flag")).booleanValue();
        if (bDuplFg != null && bDuplFg) {
            consTypDAO.updateConsTypOrd(pmParam);
        }

        return consTypDAO.mergeConsTyp(pmParam);
    }

    /**
     * 상담이력에 해당 상담유형 등록 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 상담유형 건수
     * @throws Exception
     */
    public int getConsConsTypCount(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsConsTypCount(pmParam);
    }

    /**
     * 상담유형 순서의 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getConsTypMaxOrder(Map<String, ?> pmParam) throws Exception {
        return consTypDAO.getConsTypMaxOrder(pmParam);
    }

    /**
     * 그룹코드 조회(상담관리) 리스트를 조회한다.
     *
     * @Param pmParam 검색 조건
     * @return 그룹코드 조회(상담관리) 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsGrpCd(Map<String, ?> pmParam) throws Exception {
        return dlwConsDAO.getConsGrpCd(pmParam);
    }

}
