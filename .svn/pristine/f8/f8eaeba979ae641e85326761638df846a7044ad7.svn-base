package powerservice.web.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

public class TokenRequestDataValueProcessor implements RequestDataValueProcessor {

    private String gsSubmitActionUrl = "";

    @Override
    public String processAction(HttpServletRequest poRequest, String psAction) {
        // FORM ACTION 이 "checksubmit=true" 파라미터를 포함하는 경우 처리 URL 저장
        if (psAction.indexOf("?checksubmit=true") > 0 ||
            psAction.indexOf("&checksubmit=true") > 0) {
            gsSubmitActionUrl = psAction.replace("?checksubmit=true", "").replace("&checksubmit=true", "");
        }

        return psAction;
    }

    @Override
    public String processFormFieldValue(HttpServletRequest poRequest, String psName, String psValue, String psType) {
        return psValue;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest poRequest) {
        Map<String, String> mHiddenFields = new HashMap<String, String>();

        // CSRF TOKEN 저장
        mHiddenFields.put(TokenManager.CSRF_PARAM_NAME, TokenManager.getCsrfTokenForSession(poRequest.getSession()));

        // FORM ACTION URL 이 저장된 경우 SUBMIT TOKEN 저장
        if (!"".equals(gsSubmitActionUrl)) {
            mHiddenFields.put(TokenManager.SUBMIT_ACTION_PARAM_NAME, gsSubmitActionUrl);
            mHiddenFields.put(TokenManager.SUBMIT_TOKEN_PARAM_NAME, TokenManager.getSubmitTokenForSession(poRequest.getSession(), gsSubmitActionUrl, true));

            gsSubmitActionUrl = "";
        }

        return mHiddenFields;
    }

    @Override
    public String processUrl(HttpServletRequest poRequest, String psUrl) {
        return psUrl;
    }

}