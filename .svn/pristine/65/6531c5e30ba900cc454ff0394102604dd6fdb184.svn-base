package powerservice.business.acn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;



@Repository
public class AcnTranDao extends EgovAbstractMapper{

    /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="gongSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


/* * * * * *     [[ 메인산출 탭 ]]    * * * * * */


 // 총 검색 결과 count
    public int getrecacoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getrecacoCount", pmParam);
    }

    // 업데이트
    public int updatesuconvertResult(Map<String, ?> pmParam) throws Exception {
          return update("AcnTranMap.updatesuconvertResult", pmParam);
    }


    // 업데이트
    public int updateconvertResult(Map<String, ?> pmParam) throws Exception {
          return update("AcnTranMap.updateconvertResult", pmParam);
    }



 // 총 검색 결과 count
    public int getTranMonCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getTranMonCount", pmParam);
    }
    // 총 검색 결과 count
    public int getTranMonerrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getTranMonerrCount", pmParam);
    }

 // 총 검색 결과 count
    public int getselectresultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getselectresultCount", pmParam);
    }
    // 총 검색 결과 count
    public int getsuTranMonCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getsuTranMonCount", pmParam);
    }

 // 총 검색 결과 count
    public int getsuCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcnTranMap.getsuCount", pmParam);
    }



    // 삭제카운트 count
    public int deleteCount(Map<String, ?> pmParam) throws Exception {
        return delete("AcnTranMap.deleteCount", pmParam);
    }


    // 삭제카운트 count
    public int deletesunapCount(Map<String, ?> pmParam) throws Exception {
        return delete("AcnTranMap.deletesunapCount", pmParam);
    }




    //녹취 결과 리스트로 출력
    public List<Map<String, Object>> getrecacoList(Map<String, ?> pmParam) throws Exception {
           return selectList("AcnTranMap.getrecacoList", pmParam);
    }

  //reject결과 리스트로 출력
    public List<Map<String, Object>> getrejectList(Map<String, ?> pmParam) throws Exception {
           return selectList("AcnTranMap.getrejectList", pmParam);
    }




    //검색된 결과 리스트로 출력
     public List<Map<String, Object>> getTranMonList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getTranMonList", pmParam);
     }

     //검색된 결과 리스트로 출력
     public List<Map<String, Object>> getTranMonerrList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getTranMonerrList", pmParam);
     }

     public List<Map<String, Object>> gettranseletresultList(Map<String, ?> pmParam) throws Exception {
         return selectList("AcnTranMap.gettranseletresultList", pmParam);
     }



   //검색된 결과 리스트로 출력
     public List<Map<String, Object>> getsuList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getsuList", pmParam);
     }




   //수납대행검색된 결과 리스트로 출력
     public List<Map<String, Object>> sugetTranMonList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.sugetTranMonList", pmParam);
     }

     //생성
     public int insertAClist(Map<String, ?> pmParam) throws Exception {
         return update("AcnTranMap.insertAClist", pmParam);

     }

     //수납생성
     public int suinsertAClist(Map<String, ?> pmParam) throws Exception {
         return update("AcnTranMap.suinsertAClist", pmParam);

     }
   //생성조회
     public List<Map<String, Object>> getinsertAClist(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getinsertAClist", pmParam);
     }


/* * * * *		 [[신규 탭]]		* * * * */


     // 신규 총 검색 결과 count
     public int getTranNewCount(Map<String, ?> pmParam) throws Exception {
         return (Integer) selectOne("AcnTranMap.getTranNewCount", pmParam);
     }


     public int getTranacuontCount(Map<String, ?> pmParam) throws Exception {
         return (Integer) selectOne("AcnTranMap.getTranacuontCount", pmParam);
     }


     public int getTranacuonsilCount(Map<String, ?> pmParam) throws Exception {
         return (Integer) selectOne("AcnTranMap.getTranacuonsilCount", pmParam);
     }



     //검색된 결과 리스트로 출력
     public List<Map<String, Object>> getTranNewList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getTranNewList", pmParam);
     }

     public List<Map<String, Object>> getTranYList(Map<String, ?> pmParam) throws Exception {
         return selectList("AcnTranMap.getYHjList", pmParam);
     }


     //검색된 결과 리스트로 출력
     public List<Map<String, Object>> getYHjList(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getYHjList", pmParam);
     }


     //검색된 결과 리스트로 출력

     public List<Map<String, Object>> getUpdateInfo(Map<String, ?> pmParam) throws Exception {
            return selectList("AcnTranMap.getUpdateInfo", pmParam);
     }

    ///  애큐온 회원정보 업데이트

     public int acuonupdate(Map<String, ?> pmParam) throws Exception {

      //   CommonUtils.printLog(pmParam);
         return update("AcnTranMap.acuonupdate", pmParam);
     }



}
