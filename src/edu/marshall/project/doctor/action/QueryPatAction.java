package edu.marshall.project.doctor.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all patients who belong to a specific doctor
 * @author Xuejian Li
 *
 */
public class QueryPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select p.patient_id,if(p.patient_name_mid is null,concat(p.patient_name_last,' ',p.patient_name_first),concat(p.patient_name_last,' ',p.patient_name_mid,' ',p.patient_name_first)) as patient_name,"
					+"g.gender_name,p.patient_doctor_id,o.organization_name,p.patient_email,p.patient_emg_contact_name,p.patient_emg_contact_phone,p.patient_health_problem,p.patient_phone from "+
					"patient as p left join gender as g on p.patient_gender_id=g.gender_id left join organization as o on p.patient_organization_id=o.organization_id where p.patient_doctor_id=?";
		System.out.println("&*&*&*&&*&*"+userInfo.get("organization_id"));
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("organization_id")});
		
		return result;
	}



}
