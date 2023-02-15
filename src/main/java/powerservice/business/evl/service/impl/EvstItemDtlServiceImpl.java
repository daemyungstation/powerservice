/*
 * (@)# EvstItemDtlService.java
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

package powerservice.business.evl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltReslDtlService;
import powerservice.business.evl.service.EvstItemDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvstItemDtl
 */
@Service
public class EvstItemDtlServiceImpl extends EgovAbstractServiceImpl implements EvstItemDtlService {

    @Resource
    public EvstItemDtlDAO evstItemDtlDAO;

    /**
     * 평가항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보의 검색 수
     * @throws Exception
     */
    public int getEvstItemDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evstItemDtlDAO.getEvstItemDtlCount(pmParam);
    }

    /**
     * 평가항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstItemDtlList(Map<String, ?> pmParam) throws Exception {
        return evstItemDtlDAO.getEvstItemDtlList(pmParam);
    }

    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertEvstItemDtl(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        String sEvstId = (String)pmParam.get("evst_id");
        String sCntrCd = (String)pmParam.get("cntr_cd");
        mParam.put("evst_id", sEvstId);
        mParam.put("cntr_cd", sCntrCd);
        evstItemDtlDAO.insertEvstItemDtl(pmParam);

        List<Map<String, Object>> mdataList = evstItemDtlDAO.getEvstItemDtlList(mParam);
        mParam = mdataList.get(0);
        mParam.put("desc_yn", "Y");
        mParam.put("amnd_id", pmParam.get("amnd_id"));
        nResult += evstItemDtlDAO.updateEvstDtl(mParam);
        return nResult;
    }

    /**
     * 평가유형 정보를 등록한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int insertEvltTyp(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        evstItemDtlDAO.insertEvltTyp(pmParam);
        // 유형에 따른 항목 등록
        evstItemDtlDAO.insertEvstItemDtl(pmParam);

        List<Map<String, Object>> mdataList = evstItemDtlDAO.getEvstItemDtlList(pmParam);

        mParam = mdataList.get(0);
        mParam.put("desc_yn", "Y");
        mParam.put("amnd_id", pmParam.get("amnd_id"));
        nResult += evstItemDtlDAO.updateEvstDtl(mParam);
        return nResult;
    }

    /**
     * 평가항목 정보를 삭제한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int deleteEvstItemDtl(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        String sDelFg = (String)pmParam.get("deletefg");    // 평가지 삭제여부
        evstItemDtlDAO.deleteEvstItemDtl(pmParam);

        if (!"Y".equals(sDelFg)) {
            mParam.put("evst_id", pmParam.get("evst_id"));
            mParam.put("cntr_cd", pmParam.get("cntr_cd"));
            List<Map<String, Object>> mdataList = evstItemDtlDAO.getEvstItemDtlList(mParam);

            if (mdataList.size() > 0) {
                mParam = mdataList.get(0);
            } else {    // 평가지에 항목이 없을 경우
                mParam.put("evlt_qsit_cnt", "0");
                mParam.put("evlt_totl_scr", "0");
            }

            mParam.put("desc_yn", "Y");
            mParam.put("amnd_id", pmParam.get("amnd_id"));
            nResult += evstItemDtlDAO.updateEvstDtl(mParam);
        }

        return nResult;
    }

    /**
     * 평가유형 정보를 삭제한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int deleteEvltTyp(Map<String, Object> pmParam) throws Exception {
        return evstItemDtlDAO.deleteEvltTyp(pmParam);
    }

    /**
     * 평가항목 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertCopyEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return evstItemDtlDAO.insertCopyEvstItemDtl(pmParam);
    }

    /**
     * 평가유형 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertCopyEvltTyp(Map<String, ?> pmParam) throws Exception {
        return evstItemDtlDAO.insertCopyEvltTyp(pmParam);
    }

    /**
     * 평가지 항목정보를 조회한다
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstItemDtl(Map<String, ?> pmParam) throws Exception {
        return evstItemDtlDAO.getEvstItemDtl(pmParam);
    }


    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertEvstTypItem(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        String sEvstId = (String)pmParam.get("evst_id");
        String sCntrCd = (String)pmParam.get("cntr_cd");
        mParam.put("evst_id", sEvstId);
        mParam.put("cntr_cd", sCntrCd);
        evstItemDtlDAO.insertEvstItemDtl(pmParam);

        // 유형저장
        evstItemDtlDAO.insertEvltTyp(pmParam);

        List<Map<String, Object>> mdataList = evstItemDtlDAO.getEvstItemDtlList(mParam);

        mParam = mdataList.get(0);
        mParam.put("desc_yn", "Y");
        mParam.put("amnd_id", pmParam.get("amnd_id"));
        nResult += evstItemDtlDAO.updateEvstDtl(mParam);

        return nResult;

    }

    /**
     * 설문항목보기 순서를 변경한다.
     *
     * @param pmModelList List<Map<String, Object>>
     * @param pmParam 설문항목 순서정보
     * @throws Exception
     */
    public int updateItemSortOrder(List<Map<String, Object>> pmModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (Map<String, Object> mModel : pmModelList) {
            String evlt_item_id = StringUtils.defaultString((String) mModel.get("evlt_item_id"));
            if (!"".equals(evlt_item_id)) {
                pmParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                pmParam.put("evlt_item_id", mModel.get("evlt_item_id"));
                pmParam.put("evlt_typ_id", mModel.get("evlt_typ_id"));
                pmParam.put("evst_id", mModel.get("evst_id"));

                nResult += evstItemDtlDAO.updateEvstItemDtl(pmParam);
            }
        }
        return nResult;
    }
}
