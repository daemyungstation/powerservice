/*
 * (@)# DlwResnServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
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


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import powerservice.business.dlw.service.DlwResnReceiptService;


@Service
public class DlwResnReceiptServiceImpl extends EgovAbstractServiceImpl implements DlwResnReceiptService {

    @Resource
    public DlwResnReceiptDAO dlwResnReceiptDAO;
       
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수회원정보
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptAccntInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getReceiptAccntInfo(pmParam);
    }      
    
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptList(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getReceiptList(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptProcList(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getReceiptProcList(pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수히스토리
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProcHistory(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getResnProcHistory(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getReceiptProcCount(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getReceiptProcCount(pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어상담원할당현황
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptProcCounsel(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getReceiptProcCounsel(pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약등록(처리)리스트 카운트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getResnProcCount(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getResnProcCount(pmParam);
    }     
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약등록(처리)리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProcList(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getResnProcList(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어회원리스트 카운트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getResnProtectCount(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getResnProtectCount(pmParam);
    }     
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어회원리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProtectList(Map<String, ?> pmParam) throws Exception {
        return dlwResnReceiptDAO.getResnProtectList(pmParam);
    }     
}
