package edu.marshall.project.healthcare.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query restrictions by a specific patient
 * @author Xuejian Li
 *
 */
public class QueryRestrByPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select m.*,ma.* from restriction as r left join medicine as m on r.restriction_medicine_id=m.medicine_id left join manufacturer as ma on"
					+" m.medicine_manufacturer_id=ma.manufacturer_id where r.restriction_patient_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{param.get("patient_id")});
		return result;
	}


}
