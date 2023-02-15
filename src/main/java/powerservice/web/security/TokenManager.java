package powerservice.web.security;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;

/**
 * CSRF 및 중복처리방지 TOKEN 관리
 */
public final class TokenManager {

    /**
     * TOKEN 파라미터명
     */
    static final String CSRF_PARAM_NAME = "csrf_token";
    static final String SUBMIT_ACTION_PARAM_NAME = "submit_action";
    static final String SUBMIT_TOKEN_PARAM_NAME = "submit_token";

    /**
     * SESSION 저장 TOKEN 변수명
     */
    private final static String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = "csrf.tokenval";
    private final static String SUBMIT_TOKEN_FOR_SESSION_ATTR_NAME = "submit.tokenval";

    private TokenManager() {};


    /**
     * CSRF TOKEN SESSION 저장
     */
    public static String getCsrfTokenForSession(HttpSession poSession) {
        String sToken = null;
        synchronized (poSession) {
            sToken = (String) poSession.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (sToken == null) {
                sToken = UUID.randomUUID().toString();
                poSession.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, sToken);
            }
        }
        return sToken;
    }

    /**
     * CSRF TOKEN REQUEST 저장
     */
    public static void setCsrfTokenToRequest(HttpServletRequest poRequest) {
        poRequest.setAttribute(CSRF_PARAM_NAME, getCsrfTokenForSession(poRequest.getSession()));
    }

    /**
     * 요청 CSRF TOKEN 파라미터 추출
     */
    public static String getCsrfTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }

    /**
     * 요청 CSRF TOKEN 헤더 추출
     */
    public static String getCsrfTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("X-CSRF-Token");
    }


    /**
     * 중복처리방지 TOKEN SESSION 저장
     */
    public static String getSubmitTokenForSession(HttpSession poSession, String psActionUrl, Boolean pbForce) {
        String sToken = null;
        synchronized (poSession) {
            sToken = (String) poSession.getAttribute(SUBMIT_TOKEN_FOR_SESSION_ATTR_NAME + psActionUrl);
            if (sToken == null || pbForce) {
                sToken = UUID.randomUUID().toString();
                poSession.setAttribute(SUBMIT_TOKEN_FOR_SESSION_ATTR_NAME + psActionUrl, sToken);
            }
        }
        return sToken;
    }

    /**
     * 중복처리방지 TOKEN 체크
     */
    public static boolean checkSubmitToken(ActionResult poActionResult, Map<String, Object> pmParam) {
        boolean bResult = true;

        HttpServletRequest oRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession oSession = oRequest.getSession();

        String sSubmitAction = (String) pmParam.get(SUBMIT_ACTION_PARAM_NAME); // 요청 ACTION
        if (sSubmitAction != null) {
            // 중복처리방지 TOKEN 체크
            String sRequestToken = (String) pmParam.get(SUBMIT_TOKEN_PARAM_NAME); // 요청 TOKEN
            String sSessionToken = getSubmitTokenForSession(oSession, sSubmitAction, false); // 세션 TOKEN
            // LOGGER.debug("$$$ " + sSubmitAction + " - REQ=[" + sRequestToken + "] SES=[" + sSessionToken + "]");
            if (sRequestToken == null || sSessionToken == null || !sRequestToken.equals(sSessionToken)) {
                poActionResult.setResult(ActionResultType.ERROR);
                poActionResult.setErrMsg("중복된 처리 요청 입니다.\n처리결과 확인 후 다시 시도해 주세요.");

                bResult = false;
            }

            // 새 중복처리방지 TOKEN 생성
            sSessionToken = getSubmitTokenForSession(oSession, sSubmitAction, true);
            poActionResult.setToken(sSessionToken);
            // LOGGER.debug("==========> NEW=[" + sSessionToken + "]");
        }

        return bResult;
    }

}