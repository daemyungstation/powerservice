/*
 * (@)# DlwResnController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.chn.service.ChatSndgHstrService;
import powerservice.business.cns.service.ConsService;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwConsProdService;
import powerservice.business.dlw.service.DlwPayService;
import powerservice.business.dlw.service.DlwResnService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
//2018.03.15 로그 추가

/**
 * 해약 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
 * @프로그램ID DlwResn
 */
@Controller
@RequestMapping(value = "/dlw/resn")
public class DlwResnController {

    @Resource
    private DlwResnService dlwResnService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private DlwConsProdService dlwConsProdService;

    @Resource
    private ConsService consService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private DlwEvntController dlwEvntController;

    @Resource
    private DlwCmsService dlwCmsService;

    @Resource
    private CommonService commonService;

    @Resource
    private DlwPayService dlwPayService;
    
    @Resource
    private DlwMallLinkedService DlwMallLinkedService;
    
    @Resource
    private ChatSndgHstrService chatSndgHstrService;

    /**
     * 해약정보 등록 체크
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/input-chk")
    public ModelAndView getResnInputchk(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);

            int evtChk = dlwResnService.getEventChk(hmParam);
            int resnChk = dlwResnService.getResnChk(hmParam);
            int taxtChk = dlwResnService.getTaxtChk(hmParam);
            int cmsChk = dlwResnService.getCmsChk(hmParam);
            int cmsReqChk = dlwResnService.getCmsReqCnt(hmParam);
            //콜센터 체크 유무.  20170227 정왕채  박영선 매니져 요청!!!!
            int callcenterChk = dlwResnService.getcallcenterChk(hmParam);
            String sProdCl = dlwResnService.getSelectProdCl(hmParam);
            String sJoinType = dlwResnService.getSelectJoinType(hmParam);

            hmParam.put("callcenterChk", 		callcenterChk);
            hmParam.put("evtChk", 		evtChk);
            hmParam.put("resnChk",	 	resnChk);
            hmParam.put("taxtChk",	 	taxtChk);
            hmParam.put("cmsChk",	 	cmsChk);
            hmParam.put("cmsReqChk", 	cmsReqChk);
            hmParam.put("prod_cl" 	,	sProdCl);
            hmParam.put("join_type"	,	sJoinType);

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
     * 해약 구분(해약/청약)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gubn")
    public ModelAndView getResnGubn(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("accnt_no", sAccntNo);
            int resnGubn = dlwResnService.getResnGubn(hmParam);
            mapOutVar.put("gv_resn_gubn", resnGubn);

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
     * CMS정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-info")
    public ModelAndView getCmsInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
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
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }
            hmParam.put("accnt_no", sAccntNo);

            Map<String, Object> tmpMap = dlwResnService.getCmsInfo(hmParam);

            if (tmpMap == null) {
                tmpMap = new HashMap<String, Object>();
                tmpMap.put("cms_chk", "N");
            } else {
                tmpMap.put("cms_chk", "Y");
            }
            dtptMap.setRowMaps(tmpMap);
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
     * 해약현황 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getConsList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
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

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                hmParam.put("paramAs", "e");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                ParamUtils.addSaveParam(hmParam);

                if (!"".equals(sAccntNo)) {
                    hmParam.put("acpt_mthd", "");
                    hmParam.put("prod_cd", "");
                    hmParam.put("pay_mthd", "");
                    hmParam.put("dept_cd", "");
                }

                int nTotal = dlwResnService.getResnCount(hmParam);

                mapOutVar.put("tc_resn", nTotal);

                List<Map<String, Object>> mList = dlwResnService.getResnList(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                mapOutVar.put("tc_dataAthrQury", dataAthrQury);
           }

            //2018.03.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 해약현황 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtpt")
    public ModelAndView getResnDtpt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }
            hmParam.put("accnt_no", sAccntNo);

            Map<String, Object> mResnDtpt = dlwResnService.getResnDtpt(hmParam);
                                                                                             
            if (mResnDtpt == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약상세 정보가 없습니다.");

                return modelAndView;
            }
            /* 회원몰 정보 조회*/            
            int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
            mResnDtpt.put("use_amt", (iMallAmt));      

            String sMemTel = StringUtils.defaultString((String) mResnDtpt.get("cell"));
            if ("".equals(sMemTel)) {
                sMemTel = StringUtils.defaultString((String) mResnDtpt.get("home_tel"));
                if ("".equals(sMemTel)) {
                    sMemTel = StringUtils.defaultString((String) mResnDtpt.get("wrpl_tel"));
                }
            }
            mResnDtpt.put("mem_tel", sMemTel);

