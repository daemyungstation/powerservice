/*
 * (@)# CommonUtils.java
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 8.
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
package powerservice.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import powerservice.business.bean.UserSession;
import powerservice.core.util.SessionUtils;

/**
 * 공통 유틸 Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 8.
 * @프로그램ID <<프로그램 아이디>>
 */

public class CommonUtils {

    /**
     * 문자열이 널인지 아닌지 체크하여 널이면 빈 문자열을 반환한다. 출력화면에 "null" 문자열 출력을 방지하기 위해서 사용.
     *
     * @param szOrgValue
     *            체크 대상 문자열
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */
    public static String checkNull(String str) {
        if (!StringUtils.hasLength(str)) {
            return "";
        }
        return str;
    }

    /**
     * request 로부터 parameter 정보들을 String으로 반환
     *
     * @param request
     * @return 파라미터명/값 String
     */
    public static String getParameterMapToString(HttpServletRequest request) {
        String parameterName = null; // 파라미터 구분자
        String parameterValue = null; // 파라미터 값
        String retString = "";

        @SuppressWarnings("unchecked")
        Enumeration<String> parameterNameList = request.getParameterNames();

        while (parameterNameList.hasMoreElements()) {
            parameterName = (String) parameterNameList.nextElement();

            parameterValue = request.getParameter(parameterName);

            retString += parameterName + "=" + parameterValue + "&";
        }

        return retString;
    }

