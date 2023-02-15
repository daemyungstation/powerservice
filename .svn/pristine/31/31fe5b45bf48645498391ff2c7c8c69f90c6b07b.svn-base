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

import powerservice.business.cns.service.ConsService;
import powerservice.business.dlw.service.DlwVatSvcService;
import powerservice.business.dlw.service.DlwCouponInfoService;
import powerservice.business.sys.service.BasVlService;
import powerservice.core.util.ParamUtils;
import powerservice.common.util.CommonUtils;

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
public class DlwVatSvcServiceImpl extends EgovAbstractServiceImpl implements DlwVatSvcService {


    @Resource
    public DlwVatSvcDAO dlwVatSvcDAO;

    @Resource
    private ConsService consService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private DlwCouponInfoService dlwCouponInfoService;



    /**
     * 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getVatSvcList(Map<String, Object> pmParam) throws Exception {
        return dlwVatSvcDAO.getVatSvcList(pmParam);
    }

    /**
     * 우편번호 업로드 관련 체크
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public Map<String, Object> selectSvcExceptIssueHist(Map<String, Object> pmParam) throws Exception {
        return dlwVatSvcDAO.selectSvcExceptIssueHist(pmParam);
    }


    /**
     * 부가서비스 발급이력관리 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        dlwVatSvcDAO.insertSvcIsuHist(pmParam);
    }

    /**
     * 상담내역 Detail 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertConsulDetail(Map<String, Object> pmParam) throws Exception {
        dlwVatSvcDAO.insertConsulDetail(pmParam);
    }

    /**
     * 상담내역 마스터 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertConsulMng(Map<String, Object> pmParam) throws Exception {
        dlwVatSvcDAO.insertConsulMng(pmParam);
    }


    /**
     * 쿠폰 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        dlwVatSvcDAO.deleteSvcIsuHist(pmParam);
    }


    /**
     * 부가서비스무효화
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void vatSvcInvalid(Map<String, Object> pmParam) throws Exception {
        dlwVatSvcDAO.updateVatSvcInvalid(pmParam);
//        dlwVatSvcDAO.insertVatSvcInvalid(pmParam);

    }


    /**
     * 엑셀업로드 오류건 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertExcelUploadErr(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> tempMap = new HashMap<String, Object>();

        if ("MMS".equals((String)pmParam.get("svc_cl")))
        {
            tempMap.put("err_cl", "MS" );
        }else {
            tempMap.put("err_cl", "SV" );
        }

        tempMap.put("err_val1", pmParam.get("accnt_no") );
        tempMap.put("err_text", "회원정보-부가서비스 등록되지 않음" );
        tempMap.put("reg_man", pmParam.get("rgsr_id") );

        dlwVatSvcDAO.insertExcelUploadErr(pmParam);
    }



    /**
     * 부가서비스 등록 중복 체크.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectDupIsuNo(Map<String, Object> pmParam) throws Exception {
        String result = "";
        result = dlwVatSvcDAO.selectDupIsuNo(pmParam);
        if ("F".equals(result)) {
            result = "duplication";
        }
        return result;
    }


    /**
     * 쿠폰정보 업로드
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String uploadExcelSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        List<Map<String, Object>> svcList = dlwVatSvcDAO.selectSvcList(pmParam);
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> svcHm = new HashMap<String, Object>();

        for (int i = 0; i < svcList.size(); i++) {
            hmParam = svcList.get(i);
            svcHm.put("seq", (String)hmParam.get("seq"));
            svcHm.put("svc_nm", (String)hmParam.get("svc_nm"));
        }

        String msg = "error";
        String seq = "";

        List<Map<String, Object>> accntList = dlwVatSvcDAO.selectMemProdAccntSvc(pmParam);
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, Object> tempMap = null;
        Map<String, Object> selectKeyMap = null;

        for (int i = 0; i < accntList.size(); i++) {
            hmParam2 = accntList.get(i);
        }
        seq = String.valueOf(hmParam2.get("seq"));

        if (!"".equals( seq   )  ) {

            pmParam.put("seq", seq);


            if ("MMS".equals((String)hmParam2.get("svc_cl") )    ) {
                dlwVatSvcDAO.deleteSvcIsuHist(hmParam2);
            }

/*
            System.out.println("accnt_no ######################>:"+pmParam.get("accnt_no"));
            System.out.println("seq ######################>:"+pmParam.get("seq"));
            System.out.println("optsvc_seq ######################>:"+pmParam.get("optsvc_seq"));
            System.out.println("isu_dt ######################>:"+pmParam.get("isu_dt"));
            System.out.println("isu_no ######################>:"+pmParam.get("isu_no"));
            System.out.println("invoice_no ######################>:"+pmParam.get("invoice_no"));
            System.out.println("addressee ######################>:"+pmParam.get("addressee"));
            System.out.println("send_dt ######################>:"+pmParam.get("send_dt"));
            System.out.println("addr_note ######################>:"+pmParam.get("addr_note"));
            System.out.println("no ######################>:"+pmParam.get("no"));
            System.out.println("rgsr_id ######################>:"+pmParam.get("rgsr_id"));
            System.out.println("mem_no ######################>:"+hmParam2.get("mem_no"));
*/
            String sMemMo = "";
            if (!"".equals(dlwVatSvcDAO.insertSvcIsuHist(pmParam)))
            {

                selectKeyMap = new HashMap<String, Object>();

                pmParam.put("mem_no", hmParam2.get("mem_no") );


                hmParam.put("cnsl_cd", "01");
                hmParam.put("cntr_cd", pmParam.get("cntr_cd"));

                hmParam.put("cons_typ1_cd", "CT01020000"); //상담유형1(상품관리)
                hmParam.put("cons_typ2_cd", "CT01020200"); //상담유형2(부가서비스)
                hmParam.put("cons_typ3_cd", "CT01020201"); //상담유형3(전후방서비스)

                hmParam.put("cnsl_man", pmParam.get("rgsr_id"));

                hmParam.put("consno_sqno", "1");                       		// 순번
                hmParam.put("actp_id", pmParam.get("rgsr_id"));        		// 접수자ID
                hmParam.put("cons_stat_cd", "30");                     		// 처리상태(처리완료)
                hmParam.put("cons_dspsmddl_dtpt_cd", "10");            		// 처리방법(일반상담)
                hmParam.put("rsps_dept_cd", pmParam.get("rsps_dept_cd"));   // 담당부서
                hmParam.put("chpr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("accnt_no", pmParam.get("accnt_no"));     		// 회원번호
                hmParam.put("mem_no", pmParam.get("mem_no"));        		// 회원 고유번호
                hmParam.put("rgsr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("amnd_id", pmParam.get("rgsr_id"));        		// 담당자ID

                hmParam.put("acpg_chnl_cd", "99");   						// 접수채널 - 기타
                hmParam.put("cons_memo_cntn", sMemMo);   				// 상담메모
                hmParam.put("note", pmParam.get("isu_no"));   // 비고:쿠폰번호

                //CONS_MEMO_CNTN : 상담메모

//                consService.insertCons(hmParam);

                msg = "success";
            }
        } else {
            String result_msg = "";
            result_msg = "에러=> 회원번호:"+(String)pmParam.get("accnt_no")+",쿠폰번호:"+(String)pmParam.get("isu_no")+",서비스코드:"+(String)pmParam.get("optsvc_seq");

            pmParam.put("err_cl", "SV");
            pmParam.put("err_val1", pmParam.get("accnt_no"));
            pmParam.put("err_val2", pmParam.get("isu_no"));
            pmParam.put("err_val3", pmParam.get("optsvc_seq"));

            pmParam.put("err_text", result_msg);

            this.insertExcelUploadErr(pmParam);

            throw new Exception (result_msg);
        }

        return msg;
    }



