package edu.marshall.project.user.action;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class UserTypeAction implements Action{

	@Override
	public String excute(String params) {
		DaoHelper daoHelper=new DaoHelper();
		
		String result=daoHelper.select("select * from user_type", null);
		System.out.println(result);
		return result;
	}



}
