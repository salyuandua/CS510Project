package edu.marshall.project.doctor.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query medicines that contained in patient's prescription.
 * @author Xuejian Li
 *
 */
public class QueryMedByPreAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from prescription_medicine as prem left join medicine as m on prem.prescription_medicine_medicine_id=m.medicine_id"+
				" left join manufacturer as ma  on m.medicine_manufacturer_id=ma.manufacturer_id where prem.prescription_medicine_prescription_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{param.get("prescription_id")});
		return result;
	}
	


}
