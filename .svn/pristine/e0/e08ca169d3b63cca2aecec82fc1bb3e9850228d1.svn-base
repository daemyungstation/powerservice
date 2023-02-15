/*
 * (@)# DlwConsDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/25
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

/**
 * 대명 상담정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/25
 * @프로그램ID DlwCons
 */
@Repository
public class DlwConsDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상담정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 상담정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 엔컴 상담정보 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담정보 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwConsCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwConsMap.getDlwConsCount", pmParam);
    }

    /**
     * 엔컴 상담정보 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) {
        return selectList("DlwConsMap.getDlwConsList", pmParam);
    }

    /**
     * 상담정보를 등록한다.
     *
     * @param pmParam 상담정보
     * @throws Exception
     */
    public int insertCons(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsMap.insertCons", pmParam);
    }

    /**
     * 상담정보를 수정한다.
     *
     * @param pmParam 상담정보
     * @throws Exception
     */
    public int updateCons(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsMap.updateCons", pmParam);
    }

    /**
     * 상담 상세 정보를 등록한다.
     *
     * @param pmParam 상담 상세 정보
     * @throws Exception
     */
    public int insertConsDtpt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwConsMap.insertConsDtpt", pmParam);
    }

    /**
     * 상담 상세 정보를 수정한다.
     *
     * @param pmParam 상담 상세 정보
     * @throws Exception
     */
    public int updateConsDtpt(Map<String, ?> pmParam) throws Exception {
        return update("DlwConsMap.updateConsDtpt", pmParam);
    }

    /**
     * 상담 정보를 삭제한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int deleteCons(Map<String, ?> pmParam) throws Exception {
        delete("DlwConsMap.deleteConsDtpt", pmParam);	// 상담DTL 삭제
        return update("DlwConsMap.deleteCons", pmParam);// 상담MNG 삭제
    }

    /**
     * 그룹코드 조회(상담관리) 리스트를 조회한다.
     *
     * @Param pmParam 검색 조건
     * @return 그룹코드 조회(상담관리) 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsGrpCd(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwConsMap.getConsGrpCd", pmParam);
    }
    
    
    /**
     * 홈페이지 회원 변경 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원 변경 이 검색 건수
     * @throws Exception
     */
    public int getDlwlifeweyCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwConsMap.getDlwlifeweyCount", pmParam);
    }
    
    /**
     * 홈페이지 회원 변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원 변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) {
        return selectList("DlwConsMap.getDlwlifeweyList", pmParam);
    }


}
