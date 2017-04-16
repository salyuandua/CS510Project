package edu.marshall.project.patient.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * query orders history that belong to the patient who has logged in
 * @author Xuejian Li
 *
 */
public class QueryOrderByPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select o.*,date_format(o.orders_date,'%M/%d/%Y') as order_date,ba.bank_account_num,org.organization_name from "
				+ "orders as o left join bank_account as ba on o.orders_account_id=ba.bank_account_id "
				+ "left join organization as org on ba.bank_account_org_id=org.organization_id "
				+ "where o.orders_patient_id=?";
		Object[] params=new Object[]{userInfo.get("user_id")};
		String result=new DaoHelper().selectV2(sql, params);
		return result;
	}



}
