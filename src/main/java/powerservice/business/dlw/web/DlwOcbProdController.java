/*
 * (@)# DlwOcbProdController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
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

package powerservice.business.dlw.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//2018.01.25 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwOcbProdService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
/**
 * 대명라이프웨이 OCB 상품 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
 * @프로그램ID DlwOcbProd
 */
@Controller
@RequestMapping(value = "/dlw/ocb-prod")
public class DlwOcbProdController {

    @Resource
    private DlwOcbProdService DlwOcbProdService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private CommonService commonService;

    /**
     * 대명라이프웨이 OCB 상품 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getOcbProdMstList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = DlwOcbProdService.getOcbProdCount(hmParam);
                mapOutVar.put("tc_ocbProd", nTotal);

                List<Map<String, Object>> mList = DlwOcbProdService.getOcbProdList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

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

    /**
     * 대명라이프웨이 OCB 상품 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge")
    public ModelAndView mergeOcbProd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int ocbProdCnt = DlwOcbProdService.getOcbProdSaveCount(hmParam);
                String mode = (String)hmParam.get("mode");
                if (ocbProdCnt == 0 || "update".equals(mode)) {
                    int result = 0;
                    if("insert".equals(mode)) {
                        result = DlwOcbProdService.insertOcbProd(hmParam);
                    } else if ("update".equals(mode)) {
                        result = DlwOcbProdService.updateOcbProd(hmParam);
                    }
                    if (result == 1) {
                        mapOutVar.put("result_msg", "success");
                    } else {
                        mapOutVar.put("result_msg", "fail");
                    }
                }
            } else {
                mapOutVar.put("result_msg", "noSuccess");
            }

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

    /**
     * 대명라이프웨이 OCB 상품 정보 등록 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/reg-list")
    public ModelAndView getOcbProdRegList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                int nTotal = DlwOcbProdService.getOcbTransHistCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList = DlwOcbProdService.getOcbTransHistList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.25 접속 로그////////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////
            }

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

    /**
     * 대명라이프웨이 OCB 상품 정보 등록 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-reg-list")
    public ModelAndView getOcbProdTransList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보

                List<Map<String, Object>> mList = DlwOcbProdService.getOcbTransList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.01.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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

    /**
     * 대명라이프웨이 OCB 상품 정보 취소 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-del-list")
    public ModelAndView getOcbProdDelList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sendType = "";
            sendType = (String)mapInVar.get("sendtype");

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보

                hmParam.put("settype", sendType);

                List<Map<String, Object>> mList = DlwOcbProdService.getOcbDelList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.01.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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

    /**
     * 대명라이프웨이 OCB 상품 정보 등록 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-add-list")
    public ModelAndView getOcbProdAddList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();


        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보

                String sendType = "";
                sendType = (String)mapInVar.get("sendtype");

                hmParam.put("settype", sendType);

                List<Map<String, Object>> mList = DlwOcbProdService.getOcbAddList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.01.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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

    /**
     * 대명라이프웨이 OCB 정보를 전송한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-reg")
    public ModelAndView transOcbProdReg(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        String sendType = "";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            /*******************************************************/
            if (listInDs2.size() > 0) {
                hmParam2 = listInDs2.get(0);
            }

            Map<String, Object> OCBinfo = new HashMap<String, Object>();
            String stt_dt = (String)hmParam2.get("stt_dt");
            String end_dt = (String)hmParam2.get("end_dt");
            int listCnt = listInDs.size();
            long listAmt = 0L;

            String ocbPoint = null;
            String doc_no = "";
            String member_store = "";
            String contract_code = "";
            String chit_code = "";					// 전표코드
            String bussiness_yn = "";					// 거래구분 코드
            String password = "";

            int successCnt = 0;
            int failCnt = 0;

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    ocbPoint = (String)hmParam.get("ocb_point");
                    Date now = new Date();

                    sendType = (String)mapInVar.get("sendtype");

                    switch(sendType){
                    case "reg" :
                        OCBinfo.put("ocb_gubun", "K100"); //서비스 전문 번호
                        doc_no = "K100";			      //서비스 전문 번호
                        member_store = "30031802";
                        // 기존 ‘0R’로 보내고 있는 것을 ‘CG’로 변경해야 한다고합니다. 20181228 곽보람 수정요청
                        //contract_code = "0R";   // 계약구분코드
                        contract_code = "CG";   // 계약구분코드
                        chit_code = "01";       // 전표코드
                        bussiness_yn = "22";	// 거래구분 코드
                        password = "";
                        break;
                    case "sav" :
                        OCBinfo.put("ocb_gubun", "K300");
                        doc_no = "K100";
                        member_store = "30031923";
                        contract_code = "01";
                        chit_code = "01";
                        bussiness_yn = "22";
                        password = "";
                        break;
                    case "use" :
                        OCBinfo.put("ocb_gubun", "K200");
                        doc_no = "K400";
                        member_store = "30031923";
                        contract_code = "08";
                        chit_code = "11";
                        bussiness_yn = "40";
                        password = "FUSE";
                        break;
                    }


