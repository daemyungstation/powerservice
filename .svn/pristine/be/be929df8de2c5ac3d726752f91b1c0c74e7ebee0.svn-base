package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.dlw.service.DlwBasiDataMngService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service
public class DlwBasiDataMngServiceImpl extends EgovAbstractServiceImpl implements DlwBasiDataMngService{

      @Resource
        public DlwBasiDataMngDao dlwBasiDataMngDao;

      //지부 관리자 리스트
      public List<Map<String, Object>> getbranchList(Map<String, ?> pmParam) throws Exception {
            return dlwBasiDataMngDao.getbranchList(pmParam);
        }

    //장례식장 코드가져오기
      public List<Map<String, Object>> get_funrl_code(Map<String, ?> pmParam) throws Exception {
            return dlwBasiDataMngDao.get_funrl_code(pmParam);
        }
    //지부 코드가져오기
      public List<Map<String, Object>> get_branch_code(Map<String, ?> pmParam) throws Exception {
            return dlwBasiDataMngDao.get_branch_code(pmParam);
        }

    //장례식장 관리자 리스트
      public List<Map<String, Object>> getjangList(Map<String, ?> pmParam) throws Exception {
            return dlwBasiDataMngDao.getjangList(pmParam);
        }

      public int branch_insert(Map<String, ?> pmParam) throws Exception {

          return dlwBasiDataMngDao.branch_insert(pmParam);
      }
       public int branch_update(Map<String, ?> pmParam) throws Exception {
           return  dlwBasiDataMngDao.branch_update(pmParam);

        }
       public int branch_delete(Map<String, ?> pmParam) throws Exception {
           return  dlwBasiDataMngDao.branch_delete(pmParam);

        }
       //wㅣ부 조회 1건
       public List<Map<String, Object>> branch_select(Map<String, ?> pmParam) throws Exception {
             return dlwBasiDataMngDao.branch_select(pmParam);
         }

       //장례식장 조회 1건
       public List<Map<String, Object>> funrl_select(Map<String, ?> pmParam) throws Exception {
             return dlwBasiDataMngDao.funrl_select(pmParam);
         }


     //==========================================================================================================

       //거래처 관리자 리스트
         public List<Map<String, Object>> getCustmrMngList(Map<String, ?> pmParam) throws Exception {
               return dlwBasiDataMngDao.getCustmrMngList(pmParam);
           }
         //거래처 코드가져오기
         public List<Map<String, Object>> getCustmrCd(Map<String, ?> pmParam) throws Exception {
               return dlwBasiDataMngDao.getCustmrCd(pmParam);
           }

        /****   거래처 등록   ****/
        public String insrtCustmrMng(Map<String, Object> pmParam) throws Exception {

            int dupl_custmrCd = dlwBasiDataMngDao.getDuplCustmrCd(pmParam); //거래처 코드 중복 여부 가져옴

            if (dupl_custmrCd > 0) {
                                        return "중복";
                                    }
                String rslt = "1";
                dlwBasiDataMngDao.insrtCustmrMng(pmParam);
            return "1";
        }

        /* 거래처 삭제 */
        public int deleteCustmrMng(Map<String, ?> pmParam) throws Exception {
            return  dlwBasiDataMngDao.deleteCustmrMng(pmParam);
         }

        /* 거래처 수정 */
        public int updateCustmrMng(Map<String, Object> pmParam) throws Exception {
             ParamUtils.addSaveParam(pmParam);
                return  dlwBasiDataMngDao.updateCustmrMng(pmParam);

         }

    //===========================================================================
        //창고 관리 리스트
        public List<Map<String, Object>> getWHMngList(Map<String, ?> pmParam) throws Exception {
              return dlwBasiDataMngDao.getWHMngList(pmParam);
          }


        //창고 코드가져오기
        public List<Map<String, Object>> getWHCd(Map<String, ?> pmParam) throws Exception {
              return dlwBasiDataMngDao.getWHCd(pmParam);
          }

  // ============================		행사자 		========================================================
      //행사자 관리자 리스트
        public List<Map<String, Object>> getEvtManaMngList(Map<String, ?> pmParam) throws Exception {
              return dlwBasiDataMngDao.getEvtManaMngList(pmParam);
          }

