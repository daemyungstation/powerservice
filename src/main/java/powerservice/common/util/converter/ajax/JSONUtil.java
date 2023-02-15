/**
 * @(#)JSONUtil.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.common.util.converter.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON 객체를 처리하는데 필요한 기능을 모아둔 유틸성 객체
 *
 * @author 이창희
 * @date 2011/08/5
 *
 */
public class JSONUtil {

    /**
     * ArrayList데이터를 JSONObject형태로 변환하여 리턴한다. <br>
     * Properties로 구성된 ArrayList만 정상 작동
     *
     * @param source
     * @return
     * @throws JSONException
     */
    public static JSONArray convertArrayList(ArrayList<Properties> source) throws JSONException {
        Properties tempProp;
        JSONArray returnArray = new JSONArray();
        Object tempKey;
        String tempValue;

        if (source != null) {
            for (int idx = 0, size = source.size(); idx < size; idx++) {
                tempProp = source.get(idx);
                Iterator<Object> keyIter = tempProp.keySet().iterator();
                String rowData = "{";

                while (keyIter.hasNext()) {
                    tempKey = keyIter.next();

                    if (!rowData.equals("{")) {
                        rowData += ",";
                    }

                    if(tempProp.get(tempKey) != null) {
                        tempValue = tempProp.get(tempKey).toString();
                        // 만약 쌍따옴표가 있다면 제거 함.
                        if (tempValue.indexOf("'") >= 0) {
                            tempValue = tempValue.replaceAll("'", "&uml");
                        }
                        if (tempValue.indexOf("\"") >= 0) {
                            tempValue = tempValue.replaceAll("\"", "&uml");
                        }
                        if (tempValue.indexOf("\r\n") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\r\n", "%u000a");
                        }
                        if (tempValue.indexOf("\n") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\n", "%u000a");
                        }
                        if (tempValue.indexOf("\r") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\r", "%u000a");
                        }

                    } else {
                        tempValue = "";
                    }
                    // tempKey가 자바스크립트 예약어일 경우 앞에 (-) 추가한다.
                    if(tempKey.equals("break")) tempKey = "_"+tempKey;
                    rowData += "\"" + tempKey + "\":\"" + tempValue + "\"";
                }
                returnArray.put(new JSONObject(rowData + "}"));
            }
        }
        return returnArray;
    }

    /**
     * Properties 데이터를 JSONObject형태로 변환하여 리턴한다. <br>
     * queryForObject로 하나의 레코드를 가져온 경우 사용(arraylist에서 for loop만 제거)
     *
     * @param source
     * @return
     * @throws JSONException
     */
    public static JSONArray convertObject(Properties source) throws JSONException {
        Properties tempProp;
        JSONArray returnArray = new JSONArray();
        Object tempKey;
        String tempValue;

        tempProp = (Properties) source;

        if (tempProp != null) {
            Iterator<Object> keyIter = tempProp.keySet().iterator();
            String rowData = "{";

            while (keyIter.hasNext()) {
                tempKey = keyIter.next();

                if (!rowData.equals("{")) {
                    rowData += ",";
                }

                if(tempProp.get(tempKey) != null) {
                    tempValue = tempProp.get(tempKey).toString();
                    // 만약 쌍따옴표가 있다면 제거 함.
                    if (tempValue.indexOf("'") >= 0) {
                        tempValue = tempValue.replaceAll("'", "&uml");
                    }
                    if (tempValue.indexOf("\"") >= 0) {
                        tempValue = tempValue.replaceAll("\"", "&uml");
                    }
                    if (tempValue.indexOf("\r\n") >= 0) {
                        // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                        tempValue = tempValue.replaceAll("\r\n", "%u000a");
                    }
                    if (tempValue.indexOf("\n") >= 0) {
                        // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                        tempValue = tempValue.replaceAll("\n", "%u000a");
                    }
                    if (tempValue.indexOf("\r") >= 0) {
                        // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                        tempValue = tempValue.replaceAll("\r", "%u000a");
                    }

                } else {
                    tempValue = "";
                }
                // tempKey가 자바스크립트 예약어일 경우 앞에 (-) 추가한다.
                if(tempKey.equals("break")) tempKey = "_"+tempKey;
                rowData += "\"" + tempKey + "\":\"" + tempValue + "\"";
            }
            returnArray.put(new JSONObject(rowData + "}"));
        }
        return returnArray;
    }

