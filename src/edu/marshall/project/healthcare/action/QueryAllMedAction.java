package edu.marshall.project.healthcare.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all medicines which exist in database
 * @author Xuejian Li
 *
 */
public class QueryAllMedAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from medicine as m left join manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id";
		
		return new DaoHelper().selectV2(sql, null);
	}


}
