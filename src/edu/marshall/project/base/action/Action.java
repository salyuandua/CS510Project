package edu.marshall.project.base.action;

import java.util.Map;

/**
 * All actions should implement this interface. When you implement a action, 3 stages must be done:
 * 1, Create a class which implements {@link Action} and excute method in {@link Action}.
 * 2, Add a node into actions.xml. 
 * 2, Notice add action=xxx in http post url
 * @author Xuejian Li
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