    /**
     * request 로부터 parameter 정보들을 HashMap으로 반환
     *
     * @param request
     * @return 파라미터명/값 HashMap
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        HashMap<String, String> hmParameter = new HashMap<String, String>();
        String parameterName = null; // 파라미터 구분자
        String parameterValue = null; // 파라미터 값
        String decodefg = request.getParameter("decodefg");

        @SuppressWarnings("unchecked")
        Enumeration<String> parameterNameList = request.getParameterNames();

        while (parameterNameList.hasMoreElements()) {
            parameterName = (String) parameterNameList.nextElement();

            try {
                if ("1".equals(decodefg)) {
                    parameterValue = URLDecoder.decode(request.getParameter(parameterName),"utf-8");
                } else {
                    parameterValue = request.getParameter(parameterName);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (parameterValue != null) {
                hmParameter.put(parameterName, parameterValue.trim());
            } else {
                hmParameter.put(parameterName, parameterValue);
            }
        }

        return hmParameter;
    }

    /**
     * request 로부터 parameter 정보들을 HashMap으로 반환
     *
     * @param request
     * @return 파라미터명/값 HashMap
     */
    public static Map<String, Object> getParameterArrayMap(HttpServletRequest request) {
        HashMap<String, Object> hmParameter = new HashMap<String, Object>();
        String parameterName = null; // 파라미터 구분자
        String parameterValue = null; // 파라미터 값
        String[] parameterArray = null; // 파라미터 배열
        @SuppressWarnings("unchecked")
        Enumeration<String> parameterNameList = request.getParameterNames();

        while (parameterNameList.hasMoreElements()) {
            parameterName = (String) parameterNameList.nextElement();
            parameterValue = request.getParameter(parameterName);
            parameterArray = request.getParameterValues(parameterName);

            if (parameterArray != null && parameterArray.length > 1) {
                hmParameter.put(parameterName, parameterArray);
            } else if (parameterValue != null) {
                hmParameter.put(parameterName, parameterValue.trim());
            } else {
                hmParameter.put(parameterName, parameterValue);
            }
        }
        return hmParameter;
    }
    
    
    /**
     * Xplatform 에러 응답 메시지를 생성한다.
     *
     * @param errmsg
     * @return 포멧팅에러메시지
     */
    /* 
     ServletOutputStream os = response.getOutputStream();
     os.println(CommonUtils.mkXpErrorMsg("You have the wrong number!"));
     os.close();
     */
    public static String mkXpErrorMsg(String errmsg) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	sb.append("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
    	sb.append("<Parameters>");
    	sb.append("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
    	sb.append("<Parameter id=\"ErrorMsg\" type=\"string\">" + errmsg + "</Parameter>");
    	sb.append("</Parameters>");
    	sb.append("</Root>");
    	return sb.toString();
    }

    public static HashMap<String, String> getParameterMapProperties(Properties properties){
        HashMap<String, String> outParameter = new HashMap<String, String>();
        String parameterName = null; // 파라미터 구분자
        String parameterValue = null; // 파라미터 값
        Enumeration parameterNameList = properties.propertyNames();
        while (parameterNameList.hasMoreElements()) {
            parameterName = (String) parameterNameList.nextElement();
            parameterValue = properties.getProperty(parameterName);
            if (parameterValue != null) {
                outParameter.put(parameterName, parameterValue.trim());
            } else {
                outParameter.put(parameterName, parameterValue);
            }
        }
        return outParameter;
    }

    /**
     * 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 왼쪽에 filledStr이 채워진다.
     *
     * @param length
     *            총 문자열 길이
     * @param filledStr
     *            채워질 문자열
     * @param str
     *            문자열
     * @return 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 왼쪽에 filledStr이 채워진 문자열.
     * @since
     */
    public static String lfillstr(int length, String filledStr, String str) {
        if (filledStr == null) {
            return str;
        }

        if (str == null) {
            str = "";
        }

        int appendStrLength = str.length();
        if (appendStrLength > length) {
            return str.substring(0, length);
        }
        StringBuffer buffer = new StringBuffer();
        int fillCount = length - appendStrLength;
        for (int i = 0; i < fillCount; i++) {
            buffer.append(filledStr);
        }

        int bufferLength = buffer.length();
        if (bufferLength > fillCount) {
            buffer.delete(fillCount, bufferLength);
        }
        buffer.append(str);

        return buffer.toString();
    }

    /**
     * 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 왼쪽에 filledStr이 채워진다.
     *
     * @param length
     *            총 문자열 길이
     * @param filledStr
     *            채워질 문자열
     * @param str
     *            문자열
     * @return 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 왼쪽에 filledStr이 채워진 문자열.
     * @since
     */
    public static String lfillstrByte(int length, String filledStr, String str) {
        if (filledStr == null) {
            return str;
        }

        if (str == null) {
            str = "";
        }

        int appendStrLength = str.getBytes().length;
        if (appendStrLength > length) {
            return str.substring(0, length);
        }
        StringBuffer buffer = new StringBuffer();
        int fillCount = length - appendStrLength;
        for (int i = 0; i < fillCount; i++) {
            buffer.append(filledStr);
        }

        int bufferLength = buffer.length();
        if (bufferLength > fillCount) {
            buffer.delete(fillCount, bufferLength);
        }
        buffer.append(str);

        return buffer.toString();
    }

    /**
     * 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 오른쪽에 filledStr이 채워진다.
     *
     * @param length
     *            총 문자열 길이
     * @param filledStr
     *            채워질 문자열
     * @param str
     *            문자열
     * @return 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 오른쪽쪽에 filledStr이 채워진 문자열.
     * @since
     */
    public static String rfillstr(int length, String filledStr, String str) {
        if (filledStr == null) {
            return str;
        }

        if (str == null) {
            str = "";
        }

        int appendStrLength = str.length();
        if (appendStrLength > length) {
            return str.substring(0, length);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(str);

        int fillCount = length - appendStrLength;
        for (int i = 0; i < fillCount; i++) {
            buffer.append(filledStr);
        }

        int bufferLength = buffer.length();
        if (bufferLength > length) {
            buffer.delete(fillCount, length);
        }

        return buffer.toString();
    }

    /**
     * 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 오른쪽에 filledStr이 채워진다.
     *
     * @param length
     *            총 문자열 길이
     * @param filledStr
     *            채워질 문자열
     * @param str
     *            문자열
     * @return 총 문자열 길이에서 str 문자열 길이를 뺀 만큼의 개수 대로 str 오른쪽쪽에 filledStr이 채워진 문자열.
     * @since
     */
    public static String rfillstrByte(int length, String filledStr, String str) {
        if (filledStr == null) {
            return str;
        }

        if (str == null) {
            str = "";
        }

        int appendStrLength = str.getBytes().length;
        if (appendStrLength > length) {
            return str.substring(0, length);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(str);

        int fillCount = length - appendStrLength;
        for (int i = 0; i < fillCount; i++) {
            buffer.append(filledStr);
        }

        int bufferLength = buffer.length();
        if (bufferLength > length) {
            buffer.delete(fillCount, length);
        }

        return buffer.toString();
    }

    /**
     * 지역번호, 국번, 번호가 합쳐진 전화번호를 parsing한다.
     *
     * @param psPhoneNo
     *            전화번호
     * @param pnType
     *            지역번호: 1, 국번: 2, 번호: 3
     * @return pnType에 해당하는 번호
     */
    public static String parsePhoneNo(String psPhoneNo, int pnType) {
        if (psPhoneNo == null) {
            return "";
        }

        // 숫자 이외 문자 제거
        String sPhoneNo = extractNumeral(psPhoneNo);
        // sPhoneNo = sPhoneNo.replaceAll(" ", "");
        // sPhoneNo = sPhoneNo.replaceAll("	", "");
        // sPhoneNo = sPhoneNo.replaceAll("-", "");
        // sPhoneNo = sPhoneNo.replaceAll("(", "");
        // sPhoneNo = sPhoneNo.replaceAll(")", "");

        // 내선번호인 경우 (최대 5자리) 번호 반환
        if (sPhoneNo.length() < 6) {
            if (pnType == 3) { // 번호
                return sPhoneNo;
            } else { // 지역번호 / 국번
                return "";
            }
        }

        String sTwoDigit = sPhoneNo.substring(0, 2);
        String sThreeDigit = sPhoneNo.substring(0, 3);
        String sResult = "";

        // 서울
        if ("02".equals(sTwoDigit)) {
            if (pnType == 1) { // 지역번호
                sResult = "02";
            } else { // 국번 / 번호
                sResult = parsePhoneNo2(sPhoneNo.substring(2), pnType);
            }
        }
        // 서울 외 전국 / 핸드폰
        else if ("010".equals(sThreeDigit) || "011".equals(sThreeDigit) ||"016".equals(sThreeDigit)
                || "017".equals(sThreeDigit) || "018".equals(sThreeDigit) || "019".equals(sThreeDigit)
                || "070".equals(sThreeDigit) || "080".equals(sThreeDigit)
                || "031".equals(sThreeDigit) || "032".equals(sThreeDigit) || "033".equals(sThreeDigit)
                || "041".equals(sThreeDigit) || "042".equals(sThreeDigit) || "043".equals(sThreeDigit) || "044".equals(sThreeDigit)
                || "051".equals(sThreeDigit) || "052".equals(sThreeDigit) || "053".equals(sThreeDigit) || "054".equals(sThreeDigit) || "055".equals(sThreeDigit)
                || "061".equals(sThreeDigit) || "062".equals(sThreeDigit) || "063".equals(sThreeDigit) || "064".equals(sThreeDigit)) {
            if (pnType == 1) { // 지역번호
                sResult = sPhoneNo.substring(0, 3);
            } else { // 국번 / 번호
                sResult = parsePhoneNo2(sPhoneNo.substring(3), pnType);
            }
        }
        // 지역번호를 찾을 수 없는 경우
        else {
            if (pnType == 3) { // 번호
                sResult = sPhoneNo;
            } else { // 지역번호 / 국번
                sResult = "";
            }
        }

        return sResult;
    }

    /**
     * 국번, 번호가 합쳐진 전화번호를 parsing한다.
     *
     * @param psPhoneNo
     *            전화번호
     * @param pnType
     *            국번: 2, 번호: 3
     * @return pnType에 해당하는 번호
     */
    private static String parsePhoneNo2(String psPhoneNo, int pnType) {
        String sFirstDigit = "";
        String sSecondDigit = "";

        switch (psPhoneNo.length()) {
            case 8 : // 1234-5678
                sFirstDigit = psPhoneNo.substring(0, 4);
                sSecondDigit = psPhoneNo.substring(4);
                break;
            case 7 : // 123-4567
                sFirstDigit = psPhoneNo.substring(0, 3);
                sSecondDigit = psPhoneNo.substring(3);
                break;
            case 6 : // 12-4567
                sFirstDigit = psPhoneNo.substring(0, 2);
                sSecondDigit = psPhoneNo.substring(2);
                break;
            case 5 : // 1-4567
                sFirstDigit = psPhoneNo.substring(0, 1);
                sSecondDigit = psPhoneNo.substring(1);
                break;
            default :
                sSecondDigit = psPhoneNo;
                break;
        }

        if (pnType == 2) { // 국번
            return sFirstDigit;
        } else { // 번호
            return sSecondDigit;
        }
    }

    /**
     * 전화번호를 "-"가 포함된 문자열로 반환한다.
     *
     * @param psPhoneNo
     *            전화번호
     * @return "-"가 포함된 문자열
     */
    public static String convertPhoneString(String psPhoneNo) {
        String sFirstDigit = parsePhoneNo(psPhoneNo, 1); // 지역번호
        String sSecondDigit = parsePhoneNo(psPhoneNo, 2); // 국번
        String sThirdDigit = parsePhoneNo(psPhoneNo, 3); // 번호

        // 문자열 조합
        String sResult = sFirstDigit;
        if (!"".equals(sSecondDigit)) {
            if (!"".equals(sResult)) {
                sResult += "-";
            }
            sResult += sSecondDigit;
        }
        if (!"".equals(sThirdDigit)) {
            if (!"".equals(sResult)) {
                sResult += "-";
            }
            sResult += sThirdDigit;
        }

        return sResult;
    }

    /**
     * 숫자를 세자리 마다 ","가 포함된 문자열로 반환한다.
     *
     * @param pnNum
     *            숫자
     * @return ","가 포함된 문자열
     */
    public static String convertNumString(Integer pnNum) {
        String nResult = "0";
        if (pnNum == null) {
            return nResult;
        }
        nResult = (new DecimalFormat("#,###")).format(pnNum);
        return nResult;
    }

    /**
     * 숫자를 세자리 마다 ","가 포함된 문자열로 반환한다.
     *
     * @param psNum
     *            숫자
     * @return ","가 포함된 문자열
     */
    public static String convertNumString(String psNum) {
        if (psNum == null) {
            return "0";
        }
        int nMinus = (psNum.indexOf('-') == 0) ? -1 : 1;
        psNum = extractNumeral(psNum);
        if ("".equals(psNum)) {
            return "0";
        } else {
            return convertNumString(Integer.parseInt(psNum) * nMinus);
        }
    }

    public static String getCommandName(Object obj) {
        String m_szCommandName = obj.getClass().getName();

        if (m_szCommandName != null) {
            int i = m_szCommandName.lastIndexOf(".");

            if (i > 0) {
                m_szCommandName = m_szCommandName.substring(i + 1, m_szCommandName.length());
            }
        }
        return m_szCommandName;
    }

    public static String getErrorPage(String errCode) {
        String rtn = "error/errorpage_access1";

        if ("9998".equals(errCode)) {
            rtn = "error/errorpage_access2";
        } else if ("9997".equals(errCode)) {
            rtn = "error/errorpage_expired";
        }

        return rtn;
    }

    public static String getDateTime() {
        return getDateTime("yyyy년 MM월 dd일 EEE요일 aa hh시 mm분 ss초");
    }

    public static String getDateTime(String format) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat date = new SimpleDateFormat(format);

        return date.format(cal.getTime());
    }

    public static String getHrMinSec(String input) {
        float second = Float.parseFloat(input);
        int cday = 86400;	//하루
        int chr = 3600; 	//한시간
        int cmin = 60; 		//1분

        int hr = (int) (second % cday / chr);	//시간
        int min = (int) (second % cday % chr / cmin); 	//분
        int sec = (int) (second % cday % chr % cmin);	//초

        return lfillstr(2, "0", Integer.toString(hr)) + ":" + lfillstr(2, "0", Integer.toString(min)) + ":" + lfillstr(2, "0", Integer.toString(sec));
    }

    /*
     *  자신의 센터에 맞는 코드값을 가져온다.
     */
    public static Hashtable getCodeTable(HttpSession session, PageContext pageContext) {
        Hashtable rtnVal = new Hashtable();
        UserSession userSession = (UserSession)SessionUtils.getLoginUser();
        Hashtable codeTable = (Hashtable) pageContext.getServletContext().getAttribute("SYSTEMCODE");
        String centercd = userSession.getCentercd();
        rtnVal = (Hashtable) codeTable.get(centercd);
        return rtnVal;
    }

    /*
     *  자신의 센터에 맞는 코드값을 가져온다.
     */
    public static Hashtable getCodeTable(HttpSession session, WebApplicationContext webApplicationContext) {
        Hashtable rtnVal = new Hashtable();
        UserSession userSession = (UserSession)SessionUtils.getLoginUser();
        Hashtable codeTable = (Hashtable) webApplicationContext.getServletContext().getAttribute("SYSTEMCODE");
        String centercd = userSession.getCentercd();
        rtnVal = (Hashtable) codeTable.get(centercd);
        return rtnVal;
    }

    /**
     * Delimiter 로 구분된 하나의 Source String 을 Delimiter 단위로 분해하여 Array 타입의
     * Strings 결과를 Return한다. <BR>
     *
     * <PRE>
     *
     * @param szSource:
     *                source string
     * @param szDelimiter:
     *                delimiter 로 사용할 string
     * @param bIncludeNullString:
     *                Null String도 Array의 element도 잡는다.
     *
     * 예) szSource: "|123|456||789|", szDelimiter: "|" -> Return: Vector
     * {"", "123", "456", "", "789", ""}
     *
     * </PRE>
     */
    public static String[] getTokenizedStringsWithDelimiter(String szSource,
        String szDelimiter, boolean bIncludeNullString) {
        if (szSource == null || szDelimiter == null) {
            throw new IllegalArgumentException();
        }

        if (szSource.length() == 0) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(szSource, szDelimiter, true);
        Vector<String> vctString = new Vector<String>();
        String szTemp = "";
        boolean bNextDelimiter = false;
        int nCount = 0;

        while (st.hasMoreTokens()) {
            szTemp = st.nextToken();

            if (szTemp.equals(szDelimiter)) {
            if (!bNextDelimiter) {
                if (bIncludeNullString) {
                vctString.addElement("");
                }
            }

            bNextDelimiter = false;
            } else {
            vctString.addElement(szTemp);
            bNextDelimiter = true;
            }
        }

        if (!bNextDelimiter) {
            vctString.addElement("");
        }

        nCount = vctString.size();
        String[] arrString = new String[nCount];

        for (int i = 0; i < nCount; i++) {
            arrString[i] = vctString.elementAt(i);
        }

        return arrString;
    }

    /**
     * item 정보를 가져온다
     *
     * @param items List
     * @return HashMap
     */
    public static HashMap<String, Serializable> getItems(List items){
        HashMap<String, Serializable> param = new HashMap<String, Serializable>();
        HashMap filter = new HashMap();
        Iterator iter = items.iterator();
        ArrayList<String> filenmList = new ArrayList<String>();

        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            String fileName = "";

            if (item.isFormField()) {
                // 일반 필드인 경우 paramMap에 데이터 입력
                param.put(item.getFieldName().toString(), item.getString());
            }
            else {
                if ("".equals(item.getName())) {
                    continue;
                }
                // 파일 필드인 경우 파일리스트에 파일정보 추가
                fileName = item.getName();
                if (fileName.indexOf("\\") > 0) {
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                }
                filenmList.add(fileName);
                param.put(fileName, item.get());
            }
        }
        param.put("filenmList", filenmList);

        return param;
    }

    public static String byteTranslater(String value) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
        int intSize = 0;
        int kbyteSize = 1024;
        double doubleSize = 0;
        String returnSize = null;
        long size = Long.parseLong(value);

        if (size >= (1000 * 1024 * 1024)) {
            intSize = new Long(size / (1000 * 1024 * 1024)).intValue();
            return nf.format(intSize) + "GB";
        } else if (size > (kbyteSize * 1024)) {
            intSize = (int) (((double) size) / ((double) (kbyteSize * 1024)) * 100);
            doubleSize = (double) (((double) intSize) / 100);
            returnSize	= df.format(doubleSize);

            if (returnSize.lastIndexOf(".") != -1) {
                if ("00".equals(returnSize.substring(returnSize.length() - 2, returnSize.length()))) {
                    returnSize = returnSize.substring(0, returnSize.lastIndexOf("."));
                }
            }
            return returnSize + "MB";
        } else if (size > kbyteSize) {
            intSize = new Long(size / kbyteSize).intValue();
            return nf.format(intSize) + "KB";
        } else {
            return nf.format(size) + "Byte";
//    		return "1KB";
        }
    }

