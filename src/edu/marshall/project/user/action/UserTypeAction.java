package edu.marshall.project.user.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class UserTypeAction implements Action{

	@Override
	public String excute(Map hashMap) {
		//HashMap<String, Object> hashMap=JSON.parseObject(param, HashMap.class);//param from client
		DaoHelper daoHelper=new DaoHelper();
		System.out.println("%%%%%%select * from user_type where user_type_organization_type_id="+hashMap.get("organization_id"));
		
		String result=daoHelper.selectV2("select * from user_type where user_type_organization_type_id=?", new Object[]{hashMap.get("organization_type_id")});
		//System.out.println(result);
		return result;
	}





}
