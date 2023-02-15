package powerservice.business.mall.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwMallLinkedController {
	
	@Resource
	private DlwMallLinkedService dlwMallLinkedService;

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/malllinked/getMemberBasicList")
    public ModelAndView getMemberBasicList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList01 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        String sMpaYn = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }
            
            List<Map<String, Object>> mListYn01 = dlwMallLinkedService.getMemberBasicYn(hmParam);
            
            for(int idx = 0; idx < mListYn01.size(); idx++)
            {
            	Map<String,Object> rowData = mListYn01.get(idx);
            	sMpaYn = (String)rowData.get("MPA_YN");
            }
            
            if("Y".equals(sMpaYn)) {
            	mList01 = dlwMallLinkedService.getMemberBasicList(hmParam);
            	listMap01.setRowMaps(mList01);
                mapOutDs.put("ds_output01", listMap01);
            }
//            List<Map<String, Object>> mList01 = dlwMallLinkedService.getMemberBasicList(hmParam);
//            listMap01.setRowMaps(mList01);
//            mapOutDs.put("ds_output01", listMap01);

            List<Map<String, Object>> mList02 = dlwMallLinkedService.getMemberBasicListUseAmt(hmParam);
            listMap02.setRowMaps(mList02);
            mapOutDs.put("ds_output02", listMap02);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 신규회원 전송
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/malllinked/insertMemberBasicNew")
    public ModelAndView insertMemberBasicNew(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("P_MEM_NO", strMemberNo);
            hmParam.put("P_ACCNT_NO", sAccountNo);
            hmParam.put("P_STATE", "0");
            hmParam.put("P_CREATE_ID", oUser.getUserid());

            dlwMallLinkedService.updateLFResnMemberState(hmParam);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/malllinked/insertMemberBasicAmt")
    public ModelAndView insertMemberBasicAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            hmParam.put("sMem_no", strMemberNo);
            hmParam.put("sAccnt_no", sAccountNo);
            hmParam.put("nSend_amt", sSendAmt);

            ParamUtils.addSaveParam(hmParam);
            
            dlwMallLinkedService.insertMemberBasicAmt(hmParam);

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
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부/행사여부 확인
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/malllinked/getResnMemberState")
    public ModelAndView getResnMemberState(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try 
        {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String strMemberNo = (String)mapInVar.get("sMem_no");
            String sAccountNo  = (String)mapInVar.get("sAccnt_no");
            String sSendAmt    = (String)mapInVar.get("nSend_amt");

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("P_MEM_NO", strMemberNo);
            hmParam.put("P_ACCNT_NO", sAccountNo);
            hmParam.put("P_STATE", "0");
            
            List<Map<String, Object>> mList01 = dlwMallLinkedService.getResnMemberStateEvent(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output01", listMap01);

            List<Map<String, Object>> mList02 = dlwMallLinkedService.getResnMemberStateRescission(hmParam);
            listMap02.setRowMaps(mList02);
            mapOutDs.put("ds_output02", listMap02);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/malllinked/updateResnMemberState")
    public ModelAndView updateResnMemberState(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try 
        {
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            if (listInDs.size() > 0) 
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                hmParam.put("P_STATE", hmParam.get("state"));
                hmParam.put("P_CREATE_ID", oUser.getUserid());
                
                if("0".equals(hmParam.get("state"))) {
                	int count = dlwMallLinkedService.updateLFResnMemberState(hmParam);
                } else {
                	int count = dlwMallLinkedService.updateResnMemberState(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 상품권 종류 저장
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/malllinked/saveOptSvcList")
    public ModelAndView saveOptSvcList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            String msg = dlwMallLinkedService.saveOptSvcList(srchInDs);  // 저장

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
     * 해약 구분(해약/청약)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/malllinked/gubn")
    public ModelAndView getResnGubn(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) 
            {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }
            
            hmParam.put("accnt_no", sAccntNo);
            int resnGubn = dlwMallLinkedService.getResnGubn(hmParam);
            mapOutVar.put("gv_resn_gubn", resnGubn);

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