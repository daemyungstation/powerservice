package egovframework.rte.cmmn.ria.support;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tobesoft.xplatform.tx.HttpPlatformRequest;

/**
 * X-Platform MiAdaptor 클래스
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

public abstract class XpfAdaptor implements UiAdaptor {

	protected Log log = LogFactory.getLog(this.getClass());

	public Object convert(HttpServletRequest request) throws Exception {

		HttpPlatformRequest httpPlatformRequest = null;

		try {
			
			/*
			 * client에서 요청한 data를 request객체를 이용하여 확인할 수 있는 예 
			 * 
			 * byte[] b = new byte[1024];
			 * InputStream is = request.getInputStream();
			 * BufferedInputStream bis = new BufferedInputStream(is);
			 * while( bis.read(b, 0, b.length) > -1) {
			 * 		System.out.println(new String(b));
			 * }
			 * 
			 */
			
			httpPlatformRequest = new HttpPlatformRequest(request);
			httpPlatformRequest.receiveData();
			
		} catch (Exception ex) {
			ex.getStackTrace();
			throw new Exception("HttpPlatformRequest error");
		}

		/*
		 * HttpPlatformRequest 에서 VariableList 와 DatasetList 를 뽑아 결정된 Map
		 * 형태(example : ModelMap) 또는 VO 객체에 담아 return 한다.
		 */
				
		Object dto = converte4In(httpPlatformRequest, request);
		
		return dto;
	}

	public abstract Object converte4In(HttpPlatformRequest HttpPlatformRequest, HttpServletRequest request);
	
	public abstract Class getModelName();
	
	
}
