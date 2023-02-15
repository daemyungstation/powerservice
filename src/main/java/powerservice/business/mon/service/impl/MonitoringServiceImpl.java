/*
 * (@)# MonitoringServiceImpl.java
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

package powerservice.business.mon.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.mon.service.MonitoringService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 모니터링 정보를 조회한다.
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2014/07/03
 * @프로그램ID Monitoring
 */

@Service
public class MonitoringServiceImpl extends EgovAbstractServiceImpl implements MonitoringService {

    @Resource
    public MonitoringDAO MonitoringDAO;

    /**
     * 지연시간별 미처리 상담건수현황를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getPastTimeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getPastTimeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 상담건수에 할당된 팀를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getPastTimeTeamChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getPastTimeTeamChartList(pmParam);
    }


    /**
     * 지연시간별 미처리 상담건수에 할당된 상담원를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getPastTimeCounselorChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getPastTimeCounselorChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 상담건수의 대분류를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getPastTimeBigtypeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getPastTimeBigtypeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 상담건수의 대분류를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getPastTimeMidtypeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getPastTimeMidtypeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 VOC 건수현황를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getVocPastTimeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getVocPastTimeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 VOC 데이터의 유형를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getVocPastTimeTypeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getVocPastTimeTypeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 VOC 데이터의 담당자를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getVocPastTimeCounselorChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getVocPastTimeCounselorChartList(pmParam);
    }


    /**
     * 지연시간별 미처리 VOC 데이터의 담당자를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getVocPastTimeValueChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getVocPastTimeValueChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 콜백 건수현황를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getCallbackPastTimeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getCallbackPastTimeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 콜백 데이터의 팀를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getCallbackPastTimeTeamChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getCallbackPastTimeTeamChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 콜백 데이터의 상담사를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getCallbackPastTimeCounselorChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getCallbackPastTimeCounselorChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 콜백 데이터의 IVR를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getCallbackPastTimeIvrChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getCallbackPastTimeIvrChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 재통화 건수현황를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getResvcallPastTimeChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getResvcallPastTimeChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 재통화 데이터의 팀를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getRecallPastTimeTeamChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getRecallPastTimeTeamChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 재통화 데이터의 팀를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getRecallPastTimeMajorChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getRecallPastTimeMajorChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 재통화 데이터의 상담사를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getRecallPastTimeCounselorChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getRecallPastTimeCounselorChartList(pmParam);
    }

    /**
     * 지연시간별 미처리 재통화 데이터의 상담유형 중분류를 조회한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getRecallPastTimeMiddleChartList(Map<String, ?> pmParam) throws Exception {
        return MonitoringDAO.getRecallPastTimeMiddleChartList(pmParam);
    }

}
