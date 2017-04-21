package edu.marshall.project.bank.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all accounts that exist in the same bank as user
 * @author Xuejian Li
 *
 */
public class QueryAllAccountAction implements Action<String>{
	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="select ba.*,if(p.patient_name_mid is null,concat(p.patient_name_first,' ',p.patient_name_last),concat(p.patient_name_first,' ',p.patient_name_mid,' ',p.patient_name_last)) as patient_name,p.patient_email,p.patient_id,p.patient_phone,o.organization_name from bank_account as ba left join patient as p on ba.bank_account_patient=p.patient_id left join organization as o on ba.bank_account_org_id=o.organization_id where ba.bank_account_org_id=?";
		return new DaoHelper().selectV2(sql, new Object[]{userInfo.get("organization_id")});
	}

}
