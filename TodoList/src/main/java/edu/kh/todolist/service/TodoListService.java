package edu.kh.todolist.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import edu.kh.todolist.dto.Todo;

public interface TodoListService {

	/**
	 * 전체조회
	 */
	Map<String, Object> todoListFullView() throws Exception;

	/**
	 * 할일 추가하기
	 * @param title
	 * @param detail
	 * @return
	 */
	int todoAdd(String title, String detail) throws Exception;

	
	
	
	
	

}
