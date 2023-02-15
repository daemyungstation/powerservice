package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.chn.web.DlvController;
import powerservice.business.dlw.service.DlwOthersService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

//2017.12.26 로그 추가
import powerservice.business.common.service.CommonService;
@Controller
@RequestMapping(value = "/dlw/others")
public class DlwOthersContoller {
    @Resource
    private DlwOthersService  dlwOthersService;

    @Resource
    private CommonService commonService;

    private final Logger log = LoggerFactory.getLogger(DlwOthersContoller.class);

    /**
     ** 회사정보 조회
     **/
    @RequestMapping(value = "/getUseEntrList")
    public ModelAndView getUseEntrList(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # # # # # 회사정보 조회 컨트롤러# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {
                    Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                            List<Map<String, Object>> mList =dlwOthersService.getUseEntrList(hmParam);
                            listMap.setRowMaps(mList);
                            mapOutDs.put("ds_output", listMap);
                            CommonUtils.printLog(mList);
                            System.out.println("22222");

                        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                 } catch (Exception e)     {
                                                e.printStackTrace();
                                                szErrorCode = "-1";
                                                szErrorMsg  = e.getMessage();
                                            }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     ** 회사정보 저장
     **/
    @RequestMapping(value = "/updateUseEntr")
    public ModelAndView updateUseEntr(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("================================== 회사정보 저장 컨트롤러# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> hmParam = new HashMap<String, Object>();
        ParamUtils.addSaveParam(hmParam);

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {

                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

                    DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                    if (srchInDs != null && srchInDs.size() > 0)
                        {

                            hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                            hmParam.put("reg_man", hmParam.get("rgsr_id"));
                            hmParam.put("upd_man", hmParam.get("upd_id"));
                            dlwOthersService.updateUseEntr(hmParam);
                            CommonUtils.printLog(hmParam);
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
     ** CMS 기초환경 설정 조회
     **/
    @RequestMapping(value = "/getCmsCom")
    public ModelAndView getCmsCom(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # # # # # CMS 기초환경 설정 리스트 컨트롤러# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {

                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                    Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                            List<Map<String, Object>> mList =dlwOthersService.getDlwCmsComnInfo(hmParam);
                            listMap.setRowMaps(mList);
                            mapOutDs.put("ds_output", listMap);
                            CommonUtils.printLog(mList);
                            System.out.println("22222");

                        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                 } catch (Exception e)     {
                                                e.printStackTrace();
                                                szErrorCode = "-1";
                                                szErrorMsg  = e.getMessage();
                                            }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;

    }

    /**
     ** CMS 기초환경 설정 저장
     **/
    @RequestMapping(value = "/saveCmsCom")
    public ModelAndView saveCmsCom(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("================================== CMS 기초환경 설정 저장 컨트롤러# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {

                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();


                    DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

                    if (srchInDs != null && srchInDs.size() > 0)
                        {

                            hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                            dlwOthersService.updateCMSComInfo(hmParam);
                            CommonUtils.printLog(hmParam);
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
     ** 그룹 코드 조회
     **/
    @RequestMapping(value = "/getGroupCdList")
    public ModelAndView getGroupCdList(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # # # # # 그룹 코드 조회 컨트롤러 (selectComCdGrpList)# # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {
                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                    Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                    DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                    if (srchInDs != null && srchInDs.size() > 0)
                    {

                        hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                        CommonUtils.printLog(hmParam);

                            List<Map<String, Object>> mList = dlwOthersService.getGroupCdList(hmParam);
                            listMap.setRowMaps(mList);
                    }
                            mapOutDs.put("ds_output", listMap);

                        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                 } catch (Exception e)     {
                                                e.printStackTrace();
                                                szErrorCode = "-1";
                                                szErrorMsg  = e.getMessage();
                                            }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
    ** 그룹 코드 등록
    **/

    @RequestMapping(value = "/insertComCdGrp")
    public ModelAndView insertComCdGrp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                        CommonUtils.printLog(hmParam);

                         dlwOthersService.insertComCdGrp(hmParam);
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


    /*
    ** 그룹 코드 수정
    */
    @RequestMapping(value = "/updateComCdGrp")
    public ModelAndView updateComCdGrp(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # #			그룹 코드 수정		 # # # # # # # #");
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
                dlwOthersService.updateComCdGrp(hmParam);

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
     **   공통 코드 조회 컨트롤러
     **/
    @RequestMapping(value = "/selectComCdGrpLis")
    public ModelAndView selectComCdGrpLis(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # # # # # 공통 코드 조회 컨트롤러 # # # # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {
                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                    Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                    DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                    if (srchInDs != null && srchInDs.size() > 0)
                    {
                        hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                        CommonUtils.printLog(hmParam);
                        dlwOthersService.selectComCdGrpLis(hmParam);

                     }
                            List<Map<String, Object>> mList =dlwOthersService.selectComCdGrpLis(hmParam);
                            listMap.setRowMaps(mList);
                            mapOutDs.put("ds_output", listMap);

                        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                 } catch (Exception e)     {
                                                e.printStackTrace();
                                                szErrorCode = "-1";
                                                szErrorMsg  = e.getMessage();
                                            }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /*
     ** 그룹 코드 등록
     */
     @RequestMapping(value = "/insertComCd")
     public ModelAndView insertComCd(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                            dlwOthersService.insertComCd(hmParam);

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


     /*
     ** 그룹 코드 수정
     */
     @RequestMapping(value = "/updateComCd")
     public ModelAndView updateComCd(XPlatformMapDTO xpDto, Model model) throws Exception {
         System.out.println("# # # # # # # #			그룹 코드 수정		 # # # # # # # #");
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
                 CommonUtils.printLog(hmParam);
                 dlwOthersService.updateComCd(hmParam);

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


     /*
      ** 그룹 코드 삭제
      */
      @RequestMapping(value = "/deleteComcd")
      public ModelAndView deleteComcd(XPlatformMapDTO xpDto, Model model) throws Exception {
          System.out.println("# # # # # # # #			그룹 코드 삭제		 # # # # # # # #");
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
                  CommonUtils.printLog(hmParam);
                  dlwOthersService.deleteComcd(hmParam);

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
       ** 불능 코드 조회
       **/
      @RequestMapping(value = "/getImpsCdList")
      public ModelAndView getImpsCdList(XPlatformMapDTO xpDto, Model model) throws Exception {

          System.out.println("# # # # # # # # # # # # # 불능 코드 조회 컨트롤러 (selectComCdGrpList)# # # # # # # # # # # # #");
          ModelAndView modelAndView = new ModelAndView("xplatformMapView");

          DataSetMap listMap = new DataSetMap();
          Map<String, Object> hmParam = new HashMap<String, Object>();

          // 에러코드및 메시지
          String szErrorCode="0";
          String szErrorMsg="OK";

              try {
                      Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                      Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                      DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                     if (srchInDs != null && srchInDs.size() > 0)
                      {
                         hmParam = srchInDs.get(0);
                          //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                          CommonUtils.printLog(hmParam);

                      }
                              List<Map<String, Object>> mList = dlwOthersService.getImpsCdList(hmParam);
                             listMap.setRowMaps(mList);

                              mapOutDs.put("ds_output", listMap);

                          modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                          modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                   } catch (Exception e)     {
                                                  e.printStackTrace();
                                                  szErrorCode = "-1";
                                                  szErrorMsg  = e.getMessage();
                                              }
          modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
          modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

          return modelAndView;
      }


      /**
      ** 불능 코드 등록
      **/

      @RequestMapping(value = "/insertImpsCd")
      public ModelAndView insertImpsCd(XPlatformMapDTO xpDto, Model model) throws Exception {
          System.out.println("#  		불능코드 관리   등록           #");
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

                          CommonUtils.printLog(hmParam);


                           String sResult = dlwOthersService.insertImpsCd(hmParam);
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



      /*
       ** 그룹 코드 삭제
       */
       @RequestMapping(value = "/deleteImpsCd")
       public ModelAndView deleteImpsCd(XPlatformMapDTO xpDto, Model model) throws Exception {
           System.out.println("# # # # # # # #			불능 코드 삭제		 # # # # # # # #");
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
                   CommonUtils.printLog(hmParam);
                   dlwOthersService.deleteImpsCd(hmParam);

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


       /**월별건수목록 조회
       *
       * @param xpDto XPlatformMapDTO
       * @return
       * @throws Exception
       */
      @RequestMapping(value = "/selectMonTargetNoList")
      public ModelAndView selectMonTargetNoList(XPlatformMapDTO xpDto) throws Exception {
          System.out.println("# # # # # # # # # # # # # 월별건수목록 조회 컨트롤러 (selectComCdGrpList)# # # # # # # # # # # # #");
          ModelAndView modelAndView = new ModelAndView("xplatformMapView");

          DataSetMap listMap = new DataSetMap();
          Map<String, Object> hmParam = new HashMap<String, Object>();

          // 에러코드및 메시지
          String szErrorCode="0";
          String szErrorMsg="OK";

              try {
                      Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                      Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

                      DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                     if (srchInDs != null && srchInDs.size() > 0)
                      {
                         hmParam = srchInDs.get(0);
                          //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                          CommonUtils.printLog(hmParam);

                      }
                              List<Map<String, Object>> mList = dlwOthersService.selectMonTargetNoList(hmParam);
                             listMap.setRowMaps(mList);

                              mapOutDs.put("ds_output", listMap);

                          modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                          modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

                   } catch (Exception e)     {
                                                  e.printStackTrace();
                                                  szErrorCode = "-1";
                                                  szErrorMsg  = e.getMessage();
                                              }
          modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
          modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

          return modelAndView;
      }

      /**
       * 반송 발송 관리
       * 임동진
       * @param pmParam Map<String, Object>
       * @return ModelAndView
       * @throws Exception
       */
      @RequestMapping(value = "/postsendmng")
      public ModelAndView getdlwOthersPostSendList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

              DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
              if (srchInDs != null && srchInDs.size() > 0) {
                  hmParam = srchInDs.get(0);

                  // 엔컴 부서코드 조건여부
                  String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                  // 페이징 정보
                  DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                  if (listInDs != null && listInDs.size() > 0) {
                      Map pMap = listInDs.get(0);

                      String excel_fg = (String)mapInVar.get("excel_fg");
                      if (!"Y".equals(excel_fg)) {
                          hmParam.put("startLine", pMap.get("startNum"));
                          hmParam.put("endLine", pMap.get("endNum"));
                      }
                  }
                  ParamUtils.addCenterParam(hmParam);

                  int nTotal = dlwOthersService.getPostSendListCount(hmParam);

                  mapOutVar.put("tc_mem", nTotal);

                  List<Map<String, Object>> mList = dlwOthersService.getPostSendList(hmParam);
                  listMap.setRowMaps(mList);
                  mapOutDs.put("ds_output", listMap);
              }

              //2017.12.26 접속 로그////////////////////////////////////////////////////////////////////////////////
              DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

              if (listLogIn.size() > 0) {
                  hmParam = listLogIn.get(0);
                  commonService.insertLog(hmParam);
              }
              //////////////////////////////////////////////////////////////////////////////////////////////////////

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

      @RequestMapping(value = "/excel-upload")
      public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

          // 에러코드및 메시지
          String szErrorCode="0";
          String szErrorMsg="OK";

          Map<String, Object> mResult = new HashMap<>();
          String fileNm = "";

          PlatformData resData = new PlatformData();
          VariableList resVarList = resData.getVariableList();

          log.debug("--------^^--------");

          try {
              log.debug("uploadToNas 컨트롤러 - 1");
              dlwOthersService.uploadToNas(request, response, mResult);
              log.debug("uploadToNas 컨트롤러 - 2");

              resVarList.add("ErrorCode"	, szErrorCode);
              resVarList.add("ErrorMsg"	, szErrorMsg);

          } catch (EgovBizException e) {
              resVarList.add("ErrorCode", "-1");
              resVarList.add("ErrorMsg", e.getMessage());
              e.printStackTrace();
          } catch (Exception e) {
              resVarList.add("ErrorCode", "-1");
              resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
              e.printStackTrace();
          }

          HttpPlatformResponse res = new HttpPlatformResponse(response);
          res.setData(resData);
          res.sendData();

      }

      @RequestMapping(value = "/excel-upload_accnt")
      public void uploadToAccnt(HttpServletRequest request, HttpServletResponse response) throws Exception {

          // 에러코드및 메시지
          String szErrorCode="0";
          String szErrorMsg="OK";

          Map<String, Object> mResult = new HashMap<>();
          String fileNm = "";

          PlatformData resData = new PlatformData();
          VariableList resVarList = resData.getVariableList();

          log.debug("--------^^--------");

          try {
              log.debug("uploadToNas 컨트롤러 - 1");
              dlwOthersService.deleteAccnt();
              dlwOthersService.uploadToAccnt(request, response, mResult);
              log.debug("uploadToNas 컨트롤러 - 2");

              resVarList.add("ErrorCode"	, szErrorCode);
              resVarList.add("ErrorMsg"	, szErrorMsg);

          } catch (EgovBizException e) {
              resVarList.add("ErrorCode", "-1");
              resVarList.add("ErrorMsg", e.getMessage());
              e.printStackTrace();
          } catch (Exception e) {
              resVarList.add("ErrorCode", "-1");
              resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
              e.printStackTrace();
          }

          HttpPlatformResponse res = new HttpPlatformResponse(response);
          res.setData(resData);
          res.sendData();

      }

      @RequestMapping(value = "/save")
      public ModelAndView saveDlv(XPlatformMapDTO xpDto, Model model) throws Exception {
          ModelAndView modelAndView = new ModelAndView("xplatformMapView");

          // 에러코드및 메시지
          String szErrorCode = "0";
          String szErrorMsg  = "OK";
          int iAccntCnt = 0 ;  //회원번호 여부 체크용

          try {
              Map <String, Object> mapInVar     = xpDto.getInVariableMap();
              Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
              Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
              Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

              DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

              Map<String, Object> hmParam = listInDs.get(0);
              ParamUtils.addSaveParam(hmParam);

              if (hmParam.get("dlv_id").equals("")){
                  iAccntCnt = dlwOthersService.getDlvAccntCount(hmParam);
                  if (iAccntCnt > 0) {
                      throw new EgovBizException("회원번호가 중복 되었습니다. > " + hmParam.get("accnt_no"));
                  }
              }

              String sDlvId = dlwOthersService.saveDlv(hmParam);
              mapOutVar.put("sDlvId", sDlvId);

              modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
              modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
          }catch(Exception e){
              e.printStackTrace();
              szErrorCode ="-1";
              szErrorMsg = e.getMessage();
          }

          modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
          modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

          return modelAndView;
      }

      /**
       * 발송 /반송 히스토리 검색한다.
       *
       * @param psPathType String
       * @param pmParam Map<String, Object>
       * @return ModelAndView
       * @throws Exception
       */
      @RequestMapping(value = "/postsenddtl")
      public ModelAndView getdlwOthersPostSendDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
          ModelAndView modelAndView = new ModelAndView("xplatformMapView");
          Map<String, Object> hmParam = new HashMap<String, Object>();
          DataSetMap listMap = new DataSetMap();

          // 에러코드및 메시지
          String szErrorCode = "0";
          String szErrorMsg  = "OK";

          try {
              Map <String, Object> mapInVar     = xpDto.getInVariableMap();
              Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
              Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
              Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

              DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
              if (srchInDs != null && srchInDs.size() > 0) {
                  hmParam = srchInDs.get(0);
              }
              ParamUtils.addCenterParam(hmParam);

              List<Map<String, Object>> mList = dlwOthersService.getPostSendDtlList(hmParam);

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

      /**
       * 발송 /반송 히스토리 검색한다.
       *
       * @param psPathType String
       * @param pmParam Map<String, Object>
       * @return ModelAndView
       * @throws Exception
       */
      @RequestMapping(value = "/infoofcell")
      public ModelAndView getdlwOthersInfoofCellList(XPlatformMapDTO xpDto, Model model) throws Exception {
          ModelAndView modelAndView = new ModelAndView("xplatformMapView");
          Map<String, Object> hmParam = new HashMap<String, Object>();
          DataSetMap listMap = new DataSetMap();

          // 에러코드및 메시지
          String szErrorCode = "0";
          String szErrorMsg  = "OK";

          try {
              Map <String, Object> mapInVar     = xpDto.getInVariableMap();
              Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
              Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
              Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

              DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
              if (srchInDs != null && srchInDs.size() > 0) {
                  hmParam = srchInDs.get(0);
              }
              ParamUtils.addCenterParam(hmParam);

              List<Map<String, Object>> mList = dlwOthersService.getInfoofCellList(hmParam);

              listMap.setRowMaps(mList);

              mapOutDs.put("ds_output", listMap);

              //2018.05.03 접속 로그////////////////////////////////////////////////////////////////////////////////
              DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

              if (listLogIn.size() > 0) {
                  hmParam = listLogIn.get(0);
                  commonService.insertLog(hmParam);
              }
              //////////////////////////////////////////////////////////////////////////////////////////////////////

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
}

