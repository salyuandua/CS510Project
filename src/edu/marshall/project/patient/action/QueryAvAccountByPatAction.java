package edu.marshall.project.patient.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all bank accounts specific patient has
 * @author Xuejian Li
 *
 */
public class QueryAvAccountByPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select * from bank_account as ba left join organization as o on ba.bank_account_org_id=o.organization_id where ba.bank_account_patient=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{userInfo.get("user_id")});
		return result;
	}


}
