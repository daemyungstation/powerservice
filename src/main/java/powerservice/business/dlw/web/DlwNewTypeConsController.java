package powerservice.business.dlw.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwNewTypeConsService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwNewTypeConsController {

    private final Logger log = LoggerFactory.getLogger(DlwNewTypeConsController.class);

    @Resource
    private DlwNewTypeConsService dlwNewTypeConsService;
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 탭
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/cons/getNewTypeMemConsHstrList")
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

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                if ("Y".equals(sDeptYn)) {
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "A");
                    String dataAthrQury = StringUtils.defaultString((String) dlwNewTypeConsService.getDataAthrQury(hmParam));
                    if (!" ".equals(dataAthrQury)) {
                        dataAthrQury = dataAthrQury.replace("AND", "AND (");
                        dataAthrQury += "OR A.DEPT_CD IS NULL)";
                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                String excel_fg = (String)mapInVar.get("excel_fg");
                if (!"Y".equals(excel_fg)) {
                    int nTotal = dlwNewTypeConsService.getConsCount(hmParam);

                    mapOutVar.put("tc_cons", nTotal);

                    List<Map<String, Object>> mList = dlwNewTypeConsService.getConsList(hmParam);


                    listMap.setRowMaps(mList);

                    mapOutDs.put("ds_output", listMap);
                }

                if (null == hmParam.get("dataAthrQury")) {
                    hmParam.put("dataAthrQury", "");
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
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
   
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 팝업 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/cons/getNewTypeConsPopList")
    public ModelAndView getNewTypeConsPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            int nTotal = dlwNewTypeConsService.getNewTypeConsPopCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            List<Map<String, Object>> mList = dlwNewTypeConsService.getNewTypeConsPopList(hmParam);
            listMap.setRowMaps(mList);
            
            mapOutDs.put("ds_output", listMap);

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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 캠페인 이력 탭
     * 대상 테이블 : PS_WILLVI.TB_TRGT_CUST_DTPT, PS_WILLVI.TB_DPMS_RESL_HSTR
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/cmpg/getNewTypeDpmsReslHstrList")
    public ModelAndView getNewTypeDpmsReslHstrList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
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
            int nTotal = dlwNewTypeConsService.getIbDpmsReslHstrCount(hmParam);

            List<Map<String, Object>> mDpmsReslHstrList = dlwNewTypeConsService.getIbDpmsReslHstrList(hmParam);

            listMap.setRowMaps(mDpmsReslHstrList);
            mapOutVar.put("total_count", nTotal);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 VOC 이력 탭
     * 대상 테이블 : PS.WILLVI.TB_VOC_DTL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/voc/getNewTypeMemVocHstrList")
    public ModelAndView getNewTypeMemVocHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("excel_fg", (String)mapInVar.get("excel_fg"));

            int nTotal = dlwNewTypeConsService.getVocDtlCount(hmParam);
            mapOutVar.put("tc_voc", nTotal);

            List<Map<String, Object>> mList = dlwNewTypeConsService.getVocDtlList(hmParam);
            listMap.setRowMaps(mList);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 SMS 이력 탭
     * 대상 테이블 : PS.WILLVI.TB_CHAT_SNDG_HSTR
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/chat/getNewTypeChatSndgHstrList")
    public ModelAndView getNewTypeChatSndgHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엑셀 여부 확인
                String sExcelYn = "N";
                if (mapInVar != null) {
                    sExcelYn = StringUtils.defaultString((String)mapInVar.get("excel_yn"));
                }

                // 페이징 정보
                if (!"Y".equals(sExcelYn)) {
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = dlwNewTypeConsService.getChatSndgHstrCount(hmParam);
                mapOutVar.put("tc_chat_sndg_hstr", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeConsService.getChatSndgHstrList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 Encom 이력 탭
     * 대상 테이블 : LF_DMUSER.CNSL_MNG, LF_DMUSER.MEM_PROD_ACCNT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/encom/getNewTypeDlwEncomList")
    public ModelAndView getNewTypeDlwEncomList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "엔컴 상담 조회대상 고객번호가 없습니다.");

                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = dlwNewTypeConsService.getDlwConsCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeConsService.getDlwConsList(hmParam);

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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 홈페이지 이력 탭
     * 대상 테이블 : LF_DMUSER.MB_MEM_CHNG_PTC@DMWEB, LF_DMUSER.MB_MEM_MST@DMWEB
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/lifeway/getNewTypeLifeWayList")
    public ModelAndView getDlwlifeweyList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "고객번호가 없습니다.");
                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = dlwNewTypeConsService.getDlwlifeweyCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeConsService.getDlwlifeweyList(hmParam);

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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 아웃바운드정보 탭
     * 대상 테이블 : PS_WILLVI.TB_TRGT_CUST_DTPT, PS_WILLVI.TB_DPMS_RESL_HSTR
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div3/trgt/getNewTypeTrgtCustDtptList")
    public ModelAndView getNewTypeTrgtCustDtptList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                mList = dlwNewTypeConsService.getNewTypeTrgtCustDtptList(hmParam);
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 해약정보 탭
     * 대상 테이블 : LF_DMUSER.RESNSTD_M, LF_DMUSER.RESNSTD_D, LF_DMUSER.PAY_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/resn/getNewTypeResnCnctList")
    public ModelAndView getDlwCnctList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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

                Map<String, Object> mDtpt = dlwNewTypeConsService.getDlwResnAmtList(hmParam); // 적용일자 및 SEQ 조회

                if (mDtpt != null) {
                    hmParam.put("mst_seq", mDtpt.get("seq"));

                    List<Map<String, Object>> mList = dlwNewTypeConsService.getResnAmtDetailList(hmParam);

                    List<Map<String, Object>> mRntmList = dlwNewTypeConsService.getDlwPayMngList(hmParam);
                    if (mRntmList.size() > 0) {
                        mapOutVar.put("gv_rntm_cnt", mRntmList.get(0).get("no")); // 회차
                    } else {
                        mapOutVar.put("gv_rntm_cnt", 0);
                    }

                    dtptMap.setRowMaps(mDtpt);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output_dtpt", dtptMap);
                    mapOutDs.put("ds_output_list", listMap);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 납입이력 탭
     * 대상 테이블 : LF_DMUSER.PAY_MNG, LF_DMUSER.PAY_MNG_DTL, LF_DMUSER.PAY_MNG_DTL1
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/pay/getNewTypePayMngHstrList")
    public ModelAndView getDlwPayMngAllList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getDlwPayMngAllList(hmParam);

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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 결제정보 탭
     * 대상 테이블 : LF_DMUSER.NEW_CANCL_APP, LF_DMUSER.CARD_NEW_CANCL_APP
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/pay/getNewTypePayAppHstrList")
    public ModelAndView getDlwPymnHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getDlwPymnHstrList(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20190911
     * 이름 : 송준빈
     * 추가내용 : 고객 결제정보 크게 보기
     * 대상 테이블 : LF_DMUSER.NEW_CANCL_APP, LF_DMUSER.CARD_NEW_CANCL_APP, LF_DMUSER.MEM_PROD_ACCNT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/pay/getNewTypePayAppHstrPopList")
    public ModelAndView getNewTypePayAppHstrPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            
            /* 회원 결제정보이력 기본정보 */
            List<Map<String, Object>> mList1 = dlwNewTypeConsService.getDlwPymnHstrList(hmParam);
            listMap1.setRowMaps(mList1);
            mapOutDs.put("ds_output1", listMap1);
            
            /* 회원 기본정보*/
            List<Map<String, Object>> mList2 = dlwNewTypeConsService.getDlwMemProdAccntList(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 과거청구이력 탭
     * 대상 테이블 : LF_DMUSER.WDRW_REQ, LF_DMUSER.CARD_WDRW_REQ
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/wdrw/getNewTypeBeforeWdrwList")
    public ModelAndView getDlwAskHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getDlwAskHstrList(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 신규청구이력 탭
     * 대상 테이블 : LF_DMUSER.WDRW_REQ, LF_DMUSER.CARD_WDRW_REQ
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/wdrw/getNewTypeAfterWdrwList")
    public ModelAndView getDlwAskHstrList1(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList2 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getMainWdrwLogList(hmParam);
            mList2 = dlwNewTypeConsService.getMainWdrwExceList(hmParam);
            listMap.setRowMaps(mList);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output", listMap);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 LGUPLUS 선납입정보 탭
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/uplus/getNewTypeLgUplusPrepayList")
    public ModelAndView getNewTypeLgUplusPrepayList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getNewTypeLgUplusPrepayList(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 입금전체현황 탭
     * 대상 테이블 : LF_DMUSER.PAY_TOT_INFO_VIEW, LF_DMUSER.PRODUCT_DTL, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/paytot/getNewTypePayTotInfoList")
    public ModelAndView getNewTypePayTotInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList1 = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList2 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String sProdCd = StringUtils.defaultString((String) mapInVar.get("prod_cd"));

            if ("".equals(sAccntNo)) 
            {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }
            else
            {
            	hmParam.put("accnt_no", sAccntNo);
            	hmParam.put("prod_cd", sProdCd);
            	
                mList1 = dlwNewTypeConsService.getNewTypePayTotInfoList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output", listMap1);
                
                mList2 = dlwNewTypeConsService.getNewTypeProductDtl(hmParam);
                
                if(mList2.size() <= 0) // PRODUCT_DTL에 포함이 되지 아니한 NXX계열의 상품들
                {
                	mList2 = dlwNewTypeConsService.getNewTypeProductDtlNxx(hmParam);
                }
                
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_outputProdDtl", listMap2);
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
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 부가서비스정보 탭
     * 대상 테이블 :  LF_DMUSER.PROD_OPT_SVC_MST, LF_DMUSER.PROD_OPT_SVC_DTL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/newType/div6/optsvc/getNewTypeProdOptSvcList")
    public ModelAndView getNewTypeProdOptSvcList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = dlwNewTypeConsService.getNewTypeProdOptSvcList(hmParam);
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
}