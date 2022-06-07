import java.util.Date;

/*회원 클래스*/
public class Member {
	private String memberCode; // 회원 번호
	private String memberName; // 회원 이름
	private String memberPhone; // 회원 전화번호
	private int memberPeriod; // 대여 기간
	private Date memberDate; // 회원 대여일자
	private String[] rentArray; // 대여 물품 리스트
	private int rcount;
	
	public Member() { // 생성자
		rentArray = new String[3];
		rcount = 0;
	} 
	
	public void setMemberCode(String memberCode) { // 회원 번호 setter 메소드
		this.memberCode = memberCode;
	}
	
	public void setMemberName(String memberName) { // 회원 이름 setter 메소드
		this.memberName = memberName;
	}
	
	public void setMemberPhone(String memberPhone) { // 회원 전화번호 setter 메소드
		this.memberPhone = memberPhone;
	}
	
	public void setMemberPeriod(int memberPeriod) { // 회원 대여기간 setter 메소드
		this.memberPeriod = memberPeriod;
	}
	
	public void setMemberDate(Date memberDate) { // 회원 대여일자 setter 메소드
		this.memberDate = memberDate;
	}
	
	public String getMemberCode() { // 회원 번호 getter 메소드
		return memberCode;
	}
	
	public String getMemberName() { // 회원 이름 getter 메소드
		return memberName;
	}
	
	public String getMemberPhone() { // 회원 전화번호 getter 메소드
		return memberPhone;
	}
	
	public int getMemberPeriod() { // 회원 대여기간 getter 메소드
		return memberPeriod;
	}
	
	public Date getMemberDate() { // 회원 대여일자 getter 메소드
		return memberDate;
	}
	
	public void addRentArray(String productCode) { // 대여 물품코드 리스트에 추가
		if (rcount < 3) {
			rentArray[rcount] = productCode;
			this.rcount = rcount + 1;
		}
	}
	
	public void resetRentArray(int i, String reset) { // 대여 물품 코드 getter 메소드
		rentArray[i] = reset;
	}
	
	public String getRentArray(int i) { // 대여 물품 코드 getter 메소드
		return rentArray[i];
	}
	
	/*날짜 계산 함수*/
	public int calculate(Date returnDate) {
		Date rentDate = getMemberDate();
		long rentSec = returnDate.getTime() - rentDate.getTime(); // 반납일 - 대여일
		int rentDays = (int) rentSec / (24*60*60); // 실제 대여 기간(단위: 일)
		return rentDays;
	}
	
	public void setRentDate() { // 오늘 날짜 getter 메소드
		Date today = new Date();
		this.memberDate = today;
	}
}


