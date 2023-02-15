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

import powerservice.business.dlw.service.EventStockMngService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 행사 모니터링 정보 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Controller
public class EventStockMngController {

	private final Logger log = LoggerFactory.getLogger(EventStockMngController.class);
	
    @Resource
    private EventStockMngService eventStockMngService;
    
    /**
     * ???
     * 관련화면 : 발주관리
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectOrderMngList")
    public ModelAndView selectOrderMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = eventStockMngService.selectOrderMngList(hmParam);
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteWhInCncl")
    public ModelAndView saveMonitorResultReport(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int rst = eventStockMngService.deleteWhInCncl(xpDto);
            if (rst == -1) {
            	// TODO : 메시지 확인 필요
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     * 관련화면 : 발주관리
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectOrdDtlInfo")
    public ModelAndView selectOrdDtlInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap 	= new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList = eventStockMngService.selectOrderMngList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); // list
                
                List<Map<String, Object>> dtlList = eventStockMngService.selectOrdDtlInfo(hmParam);
                listMap2.setRowMaps(dtlList);
                mapOutDs.put("ds_output2", listMap2); // dtlList
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/mergeOrderMng")
    public ModelAndView mergeOrderMng(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            eventStockMngService.mergeOrderMng(xpDto);
            
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteOrdMst")
    public ModelAndView deleteOrdMst(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            eventStockMngService.deleteOrdMst(xpDto);
            
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/insertWhInMst")
    public ModelAndView insertWhInMst(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.insertWhInMst(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("입력한 입고일자의 해당월은 이미 마감되어 입고처리 할 수 없습니다.");
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
     * 입고목록 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectInWhList")
    public ModelAndView selectInWhList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = eventStockMngService.selectInWhList(hmParam);
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
     * ???
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectOutWhList")
    public ModelAndView selectOutWhList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = eventStockMngService.selectOutWhList(hmParam);
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
     * ???
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectWhMvList")
    public ModelAndView selectWhMvList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = eventStockMngService.selectWhMvList(hmParam);
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
     * 입고내역 등록/수정
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/mergeWhInMst")
    public ModelAndView mergeWhInMst(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        HashMap mRet = new HashMap<String,String>(); // wh_in_no 리턴
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
    	
    	try {
            int cnt = eventStockMngService.mergeWhInMst(xpDto, mRet);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
            }
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }
    	
    	mapOutVar.put("fv_wh_in_no2", CommonUtils.nvls((String)mRet.get("wh_in_no")));
    	
    	modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
    	
    	return modelAndView;
    }
    
	/**
     * 입고내역 삭제
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteWhInMng")
    public ModelAndView deleteWhInMng(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.deleteWhInMng(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     * 관련화면 : 발주관리
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectInWhDtlInfo")
    public ModelAndView selectInWhDtlInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap 	= new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList = eventStockMngService.selectInWhList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); // list
                
                List<Map<String, Object>> dtlList = eventStockMngService.selectInWhDtlInfo(hmParam);
                listMap2.setRowMaps(dtlList);
                mapOutDs.put("ds_output2", listMap2); // dtlList
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/mergeWhOutMst")
    public ModelAndView mergeWhOutMst(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.mergeWhOutMst(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteWhOutMng")
    public ModelAndView deleteWhOutMng(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            
    		int cnt = eventStockMngService.deleteWhOutMng(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/mergeWhMvMng")
    public ModelAndView mergeWhMvMng(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.mergeWhMvMng(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteWhMvMng")
    public ModelAndView deleteWhMvMng(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.deleteWhMvMng(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectStockInspectList")
    public ModelAndView selectStockInspectList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = eventStockMngService.selectStockInspectList(hmParam);
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/mergeStockInspect")
    public ModelAndView mergeStockInspect(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.mergeStockInspect(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteStockInspectMst")
    public ModelAndView deleteStockInspectMst(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.deleteStockInspectMst(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     * 관련화면 : 발주관리
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/selectStockCloseList")
    public ModelAndView selectStockCloseList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap 	= new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList = eventStockMngService.selectStockCloseList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); // list
                
                List<Map<String, Object>> dtlList = eventStockMngService.selectStockCloseListByWh(hmParam);
                listMap2.setRowMaps(dtlList);
                mapOutDs.put("ds_output2", listMap2); // gdsList
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/insertStockClose")
    public ModelAndView insertStockClose(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
            int cnt = eventStockMngService.insertStockClose(xpDto);
            
            if (cnt == -1) {
            	throw new Exception("이미 마감처리되었습니다.");
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
     * ???
     *
     * @param xpDto XPlatformMapDTO
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/evnt/stockMng/deleteStockClose")
    public ModelAndView deleteStockClose(XPlatformMapDTO xpDto) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
    	
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
    	
    	try {
    		
            int cnt = eventStockMngService.deleteStockClose(xpDto);
            
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