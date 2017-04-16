package edu.marshall.project.patient.action;

import java.sql.Connection;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.db.ConnectionBuilder;
import edu.marshall.project.util.DaoHelper;
import edu.marshall.project.util.ProjectUtil;
/**
 * Add a order,this order belongs to this patient who has signed in
 * @author Xuejian Li
 *
 */
public class AddOrderAction implements Action<String>{

	@Override
	public String excute(Map<String, Object> param) {
		Map<String, Object> userInfo=(Map<String, Object>) param.get("userInfo");
		//query balance is enough or not
		Connection conn=ConnectionBuilder.getConnection();
		String sql="select bank_account_blance from bank_account where bank_account_id=?";
		Object[] params=new Object[]{param.get("account_id")};
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement=conn.prepareStatement(sql);
			DaoHelper.paramBuilder(params, preparedStatement);
			ResultSet resultSet=preparedStatement.executeQuery();
			double balance=0;
			while(resultSet.next()){
				balance=resultSet.getDouble("bank_account_blance");
			}
			double reqBlance=Double.parseDouble((String)param.get("total"));
			if(balance<reqBlance){//if balance is not enough, return
				return JSON.toJSONString("BALANCE_NOT_EN");
			}
			//charge cost
			double charge=balance-reqBlance;
			sql="update bank_account set bank_account_blance=? where bank_account_id=?";
			params=new Object[]{charge,param.get("account_id")};
			preparedStatement=conn.prepareStatement(sql);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			//insert data into main table
			sql="insert into orders(orders_num,orders_total_price,orders_tax,orders_date,orders_patient_id,orders_account_id) values (?,?,?,?,?,?)";
			params=new Object[]{ProjectUtil.orderNumGenerter(),param.get("total"),param.get("tax"),ProjectUtil.getDate(),userInfo.get("user_id"),param.get("account_id")};
			preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			DaoHelper.paramBuilder(params, preparedStatement);
			preparedStatement.executeUpdate();
			int orderId=0;
			resultSet=preparedStatement.getGeneratedKeys();
			while(resultSet.next()){
				orderId=resultSet.getInt(1);
			}
			//insert data into child table
			sql="update bank_account set bank_account_blance=? where bank_account_id=?";
			JSONArray medList=(JSONArray) param.get("med_list");
			for(int i=0;i<medList.size();i++){
				JSONObject oneMed=medList.getJSONObject(i);
				sql="insert into order_medicine(order_medicine_order_id,order_medicine_medicine_id,order_medicine_req_num) values(?,?,?)";
				params=new Object[]{orderId,oneMed.get("medicine_id"),oneMed.get("num")};
				preparedStatement=conn.prepareStatement(sql);
				DaoHelper.paramBuilder(params, preparedStatement);
				preparedStatement.executeUpdate();
			}
			conn.commit();
			return JSON.toJSONString(true);
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
		return JSON.toJSONString(false);
	}



}