    public static String stripTag(String html) {
        String text = "";

        if (html != null) {
            text = html;

            text = text.replaceAll("/(<br>)|(<br />)|(<BR>)|(<BR />)/gi", "\n");
            text = text.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        }
        return text;
    }

    /**
     * 태그 변환
     *
     * @param 변환할 문자
     * @return 변환된 문자
     */
    public static String replaceTag(String value) {
        if (value == null) {
            value = "";
        } else {
            value = value.replaceAll("<", "&lt;");
            value = value.replaceAll(">", "&gt;");
            value = value.replaceAll("\"", "&quot;");
            value = value.replaceAll("'", "&apos;");
            value = value.replaceAll("#", "&#35;");
            value = value.replaceAll("%", "&#37;");
            value = value.replaceAll("&", "&#38;");
            value = value.replaceAll("(", "&#40;");
            value = value.replaceAll(")", "&#41;");
            value = value.replaceAll("+", "&#43;");
            value = value.replaceAll(";", "&#59;");
        }
        return value;
    }

    /**
     * 문자열 Byte 길이를 반환. 한글3바이트로 계산
     *
     * @param String
     * @return 문자열 길이
     */
    public static int getByteLength(String str) {
        int len = 0;

        for (int rSize = 0; rSize < str.length(); rSize++ ) {
            if (str.charAt(rSize) > 0x007F) {
                len += 3;
            } else {
                len++;
            }
        }
        return len;
    }

