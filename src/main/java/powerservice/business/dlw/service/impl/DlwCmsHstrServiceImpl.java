/*
 * (@)# DlwCmsHstrServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/16
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCmsHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * CMS 이력 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/16
 * @프로그램ID DlwCmsHstr
 */
@Service
public class DlwCmsHstrServiceImpl extends EgovAbstractServiceImpl implements DlwCmsHstrService {

    @Resource
    public DlwCmsHstrDAO dlwCmsHstrDAO;

    /**
     * 납입이력(상조부금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwPayMngList(pmParam);
    }

    /**
     * 납입이력(결합상품할부원금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품할부원금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwPayMngDtlList(pmParam);
    }

    /**
     * 납입이력(결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwPayMngDtl1List(pmParam);
    }

    /**
     * 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngAllList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwPayMngAllList(pmParam);
    }

    /**
     * 해약정보_상품정보 및 가입일자에 따른 SEQ, 적용일자를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약정보
     * @throws Exception
     */
    public Map<String, Object> getDlwResnAmtList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwResnAmtList(pmParam);
    }

    /**
     * 결제정보를 검색한다. (개별목록 신청내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 결제정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwPymnHstrList(pmParam);
    }

    /**
     * 청구내역을 검색한다. (개별목록 청구내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 청구내역 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwAskHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwCmsHstrDAO.getDlwAskHstrList(pmParam);
    }

}
