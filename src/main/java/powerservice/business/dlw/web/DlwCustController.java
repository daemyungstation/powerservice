/*
 * (@)# DlwCustController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
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

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwEmplService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명 고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value = "/dlw/cust")
public class DlwCustController {


    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private DlwCmsService dlwCmsService;

    @Resource
    private DlwEmplService dlwEmplService;
    
    @Resource
	private DlwMallLinkedService dlwMallLinkedService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private UserService userService;

    @Resource
    private ConsService consService;
    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getDlwCustPrdtList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = dlwCustService.getDlwCustPrdtList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 고객-상품 상세정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dtpt")
    public ModelAndView getDlwCustPrdtDtpt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                String sAthrQuryFg = StringUtils.defaultString((String) hmParam.get("athr_qury_fg"));

                ParamUtils.addCenterParam(hmParam);
                hmParam.put("unty_inq_yn", "Y");

                if ("Y".equals(sAthrQuryFg)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "EMP");
                    String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                    hmParam.put("dataAthrQury", dataAthrQury);
                }

                Map<String, Object> tmpMap = dlwCustService.getDlwCustPrdtDtpt(hmParam);
                if (tmpMap != null) {
                    dtptMap.setRowMaps(tmpMap);
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
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-acnt/list")
    public ModelAndView getDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                /* 임의등록에서 회원정보검색시 해피콜 조인구분 변수 추가 - 2018.02.13 */
                hmParam.put("hp_join_gb", mapInVar.get("hp_join_gb"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                Map<String, Object> mEmpl = dlwEmplService.getDlwEmplDtpt((String)hmParam.get("rgsr_id"));
                if (mEmpl != null) {
                    String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                    if ("1602".equals(sDeptCd)) {
                        hmParam.put("ns_yn", "Y");
                    } else {
                        hmParam.put("ns_yn", "N");
                    }
                }

                UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
                hmParam.put("paramAs", "EMP");

                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = dlwCustService.getDlwCustAcntCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = dlwCustService.getDlwCustAcntList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
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
     * 온라인 가입회원 구좌번호로 CMS/카드정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/online-accnt/list")
    public ModelAndView getDlwOnlineMemberInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String gubun = (String)mapInVar.get("gubun");

            String ncaIdnNo = "";

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>> ();

            if("card".equals(gubun)) {
                dlwCustService.getDlwOnlineMemberCardInfo(hmParam);
            } else {
                dlwCustService.getDlwOnlineMemberCmsInfo(hmParam);
                ncaIdnNo = dlwCmsService.getNcaIdnNo(hmParam);
            }

            mapOutVar.put("ncaIdnNo", ncaIdnNo);

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
     * 온라인 가입회원(결합상품) 구좌번호로 CMS/카드정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/online-accnt-ss/list")
    public ModelAndView getDlwOnlineSSMemberInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("site_id", mapInVar.get("site_id"));

            String gubun = (String)mapInVar.get("gubun");

            String ncaIdnNo = "";

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>> ();

            if("card".equals(gubun)) {
                dlwCustService.getDlwOnlineSSMemberCardInfo(hmParam);
            } else {
                dlwCustService.getDlwOnlineSSMemberCmsInfo(hmParam);
                ncaIdnNo = dlwCmsService.getNcaIdnNo(hmParam);
            }

            mapOutVar.put("ncaIdnNo", ncaIdnNo);

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
     * 납입정보(부가서비스)등록 회원을 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/buga-srvc/chk")
    public ModelAndView getBugaSrvcMemChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);

            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
            hmParam.put("paramAs", "EMP");
            String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
            hmParam.put("dataAthrQury", dataAthrQury);

            /*if ("1602".equals(oUserSession.getTeamcd())) {         // dept_cd
                hmParam.put("nsfg", "Y");
            } else {
                hmParam.put("nsfg", "N");
            }*/
            Map<String, Object> mEmpl = dlwEmplService.getDlwEmplDtpt(oUserSession.getUserid());
            if (mEmpl != null) {
                String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                if ("1602".equals(sDeptCd)) {
                    hmParam.put("nsfg", "Y");
                } else {
                    hmParam.put("nsfg", "N");
                }

                hmParam.put("cmsfg", "Y");
                Map<String, Object> cmsMap = dlwCustService.getBugaSrvcMemChk(hmParam);
                if (cmsMap != null) {
                    mapOutVar.put("gv_bugaCmsFg", "Y");
                } else {
                    mapOutVar.put("gv_bugaCmsFg", "N");
                }

                hmParam.put("cmsfg", "");
                hmParam.put("cardfg", "Y");
                Map<String, Object> cardMap = dlwCustService.getBugaSrvcMemChk(hmParam);
                if (cmsMap != null) {
                    mapOutVar.put("gv_bugaCardFg", "Y");
                } else {
                    mapOutVar.put("gv_bugaCardFg", "N");
                }
            } else {
                mapOutVar.put("gv_bugaCmsFg", "N");
                mapOutVar.put("gv_bugaCardFg", "N");
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
     * 부가서비스(CMS/CARD) 신쳥구분을 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/buga-srvc/get_app_cl")
    public ModelAndView getBugaSrvcAppClChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = (String) mapInVar.get("accnt_no");

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);

            String bugaAppCl = dlwCustService.getBugaSrvcAppCl(hmParam);
            mapOutVar.put("gv_bugaAppCl", bugaAppCl);

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
     * 고객 OCB 정보를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update-ocb")
    public ModelAndView updateMemberOCB(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sOcbYn = StringUtils.defaultString((String) mapInVar.get("ocb_yn"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                return modelAndView;
            }

            hmParam.put("mem_no", sMemNo);
            hmParam.put("ocb_yn", sOcbYn);

            ParamUtils.addSaveParam(hmParam);
            dlwCustService.updateMemberOCB(hmParam);

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
     * 양도양수 정보를 등록한다
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insydys-mem")
    public ModelAndView insertYdysMemTrans(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            Object ys_mem_no = (Object)mapInVar.get("ys_mem_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("ys_mem_no", ys_mem_no);
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out.println("===================================>>>>> insertYdysMemTrans");
            CommonUtils.printLog(hmParam);

            System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");

            dlwCustService.insertYdysMemTrans(hmParam);   // 양도양수 정보 등록
            dlwCustService.updateYdysMemProd(hmParam);  // 회원상품정보 고유번호 변경

            dlwCustService.updateYdysClsl(hmParam);     // 상담내역 고유번호 변경
            dlwCustService.updateYdysChng(hmParam);     // 정보변경 내역 변경

            consService.updateYdysCons(hmParam);     // 인우 상담내역 변경(TB_CONS)
            consService.updateYdysConsHstr(hmParam);  // 인우 상담이력 변경(TB_CONS_HSTR)


            /* ================================================================
             * 날짜 : 20180403
             * 이름 : 김찬영
             * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 양도로 변경
             * ================================================================
             * */
            hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
            hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
            hmParam.put("P_STATE", "7");

            System.out.println("insert인풋데이터 :: " +hmParam );
            //dlwCustService.updateResnMemberState(hmParam);
            dlwMallLinkedService.updateResnMemberState(hmParam);

            /* ================================================================
             * 날짜 : 20180403
             * 이름 : 김찬영
             * 추가내용 : 쇼핑몰 연동 Table 에 양수 받는 고객 등록
             * ================================================================
             * */
            hmParam.put("P_MEM_NO", hmParam.get("ys_mem_no"));
            hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
            hmParam.put("P_STATE", "0");

            System.out.println("insert인풋데이터 :: " +hmParam );
//            dlwCustService.updateResnMemberState(hmParam); updateLFResnMemberState
            dlwMallLinkedService.updateLFResnMemberState(hmParam);
            


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
     * 고객별 실적통계 결과를 보여준다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/AnalResultlist")
    public ModelAndView getAnalResultList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = dlwCustService.getAnalResultList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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