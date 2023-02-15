/*
 * (@)# EvltPlan.java
 *
 * @author 최현식
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

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltReslDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvltReslDtl
 */
@Service
public class EvltReslDtlServiceImpl extends EgovAbstractServiceImpl implements EvltReslDtlService {

    @Resource
    public EvltReslDtlDAO evltReslDtlDAO;

    /**
     * 평가결과(상담별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 정보의 검색 수
     * @throws Exception
     */
    public int getEvltReslDtlCountByCons(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltReslDtlDAO.getEvltReslDtlCountByCons(pmParam);
    }

    /**
     * 평가결과(상담별) 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltReslDtlListByCons(Map<String, ?> pmParam) throws Exception {
        return evltReslDtlDAO.getEvltReslDtlListByCons(pmParam);
    }

    /**
     * 평가결과 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 정보의 검색 수
     * @throws Exception
     */
    public int getEvltReslDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltReslDtlDAO.getEvltReslDtlCount(pmParam);
    }

    /**
     * 평가결과 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltReslDtlList(Map<String, ?> pmParam) throws Exception {
        return evltReslDtlDAO.getEvltReslDtlList(pmParam);
    }

    /**
     * 평가수행-평가지 상세정보(1건)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가지 상세정보
     * @throws Exception
     */
    public Map<String, Object> getQaResultSheet(Map<String, ?> pmParam) throws Exception {
        return evltReslDtlDAO.getQaResultSheet(pmParam);
    }

    /**
     * 평가수행-평가지 상세정보(1건)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 평가지 상세정보
     * @throws Exception
     */
    public Map<String, Object> getQaResultSheetId(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("qaresultsheetid", id);

        return evltReslDtlDAO.getQaResultSheet(pmParam);
    }

    /**
     * 평가지 결과정보를 등록한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public String insertEvltReslDtl(Map<String, Object> pmParam) throws Exception {
        Map<String, String> mItmParam = new HashMap<String, String> ();
        String sKey = "";
        String sEvltItemIds = (String)pmParam.get("evlt_item_ids");

        int nResult = evltReslDtlDAO.insertEvltReslDtl(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("evlt_resl_id");
        }

        if (!"".equals(sEvltItemIds)) {
            String[] evltItemId = sEvltItemIds.split(",");
            for (int i=0; i< evltItemId.length; i++){
                String sId = evltItemId[i];
                mItmParam.put("evlt_resl_id", 	sKey);
                mItmParam.put("evlt_item_id",   sId);
                mItmParam.put("evst_id",     	(String)pmParam.get("evst_id"));
                mItmParam.put("cntr_cd",       	(String)pmParam.get("cntr_cd"));
                mItmParam.put("rgsr_id",       	(String)pmParam.get("rgsr_id"));
                mItmParam.put("amnd_id",       	(String)pmParam.get("amnd_id"));

                evltReslDtlDAO.insertEvltReslItemDtl(mItmParam);
            }
        }

        return sKey;
    }

    /**
     * 평가지 결과정보를 수정한다.
     *
     * @pmParam pmParam 평가지결과 정보
     * @throws Exception
     */
    public int updateEvltReslDtl(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, String> mItmParam = new HashMap<String, String> ();
        String sEvltItemIds = (String)pmParam.get("evlt_item_ids");

        evltReslDtlDAO.updateEvltReslDtl(pmParam);

        evltReslDtlDAO.deleteEvltReslItemDtl(pmParam);

        if (!"".equals(sEvltItemIds)) {
            String[] evltItemId = sEvltItemIds.split(",");
            for (int i=0; i< evltItemId.length; i++){
                String sId = evltItemId[i];
                mItmParam.put("evlt_resl_id", 	(String)pmParam.get("evlt_resl_id"));
                mItmParam.put("evlt_item_id",   sId);
                mItmParam.put("evst_id",     	(String)pmParam.get("evst_id"));
                mItmParam.put("cntr_cd",        (String)pmParam.get("cntr_cd"));
                mItmParam.put("rgsr_id",       	(String)pmParam.get("rgsr_id"));
                mItmParam.put("amnd_id",       	(String)pmParam.get("amnd_id"));

                nResult += evltReslDtlDAO.insertEvltReslItemDtl(mItmParam);
            }
        }
        return nResult;
    }

}
