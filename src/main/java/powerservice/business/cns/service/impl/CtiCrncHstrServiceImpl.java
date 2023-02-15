/*
 * (@)# CtiCrncHstrService.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.CtiCrncHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * CTI 통화이력을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
 * @프로그램ID CtiCrncHstr
 */
@Service
public class CtiCrncHstrServiceImpl extends EgovAbstractServiceImpl implements CtiCrncHstrService {

    @Resource
    public CtiCrncHstrDAO ctiCrncHstrDAO;

    /**
     * 녹취 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 녹취 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getCtiCrncHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ctiCrncHstrDAO.getCtiCrncHstrCount(pmParam);
    }

    /**
     * 녹취 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 녹취 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCtiCrncHstrList(Map<String, ?> pmParam) throws Exception {
        return ctiCrncHstrDAO.getCtiCrncHstrList(pmParam);
    }

    /**
     * CTI 통화이력 정보를 등록한다.
     *
     * @param pmParam CTI 통화이력 정보
     * @throws Exception
     */
    public String insertCtiCrncDtl(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = ctiCrncHstrDAO.insertCtiCrncDtl(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("cti_crnc_dtl_id");

            ctiCrncHstrDAO.insertCtiCrncHstr(pmParam);
        }
        return skey;
    }

    /**
     * CTI 통화이력 정보를 수정한다.
     *
     * @param pmParam CTI 통화이력 정보
     * @throws Exception
     */
    public int updateCtiCrncDtl(Map<String, Object> pmParam) throws Exception {
        int nResult = ctiCrncHstrDAO.updateCtiCrncDtl(pmParam);

        if (nResult > 0) {
            ctiCrncHstrDAO.insertCtiCrncHstr(pmParam);
        }
        return nResult;
    }

    /**
     * 녹취 상담 정보를 등록한다.
     *
     * @param pmParam 녹취 상담 정보
     * @throws Exception
     */
    public String mergeRecConsDtl(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = ctiCrncHstrDAO.mergeRecConsDtl(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("rec_cons_dtl_id");
        }
        return skey;
    }

    /**
     * 녹취 상품 정보를 등록한다.
     *
     * @param pmParam 녹취 상품 정보
     * @throws Exception
     */
    public String mergeRecProdDtl(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = ctiCrncHstrDAO.mergeRecProdDtl(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("rec_prod_dtl_id");
        }
        return skey;
    }

    /**
     * 녹취 TM 정보를 등록한다.
     *
     * @param pmParam 녹취 TM 정보
     * @throws Exception
     */
    public String mergeRecTmDtl(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = ctiCrncHstrDAO.mergeRecTmDtl(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("rec_tm_dtl_id");
        }
        return skey;
    }

}
