/*
 * (@)# InfoController.java
 *
 * @author 김은지
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
package powerservice.business.main.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.BswrDmndDtlService;
import powerservice.business.cns.service.CalbDtlService;
import powerservice.business.cns.service.ConsService;
import powerservice.business.cns.service.ConsTrctHstrService;
import powerservice.business.cns.service.VocDtlService;
import powerservice.business.kms.service.AncmMtrDtlService;
import powerservice.business.sys.service.NmsgDtlService;
import powerservice.business.usr.service.UserService;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Info
 */
@Controller
@RequestMapping(value = "/main/info")
public class InfoController {
    private static int REFRESH_TIME_NEW_MESSAGE_COUNT = 3000;
    private static int REFRESH_TIME_TODAY_COUNSEL_COUNT = 3000;
    private static int REFRESH_TIME_THIS_MONTH_COUNSEL_COUNT = 3000;
    private static int REFRESH_TIME_TODO_CALL_BACK_COUNT = 3000;
    private static int REFRESH_TIME_VOC_HAPPY_CALL_COUNT = 60000;
    private static int REFRESH_TIME_CONS_TRCT_COUNT = 3000;
    private static int REFRESH_TIME_CONS_TRCT_BOX_CHPR_COUNT = 3000;
    
    @Resource
    private UserService userService;

    @Resource
    private NmsgDtlService nmsgDtlService;

    @Resource
    private ConsService consService;

    @Resource
    private CalbDtlService calbDtlService;

    @Resource
    private AncmMtrDtlService ancmMtrDtlService;

    @Resource
    private VocDtlService vocDtlService;

    @Resource
    private ConsTrctHstrService consTrctHstrService;

    @Resource
    private BswrDmndDtlService bswrDmndDtlService;

    /**
     * MultiKeyMap 은 thread-safe 하지 않으나 여기서는 한곳에서만 write 하기 때문에 thread-safe 할 필요없다.
     */
    private static MultiKeyMap<String, Map<?, ?>> newNmsgCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> todayConsCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> thisMonthConsCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> todoCalbDtlCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> vocHappyCallCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> consTrctCountMap = new MultiKeyMap<String, Map<?, ?>>();
    private static MultiKeyMap<String, Map<?, ?>> consTrctBoxChprCountMap = new MultiKeyMap<String, Map<?, ?>>();

    private static AtomicLong updateTimeOfNewNmsgCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_NEW_MESSAGE_COUNT);
    private static AtomicLong updateTimeOfTodayConsCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_TODAY_COUNSEL_COUNT);
    private static AtomicLong updateTimeOfThisMonthConsCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_THIS_MONTH_COUNSEL_COUNT);
    private static AtomicLong updateTimeOfTodoCalbDtlCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_TODO_CALL_BACK_COUNT);
    private static AtomicLong updateTimeOfVocHappyCallCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_VOC_HAPPY_CALL_COUNT);
    private static AtomicLong updateTimeOfConsTrctCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_CONS_TRCT_COUNT);
    private static AtomicLong updateTimeOfconsTrctBoxChprCountMap = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_CONS_TRCT_BOX_CHPR_COUNT);
    