    /**
     * 부가서비스(대명투어몰(10만원)만) 발급
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String regSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();


        String msg = "error";
        String dupNo = "";
        int succCnt = 0;

        hmParam.put("optsvc_seq", pmParam.get("opt_svc_cd"));
        hmParam.put("accnt_no", pmParam.get("accnt_no"));
        hmParam.put("seq", pmParam.get("seq"));
        hmParam.put("no_crt_yn", pmParam.get("no_crt_yn"));
        hmParam.put("reg_man", pmParam.get("reg_man"));
        hmParam.put("opt_svc_cd", pmParam.get("opt_svc_cd"));

        int isuCnt = Integer.valueOf(String.valueOf(pmParam.get("isu_cnt"))).intValue();

        Map<String, Object> selectKeyMap = null;
        Map<String, Object> tempMap = null;
        for (int i = 0; i < isuCnt; i++) {
            if ("Y".equals((String)hmParam.get("no_crt_yn"))) {
                if (("0001".equals((String)hmParam.get("opt_svc_cd"))) || ("0002".equals((String)hmParam.get("opt_svc_cd"))) || ("0003".equals((String)hmParam.get("opt_svc_cd")))) {

                    hmParam.put("crt_no", dlwVatSvcDAO.selectCrtNo(hmParam));


                    dupNo = dlwVatSvcDAO.dupNoChk(hmParam);
                    if ((!"".equals(dupNo)) && (dupNo != null))
                    {
                        msg = "dupNo";
                        return msg;
                    }

                    if ((!"".equals((String)hmParam.get("isu_no"))) && (dlwVatSvcDAO.updateCrtNo(hmParam) <= 0)) {
                        msg = "error";
                        break;
                    }
                }else if (("0014".equals((String)pmParam.get("opt_svc_cd"))) || ("0015".equals((String)pmParam.get("opt_svc_cd")))) {

                    if (dlwVatSvcDAO.selectCrtNoPerDaySearch(hmParam) == null) {
                        hmParam.put("isu_no", "000");
                        dlwVatSvcDAO.insertCouponNoPerDayYMS(hmParam);
                    }

                    String isuNo = dlwVatSvcDAO.selectOtherCrtNoPerDay(hmParam);
                    hmParam.put("isu_no", isuNo);
                    if (!"".equals(isuNo)) {
                        if (Integer.parseInt(isuNo.substring(14)) > 999) {
                            msg = "err_max_seq";
                            break;
                        }
                        if (dlwVatSvcDAO.updateOtherCrtNoPerDay1(hmParam) <= 0) {
                            msg = "error";
                            break;
                        }
                    }
                } else {
                    if (dlwVatSvcDAO.selectCrtNoSearch(hmParam) == null) {
                        dlwVatSvcDAO.insertCouponNoYM(hmParam);
                    }
                    hmParam.put("isu_no", dlwVatSvcDAO.selectOtherCrtNo(hmParam));
                    if ((!"".equals((String)hmParam.get("isu_no"))) && (dlwVatSvcDAO.updateOtherCrtNo(hmParam) <= 0)) {
                        msg = "error";
                        break;
                    }
                }
            } else {
                hmParam.put("isu_no", "");
            }

            hmParam.put("isu_coupon_tp", 	"NO");	//쿠폰발급 종류(KEY:신규발급 쿠폰 KEY, NO:기존 및 투어몰10만원 발급번호
            if (!"".equals(dlwVatSvcDAO.insertSvcIsuHist(hmParam)))
            {

                String sMemMo = "";

                selectKeyMap = new HashMap<String, Object>();
                selectKeyMap = dlwVatSvcDAO.selectKeyInsertConsulMng(pmParam);

                sMemMo = "발급일자 : " + (String)pmParam.get("isu_dt") + ", 쿠폰명:" + (String)pmParam.get("svc_nm") + ", 쿠폰번호 : "+(String)hmParam.get("isu_no");

                tempMap = new HashMap<String, Object>();
                tempMap.put("skey", selectKeyMap.get("skey"));
                tempMap.put("seq", selectKeyMap.get("seq"));
                tempMap.put("cnsl_seq", selectKeyMap.get("cnsl_seq"));

                tempMap.put("mem_no", pmParam.get("mem_no"));
                tempMap.put("accnt_no", pmParam.get("accnt_no"));

                tempMap.put("cnsl_dt", pmParam.get("isu_dt"));
                tempMap.put("cnsl_mng_con", "부가서비스  발급");
                tempMap.put("cnsl_man", pmParam.get("rgsr_id"));
                tempMap.put("reg_man", pmParam.get("rgsr_id"));
                tempMap.put("upd_man", pmParam.get("rgsr_id"));
                tempMap.put("call_tel", "");
                tempMap.put("rec_file", "");
                tempMap.put("rec_path", "");
                tempMap.put("cti_id", "");

                tempMap.put("cnsl_dtl_con", sMemMo);
                tempMap.put("gubn", "80");
                tempMap.put("cnsl_cd", "09");

//                this.insertConsulMng(tempMap);  //엔컴 상담이력 (삭제 대상)

//                this.insertConsulDetail(tempMap); //엔컴 상담이력 (삭제 대상)


                //상담이력

                hmParam.put("cnsl_cd", "01");
                hmParam.put("cntr_cd", pmParam.get("cntr_cd"));

                hmParam.put("cons_typ1_cd", "CT01020000"); //상담유형1(상품관리)
                hmParam.put("cons_typ2_cd", "CT01020200"); //상담유형2(부가서비스)
                hmParam.put("cons_typ3_cd", "CT01020201"); //상담유형3(전후방서비스)

                hmParam.put("cnsl_man", pmParam.get("rgsr_id"));

                hmParam.put("consno_sqno", "1");                       		// 순번
                hmParam.put("actp_id", pmParam.get("rgsr_id"));        		// 접수자ID
                hmParam.put("cons_stat_cd", "30");                     		// 처리상태(처리완료)
                hmParam.put("cons_dspsmddl_dtpt_cd", "10");            		// 처리방법(일반상담)
                hmParam.put("rsps_dept_cd", pmParam.get("rsps_dept_cd"));   // 담당부서
                hmParam.put("chpr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("mem_no", pmParam.get("mem_no"));        		// 회원 고유번호
                hmParam.put("rgsr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("amnd_id", pmParam.get("rgsr_id"));        		// 담당자ID

                hmParam.put("acpg_chnl_cd", "99");   			// 접수채널 - 기타
                hmParam.put("cons_memo_cntn", sMemMo);   		// 상담메모
                hmParam.put("note", pmParam.get("isu_no"));   	// 비고:쿠폰번호

//                consService.insertCons(hmParam); //상담이력 insert

                msg = "success";
                succCnt++;
            }
        }

        if (msg == "success"){
            return String.valueOf(succCnt);
        }
        return "0";
    }


    /**
     * 부가서비스(대명투어몰(10만원)을 제외한 모든 쿠폰) 발급
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String newRegSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String msg = "error";
        String dupNo = "";
        int succCnt = 0;

        hmParam.put("optsvc_seq", pmParam.get("opt_svc_cd"));
        hmParam.put("accnt_no", pmParam.get("accnt_no"));
        hmParam.put("seq", pmParam.get("seq"));
        hmParam.put("no_crt_yn", pmParam.get("no_crt_yn"));
        hmParam.put("reg_man", pmParam.get("reg_man"));
        hmParam.put("opt_svc_cd", pmParam.get("opt_svc_cd"));

        hmParam.put("coupon_tp", 		pmParam.get("opt_svc_cd"));	//쿠폰종류 코드 셋팅
        hmParam.put("prov_dt", 			pmParam.get("isu_dt"));			//지급 일자 셋팅
        hmParam.put("isu_dt", 			pmParam.get("isu_dt"));			//발급 일자 셋팅
        hmParam.put("coupon_dtl_no", 	pmParam.get("coupon_dtl_no"));	//쿠폰상세 번호 셋팅

        hmParam.put("isu_coupon_tp", 	"KEY");	//쿠폰발급 종류(KEY:신규발급 쿠폰 KEY, NO:기존 및 투어몰10만원 발급번호
        hmParam.put("reg_id", 			pmParam.get("reg_man")); //사용자 셋팅
        hmParam.put("upd_id", 			pmParam.get("reg_man")); //사용자 셋팅
        hmParam.put("use_yn", "Y");

        int isuCnt = Integer.valueOf(String.valueOf(pmParam.get("isu_cnt"))).intValue();
        String sMemMo = "";
        String sCouponNo = "";
        String sCouponDtlNo = "";

        Map<String, Object> selectKeyMap = null;
        Map<String, Object> tempMap = null;
        for (int i = 0; i < isuCnt; i++) {

            tempMap = dlwCouponInfoService.selectCouponTpSearch(hmParam); //쿠폰종류별 조회
            if(tempMap == null){
                throw new Exception ("가져올 쿠폰이 없습니다.");
            }

            hmParam.put("coupon_inh_no", tempMap.get("coupon_inh_no")); //쿠폰고유번호 셋팅
            hmParam.put("isu_no", 		 tempMap.get("coupon_inh_no")); //
            hmParam.put("coupon_tp", 		 tempMap.get("coupon_tp")); //
            hmParam.put("coupon_no1", 		 tempMap.get("coupon_no1")); //
            hmParam.put("coupon_no2", 		 tempMap.get("coupon_no2")); //

            sCouponDtlNo = dlwCouponInfoService.selectKeyCouponDtl(hmParam);

//            CommonUtils.printLog("sCouponDtlNo #########################> "+sCouponDtlNo);
            //CommonUtils.printLog("========================================= newprod");

            hmParam.put("dml_mode", "insert"); //등록/수정 구분값 셋팅
            hmParam.put("coupon_dtl_no", 	sCouponDtlNo);	//쿠폰상세 번호 셋팅
            hmParam.put("use_yn", "Y");
            dlwCouponInfoService.saveCouponInfoDtl(hmParam);


            if (!"".equals(dlwVatSvcDAO.insertSvcIsuHist(hmParam)))
            {
                hmParam.put("use_yn", "Y"); //쿠폰고유번호 셋팅
                //mst 사용으로 변경
                dlwCouponInfoService.updateCouponInfoMstUseYn(hmParam);

                if("".equals(hmParam.get("coupon_no2")) || hmParam.get("coupon_no2") == null){
                    sCouponNo = String.valueOf(hmParam.get("coupon_no1"));
                }else{
                    sCouponNo = String.valueOf(hmParam.get("coupon_no1")) + "," + String.valueOf(hmParam.get("coupon_no2"));
                }

                sMemMo = "발급일자 : " + (String)pmParam.get("isu_dt") + ", 쿠폰명:" + (String)pmParam.get("svc_nm") + ", 쿠폰번호 : "+sCouponNo;

                hmParam.put("cnsl_cd", "01");
                hmParam.put("cntr_cd", pmParam.get("cntr_cd"));

                hmParam.put("cons_typ1_cd", "CT01020000"); //상담유형1(상품관리)
                hmParam.put("cons_typ2_cd", "CT01020200"); //상담유형2(부가서비스)
                hmParam.put("cons_typ3_cd", "CT01020201"); //상담유형3(전후방서비스)

                hmParam.put("cnsl_man", pmParam.get("rgsr_id"));

                hmParam.put("consno_sqno", "1");                       		// 순번
                hmParam.put("actp_id", pmParam.get("rgsr_id"));        		// 접수자ID
                hmParam.put("cons_stat_cd", "30");                     		// 처리상태(처리완료)
                hmParam.put("cons_dspsmddl_dtpt_cd", "10");            		// 처리방법(일반상담)
                hmParam.put("rsps_dept_cd", pmParam.get("rsps_dept_cd"));   // 담당부서
                hmParam.put("chpr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("mem_no", pmParam.get("mem_no"));        		// 회원 고유번호
                hmParam.put("rgsr_id", pmParam.get("rgsr_id"));        		// 담당자ID
                hmParam.put("amnd_id", pmParam.get("rgsr_id"));        		// 담당자ID

                hmParam.put("acpg_chnl_cd", "99");   			// 접수채널 - 기타
                hmParam.put("cons_memo_cntn", sMemMo);   		// 상담메모
                hmParam.put("note", pmParam.get("isu_no"));   	// 비고:쿠폰번호

//                consService.insertCons(hmParam); //상담이력 insert

                msg = "success";
                succCnt++;
            }
        } //for end

        if (msg == "success"){
            return String.valueOf(succCnt);
        }
        return "0";
    }



    /**
     * 우편번호 업로드
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String uploadExcelSvcPostInfo(Map<String, Object> pmParam) throws Exception {
        List<Map<String, Object>> accntList = dlwVatSvcDAO.selectSvcIssueHist(pmParam);
        Map<String, Object> hmParam = new HashMap<String, Object>();


        for (int i = 0; i < accntList.size(); i++) {
            hmParam = accntList.get(i);
        }


        String msg = "error";

        Map<String, Object> tempMap = null;
        String seq = "";
        seq = String.valueOf(hmParam.get("seq"));

        if (!"".equals(seq) ) {
            tempMap = new HashMap<String, Object>();

            tempMap.put("upd_man", pmParam.get("rgsr_id"));
            tempMap.put("invoice_no", pmParam.get("invoice_no"));
            tempMap.put("dtl_seq", hmParam.get("dtl_seq"));
            tempMap.put("accnt_no", pmParam.get("accnt_no"));
            tempMap.put("valid_svc", "");
            tempMap.put("addr_note", pmParam.get("addr_note"));
            tempMap.put("addressee", pmParam.get("addressee"));
            tempMap.put("send_dt", pmParam.get("send_dt"));
            tempMap.put("menu", "SV");

            if (!"".equals(Integer.valueOf(dlwVatSvcDAO.updateVatSvc(tempMap)))) {
                msg = "success";
            }
        }

        return msg;
    }


    public void insertConsulEtc(Map<String, Object> conMap) throws Exception
    {
        Map<String, Object> selectKeyMap = null;
        try
        {
            selectKeyMap = new HashMap<String, Object>();
            selectKeyMap = dlwVatSvcDAO.selectKeyInsertConsulMng(conMap);

            conMap.put("skey", selectKeyMap.get("skey"));
            conMap.put("seq", selectKeyMap.get("seq"));
            conMap.put("cnsl_seq", selectKeyMap.get("cnsl_seq"));

            this.insertConsulMng(conMap);

            this.insertConsulDetail(conMap);

        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 상담업무 수정시 부가서비스 업무 수정 및 로직체크
     *
     * @param pmParam 회원번호
     * @return void
     * @throws Exception
     */
    public void updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception {
       // return dlwVatSvcDAO.selectSvcAccntNoHist(pmParam);


        Map<String, Object> hmParam = null;
        Map<String, Object> list01Map = null;
        Map<String, Object> list02Map = null;

        try
        {
            List<Map<String, Object>> accntNoHistList01 = dlwVatSvcDAO.selectSvcAccntNoHist(pmParam);
            List<Map<String, Object>> memSvcList02 = dlwVatSvcDAO.selectMemProdAccntSvcTot(pmParam);
            String list01Pk = "";
            String list01AccntNo = "";
            String list01DtlSeq = "";
            String list01Seq = "";
            String list01OptSvcSeq = "";

            String list02Pk = "";
            String list02AccntNo = "";
            String list02DtlSeq = "";
            String list02Seq = "";
            String list02OptSvcCd = "";


            for (int i = 0; i < accntNoHistList01.size(); i++) { // 부가서비스 관리 이력에 저장되어 있는 데이타 loop
                list01Map = accntNoHistList01.get(i);
                list01AccntNo = String.valueOf(list01Map.get("accnt_no"));
                list01DtlSeq = String.valueOf(list01Map.get("dtl_seq"));
                list01Seq = String.valueOf(list01Map.get("seq"));
                list01OptSvcSeq = String.valueOf(list01Map.get("optsvc_seq"));

                list01Pk = list01AccntNo+list01DtlSeq+list01OptSvcSeq;

                for (int j = 0; j < memSvcList02.size(); j++) { // 회원별 부가서비스 이력에 저장되어 있는 데이타 loop
                    list02Map = memSvcList02.get(i);
                    list02AccntNo = String.valueOf(list02Map.get("accnt_no"));
                    list02DtlSeq = String.valueOf(list02Map.get("dtl_seq"));
                    list02Seq = String.valueOf(list02Map.get("seq"));
                    list02OptSvcCd = String.valueOf(list02Map.get("opt_svc_cd"));

                    list02Pk = list02AccntNo+list02DtlSeq+list02OptSvcCd;

                    if(list01Pk.equals(list02Pk)){ // accnt_no,dtl_seq 데이타가 같은 키가 비교 타켓
                        if(!list01Seq.equals(list02Seq)){ //seq 틀릴시에 상담에서 변경되면 부가서비스 이력에도 수정하도록 처리하는 로직
                            hmParam = new HashMap<String, Object>();
                            hmParam.put("accnt_no", 	list01AccntNo);
                            hmParam.put("dtl_seq", 		list01DtlSeq);
                            hmParam.put("seq", 			list02Seq);
                            hmParam.put("optsvc_seq", 	list01OptSvcSeq);

                            dlwVatSvcDAO.updateCallCenterVatSvcHist(hmParam);
                        }
                    }

                }

            }


        } catch (Exception e) {
            throw e;
        }
    }




}
