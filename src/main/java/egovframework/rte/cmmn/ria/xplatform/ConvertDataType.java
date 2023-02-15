package egovframework.rte.cmmn.ria.xplatform;

import java.util.Date;

import com.tobesoft.xplatform.data.DataTypes;

public class ConvertDataType {
	public static int getPlatformDataType(Object obj) {
		int dataType = DataTypes.STRING;
		if (obj== null) return dataType;
		Class clz = obj.getClass();
		String typeName = clz.getName();
		//System.out.println("type=" + typeName);
		if (typeName.equals(String.class.getName())) {
			dataType = DataTypes.STRING;
		} else if (typeName.equals(Integer.class.getName())) {
			dataType = DataTypes.INT;
		} else if (typeName.equals(Boolean.class.getName())) {
			dataType = DataTypes.INT;
		} else if (typeName.equals(Long.class.getName())) {
			dataType = DataTypes.BIG_DECIMAL;
		} else if (typeName.equals(Double.class.getName())) {
			dataType = DataTypes.FLOAT;
		} else if (typeName.equals(Date.class.getName())) {
			dataType = DataTypes.DATE_TIME;
		} else if (typeName.equals(Byte[].class.getName())) {
			dataType = DataTypes.BLOB;
		}

		return dataType;
	}
}
