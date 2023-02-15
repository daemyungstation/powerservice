/*
 * (@)# BswrDmndDtlService.java
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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.BswrDmndDtlService;
import powerservice.core.util.SessionUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 업무요청내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BswrDmndDtl
 */
@Service
public class BswrDmndDtlServiceImpl extends EgovAbstractServiceImpl implements BswrDmndDtlService {

    @Resource
    public BswrDmndDtlDAO bswrDmndDtlDAO;

    @Resource
    public BswrDmndDtlHstrDAO bswrDmndDtlHstrDAO;

    @Resource
    public ConsDAO consDAO;

    @Resource
    public ConsHstrDAO consHstrDAO;

    /**
     * 업무요청내역 정보를 등록한다.
     *
     * @param param 업무요청내역 정보
     * @throws Exception
     */
    public int insertBswrDmndDtl(Map<String, ?> pmParam) throws Exception {
        int nResult = bswrDmndDtlDAO.insertBswrDmndDtl(pmParam);
        if (nResult > 0) {
            bswrDmndDtlHstrDAO.insertBswrDmndDtlHstr(pmParam);
        }
        return nResult;
    }

    /**
     * 이관업무 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관업무 처리현황 목록
     * @throws Exception
     */
    public Map<String, Object> getDpmsReslDsps(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getDpmsReslDsps(pmParam);
    }

    /**
     * 이관대상목록 정보의 검색 건을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관대상목록 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctConsCount(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsCount(pmParam);
    }

    /**
     * 이관대상목록 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관대상목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsList(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsList(pmParam);
    }

    /**
     * 이관상담 이력정보의 검색 건을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctConsHstrCount(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsHstrCount(pmParam);
    }

    /**
     * 이관상담 이력정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담이력
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsHstr(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsHstr(pmParam);
    }

    /**
     * 이관상담 정보를 저장한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 저장 결과
     * @throws Exception
     */
    public int saveBswrDmndDsps(Map<String, Object> pmParam) throws Exception {
        String trct_chpr_fg = StringUtils.defaultString((String)pmParam.get("trct_chpr_fg"));
        String bswr_dmnd_stat_cd = StringUtils.defaultString((String)pmParam.get("bswr_dmnd_stat_cd"));
        String trct_cons_atmt_cmpl_yn = StringUtils.defaultString((String)pmParam.get("trct_cons_atmt_cmpl_yn"));
        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        if ("S".equals(trct_chpr_fg)) {
            pmParam.put("trct_chpr_id", oUser.getUserid());
        }
        int nResult = 0;

        if (bswrDmndDtlDAO.getBswrDmndDspsCount(pmParam) > 0) {
            nResult = bswrDmndDtlDAO.updateBswrDmndDsps(pmParam);
        }else{
            if("S".equals(trct_chpr_fg)){
                return -1;
            }else{
                return -3;
            }
        }

        if (nResult > 0) {
            bswrDmndDtlHstrDAO.insertBswrDmndDtlHstr(pmParam);
        }

        // 상담자동완료 체크 + 이관업무 처리완료, 처리불가시 상담데이터 처리완료
        if ("".equals(trct_chpr_fg) && "Y".equals(trct_cons_atmt_cmpl_yn) && ("30".equals(bswr_dmnd_stat_cd) || "40".equals(bswr_dmnd_stat_cd))) {
            pmParam.put("amnd_id", oUser.getUserid());
            int nConsResult = consDAO.updateConsTrctconsCmpl(pmParam);
            if (nConsResult > 0) {
                consHstrDAO.insertConsHstr(pmParam);
            }
        }

        return nResult;
    }

    /**
     * 이관업무 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관업무 처리현황 목록
     * @throws Exception
     */
    public Map<String, Object> getTrctCons(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctCons(pmParam);
    }

    /**
     * 이관상담 정보를 삭제한다.
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int deleteBswrDmnd(Map<String, ?> pmParam) throws Exception {
        int nResult = 0;
        nResult = bswrDmndDtlDAO.deleteBswrDmnd(pmParam);
        if (nResult > 0) {
            bswrDmndDtlHstrDAO.deleteBswrDmndDtlHstr(pmParam);
        }
        return nResult;
    }

    /**
     * 이관상담 차트 데이터를 조회한다
     *
     * @param pmParam 검색조건
     * @return 센터현황 차트 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsChartWeeksList(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsChartWeeksList(pmParam);
    }

    /**
     * 해당 이관상담의 담당자를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담의 담당자
     * @throws Exception
     */
    public Map<String, Object> checkTrctChpr(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.checkTrctChpr(pmParam);
    }

    /**
     * 사용자별 이관접수 미처리건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 사용자별 이관접수 미처리건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoConsTrctHstrCount() throws Exception {
        return bswrDmndDtlDAO.getTodoConsTrctHstrCount();
    }

    /**
     * 이관상담 처리상태 정보의 검색 건을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 처리상태 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctConsStatCount(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsStatCount(pmParam);
    }

    /**
     * 이관상담 처리상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 처리상태
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsStatList(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlDAO.getTrctConsStatList(pmParam);
    }

    /**
     * 이관상담_이관박스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 _이관박스 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTrctBoxChprCount() throws Exception {
        return bswrDmndDtlDAO.getConsTrctBoxChprCount();
    }
}
