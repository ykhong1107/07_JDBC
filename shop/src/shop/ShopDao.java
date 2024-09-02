package shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopDao {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public ShopMember selectMember(Connection conn, String memberId) {
		
		ShopMember sm = null; // 결과 저장용 변수
		
		try {
			String sql = "SELECT * FROM SHOP_MEMBER WHERE MEMBER_ID = ?";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();
			
		    // 각 컬럼의 값 얻어오기
			if(rs.next()) {
				String id = rs.getString("MEMBER_ID");
				String pw = rs.getString("MEMBER_PW");
				String phone =  rs.getString("PHONE");
				String gender = rs.getString("GENDER");
				
				// 조회된 컬럼값을 이용해 User 객체생성
				sm = new ShopMember(id, 
									pw, 
									phone, 
									gender);
				
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		
		return sm;
		
	}
	
	
	
}
