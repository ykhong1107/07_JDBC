package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {

	public static void main(String[] args) {
		
		// 입력받은 급여보다 초과해서 받는 사원의
		// 사번, 이름, 급여조회
		
		/* 1. JDBC 객체 참조용 변수선언 */
		Connection conn = null; // DB 연결정보 저장객체
		Statement  stmt = null; // SQL 수행, 결과반환용 객체
		ResultSet  rs   = null; // SELECT 수행결과 저장객체
		
		
		try {
			
			/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
			
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재)하기 */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			/* 2-2) DB 연결 정보(ip,port,id...) 작성*/
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
			String port = ":1521"; // 프로그램 연결을 위한 구분 번호
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			String userName = "KH_HYK"; // 사용자 계정명
			String password = "KH1234"; // 계정 비밀번호
			
			/* 2-3) DB 연결 정보와 DriverManager를 이용해서 
			 * Connection 객체 생성*/
			conn = DriverManager.getConnection(
					type + host + port + dbName, 
					userName, 
					password);
			
//			System.out.println(conn);
			
			/* 3. SQL 작성 */
			Scanner sc = new Scanner(System.in);
			
			System.out.println("급여 입력 : ");
			int input = sc.nextInt();
			
			String sql = """
			SELECT EMP_ID, EMP_NAME, SALARY
			FROM EMPLOYEE
			WHERE SALARY > """ + input;
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환받기 */
			rs = stmt.executeQuery(sql);
			
			// executeQuery()  : SELECT 실행, ResultSet 반환
			// executeUpdate() : DML실행, 결과행의 개수 반환(int) 
			
			/* 6. 조회 결과가 담겨있는 ResultSet을
			 * 커서(Cursor)를 이용해 1행씩 접근해
			 * 각 행에 작성된 컬럼 값 얻어오기
			 *  */
			while(rs.next()) {
				// rs.next() : 커서를 다음행으로 이동
				// 값이 있으면 true, 없으면 false 반환
				
				String empId 	= rs.getString("EMP_ID");
				String empName  = rs.getString("EMP_NAME");
				int    salary   = rs.getInt   ("SALARY");
				
				System.out.printf("%s / %s / %d원 \n"
						,empId, empName, salary);
			}
			
			
		}catch(Exception e) {
			// 최상위 예외인 Exception을 이용해서 모든 예외를 처리하겠다
			// -> 다형성의 업캐스팅 적용
			e.printStackTrace();
			
		}finally {
			/* 7. 사용완료된 JDBC 객체 자원반환(close) */
			/* 생성된 역순으로 close 수행 */
			try {
				if(rs   != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		
		
	}
	
}
