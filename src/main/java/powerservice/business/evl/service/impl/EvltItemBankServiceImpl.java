/*
 * (@)# EvltItemBankServiceImpl.java
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

import powerservice.business.evl.service.EvltItemBankService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltItemBank
 */
@Service
public class EvltItemBankServiceImpl extends EgovAbstractServiceImpl implements EvltItemBankService {

    @Resource
    public EvltItemBankDAO evltItemBankDAO;

    /**
     * 평가항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보의 검색 수
     * @throws Exception
     */
    public int getEvltItemBankCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltItemBankDAO.getEvltItemBankCount(pmParam);
    }

    /**
     * 평가항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltItemBankList(Map<String, ?> pmParam) throws Exception {
        return evltItemBankDAO.getEvltItemBankList(pmParam);
    }

    /**
     * 평가항목 정보를 등록한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public String insertEvltItemBank(Map<String, Object> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String sDupYn = StringUtils.defaultString((String) pmParam.get("dup_yn"));
        if ("Y".equals(sDupYn)) {
            evltItemBankDAO.modifyEvltItemBankOrd(pmParam);
        }

        String sKey = "";

        int nResult = evltItemBankDAO.insertEvltItemBank(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("evlt_item_id");
        }

        return sKey;
    }

    /**
     * 평가항목 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가항목 정보
     * @throws Exception
     */
    public Map<String, Object> getEvltItemBank(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("evlt_item_id", psId);

        return evltItemBankDAO.getEvltItemBank(mParam);
    }


    /**
     * 평가항목 정보를 수정한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int updateEvltItemBank(Map<String, Object> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String sDupYn = StringUtils.defaultString((String) pmParam.get("dup_yn"));
        if ("Y".equals(sDupYn)) {
            return evltItemBankDAO.modifyEvltItemBankOrd(pmParam);
        }

        return evltItemBankDAO.updateEvltItemBank(pmParam);
    }

    /**
     * 평가항목 정보를 삭제한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public void deleteEvltItemBank(Map<String, Object> pmParam) throws Exception {

        if (pmParam.get("selectcheck") != null) {
            List<Map<String, Object>> lsEvltTypId = evltItemBankDAO.getEvltTypIdList(pmParam);
            evltItemBankDAO.deleteEvltItemBank(pmParam);
            pmParam.put("selectcheck", lsEvltTypId);
            evltItemBankDAO.updateEvltItemBankOrd(pmParam);

        } else {
            evltItemBankDAO.deleteEvltItemBank(pmParam);
        }



    }

    /**
     * 평가항목 정보를 복사한다.
     *
     * @param param 평가항목 정보
     * @throws Exception
     */
    public int insertCopyEvltItemBank(Map<String, ?> pmParam) throws Exception {
        return evltItemBankDAO.insertCopyEvltItemBank(pmParam);
    }

}
