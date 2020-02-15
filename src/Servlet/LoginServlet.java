package Servlet;
//下面三个是servlet必须引入的三个类
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


import Servlet.DataService;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {//构造函数
		super();
	}
	
	public void init(ServletConfig config) throws ServletException {//该servlet装载到容器（tomcat）后将自动执行的初始方法
	//放置初始化代码
	}
	
	public void destroy() {//销毁时调用的方法
		super.destroy();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("学号");
		String password = request.getParameter("密码");
		System.out.println("username: " + username + "password: " + password);
//		调用DataService类中的trylogin函数，检查密码正确性并分配tokenid
		String token = DataService.tryLogin(username, password);
		System.out.println("该学生学号和token" + username + " " + token);
		if(token != "fail") {
			response.getWriter().append(token);
		}
		else
			response.getWriter().append("登录失败");
	}
}
