package edu.marshall.project.healthcare.action;

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

public class AddPatAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Connection conn=ConnectionBuilder.getConnection();
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="insert into patient(patient_name_first,patient_name_mid,patient_name_last,patient_gender_id,"
				+ "patient_doctor_id,patient_username,patient_password,patient_organization_id,patient_phone,"
				+ "patient_email,patient_emg_contact_name,patient_emg_contact_phone,patient_health_problem) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramForMainTab=new Object[]{param.get("first_name"),param.get("mid_name"),param.get("last_name"),param.get("gender_id"),
				param.get("doctor_id"),param.get("user_name"),param.get("pwd"),userInfo.get("organization_id"),
				param.get("phone"),param.get("email"),param.get("ec_name"),param.get("ec_phone"),param.get("prob_dec")};
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(paramForMainTab, preparedStatement);
			//insert 
			preparedStatement.executeUpdate();
			ResultSet resultSet=preparedStatement.getGeneratedKeys();
			//get id
			int patientId=0;
			while(resultSet.next()){
				patientId=resultSet.getInt(1);
			}
			//get restriction medicines
			JSONArray restrMed=(JSONArray) param.get("restr_list");
			for(int i=0;i<restrMed.size();i++){
				sql="insert into restriction(restriction_patient_id,restriction_medicine_id) values(?,?)";
				Object[] paramForRestr=new Object[]{patientId,restrMed.get(i)};
				preparedStatement=conn.prepareStatement(sql);
				DaoHelper.paramBuilder(paramForRestr, preparedStatement);
				preparedStatement.executeUpdate();
			}
			//get req medicines
			JSONArray reqMed=(JSONArray) param.get("req_med_list");
			for(int i=0;i<reqMed.size();i++){
				JSONObject oneNode=reqMed.getJSONObject(i);
				sql="insert into patient_req_medicines(patient_req_medicines_patient_id,patient_req_medicines_medicine_id,patient_req_medicines_req_num) "
						+ "values(?,?,?)";
				Object[] paramForReqMed=new Object[]{patientId,oneNode.get("medicine_id"),oneNode.get("re_num")};
				preparedStatement=conn.prepareStatement(sql);
				DaoHelper.paramBuilder(paramForReqMed, preparedStatement);
				preparedStatement.executeUpdate();
			}
			//add application
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+patientId+",1,1)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+patientId+",1,7)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			conn.commit();
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
