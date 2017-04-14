package edu.marshall.project.user.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class GenderQueryAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from gender";
		
		return new DaoHelper().selectV2(sql, null);
	}


}
