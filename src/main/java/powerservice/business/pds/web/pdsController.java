package powerservice.business.pds.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.pds.service.pdsPartService;
import powerservice.business.pds.service.pdsService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@EnableScheduling
@Controller
public class pdsController {
	
	@Resource
	private pdsPartService pdsPartService;
	
	@Resource
	private pdsService pdsService;
	
	@Resource
    private ConsService consService;
	
	// DB연결테스트 - gocs
	@RequestMapping(value = "/testConn/gocs")
	public void testConn() {
		System.out.println("pdsController testConn 호출!! ===================");
		
		List<Map<String, Object>> selectData = pdsPartService.testConn();
		
		System.out.println("selectData : " + selectData);
	}
	
	// DB연결테스트 - DMLIFE
	@RequestMapping(value = "/testConn/DMLIFE")
	public void testConn02() {
		System.out.println("pdsController testConn02 호출!! ========================");
		
		List<Map<String, Object>> selectData = pdsService.testConn02();
		
		System.out.println("selectData : " + selectData);
	}
	
	@RequestMapping(value = "/pds/findData")
	@ResponseBody
    public ModelAndView findData(XPlatformMapDTO xpDto, Model mode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> findDataInfo = new HashMap<String, Object>();
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
            findDataInfo = srchInDs.get(0);
            
            
            // 조회
            List<Map<String, Object>> DataList = pdsService.findData(findDataInfo);
            
            /*
            for(int i = 0; i < DataList.size(); i++) {
            	DataList.get(i).put("chk", "N");
            	           	
            	if(DataList.get(i).get("work_kind").equals("DF")) {
            		DataList.get(i).put("work_kind", "미납");
            	} else if(DataList.get(i).get("work_kind").equals("CD")){
            		DataList.get(i).put("work_kind", "해약방어");
            	} else {
            		DataList.get(i).put("work_kind", "해피콜");
            	}
            }
            */
            
            listMap.setRowMaps(DataList);
            mapOutDs.put("ds_output", listMap);

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
	
	@RequestMapping(value = "/pds/getMyId")
	public ModelAndView getMyName(XPlatformMapDTO xpDto, Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
		DataSetMap dsMap = new DataSetMap();
		
		// 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
		
		try {
			Map<String, Object> inDataMap = new HashMap<String, Object>();
			List<Map<String, Object>> inData = new ArrayList<Map<String,Object>>();
			
			Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
			UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
	        String id = oUserSession.getUserid();
	        
	        inDataMap.put("id", id);
	        inData.add(0, inDataMap);
	        dsMap.setRowMaps(inData);
	        outDataset.put("ds_output", dsMap);
	        
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
	
	@RequestMapping(value = "pds/uploadSave")
	@ResponseBody
	public ModelAndView uploadSave (XPlatformMapDTO xpDto, Model model) throws Exception{
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
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
            String work_kind = StringUtils.defaultString((String)mapInVar.get("work_kind"));
            
            List<Map<String, Object>> DataList = new ArrayList<Map<String, Object>>();
            String getDate = "";
            
            if(work_kind.equals("DF")) {
            	for(int i = 0; i < srchInDs.size(); i++) {
            		Map<String, Object> selectMap = pdsService.insertDF(srchInDs.get(i));
            		
            		Map<String, Object> addMap = new HashMap<String, Object>();
            		addMap.putAll(srchInDs.get(i));
            		addMap.put("seq", selectMap.get("SEQ"));
            		addMap.put("reg_dm", selectMap.get("REG_DM"));
            		addMap.put("work_kind", "미납");
            		addMap.put("chk", "N");
            		
            		DataList.add(addMap);
            	}
            	
            } else if (work_kind.equals("CD")) {
            	for(int i = 0; i < srchInDs.size(); i++) {
            		Map<String, Object> selectMap = pdsService.insertCD(srchInDs.get(i));
            		
            		Map<String, Object> addMap = new HashMap<String, Object>();
            		addMap.putAll(srchInDs.get(i));
            		addMap.put("seq", selectMap.get("SEQ"));
            		addMap.put("reg_dm", selectMap.get("REG_DM"));
            		addMap.put("work_kind", "해약방어");
            		addMap.put("chk", "N");
            		
            		DataList.add(addMap);
            	}
            } else {
            	for(int i = 0; i < srchInDs.size(); i++) {
            		Map<String, Object> selectMap = pdsService.insertHC(srchInDs.get(i));
            		
            		Map<String, Object> addMap = new HashMap<String, Object>();
            		addMap.putAll(srchInDs.get(i));
            		addMap.put("seq", selectMap.get("SEQ"));
            		addMap.put("reg_dm", selectMap.get("REG_DM"));
            		addMap.put("work_kind", "해피콜");
            		addMap.put("chk", "N");
            		
            		DataList.add(addMap);
            	}
            }
            
            listMap.setRowMaps(DataList);
            mapOutDs.put("ds_output", listMap);
            
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
	
	@RequestMapping(value = "pds/checkDelete")
	@ResponseBody
	public ModelAndView checkDelete (XPlatformMapDTO xpDto, Model model) throws Exception{
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> checkDeleteList = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            
            String strWorkGbn = mapInVar.get("work_kind").toString();
            
            DataSetMap findResultList = (DataSetMap)mapInDs.get("ds_input");
                        
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
	        String id = oUserSession.getUserid();
	        String strMemo = "";
            
            for(int i = 0; i < findResultList.size(); i++) {
            	if(findResultList.get(i).get("chk").equals("Y")) {
            		
            		findResultList.get(i).put("id", id);
            		findResultList.get(i).put("work_kind", strWorkGbn);
            		pdsService.checkDelete(findResultList.get(i));
            		//파라미터 확인해서 메모에 캠페인연동이라고 되어있으면 캠페인쪽 할당ID 삭제
            		//mem_no, accnt_no, tal, memo
            		//pmParam.get("mangi_amt_gbn").toString();
            		strMemo = findResultList.get(i).get("memo").toString();
            		if("캠페인PDS연동".equals(strMemo)) {
            			//update
            			pdsService.updateAlct(findResultList.get(i));
            		}
            		
            		continue;
            	}
            	
            	dataList.add(findResultList.get(i));
            }
            
            listMap.setRowMaps(dataList);
            mapOutDs.put("ds_output", listMap);

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
	
	@RequestMapping(value = "/pds/findResultData")
	@ResponseBody
    public ModelAndView findResultData(XPlatformMapDTO xpDto, Model mode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> findDataInfo = new HashMap<String, Object>();
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
            findDataInfo = srchInDs.get(0);
            
            
            // 조회
            List<Map<String, Object>> DataList = pdsService.findResultData(findDataInfo);
            /*
            for(int i = 0; i < DataList.size(); i++) {
            	int result_kind = Integer.parseInt(String.valueOf(DataList.get(i).get("result_kind")));
            	
            	// work_kind 코드값 > 이름 으로 변경
            	if(DataList.get(i).get("work_kind").equals("DF")) {
            		DataList.get(i).put("work_kind", "미납");
            	} else if(DataList.get(i).get("work_kind").equals("CD")){
            		DataList.get(i).put("work_kind", "해약방어");
            	} else {
            		DataList.get(i).put("work_kind", "해피콜");
            	}
            	
            	if(result_kind == 28) {
            		DataList.get(i).put("result_kind", "정보 없음");
            	} else {
            		// result_kind 코드값 > 이름 으로 변경
                	if(result_kind == 21) {
                		DataList.get(i).put("result_kind", "포기콜");
                	} else if(result_kind == 33){
                		DataList.get(i).put("result_kind", "응답");
                	} else if(result_kind == 9){
                		DataList.get(i).put("result_kind", "기계음(자동응답)");
                	} else if(result_kind == 4){
                		DataList.get(i).put("result_kind", "통화중");
                	} else if(result_kind == 17){
                		DataList.get(i).put("result_kind", "팩스");
                	} else if(result_kind == 17){
                		DataList.get(i).put("result_kind", "에러");
                	} else if(result_kind == 7){
                		DataList.get(i).put("result_kind", "전화 안받음");
                	} else if(result_kind == 5){
                		DataList.get(i).put("result_kind", "전화끊음");
                	} else if(result_kind == 32){
                		DataList.get(i).put("result_kind", "묵음");
                	} else if(result_kind == 15){
                		DataList.get(i).put("result_kind", "결번");
                	} else if(result_kind == 46){
                		DataList.get(i).put("result_kind", "Stale");
                	} else if(result_kind == 28){
                		DataList.get(i).put("result_kind", "초기 설정값");
                	}
            	}
            	
            }
            
            int pdsDataAllCount = pdsService.getAllCount(findDataInfo);
            mapOutVar.put("pdsDataAllCount", pdsDataAllCount);
            */
            
            listMap.setRowMaps(DataList);
            mapOutDs.put("ds_output", listMap);

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
	
	@RequestMapping(value = "/pds/findResultRealData")
	@ResponseBody
    public ModelAndView findResultRealData(XPlatformMapDTO xpDto, Model mode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> findDataInfo = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strGubun = "";		//업무구분값
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            findDataInfo = srchInDs.get(0);
            
            System.out.println("xxxxx : " + findDataInfo);
                        
            // 조회            
			List<Map<String, Object>> genesysDataListDF = pdsPartService.findResultRealData(findDataInfo);
                                                            
            listMap.setRowMaps(genesysDataListDF);
            mapOutDs.put("ds_output", listMap);

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
	
	
	@RequestMapping(value = "/pds/connectPds")
	@ResponseBody
    public ModelAndView connectPds(XPlatformMapDTO xpDto, Model mode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            List<Map<String, Object>> DataList = new ArrayList<Map<String,Object>>(); 

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            for(int i = 0; i < srchInDs.size(); i++) {
            	if(srchInDs.get(i).get("chk").equals("Y")) {
            		String reg_dm = String.valueOf(srchInDs.get(i).get("reg_dm")).replaceAll("[^\uAC00-\uD7A30-9a-zA-Z\\s]", "").replaceAll(" ", "");
            		srchInDs.get(i).put("reg_dm", reg_dm);
            		
            		pdsPartService.connPds(srchInDs.get(i));
            		pdsService.updateStatus(srchInDs.get(i));
            		//상담이력 한건씩 등록
            		saveConsdlw(srchInDs.get(i), "1");
            		continue;
            	}
            	
            	DataList.add(srchInDs.get(i));
            }
            
            listMap.setRowMaps(DataList);
            mapOutDs.put("ds_output", listMap);

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
	
	@RequestMapping(value = "/pds/chkConnection")
	@ResponseBody
    public ModelAndView chkConnection(XPlatformMapDTO xpDto, Model mode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> findDataInfo = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            // 조회
            List<Map<String, Object>> DataList = pdsPartService.chkConnection();
            
            listMap.setRowMaps(DataList);
            mapOutDs.put("ds_output", listMap);

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
	
	// 매일 아침 6시마다 실행
	// genesys db의 call_result데이터를 당일 ~ 전날3일까지만큼 가져와
	// DLCC DB의 시퀀스 번호 일치&date_result가 null인것 기준으로 대조해서 result_kind&date_result 컬럼 업데이트 시켜주기
	@Scheduled(cron = "0 0 6 * * *")
	// 테스트용
//	@Scheduled(cron = "0 0/10 * * * *")
	public void pdsConnDataInsert() {
		
		try {
			List<Map<String, Object>> genesysDataListDF = pdsPartService.selectDataDF();
			List<Map<String, Object>> genesysDataListCD = pdsPartService.selectDataCD();
			List<Map<String, Object>> genesysDataListHC = pdsPartService.selectDataHC();
			
			for(int i = 0; i < genesysDataListDF.size(); i++) {
				int result = pdsService.updateDataDF(genesysDataListDF.get(i));
				if(result > 0) {
					saveConsdlw(genesysDataListDF.get(i), "2");
				}
			}
			
			for(int i = 0; i < genesysDataListCD.size(); i++) {
				int result = pdsService.updateDataCD(genesysDataListCD.get(i));
				if(result > 0) {
					saveConsdlw(genesysDataListCD.get(i), "2");
				}
			}

			for(int i = 0; i < genesysDataListHC.size(); i++) {
				int result = pdsService.updateDataHC(genesysDataListHC.get(i));
				if(result > 0) {
					saveConsdlw(genesysDataListHC.get(i), "2");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
     * 상담 정보를 등록/수정한다. (대명)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public void saveConsdlw(Map <String, Object> pmParam, String psChk) throws Exception {
        String sConsno = "";
        String sAccntNo = StringUtils.defaultString((String)pmParam.get("accnt_no")); // 회원번호
        String sTeamCd = "";
        String sUserId = "";
        
        if("1".equals(psChk)) {
        	UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        	sTeamCd = oLoginUser.getTeamcd();
        	sUserId = oLoginUser.getUserid();
        } else if ("2".equals(psChk)) {
        	sTeamCd = "T10500";
        	sUserId = "ADMIN";
        }
        

        //인우상담 관련
        // 담당자 정보 저장
        pmParam.put("rsps_dept_cd", sTeamCd);
        pmParam.put("chpr_id", sUserId);
//        ParamUtils.addSaveParam(pmParam);
//        pmParam.put("cons_memo_tit", pmParam.get("cons_memo"));
//        pmParam.put("cons_memo_cntn", pmParam.get("cnsl_con"));
        pmParam.put("cons_dspsmddl_dtpt_cd", "10"); //상담처리중 - 일반
        pmParam.put("cons_stat_cd", "30"); //상담상태 - 처리완료
        pmParam.put("clnt_nm", pmParam.get("mem_nm"));
        pmParam.put("acpg_chnl_cd", "99");
        pmParam.put("consno_sqno", "1");
        pmParam.put("conc_yn", "N");

        //대명상담 관련
        pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
        pmParam.put("cnsl_cd", "01"); // COM_CD_GRP 테이블 기타

        pmParam.put("rgsr_id", sUserId);
        pmParam.put("amnd_id", sUserId);
        pmParam.put("cnsl_man", sUserId);
        pmParam.put("cntr_cd", "CCA");
        
        
        pmParam.put("cons_typ1_cd", "CT01040000" );
        pmParam.put("cons_typ2_cd", "CT01040100");
        
        String sResultNm = "";
        
        if("1".equals(psChk)) { //PDS연동
        	pmParam.put("cons_typ3_cd", "CT01040101"); //행사접수
        	pmParam.put("cons_memo_tit", "PDS연동");
//        	pmParam.put("cons_memo_cntn", "");
        	pmParam.put("cons_memo_cntn", "");
        } else if("2".equals(psChk)) { //아침6시 결과등록
        	sResultNm = (String)pmParam.get("result_nm");
        	pmParam.put("cons_typ3_cd", "CT01040101"); //접수취소
        	pmParam.put("cons_memo_tit", "PDS결과");
        	pmParam.put("cons_memo_cntn", sResultNm);
        } 
//        String sConsMemoTit = StringUtils.defaultString((String)pmParam.get("cons_memo_tit")); 		// 제목
//        String sConsMemoCntn = StringUtils.defaultString((String)pmParam.get("cons_memo_cntn")); 	// 내용
        // 회원번호가 없을 경우 기타문의 설정
        if ("".equals(sAccntNo)) {
            pmParam.put("accnt_no", "00000"); // 기타문의 lifeMngController.java 1199
        }

        // 신규등록
        if ("".equals(sConsno)) {
            pmParam.put("actp_id", sUserId); // 접수자 정보
            // 상담 등록
            sConsno = consService.insertCons(pmParam);
        }
    }
}


























