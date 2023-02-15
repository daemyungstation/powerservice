/*
 * (@)# DlwCustController.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
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

package powerservice.business.gongje.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.gongje.service.GongjeService;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2018.03.16 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 대명 고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value = "/gongje/new")
public class GongjeController {

    private final Logger log = LoggerFactory.getLogger(GongjeController.class);

    @Resource
    private GongjeService gongjeService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private UserService userService;

    @Resource
    private CommonService commonService;

    /**
     * 공제-조회시 Temp테이블 입력 및 기존데이터 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/temp")
    public ModelAndView getGongjeInsert_Temp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog("TempTempTemppppppppppppppppppppppppppppppppppppppppppp:" + hmParam);

                gongjeService.getGongjeTempDelete();
                gongjeService.getGongjeNewInsert_Temp(hmParam);
                gongjeService.getGongjeChngInsert_Temp(hmParam);
                gongjeService.getGongjeRessInsert_Temp(hmParam);
                gongjeService.getGongjeYdsInsert_Temp(hmParam);
                gongjeService.getGongjeHangsaInsert_Temp(hmParam);
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

    /**
     * 공제-조회시 Temp테이블 입력 및 기존데이터 삭제
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/temppay")
    public ModelAndView getGongjeInsertPay_Temp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog("TempTempTemppppppppppppppppppppppppppppppppppppppppppp:" + hmParam);

                gongjeService.getGongjeTempDelete();
                gongjeService.getGongjePayInsert_Temp(hmParam);
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

    /**
     * 공제 - Excel 다운로드전 기존 미전송 데이터 체크
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/excelchk")
    public ModelAndView getGongjeExcelChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            ParamUtils.addCenterParam(hmParam);

            int sTotal = gongjeService.getGongjeExcelchkCount();

            CommonUtils.printLog("엑셀미전송데이터 건수:"+sTotal);

            if(sTotal > 0){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "전송처리하지 않은 데이터가 존재합니다. 전송처리 이후 진행해주세요!");

                return modelAndView;
            }

            mapOutVar.put("total", sTotal);


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
     * 공제 - Excel 전송상태 (미전송) 데이터 취소
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancelEx")
    public ModelAndView getGongjeExcelcancel(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            ParamUtils.addCenterParam(hmParam);

            int sTotal = gongjeService.getGongjeExcelchkCount();

            CommonUtils.printLog("엑셀처리데이터 건수:"+sTotal);

            if(sTotal < 1){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "취소할 데이터가 존재하지 않습니다.");

                return modelAndView;
            }

            // 엑셀비트 미전송 데이터 삭제
            gongjeService.getGongjeExcelcancel();

            mapOutVar.put("total", sTotal);


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
     * 공제-신규 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getGongjeNewList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeNewCount(hmParam);
                mapOutVar.put("new_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeNewList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제-신규 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/newsave")
    public ModelAndView getGongjeNewInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("BBBBBBBBBBBBBBBBBBBBBBBBCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeNewInsert(hmParam);
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


    /**
     * 공제-해약 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ress")
    public ModelAndView getGongjeRessList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeRessCount(hmParam);
                mapOutVar.put("ress_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeRessList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 공제-해약 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resssave")
    public ModelAndView getGongjeRessInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeRessInsert(hmParam);
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

    /**
     * 공제-소비자정보 변경을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chng")
    public ModelAndView getGongjeChngList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeChngCount(hmParam);
                mapOutVar.put("chng_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeChngList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 공제-소비자정보 변경 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chngsave")
    public ModelAndView getGongjeChngInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeChngInsert(hmParam);
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

    /**
     * 공제-양도양수 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/yds")
    public ModelAndView getGongjeYdsList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeYdsCount(hmParam);
                mapOutVar.put("yds_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeYdsList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 공제-양도양수 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ydssave")
    public ModelAndView getGongjeYdsInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeYdsInsert(hmParam);
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

    /**
     * 공제-행사 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hangsa")
    public ModelAndView getGongjeHangsaList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+srchInDs);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeHangsaCount(hmParam);
                mapOutVar.put("hangsa_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeHangsaList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 공제-행사 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hangsasave")
    public ModelAndView getGongjeHangsaInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeHangsaInsert(hmParam);
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

    /**
     * 공제-선수금납입 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay")
    public ModelAndView getGongjePayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                    CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+hmParam);
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjePayCount(hmParam);
                mapOutVar.put("tc_prod", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjePayList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제-선수금 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/paysave")
    public ModelAndView getGongjePayInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjePayInsert(hmParam);
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

    /**
     * 공제-선수금취소 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/paycncl")
    public ModelAndView getGongjePay_CnclList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                String sAccntNo = CommonUtils.nvls((String)mapInVar.get("accnt_no"));

                CommonUtils.printLog("hmParam: " + hmParam);
                CommonUtils.printLog("sAccntNo: " + sAccntNo);

                int nTotal = gongjeService.getGongjePay_CnclCount(hmParam);
                mapOutVar.put("pay_cnt", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjePay_CnclList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제-선수금취소 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/paycncl_save")
    public ModelAndView getGongjePay_CnclInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog("hmParam: " + hmParam);

                gongjeService.getGongjePay_CnclInsert(hmParam);

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
     * 공제-해약취소 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resscncl")
    public ModelAndView getGongjeRess_CnclList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                String sAccntNo = CommonUtils.nvls((String)mapInVar.get("accnt_no"));

                CommonUtils.printLog("hmParam: " + hmParam);
                CommonUtils.printLog("sAccntNo: " + sAccntNo);

                List<Map<String, Object>> mList = gongjeService.getGongjeRess_CnclList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제-해약취소 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resscncl_save")
    public ModelAndView getGongjeRess_CnclInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog("hmParam: " + hmParam);

                gongjeService.getGongjeRess_CnclInsert(hmParam);

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
     * 공제-행사취소 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hangsacncl")
    public ModelAndView getGongjeHangsa_CnclList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                String sAccntNo = CommonUtils.nvls((String)mapInVar.get("accnt_no"));

                CommonUtils.printLog("hmParam: " + hmParam);
                CommonUtils.printLog("sAccntNo: " + sAccntNo);

                List<Map<String, Object>> mList = gongjeService.getGongjeHangsa_CnclList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제-해약취소 엑셀반출시 히스토리 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hangsacncl_save")
    public ModelAndView getGongjeHangsa_CnclInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog("hmParam: " + hmParam);

                gongjeService.getGongjeHangsa_CnclInsert(hmParam);

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
     * 공제 - (신규/해약/정보변경/양도양수/행사) 전송처리
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sendnewup")
    public ModelAndView getGongjeNewUpdate(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog("User: " + oLoginUser.getUserid());

                hmParam.put("user_id", oLoginUser.getUserid());

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjeNewUpdate(hmParam);
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

    /**
     * 공제 - (선수금) 전송처리
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sendpayup")
    public ModelAndView getGongjePayUpdate(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog("User: " + oLoginUser.getUserid());

                hmParam.put("user_id", oLoginUser.getUserid());

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjePayUpdate(hmParam);
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

    /**
     * 공제 - (선수금취소) 전송처리
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sendpaycnclup")
    public ModelAndView getGongjePayCnclUpdate(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                CommonUtils.printLog("User: " + oLoginUser.getUserid());

                hmParam.put("user_id", oLoginUser.getUserid());

                ParamUtils.addCenterParam(hmParam);

                gongjeService.getGongjePayCnclUpdate(hmParam);
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

    /**
     * 공제-전송이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hist")
    public ModelAndView getGongjeHistList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                    CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+hmParam);
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeHistCount(hmParam);
                mapOutVar.put("tc_prod", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeHistList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.16 접속 로그//////////////////////////////////////////////////////////////////////////////
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

    /**
     * 공제 - 결과처리(신규/해약/정보변경/양도양수/행사)
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resultbase")
    public ModelAndView getGongjeBaseResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = gongjeService.getGongjeResultchkCount();

            if(nTotal < 1){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "결과처리 할 데이터가 없습니다.");

                return modelAndView;
            }

            CommonUtils.printLog("nTotal:"+nTotal);

            // 공제전송 히스토리 업데이트
            gongjeService.getGongjeBaseResult();

            // 신규 업데이트
            gongjeService.getGongjeSinResult();
            // 해약 업데이트
            gongjeService.getGongjeRessResult();
            // 정보변경 업데이트
            gongjeService.getGongjeChngResult();
            // 행사 업데이트
            gongjeService.getGongjeHangsaResult();
            // 해약/행사 성공시 회원정보 업데이트
            gongjeService.getGongjeMemProdUpdate();
            // 양도양수 업데이트
            gongjeService.getGongjeYdsResult();
            // 양도양수 공제번호 업데이트
            gongjeService.getGongjeYds_New_Result();
            // 공제전송내역 처리완료비트 업데이트
            gongjeService.getGongjeHistResult();
            // 공제결과내역 처리완료비트 업데이트
            gongjeService.getGongjeLastResult();

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
     * 공제 - 선수금 결과처리(입금)
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resultpay")
    public ModelAndView getGongjePayResult(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = gongjeService.getGongjeResultchkCount();

            if(nTotal < 1){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "결과처리 할 데이터가 없습니다.");

                return modelAndView;
            }

            CommonUtils.printLog("nTotal:"+nTotal);

            // 공제전송 히스토리 업데이트
            gongjeService.getGongjeBaseResult_Pay();

            // 입금테이블 업데이트
            gongjeService.getGongjePayResult();

            // 공제전송내역 처리완료비트 업데이트
            gongjeService.getGongjeHistResult();
            // 공제결과내역 처리완료비트 업데이트
            gongjeService.getGongjeLastResult();

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
     * 공제 - XML 업로드전 유효성 체크
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/upchk")
    public ModelAndView getGongjeUploadChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            ParamUtils.addCenterParam(hmParam);

            int sTotal = gongjeService.getGongjeUploadchkCount();

            CommonUtils.printLog("sTotal:"+sTotal);

            if(sTotal > 0){
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "결과처리되지 않은 업로드 XML 파일이 존재합니다 결과처리 후 진행해주세요!");

                return modelAndView;
            }

            mapOutVar.put("total", sTotal);


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
     * 공제 - XML 업로드(신규/해약/정보변경/양도양수/행사) - 데이터 삽입
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public void insertFile2(final HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pmParam) throws Exception {

        String szErrorCode="0";
        String szErrorMsg="OK";
        String Gubun = "";
        File upfile = null;

        Map<String, Object> hmParam = new HashMap<String, Object>();

        String tempDir = System.getProperty("java.io.tmpdir");
        //MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*100);
        // 2018.01.30 공제조합 선수금 결과파일(103MB) 업로드시 문제발생으로 수정
        // 업로드시 파일크기 100MB 제한 => 200MB 제한으로 변경 - 2018.01.30 정승철
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*200);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();
        Gubun = request.getParameter("gubun");
        CommonUtils.printLog("gubun : " + Gubun);

        try {

            sKey = (String)enm.nextElement();
            log.info("sKey : " + sKey);
            upfile = multipartRequest.getFile(sKey);

            String sAbsFilePath = upfile.getAbsolutePath();
            long nFileSize = upfile.length();

            int S = sAbsFilePath.lastIndexOf("\\");
            int M = sAbsFilePath.lastIndexOf(".");
            int E = sAbsFilePath.length();

            String sBasename = sAbsFilePath.substring(S+1, M);

            log.info("sBasename:"+sBasename);

            pmParam.put("fileNm", sAbsFilePath);
            pmParam.put("fileSize", nFileSize);
            pmParam.put("fileName", sBasename);

            CommonUtils.printLog(pmParam);

            String Gdate = sBasename.replaceAll("[^0-9]", ""); // 파일명에서 숫자만 가져오기

            Gdate = Gdate.substring(0, 8); // 신고일자 가져오기

            pmParam.put("gdate", Gdate);
            pmParam.put("gubun", Gubun);

            log.info("Gdate : " + Gdate);

            gongjeService.insertFile(pmParam);

            gongjeService.getGongjeUploadResult(pmParam);

            resVarList.add("ErrorCode", szErrorCode);
            resVarList.add("ErrorMsg", 	szErrorMsg);
        } catch(Exception e) {
            e.printStackTrace();
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    public byte[] getFileBinaryData(FileItem item) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream is = null;

        FileInputStream fis = null;
        BufferedInputStream input = null;

        try {
            is = item.getInputStream();

            byte[] buff = new byte[8152];
            int len = -1;
            while ((len = is.read(buff)) != -1){  // 512 길이만큼읽어와 버퍼에 저장
                output.write(buff, 0, len); // 버퍼에 지정길이만큼 읽어서 쓴다
            }
        } catch(Exception except) {
            //
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return output.toByteArray();
    }

    /**
     * 선수금 엑셀다운 - 데이터를 엑셀파일로 만들고 엑셀파일을 다운로드한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/exceldown")
    public void getProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        int iSheetIdx = 0;
        int iExcelRowNo = 0;

        try {

            String Stt_dt = CommonUtils.nvls(request.getParameter("stt_dt"));
            String End_dt = CommonUtils.nvls(request.getParameter("end_dt"));
            hmParam.put("stt_dt", Stt_dt);
            hmParam.put("end_dt", End_dt);
            log.debug("date : " + Stt_dt + "/" + End_dt);

            List<Map<String, Object>> mList = gongjeService.getGongjePayExcel(hmParam);

            String sTmpDir = System.getProperty("java.io.tmpdir");
            String xlFileNm = sTmpDir + "filename.xlsx";
            log.debug(">xlFileNm : " + xlFileNm);

            File xfile = new File(xlFileNm);

            WritableWorkbook workbook = Workbook.createWorkbook(xfile);

            WritableSheet sheet = workbook.createSheet("21_0" + String.valueOf(iSheetIdx), iSheetIdx);

            // 해당 셀에 데이터를 입력한다.
            int i, j;
            iExcelRowNo = 0;
            Map<String, Object> mRow = null;
            for (i = 0; i < mList.size(); i++) {

                mRow = mList.get(i);

                iExcelRowNo++;

//            	if (iExcelRowNo % 100 == 0) {
//            		log.debug(">i : " + iExcelRowNo);
//            	}

                j = 0;
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("gubun"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("accnt_no"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("ded_no"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("yymm"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("pay_dt"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("no"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.stringValueOf(mRow.get("pay_amt"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("pay_type"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("type"))));
                sheet.addCell(new Label(j++, iExcelRowNo, CommonUtils.nvls((String)mRow.get("seq_no"))));

                if (iExcelRowNo >= 60000) {
                    iSheetIdx++;
                    sheet = workbook.createSheet("21_0" + String.valueOf(iSheetIdx), iSheetIdx);
                    iExcelRowNo = 0;
                }
            }


            /* 핑드명 */
            for (i=0; i<workbook.getSheetNames().length; i++) {
                sheet = workbook.getSheet(i);

                j=0;
                sheet.addCell(new Label(j++, 0, "구분"));
                sheet.addCell(new Label(j++, 0, "회원번호"));
                sheet.addCell(new Label(j++, 0, "공제번호"));
                sheet.addCell(new Label(j++, 0, "정산년월"));
                sheet.addCell(new Label(j++, 0, "납입일자"));
                sheet.addCell(new Label(j++, 0, "납입회차"));
                sheet.addCell(new Label(j++, 0, "납입금액"));
                sheet.addCell(new Label(j++, 0, "납입구분"));
                sheet.addCell(new Label(j++, 0, "납입방법"));
                sheet.addCell(new Label(j++, 0, "고유번호값"));

            }

            workbook.write();

            // workbook 을 닫는다.
            workbook.close();

            File xDownFile = new File(xlFileNm);
            String contentType = request.getContentType();
            response.setContentType("x-msdownload");

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            String sDisplayFileName = "DMLIFE_PAY.xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();

            try{
                if (xDownFile.exists()) {
                    xDownFile.delete();
                }
            }catch(Exception e){}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 공제-월별보고서 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/magam_data")
    public ModelAndView getGongjeMagamData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap  = new DataSetMap(); // 선수금 현황
        DataSetMap listMap2 = new DataSetMap(); // 회원 현황
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

                ParamUtils.addCenterParam(hmParam);

            }

            // 선수금 현황 조회
            List<Map<String, Object>> mList = gongjeService.getGongjeMagamPay(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 회원 현황 조회
            List<Map<String, Object>> mList2 = gongjeService.getGongjeMagamMem(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

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
     * 월별 보고서 신규내역
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_new")
    public ModelAndView getReportNewList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = gongjeService.getReportNewCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);


            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = gongjeService.getReportNewList(hmParam);
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
     * 월별 보고서 해약내역
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_res")
    public ModelAndView getReportResList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = gongjeService.getReportResCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);


            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = gongjeService.getReportResList(hmParam);
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
     * 월별 보고서 행사내역
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_evnt")
    public ModelAndView getReportEvntList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = gongjeService.getReportEvntCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);


            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = gongjeService.getReportEvntList(hmParam);
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
     * 월별 보고서 기타 환불내역
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_cncl")
    public ModelAndView getReportCnclList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = gongjeService.getReportCnclCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);


            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = gongjeService.getReportCnclList(hmParam);
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
     * 월별보고서 CMS 이체내역 상세팝업 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_cmspay")
    public ModelAndView getGongjeReportCmsPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                    CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+hmParam);
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeReportCmsPayCount(hmParam);
                mapOutVar.put("tc_prod", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeReportCmsPayList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 월별보고서 현금 등 기타내역 상세팝업 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_otpay")
    public ModelAndView getGongjeReportOtPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                    CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+hmParam);
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeReportOtPayCount(hmParam);
                mapOutVar.put("tc_prod", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeReportOtPayList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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
     * 월별보고서 누적선수금내역 상세팝업 리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/report_totalpay")
    public ModelAndView getGongjeReportTotalPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                    CommonUtils.printLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+hmParam);
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = gongjeService.getGongjeReportTotalPayCount(hmParam);
                mapOutVar.put("tc_prod", nTotal);

                List<Map<String, Object>> mList = gongjeService.getGongjeReportTotalPayList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
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