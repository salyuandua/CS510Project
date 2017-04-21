package edu.marshall.project.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.ResultSetDynaClass;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.db.ConnectionBuilder;
/**
 * A useful JDBC until class for database operations(select,update,delete,insert)
 * Note: sql must be like "select * from table where attr1=? and attr2=?" Use ? to protect
 * database from rejecting attack.
 * @author Xuejian Li
 * 
 */


public class DaoHelper {
private Connection conn;
private PreparedStatement preStatement;
private Statement statement;
private ResultSetDynaClass resultSetDClass;
private DynaBean bean;
private Map<String, Object> singleData;
private List<Map<String, Object>> results;

private static int IS_INSERT=0;
private static int IS_UPDATE=1;
private static int IS_DELETE=2;
private static int IS_SELECT=3;




/**
 * select result by given sql. note: this method and method select has a problem when sql contains any 
 * rename operation because there is an issue in "dbuntils" framework, so command use selectV2 
 *  as a placement.
 * @param sql
 * @return result of List
 * @see selectV2
 */
public List<Map<String, Object>> selectForList(String sql, Object[] params){
	System.out.println("SQL IS "+sql);
	if(sql==null||sql.equals("")||sql.toString().trim().equals("")){
		try {
			throw new SQLException("given SQL is wrong!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	if(conn==null){
		conn=ConnectionBuilder.getConnection();
	}
	try {
		preStatement=conn.prepareStatement(sql);
		if(params!=null){
		if(!paramsBuilder(params)){
			throw new SQLException("build SQL fail!");
		}
		}
		ResultSet resultSet=preStatement.executeQuery();
		resultSetDClass=new ResultSetDynaClass(resultSet);
		DynaProperty p[]=resultSetDClass.getDynaProperties();		
		Iterator<DynaBean> row=resultSetDClass.iterator();		
		Object value;
		String propertyName="";	
		results=new ArrayList<>();
		while(row.hasNext()){
			singleData=new HashMap<String,Object>();
			bean=row.next();
			for(int i=0;i<p.length;i++){
				propertyName=p[i].getName();
				value= bean.get(propertyName);
				singleData.put(propertyName, value);
			}
			results.add(singleData);
		}
		//gson=new Gson();
		
		preStatement.close();
		conn.close();
//		String json=JSON.toJSONString(results);
//		System.out.println("RESULT IS "+json);
		return results;
	} catch (SQLException e) {
		e.printStackTrace();
		try {
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}	
}

public String select(String sql, Object[] params){
	selectForList(sql, params);
	String json=JSON.toJSONString(results);
	System.out.println("RESULT IS "+json);
	return json;
}

/**
 * if any rename(as) operation contained in sql, use this method or selectV2 to get a right result
 * 
 */
public List<Map<String, Object>> selectForListV2(String sql,Object[] params){
	System.out.println("SQL IS: "+sql);
	if(sql==null||sql.equals("")||sql.toString().trim().equals("")){
		try {
			throw new SQLException("given SQL is wrong!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	try {
		if(conn==null||conn.isClosed()){
			conn=ConnectionBuilder.getConnection();
		}
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

	try {
		preStatement=conn.prepareStatement(sql);
		if(params!=null){
		if(!paramsBuilder(params)){
			throw new SQLException("build SQL fail!");
		}
		}
		
		ResultSet resultSet=preStatement.executeQuery();
		ResultSetMetaData metaData=resultSet.getMetaData();
		int columnCount=metaData.getColumnCount();
		Map<String, Object> oneResult=null;
		List<Map<String, Object>> resultList=new LinkedList<Map<String, Object>>();
		while(resultSet.next()){
			oneResult=new HashMap<String, Object>();
			for(int i=1;i<=columnCount;i++){
				String key=metaData.getColumnLabel(i);
				Object value=resultSet.getObject(i);
				//System.out.println(key+"***"+value);
				if(value==null){
					value="";
				}
				oneResult.put(key, value);
			}
			resultList.add(oneResult);
		}
		preStatement.close();
		conn.close();

		return resultList;
	} catch (SQLException e) {
		e.printStackTrace();
		try {
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}	
}
/**
 * 
 * @param sql
 * @param params
 * @return 
 */
public String selectV2(String sql,Object[] params){
	results=selectForListV2(sql, params);
	String json=JSON.toJSONString(results);
	System.out.println("RESULT IS "+json);
	return json;
}
/**
 * get the max auto_creament id by sql
 */
public int getMaxId(String sql){
	if(conn==null){
		conn=ConnectionBuilder.getConnection();
	}
	try {
		preStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet=preStatement.executeQuery();
		return resultSet.getInt(0);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
}

/**
 * insert data expands sqls and params
 * @return true if successful, flase if fail
 */
//public boolean insert(String[] sqls,ArrayList<Object[]> paramsArr){
//	if(sqls.length==0){
//		throw new NullPointerException("sql is empty");
//	}
//	try{
//	if(conn==null){//create connection
//		conn=ConnectionBuilder.getConnection();
//	}
//	conn.setAutoCommit(false);
//	String sql="";
//	for(int i=0;i<sqls.length;i++){
//		sql=sqls[i];
//		preStatement=conn.prepareStatement(sql);
//		
//		if(paramsBuilder(paramsArr.get(i))){
//			preStatement.executeUpdate();
//			
//		}else{
//			
//			conn.setAutoCommit(true);
//			conn.close();
//			return false;
//		}	
//	}
//	}catch(SQLException e){
//		
//	}
//	return true;
//}




	public String update(ArrayList<String> sqls,ArrayList<Object[]> paramsArr){
		if(sqls.size()==0||(sqls.size()!=paramsArr.size())){
			throw new NullPointerException("sqls is wrong!");
		}
		try {
		if(conn==null){
			conn=ConnectionBuilder.getConnection();			

		} 
		conn.setAutoCommit(false);
		
		for(int i=0;i<sqls.size();i++){
			System.out.println("SQL IS :"+sqls.get(i));
			preStatement=conn.prepareStatement(sqls.get(i));
			if(!paramsBuilder(paramsArr.get(i))){
				conn.rollback();
				break;
			}
			preStatement.executeUpdate();
		}
		conn.commit();
		return JSON.toJSONString("{\"state\":true}");
		
		}catch (SQLException e) {
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
				preStatement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return JSON.toJSONString("{\"state\":false}");
	}
	/*
	 * delete by sql
	 * */
//	public boolean delete(String sql,Object[] param){
//		System.out.println("SQL IS: "+sql);
//		if(sql==null||sql.equals("")||sql.toString().trim().equals("")){
//			try {
//				throw new SQLException("given SQL is wrong!");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return false;
//			}
//		}
//		if(conn==null){
//			conn=ConnectionBuilder.getConnection();
//		}
//		return true;
//		
//		
//	}
	
	
	
	
	
	
	
/**
 * determine type of operation	
 * @param args
 */
	private int operationType(String sql){
		sql=sql.toLowerCase();
		if(sql.contains("insert")){
			return IS_INSERT;
		}
		if(sql.contains("update")){
			return IS_UPDATE;
		}
		if(sql.contains("delete")){
			return IS_DELETE;
		}
		String[] sArr=sql.split(" ");
		try {
			throw new SQLException("not support such operation: "+sArr[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * build a proper SQL
	 * @param args
	 */
private boolean paramsBuilder(Object[] params){		
	//System.out.println(params.length);
		for(int i=0;i<params.length;i++){
			try {
			if(params[i] instanceof Integer){//int-> " and xx=param[i] 
				preStatement.setInt(i+1, (int) params[i]);

			}
			if(params[i] instanceof Double){
				preStatement.setDouble(i+1, (double) params[i]);
			}
			if(params[i] instanceof String){
				preStatement.setString(i+1, (String) params[i]);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	
}
public static boolean paramBuilder(Object[] params,PreparedStatement preStatement){		
	//System.out.println(params.length);
		for(int i=0;i<params.length;i++){
			try {
			if(params[i] instanceof Integer){//int-> " and xx=param[i] 
				preStatement.setInt(i+1, (int) params[i]);

			}
			if(params[i] instanceof Double){
				preStatement.setDouble(i+1, (double) params[i]);
			}
			if(params[i] instanceof String){
				preStatement.setString(i+1, (String) params[i]);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	
}
public static void main(String[] args) {
int id=new DaoHelper().getMaxId("select * from doctor");
System.out.println(id);

	
}
}
