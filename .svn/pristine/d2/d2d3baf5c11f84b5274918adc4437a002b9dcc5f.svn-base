/*
 * (@)# DlwEvntController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwEmplService;
import powerservice.business.dlw.service.DlwEvntMngService;
import powerservice.business.dlw.service.DlwEvntReceiptService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.dlw.service.DlwPayService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.web.DmWebSenderController;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Controller
@RequestMapping(value = "/dlw/evnt/mng")
public class DlwEvntMngController {

	@Resource
    private DlwEvntMngService DlwEvntMngService; 
	
    /**
     * 대명라이프웨이 행사 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEvntBranchList")  //지부관리 리스트
    public ModelAndView getEvntBranchList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = DlwEvntMngService.getEvntBranchList(hmParam);

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
    
    /**
     * 대명라이프웨이 행사 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertEvntBranch")  //지부장  insert
    public ModelAndView insertEvntBranch(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList1 = DlwEvntMngService.getBranchCode(hmParam);

                String branch_cd =(String) mList1.get(0).get("branch_cd");

                hmParam.put("branch_cd",branch_cd);

              //CommonUtils.printLog(hmParam);

                DlwEvntMngService.insertEvntBranch(hmParam);

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
    
    @RequestMapping(value = "/updateEvntBranch")  //지부. update
    public ModelAndView updateEvntBranch(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                System.out.println("======================================>>>>>> updateEvntBranch");
                CommonUtils.printLog(hmParam1);
                 DlwEvntMngService.updateEvntBranch(hmParam1);
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

    @RequestMapping(value = "/deleteEvntBranch")  //장례식장 장소 삭제
    public ModelAndView deleteEvntBranch(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                 DlwEvntMngService.deleteEvntBranch(hmParam1);
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
    
    @RequestMapping(value = "/selectEnvtBranch")  //장례식장 장소 저장
    public ModelAndView selectEnvtBranch(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = DlwEvntMngService.selectEnvtBranch(hmParam);

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
    
    @RequestMapping(value = "/getEventManList")  //행사자 관리 리스트
    public ModelAndView getEventManList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                        List<Map<String, Object>> mList = DlwEvntMngService.getEventManList(hmParam);
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
    
    @RequestMapping(value = "/insrtEventMan") // 행사자 관리  등록
    public ModelAndView insrtEventMan(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                                    sCnt = DlwEvntMngService.chkEventManaCnt(hmParam);
                                }
                            }
                        }

                        CommonUtils.printLog("sCnt: " + sCnt);

                        if(sCnt > 0){
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, "등록된 지부장이 존재합니다. 동일 지부에 지부장은 1명만 등록할 수 있습니다.");

                            return modelAndView;
                        }

                        String sResult = DlwEvntMngService.insrtEventMan(hmParam);
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



    @RequestMapping(value = "/deleteEventMan")  //행사자 관리 삭제
    public ModelAndView deleteEventMan(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                DlwEvntMngService.deleteEventMan(hmParam);

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

    @RequestMapping(value = "/updateEventMan")  //행사장 업데이트
    public ModelAndView updateEventMan(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                            sCnt = DlwEvntMngService.chkEventManaCnt(hmParam);
                        }
                    }
                }

                CommonUtils.printLog("sCnt: " + sCnt);

                if(sCnt > 0){
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "등록된 지부장이 존재합니다. 동일 지부에 지부장은 1명만 등록할 수 있습니다.");

                    return modelAndView;
                }

                DlwEvntMngService.updateEventMan(hmParam);

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
    
    @RequestMapping(value = "/getEvntWHList")  //창고 관리 리스트
    public ModelAndView getEvntWHList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntWHList(hmParam);

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
     * 창고 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveEvntWarehouse")
    public ModelAndView saveEvntWarehouse(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");


        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mOut = new HashMap<>();

        try {

            int cnt = DlwEvntMngService.saveEvntWarehouse(xpDto, mOut);

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
    
    @RequestMapping(value = "/getEvntSPList")  //제공용품 리스트
    public ModelAndView getEvntSPList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPList(hmParam);

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
     * 제공용품 등록/수정/삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveEvntSupplies")
    public ModelAndView saveEvntSupplies(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");


        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mOut = new HashMap<>();

        try {

            int cnt = DlwEvntMngService.saveEvntSupplies(xpDto, mOut);

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
    
    @RequestMapping(value = "/getEvntSPmaxSqncList")  //제공용품 리스트
    public ModelAndView getEvntSPmaxSqncList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPmaxSqncList(hmParam);

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
    
    @RequestMapping(value = "/getEvntSPCtgMstList")  //제공용품 리스트
    public ModelAndView getEvntSPCtgMstList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPCtgMstList(hmParam);

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
    
    @RequestMapping(value = "/getEvntSPCtgDtlList")  //제공용품 리스트
    public ModelAndView getEvntSPCtgDtlList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPCtgDtlList(hmParam);

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
    
    @RequestMapping(value = "/getEvntSPCtgSubList")  //제공용품 리스트
    public ModelAndView getEvntSPCtgSubList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPCtgSubList(hmParam);

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
    
    @RequestMapping(value = "/saveEvntSPCtgList")
    public ModelAndView saveEvntSPCtgList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs1 = (DataSetMap)mapInDs.get("ds_input1");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");
            DataSetMap listInDs3 = (DataSetMap)mapInDs.get("ds_input3");
            
            String sMstSeq = "";
            
            if (listInDs1.size() > 0) 
            { 
            	Map hmParam = listInDs1.get(0);
                ParamUtils.addSaveParam(hmParam);
            	
                
            	Map mDataRow = null;
            	Map mSeq = null;
                
                for (int idx = 0; idx < listInDs1.size(); idx++) 
                {
                	mDataRow = listInDs1.get(idx);
                    ParamUtils.addSaveParam(mDataRow);

                    mDataRow.put("reg_man", hmParam.get("rgsr_id"));
                    mDataRow.put("upd_man", hmParam.get("rgsr_id"));
                    
                    int iRowType = ((Integer) mDataRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    
                    if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                    {
                    	DlwEvntMngService.updateEvntSPCtgMst(mDataRow);
                    }
                    else if (iRowType == DataSet.ROW_TYPE_INSERTED) 
                    {
                    	sMstSeq = String.valueOf(mDataRow.get("mst_seq"));
                    	
                        if("null".equals(sMstSeq)) {
                        	List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPCtgSeqList(hmParam);
                        	mSeq = mList.get(0);
                        	
                        	mDataRow.put("mst_seq", mSeq.get("mst_seq"));
                        	sMstSeq = mSeq.get("mst_seq").toString();
                        	System.out.println("--------------------sMstSeq: "+sMstSeq);
                        	//키 max+1 채번 세팅후 sMstSeq에 세팅
                        }
                        DlwEvntMngService.insertEvntSPCtgMst(mDataRow);
                    }
                    else if (iRowType == DataSet.ROW_TYPE_DELETED) 
                    {
                    	DlwEvntMngService.deleteEvntSPCtgMst(mDataRow);
                    }
                }
            }
            
            if (listInDs2.size() > 0) 
            { 
            	Map hmParam2 = listInDs2.get(0);
                ParamUtils.addSaveParam(hmParam2);
            	
                
            	Map mDataRow2 = null;
                
                for (int idx = 0; idx < listInDs2.size(); idx++) 
                {
                	mDataRow2 = listInDs2.get(idx);
                    ParamUtils.addSaveParam(mDataRow2);

                    mDataRow2.put("reg_man", hmParam2.get("rgsr_id"));
                    mDataRow2.put("upd_man", hmParam2.get("rgsr_id"));
                    
                    int iRowType2 = ((Integer) mDataRow2.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    
                    if (iRowType2 == DataSet.ROW_TYPE_UPDATED) 
                    {
                    	DlwEvntMngService.updateEvntSPCtgDtl(mDataRow2);
                    }
                    else if (iRowType2 == DataSet.ROW_TYPE_INSERTED) 
                    {
                    	String sMstSeq2 = String.valueOf(mDataRow2.get("mst_seq"));
                        
                        if("null".equals(sMstSeq2)) {
                        	mDataRow2.put("mst_seq", sMstSeq);
                        }
                        DlwEvntMngService.insertEvntSPCtgDtl(mDataRow2);
                    }
                    else if (iRowType2 == DataSet.ROW_TYPE_DELETED) 
                    {
                    	DlwEvntMngService.deleteEvntSPCtgDtl(mDataRow2);
                    }
                }
            }
            
            if (listInDs3.size() > 0) 
            { 
            	Map hmParam3 = listInDs3.get(0);
                ParamUtils.addSaveParam(hmParam3);
            	
                
            	Map mDataRow3 = null;
                
                for (int idx = 0; idx < listInDs3.size(); idx++) 
                {
                	mDataRow3 = listInDs3.get(idx);
                    ParamUtils.addSaveParam(mDataRow3);

                    mDataRow3.put("reg_man", hmParam3.get("rgsr_id"));
                    mDataRow3.put("upd_man", hmParam3.get("rgsr_id"));
                    
                    int iRowType3 = ((Integer) mDataRow3.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    
                    if (iRowType3 == DataSet.ROW_TYPE_INSERTED) 
                    {
                    	String sMstSeq3 = String.valueOf(mDataRow3.get("mst_seq"));
                        
                        if("null".equals(sMstSeq3)) {
                        	mDataRow3.put("mst_seq", sMstSeq);
                        }
                        DlwEvntMngService.insertEvntSPCtgSub(mDataRow3);
                    }
                    else if (iRowType3 == DataSet.ROW_TYPE_DELETED) 
                    {
                    	DlwEvntMngService.deleteEvntSPCtgSub(mDataRow3);
                    }
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/getEvntSPpopList")  //제공용품 리스트
    public ModelAndView getEvntSPpopList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                    List<Map<String, Object>> mList = DlwEvntMngService.getEvntSPpopList(hmParam);

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
}