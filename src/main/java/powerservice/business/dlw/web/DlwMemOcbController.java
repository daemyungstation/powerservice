package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
//2018.03.23 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwMemOcbService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
/**
 * 멤버십 OCB 카드
 *
 */
@Controller
@RequestMapping(value = "/dlw/memOcb")
public class DlwMemOcbController {

     @Resource
     private DlwMemOcbService dlwMemOcbService;

     @Resource
     private CommonService commonService;

     @RequestMapping(value = "/srchOcbCardCntList")  //OCB 카드코드별 카드수량
     public ModelAndView getSrchOcbCardCntList(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # # # # # # # OCB 카드코드별 카드수량 컨트롤러# # # # # # # # # # # # #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



             System.out.println("---------- 신규1");

             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

             System.out.println("********** DS_INPUT *******"+srchInDs);


             if (srchInDs != null && srchInDs.size() > 0) {
                 hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                  System.out.println("---------- 신규2");
                  // 페이징 정보
                 /* DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                  if (listInDs != null && listInDs.size() > 0) {
                      Map pMap = listInDs.get(0);
                      hmParam.put("startLine", pMap.get("startNum"));
                      hmParam.put("endLine", pMap.get("endNum"));*/

                  }

                 System.out.println("---------- 신규2");

              // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                 List<Map<String, Object>> mList =dlwMemOcbService.getSrchOcbCardCntList(hmParam);
                 listMap.setRowMaps(mList);
                 mapOutDs.put("ds_output", listMap);

             System.out.println("---------- 3");

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }

     @RequestMapping(value = "/insrtOcbCardCode") // OCB 카드 코드 등록
     public ModelAndView insrtOcbCardCode(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("#  		카드 코드 등록            #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

             System.out.println("---------- 신규1");

             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

             System.out.println("********** DS_INPUT *******"+srchInDs);

             if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                 System.out.println("---------- 신규2");
                 String sResult = dlwMemOcbService.insrtOcbCardCode(hmParam);
                 if ("중복".equals(sResult)) {
                     throw new Exception("이미 존재하는 순번입니다.");
                 }
           }

             System.out.println("---------- 3");

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

       }

     /* * *  카드 코드 삭제   * * */
     @RequestMapping(value = "/deleteCardCode")
     public ModelAndView deleteNiceVrtlAccntWdrwList(XPlatformMapDTO xpDto, Model model) throws Exception {
         // = = = = = = = = = 카드 삭제 컨드롤러 = = = = = =
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode = "0";
         String szErrorMsg  = "OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

             DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

             hmParam = listInDs.get(0);
             dlwMemOcbService.deleteCardCode(hmParam);



             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;
     }



     /* * * ocb삭제 * * */
     @RequestMapping(value = "/ocb_del")
     public ModelAndView ocb_delList(XPlatformMapDTO xpDto, Model model) throws Exception {
         // = = = = = = = = = 카드 삭제 컨드롤러 = = = = = =
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode = "0";
         String szErrorMsg  = "OK";


         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

             DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

             //hmParam = listInDs.get(0);
             dlwMemOcbService.delete_ocb_result(listInDs);

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;
     }

     @RequestMapping(value = "/newlist")  //OCB 발급
     public ModelAndView getnewlist(XPlatformMapDTO xpDto, Model model) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
             hmParam = srchInDs.get(0);
             System.out.println("********** DS_INPUT *******"+srchInDs);


             List<Map<String, Object>> mList =dlwMemOcbService.getnewList(hmParam);
             listMap.setRowMaps(mList);
             mapOutDs.put("ds_output", listMap);

             //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
             DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

