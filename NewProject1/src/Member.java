import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*회원 클래스*/
public class Member {
	private String memberCode; // 회원 번호
	private String memberName; // 회원 이름
	private String memberPhone; // 회원 전화번호
	private String memberPcode; // 회원 대여물품
	private String memberDate; // 회원 대여일자
	private String today; // 오늘 날짜
	
	public Member() {} // 생성자
	
	public void setMemberCode(String memberCode) { // 회원 번호 setter 메소드
		this.memberCode = memberCode;
	}
	
	public void setMemberName(String memberName) { // 회원 이름 setter 메소드
		this.memberName = memberName;
	}
	
	public void setMemberPhone(String memberPhone) { // 회원 전화번호 setter 메소드
		this.memberPhone = memberPhone;
	}
	
	public void setMemberPcode(String memberPcode) { // 회원 대여물품 setter 메소드
		this.memberPcode = memberPcode;
	}
	
	public void setMemberDate(String memberDate) { // 회원 대여일자 setter 메소드
		this.memberDate = memberDate;
	}
	
	//public void setToday() { // 오늘 날짜 setter 메소드

	//}
	
	public String getMemberCode() { // 회원 번호 getter 메소드
		return memberCode;
	}
	
	public String getMemberName() { // 회원 이름 getter 메소드
		return memberName;
	}
	
	public String getMemberPhone() { // 회원 전화번호 getter 메소드
		return memberPhone;
	}
	
	public String getMemberPcode() { // 회원 대여물품 getter 메소드
		return memberPcode;
	}
	
	public String getMemberDate() { // 회원 대여일자 getter 메소드
		return memberDate;
	}
	
	public String getToday() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date date = new Date();
		today = mSimpleDateFormat.format(date);
		return today;
	}
}


