/*
 * (@)# TrgtCustAlctDao.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인대상고객할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID TrgtCustAlct
 */

@Repository
public class TrgtCustAlctDAO extends EgovAbstractMapper {

    /**
     * 캠페인 대상고객에 상담사를 할당한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustAssign(Map<String, Object> pmParam) throws Exception {
        return insert("TrgtCustAlctMap.insertTgtCustAlct", pmParam);
    }

    public List<Map<String, Object>> getTgtCustNonAlctList(Map<String, Object> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTgtCustNonAlctList", pmParam);
    }

    public int insertTgtCustAlctFromSystem(Map<String, Object> mParam) throws Exception {
        return insert("TrgtCustAlctMap.insertTgtCustAlctFromSystem", mParam);
    }



    /**
     * 캠페인 대상고객 할당을 회수한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int deleteTgtcustassign(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustAlctMap.deleteTgtCustAlct", pmParam);
    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTrgtCustAlctCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctCount", pmParam);
    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getAlctCount", pmParam);
    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTrgtAlctCustCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtAlctCustCount", pmParam);
    }


    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTrgtCustAlctList", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctList", pmParam);
    }    
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtAlctCustList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtAlctCustList", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtAlctCustList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTrgtAlctCustList", pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getTgtCustAlctCountByCust(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTgtCustAlctCountByCust", pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctListByCust(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTrgtCustAlctListByCust", pmParam);
    }

    /**
     * 캠페인 결과정보를 수정한다.(ajax)
     *
     * @param pmParam 캠페인 결과정보
     * @throws Exception
     */
    public int updateCmpgResult(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateCmpgResult", pmParam);
    }

    public int updateTrgtCustByCustId(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustMap.updateTrgtCustByCustId", pmParam);
    }

    /**
     * 캠페인 결과정보를 조회한다.(ajax)
     *
     * @param pmParam 검색 조건
     * @return 캠페인 결과정보
     * @throws Exception
     */
    public Map<String, Object> getCmpgResult(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrgtCustAlctMap.getCmpgResult", pmParam);
    }

    /**
     * 캠페인별 배정 검색 수를 반환한다. (사용안하는 듯)
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getTgtCustAssignCountByCampaign(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTgtCustAssignCountByCampaign", pmParam);
    }

    /**
     * 캠페인별 배정 정보를 검색한다. (사용안하는 듯)
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTgtCustAssignListByCampaign(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTgtCustAssignListByCampaign", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 할당정보 Key (TRGT_LIST_ID,CNSR_ID,CMPG_ID,TRGT_CUST_DTPT_ID)
     * @return 할당정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrgtCustAlct(Map<String, Object> pmParam) throws Exception {
        return selectOne("TrgtCustAlctMap.getTrgtCustAlctList", pmParam);
    }

    /**
     * 캠페인 대상리스트 고객아이디를 수정한다.(ajax)
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateTrgtCustCustId(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateTrgtCustCustId", pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getCustHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getCustHstrCount", pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCustHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getCustHstrList", pmParam);
    }

    /**
     * 대상고객 처리상태를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustStatList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTrgtCustStatList", pmParam);
    }


    /**
     * 캠페인 대상고객의 상담사를 변경
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateChangeCnsr(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateChangeCnsr", pmParam);
    }

    /**
     * CtiId를 업데이트한다.
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public int updateCtiId(Map<String, Object> pmParam) throws Exception {
         return update("TrgtCustAlctMap.updateCtiId", pmParam);
    }



    /**
     * 캠페인 처리현황(상담사별)의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 처리현황(상담사별)의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustAlctByCnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getTrgtCustAlctByCnsrCount", pmParam);
    }

    /**
     * 캠페인 처리현황(상담사별)을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 처리현황(상담사별)
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctByCnsrList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getTrgtCustAlctByCnsrList", pmParam);
    }

    /**
     * 캠페인 중복할당 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 중복할당 검색 수를 반환한다.
     * @throws Exception
     */
    public int getDupAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getDupAlctCount", pmParam);
    }

    /**
     * 캠페인 중복할당을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 중복할당을 조회한다.
     * @throws Exception
     */
    public List<Map<String, Object>> getDupAlctList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getDupAlctList", pmParam);
    }

    /**
     * 캠페인 중복할당을 삭제한다. 사용
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public int deleteDupAlct(Map<String, Object> pmParam) throws Exception {
        return delete("TrgtCustAlctMap.deleteDupAlct", pmParam); // 캠페인 정보 삭제
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctDirectCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctDirectCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctDirectList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctDirectList", pmParam);
    }
    
    /**
     * 캠페인 대상고객의 상담사를 변경
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateChangeDirectCnsr(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateChangeDirectCnsr", pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 할당한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustDirectAlct(Map<String, Object> pmParam) throws Exception {
        return insert("TrgtCustAlctMap.insertTgtCustDirectAlct", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainDirectCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainDirectCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainDirectList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainDirectList", pmParam);
    }
    
    /**
     * 다이렉트 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateDirectSessionClose", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctUplusCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctUplusCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctUplusList", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctUplusPopList", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList2(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctUplusPopList2", pmParam);
    }
    
    /**
     * 다이렉트 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateUplusSessionClose(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateUplusSessionClose", pmParam);
    }
    
    /**
     * 캠페인 대상고객의 상담사를 변경
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateChangeUplusCnsr(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateChangeUplusCnsr", pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 할당한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustUplusAlct(Map<String, Object> pmParam) throws Exception {
        return insert("TrgtCustAlctMap.insertTgtCustUplusAlct", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainUplusCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainUplusCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainUplusList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainUplusList", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMangiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctMangiCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMangilist(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctMangilist", pmParam);
    }
    
    /**
     * 캠페인 대상고객의 상담사를 변경
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateChangeMangiCnsr(Map<String, ?> pmParam) throws Exception {
        return update("TrgtCustAlctMap.updateChangeMangiCnsr", pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 할당한다.
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustMangiAlct(Map<String, Object> pmParam) throws Exception {
        return insert("TrgtCustAlctMap.insertTgtCustMangiAlct", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainMangiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainMangiCount", pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainMangiList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtCustAlctMap.getNewTypeTrgtCustAlctMainMangiList", pmParam);
    }
}
