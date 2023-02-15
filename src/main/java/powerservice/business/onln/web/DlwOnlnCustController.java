/*
 * (@)# DlwOnlnCustController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
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

package powerservice.business.onln.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.onln.service.DlwOnlnCustService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.com.utl.sim.service.SeedCipher;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 온라인 회원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
 * @프로그램ID DlwOnlnCust
 */
@Controller
@RequestMapping(value = "/dlw-onln/cust")
public class DlwOnlnCustController {

    @Resource
    private DlwOnlnCustService dlwOnlnCustService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/jner-list")
    public ModelAndView getDlwOnlnCustJnerList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                paramEncode(hmParam);
            }

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
            hmParam.put("paramAs", "EMP");
            String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
            hmParam.put("dataAthrQury", dataAthrQury);

            int nTotal = dlwOnlnCustService.getDlwOnlnCustJnerCount(hmParam);
            mapOutVar.put("tc_onlnCust", nTotal);

            List<Map<String, Object>> mList = dlwOnlnCustService.getDlwOnlnCustJnerList(hmParam);
            listMap.setRowMaps(paramDecode(mList));
            mapOutDs.put("ds_output", listMap);

            //2017.12.04 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 온라인회원 가입자 정보를 변환한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trnt")
    public ModelAndView getDlwOnlnCustTrnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                int succCnt = 0;
                int errCnt  = 0;
                int nameErrCnt = 0;

                ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                String sIpAddr = oServletRequestAttribute.getRequest().getRemoteAddr();

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sUserNm = oLoginUser.getUsernm();

                for (int i = 0; i < srchInDs.size(); i++) {
                    String sMsg = "";
                    hmParam = srchInDs.get(i);
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("ip_addr", sIpAddr);
                    hmParam.put("amnd_nm", sUserNm);

                    sMsg = dlwOnlnCustService.trntMemInfo(hmParam);

                    if ("success".equals(sMsg)) {
                        ++succCnt;
                    } else {
                        errCnt++;

                        if("nameErr".equals(sMsg)) ++nameErrCnt;
                    }
                }

                mapOutVar.put("tc_succCnt", succCnt);
                mapOutVar.put("tc_errCnt", errCnt);
                mapOutVar.put("tc_nameErrCnt", nameErrCnt);

                //2017.12.11 로그 추가 ////////////////////////////////////////////////////////////////////////////////
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
    * 온라인회원 가입자 정보를 복호화한다.
    *
    * @param List<Map<String, Object>> pList
    * @return List<Map<String, Object>>
    * @throws Exception
    */
    public List<Map<String, Object>> paramDecode(List<Map<String, Object>> pList) {
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        try {
            if (pList != null && !pList.isEmpty()) {
                for (Map<String, Object> mDtpt : pList) {
                    String sEmail       = StringUtils.defaultString((String) mDtpt.get("email")).trim();
                    String sCell        = StringUtils.defaultString((String) mDtpt.get("cell")).trim();
                    String sHomeTel     = StringUtils.defaultString((String) mDtpt.get("home_tel")).trim();
                    String sHomeAddr2   = StringUtils.defaultString((String) mDtpt.get("home_addr2")).trim();
                    String sAcntHp      = StringUtils.defaultString((String) mDtpt.get("acnt_hp")).trim();
                    String sBankAccntNo = StringUtils.defaultString((String) mDtpt.get("bank_accnt_no")).trim();

              /*
                    if (!"".equals(sEmail)) {
                        mDtpt.put("email", SeedCipher.decrypt(sEmail, "UTF-8"));
                    }
                    if (!"".equals(sCell)) {
                        mDtpt.put("cell", SeedCipher.decrypt(sCell, "UTF-8").replaceAll("-", ""));
                    }
                    if (!"".equals(sHomeTel)) {
                        mDtpt.put("home_tel", SeedCipher.decrypt(sHomeTel, "UTF-8").replaceAll("-", ""));
                    }
                    if (!"".equals(sHomeAddr2)) {
                        mDtpt.put("home_addr2", SeedCipher.decrypt(sHomeAddr2, "UTF-8"));
                    }
                    if (!"".equals(sAcntHp)) {
                        mDtpt.put("acnt_hp", SeedCipher.decrypt(sAcntHp, "UTF-8").replaceAll("-", ""));
                    }
                    if (!"".equals(sBankAccntNo)) {
                        mDtpt.put("bank_accnt_no", SeedCipher.decrypt(sBankAccntNo, "UTF-8"));
                    }
                 */
                    if (!"".equals(sEmail)) {
                        mDtpt.put("email", sEmail);
                    }
                    if (!"".equals(sCell)) {
                        mDtpt.put("cell", sCell.replaceAll("-", ""));
                    }
                    if (!"".equals(sHomeTel)) {
                        mDtpt.put("home_tel", sHomeTel.replaceAll("-", ""));
                    }
                    if (!"".equals(sHomeAddr2)) {
                        mDtpt.put("home_addr2", sHomeAddr2);
                    }
                    if (!"".equals(sAcntHp)) {
                        mDtpt.put("acnt_hp", sAcntHp.replaceAll("-", ""));
                    }
                    if (!"".equals(sBankAccntNo)) {
                        mDtpt.put("bank_accnt_no", sBankAccntNo);
                    }
                    mList.add(mDtpt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mList;
    }

    /**
     * 온라인회원 가입자 정보를 암호화한다.
     *
     * @param Map<String, Object> pMap
     * @return Map<String, Object>
     * @throws Exception
     */
     public Map<String, Object> paramEncode(Map<String, Object> pMap) {
         try {
             if (pMap != null && !pMap.isEmpty()) {
                 String sCell        = StringUtils.defaultString((String) pMap.get("cell")).trim();

                 if (!"".equals(sCell)) {
                     sCell = CommonUtils.convertPhoneString(sCell);			// '-' 처리
                     pMap.put("cell", SeedCipher.encrypt(sCell, "UTF-8"));	// 암호화
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }

         return pMap;
     }

}