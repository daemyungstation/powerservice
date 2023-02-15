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
import powerservice.business.dlw.service.DlwTvFormatService;
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
public class DlwTvFormatServiceImpl extends EgovAbstractServiceImpl implements DlwTvFormatService {

    @Resource
    public DlwTvFormatDAO dlwTvFormatDAO;

    /**
     * 쿠폰 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectTvFormatList(Map<String, Object> pmParam) throws Exception {
        return dlwTvFormatDAO.selectTvFormatList(pmParam);
    }


    /**
     * 캠페인 서브대상목록 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectCompaignList(Map<String, Object> pmParam) throws Exception {
        return dlwTvFormatDAO.selectCompaignList(pmParam);
    }


    /**
     * 쿠폰정보 MST 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectTvFormatMstDetail(Map<String, Object> pmParam) throws Exception {
        return dlwTvFormatDAO.selectTvFormatMstDetail(pmParam);
    }


    /**
     * 방송편성 DTL 상세 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectTvFormatDtlDetail(Map<String, Object> pmParam) throws Exception {
        return dlwTvFormatDAO.selectTvFormatDtlDetail(pmParam);
    }


    /**
     * 쿠폰정보 MST 저장
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void saveTvFormatMst(Map<String, Object> pmParam, DataSetMap srchInDs2) throws Exception {
        String msg = "";
        String msgL = "";

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        ParamUtils.addCenterParam(pmParam);
        pmParam.put("reg_id", oLoginUser.getUserid());
        pmParam.put("upd_id", oLoginUser.getUserid());
        CommonUtils.printLog(pmParam);

        String sFormatSeq = "";
        String sCouponDtlNo = "";
        int iGubun = 0;
        if ("insert".equals((String)pmParam.get("dml_mode"))) {
            sFormatSeq = dlwTvFormatDAO.selectKeyTvFormatMst(pmParam);

            CommonUtils.printLog(pmParam);

            pmParam.put("format_seq", sFormatSeq);
            iGubun = dlwTvFormatDAO.insertTvFormatMst(pmParam);

            if(iGubun == 1) {
                for (int i = 0; i < srchInDs2.size(); i++) {
                    hmParam = srchInDs2.get(i);

                    hmParam.put("format_seq", sFormatSeq);	//번호 셋팅
                    dlwTvFormatDAO.insertTvFormatDtl(hmParam); //디테일 삭제
                }

            }

        } else { //update
            iGubun = dlwTvFormatDAO.updateTvFormatMst(pmParam);

            if(iGubun == 1) {
                sFormatSeq = String.valueOf(pmParam.get("format_seq"));
                hmParam.put("format_seq", sFormatSeq);
                dlwTvFormatDAO.deleteTvFormatDtl(hmParam); //디테일 삭제

                for (int i = 0; i < srchInDs2.size(); i++) {
                    hmParam = srchInDs2.get(i);

                    hmParam.put("format_seq", sFormatSeq);	//번호 셋팅
                    dlwTvFormatDAO.insertTvFormatDtl(hmParam); //디테일 삭제
                }

            }

        }
    }


    /**
     * 쿠폰정보 MST 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteTvFormatMst(DataSetMap srchInDs) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
//        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        for (int i = 0; i < srchInDs.size(); i++) {
            hmParam = srchInDs.get(i);
            if ("1".equals((String)hmParam.get("chk")) ) {
                dlwTvFormatDAO.deleteTvFormatMst(hmParam); //MST 삭제

                dlwTvFormatDAO.deleteTvFormatDtl(hmParam); //DTL 삭제
            }
        }
    }


}
