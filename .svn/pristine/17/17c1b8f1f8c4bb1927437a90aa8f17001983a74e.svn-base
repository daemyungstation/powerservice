/*
 * (@)# DlwProdTrntServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/06/21
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwProdTrntService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상품 변환 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/06/21
 * @프로그램ID DlwProdTrnt
 */
@Service
public class DlwProdTrntServiceImpl extends EgovAbstractServiceImpl implements DlwProdTrntService {


    @Resource
    public DlwProdTrntDAO dlwProdTrntDAO;


    /**
     * 양도양수나 상품변경 공제 미신고건 확인
     *
     * @param pmParam 검색 조건
     * @return 양도양수나 상품변경 공제 미신고건 확인
     * @throws Exception
     */
    public String getChkProdTransDedAppYn(Map<String, ?> pmParam) throws Exception {
        return (String) dlwProdTrntDAO.getChkProdTransDedAppYn(pmParam);
    }

    /**
     * 상품 변환
     *
     * @param pmParam 검색 조건
     * @return 상품 변환
     * @throws Exception
     */
    public String updateTransMemProdAccnt(Map<String, Object> pmParam) throws Exception {
        String sRslMsg = "";
        String sDedAppYn = dlwProdTrntDAO.getDedAppYn(pmParam);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        if ("Y".equals(sDedAppYn)) {
            String sJobCl = "R";
            String sTempRecord 	= "CONTRACT_CANCELATION"
                                +"&cons_no="+CommonUtils.nvl(pmParam.get("bf_tr_accnt_no"))
                                +"&guar_no="+CommonUtils.nvl(pmParam.get("ded_no"))
                                +"&cons_nm="+CommonUtils.nvl(pmParam.get("mem_nm"))
                                +"&contr_can_dt=" + sdf.format(new Date())
                                +"&tot_adv_pay_amt=" + CommonUtils.nvl(pmParam.get("bf_tr_pay_amt"))
                                +"&contr_can_type="+"E"+"\n";
            pmParam.put("jobCl", sJobCl);
            pmParam.put("tempRecord", sTempRecord);
            pmParam.put("outSqlMsg", "");

            pmParam.put("result", "success");
 //           System.out.println("sTempRecord #####################===>"+sTempRecord);

        } else {
            pmParam.put("result", "success");
        }

/*
System.out.println("sDedAppYn #####################===>"+sDedAppYn);
System.out.println("bf_tr_accnt_no		#####################===>"+pmParam.get("bf_tr_accnt_no"));
System.out.println("bf_tr_prod_cd       #####################===>"+pmParam.get("bf_tr_prod_cd"));
System.out.println("af_tr_prod_cd       #####################===>"+pmParam.get("af_tr_prod_cd"));
System.out.println("bf_tr_pay_no        #####################===>"+pmParam.get("bf_tr_pay_no"));
System.out.println("bf_tr_pay_amt       #####################===>"+pmParam.get("bf_tr_pay_amt"));
System.out.println("af_tr_pay_no        #####################===>"+pmParam.get("af_tr_pay_no"));
System.out.println("af_tr_pay_amt       #####################===>"+pmParam.get("af_tr_pay_amt"));
System.out.println("bf_tr_allt_pay_no   #####################===>"+pmParam.get("bf_tr_allt_pay_no"));
System.out.println("bf_tr_allt_pay_amt  #####################===>"+pmParam.get("bf_tr_allt_pay_amt"));
System.out.println("af_tr_allt_pay_no   #####################===>"+pmParam.get("af_tr_allt_pay_no"));
System.out.println("af_tr_allt_pay_amt  #####################===>"+pmParam.get("af_tr_allt_pay_amt"));
System.out.println("bf_tr_add_pay_no    #####################===>"+pmParam.get("bf_tr_add_pay_no"));
System.out.println("bf_tr_add_pay_amt   #####################===>"+pmParam.get("bf_tr_add_pay_amt"));
System.out.println("af_tr_add_pay_no    #####################===>"+pmParam.get("af_tr_add_pay_no"));
System.out.println("af_tr_add_pay_amt   #####################===>"+pmParam.get("af_tr_add_pay_amt"));
System.out.println("bf_tr_dc_no         #####################===>"+pmParam.get("bf_tr_dc_no"));
System.out.println("af_tr_dc_no         #####################===>"+pmParam.get("af_tr_dc_no"));
System.out.println("rgsr_id             #####################===>"+pmParam.get("rgsr_id"));
System.out.println("amnd_id             #####################===>"+pmParam.get("amnd_id"));
System.out.println("prod_model_kind     #####################===>"+pmParam.get("prod_model_kind"));
System.out.println("prod_model_cd       #####################===>"+pmParam.get("prod_model_cd"));
System.out.println("prod_kind_nm        #####################===>"+pmParam.get("prod_kind_nm"));
*/
        if ("success".equals((String)pmParam.get("result"))) {
            dlwProdTrntDAO.updateTransMemProdAccnt(pmParam);
            sRslMsg = "success";
        } else {
            sRslMsg = "fail";
        }

//        System.out.println("outSqlMsg        #####################===>"+pmParam.get("outSqlMsg"));

 //       System.out.println("===================================>>>>> Success <<");
        CommonUtils.printLog(sRslMsg);

        return sRslMsg;
    }

    /**
     * 상품 변경 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 변경 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getProdTrntHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdTrntDAO.getProdTrntHstrCount(pmParam);
    }

    /**
     * 상품 변경 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 변경 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdTrntHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwProdTrntDAO.getProdTrntHstrList(pmParam);
    }

}
