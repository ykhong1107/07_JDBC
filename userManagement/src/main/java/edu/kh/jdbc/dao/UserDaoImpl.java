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
		
		List<User> userList = new ArrayList<User>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String id = rs.getString("USER_ID");
				String pw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String userEnrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userEnrollDate, userEnrollDate, userName, userEnrollDate);
				
				userList.add(user);
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userList;
	}
	
	
	
	
}
