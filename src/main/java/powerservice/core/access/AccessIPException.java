/**
 * @(#)AccessIPException.java
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

/**
* 접근 IP 오류
*/
public class AccessIPException extends RuntimeException {
    static final long serialVersionUID = 500588345626354328L;

    public AccessIPException() {
    }

    public AccessIPException(String szMsg) {
        super(szMsg);
    }
}
