/*
 * (@)#CtiCrncHstrController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
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
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.CtiCrncHstrService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.14 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * CTI통화 이력을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
 * @프로그램ID CtiCrncHstr
 */
@Controller
@RequestMapping(value = "/cti-hstr")
public class CtiCrncHstrController {

    @Resource
    private CtiCrncHstrService ctiCrncHstrService;

    @Resource
    private CommonService commonService;

    /**
     * CTI통화 이력 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getCtiCrncHstr(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                String sBzptDivYn = StringUtils.defaultString((String) hmParam.get("bzpt_div_yn"));
                if ("Y".equals(sBzptDivYn)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    String sAthrCd = oLoginUser.getAuthoritycd();
                    String sTeamCd = oLoginUser.getTeamcd();
                    if (sAthrCd.contains("TM")) {
                        hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = ctiCrncHstrService.getCtiCrncHstrCount(hmParam);
                List<Map<String, Object>> mList = ctiCrncHstrService.getCtiCrncHstrList(hmParam);

                mapOutVar.put("tc_ctiHstr", nTotal);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
           }

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * CTI통화 이력 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCtiCrncHstr(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 		// 상담정보
            Map hmParam = listInDs.get(0);

            String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
            String sOperation = StringUtils.defaultString((String) hmParam.get("operation"));

            ParamUtils.addSaveParam(hmParam);

            if ("".equals(sCtiCrncDtlId)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_div", sTeamCd);
                } else {
                    hmParam.put("bzpt_div", "999999");
                }

                sCtiCrncDtlId = ctiCrncHstrService.insertCtiCrncDtl(hmParam);
            } else {
                ctiCrncHstrService.updateCtiCrncDtl(hmParam);
            }

            /* 통화 종료 후에도 업무 녹취 연결을 위해 초기화 막음
            if ("clear".equals(sOperation)) {
                mapOutVar.put("gv_cti_crnc_dtl_id", "");
            } else {
                mapOutVar.put("gv_cti_crnc_dtl_id", sCtiCrncDtlId);
            }
            */
            mapOutVar.put("gv_cti_crnc_dtl_id", sCtiCrncDtlId);

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
     * 녹취 이력(상담/상품/TM) 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert/{pagetype}")
    public ModelAndView insertCtiCrncHstr(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 		// 상담정보
            Map hmParam = listInDs.get(0);

            String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
            String sConsno = StringUtils.defaultString((String) hmParam.get("consno"));
            String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
            String sCmpgId = StringUtils.defaultString((String) hmParam.get("cmpg_id"));

            if ("".equals(sCtiCrncDtlId)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "CTI이력아이디가 없습니다.");

                return modelAndView;
            }

            if ("cons".equals(psPathType)) {
                if ("".equals(sConsno)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "상담아이디가 없습니다.");

                    return modelAndView;
                }
                ctiCrncHstrService.mergeRecConsDtl(hmParam);
            } else if ("prod".equals(psPathType)) {
                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                    return modelAndView;
                }
                ctiCrncHstrService.mergeRecProdDtl(hmParam);
            } else if ("ob".equals(psPathType)) {
                if ("".equals(sCmpgId)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "캠페인아이디가 없습니다.");

                    return modelAndView;
                }
                ctiCrncHstrService.mergeRecTmDtl(hmParam);
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
