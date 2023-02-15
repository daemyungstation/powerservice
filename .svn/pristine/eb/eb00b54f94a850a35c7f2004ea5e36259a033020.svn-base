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
import powerservice.business.dlw.service.DlwProdVasService;
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
public class DlwProdVasServiceImpl extends EgovAbstractServiceImpl implements DlwProdVasService {


    @Resource
    public DlwProdVasDAO dlwProdVasDAO;

    @Resource
    public DlwVatSvcDAO dlwVatSvcDAO;



    /**
     * 상품별 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdOptSvcMstList(Map<String, Object> pmParam) throws Exception {
        return dlwProdVasDAO.selectProdOptSvcMstList(pmParam);
    }

    /**
     * 상품코드 콤보 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdList(Map<String, Object> pmParam) throws Exception {
        return dlwProdVasDAO.selectProdList(pmParam);
    }

    /**
     * 부가서비스 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception {
        return dlwProdVasDAO.selectOptSvcList(pmParam);
    }


    /**
     * 부가서비스 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectDetailVas(Map<String, Object> pmParam) throws Exception {
        return dlwProdVasDAO.selectDetailVas(pmParam);
    }


    /**
     * 상품별 부가서비스 DTL 상세조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdOptSvcDtl(Map<String, Object> pmParam) throws Exception {
        return dlwProdVasDAO.selectProdOptSvcDtl(pmParam);
    }

    /**
     * 상품별 부가서비스 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String saveProdOptSvc(DataSetMap srchInDs1, DataSetMap srchInDs2) throws Exception {
        String msg = "";
        String msgL = "";

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        if (srchInDs1 != null && srchInDs1.size() > 0) {
            hmParam = srchInDs1.get(0);

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            ParamUtils.addCenterParam(hmParam);
            CommonUtils.printLog(hmParam);
        }

        String seq = "";
        if ("insert".equals((String)hmParam.get("mode"))) {

            //대명라이프웨이 회원만 insert
            hmParam.put("mem_cl", "L");

            seq = dlwProdVasDAO.selectKeyOptSvcMst(hmParam);
            hmParam.put("seq", seq);
            dlwProdVasDAO.insertProdOptSvcMst(hmParam);

            if (!"".equals(seq)){
                msg = "success";
            }else {
                msg = "error";
            }

/*
 * 			//라이프웨이 회원만 insert
            if ("Y".equals((String)hmParam.get("memClT"))) {
                hmParam.put("mem_cl", "T");
                seq = dlwProdVasDAO.insertProdOptSvcMst(hmParam);
                if (!"".equals(seq)){
                    msg = "success";
                }else {
                    msg = "error";
                }
            }

            //대명리조트 회원만 insert
            if ("Y".equals((String)hmParam.get("memClL"))) {
                hmParam.put("mem_cl", "L");
                seqL = dlwProdVasDAO.insertProdOptSvc(hmParam);
                if (!"".equals(seqL)){
                    msgL = "success";
                }else{
                    msgL = "error";
                }
            }
*/
        } else { //update
            seq = String.valueOf(hmParam.get("seq"));
            hmParam.put("seq", seq);
            hmParam.put("mem_cl", "L");
            hmParam.put("upd_man", oLoginUser.getUserid());
            if (dlwProdVasDAO.updateProdOptSvc(hmParam) > 0){
                msg = "success";
            } else {
                msg = "error";
            }
        }

        if ("success".equals(msg)) {
            dlwProdVasDAO.deleteProdOptSvcDtl(hmParam);

            for (int i = 0; i < srchInDs2.size(); i++) {
                hmParam = srchInDs2.get(i);
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("seq", seq);

                if ("1".equals((String)hmParam.get("chk")) ) {
                    dlwProdVasDAO.insertProdOptSvcDtl(hmParam);
                }
            }
        }
        return msg;

    }



    /**
     * 상품별 부가서비스 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteProdOptSvc(Map<String, Object> pmParam) throws Exception {
        dlwProdVasDAO.deleteProdOptSvc(pmParam); //마스터 삭제

        dlwProdVasDAO.deleteProdOptSvcDtl(pmParam); //마스터 상세 삭제
    }



    /**
     * 상품별 종류 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String saveOptSvcList(DataSetMap srchInDs) throws Exception {

        String msg = "error";

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        int rowType = 0;

        System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>> srchInDs.size()==>"+ srchInDs.size());

        for (int i = 0; i < srchInDs.size(); i++) {
            hmParam = srchInDs.get(i);
            rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

            System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>> rowType==>"+ rowType);
            System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>> DataSet.ROW_TYPE_UPDATED==>"+ DataSet.ROW_TYPE_UPDATED);
            System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>> DataSet.ROW_TYPE_INSERTED==>"+ DataSet.ROW_TYPE_INSERTED);
            System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>> DataSet.ROW_TYPE_DELETED==>"+ DataSet.ROW_TYPE_DELETED);

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            if (rowType == DataSet.ROW_TYPE_UPDATED) {
                System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                if (dlwProdVasDAO.updateOptSvc(hmParam) > 0) msg = "success";


                if ((!"0001".equals((String)hmParam.get("seq"))) && (!"0002".equals((String)hmParam.get("seq")))) {
                    if (("0014".equals((String)hmParam.get("seq"))) || ("0015".equals((String)hmParam.get("seq")))) {
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwVatSvcDAO.selectCrtNoPerDaySearch(hmParam) == null) {
                            hmParam.put("isu_no", "000");
                            dlwVatSvcDAO.insertCouponNoPerDayYMS(hmParam);
                        }else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no")))) {
                            String crtNo = (String)hmParam.get("crt_no");
                            if ((crtNo.length() > 3) || (crtNo.length() < 0)) {
                                msg = "err_len";
                                break;
                            }
                            dlwVatSvcDAO.updateOtherCrtNoPerDay1(hmParam);
                        }
                    }else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no")))){
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwVatSvcDAO.selectCrtNoSearch(hmParam) == null){
                            dlwProdVasDAO.insertCouponNoYMS(hmParam);
                        } else {
                            dlwProdVasDAO.updateOtherCrtNo1(hmParam);
                        }
                    }
                }

            }

            if (rowType == DataSet.ROW_TYPE_INSERTED) {
                System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                if (dlwProdVasDAO.insertOptSvc(hmParam) > 0) msg = "success";
            }


/*
            if (((String)hmParam.get("seq") != null) && (!"".equals((String)hmParam.get("seq")))) {
                if (dlwProdVasDAO.updateOptSvc(hmParam) > 0) msg = "success";

                if ((!"0001".equals((String)hmParam.get("seq"))) && (!"0002".equals((String)hmParam.get("seq")))) {
                    if (("0014".equals((String)hmParam.get("seq"))) || ("0015".equals((String)hmParam.get("seq")))) {
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwVatSvcDAO.selectCrtNoPerDaySearch(hmParam) == null) {
                            hmParam.put("isu_no", "000");
                            dlwVatSvcDAO.insertCouponNoPerDayYMS(hmParam);
                        }else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no")))) {
                            String crtNo = (String)hmParam.get("crt_no");
                            if ((crtNo.length() > 3) || (crtNo.length() < 0)) {
                                msg = "err_len";
                                break;
                            }
                            dlwVatSvcDAO.updateOtherCrtNoPerDay1(hmParam);
                        }
                    }else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no")))){
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwVatSvcDAO.selectCrtNoSearch(hmParam) == null){
                            dlwProdVasDAO.insertCouponNoYMS(hmParam);
                        } else {
                            dlwProdVasDAO.updateOtherCrtNo1(hmParam);
                        }
                    }
                }
            }else if (dlwProdVasDAO.insertOptSvc(hmParam) == 1) {
                msg = "success";
            }
*/
        }  //FOR LOOP END
        return msg;
    }

}
