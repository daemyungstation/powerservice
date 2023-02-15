/*
 * (@)# WebConsController.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
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

package powerservice.business.web.web;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cns.service.CustBasiService;
import powerservice.business.sys.service.NiceCreditService;
import powerservice.business.web.service.WebNiceSenderService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.NiceCreditUtil;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/20
 * @프로그램ID WebCons
 */

@Controller
@RequestMapping(value="/web/nice")
public class WebNiceSenderController {

    @Resource
    private WebNiceSenderService webNiceSenderService ;
    @Resource
    private NiceCreditService niceCreditService;
    @Resource
    private CustBasiService custBasiService;
    @Autowired
    private ServletContext ctx;

    final static String niceOperation  = "dev"; // NICE 환경 dev : 개발계. real : 운영계. 개발 = 개발 운영 = 운영
    final static int socketSendingTime = 1000*30; // 전문 송신/수신을 할때 건당 할당되는 소켓연결 시간 단위는 밀리세컨드 이며 5초라면 1000*5 로 표현

    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : Nice1차 전송
     * ================================================================
     * */
    @RequestMapping(value = "/insertNiceInfomation")
    public ModelAndView insertNiceInfomation(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();

        Socket socket = null;
        Map<String, Object> hmParam1 = new HashMap<String, Object>(); // ds_list_trgtCust     (고객타겟리스트 [캠페인])
        Map<String, Object> hmParam2 = new HashMap<String, Object>(); // ds_list_trgtCustAlct (타켓리스트 [캠페인])
        Map<String, String> hmParam3 = new HashMap<String, String>(); // NICE 전송용 파라미터 (makeParams 메소드에 파라미터 변수로 넘김)

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            System.out.println("NICE 전문 송신을 위한 연결을 시도합니다.");
            //socket = new Socket("172.28.220.42", 80);   // 운영계 real 일때
            //socket = new Socket("172.28.220.43", 80); // 개발계 dev 일때
            System.out.println("NICE 전문 송신을 위한 연결이 맺어졌습니다.");

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // ds_list_trgtCust     (고객타겟리스트 [캠페인])
            DataSetMap srchInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // ds_list_trgtCustAlct (타켓리스트 [캠페인])


            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            if (srchInDs2 != null && srchInDs2.size() > 0)
            {
                hmParam2 = srchInDs2.get(0);
            }

            hmParam1.putAll(hmParam2);
            ParamUtils.addSaveParam(hmParam1);

            /* 접수 공통부 파라미터 */
            hmParam3.put("data_type"       , "SICINF01"); // 전문코드(접수)
            hmParam3.put("corporation_code", "DMLF01"); // 대명DLCC
            hmParam3.put("app_no"          , (String)hmParam1.get("trgt_cust_dtpt_id")); // 고객유일식별값(협의)
            hmParam3.put("app_type"        , "01"); // 접수구분값 (신규접수 01)
            hmParam3.put("member_type"	   , "01"); // 징구주체값 (본인 01)
            hmParam3.put("app_reason"      , "01"); // 접수사유값 (01로 고정)
            hmParam3.put("contract_type"   , "01"); // 계약구분값 (01 : 1차접수, 02 : 2차접수 여기를 이용하여 1차와 2차를 구분하겠습니다)

            /* 접수 개별부 파라미터 */
            String tel  = (String)hmParam1.get("clph_tlno");
            String brth = (String)hmParam1.get("brth_mon_day");

            hmParam3.put("name",  (String)hmParam1.get("cust_nm")); // 고객명

            if(tel.length() == 13)
            {
                hmParam3.put("ph21",  tel.substring(0, 3));     // 고객 휴대폰번호 앞자리 3
                hmParam3.put("ph22",  tel.substring(4, 8));     // 고객 휴대폰번호 가운데자리 4
                hmParam3.put("ph23",  tel.substring(9, 13));    // 고객 휴대폰번호 끝자리 4
            }
            else if(tel.length() == 12)
            {
                hmParam3.put("ph21",  tel.substring(0, 3));     // 고객 휴대폰번호 앞자리 3
                hmParam3.put("ph22",  tel.substring(4, 7));     // 고객 휴대폰번호 가운데자리 3
                hmParam3.put("ph23",  tel.substring(8, 12));    // 고객 휴대폰번호 끝자리 4
            }
            else
            {

            }

            hmParam3.put("ssn11", brth.substring(2, 8));    // 고객 생년월일 6자리

            System.out.println("--------------------------------------------------");
            System.out.println("name :  " + hmParam1.get("cust_nm"));
            System.out.println("tel : " + tel);
            System.out.println("length : " + tel.length() );
            System.out.println("ph21 :  " + tel.substring(0, 3));
            System.out.println("ph22 :  " + tel.substring(4, 8));
            System.out.println("ph23 :  " + tel.substring(9, 13));
            System.out.println("ssn11 : " + brth.substring(2, 8));
            System.out.println("--------------------------------------------------");

            System.out.println("인풋데이터3 ::: " + hmParam3.toString());
            System.out.println("인풋데이터1 ::: " + hmParam1.toString());

            int result0 = webNiceSenderService.updateMemberNiceInfo(hmParam1);

            List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceInfo(hmParam1);

            Map<String, Object> insertList1 = mList1.get(0);

            System.out.println("-----------------------------------");
            System.out.println(mList1.get(0).toString());
            System.out.println("-----------------------------------");

            insertList1.putAll(hmParam3);

            // 해당고객의 접수 이력을 먼저 저장한다.
            int result2 = webNiceSenderService.insertMemberNiceInfoHist(insertList1);
            // NICE로 전문을 송신한다.
            httpConnect.makeParam(hmParam3, niceOperation, "euc-kr", socketSendingTime);

            //httpConnect.makeParam(hmParam3, "dev", "euc-kr"); // NICE 정보발송 "dev" 개발계 "real" 운영계

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (IOException ioe) {
            System.out.println("NICE 전문 송신을 위한 연결이 실패하였습니다.");
            //전문 송신이 실패하였을 경우에는 타켓리스트에 업데이트된 생년월일의 값을 초기화한다.
            webNiceSenderService.updateExceptionHandlerTrgtCust(hmParam1);
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage() + " ::: NICE 전문 송신을 위한 연결이 실패하였습니다.";
        } catch (Exception e) {
            hmParam1.putAll(hmParam2);
            //처리가 실패하였을 경우에는 타켓리스트에 업데이트된 생년월일의 값을 초기화한다.
            webNiceSenderService.updateExceptionHandlerTrgtCust(hmParam1);
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : Nice2차 전송(여기서는 고객의 증서정보와 해약금에 대한 정보도 함께 전송해야함.)
     * ================================================================
     * */
    @RequestMapping(value = "/insertNiceInfomation2")
    public ModelAndView insertNiceInfomation2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Socket socket = null;

        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>(); // ds_member          // 고객증서정보
        Map<String, String> hmParam2 = new HashMap<String, String>(); // nice 전송 파라미터

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            System.out.println("NICE 전문 송신을 위한 연결을 시도합니다.");
            //socket = new Socket("172.28.220.42", 80);    // 운영계 real 일때
            //socket = new Socket("172.28.220.43", 80); // 개발계 dev 일때
            System.out.println("NICE 전문 송신을 위한 연결이 맺어졌습니다.");

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // 회원번호,고객타켓리스트번호
            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            System.out.println("고객 기본정보 ::: " + hmParam1);
            System.out.println("------------------------------------------");
            System.out.println("------------------------------------------");

            //List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceInfo(hmParam1); // 고객 타켓리스트번호(NICE 전문 식별값) 을 가지고 오기 위함임.
            //Map<String, Object> oApp_no = mList1.get(0);

            List<Map<String, Object>> mList2 = webNiceSenderService.getCertfMng(hmParam1); // 고객의 증서 내용을 가지고 오기 위함임.
            Map<String, Object> hmParam3 = mList2.get(0);

            List<Map<String, Object>> mList3 = webNiceSenderService.getResnAmt(hmParam1); // 고객의 해약금에 대한 정보를 가지고 오기 위함임.


            /* 접수 공통부 파라미터 값이 없는 항목들은 모두 " " 로 처리 CommonUtils.nvl(value, str) */
            hmParam2.put("data_type"       , CommonUtils.nvl("SICINF01", " ")); // 전문코드(접수)
            hmParam2.put("corporation_code", CommonUtils.nvl("DMLF01", " ")); // 대명DLCC
            //hmParam2.put("app_no"          , (String)oApp_no.get("trgt_cust_dtpt_id")); // 고객유일식별값(협의)
            hmParam2.put("app_no"          , CommonUtils.nvl((String)hmParam3.get("accnt_no"), " ")); // 고객유일식별값(2차는 회원번호로 구분)
            hmParam2.put("app_type"        , CommonUtils.nvl("01", " ")); // 접수구분값 (신규접수 01)
            hmParam2.put("member_type"	   , CommonUtils.nvl("01", " ")); // 징구주체값 (본인 01)
            hmParam2.put("app_reason"      , CommonUtils.nvl("01", " ")); // 접수사유값 (01로 고정)
            hmParam2.put("contract_type"   , CommonUtils.nvl("02", " ")); // 계약구분값 (01 : 1차접수, 02 : 2차접수 여기를 이용하여 1차와 2차를 구분하겠습니다)

            /* 접수 개별부 파라미터 */
            String tel  = (String)hmParam3.get("cell_full");
            String brth = (String)hmParam3.get("brth_mon_day");

            hmParam2.put("name",  CommonUtils.nvl((String)hmParam3.get("mem_nm"), " ")); // 고객명

            if(tel.length() == 13)
            {
                hmParam2.put("ph21",  CommonUtils.nvl(tel.substring(0, 3), " "));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  CommonUtils.nvl(tel.substring(4, 8), " "));     // 고객 휴대폰번호 가운데자리 4
                hmParam2.put("ph23",  CommonUtils.nvl(tel.substring(9, 13), " "));    // 고객 휴대폰번호 끝자리 4
            }
            else if(tel.length() == 12)
            {
                hmParam2.put("ph21",  CommonUtils.nvl(tel.substring(0, 3), " "));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  CommonUtils.nvl(tel.substring(4, 7), " "));     // 고객 휴대폰번호 가운데자리 3
                hmParam2.put("ph23",  CommonUtils.nvl(tel.substring(8, 12), " "));    // 고객 휴대폰번호 끝자리 4
            }
            else
            {

            }

