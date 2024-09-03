package edu.kh.todolist.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import edu.kh.todolist.dto.Todo;

public interface TodoListDao {

	/**
	 * 전체조회
	 * @param conn
	 * @return
	 */
	Map<String, Object> todoListFullview(Connection conn)throws Exception;

	/**
	 * 할일 추가
	 * @param conn
	 * @param title
	 * @param detail
	 * @return
	 */
	int todoAdd(Connection conn, String title, String detail)throws Exception;
	
	




	
	
	
	
	
	
	
	
	
	
	
	
}
