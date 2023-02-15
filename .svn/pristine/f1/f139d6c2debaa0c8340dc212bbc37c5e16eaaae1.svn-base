/*
 * (@)# DlwVrtlAcntController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletRequestMock;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletResponseMock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//2018.01.17 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwVrtlAcntService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명라이프웨이 가상계좌 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwVrtlAcnt
 */
@Controller
@RequestMapping(value = "/dlw/vrtl-acnt")
public class DlwVrtlAcntController {

    @Resource
    private DlwVrtlAcntService DlwVrtlAcntService;

    @Resource
    private DlwCmsService DlwcmsService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    public BasVlService basVlService;

    @Resource
    private CommonService commonService;

    /**
     * 대명라이프웨이 가상계좌 산출이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/clct-hstr/list")
    public ModelAndView getDlwCardCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam = listInDs.get(0);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwVrtlAcntService.getDlwVrtlAcntClctCount(hmParam);

            mapOutVar.put("tc_log", nTotal);

            List<Map<String, Object>> mList = DlwVrtlAcntService.getDlwVrtlAcntCsmm(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.18 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 가상계좌 정보를 강제마감처리한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/clct-hstr-cls")
    public ModelAndView updateDlwPymnCancNicepay(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                String seq = StringUtils.defaultString((String) hmParam.get("seq"));
                String expirydt = StringUtils.defaultString((String) hmParam.get("expiry_dt"));
                String vrtlaccntno = StringUtils.defaultString((String) hmParam.get("vrtl_accnt_no"));
                String bankcd = StringUtils.defaultString((String) hmParam.get("bank_cd"));

                hmParam.put("seq", seq);
                hmParam.put("expiry_dt", expirydt);
                hmParam.put("vrtl_accnt_no", vrtlaccntno);
                hmParam.put("bank_cd", bankcd);
          // 체크된것만 복사해서 넘김  불필요!!
         //       if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwVrtlAcntService.deleteDlwVrtlAcntClct(hmParam);
           //     }
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
     * 대명라이프웨이 가상계좌 산출 리스트를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw/list")
    public ModelAndView getDlwVrtlAcntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            List<Map<String, Object>> mList = DlwVrtlAcntService.getVrtlAccntWdrwReqList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.17 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 가상계좌 산출 대상자 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-target/list")
    public ModelAndView getDlwVrtlAcntWdrwTargetList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = DlwVrtlAcntService.getVrtlAccntTargetList(hmParam);
                listMap.setRowMaps(mList);
            }

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
     * 대명라이프웨이 가상계좌 산출 청구건수 업데이트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/inv-gunsu-update")
    public ModelAndView updateDlwVrtlAcntInvGunsu(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam = listInDs.get(0);

            hmParam.put("inv_cnt", DlwcmsService.getDlwLastPayNo(hmParam));//최종납입회차조회

            int totalAmt = 0;
            totalAmt = DlwVrtlAcntService.getInvAmt(hmParam);
            if (totalAmt <= 0) {
                totalAmt = 0;
            }

            mapOutVar.put("monPayAmt", totalAmt);

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
     * 대명라이프웨이 가상계좌 산출 청구건수 업데이트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-req")
    public ModelAndView insertVrtlAccntWdrwReq(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //임시저장목록 삭제
            DlwVrtlAcntService.deleteTempVrtlAccntWdrwReq(hmParam);

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwVrtlAcntService.insertTempVrtlAccntWdrwReq(hmParam);
                }
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            //int result = DlwVrtlAcntService.insertVrtlAccntWdrwReq(hmParam);
            //hmParam.put("result", Integer.valueOf(result));
            hmParam.put("result", null); // 추가, 정출연 - 2016.10.13
            DlwVrtlAcntService.insertVrtlAccntWdrwReq(hmParam);

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
     * 대명라이프웨이 가상계좌 산출 청구건수 업데이트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteNiceVrtlAccntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwVrtlAcntService.deleteNiceVrtlAccntWdrwList(hmParam);
                }
            }

            //2018.01.18 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 가상계좌 NICE 전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-send")
    public ModelAndView sendNiceVrtlAccntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            /****************************************************************************************/
            /****************************************************************************************/
            int totCnt = 0;
            int sucCnt = 0;
            int errCnt = 0;

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                hmParam.put("yymmdd", mapInVar.get("yymmdd"));
                hmParam.put("inv_dt", mapInVar.get("inv_dt"));
                hmParam.put("inv_time", mapInVar.get("inv_time"));
                hmParam.put("mon_pay_amt", mapInVar.get("mon_pay_amt"));

