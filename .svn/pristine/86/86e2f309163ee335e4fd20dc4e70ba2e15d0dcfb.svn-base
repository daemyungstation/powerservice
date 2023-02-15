package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 행사 모니터링 정보 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Repository
public class EventMonitorDao extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 모니터링 질문지 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getMonitorQuestList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventMonitorMap.getMonitorQuestList", pmParam);
    }

    /**
     * 모니터링 질문지 등록
     *
     * @param pmParam NICE 신용등급 Safe-Key 발급 관련 정보
     * @throws Exception
     */
    public int insertMonitorQuest(Map<String, Object> pmParam) throws Exception {
        return insert("EventMonitorMap.insertMonitorQuest", pmParam);
    }

    /**
     * 모니터링 질문지 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateMonitorQuest(Map<String, Object> pmParam) throws Exception {
        return update("EventMonitorMap.updateMonitorQuest", pmParam);
    }

    /**
     * 모니터링 질문지 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteMonitorQuest(Map<String, Object> pmParam) throws Exception {
        return update("EventMonitorMap.deleteMonitorQuest", pmParam);
    }

    /**
     * 모니터링 결과보고서 조회
     *
     * @param pmParam 검색 조건
     * @return 모니터링결과보고서
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptList(Map<String, ?> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptList", pmParam);
    }

    /**
     * 모니터링 결과 보고서 등록
     *
     * @param pmParam NICE 신용등급 Safe-Key 발급 관련 정보
     * @throws Exception
     */
    public int insertMontrRpt(Map<String, Object> pmParam) throws Exception {
        return insert("EventMonitorMap.insertMontrRpt", pmParam);
    }

    /**
     * 모니터링 결과 보고서 등록 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateMontrRpt(Map<String, Object> pmParam) throws Exception {
        return update("EventMonitorMap.updateMontrRpt", pmParam);
    }

    /**
     * 모니터링 결과보고서 상세 조회
     *
     * @param pmParam 검색 조건
     * @return 모니터링결과보고서상세
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptDtl(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptDtl", pmParam);
    }

    /**
     * 모니터링 결과보고서 상세 등록
     *
     * @param pmParam NICE 신용등급 Safe-Key 발급 관련 정보
     * @throws Exception
     */
    public int insertMontrRptDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventMonitorMap.insertMontrRptDtl", pmParam);
    }

    /**
     * 모니터링 결과보고서 상세 삭제
     *
     * @param pmParam NICE 신용등급 Safe-Key 발급 관련 정보
     * @throws Exception
     */
    public int deleteMontrRptDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EventMonitorMap.deleteMontrRptDtl", pmParam);
    }

    /**
     * 행사 조회
     *
     * @param pmParam 검색 조건
     * @return 행사정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventInfo(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.getEventInfo", pmParam);
    }

    /**
     * 행사회원 여부 조회
     *
     * @param pmParam
     * @throws Exception
     */
    public String getEventAccntYn(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("EventMonitorMap.getEventAccntYn", pmParam);
    }

    /**
     * 지부별별 평균 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return 행사정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptAnalysisList", pmParam);
    }

    /**
     * 지부별 건수 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return 행사정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList1(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptAnalysisList1", pmParam);
    }

    /**
     * 행사자별 모니터링 현황 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return 행사정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList2(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptAnalysisList2", pmParam);
    }

    /**
     * 상품분류별 모니터링 현황 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList3(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.selectMontrRptAnalysisList3", pmParam);
    }

    /**
     * 의전행사 모니터링 상세 보고서 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMontrRptDtlList(Map<String, Object> pmParam) throws Exception {
        return selectList("EventMonitorMap.getMontrRptDtlList", pmParam);
    }

    /**
     * 의전행사 모니터링 상세 보고서 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return update("EventMonitorMap.updateMontrRptDtlBigo", pmParam);
    }

    /**
     * 의전행사 모니터링 상세 보고서 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return insert("EventMonitorMap.insertMontrRptDtlBigo", pmParam);
    }

    /**
     * 의전행사 모니터링 상세 보고서 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return update("EventMonitorMap.deleteMontrRptDtlBigo", pmParam);
    }
}
