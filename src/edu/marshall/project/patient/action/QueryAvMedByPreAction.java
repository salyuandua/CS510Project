package edu.marshall.project.patient.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query purchasable medicines for a specific patient to order 
 * @author Xuejian Li
 *
 */
public class QueryAvMedByPreAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select * from prescription_medicine as pm left join "+
				"prescription as pres on pm.prescription_medicine_prescription_id=pres.prescription_id "+
				"left join  medicine as m on pm.prescription_medicine_id=m.medicine_id left join"+
				" manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id where pres.prescription_patient_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("user_id")});
		return result;
	}



}