    /**
     * ArrayList데이터를 JSONObject형태로 변환하여 리턴한다. <br>
     * Properties로 구성된 ArrayList만 정상 작동
     *
     * TreeView(jQuery Plugin), http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
     * 2011.5.25 by jdjung.
     *
     * @param source
     * @return
     * @throws JSONException
     */
    public static JSONArray convertArrayListforTreeView(ArrayList<?> source) throws JSONException {
        Properties tempProp;
        JSONArray returnArray = new JSONArray();
        Object tempKey;
        String tempValue;

        if (source != null) {
            for (int idx = 0, size = source.size(); idx < size; idx++) {
                tempProp = (Properties) source.get(idx);
                Iterator<?> keyIter = tempProp.keySet().iterator();
                String rowData = "{";
                while (keyIter.hasNext()) {
                    tempKey = keyIter.next();

                    if (!rowData.equals("{")) {
                        rowData += ",";
                    }

                    if(tempProp.get(tempKey) != null) {
                        tempValue = tempProp.get(tempKey).toString();
                        // 만약 쌍따옴표가 있다면 제거 함.
                        if (tempValue.indexOf("\"") >= 0) {
                            tempValue = tempValue.replaceAll("\"", "&uml");
                        }
                        if (tempValue.indexOf("\r\n") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\r\n", "%u000a");
                        }
                        if (tempValue.indexOf("\n") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\n", "%u000a");
                        }
                        if (tempValue.indexOf("\r") >= 0) {
                            // 만약 개행문자를 포함하고 있다면 유니코드로 변환해준다.
                            tempValue = tempValue.replaceAll("\r", "%u000a");
                        }

                    } else {
                        tempValue = "";
                    }
                    // tempKey가 자바스크립트 예약어일 경우 앞에 (-) 추가한다.
                    if(tempKey.equals("break")) tempKey = "_"+tempKey;

                    // tempKey 값 변환(컬럼명에 따른 값 재설정
                    if ((tempKey.toString().equalsIgnoreCase("cseltypecd"))
                            || (tempKey.toString().equalsIgnoreCase("qaevaltypeid"))
                            || (tempKey.toString().equalsIgnoreCase("oucode"))
                            || (tempKey.toString().equalsIgnoreCase("prodtypeid"))) tempKey = "id";
                    if ((tempKey.toString().equalsIgnoreCase("cseltypenm"))
                            || (tempKey.toString().equalsIgnoreCase("qaevaltypenm"))
                            || (tempKey.toString().equalsIgnoreCase("ou"))
                            || (tempKey.toString().equalsIgnoreCase("prodtypenm"))) tempKey = "text";
                    if ((tempKey.toString().equalsIgnoreCase("nodecount")) && (!tempValue.equalsIgnoreCase("0"))) {
                        tempKey = "hasChildren";
                        tempValue = "true";
                    }

                    rowData += "\"" + tempKey + "\":\"" + tempValue + "\"";
                }
                returnArray.put(new JSONObject(rowData + "}"));
            }
        }
        return returnArray;
    }

    /**
     * Json형태의 String을 Map으로 변환해서 리턴한다.
     * 2015.01.02 by Yongjae, Jeong
     * @param string
     * @return Map<String, Object<
     * @throws JSONException
     */

    public static Map<String, Object> covertJsonToMap(String jsonStr) throws Exception {
       Map<String, Object> resultMap = new HashMap<String, Object>();

       if (jsonStr.equals(null) || jsonStr.equals("")) {
             resultMap.put("resultfg", false);
             resultMap.put("resultmsg", "RESULT결과값이 없습니다.");
       } else {
             Object result = null;

             try{
                    result = new JSONObject(jsonStr);
                    resultMap.put("result", JSONObjectToMap((JSONObject)result));
             } catch(Exception except) {
                    result = null;
             }

             if (result == null) {
                    try{
                           result = new JSONArray(jsonStr);
                           resultMap.put("result", JSONArrayToMap((JSONArray)result));

                    } catch(Exception except) {
                           result = null;
                    }
             }

            if (result != null) {
                resultMap.put("resultfg", true);
            } else {
                resultMap.put("resultfg", false);
                resultMap.put("resultmsg", "JSONObject 또는 JSONArray형태의 문장이 아닙니다.");
            }
       }
       return resultMap;
    }

    /**
     * JSONObject을 Map으로 변환해서 리턴한다.
     * 2015.01.02 by Yongjae, Jeong
     * @param JSONObject
     * @return Map<String, Object>
     * @throws JSONException
     */

    private static Map<String, Object> JSONObjectToMap(JSONObject jsonObject) throws Exception {
       Map<String, Object> resultMap = new HashMap<String, Object>();
       @SuppressWarnings("unchecked")
             Iterator<String> keyData = jsonObject.keys();

        while (keyData.hasNext()) {
            String key = (String)keyData.next();
            Object value = null;

            // JSONArray일 경우 JSONArrayToMap을 이용하여 파싱
            if (jsonObject.get(key) instanceof JSONArray) {
                value = JSONArrayToMap(jsonObject.getJSONArray(key));

            // JSONObject일 경우 JSONObjectToMap을 이용하여 파싱
            } else if (jsonObject.get(key) instanceof JSONObject) {
                value = JSONObjectToMap(jsonObject.getJSONObject(key));

            // String일 경우 값을 집어넣는다.
            } else if (jsonObject.get(key) instanceof String || jsonObject.get(key) instanceof Integer || jsonObject.get(key) instanceof Float || jsonObject.get(key) instanceof Long || jsonObject.get(key) instanceof Double){
                value = jsonObject.getString(key);

            // 이외의 경우는 continue
            } else {
                continue;
            }
            resultMap.put(key, value);
        }
       return resultMap;
    }



    /**
     * JSONArray을 Map으로 변환해서 리턴한다.
     * 2015.01.02 by Yongjae, Jeong
     * @param JSONArray
     * @return Map<String, Object>
     * @throws JSONException
     */
    private static List<Object> JSONArrayToMap(JSONArray jsonArray) throws Exception {

       List<Object> resultList = new ArrayList<Object>();

       for (int i=0; i<jsonArray.length(); i++) {
             Object value = null;

            // JSONArray일 경우 JSONArrayToMap을 이용하여 파싱
            if (jsonArray.get(i) instanceof JSONArray) {
                value = JSONArrayToMap(jsonArray.getJSONArray(i));

            // JSONObject일 경우 JSONObjectToMap을 이용하여 파싱
            } else if (jsonArray.get(i) instanceof JSONObject) {
                value = JSONObjectToMap(jsonArray.getJSONObject(i));

            // String일 경우 값을 집어넣는다.
            } else if (jsonArray.get(i) instanceof String || jsonArray.get(i) instanceof Integer || jsonArray.get(i) instanceof Float || jsonArray.get(i) instanceof Long || jsonArray.get(i) instanceof Double){
                value = jsonArray.getString(i);

            // 이외의 경우는 continue
            } else {
                continue;
            }
            resultList.add(value);
       }
       return resultList;
    }
}
