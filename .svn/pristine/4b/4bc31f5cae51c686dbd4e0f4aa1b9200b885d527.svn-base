/*
 * (@)# SuctSetServiceImpl.java
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

package powerservice.business.kms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.SuctSetService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 퀵링크 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID SuctSet
 */

@Service
public class SuctSetServiceImpl extends EgovAbstractServiceImpl implements SuctSetService {

    @Resource
    public SuctSetDAO suctSetDAO;

    /**
     * 퀵링크 정보를 등록한다.
     *
     * @param pmParam 퀵링크 정보
     * @throws Exception
     */
    public String insertSuctSet(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            suctSetDAO.updateQLinkSetOrder(pmParam);
        }

        // 퀵링크 정보 저장
        String sKey = "";

        int nResult = suctSetDAO.insertSuctSet(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("suct_set_id");
        }
        return sKey;
    }

    /**
     * 퀵링크 정보를 수정한다.
     *
     * @param pmParam 퀵링크 정보
     * @throws Exception
     */
    public int updateSuctSet(Map<String, ?> pmParam) throws Exception {
        int nResult = 0;
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            suctSetDAO.updateQLinkSetOrder(pmParam);
        }

        // 퀵링크 정보 저장
        nResult += suctSetDAO.updateSuctSet(pmParam);

        // 가존의 다중건을 단일건으로 바꿀때, 저장시점에 선택된 세부항목 이외에는 사용안함으로 업데이트함
        String sSuctPpupDivCd = (String) pmParam.get("suct_ppup_div_cd");
        String sOldPpupTyp = (String) pmParam.get("old_suct_ppup_div_cd");
        String sSuctId = (String) pmParam.get("suct_id");

        if ("10".equals(sSuctPpupDivCd) && "20".equals(sOldPpupTyp) && sSuctId != null && !"".equals(sSuctId)) {
            suctSetDAO.updateSuctUnused(pmParam);
        }
        return nResult;
    }

    /**
     * 퀵링크 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 정보의 검색 건수
     * @throws Exception
     */
    public int getSuctSetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctSetDAO.getSuctSetCount(pmParam);
    }

    /**
     * 퀵링크 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSuctSetList(Map<String, ?> pmParam) throws Exception {
        return suctSetDAO.getSuctSetList(pmParam);
    }

    /**
     * 퀵링크 정보를 검색한다.
     *
     * @param String 퀵링크 ID
     * @return 퀵링크 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSuctSet(String id) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("suct_set_id", id);

        return suctSetDAO.getSuctSet(mParam);
    }

    /**
     * 퀵링크 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 순서 최대값
     * @throws Exception
     */
    public int getSuctSetMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctSetDAO.getSuctSetMaxOrder(pmParam);
    }

    /**
     * 퀵링크 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 순서의 중복 건수
     * @throws Exception
     */
    public int getSuctSetDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctSetDAO.getSuctSetDuplicateCount(pmParam);
    }

    /**
     * 퀵링크 정보를 삭제한다.
     *
     * @param pmParam 퀵링크셋 아이디
     * @throws Exception
     */
    public int deleteSuctSet(Map<String, ?> pmParam) throws Exception {
        int nResult = 0;
        nResult += suctSetDAO.deleteSuctSet(pmParam);
        suctSetDAO.deleteSuct(pmParam);
        return nResult;
    }
}