                    OCBinfo.put("ocb_point", ocbPoint);
                    OCBinfo.put("accnt_no", (String)hmParam.get("accnt_no"));
                    OCBinfo.put("reg_man", hmParam.get("reg_man"));
                    OCBinfo.put("mem_no", (String)hmParam.get("mem_no"));
                    OCBinfo.put("pay_cnt", (String)hmParam.get("pay_cnt"));
                    String ci_val = (String)hmParam.get("ci_val");
                    OCBinfo.put("ci_val", ci_val);
                    OCBinfo.put("stt_dt", stt_dt);
                    OCBinfo.put("end_dt", end_dt);

                    //
                    int HistCnt = DlwOcbProdService.getOcbTransHistCnt();

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");

                    String YYYYMMDD = format1.format(now);
                    String HHMMSS = format2.format(now);
                    OCBinfo.put("send_date", YYYYMMDD);


                    String doc_code = "7075"; 					// 대외기관코드참조
                                                                  // 전송일자 YYYYMMDD
                                                                  // 전송시간 HHMMSS
                    String seq_num = Integer.toString(HistCnt); // 추적번호 SEQ
                    String doc_gubun = "ON"; 					// 전문 구분
                    String doc_length = "0753";					// 본문길이
                    String response1 = "";						// 응답코드 대분류
                    String response2 = "";						// 응답코드 소분류
                    String filler = "";							// 빈공간
                    String grid_cnt = "001";					// 전체데이터 건수
                    String mem_prog_id = "A";					// OKCAHBAG
                    String mem_service = "A1";					// 멤버쉽구분
                    String pos_gunbun = "";						// 단말기 구분
                    String pos_id = "";							// 단말기 번호
                    String WCC = "0";							// MSR
                    //String member_store = "30031802";			// 가맹점번호
                    String Member_store_idn_no = "2208809321";  // 사업자 번호
                    String deal_date = YYYYMMDD;				// 거래일자
                    String deal_time = HHMMSS;					// 거래시간
                    String card_no = "";						// 카드번호
                    String idn_no = "";							// 개인사업자번호
                    String ci = ci_val;							// CI값
                    String filler2 = "";						// 빈공간
                    String mem_id = "";							// NXM회원ID
                    String store_member_id = "";				// 제휴사 회원 ID
                    String store_member_ip = "";				// 제휴사 회원 IP
                    String store_member_appect = "";			// 제휴사 승인번호
                    String message1 = "";						// 통합승인사용
                    String message2 = "";						// 통합승인사용
                    String message3 = "";						// 대외기관사용
                    String message4 = "";						// 대외기관 사용
                    //String chit_code = "01";					// 전표코드
                    //String bussiness_yn = "22";					// 거래구분 코드
                    String compound_pay_yn = "N";				// 복합결제 여부
                    String bussiness_tot_amt = ocbPoint;		// 거래금액 총 합계
                    //String contract_code = "0R";				// 계약구분 코드
                    String bussiness_pay_amt = ocbPoint;		// 거래금액(요청포인트)
                    String store_charge = "";					// 가맹점 수수료
                    String contract_code2 = "";					// 계약구분코드 2
                    String bussiness_pay_amt2 = "";				// 거래금액2(요청포인트2)
                    String store_charge2 = "";					// 가맹점수수료
                    //String password = "";						// 비밀번호
                    String cash_bussiness_amt = "";				// 현금거래금액
                    String supply_price = "";					// 공급가액
                    String value_added_tax = "";				// 부가가치세
                    String serice_price = "";					// 봉사료
                    String purpose = "0";						// 용도구분 (소비자소득공제용)
                    String cash_receipt = "1";					// 현금영수증처리구분(적립/할인 ONLY)
                    String web_service = "N";					// 웹서비스호출구분 (프레임웤 호출)
                    String filler3 = "";						// 빈공간
                    String grid_cnt1 = "000";					// SUB DATA건수

