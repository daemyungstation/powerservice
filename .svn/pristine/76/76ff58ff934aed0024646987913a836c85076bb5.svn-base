/*
 * (@)# DlwCmsDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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
public class DlwExtcTrgtDAO extends EgovAbstractMapper {

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
     * 대상고객추출 정보의 검색 수를 반환한다.(증서멤버쉽)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount1100(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount1100", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(증서멤버쉽)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList1100(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList1100", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(가입승인)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount1200(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount1200", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(가입승인)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList1200(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList1200", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(해약미처리)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount1700(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount1700", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(해약미처리 중간 추가 건)
     * 임동진 20190107
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount1700Add(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount1700Add", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(해약미처리 중간 추가 건)
     * 임동진 20190107
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList1700Add(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList1700Add", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(해약미처리)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList1700(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList1700", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(업체관리)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount2100(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount2100", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(업체관리)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList2100(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList2100", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(해약방어)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount2200(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount2200", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(해약방어)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList2200(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList2200", pmParam);
    }


    /**
     * 미납 조회
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtListUnpy(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtListUnpy", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(당월미납)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount3300(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getExtcTrgtCount3300", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(당월미납)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList3300(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getExtcTrgtList3300", pmParam);
    }

    /**
     * 실적 (회수 , 회수회차) 조회 (납입)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyAcrs(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getUnpyAcrs", pmParam);
    }

    /**
     * 실적 (회수 , 회수회차) 조회 (해약방어)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCnctAcrs(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getCnctAcrs", pmParam);
    }


    /**
     *  원장 테이블 저장한다.
     *
     * @param param 캠페인 정보
     * @throws Exception
     */
    public int insertDlwYenche(Map<String, Object> pmParam) throws Exception {
        return insert("DlwExtcTrgtMap.insertDlwYenche", pmParam);
    }

    /**
     * 연체테이블에 데이터가 있는지 확인한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getYenche(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwExtcTrgtMap.getYenche", pmParam);
    }

    /**
     * 현재 미납상태를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyStat(Map<String,Object> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getUnpyStat", pmParam);
    }


    /**
     * B2B업체 코드를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return B2B업체 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BList(Map<String,Object> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.selectB2BList", pmParam);
    }

  //B2B코드 삽입
    public int insertB2BComp(Map<String, ?> pmParam) throws Exception {
        return  insert("DlwExtcTrgtMap.insertB2BComp", pmParam);
    }
    // B2B코드 수정
    public int updateB2BComp(Map<String, Object> hmParam) {
         return update("DlwExtcTrgtMap.updateB2BComp", hmParam);
     }

  /*//B2B코드 중복 조회
    public int getDuplB2BCd(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectOne("DlwExtcTrgtMap.getDuplB2BCd", pmParam);
    }*/

    //B2B코드 삭제
    public int deleteB2bcd(Map<String, ?> pmParam) throws Exception {
        return delete("DlwExtcTrgtMap.deleteB2bcd", pmParam);
    }

    //레저연동 조회 건수
    public int countCpHist(Map<String, Object> pmParam){
        return selectOne("DlwExtcTrgtMap.countCpHist", pmParam);
    }

    //레저연동 조회
    public List<Map<String, Object>> getCpHist(Map<String,Object> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.getCpHist", pmParam);
    }

    public String isEvent(String paramString) throws Exception{
         return (String)selectOne("DlwExtcTrgtMap.isEvent", paramString);
    }

    public int updateRsSndEnd(Map<String, ?> pmParam) throws Exception{
        return  update("DlwExtcTrgtMap.updateRsSndEnd", pmParam);
    }

    //레저연동 히스토리
    public int insertResortMngHstr(Map<String, ?> pmParam) throws Exception{
        return  insert("DlwExtcTrgtMap.insertResortMngHstr", pmParam);
    }

    //레저연체관리 조회 건수
    public int countResortYenCheInfoList() throws Exception {

        return selectOne("DlwExtcTrgtMap.countResortYenCheInfoList");
    }
    
    //레저연체 리스트 페이지 조회용
    public int countResortYenCheInfoDataCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwExtcTrgtMap.countResortYenCheInfoDataCnt", pmParam);
    }

    //데이터 연체 / 정상 업데이트 건수
    public int countResortYenCheInfo() throws Exception {

        return selectOne("DlwExtcTrgtMap.countResortYenCheInfo");
    }

    //레저연체관리 조회
    public List<Map<String, Object>> selectResortYenCheInfoList(Map<String,Object> pmParam) throws Exception {
        System.out.println("=============selectResortYenCheInfoList");
        return selectList("DlwExtcTrgtMap.selectResortYenCheInfoList", pmParam);
    }

    public int updateRsYenCheSndEnd(Map<String, ?> pmParam) throws Exception{
        return  update("DlwExtcTrgtMap.updateRsYenCheSndEnd", pmParam);
    }

    //레저연체정보 갱신
    public int deleteYenCheInfo(Map<String,Object> pmParam) throws Exception {
        System.out.println("333333333333333연체정보 갱신 삭제 DAO");
        return delete("DlwExtcTrgtMap.deleteYenCheInfo", pmParam);
    }


    public  int insertYenCheInfo(Map<String, ?> pmParam) throws Exception{
        return update("DlwExtcTrgtMap.insertYenCheInfo", pmParam);
    }

    public  int updateYenCheInfo(Map<String, ?> pmParam) throws Exception{
        return update("DlwExtcTrgtMap.updateYenCheInfo", pmParam);
    }

    public List<Map<String, Object>> select_before_updateYenCheInfo(Map<String, Object> pmParam){
        return selectList("DlwExtcTrgtMap.select_before_updateYenCheInfo");
    }

    //레저연동 이력 20170810
    public int selectResortMngHstr_cnt(Map<String,Object> pmParam){
        return selectOne("DlwExtcTrgtMap.selectResortMngHstr_cnt" , pmParam);
    }

    public List<Map<String, Object>> selectResortMngHstr(Map<String,Object> pmParam) throws Exception {
        return selectList("DlwExtcTrgtMap.selectResortMngHstr", pmParam);
    }


}
