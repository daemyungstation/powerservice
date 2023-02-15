/*
 * (@)# CommonUtils.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2014. 9. 26.
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */
package powerservice.core.util.excel;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 엑셀 필터 Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2014. 9. 26.
 * @프로그램ID <<프로그램 아이디>>
 */

public class ExcelValidator {
	public final static byte FILTER_REQUIRE	= 0x01;
	public final static byte FILTER_LENGTH	= 0x02;
	public final static byte FILTER_NUMBER	= 0x04;
	public final static byte FILTER_PHONE	= 0x08;
	public final static byte FILTER_EMAIL	= 0x10;

    /**
     * 문자열이 널인지 아닌지 체크하여 널이면 빈 문자열을 반환한다. 출력화면에 "null" 문자열 출력을 방지하기 위해서 사용.
     *
     * @param szOrgValue
     *            체크 대상 문자열
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */

	public static String validate(Map<String, Object> map, String target, int option) {
        return validate(map, target, option, 0);
    }

    public static String validate(Map<String, Object> map, String target, int option, Object addoption) {
    	String resultStr = "";
    	String str = StringUtils.defaultString((String) map.get(target));

    	if((option & FILTER_REQUIRE) > 0 && str.trim().length()<=0) {
    		map.put(target, null);
    		resultStr = target + ":'필수입력',";
    		return resultStr;
    	}

    	if(!str.equals("") && (option & FILTER_NUMBER) > 0) {
    		str = str.replaceAll("-", "");

    		String exp = "\\d*$";


    		if (addoption.getClass().getCanonicalName().equals("int[]")) {
    			int prefix = ((int [])addoption)[0];
    			int subfix = ((int [])addoption)[1];


    			exp = "^\\d{0,"+prefix+"}$|" + "^\\d{0,"+prefix+"}";
    			if (subfix > 0) {
    				exp += "[.]\\d{0,"+subfix+"}";
    			}
    			exp += "$";
    		}

    		if (!Pattern.matches(exp, str)) {
    			map.put(target, null);
	    		resultStr = target + ":'숫자제한',";
	    		return resultStr;
    		}
    		map.put(target, str);
    	}

    	if(!str.equals("") && (option & FILTER_PHONE) > 0) {
    		str = str.replaceAll("-", "");
    		if (!Pattern.matches("^0\\d{1,2}\\d{3,4}\\d{4}$", str)) {
    			map.put(target, null);
	    		resultStr = target + ":'번호제한',";
	    		return resultStr;
    		}
    		map.put(target, str);
    	}

    	if(!str.equals("") && (option & FILTER_EMAIL) > 0 && !Pattern.matches("^([0-9a-zA-Z_\\.-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$", str)) {
    		map.put(target, null);
    		resultStr = target + ":'이메일제한',";
    		return resultStr;
    	}


    	if(!str.equals("") && (option & FILTER_LENGTH) > 0 && str.length() > Integer.parseInt(String.valueOf(addoption))) {
    		map.put(target, null);
    		resultStr = target + ":'길이제한 max:" + addoption + ", now:" + str.length() + "',";
    		return resultStr;
    	}

        return resultStr;
    }

}
