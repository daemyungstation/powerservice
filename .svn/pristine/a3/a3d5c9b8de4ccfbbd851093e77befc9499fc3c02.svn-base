/*
 * (@)# HandlerInterceptor.java
 *
 * @author 김경희
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
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
package powerservice.web.handler;

import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import powerservice.business.bean.UserSession;
import powerservice.business.usr.service.SessInfoService;
import powerservice.core.access.AccessIPException;
import powerservice.core.access.AccessSessionException;
import powerservice.core.access.AccessValidator;
import powerservice.core.util.SessionUtils;
import powerservice.web.security.TokenManager;

/**
 * Controller 실행 전/후 공통 작업
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID <<HandlerInterceptor>>
 */

public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(HandlerInterceptor.class);

    @Autowired
    private ServletContext ctx;
    @Autowired
    private AccessValidator accesseValidator;
    @Autowired
    private SessInfoService sessInfoService;

    private final Character[] FORBBIDEN_URL_CHAR_LIST = {'\'', '\"', '(', ')', '<', '>'}; // URL 제한문자
    private final String[] CHECK_TOKEN_COMMAND_LIST = {"save", "insert", "update", "delete"}; // 토큰 체크 대상 요청
    private final String[] EXCEPT_TOKEN_URL_LIST =
        { "/login"						// 로그인
        , "/file/download"				// 파일 다운로드
        , "/file/insert"				// 파일 업로드
        , "/file/delete"				// 파일 삭제
        , "/file/delete/real"			// 파일 삭제-실제 삭제
        , "/file/imagebrowser/insert"	// 덱스트 에디터 이미지 업로드
        }; // 토큰 체크 제외 URL

    /**
     * 접근 제한 체크
     *
     * @param poRequest
     * @param poResponse
     * @param poHandler
     * @return boolean
     * @throws Exception
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest poRequest, HttpServletResponse poResponse, Object poHandler) throws Exception {
        String sHttpMethod = poRequest.getMethod(); // 요청 HTTP 메소드
        String sRequestPath = poRequest.getServletPath(); // 요청 경로

        if (sHttpMethod.equalsIgnoreCase("POST")) { // POST 요청
            // 정상 요청 체크
            validateRequest(poRequest);

            // 토큰 정보 체크
            validateToken(poRequest);
        } else { // POST 이외 요청
            // 일반 페이지 요청인 경우 (이미지/JS/CSS 등 웹 자원 요청 제외)
            if (sRequestPath.endsWith(".do") || sRequestPath.indexOf(".") < 0) {
                // 정상 요청 체크
                validateRequest(poRequest);

                // 토큰 정보 넣음
                TokenManager.setCsrfTokenToRequest(poRequest);
            }
        }

        return true;
    }

    /**
     * 정상 요청 체크
     *
     * @param poRequest
     * @throws Exception
     */
    private void validateRequest(HttpServletRequest poRequest) throws Exception {
        String sClientIp = poRequest.getRemoteAddr(); // 사용자 IP
        String sHttpMethod = poRequest.getMethod(); // 요청 HTTP 메소드
        String sRequestPath = poRequest.getServletPath(); // 요청 경로

        // 캐쉬 및 오류 페이지 처리 제외
        if (sRequestPath.equals("/app-cache.do") || sRequestPath.startsWith("/error") || sRequestPath.startsWith("/total-search")) {
            return;
        }

        if (sHttpMethod.equalsIgnoreCase("GET")) {
            LOGGER.info("######################## " + sHttpMethod + " - [" + sClientIp + "] - [" + sRequestPath + "]");
        } else {
            LOGGER.info("************************ " + sHttpMethod + " - [" + sClientIp + "] - [" + sRequestPath + "]");
        }

/*
        // 접근 사용자 IP 체크
        String sSessionUserIp = (String) poRequest.getSession().getAttribute("SESSION_USER_IP");
        if (!sClientIp.equals(sSessionUserIp)) {
            if (!accesseValidator.validateUserIp(sClientIp)) {
                throw new AccessIPException("[AccessIPException] " + sClientIp + " 접근 권한이 없습니다.");
            }
            poRequest.getSession().setAttribute("SESSION_USER_IP", sClientIp);
            LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%% validateUserIp [" + sClientIp + "] => ACCESS !!!");
        }

*/
        // 세션이 필요한 경로인 경우 접근 사용자 세션 체크
        if (!accesseValidator.validateUserAction(sRequestPath)) {
            // 로그인 세션 체크
            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();

            /* ================================================================
             * 날짜 : 20180122
             * 이름 : 송준빈
             * 추가내용 : Nice 전자서명 결과 전송시 로그인 세션 검증 하지 않도록 함.
             * ================================================================
             * */
            // 로그인 세션 검사 여기푸세요!!!
            if (oUserSession == null && sRequestPath.equals("/web/nice/updateNiceSendStatement") == false) {
                throw new AccessSessionException("[AccessSessionException] " + sClientIp + " 세션 정보가 없습니다.");
            }


            String sContextPath = poRequest.getContextPath(); // 컨텍스트 경로
            String sAccessCheckPath = ""; // 요청 권한 체크 대상 URL
            String sRequestPathFull = sContextPath + sRequestPath; // 컨텍스트 경로 포함 요청 URL
            if (sRequestPathFull.indexOf('?') > 0) {
                // 쿼리 스트링 제거
                sRequestPathFull = sRequestPathFull.substring(0, sRequestPathFull.indexOf('?'));
            }
            if (sHttpMethod.equalsIgnoreCase("GET") && sRequestPathFull.endsWith(".do")) { // 페이지 요청 인 경우
                // 요청 페이지 저장
                sAccessCheckPath = sRequestPathFull;

                LOGGER.debug("$$$$$$ GET ++++++ [" + sRequestPathFull + "]");
            } else if (sHttpMethod.equalsIgnoreCase("POST") && sRequestPathFull.indexOf('.') < 0) { // 거래 요청 인 경우
                // 헤더의 거래 요청 페이지 저장
                sAccessCheckPath = poRequest.getHeader("Referer");
                if (sAccessCheckPath == null || "".equals(sAccessCheckPath)) {
                    // 헤더의 거래 요청 페이지가 없는 경우 권한 없음 처리
                    sAccessCheckPath = "///";
                } else {
                    // 컨텍스트 경로 이전 URL 제거
                    if (sAccessCheckPath.indexOf(sContextPath) > 0) {
                        sAccessCheckPath = sAccessCheckPath.substring(sAccessCheckPath.indexOf(sContextPath));
                    }
                    // 쿼리 스트링 제거
                    if (sAccessCheckPath.indexOf('?') > 0) {
                        sAccessCheckPath = sAccessCheckPath.substring(0, sAccessCheckPath.indexOf('?'));
                    }
                }

                LOGGER.debug("$$$$$$ POST ------ [" + sRequestPathFull + "]");
                LOGGER.debug("$$$$$$ POST ++++++ [" + sAccessCheckPath + "]");
            }

            /* ================================================================
             * 날짜 : 20180122
             * 이름 : 송준빈
             * 추가내용 : Nice 전자서명 결과 전송시 로그인 세션 검증 하지 않도록 함.
             * ================================================================
             * */
            // 사용자 세션ID 체크 여기푸세요!!!
            if(sRequestPath.equals("/web/nice/updateNiceSendStatement") == false)
            {
                String sAppUserSessionID = (String) ctx.getAttribute("USER-SESSION-ID-" + oUserSession.getLoginid());
                if (sAppUserSessionID != null) {
                    if (!sAppUserSessionID.equals(poRequest.getSession().getId())) {
                        throw new Exception("[UserSessionException] " + sClientIp + " 다른 곳에서 로그인 했습니다.");
                    }
                }
            }

            /*
            Map<String, String> mAccessCheckParam = new HashMap<String, String>();
            mAccessCheckParam.put("user_id", oUserSession.getUserid());
            mAccessCheckParam.put("athr_cd", oUserSession.getAuthoritycd());
            mAccessCheckParam.put("was_sess_cntn", poRequest.getSession().getId());
            mAccessCheckParam.put("req_url", sAccessCheckPath);
            mAccessCheckParam.put("cntr_cd", oUserSession.getCentercd());

            Map<String, Object> mAccessCheckInfo = sessInfoService.getAccessCheckInfo(mAccessCheckParam);
            if (mAccessCheckInfo == null) {
                throw new Exception("권한 조회 오류가 발생했습니다.\r\n다시 시도해 주세요.");
            } else {
                // 중복 사용자 세션 체크
                if ("N".equals(mAccessCheckInfo.get("sess_check_yn"))) {
                    throw new Exception("[UserSessionException] " + sClientIp + " 다른 곳에서 로그인 했습니다.");
                }

                // [사용 권한이 없습니다.] 오류 발생 시 아래 예외 발생 주석 처리!!!
                // 팝업 화면 추가 시 [CM0003] 프로그램 코드 하위 3레벨에
                // 프로그램 등록 및 프로그램 권한을 추가 해야함!!!
                // 반드시 초기데이터(프로그램) 엑셀 파일 관리 하도록 함!!!
                // 요청 권한 체크
                //if (!"".equals(sAccessCheckPath) && "N".equals(mAccessCheckInfo.get("req_check_yn"))) {
                    //throw new Exception("사용 권한이 없습니다.");
                //}

                mAccessCheckInfo = null;
            }
            mAccessCheckParam = null;
            */
        }

        // URL 접근제한문자 확인
        sRequestPath = URLDecoder.decode(sRequestPath, "UTF-8");
        for (char cForbbiden : FORBBIDEN_URL_CHAR_LIST) {
            if (sRequestPath.indexOf(cForbbiden) > 0) {
                throw new Exception("잘못된 페이지 접근 요청입니다.");
            }
        }
    }

    /**
     * 토큰 정보 체크 - CSRF Protection 추가
     *
     * @param poRequest
     * @throws Exception
     */
    private void validateToken(HttpServletRequest poRequest) throws Exception {
        // 토큰 정보 체크 제외인 경우 종료
        String sRequestPath = poRequest.getServletPath(); // 요청 경로
        if (sRequestPath.endsWith(".do")) { // 페이지 요청
            return;
        }
        for (String sUrl : EXCEPT_TOKEN_URL_LIST) { // 제외 URL
            if (sRequestPath.equals(sUrl)) {
                return;
            }
        }

        // 토큰 정보 체크 대상 요청이 아닌 경우 종료
        boolean bExcept = true;
        for (String bCommand : CHECK_TOKEN_COMMAND_LIST) {
            if (sRequestPath.indexOf(bCommand) > 0) {
                bExcept = false;
                break;
            }
        }
        if (bExcept) {
            return;
        }


        // 요청 토큰 정보 확인
        /*
        String oSessionToken = TokenManager.getCsrfTokenForSession(poRequest.getSession()); // 세션 토큰
        String oRequestToken = ""; // 요청 토큰
        if ("XMLHttpRequest".equals(poRequest.getHeader("X-Requested-With")) ||
            "XMLHttpRequest".equals(poRequest.getHeader("x-requested-with"))) { // Ajax 요청인 경우
            oRequestToken = TokenManager.getCsrfTokenFromHeader(poRequest);
        } else { // 파라미터 방식 요청인 경우
            oRequestToken = TokenManager.getCsrfTokenFromRequest(poRequest);
        }
        // System.out.println("=====> " + requestToken);

        // 요청 토큰이 세션 토큰과 일치하지 않는 경우 오류 발생
        if (!oSessionToken.equals(oRequestToken)) {
            throw new Exception("잘못된 요청 정보입니다.");
        }*/
    }
}
