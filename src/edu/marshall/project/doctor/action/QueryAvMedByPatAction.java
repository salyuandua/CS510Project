package edu.marshall.project.doctor.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

public class QueryAvMedByPatAction implements Action<String> {

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from medicine as m left join manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id where m.medicine_id  not in ("+
				"select res.restriction_medicine_id from restriction as res where res.restriction_patient_id=?)";
		String result=new DaoHelper().selectV2(sql, new Object[]{param.get("patient_id")});
		return result;
	}


}
