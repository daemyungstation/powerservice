/*
 * (@)# ConsDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cons
 */
@Repository
public class ConsDAO extends EgovAbstractMapper {

    /**
     * 상담 정보를 등록한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int insertCons(Map<String, ?> pmParam) throws Exception {
        return insert("ConsMap.insertCons", pmParam);
    }

    /**
     * 상담 정보를 수정한다. (대명시퀀스 정보, 회원번호 수정)
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int updateCons(Map<String, ?> pmParam) throws Exception {
        return update("ConsMap.updateCons", pmParam);
    }

    /**
     * 상담 이관담당자 정보를 수정한다.
     *
     * @param pmParam 상담 이관담당자 정보
     * @throws Exception
     */
    public int updateConsTrctChpr(Map<String, ?> pmParam) throws Exception {
        return update("ConsMap.updateConsTrctChpr", pmParam);
    }

    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsMap.getConsCount", pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsList", pmParam);
    }
    
    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsMap.getConsCount2", pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsList2(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsList2", pmParam);
    }

    /**
     * 상담 그룹 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsGroup(Map<String, Object> pmParam) throws Exception {
        return selectList("ConsMap.getConsGroup", pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 상담 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCons(Map<String, ?> pmParam) throws Exception {
        return selectOne("ConsMap.getConsList", pmParam);
    }


    /**
     * 센터현황 건수를 조회한다
     *
     * @param pmParam 검색조건
     * @return 센터현황 건수
     * @throws Exception
     */
    public Map<String, Object> getConsChartCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("ConsMap.getConsChartCount", pmParam);
    }


    /**
     * 센터현황 차트 데이터를 조회한다
     *
     * @param pmParam 검색조건
     * @return 센터현황 차트 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getConsChartDailyList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsChartDailyList", pmParam);
    }
    public List<Map<String, Object>> getConsChartWeeklyList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsChartWeeklyList", pmParam);
    }
    public List<Map<String, Object>> getConsChartMonthlyList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsChartMonthlyList", pmParam);
    }
    public List<Map<String, Object>> getConsChart2WeeksList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsChart2WeeksList", pmParam);
    }


    /**
     * 관리자 메인화면 > 우수상담사 TOP5 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTop5List(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsMap.getConsTop5List", pmParam);
    }


    /**
     * 상담사 상담현황 > 상담사별 당일 상담건수를 조회한다
     *
     * @return 상담사별 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodayConsCount() throws Exception {
        return selectList("ConsMap.getTodayConsCount");
    }

    /**
     * 상담사 상담현황 > 상담사별 당월 상담건수를 조회한다
     *
     * @return 상담사별 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getThisMonthConsCount() throws Exception {
        return selectList("ConsMap.getThisMonthConsCount");
    }

    /**
     * 이관상담 처리완료 후 상담 정보를 수정한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int updateConsTrctconsCmpl(Map<String, ?> pmParam) throws Exception {
        return update("ConsMap.updateConsTrctconsCmpl", pmParam);
    }

    /**
     * 인우 상담내역 변경(TB_CONS)
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateYdysCons(Map<String, ?> pmParam) throws Exception {
        return update("ConsMap.updateYdysCons", pmParam);
    }

    /**
     * 인우 상담이력 변경(TB_CONS_HSTR)
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateYdysConsHstr(Map<String, ?> pmParam) throws Exception {
        return update("ConsMap.updateYdysConsHstr", pmParam);
    }
}