    /**
     * 문자열을 바이트수대로 잘라 배열로 반환
     *
     * @param 문자열, 길이
     * @return 문자열 배열
     */
    public static ArrayList<String> splitByteString(String str, int length) {
        int startIdx = 0;
        int len = 0;
        int rSize = 0;
        ArrayList<String> arrString = new ArrayList<String>();

        for (; rSize < str.length(); rSize++ ) {
            if (str.charAt(rSize) > 0x007F) {
                len += 3;
            } else {
                len++;
            }

            if (len > length) {
                arrString.add(str.substring(startIdx, rSize).trim());
                startIdx = rSize;
                len = 0;
            }
        }
        arrString.add(str.substring(startIdx, rSize).trim());

        return arrString;
    }

    /**
     * 문자열중에 숫자만 추출하기
     *
     * @param String
     * @return 숫자문자열.
     */
    public static String extractNumeral(String str) {
        String numeral = "";

        if (str == null) {
            numeral = null;
        } else {
            String patternStr = "\\d"; // 숫자를 패턴으로 저장
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(str);
            while(matcher.find()){
                numeral += matcher.group(0);
            }
        }
        return numeral;
    }

    /**
     * 원하는 테이블에 해당하는 커스텀컬럼프로퍼티들을 가져오는 함수
     *
     * @param 센터코드, 원하는테이블명, custompropList(CUSTOMPROP in servletContext)
     * @return ArrayList.
     */
    public static List<Map<String, Object>> getOriginTableList(String centercd, String tableName, List<Map<String, Object>> custompropList) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> tmpProp: custompropList) {
            String tempcentercd = (String)tmpProp.get("centercd");
            String temptablename = (String)tmpProp.get("origintable");

            if (centercd.equals(tempcentercd) && tableName.equals(temptablename)) returnList.add(tmpProp);
        }
        return returnList;
    }

    /**
     * 원하는 테이블에 해당하는 커스텀컬럼 아이디를 가져오는 함수
     *
     * @param 센터코드, 원하는테이블명, 컬럼명, custompropList(CUSTOMPROP in servletContext)
     * @return szProdid.
     */
    public static String getOriginTablePropid(String centercd, String tableName, String columnid, List<Map<String, Object>> custompropList) {
        String szProdid = "";
        for(Map<String, Object> tmpProp: custompropList) {
            String tempcentercd = (String)tmpProp.get("centercd");
            String temptablename = (String)tmpProp.get("origintable");
            String tempcolumnid = (String)tmpProp.get("columnid");

            if (centercd.equals(tempcentercd) && tableName.equals(temptablename) && columnid.equals(tempcolumnid)) {
                szProdid = (String)tmpProp.get("custompropid");
                break;
            }
        }
        return szProdid;
    }

    /**
     * 원하는 테이블에 대상테이블을 아이디를 가져오는 함수
     *
     * @param 센터코드, 원하는테이블명, 컬럼명, custompropList(CUSTOMPROP in servletContext)
     * @return szTargettable.
     */
    public static String getOriginTableTargetTable(String centercd, String tableName, String columnid, List<Map<String, Object>> custompropList) {
        String szTargettable = "";
        for(Map<String, Object> tmpProp: custompropList) {
            String tempcentercd = (String)tmpProp.get("centercd");
            String temptablename = (String)tmpProp.get("origintable");
            String tempcolumnid = (String)tmpProp.get("columnid");

            if (centercd.equals(tempcentercd) && tableName.equals(temptablename) && columnid.equals(tempcolumnid)) {
                szTargettable = (String)tmpProp.get("targettable");
                break;
            }
        }
        return szTargettable;
    }


    @SuppressWarnings("unchecked")
    private static String printLog(Object obj, int depth) throws Exception {
        String result = "";
        String space = "#[LOG] ";

        for (int i=0; i<depth; i++) {
            space += "  ";
        }

        if (obj instanceof JSONArray) {
            JSONArray tempJSONArray = (JSONArray)obj;
            for (int i=0; i<tempJSONArray.length(); i++) {
                result = result + "\r\n" + space + "JSONArray[" + i + "] :" + printLog(tempJSONArray.get(i), depth+1);
            }

        } else if (obj instanceof List) {
            List<Object> tempList = (List<Object>)obj;
            int i =0;
            for (Object temp : tempList) {
                result = result + "\r\n" +space + "List[" + (i++) + "] :" + printLog(temp, depth+1);
            }

        } else if (obj instanceof JSONObject) {
            JSONObject tempJSONObject = (JSONObject)obj;
            Iterator<String> keyData = tempJSONObject.keys();

            while (keyData.hasNext()) {
                String key = (String)keyData.next();
                Object value = tempJSONObject.get(key);

                result = result + "\r\n" +space + key + " : " + printLog(value, depth+1);
            }

        } else if (obj instanceof Map) {
            Map<String, Object> tempMap = (Map<String, Object>)obj;
            Set<String> keyData = tempMap.keySet();

            for (String key : keyData) {
                Object value = tempMap.get(key);
                result = result + "\r\n" +space + key + " : " + printLog(value, depth+1);
            }
        } else {
            result = (obj == null ? "" : obj.toString());
        }

        return result;
    }

    public static void printLog(Object obj) throws Exception {
        System.out.println(printLog(obj, 0));
    }

    public static String fillEmpVal(int len, String data, String side, String empVal)
    {
        String result = data;
        String tempStr = "";
        int dataLen = data.length();
        int gap = len - dataLen;
        if(gap < 0)
        {
            result = data.substring(0, len);
        } else
        {
            for(int i = 0; gap > i; i++)
                tempStr = (new StringBuilder(String.valueOf(tempStr))).append(empVal).toString();

            if(side.equals("L"))
                result = (new StringBuilder(String.valueOf(data))).append(tempStr).toString();
            else
            if(side.equals("R"))
                result = (new StringBuilder(String.valueOf(tempStr))).append(data).toString();
        }
        return result;
    }

    public static String transByteForBankFillIn(String fullStr)
    {
        String result = "";
        try
        {
            HashMap strMap = new HashMap();
            strMap.put(Character.valueOf('0'), "０");
            strMap.put(Character.valueOf('1'), "１");
            strMap.put(Character.valueOf('2'), "２");
            strMap.put(Character.valueOf('3'), "３");
            strMap.put(Character.valueOf('4'), "４");
            strMap.put(Character.valueOf('5'), "５");
            strMap.put(Character.valueOf('6'), "６");
            strMap.put(Character.valueOf('7'), "７");
            strMap.put(Character.valueOf('8'), "８");
            strMap.put(Character.valueOf('9'), "９");
            strMap.put(Character.valueOf('a'), "ａ");
            strMap.put(Character.valueOf('b'), "ｂ");
            strMap.put(Character.valueOf('c'), "ｃ");
            strMap.put(Character.valueOf('d'), "ｄ");
            strMap.put(Character.valueOf('e'), "ｅ");
            strMap.put(Character.valueOf('f'), "ｆ");
            strMap.put(Character.valueOf('g'), "ｇ");
            strMap.put(Character.valueOf('h'), "ｈ");
            strMap.put(Character.valueOf('i'), "ｉ");
            strMap.put(Character.valueOf('j'), "ｊ");
            strMap.put(Character.valueOf('k'), "ｋ");
            strMap.put(Character.valueOf('l'), "ｌ");
            strMap.put(Character.valueOf('m'), "ｍ");
            strMap.put(Character.valueOf('n'), "ｎ");
            strMap.put(Character.valueOf('o'), "ｏ");
            strMap.put(Character.valueOf('p'), "ｐ");
            strMap.put(Character.valueOf('q'), "ｑ");
            strMap.put(Character.valueOf('r'), "ｒ");
            strMap.put(Character.valueOf('s'), "ｓ");
            strMap.put(Character.valueOf('t'), "ｔ");
            strMap.put(Character.valueOf('u'), "ｕ");
            strMap.put(Character.valueOf('v'), "ｖ");
            strMap.put(Character.valueOf('w'), "ｗ");
            strMap.put(Character.valueOf('x'), "ｘ");
            strMap.put(Character.valueOf('y'), "ｙ");
            strMap.put(Character.valueOf('z'), "ｚ");
            strMap.put(Character.valueOf('A'), "Ａ");
            strMap.put(Character.valueOf('B'), "Ｂ");
            strMap.put(Character.valueOf('C'), "Ｃ");
            strMap.put(Character.valueOf('D'), "Ｄ");
            strMap.put(Character.valueOf('E'), "Ｅ");
            strMap.put(Character.valueOf('F'), "Ｆ");
            strMap.put(Character.valueOf('G'), "Ｇ");
            strMap.put(Character.valueOf('H'), "Ｈ");
            strMap.put(Character.valueOf('I'), "Ｉ");
            strMap.put(Character.valueOf('J'), "Ｊ");
            strMap.put(Character.valueOf('K'), "Ｋ");
            strMap.put(Character.valueOf('L'), "Ｌ");
            strMap.put(Character.valueOf('M'), "Ｍ");
            strMap.put(Character.valueOf('N'), "Ｎ");
            strMap.put(Character.valueOf('O'), "Ｏ");
            strMap.put(Character.valueOf('P'), "Ｐ");
            strMap.put(Character.valueOf('Q'), "Ｑ");
            strMap.put(Character.valueOf('R'), "Ｒ");
            strMap.put(Character.valueOf('S'), "Ｓ");
            strMap.put(Character.valueOf('T'), "Ｔ");
            strMap.put(Character.valueOf('U'), "Ｕ");
            strMap.put(Character.valueOf('V'), "Ｖ");
            strMap.put(Character.valueOf('W'), "Ｗ");
            strMap.put(Character.valueOf('X'), "Ｘ");
            strMap.put(Character.valueOf('Y'), "Ｙ");
            strMap.put(Character.valueOf('Z'), "Ｚ");
            for(int i = 0; fullStr.length() > i; i++)
            {
                char tempStr = fullStr.charAt(i);
                if(strMap.containsKey(Character.valueOf(tempStr)))
                    result = (new StringBuilder(String.valueOf(result))).append((String)strMap.get(Character.valueOf(tempStr))).toString();
                else
                    result = (new StringBuilder(String.valueOf(result))).append(String.valueOf(tempStr)).toString();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 문자열 패딩 함수 - oracle lpad와 동일
     *
     * @param str
     *            대상 문자열
     * @param len
     *            패딩 기준 문자열 길이
     * @param append
     *            채울 문자
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */
    public static String lpad(String str, int len, String append)
    {
        if (null == str) {
            str = "";
        }
        int nCnt = len - str.length();

        if (nCnt <= 0) {
            return  str.substring(0, len);
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nCnt; i++){
            sb.append(append);
        }

        return sb.toString() + str;
    }

    /**
     * 문자열 패딩 함수 - oracle lpad와 동일
     *
     * @param str
     *            대상 문자열
     * @param len
     *            패딩 기준 문자열 길이
     * @param append
     *            채울 문자
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */
    public static String rpad(String str, int len, String append)
    {
        if (null == str) {
            str = "";
        }
        int nCnt = len - str.length();

        if (nCnt <= 0) {
            return  str.substring(0, len);
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nCnt; i++){
            sb.append(append);
        }

        return str + sb.toString();
    }

    /**
     * 문자열 패딩 함수 - oracle lpad와 동일
     *
     * @param str
     *            대상 문자열
     * @param len
     *            패딩 기준 문자열 길이
     * @param append
     *            채울 문자
     * @return 대상 문자열이 널이면 "", 널이 아니면 원래 문자열을 반환한다.
     */
    public static String nvls(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }
    
    
    /**
     * NULL 제거
     * 입력값이 NULL인 경우 ""를, 입력값이 NULL이 아닌 경우는 입력값을 그대로 리턴한다.
     * @param value 입력값은 toString() 가능한 Object형이어야 한다.
     * @return String
     */
    	public static String nvl(Object value) {
            	return nvl(value, "");
    	}
    	
    /**
     * NULL 제거
     * 입력값이 NULL인 경우 str를, 입력값이 NULL이 아닌 경우는 입력값을 그대로 리턴한다.
     * @param value 입력값은 toString() 가능한 Object형이어야 한다.
     * @param str 입력값이 NULL인 경우 리턴 할 기본값
     * @return String
     */
    	public static String nvl(Object value, String str) {
//    	    System.out.println("Object : " + value);

    	    if( isNull(value) ) {
                    	return str;
    	        } else {
            	        return value.toString();
    	        }
    	}
    	
    	/**
    	* Null체크한다.
    	* @param value 문자열
    	* @return boolean 결과값
    	*/
        public static boolean isNull(String value){
            boolean rtn_value = false;
            
//          System.out.println("isNull(String value) isNull : " + value);
            
            try{
                if ( value == null || value.equals("")  ) {
                    rtn_value = true; 
                }          
            }catch(NullPointerException ne){
                rtn_value = true;    
            }catch(Exception e){
                e.printStackTrace();    
            }
            return rtn_value;
        } 
        
    	/**
    	* Null 체크한다.
    	* @param value 문자열
    	* @return boolean 결과값
    	*/
        public static boolean isNull(Object value){
            boolean rtn_value = false;
            
     //       System.out.println("isNull(Object value)  : " + value);
            
            try{
                if(value == null || value.equals("") ){
                    rtn_value = true; 
                }          
            }catch(NullPointerException ne){
                rtn_value = true;    
            }catch(Exception e){
                e.printStackTrace();    
            }

            return rtn_value;
        }
        
    /**
     * 텍스트 파일 쓰기
     *
     * @param sOutFile
     *            파일명
     * @param sConetnt
     *            내용
     * @param sCharset
     *            캐릭터셋
     * @return
     */
    public static void writeTextFile(String sOutFile, String sConetnt, String sCharset) {
        try {

            File file = new File(sOutFile);
            File file2 = new File(file.getAbsolutePath());
            File file3 = new File(file2.getParent());
            if ( !file3.exists() ) {
                file3.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(sOutFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, sCharset);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(sConetnt);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * UTF-8 => EUC-KR 변환시 문자열 길이
     *
     * @param str
     *            한글문자열
     * @param nBytes
     *            바이트수
     * @return 요청전문 - 변경된 Transaction code를 포함한 전문
     */
    public static String utf8ToEuckr(String str, int nBytes) throws Exception {

        int uLen = str.getBytes("utf-8").length;
        int kLen = str.getBytes("euc-kr").length;
        int nDiff = uLen - kLen;

        str = CommonUtils.rpad(str, nBytes+nDiff, " ")		; // 성명

        if (nDiff > 0) {
            str = new String(str.getBytes(), 0, nBytes + nDiff);
        }

        System.out.println("uLen : " + uLen);
        System.out.println("kLen : " + kLen);
        System.out.println("nDiff : " + nDiff);

        return str;
    }

    /**
     * 전각문자 만들기
     *
     * @param str
     *            한글문자열
     * @return 전각문자열
     */
    private static String toFullChar(String src)
    {
        if (src == null)    return null;
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        for (int i = 0; i < src.length(); i++)
        {
            c = src.charAt(i);
            // 영문 알파벳 이거나 특수 문자
            if (c >= 0x21 && c <= 0x7e)   c += 0xfee0;
            // 공백
            else if (c == 0x20)   c = 0x3000;
            strBuf.append(c);
        }
        return strBuf.toString();
    }

    /**
     * 반각문자 만들기
     *
     * @param str
     *            한글문자열
     * @return 전각문자열
     */
    private static String toHalfChar(String src)
    {
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        for (int i = 0; i < src.length(); i++)
        {
            c = src.charAt(i);
            //영문이거나 특수 문자 일경우.
            if (c >= '！' && c <= '～')   c -= 0xfee0;
            // 공백
            else if (c == '　')   c = 0x20;
            strBuf.append(c);
        }
        return strBuf.toString();
    }

    /**
     * 문자열 우측 패딩(한글 2바이트 계산)
     *
     * @param str
     *            문자열
     * @param n
     *            문자열 길이
     * @param ch
     *            문자
     *
     * @return 문자열
     */
    public static String filler_blank_right(String str, int n, String ch) {
        String str_s = str.trim();
        int sLength = getByteSizeToComplex(str_s);
        for (int i = 0; i < str_s.length(); i++) {
        }
        int in = 0;
        if (!str_s.equals("")) {
            in = n - sLength;
        } else {
            in = n;
        }

        for (int i = 0; i < in; i++) {
            str_s = str_s + ch;
        }
        return str_s;
    }

    /**
     * 문자열 좌측 패딩(한글 2바이트 계산)
     *
     * @param str
     *            문자열
     * @param n
     *            문자열 길이
     * @param ch
     *            문자
     *
     * @return 문자열
     */
    public static String filler_blank_left(String str, int n, String ch) {
        String str_s = str.trim();
        int sLength = getByteSizeToComplex(str_s);
        for (int i = 0; i < str_s.length(); i++) {
        }
        int in = 0;
        if (!str_s.equals("")) {
            in = n - sLength;
        } else {
            in = n;
        }

        for (int i = 0; i < in; i++) {
            str_s = ch + str_s;
        }
        return str_s;
    }    

    public static final int getByteSizeToComplex(String str) {
        int en = 0;
        int ko = 0;
        int etc = 0;
        char ch = '-';
        int iCh = 0;

        char[] string = str.toCharArray();

        for (int j = 0; j < string.length; j++) {
            if (string[j] >= 'A' && string[j] <= 'z') {
                en++;
            } else if (string[j] >= '\uAC00' && string[j] <= '\uD7A3') {
                ko++;
                ko++;
            } else {
                ch = string[j];
                iCh = ch;
                Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);

                if( UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock) ||
                        UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(unicodeBlock) ||
                        UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B.equals(unicodeBlock) ||
                        UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(unicodeBlock) ||
                        UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT.equals(unicodeBlock) ||
                        UnicodeBlock.HIRAGANA.equals(unicodeBlock) ||
                        UnicodeBlock.KATAKANA.equals(unicodeBlock) ||
                        UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS.equals(unicodeBlock) ||
                        UnicodeBlock.NUMBER_FORMS.equals(unicodeBlock)
                        )
                {
                    etc++;
                    etc++;
                }
                else if (iCh > 127) {
                    etc++;
                    etc++;
                }
                else {
                    etc++;
                }

            }
        }
        return (en + ko + etc);
    }
    
    static String convertEnc(String str, String encoding) throws IOException {
		ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
		requestOutputStream.write(str.getBytes(encoding));
		return requestOutputStream.toString(encoding);
	}

    /**
     * String.valueOf 메소드 대용
     *
     * @param object
     *            Object
     * @return 문자열
     */
    public static String stringValueOf(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    /**
     * 유일한 파일명 반환
     *
     * @param object
     *            Object
     * @return 문자열
     */
    public static String getUniqFileName(String origFileName) {
        String[] sArr 	= null;
        String sExt 	= "";

        if ( null != origFileName && !"".equals(origFileName) && origFileName.indexOf(".")>= 0 ) {
            sArr = origFileName.split("\\.");
            sExt = sArr[sArr.length - 1];
        }

        String uuid = UUID.randomUUID().toString().replace("-", "") + "." + sExt;
        return uuid;
    }

    /**
     * 비밀번호 암호화
     *  - encom
     * @param object
     *            Object
     * @return 문자열
     */
    public static String SHA256(String str) {
    	String SHA = "";
    	try {
    		MessageDigest sh = MessageDigest.getInstance("SHA-256");
    		sh.update(str.getBytes());
    		byte[] byteData = sh.digest();
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < byteData.length; i++) {
    			sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
    		}
    		SHA = sb.toString();

    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    		SHA = null;
    	}
    	return SHA;
    }

    /**
     * 문자열이 숫자형식인지 체크
     *  - encom
     * @param object
     *            Object
     * @return boolean
     */    
    public static boolean isNumeric(String str) {
    	Pattern pattern = Pattern.compile("[+-]?\\d+");
    	return pattern.matcher(str).matches();
    }

    /**
     * 문자열이 날짜 형식인지 체크
     *  - encom
     * @param object
     *            Object
     * @return boolean
     */    
    public static boolean dateValChk(String yyyyMM)
    {
    	boolean result = true;

    	if (yyyyMM.length() != 14) {
    		result = false;
    		return result;
    	}

    	if (!CommonUtils.isNumeric(yyyyMM)) {
    		result = false;
    		return result;
    	}

    	int month = Integer.parseInt(yyyyMM.substring(4, 6));
    	if ((month <= 0) || (month > 12)) {
    		result = false;
    		return result;
    	}
    	int day = Integer.parseInt(yyyyMM.substring(6, 8));
    	if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
    		if ((day <= 0) || (day > 31)) {
    			result = false;
    			return result;
    		}
    	} 
    	else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
    		if ((day <= 0) || (day > 30)) {
    			result = false;
    			return result;
    		}
    	} 
    	else if ((month == 2) && ((day <= 0) || (day > 29))) {
    		result = false;
    		return result;
    	}

    	int hour = Integer.parseInt(yyyyMM.substring(8, 10));
    	if ((hour < 0) || (hour > 23)) {
    		result = false;
    		return result;
    	}

    	int minute = Integer.parseInt(yyyyMM.substring(10, 12));
    	if ((minute < 0) || (minute > 60)) {
    		result = false;
    		return result;
    	}

    	int second = Integer.parseInt(yyyyMM.substring(12, 14));
    	if ((second < 0) || (second >= 60)) {
    		result = false;
    		return result;
    	}

    	return result;
    }
}
