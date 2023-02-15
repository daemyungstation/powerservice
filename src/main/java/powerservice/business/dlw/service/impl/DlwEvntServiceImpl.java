/*
 * (@)# DlwEvntService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
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

package powerservice.business.dlw.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.DmWebSenderService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Service
public class DlwEvntServiceImpl extends EgovAbstractServiceImpl implements DlwEvntService {

    @Resource
    public DlwEvntDAO DlwEvntDAO;

    @Resource
    public DmWebSenderService dmWebSenderService;
    
    @Resource
    public BasVlService basVlService;

    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public int getEventCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEventCount(pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEventList(pmParam);
    }

    /**
     * 대명라이프웨이 행사통계
     *
     * @param pmParam 검색 조건
     * @return 행사 통계
     * @throws Exception
     */
    public List<Map<String, Object>> getEventStats(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEventStats(pmParam);
    }

    /**
     * 대명라이프웨이 행사 상세 정보
     *
     * @param pmParam 검색 조건
     * @return  행사 상세 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDetailEvent(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getDetailEvent(pmParam);
    }

    /**
     * 대명라이프웨이 행사 매니저 등록? 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtMngrRegList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtMngrRegList(pmParam);
    }

    /**
     * 대명라이프웨이 CP 직영, 외주 구분 조회
     *
     * @param pmParam 검색 조건
     * @return CP 직영, 외주 구분
     * @throws Exception
     */
    public String getCpGubun(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCpGubun(pmParam);
    }

    /**
     * 대명라이프웨이 직영 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirMst(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례용품) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirGdsList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(현장발주) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirGdsListB(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례식장) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirGdsListC(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(협력업체) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirGdsListD(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(기타(용품/인력)) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirGdsListE(pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출1 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirAddSale1List(pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirAddSale2List(pmParam);
    }

    /**
     * 대명라이프웨이 직영 일반경비 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirNormlCostList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirPayList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirPayList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptDirPayInfo(pmParam);
    }

    /**
     * 대명라이프웨이 외주 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutMst(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutDtl1List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutDtl2List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutDtl4List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutDtl5List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRptOutPayInfo(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhMvYn(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getWhMvYn(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtCash(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getPayAmtCash(pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getFunrlHallCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getFunrlHallCount(pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFunrlHallList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getFunrlHallList(pmParam);
    }

    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvent(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvent(pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvent(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEvent(pmParam);
    }

    /**
     * 대명라이프웨이 마감데이터 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCloseDataSaveYnChk(pmParam);
    }

    /**
     * 대명라이프웨이 행사순번 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtSeq(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtSeq(pmParam);
    }

    /**
     * 대명라이프웨이 취소 내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCnclReg(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertCnclReg(pmParam);
    }

    /**
     * 대명라이프웨이 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getEventCheck(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEventCheck(pmParam);
    }

    /**
     * 대명라이프웨이 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnCheck(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getResnCheck(pmParam);
    }

    /**
     * 대명라이프웨이 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getTaxtCheck(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTaxtCheck(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsCheck(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCmsCheck(pmParam);
    }

    /**
     * 대명라이프웨이 해약 시 CMS청구내역 신청전,신청중 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCmsReqCnt(pmParam);
    }

    /**
     * 대명라이프웨이 상품구분 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getProdCl(pmParam);
    }

    /**
     * 대명라이프웨이 jointype 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getJoinType(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getJoinType(pmParam);
    }

    /**
     * 대명라이프웨이 입금 총납입부금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntSearch(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAccntSearch(pmParam);
    }

    /**
     * 대명라이프웨이 행사 삭제 (MEM_PROD_ACCNT)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvent(Map<String, ?> pmParam) throws Exception {
        int sCnt = DlwEvntDAO.deleteEvent(pmParam);
        /*if (sCnt > 0) {
            //엔컴DB 행사 삭제후 인우 MEM_PROD_ACCNT 테이블수정
            MemProdAccntDAO.deleteEvent(pmParam);
        }*/

        // 행사 삭제 후 의전행사 사진 삭제
        DlwEvntDAO.deleteEvtPicByEventSeq(pmParam);

        return sCnt;
    }

    /**
     * 대명라이프웨이 행사 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEvtNewYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtNewYnChk(pmParam);
    }

    /**
     * 대명라이프웨이 행사보고서 생성여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getIsEvtRptGen(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getIsEvtRptGen(pmParam);
    }

    /**
     * 대명라이프웨이 행사/해약 시 결합상품 할부원금 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getCheckB2bPay(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCheckB2bPay(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getDlwManrCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getDlwManrCount(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwMngrList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getDlwMngrList(pmParam);
    }

    /**
     * 거래처 장례식장 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getFunrCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getFunrCount(pmParam);
    }

    /**
     * 거래처 장례식장 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFunrList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getFunrList(pmParam);
    }

    /**
     * 거래처 매입업체 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCustmrPopCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCustmrPopCount(pmParam);
    }

    /**
     * 거래처 매입업체 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustmrPopList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCustmrPopList(pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCustmrOutCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCustmrOutCount(pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustmrOutList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCustmrOutList(pmParam);
    }

    /**
     * 행사자 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateevntMngr(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateevntMngr(pmParam);
    }

    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertevntMngr(Map<String, ?> pmParam) throws Exception {
    	int retCnt = 0;
    	//행사자 등록
    	DlwEvntDAO.insertevntMngr(pmParam);

    	//행사자 등록 중에 지도사(cp)가 등록되면 APP 푸시 알림

    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > retCnt : " + retCnt);
    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > pmParam.get(job_duty) : " + pmParam.get("job_duty"));

    	if (pmParam.get("job_duty").equals("0001")){
    		sndPushAlertMain(pmParam);
    	}

        return retCnt;
    }

    /**
     * 대명라이프웨이 행사자 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntMngrList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntMngrList(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 기초 수당 자료 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntMngrPayList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntMngrPayList(pmParam);
    }

    /**
     * 행사자 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteRowMngr(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteRowMngr(pmParam);
    }

    /**
     * 품목 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getGoodsCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGoodsCount(pmParam);
    }

    /**
     * 품목 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGoodsList(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab1JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab1JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab2JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab2JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab3JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab3JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab4JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab4JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab5JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab5JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반 경비 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab6JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab6JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab6(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab6(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab7JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab7JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab7(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab7(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab8JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab8JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab8(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab8(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTab9JikList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getTab9JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJiktab9(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateJiktab9(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertJiktab9(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertJiktab9(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab6(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab7(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab8(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsTab9(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsTab9(pmParam);
    }

    /**
     * 카드 수수료 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCardFeeList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCardFeeList(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 관련 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsOutds1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsOutds2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsOutds3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGoodsOutds4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteGoodsOutds4(pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getAnnlCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAnnlCount(pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAnnlList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAnnlList(pmParam);
    }

    /**
     * 지부 콤보박스 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBranchList(pmParam);
    }

    /**
     * 행사정산 입금정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayData(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntPayData(pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntPayDtl(pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회(회차별)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntPayDtlCnt(pmParam);
    }

    /**
     * 행사보고서(직영) 상세 조회(입금정보 포함)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntRptMagam(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEvntRptMagam(pmParam);
    }

    /**
     * 행사보고서(직영) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntOutRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvntOutRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEvntOutRptDtl(pmParam);
    }


    /**
     * 행사보고서(외주) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEvntOutRptDtlMagam(pmParam);
    }

    /**
     * 행사보고서(외주) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteEvntOutRptDtl(pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertUpdBitHis(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertUpdBitHis(pmParam);
    }

    /**
     * 회원상태 변경(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemprodKstbit(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateMemprodKstbit(pmParam);
    }

    /**
     * 회원상태 변경(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateMemprodKstbit_cncl(pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertUpdBitHis_cncl(pmParam);
    }

    /**
     * 사업장내 창고이동 물품리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMoveGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMoveGoodsList(pmParam);
    }

    /**
     * 사업장내 창고이동 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMoveGoodsMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMoveGoodsMst(pmParam);
    }

    /**
     * 창고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhmvNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getWhmvNo(pmParam);
    }

    /**
     * 입고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhinNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getWhinNo(pmParam);
    }

    /**
     * 출고 창고코드 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhcd(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getWhcd(pmParam);
    }

    /**
     * 출고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getWhoutNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getWhoutNo(pmParam);
    }

    /**
     * 창고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhmvNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSinWhmvNo(pmParam);
    }

    /**
     * 입고번호 신규생성
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhinNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSinWhinNo(pmParam);
    }

    /**
     * 출고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinWhoutNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSinWhoutNo(pmParam);
    }

    /**
     * 출고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhOutMst(pmParam);
    }

    /**
     * 입고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhinMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhinMst(pmParam);
    }

    /**
     * 창고이동 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhmvMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhmvMst(pmParam);
    }

    /**
     * 출고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhOutDtl(pmParam);
    }

    /**
     * 입고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhinDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhinDtl(pmParam);
    }

    /**
     * 창고이동 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertWhmvDtl(pmParam);
    }

    /**
     * 창고이동 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhmvMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhmvMst(pmParam);
    }

    /**
     * 창고이동 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhmvDtl(pmParam);
    }

    /**
     * 출고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhOutMst(pmParam);
    }

    /**
     * 출고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhOutDtl(pmParam);
    }

    /**
     * 입고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhinMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhinMst(pmParam);
    }

    /**
     * 입고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWhinDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteWhinDtl(pmParam);
    }

    /**
     * 입금마감 팝업 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getPayTotalList(pmParam);
    }

    /**
     * 장법 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal1(pmParam);
    }

    /**
     * 신규유지 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal2(pmParam);
    }

    /**
     * 지부 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal3(pmParam);
    }

    /**
     * 지역 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal4(pmParam);
    }

    /**
     * 직영CP 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal5(pmParam);
    }

    /**
     * 종교 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntAnal6(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntAnal6(pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회건수)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getAnnlCnclCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAnnlCnclCount(pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회리스트)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAnnlCnclList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAnnlCnclList(pmParam);
    }

    /**
     * 지부별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntCncl1(pmParam);
    }

    /**
     * 취소사유별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntCncl2(pmParam);
    }

    /**
     * 소요시간별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntCncl3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntCncl3(pmParam);
    }


    /**
     * 물품 및 서비스제공현황 조회 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEventSrvProd1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEventSrvProd1(pmParam);
    }

    /**
     * CP등록 여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getEvntCpChkCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntCpChkCount(pmParam);
    }

    /**
     * 입금마감 - 상조부금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymng(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvntPaymng(pmParam);
    }

    /**
     * 입금마감 - 결합금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymngDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvntPaymngDtl(pmParam);
    }

    /**
     * 입금마감 - 추가금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertEvntPaymngDtl1(pmParam);
    }

    /**
     * 의전행사 사진 분류 - 행사회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPicSort(Map<String, Object> pmParam) throws Exception {
        return DlwEvntDAO.getEvntPicSort(pmParam);
    }

    /**
     * 의전행사 사진 분류 - 분류/미분류회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntPicList(Map<String, Object> pmParam) throws Exception {
        return DlwEvntDAO.getEvntPicList(pmParam);
    }


    /**
     * 선택파일 미분류로 이동
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int disconncectAccnt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {

                for (int i=0; i<listInDs.size(); ++i) {
                    Map hmParam = listInDs.get(i);
                    if ("1".equals((String)hmParam.get("chk"))) {
                        ParamUtils.addSaveParam(hmParam);
                        iCudCnt += DlwEvntDAO.disconncectAccnt(hmParam);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 선택파일 회원연결
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int conncectAccnt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        Map hmParam = null;
        Map mCond = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsCond 	= (DataSetMap)mapInDs.get("ds_input2");

            System.out.println("listInDs.size() :" + listInDs.size());
            System.out.println("dsCond.size() :" + dsCond.size());

            if (listInDs != null && listInDs.size() > 0 && null != dsCond && dsCond.size() > 0) {
                mCond = dsCond.get(0);
                String accntNo = CommonUtils.nvls((String)mCond.get("accnt_no"));

                CommonUtils.printLog(mCond);
                System.out.println("accntNo :" + accntNo);

                if (!"".equals(accntNo)) {
                    for (int i=0; i<listInDs.size(); ++i) {
                        hmParam = listInDs.get(i);
                        if ("1".equals((String)hmParam.get("chk"))) {

                            ParamUtils.addSaveParam(hmParam);
                            hmParam.put("accnt_no",  accntNo);

                            iCudCnt += DlwEvntDAO.conncectAccnt(hmParam);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 행사사진 삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteEvtPic(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                for (int i=0; i<listInDs.size(); ++i) {
                    Map hmParam = listInDs.get(i);
                    System.out.println(i + " : (String)hmParam.get('chk') : " + (String)hmParam.get("chk"));
                    if ("1".equals((String)hmParam.get("chk"))) {
                        ParamUtils.addSaveParam(hmParam);
                        iCudCnt += DlwEvntDAO.deleteEvtPic(hmParam);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 행사사진 다운로드 파일 압축
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public List<Map<String, Object>> evtPicBatchDownload(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map<String, Object> hmParam;
        Map<String, Object> mRow = new HashMap<String,Object>();

        List<Map<String, Object>> lstRet = new ArrayList<>();

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List lst = DlwEvntDAO.getEvntPicSort(hmParam);

//                FileZipCompress fzc = new FileZipCompress();
//                for (int i = 0; i < lst.size(); i++) {
//                    HashMap obj = (HashMap)lst.get(i);
//
//                    // TODO : 컬럼명 소문자인지 확ㅇ니 필요...
//                    fzc.generateFileList(new File(FileZipCompress.SOURCE_FOLDER + (String)obj.get("pic_file_nm")));
//                }
//
//                // TODO : 화면에서 emple_no를 보내줘야 함 - 대명홈페이지 쪽에는 세션이 없음
//                int result = fzc.zipIt((String)hmParam.get("emple_no"), (String)hmParam.get("accnt_no"));
//
//                if (result == 1) {
//                    mRow.put("result", "success");
//                }
//                else if (result == 3) {
//                    mRow.put("result", "noFile");
//                }
//                else {
//                    mRow.put("result", "fail");
//                }
//
//                mRow.put("file_nm", (String)hmParam.get("emple_no") + "_" + (String)hmParam.get("accnt_no") + ".zip");
//                mRow.put("dir", FileZipCompress.OUTPUT_ZIP_FILE);
//
//                lstRet.add(mRow);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstRet;
    }

    /**
     * 의전행사 사진 등록
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int insertEvtPicInfo(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                iCudCnt += DlwEvntDAO.insertEvtPicInfo(hmParam);
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

	public void saveEvenPhotoUpLoad(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String tempDir = System.getProperty("java.io.tmpdir");
        //LOGGER.info("---uploadToSms 서비스 - 1");

        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        int fileCnt	= 0;

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {
        	// 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
            	fileCnt++;
                sKey = (String)enm.nextElement();

                SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
                String ts = formatter.format(new Date());

                System.out.println( " Timestamp : " + ts);

                File upfile = multipartRequest.getFile(sKey);
                String sUpFileName = upfile.getName();
                //String sServerSaveImgFileNm = request.getParameter("file_nm");
                System.out.println("sUpFileName ::: " + sUpFileName);

                if (upfile.exists())
                {
                	String sAccntNo = request.getParameter("accnt_no");
                	String sEmpleNo = request.getParameter("emple_no");
                	String sCpCell  = request.getParameter("cp_cell");
                	String sPicClCd = request.getParameter("pic_cl_cd");
                	String sMemNo   = "";
                	String sPicFileNm = "";

                	hmParam.put("accnt_no", sAccntNo);
                	hmParam.put("emple_no", sEmpleNo);
                	hmParam.put("cp_cell", sCpCell);
                	hmParam.put("pic_cl_cd", sPicClCd);
                	hmParam.put("upload_dt", ts);

                	ParamUtils.addSaveParam(hmParam);

                	List<Map<String, Object>> mListMemNo =  DlwEvntDAO.getAccntMemNo(hmParam);
                	sMemNo = (String)mListMemNo.get(0).get("mem_no");

                	sPicFileNm =  ts + "_" + sAccntNo + "_" + sMemNo + "_" + sPicClCd + ".jpg";

                	hmParam.put("pic_file_nm", sPicFileNm);

                	String sBasePath = "";

                    String osName = System.getProperty("os.name").toLowerCase();
                    if (osName.indexOf("windows") >= 0)
                    {
                    	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.windows.basePath");
                    }
                    else
                    {
                    	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.unix.basePath");
                    }

                    File sParentFilePathName = new File(sBasePath + "/" + sPicFileNm.substring(0, 6));
                    String sFullFilePathName = sBasePath + "/" + sPicFileNm.substring(0, 6) + "/" + sPicFileNm;
                    if (osName.indexOf("windows") >= 0)
                    {
                    	sFullFilePathName = sFullFilePathName.replaceAll("/", "\\\\");
                    }

                    if(sParentFilePathName.exists() == false)
                    {
                    	sParentFilePathName.mkdirs();
                    }

                	FileInputStream fis = new FileInputStream(upfile);
                	BufferedInputStream bis = new BufferedInputStream(fis);

                	FileOutputStream fos = new FileOutputStream(sFullFilePathName);
                	BufferedOutputStream bos = new BufferedOutputStream(fos);

                	byte[] readBuffer = new byte[1024 * 4];

        			while (bis.read(readBuffer, 0, readBuffer.length) != -1)
        			{
        				//버퍼 크기만큼 읽을 때마다 출력 스트림에 써준다.
        				bos.write(readBuffer);
        			}

        			DlwEvntDAO.insertEvtPicInfoNew(hmParam);
                }
                else
                {
                	System.out.println("파일 무존재.");
                }
            }

            if (fileCnt < 1)
            {
          	    System.out.println("업로드된 파일이 없습니다.");
            }

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            throw ex;
        }
        finally
        {

        }
	}

    /**
     * 상품분류명/지부별 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getbranchQury(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getbranchQury(pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntBrchAnal1(pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntBrchAnal2(pmParam);
    }

    /**
     * CP별/상품분류별 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEmplQury(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEmplQury(pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntEmplAnal1(pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntEmplAnal2(pmParam);
    }

    /**
     * 행사취소분석(상품분류별/지부별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getbranchQuryCncl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getbranchQuryCncl(pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntBrchCncl1(pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBrchCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntBrchCncl2(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEmplQuryCncl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEmplQuryCncl(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntEmplCncl1(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntEmplCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntEmplCncl2(pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getMngrIpCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMngrIpCount(pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMngrIpList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMngrIpList(pmParam);
    }

    /**
     * 장례용품 재고현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getStockClCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockClCount(pmParam);
    }

    /**
     * 장례용품 재고현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockClList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockClList(pmParam);
    }

    /**
     * 추가매출 - 직영 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAddSalesInList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAddSalesInList(pmParam);
    }

    /**
     * 추가매출 - 외주 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAddSalesOutList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getAddSalesOutList(pmParam);
    }
    
    /**
     * 추가매출 - 장지 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFuneralList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getFuneralList(pmParam);
    }

    /**
     * 일자별 발주현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getOrderAnalCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOrderAnalCount(pmParam);
    }

    /**
     * 일자별 발주현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOrderAnalList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOrderAnalList(pmParam);
    }

    /**
     * 행사잔금처리현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntRemainAmtList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntRemainAmtList(pmParam);
    }

    /**
     * 행사잔금처리현황 비고 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int RemainBigoUpdate(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.RemainBigoUpdate(pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getStockDayCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockDayCount(pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockDayList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockDayList(pmParam);
    }

    /**
     * SMS 전송 시 결제정보 조회(현금영수증 정보)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getsmsPayMent(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getsmsPayMent(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList1(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList2(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList3(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 앰블런스
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList4(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 유골함
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList5(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList_Head(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_func(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList_func(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 장례식장(금액) 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdList_func2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList_func2(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSrvProdList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdList_qry(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdListIn(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdListIn(pmParam);
    }

    /**
     * 물품 제공현황 분석- 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdAnal1(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdAnal2(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdAnal3(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장례식장(CP)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdAnal4(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 월별분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSrvProdAnal5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSrvProdAnal5(pmParam);
    }

    /**
     * 월별 지부별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMonthBrList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMonthBrList(pmParam);
    }

    /**
     * 월별 창고별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMonthWhList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getMonthWhList(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsIpList_Head(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList_func(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsIpList_func(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getGdsIpList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsIpList_qry(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsIpList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsIpList(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsUseList_Head(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList_func(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsUseList_func(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getGdsUseList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsUseList_qry(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsUseList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getGdsUseList(pmParam);
    }

    /**
     * CP별/일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCostList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCostList(pmParam);
    }

    /**
     * 회원별 일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCostMemList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getCostMemList(pmParam);
    }

    /**
     * 손익분석집계표 - 행사별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList1(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBenefitList1(pmParam);
    }

    /**
     * 손익분석집계표 - 월별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList2(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBenefitList2(pmParam);
    }

    /**
     * 손익분석집계표 - 지부별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList3(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBenefitList3(pmParam);
    }

    /**
     * 손익분석집계표 - CP별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList4(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBenefitList4(pmParam);
    }

    /**
     * 손익분석집계표 - 외주업체별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBenefitList5(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getBenefitList5(pmParam);
    }

    /**
     * 재고마감 - 품목별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockGoodsList(pmParam);
    }

    /**
     * 재고마감 - 창고별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getStockWhList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getStockWhList(pmParam);
    }

    /**
     * 재고마감 - 마감취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteStockMg(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteStockMg(pmParam);
    }

    /**
     * 재고마감 - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertStockMg(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertStockMg(pmParam);
    }

    /**
     * 발주번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getOrdNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getOrdNo(pmParam);
    }

    /**
     * 발주번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSinOrdNo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getSinOrdNo(pmParam);
    }

    /**
     * 발주 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteOrdMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteOrdMst(pmParam);
    }

    /**
     * 발주 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteOrdDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteOrdDtl(pmParam);
    }

    /**
     * 발주 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOrdMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOrdMst(pmParam);
    }

    /**
     * 발주 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOrdDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertOrdDtl(pmParam);
    }

    /**
     * 대체용품 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateRepGoods(pmParam);
    }

    /**
     * 대체용품 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.insertRepGoods(pmParam);
    }

    /**
     * 대체용품 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.deleteRepGoods(pmParam);
    }

    /**
     * 대체용품 회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtRepList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtRepList(pmParam);
    }

    /**
     * 대체용품현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getRepGoodsCount(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getRepGoodsCount(pmParam);
    }

    /**
     * 대체용품현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRepGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getRepGoodsList(pmParam);
    }

    /**
     * 행사유지현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvtUjiList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvtUjiList(pmParam);
    }

    /**
     * 행사문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntChatInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntChatInfo(pmParam);
    }

    /**
     * 행사 등록시 해당 회원번호로 행사테이블 조회
     *
     * @param hmParam 검색 조건
     * @return
     * @throws Exception
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     */
    public int countEvent(Map<String, Object> hmParam)  throws Exception{
        return DlwEvntDAO.countEvent(hmParam);
    }

    /**
     * 문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntcjChatInfo(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.getEvntcjChatInfo(pmParam);
    }
    /**
     * 알림톡 구동시 가상계좌 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEventcjChatVrtlInfo(Map<String, Object> hmParam) {
        return DlwEvntDAO.getEventcjChatVrtlInfo(hmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateEventMemberState(Map<String, ?> pmParam) throws Exception {
        return DlwEvntDAO.updateEventMemberState(pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam) {
		return DlwEvntDAO.getWdrwReqConfirm(pmParam);
	}

    /* ================================================================
     * 날짜 : 20190328
     * 이름 : 임동진
     * 추가내용 : 푸시 알림 전송
     * ================================================================
     * */
    public int sndPushAlertMain(Map<String, ?> pmParam) throws Exception {
    	Map<String, Object> mList = new HashMap<String, Object>();
    	List<Map<String, Object>> listSinfo = dmWebSenderService.getSendPushInfo(pmParam);

    	if(listSinfo.size() != 0){
	    	mList= listSinfo.get(0);

	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("SEND_MSG"));
	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("REAL_EVT_MNGR_CD"));
	        System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("MNG_BRANCH_CD"));

	     	JSONObject objList = new JSONObject();
	     	JSONArray arrList = new JSONArray();

	     	String strMsg = mList.get("SEND_MSG").toString();
	     	String strMainCp = mList.get("REAL_EVT_MNGR_CD").toString();
	     	String strMngCp = mList.get("MNG_BRANCH_CD").toString();

	     	arrList.put(strMainCp);    //진행 cp
	     	arrList.put(strMngCp); 	   //지부장
	     	//arrList.put("2019000062"); //임동진
	     	arrList.put("2017000093"); //김기래
	     	arrList.put("2017000094"); //손희영
	     	arrList.put("2017000096"); //하경수
	     	arrList.put("2017000097"); //유성원
	     	arrList.put("2017000103"); //김지연
	     	arrList.put("2018000170"); //모숙현
	     	arrList.put("2022000071"); //김종걸

	     	objList.put("message", strMsg);
	     	objList.put("target", arrList);
	     	
	     	String basVl = basVlService.getBasVlAsString("push_url");

	     	URL url = new URL(basVl);
	     	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     	conn.setDoOutput(true);
	     	conn.setRequestMethod("POST");
	     	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		    String param = "reqData="+ URLEncoder.encode(objList.toString(), "UTF-8");

	     	//전송
	     	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
	 		osw.write(param);
	 		osw.flush();

	 		System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> PARAM: " + param);

	 		//응답
	 		BufferedReader br = null;
	 		br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

	 		String line = null;
	 		while ((line = br.readLine()) != null){
	 			System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> RESULT: " + line);
	 		}

	 		osw.close();
	 		br.close();
	 		conn.disconnect();
    	}

        return 0;
    }

    public int existsRptDtlConfirm(Map<String, ?> pmParam) throws Exception {
		return DlwEvntDAO.existsRptDtlConfirm(pmParam);
	}
    
	public int existsOutRptDtlConfirm(Map<String, ?> pmParam) throws Exception {
		return DlwEvntDAO.existsOutRptDtlConfirm(pmParam);
	}
}
