/*
 * (@)# DlwEvntDAO.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class DlwEvntMngDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
    
    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public List<Map<String, Object>> getEvntBranchList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntBranchList", pmParam);
    }

    public List<Map<String, Object>> getBranchCode(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getBranchCode", pmParam);
    }
    
    public int insertEvntBranch(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.branch_insert", pmParam);
    }
    
    public int updateEvntBranch(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntBranch", pmParam);
    }
    
    public int deleteEvntBranch(Map<String, ?> pmParam) throws Exception {   /// 삭제
        return delete("DlwEvntMngMap.deleteEvntBranch", pmParam);
    }

    public List<Map<String, Object>> selectEnvtBranch(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.selectEnvtBranch", pmParam);
    }
    
    //행사자 관리 리스트 조회
    public List<Map<String, Object>>  getEventManList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEventManList", pmParam);
    }
    
    //행사자관리 등록
    public int insrtEventMan(Map<String, ?> pmParam) throws Exception {
        return  insert("DlwEvntMngMap.insrtEventMan", pmParam);
    }
    
    //행사자관리 코드 중복 조회
    public int getDuplEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectOne("DlwEvntMngMap.getDuplEvtManaMngCd", pmParam);
    }

    // 행사자관리 삭제
    public int deleteEventMan(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntMngMap.deleteEventMan", pmParam);
    }

    // 행사자관리 수정
    public int updateEventMan(Map<String, Object> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEventMan", pmParam);
    }
    
    // 지부장 등록건수 조회
    public int chkEventManaCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntMngMap.chkEventManaCnt", pmParam);
    }
    
    //창고 관리 리스트 조회
    public List<Map<String, Object>>  getEvntWHList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntWHList", pmParam);
    }
    
    //창고관리 등록
    public int insrtEvntWH(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.insrtEvntWH", pmParam);
    }
    
    // 창고관리 수정
    public int updateEvntWH(Map<String, Object> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntWH", pmParam);
    }
    
    // 창고관리 삭제
    public int deleteEvntWH(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntMngMap.deleteEvntWH", pmParam);
    }
    
    //제공용품 리스트 조회
    public List<Map<String, Object>>  getEvntSPList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPList", pmParam);
    }
    
    //제공용품 등록
    public int insrtEvntSP(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.insrtEvntSP", pmParam);
    }
    
    //제공용품 수정
    public int updateEvntSP(Map<String, Object> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntSP", pmParam);
    }
    
    //제공용품 삭제
    public int deleteEvntSP(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntMngMap.deleteEvntSP", pmParam);
    }
    
    //제공용품 리스트 조회
    public List<Map<String, Object>>  getEvntSPmaxSqncList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPmaxSqncList", pmParam);
    }
    
    //제공용품 순서 수정
    public int updateEvntSPSequence(Map<String, Object> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntSPSequence", pmParam);
    }
    
    //제공용품 카테고리 조회
    public List<Map<String, Object>>  getEvntSPCtgMstList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPCtgMstList", pmParam);
    }
    
	//제공용품 카테고리 조회
    public List<Map<String, Object>>  getEvntSPCtgDtlList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPCtgDtlList", pmParam);
    }
    
	//제공용품 카테고리 조회
    public List<Map<String, Object>>  getEvntSPCtgSubList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPCtgSubList", pmParam);
    }

	//제공용품 카테고리 조회
    public List<Map<String, Object>>  getEvntSPCtgSeqList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPCtgSeqList", pmParam);
    }    
    
    public int updateEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntSPCtgMst", pmParam);
    }
    
    public int insertEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.insertEvntSPCtgMst", pmParam);
    }
    
    public int deleteEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.deleteEvntSPCtgMst", pmParam);
    }
    
    public int updateEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.updateEvntSPCtgDtl", pmParam);
    }
    
    public int insertEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.insertEvntSPCtgDtl", pmParam);
    }
    
    public int deleteEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.deleteEvntSPCtgDtl", pmParam);
    }
    
    public int insertEvntSPCtgSub(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntMngMap.insertEvntSPCtgSub", pmParam);
    }
    
    public int deleteEvntSPCtgSub(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntMngMap.deleteEvntSPCtgSub", pmParam);
    }
    
  //제공용품 팝업 조회
    public List<Map<String, Object>>  getEvntSPpopList (Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntMngMap.getEvntSPpopList", pmParam);
    }
}
