package edu.kh.jdbc.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.tree.ExpandVetoException;

import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signUp/idCheck")
public class IdCheckServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
		
		// 비동기로 /signUp/idCheck GET 방식 요천 시
		// 응답으로 HTML 코드(문서)가 아닌
		// 특정 값만 반환하는 코드 작성
		
		// 전달받은 파라미터 얻어오기
		String userId = req.getParameter("userId");
		
		// 테스트용 코드
//		int result = 0;
//		if(userId.equals("user01")) result = 1;
		
		// 중복확인 서비스 호출 후 결과 반환받기
		UserService service = new UserServiceImpl();
		int result = service.idCheck(userId);
		
		
		// HTML이 아니라 "값" 을 반환하기 위한 응답세팅
		// - application/json : (쉬운해석) JS에서 사용 가능한 값
		resp.setContentType("application/json; charset=UTF-8");
		
		// 클라이언트와 연결된 출력용 스트림 얻어오기
		PrintWriter out = resp.getWriter();
		out.print(result);
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
