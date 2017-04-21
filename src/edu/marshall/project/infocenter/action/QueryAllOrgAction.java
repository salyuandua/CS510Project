package edu.marshall.project.infocenter.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryAllOrgAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from organization as o left join organization_type as ot on o.organization_type_id=ot.organization_type_id where o.organization_id not in (5)";
		
		return new DaoHelper().selectV2(sql, null);
	}


}
