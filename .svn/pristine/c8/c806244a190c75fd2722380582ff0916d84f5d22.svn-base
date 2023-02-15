/*
 * (@)# ExtcCndtController.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.cam.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cam.service.TrgtListService;
//2017.12.26 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.onln.service.ExtcTrgtService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import sun.misc.BASE64Encoder;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
/**
 * 추출 대상
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/06/02
 * @프로그램ID ExtcCndt
 */

@Controller
@RequestMapping(value = "/cmpg/extc-trgt")
public class ExtcTrgtController {

    @Resource
    private ExtcTrgtService extcTrgtService;

    @Resource
    private TrgtListService trgtListService;

    @Resource
    private CommonService commonService;

    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getExtcCndtList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            //System.out.println(hmParam);
            //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            String sCmpgTypCd = StringUtils.defaultString((String) hmParam.get("cmpg_typ_cd"));
            String sCmpgTypCd2 = StringUtils.defaultString((String) hmParam.get("cmpg_typ_cd2"));
            int nTotal = 0;
            List<Map<String, Object>> mExtcCndtList = new ArrayList<Map<String,Object>>();

            nTotal = extcTrgtService.getExtcTrgtCount(hmParam);

            mExtcCndtList =  extcTrgtService.getExtcTrgtList(hmParam);

            mapOutVar.put("total_count", nTotal);
            listMap.setRowMaps(mExtcCndtList);
            mapOutDs.put("ds_output", listMap);

