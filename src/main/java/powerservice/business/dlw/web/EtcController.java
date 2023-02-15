package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.EtcService;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 기타관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016.12.13
 */
@Controller
public class EtcController {

	private final Logger log = LoggerFactory.getLogger(EtcController.class);
	
    @Resource
    private EtcService etcService;
    
    /**
     * 부서별 IP 정보 목록 삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/etc/deleteDeptIPInfoList")
    public ModelAndView deleteDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        
    	try {
            int iCudCnt = etcService.deleteDeptIPInfoList(xpDto);
            
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
     * 카드 수료율 조회
     * 
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/etc/selectCardFeeRtMngList")
    public ModelAndView selectCardFeeRtMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = etcService.selectCardFeeRtMngList(hmParam);
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
     * 부서별 IP 정보 목록 조회
     * 
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/etc/selectDeptIPInfoList")
    public ModelAndView selectDeptIPInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = etcService.selectDeptIPInfoList(hmParam);
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
     * 카드 수수료율 등록/수정/삭제
     * - asis - mergeCardFeeRt
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/etc/saveCardFeeRt")
    public ModelAndView saveCardFeeRt(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        
    	try {
            int iCudCnt = etcService.saveCardFeeRt(xpDto);
            
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
     * 부서별 IP 정보 목록 저장
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/etc/saveDeptIPInfoList")
    public ModelAndView saveDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        
    	try {
            int iCudCnt = etcService.saveDeptIPInfoList(xpDto);
            
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
     * 추가내용 : 상담대상자관리(NEW) 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/etc/cmpg/getCounselTargetList")
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

            int nTotal = etcService.getCounselTargetCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            List<Map<String, Object>> mList = etcService.getCounselTargetList(hmParam);
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
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 리스트 조회
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/etc/cmpg/getDirectCounselList")
    public ModelAndView getDirectCounselList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = etcService.getDirectCounselCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            List<Map<String, Object>> mList = etcService.getDirectCounselList(hmParam);
            listMap.setRowMaps(mList);
            
            mapOutDs.put("ds_output", listMap);
            
            etcService.updateDirectSessionClose(hmParam);

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
}