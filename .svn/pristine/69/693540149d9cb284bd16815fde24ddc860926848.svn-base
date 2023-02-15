package powerservice.business.acn.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.acn.service.AcnTranService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SimpleFtpClient;
import powerservice.core.util.ParamUtils;

import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2018.05.15 로그 추가
import powerservice.business.common.service.CommonService;

@Controller
@RequestMapping(value = "/acuo/trans")
public class AcnTranController {

    @Resource
    private AcnTranService acnTranService;

    @Resource
    private CommonService commonService;

    private final Logger log = LoggerFactory.getLogger(AcnTranController.class);




     /**
     * 신규 탭 조회   newList
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/newList")
    public ModelAndView getNewTranList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 신규 조회 컨트롤러# # # # # # # # # # # # #");
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
                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 신규2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getTranNewCount(hmParam);
                System.out.println("----------전체 페이지 수메인산출조회:"+nTotal);

                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수



                List<Map<String, Object>> mList =acnTranService.getTranNewList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);



               // CommonUtils.printLog(mList);


            }

            System.out.println("---------- 3");

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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




    @RequestMapping(value = "/ynchList")
    public ModelAndView getYHjList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # # # # # 신규 조회 컨트롤러# # # # # # # # # # # # #");
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



            System.out.println("---------- 1");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

            System.out.println("********** DS_INPUT *******"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getTranNewCount(hmParam);
                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
               System.out.println("----------전체 페이지 수:nTotal");

                List<Map<String, Object>> mList =acnTranService.getTranNewList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

              //  CommonUtils.printLog(mList);
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




    @RequestMapping(value = "/updateInfo")
    public ModelAndView getUpdateInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            System.out.println("---------- 1");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

            System.out.println("********** DS_INPUT *******"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                //int nTotal = acnTranNewService.getTranMonCount(hmParam);
               // mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                //System.out.println("----------전체 페이지 수:nTotal");

                List<Map<String, Object>> mList =acnTranService.getTranNewList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);



              //  CommonUtils.printLog(mList);


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



    @RequestMapping(value = "/acuonupdate")
    public ModelAndView getacuonupdate(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            System.out.println("---------- 1");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값


            CommonUtils.printLog(srchInDs);

            System.out.println("********** DS_INPUT *******"+srchInDs);

            hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다



            acnTranService.acuonupdate(hmParam);




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
     * 메인산출 탭에서 동작한다
     *
     * 전송월 기준으로 산출결과를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/aclist")
    public ModelAndView getTranMonList(XPlatformMapDTO xpDto, Model model) throws Exception {

         System.out.println("= = = = controller 입장 = = = ");


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



            System.out.println("---------- 1");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

            System.out.println("********** DS_INPUT *******"+srchInDs);


            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
             //   CommonUtils.printLog(hmParam);
                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getTranMonCount(hmParam);
                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                System.out.println("----------전체 페이지 수1111:nTotal");
                List<Map<String, Object>> mList =acnTranService.getTranMonList(hmParam);

             //   CommonUtils.printLog(mList);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);



              //  CommonUtils.printLog(mList);


            }
            System.out.println("---------- 2");
            System.out.println("---------- 3");

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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


    /**
     * 녹취 탭에서 동작한다
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/rec_aco")
    public ModelAndView getTrec_acoMonList(XPlatformMapDTO xpDto, Model model) throws Exception {


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

//            System.out.println("********** DS_INPUT *******"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getrecacoCount(hmParam);

                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                System.out.println("----------전체 페이지 수44444:nTotal");
                List<Map<String, Object>> mList =acnTranService.getrecacoList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);




              //  CommonUtils.printLog(mList);


            }
            System.out.println("---------- 2");
            System.out.println("---------- 3");

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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



    /**
     * 수납조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sunap")
    public ModelAndView getsunapList(XPlatformMapDTO xpDto, Model model) throws Exception {


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
             //   CommonUtils.printLog(hmParam);
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getsuCount(hmParam);

                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                List<Map<String, Object>> mList =acnTranService.getsuList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);


                //System.out.println("----------sunap 3");
                //CommonUtils.printLog(mList);


            }

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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



    /////reject 회원 조회
    @RequestMapping(value = "/REJECT")
    public ModelAndView getrejectList(XPlatformMapDTO xpDto, Model model) throws Exception {


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

                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);


             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getrecacoCount(hmParam);

                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
                System.out.println("----------전체 페이지 수44444:nTotal");
                List<Map<String, Object>> mList =acnTranService.getrejectList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);



                CommonUtils.printLog(mList);


            }
            System.out.println("---------- 2");
            System.out.println("---------- 3");

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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

    /**
     * 메인산출 탭에서 동작한다
     *
     * 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deinsert")
    public ModelAndView deletelist(XPlatformMapDTO xpDto, Model model) throws Exception {

         System.out.println("= = = = controller 입장 = = = ");


        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        //Map<String, Object> hmParam = new HashMap<String, Object>();



        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



            System.out.println("---------- delete");

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            Map<String, Object> hmParam = listInDs.get(0);

            acnTranService.deleteCount(hmParam);






            System.out.println("---------- 3333");

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
     * 메인산출 탭에서 동작한다
     *
     * 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deletesunap")
    public ModelAndView deletesunaplist(XPlatformMapDTO xpDto, Model model) throws Exception {

         System.out.println("= = = =  deletesunap  controller 입장 = = = ");


        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        //Map<String, Object> hmParam = new HashMap<String, Object>();



        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



            System.out.println("---------- deletesunap");

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            Map<String, Object> hmParam = listInDs.get(0);

            acnTranService.deletesunapCount(hmParam);




        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/send")
    public @ResponseBody ModelAndView getTranMonListsend(XPlatformMapDTO xpDto, Model model) throws Exception {

         System.out.println("= = = = /send  입장 = = = ");


        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String sDataPath1 = "";
        String sDataPath2 = "";

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();




            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

              //  System.out.println(srchInDs.size() + "row 수");



           if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다

                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                System.out.println("---------- 2");
                String JDATE  =(java.lang.String) hmParam.get("year_mon");
                System.out.println(JDATE);

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));

                }

                ParamUtils.addCenterParam(hmParam);



   // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********

                int nTotal = acnTranService.getTranMonCount(hmParam);

                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
           //     System.out.println("----------전체 페이지 수:nTotal");
                List<Map<String, Object>> mList =acnTranService.getTranMonList(hmParam);


              //  CommonUtils.printLog(mList);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);



                     //   CommonUtils.printLog(pMap1);

//////////////////////ㅍ ㅏ 일 작 성 start

                Calendar cal = Calendar.getInstance();

            /*    int year = cal.get ( cal.YEAR );
                int month = cal.get ( cal.MONTH ) + 1 ;
                int date = cal.get ( cal.DATE ) ;

                String yearmon =  String.valueOf(year) + String.valueOf(month) + String.valueOf(date)  ;*/


                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

