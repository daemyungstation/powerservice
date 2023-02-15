/*
 * (@)# ConsTrctHstrService.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.ConsTrctHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담이관이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ConsTrctHstr
 */
@Service
public class ConsTrctHstrServiceImpl extends EgovAbstractServiceImpl implements ConsTrctHstrService {

    @Resource
    public ConsTrctHstrDAO consTrctHstrDtlDAO;

    @Resource
    public ConsDAO consDAO;

    /**
     * 상담이관이력 정보를 등록한다.
     *
     * @param pmParam 상담이관이력 정보
     * @throws Exception
     */
    public int insertConsTrctHstr(Map<String, ?> pmParam) throws Exception {
        return consTrctHstrDtlDAO.insertConsTrctHstr(pmParam);
    }

    /**
     * 상담이관이력 정보를 수정한다.
     *
     * @param pmParam 상담이관이력 정보
     * @throws Exception
     */
    public int updateConsTrctHstr(Map<String, ?> pmParam) throws Exception {
        int nResult = consTrctHstrDtlDAO.updateConsTrctHstr(pmParam);
        if (nResult > 0) {
            // 상담이관 처리담당자 등록인 경우 상담정보 수정
            String sOperTyp = StringUtils.defaultString((String) pmParam.get("oper_typ"));
            if ("process".equals(sOperTyp)) {
                consDAO.updateConsTrctChpr(pmParam);
            }
        }
        return nResult;
    }

    /**
     * 상담이관이력 발송처리일시 정보를 수정한다.
     *
     * @param pmParam 상담이관이력 발송처리일시 정보
     * @throws Exception
     */
    public int updateConsTrctHstrSendProcDt(Map<String, ?> pmParam) throws Exception {
        return consTrctHstrDtlDAO.updateConsTrctHstrSendProcDt(pmParam);
    }

    /**
     * 상담이관이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이관이력 정보의 검색 건수
     * @throws Exception
     */
    public int getConsTrctHstrCount(Map<String, ?> pmParam) throws Exception {
        return consTrctHstrDtlDAO.getConsTrctHstrCount(pmParam);
    }

    /**
     * 상담이관이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이관이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTrctHstrList(Map<String, ?> pmParam) throws Exception {
        return consTrctHstrDtlDAO.getConsTrctHstrList(pmParam);
    }

    /**
     * 상담이관이력 TO-DO 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이관이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTrctHstrTodoList(Map<String, ?> pmParam) throws Exception {
        return consTrctHstrDtlDAO.getConsTrctHstrTodoList(pmParam);
    }

    /**
     * 사용자별 이관접수 미처리건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 사용자별 이관접수 미처리건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoConsTrctHstrCount() throws Exception {
        return consTrctHstrDtlDAO.getTodoConsTrctHstrCount();
    }

    /**
     * 상담을 이관할 리스트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이관이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCselTrnsList() throws Exception {
        return consTrctHstrDtlDAO.getCselTrnsList();
    }

    /**
     * 상담이관이력 정보를 검색한다.
     *
     * @param id 상담아이디
     * @param seq 순번
     * @return 상담이관이력 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getConsTrctHstr(String psId, int pnSeq) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("consno", psId);
        mParam.put("trct_sqno", pnSeq);
        return consTrctHstrDtlDAO.getConsTrctHstr(mParam);
    }

    /**
     * 상담이관이력 정보를 삭제한다.
     *
     * @param pmParam 상담이관이력 정보
     * @throws Exception
     */
    public int deleteConsTrctHstr(Map<String, Object> pmParam) throws Exception {
        return consTrctHstrDtlDAO.deleteConsTrctHstr(pmParam);
    }

}
