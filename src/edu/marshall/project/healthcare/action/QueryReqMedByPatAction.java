package edu.marshall.project.healthcare.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query patient's requiring medicines
 * @author Xuejian Li
 *
 */
public class QueryReqMedByPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select prm.*,m.*,ma.* from patient_req_medicines as prm left join "
				+ "medicine as m on prm.patient_req_medicines_medicine_id=m.medicine_id "
				+ "left join manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id "
				+ "where prm.patient_req_medicines_patient_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{param.get("patient_id")});
		return result;
	}



}
