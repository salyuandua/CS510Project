package edu.marshall.project.infocenter.action;

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

public class AddAdminAction implements Action<String>{
private Map<String, Object> param;
private Connection conn;
	@Override
	public String excute(Map<String, Object> param) {
		this.param=param;
		String org_info=(String) param.get("org");
		System.out.println("ORG_INFO IS"+org_info);
		String[] temp=org_info.split("\\$");
		String org_id=temp[0];
		String org_type_id=temp[1];
		conn=ConnectionBuilder.getConnection();
		if(org_type_id.equals("1")){//health care system admin
			return JSON.toJSONString(this.addHealthCareSystemAdmin(org_id));
		}else if(org_type_id.equals("2")){
			return JSON.toJSONString(this.addPharmacyAdmin(org_id));
		}else{
			return JSON.toJSONString(this.addBankAdmin(org_id));
		}
	
	}
	private boolean addBankAdmin(String orgId){
		String sql="insert into bank_admin(bank_admin_name_first,bank_admin_name_mid,bank_admin_name_last,bank_admin_gender_id,bank_admin_username,bank_admin_password,bank_admin_organization_id) values(?,?,?,?,?,?,?)";
		Object[] params=new Object[]{param.get("first_name"),param.get("mid_name"),param.get("last_name"),param.get("gender_id"),param.get("user_name"),param.get("password"),orgId};
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet resultSet=preparedStatement.getGeneratedKeys();
			int adminId=0;
			while(resultSet.next()){
				adminId=resultSet.getInt(1);
			}
			//add application
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+adminId+",5,3)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		return true;
	}
	
	
	
	private boolean addPharmacyAdmin(String orgId){
		String sql="insert into pharmacy_admin(pharmacy_admin_name_first,pharmacy_admin_name_mid,pharmacy_admin_name_last,pharmacy_admin_gender_id,pharmacy_admin_username,pharmacy_admin_password,pharmacy_admin_organization_id) values(?,?,?,?,?,?,?)";
		Object[] params=new Object[]{param.get("first_name"),param.get("mid_name"),param.get("last_name"),param.get("gender_id"),param.get("user_name"),param.get("password"),orgId};
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet resultSet=preparedStatement.getGeneratedKeys();
			int adminId=0;
			while(resultSet.next()){
				adminId=resultSet.getInt(1);
			}
			//add application
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+adminId+",4,8)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+adminId+",4,9)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		return true;
	}
	
	
	
	private boolean addHealthCareSystemAdmin(String orgId){
		String sql="insert into health_care_system_admin(health_care_system_admin_name_first,health_care_system_admin_name_mid,health_care_system_admin_name_last,health_care_system_admin_gender_id,health_care_system_admin_username,health_care_system_admin_password,health_care_system_admin_organization_id) values(?,?,?,?,?,?,?)";
		Object[] params=new Object[]{param.get("first_name"),param.get("mid_name"),param.get("last_name"),param.get("gender_id"),param.get("user_name"),param.get("password"),orgId};
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet resultSet=preparedStatement.getGeneratedKeys();
			int adminId=0;
			while(resultSet.next()){
				adminId=resultSet.getInt(1);
			}
			//add application
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+adminId+",3,2)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			sql="insert into app_user(app_user_user_id,app_user_user_type_id,app_user_app_id) values("+adminId+",3,5)";
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		return true;
	}
	public static void main(String[] args) {
		String aa="1$1";
		String[] bb=aa.split("\\$");
		System.out.println(bb.length);
		for(int i=0;i<bb.length;i++){
			System.out.println(bb[i]);
		}
	}
}