                String datetime1 = sdf1.format(cal.getTime());

             /*   int year = cal.get ( cal.YEAR );
                int month = cal.get ( cal.MONTH ) + 1 ;
                int date = cal.get ( cal.DATE ) ;
*/
                String yearmon =  datetime1;

                System.out.println(yearmon);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

                // acuon 상조가입관련 데이터 파일 작성 디렉토리 C:/egovframework/acuon/life/join
                String sDataPath = EgovProperties.getProperty("acuon.life.join.dataPath");

                sDataPath1 = sDataPath +"DML11_" + JDATE+".DAT";
                sDataPath2 = sDataPath +"DML11_" + JDATE+"_REC.DAT";

                // TestMain.test1(); // byte string 테스트
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();

                        for (int i=0; i<   mList.size()  ; ++i) {

                            String[] sArr1 = new String[57];

                             Map pMap1= mList.get(i);
                           //  CommonUtils.printLog(pMap1);
                            // System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDR2")), 100, " ")+"/" );
/*
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("gdate")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("accnt_no")), 30, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GGUBUN")), 2, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_NM")), 40, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("01", 2, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("BRTH_MON_DAY")), 13, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 32, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CTEL")), 11, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CPOST")), 6, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDR1")), 200, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDR2")), 100, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDRGUBUN")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 100, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_NO")), 30, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 22, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("JCODE")), 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("PCODE")), 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MODELNAME")), 100, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KMODELNAME")), 100, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_AMT"))), 9, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_CNT"))), 2, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAL_FDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAL_EDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KYDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAEJI_DATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HBIGO")), 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_SUSU"))), 10, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KDAY")), 2, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KBANK")), 3, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("KBNO"))), 30, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("M_YN")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JDDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JGUBUN")), 9, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JDATE")), 8, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 4, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_SAFEKEY")), 13, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("NICE_NCNT"))), 3, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_YN")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN1")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN2")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN3")), 1, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("NAPBU_GCNT"))), 3, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_KDAY")), 2, " ")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("MINAP"))), 15, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("YEOUNCHE"))), 2, "0")+"/" );
                             System.out.println( CommonUtils.filler_blank_right("", 467, " ")+"/" );
*/


                             sArr1[0 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("gdate")), 8, " ");
                             sArr1[1 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("accnt_no")), 30, " ");
                             sArr1[2 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GGUBUN")), 2, " ");
                             sArr1[3 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_NM")), 40, " ");
                             sArr1[4 ] = CommonUtils.filler_blank_right("01", 2, " ");
                             sArr1[5 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("BRTH_MON_DAY")), 13, " ");
                             sArr1[6 ] = CommonUtils.filler_blank_right("", 32, " ");
                             sArr1[7 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CTEL")), 11, " ");
                             sArr1[8 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CPOST")), 6, " ");
                             sArr1[9 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDR1")), 200, " ");
                             sArr1[10] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDR2")), 100, " ");
                             sArr1[11] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CADDRGUBUN")), 1, " ");
                             sArr1[12] = CommonUtils.filler_blank_right("", 100, " ");
                             sArr1[13] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_NO")), 30, " ");
                             sArr1[14] = CommonUtils.filler_blank_right("", 22, " ");
                             sArr1[15] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("JCODE")), 9, " ");
                             sArr1[16] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("PCODE")), 9, " ");
                             sArr1[17] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MODELNAME")), 100, " ");
                             sArr1[18] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KMODELNAME")), 100, " ");
                             sArr1[19] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_AMT"))), 9, "0");
                             sArr1[20] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_CNT"))), 2, "0");
                             sArr1[21] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAL_FDATE")), 8, " ");
                             sArr1[22] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAL_EDATE")), 8, " ");
                             sArr1[23] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KYDATE")), 8, " ");
                             sArr1[24] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("CDATE")), 8, " ");
                             sArr1[25] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HAEJI_DATE")), 8, " ");
                             sArr1[26] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("HBIGO")), 9, " ");
                             sArr1[27] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("HAL_SUSU"))), 10, "0");
                             sArr1[28] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KDAY")), 2, " ");
                             sArr1[29] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("KBANK")), 3, " ");
                             sArr1[30] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("KBNO"))), 30, "0");
                             sArr1[31] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("M_YN")), 1, " ");
                             sArr1[32] = CommonUtils.filler_blank_right("", 9, " ");
                             sArr1[33] = CommonUtils.filler_blank_right("", 8, " ");
                             sArr1[34] = CommonUtils.filler_blank_right("", 9, " ");
                             sArr1[35] = CommonUtils.filler_blank_right("", 9, " ");
                             sArr1[36] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[37] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[38] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[39] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[40] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[41] = CommonUtils.filler_blank_right("", 1, " ");
                             sArr1[42] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JDDATE")), 8, " ");
                             sArr1[43] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JGUBUN")), 9, " ");
                             sArr1[44] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_JDATE")), 8, " ");
                             sArr1[45] = CommonUtils.filler_blank_right("", 4, " ");
                             sArr1[46] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_SAFEKEY")), 13, " ");
                             sArr1[47] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("NICE_NCNT"))), 3, "0");
                             sArr1[48] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("NICE_YN")), 1, " ");
                             sArr1[49] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN1")), 1, " ");
                             sArr1[50] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN2")), 1, " ");
                             sArr1[51] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("GS_YN3")), 1, " ");
                             sArr1[52] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("NAPBU_GCNT"))), 3, "0");
                             sArr1[53] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MEM_KDAY")), 2, " ");
                             sArr1[54] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("MINAP"))), 15, "0");
                             sArr1[55] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("YEOUNCHE"))), 2, "0");
                             sArr1[56] = CommonUtils.filler_blank_right("", 467, " ");


                             for (int ii=0; ii<sArr1.length; ++ii) {
                                 sb.append(String.format("%s", sArr1[ii]));
                             }
                             sb.append("\n");

                             int ccnt = Integer.valueOf(String.valueOf(pMap1.get("NICE_NCNT")));

                             for (int ii=1; ii<= ccnt  ; ++ii) {

                              String str = "00000" + ii ;

                            // System.out.println(str.substring(str.length()-2,str.length() ));

                             sb1.append(pMap1.get("gdate")).
                             append(CommonUtils.fillEmpVal(30, CommonUtils.nvls((String)pMap1.get("accnt_no")), "L", " "))
                             .append(CommonUtils.fillEmpVal(100, CommonUtils.nvls((String)pMap1.get("accnt_no")+"_"+ str.substring(str.length()-2,str.length() ) +".wav"      ), "L", " "))
                             .append(CommonUtils.fillEmpVal(362, "", "L", " "))
                             .append("\r\n") ;
                             }
                        }

                        // CommonUtils.writeTextFile("D:/PROJECT_DM/Hello/src/out/out.dat", sb.toString(), "EUC-KR"); //

                     //   System.out.println(sb.toString());
                     //   new String(sb.toString().getBytes("utf-8"), "euc-kr")
                     /*
                        String originalStr = "Å×½ºÆ®"; // 테스트
                        String [] charSet = {"utf-8","euc-kr","ksc5601","iso-8859-1","x-windows-949"};

                        for (int i=0; i<charSet.length; i++) {
                         for (int j=0; j<charSet.length; j++) {
                          try {
                           System.out.println("[" + charSet[i] +"," + charSet[j] +"] = " + new String(originalStr.getBytes(charSet[i]), charSet[j]));
                          } catch (UnsupportedEncodingException e) {
                           e.printStackTrace();
                          }
                         }
                        }
*/

                      // CommonUtils.writeTextFile(sDataPath1, sb.toString(), "MS949"); //
                      //CommonUtils.writeTextFile(sDataPath1, sb.toString(), "EUC-KR"); //
                        CommonUtils.writeTextFile(sDataPath1, sb.toString(), "EUC-KR"); //

                      CommonUtils.writeTextFile(sDataPath2, sb1.toString(), "UTF-8"); //
        //////////////////////ㅍ ㅏ 일 작 성  end


                    }


                   //	int nTotal = acnTranService.getTranMonCount(hmParam);

                    acnTranService.updateconvertResult(hmParam);

                    String serverIp 	= EgovProperties.getProperty("acuon.life.ftp.ip");
                    int port 			= Integer.valueOf(EgovProperties.getProperty("acuon.life.ftp.port"));
                    String user 		= EgovProperties.getProperty("acuon.life.ftp.user");
                    String pw 			= EgovProperties.getProperty("acuon.life.ftp.pw");
                    String ftpDataDir	= EgovProperties.getProperty("acuon.life.ftp.join.dataPath");

                    // ACUON FTP 파일전송
                    try {

                        boolean isSuccess = false;
                        File file = null;

                        SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw);
                        ftp.setDebugMode(true);
                        ftp.connect();
                        ftp.setPassiveMode();
                        ftp.setFileType(FTP.BINARY_FILE_TYPE);

                        // 경로 이동
                        ftp.chdir(ftpDataDir);

                        file = new File(sDataPath1);
                        if (file.exists()) {
                            log.debug("> file.getPath() : " + file.getPath());
                            log.debug("> file.getName() : " + file.getName());
                            isSuccess = ftp.upload(file.getPath(), ftpDataDir + file.getName());
                            if (isSuccess) {
                                log.debug("[" + file.getPath() + "] 파일 업로드 성공!");
                            } else {
                                log.debug("[" + file.getPath() + "] 파일 업로드 실패!");
                            }
                        }

                        file = new File(sDataPath2);
                        if (file.exists()) {
                            log.debug("> file.getPath() : " + file.getPath());
                            log.debug("> file.getName() : " + file.getName());
                            isSuccess = ftp.upload(file.getPath(), ftpDataDir + file.getName());
                            if (isSuccess) {
                                log.debug("[" + file.getPath() + "] 파일 업로드 성공!");
                            } else {
                                log.debug("[" + file.getPath() + "] 파일 업로드 실패!");
                            }
                        }

                        ftp.close();

                    } catch (IOException ex) {
                        throw new Exception(ex.getMessage());
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



    @RequestMapping(value = "/susend")
    public @ResponseBody ModelAndView getsusend(XPlatformMapDTO xpDto, Model model) throws Exception {


        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();


        // 에러코드및 메시지
        String sDataPath1="";
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

                System.out.println(hmParam.get("year_mon"));
                System.out.println(hmParam.get("kydate"));
                String ydate = (java.lang.String) hmParam.get("year_mon") ;
            //    System.out.println("---------- 2");
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                ParamUtils.addCenterParam(hmParam);
                int nTotal = acnTranService.getsuCount(hmParam);
                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수
           //     System.out.println("----------전체 페이지 수:nTotal");
                List<Map<String, Object>> mList =acnTranService.getsuList(hmParam);



                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

                String datetime1 = sdf1.format(cal.getTime());


                String yearmon =  datetime1;


                // acuon 상조가입관련 데이터 파일 작성 디렉토리 C:/egovframework/acuon/life/join
                String sDataPath = EgovProperties.getProperty("acuon.life.join.dataPath");

             //    sDataPath1 = sDataPath +"DML11_FIXEDTIME" + hmParam.get("year_mon")+".dat";
                sDataPath1 = sDataPath +"/DML_FIXEDTIME_" + ydate+".DAT";
             //   String sDataPath2 = sDataPath +"DML11_" + hmParam.get("year_mon")+"_REC.dat";

                // TestMain.test1(); // byte string 테스트
                StringBuffer sb = new StringBuffer();
               // StringBuffer sb1 = new StringBuffer();

                        for (int i=0; i<   mList.size()  ; ++i) {

                            String[] sArr1 = new String[10];

                             Map pMap1= mList.get(i);


                             sArr1[0 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("accnt_no")), 30, " ");
                             sArr1[1 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("yymm")), 6, " ");
                             sArr1[2 ] = yearmon;
                             sArr1[3 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MSDATE")), 8, " ");
                             sArr1[4 ] = "B";
                             sArr1[5 ] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("CAMT"))), 15, "0");
                             sArr1[6 ] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("SAMT"))), 15, "0");
                             sArr1[7 ] = CommonUtils.filler_blank_left(CommonUtils.nvls(String.valueOf(pMap1.get("CSAMT"))), 15, "0");
                             sArr1[8 ] = "3";
                             sArr1[9 ] = CommonUtils.filler_blank_right(CommonUtils.nvls((String)pMap1.get("MBIGO")), 51, " ");


                       //      sArr1[10] = CommonUtils.filler_blank_right("", 467, " ");



                             for (int ii=0; ii<sArr1.length; ++ii) {
                                 sb.append(String.format("%s", sArr1[ii]));
                             }
                             sb.append("\n");



                        }

                        // CommonUtils.writeTextFile("D:/PROJECT_DM/Hello/src/out/out.dat", sb.toString(), "EUC-KR"); //

                       // System.out.println(sb.toString());

                      CommonUtils.writeTextFile(sDataPath1, sb.toString(), "MS949"); //
                  //    CommonUtils.writeTextFile(sDataPath2, sb1.toString(), "UTF-8"); //
        //////////////////////ㅍ ㅏ 일 작 성  end



                    }


                   //	int nTotal = acnTranService.getTranMonCount(hmParam);

                    //   acnTranService.updateconvertResult(hmParam);


                       String serverIp 	= EgovProperties.getProperty("acuon.life.ftp.ip");
                       int port 			= Integer.valueOf(EgovProperties.getProperty("acuon.life.ftp.port"));
                       String user 		= EgovProperties.getProperty("acuon.life.ftp.user");
                       String pw 			= EgovProperties.getProperty("acuon.life.ftp.pw");
                       String ftpDataDir	= EgovProperties.getProperty("acuon.life.ftp.join.dataPath") ;

                       // ACUON FTP 파일전송
                       try {

                           boolean isSuccess = false;
                           File file = null;

                           SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw);
                           ftp.setDebugMode(true);
                           ftp.connect();
                           ftp.setPassiveMode();
                           ftp.setFileType(FTP.BINARY_FILE_TYPE);

                           // 경로 이동
                           ftp.chdir(ftpDataDir);

                           file = new File(sDataPath1);
                           if (file.exists()) {
                               log.debug("> file.getPath() : " + file.getPath());
                               log.debug("> file.getName() : " + file.getName());
                               isSuccess = ftp.upload(file.getPath(), ftpDataDir + file.getName());
                               if (isSuccess) {
                                   log.debug("[" + file.getPath() + "] 파일 업로드 성공!");
                               } else {
                                   log.debug("[" + file.getPath() + "] 파일 업로드 실패!");

                               }
                           }


                           ftp.close();

                       } catch (IOException ex) {
                           throw new Exception(ex.getMessage());
                       }

             /* 전송이 완료했으면... GUBUN  값   =  '2'  전송으로 변경 */
                       acnTranService.updatesuconvertResult(hmParam);
                  //     AcnTranMap.updateconvertResult

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

    private int  String(int year) {
        // TODO Auto-generated method stub
        return 0;
    }




    @RequestMapping(value = "/insert")
    public ModelAndView saveWdrwIchaeDt(XPlatformMapDTO xpDto, Model model) throws Exception {

          System.out.println("= = = = insert 입장했음!!!! = = = ");

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> pmParam = new HashMap<String, Object>();


        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값



            pmParam=srchInDs.get(0);


            int nTotal = acnTranService.getTranMonCount(pmParam);
            if ( nTotal >=  1) {

                szErrorCode = "-1";
                szErrorMsg  = "데이터가 있습니다 결과반영 후 생성해주세요";
                 modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                 modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
                return modelAndView ;
            }

            List<Map<String, Object>> result = acnTranService.insertAClist(pmParam);

            listMap.setRowMaps(result);
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

    private void alert(java.lang.String string) {
        // TODO Auto-generated method stub

    }




    @RequestMapping(value = "/suinsert")
    public ModelAndView suinsertdt(XPlatformMapDTO xpDto, Model model) throws Exception {

        //  System.out.println("= = = = suinsert 입장했음!!!! = = = ");

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> pmParam = new HashMap<String, Object>();


        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");	//ds_input == xplatform내에서 가져오는 값

           // System.out.println("----------44444444444444444444444444444444444");

            pmParam=srchInDs.get(0);



            int nTotal = acnTranService.getsuTranMonCount(pmParam);
            if ( nTotal >=  1) {

                szErrorCode = "-1";
                szErrorMsg  = "데이터가 있습니다 결과반영 후 생성해주세요";
                 modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                 modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
                return modelAndView ;
            }


            List<Map<String, Object>> result = acnTranService.suinsertAClist(pmParam);


           // CommonUtils.printLog(result);


            listMap.setRowMaps(result);
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
     * 메인산출err 조회한다
     *
     * 전송월 기준으로 산출결과를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/aclisterr")
    public ModelAndView getTranMonListerr(XPlatformMapDTO xpDto, Model model) throws Exception {

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


                int nTotal = acnTranService.getTranMonerrCount(hmParam);
                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수


                List<Map<String, Object>> mList =acnTranService.getTranMonerrList(hmParam);
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


    @RequestMapping(value = "/yList")
    public ModelAndView getyList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

             // *****  tranMonService.java 생성 >> getTranMonCount(hmParam); 매소드 생성 **********
               hmParam.put("SBIT","Y");

                int nTotal = acnTranService.getTranacuontCount(hmParam);
                mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수

                int nTotal2 = acnTranService.getTranacuonsilCount(hmParam);
                mapOutVar.put("tc_silmem", nTotal2); //tc_silmem=전체건수




               List<Map<String, Object>> mList =acnTranService.getTranYList(hmParam);

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



    @RequestMapping(value = "/select_result")
    public ModelAndView getselectresult(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                //CommonUtils.printLog(hmParam);
            }

            int nTotal = acnTranService.getselectresultCount(hmParam);
            mapOutVar.put("tc_mem", nTotal); //tc_mem=전체건수


            List<Map<String, Object>> mList =acnTranService.gettranseletresultList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.05.15 접속 로그////////////////////////////////////////////////////////////////////////////////
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


}
