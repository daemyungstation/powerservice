/*
 * (@)# EvlpInfoServiceImpl.java
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

package powerservice.business.edc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.edc.service.EdctCrsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가자 정보를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvlpInfo
 */
@Service
public class EdctCrsServiceImpl extends EgovAbstractServiceImpl implements EdctCrsService {

    @Resource
    public EdctCrsDAO edctCrsDAO;

    /**
     * 교육평가 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가 정보의 검색 건수
     * @throws Exception
     */
    public int getEdctCrsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctCrsDAO.getEdctCrsCount(pmParam);
    }

    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEdctCrsList(Map<String, ?> pmParam) throws Exception {
        return edctCrsDAO.getEdctCrsList(pmParam);
    }

    /**
     * 교육평가 정보를 등록한다.
     *
     * @pmParam pmParam 교육평가 등록
     * @throws Exception
     */
    public String insertEdctCrs(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = edctCrsDAO.insertEdctCrs(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("edct_crs_id");
        }
        return sKey;
    }

    /**
     * 교육평가 정보를 복사한다.
     *
     * @pmParam pmParam 교육평가 복사
     * @throws Exception
     */
    public String copyEdctCrs(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = edctCrsDAO.copyEdctCrs(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("edct_crs_id");
        }
        return sKey;
    }

    /**
     * 교육평가 정보를 수정한다.
     *
     * @pmParam pmParam FAQ 정보
     * @throws Exception
     */
    public int updateEdctCrs(Map<String, Object> pmParam) throws Exception {
        return edctCrsDAO.updateEdctCrs(pmParam);
    }

    /**
     * 교육평가를 검색한다.
     *
     * @pmParam edctcrsid 교육평가 ID
     * @return 교육평가 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEdctCrs(String edctcrsId) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("edct_crs_id", edctcrsId);

        return edctCrsDAO.getEdctCrs(pmParam);
    }

    /**
     * 교육평가를 삭제한다.
     *
     * @pmParam pmParam 교육평가
     * @throws Exception
     */
    public int deleteEdctCrs(Map<String, Object> pmParam) throws Exception {
        return edctCrsDAO.deleteEdctCrs(pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보의 검색 건수
     * @throws Exception
     */
    public int getEduResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctCrsDAO.eduresultCount(pmParam);
    }
    /**
     * 월간 교육훈련 결과(상담사별) 정보를 검색 한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctCrsResult(Map<String, ?> pmParam) throws Exception {
        return edctCrsDAO.edctCrsResult(pmParam);
    }

    /**
     * 사용자 교육 정보의 검색 수를 반환한다. -  MYPAGE
     *
     * @pmParam pmParam 검색 조건
     * @return 교육 정보의 검색 수
     * @throws Exception
     */
    public int getMyPageEdctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctCrsDAO.getMyPageEdctCount(pmParam);
    }

    /**
     * 사용자 교육 정보(목록)를 검색한다. -  MYPAGE
     *
     * @pmParam pmParam 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getMyPageEdctList(Map<String, ?> pmParam) throws Exception {
        return edctCrsDAO.getMyPageEdctList(pmParam);
    }

}
