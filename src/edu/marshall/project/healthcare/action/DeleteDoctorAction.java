package edu.marshall.project.healthcare.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class DeleteDoctorAction implements Action<String>{
	/**
	 * delete doctor operation
	 */
	@Override
	public String excute(Map<String, Object> param) {
		//HashMap<String, Object> userInfo=(HashMap<String, Object>) param.get("userInfo");
		String sql1="delete from doctor where doctor_id=?";
		Object[] param1=new Object[]{param.get("doctor_id")};
		String sql2="delete from app_user where app_user_user_id=?";
		Object[] param2=new Object[]{param.get("doctor_id")};
		ArrayList<String> sqls=new ArrayList<String>(2);
		sqls.add(sql1);
		sqls.add(sql2);
		ArrayList<Object[]> params=new ArrayList<Object[]>(2);
		params.add(param1);
		params.add(param2);
		String result=new DaoHelper().update(sqls, params);
		return result;
	}
	



}
