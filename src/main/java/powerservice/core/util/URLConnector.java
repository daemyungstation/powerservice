package powerservice.core.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * URLConnector
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2014/10/20
 *
 * URL 컨넥션을 연결하여 결과값을 가져온다.
 *
 * URL					: 연결 URL
 * PROXY_SERVER_URL 	: 프록시서버 URL
 * PROXY_SERVER_PORT 	: 프록시서버 PORT
 * cachefg				: 캐쉬 사용 유무(default : 사용안함)
 * method				: 전송방식(default : POST)
 * connTimeout			: 연결 타임아웃(default : 3초)
 * readTimeout			: 읽기 타임아웃(default : 7초)
 * contentType			: 컨텐츠 타입 (default : text/html; charset=UTF-8)
 * sendParam			: 송신할 파라미터 정보
 */
public class URLConnector {
    public static String getData(Map<String, Object> confMap) {
            StringBuffer result = new StringBuffer();
            StringBuffer sendParam = new StringBuffer();

            HttpURLConnection connection = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        int responsecode = -1;

        try {
                // 1. 서버 연결
                // Proxy instance, proxy ip:PROXY_SERVER_IP, port:PROXY_SERVER_PORT

            String apiURL = (String)confMap.get("URL");

            // 메소드(기본:POST)
            String method = StringUtils.defaultString((String)confMap.get("method")).equals("GET")? "GET" : "POST";

            // ContentType 설정
            String contentType = null;
            if (confMap.get("sendParam") != null && method.equals("POST")) {
                contentType = "application/x-www-form-urlencoded";
            } else {
                contentType = StringUtils.defaultString((String)confMap.get("contentType")).equals("")? "text/html; charset=UTF-8" : StringUtils.defaultString((String)confMap.get("contentType"));
            }

            // GET 방식 파라미터 전달
            if (confMap.get("sendParam") != null && method.equals("GET")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> sendParamMap = (Map<String, Object>)confMap.get("sendParam");
                Set<String> keySet = sendParamMap.keySet();
                for (Iterator<String> iterStr=keySet.iterator();iterStr.hasNext();) {
                    String keyName = iterStr.next();
                    String keyValue = (String)sendParamMap.get(keyName);

                    sendParam.append(keyName + "=" + keyValue).append("&");
                }

                if (sendParam.length() > 0) {
                    apiURL = apiURL + "?" + sendParam.toString();
                }
            }

            // System.out.println("##Connect URL : " + apiURL);

            if(confMap.get("PROXY_SERVER_IP") != null) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress((String)confMap.get("PROXY_SERVER_IP"), Integer.parseInt((String)confMap.get("PROXY_SERVER_PORT"))));
                connection = (HttpURLConnection)  (new URL(apiURL)).openConnection(proxy);
            } else {
                connection = (HttpURLConnection)  (new URL(apiURL)).openConnection();
            }
            connection.setDoInput(true);	// 수신 유무
            connection.setDoOutput(confMap.get("sendParam") != null && method.equals("POST"));	// 송신 유무
            connection.setUseCaches(StringUtils.defaultString((String)confMap.get("cachefg")).equals("Y"));	// 캐쉬사용 유무(기본:false)
            connection.setRequestMethod(method);
            // 타임아웃 시간(MS) - Connection
            connection.setConnectTimeout(StringUtils.defaultString((String)confMap.get("connTimeout")).equals("")? 1000 : Integer.parseInt((String)confMap.get("connTimeout")));
            // 타임아웃 시간(MS) - Read
            connection.setReadTimeout(StringUtils.defaultString((String)confMap.get("readTimeout")).equals("")? 7000 : Integer.parseInt((String)confMap.get("readTimeout")));
            connection.setAllowUserInteraction(false);	// 동기화 유무
            connection.setRequestProperty("Content-type", contentType);

            // 2. 파라미터 송신(POST방식일경우)
            if (confMap.get("sendParam") != null && method.equals("POST")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> sendParamMap = (Map<String, Object>)confMap.get("sendParam");
                Set<String> keySet = sendParamMap.keySet();
                for (Iterator<String> iterStr=keySet.iterator();iterStr.hasNext();) {
                    String keyName = iterStr.next();
                    String keyValue = (String)sendParamMap.get(keyName);

                    sendParam.append(keyName + "=" + keyValue).append("&");
                }
                if (sendParam.length() > 0) {
                    writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    try {
                        writer.write(sendParam.toString());
                        writer.flush();
                    } catch (Exception exception) {
                        throw exception;
                    } finally {
                        writer.close();
                        writer = null;
                    }

                    // System.out.println("## sendParam :" + sendParam);
                }
            }

            // 3. 결과 수신
            responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                reader = null;

            }

            // System.out.println("##result : " + result.toString());

            connection.disconnect();
            connection = null;

        } catch (Exception except) {
            System.out.println("###ERROR URL_Connection");
            System.out.println("###result from "+ confMap.get("URL") + " : " + result.toString());
            System.out.println("###sendParam : " + confMap.get("sendParam"));
            except.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            } catch (Exception except) {
                except.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
            } catch (Exception except) {
                except.printStackTrace();
            }
        }

        return result.toString();
    }
}
