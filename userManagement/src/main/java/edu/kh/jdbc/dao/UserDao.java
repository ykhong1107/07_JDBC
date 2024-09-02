package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserDao {

	/** 사용자 등록
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int insertUser(Connection conn, User user) throws Exception;

	/**
	 * 아이디 중복 여부 확인
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int idCheck(Connection conn, String userId) throws Exception;

	/** 로그인
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	User login(Connection conn, String userId, String userPw) throws Exception;

	List<User> selectAll(Connection conn) throws Exception;

	/**
	 *  검색어가 아이디에 포함된 사용자 조회
	 * @param conn
	 * @param searchId
	 * @return userList
	 * @throws Exception
	 */
	List<User> search(Connection conn, String searchId) throws Exception;

	
	/**
	 * 상세조회 
	 * @param conn
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	User selectUser(Connection conn, int userNo) throws Exception;

	/**
	 * 사용자 삭제
	 * @param conn
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	int deleteUser(Connection conn, int userNo) throws Exception;

	/**
	 * 사용자 수정
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	int updateUser(Connection conn, User user) throws Exception;

	/**
	 * 콩순이를 찾아라
	 * @param conn
	 * @param str
	 * @return
	 * @throws Exception
	 */
	String selectKong(Connection conn, String str) throws Exception;
	
	
	

}
