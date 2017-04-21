package edu.marshall.project.healthcare.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;
/**
 * Add a doctor 
 * @author Xuejian Li
 *
 */
public class AddDoctorAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		Connection conn=ConnectionBuilder.getConnection();
		String sql="insert into doctor(doctor_name_first,doctor_name_last,"
				+ "doctor_name_mid,doctor_gender_id,doctor_username,doctor_password,"
				+ "doctor_organization_id) values (?,?,?,?,?,?,?)";
		Object[] params=new Object[]{param.get("first_name"),param.get("last_name"),
				param.get("mid_name"),param.get("gender_id"),param.get("user_name"),
				param.get("password"),userInfo.get("organization_id")};
	try {
		conn.setAutoCommit(false);
		PreparedStatement preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		DaoHelper.paramBuilder(params, preparedStatement);
		preparedStatement.executeUpdate();

		ResultSet ids=preparedStatement.getGeneratedKeys();
		int doctorId=0;
		if(ids.next()){
			doctorId=ids.getInt(1);
		}
		sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values(?,?,?)";
		params=new Object[]{doctorId,2,4};
		preparedStatement=conn.prepareStatement(sql);
		DaoHelper.paramBuilder(params, preparedStatement);
		preparedStatement.executeUpdate();
		conn.commit();
		//return JSON.toJSONString(true);
		//conn.setAutoCommit(true);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}finally {
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
