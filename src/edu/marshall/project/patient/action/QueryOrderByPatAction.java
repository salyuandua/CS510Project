package edu.marshall.project.patient.action;

import java.util.Map;

import edu.marshall.project.base.action.Action;
/**
 * query orders history that belong to the patient who has logged in
 * @author Xuejian Li
 *
 */
public class QueryOrderByPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		return null;
	}



}
