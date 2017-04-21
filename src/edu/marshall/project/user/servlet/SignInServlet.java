package edu.marshall.project.user.servlet;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.base.servlet.BaseServlet;
import edu.marshall.project.user.action.SignInAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * user sign in
 */
@WebServlet("/user/signin")
public class SignInServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SignInServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> param=JSON.parseObject(request.getParameter("param"), HashMap.class);
		Map<String,Object> userInfo=new SignInAction().excute(param);
		if(userInfo!=null){//success
			//add this user to user list
			ArrayList<Map<String,Object>> userList=(ArrayList<Map<String,Object>>)request.getServletContext().getAttribute("userList");
			userList.add(userInfo);
			request.getSession().setAttribute("userInfo", userInfo);
			//System.out.println(request.getServerName()+"**"+request.getLocalName());	
//			RequestDispatcher requestDispatcher=request.getRequestDispatcher("/CS510Project/app/home.html");
//			requestDispatcher.forward(request, response);
			//response.sendRedirect("/CS510Project/app/home.html");
			//response.sendRedirect("/");
			//request.getServletContext().setAttribute("userList", userList);
			//System.out.println(userList.get(0));
			response.getWriter().print("{\"message\":\"success\"}");
			response.getWriter().flush();
			response.getWriter().close();
		}else{//fail username and pwd not match
			response.getWriter().print("{\"message\":\"User name and password not match\"}");
			response.getWriter().flush();
			response.getWriter().close();
			
			
		}
	}

}
