package egovframework.rte.cmmn.ria.xplatform.vo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author yimoc@tobesoft.com
 *
 */
public class ClassInfo {
	private Map <String,Method> setterMethods;
	private Map <String,Method> getterMethods;
	private Class clz;
	
	private ClassInfo(Class _clz){
		setterMethods = new HashMap<String, Method>();
		getterMethods = new HashMap<String, Method>();
		clz = _clz;
		addGetterSetterMethods();
	}
	
	public static ClassInfo getInstance(Class clazz){
		return new ClassInfo(clazz);
	}
	
	private void addGetterSetterMethods(){
		Method[] methods = clz.getDeclaredMethods();
		 for (Method method : methods) {
			String methodName = method.getName();
			System.out.println("methodName="+methodName);
			if (methodName.startsWith("set") && method.getParameterTypes().length == 1){
				String cuttingMember = methodName.substring(3);
				cuttingMember = cuttingMember.substring(0, 1).toLowerCase(Locale.US) + cuttingMember.substring(1);
				//System.out.println("cuttingMember ="+cuttingMember+" methodName="+methodName);
				setterMethods.put(cuttingMember, method);
			}
			
			if (methodName.startsWith("get") && method.getParameterTypes().length == 0){
				String cuttingMember = methodName.substring(3);
				cuttingMember = cuttingMember.substring(0, 1).toLowerCase(Locale.US) + cuttingMember.substring(1);
				//System.out.println("cuttingMember ="+cuttingMember+" methodName="+methodName);
				getterMethods.put(cuttingMember, method);
			}
			
			
		}
	}
	
	public Set<String> getMembers(){
		return setterMethods.keySet();
	}
	
	public Method getSetterMethod( String memberName){
		Method method = setterMethods.get(memberName);
		return method;
 	}
	
	public Method getGetterMethod( String memberName){
		Method method = getterMethods.get(memberName);
		return method;
 	}

}
