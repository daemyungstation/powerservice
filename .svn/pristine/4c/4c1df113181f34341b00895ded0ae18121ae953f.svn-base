/*
 * (@)#ConsController.java
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

package powerservice.business.cns.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cns.service.ConsNewTypeService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.core.util.ParamUtils;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.11 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cons
 */
@Controller
@RequestMapping(value = "/cons/newType")
public class ConsNewTypeController {

    private final Logger log = LoggerFactory.getLogger(ConsNewTypeController.class);

    @Resource
    private ConsNewTypeService consNewTypeService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    /**
     * 상담 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/usr")
    public ModelAndView getConsList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                String sDeptYn = StringUtils.defaultString((String) hmParam.get("dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                // 상담관리(상담사용) - 본인 담당인 상담만 조회
                hmParam.put("user_typ", "chpr_id");
                hmParam.put("user_id", hmParam.get("rgsr_id"));
                
                // 경로 구분 저장
                hmParam.put("path_typ", "usr");

                if ("Y".equals(sDeptYn)) {
                    hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                    hmParam.put("paramAs", "A");
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                    if (!" ".equals(dataAthrQury)) {
                        dataAthrQury = dataAthrQury.replace("AND", "AND (");
                        dataAthrQury += "OR A.DEPT_CD IS NULL)";
                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                String excel_fg = (String)mapInVar.get("excel_fg");
                if (!"Y".equals(excel_fg)) {
                    int nTotal = consNewTypeService.getConsCount(hmParam);

                    mapOutVar.put("tc_cons", nTotal);

                    List<Map<String, Object>> mList = consNewTypeService.getConsList(hmParam);

                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }

                if (null == hmParam.get("dataAthrQury")) {
                    hmParam.put("dataAthrQury", "");
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
           }

	        //2017.12.11 접속 로그////////////////////////////////////////////////////////////////////////////////
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