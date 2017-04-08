package edu.marshall.project.base.servlet;

import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.marshall.project.base.action.Action;
import edu.marshall.project.util.ValiateUtil;
import edu.marshall.project.util.XMLUtil;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/app/base")
public class BaseServlet extends HttpServlet {

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
		String result=(String)doAction(request);
		response.getWriter().write(result);
		response.getWriter().flush();
		response.getWriter().close();
	}
	protected Object doAction(HttpServletRequest request){
		String action=request.getParameter("action");
		String actionClassName=XMLUtil.findActionClass(action);
		Object result="";
		try {
			Class<Action> tempClass=(Class<Action>) Class.forName(actionClassName);
			Action actionClass=tempClass.newInstance();
			result=(String)actionClass.excute(request.getParameter("param"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//excute action by annotion
	protected String doActionWithAnnotion(HttpServletRequest request){
		String action=request.getParameter("action");
		return "";
	}
	
}
