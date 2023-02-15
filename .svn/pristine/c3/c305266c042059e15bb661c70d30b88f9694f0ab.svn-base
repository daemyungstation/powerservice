package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwBasiDataMngDao extends EgovAbstractMapper{

      @Resource(name="dlwSqlSession")
        public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
            super.setSqlSessionFactory(sqlSession);
        }

        public List<Map<String, Object>> getbranchList(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.getbranchList", pmParam);
        }

        public List<Map<String, Object>> getjangList(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.getjangList", pmParam);
        }

        public List<Map<String, Object>> funrl_select(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.funrl_select", pmParam);
        }

        public List<Map<String, Object>> branch_select(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.branch_select", pmParam);
        }

        public List<Map<String, Object>> get_funrl_code(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.get_funrl_code", pmParam);
        }

        public List<Map<String, Object>> get_branch_code(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.get_branch_code", pmParam);
        }


        public int funrl_insert(Map<String, ?> pmParam) throws Exception {
            return insert("DlwBasiDataMngMap.funrl_insert", pmParam);
        }

        public int branch_insert(Map<String, ?> pmParam) throws Exception {
            return insert("DlwBasiDataMngMap.branch_insert", pmParam);
        }

        public List<Map<String, Object>> get_funrl_insert(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.get_funrl_insert", pmParam);
        }

        public int funrl_update(Map<String, ?> pmParam) throws Exception {
            return update("DlwBasiDataMngMap.funrl_update", pmParam);
        }

        public int branch_update(Map<String, ?> pmParam) throws Exception {
            return update("DlwBasiDataMngMap.branch_update", pmParam);
        }

        public int funrl_delete(Map<String, ?> pmParam) throws Exception {   /// 삭제
            return delete("DlwBasiDataMngMap.funrl_delete", pmParam);
        }

        public int branch_delete(Map<String, ?> pmParam) throws Exception {   /// 삭제
            return delete("DlwBasiDataMngMap.branch_delete", pmParam);
        }




      // 거래처 조회
      public List<Map<String, Object>> getCustmrMngList(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.getCustmrMngList", pmParam);
        }

      //거래처 등록
      public int insrtCustmrMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtCustmrMng", pmParam);
      }

      //거래처 코드 가져오기
      public List<Map<String, Object>> getCustmrCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getCustmrCd", pmParam);
      }
      //거래처 코드 중복 조회
      public int getDuplCustmrCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplCustmrCd", pmParam);
      }
      // 거래처 삭제
      public int deleteCustmrMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteCustmrMng", pmParam);
      }

      // 거래처 수정
      public int updateCustmrMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateCustmrMng", pmParam);
      }



      //창고관리 등록
      public int insrtWHMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtWHMng", pmParam);
      }
      //창고관리 코드 가져오기
      public List<Map<String, Object>> getWHCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getWHCd", pmParam);
      }

      //창고관리 코드 중복 조회
      public int getDuplWHCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplWHCd", pmParam);
      }

      // 창고관리 삭제
      public int deleteWHMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteWHMng", pmParam);
      }

      // 창고관리 수정
      public int updateWHMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateWHMng", pmParam);
      }

      //창고 관리 리스트 조회
      public List<Map<String, Object>>  getWHMngList (Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getWHMngList", pmParam);
      }



      //행사자 관리 리스트 조회
      public List<Map<String, Object>>  getEvtManaMngList (Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtManaMngList", pmParam);
      }

      //행사자관리 등록
      public int insrtEvtManaMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtEvtManaMng", pmParam);
      }

      //행사자관리 코드 가져오기
      public List<Map<String, Object>> getEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtManaMngCd", pmParam);
      }
      //행사자관리 코드 중복 조회
      public int getDuplEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplEvtManaMngCd", pmParam);
      }

      // 행사자관리 삭제
      public int deleteEvtManaMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteEvtManaMng", pmParam);
      }

      // 행사자관리 수정
      public int updateEvtManaMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvtManaMng", pmParam);
      }

      // 지부장 등록건수 조회
      public int chkEvtManaMngCnt(Map<String, ?> pmParam) throws Exception {
          return selectOne("DlwBasiDataMngMap.chkEvtManaMngCnt", pmParam);
      }

      /**
       * 조직정보 트리뷰
       *
       * @param pmParam 검색 조건
       * @return 조직도 트리
       * @throws Exception
       */
      public List<Map<String, Object>> selectEventGoodsList(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.selectEventGoodsList", pmParam);
      }

      /**
       * 행사물품관리 상세 조회
       *
       * @param pmParam 검색 조건
       * @return
       * @throws Exception
       */
      public List<Map<String, Object>> selectBuyingList(Map<String, Object> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.selectBuyingList", pmParam);
      }

      /**
       * 행사물품관리 마스터 입력
       *
       * @param pmParam 검색 조건
       * @return
       * @throws Exception
       */
      public int insertEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvntGoodsMst", pmParam);
      }

      /**
       * 행사물품관리 마스터 저장
       *
       * @param pmParam 검색 조건
       * @return
       * @throws Exception
       */
      public int updateEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.updateEvntGoodsMst", pmParam);
      }

      /**
       * 행사물품관리 상세 정보 수정
       *
       * @param pmParam 검색 조건
       * @return
       * @throws Exception
       */
      public int updateEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvntGoodsDtl", pmParam);
      }

      /**
       * 행사물품관리 상세 정보 등록
       *
       * @param pmParam 검색 조건
       * @return
       * @throws Exception
       */
      public int insertEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvntGoodsDtl", pmParam);
      }

      //행사자 수당 기초 자료 리스트
      public List<Map<String, Object>> getEvtMngrList(Map<String, Object> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtMngrList", pmParam);
      }

      //행사자 수당 기초 자료 등록
      public int insertEvtMngr(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvtMngr", pmParam);
      }

      // 행사자 수당 기초 자료 수정
      public int updateEvtMngr(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvtMngr", pmParam);
      }

      // 행사자관리 삭제
      public int deleteEvtMngr(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteEvtMngr", pmParam);
      }
}