        //행사자 코드가져오기
        public List<Map<String, Object>> getEvtManaMngCd(Map<String, ?> pmParam) throws Exception {
              return dlwBasiDataMngDao.getEvtManaMngCd(pmParam);
          }

       /****   행사자 등록   ****/
       public String insrtEvtManaMng(Map<String, Object> pmParam) throws Exception {

           int dupl_custmrCd = dlwBasiDataMngDao.getDuplEvtManaMngCd(pmParam); //거래처 코드 중복 여부 가져옴

           if (dupl_custmrCd > 0) {
                                       return "중복";
                                   }
               String rslt = "";
               ParamUtils.addSaveParam(pmParam);
               dlwBasiDataMngDao.insrtEvtManaMng(pmParam);
           return "";
       }

       /* 행사자 삭제 */
       public int deleteEvtManaMng(Map<String, ?> pmParam) throws Exception {
           return  dlwBasiDataMngDao.deleteEvtManaMng(pmParam);
        }

       /* 행사자 수정 */
       public int updateEvtManaMng(Map<String, Object> pmParam) throws Exception {
           ParamUtils.addSaveParam(pmParam);
           return  dlwBasiDataMngDao.updateEvtManaMng(pmParam);

        }

       /* 지부장 등록 건수 조회 */
       public int chkEvtManaMngCnt(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.chkEvtManaMngCnt(pmParam);
       }
 // ===============================	 물품 관리 	===================================================================

       /**
        * 조직정보 트리뷰
        *
        * @param pmParam 검색 조건
        * @return 조직도 트리
        * @throws Exception
       */
       public List<Map<String, Object>> selectEventGoodsList(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.selectEventGoodsList(pmParam);
       }

       /**
        * 행사물품관리 상세 조회
        *
        * @param pmParam 검색 조건
        * @return 조직도 트리
        * @throws Exception
       */
       public List<Map<String, Object>> selectBuyingList(Map<String, Object> pmParam) throws Exception {
           return dlwBasiDataMngDao.selectBuyingList(pmParam);
       }

