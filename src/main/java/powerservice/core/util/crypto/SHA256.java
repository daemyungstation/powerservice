/**
 * @(#)SHA_2.SHA256
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.core.util.crypto;

import java.security.MessageDigest;

/**
 * SHA 256 알고리즘
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 한숙향
 * @version 1.0
 * @date 2013/04/01
 */
public class SHA256 {

    public static String digest(String data) throws Exception {
        if (data == null) {
            throw new NullPointerException();
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] raw = md.digest(data.getBytes("EUC-KR"));

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < raw.length; i++) {
            result.append(Integer.toHexString(raw[i] & 0xff));
        }
        return result.toString();
    }

//    public static void main(String[] args) throws Exception {
//        String data = "java12345";
//        String rtnData = "";
//
//        try {
//            rtnData = SHA256(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("\nOrigin data: " + data);
//        System.out.println("\nReturn data: " + rtnData);
//    }
}