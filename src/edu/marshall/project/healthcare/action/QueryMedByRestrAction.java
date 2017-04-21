package edu.marshall.project.healthcare.action;


import java.util.Map;

import com.alibaba.fastjson.JSONArray;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.DaoHelper;
/**
 * Get medicines except restriction medicines for a specific patient
 * @author Xuejian Li
 *
 */
public class QueryMedByRestrAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		//restriction medicines ids
		JSONArray idList=(JSONArray) param.get("idList");
		//System.out.println("1****"+idList.get(0));
		StringBuilder sql=new StringBuilder("select * from medicine as m left "
				+ "join manufacturer as ma on m.medicine_manufacturer_id=ma.manufacturer_id ");
				//+ "where m.medicine_id  not in (");
		int count=idList.size();
		Object[] params=null;
		if(count!=0){
			params=new Object[count];
			sql.append("where m.medicine_id  not in (");
			for(int i=0;i<count;i++){
				sql.append("?");
				if(i!=(idList.size()-1)){//not the final one 
					sql.append(",");
				}
				params[i]=idList.get(i);
			}
			sql.append(")");
		}
		

		String result=new DaoHelper().selectV2(sql.toString(), params);
		return result;
	}


}
