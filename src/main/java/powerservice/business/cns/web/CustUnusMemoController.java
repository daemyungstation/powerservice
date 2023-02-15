/*
 * (@)# CustUnusMemoController.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.cns.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.CustUnusMemoService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;

/**
 * 고객 특이메모 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CustUnusMemo
 */
@Controller
@RequestMapping(value = "/cons/cust-unus-memo")
public class CustUnusMemoController {

    @Resource
    private CustUnusMemoService custUnusMemoService;

    @Resource
    private CommonService commonService;

    /**
     * 고객 특이메모 정보를 검색한다.
     *
     * @param psPageType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    @ResponseBody
    public ModelAndView getCustUnusMemoList(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 엑셀조회시 전체조회 요청 - 2018.03.30
            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

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

            String sBzptDivYn = StringUtils.defaultString((String) hmParam.get("bzpt_div_yn"));
            if ("Y".equals(sBzptDivYn)) {
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }

            if ("usr".equals(psPathTyp)) { // 사용자 화면
                hmParam.put("user_id", oLoginUser.getUserid());
            } else { // 관리자 화면
                Boolean bMineFlag = Boolean.valueOf((String)hmParam.get("mine_flag")).booleanValue();
                if (bMineFlag != null && bMineFlag) {
                    hmParam.put("actp_id", oLoginUser.getUserid());
                }
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = custUnusMemoService.getCustUnusMemoCount(hmParam);
            mapOutVar.put("tc_cust_memo", nTotal);

            List<Map<String, Object>> mList = custUnusMemoService.getCustUnusMemoList(hmParam);
            listMap.setRowMaps(mList);
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
     * 고객 특이메모 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCustUnusMemo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sCustUnusMemoId = StringUtils.defaultString((String) hmParam.get("cust_unus_memo_id"));

                ParamUtils.addSaveParam(hmParam);

                if ("".equals(sCustUnusMemoId)) { // 등록
                    // 등록할 경우에만 업체정보 설정
                    // 업체 등록 후 관리자가 수정시 전체 표출을 방지하기 위해
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    String sAthrCd = oLoginUser.getAuthoritycd();
                    String sTeamCd = oLoginUser.getTeamcd();
                    if (sAthrCd.contains("TM")) {
                        hmParam.put("bzpt_div", sTeamCd);
                    } else {
                        hmParam.put("bzpt_div", "999999");
                    }

                    sCustUnusMemoId = custUnusMemoService.insertCustUnusMemo(hmParam);
                } else { // 수정
                    custUnusMemoService.updateCustUnusMemo(hmParam);
                }

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
     * 고객 특이메모 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteCustUnusMemo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sCustUnusMemoId = StringUtils.defaultString((String) mapInVar.get("cust_unus_memo_id"));

            if ("".equals(sCustUnusMemoId)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원 특이메모 아이디가 없습니다.");

                return modelAndView;
            }

            hmParam.put("cust_unus_memo_id", sCustUnusMemoId);

            custUnusMemoService.deleteCustUnusMemo(hmParam);

            //2017.12.11 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 고객 특이메모 정보를 검색한다.
     *
     * @param psPageType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/exsc-cnt")
    @ResponseBody
    public ModelAndView getCustUnusMemoCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sBzptDivYn = StringUtils.defaultString((String) mapInVar.get("bzpt_div_yn"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원의 고유번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("mem_no", sMemNo);

            if ("Y".equals(sBzptDivYn)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("user_id", oLoginUser.getUserid());

            ParamUtils.addCenterParam(hmParam);

            int nTotal = custUnusMemoService.getCustUnusMemoCnt(hmParam);
            mapOutVar.put("tc_cust_memo_cnt", nTotal);

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
     * 특이고객관리 엑셀 업로드
     */
    @RequestMapping(value = "/excel-upload")
    public ModelAndView insertSpecialAccntExcelList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> mParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map<String, Object> mapInVar     = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap mInDsList = (DataSetMap)mapInDs.get("ds_input");
            if (mInDsList == null || mInDsList.size() < 1) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "업로드 리스트가 없습니다.");

                return modelAndView;
            }

            ParamUtils.addSaveParam(mParam);
            custUnusMemoService.insertSpecialAccntExcelList(mInDsList, mParam);

            mapOutDs.put("ds_output", mInDsList);
        } catch(Exception e) {
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

}