            // 이마트 사용 중지 (임동진)
            //String sPoint= dlwResnService.getEmartIPoint(hmParam);
            mResnDtpt.put("point", 0);
            int monPayAmt = Integer.parseInt(String.valueOf(mResnDtpt.get("mon_pay_amt")));
            int newChanGunsu = Integer.parseInt(String.valueOf(mResnDtpt.get("new_chan_gunsu")));
            mResnDtpt.put("daeche_sum", monPayAmt * newChanGunsu);

            dtptMap.setRowMaps(mResnDtpt);
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
     * 구좌번호로 해약금 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-inq")
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
                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약금 조회대상 회원번호가 없습니다.");

                    return modelAndView; 
                }

                Map <String, Object> mDtpt = dlwConsProdService.getProdInfoInq(hmParam); // 회원 상품정보 조회
                
                /* 회원몰 정보 조회*/            
                int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
                mDtpt.put("use_amt", (iMallAmt));    
                

                String sProdCd = StringUtils.defaultString((String) mDtpt.get("prod_cd"));

                int exprNo = Integer.parseInt(mDtpt.get("expr_no") + "");
                int prodAmt = Integer.parseInt(mDtpt.get("prod_amt") + "");
                int payAmt = Integer.parseInt(mDtpt.get("pay_amt") + "");
                int payCount = Integer.parseInt(mDtpt.get("pay_amt_count") + "");
                int dcAmt = Integer.parseInt(mDtpt.get("dc_amt") + "");
                int newChanGunsu = Integer.parseInt(mDtpt.get("new_chan_gunsu") + "");
                int monPayAmt = Integer.parseInt(mDtpt.get("mon_pay_amt") + "");

                String sJoinDt = (String)mDtpt.get("join_dt");

                payAmt -= dcAmt;

                nmCount = dlwConsProdService.getNowMonthCount(sAccntNo); // 현재 회차 조회
                resnAmt = dlwConsProdService.getResnAmt(sProdCd, sAccntNo, nmCount, sJoinDt); // 해약환급금

                if(resnAmt < 0) {
                    if(payCount <= 60) {
                        accmPay = Math.round((double)payAmt - (double)payAmt * 0.1D);
                        if(exprNo >= 60) exprno = 60;
                        else exprno = exprNo;

                        double temp1 = (exprno - payCount) + 1;
                        double temp2 = temp1 / (double)exprno;
                        double temp3 = (double)prodAmt * 0.153D;
                        mskPay = Math.round((temp2 * temp3) / 10.0D) * 10L;
                        double temp4 = Math.round((accmPay - mskPay) * 0.9D);
                        resnAmt = (int)temp4;
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    } else {
                        int x = payCount - 60;
                        double temp6 = 80.5D + (double)x * 0.316D;
                        double temp8 = ((double)payAmt * temp6) / 100.0D;
                        resnAmt = (int)Math.floor(temp8);
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    }
                } else {
                    resnAmt = (int)Math.floor(resnAmt);
                }

                int chk = dlwResnService.getResnGubn(hmParam);

                hmParam.put("pay_amt", mDtpt.get("pay_amt"));
                hmParam.put("pay_amt1", mDtpt.get("pay_amt1"));
                hmParam.put("pay_amt2", mDtpt.get("pay_amt2"));
                hmParam.put("pay_amt3", mDtpt.get("pay_amt3"));

                /* 쇼핑몰 실 사용금액 추가 - 2018.01.22 */
                hmParam.put("use_amt", mDtpt.get("use_amt"));

                hmParam.put("new_chan_gunsu", newChanGunsu);
                hmParam.put("new_chan_sum", Integer.valueOf(newChanGunsu * monPayAmt));
                hmParam.put("dc_amt", mDtpt.get("dc_amt"));
                hmParam.put("ovrd_mon", mDtpt.get("ovrd_mon"));

                if(chk == 1 || "02".equals(resnGubn)) {
                    hmParam.put("resn_amt", Integer.valueOf(payAmt));
                    hmParam.put("resn_amt2", Integer.valueOf(payAmt));
                    hmParam.put("resn_gubn", "02");
                } else {
                    hmParam.put("resn_amt", Integer.valueOf(resnAmt));
                    hmParam.put("resn_amt2", Integer.valueOf(resnAmt));
                    hmParam.put("resn_gubn", "01");
                }

                String sPoint = dlwResnService.getEmartIPoint(hmParam);
                hmParam.put("point", sPoint);

                String sGasuAmt = dlwResnService.getResnGasuAmt(hmParam);
                hmParam.put("gasu_amt", sGasuAmt);

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
     * 서류 제출 기한 마감정보를 수정한다.
     * 해약 처리 정보를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update/senddt")
    public ModelAndView updateResnSenddt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                //세션
                ParamUtils.addSaveParam(hmParam);

                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    dlwResnService.updateResnSenddt(hmParam);
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
     * 서류 제출 기한 마감정보를 수정한다.
     * 해약 처리 정보를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update/proc")
    public ModelAndView updateResnProc(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                dlwResnService.updateResnProc(hmParam);

                /* ================================================================
                 * 날짜 : 20171226
                 * 이름 : 송준빈
                 * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약완료로 변경 (해약완료시)
                 * ================================================================
                 * */
                hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                hmParam.put("P_STATE", "6");

                //dlwResnService.updateResnMemberState(hmParam);
                DlwMallLinkedService.updateResnMallState(hmParam);

                /* ================================================================
                 * 날짜 : 20181114
                 * 이름 : 송준빈
                 * 추가내용 : 청구가 존재하는 회원인지 확인후 청구가 존재한다면 가수금 테이블에 insert
                 * ================================================================
                 * */

                List<Map<String, Object>> mList = dlwResnService.getWdrwReqConfirm(hmParam);


                if (mList.size() > 0)
                {
                	System.out.println("해약으로 인한 환불금 등록");
                	mList.get(0).put("auto_name", "resn");

                	System.out.println(mList.get(0));

                	dlwPayService.insertMemberReqRefund(mList.get(0));
                	
                	if("N".equals(mList.get(0).get("AUTH_YN"))) {
                		System.out.println("해약으로 인한 환불금 무승인 등록");
                    	//dlwPayService.insertNauthPayCancelResnEvnt(mList.get(0));
                	}
                }

                System.out.println("최종 param data " + hmParam.toString());
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
     * 상담내역 기록  - 제출기한마감
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert/etc")
    public ModelAndView insertConsEtc(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                String sAutoConsTyp3Cd = basVlService.getBasVlAsString("auto_cons_typ3_cd");
                if ("".equals(sAutoConsTyp3Cd)) {
                    sAutoConsTyp3Cd = "CT01010201";
                }
                String sConsTyp1Cd = sAutoConsTyp3Cd.substring(0, 6) + "0000";
                String sConsTyp2Cd = sAutoConsTyp3Cd.substring(0, 8) + "00";
                hmParam.put("cnsl_cd", "01");

                hmParam.put("cons_typ1_cd", sConsTyp1Cd);
                hmParam.put("cons_typ2_cd", sConsTyp2Cd);
                hmParam.put("cons_typ3_cd", sAutoConsTyp3Cd);

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("cnsl_man", oLoginUser.getUserid());

                hmParam.put("consno_sqno", "1");                       // 순번
                hmParam.put("actp_id", oLoginUser.getUserid());        // 접수자ID
                hmParam.put("cons_stat_cd", "30");                     // 처리상태
                hmParam.put("cons_dspsmddl_dtpt_cd", "10");            // 처리방법
                hmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());   // 담당부서
                hmParam.put("chpr_id", oLoginUser.getUserid());        // 담당자ID

                hmParam.put("acpg_chnl_cd", "99");   	// 접수채널 - 기타

                consService.insertCons(hmParam);
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
     * 해약 등록, 수정
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/{pagetype}")
    public ModelAndView saveResn(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sResnAcptDay = StringUtils.defaultString((String) hmParam.get("resn_acpt_day"));
                String sResnProcYn = StringUtils.defaultString((String) hmParam.get("resn_proc_yn"));

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);

                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("close_cl", "0003");
                hm.put("accnt_no", sAccntNo);
                hm.put("close_dt", sResnAcptDay);
                hm.put("emple_no", hmParam.get("amnd_id"));
                if ("N".equals(dlwEvntController.closeDataSaveYnChk(hm))) {
                    hmParam.put("edt_yn", "N");

                    dtptMap.setRowMaps(hmParam);
                    mapOutDs.put("ds_output", dtptMap);
                }

                if ("insertResn".equals(psPathType)) {			// 해약 등록
                    int cntChk = dlwResnService.getResnChk(hmParam);

                    if (cntChk <= 0) {
                        int cnt = dlwResnService.insertResn(hmParam);
                        if (cnt == 1) {
                            hmParam.put("msg", "success");
                        } else {
                            hmParam.put("msg", "error");
                        }
                    } else {
                        hmParam.put("msg", "duple");
                    }

                    /* ================================================================
                     * 날짜 : 20171226
                     * 이름 : 송준빈
                     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수로 변경 (해약접수시)
                     * ================================================================
                     * */
                    hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                    hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                    hmParam.put("P_STATE", "5");

                    System.out.println("insert인풋데이터 :: " +hmParam );
                    //dlwResnService.updateResnMemberState(hmParam);
                    DlwMallLinkedService.updateResnMallState(hmParam);

                    /* ================================================================
                     * 날짜 : 20190513
                     * 이름 : 송준빈
                     * 추가내용 : 고객상태가 청약철회시 가수금 테이블에 해당 정보를 insert 한다.
                     * ================================================================
                     * */
                    String sResnCl = (String)hmParam.get("resn_cl");
                    if(sResnCl.equals("02"))
                    {
                    	List<Map<String, Object>> mList = dlwResnService.getWdrwReqConfirm(hmParam);
                    	
                    	if (mList.size() > 0)
                        {
//                        	System.out.println("청약철회로 인한 가수금 등록");
                        	mList.get(0).put("auto_name", "resn_02");
                        	
                        	System.out.println("청약철회로 인한 가수금 등록");
                        	dlwPayService.insertMemberReqRefund(mList.get(0));
                        	
                        	if("N".equals(mList.get(0).get("AUTH_YN"))) {
                        		System.out.println("청약철회로 인한 가수금 무승인 등록");
                            	//dlwPayService.insertNauthPayCancelResnEvnt(mList.get(0));
                        	}
                        }
                   }
                } else if ("updateResn".equals(psPathType)) {	// 해약 수정
                    dlwResnService.updateResn(hmParam);

                    /* ================================================================
                     * 날짜 : 20171226
                     * 이름 : 송준빈
                     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수로 변경 (해약접수시)
                     * ================================================================
                     * */
                    hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                    hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                    hmParam.put("P_STATE", "5");

                    System.out.println("update인풋데이터 :: " +hmParam );
                    //dlwResnService.updateResnMemberState(hmParam);
                    DlwMallLinkedService.updateResnMallState(hmParam);
                }

                if ("".equals(sResnProcYn)) {
                    hmParam.put("resn_proc_yn", "");
                }

                hmParam.put("edt_yn", "Y");
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
     * 해약 발생시 CMS 부가서비스 등록 정보 해지
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delCmsInfo")
    public ModelAndView deleteCmsInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String rtMsg = "false";

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            List<Map<String, Object>> mList = dlwCmsService.getAccntInfo(hmParam);

            if (mList != null && mList.size() > 0) {
                Map<String, Object> tmpMap = mList.get(0);

                if (tmpMap != null) {
                    String chkDelFg = StringUtils.defaultString((String) tmpMap.get("del_fg"));
                    if ("N".equals(chkDelFg)) {
                        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                        tmpMap.put("app_cl", "3");
                        tmpMap.put("acpt_mthd", "04");
                        tmpMap.put("reg_man", oLoginUser.getUserid());

                        dlwCmsService.updateDlwCmsPymtMthd(tmpMap);

                        rtMsg = "success";
                    } else if ("Y".equals(chkDelFg)) {
                        rtMsg = "duple";
                    }

                    hmParam.put("delCmsMsg", rtMsg);
                    dtptMap.setRowMaps(hmParam);
                    mapOutDs.put("ds_output", dtptMap);
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
     * 해약 정보 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView resnDel(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sResnAcptDay = StringUtils.defaultString((String) hmParam.get("resn_acpt_day"));

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);

                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("close_cl", "0003");
                hm.put("accnt_no", sAccntNo);
                hm.put("close_dt", sResnAcptDay);
                hm.put("emple_no", hmParam.get("amnd_id"));

                if ("N".equals(dlwEvntController.closeDataSaveYnChk(hm))) {
                    hmParam.put("edt_yn", "N");

                    dtptMap.setRowMaps(hmParam);
                    mapOutDs.put("ds_output", dtptMap);
                }

                hmParam.put("new_yn", dlwResnService.resnDel(hmParam));

                /* ================================================================
                 * 날짜 : 20171226
                 * 이름 : 송준빈
                 * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 유효로 변경 (해약취소시)
                 * ================================================================
                 * */
                hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                hmParam.put("P_STATE", "1");

                System.out.println("DELETE인풋데이터 :: " +hmParam );
                //dlwResnService.updateResnMemberState(hmParam);
                DlwMallLinkedService.updateResnMallState(hmParam);

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
     * 해약현황 통계 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/stat-list")
    public ModelAndView getResnStatList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sStatGbn = StringUtils.defaultString((String) mapInVar.get("stat_gbn"));

            List<Map<String, Object>> mList = null;
            if ("Stat1".equals(sStatGbn)) {
                mList = dlwResnService.getResnStat1List(mapInVar);
            } else if ("Stat2".equals(sStatGbn)) {
                mList = dlwResnService.getResnStat2List(mapInVar);
            } else if ("Stat3".equals(sStatGbn)) {
                mList = dlwResnService.getResnStat3List(mapInVar);
            } else {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "통계구분이 없습니다.");

                return modelAndView;
            }
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
     * 매출입 현황
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    /*
    @RequestMapping(value = "/buychul-list")
    public ModelAndView getBuyChulList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>> getBuyChulList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwResnService.getBuyChulList(hmParam);
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

    */

    /**
     * 매입현황 상세조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    /*
    @RequestMapping(value = "/buydtl-list")
    public ModelAndView getBuyDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>> getBuyDtlList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwResnService.getBuyDtlList(hmParam);
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
    */

    /**
     * 회원매입매출 현황 (합계)
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/accpur-list")
    public ModelAndView getBuyChulList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>> getAccpurList");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = dlwResnService.getAccPurList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 최종마감 건수 조회
            int vFinalMgCnt = dlwResnService.srchCntAccntPurSaleMg(hmParam);
            mapOutVar.put("vFinalMgCnt", vFinalMgCnt);

            // 최종마감일 조회
            String vFinalMgDt = dlwResnService.srchMgDtAccntPurSaleMg(hmParam);
            mapOutVar.put("vFinalMgDt", vFinalMgDt);

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
     * 매입처별 매출입 최종마감 현황
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/accpur-mg-list")
    public ModelAndView getBuyChuMglList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>> getAccpurMgList");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = dlwResnService.getAccPurMgList(hmParam);
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
     * 회원매입매출 현황 (합계)
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/mem-list")
    public ModelAndView getPurMemList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            // 중간마감 건수
            int vMiddleMgCnt = dlwResnService.srchCntAccntPurSale(hmParam);
            int vMiddleMgCnt2 = dlwResnService.srchCntAccntPurSale2(hmParam); // 조건 제외


            // 최종마감 건수 조회
            hmParam.put("pur_comp", "");
            hmParam.put("accnt_no", "");
            int vFinalMgCnt2 = dlwResnService.srchCntAccntPurSaleMg(hmParam);
            mapOutVar.put("vFinalMgCnt2", vFinalMgCnt2);

            // 최종마감일 조회
            String vFinalMgDt = dlwResnService.srchMgDtAccntPurSaleMg(hmParam);
            mapOutVar.put("vFinalMgDt", vFinalMgDt);

            List<Map<String, Object>> mList;

            // 중간마감 데이터 조회
            //if (vMiddleMgCnt > 0) {
            if (vMiddleMgCnt > 0 || vMiddleMgCnt2 > 0) {
                mList = dlwResnService.getTmpMemPurList(hmParam);
                mapOutVar.put("vGubun", "middle_data");
                mapOutVar.put("tc_log", vMiddleMgCnt);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
            // 기초 데이터 조회
            //else {
            // (작업을 하지 않은 데이터는)
            // 최종마감 데이터만 있는 경우는 조회 불가
            else if(vMiddleMgCnt2 == 0 && vFinalMgCnt2 == 0) {
                mList = dlwResnService.getMemPurList(hmParam);
                mapOutVar.put("vGubun", "basic_data");

                int vBasicCnt = dlwResnService.getCntMemPurList(hmParam);
                mapOutVar.put("tc_log", vBasicCnt);

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
     * (최종마감) 회원별 상품모델 매입매출현황
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/mem-mg-list")
    public ModelAndView getPurMemMgList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);

                if (!"Y".equals(excel_fg)) {
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }



            // 최종마감 건수
            int vFinalMgCnt = dlwResnService.srchCntAccntPurSaleMg(hmParam);
            mapOutVar.put("tc_log", vFinalMgCnt);

            List<Map<String, Object>> mList = dlwResnService.getMemPurMgList(hmParam);
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
     * 회원별 상품모델 매입매출 입력 및 수정
     *  -
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/accnt_pursale_save")
    public ModelAndView saveAccntPurSale(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
        String vGubun = StringUtils.defaultString((String) mapInVar.get("vGubun"));

        if (srchInDs != null && srchInDs.size() > 0) {
            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);
                //CommonUtils.printLog(hmParam);

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                System.out.println("================= accnt_pursale save PARAMETER : " + hmParam);


                // 기초 데이터로 입력하는 경우 INSERT
                if ("basic_data".equals(vGubun)) {
                    hmParam.put("reg_man", oLoginUser.getUserid());
                    dlwResnService.saveAccntPurSale(hmParam);
                    mapOutVar.put("saveGb", "INSERT");
                }
                // 중간마감 데이터를 수정하는 경우 UPDATE
                else {
                    hmParam.put("upd_man", oLoginUser.getUserid());
                    dlwResnService.updateAccntPurSale(hmParam);
                    mapOutVar.put("saveGb", "UPDATE");
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 회원별 상품모델 매입매출 삭제
     *  -
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/accnt_pursale_delete")
    public ModelAndView deleteAccntPurSale(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

        if (srchInDs != null && srchInDs.size() > 0) {
            hmParam = srchInDs.get(0);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            System.out.println("================= accnt_pursale delete PARAMETER : " + hmParam);

            hmParam.put("upd_man", oLoginUser.getUserid());
            dlwResnService.deleteAccntPurSale(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 회원별 상품모델 매입매출 최종마감
     *  -
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/accnt_pursale_mg_save")
    public ModelAndView saveAccntPurSaleMg(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

        if (srchInDs != null && srchInDs.size() > 0) {
            hmParam = srchInDs.get(0);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("reg_man", oLoginUser.getUserid());

            dlwResnService.saveAccntPurSaleMg(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }



    /**
     * 회원별 해약환급금 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/selectResnAmtAccnt")
    public ModelAndView selectResnAmtAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        long totResnAmt = 0L;

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>> selectResnAmtAccnt");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = dlwResnService.selectResnAmtAccnt(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            totResnAmt = dlwResnService.selectResnAmtSum(hmParam);
            mapOutVar.put("fv_tot_resn_amt", totResnAmt);

            //2018.03.20 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 회원별 해약환급금 마감
     *  -
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/callUPResnAmt")
    public ModelAndView callUPResnAmt(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            int rst = dlwResnService.callUPResnAmt(xpDto);

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
     * 회원별 해약환급금 마감해지
     *  -
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteResnAmt")
    public ModelAndView deleteResnAmt(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            int rst = dlwResnService.deleteResnAmt(xpDto);

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
    
    /** ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
    @RequestMapping(value = "/getCodeBank")
    public ModelAndView getAlowAccntNoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sCdNm = StringUtils.defaultString((String) mapInVar.get("cd_nm"));
            
            if( (sCdNm != null) || (!sCdNm.equals("")) )
            {
                List<Map<String, Object>> mList = dlwResnService.getCodeBank(sCdNm);
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
    
    @RequestMapping(value = "/updateCurrentResnAmt")
    public ModelAndView updateCurrentResnAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        DataSetMap dsProd = (DataSetMap)mapInDs.get("ds_input1");
        DataSetMap dsResnAmt = (DataSetMap)mapInDs.get("ds_input2");

        if (dsProd != null && dsProd.size() > 0 && dsResnAmt != null && dsResnAmt.size() > 0) 
        {

        	hmParam1 = dsProd.get(0);
        	hmParam2 = dsResnAmt.get(0);
        	
        	//String sResnAmt = (String)hmParam1.get("resn_amt");
        	//String sCurrentResnAmt = (String)hmParam2.get("resn_amt");
        
        	//int x = dlwResnService.updateCurrentResnAmt(hmParam2);
                
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 해약현황 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newList")
    public ModelAndView getConsNewList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));
                String sSvcId = (String)hmParam.get("svc_id");

                if (!"Y".equals(sExcelYn)) {
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                hmParam.put("paramAs", "e");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                ParamUtils.addSaveParam(hmParam);

                if (!"".equals(sAccntNo)) {
                    hmParam.put("acpt_mthd", "");
                    hmParam.put("prod_cd", "");
                    hmParam.put("pay_mthd", "");
                    hmParam.put("dept_cd", "");
                }
                
                if(sSvcId.equals("tp_resn1"))
                {
                	int nTotal1 = dlwResnService.getResnNewCount(hmParam);

                    mapOutVar.put("tc_resn", nTotal1);

                    List<Map<String, Object>> mList1 = dlwResnService.getResnNewList(hmParam);

                    listMap.setRowMaps(mList1);
                    mapOutDs.put("ds_output", listMap);
                }
                else if(sSvcId.equals("tp_resn2"))
                {
                	int nTotal2 = dlwResnService.getResnNewCount2(hmParam);

                    mapOutVar.put("tc_resn2", nTotal2);

                    List<Map<String, Object>> mList2 = dlwResnService.getResnNewList2(hmParam);

                    listMap.setRowMaps(mList2);
                    mapOutDs.put("ds_output2", listMap);
                }
                else if(sSvcId.equals("tp_resn3")) {
                	                	
                	int nTotal3 = dlwResnService.getResnNewCount3(hmParam);

                    mapOutVar.put("tc_resn3", nTotal3);

                    List<Map<String, Object>> mList3 = dlwResnService.getResnNewList3(hmParam);

                    listMap.setRowMaps(mList3);
                    mapOutDs.put("ds_output3", listMap);
                }     
                else if(sSvcId.equals("tp_resn0")) {
                	                	
                	int nTotal0 = dlwResnService.getResnNewCount0(hmParam);

                    mapOutVar.put("tc_resn0", nTotal0);

                    List<Map<String, Object>> mList0 = dlwResnService.getResnNewList0(hmParam);

                    listMap.setRowMaps(mList0);
                    mapOutDs.put("ds_output0", listMap);
                }
                
                mapOutVar.put("tc_dataAthrQury", dataAthrQury);
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
     * 회원 만기일자 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMangiDate")
    public ModelAndView getMangiDate(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = (String)mapInVar.get("accnt_no");

            if (sAccntNo != null || sAccntNo != "" || !sAccntNo.equalsIgnoreCase(""))
            {
            	hmParam.put("accnt_no", sAccntNo);
                String sMangiDate = dlwResnService.getMangiDate(hmParam);

                mapOutVar.put("xMangiDate", sMangiDate);
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 만기일자를 알수 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 회원 환급 통장 이름 확인 
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getTsConfirmSend")
    public ModelAndView getTsConfirmSend(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strBankCd = (String)mapInVar.get("banksett");		//은행코드
            String strBankNo = (String)mapInVar.get("noacct");		//계좌번호
            String strName = (String)mapInVar.get("nmcomp");		//이름
            String strMid = (String)mapInVar.get("mid");				//MID
            
            String strRtnName = "";											//결과 계좌명
            String strRtnErrMsg = "";											//결과 메세지
            String strRtnCode = "";												//결과 코드 
            
            String ActionUrl = "https://iniweb-api.inicis.com/api/acctCheck.jsp";
            
    		Map<String, String> param = new HashMap<>();
    		param.put("mid", strMid);
    		param.put("banksett", strBankCd);
    		param.put("noacct", strBankNo);
    		param.put("rltURL", "https://test.com");
    		
    		String res = processHTTP(ActionUrl, param);
    		Map<String, String> resMap = splitQuery(res);
    		
    		strRtnName = resMap.get("strRet").toString();
    		strRtnErrMsg = resMap.get("strErrMsg").toString();
    		strRtnCode = resMap.get("code").toString();
    		
    		if (strRtnCode.equals("000")){
        		if (!strRtnName.equals(strName)){
        			strRtnErrMsg = "예금주명이 상이합니다. 확인바랍니다 ("+strRtnName+")";
        		}
    		}
    		
            mapOutVar.put("xTsConfirmResultMsg", strRtnErrMsg);
 
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /*
     ************************************************************************************************************************
     *  통장이름 확인에 필요한 메소드 
     * */
	public static String processHTTP(String url, Map<String, String> params) throws Exception
	{
		HttpPost post = new HttpPost(url);

		List<NameValuePair> urlParameters = new ArrayList<>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet())
		{
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters, "euc-kr"));

		String res = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post))
		{
			Header[] headerNames = response.getAllHeaders();
			for (Header header : headerNames)
			{
				if (header.getName().equals("Location"))
					res = header.getValue();
			}

			response.close();
			httpClient.close();
		}
		return res;
	}

	public static Map<String, String> splitQuery(String location) throws UnsupportedEncodingException, Exception
	{
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		URL url = new URL(location);

		String query = url.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs)
		{
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "utf-8"), URLDecoder.decode(pair.substring(idx + 1), "utf-8"));
		}
		return query_pairs;
	}
	
    /**
     * 해약 회원 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ResnAccntInfo")
    public ModelAndView getResnAccntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String sSeq = StringUtils.defaultString((String) mapInVar.get("seq"));
            
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");
                return modelAndView;
            }
            
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("seq", sSeq);

            Map<String, Object> mResnDtpt = dlwResnService.getResnAccntInfo(hmParam);
                                                                                             
            if (mResnDtpt == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약상세 정보가 없습니다.");

                return modelAndView;
            }
            
            /* 회원몰 정보 조회*/            
            //int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
           //mResnDtpt.put("use_amt", (iMallAmt));      

            dtptMap.setRowMaps(mResnDtpt);
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
     * 해약 상세 정보를 검색한다. (해약대상자)
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ResnDetailInfo")
    public ModelAndView getResnDetailInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String sSeq = StringUtils.defaultString((String) mapInVar.get("seq"));
            
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");
                return modelAndView;
            }
            
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("seq", sSeq);

            Map<String, Object> mResnDtpt = dlwResnService.getResnDetailInfo(hmParam);
                                                                                             
            if (mResnDtpt == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약상세 정보가 없습니다.");

                return modelAndView;
            }
            
            /* 회원몰 정보 조회*/            
            //int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
           //mResnDtpt.put("use_amt", (iMallAmt));      

            dtptMap.setRowMaps(mResnDtpt);
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
     * 해약 회원 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ResnSignInfo")
    public ModelAndView getResnSignInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String sSignNo = StringUtils.defaultString((String) mapInVar.get("sign_no"));
            
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");
                return modelAndView;
            }
            
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("sign_no", sSignNo);

            List<Map<String, Object>> mResnDtpt = dlwResnService.getResnSignInfo(hmParam);
                                                                                             
            if (mResnDtpt == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약상세 정보가 없습니다.");

                return modelAndView;
            }

            dtptMap.setRowMaps(mResnDtpt);
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
     * 해약 등록
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertResnInfo")
    public ModelAndView insertResnInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);                                

                int cnt = dlwResnService.insertResnInfo(hmParam);	//해약등록
                
                /* 해약 상담이력 등록 */
                dlwResnService.insertResnCounsel(hmParam);
                
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
     * 해약 수정
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateResnInfo") 
    public ModelAndView updateResnInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));			// 회원번호
                String sUpdateGbn  = StringUtils.defaultString((String) mapInVar.get("update_gbn")); // 업데이트구분값

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);
                
                hmParam.put("update_gbn", sUpdateGbn);		//업데이트 구분 (업데이트, 삭제)
                
                int cnt = dlwResnService.updateResnInfo(hmParam);
                
                System.out.println(">>>>> : " + hmParam);
                
                /* 해약 상담이력 등록 */
                dlwResnService.insertResnCounsel(hmParam);
                
                //문자 LMS처리
                if("Y".equals(hmParam.get("resn_proc_yn")) &&  "U".equals(sUpdateGbn)) {
                	//쇼핑몰 연동 Table 에 고객의 상태를 해약완료로 변경 (해약완료시)
                    hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                    hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                    hmParam.put("P_STATE", "6");

                    //dlwResnService.updateResnMemberState(hmParam);
                    DlwMallLinkedService.updateResnMallState(hmParam);

                    //청구가 존재하는 회원인지 확인후 청구가 존재한다면 가수금 테이블에 insert
                    List<Map<String, Object>> mList = dlwResnService.getWdrwReqConfirm(hmParam);


                    if (mList.size() > 0)
                    {
                    	System.out.println("해약으로 인한 환불금 등록");
                    	mList.get(0).put("auto_name", "resn");

                    	System.out.println(mList.get(0));

                    	dlwPayService.insertMemberReqRefund(mList.get(0));
                    }
                	
                	
                	//System.out.println("111111:"+hmParam);
                	chatSndgHstrService.insertSysoneSmsSend(hmParam);
                	chatSndgHstrService.insertChatSndgHstr(hmParam);
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
     * 해약 상세 정보를 검색한다. (해약대상자)
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ReadyCashReal")
    public ModelAndView getReadyCashRealAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                        
            String strAccntNo = (String)mapInVar.get("accnt_no");		//회원번호
            
            hmParam.put("accnt_no", strAccntNo);
            
            String strReadyCahs = dlwResnService.getReadyCashRealAmt(hmParam);
                		    		
            mapOutVar.put("retReadCashAmt", strReadyCahs);
 
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 해약 회원 청구 정보 조회
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ResnReqInfo")
    public ModelAndView getResnReqInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");
                return modelAndView;
            }
            
            hmParam.put("accnt_no", sAccntNo);

            List<Map<String, Object>> mResnDtpt = dlwResnService.getResnReqInfo(hmParam);
                                                                                             
            if (mResnDtpt == null) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "해약 청구 상세 정보가 없습니다.");

                return modelAndView;
            }
                       
            dtptMap.setRowMaps(mResnDtpt);
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
     * 해약자 환불 등록 (청약철회, 환급등)
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertResnRefund")
    public ModelAndView insertResnRefund(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }
            
            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String sRefundNote = hmParam.get("refund_etc").toString();
            
            hmParam.put("accnt_no", sAccntNo);
            hmParam.put("refund_etc", sRefundNote);

            ParamUtils.addSaveParam(hmParam);
                        
            int cnt = dlwResnService.insertResnRefund(hmParam);
            
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
     * 해약(전자서명) 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resnSignList")
    public ModelAndView getResnSignList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sExcelYn = StringUtils.defaultString((String) hmParam.get("excel_yn"));
                String sSvcId = (String)hmParam.get("svc_id");

                if (!"Y".equals(sExcelYn)) {
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                hmParam.put("paramAs", "e");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                ParamUtils.addSaveParam(hmParam);

                if (!"".equals(sAccntNo)) {
                    hmParam.put("acpt_mthd", "");
                    hmParam.put("prod_cd", "");
                    hmParam.put("pay_mthd", "");
                    hmParam.put("dept_cd", "");
                }
                
                int nTotal = dlwResnService.getResnSignCount(hmParam);

                mapOutVar.put("tc_resn", nTotal);

                List<Map<String, Object>> mList = dlwResnService.getResnSignList(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                
                mapOutVar.put("tc_dataAthrQury", dataAthrQury);
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
    
    
    /*
     ************************************************************************************************************************
     * */
}