                try {
                    String mem_nm = (String)hmParam.get("mem_nm") != null ? (String)hmParam.get("mem_nm") : "";
                    if(!"".equals(mem_nm)) {
                        mem_nm = (new StringBuilder("(")).append(mem_nm).append(")").toString();
                    }


                    HttpServletRequestMock request = new HttpServletRequestMock();
                    HttpServletResponseMock response = new HttpServletResponseMock();

                    NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);

                    httpRequestWrapper.addParameter("VbankBankCode", (String)hmParam.get("bank_cd"));
                    httpRequestWrapper.addParameter("VbankNum", (String)hmParam.get("vrtl_accnt_no"));
                    httpRequestWrapper.addParameter("VBankAccountName", (new StringBuilder("대명라이프")).append(mem_nm).toString());
                    httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt")));
                    httpRequestWrapper.addParameter("VbankExpDate", (String)hmParam.get("inv_dt"));
                    httpRequestWrapper.addParameter("VbankExpTime", (String)hmParam.get("inv_time"));
                    httpRequestWrapper.addParameter("Moid", (String)hmParam.get("mem_no"));

                    httpRequestWrapper.addParameter("MID", "dmlife004m");
                    httpRequestWrapper.addParameter("EncodeKey", "Kw61T06XZpKe1SuwJn+hUBnLNwYrqiQWSDR/Xa8/Ua6ZpBnLgGUOkAgzPTFVqn52kyBZSn1y5ANLlCG+6kyoIA==");
                   // httpRequestWrapper.addParameter("MID", "nictest04m");
                   // httpRequestWrapper.addParameter("EncodeKey", "b+zhZ4yOZ7FsH8pm5lhDfHZEb79tIwnjsdA0FBXh86yLc6BJeFVrZFXhAoJ3gEWgrWwN+lJMV0W4hvDdbe4Sjw==");

                   //httpRequestWrapper.addParameter("MallIP", "61.39.175.220");
                    httpRequestWrapper.addParameter("MallIP", "183.111.69.174");
                    //httpRequestWrapper.addParameter("MallIP", "210.114.225.219");
                    NicePayWEB nicepayWEB = new NicePayWEB();

