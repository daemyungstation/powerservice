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

        public int funrl_delete(Map<String, ?> pmParam) throws Exception {   /// ??????
            return delete("DlwBasiDataMngMap.funrl_delete", pmParam);
        }

        public int branch_delete(Map<String, ?> pmParam) throws Exception {   /// ??????
            return delete("DlwBasiDataMngMap.branch_delete", pmParam);
        }




      // ????????? ??????
      public List<Map<String, Object>> getCustmrMngList(Map<String, ?> pmParam) throws Exception {
            return selectList("DlwBasiDataMngMap.getCustmrMngList", pmParam);
        }

      //????????? ??????
      public int insrtCustmrMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtCustmrMng", pmParam);
      }

      //????????? ?????? ????????????
      public List<Map<String, Object>> getCustmrCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getCustmrCd", pmParam);
      }
      //????????? ?????? ?????? ??????
      public int getDuplCustmrCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplCustmrCd", pmParam);
      }
      // ????????? ??????
      public int deleteCustmrMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteCustmrMng", pmParam);
      }

      // ????????? ??????
      public int updateCustmrMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateCustmrMng", pmParam);
      }



      //???????????? ??????
      public int insrtWHMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtWHMng", pmParam);
      }
      //???????????? ?????? ????????????
      public List<Map<String, Object>> getWHCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getWHCd", pmParam);
      }

      //???????????? ?????? ?????? ??????
      public int getDuplWHCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplWHCd", pmParam);
      }

      // ???????????? ??????
      public int deleteWHMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteWHMng", pmParam);
      }

      // ???????????? ??????
      public int updateWHMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateWHMng", pmParam);
      }

      //?????? ?????? ????????? ??????
      public List<Map<String, Object>>  getWHMngList (Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getWHMngList", pmParam);
      }



      //????????? ?????? ????????? ??????
      public List<Map<String, Object>>  getEvtManaMngList (Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtManaMngList", pmParam);
      }

      //??????????????? ??????
      public int insrtEvtManaMng(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insrtEvtManaMng", pmParam);
      }

      //??????????????? ?????? ????????????
      public List<Map<String, Object>> getEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtManaMngCd", pmParam);
      }
      //??????????????? ?????? ?????? ??????
      public int getDuplEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
          System.out.println("=========Enter DAO");
        return selectOne("DlwBasiDataMngMap.getDuplEvtManaMngCd", pmParam);
      }

      // ??????????????? ??????
      public int deleteEvtManaMng(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteEvtManaMng", pmParam);
      }

      // ??????????????? ??????
      public int updateEvtManaMng(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvtManaMng", pmParam);
      }

      // ????????? ???????????? ??????
      public int chkEvtManaMngCnt(Map<String, ?> pmParam) throws Exception {
          return selectOne("DlwBasiDataMngMap.chkEvtManaMngCnt", pmParam);
      }

      /**
       * ???????????? ?????????
       *
       * @param pmParam ?????? ??????
       * @return ????????? ??????
       * @throws Exception
       */
      public List<Map<String, Object>> selectEventGoodsList(Map<String, ?> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.selectEventGoodsList", pmParam);
      }

      /**
       * ?????????????????? ?????? ??????
       *
       * @param pmParam ?????? ??????
       * @return
       * @throws Exception
       */
      public List<Map<String, Object>> selectBuyingList(Map<String, Object> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.selectBuyingList", pmParam);
      }

      /**
       * ?????????????????? ????????? ??????
       *
       * @param pmParam ?????? ??????
       * @return
       * @throws Exception
       */
      public int insertEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvntGoodsMst", pmParam);
      }

      /**
       * ?????????????????? ????????? ??????
       *
       * @param pmParam ?????? ??????
       * @return
       * @throws Exception
       */
      public int updateEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.updateEvntGoodsMst", pmParam);
      }

      /**
       * ?????????????????? ?????? ?????? ??????
       *
       * @param pmParam ?????? ??????
       * @return
       * @throws Exception
       */
      public int updateEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvntGoodsDtl", pmParam);
      }

      /**
       * ?????????????????? ?????? ?????? ??????
       *
       * @param pmParam ?????? ??????
       * @return
       * @throws Exception
       */
      public int insertEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvntGoodsDtl", pmParam);
      }

      //????????? ?????? ?????? ?????? ?????????
      public List<Map<String, Object>> getEvtMngrList(Map<String, Object> pmParam) throws Exception {
          return selectList("DlwBasiDataMngMap.getEvtMngrList", pmParam);
      }

      //????????? ?????? ?????? ?????? ??????
      public int insertEvtMngr(Map<String, ?> pmParam) throws Exception {
          return  insert("DlwBasiDataMngMap.insertEvtMngr", pmParam);
      }

      // ????????? ?????? ?????? ?????? ??????
      public int updateEvtMngr(Map<String, Object> pmParam) throws Exception {
          return update("DlwBasiDataMngMap.updateEvtMngr", pmParam);
      }

      // ??????????????? ??????
      public int deleteEvtMngr(Map<String, ?> pmParam) throws Exception {
          return delete("DlwBasiDataMngMap.deleteEvtMngr", pmParam);
      }
}
