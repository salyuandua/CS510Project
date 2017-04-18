package edu.marshall.project.pharamacy.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;

/**
 * query all Manufacturers who exist in table "manufacturer"
 * @author Xuejian Li
 *
 */
public class QueryAllManufacturerAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from manufacturer";
		
		return new DaoHelper().selectV2(sql, null);
	}



}
