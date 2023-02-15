package powerservice.business.acn.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import powerservice.business.acn.service.AcnTranService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
@Repository
public class AcnTranServiceImpl extends EgovAbstractServiceImpl implements AcnTranService{



      @Resource
      public AcnTranDao acnTranDAO;

    /**
           * 전송월 기준의 검색 수를 반환한다.
           *
           * @param pmParam 검색 조건
           * @return   검색 건수
           * @throws Exception
           */
          public int getTranMonCount(Map<String, ?> pmParam) throws Exception {
             return (Integer) acnTranDAO.getTranMonCount(pmParam);

          }
          public int getTranMonerrCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.getTranMonerrCount(pmParam);

           }
          public int getselectresultCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.getselectresultCount(pmParam);

           }

          public int getsuTranMonCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.getsuTranMonCount(pmParam);

           }

          public int getsuCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.getsuCount(pmParam);

           }



          public int updateconvertResult(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.updateconvertResult(pmParam);

           }

          public int updatesuconvertResult(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.updatesuconvertResult(pmParam);

           }




          public int getrecacoCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.getrecacoCount(pmParam);

           }



          public int deleteCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.deleteCount(pmParam);

           }

          public int deletesunapCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) acnTranDAO.deletesunapCount(pmParam);

           }



          /**
           * 전송월 기준 목록
           *
           * @param pmParam 검색 조건
           * @return   목록
           * @throws Exception
           */
          public List<Map<String, Object>> getTranMonList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getTranMonList(pmParam);

          }


          public List<Map<String, Object>> getTranMonerrList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getTranMonerrList(pmParam);

          }

          public List<Map<String, Object>> gettranseletresultList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.gettranseletresultList(pmParam);

          }


          public List<Map<String, Object>> getsuList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getsuList(pmParam);

          }




          //녹취조회리스트

          public List<Map<String, Object>> getrecacoList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getrecacoList(pmParam);

          }




        //reject조회리스트

          public List<Map<String, Object>> getrejectList(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getrejectList(pmParam);

          }







          public List<Map<String, Object>> getinsertAClist(Map<String, ?> pmParam) throws Exception {
              return acnTranDAO.getinsertAClist(pmParam);

      }

          /**
           * 전송월을 검색한다.
           *
           * @param psId 전송월
           * @return 고객 정보(1건)
           * @throws Exception
           */

         // private final Logger LOGGER = LoggerFactory.getLogger(AcnTranServiceImpl.class);

          public List<Map<String, Object>> insertAClist(Map<String, ?> pmParam) throws Exception {
             System.out.println("Enter insertAClist");
             acnTranDAO.insertAClist(pmParam);

             return acnTranDAO.getTranMonList(pmParam);

          }


          public List<Map<String, Object>> suinsertAClist(Map<String, ?> pmParam) throws Exception {
           //   System.out.println("Enter insertAClist");
              acnTranDAO.suinsertAClist(pmParam);

              return acnTranDAO.sugetTranMonList(pmParam);

           }


         /* *
          * [[신규 탭]]에서 사용하는 조회 / 정보변경 / 리스트 출력 메소드
          *
          */
         /// 신규 조회 총 count
         public int getTranNewCount(Map<String, ?> pmParam) throws Exception {
             return (Integer) acnTranDAO.getTranNewCount(pmParam);

          }

         public int getTranacuontCount(Map<String, ?> pmParam) throws Exception {
             return (Integer) acnTranDAO.getTranacuontCount(pmParam);

          }

         public int getTranacuonsilCount(Map<String, ?> pmParam) throws Exception {
             return (Integer) acnTranDAO.getTranacuonsilCount(pmParam);

          }


         public List<Map<String, Object>> getTranNewList(Map<String, ?> pmParam) throws Exception {
             return acnTranDAO.getTranNewList(pmParam);

         }

         public List<Map<String, Object>> getTranYList(Map<String, ?> pmParam) throws Exception {
             return acnTranDAO.getTranYList(pmParam);

         }


         public List<Map<String, Object>> getYHjList(Map<String, ?> pmParam) throws Exception {
             return acnTranDAO.getYHjList(pmParam);

         }

         public List<Map<String, Object>> getUpdateInfo(Map<String, ?> pmParam) throws Exception {
             return acnTranDAO.getUpdateInfo(pmParam);
         }


         public int acuonupdate(Map<String, ?> pmParam) throws Exception {
             return (Integer) acnTranDAO.acuonupdate(pmParam);

          }


}
