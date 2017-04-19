package edu.marshall.project.bank.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;
import edu.marshall.project.util.ProjectUtil;
/**
 * Add an account
 * @author Xuejian Li
 *
 */
public class AddAccountAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		String sql="insert into bank_account(bank_account_num,bank_account_patient,bank_account_blance,bank_account_org_id) values(?,?,?,?)";
		Object[] params=new Object[]{ProjectUtil.getAccountNum(),param.get("patient_id"),param.get("deposit"),userInfo.get("organization_id")};
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
