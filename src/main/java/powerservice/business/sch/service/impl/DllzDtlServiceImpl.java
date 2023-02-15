/*
 * (@)# DllzDtlService.java
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

package powerservice.business.sch.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sch.service.DllzDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 근태내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID DllzDtl
 */
@Service
public class DllzDtlServiceImpl extends EgovAbstractServiceImpl implements DllzDtlService {

    @Resource
    public DllzDtlDAO dllzDtlDAO;

    /**
     * 근태내역 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 정보의 검색 수
     * @throws Exception
     */
    public int getDllzDtlCount(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.getDllzDtlCount(pmParam);
    }

    /**
     * 근태내역 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDllzDtlList(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.getDllzDtlList(pmParam);
    }

    /**
     * 근태내역 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 리스트
     * @throws Exception
     */
    public Map<String, Object> getDllzDtl(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.getDllzDtl(pmParam);
    }

    /**
     * 근태 정보를 등록한다.
     *
     * @pmParam pmParam 근태 정보
     * @throws Exception
     */
    public int insertDllzDtl(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.insertDllzDtl(pmParam);
    }

    /**
     * 근태 정보를 수정한다.
     *
     * @pmParam pmParam 근태 정보
     * @throws Exception
     */
    public int updateDllzDtl(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.updateDllzDtl(pmParam);
    }

    /**
     * 근태내역 정보를 삭제한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public int deleteDllzDtl(Map<String, ?> pmParam) throws Exception {
        return dllzDtlDAO.deleteDllzDtl(pmParam);
    }


    /**
     * 로그인 근태내역 정보를 등록한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public void insertDllzDtlForLogin(Map<String, Object> pmParam) throws Exception {
        // 01. 오늘 등록된 근태정보가 있는지 확인
        pmParam.put("today_yn", "Y");
        int nCnt = dllzDtlDAO.getDllzDtlCount(pmParam);

        if (nCnt == 0) {
            // 02. 스케줄이 있으면 근무조, 근무상태 정보 셋팅
            Map<String, Object> mScdlWrkTyp = dllzDtlDAO.getScdlWrkTypForDllz(pmParam);
            if (mScdlWrkTyp != null) {
                pmParam.put("wrk_typ_cd", mScdlWrkTyp.get("wrk_typ_cd")); // 근무조코드
                pmParam.put("wrk_typ_nm", mScdlWrkTyp.get("wrk_typ_nm")); // 근무조명
                pmParam.put("bswr_stt_hr", mScdlWrkTyp.get("bswr_stt_hr")); // 근무시작시간
            }

            // 03. 로그인 근태내역 등록
            dllzDtlDAO.insertDllzDtlForLogin(pmParam);
        }
    }

    /**
     * 로그아웃 근태내역 정보를 등록한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public void updateDllzDtlForLogout(Map<String, Object> pmParam) throws Exception {
        // 01. 스케줄이 있으면 근무조, 근무상태 정보 셋팅
        // 근무 스케쥴배정의 근무조가 아닌 근태관리 현황의 근무조 설정
        //Map<String, Object> mScdlWrkTyp = dllzDtlDAO.getScdlWrkTypForDllz(pmParam);
        Map<String, Object> mScdlWrkTyp = dllzDtlDAO.getDllzWrkTyp(pmParam);
        if (mScdlWrkTyp != null) {
            pmParam.put("wrk_typ_cd", mScdlWrkTyp.get("wrk_typ_cd")); // 근무조코드
            pmParam.put("wrk_typ_nm", mScdlWrkTyp.get("wrk_typ_nm")); // 근무조명
            pmParam.put("bswr_end_hr", mScdlWrkTyp.get("bswr_end_hr")); // 근무종료시간
        }

        // 02. 로그아웃 근태정보 등록
        dllzDtlDAO.updateDllzDtlForLogout(pmParam);
    }

}
