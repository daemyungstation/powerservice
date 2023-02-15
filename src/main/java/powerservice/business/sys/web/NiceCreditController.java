package powerservice.business.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.sys.service.NiceCreditService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.NiceCreditUtil;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 나이스 평가정보 신용등급 조회
 *
 * @author 정출연
 * @version 1.0
 * @date 2015/09/01
 * @프로그램ID NiceCredit
 */
@Controller
@RequestMapping(value = "/sys/nice-credit")
public class NiceCreditController {

    private final Logger log = LoggerFactory.getLogger(NiceCreditController.class);

    @Resource
    private NiceCreditService niceCreditService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private CommonService commonService;

    /**
     * NICE 신용 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getNiceCredit")
    public ModelAndView getNiceCredit(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mRslt = null;

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                mRslt = niceCreditService.getNiceCredit(hmParam);
                listMap.setRowMaps(mRslt);
            }

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
     * NICE 신용 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getNiceCredit600")
    public ModelAndView getNiceCredit600(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mRslt = null;

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                mRslt = niceCreditService.getNiceCredit600(hmParam);
                listMap.setRowMaps(mRslt);
            }

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
     * NICE Safe-Key 발급권유 SMS 발송결과 - NICE 평가정보 서버에서 호출한다.
     * Safe-Key 발급 결과와 상관없이 순수하게 SMS 발송결과만 전송된다.
     *
     * @param request HttpServletRequest
     * @param sess HttpSession
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/checkplus_send_result")
    public ModelAndView updateSafeKeySmsSendResult(HttpServletRequest request, HttpSession sess) throws Exception {
        ModelAndView mav = new ModelAndView();

        niceCreditService.updateSafeKeySmsSendResult(request, sess);

        mav.setViewName("checkplus/checkplus_send_result");
        return mav;
    }

    /**
     * 고객이 NICE 에서 세이프키 발급 성공  - NICE 평가정보 서버에서 호출한다.
     *
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkplus_mobile_success.do")
    public ModelAndView updateNiceSafekeyMobileSuccess(HttpServletRequest request) throws Exception {
        boolean isSuccess = true;
        ModelAndView mav = new ModelAndView();

        niceCreditService.updateNiceSafekeyMobileIssueResult(request, isSuccess);

        mav.setViewName("checkplus/nice_safekey_sms_issue_result");
        return mav;
    }

    /**
     * 고객이 NICE 에서 세이프키 발급 실패 - NICE 평가정보 서버에서 호출한다.
     *
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkplus_mobile_fail.do")
    public ModelAndView updateNiceSafekeyMobileFail(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        boolean isSuccess = false;
        niceCreditService.updateNiceSafekeyMobileIssueResult(request, isSuccess);

        mav.setViewName("checkplus/nice_safekey_sms_issue_result");
        return mav;
    }

    /**
     * NICE Safe-Key 발급안내 SMS 발송 - 화면에서 SMS발송 커맨드 호출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/sendSafeKeyIssueSms")
    public ModelAndView sendSafeKeyIssueSms(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();

        Map<String, Object> mRslt = niceCreditService.sendSafeKeyIssueSms(request);

        mav.addObject("sPlainData"			, mRslt.get("sPlainData")		);
        mav.addObject("sReqDatetime"		, mRslt.get("sReqDatetime")		);
        mav.addObject("sEncData"			, mRslt.get("sEncData")			);
        mav.addObject("sRequestNumber"		, mRslt.get("req_seq")			);
        mav.addObject("req_datetime"		, mRslt.get("req_datetime")		);
        mav.addObject("lgnId"				, mRslt.get("lgnId")			);
        mav.addObject("mem_no"				, mRslt.get("mem_no")			);

        mav.setViewName("checkplus/checkplus_send_main");
        return mav;
    }

    /**
     * 신용인증 NICE selfkey 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getNiceSafeKey")
    public ModelAndView getNiceSafeKey(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mRslt = null;

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                mRslt = niceCreditService.getNiceSafeKey(hmParam);
                listMap.setRowMaps(mRslt);
            }

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
     * 고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMoNiceSafeKeySmsList")
    public ModelAndView getMoNiceSafeKeySmsList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);

                int nTotal = niceCreditService.getMoNiceSafeKeySmsCount(hmParam);
                mapOutVar.put("fv_tc", nTotal);

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                log.debug("listInDs.size() : " + listInDs.size());

                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);

                    String sStartNum = String.valueOf(pMap.get("startNum"));
                    String sEndNum = String.valueOf(pMap.get("endNum"));

                    log.debug("sStartNum : " + sStartNum);
                    log.debug("sEndNum : " + sEndNum);

                    hmParam.put("startLine", sStartNum);
                    hmParam.put("endLine", sEndNum);
                }

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = niceCreditService.getMoNiceSafeKeySmsList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.12.04 접속 로그////////////////////////////////////////////////////////////////////////////////
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

    /**
     * NICE Safe-Key 발급상태 변경 - 나이스에서 결과를 받지 못한 건들에 대해 Safe-Key를 조회하고 조회된 Safe-Key가 있으면 응답코드를 변경한다.
     *
     * @param xpDto XPlatformMapDTO
     * @param model Model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateSafeKeyIssueStat")
    public ModelAndView updateSafeKeyIssueStat(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                niceCreditService.updateSafeKeyIssueStat(hmParam);
            }

            //2017.12.04 접속 로그////////////////////////////////////////////////////////////////////////////////
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

    /**
     * NICE 신용 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getSafekeyIssueStatusCodeList")
    public ModelAndView getSafekeyIssueStatusCodeList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> mList = NiceCreditUtil.getSafekeyIssueStatusCodeList();
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
     * 고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getFoo")
    public ModelAndView getFoo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

        try {
            mapOutVar.put("foo", "var");

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
     * 고객이 NICE 에서 세이프키 발급 실패 - NICE 평가정보 서버에서 호출한다.
     *
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertJcyTest1")
    public ModelAndView insertJcyTest1(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            niceCreditService.insertJcyTest1();

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        return modelAndView;
    }

    //
    /**
     * 고객의 신용등급을 조회 후 회원테이블의 세이프키를 갱신
     *
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateMemberNiceSafekey")
    public ModelAndView updateMemberNiceSafekey(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                CommonUtils.printLog(hmParam);

                niceCreditService.updateMemberNiceSafekey(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        return modelAndView;
    }

    /**
     * NICE Safe-Key 발급 SMS 최종 발송일자 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getSmsSendDt")
    public ModelAndView getSmsSendDt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);
                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = niceCreditService.getSmsSendDt(hmParam);
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
     * NICE Safe-Key SMS 내역 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getNiceSafeSMSInfo")
    public ModelAndView getNiceSafeSMSInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        Map<String, Object> mRslt = null;

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);
                ParamUtils.addCenterParam(hmParam);
                System.out.println("============================================== 파라미터 : " + hmParam);

                mRslt = niceCreditService.getNiceSafeSMSInfo(hmParam);
                listMap.setRowMaps(mRslt);
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