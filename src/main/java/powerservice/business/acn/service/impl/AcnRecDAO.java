package powerservice.business.acn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * * 나이스 평가정보사의 신용정보 조회 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/09/01
 * @프로그램ID NiceCredit
 */
@Repository
public class AcnRecDAO extends EgovAbstractMapper {
    /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession
     *            대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name = "gongSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    public int insertAcuonRecHist(Map<String, Object> map) {
        return (Integer) insert("AcnRecMap.insertAcuonRecHist", map);
    }
    public int updateAcuonRecHist(Map<String, Object> map) {
        return (Integer) update("AcnRecMap.updateAcuonRecHist", map);
    }
    public void deleteAcuonRecHist(Map<String, Object> map) {
        insert("AcnRecMap.deleteAcuonRecHist", map);
    }
    public void deleteAcuonRecHistBySdate(Map<String, Object> map) {
        insert("AcnRecMap.deleteAcuonRecHistBySdate", map);
    }
    public List<Map<String, Object>> getAcuonRecHistList(Map<String, Object> mapCond) {
        return selectList("AcnRecMap.getAcuonRecHist", mapCond);
    }
    public Map<String, Object> getAcuonRecHist(Map<String, Object> mapCond) {
        return selectOne("AcnRecMap.getAcuonRecHist", mapCond);
    }

    /* MG월별 파일 전송 리스트  */
    public List<Map<String, Object>> getMgFileTransList(Map<String, Object> mapCond) {
        return selectList("AcnRecMap.getMgFileTransList", mapCond);
    }

    /* MG월별 파일 전송 인서트  */
    public int insertMgFileTransList(Map<String, Object> map) {
        return (Integer) insert("AcnRecMap.insertMgFileTransList", map);
    }
}
