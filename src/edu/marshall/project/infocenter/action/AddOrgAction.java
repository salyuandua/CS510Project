package edu.marshall.project.infocenter.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;

public class AddOrgAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="insert into organization(organization_name,organization_type_id,organization_address,organization_phone) values(?,?,?,?)";
		Object[] params=new Object[]{param.get("org_name"),param.get("org_type"),param.get("address"),param.get("phone")};
		Connection conn=ConnectionBuilder.getConnection();
		try {
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return JSON.toJSONString(true);
	}



}
