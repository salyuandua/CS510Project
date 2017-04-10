package edu.marshall.project.user.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryOrgsAction implements Action<String>{
/**
 * get all organizations for sign in page
 */
	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from organization";
		String result=new DaoHelper().selectV2(sql, null);
		return result;
	}



}
