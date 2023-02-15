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

import powerservice.business.dlw.service.DlwNewTypeEvntService;
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
public class DlwNewTypeEvntServiceImpl extends EgovAbstractServiceImpl implements DlwNewTypeEvntService {

    @Resource
    public DlwNewTypeEvntDAO DlwNewTypeEvntDAO;

    @Resource
    public DmWebSenderService dmWebSenderService;

    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public int getNewTypeEventCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEventCount(pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEventList(pmParam);
    }

    /**
     * 대명라이프웨이 행사통계
     *
     * @param pmParam 검색 조건
     * @return 행사 통계
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventStats(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEventStats(pmParam);
    }

    /**
     * 대명라이프웨이 행사 상세 정보
     *
     * @param pmParam 검색 조건
     * @return  행사 상세 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeDetailEvent(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeDetailEvent(pmParam);
    }

    /**
     * 대명라이프웨이 행사 매니저 등록? 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtMngrRegList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtMngrRegList(pmParam);
    }

    /**
     * 대명라이프웨이 CP 직영, 외주 구분 조회
     *
     * @param pmParam 검색 조건
     * @return CP 직영, 외주 구분
     * @throws Exception
     */
    public String getNewTypeCpGubun(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCpGubun(pmParam);
    }

    /**
     * 대명라이프웨이 직영 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirMst(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례용품) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirGdsList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(현장발주) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirGdsListB(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(장례식장) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirGdsListC(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(협력업체) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirGdsListD(pmParam);
    }

    /**
     * 대명라이프웨이 직영 행사품목(기타(용품/인력)) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirGdsListE(pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출1 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirAddSale1List(pmParam);
    }

    /**
     * 대명라이프웨이 직영 추가매출2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirAddSale2List(pmParam);
    }

    /**
     * 대명라이프웨이 직영 일반경비 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirNormlCostList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirPayList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirPayList(pmParam);
    }

    /**
     * 대명라이프웨이 직영 결제정보 관련2 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptDirPayInfo(pmParam);
    }

    /**
     * 대명라이프웨이 외주 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutMst(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutDtl1List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutDtl2List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 결제정보 관련 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutDtl4List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 사용물품 등록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutDtl5List(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRptOutPayInfo(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhMvYn(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeWhMvYn(pmParam);
    }

    /**
     * 대명라이프웨이 외주 행사 결제 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypePayAmtCash(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypePayAmtCash(pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeFunrlHallCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeFunrlHallCount(pmParam);
    }

    /**
     * 대명라이프웨이 장례식장 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeFunrlHallList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeFunrlHallList(pmParam);
    }

    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvent(pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEvent(pmParam);
    }

    /**
     * 대명라이프웨이 마감데이터 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCloseDataSaveYnChk(pmParam);
    }

    /**
     * 대명라이프웨이 행사순번 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtSeq(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtSeq(pmParam);
    }

    /**
     * 대명라이프웨이 취소 내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeCnclReg(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeCnclReg(pmParam);
    }

    /**
     * 대명라이프웨이 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeEventCheck(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEventCheck(pmParam);
    }

    /**
     * 대명라이프웨이 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeResnCheck(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeResnCheck(pmParam);
    }

    /**
     * 대명라이프웨이 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeTaxtCheck(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTaxtCheck(pmParam);
    }

    /**
     * 대명라이프웨이 CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsCheck(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCmsCheck(pmParam);
    }

    /**
     * 대명라이프웨이 해약 시 CMS청구내역 신청전,신청중 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCmsReqCnt(pmParam);
    }

    /**
     * 대명라이프웨이 상품구분 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeProdCl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeProdCl(pmParam);
    }

    /**
     * 대명라이프웨이 jointype 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeJoinType(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeJoinType(pmParam);
    }

    /**
     * 대명라이프웨이 입금 총납입부금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccntSearch(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAccntSearch(pmParam);
    }

    /**
     * 대명라이프웨이 행사 삭제 (MEM_PROD_ACCNT)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvent(Map<String, ?> pmParam) throws Exception {
        int sCnt = DlwNewTypeEvntDAO.deleteNewTypeEvent(pmParam);
        /*if (sCnt > 0) {
            //엔컴DB 행사 삭제후 인우 MEM_PROD_ACCNT 테이블수정
            MemProdAccntDAO.deleteEvent(pmParam);
        }*/

        // 행사 삭제 후 의전행사 사진 삭제
        DlwNewTypeEvntDAO.deleteNewTypeEvtPicByEventSeq(pmParam);

