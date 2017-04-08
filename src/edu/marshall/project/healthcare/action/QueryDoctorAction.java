package edu.marshall.project.healthcare.action;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryDoctorAction implements Action<String>{

	@Override
	public String excute(String param) {
		HashMap<String, Object> hashMap=JSON.parseObject(param, HashMap.class);//param from client
//		String beginNum=Integer.parseInt(hashMap.get("beginNum"));
//		String endNum=Integer.parseInt(hashMap.get("endNum"));
		String sql="select a.doctor_id,a.doctor_name_first,a.doctor_name_last,a.doctor_name_mid,a.doctor_username,a.doctor_gender_id,b.gender_name "
				+ "from doctor as a left join gender as b on a.doctor_gender_id=b.gender_id limit "+hashMap.get("beginNum")+","+hashMap.get("endNum");
		String result=new DaoHelper().selectV2(sql, null);
		return result;
	}


}
