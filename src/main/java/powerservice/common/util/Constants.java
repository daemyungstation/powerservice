/*
 * (@)# Constants.java
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

/**
 * Constants
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 8.
 * @프로그램ID <<프로그램 아이디>>
 */

public class Constants {
    public static final String LOGIN_SUCCESS = "loginSuccess"; // 성공
    public static final String NOT_EXIST_USERID = "존재하지 않는 아이디입니다."; // USER ID 존재안함
    public static final String LOGIN_DO_NOT_MATCH_PASSWORD = "loginDoNotMatchPassword"; // PASSWORD 불일치
    public static final String LOGIN_TEMP_PASSWORD = "loginTempPassword"; // 임시 비밀번호
    public static final String LOGIN_MODIFY_PASSWORD = "loginModifyPassword"; // 기준일(기본90일) 지난 비밀번호
    public static final String LOGIN_PRODUCT_ACCESS = "loginProductAccess"; // 제품접근권한없음
    public static final String LOGIN_QUIT_USER = "loginQuitUser"; // 퇴사
    public static final String LOGIN_OFF_USER = "loginOffUser"; // 휴직
    public static final String INVALID_PASSWORD = "invalidPassword"; // 비밀번호변경 - 현재비밀번호틀림
    public static final long FILEUPLOAD_SIZE = 4194304;
    public static String SERVERID = "";
    public static String MSG_NOT_EXIST_SYNC = "싱크정보가 없습니다.";
    public static String MSG_NOT_EXIST_SESSION = "세션정보가 없습니다.";
    public static String MSG_EXPIRED_PAGE = "만료된 페이지입니다.";

    public static String ACTIVE_PROFILE = ""; // 활성 프로파일(DEV:로컬, TST:개발, OPR:운영)
    public static String SRVR_DIV_CD = "30";  // 기본값:30 - 개발자 서버

    // 엔컴 암호화 관련 상수
    public static String aesKey = "1dpszja3eo5aud79";
}
