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
import powerservice.business.dlw.service.DlwBasiDataMngService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;


@Controller
@RequestMapping(value = "/dlw/basiDataMng")

public class DlwBasiDataMngController {



    @Resource
    private DlwBasiDataMngService dlwBasiDataMngService;



    @RequestMapping(value = "/getbranchList")  //지부관리 리스트
    public ModelAndView getbranchMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 지부관리 리스트 컨트롤러# # # # # # # # # # # # #");
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

            System.out.println("********** DS_INPUT *******"+srchInDs);


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                 System.out.println("---------- 신규2");
                 // 페이징 정보
               DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                 if (listInDs != null && listInDs.size() > 0) {
                     Map pMap = listInDs.get(0);
                     }
                 }

                System.out.println("---------- 신규2");

             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                List<Map<String, Object>> mList =dlwBasiDataMngService.getbranchList(hmParam);

                //CommonUtils.printLog(mList);
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


    @RequestMapping(value = "/getjangList")  //장례식장 관리 리스트
    public ModelAndView getjangList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 장례식장 관리 리스트 컨트롤러# # # # # # # # # # # # #");
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

                                                                  }
                     }

                    System.out.println("---------- 신규2");


                  //  CommonUtils.printLog(hmParam);
                 // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                    List<Map<String, Object>> mList =dlwBasiDataMngService.getjangList(hmParam);

                   // CommonUtils.printLog(mList);
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



    @RequestMapping(value = "/branch_insert")  //지부장  insert
    public ModelAndView getbranch_insert(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 지부 insert 컨트롤러# # # # # # # # # # # # #");
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
            hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

            /// 지부코드따기

                List<Map<String, Object>> mList1 = dlwBasiDataMngService.get_branch_code(hmParam);

                String branch_cd =(String) mList1.get(0).get("branch_cd");

                hmParam.put("branch_cd",branch_cd);

              //CommonUtils.printLog(hmParam);

                dlwBasiDataMngService.branch_insert(hmParam);

//
//                listMap.setRowMaps(mList);
//
//                mapOutDs.put("ds_output", listMap);

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

    @RequestMapping(value = "/funrl_select")  //장례식장 조회
    public ModelAndView funrl_getselect(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 장례식장 조회 컨트롤러# # # # # # # # # # # # #");
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



                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                List<Map<String, Object>> mList = dlwBasiDataMngService.funrl_select(hmParam);

               //CommonUtils.printLog(mList);
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

    @RequestMapping(value = "/branch_select")  //장례식장 장소 저장
    public ModelAndView branch_getselect(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 장례식장 조회 컨트롤러# # # # # # # # # # # # #");
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



                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                List<Map<String, Object>> mList = dlwBasiDataMngService.branch_select(hmParam);

              // CommonUtils.printLog(mList);
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

    @RequestMapping(value = "/branch_update")  //지부. update
    public ModelAndView branch_getupdate(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 지부. update 컨트롤러# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                hmParam1 = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                hmParam1.put("reg_man", oLoginUser.getUserid());
                hmParam1.put("upd_man", oLoginUser.getUserid());

                System.out.println("======================================>>>>>> branch_update");
                CommonUtils.printLog(hmParam1);
                 dlwBasiDataMngService.branch_update(hmParam1);
             //   mapOutDs.put("ds_output", listMap);

          //  System.out.println("---------- 3");
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

    @RequestMapping(value = "/branch_delete")  //장례식장 장소 삭제
    public ModelAndView branch_getdelete(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                hmParam1 = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
           //     CommonUtils.printLog(hmParam1);
                 dlwBasiDataMngService.branch_delete(hmParam1);
             //   mapOutDs.put("ds_output", listMap);

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

 // =========================================== 거래처 ========================================================================
    @RequestMapping(value = "/getCustmrMngList")  //거래처 관리 리스트
    public ModelAndView getCustmrMngList(XPlatformMapDTO xpDto, Model model) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

                // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                if (srchInDs != null && srchInDs.size() > 0) {
                    hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                    CommonUtils.printLog(hmParam);
                    List<Map<String, Object>> mList =dlwBasiDataMngService.getCustmrMngList(hmParam);
                    listMap.setRowMaps(mList);

//                    List<Map<String, Object>> mList1 = dlwBasiDataMngService.getCustmrCd(hmParam);
//                    String custmr_cd =(String) mList1.get(0).get("custmr_cd");

                    mapOutDs.put("ds_output", listMap);
                    // mapOutVar.put("custmr_cd", custmr_cd);
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

 // ====================================== 창고 ======================================================

     @RequestMapping(value = "/getWHMngList")  //창고 관리 리스트
     public ModelAndView getWHMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # # # # # # # 창고 관리 리스트 컨트롤러# # # # # # # # # # # # #");
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

                     // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********
                     List<Map<String, Object>> mList =dlwBasiDataMngService.getWHMngList(hmParam);

                     listMap.setRowMaps(mList);

                     mapOutDs.put("ds_output", listMap);

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

//==================================================	 행사자		=========================================================================


     @RequestMapping(value = "/getEvtManaMngList")  //행사자 관리 리스트
     public ModelAndView getEvtManaMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # # # # # # # 행사자 관리 리스트 컨트롤러# # # # # # # # # # # # #");
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
                         List<Map<String, Object>> mList =dlwBasiDataMngService.getEvtManaMngList(hmParam);
                         listMap.setRowMaps(mList);
                         mapOutDs.put("ds_output", listMap);

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



     @RequestMapping(value = "/insrtEvtManaMng") // 행사자 관리  등록
     public ModelAndView insrtEvtManaMng(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("#  		행사자 관리   등록           #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
                 Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                 DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                 System.out.println("********** DS_INPUT *******"+srchInDs);

                 if (srchInDs != null && srchInDs.size() > 0) {
                         hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                         ParamUtils.addSaveParam(hmParam);
                         hmParam.put("reg_man", hmParam.get("rgsr_id"));
                         hmParam.put("upd_man", hmParam.get("rgsr_id"));
                         System.out.println("등록자 ="+hmParam.get("rgsr_id"));
                         String job_duty = (String) hmParam.get("job_duty");
                         String emp_gubun = (String) hmParam.get("emp_gubun");
                         String admin_auth = (String) hmParam.get("admin_auth");
                         CommonUtils.printLog(job_duty + "/" + emp_gubun + "/" + admin_auth);
                         CommonUtils.printLog(hmParam);

                         int sCnt = 0;

                         if(job_duty.equals("0001")){
                             if(emp_gubun.equals("0002")){
                                 if(admin_auth.equals("0003")){
                                     sCnt = dlwBasiDataMngService.chkEvtManaMngCnt(hmParam);
                                 }
                             }
                         }

                         CommonUtils.printLog("sCnt: " + sCnt);

                         if(sCnt > 0){
                             modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                             modelAndView.addObject(XPlatformConstant.ERROR_MSG, "등록된 지부장이 존재합니다. 동일 지부에 지부장은 1명만 등록할 수 있습니다.");

                             return modelAndView;
                         }

                         String sResult = dlwBasiDataMngService.insrtEvtManaMng(hmParam);
                         if ("중복".equals(sResult)) {
                                                         throw new Exception("이미 존재하는 순번입니다.");
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



     @RequestMapping(value = "/deleteEvtManaMng")  //행사자 관리 삭제
     public ModelAndView deleteEvtManaMng(XPlatformMapDTO xpDto, Model model) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
                 Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                 DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                 hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                 dlwBasiDataMngService.deleteEvtManaMng(hmParam);

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

     @RequestMapping(value = "/updateEvtManaMng")  //행사장 업데이트
     public ModelAndView updateEvtManaMng(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # # # # # # # 행사장 업데이트 컨트롤러# # # # # # # # # # # # #");
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");

         DataSetMap listMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";

         try {
                 Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                 DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                 hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                 String job_duty = (String) hmParam.get("job_duty");
                 String emp_gubun = (String) hmParam.get("emp_gubun");
                 String admin_auth = (String) hmParam.get("admin_auth");
                 CommonUtils.printLog(job_duty + "/" + emp_gubun + "/" + admin_auth);

                 int sCnt = 0;

                 if(job_duty.equals("0001")){
                     if(emp_gubun.equals("0002")){
                         if(admin_auth.equals("0003")){
                             sCnt = dlwBasiDataMngService.chkEvtManaMngCnt(hmParam);
                         }
                     }
                 }

                 CommonUtils.printLog("sCnt: " + sCnt);

                 if(sCnt > 0){
                     modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                     modelAndView.addObject(XPlatformConstant.ERROR_MSG, "등록된 지부장이 존재합니다. 동일 지부에 지부장은 1명만 등록할 수 있습니다.");

                     return modelAndView;
                 }

                 dlwBasiDataMngService.updateEvtManaMng(hmParam);

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

//===========================================	행사 물품 관리	============================================================================
     /**
      * 조직 트리 구조
      *
      * @param pmParam Map<String, Object>
      * @return ModelAndView
      * @throws Exception
      */
    @RequestMapping(value = "/getTree")
    public ModelAndView getTree(XPlatformMapDTO xpDto, Model model) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         DataSetMap listStatMap = new DataSetMap();
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


                     List<Map<String, Object>> mList = dlwBasiDataMngService.selectEventGoodsList(hmParam);
                     listMap.setRowMaps(mList);
                     mapOutDs.put("ds_output", listMap);
                  CommonUtils.printLog(mList);

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



    @RequestMapping(value = "/insertEvntProd")
    public ModelAndView insertEvntProd(XPlatformMapDTO xpDto, Model model) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         DataSetMap listMap = new DataSetMap();
         DataSetMap listStatMap = new DataSetMap();
         Map<String, Object> hmParam = new HashMap<String, Object>();

         // 에러코드및 메시지
         String szErrorCode = "0";
         String szErrorMsg  = "OK";

         try {

             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");


//                     List<Map<String, Object>> mList = dlwBasiDataMngService.insertEvntProd(hmParam); //controller만 생성함
//                     listMap.setRowMaps(mList);

  //                CommonUtils.printLog(mList);

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
     * 행사 물품 마스터 입력
    **/
    @RequestMapping(value = "/evntgoods-ins")
    public ModelAndView insertEvntGoodsMst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            Object lvl = (Object)mapInVar.get("lvl");
            Object high_ed_cd = (Object)mapInVar.get("high_ed_cd");

                hmParam = srchInDs.get(0);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("lvl", lvl);
                hmParam.put("high_ed_cd", high_ed_cd);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out.println("==============================================>>" + rowType);
                CommonUtils.printLog(hmParam);

                System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                dlwBasiDataMngService.insertEvntGoodsMst(hmParam);  // 등록

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
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
     * 행사 물품 마스터 저장
    **/
    @RequestMapping(value = "/evntgoods-upt")
    public ModelAndView updateEvntGoodsMst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            Object lvl = (Object)mapInVar.get("lvl");
            Object gds_cd = (Object)mapInVar.get("gds_cd");
            Object gds_nm = (Object)mapInVar.get("gds_nm");
            Object report_auto_reg = (Object)mapInVar.get("report_auto_reg");
            Object gds_prn_nm = (Object)mapInVar.get("gds_prn_nm");
            Object standard = (Object)mapInVar.get("standard");
            Object meterial = (Object)mapInVar.get("meterial");
            Object prn_ordr_no = (Object)mapInVar.get("prn_ordr_no");
            Object unit = (Object)mapInVar.get("unit");
            Object note = (Object)mapInVar.get("note");
            Object gds_grp_cd = (Object)mapInVar.get("gds_grp_cd");
            Object report_mng_yn = (Object)mapInVar.get("report_mng_yn");

                hmParam = srchInDs.get(0);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("lvl", lvl);
                hmParam.put("gds_cd", gds_cd);
                hmParam.put("gds_nm", gds_nm);
                hmParam.put("report_auto_reg", report_auto_reg);
                hmParam.put("gds_prn_nm",gds_prn_nm);
                hmParam.put("standard", standard);
                hmParam.put("meterial", meterial);
                hmParam.put("prn_ordr_no", prn_ordr_no);
                hmParam.put("unit", unit);
                hmParam.put("note", note);
                hmParam.put("gds_grp_cd", gds_grp_cd);
                hmParam.put("report_mng_yn", report_mng_yn);

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog(hmParam);

                System.out.println("<<<<<<<<<<<<< 수정 >>>>>>>>>>>>>>>>>>");
                dlwBasiDataMngService.updateEvntGoodsMst(hmParam);  // 등록

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
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
     * 행사 물품 상세정보 등록
    **/
    @RequestMapping(value = "/evntgoods-save")
    public ModelAndView insertEvntGoodsDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            Object gds_cd = (Object)mapInVar.get("gds_cd");

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("gds_cd", gds_cd);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out.println("==============================================>>" + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    dlwBasiDataMngService.updateEvntGoodsDtl(hmParam);  // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {
                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    dlwBasiDataMngService.insertEvntGoodsDtl(hmParam);  // 등록
                }

                //mapOutVar.put("tc_mem_prod", mList.size());
                //listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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

    @RequestMapping(value = "/selectBuyingList")  //행사자 관리 리스트
    public ModelAndView selectBuyingList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                List<Map<String, Object>> mList =dlwBasiDataMngService.selectBuyingList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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



    /**
     * 거래처 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveCustmr")
    public ModelAndView saveCustmr(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mOut = new HashMap<>();
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        try {
            int cnt = dlwBasiDataMngService.saveCustmr(xpDto, mOut);

            String custmr_cd = CommonUtils.nvls((String)mOut.get("custmr_cd"));
            mapOutVar.put("fv_custmr_cd2", custmr_cd);

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
     * 장례식장 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveFunrl")
    public ModelAndView saveFunrl(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");


        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mOut = new HashMap<>();

        try {

            int cnt = dlwBasiDataMngService.saveFunrl(xpDto, mOut);

            String funrl_cd = CommonUtils.nvls((String)mOut.get("funrl_cd"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

            mapOutVar.put("fv_funrl_cd2", funrl_cd);

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
     * 창고 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveWarehouse")
    public ModelAndView saveWarehouse(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");


        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mOut = new HashMap<>();

        try {

            int cnt = dlwBasiDataMngService.saveWarehouse(xpDto, mOut);

            String wh_cd = CommonUtils.nvls((String)mOut.get("wh_cd"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

            mapOutVar.put("fv_wh_cd2", wh_cd);

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }



//============================================ 행사자 기초 수당 ==============================================

    @RequestMapping(value = "/getEvtMngrList")  //행사자 기초 수당 리스트
    public ModelAndView getEvtMngrList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 행사자 기초 수당 리스트 컨트롤러# # # # # # # # # # # # #");
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

                        CommonUtils.printLog(hmParam);
                        List<Map<String, Object>> mList =dlwBasiDataMngService.getEvtMngrList(hmParam);
                        listMap.setRowMaps(mList);
                        mapOutDs.put("ds_output", listMap);

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


    /**
     * 행사자 기초 수당 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveEvtMngrAlow")
    public ModelAndView saveEvtMngrAlow(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        System.out.println("=============행사자 기초 수당 등록/수정/삭제");
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mOut = new HashMap<>();

        try {

            int cnt = dlwBasiDataMngService.saveEvtMngrAlow(xpDto, mOut);

            String evt_mngr_alow_cd = CommonUtils.nvls((String)mOut.get("evt_mngr_alow_cd"));
            System.out.println("컨트롤:"+evt_mngr_alow_cd);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

           // mapOutVar.put("fv_wh_cd2", wh_cd);

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
