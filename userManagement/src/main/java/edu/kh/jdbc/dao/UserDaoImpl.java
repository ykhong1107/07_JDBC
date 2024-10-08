package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

public class UserDaoImpl implements UserDao{

	// 필드
	
	// JDBC 객체 참조변수 + Properties 참조변수 선언!
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	// -> K,V가 모두 String인 Map, 파일 입출력이 쉬움
	
	// 기본 생성자 -- 생성될 때 실행되는 메서드
	public UserDaoImpl() {
		
		// 객체생성시 
		// 외부에 존재하는 sql.xml 파일을 읽어와
		// prop에 저장
		
		try {
			String filePath = 
					JDBCTemplate.class
					.getResource("/edu/kh/jdbc/sql/sql.xml").getPath();
			
			// 지정된 경로의 XML 파일내용을 읽어와
			// Properties 객체에 K:V 세팅
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int insertUser(Connection conn, User user) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성  
			// -> properties를 이용해 외부 sql.xml 파일에서
			//	  읽어온 sql 이용
			String sql = prop.getProperty("insertUser");
			
			// 3. PreparedStatement (플레이스홀더 3개라 써야함) 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 에 알맞은 값 세팅
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate()) 후
			//    결과(삽입된 행의 개수, int) 반환
			result = pstmt.executeUpdate();
			
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
			
		}
		
		
		return result;
	}

	@Override
	public int idCheck(Connection conn, String userId) throws Exception {
		
		// 결과 저장할 변수 선언
		int result = 0;
		
		try {
			String sql = prop.getProperty("idCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 결과가 1행밖에 없어서 if 사용
				result = rs.getInt(1); // 조회결과 1번 컬럼 값 얻어옴
			}
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		return result;
	}
	
	@Override
	public User login(Connection conn, String userId, String userPw) throws Exception {

		// 결과 저장용 변수선언
		User loginUser = null;
		
		try {
			
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int useNo = rs.getInt("USER_NO");
				String id = rs.getString("USER_ID");
				String pw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				loginUser = 
					new User(useNo, userId, userPw, userName, enrollDate);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return loginUser;
	}

	@Override
	public List<User> selectAll(Connection conn) throws Exception {
		
		// 결과 저장용 변수 선언 // ArrayList 객체를 만든 이유
		// SQL얻어올 예정
		List<User> userList = new ArrayList<User>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			// 강사님이 한 코드
			// stmt = conn.createStatement();
			// rs = stmt.executeQuery(sql); 
			
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String userEnrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userId, userPw, userName, userEnrollDate);
				
				userList.add(user);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userList;
	}
	
	@Override
	public List<User> search(Connection conn, String searchId) throws Exception {

		// ArrayList 객체를 미리 생성하는 이유
		// == 조회된 결과를 추가(add)해서 묶어 반환하기 위해
		List<User> userList = new ArrayList<User>();
		
try {
			
			String sql = prop.getProperty("search");
			
			// 강사님이 한 코드
			// stmt = conn.createStatement();
			// rs = stmt.executeQuery(sql); 
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchId); // '%검색어%'
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String userEnrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userId, userPw, userName, userEnrollDate);
				
				userList.add(user);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userList;
	}
	
	@Override
	public User selectUser(Connection conn, int userNo) throws Exception {
		
		User userOne = null;
		
		try {
			String sql = prop.getProperty("selectUser");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String userEnrollDate = rs.getString("ENROLL_DATE");
				
				userOne = new User(userNo, userId, userPw, userName, userEnrollDate);
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return userOne;
	}
	
	/**
	 *  사용자 삭제
	 */
	@Override
	public int deleteUser(Connection conn, int userNo) throws Exception {
		
		// 결과 저장용변수 
		int result = 0; // 왜 0이냐? 결과 행을 저장할거니까 - delete 결과 행 개수 저장
		
		try {
			String sql = prop.getProperty("deleteUser");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			// DML은 executeUpdate() 호출
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	@Override
	public int updateUser(Connection conn, User user) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("updateUser");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getUserPw());
			pstmt.setInt(3, user.getUserNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	@Override
	public String selectKong(Connection conn, String str) throws Exception {
		// @Override : 부모클래스 또는 인터페이스의 메서드를 재정의하고 있음을 나타냄
		// String 타입의 결과를 반환, Connection 객체와 String 타입의 매개변수를 받음
		
		String kong = null;
		// 데이터베이스에서 조회한결과를 저장할 변수, 초기값은 'null'로 설정!!
		try {
			String sql = prop.getProperty("selectKong");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, str);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
// 담아갈 값이 담겨있는 변수이름 왼쪽  || 담아가고싶은 내용 or 값 or 결과 오른쪽 
				kong = rs.getString("USER_NAME");
				
			}
		}finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		
		return kong;
	}
	
}
