package edu.marshall.project.healthcare.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query patients by a specific doctor
 * @author Xuejian Li
 *
 */
public class QueryPatientByDocAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		//HashMap<String, Object> hashMap=JSON.parseObject(param, HashMap.class);//param from client
		String sql="select p.patient_id,p.patient_doctor_id,p.patient_gender_id,g.gender_name,if(patient_name_mid is null,concat(p.patient_name_last,' ',p.patient_name_first),concat(p.patient_name_last,' ',p.patient_name_mid,' ',p.patient_name_first)) as patient_name"+ 
" from patient as p left join gender as g on p.patient_gender_id=g.gender_id where p.patient_doctor_id=?";
		Object[] params=new Object[]{param.get("doctor_id")};
		String result=new DaoHelper().selectV2(sql, params);
		return result;
	}



}
