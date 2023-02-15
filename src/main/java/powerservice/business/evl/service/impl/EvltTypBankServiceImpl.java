/*
 * (@)# VctnAplcDtlService.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltTypBankService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnAplcDtl
 */
@Service
public class EvltTypBankServiceImpl extends EgovAbstractServiceImpl implements EvltTypBankService {

    @Resource
    public EvltTypBankDAO evltTypBankDAO;

    @Resource
    public EvltItemBankDAO evltItemBankDAO;

    /**
     * 평가유형 트리 정보을 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypTree(Map<String, ?> pmParam) throws Exception {
        return evltTypBankDAO.getEvltTypTree(pmParam);
    }

    /**
     * 평가유형 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가유형 정보의 검색 수
     * @throws Exception
     */
    public int getEvltTypCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltTypBankDAO.getEvltTypCount(pmParam);
    }

    /**
     * 평가유형 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypList(Map<String, ?> pmParam) throws Exception {
        return evltTypBankDAO.getEvltTypList(pmParam);
    }

    /**
     * 평가유형 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가유형 정보
     * @throws Exception
     */
    public Map<String, Object> getEvltTypView(Map<String, ?> pmParam) throws Exception {
        String sEvltTypId = (String) pmParam.get("evlt_typ_id");

        if ("".equals(sEvltTypId) || sEvltTypId == null) {
            return evltTypBankDAO.getEvltTypRoot(pmParam);
        } else {
            return evltTypBankDAO.getEvltTypView(pmParam);
        }
    }

    /**
     * 평가유형 정보를 등록한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public String insertEvltTyp(Map<String, Object> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String sDupYn = StringUtils.defaultString((String) pmParam.get("dup_yn"));
        if ("Y".equals(sDupYn)) {
            evltTypBankDAO.updateEvltTypOrd(pmParam);
        }

        String key = "";

        int result = evltTypBankDAO.insertEvltTyp(pmParam);
        if (result > 0) {
            key = (String) pmParam.get("evlt_typ_id");
        }

        return key;
    }

    /**
     * 평가유형 정보를 수정한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public int updateEvltTyp(Map<String, Object> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String sDupYn = StringUtils.defaultString((String) pmParam.get("dup_yn"));
        if ("Y".equals(sDupYn)) {
            evltTypBankDAO.updateEvltTypOrd(pmParam);
        }

        int nResult = evltTypBankDAO.updateEvltTyp(pmParam);
        return nResult;
    }

    /**
     * 평가유형 정보를 삭제한다.
     *
     * @param param 평가유형 정보
     * @throws Exception
     */
    public void deleteEvltTyp(Map<String, Object> pmParam) throws Exception {
        evltItemBankDAO.deleteEvltItemBank(pmParam);

        // 정렬순서 차감
        pmParam.put("old_expe_sqnc", pmParam.get("expe_sqnc"));
        pmParam.put("expe_sqnc", "999");
        evltTypBankDAO.updateEvltTypOrd(pmParam);

        evltTypBankDAO.deleteEvltTyp(pmParam);

    }

    /**
     * 평가유형 DROPDOWNLIST
     *
     * @param param 검색 조건
     * @return 평가유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTypDropdownList(Map<String, ?> pmParam) throws Exception {
        return evltTypBankDAO.getEvltTypDropdownList(pmParam);
    }


}
