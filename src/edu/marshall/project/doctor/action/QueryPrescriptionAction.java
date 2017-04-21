package edu.marshall.project.doctor.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all prescription that a specific doctor makes
 * @author Xuejian Li
 *
 */
public class QueryPrescriptionAction implements Action<String>{


	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select pre.*,date_format(pre.prescription_date,'%M/%d/%Y') as real_date,p.patient_id,if(p.patient_name_mid is null,concat(p.patient_name_first,' ',p.patient_name_last),concat(p.patient_name_first,' ',p.patient_name_mid,p.patient_name_last)) as patient_name,g.gender_name"
				+" from prescription as pre left join patient as p on pre.prescription_patient_id=p.patient_id left join gender as g on p.patient_gender_id=g.gender_id where pre.prescription_doctor_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("user_id")});
		return result;
	}


}