                    String tmp = "";
                    tmp = (new StringBuilder(String.valueOf(CommonUtils.fillEmpVal(4, doc_no, "L", " ")))).append(CommonUtils.fillEmpVal(4, doc_code, "L", " ")).append(CommonUtils.fillEmpVal(8, YYYYMMDD, "L", " ")).append(CommonUtils.fillEmpVal(6, HHMMSS, "L", " ")).append(CommonUtils.fillEmpVal(10, seq_num, "R", "0")).append(CommonUtils.fillEmpVal(2, doc_gubun, "L", " ")).append(CommonUtils.fillEmpVal(4, doc_length, "L", " ")).append(CommonUtils.fillEmpVal(2, response1, "L", " ")).append(CommonUtils.fillEmpVal(2, response2, "L", " ")).append(CommonUtils.fillEmpVal(8, filler, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt, "L", " ")).append(CommonUtils.fillEmpVal(1, mem_prog_id, "L", " ")).append(CommonUtils.fillEmpVal(2, mem_service, "L", " ")).append(CommonUtils.fillEmpVal(2, pos_gunbun, "L", " ")).append(CommonUtils.fillEmpVal(10, pos_id, "L", " ")).append(CommonUtils.fillEmpVal(1, WCC, "L", " ")).append(CommonUtils.fillEmpVal(15, member_store, "L", " ")).append(CommonUtils.fillEmpVal(10, Member_store_idn_no, "L", "0")).append(CommonUtils.fillEmpVal(8, deal_date, "L", " ")).append(CommonUtils.fillEmpVal(6, deal_time, "L", " ")).append(CommonUtils.fillEmpVal(37, card_no, "L", " ")).append(CommonUtils.fillEmpVal(13, idn_no, "L", " ")).append(CommonUtils.fillEmpVal(88, ci, "R", " ")).append(CommonUtils.fillEmpVal(50, filler2, "L", " ")).append(CommonUtils.fillEmpVal(9, mem_id, "L", " ")).append(CommonUtils.fillEmpVal(40, store_member_id, "L", " ")).append(CommonUtils.fillEmpVal(25, store_member_ip, "L", " ")).append(CommonUtils.fillEmpVal(12, store_member_appect, "L", " ")).append(CommonUtils.fillEmpVal(64, message1, "L", " ")).append(CommonUtils.fillEmpVal(64, message2, "L", " ")).append(CommonUtils.fillEmpVal(64, message3, "L", " ")).append(CommonUtils.fillEmpVal(64, message4, "L", " ")).append(CommonUtils.fillEmpVal(2, chit_code, "L", " ")).append(CommonUtils.fillEmpVal(2, bussiness_yn, "L", " ")).append(CommonUtils.fillEmpVal(1, compound_pay_yn, "R", "N")).append(CommonUtils.fillEmpVal(10, bussiness_tot_amt, "R", "0")).append(CommonUtils.fillEmpVal(2, contract_code, "L", " ")).append(CommonUtils.fillEmpVal(10, bussiness_pay_amt, "R", "0")).append(CommonUtils.fillEmpVal(9, store_charge, "R", " ")).append(CommonUtils.fillEmpVal(2, contract_code2, "R", " ")).append(CommonUtils.fillEmpVal(10, bussiness_pay_amt2, "R", " ")).append(CommonUtils.fillEmpVal(9, store_charge2, "R", " ")).append(CommonUtils.fillEmpVal(16, password, "R", " ")).append(CommonUtils.fillEmpVal(9, cash_bussiness_amt, "R", "0")).append(CommonUtils.fillEmpVal(9, supply_price, "R", "0")).append(CommonUtils.fillEmpVal(9, value_added_tax, "R", "0")).append(CommonUtils.fillEmpVal(9, serice_price, "R", "0")).append(CommonUtils.fillEmpVal(1, purpose, "R", "0")).append(CommonUtils.fillEmpVal(1, cash_receipt, "R", "1")).append(CommonUtils.fillEmpVal(1, web_service, "R", "N")).append(CommonUtils.fillEmpVal(50, filler3, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt1, "R", "0")).toString();
                    OCBinfo.put("OCBinfo", tmp);

