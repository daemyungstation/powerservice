package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 사원관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/12/27
 * @프로그램ID 
 */
@Repository
public class EmpDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
	    super.setSqlSessionFactory(sqlSession);
	}	

	
	/**
     * 문서조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectDocKeep(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectDocKeep", pmParam);
    }
    
    /**
     * 회원 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectMemberList(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectMemberList", pmParam);
    }
    
    /**
     * 회원 조회2
     * - asis : selectMemberList
     * - 불필요하게 조회하는 컬럼이 너무 많아서 새로 만듬
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectMemberListByEmpleNo(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectMemberListByEmpleNo", pmParam);
    }
    
    /**
     * 자료권한 조회 조건 쿼리 생성
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public String createDataAuthQry(Map<String, Object> pmParam) throws Exception {
        return selectOne("EmpMap.createDataAuthQry", pmParam);
    }
    
    /**
     * 사번조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public String selectCreateEmpNo(Map<String, Object> pmParam) throws Exception {
        return selectOne("EmpMap.selectCreateEmpNo", pmParam);
    }
    
    /**
     * 사원 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectEmpleList(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectEmpleList", pmParam);
    }
    
    /**
     * 담당자변경 관리검색
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectEmpleMemChangeList(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectEmpleMemChangeList", pmParam);
    }
    
    /**
     * selectGrpEmpNoDupChk
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public String selectGrpEmpNoDupChk(Map<String, Object> pmParam) throws Exception {
        return selectOne("EmpMap.selectGrpEmpNoDupChk", pmParam);
    }
	
    /**
     * 사원 소속 변경 이력 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertDeptTransHist(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.insertDeptTransHist", pmParam);
    }
    
    /**
     * 사원등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertEmployee(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.insertEmployee", pmParam);
    }
    
    /**
     * 담당자변경 관리등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertMstrChgInf(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.insertMstrChgInf", pmParam);
    }
    
    /**
     * 사원퇴사 취소
     *
     * @param pmParam
     * @throws Exception
     */
    public int resinCancel(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.resinCancel", pmParam);
    }
    
    /**
     * 사원 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateEmployee(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.updateEmployee", pmParam);
    }
    
    /**
     * 사원인트라 비밀번호 초기화
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateEmployeeIntraPasswd(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.updateEmployeeIntraPasswd", pmParam);
    }
    
    /**
     * 사원 비밀번호 초기화
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateEmployeePasswd(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.updateEmployeePasswd", pmParam);
    }
    
    /**
     * 담당자변경작업 - 체크된회원만변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateMemProdAccntEmpleChange(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.updateMemProdAccntEmpleChange", pmParam);
    }
    
    /**
     * 상담내역 Detail 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertConsulDetail(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.insertConsulDetail", pmParam);
    }
    
    /**
     * insertConsulMng
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertConsulMng(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.insertConsulMng", pmParam);
    }
    
    /**
     * 담당자변경내역 등록 - 퇴사처리시
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertMstrChgInfResin(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.insertMstrChgInfResin", pmParam);
    }
    
    /**
     * 퇴사작업
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateEmployeeResin(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.updateEmployeeResin", pmParam);
    }
    
    /**
     * 퇴사작업 - 담당자변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateMemProdAccntEmple2(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.updateMemProdAccntEmple2", pmParam);
    }
    
    
    /**
     * 직위변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int updatePosnCd(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.updatePosnCd", pmParam);
    }
    
    /**
     * 직책변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateJobdutyCd(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.updateJobdutyCd", pmParam);
    }
    
    /**
     * 직위변동 이력 입력
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertHistPosnEmp(Map<String, Object> pmParam) throws Exception {
        return insert("EmpMap.insertHistPosnEmp", pmParam);
    }
    
    /**
     * selectDiscntPassMstlist
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectDiscntPassMstlist(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectDiscntPassMstlist", pmParam);
    }
    
    /**
     * 생성된 난수 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectRandNumList(Map<String, Object> pmParam) throws Exception {
        return selectList("EmpMap.selectRandNumList", pmParam);
    }
    
    /**
     * 할인우대권 번호 비고 변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int noteSave(Map<String, Object> pmParam) throws Exception {
        return update("EmpMap.noteSave", pmParam);
    }
}
