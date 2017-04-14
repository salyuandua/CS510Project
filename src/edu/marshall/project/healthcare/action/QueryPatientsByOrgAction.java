package edu.marshall.project.healthcare.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryPatientsByOrgAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select p.patient_id,if(p.patient_name_mid is null,concat(p.patient_name_last,' ',p.patient_name_first),concat(p.patient_name_last,' ',p.patient_name_mid,' ',p.patient_name_first)) as patient_name,"
					+"p.patient_doctor_id,g.gender_name, if(d.doctor_name_mid is null,concat(d.doctor_name_last,' ',d.doctor_name_first),concat(d.doctor_name_last,' ',d.doctor_name_mid,' ',d.doctor_name_first)) as doctor_name,o.organization_name,p.patient_email,"
					+"p.patient_emg_contact_name,p.patient_emg_contact_phone,p.patient_health_problem,p.patient_phone,p.patient_username from "
					+"patient as p left join gender as g on p.patient_gender_id=g.gender_id left join doctor as d on p.patient_doctor_id=d.doctor_id " 
					+"left join organization as o on p.patient_organization_id=o.organization_id where p.patient_organization_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("organization_id")});
		return result;
	}


}
