package edu.marshall.project.doctor.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;
import edu.marshall.project.util.ProjectUtil;
/**
 * Add a prescription with transaction
 * @author Xuejian Li
 *
 */
public class InsertPrescriptionAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="insert into prescription (prescription_num,prescription_doctor_id,prescription_patient_id,prescription_date,prescription_decription) values(?,?,?,?,?)";
		Object[] params=new Object[]{ProjectUtil.prescriptionNumGenerter(),userInfo.get("organization_id"),param.get("patient_id"),ProjectUtil.getDate(),param.get("description")};
		Connection conn=ConnectionBuilder.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet resultSet=preparedStatement.getGeneratedKeys();
			int prescriptionId=0;
			if(resultSet.next()){
				prescriptionId=resultSet.getInt(1);
			}
			//add medicines
			JSONArray medArr=(JSONArray) param.get("medicine_list");
			for(int i=0;i<medArr.size();i++){
				sql="insert into prescription_medicine(prescription_medicine_prescription_id,prescription_medicine_medicine_id,prescription_medicine_req_mun) values(?,?,?)";
				JSONObject oneMed=medArr.getJSONObject(i);
				params=new Object[]{prescriptionId,oneMed.get("medicine_id"),oneMed.get("req_num")};
				preparedStatement=conn.prepareStatement(sql);
				DaoHelper.paramBuilder(params, preparedStatement);
				preparedStatement.executeUpdate();
			}
			conn.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return JSON.toJSONString(true);
	}


}
