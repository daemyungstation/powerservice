package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 기타관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/12/27
 * @프로그램ID 
 */
@Repository
public class EtcDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
	    super.setSqlSessionFactory(sqlSession);
	}	
	
	/**
     * 카드 수료율 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectCardFeeRtMngList(Map<String, Object> pmParam) throws Exception {
        return selectList("EtcMap.selectCardFeeRtMngList", pmParam);
    }
    
    /**
     * 부서별 IP 정보 목록 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectDeptIPInfoList(Map<String, Object> pmParam) throws Exception {
        return selectList("EtcMap.selectDeptIPInfoList", pmParam);
    }
    
    /**
     * 부서별 IP 정보 목록 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteDeptIPInfoList(Map<String, Object> pmParam) throws Exception {
        return update("EtcMap.deleteDeptIPInfoList", pmParam);
    }
    
    /**
     * 카드 수수료율 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertCardFeeRt(Map<String, Object> pmParam) throws Exception {
        return update("EtcMap.insertCardFeeRt", pmParam);
    }
    
    /**
     * 카드 수수료율 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateCardFeeRt(Map<String, Object> pmParam) throws Exception {
        return update("EtcMap.updateCardFeeRt", pmParam);
    }
    
    /**
     * 부서별 IP 정보 목록 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateDeptIPInfoList(Map<String, Object> pmParam) throws Exception {
        return update("EtcMap.updateDeptIPInfoList", pmParam);
    }
    
    /**
     * 부서별 IP 정보 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertDeptIPInfoList(Map<String, Object> pmParam) throws Exception {
        return update("EtcMap.insertDeptIPInfoList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 조회수
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
    public int getCounselTargetCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("EtcMap.getCounselTargetCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
    public List<Map<String, Object>> getCounselTargetList(Map<String, ?> pmParam) throws Exception {
        return selectList("EtcMap.getCounselTargetList", pmParam);
    } 
    
    /** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 조회수
     * 대상 테이블 : 
     * ================================================================
     * */
    public int getDirectCounselCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("EtcMap.getDirectCounselCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 리스트 조회
     * 대상 테이블 : 
     * ================================================================
     * */
    public List<Map<String, Object>> getDirectCounselList(Map<String, ?> pmParam) throws Exception {
        return selectList("EtcMap.getDirectCounselList", pmParam);
    }
    
    /**
     * 다이렉트 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception {
        return update("EtcMap.updateDirectSessionClose", pmParam);
    }
}
