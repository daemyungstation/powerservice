package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwOthersDao extends EgovAbstractMapper{

    @Resource(name="dlwSqlSession")
      public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
          super.setSqlSessionFactory(sqlSession);
      }

    //회사정보 조회
    public List<Map<String, Object>> getUseEntrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOthersMap.getUseEntrList", pmParam);
    }
  //회사정보 수정
    public int updateUseEntr(Map<String, Object> hmParam) {
        return update("DlwOthersMap.updateUseEntr", hmParam);
    }



    // CMS 기초환경설정 조회
    public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwCmsMap.getDlwCmsComnInfo", pmParam);
      }
    // CMS 기초환경설정 수정
   public int updateCMSComInfo(Map<String, Object> hmParam) {
        return update("DlwCmsMap.updateCMSComInfo", hmParam);
    }



   //그룹코드 조회
   public List<Map<String, Object>> getGroupCdList(Map<String, ?> pmParam) throws Exception {
       System.out.println("* * * * * **  DAO * * * * ");
       return selectList("DlwOthersMap.getGroupCdList", pmParam);
   }
   //그룹코드 삽입
   public int insertComCdGrp(Map<String, ?> pmParam) throws Exception {
       return  insert("DlwOthersMap.insertComCdGrp", pmParam);
   }
   // 그룹코드 수정
   public int updateComCdGrp(Map<String, Object> hmParam) {
        return update("DlwCmsMap.updateComCdGrp", hmParam);
    }







   //공통 코드 조회
   public List<Map<String, Object>> selectComCdGrpLis(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwOthersMap.selectComCdGrpLis", pmParam);
   }
   //공통 코드 삽입
   public int insertComCd(Map<String, ?> pmParam) throws Exception {
       return  insert("DlwOthersMap.insertComCd", pmParam);
   }
   // 공통 코드 수정
   public int updateComCd(Map<String, Object> hmParam) {
        return update("DlwOthersMap.updateComCd", hmParam);
    }

   //공통 코드 삽입
   public int deleteComcd(Map<String, ?> pmParam) throws Exception {
       return  insert("DlwOthersMap.deleteComcd", pmParam);
   }



 //불능 코드 조회
   public List<Map<String, Object>> getImpsCdList(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwOthersMap.getImpsCdList", pmParam);
   }

   //불능 코드 중복 조회
   public int getDuplImpsCd(Map<String, ?> pmParam) throws Exception {
       System.out.println("=========Enter DAO");
     return selectOne("DlwOthersMap.getDuplImpsCd", pmParam);
   }
   //불능 코드 삽입
   public int insertImpsCd(Map<String, Object> pmParam) throws Exception {
       return  insert("DlwOthersMap.insertImpsCd", pmParam);
   }

 //불능 코드 삭제
   public int deleteImpsCd(Map<String, ?> pmParam) throws Exception {
       return  insert("DlwOthersMap.deleteImpsCd", pmParam);
   }



   //월목표건수 조회
     public List<Map<String, Object>> selectMonTargetNoList(Map<String, ?> pmParam) throws Exception {
         return selectList("DlwOthersMap.selectMonTargetNoList", pmParam);
     }

     //월목표건수 LOC_CD 중복 조회  ------- 아직 만들지는 않음
    /* public int getDuplLocCd(Map<String, ?> pmParam) throws Exception {
         System.out.println("=========Enter DAO");
       return selectOne("DlwOthersMap.getDuplLocCd", pmParam);
     }*/
     //월목포건수 삽입
     public int insertMonTargetNo(Map<String, Object> pmParam) throws Exception {
         return  insert("DlwOthersMap.insertMonTargetNo", pmParam);
     }

   //월목포건수 삭제
     public int deleteMonTargetNo(Map<String, ?> pmParam) throws Exception {
         return  insert("DlwOthersMap.deleteMonTargetNo", pmParam);
     }

     /**
      * 반송 발송 대상자 조회 count
      *
      * @param pmParam 검색 조건
      * @return 고객 정보의 검색 건수
      * @throws Exception
      */
     public int getPostSendListCount(Map<String, ?> pmParam) throws Exception {
         return (Integer) selectOne("DlwOthersMap.getPostSendListCount", pmParam);
     }


     /**
      * 반송 발송 대상자 조회
      *
      * @param pmParam 검색 조건
      * @return 고객 정보 목록
      * @throws Exception
      */
     public List<Map<String, Object>> getPostSendList(Map<String, ?> pmParam) throws Exception {
         return selectList("DlwOthersMap.getPostSendList", pmParam);
     }

     /**
      * 회원번호의 증서가 있는지 알기위해 회원번호 개수
      *
      * @param pmParam 검색 조건
      * @return Dlv 정보의 검색 수
      * @throws Exception
      */
     public int getDlvAccntCount(Map<String, ?> pmParam) throws Exception {
         return (Integer) selectOne("DlwOthersMap.getDlvAccntCount", pmParam);
     }

     /**
      * 신규 발송 / 반송 MASTER 등록한다.
      *
      * @param pmParam 검색 조건
      * @return insert 결과
      * @throws Exception
      */
     public int insertDlv(Map<String, ?> pmParam) throws Exception {
         return insert("DlwOthersMap.insertDlv", pmParam);
     }

     /**
      * 신규 발송 / 반송  DTL 등록한다.
      *
      * @param pmParam 검색 조건
      * @return insert 결과
      * @throws Exception
      */
     public int insertDlvDtl(Map<String, ?> pmParam) throws Exception {
         return insert("DlwOthersMap.insertDlvDtl", pmParam);
     }

     /**
      * 신규 발송 / 반송  DTL 업데이트 한다.
      *
      * @param pmParam 검색 조건
      * @return Dlv 리스트
      * @throws Exception
      */
     public int updateDlvDtl(Map<String, ?> pmParam) throws Exception {
         return update("DlwOthersMap.updateDlvDtl", pmParam);
     }

     /**
      * 발송 / 반송 상세 정보의 검색 리스트를 반환한다.
      *
      * @param pmParam 검색 조건
      * @return 물류상세 리스트
      * @throws Exception
      */

     public List<Map<String, Object>> getPostSendDtlList(Map<String, ?> pmParam) throws Exception {
         return selectList("DlwOthersMap.getPostSendDtlList", pmParam);
     }

     /**
      * 전화번호 엘셀 업로드 데이터 회원조회결과
      *
      * @param pmParam 검색 조건
      * @return 물류상세 리스트
      * @throws Exception
      */

     public List<Map<String, Object>> getInfoofCellList(Map<String, ?> pmParam) throws Exception {
         return selectList("DlwOthersMap.getInfoofCellList", pmParam);
     }

     /**
      * 업로드된 회원번호 insert
      *
      * @param pmParam 검색 조건
      * @return insert 결과
      * @throws Exception
      */
     public int insertAccnt(Map<String, ?> pmParam) throws Exception {
         return insert("DlwOthersMap.insertAccnt", pmParam);
     }

     /**
      * 업로드된 회원번호 delete
      *
      * @param pmParam 검색 조건
      * @return insert 결과
      * @throws Exception
      */
     public int deleteAccnt() throws Exception {
         return delete("DlwOthersMap.deleteAccnt", "");
     }



}
