/*회원 클래스*/
public class Member {
	private int member_number; // 회원 번호
	private String member_name; // 회원 이름
	private String member_phone; // 회원 전화번호
	private String member_status; // 회원 대여상태
	private String member_date; // 회원 대여일자
	
	public Member() {} // 생성자

	public void setMemberNumber(int member_number) { // 회원 번호 setter 메소드
		this.member_number = member_number;
	}
	
	public void setMemberName(String member_name) { // 회원 이름 setter 메소드
		this.member_name = member_name;
	}
	
	public void setMemberPhone(String member_phone) { // 회원 전화번호 setter 메소드
		this.member_phone = member_phone;
	}
	
	public void setMemberStatus(String member_status) { // 회원 대여상태 setter 메소드
		this.member_status = member_status;
	}
	
	public void setMemberDate(String member_date) { // 회원 대여일자 setter 메소드
		this.member_date = member_date;
	}
	
	public int getMemberNumber() { // 회원 번호 getter 메소드
		return member_number;
	}
	
	public String getMemberName() { // 회원 이름 getter 메소드
		return member_name;
	}
	
	public String getMemberPhone() { // 회원 전화번호 getter 메소드
		return member_phone;
	}
	
	public String getMemberStatus() { // 회원 대여상태 getter 메소드
		return member_status;
	}
	
	public String getMemberDate() { // 회원 대여일자 getter 메소드
		return member_date;
	}
}
