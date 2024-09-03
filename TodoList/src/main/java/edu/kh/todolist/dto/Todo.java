package edu.kh.todolist.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable{
							// 직렬화
	
	private int todoNo;
	private String title;
	private String detail;
    private boolean complete;
    private String time;
    
    
	

}
