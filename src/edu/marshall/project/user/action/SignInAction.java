package edu.marshall.project.user.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * user sign in method
 * params is like: {username:xxx,password:xxx}
 */
public class SignInAction implements Action<Map<String,Object>>{

	@Override
	public Map<String,Object> excute(Map<String, Object> param) {
		//HashMap<String, String> hashMap=JSON.parseObject(param, HashMap.class);//param from client
		DaoHelper daoHelper=new DaoHelper();
		String tablename=(String) param.get("tablename");
		String sql="select a."+tablename+"_id as user_id,a."+tablename+"_name_first as user_first_name,a."+tablename+"_name_last as user_last_name,a."+tablename+"_name_mid as user_mid_name,"
				+"a."+tablename+"_gender_id as user_gender_id,b.gender_name as user_gender_name,a."+tablename+"_username as user_name"+
				 " from "+tablename+" as a left join gender as b on a."+tablename+"_gender_id=b.gender_id"
					+ " where "+tablename+"_username=? and "+tablename+"_password=?";
		List<Map<String,Object>> result= daoHelper.selectForListV2(sql,new Object[]
				{param.get("username"),param.get("password")});
		if(result.size()==0){//fail
			return null;
		}
		//success
		Map<String, Object> userInfo=result.get(0);
		//System.out.println("&&&&"+result);
		userInfo.put("tablename", tablename);
		userInfo.put("user_type_id", param.get("user_type_id"));
		userInfo.put("user_type", tablename);
		userInfo.put("organization_id", param.get("organization_id"));
		userInfo.put("organization_name", param.get("organization_name"));
		userInfo.put("organization_type_id", param.get("organization_type_id"));
		//System.out.println("USER INFO IS :"+JSON.toJSONString(userInfo));
		return userInfo;
		
	}



}
