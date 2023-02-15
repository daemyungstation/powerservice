/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerservice.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;

public class ExceptionHandler extends SimpleMappingExceptionResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    private String ajaxErrorView;
    private String ajaxDefaultErrorMessage = "An error has occurred";
    private boolean ajaxShowTechMessage = true;

    @Override
    public ModelAndView resolveException(HttpServletRequest poRequest, HttpServletResponse poResponse, Object poHandler, Exception poException) {
        String sErrorMessage = poException.getMessage();
        if (sErrorMessage != null && (sErrorMessage.startsWith("[AccessIPException]") ||
            sErrorMessage.startsWith("[AccessSessionException]") ||
            sErrorMessage.startsWith("[UserSessionException]"))) { // HandlerInterceptor 오류 메시지
            LOGGER.error("HandlerInterceptor Error - " + sErrorMessage.replace("Exception", "Validation"));
        } else { // 기타 오류 메시지 출력
            LOGGER.error("PowerServiceException", poException);
        }

        Object oAjaxExceptionHandler = poRequest.getAttribute("bAjaxExceptionHandler");
        if (isAjaxRequest(poRequest) || oAjaxExceptionHandler != null) { // Ajax 요청 오류
            // 오류 메시지 작성
            String sExceptionMessage = ajaxDefaultErrorMessage;
            if (ajaxShowTechMessage) {
                sExceptionMessage += "\n" + getExceptionMessage(poException);
            }

            // 화면 오류 메시지 출력을 위한 응답코드 반환
            if (poRequest.getAttribute("HttpErrorCode") != null) {
                // MainController 에서 온 경우 저장된 HTTP 오류코드 반환
                poResponse.setStatus(Integer.parseInt(String.valueOf(poRequest.getAttribute("HttpErrorCode"))));
            } else {
                // 기본 서버 내부 오류코드 반환
                poResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            // Ajax 오류 페이지 반환
            ModelAndView modelAndView = new ModelAndView(ajaxErrorView);
            modelAndView.addObject("errorMessage", sExceptionMessage);
            return modelAndView;

        } else if(isXplatformRequest(poRequest)) {			// XPLATFORM 오류
            ModelAndView modelAndView = new ModelAndView("xplatformMapView");
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-2");
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  getExceptionMessage(poException));

            return modelAndView;
        } else {											// 일반 요청 오류
            // SimpleMappingExceptionResolver 처리
            return super.resolveException(poRequest, poResponse, poHandler, poException);
        }
    }

    private String getExceptionMessage(Throwable poThrowable) {
        String sMessage = "";
        while (poThrowable != null) {
            sMessage += poThrowable.getMessage() + "\n";
            poThrowable = poThrowable.getCause();
        }
        return sMessage;
    }

    private boolean isAjaxRequest(HttpServletRequest poRequest) {
        return "XMLHttpRequest".equals(poRequest.getHeader("X-Requested-With"))
            || "XMLHttpRequest".equals(poRequest.getHeader("x-requested-with"));
    }

    private boolean isXplatformRequest(HttpServletRequest poRequest) {
        return poRequest.getHeader("user-agent").indexOf("XPLATFORM") > -1;
    }

    /*
    private boolean isResourceRequest(HttpServletRequest poRequest) {
        return poRequest.getRequestURI().indexOf('.') > 0;
    }
    */

    public void setAjaxDefaultErrorMessage(String ajaxDefaultErrorMessage) {
        this.ajaxDefaultErrorMessage = ajaxDefaultErrorMessage;
    }

    public void setAjaxErrorView(String ajaxErrorView) {
        this.ajaxErrorView = ajaxErrorView;
    }

    public void setAjaxShowTechMessage(boolean ajaxShowTechMessage) {
        this.ajaxShowTechMessage = ajaxShowTechMessage;
    }

    public String getAjaxDefaultErrorMessage() {
        return ajaxDefaultErrorMessage;
    }

    public String getAjaxErrorView() {
        return ajaxErrorView;
    }

    public boolean isAjaxShowTechMessage() {
        return ajaxShowTechMessage;
    }
}
