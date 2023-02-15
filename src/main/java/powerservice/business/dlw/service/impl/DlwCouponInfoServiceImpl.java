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
import powerservice.business.dlw.service.DlwCouponInfoService;
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
public class DlwCouponInfoServiceImpl extends EgovAbstractServiceImpl implements DlwCouponInfoService {

    @Resource
    public DlwCouponInfoDAO dlwCouponInfoDAO;

    /**
     * 쿠폰 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponInfoList(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectCouponInfoList(pmParam);
    }


    /**
     * 쿠폰 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponStatList(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectCouponStatList(pmParam);
    }


    /**
     * 쿠폰정보 이력을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponHist(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectCouponHist(pmParam);
    }


    /**
     * 쿠폰정보 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectCouponDetail(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectCouponDetail(pmParam);
    }


    /**
     * 쿠폰종류 별 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectCouponTpSearch(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectCouponTpSearch(pmParam);
    }


    /**
     * 쿠폰번호 중복 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectDupCouponNo(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectDupCouponNo(pmParam);
    }


    /**
     * 쿠폰정보 MST 저장
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void saveCouponInfoMst(Map<String, Object> pmParam) throws Exception {
        String msg = "";
        String msgL = "";

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        ParamUtils.addCenterParam(pmParam);
        pmParam.put("reg_id", oLoginUser.getUserid());
        pmParam.put("upd_id", oLoginUser.getUserid());
        CommonUtils.printLog(pmParam);

        String sCouponInhNo = "";
        String sCouponDtlNo = "";
        int iGubun = 0;
        if ("insert".equals((String)pmParam.get("dml_mode"))) {
            sCouponInhNo = dlwCouponInfoDAO.selectKeyCouponMst(pmParam);

            pmParam.put("coupon_inh_no", sCouponInhNo);
            iGubun = dlwCouponInfoDAO.insertCouponInfoMst(pmParam);
            CommonUtils.printLog("iGubun $$$$$$$$$$$$$$$$$$$$$$$>"+iGubun);

            if(iGubun == 1) {
                pmParam.put("dml_mode", "insert");

                sCouponDtlNo = selectKeyCouponDtl(pmParam);
                pmParam.put("coupon_dtl_no", sCouponDtlNo);	//쿠폰상세 번호 셋팅
                pmParam.put("note_cd", "08"); //최초입력
                pmParam.put("use_yn", "N"); //사용유무

                saveCouponInfoDtl(pmParam);
            }


        } else { //update
            dlwCouponInfoDAO.updateCouponInfoMst(pmParam);
        }
    }


    /**
     * 쿠폰정보 DTL 저장
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void saveCouponInfoDtl(Map<String, Object> pmParam) throws Exception {
        String msg = "";
        String msgL = "";

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        ParamUtils.addCenterParam(pmParam);
        pmParam.put("reg_id", oLoginUser.getUserid());
        pmParam.put("upd_id", oLoginUser.getUserid());
        CommonUtils.printLog(pmParam);

//        CommonUtils.printLog("mode$$$$$$$$$$$$$$$$$$$$$$$>"+(String)pmParam.get("mode"));


        String seq = "";
        if ("insert".equals((String)pmParam.get("dml_mode"))) {

//          CommonUtils.printLog("sCouponDtlNo #########################> "+sCouponDtlNo);

            dlwCouponInfoDAO.insertCouponInfoDtl(pmParam);

            if (!"".equals(seq)){
                msg = "success";
            }else {
                msg = "error";
            }

        } else { //update
            dlwCouponInfoDAO.updateCouponInfoDtl(pmParam);
        }
    }

    /**
     * 쿠폰정보 MST 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteCouponInfoMst(DataSetMap srchInDs) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
//        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        for (int i = 0; i < srchInDs.size(); i++) {
            hmParam = srchInDs.get(i);
            if ("1".equals((String)hmParam.get("chk")) ) {
                dlwCouponInfoDAO.deleteCouponInfoMst(hmParam); //마스터 삭제
            }
        }
    }

    /**
     * 쿠폰 사용유무 MST update
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCouponInfoMstUseYn(Map<String, Object> pmParam) throws Exception {
        dlwCouponInfoDAO.updateCouponInfoMstUseYn(pmParam);
    }


    /**
     * 쿠폰 사용유무 DTL update
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCouponInfoDtlUseYn(Map<String, Object> pmParam) throws Exception {
        dlwCouponInfoDAO.updateCouponInfoDtlUseYn(pmParam);
    }


    /**
     * 쿠폰정보 DTL 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteCouponInfoDtl(Map<String, Object> pmParam) throws Exception {
        dlwCouponInfoDAO.deleteCouponInfoDtl(pmParam);
    }


    /**
     * 쿠폰정보 DTL 키 select
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectKeyCouponDtl(Map<String, Object> pmParam) throws Exception {
        return dlwCouponInfoDAO.selectKeyCouponDtl(pmParam);
    }
}
