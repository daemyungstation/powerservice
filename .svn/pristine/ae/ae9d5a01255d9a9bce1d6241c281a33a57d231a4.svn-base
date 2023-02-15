/*
 * (@)# ZipCdController.java
 *
 * @author 전예원
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

package powerservice.business.zip.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.WebSvsSoap;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.URLConnector;

/**
 * 우편번호 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 전예원
 * @version 1.0
 * @date 2015/05/13
 * @프로그램ID ZipCd
 */
@Controller
@RequestMapping(value = "/syst/zip-cd")
public class ZipCdController {

    @Resource
    private BasVlService basVlService;

    /**
     * 우편번호를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getBasVlList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            WebSvsSoap wss = new WebSvsSoap();
            wss.inqWebSvs(hmParam);

            mapOutVar.put("pageNo", hmParam.get("pageNo"));
            mapOutVar.put("totPgNum", hmParam.get("totPgNum"));

            listMap.setRowMaps((List<Map<String, Object>>) hmParam.get("lists"));
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
/*
    @RequestMapping(value = "/list")
    public ModelAndView getBasVlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sKeyword = StringUtils.defaultString((String)pmParam.get("keyword"));
        if ("".equals(sKeyword)) {
            throw new Exception("키워드를 입력하세요.");
        }

        // 우편번호 연계 인증키 정보를 가져온다.
        String sZipConfmKey = basVlService.getBasVlAsString("zip_confm_key");
        if ("".equals(sZipConfmKey)) {
            sZipConfmKey = "bnVsbDIwMTQxMTA2MTExMjQ0";
        }

        int nTotalCount = 0;
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 페이징
        ParamUtils.addPagingParam(pmParam);

        try {
            *//********** 필수 파라미터 **********//*
            // String sApiUrl = "http://www.juso.go.kr/link/search.do";
            String sApiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do";
            String sConfmKey = sZipConfmKey;                  // 인증키
            int nPage = (Integer)pmParam.get("page");         // 요청페이지
            int nPageSize = (Integer)pmParam.get("pageSize"); // 페이지당 표시할 게시물수
            *//********** 필수 파라미터 **********//*

            // 프록시 서버 정보를 가져온다.
            // String PROXY_SERVER_IP = basicValueService.getBasicValueAsString("PROXY_SERVER_IP");
            // String PROXY_SERVER_PORT = basicValueService.getBasicValueAsString("PROXY_SERVER_PORT");

            Map<String, Object> mUrlConf = new HashMap<String, Object>();
            Map<String, Object> mUrlParam = new HashMap<String, Object>();

            // URL 컨넥션 셋팅
            mUrlConf.put("URL", sApiUrl);
            mUrlConf.put("method", "GET");
            // mUrlConf.put("PROXY_SERVER_IP", PROXY_SERVER_IP);
            // mUrlConf.put("PROXY_SERVER_PORT", PROXY_SERVER_PORT);

            // 전달할 파라미터 설정
            mUrlParam.put("confmKey",     sConfmKey);
            mUrlParam.put("currentPage",  String.valueOf(nPage));
            mUrlParam.put("countPerPage", String.valueOf(nPageSize));
            mUrlParam.put("keyword",      URLEncoder.encode(sKeyword, "UTF-8"));

            mUrlConf.put("sendParam", mUrlParam);

            String sResultXml = URLConnector.getData(mUrlConf);
            if ("".equals(sResultXml)) {
                throw new Exception("주소 서버로부터 응답을 받지 못했습니다.");
            }

            SAXBuilder oBuilder = new SAXBuilder();
            Document oDocument = oBuilder.build(new InputSource(new ByteArrayInputStream(sResultXml.getBytes("UTF-8"))));
            Element oRootElement = oDocument.getRootElement();

            // 오류메시지 확인
            Element oCommonElement = oRootElement.getChild("common");
            if (oCommonElement != null) {
                Element oErrorMessage = oCommonElement.getChild("errorMessage");
                if (oErrorMessage != null && !"정상".equals(oErrorMessage.getText())) {
                    throw new Exception(oErrorMessage.getText());
                }
                Element oTotalCount = oCommonElement.getChild("totalCount");
                if (oTotalCount != null) {
                    nTotalCount = Integer.parseInt(oTotalCount.getText());
                }
            }

            // 주소 리스트 확인
            @SuppressWarnings("unchecked")
            List<Element> oJusoList = (List<Element>) oRootElement.getChildren("juso");
            if (oJusoList == null) {
                throw new Exception("주소 데이터가 없습니다.");
            }

            // 결과 컬럼 정보
            // errorCode     : 0
            // errorMessage  : 정상
            // roadAddr      : 서울특별시 구로구 디지털로34길 55 (구로동)
            // roadAddrPart1 : 서울특별시 구로구 디지털로34길 55
            // roadAddrPart2 :  (구로동)
            // jibunAddr     : 서울특별시 구로구 구로동 811 코오롱싸이언스밸리2차
            // engAddr       : 55, Digital-ro 34-gil, Guro-gu, Seoul
            // zipNo         : 08378
            // admCd         : 1153010200
            // rnMgtSn       : 115304148341
            // bdMgtSn       : 1153010200108110000026784

            Element oRoadAddr  = null;
            Element oJibunAddr = null;
            Element oZipNo     = null;
            Map<String, Object> mData = null;
            for (Element oJuso : oJusoList) {
                oRoadAddr  = (Element) oJuso.getChild("roadAddr");
                oJibunAddr = (Element) oJuso.getChild("jibunAddr");
                oZipNo     = (Element) oJuso.getChild("zipNo");

                mData = new HashMap<String, Object>();
                mData.put("road_addr",  oRoadAddr != null ? oRoadAddr.getText() : "");
                mData.put("jibun_addr", oJibunAddr != null ? oJibunAddr.getText() : "");
                mData.put("zip_no",     oZipNo != null ? oZipNo.getText() : "");
                mList.add(mData);
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw e;
        }

        oData.setTotal(nTotalCount);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 행정구역 리스트를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/drop-down-list")
    public ModelAndView searchAreaList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        Map<String, Object> mSearchParam = ParamUtils.convertAreacdDropDownListParam(pmParam);

        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        String sApiUrl = "http://www.juso.go.kr/getAreaCode.do";
        String sFrom = StringUtils.defaultString((String)pmParam.get("from"));
        String sTo = StringUtils.defaultString((String)pmParam.get("to"));
        String sValFrom = StringUtils.defaultString((String)mSearchParam.get("parareacd"));
        String sSearchType = StringUtils.defaultString((String)pmParam.get("searchType"));

        try {
            // 프록시 서버 정보를 가져온다.
            // String PROXY_SERVER_IP = basicValueService.getBasicValueAsString("PROXY_SERVER_IP");
            // String PROXY_SERVER_PORT = basicValueService.getBasicValueAsString("PROXY_SERVER_PORT");

            Map<String, Object> mUrlConf = new HashMap<String, Object>();
            Map<String, Object> mResult = new HashMap<String, Object>();

            // URL 컨넥션 셋팅
            mUrlConf.put("URL", sApiUrl);
            // mUrlConf.put("PROXY_SERVER_IP", PROXY_SERVER_IP);
            // mUrlConf.put("PROXY_SERVER_PORT", PROXY_SERVER_PORT);
            mUrlConf.put("method", "GET");

            mResult.put("from", sFrom);
            mResult.put("to", sTo);
            mResult.put("valFrom", sValFrom);
            mResult.put("searchType", sSearchType);

            mUrlConf.put("sendParam", mResult);

            String sResultXml = URLConnector.getData(mUrlConf);
            SAXBuilder oBuilder = new SAXBuilder();
            Document oDocument = oBuilder.build(new InputSource(new ByteArrayInputStream(sResultXml.getBytes("UTF-8"))));
            Element oRootElement = oDocument.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> oNameList = (List<Element>) oRootElement.getChildren("name");
            @SuppressWarnings("unchecked")
            List<Element> oValueList = (List<Element>) oRootElement.getChildren("value");

            Map<String, Object> mData = null;
            for (int i = 0; i < oNameList.size(); i++) {
                Element oNameItem = (Element) oNameList.get(i);
                Element oValueItem = (Element) oValueList.get(i);

                mData = new HashMap<String, Object>();
                mData.put("name", oNameItem.getText());
                mData.put("value", sValFrom + oValueItem.getText()); // 결과코드 : 상위코드 붙여서 출력
                mList.add(mData);
            }

            oData.setTotal(mList.size());
            oData.setList(mList);
            oResult.setData(oData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}