                    ParamUtils.addCenterParam(hmParam);
                    String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));

                    nicepayWEB.setParam("NICEPAY_LOG_HOME", sPayFilePath+"/web_site/nice_vrtl_log/send/");
                    nicepayWEB.setParam("APP_LOG", "1");
                    nicepayWEB.setParam("EVENT_LOG", "1");
                    nicepayWEB.setParam("EncFlag", "S");
                    nicepayWEB.setParam("SERVICE_MODE", "PY0");
                    nicepayWEB.setParam("Currency", "KRW");
                    nicepayWEB.setParam("PayMethod", "VBANKFVB01");
                    WebMessageDTO result = nicepayWEB.doService(httpRequestWrapper, response);
                    String resultCode = result.getParameter("ResultCode");
                    String resultMsg = result.getParameter("ResultMsg");
                    String tid = result.getParameter("TID");
                    /*
                    System.out.println("--------------------------------------------");
                    System.out.println((new StringBuilder("결과                   : ")).append(resultCode).toString());
                    System.out.println((new StringBuilder("결과 메시지        : ")).append(resultMsg).toString());
                    System.out.println((new StringBuilder("거래번호(TID) : ")).append(tid).toString());
                    System.out.println("--------------------------------------------");
                    */
                    hmParam.put("result_cd", resultCode);
                    hmParam.put("result_msg", resultMsg);
                    hmParam.put("tid", tid);
                    if("4120".equals(resultCode)) {
                        sucCnt++;
                    } else {
                        errCnt++;
                    }
                    DlwVrtlAcntService.updateNiceVrtlAccntWdrwReq(hmParam);
                } catch(Exception e) {
                    e.printStackTrace();
                    mapOutVar.put("result", "fail");
                }
            }

            mapOutVar.put("result", "success");
            mapOutVar.put("totCnt", String.valueOf(totCnt));
            mapOutVar.put("sucCnt", String.valueOf(sucCnt));
            mapOutVar.put("errCnt", String.valueOf(errCnt));


            /****************************************************************************************/
            /****************************************************************************************/
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
     * 대명라이프웨이 가상계좌 정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/info/list")
    public ModelAndView getDlwVrtlAcntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam = listInDs.get(0);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwVrtlAcntService.getVrtlAccntInfoCount(hmParam);

            mapOutVar.put("tc_info", nTotal);

            List<Map<String, Object>> mList = DlwVrtlAcntService.getVrtlAccntInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.17 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 가상계좌 정보를 강제마감처리한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recover")
    public ModelAndView updateVrtlAccntRecoverProc(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwVrtlAcntService.updateVrtlAccntRecoverProc(hmParam);
                }
            }

            //2018.01.17 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 가상계좌 입금오류건 입금처리
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-by-admin")
    public ModelAndView vrtlAcntPayByAdmin(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                DlwVrtlAcntService.vrtlAcntPayByAdmin(hmParam);
            }
            mapOutVar.put("result_msg", "true");

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
     * 대명라이프웨이 가상계좌 입금오류건 입금처리
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
/*
    @RequestMapping(value = "/niceVacctNoti")
    public void niceVacctNoti(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String PayMethod        = CommonUtils.nvls(request.getParameter("PayMethod")        ); //지불수단
        String MID              = CommonUtils.nvls(request.getParameter("MID")              ); //상점ID
        String MallUserID       = CommonUtils.nvls(request.getParameter("MallUserID")       ); //회원사 ID
        String Amt              = CommonUtils.nvls(request.getParameter("Amt")              ); //금액
        String name             = CommonUtils.nvls(request.getParameter("name")             ); //구매자명
        String GoodsName        = CommonUtils.nvls(request.getParameter("GoodsName")        ); //상품명
        String TID              = CommonUtils.nvls(request.getParameter("TID")              ); //거래번호
        String MOID             = CommonUtils.nvls(request.getParameter("MOID")             ); //주문번호
        String AuthDate         = CommonUtils.nvls(request.getParameter("AuthDate")         ); //입금일시 (yyMMddHHmmss)
        String ResultCode       = CommonUtils.nvls(request.getParameter("ResultCode")       ); //결과코드 ('4110' 경우 입금통보)
        String ResultMsg        = CommonUtils.nvls(request.getParameter("ResultMsg")        ); //결과메시지
        String VbankNum         = CommonUtils.nvls(request.getParameter("VbankNum")         ); //가상계좌번호
        String FnCd             = CommonUtils.nvls(request.getParameter("FnCd")             ); //가상계좌 은행코드
        String VbankName        = CommonUtils.nvls(request.getParameter("VbankName")        ); //가상계좌 은행명
        String VbankInputName   = CommonUtils.nvls(request.getParameter("VbankInputName")   ); //입금자 명

        String RcptTID   		= CommonUtils.nvls(request.getParameter("RcptTID")   		); // 현금영수증 거래번호
        String RcptType   		= CommonUtils.nvls(request.getParameter("RcptType")   		); // 현금 영수증 구분(0:미발행, 1:소득공제용, 2:지출증빙용)
        String RcptAuthCode   	= CommonUtils.nvls(request.getParameter("RcptAuthCode")   	); // 현금영수증 승인번호


        SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        String sYmd = smt.format(cal.getTime());

        String osName =System.getProperty("os.name").toUpperCase();
        System.out.println(">osName : " + osName);
        // String file_path = "/web_site/nice_vrtl_log/recv/nice_vacct_noti_result.log";

        String file_path = EgovProperties.getProperty("nice.vrtlAccntNotice.log.win");
        if (-1 == osName.indexOf("WINDOWS")) {
            file_path = EgovProperties.getProperty("nice.vrtlAccntNotice.log");
        }

        file_path = file_path + "\\" + sYmd + ".log";

        File file = new File(file_path);
        file.createNewFile();
        FileWriter fw = new FileWriter(file_path, true);
        fw.write("************************************************\r\n");
        fw.write("PayMethod : " + PayMethod + "\r\n");
        fw.write("MID : " + MID + "\r\n");
        fw.write("MallUserID : "+ MallUserID + "\r\n");
        fw.write("Amt : " + Amt + "\r\n");
        fw.write("name : " +  name + "\r\n");
        fw.write("GoodsName : " + GoodsName + "\r\n");
        fw.write("TID : "+ TID + "\r\n");
        fw.write("MOID : "+ MOID + "\r\n");
        fw.write("AuthDate : "+ AuthDate + "\r\n");
        fw.write("ResultCode : "+ ResultCode + "\r\n");
        fw.write("ResultMsg : "+ ResultMsg + "\r\n");
        fw.write("VbankNum : "+ VbankNum + "\r\n");
        fw.write("FnCd : "+ FnCd + "\r\n");
        fw.write("VbankName : "+ VbankName + "\r\n");
        fw.write("VbankInputName : "+ VbankInputName + "\r\n");
        fw.write("RcptTID : "+ RcptTID + "\r\n");
        fw.write("RcptType : "+ RcptType + "\r\n");
        fw.write("RcptAuthCode : "+ RcptAuthCode + "\r\n");
        fw.write("************************************************\r\n");
        fw.close();

        //가맹점 DB처리
        HashMap resultMap = new HashMap();
        HashMap hm = new HashMap();
        hm.put("pay_method", PayMethod);			//지불수단
        hm.put("mid", MID); 						//상점ID
        hm.put("mall_user_id", MallUserID);			//회원사 ID
        hm.put("amt", Amt);							//금액
        hm.put("name", name);						//구매자명
        hm.put("goods_name", GoodsName);			//상품명
        hm.put("tid", TID);							//거래번호
        hm.put("moid", MOID);						//주문번호
        hm.put("auth_date", AuthDate);				//입금일시 (yyMMddHHmmss)
        hm.put("result_code", ResultCode);			//결과코드 ('4110' 경우 입금통보)
        hm.put("result_msg", ResultMsg);			//결과메시지
        hm.put("vbank_num", VbankNum);				//가상계좌번호
        hm.put("fn_cd", FnCd);						//가상계좌 은행코드
        hm.put("vbank_name", VbankName);			//가상계좌 은행명
        hm.put("vbank_input_name", VbankInputName);	//입금자 명

        //가상계좌채번시 현금영수증 자동발급신청이 되었을경우 전달되며
        //RcptTID 에 값이 있는경우만 발급처리 됨
        hm.put("rcpt_tid", RcptTID);				//현금영수증 거래번호
        hm.put("rcpt_type", RcptType);				//현금 영수증 구분(0:미발행, 1:소득공제용, 2:지출증빙용)
        hm.put("rcpt_auth_code", RcptAuthCode);		//현금영수증 승인번호
        hm.put("err_pay_proc", "N");

        String sResult = "";
        try {
            int cudCnt = DlwVrtlAcntService.insertNiceVacctNoti(hm);
            sResult = "OK";
        } catch (Exception ex) {
            sResult = "FAIL";
        }

        ServletOutputStream os = response.getOutputStream();
        os.print(sResult);
        os.close();
    }.*/
    /**
     * 대명라이프웨이 가상계좌 NICE 전송  (강제마감처리    입금액을   0 원으로 처리!!! )
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-cancelsend")
    public ModelAndView cancelsendNiceVrtlAccntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            /****************************************************************************************/
            /****************************************************************************************/

            System.out.println(listInDs.size());
            CommonUtils.printLog(listInDs.get(0));
            int totCnt = 0;
            int sucCnt = 0;
            int errCnt = 0;

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                hmParam.put("yymmdd", mapInVar.get("yymmdd"));
                hmParam.put("inv_dt", mapInVar.get("inv_dt"));
                hmParam.put("inv_time", mapInVar.get("inv_time"));
                hmParam.put("wdrw_req_amt_cancel","555");  //(강제마감처리    입금액을   0 원으로 처리!!!   500이하는 가상계좌 산출 불가능  !!!  555 셋팅!!)

                try {
                    String mem_nm = (String)hmParam.get("mem_nm") != null ? (String)hmParam.get("mem_nm") : "";
                    if(!"".equals(mem_nm)) {
                        mem_nm = (new StringBuilder("(")).append(mem_nm).append(")").toString();
                    }


                    HttpServletRequestMock request = new HttpServletRequestMock();
                    HttpServletResponseMock response = new HttpServletResponseMock();

                    NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);

                    httpRequestWrapper.addParameter("VbankBankCode", (String)hmParam.get("bank_cd"));
                    httpRequestWrapper.addParameter("VbankNum", (String)hmParam.get("vrtl_accnt_no"));
                    httpRequestWrapper.addParameter("VBankAccountName", (new StringBuilder("대명라이프")).append(mem_nm).toString());
                    httpRequestWrapper.addParameter("Amt", String.valueOf(hmParam.get("wdrw_req_amt_cancel")));
                    httpRequestWrapper.addParameter("VbankExpDate", (String)hmParam.get("inv_dt"));
                    httpRequestWrapper.addParameter("VbankExpTime", (String)hmParam.get("inv_time"));
                    httpRequestWrapper.addParameter("Moid", (String)hmParam.get("mem_no"));

                    httpRequestWrapper.addParameter("MID", "dmlife004m");
                    httpRequestWrapper.addParameter("EncodeKey", "Kw61T06XZpKe1SuwJn+hUBnLNwYrqiQWSDR/Xa8/Ua6ZpBnLgGUOkAgzPTFVqn52kyBZSn1y5ANLlCG+6kyoIA==");
                   // httpRequestWrapper.addParameter("MID", "nictest04m");
                   // httpRequestWrapper.addParameter("EncodeKey", "b+zhZ4yOZ7FsH8pm5lhDfHZEb79tIwnjsdA0FBXh86yLc6BJeFVrZFXhAoJ3gEWgrWwN+lJMV0W4hvDdbe4Sjw==");

                   //httpRequestWrapper.addParameter("MallIP", "61.39.175.220");
                    httpRequestWrapper.addParameter("MallIP", "183.111.69.174");
                    //httpRequestWrapper.addParameter("MallIP", "210.114.225.219");
                    NicePayWEB nicepayWEB = new NicePayWEB();

                    ParamUtils.addCenterParam(hmParam);
                    String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));

                    nicepayWEB.setParam("NICEPAY_LOG_HOME", sPayFilePath+"/web_site/nice_vrtl_log/send/");
                    nicepayWEB.setParam("APP_LOG", "1");
                    nicepayWEB.setParam("EVENT_LOG", "1");
                    nicepayWEB.setParam("EncFlag", "S");
                    nicepayWEB.setParam("SERVICE_MODE", "PY0");
                    nicepayWEB.setParam("Currency", "KRW");
                    nicepayWEB.setParam("PayMethod", "VBANKFVB01");


                    WebMessageDTO result = nicepayWEB.doService(httpRequestWrapper, response);
                    String resultCode = result.getParameter("ResultCode");
                    String resultMsg = result.getParameter("ResultMsg");
                    String tid = result.getParameter("TID");


                    /*
                    System.out.println("--------------------------------------------");
                    System.out.println((new StringBuilder("결과                   : ")).append(resultCode).toString());
                    System.out.println((new StringBuilder("결과 메시지        : ")).append(resultMsg).toString());
                    System.out.println((new StringBuilder("거래번호(TID) : ")).append(tid).toString());
                    System.out.println("--------------------------------------------");
                    */
                    hmParam.put("result_cd", resultCode);
                    hmParam.put("result_msg", resultMsg);
                    hmParam.put("tid", tid);
                    if("4120".equals(resultCode)) {
                        sucCnt++;
                    } else {
                        errCnt++;
                    }
                    DlwVrtlAcntService.updateNiceVrtlAccntWdrwReq(hmParam);
                } catch(Exception e) {
                    e.printStackTrace();
                    mapOutVar.put("result", "fail");
                }
            }

            mapOutVar.put("result", "success");
            mapOutVar.put("totCnt", String.valueOf(totCnt));
            mapOutVar.put("sucCnt", String.valueOf(sucCnt));
            mapOutVar.put("errCnt", String.valueOf(errCnt));


            /****************************************************************************************/
            /****************************************************************************************/
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