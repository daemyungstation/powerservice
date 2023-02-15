/*
 * (@)# DlwProdDAO.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 입금마감현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author BIJ
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwMarktMngDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상품정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
    
    /**
     * 마케팅 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwMarktMngMap.getList", pmParam);
    }
    
    public int getListCount(Map<String, Object> pmParam) {
        return selectOne("DlwMarktMngMap.getListCount", pmParam);
    }
    
    /**
     * 마케팅 sms 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMmsMngList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwMarktMngMap.getMmsMngList", pmParam);
    }
    
    /**
     * 마케팅 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMonCntPopList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwMarktMngMap.getMonCntPopList", pmParam);
    }
    
    /**
     * 메세지 생성
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSendMsgForm(Map<String, ?> pmParam) throws Exception {
       return insert("DlwMarktMngMap.insertSendMsgForm", pmParam);
    }
    
    /**
     * LMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSendLms(Map<String, ?> pmParam) throws Exception {
       return insert("DlwMarktMngMap.insertSendLms", pmParam);
    }
    
    /**
     * 문자전송상태값 수정
     * 김주용
     * 20220329
    */
    public int updateMktMmsSend(Map<String, ?> pmParam) throws Exception {
        return update("DlwMarktMngMap.updateMktMmsSend", pmParam);
    }
    
    /**
     * 메세지 삭제
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int deleteSendMsgForm(Map<String, ?> pmParam) throws Exception {
        return delete("DlwMarktMngMap.deleteSendMsgForm", pmParam);
    }
}
