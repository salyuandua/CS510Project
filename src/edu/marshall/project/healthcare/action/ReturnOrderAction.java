package edu.marshall.project.healthcare.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;

public class ReturnOrderAction implements Action<String>{
	@Override
	public String excute(Map<String, Object> param) {
		String sql="update orders set orders_statue=? where orders_id=?";
		Object[] params=new Object[]{0,param.get("order_id")};
		Connection conn=ConnectionBuilder.getConnection();
		try {
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
