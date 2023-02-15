package powerservice.core.util;

import powerservice.business.bean.UserSession;
import powerservice.core.bean.UserSessionCore;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils {
    private static final String USER_SESSION = "USER_SESSION";

    public static UserSessionCore getLoginUser() {
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
        UserSession user = (UserSession) httpSession.getAttribute(USER_SESSION);
        return user;
    }

    public static void setLoginUser(UserSessionCore user) {
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
        httpSession.setAttribute(USER_SESSION, user);
    }
}
