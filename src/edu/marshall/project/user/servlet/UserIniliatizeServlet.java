package edu.marshall.project.user.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.base.servlet.BaseServlet;
import edu.marshall.project.user.action.UserIniliatizeAction;

/**
 * Servlet implementation class UserIniliatizeServlet
 */
@WebServlet("/app/initialize")
public class UserIniliatizeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIniliatizeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see initialize home page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> userInfo=(Map<String, Object>)request.getSession().getAttribute("userInfo");
		System.out.println(JSON.toJSONString(userInfo));//{"patient_username":"as","patient_id":1,
		//"patient_gender_id":2,"patient_password":"as","patient_name_first":"Rose","patient_name_last":"Donlad",
		//"user_type_id":"1","patient_name_mid":"H","tablename":"patient","patient_doctor_id":1}
		String result=new UserIniliatizeAction().excute(userInfo);
		response.getWriter().write(result);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
