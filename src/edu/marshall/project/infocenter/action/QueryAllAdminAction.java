package edu.marshall.project.infocenter.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryAllAdminAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String[] tableNames=new String[]{"health_care_system_admin","pharmacy_admin","bank_admin"};
		DaoHelper daoHelper=new DaoHelper();
		String sql="select a.@_id as admin_id,if(a.@_name_mid is null,concat(a.@_name_first,' ',a.@_name_last),concat(a.@_name_first,' ',a.@_name_mid,' ',a.@_name_last)) as admin_name,"
				+"b.gender_name as admin_gender,o.organization_name,a.@_username as admin_username from @ as a left join gender as b on a.@_gender_id=b.gender_id left join organization as o on a.@_organization_id=o.organization_id";
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		for(int i=0;i<tableNames.length;i++){
			String subSql=sql.replace("@", tableNames[i]);
			List<Map<String, Object>> subResult=daoHelper.selectForListV2(subSql, null);
			result.addAll(subResult);
		}
		return JSON.toJSONString(result);
	}
public static void main(String[] args) {
	QueryAllAdminAction aa=new QueryAllAdminAction();
	System.out.println(aa.excute(null));
}
}
