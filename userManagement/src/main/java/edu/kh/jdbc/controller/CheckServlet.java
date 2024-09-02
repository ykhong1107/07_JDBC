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
/* @ -> 어노테이션은 서블릿을 웹 에플리케이션의 /check 경로와
 * 매핑을 한다. */ 
public class CheckServlet extends HttpServlet{
// CheckServlet 클래스는 HttpServlet을 확장하여 HTTP요청을 처리한다.
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// doget 메서드는 HTTP GET 요청을 처리해준다!!!
		
		String str = req.getParameter("check");
		// 클라이먼트로부터 'check'라는 이름의 쿼리 파라미터값을 읽어온다
		// """ 클라이먼트로부터 """ VSCODE에 action="/check" 라고 되어있는 구문
		// str 이라는 변수로 check라는 이름의 쿼리 파라미터값을 읽어옴!!!
		
		try {
			UserService service = new UserServiceImpl();
			// UserService 의 service 라는 객체를 생성
			// new UserServiceImpl(); 을 하는 이유는
			// Service 인터페이스에 상속받아야만 하기때문에
			
			String kong = service.selectKong(str); // selectKong 에서 -> UserService로 갈 예정!
			// service 클래스에서 SelectKong 메서드를 호출하여 
			// 데이터베이스 또는 다른곳에서 값을 가져옴
			
			req.setAttribute("kong", kong);
			//               "객체명", 객체
			// 요청속성에 'kong'이라는 이름으로 값을 저장함!!!!!
			
			String path = "/WEB-INF/views/main.jsp";
			
			// 응답을 생성할 JSP 페이지의 경로 설정
			
			req.getRequestDispatcher(path).forward(req, resp);
		    // 요청을 지정된 JSP 페이지로 포워딩하여 응답을 생성!!!!!!
			
			
		}catch(Exception e) {
			e.printStackTrace();
			// 예외 발생 시 스택트레이스 출력
			
			
		}
		
		
		
		
	
	}
	
}
