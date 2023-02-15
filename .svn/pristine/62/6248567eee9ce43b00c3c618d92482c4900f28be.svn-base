/*
 * (@)# DlwCmsService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/22
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

package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwCardService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.common.util.ByteDataReader;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 대명라이프웨이 Cms 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwCms
 */
@Service
public class DlwCmsServiceImpl extends EgovAbstractServiceImpl implements DlwCmsService {

    @Resource
    public DlwCmsDAO DlwCmsDAO;

    @Resource
    public DlwCardDAO DlwCardDAO;

    @Resource
    private DlwCardService DlwCardService;

    private final Logger log = LoggerFactory.getLogger(DlwCmsServiceImpl.class);

    /**
     * 대명라이프웨이의 Cms 회원정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 건수
     * @throws Exception
     */
    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsCsmmCount(pmParam);
    }

    /**
     * 대명라이프웨이의 Cms 회원정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsCsmm(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Cms 신청내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 정보 건수
     * @throws Exception
     */
    public int getDlwCmsRgsnHstrCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsRgsnHstrCount(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Cms 신청내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsRgsnHstrList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsRgsnHstrList(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Cms 출금의뢰내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 정보 건수
     * @throws Exception
     */
    public int getDlwCmsWdrwHstrCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsWdrwHstrCount(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Cms 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsWdrwHstrList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsWdrwHstrList(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 이체정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 수정결과
     * @throws Exception
     */
    public int updateCmsTranInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateCmsTranInfo(pmParam);
    }

    /**
     * 대명라이프웨이의 Cms 산출중 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 산출 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsWdrwChk(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsWdrwChk(pmParam);
    }


    /**
     * 대명라이프웨이의 CMS 금일 부가서비스 등록이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보 건수
     * @throws Exception
     */
    public int getDlwCmsAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsAplcDtlCount(pmParam);
    }

    /**
     * 대명라이프웨이의 CMS 금일 부가서비스 등록이력을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsAplcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsAplcDtl(pmParam);
    }

    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwCmsAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
         //CMS 해당구좌 CMS회원여부 검사
        String dltnFlag = DlwCmsDAO.getDltnFlagCmsMmbr(pmParam);
        pmParam.put("clTmp", "tempModule");

        String app_cl = (String)pmParam.get("app_cl");
        if ("1".equals(app_cl)) { // 신규일경우
            if("N".equals(dltnFlag)) {//유효한 회원존재
                rslt = "overlap";
            } else if("O".equals(dltnFlag)) {//회원없음
                DlwCmsDAO.insertDlwCmsAnxtSrvc(pmParam); // CMS 정보등록
                DlwCmsDAO.insertDlwCmsMmbr(pmParam);     // CMS 회원 신규등록
                DlwCmsDAO.updateDlwCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 엔컴
                //MemProdAccntDAO.updateCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 인우
                rslt = "success"; //등록되었습니다.


              //대리납 확인
                /*
                2016-10-24
                ASIS의 아래 부분을 4개의 메소드 호출로 변경
                int result = DlwCmsDAO.updateDlwCmsRprvPymt(pmParam);
                if(result > 0) {
                    rslt = "proxy";
                }
                */

                List<Map<String, Object>> mRow = DlwCmsDAO.getCardAndMemberBirth(pmParam); // CMS, 회원의 생일과 해피콜건수 조회
                Map<String, Object> hmmRow = mRow.get(0);
                String cmsBirth = CommonUtils.nvls((String)hmmRow.get("cms_birth"));
                String memBirth = CommonUtils.nvls((String)hmmRow.get("mem_birth"));
                int hpCallCnt 	= Integer.valueOf((String)hmmRow.get("ha_call_cnt"));
                if (!cmsBirth.equals(memBirth)) {
                    if (hpCallCnt > 0) {
                        DlwCmsDAO.updateHpCall(pmParam); // 해피콜 변경
                    } else {
                        DlwCmsDAO.insertHpCall(pmParam); // 해피콜 등록
                    }
                    DlwCmsDAO.insertHpCallHist(pmParam); // 해피콜이력 등록
                    rslt = "proxy";
                }

            } else if("Y".equals(dltnFlag)) {//해지된 회원존재
                DlwCmsDAO.insertDlwCmsAnxtSrvc(pmParam); // CMS 정보등록
                DlwCmsDAO.updateDlwCmsMmbr(pmParam);     // CMS 기존 회원정보 수정
                DlwCmsDAO.updateDlwCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 엔컴
                //MemProdAccntDAO.updateCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 인우
                rslt = "success"; //등록되었습니다.
                //대리납 확인
                /*
                int result = DlwCmsDAO.updateDlwCmsRprvPymt(pmParam);
                if(result > 0) {
                    rslt = "proxy";//대리납입니다.
                }
                */
                List<Map<String, Object>> mRow = DlwCmsDAO.getCardAndMemberBirth(pmParam); // CMS, 회원의 생일과 해피콜건수 조회
                Map<String, Object> hmmRow = mRow.get(0);
                String cmsBirth = CommonUtils.nvls((String)hmmRow.get("cms_birth"));
                String memBirth = CommonUtils.nvls((String)hmmRow.get("mem_birth"));
                int hpCallCnt 	= Integer.valueOf((String)hmmRow.get("ha_call_cnt"));
                if (!cmsBirth.equals(memBirth)) {
                    if (hpCallCnt > 0) {
                        DlwCmsDAO.updateHpCall(pmParam); // 해피콜 변경
                    } else {
                        DlwCmsDAO.insertHpCall(pmParam); // 해피콜 등록
                    }
                    DlwCmsDAO.insertHpCallHist(pmParam); // 해피콜이력 등록
                    rslt = "proxy";
                }
            }

        } else {
            if("N".equals(dltnFlag)) {
                DlwCmsDAO.insertDlwCmsAnxtSrvc(pmParam); // CMS 정보등록
                DlwCmsDAO.deleteDlwCmsMmbr(pmParam);     // CMS 회원정보 해지
                DlwCmsDAO.updateDlwCmsAcpgMthd(pmParam); // CMS 납입방법 수정
                DlwCmsDAO.updateDlwCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 엔컴
                //MemProdAccntDAO.updateCmsPymtMthd(pmParam); // CMS 납입방법 수정 - 인우
                rslt = "success";
            } else {
                rslt = "empty";//CMS 회원이 아닙니다.
            }
        }
        return rslt;
    }


    /**
     * 대명라이프웨이 CMS 출금이체 신청내역조회-신청전인 Data 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 출금이체 신청 전 데이터 건수
     * @throws Exception
     */
    public int getDlwWdrwTranDtlCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwTranDtlCount(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금이체 신청내역조회-신청전인 데이터를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 출금이체 신청 전 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwTranDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwTranDtlList(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 당월 출금의뢰 총액 조회
     *
     * @param pmParam 검색 조건
     * @return CMS 당월 출금의뢰 총액
     * @throws Exception
     */
    public String getDlwWdrwReqDtTtamt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwReqDtTtamt(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 공통정보
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsComnInfo(pmParam);
    }

    /**
     * 대명라이프웨이 공통정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwComnInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwComnInfo(pmParam);
    }

    /**
     * 대명라이프웨이 청구건수 수정 (콜센터 산출)
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public boolean updateDlwInvGunsuCall(Map<String, Object> pmParam) throws Exception{
        boolean flag = false;

        int gunsu = Integer.parseInt((String)pmParam.get("inv_gunsu"));
        int monAmt = Integer.parseInt((String)pmParam.get("mon_pay_amt"));

        int invCnt = DlwCmsDAO.getDlwLastPayNo(pmParam);//CMS 구좌별 최종납입회차 조회
        String evtNm = DlwCmsDAO.getEvntAccntNo(pmParam); // 행사고객 여부?
        String prodCl = DlwCmsDAO.getPrdtDiv(pmParam);//상품구분 조회 - 결합상품 구분용

        pmParam.put("gunsu", gunsu);
        pmParam.put("inv_cnt", invCnt);
        pmParam.put("wdrw_req_amt",DlwCmsDAO.getInvAmt(pmParam)); //납입액
        pmParam.put("inv_no",String.valueOf(invCnt + gunsu));

        List<Map<String, Object>> mComnList =  DlwCmsDAO.getDlwCmsComnInfo(pmParam);

        int gunLimitAmt = 0;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            gunLimitAmt = Integer.parseInt((String)mComnList.get(0).get("gundang_wdrw_limit"));
        }

        String bank_filin_brkdn = "";
        if("N".equals(evtNm) && "03".equals(prodCl)) {
            bank_filin_brkdn = (new StringBuilder(String.valueOf(mComnList.get(0).get("bank_filin_brkdn")))).append(String.valueOf(invCnt + gunsu)).toString();
        } else {
            bank_filin_brkdn = (new StringBuilder(String.valueOf(mComnList.get(0).get("bank_filin_brkdn")))).append(String.valueOf(invCnt + gunsu)).toString();
        }

        pmParam.put("bank_filin_brkdn", bank_filin_brkdn.replaceAll("\\s", ""));

        int wdrw_req_amt = 0;
        int expr_no = 0;
        wdrw_req_amt = Integer.parseInt(String.valueOf(pmParam.get("wdrw_req_amt")));
        expr_no = Integer.parseInt(String.valueOf(pmParam.get("expr_no")));

        if(gunLimitAmt != 0)
        {
            if(wdrw_req_amt > gunLimitAmt) {
                flag = false;
            } else if(expr_no < invCnt + gunsu) {
                flag = false;
            } else {
                DlwCmsDAO.updateInvGunsuForEB21Call(pmParam);//청구건수 수정,-실청구금액 수정
                flag = true;
            }
        } else {
            if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
                DlwCmsDAO.updateInvGunsuForEB21Call(pmParam);
            }
            flag = true;
        }
        return flag;
    }

    /**
     * 대명라이프웨이 청구건수 수정
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public boolean updateDlwInvGunsu(Map<String, Object> pmParam) throws Exception{
        boolean flag = false;

        int gunsu = Integer.parseInt((String)pmParam.get("inv_gunsu"));
        int monAmt = Integer.parseInt((String)pmParam.get("mon_pay_amt"));

        int invCnt = DlwCmsDAO.getDlwLastPayNo(pmParam);//CMS 구좌별 최종납입회차 조회
        String evtNm = DlwCmsDAO.getEvntAccntNo(pmParam); // 행사고객 여부?
        String prodCl = DlwCmsDAO.getPrdtDiv(pmParam);//상품구분 조회 - 결합상품 구분용

        pmParam.put("gunsu", gunsu);
        pmParam.put("inv_cnt", invCnt);
        pmParam.put("wdrw_req_amt",DlwCmsDAO.getInvAmt(pmParam)); //납입액
        pmParam.put("inv_no",String.valueOf(invCnt + gunsu));

        List<Map<String, Object>> mComnList =  DlwCmsDAO.getDlwCmsComnInfo(pmParam);

        int gunLimitAmt = 0;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            gunLimitAmt = Integer.parseInt((String)mComnList.get(0).get("gundang_wdrw_limit"));
        }

        String bank_filin_brkdn = "";
        if("N".equals(evtNm) && "03".equals(prodCl)) {
            bank_filin_brkdn = (new StringBuilder(String.valueOf(mComnList.get(0).get("bank_filin_brkdn")))).append(String.valueOf(invCnt + gunsu)).toString();
        } else {
            bank_filin_brkdn = (new StringBuilder(String.valueOf(mComnList.get(0).get("bank_filin_brkdn")))).append(String.valueOf(invCnt + gunsu)).toString();
        }

        pmParam.put("bank_filin_brkdn", bank_filin_brkdn.replaceAll("\\s", ""));

        int wdrw_req_amt = 0;
        int expr_no = 0;
        wdrw_req_amt = Integer.parseInt(String.valueOf(pmParam.get("wdrw_req_amt")));
        expr_no = Integer.parseInt(String.valueOf(pmParam.get("expr_no")));

        if(gunLimitAmt != 0) {
            if(wdrw_req_amt > gunLimitAmt) {
                flag = false;
            } else if(expr_no < invCnt + gunsu) {
                flag = false;
            } else {
                DlwCmsDAO.updateInvGunsuForEB21(pmParam);//청구건수 수정,-실청구금액 수정
                flag = true;
            }
        } else {
            if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
                DlwCmsDAO.updateInvGunsuForEB21(pmParam);
            } else {
                if(expr_no < invCnt + gunsu) {
                    flag = false;
                } else {
                    DlwCmsDAO.updateInvGunsuForCard(pmParam);
                    flag = true;
                }
                return flag;
            }
            flag = true;
        }

        //추가 >> [카드] 청구금액값이 5만원 미만일경우 할부건수 0으로 수정
        if(flag && "Card".equals(pmParam.get("wdrw_gubun"))) {
            if (wdrw_req_amt <= 50000) {
                pmParam.put("install_period", "0");
                DlwCardDAO.updateInstallPeriodForCard(pmParam);
            }
        }
        return flag;
    }

// 	컨트롤러의 메소드를 서비스로 옮기면서 주석 처리
//	updateCmsWdrwReq, updateCardWdrwReq
//	정출연 2016-10-14
//    /**
//     * 대명라이프웨이 CMS 청구파일 적용
//     *
//     * @param pmParam 검색 조건
//     * @return
//     * @throws Exception
//     */
//    public void updateCmsWdrwReq(Map<String, ?> pmParam) throws Exception {
//        DlwCmsDAO.deleteCmsWdrwReq(pmParam);
//        DlwCmsDAO.insertCmsWdrwReqByCall(pmParam);
//        DlwCmsDAO.updateWdrwReqYn(pmParam);
//    }
//
//    /**
//     * 대명라이프웨이 Card 청구파일 적용
//     *
//     * @param pmParam 검색 조건
//     * @return
//     * @throws Exception
//     */
//    public void updateCardWdrwReq(Map<String, ?> pmParam) throws Exception {
//        DlwCardDAO.deleteCardWdrwReq(pmParam);
//        DlwCardDAO.insertCardWdrwReqByCall(pmParam);
//        DlwCmsDAO.updateWdrwReqYn(pmParam);
//    }

    /**
     * 대명라이프웨이 Card 청구파일 적용
     *  - 기존의 updateCmsWdrwReq(), updateCardWdrwReq() 내용을 하나로 합친것
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCmsAndCardWdrwReq(Map<String, ?> mapInDs, Map<String, ?> mapInVar) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

        String msg = "";

        String gubun = (String)mapInVar.get("gubun");

        for (int i = 0; i < listInDs.size(); i++) {
            hmParam = listInDs.get(i);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

            if (rowType == DataSet.ROW_TYPE_UPDATED) {
                if ("cms".equals(gubun)) {
                    DlwCmsDAO.deleteCmsWdrwReq(hmParam);
                    DlwCmsDAO.insertCmsWdrwReqByCall(hmParam);
                    DlwCmsDAO.updateWdrwReqYn(hmParam);

                } else if ("card".equals(gubun)) {
                    DlwCardDAO.deleteCardWdrwReq(hmParam);
                    DlwCardDAO.insertCardWdrwReqByCall(hmParam);
                    DlwCmsDAO.updateWdrwReqYn(hmParam);
                }
            }
        }
    }

    /**
     * 대명라이프웨이 결합상품 산출 오류를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCombErrList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCombErrList(pmParam);
    }

    /**
     * 대명라이프웨이 출금이체 임시회원을 산출한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwTempMember(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwTempMember(pmParam);
    }

    /**
     * 대명라이프웨이 카드취소회원을 산출한다.
     * 임동진
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwErrorMember(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwErrorMember(pmParam);
    }

    /**
     * 대명라이프웨이 출금이체 연체회원을 산출한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwDelayMember(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwDelayMember(pmParam);
    }

    /**
     * 대명라이프웨이 임시/연체건 > 청구내역 추가
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertDlwWdrwEb21Add(Map<String, Object> pmParam) throws Exception {
        String accnt_no = CommonUtils.checkNull((String)pmParam.get("accnt_no"));
        String wdrw_gubun = CommonUtils.checkNull((String)pmParam.get("wdrw_gubun"));
        String mode = CommonUtils.checkNull((String)pmParam.get("mode"));

        int gunLimitAmt = Integer.parseInt((String)pmParam.get("gundang_wdrw_limit"));
        int delayCnt = 0;
        int invGunsu = 0;

        String delayStat = String.valueOf(pmParam.get("del_stat"));

        int exprNo = Integer.parseInt(String.valueOf(pmParam.get("expr_no")));
        int lastPayNo = Integer.parseInt(String.valueOf(pmParam.get("last_no")));
        int prePayGunsu = Integer.parseInt(String.valueOf(pmParam.get("pre_pay_gunsu")));
        int totalAmt = 0;
        int Mon_Pay = Integer.parseInt(String.valueOf(pmParam.get("mon_pay_amt")));

        invGunsu = prePayGunsu;
        if(invGunsu > exprNo - lastPayNo) {
            invGunsu = exprNo - lastPayNo;
        }
        pmParam.put("inv_gunsu", invGunsu);
        pmParam.put("last_no", lastPayNo);

        if (!"error".equals(mode)){
            totalAmt = DlwCmsDAO.getInvAmtByInvGunsu(pmParam);
        } else {
            totalAmt = Mon_Pay;
        }

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>" + Mon_Pay);

        if(!"Card".equals(wdrw_gubun)) {
            for(; totalAmt > gunLimitAmt; totalAmt = DlwCmsDAO.getInvAmtByInvGunsu(pmParam))
            {
                invGunsu--;
                pmParam.put("inv_gunsu", invGunsu);
            }
        }

        pmParam.put("inv_gunsu", invGunsu);
        pmParam.put("wdrw_req_amt", totalAmt);

        pmParam.put("stat", "01");
        if(mode.equals("temp")) {
            pmParam.put("cl", "5");
        } else if (mode.equals("delay")) {
            pmParam.put("cl", "4");
        } else {
            pmParam.put("cl", "1");
        }

        if(exprNo - lastPayNo != 0)
        {
            if(!"Card".equals(wdrw_gubun)) {

                int inv_no = DlwCmsDAO.getnew_inv_no(pmParam);
                 pmParam.put("inv_no", inv_no);
               // CommonUtils.printLog(pmParam);
                DlwCmsDAO.insertCmsWdrwReq(pmParam);
            }
            else {
                List<Map<String, Object>> lstInvNo = DlwCmsDAO.getInvNo(pmParam);
                String sInvNo = "";
                if (null == lstInvNo) {
                    sInvNo = (String)pmParam.get("inv_gunsu");
                } else {
                    sInvNo = String.valueOf(lstInvNo.get(0).get("inv_no"));
                }
                pmParam.put("inv_no", sInvNo);

                DlwCmsDAO.insertCardWdrwReq(pmParam);
            }
        } else {
            System.out.println((new StringBuilder("만기회원 제외 :: ")).append(accnt_no).toString());
        }
    }

    /**
     * 대명라이프웨이 출금이체 신청전 조회-구좌에 의한
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwAddData(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwAddData(pmParam);
    }
    /**
     * 대명라이프웨이 납입액 조회
     *
     * @param pmParam 검색 조건
     * @return 납입액
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getInvAmt(pmParam);
    }


    /**
     * 대명라이프웨이 CMS 구좌별 최종납입회차 조회
     *
     * @param pmParam 검색 조건
     * @return 최종납입회차
     * @throws Exception
     */
    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwLastPayNo(pmParam);
    }

    /**
     * 대명라이프웨이 EB21 산출 적용
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertAddTempEb21(Map<String, Object> pmParam) throws Exception {
        //공통정보
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {

              int inv_no = DlwCmsDAO.getnew_inv_no(pmParam);
              pmParam.put("inv_no", inv_no);
            DlwCmsDAO.insertCmsWdrwReq(pmParam);
        } else {

            List<Map<String, Object>> lstInvNo = DlwCmsDAO.getInvNo(pmParam);

            String sInvNo = "";
            if (null == lstInvNo) {
                System.out.println("aaaaa");
                sInvNo = (String)pmParam.get("inv_gunsu");
            } else {
                System.out.println("bbbb");
                //sInvNo = (String)lstInvNo.get(0).get("inv_no");

                // 원본
                sInvNo = String.valueOf(lstInvNo.get(0).get("inv_no"));

                System.out.println("ccccc");
                System.out.println(sInvNo);
            }
            pmParam.put("inv_no", sInvNo);
            DlwCmsDAO.insertCardWdrwReq(pmParam);
        }
    }

    /**
     * 대명라이프웨이 EB21 콜센터 산출 적용
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertAddTempCallEb21(Map<String, Object> pmParam) throws Exception {
        //공통정보

        // 정출연 - 기존에 1개의 메소드로 하던것을 2개의 메소드로 분리
        // 2016.10.17
        List<Map<String, Object>> lstInvNoAndSeq = DlwCmsDAO.getInvNoAndSeq(pmParam);

        Map<String, Object> hmInvNoAndSeq = lstInvNoAndSeq.get(0);
        pmParam.put("p_inv_no", (String)hmInvNoAndSeq.get("p_inv_no"));
        pmParam.put("p_seq", (String)hmInvNoAndSeq.get("p_seq"));
        DlwCmsDAO.insertDlwWdrwCallCenter(pmParam);
    }

    /**
     * 대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwHstr(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwHstr(pmParam);
    }

    /**
     * 대명라이프웨이 콜센터 청구취소 복원
     *
     * @param pmParam 검색 조건
     * @return 복원 건수
     * @throws Exception
     */
    public int updateAllWdrwCallCenterTemp(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.updateAllWdrwCallCenterTemp(pmParam);
    }

    /**
     * 대명라이프웨이 청구취소
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int deleteAllWdrwTemp(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.deleteAllWdrwTemp(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 산출 신청전 조회-이체일에 의한( 산출  )
     *
     * @param pmParam 검색 조건
     * @return 산출 리턴값
     * @throws Exception
     */
    public List<Map<String, Object>> getCmsMemberByIchaeDt(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> result = null;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            DlwCmsDAO.getCmsMemberByIchaeDt(pmParam);
            result = DlwCmsDAO.getDlwWdrwHstr(pmParam);
        } else {

            DlwCmsDAO.getNiceMemberByIchaeDt(pmParam);
            result = DlwCardDAO.getCardWdrwReqList(pmParam);
        }
        return result;
    }


    /** cms 임시*/
    public List<Map<String, Object>> getCmsMemberByimsiIchaeDt(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> result = null;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            DlwCmsDAO.getCmsMemberByimsiIchaeDt(pmParam);
           // result = DlwCmsDAO.getDlwWdrwHstr(pmParam);
        } else {

            DlwCardDAO.getNiceCARDMemberByIchaeDt(pmParam);
          //  result = DlwCardDAO.getCardWdrwReqList(pmParam);
        }
        return result;
    }

    /**
     * 산출된 건 중 고객만족센터 미적용등록 여부
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCallWdrwReqCheck(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.getCallWdrwReqCheck(pmParam);
    }

    /**
     *  CMS, 콜센터 메뉴 모두에서 출금신청했을 경우
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCallDupWdrw(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getCallDupWdrw(pmParam);
    }

    /**
     * 대명라이프웨이 카드 출금이체의뢰 내역 신청상태 변경(대량건)
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateCmsAppStateLrqnt(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.updateCmsAppStateLrqnt(pmParam);
    }

    /**
     *  대명라이프웨이 CMS 결과 파일명 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCmsResultFileNm(Map<String, Object> pmParam) throws Exception {
         return DlwCmsDAO.getCmsResultFileNm(pmParam);
    }

    /**
     *  대명라이프웨이 EB22 파일 결과처리 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void getCmsAppIsTransEb22(Map<String, ?> pmParam) throws Exception {
        DlwCmsDAO.getCmsAppIsTransEb22(pmParam);
    }

    /**
     *  대명라이프웨이 EB22 파일 결과처리 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void immsiinsert(Map<String, ?> pmParam) throws Exception {
        DlwCmsDAO.immsiinsert(pmParam);
    }

    /**
     *  대명라이프웨이 Cms 결과 파일 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getReadFileCms(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getReadFileCms(pmParam);
    }

    /**
     * 대명라이프웨이 Eb22 파일에 해당하는 신청정보 건수
     *
     * @param pmParam 검색 조건
     * @return 신청정보 건수
     * @throws Exception
     */
    public int getEB22WdrwCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getEB22WdrwCount(pmParam);
    }




    public int CmsMemberByIchaeCount(Map<String, ?> pmParam) throws Exception {
         if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
             return DlwCmsDAO.CmsMemberByIchaeCount(pmParam);
        } else {

            return DlwCardDAO.CARDMemberByIchaeCount(pmParam);
        }
    }
    /**
     *  대명라이프웨이 cms 결과 업데이트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> cmsResultProc(Map<String, Object> pmParam) throws Exception {
        return DlwCmsDAO.cmsResultProc(pmParam);
    }

    /**
     *  대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrByInvDt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getWdrwHstrByInvDt(pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrSttc(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getWdrwHstrSttc(pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrStatsSumByInvDt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getWdrwHstrStatsSumByInvDt(pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계 - 합)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrStatsCombByInvDt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getWdrwHstrStatsCombByInvDt(pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결제 신청전으로 상태값 변경
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateWdrwApclCancCard(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwApclCancCard(pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결제 로그 삭제 - 신청전으로 되돌리기 시
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int deleteNiceCardLog(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteNiceCardLog(pmParam);
    }

    /**
     * 대명라이프웨이 Cms청구 신청전으로 상태값 변경
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateWdrwApclCancCms(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwApclCancCms(pmParam);
    }

    /**
     * 대명라이프웨이 Eb11정보를 업데이트한다.
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public List<Map<String, Object>> updateConvertFileEB11(Map<String, Object> pmParam) throws Exception {
        List<Map<String, Object>> arrImpsCdList = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> sortedEB11Arr = new ArrayList<Map<String,Object>>();

        DataSetMap arrEB11 = (DataSetMap)pmParam.get("list");

        String emple_no = (String)pmParam.get("emple_no");
        String mmdd = (String)pmParam.get("mmdd");

        for(int i = 0; arrEB11.size() > i; i++)
        {
            HashMap map = (HashMap)arrEB11.get(i);
            if(!"1".equals(String.valueOf(map.get("app_cl")))) {
                sortedEB11Arr.add(map);
            }
        }

        for(int i = 0; arrEB11.size() > i; i++)
        {
            HashMap map = (HashMap)arrEB11.get(i);
            if("1".equals(String.valueOf(map.get("app_cl")))) {
                sortedEB11Arr.add(map);
            }
        }

        for(int i = 0; sortedEB11Arr.size() > i; i++)
        {
            Map<String, Object> CmsAppInfo = (HashMap<String, Object>)sortedEB11Arr.get(i);
            CmsAppInfo.put("emple_no", emple_no);
            CmsAppInfo.put("mmdd", mmdd);
            CmsAppInfo.put("inv_tg_dt","15");
            CmsAppInfo.put("cl_tmp","syncCheck");


            // 회원번호가 소문자인 경우 대문자로 변환 - 2018.03.15 정승철
            String upperAccntNo = (String)CmsAppInfo.get("accnt_no");
            upperAccntNo = upperAccntNo.toUpperCase();
            CmsAppInfo.put("accnt_no",upperAccntNo);


            //대명라이프웨이 CMS 해당구좌 CMS회원여부 검사
            String cmsYo = DlwCmsDAO.getDltnFlagCmsMmbr(CmsAppInfo);               //// 사용여부 확인
            //대명라이프웨이 CMS 회원 정보 조회
            String cmsYn = DlwCmsDAO.getCmsMem(CmsAppInfo);                       // RNWHKRK DL

            //파일의 SyncNo 를 통한 구좌 기본정보 조회
            List<Map<String, Object>> mAccntMemList = DlwCmsDAO.getAccntMemInfoBySyncNo(CmsAppInfo);
            Map<String, Object> accntMem = new HashMap<String, Object>();

            if(mAccntMemList != null && mAccntMemList.size() > 0) {
                accntMem = mAccntMemList.get(0);
            }

            if(accntMem.get("accnt_no") != null) {
                CmsAppInfo.put("accnt_no", accntMem.get("accnt_no"));
                CmsAppInfo.put("payr_no", accntMem.get("mem_no"));
                CmsAppInfo.put("depr_nm", accntMem.get("mem_nm"));
            } else {

                //CmsAppInfo.put("accnt_no", accntMem.get("accnt_no"));   회원코드값이 없는데 값 가져와서 넣어놨다.. 빈값으로 수정  정왕채
                CmsAppInfo.put("accnt_no", "");
                CmsAppInfo.put("payr_no", "");
                CmsAppInfo.put("depr_nm", "");
            }

            CmsAppInfo.put("stat", "04");
            CmsAppInfo.put("app_dt", mmdd);


            if("1".equals(CmsAppInfo.get("app_cl"))) {
                CmsAppInfo.put("acpt_mthd","06");
            } else {
                CmsAppInfo.put("acpt_mthd","07");
            }

            CmsAppInfo.put("rltn_cd", "20");
            if("E".equals(cmsYn)) { //잘못된 회원 (Error)
                Map<String, Object> impsMap = CmsAppInfo;
                impsMap.put("imps_cd", "A013");
                // 불가능 리스트에 추가..
                arrImpsCdList.add(impsMap);
                CmsAppInfo.put("stat", "03");
                CmsAppInfo.put("imps_cd","A016");
                //대명라이프웨이 CMS 신규/해지신청-기관(은행)
                DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
            } else if("1".equals(CmsAppInfo.get("app_cl")) && "N".equals(cmsYn)) { //신규, 정상회원(삭제여부n)
                if("O".equals(cmsYo)) {// 신규회원
                    //대명라이프웨이 CMS 신규/해지신청-기관(은행)
                    DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                    //대명라이프웨이 CMS 회원 등록
                    DlwCmsDAO.insertDlwCmsMmbr(CmsAppInfo);
                    //대명라이프웨이 CMS 납입방법 수정
                    DlwCmsDAO.updateDlwCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 엔컴
                    //MemProdAccntDAO.updateCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 인우

                } else if("Y".equals(cmsYo)) { // 삭제 회원
                    //대명라이프웨이 CMS 신규/해지신청-기관(은행)
                    DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                    //대명라이프웨이 CMS 회원 수정
                    DlwCmsDAO.updateDlwCmsMmbr(CmsAppInfo);
                    //대명라이프웨이 CMS 납입방법 수정
                    DlwCmsDAO.updateDlwCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 엔컴
                    //MemProdAccntDAO.updateCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 인우
                } else if("N".equals(cmsYo)) { // 기존 회원
                    Map<String, Object> impsMap = CmsAppInfo;
                    impsMap.put("imps_cd", "A016");
                    // 불가능 리스트에 추가..
                    arrImpsCdList.add(impsMap);
                    CmsAppInfo.put("stat", "03");
                    CmsAppInfo.put("imps_cd", "A016");
                    DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                }
            } else if ("3".equals(CmsAppInfo.get("app_cl")) || "7".equals(CmsAppInfo.get("app_cl")) && "Y".equals(cmsYn)) {//해지, 보류해지 , 정상회원(삭제여부y)
                if ("O".equals(cmsYo)) {
                    Map<String, Object> impsMap = CmsAppInfo;
                    impsMap.put("imps_cd", "A013");
                    arrImpsCdList.add(impsMap);
                    CmsAppInfo.put("stat", "03");
                    CmsAppInfo.put("imps_cd","A013");
                    DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                } else if ("Y".equals(cmsYo)) {
                    Map<String, Object> impsMap = CmsAppInfo;
                    impsMap.put("imps_cd", "A013");
                    arrImpsCdList.add(impsMap);
                    CmsAppInfo.put("stat", "03");
                    CmsAppInfo.put("imps_cd","A013");
                    DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                } else if ("N".equals(cmsYo)) {
                    if("Y".equals(DlwCmsDAO.getIsRealCmsInfoByBankCancl(CmsAppInfo))) {
                        DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                        DlwCmsDAO.deleteDlwCmsMmbr(CmsAppInfo);
                        DlwCmsDAO.updateDlwCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 엔컴
                        //MemProdAccntDAO.updateCmsPymtMthd(CmsAppInfo); // CMS 납입방법 수정 - 인우
                    } else if ("N".equals(DlwCmsDAO.getIsRealCmsInfoByBankCancl(CmsAppInfo))) {
                        Map<String, Object> impsMap = CmsAppInfo;
                        impsMap.put("imps_cd", "A017");
                        arrImpsCdList.add(impsMap);
                        CmsAppInfo.put("stat", "03");
                        CmsAppInfo.put("imps_cd","A017");
                        DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
                    }
                }
            } else {
                Map<String, Object> impsMap = CmsAppInfo;
                impsMap.put("imps_cd", "A017");
                arrImpsCdList.add(impsMap);
                CmsAppInfo.put("stat", "03");
                CmsAppInfo.put("imps_cd","A017");
                DlwCmsDAO.insertDlwCmsAppByNomal(CmsAppInfo);
            }
        }

        return arrImpsCdList;
    }

    /**
     *  구좌별 CMS 정보조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCMSInfoByAccnt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getCMSInfoByAccnt(pmParam);
    }

    /**
     * CMS정보 Idn_no 추출
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getNcaIdnNo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getNcaIdnNo(pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 산출테이블에서 삭제시
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn1(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwReqYn1(pmParam);
    }

    public int updateWdrwReqYn1all(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwReqYn1all(pmParam);
    }


    /**
     * 대명라이프웨이 CMS EB21 산출내역 삭제
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteWDRWHistory(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteWDRWHistory(pmParam);
    }


    public int deleteWDRWHistoryall(Map<String, ?> pmParam) throws Exception {
        if (  !"Card".equals(pmParam.get("gubun")) ){
            return DlwCmsDAO.deleteWDRWHistoryall(pmParam);
      } else {
          return DlwCmsDAO.deletecardWDRWHistoryall(pmParam);

      }
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 카드 산출테이블에서 삭제시
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn2(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwReqYn2(pmParam);
    }

    /**
     * 대명라이프웨이 EB21 산출내역 삭제 (카드)
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCardWdrw(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteCardWdrw(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 해당구좌 CMS회원여부 검사
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getDltnFlagCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDltnFlagCmsMmbr(pmParam);
    }

    /**
     *  구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getAccntInfo(pmParam);
    }

    /**
     *  입금 조회 간소화
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPaySoftInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPaySoftInfo(pmParam);
    }


    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngBugaInfo(pmParam);
    }

    /**
     *  입금전표조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngList(pmParam);
    }

    /**
     *  입금전표조회-결합상품할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngDtlList(pmParam);
    }

    /**
     *  입금전표조회-결합상품추가부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngDtl1List(pmParam);
    }

    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 할부금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngDtlBugaInfo(pmParam);
    }

    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 추가금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayMngDtl1BugaInfo(pmParam);
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getRefundList(pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getRefundDtlList(pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getRefundDtl1List(pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayAmtByPayCnt(pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayAmtDtlByPayCnt(pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 추가 부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayAmtDtl1ByPayCnt(pmParam);
    }

    /**
     *  가수금 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getGasuMngCount(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getGasuMngCount(pmParam);
    }

    /**
     *  가수금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGasuMngList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getGasuMngList(pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCmsPymtMthd(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateDlwCmsPymtMthd(pmParam);
    }

    /**
     *  고객만족센터 산출 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */



 public int deleteWDRWCall(XPlatformMapDTO xpDto) throws Exception {

     int iCnt = 0;
     int iRetCnt = 0;
 //    Map<String, Object> hmParam = new HashMap<String, Object>();


     Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        try {
             DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
             for (int i = 0; i < listInDs.size(); i++) {
                 Map hmParam = listInDs.get(i);
                 //세션
                 ParamUtils.addSaveParam(hmParam);
                 hmParam.put("emple_no", hmParam.get("rgsr_id"));
                 hmParam.put("reg_man", hmParam.get("rgsr_id"));
                 hmParam.put("upd_man", hmParam.get("rgsr_id"));
                 /*int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                 if (rowType == DataSet.ROW_TYPE_UPDATED) {
                     iCnt = DlwCmsDAO.deleteWDRWCall(hmParam);
                     iRetCnt = iRetCnt + iCnt;
                 }*/

                 iCnt = DlwCmsDAO.deleteWDRWCall(hmParam);
                 iRetCnt = iRetCnt + iCnt;
             }

        } catch (Exception e) {
            throw e;
        }
        return iRetCnt;
    }

    /**
     *  입금전표조회-총금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getPayTotalList(pmParam);
    }

    /**
     *  입금전표조회-상품정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getProductDtl(pmParam);
    }

    /**
     * 회원번호로 상품코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getProdCdByAccntNo(pmParam);
    }

    /**
     *  가수금 환불 내역조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGasuDetail(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getGasuDetail(pmParam);
    }

    /**
     *  가수 환불 상세 내역 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateGasuDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateGasuDtl(pmParam);
    }

    /**
     *  가수금 환불 내역조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGasuDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteGasuDtl(pmParam);
    }

    /**
     *  가수 환불 상세 내역 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertGasuDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.insertGasuDtl(pmParam);
    }

    /**
     *  가수 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertGasuMng(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.insertGasuMng(pmParam);
    }

    /**
     *  가수 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateGasuMng(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateGasuMng(pmParam);
    }

    /**
     *  가수 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGasuMng(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteGasuMng(pmParam);
    }

    /**
     * 대명라이프웨이 청구건수 수정
     *  - 컨트롤러의 로직을 서비스로 옮기면서 생성함
     *  - 2016-10.14
     *  - 정출연
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public Map<String, Object> updateDlwInvGunsuNew(Map<String, ?> mapInDs, Map<String, ?> mapInVar, String psPathTyp) throws Exception{
        Map<String, Object> mRet = new HashMap<String, Object>();

        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
        List<Map<String, Object>> mList = null;
        String msg = "";

        try {

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                hmParam.put("mode", mapInVar.get("mode"));
                hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                String seq = StringUtils.defaultString(String.valueOf(hmParam.get("seq")));
                String accnt_no = StringUtils.defaultString(String.valueOf(hmParam.get("accnt_no")));
                String inv_gunsu = StringUtils.defaultString(String.valueOf(hmParam.get("inv_gunsu")));
                String mon_pay_amt = StringUtils.defaultString(String.valueOf(hmParam.get("mon_pay_amt")));
                String expr_no = StringUtils.defaultString(String.valueOf(hmParam.get("expr_no")));
                String inv_no = StringUtils.defaultString(String.valueOf(hmParam.get("inv_no")));

                hmParam.put("seq", seq);
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("inv_gunsu", inv_gunsu);
                hmParam.put("mon_pay_amt", mon_pay_amt);
                hmParam.put("expr_no", expr_no);
                hmParam.put("inv_no", inv_no);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    boolean update_fg = true;
                    if("call".equals(psPathTyp)){
                        update_fg = updateDlwInvGunsuCall(hmParam);
                    } else {
                        update_fg = updateDlwInvGunsu(hmParam);
                    }

                    if (update_fg) {
                        msg = "ok";
                    } else {
                        msg = "limit";
                    }
                }

                if("call".equals(psPathTyp)){
                    mList = getDlwWdrwTranDtlList(hmParam);
                } else {
                    if (!"Card".equals(mapInVar.get("wdrw_gubun"))) {
                        mList = getDlwWdrwHstr(hmParam);
                    } else {
                        mList = DlwCardService.getCardWdrwReqList(hmParam);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        mRet.put("mList", mList);
        mRet.put("msg", msg);

        return mRet;
    }

    /**
     *  출금이체의뢰결과파일변환(EB22) 오류 내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEB22ErrorInfo(XPlatformMapDTO xpDto, Map hmOutParam) throws Exception {
        int cudCnt = 0;

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        DataSetMap dsCond = (DataSetMap)mapInDs.get("ds_input");
        Map<String, Object> pmParam = dsCond.get(0);

        String sYmd 	= CommonUtils.nvls((String)pmParam.get("yymmdd")); // 년월일 - 년이 2자리임


        /*CMS경로*/
        String sFileDir 		= "/app/CMS/9993083157/EB22/";
        String sBaseNm			= "EB22" + sYmd.substring(2);
        String sInputFile 		= sFileDir + sBaseNm;
        log.debug(">sInputFileDir : " + sInputFile);

        try {
            File file = new File(sInputFile);

            if (!file.exists()) {
                return -1;
            }

            if (!file.canRead()) {
                return -2;
            }

            FileInputStream fis = new FileInputStream(file);
            int nReadUnit 		= 150;
            byte[] buf 			= new byte[nReadUnit];
            int read 			= 0;

            Map<String, Object> hmParam = new HashMap<String, Object>();

            ByteDataReader bdr = null;

            // R (불능건) 이 1건이라도 있으면 마지막 T로 검색하여 인서트하는 부분을 체크하는 변수
            int Check = 0;
            while ((read = fis.read(buf)) != -1) {
                bdr = new ByteDataReader(buf, nReadUnit);
                bdr.setTrim(true);

                String rec_gbn = bdr.get(1);
              if ("R".equals(rec_gbn) ) {
                Check+= Check;
                hmParam = new HashMap<String, Object>();
                // hmParam.put("rec_gbn"			, bdr.get(1))	; // Record 구분
                hmParam.put("rec_gbn"			, rec_gbn)	; // Record 구분
                hmParam.put("data_ser_no"		, bdr.get(8))	; // Data 일련번호
                hmParam.put("org_cd"			, bdr.get(10))	; // 기관코드
                hmParam.put("wd_bank_cd"		, bdr.get(7))	; // 출금참가기관(은행)점코드
                hmParam.put("wd_accnt_no"		, bdr.get(16))	; // 출금계좌번호
                hmParam.put("wd_imps_amt"		, bdr.get(13))	; // 출금불능금액
                hmParam.put("birth_dt"			, bdr.get(13))	; // 생년월일 또는 사업자등록번호
                hmParam.put("wd_yn"				, bdr.get(1))	; // 출금여부
                hmParam.put("wd_imps_cd"		, bdr.get(4))	; // 출금불능코드

                // hmParam.put("accnt_wrt_conts"	, new String(bdr.getByEnc(16, "EUC-KR").getBytes(), "UTF-8")); // 통장기재내용
                hmParam.put("accnt_wrt_conts"	, bdr.getByEnc(16, "EUC-KR")); // 통장기재내용

                hmParam.put("money_type"		, bdr.get(2))	; // 자금종류
                hmParam.put("payers_no"			, bdr.get(20))	; // 납부자번호
                hmParam.put("org_use_section"	, bdr.get(5))	; // 이용기관 사용영역
                hmParam.put("wd_type"			, bdr.get(1))	; // 출금형택
                hmParam.put("tel"				, bdr.get(12))	; // 전화번호(핸드폰)
                hmParam.put("filler"			, bdr.get(21))	; // 전화번호(핸드폰)

                // 테이블에 인서트할 내용
                hmParam.put("accnt_no"		, hmParam.get("payers_no"));  /// 납부자번호
                hmParam.put("imps_cd"		, hmParam.get("wd_imps_cd"));  ///  출금불능코드
                hmParam.put("file_nm"		, sYmd+ "-" + sBaseNm);

                hmOutParam.put("file_nm"	, sYmd+ "-" + sBaseNm);

                log.debug("데이터일련번호 : " + (String)hmParam.get("data_ser_no"));
                log.debug("통장기재내용 : " + (String)hmParam.get("accnt_wrt_conts"));
                log.debug("자금종류 : " + (String)hmParam.get("money_type"));
                log.debug("레코드구분 : " + (String)hmParam.get("rec_gbn"));
                log.debug("출금계좌번호 : " + (String)hmParam.get("wd_accnt_no"));
                log.debug("전화번호 : " + (String)hmParam.get("tel"));
                log.debug("---");

                cudCnt += DlwCmsDAO.insertEB22ErrorInfo(hmParam);

              }
              else if ("T".equals(rec_gbn) && Check == 0 ){
                  hmParam.put("accnt_no"		, "999999999999");  /// 납부자번호
                  hmParam.put("imps_cd"		, "0021");  ///  출금불능코드
                  hmParam.put("file_nm"		, sYmd+ "-" + sBaseNm);
                  hmOutParam.put("file_nm"	, sYmd+ "-" + sBaseNm);
                  log.debug("오류건 없음 : file_nm  :: "	+sYmd+ "-" + sBaseNm);
                  log.debug("---");

                  cudCnt += DlwCmsDAO.insertEB22ErrorInfo(hmParam);
              }
             }



            /* 출금의뢰신청 상태 변경 */
            Map<String, Object> mCond = new HashMap<>();
            ParamUtils.addSaveParam(mCond);

            mCond.put("inv_dt", sYmd);
            // mCond.put("file_nm", sYmd+ "-" + sBaseNm);
            // mCond.put("pay_day", sYmd);
            cudCnt += DlwCmsDAO.updateWrdwReqStat(mCond);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cudCnt;
    }



    public void eb22upload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

         String subDir = CommonUtils.nvls(request.getParameter("subDir"));

       //  log.info(">> subDir : " + subDir);
         int idx = 1;

         String[] fileClToArr	= { "0001", "0002", "0003", "0004", "0005" };

         String successPath 		= "";
         String errorPath 		= "";

         log.info("hsFactoringFileBatchUpload> 2 ");

         String osName = System.getProperty("os.name").toLowerCase();

       //  System.out.println(osName);
         if (osName.indexOf("windows") >= 0) {
             successPath = EgovProperties.getProperty("cms.file.windows.successPath");
          //   System.out.println(successPath);
       //      System.out.println("bbbbbbbbbbbbbbbbb");
         } else {
             successPath = EgovProperties.getProperty("cms.file.unix.successPath");
         }

         String tempDir = System.getProperty("java.io.tmpdir");

         log.info("---uploadToNas 서비스 - 1");
         MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);

         Enumeration enm = multipartRequest.getFileNames();

   //      System.out.println("파일 네임 getFileNames " + enm);
         String sKey = "";

  //-       String sCurrDthms 	= DateUtil.getCurrDthms();
    //     String sYmd 		=  "20170214";  // sCurrDthms.substring(0,8);

         int cnt				= 0;
         String[] tok		= null;
         String[] fileLayout	= null;
         String fileNm		= "";
         String ext 			= "";
         String accntNo 		= "";
         String phone		= "";
         String cl1 			= "";
         String cl2 			= "";
         String tmpStr 		= "";
         String randomYn		= "N";
         String genDm 		= "";
         HashMap<String,String> fileInfo	= null;
         int fileCnt			= 0;

         UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
         String empleNo 		= CommonUtils.nvls(oUserSession.getUserid());
         Map<String, Object> mParam = null;

         try {

             if ("".equals(empleNo)) {
                 throw new EgovBizException("업로드 권한이 없습니다.");
             }

             mParam = new HashMap<>();
             mParam.put("emple_no", empleNo);

             // 실제로는 단건만 처리함
             while (enm.hasMoreElements())
             {
                 fileCnt++;
                 sKey = (String)enm.nextElement();
                 log.info("sKey : " + sKey);
                 File upfile = multipartRequest.getFile(sKey);

                 String sOrigFn = upfile.getName();


                 mResuslt.put("file_nm", sOrigFn);

              //   System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
                 System.out.println(sOrigFn);

                 String p_cms = sOrigFn.substring(0, 4);
              //   String p_cms = "EB22";
                 System.out.println(p_cms);
                 System.out.println(successPath);



                      log.info("hsFactoringFileBatchUpload> 16 ");

                      File fileClFolder = new File(successPath + p_cms );
                      if (!fileClFolder.isDirectory()) {
                          fileClFolder.mkdirs();
                      }

                      log.info("hsFactoringFileBatchUpload> 17 ");

                      String fileSavePath = successPath + p_cms + "/" + sOrigFn;
                      log.info("파일저장경로> fileSavePath : " + fileSavePath);
                      log.info("upfile.length() : " + upfile.length());
                      log.info("upfile.getAbsolutePath() : " + upfile.getAbsolutePath());

                      FileOutputStream newFileOs = new FileOutputStream(new File(fileSavePath));
                  //    newFileOs.close();
                      FileUtils.copyFile(upfile, newFileOs);
                      upfile.delete();
                      newFileOs.close();

                      log.info("hsFactoringFileBatchUpload> 18 ");

                      HashMap<String,String> obj = new HashMap<>();
                      if ("Y".equals(randomYn)) {
                          log.info("hsFactoringFileBatchUpload> 19 ");
                          obj.put("cl", "임의매치");
                          obj.put("file_nm", sOrigFn);
                      }
                      else {
                          log.info("hsFactoringFileBatchUpload> 20 ");
                          obj.put("cl", "성공");
                          obj.put("file_nm", sOrigFn);
                      }
                      log.info("hsFactoringFileBatchUpload> 21 ");


             }

             if (fileCnt < 1) {
                 throw new EgovBizException("업로드된 파일이 없습니다.");
             }

         } catch (FileNotFoundException ex) {

             log.info("hsFactoringFileBatchUpload> 28 ");
             ex.printStackTrace();

             throw ex;
         } catch (IOException ex) {

             log.info("hsFactoringFileBatchUpload> 29 ");
             ex.printStackTrace();

             throw ex;
         }

    }

    /**
     * 대명라이프웨이 콜센터 산출 중복건 리스트
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCallDupList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getCallDupList(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 산출 신청전 조회-이체일에 의한(산출)_기초데이터작업(170523)
     *
     * @param pmParam 검색 조건
     * @return 산출 리턴값
     * @throws Exception
     */
    public List<Map<String, Object>> getCmsMemberByIchaeDt_Basic(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> result = null;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            DlwCmsDAO.getCmsMemberByIchaeDt_Basic(pmParam);
            result = DlwCmsDAO.getDlwWdrwHstr_Basic(pmParam);
        } else {
            DlwCmsDAO.getNiceMemberByIchaeDt(pmParam);
            result = DlwCardDAO.getCardWdrwReqList(pmParam);
        }
        return result;
    }

    /** 임시건 등록 산출
     *
     * @param pmParam 검색 조건
     * @return 산출 리턴값
     * @throws Exception
     */
    public List<Map<String, Object>> saveWdrwimsiIchaeDt_Basic(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> result = null;
        if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
            DlwCmsDAO.getCmsimsiIchaeDt_Basic(pmParam);
        } else {

            DlwCardDAO.getCardimsiIchaeDt_Basic(pmParam);
        }
        return result;
    }

    /** 산출 기초데이터 확정
    *
    * @param pmParam 검색 조건
    * @return 산출 리턴값
    * @throws Exception
    */
   public List<Map<String, Object>> getBasicWdrw_Fix(Map<String, ?> pmParam) throws Exception {
       List<Map<String, Object>> result = null;
       if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
           DlwCmsDAO.getBasicWdrwCms_Fix(pmParam);
       } else {

           DlwCardDAO.getBasicWdrwCard_Fix(pmParam);
       }
       return result;
   }

    /**
     * 대명라이프웨이 CMS 당월 출금의뢰 총액 조회_기초데이터작업(170523)
     *
     * @param pmParam 검색 조건
     * @return CMS 당월 출금의뢰 총액
     * @throws Exception
     */
    public String getDlwWdrwReqDtTtamt_Basic(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwReqDtTtamt_Basic(pmParam);
    }

    /**
     * CMS 기초데이터 수정
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateBasicAmt(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateBasicAmt(pmParam);
    }

    /**
     * 대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwHstr_Basic(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwWdrwHstr_Basic(pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 산출테이블에서 삭제시 고객만족센터 청구비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn_Basic(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateWdrwReqYn_Basic(pmParam);
    }

    /**
     * 대명라이프웨이 CMS EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCmsBasic(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteCmsBasic(pmParam);
    }

    /**
     * 대명라이프웨이 Card EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCardBasic(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.deleteCardBasic(pmParam);
    }

    /**
     * 대명라이프웨이 일괄삭제 시 산출기초테이블 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteWDRWHistoryall_Basic(Map<String, ?> pmParam) throws Exception {
        if (  !"Card".equals(pmParam.get("gubun")) ){
            return DlwCmsDAO.deleteCmsAllBasic(pmParam);
      } else {
          return DlwCmsDAO.deleteCardAllBasic(pmParam);

      }
    }

    /**
     * 대명라이프웨이 고객콜센터 청구파일 적용(기초테이블로 이동)
     *  - 기존의 updateCmsWdrwReq(), updateCardWdrwReq() 내용을 하나로 합친것
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCmsAndCardWdrwReq_Basic(Map<String, ?> mapInDs, Map<String, ?> mapInVar) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

        String msg = "";

        String gubun = (String)mapInVar.get("gubun");

        for (int i = 0; i < listInDs.size(); i++) {
            hmParam = listInDs.get(i);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", oLoginUser.getUserid());
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

            if (rowType == DataSet.ROW_TYPE_UPDATED) {
                if ("cms".equals(gubun)) {
                    DlwCmsDAO.deleteCmsWdrwReq_Basic(hmParam);
                    DlwCmsDAO.insertCmsWdrwReqByCall_Basic(hmParam);
                    DlwCmsDAO.updateWdrwReqYn(hmParam);

                } else if ("card".equals(gubun)) {
                    DlwCardDAO.deleteCardWdrwReq(hmParam);
                    DlwCardDAO.insertCardWdrwReqByCall(hmParam);
                    DlwCmsDAO.updateWdrwReqYn(hmParam);
                }
            }
        }
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getdaRefundList(pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getdaRefundDtlList(pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getdaRefundDtl1List(pmParam);
    }

    public List<Map<String, Object>> getAcquResultDataMst(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataMst(pmParam);
	}
    
	public int insertAcquResultDataMst(Map<String, ?> pmParam) throws Exception {
		return DlwCmsDAO.insertAcquResultDataMst(pmParam);
	}

	public int insertAcquResultDataDtl(Map<String, ?> pmParam) throws Exception {
		return DlwCmsDAO.insertAcquResultDataDtl(pmParam);
	}

	public int getAcquResultDataListTotalCnt(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataListTotalCnt(pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataList(pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataListTotalSummary1(pmParam);
	}
	
	public List<Map<String, Object>> getAcquResultDataListTotalSummary2(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataListTotalSummary2(pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataListExcel(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquResultDataListExcel(pmParam);
	}
	
	public int getNotAuthResultDataCount(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataCount(pmParam);
	}

	public List<Map<String, Object>> getNotAuthResultDataList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataList(pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataListTotalSummary1(pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataCancelList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataCancelList(pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataCancelListTotalSummary1(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataCancelListTotalSummary1(pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataListTotalSummary2(Map<String, ?> pmParam) {
		return DlwCmsDAO.getNotAuthResultDataListTotalSummary2(pmParam);
	}
	
	public int getAcquInicisResultDataListTotalCnt(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquInicisResultDataListTotalCnt(pmParam);
	}

	public List<Map<String, Object>> getAcquInicisResultDataList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquInicisResultDataList(pmParam);
	}

	public List<Map<String, Object>> getAcquInicisResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return DlwCmsDAO.getAcquInicisResultDataListTotalSummary1(pmParam);
	}
	
	public int getLoanPurchResultDataTotalCnt(Map<String, ?> pmParam) {
		return DlwCmsDAO.getLoanPurchResultDataTotalCnt(pmParam);
	}

	public List<Map<String, Object>> getLoanPurchResultDataList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getLoanPurchResultDataList(pmParam);
	}

	public List<Map<String, Object>> getLoanPurchResultDataListTotalSummary(Map<String, ?> pmParam) {
		return DlwCmsDAO.getLoanPurchResultDataListTotalSummary(pmParam);
	}
	
    public void loanPurchResultUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        int fileCnt	= 0;
        
        //XSSFWorkbook workbook = null; // 엑셀파일 워크북 객체 생성 .xlsx
        //XSSFSheet sheet = null; // 시트 지정 .xlsx
        //XSSFRow xrow = null; // 로우 .xlsx
        //XSSFCell xcell = null; // cell .xlsx
        
        HSSFWorkbook workbook = null; // 엑셀파일 워크북 객체 생성 .xls
        HSSFSheet sheet = null; // 시트 지정 .xls
        HSSFRow xrow = null; // 로우 .xls
        HSSFCell xcell = null; // cell .xls
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Map<String,Object>> lst = new ArrayList<>();
        Map<String,Object> mRow = new HashMap<String, Object>();

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try 
        {
            // 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
            	ParamUtils.addSaveParam(hmParam);
            	
                fileCnt++;
                sKey = (String)enm.nextElement();

                log.debug("sKey : " + sKey);

                File upfile = multipartRequest.getFile(sKey);
                String sUpFileName = upfile.getName();
                String strRegman = hmParam.get("rgsr_id").toString();
                String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
                String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
                String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
                String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");
                
                String sReqDay = "";
                
                String sqlStatement = "INSERT INTO LF_DMUSER.TB_ACQU_RSLT_DTL \n";
                sqlStatement += "(MST_SERIAL_NUM, DATA_GB, DATA_TID, DATA_FRAN_ID, DATA_TRAD_GB, DATA_TRAD_DTTM, DATA_PURC_DTTM, DATA_APPY_NUM, DATA_PURC_CRCO, DATA_CARD_NUM, DATA_CARD_GB, \n";
                sqlStatement += "DATA_PURC_PAY, DATA_FRAN_FEE, PURCH_CMIS_POINT, DATA_ETC_FEE, PURCH_CMIS_TOT, DATA_AMOT_DTTM, ACCNT_NO, RGSR_ID, RGSR_DTTM, DEL_FG, PRE_TRAD_DTTM) \n";
          	    sqlStatement += "VALUES ( 99999, 'P', ?, 'purch000m', ?, ?, ?, ?, ?, ?, ?, \n";
          	    sqlStatement += "TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), ?, '', ?, SYSDATE, 'N',?) \n";
          	    
          	    Class.forName(sAccessClassName);
                connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(sqlStatement);

                log.debug("upfile.length() : " + upfile.length());

                if (upfile.exists()) 
                {
                    log.debug("file exists");
                } 
                else 
                {
                    log.debug("file does not exists");
                }

                //workbook = new XSSFWorkbook(new FileInputStream(upfile)); // 엑셀파일 워크북 객체 생성 .xlsx
                workbook = new HSSFWorkbook(new FileInputStream(upfile)); // 엑셀파일 워크북 객체 생성 .xls

                sheet = workbook.getSheetAt(0);

                int rows = sheet.getPhysicalNumberOfRows();

                if (rows > 50005)
                {
                    throw new EgovBizException("업로드정보가 50000건이 넘습니다. 전산팀에 업로드 요청 부탁드립니다.!!");
                }

                for (int rIdx = 0; rIdx < rows; rIdx++)
                {
                    xrow = sheet.getRow(rIdx);
                    int cells = xrow.getPhysicalNumberOfCells();

                    if (rIdx < 1)
                    {
                        continue;
                    }

                    for(int cIdx = 0; cIdx < cells; cIdx++)
                    {
                        xcell = xrow.getCell(cIdx);

                        String value = "";

                        switch (xcell.getCellType())
                        {
                            case Cell.CELL_TYPE_BOOLEAN:
                                value = "" + xcell.getBooleanCellValue();
                            break;
                            
                            case Cell.CELL_TYPE_NUMERIC:
                                value = "" + xcell.getNumericCellValue();
                            break;
                            
                            case Cell.CELL_TYPE_STRING:
                                value = "" + xcell.getStringCellValue();
                            break;

                            case Cell.CELL_TYPE_BLANK:
                                value = " ";
                            break;

                            default:
                                value = "" + xcell.getStringCellValue();
                            break;
                        }

                        if (null != value)
                        {
                            value = value.trim();
                        }

                        switch (cIdx)
                        {

                            case 1:
                            	mRow.put("data_trad_dttm", value);
                            break;
                            
                            case 2:
                            	mRow.put("data_purc_dttm", value);
                            break;
                            
                            case 3:
                            	mRow.put("data_appy_num", value);
                            break;
                            
                            case 4:
                            	if(value.equals("KB카드"))
                            	{
                            		value = "02";
                            	}
                            	else if(value.equals("농협NH카드"))
                            	{
                            		value = "12";
                            	}
                            	else if(value.equals("롯데카드"))
                            	{
                            		value = "08";
                            	}
                            	else if(value.equals("비씨카드"))
                            	{
                            		value = "01";
                            	}
                            	else if(value.equals("삼성카드"))
                            	{
                            		value = "04";
                            	}
                            	else if(value.equals("신한카드"))
                            	{
                            		value = "06";
                            	}
                            	else if(value.equals("하나카드"))
                            	{
                            		value = "16";
                            	}
                            	else if(value.equals("우리카드"))
                            	{
                            		value = "15";
                            	}
                            	else if(value.equals("씨티카드"))
                            	{
                            		value = "10";
                            	}
                            	else if(value.equals("외환카드"))
                            	{
                            		value = "03";
                            	}
                            	else
                            	{
                            		value = "";
                            	}
                            	mRow.put("data_purc_crco", value); //
                            break;

                            case 6:
                            	mRow.put("data_card_num", value);
                            break;
                            
                            case 7:
                            	if(value.equals("신용카드"))
                            	{
                            		value = "0";
                            	}
                            	else if(value.equals("체크카드"))
                            	{
                            		value = "1";
                            	}
                            	else if(value.equals("기타"))
                            	{
                            		value = "2";
                            	}
                            	else
                            	{
                            		value = "2";
                            	}
                            	mRow.put("data_card_gb", value); //
                            break;
                            
                            case 8:
                            	mRow.put("data_purc_pay", value);
                            break;
                            
                            case 9:
                            	mRow.put("data_fran_fee", value);
                            break;
                            
                            case 10:
                            	mRow.put("purch_cmis_point", value);
                            break;
                            
                            case 12:
                            	mRow.put("data_etc_fee", value);
                            break;
                            
                            case 13:
                            	mRow.put("purch_cmis_tot", value);
                            break;
                            
                            case 16:
                            	mRow.put("data_amot_dttm", value);
                            break;

                            default:

                            break;
                        }
                    }
                    
                    if(rIdx > 2)
                    {
                    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    	Calendar cal = Calendar.getInstance();
                    	
                    	String sRawDataTradDttm = mRow.get("data_purc_dttm").toString().replace("-", "");
                    	Date date = format.parse(sRawDataTradDttm);
                    	cal.setTime(date);
                    	cal.add(Calendar.DATE, -1); 
                    	
                    	String sDataTradDttm = format.format(cal.getTime());
                    	String sDataPreTradDttm = mRow.get("data_trad_dttm").toString().replace("-", "");
                    	String sDataPurcDttm = mRow.get("data_purc_dttm").toString().replace("-", "");
                    	String sDataAppyNum = mRow.get("data_appy_num").toString();
                    	String sDataPurcCrco = mRow.get("data_purc_crco").toString();
                    	String sDataCardNum = mRow.get("data_card_num").toString().replace("-", "");
                    	String sDataCardGb = mRow.get("data_card_gb").toString();
                    	String sDataPurcPay = mRow.get("data_purc_pay").toString().replace(",", "");
                    	String sDataFranFee = mRow.get("data_fran_fee").toString().replace(",", "");
                    	String sPurchCmisPoint = mRow.get("purch_cmis_point").toString().replace(",", "");
                    	String sDataEtcFee = mRow.get("data_etc_fee").toString().replace(",", "");
                    	String sPurchCmisTot = mRow.get("purch_cmis_tot").toString().replace(",", "");
                    	String sDataAmotDttm = mRow.get("data_amot_dttm").toString().replace("-", "");
                    	String sDataTID = "purch0" + "06" + sDataTradDttm + sDataAppyNum + sDataCardNum.substring(0, 6);
                    	String sDataTradGb = sDataPurcPay.indexOf('-') >= 0 ? "2" : "0";
                    	                    	
                    	//청구일 파라미터 생성 
                    	sReqDay = sDataTradDttm;
                    	
                    	if(!sDataPurcCrco.equals("04") && !sDataPurcCrco.equals("06")) // 삼성카드와 신한카드는 제외한다.
                    	{
                    	    //preparedStatement.setString(1, sDataTID);
                    		preparedStatement.setString(1, "NOT CONNECT");
                    	    preparedStatement.setString(2, sDataTradGb);
                    	    preparedStatement.setString(3, sDataTradDttm);
                    	    preparedStatement.setString(4, sDataPurcDttm);
                    	    preparedStatement.setString(5, sDataAppyNum);
                    	    preparedStatement.setString(6, sDataPurcCrco);
                    	    preparedStatement.setString(7, sDataCardNum);
                    	    preparedStatement.setString(8, sDataCardGb);
                    	    preparedStatement.setString(9, sDataPurcPay);
                    	    preparedStatement.setString(10, sDataFranFee);
                    	    preparedStatement.setString(11, sPurchCmisPoint);
                    	    preparedStatement.setString(12, sDataEtcFee);
                    	    preparedStatement.setString(13, sPurchCmisTot);
                    	    preparedStatement.setString(14, sDataAmotDttm);
                    	    preparedStatement.setString(15, strRegman);
                    	    preparedStatement.setString(16, sDataPreTradDttm);
                    	    
                    	    log.debug("입력되는 데이터 ::: " + mRow.toString());
                            
                            preparedStatement.addBatch(); // addBatch에 담기
                            preparedStatement.clearParameters(); // 파라미터 Clear
                    	}
                    }
                    
                    if( (rIdx % 1000) == 0)
                    {
                        preparedStatement.executeBatch(); // Batch 실행
                        preparedStatement.clearBatch(); // Batch 초기화
                        connection.commit(); // 커밋
                    }
                }
                
                preparedStatement.executeBatch() ;
                connection.commit() ;
                preparedStatement.close();
                connection.close();
                upfile.delete();
                
                //String sUploadFileName = new String(sUpFileName.getBytes("iso-8859-1"), "utf-8");
                //hmParam.put("file_name", sUpFileName);
                
                hmParam.put("req_day",sReqDay);
                
                DlwCmsDAO.updateLoanPurchResultAccntNoRealTID(hmParam);
                //DlwCmsDAO.insertPurchResultMst(hmParam);
                
                mResuslt.put("ErrorCode", "1");
                mResuslt.put("ErrorMsg", "업로드가 완료되었습니다.");
            }

            if (fileCnt < 1)
            {
                throw new EgovBizException("업로드된 파일이 없습니다.");
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            mResuslt.put("ErrorCode", "-1");
            mResuslt.put("ErrorMsg", ex.getMessage());
            throw ex;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            mResuslt.put("ErrorCode", "-1");
            mResuslt.put("ErrorMsg", ex.getMessage());
            throw ex;
        }
        finally
        {

        }
    }

	public int updateLoanPurchResultAccntNoRealTID(Map<String, Object> pmParam)throws Exception {
		return DlwCmsDAO.updateLoanPurchResultAccntNoRealTID(pmParam);
	}

	public List<Map<String, Object>> getMemberWdrwResultAuthList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getMemberWdrwResultAuthList(pmParam);
	}

	public List<Map<String, Object>> getMemberWdrwResultCancelList(Map<String, ?> pmParam) {
		return DlwCmsDAO.getMemberWdrwResultCancelList(pmParam);
	}

	public int updateLoanPurchMemberWdrwResultAuth(Map<String, Object> pmParam) throws Exception {
		return DlwCmsDAO.updateLoanPurchMemberWdrwResultAuth(pmParam);
	}
	
	public int updateLoanPurchMemberWdrwResultCancel(Map<String, Object> pmParam) throws Exception {
		return DlwCmsDAO.updateLoanPurchMemberWdrwResultCancel(pmParam);
	}
	
    /**
     *CMSEB11데이터조회
     *
     * @param pmParam 검색 조건
     * @return  CMS EB11
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsEb11List(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.getDlwCmsEb11List(pmParam);
    }
    
    /**
     *  EB11정보 등록 및 CMS결제정보 수정
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public String insertDlwCmsDataInsert(Map<String, Object> pmParam) throws Exception {
    	 DlwCmsDAO.insertDlwCmsDataInsert(pmParam); // CMS 정보등록
    	 return "";
    }
    
    /**
     *  EB11 CMS결제정보이력저장
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public String insertDlwCmsHistoryInsert(Map<String, Object> pmParam) throws Exception {
    	 DlwCmsDAO.insertDlwCmsHistoryInsert(pmParam); // CMS 정보등록
    	 return "";
    }
    
    /**
     *  EB11 CMS결제정보변경
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public int updateDlwCmsInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCmsDAO.updateDlwCmsInfo(pmParam);
    }

}
