/*
 * (@)# DlwCondController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cns.web.ConsController;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCertfService;
import powerservice.business.dlw.service.DlwCondService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.sys.web.NiceCreditController;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2018.03.16 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 대명라이프웨이 증서 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
 * @프로그램ID DlwCond
 */
@Controller
@RequestMapping(value = "/dlw/certf")
public class DlwCertfController {

    private final Logger log = LoggerFactory.getLogger(DlwCertfController.class);

    @Resource
    private DlwCertfService DlwCertfService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private DlwCondService DlwCondService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private ConsController consController;

    @Resource
    private CommonService commonService;

    /**
     * 대명라이프웨이 증서출력 - 일괄출력 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getCertfList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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






                String excel_fg = (String)mapInVar.get("excel_fg");

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "EMP");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                String condQury = "";
                String cond2 = CommonUtils.checkNull((String)hmParam.get("cond2"));

               if (!"".equals(cond2)) {
                    hmParam.put("cl_cd", hmParam.get("cond2"));
                    condQury = DlwCondService.getInqCondQry(hmParam);
                }
                hmParam.put("condQury", condQury);

                if (!"Y".equals(excel_fg)) {
                    int nTotal = DlwCertfService.getCertfCount(hmParam);
                    mapOutVar.put("tc_certf", nTotal);

                    List<Map<String, Object>> mList = DlwCertfService.getCertfList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
            }

            //2018.03.16 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 대명라이프웨이 증서출력 출력정보를 저장한다.
     *  1. 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *  2. 대명라이프웨이 증서출력 출력정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCertfPrintHist")
    public ModelAndView insertCertfHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> rec = new HashMap<String, Object>();
        Map<String, Object> mParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        // sss

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String send_mth = CommonUtils.nvls((String)mapInVar.get("send_mth"));
            String prt_kind = CommonUtils.nvls((String)mapInVar.get("prt_kind")); // 출력종류(** 회원증서(certfMember) 또는 회원약관(clauseMember))

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            for (int i=0; i<listInDs.size(); ++i) {
                rec = listInDs.get(i);

                /*************************************************************************************/
                /* 1. 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시) */
                /*************************************************************************************/
                mParam = new HashMap<String, Object>();
                ParamUtils.addSaveParam(mParam);

                mParam.put("accnt_no"		, rec.get("accnt_no"));
                mParam.put("mem_no"			, rec.get("mem_no"));
                mParam.put("cons_memo"		, "증서발급 정보");
                mParam.put("cnsl_con"		, "증서출력");
                mParam.put("gubn"			, "80");
                mParam.put("cnsl_cd"		, "01");
                mParam.put("pyin_chng_yn"	, "N");

                Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(mParam);

                //회원정보 없을경우 상담저장 안함
                if (mCust != null && mCust.size() > 0) {
                    String pyin_chng_yn = CommonUtils.checkNull((String)mParam.get("pyin_chng_yn"));
                    if ("".equals(pyin_chng_yn)) {
                        mParam.put("pyin_chng_yn", "Y"); //납입수단변경
                    } else {
                        mParam.put("pyin_chng_yn", "N"); //기타
                    }

                    consController.saveConsdlw(mParam);
                }

                /*************************************************************************************/
                /* 2. 대명라이프웨이 증서출력 출력정보를 저장한다. */
                /*************************************************************************************/
                mParam = new HashMap<String, Object>();
                ParamUtils.addSaveParam(mParam);

                mParam.put("accnt_no", rec.get("accnt_no"));
                mParam.put("send_mth", send_mth);
                mParam.put("reg_man", mParam.get("rgsr_id"));

                // 회원증서 또는 약관 출력에 따라 분기
                if("certfMember".equals(prt_kind)) {
                    mParam.put("certf_kind", "0004"); // 회원증서
                }
                else if("clauseMember".equals(prt_kind)) {
                    mParam.put("certf_kind", "0006"); // 약관
                }

                int chk = DlwCertfService.getCertPrintChk(mParam);
                if(chk == 0) {
                    mParam.put("prn_type", "01");
                } else {
                    mParam.put("prn_type", "02");
                }
                DlwCertfService.insertCertHist(mParam);

                List<Map<String, Object>> mList = DlwCertfService.getCertfList(mParam);
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
     * 증서관리
     *
     *
     * */
    @RequestMapping(value = "/jlist")
    public ModelAndView getCertfjlist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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
            //    ParamUtils.addSaveParam(hmParam);

                    List<Map<String, Object>> mList = DlwCertfService.getCertfprodList(hmParam);
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
     * 대명라이프웨이 증서 재발행 리스틈 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/jaeballist")
    public ModelAndView getjaeballistList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);



                // 페이징 정보
                DataSetMap  dlistInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if ( dlistInDs != null && dlistInDs.size() > 0) {
                    Map<String, Object> pMap = dlistInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                //세션
                ParamUtils.addSaveParam(hmParam);

                int nTotal = DlwCertfService.getjengseCount(hmParam);
                mapOutVar.put("total_count", nTotal);

                List<Map<String, Object>> mList = DlwCertfService.getjengseList(hmParam);
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



}