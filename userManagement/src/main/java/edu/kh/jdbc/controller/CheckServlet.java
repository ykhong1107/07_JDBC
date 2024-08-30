package edu.kh.jdbc.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/check")
public class CheckServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String str = req.getParameter("check");
		
		try {
			UserService service = new UserServiceImpl();
			
			String kong = service.selectKong(str);
			
			req.setAttribute("kong", kong);
			
			String path = "/WEB-INF/views/main.jsp";
			
			req.getRequestDispatcher(path).forward(req, resp);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
			
			
			
		}
		
		
		
		
		
	
	}
	
}