                    System.out.println("SEND : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + tmp);

                    /* 2016AA000005 테스트
                        OCBinfo.put("resultcode", "0000");
                        OCBinfo.put("accept_no", "123456789");
                    */

                    Map<String, Object> resultMap = socketOCBSend(OCBinfo);
                    DlwOcbProdService.insertOcbTransHist(OCBinfo);

                    if("0000".equals(resultMap.get("resultcode"))) {
                        successCnt++;
                    } else {
                        failCnt++;
                    }

                    mapOutVar.put("result_msg", (new StringBuilder("성공 : ")).append(successCnt).append("건 ,실패 : ").append(failCnt).append("건 처리되었습니다.").toString());

                }
            }

            //2018.01.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            /*******************************************************/
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

    /**
     * 대명라이프웨이 OCB 정보를 전송 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-del")
    public ModelAndView transOcbProdDel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";



        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            /*******************************************************/
            int listCnt = listInDs.size();
            long listAmt = 0L;
            String ocbPoint = null;
            int successCnt = 0;
            int failCnt = 0;

            String sendType = "";
            String doc_no = "";
            String member_store = "";
            String chit_code = "";
            String bussiness_yn = "";


            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    ocbPoint = (String)hmParam.get("ocb_point");
                    String ciVal = (String)hmParam.get("ci_val");
                    String sendDate = (String)hmParam.get("send_date");

                    sendType = (String)mapInVar.get("sendtype");

                    Map<String, Object> OCBinfo = new HashMap<String, Object>();

                    switch(sendType){

                    case "regdel" :
                        OCBinfo.put("ocb_gubun", "K110"); //서비스번호
                        doc_no = "K110";
                        member_store = "30031802";
                        chit_code = "21";
                        bussiness_yn = "41";
                        break;
                    case "savdel" :
                        OCBinfo.put("ocb_gubun", "K310"); //서비스번호
                        doc_no = "K110";
                        member_store = "30031923";
                        chit_code = "21";
                        bussiness_yn = "41";
                        break;
                    case "usedel" :
                        OCBinfo.put("ocb_gubun", "K210"); //서비스번호
                        doc_no = "K410";
                        member_store = "30031923";
                        chit_code = "22";
                        bussiness_yn = "42";
                        break;
                    }

                    OCBinfo.put("ocb_point", ocbPoint);
                    OCBinfo.put("accept_no", (String)hmParam.get("accept_no"));
                    OCBinfo.put("reg_man", hmParam.get("reg_man"));
                    OCBinfo.put("accnt_no", (String)hmParam.get("accnt_no"));
                    OCBinfo.put("mem_no", (String)hmParam.get("mem_no"));
                    OCBinfo.put("send_date", sendDate);
                    OCBinfo.put("seq", (String)hmParam.get("seq"));

                    int HistCnt = DlwOcbProdService.getOcbTransHistCnt();

                    Date now = new Date();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");

                    String doc_code = "7075"; 							   // 대외기관코드
                    String YYYYMMDD = format1.format(now);				   // 전송일자
                    String HHMMSS = format2.format(now);				   // 전송시간
                    String seq_num = Integer.toString(HistCnt);			   // 추적번호
                    String doc_gubun = "ON";							   // 전문구분
                    String doc_length = "0684";							   // 본문길이 (원래 0684)
                    String response1 = "";								   // 응답코드 대분류
                    String response2 = "";								   // 응답코드 소분류
                    String filler = "";									   // 빈공간
                    String grid_cnt = "001";							   // 전체 데이터 건수
                    String mem_prog_id = "A";							   // 멤버쉽프로그램 ID
                    String mem_service = "A1";							   // 멤버쉽서비스구분
                    String pos_gunbun = "";								   // 단말기구분
                    String pos_id = "";									   // 단말기번호
                    String WCC = "0";									   // MSR
                    //String member_store = "30031802";					   // 가맹점번호
                    String Member_store_idn_no = "2208809321";			   // 가맹점사업자번호
                    String now_date = format1.format(now);				   // 거래일자
                    String now_time = format2.format(now);				   // 거래시간
                    String card_no = "";								   // 카드번호
                    String idn_no = "";									   // 주민번호
                    String ci = ciVal;									   // CI값
                    String filler2 = "";								   // 빈공간
                    String mem_id = "";									   // NXM회원ID
                    String store_member_id = "";						   // 제휴사회원ID
                    String store_member_ip = "";						   // 제휴사 회원IP
                    String store_member_appect = "";					   // 제휴사 승인번호
                    String deal_date = sendDate;						   // 원거래일자
                    String deal_accept = (String)hmParam.get("accept_no"); // 원거래승인번호
                    String deal_store_accept = "";						   // 원거래제휴사승인번호
                    String message1 = "";								   // 통합승인 사용
                    String message2 = "";								   // 통합승인 사용
                    String message3 = "";								   // 대외기관 사용
                    String message4 = "";								   // 대외기관 사용
                    //String chit_code = "21";							   // 전표코드(적립/충전 :21, 사용:22)
                    //String bussiness_yn = "41";							   // 거래구분코드(적립/충전 : 41 사용 : 42)
                    String compound_pay_yn = "N";						   // 복합결제여부
                    String bussiness_tot_amt = ocbPoint;				   // 원거래금액
                    String purpose = "1";								   // 취소요청구분
                    String web_service = "N";							   // 웹서비스 호출
                    String filler3 = "";								   // 빈공간
                    String tmp = "";

                    tmp = (new StringBuilder(String.valueOf(CommonUtils.fillEmpVal(4, doc_no, "L", " ")))).append(CommonUtils.fillEmpVal(4, doc_code, "L", " ")).append(CommonUtils.fillEmpVal(8, YYYYMMDD, "L", " ")).append(CommonUtils.fillEmpVal(6, HHMMSS, "L", " ")).append(CommonUtils.fillEmpVal(10, seq_num, "R", "0")).append(CommonUtils.fillEmpVal(2, doc_gubun, "L", " ")).append(CommonUtils.fillEmpVal(4, doc_length, "L", " ")).append(CommonUtils.fillEmpVal(2, response1, "L", " ")).append(CommonUtils.fillEmpVal(2, response2, "L", " ")).append(CommonUtils.fillEmpVal(8, filler, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt, "L", " ")).append(CommonUtils.fillEmpVal(1, mem_prog_id, "L", " ")).append(CommonUtils.fillEmpVal(2, mem_service, "L", " ")).append(CommonUtils.fillEmpVal(2, pos_gunbun, "L", " ")).append(CommonUtils.fillEmpVal(10, pos_id, "L", " ")).append(CommonUtils.fillEmpVal(1, WCC, "L", " ")).append(CommonUtils.fillEmpVal(15, member_store, "L", " ")).append(CommonUtils.fillEmpVal(10, Member_store_idn_no, "L", " ")).append(CommonUtils.fillEmpVal(8, now_date, "L", " ")).append(CommonUtils.fillEmpVal(6, now_time, "L", " ")).append(CommonUtils.fillEmpVal(37, card_no, "L", " ")).append(CommonUtils.fillEmpVal(13, idn_no, "L", " ")).append(CommonUtils.fillEmpVal(88, ci, "L", " ")).append(CommonUtils.fillEmpVal(50, filler2, "L", " ")).append(CommonUtils.fillEmpVal(9, mem_id, "L", " ")).append(CommonUtils.fillEmpVal(40, store_member_id, "L", " ")).append(CommonUtils.fillEmpVal(25, store_member_ip, "L", " ")).append(CommonUtils.fillEmpVal(12, store_member_appect, "L", " ")).append(CommonUtils.fillEmpVal(8, deal_date, "L", " ")).append(CommonUtils.fillEmpVal(9, deal_accept, "R", " ")).append(CommonUtils.fillEmpVal(12, deal_store_accept, "L", " ")).append(CommonUtils.fillEmpVal(64, message1, "L", " ")).append(CommonUtils.fillEmpVal(64, message2, "L", " ")).append(CommonUtils.fillEmpVal(64, message3, "L", " ")).append(CommonUtils.fillEmpVal(64, message4, "L", " ")).append(CommonUtils.fillEmpVal(2, chit_code, "L", " ")).append(CommonUtils.fillEmpVal(2, bussiness_yn, "L", " ")).append(CommonUtils.fillEmpVal(1, compound_pay_yn, "L", " ")).append(CommonUtils.fillEmpVal(10, bussiness_tot_amt, "R", "0")).append(CommonUtils.fillEmpVal(1, purpose, "L", " ")).append(CommonUtils.fillEmpVal(1, web_service, "L", " ")).append(CommonUtils.fillEmpVal(50, filler3, "L", " ")).toString();
                    OCBinfo.put("OCBinfo", tmp);

                    System.out.println("CANCEL : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + tmp);

                    /*
                    OCBinfo.put("resultcode", "0000");
                    OCBinfo.put("accept_no", "694811363");
                    */

                    successCnt = 0;
                    failCnt = 0;

                    Map<String, Object> resultMap = socketOCBSend(OCBinfo);
                    // HISTORY 등록
                    DlwOcbProdService.insertOcbTransHist(OCBinfo);

                    if("0000".equals(resultMap.get("resultcode"))) {
                        // 해당 전송건 취소 업데이트
                        DlwOcbProdService.updateOcbTransHist(OCBinfo);
                        successCnt++;
                    } else {
                        failCnt++;
                    }

                    mapOutVar.put("result_msg", (new StringBuilder("성공 : ")).append(successCnt).append("건 ,실패 : ").append(failCnt).append("건 처리되었습니다.").toString());
                }
            }

            //2018.01.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            /*******************************************************/
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

    public Map<String, Object> socketOCBSendTest(Map<String, Object> pmParam) throws Exception {

        pmParam.put("recvRecord", "K101");
        pmParam.put("code", "K101");
        pmParam.put("resultcode", "0000");
        pmParam.put("message", "TEST입니다");
        pmParam.put("accept_no", "1245111");

        return pmParam;
    }

    /**
     * 대명라이프웨이 OCB 상용정보를 전송한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trans-add")
    public ModelAndView transOcbProdAdd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");

            /*******************************************************/
            if (listInDs2.size() > 0) {
                hmParam2 = listInDs2.get(0);
            }

            Map<String, Object> OCBinfo = new HashMap<String, Object>();
            String stt_dt = (String)hmParam2.get("stt_dt");
            String end_dt = (String)hmParam2.get("end_dt");
            int listCnt = listInDs.size();
            long listAmt = 0L;
            String ocbPoint = null;
            int successCnt = 0;
            int failCnt = 0;

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    ocbPoint = (String)hmParam.get("ocb_point");
                    Date now = new Date();
                    OCBinfo.put("ocb_gubun", "K400");
                    OCBinfo.put("ocb_point", ocbPoint);
                    OCBinfo.put("accnt_no", (String)hmParam.get("accnt_no"));
                    OCBinfo.put("reg_man", hmParam.get("reg_man"));
                    OCBinfo.put("mem_no", (String)hmParam.get("mem_no"));
                    OCBinfo.put("pay_cnt", (String)hmParam.get("pay_cnt"));
                    String ci_val = (String)hmParam.get("ci_val");
                    OCBinfo.put("ci_val", ci_val);
                    OCBinfo.put("stt_dt", stt_dt);
                    OCBinfo.put("end_dt", end_dt);

                    int HistCnt = DlwOcbProdService.getOcbTransHistCnt();

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");

                    String YYYYMMDD = format1.format(now);
                    String HHMMSS = format2.format(now);

                    OCBinfo.put("send_date", YYYYMMDD);

                    String doc_no = "K400";               //////////////////////////////////
                    String doc_code = "7075";
                    String seq_num = Integer.toString(HistCnt);
                    String doc_gubun = "ON";
                    String doc_length = "0753";
                    String response1 = "";
                    String response2 = "";
                    String filler = "";
                    String grid_cnt = "001";
                    String mem_prog_id = "A";
                    String mem_service = "A1";
                    String pos_gunbun = "";
                    String pos_id = "";
                    String WCC = "0";
                    String member_store = "30031923";     //////////////////////////////
                    String Member_store_idn_no = "2208809321";
                    String deal_date = YYYYMMDD;
                    String deal_time = HHMMSS;
                    String card_no = "";
                    String idn_no = "";
                    String ci = ci_val;
                    String filler2 = "";
                    String mem_id = "";
                    String store_member_id = "";
                    String store_member_ip = "";
                    String store_member_appect = "";
                    String message1 = "";
                    String message2 = "";
                    String message3 = "";
                    String message4 = "";
                    String chit_code = "11";
                    String bussiness_yn = "40"; /////////////////////거래구분코드 40
                    String compound_pay_yn = "N";
                    String bussiness_tot_amt = ocbPoint;
                    String contract_code = "08";////////////////////거래계약구분코드 08
                    String bussiness_pay_amt = ocbPoint;
                    String store_charge = "";
                    String contract_code2 = "";
                    String bussiness_pay_amt2 = "";
                    String store_charge2 = "";
                    String password = "";  //////비밀번호 제공 SK
                    String cash_bussiness_amt = "";
                    String supply_price = "";
                    String value_added_tax = "";
                    String serice_price = "";
                    String purpose = "0";
                    String cash_receipt = "1";
                    String web_service = "N";
                    String filler3 = "";
                    String grid_cnt1 = "000";
                    String tmp = "";
                    tmp = (new StringBuilder(String.valueOf(CommonUtils.fillEmpVal(4, doc_no, "L", " ")))).append(CommonUtils.fillEmpVal(4, doc_code, "L", " ")).append(CommonUtils.fillEmpVal(8, YYYYMMDD, "L", " ")).append(CommonUtils.fillEmpVal(6, HHMMSS, "L", " ")).append(CommonUtils.fillEmpVal(10, seq_num, "R", "0")).append(CommonUtils.fillEmpVal(2, doc_gubun, "L", " ")).append(CommonUtils.fillEmpVal(4, doc_length, "L", " ")).append(CommonUtils.fillEmpVal(2, response1, "L", " ")).append(CommonUtils.fillEmpVal(2, response2, "L", " ")).append(CommonUtils.fillEmpVal(8, filler, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt, "L", " ")).append(CommonUtils.fillEmpVal(1, mem_prog_id, "L", " ")).append(CommonUtils.fillEmpVal(2, mem_service, "L", " ")).append(CommonUtils.fillEmpVal(2, pos_gunbun, "L", " ")).append(CommonUtils.fillEmpVal(10, pos_id, "L", " ")).append(CommonUtils.fillEmpVal(1, WCC, "L", " ")).append(CommonUtils.fillEmpVal(15, member_store, "L", " ")).append(CommonUtils.fillEmpVal(10, Member_store_idn_no, "L", "0")).append(CommonUtils.fillEmpVal(8, deal_date, "L", " ")).append(CommonUtils.fillEmpVal(6, deal_time, "L", " ")).append(CommonUtils.fillEmpVal(37, card_no, "L", " ")).append(CommonUtils.fillEmpVal(13, idn_no, "L", " ")).append(CommonUtils.fillEmpVal(88, ci, "R", " ")).append(CommonUtils.fillEmpVal(50, filler2, "L", " ")).append(CommonUtils.fillEmpVal(9, mem_id, "L", " ")).append(CommonUtils.fillEmpVal(40, store_member_id, "L", " ")).append(CommonUtils.fillEmpVal(25, store_member_ip, "L", " ")).append(CommonUtils.fillEmpVal(12, store_member_appect, "L", " ")).append(CommonUtils.fillEmpVal(64, message1, "L", " ")).append(CommonUtils.fillEmpVal(64, message2, "L", " ")).append(CommonUtils.fillEmpVal(64, message3, "L", " ")).append(CommonUtils.fillEmpVal(64, message4, "L", " ")).append(CommonUtils.fillEmpVal(2, chit_code, "L", " ")).append(CommonUtils.fillEmpVal(2, bussiness_yn, "L", " ")).append(CommonUtils.fillEmpVal(1, compound_pay_yn, "R", "N")).append(CommonUtils.fillEmpVal(10, bussiness_tot_amt, "R", "0")).append(CommonUtils.fillEmpVal(2, contract_code, "L", " ")).append(CommonUtils.fillEmpVal(10, bussiness_pay_amt, "R", "0")).append(CommonUtils.fillEmpVal(9, store_charge, "R", " ")).append(CommonUtils.fillEmpVal(2, contract_code2, "R", " ")).append(CommonUtils.fillEmpVal(10, bussiness_pay_amt2, "R", " ")).append(CommonUtils.fillEmpVal(9, store_charge2, "R", " ")).append(CommonUtils.fillEmpVal(16, password, "R", " ")).append(CommonUtils.fillEmpVal(9, cash_bussiness_amt, "R", "0")).append(CommonUtils.fillEmpVal(9, supply_price, "R", "0")).append(CommonUtils.fillEmpVal(9, value_added_tax, "R", "0")).append(CommonUtils.fillEmpVal(9, serice_price, "R", "0")).append(CommonUtils.fillEmpVal(1, purpose, "R", "0")).append(CommonUtils.fillEmpVal(1, cash_receipt, "R", "1")).append(CommonUtils.fillEmpVal(1, web_service, "R", "N")).append(CommonUtils.fillEmpVal(50, filler3, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt1, "R", "0")).toString();
                    OCBinfo.put("OCBinfo", tmp);

                    //Map<String, Object> resultMap = socketOCBSend(OCBinfo);

                    DlwOcbProdService.insertOcbTransHist(OCBinfo);
                    /*
                    CommonUtils.printLog("------------------------------------------>" + tmp);
                    CommonUtils.printLog("------------------------------------------>" + resultMap.get("resultcode"));

                    if("0000".equals(resultMap.get("resultcode"))) {
                        successCnt++;
                    } else {
                        failCnt++;
                    }
                    */
                    mapOutVar.put("result_msg", (new StringBuilder("성공 : ")).append(successCnt).append("건 ,실패 : ").append(failCnt).append("건 처리되었습니다.").toString());

                }
            }
            /*******************************************************/
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


    /**
     * 대명라이프웨이 OCB정보 소켓전송
     *
     * @param pmParam Map<String, Object>
     * @return Map
     * @throws Exception
     */
    public Map<String, Object> socketOCBSend(Map<String, Object> pmParam) throws Exception {
        Socket socket = null;
        BufferedWriter bw = null;
        String recvRecord = "";
        String result = "";
        String message = "";
        String code = "";
        String acceptNo = "";
        String OCB = (String)pmParam.get("OCBinfo");

        String sOcbIp = basVlService.getBasVlAsString("ocb_ip");
        String sOCbPort = basVlService.getBasVlAsString("ocb_port");

        try
        {
            socket = new Socket(sOcbIp, Integer.parseInt(sOCbPort));
            System.out.println("socket : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + socket);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            pmParam.put("result", "connect");
            pmParam.put("message", "연결오류");
            return pmParam;
        }
        try
        {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            bw.write(OCB);

            System.out.println("socket.isBound : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + socket.isBound());

            if(socket.isBound())
            {
                bw.flush();
                System.out.println("BufferedWriter_CHECK_1 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                bw.newLine();
                System.out.println("BufferedWriter_CHECK_2 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                InputStream in = null;
                byte buffer[] = new byte[10000];
                in = socket.getInputStream();
                System.out.println("BufferedWriter_CHECK_3 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                int bytesRead = in.read(buffer);
                System.out.println("BufferedWriter_CHECK_4 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                socket.close();
                System.out.println("bytesRead : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + bytesRead);

                recvRecord = new String(buffer, 0, bytesRead);
                code = recvRecord.substring(0, 4);
                result = recvRecord.substring(38, 42);

                if ("K101".equals(code)) {
                    acceptNo = recvRecord.substring(309, 318);
                    message = recvRecord.substring(318, 382);
                } else if("K111".equals(code)) {
                    message = recvRecord.substring(318, 382);
                } else if("K401".equals(code)) {
                    acceptNo = recvRecord.substring(309, 318);
                    message = recvRecord.substring(318, 382);
                } else if("K411".equals(code)) {
                    message = recvRecord.substring(318, 382);
                } else if("W401".equals(code)) {
                    message = recvRecord.substring(442, 507);
                }

                pmParam.put("recvRecord", recvRecord);
                pmParam.put("code", code);
                pmParam.put("resultcode", result);
                pmParam.put("message", recvRecord);
                pmParam.put("accept_no", acceptNo);

                if("0000".equals(result)) {
                    pmParam.put("result", "success");
                } else {
                    pmParam.put("result", "fail");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            pmParam.put("result", "getInputStream");
            pmParam.put("message", "getInputStream 타임오버 오류");
            return pmParam;
        }
        return pmParam;
    }

    /**
     * 대명라이프웨이 OCB 상품 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chk")
    public ModelAndView getOcbChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                Map<String, Object> OCBinfo = new HashMap<String, Object>();
                String sCiVal = StringUtils.defaultString((String) hmParam.get("ci_val"));

                int HistCnt = DlwOcbProdService.getOcbTransHistCnt();

                OCBinfo.put("ocbGubun", "W400");

                Date now = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
                String doc_no = "W400";
                String doc_code = "7075";
                String YYYYMMDD = format1.format(now);
                String HHMMSS = format2.format(now);
                String seq_num = Integer.toString(HistCnt);
                String doc_gubun = "ON";
                String doc_length = "0650";
                String response1 = "";
                String response2 = "";
                String filler = "";

                String grid_cnt = "001";
                String mem_prog_id = "A";
                String mem_service = "A1";
                String point_gunbun = "O";
                String point_kind = "";
                String point_accept = "";
                String member_store_code = "";
                String Member_store_idn_no = "30031802";
                String accept_gubun = "WB";
                String member_gubun = "1";
                String card_no = "";
                String card_code = "";

                String ci = sCiVal;
                String mem_id = "";
                String store_member_id = "";
                String password = "";
                String store_member_ip = "";
                String store_member_appect = "";
                String save_yn = "";
                String use_yn = "";
                String bussiness_tot_amt = "0";
                String point_gubun1 = "";
                String take_point1 = "";
                String use_point1 = "";
                String save_point1 = "";
                String point_gubun2 = "";
                String take_point2 = "";
                String use_point2 = "";
                String save_point2 = "";
                String service = "";
                String filler2 = "";
                String message1 = "";
                String message2 = "";
                String message3 = "";
                String message4 = "";
                String web_service = "N";
                String tmp = "";

                tmp = (new StringBuilder(String.valueOf(CommonUtils.fillEmpVal(4, doc_no, "L", " ")))).append(CommonUtils.fillEmpVal(4, doc_code, "L", " "))
                        .append(CommonUtils.fillEmpVal(8, YYYYMMDD, "L", " ")).append(CommonUtils.fillEmpVal(6, HHMMSS, "L", " ")).append(CommonUtils.fillEmpVal(10, seq_num, "R", "0"))
                        .append(CommonUtils.fillEmpVal(2, doc_gubun, "L", " ")).append(CommonUtils.fillEmpVal(4, doc_length, "L", " ")).append(CommonUtils.fillEmpVal(2, response1, "L", " "))
                        .append(CommonUtils.fillEmpVal(2, response2, "L", " ")).append(CommonUtils.fillEmpVal(8, filler, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt, "L", " "))
                        .append(CommonUtils.fillEmpVal(1, mem_prog_id, "L", " ")).append(CommonUtils.fillEmpVal(2, mem_service, "L", " ")).append(CommonUtils.fillEmpVal(1, point_gunbun, "L", " "))
                        .append(CommonUtils.fillEmpVal(3, point_kind, "L", " ")).append(CommonUtils.fillEmpVal(1, point_accept, "L", " ")).append(CommonUtils.fillEmpVal(6, member_store_code, "L", " "))
                        .append(CommonUtils.fillEmpVal(15, Member_store_idn_no, "L", " ")) .append(CommonUtils.fillEmpVal(2, accept_gubun, "L", " ")).append(CommonUtils.fillEmpVal(1, member_gubun, "L", " "))
                        .append(CommonUtils.fillEmpVal(16, card_no, "L", " ")).append(CommonUtils.fillEmpVal(4, card_code, "L", " ")).append(CommonUtils.fillEmpVal(88, ci, "L", " "))
                        .append(CommonUtils.fillEmpVal(9, mem_id, "L", " ")).append(CommonUtils.fillEmpVal(13, store_member_id, "L", " ")).append(CommonUtils.fillEmpVal(16, password, "R", " "))
                        .append(CommonUtils.fillEmpVal(1, save_yn, "L", " ")).append(CommonUtils.fillEmpVal(1, use_yn, "L", " ")).append(CommonUtils.fillEmpVal(10, bussiness_tot_amt, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, point_gubun1, "R", " ")).append(CommonUtils.fillEmpVal(10, take_point1, "R", "0")).append(CommonUtils.fillEmpVal(10, use_point1, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, save_point1, "R", "0")).append(CommonUtils.fillEmpVal(10, point_gubun2, "R", " ")).append(CommonUtils.fillEmpVal(10, take_point2, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, use_point2, "R", "0")).append(CommonUtils.fillEmpVal(10, save_point2, "R", "0")).append(CommonUtils.fillEmpVal(3, service, "R", " "))
                        .append(CommonUtils.fillEmpVal(117, filler2, "L", " ")).append(CommonUtils.fillEmpVal(64, message1, "L", " ")).append(CommonUtils.fillEmpVal(64, message2, "L", " "))
                        .append(CommonUtils.fillEmpVal(64, message3, "L", " ")).append(CommonUtils.fillEmpVal(64, message4, "L", " ")).append(CommonUtils.fillEmpVal(1, web_service, "L", " ")).toString();
                OCBinfo.put("OCBinfo", tmp);

                Map<String, Object> resultMap = socketOCBSend(OCBinfo);

                hmParam.put("rslMsg", resultMap.get("result"));
                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output", dtptMap);
            }

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