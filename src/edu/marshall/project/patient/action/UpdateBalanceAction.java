package edu.marshall.project.patient.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;

/**
 * deposit balance for an account
 * @author l1876
 *
 */
public class UpdateBalanceAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		
		String sql="update bank_account set bank_account_blance=bank_account_blance+"+param.get("deposite")+" where bank_account_id=?";
		Object[] params=new Object[]{param.get("bank_account_id")};
		Connection conn=ConnectionBuilder.getConnection();
		try {
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			//return JSON.toJSONString(true);
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
