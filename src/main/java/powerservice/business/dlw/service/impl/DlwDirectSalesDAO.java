package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwDirectSalesDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회수
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public int getDirectSalesChannelCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwDirectSalesMap.getDirectSalesChannelCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getDirectSalesChannelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDirectSalesMap.getDirectSalesChannelList", pmParam);
    }
}