        return sCnt;
    }

    /**
     * 대명라이프웨이 행사 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEvtNewYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtNewYnChk(pmParam);
    }

    /**
     * 대명라이프웨이 행사보고서 생성여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeIsEvtRptGen(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeIsEvtRptGen(pmParam);
    }

    /**
     * 대명라이프웨이 행사/해약 시 결합상품 할부원금 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeCheckB2bPay(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCheckB2bPay(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeDlwManrCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeDlwManrCount(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeDlwMngrList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeDlwMngrList(pmParam);
    }

    /**
     * 거래처 장례식장 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeFunrCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeFunrCount(pmParam);
    }

    /**
     * 거래처 장례식장 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeFunrList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeFunrList(pmParam);
    }

    /**
     * 거래처 매입업체 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCustmrPopCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCustmrPopCount(pmParam);
    }

    /**
     * 거래처 매입업체 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCustmrPopList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCustmrPopList(pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCustmrOutCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCustmrOutCount(pmParam);
    }

    /**
     * 거래처 행사외주업체 팝업 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCustmrOutList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCustmrOutList(pmParam);
    }

    /**
     * 행사자 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeevntMngr(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeevntMngr(pmParam);
    }

    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeevntMngr(Map<String, ?> pmParam) throws Exception {
    	int retCnt = 0;
    	//행사자 등록
    	DlwNewTypeEvntDAO.insertNewTypeevntMngr(pmParam);

    	//행사자 등록 중에 지도사(cp)가 등록되면 APP 푸시 알림

    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > retCnt : " + retCnt);
    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx > pmParam.get(job_duty) : " + pmParam.get("job_duty"));

    	if (pmParam.get("job_duty").equals("0001")){
    		sndNewTypePushAlertMain(pmParam);
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
    public List<Map<String, Object>> getNewTypeEvntMngrList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntMngrList(pmParam);
    }

    /**
     * 대명라이프웨이 행사자 기초 수당 자료 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntMngrPayList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntMngrPayList(pmParam);
    }

    /**
     * 행사자 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeRowMngr(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeRowMngr(pmParam);
    }

    /**
     * 품목 팝업 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeGoodsCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGoodsCount(pmParam);
    }

    /**
     * 품목 팝업 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGoodsList(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab1JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab1JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab2JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab2JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab3JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab3JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab4JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab4JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab5JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab5JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타(용품/입력)) 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반 경비 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab6JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab6JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab6(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB 일반경비 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab6(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab7JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab7JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab7(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab7(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab8JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab8JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab8(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab8(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTab9JikList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeTab9JikList(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeJiktab9(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeJiktab9(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeJiktab9(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeJiktab9(pmParam);
    }

    /**
     * 행사보고서(직영) - 첫번째TAB (장례용품) 선택 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab1(pmParam);
    }

    /**
     * 행사보고서(직영) - 두번째TAB (현장발주) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab2(pmParam);
    }

    /**
     * 행사보고서(직영) - 세번째TAB (장례식장) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab3(pmParam);
    }

    /**
     * 행사보고서(직영) - 네번째TAB (협력업체) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab4(pmParam);
    }

    /**
     * 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab5(pmParam);
    }

    /**
     * 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab6(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab6(pmParam);
    }

    /**
     * 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab7(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab7(pmParam);
    }

    /**
     * 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab8(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab8(pmParam);
    }

    /**
     * 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsTab9(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsTab9(pmParam);
    }

    /**
     * 카드 수수료 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCardFeeList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCardFeeList(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 관련 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOutsrc_1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOutsrc_2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOutsrc_3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOutsrc_4(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsOutds1(pmParam);
    }

    /**
     * 행사보고서(외주) - 추가매출1 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsOutds2(pmParam);
    }

    /**
     * 행사보고서(외주) - 제공용품2 및 추가매출2 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsOutds3(pmParam);
    }

    /**
     * 행사보고서(외주) - 결제정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeGoodsOutds4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeGoodsOutds4(pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeAnnlCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAnnlCount(pmParam);
    }

    /**
     * 행사현황 및 분석 조회(메인) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAnnlList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAnnlList(pmParam);
    }

    /**
     * 지부 콤보박스 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBranchList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBranchList(pmParam);
    }

    /**
     * 행사정산 입금정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayData(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntPayData(pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntPayDtl(pmParam);
    }

    /**
     * 입금회차에 따른 입금상세정보 조회(회차별)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntPayDtlCnt(pmParam);
    }

    /**
     * 행사보고서(직영) 상세 조회(입금정보 포함)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntRptMagam(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEvntRptMagam(pmParam);
    }

    /**
     * 행사보고서(직영) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(직영) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeEvntRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntOutRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) - 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvntOutRptDtl(pmParam);
    }

    /**
     * 행사보고서(외주) - 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEvntOutRptDtl(pmParam);
    }


    /**
     * 행사보고서(외주) - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEvntOutRptDtlMagam(pmParam);
    }

    /**
     * 행사보고서(외주) - 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeEvntOutRptDtl(pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeUpdBitHis(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeUpdBitHis(pmParam);
    }

    /**
     * 회원상태 변경(행사등록 시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeMemprodKstbit(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeMemprodKstbit(pmParam);
    }

    /**
     * 회원상태 변경(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeMemprodKstbit_cncl(pmParam);
    }

    /**
     * 회원상태 변경이력 등록(행사취소시)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeUpdBitHis_cncl(pmParam);
    }

    /**
     * 사업장내 창고이동 물품리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMoveGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMoveGoodsList(pmParam);
    }

    /**
     * 사업장내 창고이동 마스터 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMoveGoodsMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMoveGoodsMst(pmParam);
    }

    /**
     * 창고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhmvNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeWhmvNo(pmParam);
    }

    /**
     * 입고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhinNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeWhinNo(pmParam);
    }

    /**
     * 출고 창고코드 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhcd(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeWhcd(pmParam);
    }

    /**
     * 출고번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeWhoutNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeWhoutNo(pmParam);
    }

    /**
     * 창고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhmvNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSinWhmvNo(pmParam);
    }

    /**
     * 입고번호 신규생성
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhinNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSinWhinNo(pmParam);
    }

    /**
     * 출고번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinWhoutNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSinWhoutNo(pmParam);
    }

    /**
     * 출고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhOutMst(pmParam);
    }

    /**
     * 입고 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhinMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhinMst(pmParam);
    }

    /**
     * 창고이동 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhmvMst(pmParam);
    }

    /**
     * 출고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhOutDtl(pmParam);
    }

    /**
     * 입고 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhinDtl(pmParam);
    }

    /**
     * 창고이동 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeWhmvDtl(pmParam);
    }

    /**
     * 창고이동 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhmvMst(pmParam);
    }

    /**
     * 창고이동 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhmvDtl(pmParam);
    }

    /**
     * 출고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhOutMst(pmParam);
    }

    /**
     * 출고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhOutDtl(pmParam);
    }

    /**
     * 입고 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhinMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhinMst(pmParam);
    }

    /**
     * 입고 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeWhinDtl(pmParam);
    }

    /**
     * 입금마감 팝업 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypePayTotalList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypePayTotalList(pmParam);
    }

    /**
     * 장법 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal1(pmParam);
    }

    /**
     * 신규유지 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal2(pmParam);
    }

    /**
     * 지부 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal3(pmParam);
    }

    /**
     * 지역 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal4(pmParam);
    }

    /**
     * 직영CP 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal5(pmParam);
    }

    /**
     * 종교 분석현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntAnal6(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntAnal6(pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회건수)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeAnnlCnclCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAnnlCnclCount(pmParam);
    }

    /**
     * 행사취소현황 및 분석(취소현황 조회리스트)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAnnlCnclList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAnnlCnclList(pmParam);
    }

    /**
     * 지부별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntCncl1(pmParam);
    }

    /**
     * 취소사유별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntCncl2(pmParam);
    }

    /**
     * 소요시간별 취소분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntCncl3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntCncl3(pmParam);
    }


    /**
     * 물품 및 서비스제공현황 조회 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventSrvProd1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEventSrvProd1(pmParam);
    }

    /**
     * CP등록 여부 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeEvntCpChkCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntCpChkCount(pmParam);
    }

    /**
     * 입금마감 - 상조부금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymng(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvntPaymng(pmParam);
    }

    /**
     * 입금마감 - 결합금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymngDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvntPaymngDtl(pmParam);
    }

    /**
     * 입금마감 - 추가금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeEvntPaymngDtl1(pmParam);
    }

    /**
     * 의전행사 사진 분류 - 행사회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPicSort(Map<String, Object> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntPicSort(pmParam);
    }

    /**
     * 의전행사 사진 분류 - 분류/미분류회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntPicList(Map<String, Object> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntPicList(pmParam);
    }


    /**
     * 선택파일 미분류로 이동
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int disconncectNewTypeAccnt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {

                for (int i=0; i<listInDs.size(); ++i) {
                    Map hmParam = listInDs.get(i);
                    if ("1".equals((String)hmParam.get("chk"))) {
                        ParamUtils.addSaveParam(hmParam);
                        iCudCnt += DlwNewTypeEvntDAO.disconncectNewTypeAccnt(hmParam);
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
    public int conncectNewTypeAccnt(XPlatformMapDTO xpDto) throws Exception {

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

                            iCudCnt += DlwNewTypeEvntDAO.conncectNewTypeAccnt(hmParam);
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
    public int deleteNewTypeEvtPic(XPlatformMapDTO xpDto) throws Exception {

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
                        iCudCnt += DlwNewTypeEvntDAO.deleteNewTypeEvtPic(hmParam);
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
    public List<Map<String, Object>> evtNewTypePicBatchDownload(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map<String, Object> hmParam;
        Map<String, Object> mRow = new HashMap<String,Object>();

        List<Map<String, Object>> lstRet = new ArrayList<>();

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List lst = DlwNewTypeEvntDAO.getNewTypeEvntPicSort(hmParam);

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
    public int insertNewTypeEvtPicInfo(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                iCudCnt += DlwNewTypeEvntDAO.insertNewTypeEvtPicInfo(hmParam);
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

	public void saveNewTypeEvenPhotoUpLoad(HttpServletRequest request,HttpServletResponse response) throws Exception {
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

                	List<Map<String, Object>> mListMemNo =  DlwNewTypeEvntDAO.getNewTypeAccntMemNo(hmParam);
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

        			DlwNewTypeEvntDAO.insertNewTypeEvtPicInfoNew(hmParam);
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
    public String getNewTypebranchQury(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypebranchQury(pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntBrchAnal1(pmParam);
    }

    /**
     * 행사분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntBrchAnal2(pmParam);
    }

    /**
     * CP별/상품분류별 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEmplQury(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEmplQury(pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntEmplAnal1(pmParam);
    }

    /**
     * 행사분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntEmplAnal2(pmParam);
    }

    /**
     * 행사취소분석(상품분류별/지부별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypebranchQuryCncl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypebranchQuryCncl(pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntBrchCncl1(pmParam);
    }

    /**
     * 행사취소분석(상품분류명/지부별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntBrchCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntBrchCncl2(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeEmplQuryCncl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEmplQuryCncl(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplCncl1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntEmplCncl1(pmParam);
    }

    /**
     * 행사취소분석(CP별/상품분류별) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntEmplCncl2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntEmplCncl2(pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeMngrIpCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMngrIpCount(pmParam);
    }

    /**
     * 외부 일용직 입금대장 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMngrIpList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMngrIpList(pmParam);
    }

    /**
     * 장례용품 재고현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeStockClCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockClCount(pmParam);
    }

    /**
     * 장례용품 재고현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockClList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockClList(pmParam);
    }

    /**
     * 추가매출 - 직영 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAddSalesInList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAddSalesInList(pmParam);
    }

    /**
     * 추가매출 - 외주 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAddSalesOutList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeAddSalesOutList(pmParam);
    }

    /**
     * 일자별 발주현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeOrderAnalCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOrderAnalCount(pmParam);
    }

    /**
     * 일자별 발주현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeOrderAnalList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOrderAnalList(pmParam);
    }

    /**
     * 행사잔금처리현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntRemainAmtList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntRemainAmtList(pmParam);
    }

    /**
     * 행사잔금처리현황 비고 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int RemainNewTypeBigoUpdate(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.RemainNewTypeBigoUpdate(pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeStockDayCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockDayCount(pmParam);
    }

    /**
     * 일자별 입출고 내역 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockDayList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockDayList(pmParam);
    }

    /**
     * SMS 전송 시 결제정보 조회(현금영수증 정보)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypesmsPayMent(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypesmsPayMent(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList1(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList2(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList3(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 앰블런스
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList4(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황 - 유골함
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList5(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList_Head(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 펑션실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_func(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList_func(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 장례식장(금액) 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdList_func2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList_func2(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 쿼리 불러오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSrvProdList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdList_qry(pmParam);
    }

    /**
     * 물품 및 서비스 제공현황(직영) - 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdListIn(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdListIn(pmParam);
    }

    /**
     * 물품 제공현황 분석- 상복
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdAnal1(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 제단
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdAnal2(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장의차량
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdAnal3(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 장례식장(CP)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdAnal4(pmParam);
    }

    /**
     * 물품 제공현황 분석 - 월별분석
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeSrvProdAnal5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSrvProdAnal5(pmParam);
    }

    /**
     * 월별 지부별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMonthBrList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMonthBrList(pmParam);
    }

    /**
     * 월별 창고별 장의용품 재고현황
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMonthWhList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeMonthWhList(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsIpList_Head(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList_func(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsIpList_func(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeGdsIpList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsIpList_qry(pmParam);
    }

    /**
     * 일자별 지부별 물품 입고 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsIpList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsIpList(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 헤더
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList_Head(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsUseList_Head(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 펑션 실행
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList_func(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsUseList_func(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeGdsUseList_qry(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsUseList_qry(pmParam);
    }

    /**
     * 일자별 지부별 물품 사용내역 조회 - 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeGdsUseList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeGdsUseList(pmParam);
    }

    /**
     * CP별/일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCostList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCostList(pmParam);
    }

    /**
     * 회원별 일반경비별 현황 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeCostMemList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeCostMemList(pmParam);
    }

    /**
     * 손익분석집계표 - 행사별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList1(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBenefitList1(pmParam);
    }

    /**
     * 손익분석집계표 - 월별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList2(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBenefitList2(pmParam);
    }

    /**
     * 손익분석집계표 - 지부별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList3(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBenefitList3(pmParam);
    }

    /**
     * 손익분석집계표 - CP별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList4(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBenefitList4(pmParam);
    }

    /**
     * 손익분석집계표 - 외주업체별
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBenefitList5(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeBenefitList5(pmParam);
    }

    /**
     * 재고마감 - 품목별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockGoodsList(pmParam);
    }

    /**
     * 재고마감 - 창고별 마감 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeStockWhList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeStockWhList(pmParam);
    }

    /**
     * 재고마감 - 마감취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeStockMg(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeStockMg(pmParam);
    }

    /**
     * 재고마감 - 마감저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeStockMg(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeStockMg(pmParam);
    }

    /**
     * 발주번호 가져오기
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeOrdNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeOrdNo(pmParam);
    }

    /**
     * 발주번호 신규생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSinOrdNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeSinOrdNo(pmParam);
    }

    /**
     * 발주 마스터 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeOrdMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeOrdMst(pmParam);
    }

    /**
     * 발주 디테일 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeOrdDtl(pmParam);
    }

    /**
     * 발주 마스터 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOrdMst(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOrdMst(pmParam);
    }

    /**
     * 발주 디테일 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeOrdDtl(pmParam);
    }

    /**
     * 대체용품 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeRepGoods(pmParam);
    }

    /**
     * 대체용품 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.insertNewTypeRepGoods(pmParam);
    }

    /**
     * 대체용품 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNewTypeRepGoods(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.deleteNewTypeRepGoods(pmParam);
    }

    /**
     * 대체용품 회원 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtRepList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtRepList(pmParam);
    }

    /**
     * 대체용품현황 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeRepGoodsCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeRepGoodsCount(pmParam);
    }

    /**
     * 대체용품현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeRepGoodsList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeRepGoodsList(pmParam);
    }

    /**
     * 행사유지현황 조회 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvtUjiList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvtUjiList(pmParam);
    }

    /**
     * 행사문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntChatInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntChatInfo(pmParam);
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
    public int countNewTypeEvent(Map<String, Object> hmParam)  throws Exception{
        return DlwNewTypeEvntDAO.countNewTypeEvent(hmParam);
    }

    /**
     * 문자 전송시 회원정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEvntcjChatInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.getNewTypeEvntcjChatInfo(pmParam);
    }
    /**
     * 알림톡 구동시 가상계좌 검색
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeEventcjChatVrtlInfo(Map<String, Object> hmParam) {
        return DlwNewTypeEvntDAO.getNewTypeEventcjChatVrtlInfo(hmParam);
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateNewTypeEventMemberState(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeEvntDAO.updateNewTypeEventMemberState(pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam) {
		return DlwNewTypeEvntDAO.getNewTypeWdrwReqConfirm(pmParam);
	}

    /* ================================================================
     * 날짜 : 20190328
     * 이름 : 임동진
     * 추가내용 : 푸시 알림 전송
     * ================================================================
     * */
    public int sndNewTypePushAlertMain(Map<String, ?> pmParam) throws Exception {
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

	     	URL url = new URL("http://192.168.10.95:8080/api/send_push.jsp");
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
}