             if (listLogIn.size() > 0) {
                 hmParam = listLogIn.get(0);
                 commonService.insertLog(hmParam);
             }
             ////////////////////////////////////////////////////////////////////////////////////////////////////

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }

     @RequestMapping(value = "/ocb_reject")  //OCB 파일번현 리스트 조회
     public ModelAndView getocb_rejectlist(XPlatformMapDTO xpDto, Model model) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
             hmParam = srchInDs.get(0);
             System.out.println("********** DS_INPUT *******"+srchInDs);


              // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

             List<Map<String, Object>> mList =dlwMemOcbService.getocb_rejectList(hmParam);
               //  CommonUtils.printLog(mList);
             listMap.setRowMaps(mList);
             mapOutDs.put("ds_output", listMap);

             //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
             DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

             if (listLogIn.size() > 0) {
                 hmParam = listLogIn.get(0);
                 commonService.insertLog(hmParam);
             }
             ////////////////////////////////////////////////////////////////////////////////////////////////////

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }

     @RequestMapping(value = "/ocb_save")  //OCB 카드코드별 카드수량--OCB 발급진행
     public ModelAndView getocb_save(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # # # # # # # OCB 발급진행# # # # # # # # # # # # #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
        // Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
             String c_gubun = (String) mapInVar.get("c_gubun");
             dlwMemOcbService.ocb_save_start(srchInDs, c_gubun );
                // listMap.setRowMaps(mList);
                // mapOutDs.put("ds_output", listMap);


             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }

     @RequestMapping(value = "/ocb_update")  //OCB 카드코드별 카드수량
     public ModelAndView ocb_update(XPlatformMapDTO xpDto, Model model) throws Exception {
        // System.out.println("# # # # # # # # # # # # # OCB 발급진행# # # # # # # # # # # # #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();
         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



             System.out.println("---------- ocb발급업데이트산출1");

             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
             hmParam = srchInDs.get(0);
             System.out.println("********** DS_INPUT *******"+srchInDs);

          //   \
             //   String login_id = (String) mapInVar.get("login_id");

             //    System.out.println("---------- ocb발급업데이트산출2");

              // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********
     //   CommonUtils.printLog(hmParam);
                dlwMemOcbService.ocb_update_start(hmParam );


                // listMap.setRowMaps(mList);
                // mapOutDs.put("ds_output", listMap);

             System.out.println("---------- 3");

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }

     @RequestMapping(value = "/ocb_point_update")  //OCB 포인트 결과처리
     public ModelAndView getocb_point_update(XPlatformMapDTO xpDto, Model model) throws Exception {
        // System.out.println("# # # # # # # # # # # # # OCB 발급진행# # # # # # # # # # # # #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();
         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
             Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


             DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
             hmParam = srchInDs.get(0);

             ParamUtils.addSaveParam(hmParam);
             hmParam.put("upd_man", hmParam.get("rgsr_id"));

                dlwMemOcbService.ocb_point_update_start(hmParam );


             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;

     }


        @RequestMapping(value = "/srchOcbCardCodeList") // OCB 카드 코드관리
        public ModelAndView getSrchOcbCardCodeList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # OCB 카드 코드관리 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



                System.out.println("---------- 신규1");

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                System.out.println("********** DS_INPUT *******"+srchInDs);

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    System.out.println("---------- 신규2");
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));

                    }

                 // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                  // int nTotal = dlwMemOcbService.getTranNewCount(hmParam);
                   /* System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);

                    mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수 */
                    List<Map<String, Object>> mList =dlwMemOcbService.getSrchOcbCardCodeList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
              }

                System.out.println("---------- 3");

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }


        @RequestMapping(value = "/srchMemYHCardList") // 회원별 OCB 유효 발급현황
        public ModelAndView getSrchMemYHCardList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # 회원별 OCB 유효 발급현황 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



                System.out.println("---------- 신규1");

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                System.out.println("********** DS_INPUT *******"+srchInDs);

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    System.out.println("---------- 신규2");
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));

                    }

                 // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                  // int nTotal = dlwMemOcbService.getTranNewCount(hmParam);
                   /* System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);

                    mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수 */
                    List<Map<String, Object>> mList =dlwMemOcbService.getSrchMemYHCardList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
              }

                System.out.println("---------- 3");

                //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }


        @RequestMapping(value = "/srchMemCrtFileList") // 회원별 OCB 발급이력
        public ModelAndView getSrchMemCrtFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # 회원별 OCB 발급이력 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



                System.out.println("---------- 신규1");

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                System.out.println("********** DS_INPUT *******"+srchInDs);

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    System.out.println("---------- 신규2");
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));

                    }

                 // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                  // int nTotal = dlwMemOcbService.getTranNewCount(hmParam);
                   /* System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);

                    mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수 */
                    List<Map<String, Object>> mList =dlwMemOcbService.getSrchMemCrtFileList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
              }

                System.out.println("---------- 3");

                //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }

        // OCB 포인트 산출조회
        @RequestMapping(value = "/srchOcbPointList") // 회원별 OCB 발급산출조회
        public ModelAndView getsrchOcbPointList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # OCB 포인트 산출 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                        System.out.println(">>>>>>>>>>>>>>>:"+ pMap.get("startNum") + "||||||"+ pMap.get("endNum") );

                    }

                  // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                   int nTotal = dlwMemOcbService.getsrchOcbPointcount(hmParam);

                  // mapOutVar.put("tc_cardCsmm", nTotal);

                  // int nTotal = dlwMemOcbService.??(hmParam); //산출 건수 생성 필요
                   /* System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);*/

                    mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수

                    List<Map<String, Object>> mList =dlwMemOcbService.getsrchOcbPointList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
              }

                System.out.println("---------- 3");

                //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }


        // OCB 포인트 산출 입력
        @RequestMapping(value = "/calOcbPoint") // 회원별 OCB 산출 입력
        public ModelAndView getscalOcbPointList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # OCB 포인트 산출 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();


            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

                System.out.println("---------- 신규1");

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                System.out.println("********** DS_INPUT *******"+srchInDs);

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    System.out.println("---------- 신규2");
                    String sResult = dlwMemOcbService.insrtOcbCardCode(hmParam);
                    if ("중복".equals(sResult)) {
                        throw new Exception("중복입니다.");
                    }
              }

                System.out.println("---------- 3");

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }


        // OCB 포인트 산출 입력 -정왕채
        @RequestMapping(value = "/OcbPointinsert") // 회원별 OCB 산출 입력 -정왕채
        public ModelAndView getsOcbPointinsert(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # OCB 포인트 산출 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값


                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                   //세션
                   ParamUtils.addSaveParam(hmParam);

                   hmParam.put("reg_man", hmParam.get("rgsr_id"));
                   hmParam.put("upd_man", hmParam.get("rgsr_id"));

                   int iResult = dlwMemOcbService.insertOcbpointcnt(hmParam);

                   if(iResult <= 0){
                       ////  ocb포인트 산출 insert
                       dlwMemOcbService.insrtOcbpoint(hmParam);
                       mapOutVar.put("sResult", "OK"); //tc_mem=전체건수
                   }else{
                       mapOutVar.put("sResult", "NG"); //tc_mem=전체건수
                   }


                    ////  ocb포인트 산출 insert
                   //String sResult = dlwMemOcbService.insrtOcbpoint(hmParam);

                   //System.out.println("---------- 3    " + sResult);

                   /* if ("중복".equals(sResult)) {
                        throw new Exception("중복입니다.");
                    }*/
              }

                //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }






        // OCB 포인트 산출 이력
        @RequestMapping(value = "/srchOcbPointHisList") // 회원별 OCB 발급이력
        public ModelAndView getsrchOcbPointHisList(XPlatformMapDTO xpDto, Model model) throws Exception {
            System.out.println("# # # # # # # # # # # # # OCB 포인트 산출 이력 컨트롤러# # # # # # # # # # # # #");
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



                System.out.println("---------- 신규1");

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                System.out.println("********** DS_INPUT *******"+srchInDs);

                if (srchInDs != null && srchInDs.size() > 0) {
                   hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    System.out.println("---------- 신규2");
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));

                    }

                 // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********


                  int nTotal = dlwMemOcbService.getOcbPointHistCount(hmParam); //산출 이력 건수
                   /* System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);*/

                    mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                    List<Map<String, Object>> mList =dlwMemOcbService.getsrchOcbPointhist(hmParam);

                  //  CommonUtils.printLog(mList);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
              }

                System.out.println("---------- 3");

                //2018.03.23 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

          }

        @RequestMapping(value = "/ocblist")  //OCB발급현황
        public ModelAndView getocblist(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
                hmParam = srchInDs.get(0);


                    List<Map<String, Object>> mList =dlwMemOcbService.getOCBList(hmParam);

                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);


                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

        }

        /**
         *   //OCB 미혜택 모바일 증서 발급
         * @param xpDto
         * @param model
         * @return
         * @throws Exception
         */

        @RequestMapping(value = "/ocb_m_insert")
        public ModelAndView ocb_ocb_m_insert(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값


                //hmParam = srchInDs.get(0);
                dlwMemOcbService.ocb_m_update(srchInDs );



                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

        }

        /* OCB,멤버쉽 카드발급신청 엑셀업로드 */
        @RequestMapping(value = "/excel-upload")
        public ModelAndView uploadIssueExcelList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            Map<String, Object> mParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map<String, Object> mapInVar     = xpDto.getInVariableMap();
                Map<String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map<String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap mInDsList = (DataSetMap)mapInDs.get("ds_input");
                if (mInDsList == null || mInDsList.size() < 1) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "업로드 리스트가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(mParam);

                dlwMemOcbService.uploadIssueExcelList(mInDsList, mParam);

                //mapOutDs.put("ds_output", mInDsList);
            } catch(Exception e) {
                e.printStackTrace();
                szErrorCode ="-1";
                szErrorMsg = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

        /* OCB,멤버쉽 카드발급신청 조회 */
        @RequestMapping(value = "/getIssueList")
        public ModelAndView getIssueList(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");

            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값
                hmParam = srchInDs.get(0);
                System.out.println("********** DS_INPUT *******"+srchInDs);

                int nTotal = dlwMemOcbService.getIssueListCnt(hmParam);
                mapOutVar.put("tc_List", nTotal);

                List<Map<String, Object>> mList = dlwMemOcbService.getIssueList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;

        }

        /* OCB,멤버쉽 카드 반송처리시 */
        @RequestMapping(value = "/save_cons/return_card")
        public ModelAndView saveConsRetrunCard(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap dtptMap = new DataSetMap();

            // 에러코드및 메시지
            String szErrorCode="0";
            String szErrorMsg="OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); // 상담정보

                if (listInDs.size() > 0) {
                    Map<String, Object> hmParam = new HashMap<String, Object>();

                    for (int i = 0; i < listInDs.size(); i++) {
                        hmParam = listInDs.get(i);
                        System.out.println("@@@@@@@@@@@@@@@@@@ OCB,멤버쉽카드 반송일자 UPDATE : " + hmParam);

                        ParamUtils.addSaveParam(hmParam);
                        hmParam.put("upd_man", hmParam.get("rgsr_id"));

                        // 1. 반송일자 UPDATE ///////////////////////////////////////////////
                        dlwMemOcbService.updateReturnDt(hmParam);

                        // 2. 상담기록 저장 /////////////////////////////////////////////////
                        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                        // 담당자 정보
                        hmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());
                        hmParam.put("chpr_id", oLoginUser.getUserid());
                        hmParam.put("actp_id", oLoginUser.getUserid());

                        // 상담 등록
                        System.out.println("@@@@@@@@@@@@@@@@@@ OCB,멤버쉽카드 반송처리 상담기록 : " + hmParam);
                        dlwMemOcbService.saveConsRetrunCard(hmParam);
                    }
                }

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }


        /* OCB,멤버쉽카드요청 삭제처리 */
        @RequestMapping(value = "/delRequestIssue")
        public ModelAndView delRequestIssue(XPlatformMapDTO xpDto, Model model) throws Exception {
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            DataSetMap listMap = new DataSetMap();
            Map<String, Object> hmParam = new HashMap<String, Object>();

            // 에러코드및 메시지
            String szErrorCode = "0";
            String szErrorMsg  = "OK";

            try {
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

                hmParam = listInDs.get(0);
                System.out.println("@@@@@@@@@@@@ OCB,멤버쉽카드요청 삭제처리 : " + hmParam);

                // 1. 발급진행중인 회원 체크
                int chkCnt = dlwMemOcbService.chkIssuingStat(hmParam);

                // 1-1. 발급진행중인 경우 삭제 및 기존 데이터 있는 경우 원복
                if(chkCnt > 0) {
                    dlwMemOcbService.delete_ocb_result(listInDs);
                }

                // 2. 발급요청 업로드내역 삭제
                dlwMemOcbService.delRequestIssue(hmParam);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }

}

