/*
 * (@)# WebConsController.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
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

package powerservice.business.web.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.chn.service.ChatSndgHstrService;
import powerservice.business.dlw.service.DlwCondService;
import powerservice.business.onln.service.ExtcTrgtService;
import powerservice.business.web.service.WebShoppingService;
import powerservice.core.util.ParamUtils;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/20
 * @프로그램ID WebCons
 */

@Controller
@RequestMapping(value="/web/manual")
public class WebShoppingBatchManual {

    @Resource
    private DlwCondService dlwCondService;
    @Resource
    private ExtcTrgtService extcTrgtService;
    @Resource
    private ChatSndgHstrService chatSndgHstrService;


    @Autowired
    private ServletContext ctx;

    /* ================================================================
     * 날짜 : 20171229
     * 이름 : 송준빈
     * 추가내용 : 새벽 3시수행 - 실적현황
     * description : DB링크로 인한 Table 변화는 발견되지 않았음.
     * query목록 :
     * 1. DlwCondMap.insertUserProdInfo -- TB_USER_PROD_INFO(인우) 테이블 인서트
     * ================================================================
     * */
    @RequestMapping(value = "/insertUserProdInfo")
    public ModelAndView insertUserProdInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            System.out.println("WebShoppingBatchManual.insertUserProdInfo() ::: 컨트롤러가 수행되었습니다.");
            dlwCondService.insertUserProdInfo();
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

    /* ================================================================
     * 날짜 : 20171229
     * 이름 : 송준빈
     * 추가내용 : 새벽 5시수행 - 캠페인 실적(미납) ORACLE MST 테이블에 저장
     * description : DB링크로 인한 Table 변화는 발견되지 않았음.
     * query목록 :
     * 1. DlwExtcTrgtMap.getUnpyAcrs
     * 2. ExtcMstTrgtMap.updateAcrs -- TB_UNPY_MNGE(미납 월간 현황 - 인우) 테이블 업데이트
     * ================================================================
     * */
    @RequestMapping(value = "/updateUnpyAcrs")
    public ModelAndView updateUnpyAcrs(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            System.out.println("WebShoppingBatchManual.updateUnpyAcrs() ::: 컨트롤러가 수행되었습니다.");
            extcTrgtService.updateUnpyAcrs();
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

    /* ================================================================
     * 날짜 : 20171229
     * 이름 : 송준빈
     * 추가내용 : 새벽 5시30분수행 - 캠페인 실적(해약) ORACLE MST 테이블에 저장
     * description : DB링크로 인한 Table 변화는 발견되지 않았음.
     * query목록 :
     * 1. DlwExtcTrgtMap.getCnctAcrs
     * 2. ExtcMstTrgtMap.updateAcrs -- TB_UNPY_MNGE(미납 월간 현황 - 인우) 테이블 업데이트
     * 3. UnpyMngeHstrMap.insertUnpyMngeHstr -- TB_UNPY_MNGE_HSTR(인우) 테이블 인서트
     * ================================================================
     * */
    @RequestMapping(value = "/updateCnctAcrs")
    public ModelAndView updateCnctAcrs(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            System.out.println("WebShoppingBatchManual.updateCnctAcrs() ::: 컨트롤러가 수행되었습니다.");
            extcTrgtService.updateCnctAcrs();
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

    /* ================================================================
     * 날짜 : 20171229
     * 이름 : 송준빈
     * 추가내용 : 오전 6시 ~ 22시 사이 10분마다 수행 - TM 서브타겟고객 저장 트리거
     * description : DB링크로 인한 Table 변화는 발견되지 않았음.
     * query목록 :
     * 1. ExtcTrgtMap.getExtcTrgtList4100
     * 2. SubTrgtListMap.insertTrgtCustByDB -- TB_TRGT_CUST_DTPT(대상 고객 상세 - 인우) 테이블 인서트
     * 3. SubTrgtListMap.updateSubTrgtList -- TB_SUB_TRGT_LIST_DTL(서브 대상 목록 내역 - 인우) 테이블 업데이트
     * 4. TrgtListMap.updateTrgtList -- TB_TRGT_LIST_DTL(대상 목록 내역 - 인우) 테이블 업데이트
     * 5. ExtcTrgtMap.updateOnlineStat -- OM_OSC_CNSL_MST(????) 테이블 업데이트
     * ================================================================
     * */
    @RequestMapping(value = "/insertB2QTmJoinCust")
    public ModelAndView insertB2QTmJoinCust(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            System.out.println("WebShoppingBatchManual.insertB2QTmJoinCust() ::: 컨트롤러가 수행되었습니다.");
            extcTrgtService.insertB2QTmJoinCust();
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

    /* ================================================================
     * 날짜 : 20171229
     * 이름 : 송준빈
     * 추가내용 : - 오전 7시 ~ 23시 사이 5분 마다 30초에 실행 - 문자시스템 전송결과 데이터 동기화
     * description : DB링크로 인한 Table 변화는 발견되지 않았음.
     * query목록 :
     * 1. ChatSndgHstrMap.getEmLogList
     * 2. ChatSndgHstrMap.updateChatSndgHstrResl -- TB_CHAT_SNDG_HSTR(문자 발송 이력 - 인우) 테이블 업데이트
     * 3. ChatSndgHstrMap.newgetEmLogList
     * 4. ChatSndgHstrMap.updatenewChatSndgHstrResl -- TB_CHAT_SNDG_HSTR_NEW(?? - 인우) 테이블 업데이트
     * ================================================================
     * */
    @RequestMapping(value = "/updateChatSndgHstrResl")
    public ModelAndView updateChatSndgHstrResl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            System.out.println("WebShoppingBatchManual.updateChatSndgHstrResl() ::: 컨트롤러가 수행되었습니다.");
            chatSndgHstrService.updateChatSndgHstrResl();
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