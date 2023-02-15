/*
 * (@)# DlwProdServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwPayCloseService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Service
public class DlwPayCloseServiceImpl extends EgovAbstractServiceImpl implements DlwPayCloseService {


    @Resource
    public DlwPayCloseDAO dlwPayCloseDAO;


    /**
     * 부가서비스 발급현황 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayCloseList(Map<String, Object> pmParam) throws Exception {
        return dlwPayCloseDAO.selectPayCloseList(pmParam);
    }


    /**
     * 전체 입금마감 데이타 수동생성
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public void execPayDlTotProc(Map<String, Object> pmParam) throws Exception{
        String outsqlcd = "";
        String outsqlmg = "";

        if ("01".equals((String)pmParam.get("dl_type"))) //입금마감
        {
            pmParam.put("outsqlcd", "");
            pmParam.put("outsqlmg", "");

            dlwPayCloseDAO.execPayDlProcTotMst(pmParam);
            
            /*
            outsqlcd = String.valueOf(pmParam.get("outsqlcd"));
            outsqlmg = String.valueOf(pmParam.get("outsqlmg"));

            if(!outsqlcd.equals("00")){
                //Exception
                throw new Exception (outsqlcd +":"+ outsqlmg);
            }else{
                dlwPayCloseDAO.execPayDlProcTotDtl(pmParam);
                outsqlcd = String.valueOf(pmParam.get("outsqlcd"));
                outsqlmg = String.valueOf(pmParam.get("outsqlmg"));
                if(!outsqlcd.equals("00")){
                    throw new Exception (outsqlcd +":"+ outsqlmg);
                }
            }
            */

        }else{ //행사마감
            dlwPayCloseDAO.execPayDlProcTotMst(pmParam);
        }
    }


    /**
     * 전체 마감 해지 처리
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public void execPayDlTotCancelProc(Map<String, Object> pmParam) throws Exception{
        if ("01".equals((String)pmParam.get("dl_type"))) //입금마감 해지
        {
            dlwPayCloseDAO.execPayDlTotCancelMstProc(pmParam);
            dlwPayCloseDAO.execPayDlTotCancelDtlProc(pmParam);
        }else{ //행사마감 해지
            dlwPayCloseDAO.execPayDlTotCancelMstProc(pmParam);
        }
    }


    /**
     * 기준년월 중복 체크
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public String selectDupChk(Map<String, Object> pmParam) throws Exception {
        return dlwPayCloseDAO.selectDupChk(pmParam);
    }


}
