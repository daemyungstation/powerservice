package powerservice.business.acn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class AcnTranResultDao extends EgovAbstractMapper{

    /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="gongSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
        System.out.println();
    }

    // 전송월로 검색한 처리구분 결과 count
    public int getTranResulCount(Map<String, ?> pmParam) throws Exception {
        System.out.println("Enter AcnTranResultDao");
       return (Integer) selectOne("AcnTranResultMap.getTranResulCount", pmParam);
    }


    //전송월로 검색한 처리구분 리스트로 출력
    public List<Map<String, Object>> getTranResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("AcnTranResultMap.getTranResultList", pmParam);
    }

    //전송월로 검색한 처리구분 리스트로 출력
    public List<Map<String, Object>> getTranResultListSunab(Map<String, ?> pmParam) throws Exception {
        return selectList("AcnTranResultMap.getTranResultListSunab", pmParam);
    }

     //전송월로 검색한 처리구분 리스트로 출력
     public List<Map<String, Object>> getTranMonResultList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranResultMap.getTranMonResultList", pmParam);
     }
     
     public List<Map<String, Object>> getacoyencheResultList(Map<String, ?> pmParam) throws Exception {
         return selectList("AcnTranResultMap.getacoyencheResultList", pmParam);
  }
     /**
      * ???
      *
      * @param pmParam ???
      * @throws Exception
      */
     public int convertTranMonMainResult(List<Map<String, Object>> plAarg) throws Exception {

       //  CommonUtils.printLog(plAarg);

         return update("AcnTranResultMap.convertTranMonMainResult", plAarg);
     }

     /**
      * ???
      *
      * @param pmParam ???
      * @throws Exception
      */
     public int processTranMonMainResult(Map<String, ?> pmParam) throws Exception {
         return update("AcnTranResultMap.processTranMonMainResult", pmParam);
     }

}
