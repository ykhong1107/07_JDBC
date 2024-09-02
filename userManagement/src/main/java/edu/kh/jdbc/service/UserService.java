package edu.kh.jdbc.service;

import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserService {

	
	/** 사용자등록
	 * 
	 * @param user
	 * @return result 1 || 0
	 */
	int insertUser(User user) throws Exception;

	/**
	 * 아이디 중복여부 확인
	 * @param userId
	 * @return result(1:중복, 0:중복X)
	 * @throws Exception
	 */
	int idCheck(String userId) throws Exception;
	
	/** 로그인
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	User login(String userId, String userPw) throws Exception;

	/**
	 * 사용자 목록조회
	 * @return
	 * @throws Exception
	 */
	List<User> selectAll() throws Exception;

	/**
	 * 검색어가 아이디에 포함된 사용자 조회 
	 * @param searchId
	 * @return
	 * @throws Exception
	 */
	List<User> search(String searchId) throws Exception;

	/**
	 * 상세조회
	 * @param userNo 
	 * @return
	 * @throws Exception
	 */
	User selectUser(int userNo) throws Exception;

	/**
	 *  사용자 삭제
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	int deleteUser(int userNo) throws Exception;

	/**
	 *  사용자 수정
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int updateUser(User user) throws Exception;

	
	/**
	 * 콩순이를 찾아라
	 * @param str
	 * @return 
	 * @throws Exception
	 */
	String selectKong(String str) throws Exception;
	// 메서드 반환타입('String') -> 이 메서드가 문자열을 반환할 것
	// 메서드 이름 = selectKong 가 메서드 이름이므로 일반적으로 "KONG"이라는 값을 선택하거나 조회함
	// String Str은 메서드가 입력으로 받는 매개변수임
	// 'str'이라는 이름의 문자열 값을 메서드에 전달함
	// 이 매개변수는 메서드 내부에서 조회하거나 처리할 데이터를 전달하는 역할을함!
	
	
	
}

