/*
 * (@)# DlwProdController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/15
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwVasSearchService;
import powerservice.business.dlw.service.impl.EventMonitorDao;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import powerservice.core.util.excel.ExcelImportRowHandler;
import powerservice.core.util.excel.ExcelImporter;
import powerservice.core.util.excel.ExcelValidator;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwProd
 */
@Controller
@RequestMapping(value = "/dlw/vasSearch")
public class DlwVasSearchController {

    @Resource
    private DlwVasSearchService dlwVasSearchService;

    /**
     * 상품별 부가서비스 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView selectVatIssuCondList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
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
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            ParamUtils.addCenterParam(hmParam);

            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            List<Map<String, Object>> mList1 = dlwVasSearchService.selectVatIssuCondList1(hmParam);
            listMap1.setRowMaps(mList1);
            mapOutDs.put("ds_output1", listMap1);

            List<Map<String, Object>> mList2 = dlwVasSearchService.selectVatIssuCondList2(hmParam);
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

}