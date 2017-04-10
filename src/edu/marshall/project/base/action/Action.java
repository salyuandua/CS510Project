package edu.marshall.project.base.action;

import java.util.Map;

/**
 * All action should implement this interface 
 * @author l1876
 *
 */
public interface Action <T>{
	public static final String SIGNIN_NOT_MATCH="0"; 
	public static final String SIGNIN_SUCCESS="1";
	/**
	 * 
	 * @param params
	 * @return result
	 */
	public T excute(Map<String, Object> param);
}
