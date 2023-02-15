/*
 * (@)# DlwConsProdController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.chn.service.DlvService;
import powerservice.business.cns.service.ConsService;
import powerservice.business.cns.service.CtiCrncHstrService;
//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwConsProdService;
import powerservice.business.dlw.service.DlwResnService;
import powerservice.business.seed.kisa.SEED_KISA;
import powerservice.business.sys.service.BasVlService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
/**
 * 상담-상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
 * @프로그램ID DlwConsProd
 */
@Controller
@RequestMapping(value = "/dlw/cons-prod")
public class DlwConsProdController {

    private final Logger log = LoggerFactory.getLogger(DlwConsProdController.class);

    @Resource
    private DlwConsProdService dlwConsProdService;

    @Resource
    private ConsService consService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private DlwResnService dlwResnService;

    @Resource
    private CtiCrncHstrService ctiCrncHstrService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private DlvService dlvService;

    @Resource
    private CommonService commonService;

    /**
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getDlwConsProdList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                List<Map<String, Object>> mList = dlwConsProdService.getDlwConsProdList(hmParam);

                //전자서명 인증값 넣어준다
                //Map<String, Object> listNiceAuth = dlwConsProdService.getNiceAuthStat(hmParam);
                //System.out.println("XXXXXLIMDONGJINXXXXX    :" + listNiceAuth.get("NICE_NO"));

                //List Row 추가 (인증값 받으려면 플랫폼에서 Rowindex 1)

                //mList.add(listNiceAuth);


                if ("Y".equals(hmParam.get("etc_yn"))) { // 기타 문의 추가 여부
                    Map<String, Object> mTmp = new HashMap<String, Object>();

                    mTmp.put("accnt_no", "00000");
                    mTmp.put("prod_nm", "기타문의");

                    mList.add(mTmp);
                }

                //System.out.println("XXXXXLIMDONGJINXXXXX" +  mList);
                listMap.setRowMaps(mList);

                mapOutVar.put("tc_consProd", mList.size());
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
     * 상담-상품 정보 선택에 따른 모델분류/모델명/상품종류 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/modl-list/{pagetype}")
    public ModelAndView getDlwConsProdModlList(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap sublistMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mSubList = new ArrayList<Map<String, Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("prod_cd", StringUtils.defaultString((String) mapInVar.get("prod_cd")));
            hmParam.put("prod_model_kind", StringUtils.defaultString((String) mapInVar.get("prod_kind")));
            hmParam.put("continued", StringUtils.defaultString((String) mapInVar.get("continued")));

            if ("modlTyp".equals(psPathType)) {
                mList = dlwConsProdService.getDlwModlMstInfo(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            } else if ("modlNm".equals(psPathType)) {
                mList = dlwConsProdService.getDlwModlDtlInfo(hmParam);
                mSubList = dlwConsProdService.getDlwProdKindList(hmParam);

                listMap.setRowMaps(mList);
                sublistMap.setRowMaps(mSubList);
                mapOutDs.put("ds_output", listMap);
                mapOutDs.put("ds_subOutput", sublistMap);
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
     * 해약금 정보 조회.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resn-amt")
    public ModelAndView getResnPayInq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                int exprno = 0;
                int resnAmt = 0;
                int nmCount = 0;

                double accmPay = 0.0D;
                double mskPay = 0.0D;

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String resnGubn = StringUtils.defaultString((String) hmParam.get("r_gubn"));

                Map <String, Object> mDtpt = dlwConsProdService.getProdInfoInqNew(hmParam); // 회원 상품정보 조회 2018.01.30 김찬영 getProdInfoInq => getProdInfoInqNew 변경

                String sProdCd = StringUtils.defaultString((String) mDtpt.get("prod_cd"));

                int exprNo = Integer.parseInt(mDtpt.get("expr_no") + "");
                int prodAmt = Integer.parseInt(mDtpt.get("prod_amt") + "");
                int payAmt = Integer.parseInt(mDtpt.get("pay_amt") + "");
                int payCount = Integer.parseInt(mDtpt.get("pay_amt_count") + "");
                int dcAmt = Integer.parseInt(mDtpt.get("dc_amt") + "");

                String sJoinDt = (String)mDtpt.get("join_dt");

                payAmt -= dcAmt;

                nmCount = dlwConsProdService.getNowMonthCount(sAccntNo); // 현재 회차 조회
                resnAmt = dlwConsProdService.getResnAmt(sProdCd, sAccntNo, nmCount, sJoinDt); // 해약환급금

                if(resnAmt < 0) {
                    if(payCount <= 60) {
                        accmPay = Math.round((double)payAmt - (double)payAmt * 0.10000000000000001D);
                        if(exprNo >= 60) exprno = 60;
                        else exprno = exprNo;

                        double temp1 = (exprno - payCount) + 1;
                        double temp2 = temp1 / (double)exprno;
                        double temp3 = (double)prodAmt * 0.153D;
                        mskPay = Math.round((temp2 * temp3) / 10.0D) * 10L;
                        double temp4 = Math.round((accmPay - mskPay) * 0.90000000000000002D);
                        resnAmt = (int)temp4;
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    } else {
                        int x = payCount - 60;
                        double temp6 = 80.5D + (double)x * 0.316D;
                        double temp8 = ((double)payAmt * temp6) / 100D;
                        resnAmt = (int)Math.floor(temp8);
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    }
                } else {
                    resnAmt = (int)Math.floor(resnAmt);
                }

                int chk = dlwResnService.getResnGubn(hmParam);

                if(chk == 1 || "02".equals(resnGubn)) {
                    hmParam.put("resn_amt", Integer.valueOf(payAmt));
                    hmParam.put("resn_gubn", "02");
                } else {
                    hmParam.put("resn_amt", Integer.valueOf(resnAmt));
                    hmParam.put("resn_gubn", "01");
                }

                System.out.println("XXXXXLIMDONGJINXXXXX" +  hmParam);

                //전자서명 인증값 넣어준다
                String strNice_no = "";
                String strAuth_01 = "";
                String strAuth_02 = "";

                Map<String, Object> listNiceAuth = dlwConsProdService.getNiceAuthStat(hmParam);
                if (listNiceAuth != null){
                    strNice_no = String.valueOf(listNiceAuth.get("NICE_NO"));
                    strAuth_01 = String.valueOf(listNiceAuth.get("AUTH_01"));
                    strAuth_02 = String.valueOf(listNiceAuth.get("AUTH_02"));
                }

                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output", dtptMap);

                mapOutVar.put("gv_niceNo", strNice_no);
                mapOutVar.put("gv_Auth01", strAuth_01);
                mapOutVar.put("gv_Auth02", strAuth_02);
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
     * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srvc-list")
    public ModelAndView getProdSrvcMstList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String sGubun = StringUtils.defaultString((String) hmParam.get("gubun"));
                String sPayGubun = StringUtils.defaultString((String) hmParam.get("pay_gubun"));

                String isChgPayGubun = "Y";
                if (!"".equals(sGubun) && !"inq".equals(sGubun)) {
                    if (dlwConsProdService.getProdSrvcHistForMPAS(hmParam) > 0) {
                        isChgPayGubun = "N";
                        mapOutVar.put("isChgPayGubun", isChgPayGubun);

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, "0");
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "부가서비스 정보가 없습니다.");

                        return modelAndView;
                    }
                }

                if("00".equals(sPayGubun)) hmParam.put("mem_cl", "L");
                else if(!"".equals(sPayGubun)) hmParam.put("mem_cl", "T");

                List<Map<String, Object>> mList = dlwConsProdService.getProdSrvcMstList(hmParam);

                listMap.setRowMaps(mList);

                mapOutVar.put("isChgPayGubun", isChgPayGubun);
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
     * 할인우대권 체크
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chk-rand-num")
    public ModelAndView getRandNum(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sRandNum = StringUtils.defaultString((String) mapInVar.get("rand_num"));

            if ("".equals(sRandNum)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "할인우대권번호가 없습니다.");

                return modelAndView;
            }

            String sRslYn = dlwConsProdService.validateDiscntPassNo(sRandNum); // 할인우대권 체크
            if ("Y".equals(sRslYn)) {
                String sNewChanGunsu = dlwConsProdService.selectNewChanGunsuDPM(sRandNum); // 특별할인
                hmParam.put("new_chan_gunsu", sNewChanGunsu);
                hmParam.put("resultMsg", "success");
            } else {
                hmParam.put("resultMsg", "fail");
            }

            dtptMap.setRowMaps(hmParam);
            mapOutDs.put("ds_output", dtptMap);

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
     * 리조트 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resort-info")
    public ModelAndView getResortInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원의 고유번호가 없습니다.");

                return modelAndView;
            }

            Map<String, Object> mDtpt = dlwConsProdService.getResortInfo(sMemNo);
            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
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

    /**
     * B2B회사 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/b2b-list")
    public ModelAndView getB2bCompList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = dlwConsProdService.getB2bCompCount(hmParam);
            mapOutVar.put("tc_b2b", nTotal);

            List<Map<String, Object>> mList = dlwConsProdService.getB2bCompList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 매입업체(세금계산서)회사 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pur-list")
    public ModelAndView getPurCompList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            mapOutVar.put("tc_b2b", 0);

            List<Map<String, Object>> mList = dlwConsProdService.getPurCompList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 삼성매장 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smShop-list")
    public ModelAndView getSmShopList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = dlwConsProdService.getSmShopCount(hmParam);
            mapOutVar.put("tc_smShop", nTotal);

            List<Map<String, Object>> mList = dlwConsProdService.getSmShopList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 2구좌 가입 제한을 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/no-sale-accnt")
    public ModelAndView getNoSaleAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String sResultFlag = dlwConsProdService.getNoSaleAccnt(hmParam);
                hmParam.put("result_flag", sResultFlag);
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

    /**
     * 상품정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public @ResponseBody ModelAndView saveMemProdAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap subListInDs = (DataSetMap)mapInDs.get("ds_subInput");
            DataSetMap chbfProdDs = (DataSetMap)mapInDs.get("ds_chbfProd");

            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));
                String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
                String sDspsTypCd = "";
                String sInsertCnt = StringUtils.defaultString((String) hmParam.get("insert_cnt"));
                int iInsertCnt = 0;

                if(!"".equals(sInsertCnt)) iInsertCnt = Integer.parseInt(sInsertCnt);

                ParamUtils.addSaveParam(hmParam);

                hmParam.put("comp_dt", "");
                hmParam.put("join_prod", "");
                hmParam.put("org_join_gunsu", "0");
                hmParam.put("certf_rcv_chk", "0");
                hmParam.put("del_fg", "N");

                if ("".equals(sAccntNo)) { // 등록
                    sDspsTypCd = "I";


                    if(iInsertCnt > 0){
                        for(int k=0; k<iInsertCnt; k++) { //단체건수

                            hmParam.put("membership", dlwConsProdService.getCardCode(sProdCd));   // 카드코드 조회

                            sAccntNo = dlwConsProdService.createAccntNo(sProdCd);
                            hmParam.put("accnt_no", sAccntNo);

                            //pay_perd                : 납입주기 사용안함
                            //post_mtr_rcv            : 우편수령 사용안함
                            //pay_stat_1no            : 1회분 납입상태 사용안함
                            //before_dc_cnt           : 선할인 사용안함(추가시 기본 0 설정)

                            //ded_no update 어디에서 하는지 확인
                            //resort_mem_gubun 값 들어오는지 확인

                            dlwConsProdService.insertMemProdAccnt(hmParam);            // 회원_상품_계좌 등록

                            if ("S7".equals(sProdCd) || "S8".equals(sProdCd) || "S9".equals(sProdCd) || "T0".equals(sProdCd) || "X5".equals(sProdCd) || "X6".equals(sProdCd)) {
                                hmParam.put("issue_stat", "0001");
                                hmParam.put("cnsl_stat", "0001");
                                dlwConsProdService.insertSmartLifeCnslMng(hmParam);    // 스마트라이프 상담등록
                            }

                            if (subListInDs != null && subListInDs.size() > 0) {
                                for (int i = 0; i < subListInDs.size(); i++) {
                                    Map subHmParam = subListInDs.get(i);
                                    String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                    if ("1".equals(sChk)) {
                                        ParamUtils.addSaveParam(subHmParam);

                                        subHmParam.put("accnt_no", sAccntNo);
                                        dlwConsProdService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                    }
                                }
                            }

                            /* 20181105 수정사항
                             * 두구좌 상품인 경우 자동으로 구좌상품 하나를 더 등록하도록 함.
                             * 회원번호, 모델분류, 모델명, 상품종류를 제외한 나머지 값들은 모두 동일하게 저장됨.
                             */
                            if("EI".equals(sProdCd))
                            {
                                Map<String, Object> saveTwinAccntParam = new HashMap<String, Object>();
                                ParamUtils.addSaveParam(saveTwinAccntParam);
                                saveTwinAccntParam.put("mem_no", sMemNo);
                                saveTwinAccntParam.put("twin_accnt_no1", sAccntNo);
                                saveTwinAccntParam.put("prod_cd1", "EI");

                                hmParam.put("prod_cd", "EJ"); // EI상품일경우 두구좌의 해당 상품인 EJ를 자동으로 등록하도록 함.
                                sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));

                                hmParam.put("membership", dlwConsProdService.getCardCode(sProdCd));   // EJ 상품 카드코드 조회 (멤버쉽)
                                sAccntNo = dlwConsProdService.createAccntNo(sProdCd);                 // 해당 등록상품의 EJ 회원번호 추출
                                hmParam.put("accnt_no", sAccntNo);
                                hmParam.put("prod_model_kind", "0800");
                                hmParam.put("prod_model_cd", "");
                                hmParam.put("prod_kind", "");
                                dlwConsProdService.insertMemProdAccnt(hmParam);

                                if (subListInDs != null && subListInDs.size() > 0) {
                                    for (int i = 0; i < subListInDs.size(); i++) {
                                        Map subHmParam = subListInDs.get(i);
                                        String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                        if ("1".equals(sChk)) {
                                            ParamUtils.addSaveParam(subHmParam);

                                            subHmParam.put("accnt_no", sAccntNo);
                                            dlwConsProdService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                        }
                                    }
                                }

                                saveTwinAccntParam.put("twin_accnt_no2", sAccntNo);
                                saveTwinAccntParam.put("prod_cd2", sProdCd);
                                dlwConsProdService.insertMemTwinAccnt(saveTwinAccntParam);
                            }
                        }
                    }else{
                        hmParam.put("membership", dlwConsProdService.getCardCode(sProdCd));   // 카드코드 조회

                        sAccntNo = dlwConsProdService.createAccntNo(sProdCd);
                        hmParam.put("accnt_no", sAccntNo);

                        //pay_perd                : 납입주기 사용안함
                        //post_mtr_rcv            : 우편수령 사용안함
                        //pay_stat_1no            : 1회분 납입상태 사용안함
                        //before_dc_cnt           : 선할인 사용안함(추가시 기본 0 설정)

                        //ded_no update 어디에서 하는지 확인
                        //resort_mem_gubun 값 들어오는지 확인

                        dlwConsProdService.insertMemProdAccnt(hmParam);            // 회원_상품_계좌 등록

                        if ("S7".equals(sProdCd) || "S8".equals(sProdCd) || "S9".equals(sProdCd) || "T0".equals(sProdCd) || "X5".equals(sProdCd) || "X6".equals(sProdCd)) {
                            hmParam.put("issue_stat", "0001");
                            hmParam.put("cnsl_stat", "0001");
                            dlwConsProdService.insertSmartLifeCnslMng(hmParam);    // 스마트라이프 상담등록
                        }

                        if (subListInDs != null && subListInDs.size() > 0) {
                            for (int i = 0; i < subListInDs.size(); i++) {
                                Map subHmParam = subListInDs.get(i);
                                String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                if ("1".equals(sChk)) {
                                    ParamUtils.addSaveParam(subHmParam);

                                    subHmParam.put("accnt_no", sAccntNo);
                                    dlwConsProdService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                }
                            }
                        }

                        /* 20181105 수정사항
                         * 두구좌 상품인 경우 자동으로 구좌상품 하나를 더 등록하도록 함.
                         * 회원번호, 모델분류, 모델명, 상품종류를 제외한 나머지 값들은 모두 동일하게 저장됨.
                         */
                        if("EI".equals(sProdCd))
                        {
                            Map<String, Object> saveTwinAccntParam = new HashMap<String, Object>();
                            ParamUtils.addSaveParam(saveTwinAccntParam);
                            saveTwinAccntParam.put("mem_no", sMemNo);
                            saveTwinAccntParam.put("twin_accnt_no1", sAccntNo);
                            saveTwinAccntParam.put("prod_cd1", sProdCd);

                            hmParam.put("prod_cd", "EJ"); // EI상품일경우 두구좌의 해당 상품인 EJ를 자동으로 등록하도록 함.
                            sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));

                            hmParam.put("membership", dlwConsProdService.getCardCode(sProdCd));   // EJ 상품 카드코드 조회 (멤버쉽)
                            sAccntNo = dlwConsProdService.createAccntNo(sProdCd);                 // 해당 등록상품의 EJ 회원번호 추출
                            hmParam.put("accnt_no", sAccntNo);
                            hmParam.put("prod_model_kind", "0800");
                            hmParam.put("prod_model_cd", "");
                            hmParam.put("prod_kind", "");
                            dlwConsProdService.insertMemProdAccnt(hmParam);

                            if (subListInDs != null && subListInDs.size() > 0) {
                                for (int i = 0; i < subListInDs.size(); i++) {
                                    Map subHmParam = subListInDs.get(i);
                                    String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                    if ("1".equals(sChk)) {
                                        ParamUtils.addSaveParam(subHmParam);

                                        subHmParam.put("accnt_no", sAccntNo);
                                        dlwConsProdService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                    }
                                }
                            }

                            saveTwinAccntParam.put("twin_accnt_no2", sAccntNo);
                            saveTwinAccntParam.put("prod_cd2", sProdCd);
                            dlwConsProdService.insertMemTwinAccnt(saveTwinAccntParam);
                        }
                    }
                } else {                   // 수정
                    sDspsTypCd = "U";
                    dlwConsProdService.updateMemProdAccnt(hmParam);             // 회원_상품_계좌 수정

                    dlwConsProdService.deleteMemProdAccntSvc(sAccntNo);         // 부가서비스 삭제

                    if (subListInDs != null && subListInDs.size() > 0) {
                        for (int i = 0; i < subListInDs.size(); i++) {
                            Map subHmParam = subListInDs.get(i);
                            String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                            if ("1".equals(sChk)) {
                                ParamUtils.addSaveParam(subHmParam);

                                subHmParam.put("accnt_no", sAccntNo);
                                dlwConsProdService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                            }
                        }
                    }

                    String[] arrAccntCol = {"emple_no", "bef_emple_cd", "prod_model_kind", "prod_model_cd", "prod_kind"
                                          , "bank_accnt_no", "pay_mthd", "join_dt", "join_cl", "new_chan_gunsu"
                                          , "b2b_comp_cd", "b2b_emple_no", "sm_shop_info", "inspl_zip", "inspl_addr"
                                          , "inspl_addr2", "inspl_phone", "pay_gubun", "resort_mem_no", "resort_mem_nm"
                                          , "id_no", "kb_no", "rand_num", "order_num", "ja_subscrpt_yn"
                                          , "ocb_reg_yn", "hpcl_stat_nm", "main_contract_nm"};

                    String[] arrAccntColNm = {"담당사원", "모집사원", "모델분류", "모델명", "상품종류"
                                            , "계좌번호", "납입방법", "가입일자", "가입구분", "특별할인"
                                            , "B2B회사", "B2B사원코드", "매장정보", "설치장소우편번호", "설치장소주소"
                                            , "설치장소상세주소", "설치장소연락처", "리조트구분", "리조트번호", "리조트회원명"
                                            , "ID NO", "KB NO", "할인우대권", "주문번호", "구독여부"
                                            , "OCB여부", "해피콜상태", "주계약"};

                    String sCnslCon = "";

                    if (chbfProdDs != null && chbfProdDs.size() > 0) {
                        Map chbfProdParam = chbfProdDs.get(0);
                        Map<String, Object> mChngAccntInfo = new HashMap<String, Object>();
                        ParamUtils.addSaveParam(mChngAccntInfo);
                        String tgubun = "";
                        for (int i = 0; i < arrAccntCol.length; i++) {
                            String sChbfProdInfo = StringUtils.defaultString((String) chbfProdParam.get(arrAccntCol[i]));
                            String sProdInfo = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));

                            if (!sChbfProdInfo.equals(sProdInfo)) {
                                mChngAccntInfo.put("mem_no", sMemNo);
                                mChngAccntInfo.put("accnt_no", sAccntNo);
                                mChngAccntInfo.put("cl1", "U");
                                mChngAccntInfo.put("dat1", arrAccntColNm[i]);                  // 필드명

                                String sDat2 = StringUtils.defaultString((String)chbfProdParam.get(arrAccntCol[i]));
                                if ("".equals(sDat2)) {
                                    sDat2 = "빈값";
                                }
                                String sDat3 = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));
                                if ("".equals(sDat3)) {
                                    sDat3 = "빈값";
                                }

                                /**   20170519
                                 * 설치장소우편번호,설치장소주소,   설치장소상세주소 변경되면  tgubun ='1'  셋팅!!

                               * */
                                if ("설치장소우편번호".equals(arrAccntColNm[i])  || "설치장소주소".equals(arrAccntColNm[i]) || "설치장소상세주소".equals(arrAccntColNm[i]) ) {
                                      String sc_data = StringUtils.defaultString((String) chbfProdParam.get(arrAccntCol[i]));
                                      String sc_data_new = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));
                                      if (!sc_data.equals(sc_data_new)) {
                                          tgubun = "1";
                                      }
                                }




                                mChngAccntInfo.put("dat2", sDat2); // 변경전값
                                mChngAccntInfo.put("dat3", sDat3);       // 변경후값

                                sCnslCon = (new StringBuilder(String.valueOf(sCnslCon))).append((String)mChngAccntInfo.get("dat1")).append(": ").append(sDat2).append(" -> ").append(sDat3).append("\n").toString();
                                dlwConsProdService.insertReqUpdDelInf(mChngAccntInfo);         // 변경 삭제 내역 등록
                            }


                        }


                        /**
                         * 설치장소우편번호,설치장소주소,   설치장소상세주소    인서트진행  DELIVERY_INFO(발주테이블(?)) 테이블
                         *
                     */
                        if ("1".equals(tgubun)) {
                             String scaddr=  "";
                             scaddr =  (new StringBuilder((String)hmParam.get("inspl_zip"))).append(")").append((String)hmParam.get("inspl_addr")).append(" ").append((String)hmParam.get("inspl_addr2")).toString();
                             hmParam.put("scaddr", scaddr);       // 설치장소변경데이터 넣구

                             // 있는지 체크함  있으면 업데이트 없으면 노업데이트 ㅋ
                             int nTotal = dlwConsProdService.getdeliveryCnt(hmParam);

                             if (nTotal  == 1) {   /// 건수가 1건이면 업데이트(동기화)  그외것들은  패스 (발주목록은 각회원번호데이터가 무조건 1건이다)
                                 dlwConsProdService.updatedelivery(hmParam);             // 발주테이블.. 설치주소 업데이트
                             }

                        }




                        if (!"".equals(sCnslCon)) {
                            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                            mChngAccntInfo.put("mem_no", sMemNo);
                            mChngAccntInfo.put("accnt_no", sAccntNo);
                            mChngAccntInfo.put("cons_memo_tit", "회원정보수정 정보");
                            mChngAccntInfo.put("cons_memo_cntn", sCnslCon);
                            mChngAccntInfo.put("gubn", "80");
                            mChngAccntInfo.put("cnsl_cd", "01");
                            mChngAccntInfo.put("cnsl_man", oLoginUser.getUserid());

                            mChngAccntInfo.put("consno_sqno", "1");                       // 순번
                            mChngAccntInfo.put("actp_id", oLoginUser.getUserid());        // 접수자ID
                            mChngAccntInfo.put("cons_stat_cd", "30");                     // 처리상태
                            mChngAccntInfo.put("cons_dspsmddl_dtpt_cd", "10");            // 처리방법
                            mChngAccntInfo.put("rsps_dept_cd", oLoginUser.getTeamcd());   // 담당부서
                            mChngAccntInfo.put("chpr_id", oLoginUser.getUserid());        // 담당자ID

                            mChngAccntInfo.put("acpg_chnl_cd", "99");             		  // 접수채널 - 기타

                            String sAutoConsTyp3Cd = basVlService.getBasVlAsString("auto_cons_typ3_cd");
                            if ("".equals(sAutoConsTyp3Cd)) {
                                sAutoConsTyp3Cd = "CT01010201";
                            }
                            String sConsTyp1Cd = sAutoConsTyp3Cd.substring(0, 6) + "0000";
                            String sConsTyp2Cd = sAutoConsTyp3Cd.substring(0, 8) + "00";

                            mChngAccntInfo.put("cons_typ1_cd", sConsTyp1Cd);
                            mChngAccntInfo.put("cons_typ2_cd", sConsTyp2Cd);
                            mChngAccntInfo.put("cons_typ3_cd", sAutoConsTyp3Cd);
                            /*mChngAccntInfo.put("cons_typ1_cd", "CT01010000");             // 상담유형1 - 회원관리
                            mChngAccntInfo.put("cons_typ2_cd", "CT01010200");             // 상담유형2 - 회원관리정보변경
                            mChngAccntInfo.put("cons_typ3_cd", "CT01010201");             // 상담유형3 - 기본정보변경
*/
                            consService.insertCons(mChngAccntInfo);                       // 엔컴 마스터 상담 및 상세 상담 등록, PS 상담등록
                        }
                    }
                }

                // 녹취이력 저장
                if (!"".equals(sCtiCrncDtlId) && !"".equals(sAccntNo)) {
                    hmParam.put("dsps_typ_cd", sDspsTypCd);
                    ctiCrncHstrService.mergeRecProdDtl(hmParam);
                }

                mapOutVar.put("gv_accnt_no", sAccntNo);
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
     * 상품정보 삭제를 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-req-srch")
    public ModelAndView getWdrwReqSrch(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String result = "";
            String payResult = "";
            String cmsResult = "";
            String cardResult = "";

            cmsResult = dlwConsProdService.getCmsWdrwReqChk(sAccntNo);
            cardResult = dlwConsProdService.getCardWdrwReqChk(sAccntNo);

            if (("".equals(cmsResult) || cmsResult == null) && ("".equals(cardResult) || cardResult == null)) {
                result = "Y";
            } else {
                result = "N";
            }

            payResult = dlwConsProdService.getPayChk(sAccntNo);

            if("N".equals(payResult) && "Y".equals(result)) {
                result = "F";
            }

            mapOutVar.put("wdrwReqSrchFlag", result);

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
     * 상품정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteMemProdAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
                String sEmpleNo = StringUtils.defaultString((String) hmParam.get("emple_no"));

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("dat1", sMemNo);
                hmParam.put("cl1", "D");
                hmParam.put("emple_no", hmParam.get("amnd_id"));

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                dlwConsProdService.insertReqUpdDelInf(hmParam); // 변경 삭제 내역 등록

                // 녹취이력 저장
                if (!"".equals(sCtiCrncDtlId) && !"".equals(sAccntNo)) {
                    hmParam.put("dsps_typ_cd", "D");
                    hmParam.put("emple_no", sEmpleNo);
                    ctiCrncHstrService.mergeRecProdDtl(hmParam);
                }
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
     * 스마트라이프 카드정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smart-life/card")
    public ModelAndView getSmartLifeCardInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            Map<String, Object> mDtpt = dlwConsProdService.getSmartLifeCardInfo(sAccntNo);

            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
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

    /**
     * 상품정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hist/insert")
    public ModelAndView insertSearchHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("accnt_no", sAccntNo);

            //dlwConsProdService.insertSearchHist(hmParam);  조회이력은  안들어가게 처리함..

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
     * 발주정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/odrg-info")
    public ModelAndView getOdrgInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            ParamUtils.addCenterParam(hmParam);

            Map<String, Object> mDtpt = dlwConsProdService.getOdrgInfo(hmParam);
            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
                mapOutDs.put("ds_output", dtptMap);
            }

            List<Map<String, Object>> mList = dlvService.getDlvList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output_list", listMap);

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
     * 신한제휴카드인증 / 신한세이브약정인증
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sh-athn")
    public ModelAndView certfShinHanCard(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Socket socket = null;
        BufferedOutputStream bos = null;
        InputStream in = null;
        StringBuffer sendRecord = new StringBuffer();
        String result = "fail";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                sendRecord.append("0070");
                if ("card".equals((String)hmParam.get("cl"))) sendRecord.append("DAEMYUNG");
                else sendRecord.append("EDI03038");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
                Date current = new Date();
                String currentDate = sdf.format(current).substring(0, 8);
                String currentTime = sdf.format(current).substring(8, 14);
                sendRecord.append(currentDate);
                sendRecord.append(currentTime);

                byte pbUserKey[] = {99, -35, 76, -47, 34, 121, 43, 90, -88, 68,-107, 57, -8, -34, -72, 43};
                byte pbCipher[] = new byte[16];
                int pdwRoundKey[] = new int[32];
                SEED_KISA.SeedRoundKey(pdwRoundKey, pbUserKey);
                SEED_KISA.SeedEncrypt(((String)hmParam.get("card_no")).getBytes(), pdwRoundKey, pbCipher);

                for(int i = 0; i < 16; i++) {
                    //sendRecord.append(Integer.toHexString(0xff & pbCipher[i]).length() != 1 ? Integer.toHexString(0xff & pbCipher[i]) : (new StringBuilder("0")).append(Integer.toHexString(0xff & pbCipher[i])).toString());
                    sendRecord.append(Integer.toHexString(0xFF & pbCipher[i]).length() == 1 ? "0" + Integer.toHexString(0xFF & pbCipher[i]) : Integer.toHexString(0xFF & pbCipher[i]));
                }
                sendRecord.append("  ");
                sendRecord.append("              ");
                System.out.println((new StringBuilder("record : ")).append(sendRecord.toString()).append(" * size : ").append(sendRecord.length()).toString());

                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("58.150.242.13", 57439), 5000);
                    System.out.println("■■최초 연결 성공!■■");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("■■최초 연결 실패!■■");
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "fail");
                    if (socket != null) socket.close();
                    return modelAndView;
                }

                if(socket.isClosed()) {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("58.150.242.13", 57439), 5000);
                }
                bos = new BufferedOutputStream(socket.getOutputStream());

                try	{
                    bos.write(sendRecord.toString().getBytes());

                    if(socket.isBound())	{
                        socket.setSoTimeout(5000);
                        bos.flush();
                        byte buffer[] = new byte[74];
                        in = socket.getInputStream();
                        int bytesRead = in.read(buffer);
                        socket.close();
                        in.close();
                        bos.close();

                        String recvRecord = new String(buffer, 0, bytesRead);
                        result = recvRecord.substring(58, 60);
                        hmParam.put("result", result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "fail");
                } finally {
                    if (socket != null) socket.close();
                    if (bos != null) bos.close();
                    if (in != null) in.close();
                }

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

    /**
     * 종합현황을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cmph-psct")
    public ModelAndView getCmphPsctList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));	// 엑셀 여부

                // 페이징 정보
                if (!"Y".equals(sExcelYn)) {
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        int endCnt = Integer.parseInt(pMap.get("endNum") + "");
                        hmParam.put("page", endCnt / 100);
                    }
                }

                /* 권한별 자료권한 체크 제거, 컬럼 동적으로 가져오지 않고 정적으로 변경
                 * 2017.01.05
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                hmParam.put("paramAs", "EMP");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                Map<String, Object> quryMap = dlwConsProdService.getTotCondQury(hmParam);
                if (quryMap == null || quryMap.get("lvl") == null) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "현황 권한이 없습니다.\n관리자에게 문의하세요.");
                    return modelAndView;
                }

                log.debug("=========> quryMap : ");
                CommonUtils.printLog(quryMap);

                int sLvl = Integer.parseInt(CommonUtils.stringValueOf(quryMap.get("lvl")));
                String sDgLbQry = StringUtils.defaultString((String) quryMap.get("dgLbQry")); 	// 컬럼명
                String sDfQry = StringUtils.defaultString((String) quryMap.get("dfQry")); 		// 컬럼ID

                if (sLvl == 0) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "2");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "권한이 없습니다.");
                    //mapOutVar.put("rMsg", "noAuth");
                    return modelAndView;
                }
                hmParam.put("lvl", sLvl);
                hmParam.put("dgLbQry", sDgLbQry);
                hmParam.put("dfQry", sDfQry);
                 */



                if (!"Y".equals(sExcelYn)) {

                    /*
                     * 2017.01.04 : 기존에 3개의 쿠리와 자바메소드로 되어 있던것을 1개의 메소드와 1개의 쿼리로 변경
                     * 변경자 : 정출연
                    if (sLvl == 1) {
                        mList = dlwConsProdService.getTotCondListLv1(hmParam);
                    } else if (sLvl == 2) {
                       mList = dlwConsProdService.getTotCondListLv2(hmParam);  //찐짜
                    } else if (sLvl == 3) {
                        mList = dlwConsProdService.getTotCondListLv3(hmParam);
                    }
                    */
                    mList = dlwConsProdService.getTotCondListLv(hmParam);

                    int nTotal = 0;
                    if (mList != null && mList.size() > 0) {
                        nTotal = Integer.parseInt(mList.get(0).get("total_cnt") + "");
                    }

                    mapOutVar.put("tc_cmphPsct", nTotal);

                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output_header", dtptMap);

                //2017.12.04 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 해피콜 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hpcl-list")
    public ModelAndView getHpCallList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
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
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));

                if (!"Y".equals(sExcelYn)) {
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }
                int nTotal = dlwConsProdService.getHpCallCnt(hmParam);
                mapOutVar.put("tc_hpcl", nTotal);

                List<Map<String, Object>> mList = dlwConsProdService.getHpCallList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.02.19 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 해피콜을 일괄승인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hpcl/bnde-ackd")
    public ModelAndView saveHpclBndeAckd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sSelectcheck = StringUtils.defaultString((String) mapInVar.get("selectcheck"));
            hmParam.put("selectcheck", sSelectcheck);

            ParamUtils.addSaveParam(hmParam);
            dlwConsProdService.saveHpclBndeAckd(hmParam);

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