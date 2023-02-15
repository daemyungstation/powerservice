/*
 * (@)# DlwProdServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwPayCloseService;
import powerservice.business.dlw.service.DlwMarktMngService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Service
public class DlwMarktMngServiceImpl extends EgovAbstractServiceImpl implements DlwMarktMngService {


    @Resource
    public DlwMarktMngDAO DlwMarktMngDAO;

    
    /**
     * 마케팅 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getList(Map<String, Object> pmParam) throws Exception {
        return DlwMarktMngDAO.getList(pmParam);
    }
    
    public int getListCount(Map<String, Object> pmParam) {
        return DlwMarktMngDAO.getListCount(pmParam);
    }
    
    /**
     * 마케팅 SMS조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMmsMngList(Map<String, Object> pmParam) throws Exception {
        return DlwMarktMngDAO.getMmsMngList(pmParam);
    }
    
    /**
     * 마케팅 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMonCntPopList(Map<String, Object> pmParam) throws Exception {
        return DlwMarktMngDAO.getMonCntPopList(pmParam);
    }
    
    /**
     * 메시지생성
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public int insertSendMsgForm(Map<String, ?> pmParam) throws Exception {
        return DlwMarktMngDAO.insertSendMsgForm(pmParam);
    }
    
    /**
     * 메시지삭제
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public int deleteSendMsgForm(Map<String, ?> pmParam) throws Exception {
        return DlwMarktMngDAO.deleteSendMsgForm(pmParam);
    }
    
    public int insertMktSmsSend() throws Exception {
    	
    	Map<String, Object> hmParamTypeA = new HashMap<String, Object>();
    	//List<Map<String, Object>> listBatchInfo = DlwMarktMngDAO.getMmsMngList(hmParamTypeA);

//      if (listBatchInfo.size() <= 0) {
//          System.out.println("배치수행 일자가 아닙니다. 배치를 종료합니다.");
//          return -1;
//      }
    	
    	//Map<String, Object> hmParamTypeA = new HashMap<String, Object>();
    	
    	/*[02] 최초 MMS알림 메세지 생성 
         * 신규 상품 추가한 회원 마케팅 동의정보 전송 (마케팅 동의 거절 둘다)
         * 당일 정보 변경건으로 인한 마케팅정보 전송 (마케팅 동의한 문자만 전송)
         * 1년이 지난 회원 개인정보 동의 
         */   
        //insert
    	//DlwMarktMngDAO.insertSendMsgForm(hmParamTypeA);
    	
    	/*[03] 예약일자를 기준으로 한 저녁 5시50분정도에 예약 문자 전송 */
    	//insert 문자전송
        DlwMarktMngDAO.insertSendLms(hmParamTypeA);
    	
    	/*[04] 전송한 문자내역 전송여부Y업데이트 */
    	//update
    	DlwMarktMngDAO.updateMktMmsSend(hmParamTypeA);

   	 return 0;
    }
}
