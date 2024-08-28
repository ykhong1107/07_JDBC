package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

// DAO(Data Access Object) : 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java 에서 원하는 결과를 얻기위해
// 	  SQL을 수행하고 결과 반환받는 역할

public class UserDao {
	
	// 필드
	// - DB 접근관련한 JDBC 객체 참조형 변수를 미리선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 전달받은 Connection을 이용해 DB에 접근하여
	 *  전달받은 아이디와 일치하는 User 정보 조회하기
	 * @param conn  : Service에서 생성한 Connection 객체
	 * @param input : View에서 입력받은 아이디
	 * @return
	 */
	public User selectId(Connection conn, String input) {
		
		User user = null; // 결과 저장용 변수
		
		try {
			// SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, input);
			
			// SQL 수행 후 결과 반환받기
			rs = pstmt.executeQuery();
			
			// 조회결과가 있을경우
			// -> 중복되는 아이디가 없을경우
			// 	  1행만 조회되기 때문에 while보단 if를 사용하는게 효과적
			if(rs.next()) {
				
				// 각 컬럼의 값 얻어오기
				int userNo		 	= rs.getInt("USER_NO");
				String userId 		= rs.getString("USER_ID");
				String userPw 		= rs.getString("USER_PW");
				String userName 	= rs.getString("USER_NAME");
				
				// java.sql.Date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				// 조회된 컬럼값을 이용해 User 객체생성
				user = new User(userNo, 
								userId,
								userPw,
								userName,
								enrollDate.toString());
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		
		}finally {
			// 사용한 JDBC 객체 자원반환(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 Service에서 close!!!!!!
		}

		
		return user; // 결과 반환(생성된 User 또는 null)
		
	}

	/**
	 * User 등록 DAO 메서드
	 * @param conn : DB 연결정보가 담겨있는 객체
	 * @param user : 입력받은 id, pw, name
	 * @return result : INSERT 결과 행의 개수
	 * @throws Exception : 발생하는 예외 모두던짐
	 */
	public int insertUser(Connection conn, User user) throws Exception{
		
		// SQL 수행중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
		// -> catch문이 필요없다!!
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성
			String sql = """
	INSERT INTO TB_USER
	VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT) """;
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, user.getUserId()); 
			pstmt.setString(2, user.getUserPw()); 
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate()) 후 
			// 결과(삽입된 행의 개수, int) 반환받기
			result = pstmt.executeUpdate();
			
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환(Close)
		close(pstmt);
		}
		
		// 결과 저장용 변수에 저장된 값을 반환
		return result;
	}

	
	/** User 전체조회 DAO 메서드
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectAll(Connection conn)throws Exception {
		
		// 1. 결과 저장용 변수선언
		// -> List 같은 컬렉션을 반환하는 경우에는
		//    변수 선언시 객체도 같이 생성해두자!!!
		List<User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL 작성
			String sql = """
			SELECT
				USER_NO,
				USER_ID,
				USER_PW,
				USER_NAME,
				TO_CHAR(ENROLL_DATE, 'YYYY"년" MM "월" DD"일"')ENROLL_DATE
			FROM TB_USER
			ORDER BY USER_NO ASC	
			""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞는 값 대입 (없으면 패스~)
			
			// 5. SQL(SELECT) 수행(executeQuery()) 후 
			// 	  결과(ResultSet) 반환받기
			rs = pstmt.executeQuery();
			
			// 6. 조회결과(ResultSet)를 
			// 커서를 이용해서 1행씩 접근하여 컬럼값 얻어오기
			
			/* 몇 행이 조회될지 모른다 	-> while
			 * 무조건 1행이 조회된다 	-> if
			 */
			
			// rs.next() : 커서를 1행 이동
			// 이동된 행의 데이터가 있으면 true, 없으면 false
			while(rs.next()) {
				int userNo			= rs.getInt("USER_NO");
				String userId		= rs.getString("USER_ID");
				String userPw 		= rs.getString("USER_PW");
				String userName 	= rs.getString("USER_NAME");
				String enrollDate	= rs.getString("ENROLL_DATE");
				// - java.sql.Date 타입으로 값을 저장하지 않는 이유!
				// -> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
				
				// ** 조회된 값을 userList에 추가 **
				// -> User 객체를 생성해 조회된 값을 담고
				//    userList에 추가하기
				User user = new User(userNo, userId, 
							userPw, userName, enrollDate);
				
				userList.add(user);
				
				// ResultSet을 이용해 List에 옮겨담는이유
				
				// 1. List가 사용이 편해서
				//  -> 호환되는 곳도 많음(jsp, thymeleaf 등)
				
				// 2. 사용된 ResultSet은 DAO에 close 되기때문
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		// 조회결과가 담긴 List 반환
		return userList;
	}

	/** 이름에 검색어가 포함되는 회원 모두조회 Service메서드
	 * USER
	 * @param keyword
	 * @return searchList
	 * @throws Exception 
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {
		
		List<User> searchList = new ArrayList<User>();
		
		try {
			String sql = """
			SELECT
				USER_NO,
				USER_ID,
				USER_PW,
				USER_NAME,
				TO_CHAR(ENROLL_DATE, 'YYYY"년" MM "월" DD"일"')ENROLL_DATE
			FROM TB_USER
			WHERE USER_NAME LIKE '%' || ? || '%'
			ORDER BY USER_NO ASC
					""";
			
			//  PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			// SQL(SELECT) 수행(executeQuery()) 후 
			// 	  결과(ResultSet) 반환받기
			rs = pstmt.executeQuery();
			
			// rs.next() : 커서를 1행 이동
						// 이동된 행의 데이터가 있으면 true, 없으면 false
				while(rs.next()) {
					int userNo			= rs.getInt("USER_NO");
					String userId		= rs.getString("USER_ID");
					String userPw 		= rs.getString("USER_PW");
					String userName 	= rs.getString("USER_NAME");
					String enrollDate	= rs.getString("ENROLL_DATE");
					
				// ** 조회된 값을 userList에 추가 **
				// -> User 객체를 생성해 조회된 값을 담고
				//    userList에 추가하기
				User user = new User(userNo, userId, 
							userPw, userName, enrollDate);
				
				searchList.add(user);
					
				}
				
			}finally {
				close(rs);
				close(pstmt);
				
			}
		
		return searchList;
	}

	/** USER_NO를 입력받아 일치하는 User 조회 
	 * @param conn
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public User selectUser(Connection conn, int input) throws Exception {
		
		User user = null;
		
		try {
			String sql = """
			SELECT
				USER_NO,
				USER_ID,
				USER_PW,
				USER_NAME,
				TO_CHAR(ENROLL_DATE, 'YYYY"년" MM "월" DD"일"')ENROLL_DATE
			FROM TB_USER
			WHERE USER_NO = ?					
			""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				// 각 컬럼의 값 얻어오기
				int userNo		 	= rs.getInt("USER_NO");
				String userId 		= rs.getString("USER_ID");
				String userPw 		= rs.getString("USER_PW");
				String userName 	= rs.getString("USER_NAME");
				String enrollDate   = rs.getString("ENROLL_DATE");
				
				// 조회된 컬럼값을 이용해 User 객체생성
				user = new User(userNo, 
								userId,
								userPw,
								userName,
								enrollDate);
				
			}
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return user;
	}

	
	public int deleteUser(Connection conn, int input) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
				
			// 2. SQL 작성
			String sql = """
				DELETE
				FROM TB_USER
				WHERE USER_NO = ?
			""";
			
			// 3. PreparedStatement 생성(placeholder(?)가 있으면 무조건 써야함)
			
			pstmt = conn.prepareStatement(sql); // preparedStatement가 만들어지자마자(괄호안에)
			/* -> (sql) """
			DELETE
			FROM TB_USER
			WHERE USER_NO = ?
			"""; 가 들어감!!!!!! */
			
			// 4. ?에 알맞은 값 대입
			pstmt.setInt(1, input);
			
			// 5. SQL(DELETE) 수행(executeUpdate) 후
			// 결과(int) 반환 받기 DELETE는 update
			result = pstmt.executeUpdate();
			
		
			
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환
			close(pstmt);
			
		}
		
		return result;
	}

	/** 아이디, 비밀번호가 일치하는 User의 USER_NO 조회
	 * @param userId
	 * @param userPw
	 * @return userNo
	 * @throws Exception
	 */
	public int selectUser(Connection conn, String userId, String userPw) 
									throws Exception{
		
		int userNo = 0; // 결과 저장용 변수선언
		
		try {
			String sql = """
			SELECT USER_NO
			FROM TB_USER
			WHERE USER_ID = ?
			AND USER_PW = ?		
			""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			// SQL(SELECT) 수행(executeQuery()) 후
			// 결과(resultSet) 반환받기   ★☆★☆ select = resultset
			rs = pstmt.executeQuery();
			
			// 조회된 1행이 있을경우
			if(rs.next()) {
				userNo = rs.getInt("USER_NO");
			}
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return userNo; // 조회성공 USER_NO, 실패 0 반환
	}

	/** userNo가 일치하는 User의 이름수정
	 * @param userName
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public int updateName(Connection conn, String userName, int userNo) throws SQLException {
		
		int result = 0;
		
		try {String sql = """
			UPDATE TB_USER
			SET USER_NAME = ?
			WHERE USER_NO = ?
		""";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userName);
		pstmt.setInt(2, userNo);
		
		result = pstmt.executeUpdate();
		
	
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}

	
	/** 아이디 중복확인
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int idCheck(Connection conn, String userId) throws Exception{
		
		int count = 0; // 결과 저장용 변수
		
		try {
			String sql = """
				SELECT COUNT(*)
				FROM TB_USER
				WHERE USER_ID = ?
		""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // COUNT(*) 그룹함수 결과 1행만 조회
				count = rs.getInt(1); // 조회된 컬럼순서를 이용해
									  // 컬럼 값 얻어오기
			}
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		return count;
	}
	
	
}











