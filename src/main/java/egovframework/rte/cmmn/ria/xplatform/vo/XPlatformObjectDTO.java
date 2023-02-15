package egovframework.rte.cmmn.ria.xplatform.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.ibatis.common.beans.ClassInfo;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataSetList;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;

/**
 * XPlatform 과 통신 자료 구조
 * @author yimoc@tobesoft.com
 *
 */
public class XPlatformObjectDTO implements Serializable {
    private static final long serialVersionUID = 122554256929227668L;

    private DataSetObject transInfo;                   // transaction 정보를 가지는  Dataset정보
    private Map<String, Object> inVariableMap;         // request 된 Variable정보
    private Map<String, Object> outVariableMap;        // response할 Variable 정보

    private Map<String, DataSetObject> inDataSetMap;   // Request 된 DataSets 정보 <DataSetName, dataSet >
    private Map<String, DataSetObject> outDataSetMap;  // Respose 할 DataSets 정보 <DataSetName, dataSet >

    public void setVariableMap(VariableList vList) {
        inVariableMap = new HashMap<String, Object>();
        for (int i = 0; i < vList.size(); i++) {
            inVariableMap.put(vList.get(i).getName(), vList.get(i).getObject());
        }
    }

    /**
     * Dataset 중에 Transaction 정보를 가진 DataSet을 DataSetObject 정보로 바꾼다.
     *
     * @param dsList
     * @param datasetName
     */
    public void setTranInfoMap(DataSetList dsList) {
        transInfo = new DataSetObject();
        DataSet dsTransInfo = dsList.get(XPlatformConstant.TRAN_INFO_DATASET_NAME);
        if (dsTransInfo != null){
            DataSetObject dsObject = new DataSetObject();
            transInfo.convertDataSet2DataSetObject(dsTransInfo,"egovframework.rte.cmmn.ria.xplatform.vo.XPlatformTransactionVO");
        }
    }

    /**
     * n개의 input DataSet을 Map에 넣는다.
     *    Map의 key 값은 input DataSet이름이 들어가며 value는 DataSetObject(List)객체가 들어간다.
     *    DataSetObject 객체는 Object(class member= DataSet.column명, member value=DataSet.rowvalue)를 가진다.
     *
     * @param dsList
     */
    public void setInDataSetMap(DataSetList dsList) {
        inDataSetMap = new HashMap();
        for (int i = 0; i < transInfo.size(); i++) {
            XPlatformTransactionVO tranVO = (XPlatformTransactionVO) transInfo.get(i);         //transaction Info DataSet에서
            String datasetName = tranVO.getStrInDataset();
            String voName = tranVO.getStrInVOClass();

            DataSet dataset = dsList.get(datasetName);
            DataSetObject dsObject = new DataSetObject();
            dsObject.convertDataSet2DataSetObject(dataset,voName);
            if (dsObject != null)
                inDataSetMap.put(datasetName, dsObject);
        }

    }

    public DataSetObject getTransInfo() {
        return transInfo;
    }

    public Map<String, Object> getInVariableMap() {
        return inVariableMap;
    }

    public Map<String, DataSetObject> getInDataSetMap() {
        return inDataSetMap;
    }

    public Map<String, DataSetObject> getOutDataSetMap() {
        if (outDataSetMap == null)
            outDataSetMap = new HashMap();
        return outDataSetMap;
    }

    public Map<String, Object> getOutVariableMap() {
        if (outVariableMap == null)
            outVariableMap = new HashMap();
        return outVariableMap;
    }

    /*
    private void printMap(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            System.out.println("{" + key + " , " + value + "}");
        }
    }*/

}
