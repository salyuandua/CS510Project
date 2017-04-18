package edu.marshall.project.pharamacy.action;

import java.sql.Connection;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;

/**
 * add a medicine to the table "medicine"
 * @author Xuejian Li
 *
 */
public class AddMedAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Connection conn=ConnectionBuilder.getConnection();
		String sql="insert into medicine(medicine_name,medicine_available_count,medicine_price,medicine_manufacturer_id) values(?,?,?,?)";
		Object[] params=new Object[]{param.get("med_name"),param.get("med_stock"),param.get("med_price"),param.get("ma_id")};
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
