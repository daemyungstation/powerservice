package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 행사 재고 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Repository
public class EventStockMngDao extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhInDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhInDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhMvDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhMvDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhOutDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhOutDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteOrdDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteOrdDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteStockClose(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteStockClose", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteStockInspectDtl(Map<String, Object> pmParam) throws Exception {
        return update("deleteStockInspectDtl.deleteStockInspectDtl", pmParam);
    }
    
    /**
     * 입고상세 삭제 - 발주번호로 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhInDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhInDtl", pmParam);
    }
    
    /**
     * 입고상세 삭제 - 입고번호로 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhInDtlByWhInNo(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhInDtlByWhInNo", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhIn(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhIn", pmParam);
    }
    
    /**
     * 입고상세 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhInDtl1(Map<String, Object> pmParam) throws Exception {
    	// TODO : 삭제로직이 좀 이상함, ord_no 로 삭제를 했는데 기대건수 2건, 실제 삭제된 건이 3건인 경우 발생했음
    	// JCY : 2016-11-29
        return update("EventStockMngMap.deleteWhInDtl1", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhMvDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhMvDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhOutDtl(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhOutDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhMv(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhMv", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteEvtRptWhOut(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteEvtRptWhOut", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteOrdMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteOrdMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteStockInspectMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteStockInspectMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhInMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhInMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhMvMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhMvMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteWhOutMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.deleteWhOutMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateOrdMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateOrdMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateOrdMstProcStat(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateOrdMstProcStat", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateStockInspectMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateStockInspectMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateWhInMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateWhInMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateWhMvMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateWhMvMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateWhOutMst(Map<String, Object> pmParam) throws Exception {
        return update("EventStockMngMap.updateWhOutMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertOrdDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertOrdDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertOrdMst(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertOrdMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertStockClose(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertStockClose", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertStockInspectDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertStockInspectDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertStockInspectMst(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertStockInspectMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhInDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhInDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhInMst(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhInMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhMvDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhMvDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhMvMst(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhMvMst", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhOutDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhOutDtl", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertWhOutMst(Map<String, Object> pmParam) throws Exception {
        return insert("EventStockMngMap.insertWhOutMst", pmParam);
    }

    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectGoodsComList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectGoodsComList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectIOWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectIOWhList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectInWhDtlInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectInWhDtlInfo", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectInWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectInWhList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrdDtlInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectOrdDtlInfo", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrderMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectOrderMngList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectOutWhList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectOutWhList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectSchWhInNo(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectSchWhInNo", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockCloseList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectStockCloseList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockCloseListByWh(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectStockCloseListByWh", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockInspectList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectStockInspectList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectWhMvList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventStockMngMap.selectWhMvList", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public String selectNewOrdNo(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("EventStockMngMap.selectNewOrdNo", pmParam);
    }    
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public String selectNewStockInspectNo(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("EventStockMngMap.selectNewStockInspectNo", pmParam);
    }    
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public String selectNewWhInNo() throws Exception {
        return (String)selectOne("EventStockMngMap.selectNewWhInNo");
    }    
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public String selectNewWhMvNo() throws Exception {
        return (String)selectOne("EventStockMngMap.selectNewWhMvNo");
    }    
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public String selectNewWhOutNo() throws Exception {
        return (String)selectOne("EventStockMngMap.selectNewWhOutNo");
    }    
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public int selectStockCloseCnt(Map<String, Object> pmParam) throws Exception {
        return (Integer)selectOne("EventStockMngMap.selectStockCloseCnt", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public int selectWhInDtlCnt(Map<String, Object> pmParam) throws Exception {
        return (Integer)selectOne("EventStockMngMap.selectWhInDtlCnt", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public int checkStockClose(String chkDt) throws Exception {
        return (Integer)selectOne("EventStockMngMap.checkStockClose", chkDt);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색조건 
     * @throws Exception
     */
    public int selectWhInDtlCntByWhInNo(Map<String, Object> pmParam) throws Exception {
        return (Integer)selectOne("EventStockMngMap.selectWhInDtlCntByWhInNo", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> sss(Map<String, ?> pmParam) throws Exception {
    	if (true) {
    		throw new Exception("사용하지 않는 메소드입니다.");
    	}
        return selectList("EventStockMngMap.sss", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateSss(Map<String, Object> pmParam) throws Exception {
    	if (true) {
    		throw new Exception("사용하지 않는 메소드입니다.");
    	}
        return update("EventStockMngMap.sss", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertSss(Map<String, Object> pmParam) throws Exception {
    	if (true) {
    		throw new Exception("사용하지 않는 메소드입니다.");
    	}
        return insert("EventStockMngMap.sss", pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteSss(Map<String, Object> pmParam) throws Exception {
    	if (true) {
    		throw new Exception("사용하지 않는 메소드입니다.");
    	}
        return update("EventStockMngMap.sss", pmParam);
    }
}