            hmParam2.put("ssn11"           , CommonUtils.nvl(brth.substring(2, 8), " "));    // 고객 생년월일 6자리

            hmParam2.put("member_no"         , CommonUtils.nvl((String)hmParam3.get("mem_no"          ), " ")); // 고객고유번호
            hmParam2.put("contract_code"     , CommonUtils.nvl((String)hmParam3.get("accnt_no"        ), " ")); // 고객회원번호
            hmParam2.put("contract_date"     , CommonUtils.nvl((String)hmParam3.get("join_dt"         ), " ")); // 가입일자
            //hmParam2.put("zip21"       	 	 , (String)hmParam3.get("home_zip"        )); // 우편번호
            String sZip = (String)hmParam3.get("home_zip"        );
            hmParam2.put("zip21"       	 	 , CommonUtils.nvl(sZip.substring(0, 3), " ")); // 우편번호 앞자리
            hmParam2.put("zip22"       	 	 , CommonUtils.nvl(sZip.substring(3, 5), " ")); // 우편번호 뒷자리
            hmParam2.put("addr21"       	 , CommonUtils.nvl((String)hmParam3.get("home_addr"       ), " ")); // 주소1
            hmParam2.put("addr22"      	     , CommonUtils.nvl((String)hmParam3.get("home_addr2"      ), " ")); // 주소2
            hmParam2.put("goods_key"         , CommonUtils.nvl((String)hmParam3.get("prod_cd"         ), " ")); // 고객상품코드
            hmParam2.put("loan_name"         , CommonUtils.nvl((String)hmParam3.get("prod_nm"         ), " ")); // 상품명(상품정보1)
            hmParam2.put("etc_01"        	 , CommonUtils.nvl((String)hmParam3.get("prod_amt"        ), " ")); // 상품금액
            hmParam2.put("etc_02"     		 , CommonUtils.nvl((String)hmParam3.get("mon_pay_amt"     ), " ")); // 회차별납입액
            hmParam2.put("etc_03"         	 , CommonUtils.nvl(hmParam3.get("expr_no"      ).toString(), " ")); // 만기회차
            hmParam2.put("loan_name2"		 , CommonUtils.nvl((String)hmParam3.get("prod_join_prt_nm"), " ")); // 상품명(상품정보2)
            hmParam2.put("etc_04"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val2"  ), " ")); // 계약금액
            hmParam2.put("etc_05"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val3"  ), " ")); // 월납입금
            hmParam2.put("etc_06"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val4"  ), " ")); // 계약유형
            hmParam2.put("hope_contract_day" , CommonUtils.nvl((String)hmParam3.get("ichae_dt"        ), " ")); // 결제일자
            hmParam2.put("invo_corp"         , CommonUtils.nvl((String)hmParam3.get("bank_nm"         ), " ")); // 카드사 / 은행명
            hmParam2.put("invo_no"           , CommonUtils.nvl((String)hmParam3.get("bank_accnt_no"   ), " ")); // 카드번호 / 계좌번호

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("bank_owner_name"   ,  CommonUtils.nvl((String)hmParam3.get("depr"            ), " ")); // 예금주
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("bank_owner_name"   , CommonUtils.nvl(" ", " ")); // 예금주
            }

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("invo_ca"     		 , CommonUtils.nvl(" ", " ")); // 유효기간
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("invo_ca"           , CommonUtils.nvl((String)hmParam3.get("expire_date"     ), " ")); // 유효기간
            }

            hmParam2.put("info_01"           , CommonUtils.nvl((String)hmParam3.get("coffin1"         ), " ")); // 매장
            hmParam2.put("info_02"           , CommonUtils.nvl((String)hmParam3.get("coffin2"         ), " ")); // 화장/탈관
            hmParam2.put("info_03"           , CommonUtils.nvl((String)hmParam3.get("shroud_m1"       ), " ")); // 수의 1
            hmParam2.put("info_04"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_d1"       ), " ")); // 수의 1 내용
            hmParam2.put("info_05"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_m2"       ), " ")); // 수의 2
            hmParam2.put("info_06"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_d2"       ), " ")); // 수의 2 내용
            hmParam2.put("info_07"     		 , CommonUtils.nvl((String)hmParam3.get("coffin_gds1"     ), " ")); // 입관/수시용품1
            hmParam2.put("info_08"     		 , CommonUtils.nvl((String)hmParam3.get("coffin_gds2"     ), " ")); // 입관/수시용품2
            hmParam2.put("info_09"   		 , CommonUtils.nvl((String)hmParam3.get("mortuary_gds1"   ), " ")); // 빈소/기타용품1
            hmParam2.put("info_10"   		 , CommonUtils.nvl((String)hmParam3.get("mortuary_gds2"   ), " ")); // 빈소/기타용품2
            hmParam2.put("info_11"           , CommonUtils.nvl((String)hmParam3.get("car1"            ), " ")); // 이송차량
            hmParam2.put("info_12"           , CommonUtils.nvl((String)hmParam3.get("car2"            ), " ")); // 리무진
            hmParam2.put("info_13"           , CommonUtils.nvl((String)hmParam3.get("car3"            ), " ")); // 유족버스
            hmParam2.put("info_14"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m1"       ), " ")); // 제단 장식 1
            hmParam2.put("info_15"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d1"       ), " ")); // 제단 장식 1 내용
            hmParam2.put("info_16"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m2"       ), " ")); // 제단 장식 2
            hmParam2.put("info_17"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d2"       ), " ")); // 제단 장식 2 내용
            hmParam2.put("info_18"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m3"       ), " ")); // 제단 장식 3
            hmParam2.put("info_19"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d3"       ), " ")); // 제단 장식 3 내용
            hmParam2.put("info_20"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes1"), " ")); // 현대식
            hmParam2.put("info_21"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes2"), " ")); // 전통식 1
            hmParam2.put("info_22"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes3"), " ")); // 전통식 2
            hmParam2.put("info_23"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m1"       ), " ")); // 인력 1
            hmParam2.put("info_24"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d1"       ), " ")); // 인력 1 내용
            hmParam2.put("info_25"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m2"       ), " ")); // 인력 2
            hmParam2.put("info_26"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d2"       ), " ")); // 인력 2 내용
            hmParam2.put("info_27"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m3"       ), " ")); // 인력 3
            hmParam2.put("info_28"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d3"       ), " ")); // 인력 3 내용
            hmParam2.put("info_29"           , CommonUtils.nvl((String)hmParam3.get("cmetc"           ), " ")); // 비고
            hmParam2.put("info_30"           , CommonUtils.nvl((String)hmParam3.get("etc_val"         ), " ")); // 비고내용
            hmParam2.put("info_31"       	 , CommonUtils.nvl((String)hmParam3.get("terms_dtl"       ), " ")); // 장의상품 상세정보
            hmParam2.put("info_32"        	 , CommonUtils.nvl((String)hmParam3.get("consm_nt"        ), " ")); // 소비자 유의사항
            hmParam2.put("info_33"       	 , CommonUtils.nvl((String)hmParam3.get("refund_nt"       ), " ")); // 환급기준 및 환급시기
            hmParam2.put("info_34"      	 , CommonUtils.nvl((String)hmParam3.get("refund_amt"      ), " ")); // 총 고객환급의무액
            hmParam2.put("info_35"           , CommonUtils.nvl((String)hmParam3.get("asset"           ), " ")); // 상조관련 자산
            hmParam2.put("info_36"   		 , CommonUtils.nvl((String)hmParam3.get("consm_amt_mng"   ), " ")); // 고객 불입금 관리방법

            String sResn_amt = "";

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(mList3.toString());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for(int idx = 0; idx < mList3.size(); idx++)
            {
                sResn_amt = sResn_amt + mList3.get(idx).get("no") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("pct") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("resn_amt") + ";";
            }

            hmParam2.put("resn_amt"   , CommonUtils.nvl(sResn_amt, " ")); // 해약환금율
            hmParam2.put("info_37"    , CommonUtils.nvl((String)hmParam3.get("refund_math1"    ), " ")); // 해약환급금 산식(정기형)
            hmParam2.put("info_38"    , CommonUtils.nvl((String)hmParam3.get("refund_math2"    ), " ")); // 해약환급금 산식(부정기형)
            hmParam2.put("info_39"    , CommonUtils.nvl((String)hmParam3.get("refund_math3"    ), " ")); // 해약환급금 산식(부정기형) 밑에 내용
            hmParam2.put("info_40"    , CommonUtils.nvl((String)hmParam3.get("pay_mthd"        ), " ")); // 납입방식 04 : CMS, 06: Card

            hmParam2.put("info_41"    , CommonUtils.nvl((String)hmParam3.get("terms_svc"    	), " ")); // 2018.05.10 추가 토탈라이프서비스
            hmParam2.put("info_42"    , CommonUtils.nvl((String)hmParam3.get("resn_amt_info"    ), " ")); // 2018.05.10 추가 실 해약환급내용 추가

            hmParam1.putAll(hmParam2);
            hmParam1.put("brth_mon_day",  brth);                 // 고객 생년월일
            hmParam1.put("clph_tlno",  tel.replaceAll("-", "")); // 고객 전화번호
            hmParam1.put("cust_nm",  (String)hmParam3.get("mem_nm"));   // 고객명
            hmParam1.put("mem_no",   (String)hmParam3.get("mem_no"));   // 고객고유번호
            hmParam1.put("accnt_no", (String)hmParam3.get("accnt_no")); // 고객회원번호
            hmParam1.put("prod_cd",  (String)hmParam3.get("prod_cd"));  // 상품코드

            System.out.println("-----------------------------------");
            System.out.println("2차접수값 :: " + hmParam1.toString());
            System.out.println("-----------------------------------");
            System.out.println(tel.substring(0, 3) + "::" + tel.substring(4, 8) + "::" + tel.substring(9, 13) );
            System.out.println("-----------------------------------");
            System.out.println("2차접수값 :: " + sResn_amt);
            System.out.println("-----------------------------------");

            int result4 = webNiceSenderService.insertMemberNiceInfoSecond(hmParam1);

            System.out.println("나이스 전송 파라미터1 ::: " + hmParam1);
            System.out.println("나이스 전송 파라미터2 ::: " + hmParam2);

            //int result3 = webNiceSenderService.insertMemberNiceInfoHist(hmParam1); // NICE신용정보 전자서명 테이블 히스토리 인서트
            /* 보고를 따로 드려야함 */

            httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", 1000*30); // NICE 정보발송 "dev" 개발계 "real" 운영계

            //int result1 = webNiceSenderService.updateMemberNiceInfo2(hmParam1);    // 고객 타켓리스트 테이블 업데이트 (고객고유번호, 고객회원번호)
            //int result2 = webNiceSenderService.updateMemberNiceInfo3(hmParam1);    // NICE신용정보 전자서명 테이블 업데이트 (고객고유번호, 고객회원번호)

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (IOException ioe)
        {
            System.out.println("NICE 전문 송신을 위한 연결이 실패하였습니다.");
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage() + " ::: NICE 전문 송신을 위한 연결이 실패하였습니다.";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : Nice1,2차 전송시 NICE에서 Return 해주는 정보값 업데이트
     * ================================================================
     * */
    @RequestMapping(value = "/updateNiceSendStatement")
    public String updateNiceSendStatement(HttpServletRequest httpServletRequest) //(Map<String, Object> map)
    {
        ModelAndView modelAndView = new ModelAndView();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();

        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, String> hmParam2 = new HashMap<String, String>();
        Map<String, Object> hmParam3 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            String data_type         = httpServletRequest.getParameter("data_type");        // 전문 식별 값
            String corporation_code  = httpServletRequest.getParameter("corporation_code"); // 고객사 코드 값
            String app_no            = httpServletRequest.getParameter("app_no");           // 고객 식별 값
            String nice_no           = httpServletRequest.getParameter("nice_no");          // NICE가 발행한 계약건 유니크 값
            String nccs_stat         = httpServletRequest.getParameter("nccs_stat");        // 계약건 상태 값 1차 발송 시 - 01: 젒수 시	02: 통신사 인증 성공 시	04: 세이프키 및 CB정보 획득 후 완료 시 / 2차 발송 시 - 01: 접수 시	02: 위탁처리 동의 시 	04: 계약서 작성 및 완료 시
            String memo_no           = httpServletRequest.getParameter("memo_no");          // 메모 시퀀스 값
            String upp_tx_type       = httpServletRequest.getParameter("upp_tx_type");      // 전송 구분 값 01: 1차 접수 시	02: 통신사 인증 성공 시	03: 세이프키 및 CB정보 획득 후 완료 시 04: 2차 접수 시 05: 위탁처리 동의 시 06: 계약서 작성 및 완료 시
            String result_memo		 = httpServletRequest.getParameter("result_memo");      // 응답실패시 메시지
            String result_code 		 = httpServletRequest.getParameter("result_code");      // 응답 결과 01 : 성공, 02 : 실패
            String ci                = httpServletRequest.getParameter("ci");               // 고객 ci 값
            String gender            = httpServletRequest.getParameter("gender");           // 성별
            String recvMessage       = httpServletRequest.getParameter("recvMessage");      // 신용조회 결과값 (파싱해서 사용함)
            //String safe_key          = httpServletRequest.getParameter("safe_key");         // 고객 safe key 값
            String img_file          = httpServletRequest.getParameter("img_file");         // 이미지 파일명

            /* 응답 공통부 */
            hmParam.put("data_type"       , data_type);
            hmParam.put("corporation_code", corporation_code);
            hmParam.put("app_no"          , app_no);
            hmParam.put("nice_no"         , nice_no);
            hmParam.put("nccs_stat"       , nccs_stat);
            hmParam.put("memo_no"         , memo_no);
            hmParam.put("upp_tx_type"     , upp_tx_type);
            hmParam.put("result_code"     , result_code);
            hmParam.put("result_memo"     , result_memo);
            hmParam.put("ci"              , ci);
            hmParam.put("gender"          , gender);
            hmParam.put("recvMessage"     , recvMessage);
            //hmParam.put("safe_key"        , safe_key);
            hmParam.put("img_file"        , img_file);
            hmParam.put("rgsr_id"         , "NICE");

            // NICE의 각 처리과정을 거치면서 업데이트, 인서트 되어 추가되는 데이터
            if(upp_tx_type.equals("01"))
            {
                hmParam.put("extend_data"        , nice_no); // nice 관리번호
            }
            else if (upp_tx_type.equals("02"))
            {
                hmParam.put("extend_data"        , ci);     // 고객 ci 번호
            }
            else if(upp_tx_type.equals("03"))
            {
                hmParam.put("extend_data"        , recvMessage); // 고객 신용정보
            }
            else if(upp_tx_type.equals("04"))
            {
                hmParam.put("extend_data"        , nice_no); // nice 관리번호 2차
            }
            else if(upp_tx_type.equals("05"))
            {
                hmParam.put("extend_data"        , "위탁처리"); // 위탁처리
            }
            else if(upp_tx_type.equals("06"))
            {
                hmParam.put("extend_data"        , img_file); // 고객 회원 증서 이미지 파일명
            }

            /* 응답 개별부 */
            //hmParam.put("cust_nm"                       , httpServletRequest.getParameter("cust_nm"));
            //hmParam.put("clph_tlno"                     , httpServletRequest.getParameter("clph_tlno"));
            //hmParam.put("brth_mon_day"                  , httpServletRequest.getParameter("brth_mon_day"));

            System.out.println("WebNiceSenderController.updateNiceSendStatement() ::: " + hmParam.toString());

            /* 1. 1차 접수 완료시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 01 : 1차 접수완료)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때 NICE_NO(나이스 사에서 제공되는 키값) 이 부여된다. */
            if(httpServletRequest.getParameter("upp_tx_type").equals("01"))
            {
                // 전문이 송신 되었으면 TB_MEMBER_NICE_INFO 테이블에 고객 기본정보를 저장한다.
                List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceInfo(hmParam);

                Map<String, Object> insertList1 = mList1.get(0);
                int result1 = webNiceSenderService.insertMemberNiceInfo(insertList1);

                webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트
            }

            /* 2. 본인인증 완료시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 02 : 본인인증완료, NCCS_STAT = 02 : 진행)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때 CI_VAL(고객 ci 값)과 GENDER(고객성별) 이 부여된다. */
            if(httpServletRequest.getParameter("upp_tx_type").equals("02") && httpServletRequest.getParameter("nccs_stat").equals("02"))
            {
                webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트
            }

            /* 3. 신용정보취득시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 03 : 신용정보취득, NCCS_STAT = 04 : 완료)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때 SAFEKEY(고객SAFEKEY(고객의주민등록번호를대체하는번호))와 고객의 신용정보가 부여된다. */
            if(httpServletRequest.getParameter("upp_tx_type").equals("03") && httpServletRequest.getParameter("nccs_stat").equals("04") &&
               httpServletRequest.getParameter("result_code").equals("01")) // 신용정보 취득시
            {
                long lSyGubun_RK0400_000 = 100; // CB스코어 등급
                long lSyGubun_RK0400_700 = 100; // 서브프라임 스코어 등급
                Map<String, Object> mapSumServiceItem = new HashMap<String, Object>();

                String safekey = recvMessage.substring(111, 124); // nice safekey;

                /* NICE에서 신용정보에 대한 결과를 String으로 보내줌. 각 부분을 파싱하여 신용여부를 map에 저장함 */
                mapSumServiceItem.put("B12000200", recvMessage.substring(169,178)); // (신용관리대상/공공정보)미해제등록 총 건수 1건이상
                mapSumServiceItem.put("B22000200", recvMessage.substring(189,198)); // (채무불이행-신용정보사)미해제등록 총 건수 1건이상
                mapSumServiceItem.put("BE0000020", recvMessage.substring(209,218)); // (채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상
                mapSumServiceItem.put("BS0000051", recvMessage.substring(229,238)); // 희망모아 신용회복지원 대상 구분 1,2
                mapSumServiceItem.put("BS0000784", recvMessage.substring(249,258)); // 상록수 신용회복지원 구분 1,2
                mapSumServiceItem.put("CRT000005", recvMessage.substring(269,278)); // 파산면책 등록 유무 1
                mapSumServiceItem.put("CRT000006", recvMessage.substring(289,298)); // 개인회생 등록 유무 1
                mapSumServiceItem.put("CRT000009", recvMessage.substring(309,318)); // 실종공시최고 유무 1
                mapSumServiceItem.put("CRT000010", recvMessage.substring(329,338)); // 실종선고 유무 1
                mapSumServiceItem.put("CRT000012", recvMessage.substring(349,358)); // 부재선고 유무 1
                mapSumServiceItem.put("CRT000013", recvMessage.substring(369,378)); // 금치산선고 유무 1
                mapSumServiceItem.put("CRT000015", recvMessage.substring(389,398)); // 한정치산선고 유무 1
                mapSumServiceItem.put("CRT000017", recvMessage.substring(409,418)); // 국적상실 유무 1
                mapSumServiceItem.put("CRT000019", recvMessage.substring(429,438)); // 국적이탈 유무 1
                mapSumServiceItem.put("CRT000023", recvMessage.substring(449,458)); // 파산선고 유무 1
                mapSumServiceItem.put("CRT000025", recvMessage.substring(469,478)); // 면책선고 유무 1
                mapSumServiceItem.put("CRT000027", recvMessage.substring(489,498)); // 개인회생개시 유무 1
                mapSumServiceItem.put("AS0000138", recvMessage.substring(509,518)); // 외국인 구분(0:외국인, 1:내국인) 0

                lSyGubun_RK0400_000 = Long.parseLong(recvMessage.substring(612,616).toString());
                lSyGubun_RK0400_700	= Long.parseLong(recvMessage.substring(712,716).toString());

                System.out.println("recvMassage ::: " + recvMessage);
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("신용정보 여부 ::: " + mapSumServiceItem.toString());
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("CB신용등급 ::: " + lSyGubun_RK0400_000);
                System.out.println("서브프라임신용등급 ::: " + lSyGubun_RK0400_700);
                System.out.println("----------------------------------------------------------------------------------------------------");

                /* 애큐온 인증 (이부분을 통하여 대명 고객 가입이 가능한지 아닌지를 판단하게 됨)*/
                if(NiceCreditUtil.hasNiceCreditForAquon(lSyGubun_RK0400_000, lSyGubun_RK0400_700, mapSumServiceItem) == true)
                {
                    hmParam.put("acuon_fitness_yn"        , "Y");
                    System.out.println("가입가능");
                }
                else
                {
                    hmParam.put("acuon_fitness_yn"        , "N");
                    System.out.println("가입불가");
                }

                hmParam.put("trgt_cust_dtpt_id", app_no);
                List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceInfo(hmParam); // 고객의 기본정보를 조회함
                Map<String, Object> map = mList1.get(0);
                String cust_nm      = (String)map.get("cust_nm");      // 고객명
                String clph_tlno    = (String)map.get("clph_tlno");    // 고객전화번호
                String brth_mon_day = (String)map.get("brth_mon_day"); // 고객생년월일

                List<Map<String, Object>> mList2 = webNiceSenderService.getMemberNiceRetrieve(hmParam); // 고객의 safekey와 성별을 조회함.
                Map<String, Object> map2 = mList2.get(0);

                /* 고객 NICE SafeKey 조회 이력을 남김 */
                hmParam.put("safekey", safekey); // hmParam.put("safekey", "7777779999999"); 이부분은 고객 신용정보가 필요함.
                hmParam.put("name", cust_nm);
                hmParam.put("cell", clph_tlno);
                hmParam.put("brth_mon_day", brth_mon_day);
                hmParam.put("sex", map2.get("gender"));
                hmParam.put("return_code", result_code);
                hmParam.put("loginid"		, "ADMIN");
                hmParam.put("empl_no"		, "999999");

                webNiceSenderService.insertNiceSafekeySearchHis(hmParam); // 고객 NICE SafeKey 조회 이력을 남김

                // NICE에서 리턴받은뒤 응답.
                hmParam2.put("data_type"       , data_type);
                hmParam2.put("corporation_code", corporation_code);
                hmParam2.put("app_no"          , app_no);
                hmParam2.put("nice_no"         , nice_no);
                hmParam2.put("nccs_stat"       , nccs_stat);
                hmParam2.put("memo_no"         , memo_no);
                hmParam2.put("upp_tx_type"     , upp_tx_type);
                hmParam2.put("result_code"     , result_code);
                hmParam2.put("result_memo"     , result_memo);

                // 1차가 완료됐다는 것을 NICE쪽에 통보만 한다.
                //httpConnect.makeParam(hmParam2, "dev", "euc-kr"); // 개발계 : dev , 운영계 : real
                httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", socketSendingTime);

                hmParam3.put("app_no", app_no);
                hmParam3.put("finish_flag", "1Y");

                webNiceSenderService.updateNiceFinishStatement(hmParam3);

                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("응답받은뒤 다시 NICE로 전송하는 값 ::: " + hmParam2.toString());
                System.out.println("----------------------------------------------------------------------------------------------------");

                webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트
            }

            /* 4. 신용정보취득시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 04 : 2차 접수완료)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때 NICE_NO2(나이스 사에서 2차로 제공되는 키값) 이 부여된다. */
            if(httpServletRequest.getParameter("upp_tx_type").equals("04"))
            {
            	webNiceSenderService.updateNiceSendStatement2(hmParam);
                //webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                //webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트
            }

            /* 5. 본인인증 완료시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 05 : 위탁처리동의, NCCS_STAT = 02 : 진행)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때는 상태값만 업데이트하고 달리 부여되는 값은 없다. */
            if(httpServletRequest.getParameter("upp_tx_type").equals("05") && httpServletRequest.getParameter("nccs_stat").equals("02"))
            {
            	webNiceSenderService.updateNiceSendStatement2(hmParam);
                //webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                //webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트
            }

            /* 6. 본인인증 완료시 처리되는 내용들
             * (1) 고객의 상태값을 업데이트 한다. (UPP_TX_TYPE = 06 : 회원가입완료, NCCS_STAT = 04 : 완료)
             * (2) 고객의 전자서명접수과정에 대한 이력을 저장한다.
             * ps. 이때 IMG_FILE_NM(고객증서파일이름) 이 부여된다. */
            if(("06".equals(upp_tx_type) || "CHGIMG01".equals(data_type) || "CHGIMG02".equals(data_type)) && !"".equals(img_file))
            {
                List<Map<String, Object>> mList = webNiceSenderService.getMemberNiceRetrieve2(hmParam); // 다운로드 받을 파일의 nice_no2 파라미터 값을 알기위해 조회함.
                Map<String, Object> map = mList.get(0);
                Map<String, Object> hmParam4 = new HashMap<String, Object>();
                String nice_no2 = (String)map.get("nice_no2");

                List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceJoinDt(map);
                Map<String, Object> map1 = mList1.get(0);
                String join_dt = (String)map1.get("join_dt");

                System.out.println(join_dt);

                //String downPath	= "/app/powerservice/common/imgFile/"; // 다운로드 받을 서버의 디렉토리 path
                String downPath = "/app/nice/" + join_dt + "/";

                //권한 이라는데 되는건가?
                //String cmd = "chmod 777 " + downPath;
                //Runtime rt = Runtime.getRuntime();
                //Process p = rt.exec(cmd);
                //p.waitFor();

                //System.out.println("11111XXXXXXXXXXXXXXXXXXXXXLDJTESTXXXXXXXXXXXXXX:   " + downPath);
                File downDirectory = new File(downPath);
                //System.out.println("22222XXXXXXXXXXXXXXXXXXXXXLDJTESTXXXXXXXXXXXXXX:   " + downDirectory.exists());

                if (downDirectory.exists() == false)
                {
                    downDirectory.mkdir();
                }
                //System.out.println("33333XXXXXXXXXXXXXXXXXXXXXLDJTESTXXXXXXXXXXXXXX:   ");

                String[] fileArray	= img_file.split("\\|");

                for(int i=0;i< fileArray.length; i++)
                {
                    //httpConnect.fileDownload("dev", fileArray[i], downPath, nice_no2); // 운영계. "dev" ("real", fileArray[i], downPath, nice_no2)
                    httpConnect.fileDownload(niceOperation, fileArray[i], downPath, nice_no2);
                }

                System.out.println("44444XXXXXXXXXXXXXXXXXXXXXLDJTESTXXXXXXXXXXXXXX:   ");

                hmParam.put("img_file_nm", img_file);              // 이미지 파일명
                hmParam.put("img_file_path", downPath + img_file); // 이미지가 존재하는 file path

             // NICE에서 리턴받은뒤 응답.
                hmParam2.put("data_type"       , data_type);
                hmParam2.put("corporation_code", corporation_code);
                hmParam2.put("app_no"          , app_no);
                hmParam2.put("nice_no"         , nice_no);
                hmParam2.put("nccs_stat"       , nccs_stat);
                hmParam2.put("memo_no"         , memo_no);
                hmParam2.put("upp_tx_type"     , upp_tx_type);
                hmParam2.put("result_code"     , result_code);
                hmParam2.put("result_memo"     , result_memo);

                //httpConnect.makeParam(hmParam2, "dev", "euc-kr");
                httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", socketSendingTime);

                hmParam3.put("app_no", app_no);
                hmParam3.put("finish_flag", "2Y");

                webNiceSenderService.updateNiceFinishStatement2(hmParam3);
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("응답받은뒤 다시 NICE로 전송하는 값 ::: " + hmParam2.toString());
                System.out.println("----------------------------------------------------------------------------------------------------");


                /*해피콜 상태 업데이트*/
                String vACCNT_NO = (String)map.get("accnt_no");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ : " + vACCNT_NO);

                hmParam4.put("hpcl_stat_cd", "01");  // 승인
                hmParam4.put("amnd_id", "ADMIN"); // 테스트유저
                hmParam4.put("accnt_no", vACCNT_NO);
                hmParam4.put("rgsr_id", "ADMIN");

                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("해피콜 업데이트 전송하는 값 ::: " + hmParam4.toString());
                System.out.println("----------------------------------------------------------------------------------------------------");

                // 해피콜 상태 UPDATE (HP_CALL),
                // 해피콜이력  INSERT (HP_CALL_HIST)
                custBasiService.updateHpclAckd(hmParam4);

                webNiceSenderService.updateNiceSendStatement2(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                //webNiceSenderService.updateNiceSendStatement(hmParam);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                //webNiceSenderService.insertMemberNiceInfoHist(hmParam); // NICE신용정보 전자서명 테이블 히스토리 인서트

            }

            /* 7. 취소전문 리턴시 처리되는 내용등
             * (1) 고객의 상태값을 업데이트 한다. (전문 데이터 타입 : CANREQ01)
             * (2) 특정 고객의 접수를 취소한다.
             * ps. 이때 고객에게 발송된 알림톡은 취소되어 모바일에서 접속시 진행되지 않는다. */
            if("CANREQ01".equals(data_type))
            {
                List<Map<String, Object>> mList = webNiceSenderService.getMemberNiceRetrieve(hmParam); // 고객의 safekey와 성별을 조회함.
                Map<String, Object> map = mList.get(0);

                System.out.println("nice_no2 의 값 ::: " + map.get("nice_no2"));

                // 2차 취소 전문 처리시 과정
                if(map.get("upp_tx_type").equals("04") == true || (map.get("upp_tx_type")).equals("05") == true)
                {
                    System.out.println("나이스2차값이 있다.");
                    hmParam3.put("extend_data", "2차취소전문"); // 취소전문구분 2차
                    hmParam3.put("finish_flag", "1Y");
                    hmParam3.put("nccs_stat"  , "04");
                    hmParam3.put("upp_tx_type", "03");
                    hmParam3.put("app_no"     , app_no);
                    webNiceSenderService.updateExceptionHandlerNiceInfo2(hmParam3);
                    webNiceSenderService.updateExceptionHandlerTrgtCust2(hmParam3);
                    webNiceSenderService.updateNiceSendStatement(hmParam3);  // NICE신용정보 전자서명 테이블 상태및 추가사항 업데이트
                    webNiceSenderService.insertMemberNiceInfoHist(hmParam3); // NICE신용정보 전자서명 테이블 히스토리 인서트
                }
                // 1차 취소 전문 처리시 과정
                else if(map.get("upp_tx_type").equals("01") || (map.get("upp_tx_type")).equals("02") == true)
                {
                    System.out.println("나이스2차값이 없다.");
                    hmParam3.put("extend_data", "1차취소전문"); // 취소전문구분 1차
                    hmParam3.put("finish_flag", "");
                    hmParam3.put("upp_tx_type", "");
                    hmParam3.put("app_no"      , app_no);
                    webNiceSenderService.deleteExceptionHandlerNiceInfo(hmParam3);
                    webNiceSenderService.updateExceptionHandlerTrgtCust(hmParam3);
                    webNiceSenderService.insertMemberNiceInfoHist(hmParam3); // NICE신용정보 전자서명 테이블 히스토리 인서트
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, "1111");
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 "3333");
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        return "/niceTestSuccess.jsp";
    }

    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : 고객 증서 파일 미리보기시 사전 점검 사항.
     * - 파일이 있으면 있는 파일을 미리보기 해줌
     * - 파일이 없으면 다시 다운로드 요청뒤 미리보기 해줌
     * ================================================================
     * */
    @RequestMapping(value = "/getNicePdfFile")
    public ModelAndView getNicePdfFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList01 = webNiceSenderService.getMemberNiceRetrieve2(hmParam);
            Map<String, Object> map1 = mList01.get(0);

            //System.out.println("map의값 ::: " + map.toString());
            //List<Map<String, Object>> mList02 = webNiceSenderService.getMemberNiceJoinDt(map);
            //Map<String, Object> map1 = mList02.get(0);
            //System.out.println("map1의값 ::: " + map1.toString());
            //String join_dt = (String)map1.get("join_dt");

            String img_file_path = "";
            String img_file_name = "";
            String trgt_cust_dtpt_id = "";
            String join_dt = "";

            if (mList01.size() <= 0)
            {
                System.out.println("2차전송이 되지 않은 회원입니다.");
            }
            else
            {
                img_file_path = (String)map1.get("img_file_path");
                img_file_name = (String)map1.get("img_file_nm");
                trgt_cust_dtpt_id = (String)map1.get("trgt_cust_dtpt_id");
                join_dt = (String)hmParam.get("join_dt");

                if(img_file_path == null || img_file_path.equals(""))
                {
                    System.out.println("회원가입완료가 되지 않은건입니다.");
                }
                else
                {
                    //File file = new File("http://61.39.175.227:8080/powerservice/common/imgFile/"+"TAR201802190610556.pdf");
                    File file = new File(img_file_path);
                    if (file.exists() == true)
                    {
                        System.out.println("있는파일입니다.");
                    }
                    else
                    {
                        System.out.println("없는파일이므로 다운로드를 다시 수행합니다.");

                        String nice_no2 = (String)map1.get("nice_no2");

                        String img_file = "";
                        //String downPath	= "/app/powerservice/common/imgFile/";
                        String downPath =  "/app/nice/" + join_dt + "/";

                        File downDirectory = new File(downPath);

                        if (downDirectory.exists() == false)
                        {
                            downDirectory.mkdir();
                        }

                        String[] fileArray	= img_file.split("\\|");

                        for(int i=0;i< fileArray.length; i++)
                        {
                            //httpConnect.fileDownload("dev", fileArray[i], downPath, nice_no2); // 운영계. ("real", fileArray[i], downPath, nice_no2)
                            httpConnect.fileDownload(niceOperation, fileArray[i], downPath, nice_no2); // 운영계. ("real", fileArray[i], downPath, nice_no2)
                        }

                        hmParam.put("img_file_nm", img_file);              // 이미지 파일명
                        hmParam.put("img_file_path", downPath + img_file); // 이미지가 존재하는 filepath
                    }
                }
            }

            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20171222
     * 이름 : 송준빈
     * 추가내용 :
     * ================================================================
     * */
    @RequestMapping(value = "/getNiceSenderInfoList")
    public ModelAndView getMemberBasicAllAmtInfomation(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            int nTotal = webNiceSenderService.getTotalCount(hmParam);
            mapOutVar.put("fv_tc", nTotal);

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
                //{ROW_TYPE=0, pageNo=1, endNum=101, startNum=1, reqCount=100}
            }

            List<Map<String, Object>> mList01 = webNiceSenderService.getNiceSenderInfoList(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/sendNiceCancel")
    public ModelAndView sendNiceCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam2 = new HashMap<String, String>();	//전문 값을 담을 Map 생성

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1");

            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            /**
                고객사 AP에서 DB값을 불러와 취소할 계약건의 app_no, nice_no, cancel_reason, cancel_manager, memo
                값을 map에 세팅 - 고객사 구현 구간
            */

            List<Map<String, Object>> mList = webNiceSenderService.getMemberNiceRetrieve(hmParam1);
            Map<String, Object> map = mList.get(0);

            //접수 공통부
            hmParam2.put("data_type"       , "CANREQ01"); 		    				     // 전문 식별 값
            hmParam2.put("corporation_code", "DMLF01");						   		     // 고객사 업체 코드
            hmParam2.put("app_no"          , (String)hmParam1.get("trgt_cust_dtpt_id")); // 고객 식별 번호

            if(map.get("upp_tx_type").equals("04") == true || map.get("upp_tx_type").equals("05") == true)
            {
                hmParam2.put("nice_no"         , (String)map.get("nice_no2"));				 // NICE계약건 번호
            }
            else if(map.get("upp_tx_type").equals("01") == true || map.get("upp_tx_type").equals("02") == true)
            {
                hmParam2.put("nice_no"         , (String)map.get("nice_no"));				 // NICE계약건 번호
            }

            hmParam2.put("cancel_reason"   , "01");								   		 // 취소사유
            hmParam2.put("cancel_manager"  , (String)hmParam1.get("rgsr_id"));   		 // 취소 담당자 성명
            hmParam2.put("memo"            , "고객의 사정/ 처리오류로 취소합니다.");				 // 비고사항

            if(map.get("upp_tx_type").equals("04") == true || map.get("upp_tx_type").equals("05") == true)
            {
                hmParam2.put("finish_flag"         , "2N");				 // NICE계약건 번호
            }
            else if(map.get("upp_tx_type").equals("01") == true || map.get("upp_tx_type").equals("02") == true)
            {
                hmParam2.put("finish_flag"         , "1N");				 // NICE계약건 번호
            }

            hmParam1.putAll(hmParam2);
            webNiceSenderService.updateNiceFinishStatement(hmParam1);

            //함수 호출
            //httpConnect.makeParam(hmParam2, "dev", "euc-kr");
            httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", socketSendingTime);

            System.out.println("hmParam3 ::: " + hmParam2.toString());
            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            /*
            if(hmParam1.get("upp_tx_type").equals("04") == true)
            {
                hmParam2.put("finish_flag"         , "1Y");				 // NICE계약건 번호
            }
            else if(hmParam1.get("upp_tx_type").equals("01") == true)
            {
                hmParam2.put("finish_flag"         , "");				 // NICE계약건 번호
            }
            webNiceSenderService.updateNiceFinishStatement(hmParam1);
            */
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/resendKaKao")
    public ModelAndView resendKaKao(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, String> hmParam3 = new HashMap<String, String>();	//전문 값을 담을 Map 생성

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // ds_list_trgtCust     (고객타겟리스트 [캠페인])
            DataSetMap srchInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // ds_list_trgtCustAlct (타켓리스트 [캠페인])


            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            if (srchInDs2 != null && srchInDs2.size() > 0)
            {
                hmParam2 = srchInDs2.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            hmParam1.putAll(hmParam2);

            /**
                고객사 AP에서 DB값을 불러와 취소할 계약건의 app_no, nice_no, cancel_reason, cancel_manager, memo
                값을 map에 세팅 - 고객사 구현 구간
            */

            List<Map<String, Object>> mList = webNiceSenderService.getMemberNiceRetrieve(hmParam1);
            Map<String, Object> map = mList.get(0);

            //접수 공통부
            hmParam3.put("corporation_code", "DMLF01");						   		     // 고객사 업체 코드

            if((map.get("upp_tx_type")).equals("04") == true || (map.get("upp_tx_type")).equals("05") == true)
            {
                hmParam3.put("nice_no"         , (String)map.get("nice_no2"));				 // NICE계약건 번호
            }
            else if((map.get("upp_tx_type")).equals("01") == true || (map.get("upp_tx_type")).equals("02") == true)
            {
                hmParam3.put("nice_no"         , (String)map.get("nice_no"));				 // NICE계약건 번호
            }
            else
            {
            	System.out.println("재전송할수 있는 대상이 아닙니다");
            }

            //hmParam1.putAll(hmParam2);

            //함수 호출
            //httpConnect.makeParam(hmParam2, "dev", "euc-kr");

            System.out.println("hmParam3 ::: " + hmParam3.toString());
            httpConnect.resendKakao(hmParam3, niceOperation, "euc-kr");

            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/resendKaKao2")
    public ModelAndView resendKaKao2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam3 = new HashMap<String, String>();	//전문 값을 담을 Map 생성

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // ds_list_trgtCust     (고객타겟리스트 [캠페인])

            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            /**
                고객사 AP에서 DB값을 불러와 취소할 계약건의 app_no, nice_no, cancel_reason, cancel_manager, memo
                값을 map에 세팅 - 고객사 구현 구간
            */

            List<Map<String, Object>> mList = webNiceSenderService.getMemberNiceRetrieve2(hmParam1);
            Map<String, Object> map = mList.get(0);

            //접수 공통부
            hmParam3.put("corporation_code", "DMLF01");						   		     // 고객사 업체 코드

            if((map.get("upp_tx_type")).equals("04") == true || (map.get("upp_tx_type")).equals("05") == true)
            {
                hmParam3.put("nice_no"         , (String)map.get("nice_no2"));				 // NICE계약건 번호
            }
            else
            {
            	System.out.println("재전송할수 있는 대상이 아닙니다");
            }

            //함수 호출
            //httpConnect.makeParam(hmParam2, "dev", "euc-kr");

            System.out.println("hmParam3 ::: " + hmParam3.toString());
            httpConnect.resendKakao(hmParam3, niceOperation, "euc-kr");

            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/resendNicePdfFile")
    public ModelAndView resendNicePdfFile(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Socket socket = null;

        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>(); // ds_member          // 고객증서정보
        Map<String, String> hmParam2 = new HashMap<String, String>(); // nice 전송 파라미터

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            System.out.println("NICE 전문 송신을 위한 연결을 시도합니다.");
            socket = new Socket("172.28.220.42", 80);    // 운영계 real 일때
            //socket = new Socket("172.28.220.43", 80); // 개발계 dev 일때
            System.out.println("NICE 전문 송신을 위한 연결이 맺어졌습니다.");

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // 회원번호,고객타켓리스트번호
            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            System.out.println("고객 기본정보 ::: " + hmParam1);
            System.out.println("------------------------------------------");
            System.out.println("나이스 전송 파라미터 ::: " + hmParam2);
            System.out.println("------------------------------------------");

            //List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceRetrieve(hmParam1); // 고객 타켓리스트번호(NICE 전문 식별값) 을 가지고 오기 위함임.
            //Map<String, Object> oApp_no = mList1.get(0);

            List<Map<String, Object>> mList2 = webNiceSenderService.getCertfMng(hmParam1); // 고객의 증서 내용을 가지고 오기 위함임.
            Map<String, Object> hmParam3 = mList2.get(0);

            List<Map<String, Object>> mList3 = webNiceSenderService.getResnAmt(hmParam1); // 고객의 해약금에 대한 정보를 가지고 오기 위함임.

            webNiceSenderService.updateResendNicePdfFile(hmParam1);

            /* 접수 공통부 파라미터 */
            hmParam2.put("data_type"       , "SICINF01"); // 전문코드(접수)
            hmParam2.put("corporation_code", "DMLF01"); // 대명DLCC
            hmParam2.put("app_no"          , (String)hmParam3.get("accnt_no")); // 고객유일식별값(협의)
            hmParam2.put("app_type"        , "01"); // 접수구분값 (신규접수 01)
            hmParam2.put("member_type"	   , "01"); // 징구주체값 (본인 01)
            hmParam2.put("app_reason"      , "01"); // 접수사유값 (01로 고정)
            hmParam2.put("contract_type"   , "02"); // 계약구분값 (01 : 1차접수, 02 : 2차접수 여기를 이용하여 1차와 2차를 구분하겠습니다)

            /* 접수 개별부 파라미터 */
            String tel  = (String)hmParam3.get("cell_full");
            String brth = (String)hmParam3.get("brth_mon_day");

            hmParam2.put("name",  (String)hmParam3.get("mem_nm")); // 고객명

            if(tel.length() == 13)
            {
                hmParam2.put("ph21",  tel.substring(0, 3));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  tel.substring(4, 8));     // 고객 휴대폰번호 가운데자리 4
                hmParam2.put("ph23",  tel.substring(9, 13));    // 고객 휴대폰번호 끝자리 4
            }
            else if(tel.length() == 12)
            {
                hmParam2.put("ph21",  tel.substring(0, 3));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  tel.substring(4, 7));     // 고객 휴대폰번호 가운데자리 3
                hmParam2.put("ph23",  tel.substring(8, 12));    // 고객 휴대폰번호 끝자리 4
            }
            else
            {

            }

            hmParam2.put("ssn11"           , brth.substring(2, 8));    // 고객 생년월일 6자리

            hmParam2.put("member_no"         , (String)hmParam3.get("mem_no"          )); // 고객고유번호
            hmParam2.put("contract_code"     , (String)hmParam3.get("accnt_no"        )); // 고객회원번호
            hmParam2.put("contract_date"     , (String)hmParam3.get("join_dt"         )); // 가입일자
            //hmParam2.put("zip21"       	 	 , (String)hmParam3.get("home_zip"        )); // 우편번호
            String sZip = (String)hmParam3.get("home_zip"        );
            hmParam2.put("zip21"       	 	 , sZip.substring(0, 3)); // 우편번호 앞자리
            hmParam2.put("zip22"       	 	 , sZip.substring(3, 5)); // 우편번호 뒷자리
            hmParam2.put("addr21"       	 , (String)hmParam3.get("home_addr"       )); // 주소1
            hmParam2.put("addr22"      	     , (String)hmParam3.get("home_addr2"      )); // 주소2
            hmParam2.put("goods_key"         , (String)hmParam3.get("prod_cd"         )); // 고객상품코드
            hmParam2.put("loan_name"         , (String)hmParam3.get("prod_nm"         )); // 상품명(상품정보1)
            hmParam2.put("etc_01"        	 , (String)hmParam3.get("prod_amt"        )); // 상품금액
            hmParam2.put("etc_02"     		 , (String)hmParam3.get("mon_pay_amt"     )); // 회차별납입액
            hmParam2.put("etc_03"         	 , hmParam3.get("expr_no"      ).toString()); // 만기회차
            hmParam2.put("loan_name2"		 , (String)hmParam3.get("prod_join_prt_nm")); // 상품명(상품정보2)
            hmParam2.put("etc_04"  			 , (String)hmParam3.get("prod_buga_val2"  )); // 계약금액
            hmParam2.put("etc_05"  			 , (String)hmParam3.get("prod_buga_val3"  )); // 월납입금
            hmParam2.put("etc_06"  			 , (String)hmParam3.get("prod_buga_val4"  )); // 계약유형
            hmParam2.put("hope_contract_day" , (String)hmParam3.get("ichae_dt"        )); // 결제일자
            hmParam2.put("invo_corp"         , (String)hmParam3.get("bank_nm"         )); // 카드사 / 은행명
            hmParam2.put("invo_no"           , (String)hmParam3.get("bank_accnt_no"   )); // 카드번호 / 계좌번호

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("bank_owner_name"   , (String)hmParam3.get("depr"            )); // 예금주
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("bank_owner_name"   , " "); // 예금주
            }

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("invo_ca"     		 , " "); // 유효기간
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("invo_ca"           , (String)hmParam3.get("expire_date"     )); // 유효기간
            }

            hmParam2.put("info_01"           , (String)hmParam3.get("coffin1"         )); // 매장
            hmParam2.put("info_02"           , (String)hmParam3.get("coffin2"         )); // 화장/탈관
            hmParam2.put("info_03"           , (String)hmParam3.get("shroud_m1"       )); // 수의 1
            hmParam2.put("info_04"       	 , (String)hmParam3.get("shroud_d1"       )); // 수의 1 내용
            hmParam2.put("info_05"       	 , (String)hmParam3.get("shroud_m2"       )); // 수의 2
            hmParam2.put("info_06"       	 , (String)hmParam3.get("shroud_d2"       )); // 수의 2 내용
            hmParam2.put("info_07"     		 , (String)hmParam3.get("coffin_gds1"     )); // 입관/수시용품1
            hmParam2.put("info_08"     		 , (String)hmParam3.get("coffin_gds2"     )); // 입관/수시용품2
            hmParam2.put("info_09"   		 , (String)hmParam3.get("mortuary_gds1"   )); // 빈소/기타용품1
            hmParam2.put("info_10"   		 , (String)hmParam3.get("mortuary_gds2"   )); // 빈소/기타용품2
            hmParam2.put("info_11"           , (String)hmParam3.get("car1"            )); // 이송차량
            hmParam2.put("info_12"           , (String)hmParam3.get("car2"            )); // 리무진
            hmParam2.put("info_13"           , (String)hmParam3.get("car3"            )); // 유족버스
            hmParam2.put("info_14"       	 , (String)hmParam3.get("flower_m1"       )); // 제단 장식 1
            hmParam2.put("info_15"       	 , (String)hmParam3.get("flower_d1"       )); // 제단 장식 1 내용
            hmParam2.put("info_16"       	 , (String)hmParam3.get("flower_m2"       )); // 제단 장식 2
            hmParam2.put("info_17"       	 , (String)hmParam3.get("flower_d2"       )); // 제단 장식 2 내용
            hmParam2.put("info_18"       	 , (String)hmParam3.get("flower_m3"       )); // 제단 장식 3
            hmParam2.put("info_19"       	 , (String)hmParam3.get("flower_d3"       )); // 제단 장식 3 내용
            hmParam2.put("info_20"			 , (String)hmParam3.get("funeral_clothes1")); // 현대식
            hmParam2.put("info_21"			 , (String)hmParam3.get("funeral_clothes2")); // 전통식 1
            hmParam2.put("info_22"			 , (String)hmParam3.get("funeral_clothes3")); // 전통식 2
            hmParam2.put("info_23"       	 , (String)hmParam3.get("helper_m1"       )); // 인력 1
            hmParam2.put("info_24"       	 , (String)hmParam3.get("helper_d1"       )); // 인력 1 내용
            hmParam2.put("info_25"       	 , (String)hmParam3.get("helper_m2"       )); // 인력 2
            hmParam2.put("info_26"       	 , (String)hmParam3.get("helper_d2"       )); // 인력 2 내용
            hmParam2.put("info_27"       	 , (String)hmParam3.get("helper_m3"       )); // 인력 3
            hmParam2.put("info_28"       	 , (String)hmParam3.get("helper_d3"       )); // 인력 3 내용
            hmParam2.put("info_29"           , (String)hmParam3.get("cmetc"           )); // 비고
            hmParam2.put("info_30"           , (String)hmParam3.get("etc_val"         )); // 비고내용
            hmParam2.put("info_31"       	 , (String)hmParam3.get("terms_dtl"       )); // 장의상품 상세정보
            hmParam2.put("info_32"        	 , (String)hmParam3.get("consm_nt"        )); // 소비자 유의사항
            hmParam2.put("info_33"       	 , (String)hmParam3.get("refund_nt"       )); // 환급기준 및 환급시기
            hmParam2.put("info_34"      	 , (String)hmParam3.get("refund_amt"      )); // 총 고객환급의무액
            hmParam2.put("info_35"           , (String)hmParam3.get("asset"           )); // 상조관련 자산
            hmParam2.put("info_36"   		 , (String)hmParam3.get("consm_amt_mng"   )); // 고객 불입금 관리방법

            String sResn_amt = "";

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(mList3.toString());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for(int idx = 0; idx < mList3.size(); idx++)
            {
                sResn_amt = sResn_amt + mList3.get(idx).get("no") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("pct") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("resn_amt") + ";";
            }

            hmParam2.put("resn_amt"   , sResn_amt); // 해약환금율
            hmParam2.put("info_37"    , (String)hmParam3.get("refund_math1"    )); // 해약환급금 산식(정기형)
            hmParam2.put("info_38"    , (String)hmParam3.get("refund_math2"    )); // 해약환급금 산식(부정기형)
            hmParam2.put("info_39"    , (String)hmParam3.get("refund_math3"    )); // 해약환급금 산식(부정기형) 밑에 내용
            hmParam2.put("info_40"    , (String)hmParam3.get("pay_mthd"        )); // 납입방식 04 : CMS, 06: Card

            hmParam2.put("info_41"    , (String)hmParam3.get("terms_svc"    	)); // 2018.05.10 추가 토탈라이프서비스
            hmParam2.put("info_42"    , (String)hmParam3.get("resn_amt_info"    )); // 2018.05.10 추가 실 해약환급내용 추가

            hmParam1.putAll(hmParam2);
            hmParam1.put("brth_mon_day",  brth);                 // 고객 생년월일
            hmParam1.put("clph_tlno",  tel.replaceAll("-", "")); // 고객 전화번호
            hmParam1.put("cust_nm",  (String)hmParam3.get("mem_nm"));   // 고객명
            hmParam1.put("mem_no",   (String)hmParam3.get("mem_no"));   // 고객고유번호
            hmParam1.put("accnt_no", (String)hmParam3.get("accnt_no")); // 고객회원번호
            hmParam1.put("prod_cd",  (String)hmParam3.get("prod_cd"));  // 상품코드

            System.out.println("-----------------------------------");
            System.out.println("2차접수값(증서재발송) :: " + hmParam1.toString());
            System.out.println("-----------------------------------");
            System.out.println(tel.substring(0, 3) + "::" + tel.substring(4, 8) + "::" + tel.substring(9, 13) );
            System.out.println("-----------------------------------");
            System.out.println("2차접수값(증서재발송) :: " + sResn_amt);
            System.out.println("-----------------------------------");

            int result3 = webNiceSenderService.insertMemberNiceInfoHist(hmParam1); // NICE신용정보 전자서명 테이블 히스토리 인서트
            httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", 1000*30); // NICE 정보발송 "dev" 개발계 "real" 운영계
            int result1 = webNiceSenderService.updateMemberNiceInfo2(hmParam1);    // 고객 타켓리스트 테이블 업데이트 (고객고유번호, 고객회원번호)
            int result2 = webNiceSenderService.updateMemberNiceInfo3(hmParam1);    // NICE신용정보 전자서명 테이블 업데이트 (고객고유번호, 고객회원번호)

            //httpConnect.makeParam(hmParam2, "dev", "euc-kr"); // NICE 정보발송 "dev" 개발계 "real" 운영계

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (IOException ioe)
        {
            System.out.println("NICE 전문 송신을 위한 연결이 실패하였습니다.");
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage() + " ::: NICE 전문 송신을 위한 연결이 실패하였습니다.";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금정보 전체 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getAcuonLatelyAuth")
    public ModelAndView getAcuonLatelyAuth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input1");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList01 = webNiceSenderService.getAcuonLatelyAuth(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명인증고객 관리 List
     * ================================================================
     * */
    @RequestMapping(value = "/getNiceAuthInformation")
    public ModelAndView getNiceAuthInformation(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            } 

            List<Map<String, Object>> mList01 = webNiceSenderService.getNiceAuthInformation(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명가입고객 관리 List
     * ================================================================
     * */
    @RequestMapping(value = "/getNiceJoinInformation")
    public ModelAndView getNiceJoinInformation(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            } 

            List<Map<String, Object>> mList01 = webNiceSenderService.getNiceJoinInformation(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명인증고객 일괄재전송 
     * ================================================================
     * */
    @RequestMapping(value = "/sendResendKakaoAuth")
    public ModelAndView sendResendKakaoAuth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam2 = new HashMap<String, String>();	//전문 값을 담을 Map 생성
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsResendKakaoAuth1 = (DataSetMap)mapInDs.get("ds_input");
            
            for(int idx = 0; idx < dsResendKakaoAuth1.size(); idx++)
            {
            	System.out.println("데이터셋의 Rows 내용 : " + dsResendKakaoAuth1.get(idx));
            	System.out.println("upp_tx_type상태 ::: " + dsResendKakaoAuth1.get(idx).get("upp_tx_type"));
            	System.out.println("처리 NICE 번호 ::: " + (String)dsResendKakaoAuth1.get(idx).get("nice_no"));
            	
            	if( (dsResendKakaoAuth1.get(idx).get("upp_tx_type")).equals("01") == true || 
            		(dsResendKakaoAuth1.get(idx).get("upp_tx_type")).equals("02") == true)
                {
            		String sCorporationCode = "DMLF01";
            		String sNiceNo1 = (String)dsResendKakaoAuth1.get(idx).get("nice_no");
            	
            		hmParam2.put("corporation_code", sCorporationCode);
            		hmParam2.put("nice_no", sNiceNo1);
            	
            		httpConnect.resendKakao(hmParam2, niceOperation, "euc-kr");
                }
            }
            
            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명가입고객 일괄재전송 
     * ================================================================
     * */
    @RequestMapping(value = "/sendResendKakaoJoin")
    public ModelAndView sendResendKakaoJoin(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam2 = new HashMap<String, String>();	//전문 값을 담을 Map 생성
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsResendKakaoJoin = (DataSetMap)mapInDs.get("ds_input");

            for(int idx = 0; idx < dsResendKakaoJoin.size(); idx++)
            {
            	System.out.println("upp_tx_type상태 ::: " + dsResendKakaoJoin.get(idx).get("upp_tx_type_auth_2"));
            	System.out.println("처리 NICE 번호 ::: " + (String)dsResendKakaoJoin.get(idx).get("nice_no_2"));
            	
            	if( (dsResendKakaoJoin.get(idx).get("upp_tx_type_auth_2")).equals("04") == true || 
            		(dsResendKakaoJoin.get(idx).get("upp_tx_type_auth_2")).equals("05") == true)
                {
            		String sCorporationCode = "DMLF01";
            		String sNiceNo2 = (String)dsResendKakaoJoin.get(idx).get("nice_no_2");
            	
            		hmParam2.put("corporation_code", sCorporationCode);
            		hmParam2.put("nice_no", sNiceNo2);
            	
            		httpConnect.resendKakao(hmParam2, niceOperation, "euc-kr");
                }
            }
            
            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20181015
     * 이름 : 송준빈
     * 추가내용 : 전자서명가입고객데이터 삭제 
     * ================================================================
     * */
    @RequestMapping(value = "/deleteNiceJoinData")
    public ModelAndView deleteNiceJoinData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam2 = new HashMap<String, String>();	//전문 값을 담을 Map 생성
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsDeleteKakaoJoin = (DataSetMap)mapInDs.get("ds_input");

            for(int idx = 0; idx < dsDeleteKakaoJoin.size(); idx++)
            {
            	String sUppTxTypeAuth2 = (String)dsDeleteKakaoJoin.get(idx).get("upp_tx_type_auth_2");
            	String sNiceNo1        = (String)dsDeleteKakaoJoin.get(idx).get("nice_no_1");
            	String sNiceNo2        = (String)dsDeleteKakaoJoin.get(idx).get("nice_no_2");
            	
            	System.out.println("upp_tx_type상태 ::: " + sUppTxTypeAuth2);
            	System.out.println("삭제 NICE1 번호 ::: " + sNiceNo1);
            	System.out.println("삭제 NICE2 번호 ::: " + sNiceNo2);
            	
            	if( (sUppTxTypeAuth2 == null)              ||
            		(sUppTxTypeAuth2.equals(""))           ||
            		(sUppTxTypeAuth2.equals("04") == true) || 
            		(sUppTxTypeAuth2.equals("05") == true) )
                {	
            		webNiceSenderService.updateNiceJoinData(dsDeleteKakaoJoin.get(idx));
                }
            } 
            
            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
}
