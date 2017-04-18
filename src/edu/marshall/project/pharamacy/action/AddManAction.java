package edu.marshall.project.pharamacy.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;
/**
 * add a manufacturer to table "manufacturer"
 * @author Xuejian li
 *
 */
public class AddManAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		String sql="insert into manufacturer(manufacturer_name,manufacturer_address,manufacturer_phone) values(?,?,?)";
		Object[] params=new Object[]{param.get("ma_name"),param.get("address"),param.get("phone")};
		Connection conn=ConnectionBuilder.getConnection();
		try {
			PreparedStatement preparedStatement =conn.prepareStatement(sql);
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
