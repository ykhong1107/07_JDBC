package edu.kh.jdbc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/selectAll")
public class SelectAllServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
		
		// 요청
		
		UserService service = new UserServiceImpl(); 
		
		List<User> userList = new ArrayList<User>();
		
		userList = service.selectAll();
		
		req.setAttribute("userList", userList);
		
		// 경로 지정 / 코드순서는 위에서 아래로
		String path = "/WEB-INF/views/selectAll.jsp";
		
		req.getRequestDispatcher(path).forward(req, resp);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
//		String userNo = req.getParameter("userNo");
//		String userId = req.getParameter("userId");
//		String userPw = req.getParameter("userPw");
//		String userName = req.getParameter("userName");
//		String enrollDate = req.getParameter("enrollDate");
		
	
	
	
	
	}
	
	
	
	
	
	
}
