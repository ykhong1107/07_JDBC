package edu.kh.todolist.service;

import static edu.kh.todolist.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.Map;

import edu.kh.todolist.dao.TodoListDao;
import edu.kh.todolist.dao.TodoListDaoImpl;

public class TodoListServiceImpl implements TodoListService{

	private TodoListDao dao = new TodoListDaoImpl();
	
	@Override
	public Map<String, Object> todoListFullView() throws Exception {
		
		Connection conn = getConnection();
		
		Map<String, Object> todoList = dao.todoListFullview(conn);
		
		close(conn);
		
		return todoList;
	}
	
	
	
	
	
}
