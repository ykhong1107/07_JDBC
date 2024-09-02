package edu.kh.todolist.dao;

import static edu.kh.todolist.common.JDBCTemplate.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.tags.shaded.org.apache.xalan.processor.ProcessorExsltFuncResult;

import edu.kh.todolist.common.JDBCTemplate;
import edu.kh.todolist.dto.Todo;

public class TodoListDaoImpl implements TodoListDao {

	
	private List<Todo> todoList = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	// 기본 생성자
	public TodoListDaoImpl() {
		
		// 객체생성시 
		// 외부에 존재하는 sql.xml 파일을 읽어와
		// prop에 저장
		
		try {
			String filePath =
					JDBCTemplate.class
					.getResource("/edu/kh/todolist/sql/sql.xml").getPath();
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Map<String, Object> todoListFullview(Connection conn) throws Exception {
		
		List<Todo> todoList = new ArrayList<Todo>();
		
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			String sql = prop.getProperty("todoListFullview");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String todoTitle = rs.getString("TODO_TITLE");
				String todoDetail = rs.getString("TODO_DETAIL");
				boolean todoComplete = rs.getBoolean("TODO_COMPLETE");
				String todoTime = rs.getString("TODO_TIME");
				
				Todo todo = new Todo(todoTitle, todoDetail, todoComplete, todoTime);
				
				todoList.add(todo);
				
			}
			
			resultMap.put("todoList", todoList);
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		return resultMap;
		
	}
	
	

}
