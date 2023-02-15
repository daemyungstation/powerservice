/*
 * (@)# DlwResnDAO.java
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

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;


@Repository
public class DlwResnReceiptDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 해약정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 해약 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수회원정보
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptAccntInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getReceiptAccntInfo", pmParam);
    }   
    
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getReceiptList", pmParam);
    }    
    
    /* ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수히스토리
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProcHistory(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getResnProcHistory", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptProcList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getReceiptProcList", pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어접수리스트카우트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getReceiptProcCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnReceiptMap.getReceiptProcCount", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어상담원할당현황
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getReceiptProcCounsel(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getReceiptProcCounsel", pmParam);
    } 
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약등록(처리)리스트 카운트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getResnProcCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnReceiptMap.getResnProcCount", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약등록(처리)리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProcList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getResnProcList", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어회원리스트 카운트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public int getResnProtectCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwResnReceiptMap.getResnProtectCount", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어회원리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getResnProtectList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnReceiptMap.getResnProtectList", pmParam);
    }    
}
