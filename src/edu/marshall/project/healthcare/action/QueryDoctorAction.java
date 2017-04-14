package edu.marshall.project.healthcare.action;

import java.util.HashMap;
import java.util.Map;



import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryDoctorAction implements Action<String>{
/*USER INFO IS :{"health_care_system_admin_id":1,"user_last_name":"Liu","user_name":"admin",
 * "organization_name":"Health Care System1","user_first_name":"XiaoXin","user_mid_name":"L",
 * "user_gender_name":"Male","user_type":"health_care_system_admin","organization_type_id":1,
 * "organization_id":1,"user_gender_id":1,"user_type_id":"3","tablename":"health_care_system_admin"}*/
	@Override
	public String excute(Map<String, Object> param) {
//		String beginNum=Integer.parseInt(hashMap.get("beginNum"));
//		String endNum=Integer.parseInt(hashMap.get("endNum"));
		HashMap<String, Object> userInfo=(HashMap<String, Object>) param.get("userInfo");
		String sql="select a.doctor_id,a.doctor_name_first,a.doctor_name_last,a.doctor_name_mid,if(a.doctor_name_mid is null,concat(a.doctor_name_first,' ',a.doctor_name_last),concat(a.doctor_name_first,' ',a.doctor_name_mid,' ',a.doctor_name_last)) as doctor_name, a.doctor_username,a.doctor_gender_id,b.gender_name,count(p.patient_id) as patient_num "
				+ "from doctor as a left join gender as b on a.doctor_gender_id=b.gender_id left join patient as p on a.doctor_id=p.patient_doctor_id where a.doctor_organization_id=? group by a.doctor_id ";
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("organization_id")});
		return result;
	}


}
