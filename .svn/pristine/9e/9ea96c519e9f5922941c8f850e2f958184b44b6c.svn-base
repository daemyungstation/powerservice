/*
 * (@)# TmplXlsController.java
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

package powerservice.business.sys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.CompressUtil;
import powerservice.common.util.DateUtil;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.SessionUtils;

/**
 * 템플릿 엑셀 다운로드를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID TmplXlsController
 */
@Controller
@RequestMapping(value = "/syst/tmpl-xls-down")
public class TmplXlsController {

    @Resource
    private ServletContext ctx;


    /**
     * 엑셀 다운로드 진행을 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download-check")
    public ModelAndView checkXls(@RequestParam Map<String, String> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        oResult.setData(poSession.getAttribute("EXCEL_SESSION") == null ? "N" : "Y");

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 샘플01 엑셀 다운로드를 한다.
     * @pmParam pmParam Map<String, Object>
     * @pmParam poSession HttpSession
     * @pmParam poResponse HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smpl01")
    public ModelAndView excelDownMeetingRoomBooking(@RequestParam Map<String, Object> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        ModelAndView oModelAndView = null;

        // 엑셀다운로드 중복 방지용 세션
        synchronized (poSession) {
            if (poSession.getAttribute("EXCEL_SESSION") != null) {
                oModelAndView = new ModelAndView("common/xls-down-result");
                oModelAndView.addObject("result", "error_progress");
                return oModelAndView;
            } else {
                poSession.setAttribute("EXCEL_SESSION", "Y");
            }
        }

        // 엑셀 다운로드 정보 저장
        String sTargetPath = ""; // 사용자 작업 임시 폴더
        String sTemplateFileName = "SMPL01.xls"; // 템플릿 파일
        String sTargetFileName = "샘플01_" + DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM) + ".xls"; // 생성 엑셀 파일

        // 엑셀 다운로드 준비
        String sErrCd = prepareExcelDown(pmParam, sTemplateFileName);
        if (sErrCd != null) {
            poSession.removeAttribute("EXCEL_SESSION");

            oModelAndView = new ModelAndView("common/xls-down-result");
            oModelAndView.addObject("result", sErrCd);
            return oModelAndView;
        }
        sTemplateFileName = (String) pmParam.get("template_file_name");
        sTargetPath = (String) pmParam.get("target_path");


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        try {
            // TODO : 코드 리스트 조회
            // 코드 "'PVP010','PVP020',..." 를 파라미터로 한번에 DB 조회
            // 코드 순서에 맞게 가져와야 함
            Map<String, Object> mCd = null;
            List<Map<String, Object>> mCdList = new ArrayList<Map<String, Object>>();
            // 예약방식
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP010");
            mCd.put("cd", "10");
            mCd.put("cd_nm", "전화");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP010");
            mCd.put("cd", "20");
            mCd.put("cd_nm", "홈페이지");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP010");
            mCd.put("cd", "30");
            mCd.put("cd_nm", "E-Mail");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP010");
            mCd.put("cd", "40");
            mCd.put("cd_nm", "기타");
            mCdList.add(mCd);
            // 알게된경로
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP020");
            mCd.put("cd", "10");
            mCd.put("cd_nm", "네이버 검색");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP020");
            mCd.put("cd", "20");
            mCd.put("cd_nm", "구글 검색");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP020");
            mCd.put("cd", "30");
            mCd.put("cd_nm", "페이스북 광고");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP020");
            mCd.put("cd", "40");
            mCd.put("cd_nm", "지인 소개");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP020");
            mCd.put("cd", "50");
            mCd.put("cd_nm", "브로셔");
            mCdList.add(mCd);
            // 결제방식
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP030");
            mCd.put("cd", "10");
            mCd.put("cd_nm", "신용카드");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP030");
            mCd.put("cd", "20");
            mCd.put("cd_nm", "계좌이체");
            mCdList.add(mCd);
            // 전자세금계산서발행
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP040");
            mCd.put("cd", "10");
            mCd.put("cd_nm", "요청");
            mCdList.add(mCd);
            mCd = new HashMap<String, Object>();
            mCd.put("cd_set_cd", "PVP040");
            mCd.put("cd", "20");
            mCd.put("cd_nm", "요청안함");
            mCdList.add(mCd);

            // TODO : 회의실 리스트 조회
            // 순서에 맞게 가져와야 함
            Map<String, Object> mMeetRoom = null;
            List<Map<String, Object>> mMeetRoomList = new ArrayList<Map<String, Object>>();
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3010");
            mMeetRoom.put("meet_room_nm", "Board");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3020");
            mMeetRoom.put("meet_room_nm", "East");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3030");
            mMeetRoom.put("meet_room_nm", "West");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3040");
            mMeetRoom.put("meet_room_nm", "South 1");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3050");
            mMeetRoom.put("meet_room_nm", "South 2");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3060");
            mMeetRoom.put("meet_room_nm", "North 1");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3070");
            mMeetRoom.put("meet_room_nm", "North 2");
            mMeetRoom.put("flr", "30");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3710");
            mMeetRoom.put("meet_room_nm", "Zeus");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3720");
            mMeetRoom.put("meet_room_nm", "Hermes");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3730");
            mMeetRoom.put("meet_room_nm", "Athena");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3740");
            mMeetRoom.put("meet_room_nm", "Apollon");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3750");
            mMeetRoom.put("meet_room_nm", "Aphrodite");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);
            mMeetRoom = new HashMap<String, Object>();
            mMeetRoom.put("meet_room_id", "MTR3760");
            mMeetRoom.put("meet_room_nm", "Hera");
            mMeetRoom.put("flr", "37");
            mMeetRoomList.add(mMeetRoom);

            // TODO : 사용 회의실 리스트 조회
            // 회의 시간 순서에 맞게 가져와야 함
            Map<String, Object> mUseMeetRoom = null;
            List<Map<String, Object>> mUseMeetRoomList = new ArrayList<Map<String, Object>>();
            mUseMeetRoom = new HashMap<String, Object>();
            mUseMeetRoom.put("meet_dt", "20150506");
            mUseMeetRoom.put("meet_stt_tm", "090000");
            mUseMeetRoom.put("meet_end_tm", "180000");
            mUseMeetRoom.put("meet_purp", "일반회의");
            mUseMeetRoom.put("etrc_nmpr", "약 10명");
            mUseMeetRoom.put("meet_room_id", "MTR3740");
            mUseMeetRoom.put("meet_room_nm", "Apollon");
            mUseMeetRoom.put("use_amt", "540000");
            mUseMeetRoomList.add(mUseMeetRoom);
            mUseMeetRoom = new HashMap<String, Object>();
            mUseMeetRoom.put("meet_dt", "20150507");
            mUseMeetRoom.put("meet_stt_tm", "100000");
            mUseMeetRoom.put("meet_end_tm", "170000");
            mUseMeetRoom.put("meet_purp", "중요회의");
            mUseMeetRoom.put("etrc_nmpr", "약 3명");
            mUseMeetRoom.put("meet_room_id", "MTR3010");
            mUseMeetRoom.put("meet_room_nm", "Board");
            mUseMeetRoom.put("use_amt", "820000");
            mUseMeetRoomList.add(mUseMeetRoom);


            // 엑셀 데이터 처리
            Map<String, Object> mExcelData = new HashMap<String, Object>();

            // 담당자 정보
            Map<String, Object> mChprInfo = new HashMap<String, Object>();
            mChprInfo.put("chpr_nm", "박혜원"); // 담당자
            mChprInfo.put("resr_dt", DateUtil.convertString("20150507", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // 예약일
            mChprInfo.put("tlno", CommonUtils.convertPhoneString("0260013015")); // 연락처
            mChprInfo.put("emil_addr", "Rachel@pivotoffice.com"); // E-Mail
            mExcelData.put("chpr_info", mChprInfo);

            // 고객 정보
            Map<String, Object> mCustInfo = new HashMap<String, Object>();
            mCustInfo.put("cust_nm", "김나나 담당자님"); // 고객명
            mCustInfo.put("comp_nm", "Apple Korea"); // 회사명/업종
            mCustInfo.put("emil_addr", "nanakim@apple.co.kr"); // E-Mail
            mCustInfo.put("tlno", CommonUtils.convertPhoneString("0260011234")); // 전화번호/핸드폰번호
            mCustInfo.put("resr_mthd", createSelectListString(mCdList, "cd_set_cd", "PVP010", "cd", new String[]{"10"}, "cd_nm", 8)); // 예약방식
            mCustInfo.put("cntc_path", createSelectListString(mCdList, "cd_set_cd", "PVP020", "cd", new String[]{"20"}, "cd_nm", 5)); // 알게된경로
            mExcelData.put("cust_info", mCustInfo);

            // 회의 정보
            String sMeetRoomNmList = "";
            int nTotalUseAmt = 0;
            Map<String, Object> mMeetInfo = new HashMap<String, Object>();
            if (mUseMeetRoomList != null) {
                String sMeetDtList = "";
                for (int i = 0; i < mUseMeetRoomList.size(); i++) {
                    mUseMeetRoom = (Map<String, Object>) mUseMeetRoomList.get(i);
                    if (mUseMeetRoom == null) {
                        continue;
                    }
                    // 회의날짜
                    String sMeetDt = (String) mUseMeetRoom.get("meet_dt");
                    if (sMeetDt == null) {
                        sMeetDt = "";
                    } else if (sMeetDt.length() == 8) {
                        sMeetDt = DateUtil.convertString(sMeetDt, DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDDEEE_HAN);
                    }
                    // 회의시작시간
                    String sMeetSttDt = (String) mUseMeetRoom.get("meet_stt_tm");
                    String sMeetSttDt1 = "";
                    String sMeetSttDt2 = "";
                    if (sMeetSttDt != null && sMeetSttDt.length() == 6) {
                        sMeetSttDt1 = DateUtil.convertString(sMeetSttDt, DateUtil.SDF_HHMMSS, DateUtil.SDF_HHMMM_DASH);
                        sMeetSttDt1 = sMeetSttDt1.replace("오전", "AM").replace("오후", "PM");
                        sMeetSttDt2 = DateUtil.convertString(sMeetSttDt, DateUtil.SDF_HHMMSS, DateUtil.SDF_HHMM_DASH);
                    }
                    String sMeetEndTm = (String) mUseMeetRoom.get("meet_end_tm");
                    String sMeetEndTm1 = "";
                    String sMeetEndTm2 = "";
                    if (sMeetEndTm != null && sMeetEndTm.length() == 6) {
                        sMeetEndTm1 = DateUtil.convertString(sMeetEndTm, DateUtil.SDF_HHMMSS, DateUtil.SDF_HHMMM_DASH);
                        sMeetEndTm1 = sMeetEndTm1.replace("오전", "AM").replace("오후", "PM");
                        sMeetEndTm2 = DateUtil.convertString(sMeetEndTm, DateUtil.SDF_HHMMSS, DateUtil.SDF_HHMM_DASH);
                    }
                    String sMeetRoomNm = (String) mUseMeetRoom.get("meet_room_nm");
                    if (sMeetRoomNm == null) {
                        sMeetRoomNm = "";
                    }
                    String sUseAmt = (String) mUseMeetRoom.get("use_amt");
                    if (sUseAmt == null || "".equals(sUseAmt)) {
                        sUseAmt = "0";
                    }
                    // 첫 회의 정보만 넣음
                    if (i == 0) {
                        mMeetInfo.put("meet_stt_tm", sMeetSttDt1); // 회의시작시간
                        mMeetInfo.put("meet_end_tm", sMeetEndTm1); // 회의종료시간
                        mMeetInfo.put("meet_purp", (String) mUseMeetRoom.get("meet_purp")); // 회의목적
                        mMeetInfo.put("etrc_nmpr", (String) mUseMeetRoom.get("etrc_nmpr")); // 참가인원
                        mMeetInfo.put("meet_room_30_flr",
                            createSelectListString(mMeetRoomList, "flr", "30", "meet_room_id", new String[]{(String) mUseMeetRoom.get("meet_room_id")}, "meet_room_nm", 3)); // 회의실(30F)
                        mMeetInfo.put("meet_room_37_flr",
                            createSelectListString(mMeetRoomList, "flr", "37", "meet_room_id", new String[]{(String) mUseMeetRoom.get("meet_room_id")}, "meet_room_nm", 5)); // 회의실(30F)
                    }
                    // 회의날짜 문자열 조합
                    if (!"".equals(sMeetDtList)) {
                        sMeetDtList += " / ";
                    }
                    sMeetDtList += sMeetDt;
                    // 회의실 문자열 조합
                    if (!"".equals(sMeetRoomNmList)) {
                        sMeetRoomNmList += " / ";
                    }
                    sMeetRoomNmList += sMeetRoomNm + "(" + sMeetSttDt2 + "-" + sMeetEndTm2 + ")";
                    // 회의실 사용 금액 합계 계산
                    nTotalUseAmt += Integer.parseInt(sUseAmt);
                }
                mMeetInfo.put("meet_dt_list", sMeetDtList); // 회의날짜
            }
            mExcelData.put("meet_info", mMeetInfo);

            // 서비스 정보
            // 엑셀 포맷을 위해 숫자인 경우 반드시 정수형을 넣어줘야 함
            Map<String, Object> mSrvcInfo = new HashMap<String, Object>();
            mSrvcInfo.put("prjt_use_qt", "10,000/hr"); // 빔프로젝션 사용량
            mSrvcInfo.put("prjt_use_amt", Integer.parseInt("60000")); // 빔프로젝션 요금
            mSrvcInfo.put("wrls_inrn_use_qt", ""); // 무선인터넷 사용량
            mSrvcInfo.put("wrls_inrn_use_amt", null); // 무선인터넷 요금
            mSrvcInfo.put("line_inrn_use_qt", ""); // 인터넷라인 사용량
            mSrvcInfo.put("line_inrn_use_amt", null); // 인터넷라인 요금
            mSrvcInfo.put("gdnc_brd_use_qt", ""); // 회의안내보드 사용량
            mSrvcInfo.put("gdnc_brd_use_amt", null); // 회의안내보드 요금
            mSrvcInfo.put("tee_use_qt", "1set"); // 케이터링 사용량
            mSrvcInfo.put("tee_use_amt", Integer.parseInt("70000")); // 케이터링 요금
            mSrvcInfo.put("lnch_use_qt", ""); // Working Lunch 사용량
            mSrvcInfo.put("lnch_use_amt", null); // Working Lunch 요금
            mSrvcInfo.put("sndw_use_qt", ""); // 샌드위치 사용량
            mSrvcInfo.put("sndw_use_amt", null); // 샌드위치 요금
            mExcelData.put("srvc_info", mSrvcInfo);

            // 견적 정보
            // TODO : 장비 사용 리스트 및 요금, 서비스 사용 리스트 및 요금 조합 및 합계 계산 필요!
            Map<String, Object> mEstmInfo = new HashMap<String, Object>();
            mEstmInfo.put("meet_room_list", sMeetRoomNmList); // 회의실 사용 리스트
            mEstmInfo.put("meet_room_use_amt", nTotalUseAmt); // 회의실 사용 요금
            mEstmInfo.put("eqpn_use_list", "Beam Projection System"); // 장비 사용 리스트
            mEstmInfo.put("eqpn_use_amt", Integer.parseInt("60000")); // 장비 사용 요금
            mEstmInfo.put("srvc_use_list", "Catering service"); // 서비스 사용 리스트
            mEstmInfo.put("srvc_use_amt", Integer.parseInt("70000")); // 서비스 사용 요금
            mExcelData.put("estm_info", mEstmInfo);

            // 결제 정보
            Map<String, Object> mPymnInfo = new HashMap<String, Object>();
            mPymnInfo.put("pymn_mthd", createSelectListString(mCdList, "cd_set_cd", "PVP030", "cd", new String[]{"10"}, "cd_nm", 10)); // 결제방식
            mPymnInfo.put("pymn_info_1", "외환은행"); // 은행명
            mPymnInfo.put("pymn_info_2", "Swift code: KOEXKRSE"); // Swift 코드
            mPymnInfo.put("pymn_info_3", "계좌번호: 172-22-00929-4"); // 계좌번호
            mPymnInfo.put("bill_pblc", createSelectListString(mCdList, "cd_set_cd", "PVP040", "cd", new String[]{"20"}, "cd_nm", 10)); // 전자세금계산서 발생
            mExcelData.put("pymn_info", mPymnInfo);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            // 엑셀 파일 생성
            XLSTransformer oTransformer = new XLSTransformer();
            oTransformer.transformXLS(sTemplateFileName, mExcelData, sTargetPath + "/" + sTargetFileName);

            // 엑셀 파일 전송
            transFile(sTargetPath, sTargetFileName, poResponse);
        } catch (Exception exception) {
            exception.printStackTrace();

            oModelAndView = new ModelAndView("common/xls-down-result");
            oModelAndView.addObject("result", "error");
            oModelAndView.addObject("resultMessage", exception.getMessage());
        }

        poSession.removeAttribute("EXCEL_SESSION");

        return oModelAndView;
    }

    /**
     * 샘플02 엑셀 다운로드를 한다.
     * @pmParam pmParam Map<String, Object>
     * @pmParam poSession HttpSession
     * @pmParam poResponse HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smpl02")
    public ModelAndView excelDownSecretarialService(@RequestParam Map<String, Object> pmParam, HttpSession poSession, HttpServletResponse poResponse) throws Exception {
        ModelAndView oModelAndView = null;

        // 엑셀다운로드 중복 방지용 세션
        synchronized (poSession) {
            if (poSession.getAttribute("EXCEL_SESSION") != null) {
                oModelAndView = new ModelAndView("common/xls-down-result");
                oModelAndView.addObject("result", "error_progress");
                return oModelAndView;
            } else {
                poSession.setAttribute("EXCEL_SESSION", "Y");
            }
        }

        // 엑셀 다운로드 정보 저장
        String sTargetPath = ""; // 사용자 작업 임시 폴더
        String sTemplateFileName = "SMPL02.xlsx"; // 템플릿 파일
        String sTargetFileName = "샘플02_" + DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM) + ".xlsx"; // 생성 엑셀 파일

        // 엑셀 다운로드 준비
        String sErrCd = prepareExcelDown(pmParam, sTemplateFileName);
        if (sErrCd != null) {
            poSession.removeAttribute("EXCEL_SESSION");

            oModelAndView = new ModelAndView("common/xls-down-result");
            oModelAndView.addObject("result", sErrCd);
            return oModelAndView;
        }
        sTemplateFileName = (String) pmParam.get("template_file_name");
        sTargetPath = (String) pmParam.get("target_path");


        try {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            Map<String, Object> mRowData = null;

            // TODO : 서비스 신청서 리스트 조회
            // 순서에 맞게 가져와야 함
            List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "Atempo"); // Company Name
            mRowData.put("clnt_nm", "박종민"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("4875")); // Charge 1
            mDataList.add(mRowData);
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "Hankook EMC (Pivotal)"); // Company Name
            mRowData.put("clnt_nm", "이우림"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("27647")); // Charge 1
            mDataList.add(mRowData);
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "아리센트"); // Company Name
            mRowData.put("clnt_nm", "김혜림"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("3939")); // Charge 1
            mDataList.add(mRowData);
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "Commvault"); // Company Name
            mRowData.put("clnt_nm", "황세정"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("1911")); // Charge 1
            mDataList.add(mRowData);
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "Brightstar"); // Company Name
            mRowData.put("clnt_nm", "유재영"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("31146")); // Charge 1
            mDataList.add(mRowData);
            /*
            mRowData = new HashMap<String, Object>();
            mRowData.put("dt", DateUtil.convertString("20150625", DateUtil.SDF_YYYYMMDD, DateUtil.SDF_YYYYMMDD_DASH)); // Date (YYYY-MM-DD)
            mRowData.put("cro", "chloe"); // CRO
            mRowData.put("comp_nm", "Teads"); // Company Name
            mRowData.put("clnt_nm", "김태우"); // Client Name
            mRowData.put("optn_srvc", "5.26~6.25 미팅룸 전화사용요금"); // Optional Service
            mRowData.put("dscr_0", "TCC"); // Description 1
            mRowData.put("chrg_0", Integer.parseInt("1170")); // Charge 1
            mDataList.add(mRowData);
            */


