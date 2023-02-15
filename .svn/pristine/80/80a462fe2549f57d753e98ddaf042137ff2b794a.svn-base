/*
 * (@)# XlsItemController.java
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

package powerservice.business.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sys.service.XlsItemService;
import powerservice.business.sys.service.XlsService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 엑셀 항목 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID XlsItem
 */
@Controller
@RequestMapping(value = "/syst/xls-item")
public class XlsItemController {

    @Resource
    private XlsService xlsService;

    @Resource
    private XlsItemService xlsItemService;

    /**
     * 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getXlsItemList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = xlsItemService.getXlsItemList(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 다운로드를 위한 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download-list")
    public ModelAndView getXlsItemListForDownload(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        List<Map<String, Object>> mList = null;

        // 다운로드 엑셀의 SQLMAPID 조회
        List<Map<String, Object>> mXlsList = xlsService.getXlsList(pmParam);
        if (mXlsList.size() > 0) {
            Map<String, Object> mXls = mXlsList.get(0);
            pmParam.put("cscnt_qury_id", (String) mXls.get("cscnt_qury_id"));
            pmParam.put("list_qury_id", (String) mXls.get("list_qury_id"));

            // 다운로드 엑셀의 항목 리스트 조회
            List<Map<String, Object>> mXlsItemList = xlsItemService.getXlsItemList(pmParam);

            // 다운로드 엑셀의 항목 리스트 필터링
            mList = xlsItemService.getXlsItemListForDownload(pmParam, mXlsItemList);
        } else {
            mList = new ArrayList<Map<String, Object>>();
        }

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 엑셀 항목 보기
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/popup-list")
    public ModelAndView searchExcepPropPopup(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> mData = new HashMap<String, Object>();

        // 엑셀 리스트 조회
        List<Map<String, Object>> mXlsList = xlsService.getXlsList(pmParam);
        if (mXlsList == null || mXlsList.size() == 0) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("엑셀 정보가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        Map<String, Object> mXls = mXlsList.get(0);
        pmParam.put("cscnt_qury_id", (String) mXls.get("cscnt_qury_id"));
        pmParam.put("list_qury_id", (String) mXls.get("list_qury_id"));

        // 엑셀 항목 리스트 조회
        List<Map<String, Object>> mXlsItemList = xlsItemService.getXlsItemList(pmParam);
        mData.put("excelitemlist", mXlsItemList);

        // 등록된 엑셀 항목을 제외한 SQL 컬럼 리스트 조회
        List<Map<String, Object>> mSqlColumnList = xlsItemService.getXlsItemListGetColumns(pmParam, mXlsItemList);
        mData.put("sqlcolumnlist", mSqlColumnList);

        oResult.setData(mData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 엑셀 항목 저장
     *
     * @param pmModelList List<Map<String, Object>>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/popup-save")
    public ModelAndView saveXlsItemPopup(@RequestBody List<Map<String, Object>> pmModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if (pmModelList != null && pmModelList.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            xlsItemService.updateXlsItemPopup(pmModelList, mParam);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 엑셀 항목 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/popup-insert")
    public ModelAndView insertXlsItemPopup(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("sqlcolumncheck"));
        pmParam.put("sXlsColmIdList", sSelectCheck.split(","));

        xlsItemService.insertXlsItemPopup(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 엑셀 항목 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/popup-delete")
    public ModelAndView deleteXlsItemPopup(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", sSelectCheck.split(","));

        xlsItemService.deleteXlsItemPopup(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}