package shop;

public class ShopMember {

	private String memberPw;
	private String gender;
	private String memberId;
	private String phone;

	public ShopMember() {}
	
	public ShopMember(String memberId, String memberPw, String phone, String gender) {
		
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.phone = phone;
		this.gender = gender;
	}

	
	// -------------- 문제 x -----------------
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return memberId + ", " + memberPw + ", " + phone + ", " + gender;
	}
	
	
	
	
	
	
}
