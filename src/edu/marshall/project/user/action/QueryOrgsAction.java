package edu.marshall.project.user.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * get all organizations for sign in page
 */
public class QueryOrgsAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from organization";
		String result=new DaoHelper().selectV2(sql, null);
		return result;
	}



}