            //2017.12.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            //DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");
            //
            //if (listLogIn.size() > 0) {
            //    hmParam = listLogIn.get(0);
            //    commonService.insertLog(hmParam);
            //}
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tmcust")
    @ResponseBody
    public ModelAndView insertTmCust(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
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
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                ParamUtils.addCenterParam(hmParam);
                //가져오기시 살린다.extcTrgtService.insertB2QTmJoinCust(hmParam);

                //총추출건수를 위해 검색한다.
                Map<String, Object> mTrgtList = trgtListService.getTrgtList((String)hmParam.get("trgt_list_id"));

                //총추출건수 가져온다
                mapOutVar.put("total_cust_data_extc_cnt", mTrgtList.get("cust_data_extc_cnt"));
            }
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

            }catch(Exception e){
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }

            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

            return modelAndView;
        }


    /**
     * 1일자기준 업체, 해약방어, 납입 가져오기
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst")
    @ResponseBody
    public ModelAndView mst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            extcTrgtService.insertLntmBzptAdmn();//업체관리건
            extcTrgtService.insertLntmCnctPrtc();//해약방어건
            extcTrgtService.insertUnpy();//연체건
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 당월미납가져오기
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst1")
    @ResponseBody
    public ModelAndView mst1(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            hmParam.put("ichae_dt", mapInVar.get("ichae_dt"));
            extcTrgtService.insertMonthUnpy(hmParam);//당월미납
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 실적가져오기
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst2")
    @ResponseBody
    public ModelAndView mst2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            extcTrgtService.updateCnctAcrs();
            extcTrgtService.updateUnpyAcrs();
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 제외 상품 업데이트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst3")
    @ResponseBody
    public ModelAndView mst3(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            extcTrgtService.updatExcd();
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 해당월의 추출된 데이터에 분배액을 업데이트 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst4")
    @ResponseBody
    public ModelAndView mst4(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            extcTrgtService.updateAltnAmt();
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 해당월의 추출된 데이터에 제외대상을 제외한후 제외대상이 아닌것들을 MS_SQL 테입블에 인서트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mst5")
    @ResponseBody
    public ModelAndView mst5(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            extcTrgtService.insertDlwYenche();
        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/unpystat")
    @ResponseBody
    public ModelAndView getUnpyStat(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            hmParam.put("accnt_no",mapInVar.get("accnt_no"));
            List<Map<String, Object>> mUnpyStat = new ArrayList<Map<String,Object>>();
            mUnpyStat =  extcTrgtService.getUnpyStat(hmParam);
            listMap.setRowMaps(mUnpyStat);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }



    /**
     **   B2B업체 코드 조회 컨트롤러
     **/
    @RequestMapping(value = "/selectB2BList")
    public ModelAndView selectB2BList(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # # # # # B2B업체 코드 조회 컨트롤러 # # # # # # # # # # # # #");
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

                        extcTrgtService.selectB2BList(hmParam);

                     }
                            List<Map<String, Object>> mList =extcTrgtService.selectB2BList(hmParam);
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
     * B2B업체코드   등록
     **/
    @RequestMapping(value = "/insertB2BComp")
    public ModelAndView insertB2BComp(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("#  		B2B업체코드   등록           #");
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

                     String sResult = extcTrgtService.insertB2BComp(hmParam);

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
    ** B2B업체코드 수정
    */
    @RequestMapping(value = "/updateB2BComp")
    public ModelAndView updateB2BComp(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # #			B2B업체코드 수정		 # # # # # # # #");
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
                extcTrgtService.updateB2BComp(hmParam);

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



    @RequestMapping(value = "/deleteB2bcd")  //거래처 삭제
    public ModelAndView deleteB2bcd(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                extcTrgtService.deleteB2bcd(hmParam);


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
     **   레저연동 조회 컨트롤러
     **/
    @RequestMapping(value = "/getCpHist")


    public ModelAndView getCpHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                System.out.println("파라미터 : " + hmParam);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");//페이징 처리하는 공용 메소드
            
            if (listInDs != null && listInDs.size() > 0) 
            {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            
            int nTotal = extcTrgtService.countCpHist(hmParam);
            mapOutVar.put("tc_cnctRtamt", nTotal);
                            
            List<Map<String, Object>> mList = extcTrgtService.getCpHist(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.02.02 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) 
            {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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


    public String nullStringProc(String param, boolean incoding)
    {
      if ((param == null) || (param.equals(""))) {
        param = "";
      } else if (incoding) {
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] b1 = param.getBytes();
        param = encoder.encode(b1);
      }

      return param;
    }


    /* 레저연동 관리 (하도 데이터가 안맞아서 수정 20170809) */
    @RequestMapping(value = "/tmsSending")
    public ModelAndView tmsYenCheSending(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # #      레저연동 TMS 컨트롤러     # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String,Object> sendResult = new HashMap<String, Object>(); //레저 전송 후 결과값 리턴
        List<Map<String,Object>> lstResult = new ArrayList<>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int succ_cnt = 0;
        int fail_cnt = 0;
        String regMan ="";
        String updMan ="";
        String sErrorType = "";
        String p_id = "";
        String emple_No = "";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            p_id = (String)mapInVar.get("p_id"); //연체레저연동이냐 가입레저연동이냐

            System.out.println("1. =====================================");

            //emple_No = (String)mapInVar.get("emple_no"); //입력자 정보

            System.out.println("1) srchInDs =====================================" + srchInDs.size());

            if (srchInDs != null && srchInDs.size() > 0){
                for (int i = 0; i < srchInDs.size(); i++) {
                    hmParam = srchInDs.get(i);
                    CommonUtils.printLog(hmParam);
                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                    if (rowType == DataSet.ROW_TYPE_UPDATED) {

                        //세션
                        ParamUtils.addSaveParam(hmParam);
                        hmParam.put("emple_no", hmParam.get("rgsr_id"));
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        hmParam.put("upd_man", hmParam.get("rgsr_id"));
                        hmParam.put("trans_emple", hmParam.get("rgsr_id"));

                        //레저 쥅 전송
                        sendResult = extcTrgtService.sendDataDgns(hmParam);
                        System.out.println("2. =====================================");
                        CommonUtils.printLog(sendResult);

                        if("".equals(nullStringProc((String)sendResult.get("errMsg"), false))) {
                            succ_cnt = succ_cnt + 1;
                            if("yench".equals(p_id)){
                                extcTrgtService.updateRsYenCheSndEnd(sendResult);

                                //레저연동 히스토리 입력 데이터 생성
                                if("y".equals(sendResult.get("yenchae_yn"))){
                                    sendResult.put("trans_stat", "04");
                                }else{
                                    sendResult.put("trans_stat", "05");
                                }

                            } else {
                                extcTrgtService.updateRsSndEnd(sendResult);
                            }
                            System.out.println("3. =====================================");
                        }else{
                            fail_cnt = fail_cnt + 1;
                            //오류
                            sendResult.put("trans_stat", "06");
                            sendResult.put("etc", nullStringProc((String)sendResult.get("errMsg"), false));
                            //throw new EgovBizException(nullStringProc((String)sendResult.get("errMsg"), false));
                        }
                        //레저연동 히스토리 등록
                        extcTrgtService.insertResortMngHstr(sendResult);
                    }
                }

                hmParam.put("succCnt", Integer.valueOf(succ_cnt));
                hmParam.put("errCnt", Integer.valueOf(fail_cnt));
                System.out.println("4. =====================================");

                lstResult.add(sendResult);
                listMap.setRowMaps(lstResult);
                mapOutDs.put("ds_output", listMap);
            }
                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

            }catch (Exception e){
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
            return modelAndView;
      }

    @RequestMapping(value = "/tmsSending_back")
    public ModelAndView tmsYenCheSending_back(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # #      레저연동 TMS 컨트롤러     # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String,Object>> lstResult = new ArrayList<>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int succ_cnt = 0;
        int fail_cnt = 0;
        String regMan ="";
        String updMan ="";
        String sErrorType = "";
        String p_id = "";
        String emple_No = "";

            try {
                Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                p_id = (String)mapInVar.get("p_id"); //연체레저연동이냐 가입레저연동이냐
                emple_No = (String)mapInVar.get("emple_no"); //입력자 정보

                if (srchInDs != null && srchInDs.size() > 0){
                   // hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                   // CommonUtils.printLog(hmParam);
                    for (int i = 0; i < srchInDs.size(); i++) {
                        hmParam = srchInDs.get(i);
                        int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                        if (rowType == DataSet.ROW_TYPE_UPDATED) {
                            //CommonUtils.printLog(hmParam);
                            if (hmParam.get("accnt_no") != null){
                                  String accntNo = nullStringProc((String)hmParam.get("accnt_no"), false);
                                  String memNm = nullStringProc((String)hmParam.get("mem_nm"), false);
                                  String trMemNo = nullStringProc((String)hmParam.get("resort_no"), false);
                                  String rMemNo = nullStringProc((String)hmParam.get("resort_mem_no"), false);
                                  String rMemGubun = nullStringProc((String)hmParam.get("pay_gubun"), false);
                                  String eventProcDay = nullStringProc((String)hmParam.get("event_proc_day"), false);
                                  String joinDt = nullStringProc((String)hmParam.get("join_dt"), false);
                                  String resnAcptDay = nullStringProc((String)hmParam.get("resn_acpt_day"), false);
                                  String cell = nullStringProc((String)hmParam.get("cell"), false);
                                  String idnNo = nullStringProc((String)hmParam.get("idn_no"), true);
                                  String payDay = nullStringProc((String)hmParam.get("pay_day"), false);
                                  String payGubun = nullStringProc((String)hmParam.get("resort_mem_gubun"), false);
                                  String yenchae_yn = "";
                                  String ci_val = nullStringProc((String)hmParam.get("ci_val"), false);
                                  regMan = nullStringProc((String)hmParam.get("emple_no"),false);

                                  //행사 여부
                                  if ("Y".equals(extcTrgtService.isEvent(accntNo))){
                                      yenchae_yn = nullStringProc("n", false);
                                  }else {
                                      yenchae_yn = nullStringProc((String)hmParam.get("yenchae_yn"), false);
                                  }

                                  String param = "?lwMemNo=" + accntNo + "&lwMemName=" + URLEncoder.encode(memNm, "EUC-KR") +
                                    "&trMemNo=" + trMemNo + "&rMemNo=" + rMemNo +
                                    "&rMemGubun=" + rMemGubun + "&jDate=" + joinDt +
                                    "&eDate=" + eventProcDay + "&cDate=" + resnAcptDay +
                                    "&lwMemMobile=" + cell + "&lwMemId=" + idnNo +
                                    "&oDate=" + payDay + "&payGubun=" + payGubun +
                                    "&lwyunchae_yn=" + yenchae_yn + "&cntcInfo=" + ci_val ;

                                      System.out.println("=====[1]======>:"+ param);

                                      //System.out.println("=============param:"+param);
                                      String server = "https://mobile.daemyung.com/mem/inf/saveLifewayPkg.do" + param;
                                    //String server = "http://aaaa.daemyung.com/mem/inf/saveLifewayPkg.do" + param;
                                      String result = new String();
                                      String tmplsSndRslt = "";

                                      System.out.println("=====[2]======>:"+ server);

                                    try {
                                        URL url = new URL(server);
                                        URLConnection connection = url.openConnection();
                                        connection.setDoInput(true);
                                        connection.setDoOutput(true);
                                        connection.setUseCaches(false);
                                        //connection.setConnectTimeout(8000);
                                        connection.setReadTimeout(20000);
                                        //connection.connect();

                                        InputStream is = connection.getInputStream();
                                        InputStreamReader isr = new InputStreamReader(is);
                                        BufferedReader br = new BufferedReader(isr);
                                        String buf = null;
                                        result = br.readLine();

                                        System.out.println("=====[3]======>:"+ result);

                                        String[] temp = result.split("&");
                                        String[] success = temp[0].split("=");
                                        if (success[1].equals("success")) {
                                            hmParam.put("exception", success[1]);
                                            succ_cnt++;
                                            hmParam.put("success", "완료");
                                            hmParam.put("ls_snd_rslt", "Y");
                                            hmParam.put("emple_no", emple_No);
                                            System.out.println(success[1] + " : " + accntNo);
                                        } else {
                                            hmParam.put("exception", success[1]);
                                            fail_cnt++;
                                            hmParam.put("success", "오류");
                                            hmParam.put("ls_snd_rslt", "F");
                                            System.out.println(success[1] + " : " + accntNo);
                                        }
                                        extcTrgtService.updateRsSndEnd(hmParam);
                                        System.out.println("=====[4]======>:"+ accntNo);
                                    }catch (MalformedURLException mue) {
                                        hmParam.put("exception", "mue");
                                        hmParam.put("ls_snd_rslt", "F");
                                    } catch (IOException ioe) {
                                        if (ioe.getMessage().equals("connect timed out")) {
                                            hmParam.put("exception", "cto");
                                            hmParam.put("ls_snd_rslt", "F");
                                            sErrorType = "connect timed out";
                                            System.out.println("=====[5]======>:"+ sErrorType);
                                            break;
                                        }
                                        hmParam.put("exception", "rto");
                                        hmParam.put("ls_snd_rslt", "F");
                                        // return hmParam;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    }
                    if ("".equals(sErrorType)) {
                        if (srchInDs.size() == succ_cnt + fail_cnt) {
                            for (int  i = 0; i < srchInDs.size(); i++) {
                                hmParam = srchInDs.get(i);
                                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                                    String eventProcDay = nullStringProc((String)hmParam.get("event_proc_day"), false);
                                    String remainNo = nullStringProc((String)hmParam.get("remain_no"), false);
                                    String resnAcptDay = nullStringProc((String)hmParam.get("resn_acpt_day"), false);
                                    String succ = nullStringProc((String)hmParam.get("success"), false);
                                    String lsSndRslt = nullStringProc((String)hmParam.get("ls_snd_rslt"), false);

                                    System.out.println("* * * * * * * succ:"+succ);

                                    if("yench".equals(p_id)){
                                        hmParam.put("upd_man", emple_No);
                                        CommonUtils.printLog(hmParam);
                                        extcTrgtService.updateRsYenCheSndEnd(hmParam);
                                    }
                                    /*
                                    if (("Y".equals(succ)) && ((!"".equals(eventProcDay)) || (!"".equals(resnAcptDay)) || ("0".equals(remainNo))))
                                    {
                                        String accntNo = nullStringProc((String)hmParam.get("accnt_no"), false);
                                        hmParam.put("accnt_no",accntNo);
                                        hmParam.put("use_yn","Y");
                                        hmParam.put("ls_snd_rslt",lsSndRslt);

                                            if("yench".equals(p_id)){
                                                hmParam.put("ls_snd_emple_no", updMan);
                                                CommonUtils.printLog(hmParam);
                                                extcTrgtService.updateRsYenCheSndEnd(hmParam);

                                            }else{
                                                hmParam.put("ls_snd_emple_no", regMan);
                                                // CommonUtils.printLog(hmParam);
                                                extcTrgtService.updateRsSndEnd(hmParam);
                                            }
                                        }
                                    */
                                }
                            }

                            hmParam.put("succCnt", Integer.valueOf(succ_cnt));
                            hmParam.put("errCnt", Integer.valueOf(fail_cnt));

                            CommonUtils.printLog(hmParam);

                            lstResult.add(hmParam);
                            listMap.setRowMaps(lstResult);
                            mapOutDs.put("ds_output", listMap);
                        }
                        /*
                        if("yench".equals(p_id)){
                            List<Map<String, Object>> mList =extcTrgtService.selectResortYenCheInfoList(hmParam);
                        }else{
                            List<Map<String, Object>> mList =extcTrgtService.getCpHist(hmParam);
                        }
                        */
                    } else { // 에러 발생한 경우
                        lstResult.add(hmParam);
                        listMap.setRowMaps(lstResult);
                        mapOutDs.put("ds_output", listMap);
                    }

                    modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                    modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
                }
            }catch (Exception e){
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg  = e.getMessage();
            }
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
            return modelAndView;
      }


    /**
     **   레저연체자 관리 조회 컨트롤러
     **/
    @RequestMapping(value = "/selectResortYenCheInfoList")
    public ModelAndView selectResortYenCheInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        System.out.println("# # # # # # # # # #      레저연체 관리 조회 컨트롤러     # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
            }

            if("Y".equals(mapInVar.get("pageSet")))
            {
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                
                if (listInDs != null && listInDs.size() > 0) 
                {
                    Map pMap = listInDs.get(0);
                    hmParam.put("pageSet", "SET");
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }
            
            int nTotal = extcTrgtService.countResortYenCheInfoDataCnt(hmParam);
            mapOutVar.put("tc_cnctRtamt", nTotal);

            List<Map<String, Object>> mList = extcTrgtService.selectResortYenCheInfoList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.02.27 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) 
            {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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




        //레저연체정보 갱신
    @RequestMapping(value = "/yenCheInfoRefresh")
    public ModelAndView yenCheInfoRefresh(XPlatformMapDTO xpDto) throws Exception {
         ModelAndView modelAndView = new ModelAndView("xplatformMapView");
         System.out.println("# # # # # # # # # #      레저연체정보 갱신 컨트롤러     # # # # # # # # # #");

         DataSetMap listMap = new DataSetMap();
         Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
         Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

         // 에러코드및 메시지
         String szErrorCode="0";
         String szErrorMsg="OK";
         Map<String, Object> mOut = new HashMap<>();

         try {

             /*아직 발송 안된 회원의 경우 발송 후 갱신 하도옥 처리 */
            int nTotal = extcTrgtService.countResortYenCheInfoList();
            if (nTotal > 0) {
                throw new EgovBizException("아직 전송 안된 회원정보가 있습니다 전송후 시도하세요!!");
            }


             int cnt = extcTrgtService.yenCheInfoRefresh(xpDto, mOut);

             System.out.println(">>>>>>>>>>>>>");
             System.out.println(">>>>>>>>>>>>>cnt : " + cnt );
             System.out.println(">>>>>>>>>>>>>" );

             modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
             modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

            // mapOutVar.put("accnt_no", accnt_no);
             List<Map<String,Object>> mList = new ArrayList<>();
             mList.add(mOut);
             listMap.setRowMaps(mList);
             mapOutDs.put("ds_output", listMap);

         } catch (Exception e) {
             e.printStackTrace();
             szErrorCode = "-1";
             szErrorMsg  = e.getMessage();
         }

         modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
         modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

         return modelAndView;
     }


     /**   레저연동 이력 조회     **/
    @RequestMapping(value = "/selectResortMngHstr")
    public ModelAndView selectResortMngHstr(XPlatformMapDTO xpDto, Model model) throws Exception {

        System.out.println("# # # # # # # # # #      레저연체 관리 조회 컨트롤러     # # # # # # # # # #");
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

            try {
                    Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
                    Map <String, Object> mapInVar     = xpDto.getInVariableMap();
                    Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                    Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

                    DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

                    if (srchInDs != null && srchInDs.size() > 0){
                        hmParam = srchInDs.get(0); //index값을 가져오는 get메소드 >> 첫번째 index값을 가져온다
                    }

                    //System.out.println(">>>>>>>>>>>>>   " + mapInVar.get("pageSet"));
                    // 페이징 정보
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }

                    int totValue =extcTrgtService.selectResortMngHstr_cnt(hmParam);

                    List<Map<String, Object>> mList =extcTrgtService.selectResortMngHstr(hmParam);

                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);

                    //2018.02.27 접속 로그////////////////////////////////////////////////////////////////////////////////
                    DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                    if (listLogIn.size() > 0) {
                        hmParam = listLogIn.get(0);
                        commonService.insertLog(hmParam);
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////

                    mapOutVar.put("tc_cnctRtamt", totValue);
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


}