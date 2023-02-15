/**
 * @(#)EPXMLHandler.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.core.util.xml;

/**
 * XML DOM 노드 생성, 조작 도중 발생된 예외
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 문규원
 * @version 1.0
 * @date 2013/04/01
 */
public class ParseDOMException extends Exception {
    private static final long serialVersionUID = -6269867776752947816L;

    /**
     * 생성자; 메시지와 발생된 예외 정보를 통해 객체 생성
     *
     * @param szMessage
     *                발생된 예외에 대한 메시지
     * @param parentExcept
     *                발생된 예외의 상위 예외 객체
     */
    public ParseDOMException(String szMessage, Exception parentExcept) {
    super(szMessage, parentExcept);
    }

    /**
     * 생성자; 메시지 정보를 통해 객체 생성
     *
     * @param szMessage
     *                발생된 예외에 대한 메시지
     */
    public ParseDOMException(String szMessage) {
    super(szMessage);
    }
}
