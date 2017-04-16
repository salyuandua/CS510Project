package edu.marshall.project.patient.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Query all medicines that belong to a specific order
 * @author l1876
 *
 */
public class QueryMedByOrderAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="select * from order_medicine as om left join "
				+ "medicine as m on om.order_medicine_id=m.medicine_id left join "
				+ "manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id "
				+ "where om.order_medicine_order_id=?";
		String result=new DaoHelper().selectV2(sql, new Object[]{param.get("orders_id")});
		return result;
	}



}
