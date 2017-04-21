package edu.marshall.project.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Initialize home.html 
 * @author Xuejian Li
 *
 */
public class UserIniliatizeAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		//Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String userType=""+param.get("user_type");
		String sql="select * from app as a left join app_user as au on a.app_id=au.app_user_app_id where "
				+ "au.app_user_user_id="+param.get("user_id")+" "
						+ "and au.app_user_user_type_id="+param.get("user_type_id");
		List<Map<String, Object>> result=new DaoHelper().selectForList(sql, null);
		HashMap<String, Object> finalResult=new HashMap<String,Object>(param);
		finalResult.put("apps", result);
		System.out.println("OUTPUT: "+JSON.toJSONString(finalResult));
		return JSON.toJSONString(finalResult);
	}

	

}