       /**
        * 행사물품관리 마스터 입력
        *
        * @param pmParam 검색 조건
        * @return
        * @throws Exception
        */
       public int insertEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.insertEvntGoodsMst(pmParam);
       }

       /**
        * 행사물품관리 마스터 저장
        *
        * @param pmParam 검색 조건
        * @return
        * @throws Exception
        */
       public int updateEvntGoodsMst(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.updateEvntGoodsMst(pmParam);
       }


       /**
        * 행사물품관리 상세 정보 수정
        *
        * @param pmParam 검색 조건
        * @return
        * @throws Exception
        */
       public int updateEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.updateEvntGoodsDtl(pmParam);
       }

       /**
        * 행사물품관리 상세 정보 등록
        *
        * @param pmParam 검색 조건
        * @return
        * @throws Exception
        */
       public int insertEvntGoodsDtl(Map<String, ?> pmParam) throws Exception {
           return dlwBasiDataMngDao.insertEvntGoodsDtl(pmParam);
       }

       /**
        * 거래처 등록/수정/삭제
        *
        * @param XPlatformMapDTO xpDto
        * @return int
        * @throws Exception
        */
       public int saveCustmr(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           int iCudCnt = 0;
           int rowType = -1;
           Map hmParam = null;

           try {
               DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
               if (listInDs != null && listInDs.size() > 0) {
                       for (int i=0; i<listInDs.size(); ++i) {
                           hmParam = listInDs.get(i);
                           ParamUtils.addSaveParam(hmParam);


                           rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                           if (rowType == DataSet.ROW_TYPE_INSERTED) {
                               iCudCnt += dlwBasiDataMngDao.insrtCustmrMng(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                               iCudCnt += dlwBasiDataMngDao.updateCustmrMng(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_DELETED) {
                               iCudCnt += dlwBasiDataMngDao.deleteCustmrMng(hmParam);
                           }

                           mOut.put("custmr_cd", CommonUtils.nvls((String)hmParam.get("custmr_cd")));
                       }
               }
           } catch (Exception e) {
               throw e;
           }
               return iCudCnt;
       }

       /**
        * 장래식장 등록/수정/삭제
        *
        * @param XPlatformMapDTO xpDto
        * @return int
        * @throws Exception
        */
       public int saveFunrl(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           int iCudCnt = 0;
           int rowType = -1;
           Map hmParam = null;

           try {
               DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
               if (listInDs != null && listInDs.size() > 0) {
                       for (int i=0; i<listInDs.size(); ++i) {
                           hmParam = listInDs.get(i);
                           ParamUtils.addSaveParam(hmParam);

                           rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                           if (rowType == DataSet.ROW_TYPE_INSERTED) {
                               iCudCnt += dlwBasiDataMngDao.funrl_insert(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                               iCudCnt += dlwBasiDataMngDao.funrl_update(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_DELETED) {
                               iCudCnt += dlwBasiDataMngDao.funrl_delete(hmParam);
                           }

                           mOut.put("funrl_cd", CommonUtils.nvls((String)hmParam.get("funrl_cd")));
                       }
               }
           } catch (Exception e) {
               throw e;
           }
               return iCudCnt;
       }

       /**
        * 창고 등록/수정/삭제
        *
        * @param XPlatformMapDTO xpDto
        * @return int
        * @throws Exception
        */
       public int saveWarehouse(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           int iCudCnt = 0;
           int rowType = -1;
           Map hmParam = null;

           try {
               DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
               if (listInDs != null && listInDs.size() > 0) {
                       for (int i=0; i<listInDs.size(); ++i) {
                           hmParam = listInDs.get(i);
                           ParamUtils.addSaveParam(hmParam);

                           rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                           if (rowType == DataSet.ROW_TYPE_INSERTED) {
                               iCudCnt += dlwBasiDataMngDao.insrtWHMng(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                               iCudCnt += dlwBasiDataMngDao.updateWHMng(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_DELETED) {
                               iCudCnt += dlwBasiDataMngDao.deleteWHMng(hmParam);
                           }

                           mOut.put("wh_cd", CommonUtils.nvls((String)hmParam.get("wh_cd")));
                       }
               }
           } catch (Exception e) {
               throw e;
           }
               return iCudCnt;
       }


       //행사자 수당 기초 리스트
       public List<Map<String, Object>> getEvtMngrList (Map<String, Object> pmParam) throws Exception {
             return dlwBasiDataMngDao.getEvtMngrList(pmParam);
         }



       /**
        * 행사자 수당 기초 등록/수정/삭제
        *
        * @param XPlatformMapDTO xpDto
        * @return int
        * @throws Exception
        */
       public int saveEvtMngrAlow(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           int iCudCnt = 0;
           int rowType = -1;
           Map hmParam = null;

           try {
               DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
               if (listInDs != null && listInDs.size() > 0) {
                       for (int i=0; i<listInDs.size(); ++i) {
                           hmParam = listInDs.get(i);
                           ParamUtils.addSaveParam(hmParam);
                           /*hmParam.put("reg_man", hmParam.get("rgsr_id"));
                           hmParam.put("upd_man", hmParam.get("rgsr_id"));
*/

                           rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                           System.out.println("***********DataSet.ROW_TYPE_DELETED:"+DataSet.ROW_TYPE_DELETED);
                           if (rowType == DataSet.ROW_TYPE_INSERTED) {
                               System.out.println("===============입력"+hmParam.get("rgsr_id"));
                               iCudCnt += dlwBasiDataMngDao.insertEvtMngr(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                               iCudCnt += dlwBasiDataMngDao.updateEvtMngr(hmParam);
                           }
                           else if (rowType == DataSet.ROW_TYPE_DELETED) {
                               System.out.println("==============삭제");
                               iCudCnt += dlwBasiDataMngDao.deleteEvtMngr(hmParam);
                           }

                           mOut.put("evt_mngr_alow_cd", CommonUtils.nvls((String)hmParam.get("evt_mngr_alow_cd")));
                           //break;
                       }
               }
           } catch (Exception e) {
               throw e;
           }
               return iCudCnt;
       }
}
