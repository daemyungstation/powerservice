package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwAlowProportCalcDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
    public int getAlowProportAccntCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwAlowProportCalcMap.getAlowProportAccntCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportAccntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwAlowProportCalcMap.getAlowProportAccntList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public int getAlowProportProdCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwAlowProportCalcMap.getAlowProportProdCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwAlowProportCalcMap.getAlowProportProdList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 수당안분계산대상 고객업로드(임시 테이블 삭제)
     * 대상 테이블 : LF_DMUSER.TMP_PROPORT_CALC
     * ================================================================
     * */
    public int deleteTmpProportCalc(Map<String, ?> pmParam) {
    	return delete("DlwAlowProportCalcMap.deleteTmpProportCalc", pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 수당안분계산대상 고객업로드(대상 고객 업로드)
     * 대상 테이블 : LF_DMUSER.TMP_PROPORT_CALC
     * ================================================================
     * */
	public int insertAlowAccntExcel(Map<String, ?> pmParam) {
		return insert("DlwAlowProportCalcMap.insertAlowAccntExcel", pmParam);
	}
}
