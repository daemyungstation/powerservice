/*
 * (@)#ConsController.java
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.BswrDmndDtlService;
import powerservice.business.cns.service.ConsService;
import powerservice.business.cns.service.ConsTrctHstrService;
import powerservice.business.cns.service.CtiCrncHstrService;
import powerservice.business.cns.service.CustBasiService;
import powerservice.business.cns.service.RecrncResrDtlService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.web.service.WebConsService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.11 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cons
 */
@Controller
@RequestMapping(value = "/cons")
public class ConsController {

    private final Logger log = LoggerFactory.getLogger(ConsController.class);

    @Resource
    private ConsService consService;

    @Resource
    private RecrncResrDtlService recrncResrDtlService;

    @Resource
    private ConsTrctHstrService consTrctHstrService;

    @Resource
    private BswrDmndDtlService bswrDmndDtlService;

    @Resource
    private WebConsService webConsService;

    @Resource
    private CustBasiService custBasiService;

    @Resource
    private CtiCrncHstrService ctiCrncHstrService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    /**
     * 상담 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pagetype}")
    public ModelAndView getConsList(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                String sDeptYn = StringUtils.defaultString((String) hmParam.get("dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                // 상담관리(상담사용) - 본인 담당인 상담만 조회
                if ("usr".equals(psPathType)) {
                    hmParam.put("user_typ", "chpr_id");
                    hmParam.put("user_id", oLoginUser.getUserid());
                }
                // 상담업무 > 상담현황 - 당일 접수된 본인 담당인 상담만 조회
                else if ("today".equals(psPathType)) {
                    hmParam.put("user_typ", "chpr_id");
                    hmParam.put("user_id", oLoginUser.getUserid());

                    hmParam.put("dt_typ", "today");
                }

                // 경로 구분 저장
                hmParam.put("path_typ", psPathType);

                if ("Y".equals(sDeptYn)) {
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "A");
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                    if (!" ".equals(dataAthrQury)) {
                        dataAthrQury = dataAthrQury.replace("AND", "AND (");
                        dataAthrQury += "OR A.DEPT_CD IS NULL)";
                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                String excel_fg = (String)mapInVar.get("excel_fg");
                if (!"Y".equals(excel_fg)) {
                    int nTotal = consService.getConsCount(hmParam);

                    mapOutVar.put("tc_cons", nTotal);

                    List<Map<String, Object>> mList = consService.getConsList(hmParam);

                    if ("hstr-sngl".equals(psPathType)) {
                        listMap.setRowMaps(mList.get(0));
                    }else{
                        listMap.setRowMaps(mList);
                    }
                    mapOutDs.put("ds_output", listMap);
                }

                if (null == hmParam.get("dataAthrQury")) {
                    hmParam.put("dataAthrQury", "");
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
           }

            // 2017.12.15 관리업무 - 상담이력관리(상담사용, 관리자용) 화면에서 조회한 경우만 로그기록
            if ("usr".equals(psPathType) || "admin".equals(psPathType)) {

                //2017.12.11 접속 로그////////////////////////////////////////////////////////////////////////////////
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
     * 상담 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list2")
    public ModelAndView getConsList2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                String sDeptYn = StringUtils.defaultString((String) hmParam.get("dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                // 상담관리(상담사용) - 본인 담당인 상담만 조회
//                if ("usr".equals(psPathType)) {
//                    hmParam.put("user_typ", "chpr_id");
//                    hmParam.put("user_id", oLoginUser.getUserid());
//                }
//                // 상담업무 > 상담현황 - 당일 접수된 본인 담당인 상담만 조회
//                else if ("today".equals(psPathType)) {
//                    hmParam.put("user_typ", "chpr_id");
//                    hmParam.put("user_id", oLoginUser.getUserid());
//
//                    hmParam.put("dt_typ", "today");
//                }

                // 경로 구분 저장
                //hmParam.put("path_typ", psPathType);

                if ("Y".equals(sDeptYn)) {
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "A");
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                    if (!" ".equals(dataAthrQury)) {
                        dataAthrQury = dataAthrQury.replace("AND", "AND (");
                        dataAthrQury += "OR A.DEPT_CD IS NULL)";
                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                String excel_fg = (String)mapInVar.get("excel_fg");
                if (!"Y".equals(excel_fg)) {
                    int nTotal = consService.getConsCount2(hmParam);

                    mapOutVar.put("tc_cons", nTotal);

                    List<Map<String, Object>> mList = consService.getConsList2(hmParam);

//                    if ("hstr-sngl".equals(psPathType)) {
//                        listMap.setRowMaps(mList.get(0));
//                    }else{
//                        listMap.setRowMaps(mList);
//                    }
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }

                if (null == hmParam.get("dataAthrQury")) {
                    hmParam.put("dataAthrQury", "");
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
           }

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
     * 상담 그룹 정보를 검색한다.
     *
     * @param psConsnoGropNo String
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/group/{consnogropno}")
    public ModelAndView getConsGroup(XPlatformMapDTO xpDto, Model model, @PathVariable("consnogropno") String psConsnoGropNo) throws Exception {
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

            List<Map<String, Object>> mList = consService.getConsGroup(psConsnoGropNo);
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
     * 상담 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw-cons-list")
    public ModelAndView getDlwConsList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "엔컴 상담 조회대상 고객번호가 없습니다.");

                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = consService.getDlwConsCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = consService.getDlwConsList(hmParam);

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
     * 인입전화번호로 상담 고객 정보를 조회한다.
     *
     * @param psIncoTlno String
     * @param pmParam Map<String, Object>
     * @return String
     * @throws Exception
     */
    private String getConsMemberByIncoTlno(String psIncoTlno, Map<String, Object> pmParam) throws Exception {
        String sMemNo = null; // 고객ID
        String sMemNm = null; // 고객명
        if (psIncoTlno == null || psIncoTlno.length() < 4) {
            return sMemNo;
        }

        // 인입전화번호 고객 건수 조회
        Map<String, Object> mMemberParam = new HashMap<String, Object>();
        mMemberParam.put("inco_tlno", psIncoTlno);

        // 엔컴 부서코드 조건여부
        String sChkDeptYn = StringUtils.defaultString((String) pmParam.get("chk_dept_yn"));

        if ("Y".equals(sChkDeptYn)) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            pmParam.put("paramEmpleNo", oLoginUser.getUserid());
            pmParam.put("paramAs", "EMP");
            String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(pmParam));
            if (!"".equals(dataAthrQury)) {
                dataAthrQury = dataAthrQury.replace("AND", "AND (");
                dataAthrQury += "OR EMP.DEPT_CD IS NULL)";
                mMemberParam.put("dataAthrQury", dataAthrQury);
            }
        }

        int nMemberCnt = custBasiService.getCustBasiCount(mMemberParam);

        // 고객이 있는 경우 고객ID 조회 및 선택
        if (nMemberCnt > 0) {
            // 고객ID 목록 조회
            //mMemberParam.put("orderBy", "cons_dsps_dttm");
            //mMemberParam.put("orderDirection", "desc");
            mMemberParam.put("orderBy", "amnt_dttm");
            mMemberParam.put("orderDirection", "desc");
            mMemberParam.put("startLine", 1);
            mMemberParam.put("endLine", 3);
            List<Map<String, Object>> mMemberList = custBasiService.getCustBasiList(mMemberParam);
            // 가장 최근 상담 고객 선택
            if (mMemberList != null && mMemberList.size() > 0) {
                sMemNo = StringUtils.defaultString((String) mMemberList.get(0).get("mem_no"));
                sMemNm = StringUtils.defaultString((String) mMemberList.get(0).get("mem_nm"));
                mMemberList = null;
            }
        }
        // 없는 경우 신규 등록
        // 대명 회원 저장
        // 2016. 06. 07 최현식
        // 신규고객일 경우 회원정보 저장하지 않음
        /*else {
            mMemberParam.clear();
            ParamUtils.addSaveParam(mMemberParam);

            String sMemNm = StringUtils.defaultString((String) pmParam.get("mem_nm"));
            if ("".equals(sMemNm)) {
                sMemNm = "미상";
            }
            mMemberParam.put("mem_nm", sMemNm); // 고객명

            String[] sHpnoList = {"010", "011", "016", "017", "018", "019"};
            boolean incoTlnoClphFlag = false;
            if (psIncoTlno.length() == 10 || psIncoTlno.length() == 11) {
                incoTlnoClphFlag = Arrays.asList(sHpnoList).contains(psIncoTlno.substring(0, 3));
            }
            if (incoTlnoClphFlag) {
                mMemberParam.put("cell", psIncoTlno); 	// 휴대전화
            } else {
                mMemberParam.put("home_tel", psIncoTlno); 	// 자택전화
            }

            sMemNo = custBasiService.insertCustBasi(mMemberParam);
        }*/

        pmParam.put("member_cnt", nMemberCnt);
        pmParam.put("mem_nm", sMemNm);
        mMemberParam = null;

        return sMemNo;
    }

    /**
     * 상담 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCons(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 		// 상담정보
            DataSetMap trctBoxInDs = (DataSetMap)mapInDs.get("ds_trct_box");// 이관정보
            DataSetMap recrncInDs = (DataSetMap)mapInDs.get("ds_recrnc");	// 재통화정보

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                Map trctBoxParam = new HashMap<String, Object>();
                Map recrncParam = new HashMap<String, Object>();

                String sConsno = StringUtils.defaultString((String) hmParam.get("consno")); // 상담번호
                String sConsDspsmddlDtptCd = StringUtils.defaultString((String) hmParam.get("cons_dspsmddl_dtpt_cd")); // 처리방법
                String sOldConsDspsmddlDtptCd = StringUtils.defaultString((String) hmParam.get("old_cons_dspsmddl_dtpt_cd"));
                String sAccntNo = StringUtils.defaultString((String)hmParam.get("accnt_no")); // 회원번호
                String sFistCallIncoYn = StringUtils.defaultString((String)hmParam.get("fist_call_inco_yn")); // 최초 전화 인입시 상담저장 여부
                String sAdmrAmntYn = StringUtils.defaultString((String) hmParam.get("admr_amnt_yn")); // 관리자 수정 여부
                String sIncoTlno = StringUtils.defaultString((String) hmParam.get("inco_tlno")); // 인입전화번호
                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));

                // 재통화 정보 확인
                if ("40".equals(sConsDspsmddlDtptCd)) {
                    if (recrncInDs.size() > 0) {
                        recrncParam = recrncInDs.get(0);

                        String sRecrncCntcTlno = StringUtils.defaultString((String) recrncParam.get("recrnc_cntc_tlno")); // 재통화 전화번호
                        String sRecrncDt = StringUtils.defaultString((String) recrncParam.get("recrnc_dt")); // 재통화 일자
                        String sRecnrcHrmn = StringUtils.defaultString((String) recrncParam.get("recrnc_hrmn")); // 재통화 시간

                        if ("".equals(sRecrncCntcTlno) || "".equals(sRecrncDt) || "".equals(sRecnrcHrmn)) {
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, "재통화 건은 예약시간과 연락받을 번호를 꼭 입력해야 합니다.");

                            return modelAndView;
                        }
                    }
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                // 담당자 정보 저장
                hmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());
                hmParam.put("chpr_id", oLoginUser.getUserid());

                ParamUtils.addSaveParam(hmParam);

                // 인입콜이 아닐 경우
                //if (!"Y".equals(sFistCallIncoYn)) {
                    // 회원번호가 없을 경우 기타문의 설정
                    if ("".equals(sAccntNo)) {
                        hmParam.put("accnt_no", "00000"); // 기타문의 lifeMngController.java 1199
                    }
                //}

                if ("Y".equals(sFistCallIncoYn)) { // 콜 인입시 인입번호로 고객정보 조회
                    // 인입 정보 설정
                    hmParam.put("consno_sqno", 1); // 상담번호순번
                    hmParam.put("acpg_chnl_cd", "10"); // 접수채널
                    hmParam.put("cons_stat_cd", "10"); // 상담상태
                    hmParam.put("cons_dsps_mthd_cd", "10"); // 상담처리방법
                    hmParam.put("admr_amnt_yn", "N"); // 관리자수정여부

                    if ("".equals(sMemNo)) {
                        sMemNo = getConsMemberByIncoTlno(sIncoTlno, hmParam);
                    }
                    // 2016. 06. 07 최현식
                    // 신규고객일 경우 고객정보 없음
                    if (sMemNo == null || "".equals(sMemNo)) {
                        hmParam.put("mem_no", "");
                    } else {
                        hmParam.put("mem_no", sMemNo); // 고유번호 설정
                    }
                }

                // 상담번호가 없을 때 신규등록
                if ("".equals(sConsno)) {
                    // 2016. 06. 07 최현식
                    // 신규고객일 경우 상담저장하지 않음
                    if (sMemNo != null && !"".equals(sMemNo)) {
                        hmParam.put("actp_id", oLoginUser.getUserid()); // 접수자 정보

                        // 상담 등록
                        CommonUtils.printLog("BSFASDGASDFASDFAGF"+hmParam);
                        sConsno = consService.insertCons(hmParam);

                        if ("Y".equals(sFistCallIncoYn)) { // 콜 인입시 인입번호로 고객정보 조회
                            hmParam.put("consno_grop_no", sConsno);
                            hmParam.put("consno", sConsno);
                            hmParam.put("consno_sqno", "1");
                        }
                    }
                } else {
                    // 관리자 수정인 경우 관리자 정보 저장
                    if ("Y".equals(sAdmrAmntYn)) {
                        hmParam.put("admr_id", oLoginUser.getUserid());
                    }
                    // 상담 수정
                    consService.updateCons(hmParam);
                }

                // 녹취이력 저장
                if (!"".equals(sCtiCrncDtlId) && !"".equals(sConsno)) {
                    ctiCrncHstrService.mergeRecConsDtl(hmParam);
                }

                // 상담 이관/업무요청
                if (trctBoxInDs != null && trctBoxInDs.size() > 0) {
                    trctBoxParam = trctBoxInDs.get(0);

                    String sTrctBoxId = StringUtils.defaultString((String) trctBoxParam.get("trct_box_id")); // 이관박스아이디

                    if (!"".equals(sTrctBoxId) && "20".equals(sConsDspsmddlDtptCd)) {
                        trctBoxParam.put("consno", sConsno); // 상담번호 설정
                        ParamUtils.addSaveParam(trctBoxParam);

                        // 이관 정보 추가
                        trctBoxParam.put("trct_acpg_dept_cd", oLoginUser.getTeamcd()); // 이관접수부서코드
                        trctBoxParam.put("trct_actp_id", oLoginUser.getUserid()); // 이관접수자아이디

                        bswrDmndDtlService.insertBswrDmndDtl(trctBoxParam);
                    }
                }

                // 재통화 설정
                recrncParam.put("consno", sConsno);
                ParamUtils.addSaveParam(recrncParam);
                if ("40".equals(sConsDspsmddlDtptCd)) {
                    int nResult = recrncResrDtlService.getRecrncResrDtlCount(recrncParam);

                    if (nResult < 1) {							// 재통화 신규등록
                        String sAthrCd = oLoginUser.getAuthoritycd();
                        String sTeamCd = oLoginUser.getTeamcd();
                        if (sAthrCd.contains("TM")) {
                            recrncParam.put("bzpt_div", sTeamCd);
                        } else {
                            recrncParam.put("bzpt_div", "999999");
                        }

                        recrncResrDtlService.insertRecrncResrDtl(recrncParam);
                    } else {
                        recrncParam.put("oper_typ", "csel");	// 재통화 정보 수정
                        recrncResrDtlService.updateRecrncResrDtl(recrncParam);
                    }
                } else {
                    if ("40".equals(sOldConsDspsmddlDtptCd)) {
                        recrncParam.put("oper_typ", "close");  	// 처리완료
                        recrncResrDtlService.updateRecrncResrDtl(recrncParam);
                    }
                }

                if ("Y".equals(sFistCallIncoYn)) { // 콜 인입
                    dtptMap.setRowMaps(hmParam);
                    mapOutDs.put("ds_output", dtptMap);
                } else {
                    Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
                    mSearchParam.put("consno", sConsno);

                    dtptMap.setRowMaps(consService.getCons(mSearchParam));
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
     * 상담 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getCons(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 		// 상담정보

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                dtptMap.setRowMaps(consService.getCons(hmParam));
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
     * 관리자 메인화면 > 센터현황 건수를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chart-count/{pagetype}")
    public ModelAndView getConsChartCount(@PathVariable("pagetype") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        if ("usr".equals(psPathType)) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

            pmParam.put("user_id", oLoginUser.getUserid());
        }

        oResult.setData(consService.getConsChartCount(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 관리자 메인화면 > 센터현황 차트 데이터를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chart-list/{pagetype}")
    public ModelAndView getConsChartList(@PathVariable("pagetype") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        if ("usr".equals(psPathType)) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

            pmParam.put("srch_typ", "week");
            pmParam.put("user_id", oLoginUser.getUserid());
        }

        oResult.setData(consService.getConsChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 관리자 메인화면 > 우수상담사 TOP5 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/top5")
    public ModelAndView getConsTop5List(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = consService.getConsTop5List(pmParam);
        oData.setList(mList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 상담 정보를 등록/수정한다. (대명)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public void saveConsdlw(Map <String, Object> pmParam) throws Exception {
        String sConsno = "";
        String sAccntNo = StringUtils.defaultString((String)pmParam.get("accnt_no")); // 회원번호

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        //인우상담 관련
        // 담당자 정보 저장
        pmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());
        pmParam.put("chpr_id", oLoginUser.getUserid());
        ParamUtils.addSaveParam(pmParam);
        pmParam.put("cons_memo_tit", pmParam.get("cons_memo"));
        pmParam.put("cons_memo_cntn", pmParam.get("cnsl_con"));
        pmParam.put("cons_dspsmddl_dtpt_cd", "10"); //상담처리중 - 일반
        pmParam.put("cons_stat_cd", "30"); //상담상태 - 처리완료
        pmParam.put("clnt_nm", pmParam.get("mem_nm"));
        pmParam.put("acpg_chnl_cd", "10");
        pmParam.put("consno_sqno", "1");
        pmParam.put("conc_yn", "N");

        //대명상담 관련
        pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
        pmParam.put("cnsl_cd", "01"); // COM_CD_GRP 테이블 기타

        pmParam.put("rgsr_id", oLoginUser.getUserid());
        pmParam.put("amnd_id", oLoginUser.getUserid());
        pmParam.put("cnsl_man", oLoginUser.getUserid());

        if ("Y".equals(pmParam.get("pyin_chng_yn"))) {
            pmParam.put("cons_typ1_cd", "CT01010000" );
            pmParam.put("cons_typ2_cd", "CT01010100");
            pmParam.put("cons_typ3_cd", "CT01010102");
        } else {
            pmParam.put("cons_typ1_cd", "CT01040000" );
            pmParam.put("cons_typ2_cd", "CT01040100");
            pmParam.put("cons_typ3_cd", "CT01040101");
        }
        
        String sEvntCnslRegi = StringUtils.defaultString((String)pmParam.get("evnt_cnsl_regi"));
        
        if(sEvntCnslRegi.equals("Cancel"))
        {
        	pmParam.put("cons_typ1_cd", "CT01120000" );
            pmParam.put("cons_typ2_cd", "CT01121500");
            pmParam.put("cons_typ3_cd", "CT01121502");
        }
        else if(sEvntCnslRegi.equals("New"))
        {
        	pmParam.put("cons_typ1_cd", "CT01120000" );
            pmParam.put("cons_typ2_cd", "CT01121500");
            pmParam.put("cons_typ3_cd", "CT01121501");
        }
        else
        {
        	
        }

        // 회원번호가 없을 경우 기타문의 설정
        if ("".equals(sAccntNo)) {
            pmParam.put("accnt_no", "00000"); // 기타문의 lifeMngController.java 1199
        }

        // 신규등록
        if ("".equals(sConsno)) {
            pmParam.put("actp_id", oLoginUser.getUserid()); // 접수자 정보
            // 상담 등록
            sConsno = consService.insertCons(pmParam);
        }
    }

    /**
     * 상담 정보를 검색한다. - 엑셀다운로드용
     * getConsList() 메소드를 복사하여 만듬
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/getConsListForExcel")
    public void getConsListForExcel(XPlatformMapDTO xpDto
                                    , HttpServletRequest request, HttpServletResponse response) throws Exception {

        DataSetMap listMap2 = new DataSetMap();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        String sCurrTime = smt.format(cal.getTime());

        String sDeptYn = CommonUtils.nvls(request.getParameter("dept_yn"));
        String psPathType = CommonUtils.nvls(request.getParameter("path_typ"));

        Map<String, Object> hmParam = new HashMap<String, Object>();
        Enumeration enm = request.getParameterNames();
        String sColId = "";
        while (enm.hasMoreElements()) {
            sColId = (String)enm.nextElement();
            hmParam.put(sColId, request.getParameter(sColId));
        }

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

            // 경로 구분 저장
            hmParam.put("path_typ", psPathType);

            if ("Y".equals(sDeptYn)) {
                hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                hmParam.put("paramAs", "A");
                String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                if (!" ".equals(dataAthrQury)) {
                    dataAthrQury = dataAthrQury.replace("AND", "AND (");
                    dataAthrQury += "OR A.DEPT_CD IS NULL)";
                    hmParam.put("dataAthrQury", dataAthrQury);
                }
            }

            ParamUtils.addCenterParam(hmParam);
            if (null == hmParam.get("dataAthrQury")) {
                hmParam.put("dataAthrQury", "");
            }

            List<Map<String, Object>> mList = consService.getConsList(hmParam);

            String os_name = System.getProperty("os.name").toUpperCase();
            String sTmpDir = System.getProperty("java.io.tmpdir");
            String xlFileNm = sTmpDir + File.separator + "filename" + sCurrTime + ".xlsx";
            if (os_name.contains("WINDOW")) {
                xlFileNm = sTmpDir + "filename" + sCurrTime + ".xlsx";
            }

            log.debug(">xlFileNm : " + xlFileNm);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("상담이력");

            String[] sArrTitle = {"고유번호", "회원번호", "회원명", "생년월일", "성별",
                                    "접수채널", "인입번호", "자택전화", "휴대전화", "상담유형1",
                                    "상담유형2", "상담유형3", "처리방법", "처리상태", "담당자",
                                    "처리일시", "상담메모"};

            List <String> lstCol = new ArrayList<String>();
            lstCol.add("mem_no");
            lstCol.add("accnt_no");
            lstCol.add("mem_nm");
            lstCol.add("brth_mon_day");
            lstCol.add("sex");
            lstCol.add("acpg_chnl_nm");
            lstCol.add("inco_tlno");
            lstCol.add("home_tel");
            lstCol.add("cell");
            lstCol.add("cons_typ1_nm");
            lstCol.add("cons_typ2_nm");
            lstCol.add("cons_typ3_nm");
            lstCol.add("cons_dspsmddl_dtpt_nm");
            lstCol.add("cons_stat_nm");
            lstCol.add("chpr_nm");
            lstCol.add("cons_dsps_dttm");
            lstCol.add("cons_memo_cntn");

            int i = 0;
            int j = 0;
            Row row = sheet.createRow(i++);
            for (String field : sArrTitle) {
                Cell cell = row.createCell(j++);
                cell.setCellValue((String) field);
            }

            // 해당 셀에 데이터를 입력한다.

            Map<String, Object> mRow = null;
            String val = "";
            String colId = "";
            for (i = 0; i < mList.size(); i++) {
                mRow = mList.get(i);
                row = sheet.createRow(i+1);
                for (j=0; j<lstCol.size(); ++j) {
                    colId = lstCol.get(j);
                    Cell cell = row.createCell(j);
                    val = CommonUtils.nvls((String)mRow.get(colId));
                    if ("brth_mon_day".equals(colId) && !"".equals(val)) {
                        cell.setCellValue(val.substring(0,4) + "-" + val.substring(4,6) + "-" + val.substring(6,8));
                    } else if ("cons_dsps_dttm".equals(colId) && !"".equals(val)) {
                        cell.setCellValue(val.substring(0,4) + "-" + val.substring(4,6) + "-" + val.substring(6,8) + " " + val.substring(8,10) + ":" + val.substring(10,12));
                    } else if (("home_tel".equals(colId) || "cell".equals(colId)) && !"".equals(val)) {
                        cell.setCellValue(val.trim());
                    } else if ("sex".equals(colId) && !"".equals(val)) {
                        if ("0001".equals(val)) {
                            cell.setCellValue("남자");
                        } else if ("0002".equals(val)) {
                            cell.setCellValue("여자");
                        }
                    } else {
                        cell.setCellValue(val);
                    }
                }
            }

            FileOutputStream outputStream = new FileOutputStream(xlFileNm);
            workbook.write(outputStream);

            File xDownFile = new File(xlFileNm);
            String contentType = request.getContentType();
            response.setContentType("x-msdownload");

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            String sDisplayFileName = "상담이력관리_" + sCurrTime + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

            try{
                if (xDownFile.exists()) {
                    xDownFile.delete();
                }
            }catch(Exception e){}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 홈페이지 웹  회원 정보 수정 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw-lifewey-list")
    public ModelAndView getDlwlifeweyList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "고객번호가 없습니다.");
                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = consService.getDlwlifeweyCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = consService.getDlwlifeweyList(hmParam);

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