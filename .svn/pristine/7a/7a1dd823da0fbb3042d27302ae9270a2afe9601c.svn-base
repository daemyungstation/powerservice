package egovframework.rte.cmmn.ria.xplatform.vo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataTypes;

import egovframework.rte.cmmn.ria.xplatform.ConvertDataType;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;

public class DataSetVOMapping {
	
	/**
	 *  
	 *  DataSet의 하나의 row 정보를 VO클래스로 변경한다.
	 * @param ds DataSet
	 * @param classNames Value Object class Name
	 * @return
	 */
	
	public static Object getVOFromDataSetRow(String className,DataSet ds,int rowIndex,int isDelete){
		Class<?> clz;
		Object vo = null;
		try {
			clz = Class.forName(className);
			ClassInfo classInfo = ClassInfo.getInstance(clz);
			vo = clz.newInstance();
			
			Set <String> members = classInfo.getMembers();
			Iterator <String> iter = members.iterator();
			while (iter.hasNext()) {							//member 개수 만큼 값을 세팅한다.
				String memberName = iter.next();
				Object data;
				if (isDelete == 0)								//isDelete 에 따라 column 값을 읽어오는 API가 다름 
					data = ds.getObject(rowIndex, memberName);
				else 								
					data = ds.getRemovedData(rowIndex, memberName);

				Method m = classInfo.getSetterMethod(memberName);
				if (m != null && data != null) 
					m.invoke(vo, data);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	/**
	 * VO의 값들을 DataSet의 하나의 row에 추가한다.
	 * @param vo
	 * @param className
	 * @param ds
	 * @param rowIndex
	 */
	public static void setDataSetRowFromVO(Object vo, String className,DataSet ds,int rowIndex){
		Class<?> clz;

		try {
			clz = Class.forName(className);
			ClassInfo classInfo = ClassInfo.getInstance(clz);
			vo = clz.cast(vo);
			
			Set <String> members = classInfo.getMembers();
			Iterator <String> iter = members.iterator();
			while (iter.hasNext()) {							//member 개수 만큼 값을 세팅한다.
				Object value = null;
				String memberName = iter.next();
				Method m = classInfo.getGetterMethod(memberName);
				if (m != null ) 
					value = m.invoke(vo);
//				System.out.println("memberName="+memberName);
//				System.out.println("value="+value);
				if (value != null && (!memberName.equals("rowType"))) {		
					if (ds.getColumn(memberName) == null) {
						ds.setChangeStructureWithData(true);
						ds.addColumn(memberName, ConvertDataType.getPlatformDataType(value));
					}
					ds.set(rowIndex, memberName, value);
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/*	public Object getObjectFromDataSet(DataSet ds, String classNames)  {
	Class<?> clz;
	Object vo = null;
	
	try {
		
		clz = Class.forName(classNames);
		ClassInfo classInfo = ClassInfo.getInstance(clz);
		vo = clz.newInstance();
		for (int i = 0; i < ds.getColumnCount(); i++) {
			String memberName = ds.getColumn(i).getName();
			String strparam = null;
			for (int j = 0; j < ds.getRowCount(); j++) {
				strparam = ds.getString(j, i);			//row value
				System.out.println("strparam="+strparam);
				Method m = classInfo.getMethod(memberName);
				if (m != null){
					m.invoke(vo, strparam);
				}
			}
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	}
	
	return vo;
}*/
}