            // 엑셀 데이터 처리 / 파일 생성
            Map<String, Object> mExcelData = new HashMap<String, Object>();
            try {
                if (mDataList != null) {
                    XLSTransformer oTransformer = new XLSTransformer();

                    // 사용자 작업 임시 폴더 생성
                    File oTargetPathFile = new File(sTargetPath + "/list");
                    if (!oTargetPathFile.exists()) {
                        oTargetPathFile.mkdir();
                    }

                    int nFormCountPerFile = 2;
                    int nDataCount = mDataList.size();
                    for (int i = 0; i < nDataCount; i += nFormCountPerFile) {
                        mExcelData.clear();
                        for (int j = 0; j < nFormCountPerFile; j++) {
                            if (i + j < nDataCount) {
                                mExcelData.put("data_" + j, mDataList.get(i + j));
                            }
                        }
                        // 엑셀 파일 생성
                        oTransformer.transformXLS(sTemplateFileName, mExcelData, sTargetPath + "/list/" + sTargetFileName.replace(".xlsx", "_" + (i / nFormCountPerFile + 1) + ".xlsx"));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();

                oModelAndView = new ModelAndView("common/xls-down-result");
                oModelAndView.addObject("result", "error");
                oModelAndView.addObject("resultMessage", exception.getMessage());
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            // 엑셀 파일 전송
            transFile(sTargetPath, sTargetFileName, poResponse);
        } catch (Exception exception) {
            exception.printStackTrace();

            oModelAndView = new ModelAndView("common/xls-down-result");
            oModelAndView.addObject("result", "error");
            oModelAndView.addObject("resultMessage", exception.getMessage());
        }

        poSession.removeAttribute("EXCEL_SESSION");

        return oModelAndView;
    }


    /**
     * 엑셀 다운로드 준비를 한다.
     * @pmParam pmParam Map<String, Object>
     * @pmParam psTemplateFileName pmParam
     * @pmParam psTargetPath pmParam
     * @return String
     * @throws Exception
     */
    private String prepareExcelDown(Map<String, Object> pmParam, String psTemplateFileName) throws Exception {
        File oTargetPathFile = null; // 작업 폴더
        File[] oFileList = null; // 생성 파일 리스트

        // 세션 확인
        UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
        if (oUserSession == null) {
            return "error_session";
        }

        String sUserId = oUserSession.getUserid();
        String sRealPath = ctx.getRealPath("");
        String sTemplateFileName = sRealPath + "/WEB-INF/xls/" + psTemplateFileName; // 템플릿 파일
        String sTargetPath = sRealPath + "/WEB-INF/temp/" + sUserId; // 사용자 작업 임시 폴더

        pmParam.put("template_file_name", sTemplateFileName);
        pmParam.put("target_path", sTargetPath);
        pmParam.put("real_path", sRealPath);

        // 템플릿 파일 확인
        if (!(new File(sTemplateFileName)).exists()) {
            return "error_tempfile";
        }

        // 사용자 작업 임시 폴더 생성
        oTargetPathFile = new File(sTargetPath);
        if (!oTargetPathFile.getParentFile().exists()) {
            oTargetPathFile.getParentFile().mkdir();
        }
        if (!oTargetPathFile.exists()) {
            oTargetPathFile.mkdir();
        }

        // 이전 생성 엑셀 파일 삭제
        oFileList = oTargetPathFile.listFiles();
        if (oFileList != null) {
            for (int i = 0; i < oFileList.length; i++) {
                oFileList[i].delete();
            }
        }
        // 이전 생성 압축 파일 삭제
        oTargetPathFile = new File(sTargetPath + "/list");
        if (oTargetPathFile != null && oTargetPathFile.exists()) {
            oFileList = oTargetPathFile.listFiles();
            if (oFileList != null) {
                for (int i = 0; i < oFileList.length; i++) {
                    oFileList[i].delete();
                }
            }
            oTargetPathFile.delete();
        }

        // 엑셀 파라미터 처리
        /*
        String sXlsParam = StringUtils.defaultString((String) pmParam.get("xls_param"));
        if (!"".equals(sXlsParam)) {
            ObjectMapper oJsonMapper = ObjectMapperFactory.getInstance();
            Map<String, Object> mExcelParam = oJsonMapper.readValue(sXlsParam, new TypeReference<Map<String, Object>>() {});

            pmParam.putAll(mExcelParam);

            mExcelParam = null;
        }
        */

        return null;
    }

    /**
     * 엑셀 파일 전송을 한다.
     * @pmParam psTargetPath String
     * @pmParam psTargetFileName String
     * @pmParam poResponse HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    private boolean transFile(String psTargetPath, String psTargetFileName, HttpServletResponse poResponse) throws Exception {
        boolean bResult = false;

        File oTransFile = null; // 전송 파일
        FileInputStream oFileInputStream = null;
        ServletOutputStream oServletOutputStream = null;

        File oTargetPathFile = null; // 작업 폴더
        File[] oFileList = null; // 생성 파일 리스트

        try {
            // 생성 엑셀 파일 1건이 있는 경우
            oTransFile = new File(psTargetPath + "/" + psTargetFileName);
            if (oTransFile != null && oTransFile.exists()) { // 엑셀 파일 1건
                // 엑셀 파일 헤더 설정
                StringBuffer sContentDisposition = new StringBuffer();
                sContentDisposition.append("attachment;fileName=\"");
                sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
                sContentDisposition.append("\";");
                poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                poResponse.setContentType("application/x-msexcel");
                poResponse.setContentLength(((Long) oTransFile.length()).intValue());
            }
            // 생성 엑셀 파일 1건이 없는 경우
            else {
                // 사용자 작업 리스트 폴더 생성 파일 확인
                oTargetPathFile = new File(psTargetPath + "/list");
                if (oTargetPathFile != null && oTargetPathFile.exists()) {
                    oFileList = oTargetPathFile.listFiles();
                }

                if (oFileList != null && oFileList.length > 0) {
                    // 생성 파일명 변경
                    psTargetFileName = psTargetFileName.replace(".xlsx", ".zip").replace(".xls", ".zip");

                    // 압축 파일 생성
                    oTransFile = new File(psTargetPath + "/" + psTargetFileName);
                    OutputStream oFileOutputStream = null;
                    try {
                        oFileOutputStream = new FileOutputStream(oTransFile);
                        (new CompressUtil()).zip(oTargetPathFile, oFileOutputStream, Charset.defaultCharset().name(), false);
                        oFileOutputStream.close();
                        oFileOutputStream = null;
                    } catch (Exception exception) {
                        throw exception;
                    } finally {
                        if (oFileOutputStream != null) {
                            oFileOutputStream.close();
                        }
                    }

                    // 압축 파일 헤더 설정
                    StringBuffer sContentDisposition = new StringBuffer();
                    sContentDisposition.append("attachment;fileName=\"");
                    sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
                    sContentDisposition.append("\";");
                    poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                    poResponse.setContentType("application/octet-stream");
                    poResponse.setContentLength(((Long) oTransFile.length()).intValue());
                }
            }

            if (oTransFile != null && oTransFile.exists()) {
                // 파일 전송
                oFileInputStream = new FileInputStream(oTransFile);
                oServletOutputStream = poResponse.getOutputStream();
                int nReadSize = 0;
                byte[] bytes = new byte[2048];
                while ((nReadSize = oFileInputStream.read(bytes)) != -1) {
                    oServletOutputStream.write(bytes, 0, nReadSize);
                }

                // 자원 해제
                oServletOutputStream.flush();
                oServletOutputStream.close();
                oServletOutputStream = null;
                oFileInputStream.close();
                oFileInputStream = null;

                bResult = true;
            }
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (oServletOutputStream != null) {
                oServletOutputStream.close();
            }
            if (oFileInputStream != null) {
                oFileInputStream.close();
            }

            // 작업 파일 삭제
            if (oTransFile != null && oTransFile.exists()) {
                oTransFile.delete();
            }
            // 이전 생성 엑셀 파일 삭제
            oTargetPathFile = new File(psTargetPath);
            if (oTargetPathFile != null && oTargetPathFile.exists()) {
                oFileList = oTargetPathFile.listFiles();
                if (oFileList != null) {
                    for (int i = 0; i < oFileList.length; i++) {
                        oFileList[i].delete();
                    }
                }

                oTargetPathFile.delete();
            }
            // 이전 생성 압축 파일 삭제
            oTargetPathFile = new File(psTargetPath + "/list");
            if (oTargetPathFile != null && oTargetPathFile.exists()) {
                oFileList = oTargetPathFile.listFiles();
                if (oFileList != null) {
                    for (int i = 0; i < oFileList.length; i++) {
                        oFileList[i].delete();
                    }
                }
                oTargetPathFile.delete();
            }
        }

        return bResult;
    }

    /**
     * 엑셀 선택 문자열을 생성한다.
     * @pmParam pmDataList List<Map<String, Object>>
     * @pmParam psSetCdCol String
     * @pmParam psSetCd String
     * @pmParam psCdCol String
     * @pmParam psCd String
     * @pmParam psNmCol String
     * @pmParam pnSpaceSize int
     * @return String
     * @throws Exception
     */
    private String createSelectListString(List<Map<String, Object>> pmDataList, String psSetCdCol, String psSetCd, String psCdCol, String[] psCdList, String psNmCol, int pnSpaceSize) throws Exception {
        String sResult = "";

        if (pmDataList == null || pmDataList.size() == 0) {
            return sResult;
        }
        if (psSetCdCol == null) {
            psSetCdCol = "cd_set_cd";
        }
        if (psSetCd == null) {
            psSetCd = "";
        }
        if (psCdCol == null) {
            psCdCol = "cd";
        }
        if (psCdList == null) {
            psCdList = new String[]{""};
        }
        if (psNmCol == null) {
            psNmCol = "cd_nm";
        }
        if (pnSpaceSize < 1 || pnSpaceSize > 15) {
            pnSpaceSize = 3;
        }
        String sSpace = "";
        for (int i = 0; i < pnSpaceSize; i++) {
            sSpace += " ";
        }

        for (Map<String, Object> mData : pmDataList) {
            if ("".equals(psSetCd) || psSetCd.equals(mData.get(psSetCdCol))) {
                // 코드명 사이 간격 넣기
                if (!"".equals(sResult)) {
                    sResult += sSpace;
                }

                boolean bCheck = false;
                for (String sCd : psCdList) {
                    if (sCd.equals(mData.get(psCdCol))) {
                        bCheck = true;
                        break;
                    }
                }
                sResult += (bCheck ? "■ " : "ㅁ ") + mData.get(psNmCol);
            }
        }

        return sResult;
    }

}