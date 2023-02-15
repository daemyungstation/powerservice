/*
 * (@)# DlwOnlnPymnAcntController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/22
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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.onln.service.DlwOnlnPymnAcntService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.com.utl.sim.service.SeedCipher;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 온라인회원 결제계좌 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/22
 * @프로그램ID DlwOnlnPymnAcnt
 */
@Controller
@RequestMapping(value = "/dlw-onln/pymn-acnt")
public class DlwOnlnPymnAcntController {

    @Resource
    private DlwOnlnPymnAcntService dlwOnlnPymnAcntService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    /**
     * 온라인회원 결제계좌 변경 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chng-list")
    public ModelAndView getDlwOnlnPymnAcntChngList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                int nTotal = dlwOnlnPymnAcntService.getOnlnPymnAcntChngCount(hmParam);
                mapOutVar.put("tc_onlnAcntChng", nTotal);


                List<Map<String, Object>> mList = dlwOnlnPymnAcntService.getOnlnPymnAcntChngList(hmParam);
                listMap.setRowMaps(paramDecode(mList));
                mapOutDs.put("ds_output", listMap);

/*
                List<Map<String, Object>> mList = paramDecode(dlwOnlnPymnAcntService.getOnlnPymnAcntChngList(hmParam));
                Map<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("unq_no", "2015082354");
                tmpMap.put("name", "석민");
                tmpMap.put("chng_seq", "999");
                tmpMap.put("id", "iw_test");
                tmpMap.put("accnt_no", "2016G1000002");
                tmpMap.put("bfr_pmt_gb", "06");
                tmpMap.put("bfr_bank_cd", "004");
                tmpMap.put("bfr_bank_nm", "국민");
                tmpMap.put("bfr_acnt_no", "21650204256336");
                tmpMap.put("bfr_card_cd", "05");
                tmpMap.put("bfr_card_nm", "신한");
                tmpMap.put("bfr_card_no", "4364200689827028");
                tmpMap.put("bfr_card_expr_yr", "21");
                tmpMap.put("bfr_card_expr_mm", "02");
                tmpMap.put("bfr_pmt_day", "15");
                tmpMap.put("aft_pmt_gb", "04");
                tmpMap.put("aft_bank_cd", "004");
                tmpMap.put("aft_bank_nm", "국민");
                tmpMap.put("aft_acnt_no", "21650204256336");
                tmpMap.put("aft_card_cd", "05");
                tmpMap.put("aft_card_nm", "신한");
                tmpMap.put("aft_card_no", "4364200689827028");
                tmpMap.put("aft_card_expr_yr", "21");
                tmpMap.put("aft_card_expr_mm", "02");
                tmpMap.put("aft_pmt_day", "15");
                tmpMap.put("hycl_able_time", "HC3");
                tmpMap.put("prcs_yn", "N");
                tmpMap.put("hp", "01075309852");
                tmpMap.put("idn_no", "890530");
                tmpMap.put("email", "mseok@inwoo.co.kr");

                List<Map<String, Object>> mList2 = new ArrayList<Map<String,Object>>();
                mList2.add(tmpMap);
                mList2.addAll(mList);
                listMap.setRowMaps(mList2);
                */
            }

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
     * 온라인회원 결제계좌 변경 변환완료여부를 업데이트한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trnt-cmpl/update")
    public ModelAndView updateOnlnTrntCmpl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            InetAddress ip_org = InetAddress.getLocalHost();
            String sIpAddr = ip_org.getHostAddress();

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("mod_ip", sIpAddr);
            hmParam.put("mod_id", hmParam.get("rgsr_id"));
            hmParam.put("chng_seq", mapInVar.get("chng_seq"));

            dlwOnlnPymnAcntService.updateOnlnTrntCmplAcntChng(hmParam);
            dlwOnlnPymnAcntService.updateOnlnTrntCmplMemChng(hmParam);

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
     * 온라인회원 결제계좌 정보를 복호화한다.
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
                     String sHp       = StringUtils.defaultString((String) mDtpt.get("hp")).trim();
                     String sBfrAcntNo      = StringUtils.defaultString((String) mDtpt.get("bfr_acnt_no")).trim();
                     String sBfrCardNo      = StringUtils.defaultString((String) mDtpt.get("bfr_card_no")).trim();
                     String sAftAcntNo      = StringUtils.defaultString((String) mDtpt.get("aft_acnt_no")).trim();
                     String sAftCardNo      = StringUtils.defaultString((String) mDtpt.get("aft_card_no")).trim();

                  /*
                     if (!"".equals(sEmail)) {
                         mDtpt.put("email", SeedCipher.decrypt(sEmail, "UTF-8"));
                     }
                     if (!"".equals(sHp)) {
                         mDtpt.put("hp", SeedCipher.decrypt(sHp, "UTF-8").replaceAll("-", ""));
                     }
                     if (!"".equals(sBfrAcntNo)) {
                         mDtpt.put("bfr_acnt_no", SeedCipher.decrypt(sBfrAcntNo, "UTF-8"));
                     }
                     if (!"".equals(sBfrCardNo)) {
                         mDtpt.put("bfr_card_no", SeedCipher.decrypt(sBfrCardNo, "UTF-8"));
                     }
                     if (!"".equals(sAftAcntNo)) {
                         mDtpt.put("aft_acnt_no", SeedCipher.decrypt(sAftAcntNo, "UTF-8"));
                     }
                     if (!"".equals(sAftCardNo)) {
                         mDtpt.put("aft_card_no", SeedCipher.decrypt(sAftCardNo, "UTF-8"));
                     }

                     */
                     if (!"".equals(sEmail)) {
                         mDtpt.put("email", sEmail);
                     }
                     if (!"".equals(sHp)) {
                         mDtpt.put("hp", sHp.replaceAll("-", ""));
                     }
                     if (!"".equals(sBfrAcntNo)) {
                         mDtpt.put("bfr_acnt_no", sBfrAcntNo);
                     }
                     if (!"".equals(sBfrCardNo)) {
                         mDtpt.put("bfr_card_no", sBfrCardNo);
                     }
                     if (!"".equals(sAftAcntNo)) {
                         mDtpt.put("aft_acnt_no", sAftAcntNo);
                     }
                     if (!"".equals(sAftCardNo)) {
                         mDtpt.put("aft_card_no", sAftCardNo);
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
      * 온라인회원 결제계좌 정보를 암호화한다.
      *
      * @param Map<String, Object> pMap
      * @return Map<String, Object>
      * @throws Exception
      */
      public Map<String, Object> paramEncode(Map<String, Object> pMap) {
          try {
              if (pMap != null && !pMap.isEmpty()) {
                  String sCell = StringUtils.defaultString((String) pMap.get("mem_hp")).trim();

                  if (!"".equals(sCell)) {
                      sCell = CommonUtils.convertPhoneString(sCell);			// '-' 처리
                      pMap.put("mem_hp", SeedCipher.encrypt(sCell, "UTF-8"));	// 암호화
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }

          return pMap;
      }
}