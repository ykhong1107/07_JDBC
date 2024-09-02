package edu.kh.jdbc.service;

// 지정된 클래스의 static 메서드를 모두 얻어와 사용
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDao;
import edu.kh.jdbc.dao.UserDaoImpl;
import edu.kh.jdbc.dto.User;

public class UserServiceImpl implements UserService{

	// 필드
	private UserDao dao = new UserDaoImpl();
	
	@Override
	public int insertUser(User user) throws Exception {
		
		// 1. Connection 생성
//		JDBCTemplate.getConnection(); // 위에 써놔서 안써도됨
		Connection conn = getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환
		int result = dao.insertUser(conn, user);
		
		// 3. DML 수행 -> 트랜잭션 처리
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		// 4. 사용 완료된 Connection 반환
		close(conn);
		
		// 5. 결과반환
		return result;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.idCheck(conn, userId);
		
		close(conn);
		
		return result;
	}

	@Override
	public User login(String userId, String userPw) throws Exception {
		
		Connection conn = getConnection();

		// DAO 메서드 호출 후 결과 반환받기
		User loginUser = dao.login(conn, userId, userPw);
		
		close(conn);
		
		return loginUser;
	}

	
	@Override
	public List<User> selectAll() throws Exception {
		
		Connection conn = getConnection();
		
		List<User> userList = new ArrayList<User>();
		// dao에게 DB 전달해서 정보 가져와 
		userList = dao.selectAll(conn);
		
		close(conn);
		
		return userList;
	}

	
	@Override
	public List<User> search(String searchId) throws Exception {
		
		// 커넥션 생성
		Connection conn = getConnection();
		
		// 데이터 가공(없으면 패스)
		searchId = '%' + searchId + '%'; // %검색어% 형태로 가공 
		
		// DAO 호출 후 결과 반환받기
		List<User> userList = dao.search(conn, searchId);
		
		close(conn);
		
		return userList;
	}
	
	@Override
	public User selectUser(int userNo) throws Exception {
		
		Connection conn = getConnection();
		
		User userOne = dao.selectUser(conn, userNo);
		
		close(conn);
		
		return userOne;
	}
	
	@Override // DML은 commit, rollback 필요!!★☆★☆
	public int deleteUser(int userNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteUser(conn, userNo);
		
		// 트랜잭션 제어처리 (dao에서 DML 수행했으니까!!!)
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	@Override
	public int updateUser(User user) throws Exception {

		Connection conn = getConnection();
		
		int result = dao.updateUser(conn, user);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	@Override
	public String selectKong(String str) throws Exception {
// public 공용접근제어자, 다른 클래스에서도 호출가능 String메서드가 반환하는 값의 타입
// selectKong 메서드의 이름 -> str이라는 입력을 사용, "Kong"이라는 데이터를 선택하거나 조회하는 작업수행
// String str = 메서드의 입력 매개변수로, 조회할 때 사용하는 문자열
		Connection conn = getConnection();
// 데이터베이스와의 연결을 생성하는 메서드. 이 메서드는 'Connection'객체를 반환하며 데이터베이스와의
// 상호작용을 위해 필요
		String kong = dao.selectKong(conn, str);
// dao.selectKong(conn, str) : 'dao' 객체의 'selectKong' 메서드를 호출하여 데이터베이스에서
// 정보를 조회함. 여기서 'dao'는 데이터베이스 작업을 수행하는 Date Access Object인스턴스를 나타냄.
// 'conn' : 데이터베이스 연결객체를 'dao.selectKong' 메서드에 전달함
// 'str' 조회에 사용할 문자열을 전달
// 'kong' 조회된 결과를 저장하는 변수. 'dao.selectKong' 메서드는 String타입의 값을 반환, 이값은 kong변수에 저장
		close(conn);
		
		return kong;
	}
	
}
