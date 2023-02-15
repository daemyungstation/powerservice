package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwMemOcbDao extends EgovAbstractMapper{

       /**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /**
     * OCB 카드코드별 카드번호 수량
     **/
    public List<Map<String, Object>> getSrchOcbCardCntList(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectList("DlwMemOcbMap.getSrchOcbCardCntList", pmParam);
    }

  /*  public List<Map<String, Object>> getnewList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
        CommonUtils.printLog(pmParam);
      return selectList("DlwMemOcbMap.getnewList", pmParam);
  }
    public List<Map<String, Object>> getocb_rejectList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
        CommonUtils.printLog(pmParam);
      return selectList("DlwMemOcbMap.getocb_rejectList", pmParam);
  }*/

    public List<Map<String, Object>> getSrchOcbCardCodeList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectList("DlwMemOcbMap.getSrchOcbCardCodeList", pmParam);
    }



    /**
     * OCB 카드 코드 등록
     **/

    public int insrtOcbCardCode(Map<String, ?> pmParam) {
        return insert("DlwMemOcbMap.insrtOcbCardCode", pmParam);
    }


    /**
     * OCB포인트 insert     **/

    public int insrtOcbpoint(Map<String, ?> pmParam) {
        return insert("DlwMemOcbMap.insrtOcbpoint", pmParam);
    }

    /*
     *  OCB 카드 코드 삭제
     */
    public int deleteCardCode(Map<String, ?> pmParam) throws Exception {
        return delete("DlwMemOcbMap.deleteCardCode", pmParam);
    }

    /*
     *  OCB  삭제
     */
    public int deleteocb(Map<String, ?> pmParam) throws Exception {
        return delete("DlwMemOcbMap.deleteocb", pmParam);
    }

    /**
     *
     **/
    public int getOcbCardCodeSeq(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwMemOcbMap.getOcbCardCodeSeq", pmParam);
    }



    /**
     * OCB 카드 유효 카드 조회
     **/
    public List<Map<String, Object>> getSrchMemYHCardList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectList("DlwMemOcbMap.getSrchMemYHCardList", pmParam);
    }

    public List<Map<String, Object>> getSrchMemCrtFileList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectList("DlwMemOcbMap.getSrchMemCrtFileList", pmParam);
    }



    /**
     * OCB 포인트 산출 리스트
     **/
    public List<Map<String, Object>> getsrchOcbPointList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectList("DlwMemOcbMap.getsrchOcbPointList", pmParam);
    }

    // OCB 포인트 산출 전체 수
    public int getOcbPointCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("DlwMemOcbMap.getOcbPointCount", pmParam);
    }


    // OCB 포인트 산출  조회전체 수
    public int getsrchOcbPointcount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMemOcbMap.getsrchOcbPointcount", pmParam);
    }

    /**
     * OCB 포인트 이력 조회
     **/
 // OCB 포인트이력 조회


    public List<Map<String, Object>> getsrchOcbPointhist(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      return selectList("DlwMemOcbMap.getsrchOcbPointhist", pmParam);
    }


    // OCB 포인트 산출이력  조회전체 수
    public int getOcbPointHistCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMemOcbMap.getOcbPointHistCount", pmParam);
    }


    //발급
    public int ocb_save_start(Map<String, ?> pmParam) throws Exception {
        return  insert("DlwMemOcbMap.ocb_save_start", pmParam);
    }
    //재발급
    public int ocb_save_start_re(Map<String, ?> pmParam) throws Exception {
        return  insert("DlwMemOcbMap.ocb_save_start_re", pmParam);
    }


    //모바일발급
    public int ocb_m_update(Map<String, ?> pmParam) throws Exception {
        return  update("DlwMemOcbMap.ocb_m_update", pmParam);
    }
    /**
    *  OCB 발급 미생전 삭제
    */
   public int delete_ocb_result(Map<String, ?> pmParam) throws Exception {
       return delete("DlwMemOcbMap.delete_ocb_result", pmParam);
   }


    // 업데이트
    public int ocb_update_hist(Map<String, ?> pmParam) throws Exception {
          return update("DlwMemOcbMap.ocb_update_hist", pmParam);
    }


    public int ocb_update_start(Map<String, Object> hmParam) {
     //   CommonUtils.printLog(hmParam);

      String ocb_cd = (String) hmParam.get("cbo_ocbgubun");
      if ("N".equals(ocb_cd)) {
           return update("DlwMemOcbMap.ocb_update_start_RE", hmParam);   ///리조트
      } else {
          return update("DlwMemOcbMap.ocb_update_start", hmParam);     // ocb
      }

    }


    public int ocb_point_update_start(Map<String, Object> hmParam) throws Exception {
       CommonUtils.printLog(hmParam);
              return update("DlwMemOcbMap.ocb_point_update_start", hmParam);   ///포인트 결과처리

       }

    public List<Map<String, Object>> getocb_rejectList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
      //  CommonUtils.printLog(pmParam);
      return selectList("DlwMemOcbMap.getocb_rejectList", pmParam);
  }


    public List<Map<String, Object>> getnewList(Map<String, ?> pmParam) throws Exception {
    CommonUtils.printLog(pmParam);
     return selectList("DlwMemOcbMap.getnewList", pmParam);
  }

    public List<Map<String, Object>> getOCBList(Map<String, ?> pmParam) throws Exception {
        System.out.println("=========Enter DAO");
        CommonUtils.printLog(pmParam);
      return selectList("DlwMemOcbMap.getOCBList", pmParam);
  }

    public int insertOcbpointcnt(Map<String,?> pmParam) throws Exception {
       return selectOne("DlwMemOcbMap.insertOcbpointcnt", pmParam);
  }

    /* OCB,멤버쉽 카드발급신청 엑셀업로드 */
    public int uploadIssueExcelList(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMemOcbMap.uploadIssueExcelList", pmParam);
    }

    /* OCB,멤버쉽 카드발급신청 카운트조회 */
    public int getIssueListCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMemOcbMap.getIssueListCnt", pmParam);
    }

    /* OCB,멤버쉽 카드발급신청 조회 */
    public List<Map<String, Object>> getIssueList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwMemOcbMap.getIssueList", pmParam);
    }

    /* OCB,멤버쉽 카드 반송처리 상담기록 */
    public int saveConsRetrunCard(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMemOcbMap.saveConsRetrunCard", pmParam);
    }

    /* OCB,멤버쉽 카드 반송처리 상담이력기록 */
    public int saveConsHstrRetrunCard(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMemOcbMap.saveConsHstrRetrunCard", pmParam);
    }

    /* OCB,멤버쉽 카드요청 삭제처리 */
    public int delRequestIssue(Map<String, ?> pmParam) throws Exception {
        return delete("DlwMemOcbMap.delRequestIssue", pmParam);
    }

    /* OCB,멤버쉽 발급진행중인 회원 체크 */
    public int chkIssuingStat(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMemOcbMap.chkIssuingStat", pmParam);
    }

    /* OCB,멤버쉽 카드 반송처리시 반송일자 UPDATE */
    public int updateReturnDt(Map<String, ?> pmParam) throws Exception {
        return update("DlwMemOcbMap.updateReturnDt", pmParam);
    }

}
