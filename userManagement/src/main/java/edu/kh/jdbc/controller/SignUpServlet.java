package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

	
	// 사용자 등록페이지로 전환
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 요청 위임
		String path = "/WEB-INF/views/signUp.jsp";
		req.getRequestDispatcher(path).forward(req, resp);
	}

	// 사용자 등록 요청처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// 제출된 파라미터 얻어오기a
						// 제출된 input의 name속성 값
		String userId = req.getParameter("userId");
		String userPw = req.getParameter("userPw");
		String userName = req.getParameter("userName");
		
		System.out.println(userId);
		System.out.println(userPw);
		System.out.println(userName);
		
		try {
			// 전달받은 파라미터를 한번에 저장할 User DTO객체 생성
			User user = new User();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// 서비스(INSERT) 호출 후 결과 반환받기
			// 결과(삽입된 행의 개수, int) 반환받기
			UserService service = new UserServiceImpl();
			int result = service.insertUser(user);
			
			// 결과에 따라 응답 방법처리
			String message = null;
			
			if(result >0) message = userId + "사용자 등록 성공";
			else		  message = "등록 실패";
			
			// page, request, session, application 중 
			// session을 이용해서 message 값 전달
			HttpSession session = req.getSession();
			
			session.setAttribute("message", message);
			
			// 메인페이지로 리다이렉트(재요청)
			resp.sendRedirect("/");
			
			// 메인페이지로 리다이렉트(재요청)
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	
	}
	
	
	
	
	
	
	
	
}