//    @RequestMapping(value = "/dataTest01")
//    public ModelAndView dataTest01(XPlatformMapDTO xpDto, Model model) throws Exception {
//    	
//    	
//    	
//    }
    
    @RequestMapping(value = "/getInCallCount")
    public ModelAndView getInCallCount(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	DataSetMap dsMap = new DataSetMap();
    	
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
    	
    	try {
    		Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
            
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            List<Map<String, Object>> inData = new ArrayList<Map<String,Object>>();
            Map<String, Object> inDataMap = new HashMap<String, Object>();
            String userId = oUserSession.getUserid();
            mSearchParam.put("userId", userId);

            List<Map<String, Object>> mList = userService.getInCallCount(mSearchParam); 
            
            dsMap.setRowMaps(mList);
          
            outDataset.put("ds_output", dsMap);
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
    	} catch (Exception e) {
			e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
		}
    	
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
    	
    	return modelAndView;
    }
    
    @RequestMapping(value = "/getOutCallCount")
    public ModelAndView getOutCallCount(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	DataSetMap dsMap = new DataSetMap();
    	
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
    	
        try {
        	Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
            
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            List<Map<String, Object>> outData = new ArrayList<Map<String,Object>>();
            Map<String, Object> outDataMap = new HashMap<String, Object>();
            String userId = oUserSession.getUserid();
            mSearchParam.put("userId", userId);

            //String outCallCount = userService.getOutCallCount(mSearchParam);
            List<Map<String, Object>> mList = userService.getOutCallCount(mSearchParam);
            
//            outDataMap.put("outCallCount", outCallCount);
//            outData.add(0, outDataMap);
//            dsMap.setRowMaps(outData);
            dsMap.setRowMaps(mList);
            
            outDataset.put("ds_output", dsMap);
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
    	} catch (Exception e) {
			e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
		}
    	
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
    	
    	return modelAndView;
    }
    
    @RequestMapping(value = "/getIvrCbCount")
    public ModelAndView getIvrCbCount(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	DataSetMap dsMap = new DataSetMap();
    	Map<String, Object> mTotalCount = new HashMap<String, Object>();
    	
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
    	
        try {
        	Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
            
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            List<Map<String, Object>> outData = new ArrayList<Map<String,Object>>();
            Map<String, Object> outDataMap = new HashMap<String, Object>();
            String userId = oUserSession.getUserid();
            mSearchParam.put("userId", userId);

            //String outCallCount = userService.getOutCallCount(mSearchParam);
            //List<Map<String, Object>> mList = userService.getOutCallCount(mSearchParam);
            
            refreshTodoCalbDtlCountMap();
            try {
                if (todoCalbDtlCountMap.get(oUserSession.getCentercd(), oUserSession.getUserid()) != null) {
                    mTotalCount.put("calb_cnt", todoCalbDtlCountMap.get(oUserSession.getCentercd(), oUserSession.getUserid()).get("cnt"));
                } else {
                    mTotalCount.put("calb_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("calb_cnt", "0");
            }
            
//            outDataMap.put("outCallCount", outCallCount);
//            outData.add(0, outDataMap);
//            dsMap.setRowMaps(outData);
            dsMap.setRowMaps(mTotalCount);
            
            outDataset.put("ds_output", dsMap);
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
    	} catch (Exception e) {
			e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
		}
    	
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
    	
    	return modelAndView;
    }
    
    @RequestMapping(value = "/getUsernm")
    public ModelAndView getUsernm(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	DataSetMap dsMap = new DataSetMap();
    	
    	// 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try {
        	Map <String, Object> inVar 			= xpDto.getInVariableMap();
            Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
            Map <String, Object> outVar 		= xpDto.getOutVariableMap();
            Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
        	
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("user_id", oUserSession.getUserid());

            dsMap.setRowMaps(userService.getUser(mSearchParam));
            
            outDataset.put("ds_output", dsMap);
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
		} catch (Exception e) {
			e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
		}
    	
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
    	
    	return modelAndView;
    }

    @RequestMapping(value = "/total-count")
    public ModelAndView getTotalCount(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mTotalCount = new HashMap<String, Object>();
        DataSetMap infoMap = new DataSetMap();
        DataSetMap infoMap2 = new DataSetMap();
        DataSetMap infoMap3 = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();

//            refreshTodayConsCountMap();
//            try {
//                if (todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
//                    mTotalCount.put("open_cnt", todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("open_cnt"));
//                    mTotalCount.put("proc_cnt", todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("proc_cnt"));
//                    mTotalCount.put("close_cnt", todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("close_cnt"));
//                    mTotalCount.put("trans_cnt", todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("trans_cnt"));
//                } else {
//                    mTotalCount.put("open_cnt", "0");
//                    mTotalCount.put("proc_cnt", "0");
//                    mTotalCount.put("close_cnt", "0");
//                    mTotalCount.put("trans_cnt", "0");
//                }
//            } catch (Exception e) {
//                mTotalCount.put("open_cnt", "0");
//                mTotalCount.put("proc_cnt", "0");
//                mTotalCount.put("close_cnt", "0");
//                mTotalCount.put("trans_cnt", "0");
//            }

            refreshThisMonthConsCountMap();
            try {
                if (thisMonthConsCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mTotalCount.put("recrnc_alarm_cnt", thisMonthConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("recrnc_alarm_cnt"));
                    mTotalCount.put("todo_cnt", thisMonthConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("todo_cnt"));
                    mTotalCount.put("recrnc_cnt", thisMonthConsCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("recrnc_cnt"));
                } else {
                    mTotalCount.put("recrnc_alarm_cnt", "0");
                    mTotalCount.put("todo_cnt", "0");
                    mTotalCount.put("recrnc_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("recrnc_alarm_cnt", "0");
                mTotalCount.put("todo_cnt", "0");
                mTotalCount.put("recrnc_cnt", "0");
            }

            refreshTodoCalbDtlCountMap();
            try {
                if (todoCalbDtlCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mTotalCount.put("calb_cnt", todoCalbDtlCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
                } else {
                    mTotalCount.put("calb_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("calb_cnt", "0");
            }

//            refreshVocHappyCallCountMap();
//            try {
//                if (vocHappyCallCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
//                    mTotalCount.put("hpcl_cnt", vocHappyCallCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
//                } else {
//                    mTotalCount.put("hpcl_cnt", "0");
//                }
//            } catch (Exception e) {
//                mTotalCount.put("hpcl_cnt", "0");
//            }

//            refreshConsTrctCountMap();
//            try {
//                if (consTrctCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
//                    mTotalCount.put("cons_trct_cnt", consTrctCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
//                } else {
//                    mTotalCount.put("cons_trct_cnt", "0");
//                }
//            } catch (Exception e) {
//                mTotalCount.put("cons_trct_cnt", "0");
//            }

            refreshNewNmsgCountMap();
            try {
                if (newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mTotalCount.put("new_nmsg_cnt", newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
                } else {
                    mTotalCount.put("new_nmsg_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("new_nmsg_cnt", "0");
            }
            
            String userId = oUser.getUserid();
            mSearchParam.put("userId", userId);
            List<Map<String, Object>> mList2 = userService.getInCallCount(mSearchParam);
            List<Map<String, Object>> mList3 = userService.getOutCallCount(mSearchParam);

            infoMap.setRowMaps(mTotalCount);
            mapOutDs.put("ds_output", infoMap);
            
            infoMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", infoMap2);
            
            infoMap3.setRowMaps(mList3);
            mapOutDs.put("ds_output3", infoMap3);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/new-nmsg-count")
    public ModelAndView getNewNmsgCount(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mCount = new HashMap<String, Object>();

            refreshNewNmsgCountMap();
            try {
                if (newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mCount.put("new_nmsg_cnt", newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
                } else {
                    mCount.put("new_nmsg_cnt", "0");
                }
            } catch (Exception e) {
                mCount.put("new_nmsg_cnt", "0");
            }

            listMap.setRowMaps(mCount);

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

    private void refreshNewNmsgCountMap() throws Exception {
        long nTemp = updateTimeOfNewNmsgCountMap.longValue();

        if (updateTimeOfNewNmsgCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_NEW_MESSAGE_COUNT) {

            if (updateTimeOfNewNmsgCountMap.compareAndSet(nTemp, Calendar.getInstance().getTimeInMillis())) {
                newNmsgCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) nmsgDtlService.getNewNmsgDtlCount();
                for (Map<String, Object> mItem : mList) {
                    newNmsgCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }

        }
    }

    @RequestMapping(value = "/today-cons-count")
    public ModelAndView getTodayConsCount(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        refreshTodayConsCountMap();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        oResult.setData(todayConsCountMap.get(oUser.getCentercd(), oUser.getUserid()));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    private void refreshTodayConsCountMap() throws Exception {
        long nTemp = updateTimeOfTodayConsCountMap.longValue();

        if (updateTimeOfTodayConsCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_TODAY_COUNSEL_COUNT) {

            if (updateTimeOfTodayConsCountMap.compareAndSet(nTemp, Calendar.getInstance().getTimeInMillis())) {
                todayConsCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) consService.getTodayConsCount();
                for (Map<String, Object> mItem : mList) {
                    todayConsCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }
        }
    }

    @RequestMapping(value = "/this-month-cons-count")
    public ModelAndView getThisMonthConsCount(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        refreshThisMonthConsCountMap();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        oResult.setData(thisMonthConsCountMap.get(oUser.getCentercd(), oUser.getUserid()));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    private void refreshThisMonthConsCountMap() throws Exception {
        long nTemp = updateTimeOfThisMonthConsCountMap.longValue();

        if (updateTimeOfThisMonthConsCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_THIS_MONTH_COUNSEL_COUNT) {

            if (updateTimeOfThisMonthConsCountMap.compareAndSet(nTemp, Calendar.getInstance().getTimeInMillis())) {
                thisMonthConsCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) consService.getThisMonthConsCount();
                for (Map<String, Object> mItem : mList) {
                    thisMonthConsCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }
        }
    }

    @RequestMapping(value = "/todo-call-back-count")
    public ModelAndView getTodoCalbDtlCount(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        refreshTodoCalbDtlCountMap();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        oResult.setData(todoCalbDtlCountMap.get(oUser.getCentercd(), oUser.getUserid()));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    private void refreshTodoCalbDtlCountMap() throws Exception {
        long nTemp = updateTimeOfTodoCalbDtlCountMap.longValue();

        if (updateTimeOfTodoCalbDtlCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_TODO_CALL_BACK_COUNT) {

            if (updateTimeOfTodoCalbDtlCountMap.compareAndSet(nTemp, Calendar.getInstance().getTimeInMillis())) {
                todoCalbDtlCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) calbDtlService.getTodoCalbDtlCount();
                for (Map<String, Object> mItem : mList) {
                    todoCalbDtlCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }

        }
    }

    @RequestMapping(value = "/voc-happy-call-count")
    public ModelAndView getVocHappyCallCount(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        refreshVocHappyCallCountMap();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        oResult.setData(vocHappyCallCountMap.get(oUser.getCentercd(), oUser.getUserid()));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    private void refreshVocHappyCallCountMap() throws Exception {
        long nTemp = updateTimeOfVocHappyCallCountMap.longValue();

        if (updateTimeOfVocHappyCallCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_VOC_HAPPY_CALL_COUNT) {

            if (updateTimeOfVocHappyCallCountMap.compareAndSet(nTemp, Calendar.getInstance().getTimeInMillis())) {
                vocHappyCallCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) vocDtlService.getTodoVocHpclDspsCount();
                for (Map<String, Object> mItem : mList) {
                    vocHappyCallCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }

        }
    }

    @RequestMapping(value = "/cons-trct-count")
    public ModelAndView getConsTrctCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        refreshConsTrctCountMap();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        oResult.setData(consTrctCountMap.get(oUser.getCentercd(), oUser.getUserid()));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    private void refreshConsTrctCountMap() throws Exception {
        long temp = updateTimeOfConsTrctCountMap.longValue();

        if (updateTimeOfConsTrctCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_CONS_TRCT_COUNT) {

            if (updateTimeOfConsTrctCountMap.compareAndSet(temp, Calendar.getInstance().getTimeInMillis())) {
                consTrctCountMap.clear();

                /**
                 *  변경일시 : 2015.11.13 일
                 *  변 경 자 : 정 훈 연구원
                 *  변경이유 : 상담이관과 업무요청 테이블 OR 로직변경에 의한 수정
                 *  요 청 자 : 우달식 이사님
                 */
                List<Map<String, Object>> mList = (List<Map<String, Object>>) bswrDmndDtlService.getTodoConsTrctHstrCount();

                for (Map<String, Object> mItem : mList) {
                    consTrctCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }

        }
    }

    @RequestMapping(value = "/trct-box-chpr/total-count")
    public ModelAndView getTrctBoxChprTotalCount(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mTotalCount = new HashMap<String, Object>();
        DataSetMap infoMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession oUser = (UserSession) SessionUtils.getLoginUser();

            refreshConsTrctBoxChprCountMap();
            try {
                if (consTrctBoxChprCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mTotalCount.put("cons_trct_box_chpr_cnt", consTrctBoxChprCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
                } else {
                    mTotalCount.put("cons_trct_box_chpr_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("cons_trct_box_chpr_cnt", "0");
            }

            refreshNewNmsgCountMap();
            try {
                if (newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()) != null) {
                    mTotalCount.put("new_nmsg_cnt", newNmsgCountMap.get(oUser.getCentercd(), oUser.getUserid()).get("cnt"));
                } else {
                    mTotalCount.put("new_nmsg_cnt", "0");
                }
            } catch (Exception e) {
                mTotalCount.put("new_nmsg_cnt", "0");
            }

            infoMap.setRowMaps(mTotalCount);
            mapOutDs.put("ds_output", infoMap);

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

    private void refreshConsTrctBoxChprCountMap() throws Exception {
        long temp = updateTimeOfconsTrctBoxChprCountMap.longValue();

        if (updateTimeOfconsTrctBoxChprCountMap.longValue() < Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_CONS_TRCT_BOX_CHPR_COUNT) {

            if (updateTimeOfconsTrctBoxChprCountMap.compareAndSet(temp, Calendar.getInstance().getTimeInMillis())) {
                consTrctBoxChprCountMap.clear();

                List<Map<String, Object>> mList = (List<Map<String, Object>>) bswrDmndDtlService.getConsTrctBoxChprCount();

                for (Map<String, Object> mItem : mList) {
                    consTrctBoxChprCountMap.put((String)mItem.get("cntr_cd"), (String)mItem.get("user_id"), mItem);
                }
            }

        }
    }

}
