package egovframework.rte.cmmn.ria.xplatform.vo;

import java.util.ArrayList;
import java.util.List;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.sample.service.XPSampleVO;

/**
 * 
 * @author yimoc@tobesoft.com
 *
 */

public class DataSetObject {
	List <Object> rowObjects;	
	
	public DataSetObject(){
		rowObjects=new ArrayList();
	}
			
	public Object get(int index){
		return rowObjects.get(index);
	}
	
	public void add(Object elem){
		rowObjects.add(elem);
	}
	
	public int size(){
		return rowObjects.size();
	}
	
	public Object[] toArray(){
		return rowObjects.toArray();
	}
	
	public void setRowObjects(List <Object> objs){
		rowObjects = objs;
	}
	
	
	public void convertDataSet2DataSetObject(DataSet dataset,String voName){
		if (dataset == null) return;
				
		//Noraml, Insert, update 
		//System.out.println("row count ="+dataset.getRowCount());
		for (int j = 0; j < dataset.getRowCount(); j++) {
			//System.out.println("noraml "+j);
			Object obj = DataSetVOMapping.getVOFromDataSetRow(voName, dataset, j, 0);
			//row type 세팅
			if (obj instanceof RowType){
				int rowType = dataset.getRowType(j);
				((RowType) obj).setRowType(rowType);
			}
			if (obj != null)
				add(obj);	
		}
		//Delete
		//System.out.println("delete count ="+dataset.getRemovedRowCount());
		for (int i = 0; i < dataset.getRemovedRowCount(); i++) {
			//System.out.println("delete "+i);
			Object obj = DataSetVOMapping.getVOFromDataSetRow(voName, dataset, i, 1);
			if (obj instanceof RowType){
				((RowType) obj).setRowType(DataSet.ROW_TYPE_DELETED);
			}
			if (obj != null) 	
				add(obj);
		}
	}
	
	/**
	 * List 형태의 Object 정보를 이용하여 하나의 Dataset의 데이터를 세팅한다.
	 * 
	 * @param datasetInfo
	 * @param ds
	 */
	public void convertDatSetObject2DataSet( DataSet ds, String voClass) {
		int rowIndex = 0;
		for (int i = 0; i < rowObjects.size(); i++) {
			rowIndex = ds.newRow();
			Object obj = rowObjects.get(i);
			
			XPSampleVO vo = (XPSampleVO) obj;
			System.out.println("vo="+vo.getId());
			System.out.println("\t="+vo.getName());
			
			DataSetVOMapping.setDataSetRowFromVO(obj , voClass, ds, i);
		}
	}


}
