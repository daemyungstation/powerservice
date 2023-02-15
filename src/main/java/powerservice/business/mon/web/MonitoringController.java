/*
 * (@)# MonitoringController.java
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

package powerservice.business.mon.web;

import java.io.FileWriter;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mon.service.MonitoringService;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 모니터링 정보를 조회한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 한숙향
 * @version 1.0
 * @date 2014/04/03
 * @프로그램ID Monitoring
 */

@Controller
@RequestMapping(value = "/monitoring")
public class MonitoringController {

    @Resource
    private MonitoringService monitoringService;

    /**
     * 메모리 사용현황을 검색한다.
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/memory")
    @ResponseBody
    public ModelAndView getMemory(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> oData = new HashMap<String, Object>();

        FileWriter oFilewriter = new FileWriter("out");
        String sEncname = oFilewriter.getEncoding();
        oFilewriter.close();

        Charset charset1 = Charset.forName(sEncname);
        Charset charset2 = Charset.forName("windows-1252");

        if (charset1.equals(charset2)) {
        } else {
        }
        String szTmp = "가상메모리테스트";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일    a hh시 mm분 ss초", Locale.KOREA);

        Runtime rt = Runtime.getRuntime();
        long free = rt.freeMemory();
        long total = rt.totalMemory();
        long usedRatio = (total - free) * 10000 / total;
        long freeRatio = free * 10000 / total;

        oData.put("time", formatter.format(new Date()));							// 측정시간
        oData.put("hostName", InetAddress.getLocalHost().getHostName());			// 호스트명
        oData.put("hostAddress", InetAddress.getLocalHost().getHostAddress());	// IP
        oData.put("totalMemory", total / (1024 * 1024) + "." + ((total) % (1024 * 1024) * 100 / (1024 * 1024)));					// 전체 메모리(MB)
        oData.put("usedMemory", (total - free) / (1024 * 1024) + "." + ((total - free) % (1024 * 1024) * 100 / (1024 * 1024)));	// 사용중 메모리(MB)
        oData.put("freeMemory", free / (1024 * 1024) + "." + (free % (1024 * 1024) * 100 / (1024 * 1024)));						// 남은 메모리(MB)
        oData.put("usedRatio", usedRatio / 100);	// 사용중 메모리 비율(%)
        oData.put("freeRatio", freeRatio / 100);	// 남은 메모리 비율(%)

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 모니터링 > 상담미처리 모니터링 > 지연시간별 미처리 상담건수 현황
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/past-time")
    @ResponseBody
    public ModelAndView getPastTimeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);
        oResult.setData(monitoringService.getPastTimeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 상담미처리 모니터링 > 팀
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/past-time-team")
    @ResponseBody
    public ModelAndView getPastTimeTeamChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getPastTimeTeamChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 모니터링 > 상담미처리 모니터링 > 상담사
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exceptperiodon
     */
    @RequestMapping(value = "/past-time-counselor")
    @ResponseBody
    public ModelAndView getPastTimeCounselorChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getPastTimeCounselorChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 상담미처리 모니터링 > 대분류
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/past-time-bigtype")
    @ResponseBody
    public ModelAndView getPastTimeBigtypeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getPastTimeBigtypeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 상담미처리 모니터링 > 중분류
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/past-time-midtype")
    @ResponseBody
    public ModelAndView getPastTimeMidtypeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getPastTimeMidtypeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > VOC 모니터링 > 지연시간별 미처리 VOC건수현황
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/voc-past-time")
    @ResponseBody
    public ModelAndView getVodPastTimeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);
        oResult.setData(monitoringService.getVocPastTimeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > VOC 모니터링 > VOC 유형
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/voc-past-time-type")
    @ResponseBody
    public ModelAndView getVocPastTimeTypeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getVocPastTimeTypeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > VOC 모니터링 > 담당자
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/voc-past-time-counselor")
    @ResponseBody
    public ModelAndView getVocPastTimeCounselorChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getVocPastTimeCounselorChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > VOC 모니터링 > 중요도
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/voc-past-time-value")
    @ResponseBody
    public ModelAndView getVocPastTimeValueChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getVocPastTimeValueChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 모니터링 > 콜백 모니터링 > 지연시간별 미처리 콜백건수현황
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/callback-past-time")
    @ResponseBody
    public ModelAndView getCallbackPastTimeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);
        oResult.setData(monitoringService.getCallbackPastTimeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 콜백모니터링 > 상담그룹
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/callback-past-time-team")
    @ResponseBody
    public ModelAndView getCallbackPastTimeTeamChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");			//분
        String state = (String) pmParam.get("state");				//콜백결과코드
        String sStartd = "";
        String sEndd = "";

        if (("30").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "30";
        } else if (("60").equals(sPeriod)) {
            sStartd = "30";
            sEndd = "60";
        } else if (("120").equals(sPeriod)) {
            sStartd = "60";
            sEndd = "120";
        } else if (("180").equals(sPeriod)) {
            sStartd = "120";
            sEndd = "180";
        } else if (("240").equals(sPeriod)) {
            sStartd = "180";
            sEndd = "240";
        } else if (("300").equals(sPeriod)) {
            sStartd = "240";
            sEndd = "300";
        } else if (("360").equals(sPeriod)) {
            sStartd = "300";
            sEndd = "360";
        } else if (("420").equals(sPeriod)) {
            sStartd = "360";
            sEndd = "420";
        }

        if (("a_cnt").equals(state) ) {
            state = "10";
        } else {
            state = "20";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);
        pmParam.put("state", state);

        oResult.setData(monitoringService.getCallbackPastTimeTeamChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 콜백모니터링 > 상담사
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/callback-past-time-counselor")
    @ResponseBody
    public ModelAndView getCallbackPastTimeCounselorChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");			//분
        String state = (String) pmParam.get("state");				//콜백결과코드
        String team_cd = (String) pmParam.get("team_cd");				//콜백결과코드
        String sStartd = "";
        String sEndd = "";

        if (("30").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "30";
        } else if (("60").equals(sPeriod)) {
            sStartd = "30";
            sEndd = "60";
        } else if (("120").equals(sPeriod)) {
            sStartd = "60";
            sEndd = "120";
        } else if (("180").equals(sPeriod)) {
            sStartd = "120";
            sEndd = "180";
        } else if (("240").equals(sPeriod)) {
            sStartd = "180";
            sEndd = "240";
        } else if (("300").equals(sPeriod)) {
            sStartd = "240";
            sEndd = "300";
        } else if (("360").equals(sPeriod)) {
            sStartd = "300";
            sEndd = "360";
        } else if (("420").equals(sPeriod)) {
            sStartd = "360";
            sEndd = "420";
        }

        if (("a_cnt").equals(state) ) {
            state = "10";
        } else {
            state = "20";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);
        pmParam.put("state", state);
        pmParam.put("team_cd", team_cd);

        oResult.setData(monitoringService.getCallbackPastTimeCounselorChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 콜백모니터링 > IVR 서비스
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/callback-past-time-ivr")
    @ResponseBody
    public ModelAndView getCallbackPastTimeIvrChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");			//분
        String state = (String) pmParam.get("state");				//콜백결과코드
        String sStartd = "";
        String sEndd = "";

        if (("30").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "30";
        } else if (("60").equals(sPeriod)) {
            sStartd = "30";
            sEndd = "60";
        } else if (("120").equals(sPeriod)) {
            sStartd = "60";
            sEndd = "120";
        } else if (("180").equals(sPeriod)) {
            sStartd = "120";
            sEndd = "180";
        } else if (("240").equals(sPeriod)) {
            sStartd = "180";
            sEndd = "240";
        } else if (("300").equals(sPeriod)) {
            sStartd = "240";
            sEndd = "300";
        } else if (("360").equals(sPeriod)) {
            sStartd = "300";
            sEndd = "360";
        } else if (("420").equals(sPeriod)) {
            sStartd = "360";
            sEndd = "420";
        }

        if (("a_cnt").equals(state) ) {
            state = "10";
        } else {
            state = "20";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);
        pmParam.put("state", state);

        oResult.setData(monitoringService.getCallbackPastTimeIvrChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 재통화 모니터링 > 지연시간별 미처리 재통화건수현황
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resvcall-past-time")
    @ResponseBody
    public ModelAndView getResvcallPastTimeChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);
        oResult.setData(monitoringService.getResvcallPastTimeChartList(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 재통화모니터링 > 팀
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recall-past-time-team")
    @ResponseBody
    public ModelAndView getRecallPastTimeTeamChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getRecallPastTimeTeamChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 모니터링 > 재통화모니터링 > 상담유형 대분류
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recall-past-time-major")
    @ResponseBody
    public ModelAndView getRecallPastTimeMajorChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getRecallPastTimeMajorChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 모니터링 > 재통화모니터링 > 상담사
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recall-past-time-counselor")
    @ResponseBody
    public ModelAndView getRecallPastTimeCounselorChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getRecallPastTimeCounselorChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 모니터링 > 재통화모니터링 > 상담사
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/recall-past-time-middle")
    @ResponseBody
    public ModelAndView getRecallPastTimeMiddleChartList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        String sPeriod = (String) pmParam.get("sPeriod");
        String sStartd = "";
        String sEndd = "";

        if (("1").equals(sPeriod)) {
            sStartd = "0";
            sEndd = "1";
        } else if (("2").equals(sPeriod)) {
            sStartd = "1";
            sEndd = "2";
        } else if (("3").equals(sPeriod)) {
            sStartd = "2";
            sEndd = "3";
        } else if (("5").equals(sPeriod)) {
            sStartd = "3";
            sEndd = "5";
        } else if (("24").equals(sPeriod)) {
            sStartd = "5";
            sEndd = "24";
        } else if (("48").equals(sPeriod)) {
            sStartd = "24";
            sEndd = "48";
        } else if (("72").equals(sPeriod)) {
            sStartd = "48";
            sEndd = "72";
        } else if (("100").equals(sPeriod)) {
            sStartd = "72";
            sEndd = "100";
        }

        pmParam.put("sStartd", sStartd);
        pmParam.put("sEndd", sEndd);

        oResult.setData(monitoringService.getRecallPastTimeMiddleChartList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

}