package egovframework.rte.cmmn.ria.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * CustomWebArgumentResolver Sample 클래스
 * @author 개발팀 홍길동
 * @since 2010.01.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.01.01  홍길동          최초 생성
 *
 * </pre>
 */

public class CustomWebArgumentResolver implements WebArgumentResolver {

	private UiAdaptor uiA;

	public void setUiAdaptor(UiAdaptor uiA) {
		this.uiA = uiA;
	}

	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		
		Class<?> type = methodParameter.getParameterType();
		Object uiObject = null;

		if (uiA == null)
			return UNRESOLVED;

		if (type.equals(uiA.getModelName())) {
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			uiObject = null;// (MiDTO) uiA.convert(request);

			return uiObject;
		}

		return UNRESOLVED;
	}

}
