package powerservice.business.dlw.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwEvtTranService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwEvtTranController {
	
	@Resource
	private DlwEvtTranService dlwEvtTranService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/ext/insertEvtTranExt")
    public ModelAndView insertEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsInsertEvtTranExt = (DataSetMap)mapInDs.get("ds_input");
            String sInsertType = (String) mapInVar.get("insert_type");
            
            if (dsInsertEvtTranExt != null && dsInsertEvtTranExt.size() > 0) 
            {
            	for(int idx = 0; idx < dsInsertEvtTranExt.size(); idx++)
                {
            		hmParam = dsInsertEvtTranExt.get(idx);
        			ParamUtils.addSaveParam(hmParam);
        			hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
        			
            		if(sInsertType.equals("I"))
                	{
            			hmParam.put("create_tran_cd", "P");

            			/* 상품코드를 채번한다. (P:상품, M:상품모델, K:패키지) */
            			String sCreateProdCd = dlwEvtTranService.getEvtTranCd(hmParam);
            			hmParam.put("prod_cd", sCreateProdCd);
                	
            			dlwEvtTranService.insertEvtTranExt(hmParam);
                	}
            		else if(sInsertType.equals("U"))
                	{
                		dlwEvtTranService.updateEvtTranExt(hmParam);
                	}
                } 
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/ext/deleteEvtTranExt")
    public ModelAndView deleteEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsDeleteEvtTranExt = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsDeleteEvtTranExt != null && dsDeleteEvtTranExt.size() > 0) 
            {
            	hmParam = dsDeleteEvtTranExt.get(0);
                dlwEvtTranService.deleteEvtTranExt(hmParam);
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/ext/getEvtTranExt")
    public ModelAndView getEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                int nTotal = dlwEvtTranService.getEvtTranExtCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);
                
                List<Map<String, Object>> mList = dlwEvtTranService.getEvtTranExtList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 저장
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/model/saveEvtTranModel")
    public ModelAndView saveEvtTranModel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsSaveEvtTranModel = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsSaveEvtTranModel != null && dsSaveEvtTranModel.size() > 0) 
            {
            	for(int idx = 0; idx < dsSaveEvtTranModel.size(); idx++)
                {
            		hmParam = dsSaveEvtTranModel.get(idx);
            		ParamUtils.addSaveParam(hmParam);
            		hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
            		hmParam.put("create_tran_cd", "M");
            		
            		int iRowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (iRowType == DataSet.ROW_TYPE_INSERTED) 
            		{
            			/* 상품모델코드를 채번한다. (P:상품, M:상품모델, K:패키지) */
                		String sCreateModelCd = dlwEvtTranService.getEvtTranCd(hmParam);
                		hmParam.put("model_cd", sCreateModelCd);
                    	
                    	dlwEvtTranService.insertEvtTranModel(hmParam);
                    }
                    else if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                    {
                    	dlwEvtTranService.updateEvtTranModel(hmParam);
                    }
                } 
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/model/deleteEvtTranModel")
    public ModelAndView deleteEvtTranModel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsDeleteEvtTranModel = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsDeleteEvtTranModel != null && dsDeleteEvtTranModel.size() > 0) 
            {
            	hmParam = dsDeleteEvtTranModel.get(0);
                dlwEvtTranService.deleteEvtTranModel(hmParam);
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 모델리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/model/getEvtTranModel")
    public ModelAndView getEvtTranModel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                int nTotal = dlwEvtTranService.getEvtTranModelCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);
                
                List<Map<String, Object>> mList = dlwEvtTranService.getEvtTranModelList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/package/insertEvtTranPackage")
    public ModelAndView insertEvtTranPackage(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsInsertEvtTranPackage = (DataSetMap)mapInDs.get("ds_input");
            String sInsertType = (String) mapInVar.get("insert_type");
            
            if (dsInsertEvtTranPackage != null && dsInsertEvtTranPackage.size() > 0) 
            {
            	hmParam = dsInsertEvtTranPackage.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
            	
            	if(sInsertType.equals("I"))
            	{
	            	hmParam.put("create_tran_cd", "K");
	
	            	/* 상품모델코드를 채번한다. (P:상품, M:상품모델, K:패키지) */
	            	String sCreatePackageCd = dlwEvtTranService.getEvtTranCd(hmParam);
	            	hmParam.put("pkg_cd", sCreatePackageCd);
	                	
	                dlwEvtTranService.insertEvtTranPackage(hmParam);
            	}
            	else if(sInsertType.equals("U"))
            	{
            		dlwEvtTranService.updateEvtTranPackage(hmParam);
            	}
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/package/deleteEvtTranPackage")
    public ModelAndView deleteEvtTranPackage(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsDeleteEvtTranPackage = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsDeleteEvtTranPackage != null && dsDeleteEvtTranPackage.size() > 0) 
            {
            	hmParam = dsDeleteEvtTranPackage.get(0);
                dlwEvtTranService.deleteEvtTranPackage(hmParam);
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/package/getEvtTranPackage")
    public ModelAndView getEvtTranPackage(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                int nTotal = dlwEvtTranService.getEvtTranPackageCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);
                
                List<Map<String, Object>> mList = dlwEvtTranService.getEvtTranPackageList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 저장
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/single/saveEvtTranList")
    public ModelAndView saveEvtTranList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsSaveEvtTranList = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsSaveEvtTranList != null && dsSaveEvtTranList.size() > 0) 
            {
            	hmParam = dsSaveEvtTranList.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
            		
            	int iRowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            	if (iRowType == DataSet.ROW_TYPE_INSERTED) 
            	{
                    dlwEvtTranService.insertEvtTranList(hmParam);
                }
                else if (iRowType == DataSet.ROW_TYPE_UPDATED) 
                {
                    dlwEvtTranService.updateEvtTranList(hmParam);
                }
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/single/deleteEvtTranList")
    public ModelAndView deleteEvtTranList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsDeleteEvtTranList = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsDeleteEvtTranList != null && dsDeleteEvtTranList.size() > 0) 
            {
            	hmParam = dsDeleteEvtTranList.get(0);
                dlwEvtTranService.deleteEvtTranList(hmParam);
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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 조회(단일)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/single/getEvtTranList")
    public ModelAndView getEvtTranList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
                                
                List<Map<String, Object>> mList = dlwEvtTranService.getEvtTranList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

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
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환회원 조회리스트(종합)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/evtTran/list/getEvtTranMember")
    public ModelAndView getEvtTranMember(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                int nTotal = dlwEvtTranService.getEvtTranMembersCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);
                
                List<Map<String, Object>> mList = dlwEvtTranService.getEvtTranMembersList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

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