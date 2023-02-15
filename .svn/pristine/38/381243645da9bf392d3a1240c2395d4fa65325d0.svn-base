package egovframework.rte.sample.service;

import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.vo.DataSetObject;
/*
 * TODO context-aspect.xml 정리하기
 */
public interface EgovSampleServiceXp {

	int sampleModifyService(DataSetMap tranInfo, Map inVariableMap, Map inDataSetMap, Map ouVariableMap, Map outDataSetMap) throws Exception;

	void sampleSelectService(
			DataSetMap tranInfo,
			Map<String, Object> inVar, 	Map<String, DataSetMap> inDataset,
			Map<String, Object> outVar, Map<String, DataSetMap> outDataset) throws Exception;
	
	
	void sampleSelectDataType(
			DataSetMap tranInfo,
			Map<String, Object> inVar, 	Map<String, DataSetMap> inDataset,
			Map<String, Object> outVar, Map<String, DataSetMap> outDataset) throws Exception;
	
	//Value Object 형태로 이용
	void sampleSelectVOService(
			DataSetObject tranInfo,
			Map<String, Object> inVar, 	Map<String, DataSetObject> inDataset,
			Map<String, Object> outVar, Map<String, DataSetObject> outDataset) throws Exception;
	
	void sampleModifyVOService(
			DataSetObject tranInfo,
			Map<String, Object> inVar, 	Map<String, DataSetObject> inDataset,
			Map<String, Object> outVar, Map<String, DataSetObject> outDataset) throws Exception;
	
}
