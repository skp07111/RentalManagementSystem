import java.io.*;

import java.util.*;
import java.text.SimpleDateFormat;

public class User {
	private String name; // 이름
	private String phone; // 전화번호
	private String rentalDay; // 대여 일자
	private String returnDay; // 반납 예정 일자
	private String[] codeList = new String[3]; // 대여 상품 코드 배열
	private int[] payList = new int[3]; // 대여 금액 배열
	private int rentalCount = 0; // 대여 상품 카운트
	Calendar getToday = Calendar.getInstance();
	
	/* 생성자 1 */
	User() {}
	
	/* 생성자 2 */
	User(String phone) {
		this.phone = phone;
	}
	
	/* 생성자 3 */
	User(String name, String phone, String rentalDay, String returnDay)
	{
		this.name = name;
		this.phone = phone;
		this.rentalDay = rentalDay;
		this.returnDay = returnDay;
	}
	
	/* 파일 read */
	User readUser(DataInputStream dis) throws Exception
	{
		try {
			this.name = dis.readUTF(); // 이름 read
			this.phone = dis.readUTF(); // 전화번호 read
			this.rentalDay = dis.readUTF(); // 대여 일자 read
			this.returnDay = dis.readUTF(); // 반납 예정 일자 read
			int readRentalCounts = dis.readInt(); // 대여한 상품 코드 read
			for (int j = 0; j < readRentalCounts; j++) {
				String pCode = dis.readUTF(); // 대여 상품 코드 read
				int pPay = dis.readInt(); // 대여 금액 read
				this.addProduct(pCode, pPay);
			}
			return this;
		}
		catch (Exception e){
			throw new Exception ("파일 read 오류");
		}
	}	
		
	/* 파일 write */
	void writeUser(DataOutputStream dos) throws Exception
	{	
		try {
			dos.writeUTF(name); // 이름 write
			dos.writeUTF(phone); // 전화번호 write
			dos.writeUTF(rentalDay); // 대여 일자 write
			dos.writeUTF(returnDay); // 반납 예정 일자 write
			dos.writeInt(rentalCount); // 대여 상품 개수 write
			for (int i = 0; i < rentalCount; i++) {
				dos.writeUTF(codeList[i]); // 대여 상품 코드 write
				dos.writeInt(payList[i]); // 대여 금액 write
			}
		}
		catch (Exception e){
			throw new Exception ("파일 write 오류");
		}
	}
	
	/* equals 함수 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) return false;
		User u = (User) o;
		return u.getPhone().equals(phone);
	}

	// 이름 반환
	public String getName()
	{
		return name;
	}
	
	// 전화번호 반환
	public String getPhone()
	{
		return phone;
	}
	
	// 대여 일자 반환
	public String getRentalDay()
	{
		return rentalDay;
	}
	
	// 반납 예정 일자 반환
	public String getReturnDay()
	{
		return returnDay;
	}
	
	// 대여 상품 코드 배열 인덱스 카운트 반환
	public int getRentalCount() {
		return rentalCount;
	}
	
	// 대여 상품 코드/금액 배열에 코드 추가
	public void addProduct(String code, int money)
	{
		codeList[rentalCount] = code;
		payList[rentalCount] = money;
		rentalCount++;
	}
	
	// 대여 상품 코드 배열 i번째 리턴
	public String codeAt(int i)
	{
		// 대여 상품 코드 배열 i번째 상품 객체 return
		return codeList[i];
	}
	
	// 대여 금액 배열 i번째 리턴
	public int payAt(int i)
	{
		// 대여 금액 배열 i번째 상품 객체 return
		return payList[i];
	}
	
	// 결제 함수
	public int pay() throws Exception
	{
		// 물건 금액 합 계산하기
		int money = 0;
		for(int i = 0; i < rentalCount; i++)
		{
			money += payList[i];
		}
		
		// 반납 일자 입력하기
		getToday.setTime(new Date());
		
		// 대여 일자 불러오기
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDay);
		Calendar rentalDate = Calendar.getInstance();
		rentalDate.setTime(date1);
		
		// 반납 예정 일자 불러오기
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(returnDay);
		Calendar returnDate = Calendar.getInstance();
		returnDate.setTime(date2);
		
		// 대여하기로 한 기간 계산하기 (반납 예정 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day1 = (int) ((returnDate.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));

		// 대여 기간 계산하기 (반납 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day2 = (int) ((getToday.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		/*금액 관련 규칙 설정
		 * 날짜 지켜서 반납 => 상품 금액 총 합 x 대여 기간
		 * 먼저 반납 => 상품 금액 총 합 x 실제 대여 기간(남은 기간은 가격 책정 x)
		 * 나중에 반납 => 상품 금액 총 합 x 실제 대여 기간 + 연체료(하루 대여료)*/
		
		// 총 금액 계산하기
		if (day1 == day2) // 반납 예정 날짜에 반납
			return money *= (day1 + 1); // 당일 대여 반납도 1일로 처리
		
		else if (day1 > day2)// 반납 예정 날짜보다 빨리 반납
			return money *= (day2 + 1); // 당일 대여 반납도 1일로 처리
		
		else // 반납 예정 날짜보다 늦게 반납
			return money *= (day2 + 2); // 연체료 (하루 대여료) 추가하여서 반환
	}
}