package edu.marshall.project.infocenter.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryOrgTypeAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from organization_type";
		return new DaoHelper().selectV2(sql, null);
	}


}
