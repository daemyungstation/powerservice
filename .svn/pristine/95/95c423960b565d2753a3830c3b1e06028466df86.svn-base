/*
 * (@)# DlwNewTypePayMngService.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;                            // 2018.11.01 추가
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicepay.module.lite.NicePayWebConnector;   // 2018.11.01 추가

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwNewTypePayMngService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.NicePayProcess;         // 2018.11.01 추가
import powerservice.core.util.ParamUtils;
import au.com.bytecode.opencsv.CSVReader;

import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;


/**
 * 산출정보를 관리한다
 */
@Service
public class DlwNewTypePayMngServiceImpl extends EgovAbstractServiceImpl implements DlwNewTypePayMngService {

    @Resource
    public DlwNewTypePayMngDAO DlwNewTypePayMngDAO;

    @Resource
    public BasVlService basVlService;


    private final Logger log = LoggerFactory.getLogger(DlwNewTypePayMngServiceImpl.class);

    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no) {
        return DlwNewTypePayMngDAO.getWdrwReqAccntConfirm(accnt_no);
    }
    
    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMng(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Map<String, Object>> accntNoList = new ArrayList();
        List<Map<String, Object>> statList = new ArrayList();

        String mode = (String)pmParam.get("mode");
        String dcAmtSeq = "0";
        String updateDcAmtSeq = "0";

        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        int exprNo = Integer.valueOf(String.valueOf(pmParam.get("expr_no"))).intValue();
        int sunNapCnt = 0;

        if(pmParam.get("paramHm") != null) {
            paramMap = (HashMap)pmParam.get("paramHm");
            if(paramMap.get("accntAC") != null) {
                accntNoList = (ArrayList)paramMap.get("accntAC");
            }
        } else if(pmParam.get("accntAC") != null) {
            accntNoList = (ArrayList)pmParam.get("accntAC");
        }
        if("update".equals(mode)) {
            if(!"Y".equals((String)pmParam.get("del_fg"))) {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwNewTypePayMngDAO.mergePayMng(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);
                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwNewTypePayMngDAO.insertReqUpdDelInf(pmParam);
                }
            }
        }

        if(accntNoList == null || accntNoList.size() == 0) {
            List<Map<String, Object>> dcAmtCntList = DlwNewTypePayMngDAO.getDCAmtCnt(pmParam);
            if(dcAmtCntList.size() > 0) {
                Map<String, Object> dcAmtCntParam = (Map<String, Object>)dcAmtCntList.get(0);
                sunNapCnt = 0;
                if (null != dcAmtCntParam.get("dc_amt_cnt")) {
                    sunNapCnt =  Integer.parseInt(String.valueOf(dcAmtCntParam.get("dc_amt_cnt")));
                }
                dcAmtCntParam.put("accnt_no", pmParam.get("accnt_no"));
                dcAmtCntParam.put("pay_day", pmParam.get("pay_day"));
                DlwNewTypePayMngDAO.deleteDCAmt(dcAmtCntParam);
            }
        }
        for(int i = no; i < no + monCnt; i++) {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            if(i == 1) {
                pmParam.put("join_dt", pmParam.get("pay_day"));
                DlwNewTypePayMngDAO.updateJoinDt(pmParam);
                //MemProdAccntDAO.updateJoinDt(pmParam);
            }
            if(accntNoList.size() == 0) {
                payMngParam.put("accnt_no", (String)pmParam.get("accnt_no"));
            } else {
                HashMap accntNoMap = (HashMap)accntNoList.get(0);
                payMngParam.put("accnt_no", (String)accntNoMap.get("accnt_no"));
            }
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", (String)pmParam.get("pay_day"));
            if("N".equals((String)pmParam.get("del_fg"))) {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwNewTypePayMngDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        for(int j = 0; j < statList.size(); j++) {
            Map<String, Object> statMap = statList.get(j);
            if(!"2".equals(statMap.get("stat"))) {
                sunNapCnt++;
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("dc_amt_seq", dcAmtSeq);
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));
        if(accntNoList.size() == 0) {
        	DlwNewTypePayMngDAO.updateDCAmtSeq(payMngParam);
        }

        if("insert".equals(mode)) {
            if(accntNoList.size() == 0) {
                if(statList.size() > 0) {
                    for(int j = 0; j < statList.size(); j++) {
                        Map<String, Object> statMap = statList.get(j);
                        payMngParam.put("no", statMap.get("no"));
                        payMngParam.put("stat", statMap.get("stat"));

                        payMngParam.put("mode", mode);
                        DlwNewTypePayMngDAO.mergePayMng(payMngParam);
                    }

                }
            } else {
                for(int i = 0; i < accntNoList.size(); i++) {
                    HashMap accntNoMap = (HashMap)accntNoList.get(i);
                    payMngParam.put("accnt_no", (String)accntNoMap.get("accnt_no"));
                    for(int j = 0; j < statList.size(); j++) {
                        Map<String, Object> statMap = statList.get(j);
                        payMngParam.put("no", statMap.get("no"));
                        payMngParam.put("stat", statMap.get("stat"));

                        payMngParam.put("mode", mode);
                        DlwNewTypePayMngDAO.mergePayMng(payMngParam);
                    }
                }
            }
        }
    }
    
    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMngDtl(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        String mode = (String)pmParam.get("mode");
        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        List<Map<String, Object>> statList = new ArrayList();
        List<Map<String, Object>> accntNoList = new ArrayList();
        if("update".equals(mode))
        {
            if(!"Y".equals((String)pmParam.get("del_fg")))
            {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwNewTypePayMngDAO.mergePayMngDtl(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);

                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwNewTypePayMngDAO.insertReqUpdDelInf(pmParam);
                }
            }

        }
        for(int i = no; i < no + monCnt; i++)
        {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            payMngParam.put("accnt_no", pmParam.get("accnt_no"));
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", pmParam.get("pay_day"));

            if("N".equals((String)pmParam.get("del_fg")))
            {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwNewTypePayMngDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("seq", pmParam.get("seq"));
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));

        if("insert".equals(mode) && accntNoList.size() == 0)
        {
            for(int j = 0; j < statList.size(); j++)
            {
                Map<String, Object> statMap = new HashMap<String, Object>();
                statMap = statList.get(j);
                payMngParam.put("no", statMap.get("no"));
                payMngParam.put("stat", statMap.get("stat"));

                payMngParam.put("mode", mode);
                DlwNewTypePayMngDAO.mergePayMngDtl(payMngParam);
            }

        }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMngDtl1(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        String mode = (String)pmParam.get("mode");
        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        List<Map<String, Object>> statList = new ArrayList();
        List<Map<String, Object>> accntNoList = new ArrayList();
        if("update".equals(mode))
        {
            if(!"Y".equals((String)pmParam.get("del_fg")))
            {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwNewTypePayMngDAO.mergePayMngDtl1(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);

                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwNewTypePayMngDAO.insertReqUpdDelInf(pmParam);
                }
            }
        }
        for(int i = no; i < no + monCnt; i++)
        {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            payMngParam.put("accnt_no", pmParam.get("accnt_no"));
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", pmParam.get("pay_day"));

            if("N".equals((String)pmParam.get("del_fg")))
            {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwNewTypePayMngDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("seq", pmParam.get("seq"));
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));

        if("insert".equals(mode) && accntNoList.size() == 0)
        {
            for(int j = 0; j < statList.size(); j++)
            {
                Map<String, Object> statMap = new HashMap<String, Object>();
                statMap = statList.get(j);
                payMngParam.put("no", statMap.get("no"));
                payMngParam.put("stat", statMap.get("stat"));

                payMngParam.put("mode", mode);
                DlwNewTypePayMngDAO.mergePayMngDtl1(payMngParam);
            }
        }
    }
    
    /**
     * 대명라이프웨이 입금 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return prod_cl
     * @throws Exception
     */
    public String getpayNewYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getpayNewYnChk(pmParam);

    }
    
    /**
     * 대명라이프웨이 부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return  부가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getBugaInfo(pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 추가 부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayAmtDtl1ByPayCnt(pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayAmtDtlByPayCnt(pmParam);
    }
    
    /**
     *  납입건수에 따른 납입금액 계산
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayAmtByPayCnt(pmParam);
    }
    
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) DlwNewTypePayMngDAO.getDlwCustAcntCount(pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getDlwCustAcntList(pmParam);
    }
    
    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 할부금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngDtlBugaInfo(pmParam);
    }
    
    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 추가금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngDtl1BugaInfo(pmParam);
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getRefundList(pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getRefundDtlList(pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getRefundDtl1List(pmParam);
    }
    
    /**
     *  입금전표조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngList(pmParam);
    }

    /**
     *  입금전표조회-결합상품할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngDtlList(pmParam);
    }

    /**
     *  입금전표조회-결합상품추가부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngDtl1List(pmParam);
    }
    
    /**
     *  입금전표조회-총금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayTotalList(pmParam);
    }

    /**
     *  입금전표조회-상품정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getProductDtl(pmParam);
    }

    /**
     * 회원번호로 상품코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getProdCdByAccntNo(pmParam);
    }
    
    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypePayMngDAO.getPayMngBugaInfo(pmParam);
    }
}