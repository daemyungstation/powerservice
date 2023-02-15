/**
 * @(#)Access.java
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2013/01/01
 *
 * Copyright (c) 2013 by Inwoo Tech Inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of Inwoo Tech Inc.
 * You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Inwoo Tech Inc.
 *
 */
package powerservice.core.access;

import java.text.ParseException;
import java.util.List;

/**
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2013/01/01
 * @PROGRAMID : Access
 * @DESCRIPTION : 접근정보
 */

public class Access {
    public final static String IP_ACCESS_MODE = "1";
    public final static String IP_BLOCK_MODE = "2";

    private List<String> m_arrAction = null; // 비세션 접근허용 리스트
    private List<String> m_arrIP = null; // IP 접근/차단 리스트
    private String m_szIpAccessMode = null; // IP 접근 모드


    /**
     * 생성자
     *
     * @param List<String> arrAction 비세션 접근허용 리스트
     * @param List<String> arrIP IP 접근/차단 리스트
     * @param String szIPAccessMode IP 접근 모드
     * @throws ParseException
     */
    public Access(List<String> arrAction, List<String> arrIP, String szIpAccessMode) throws ParseException {
        m_arrAction = arrAction;
        m_arrIP = arrIP;
        m_szIpAccessMode = szIpAccessMode;
    }

    /**
     * 비세션 접근허용 리스트 반환
     *
     * @return List<String>
     */
    public List<String> getActionList() {
        return m_arrAction;
    }

    /**
     * IP 접근/차단 리스트 반환
     *
     * @return List<String>
     */
    public List<String> getIpList() {
        return m_arrIP;
    }

    /**
     * IP 접근 모드 반환
     *
     * @return String
     */
    public String getIpAccessMode() {
        return m_szIpAccessMode;
    }
}
