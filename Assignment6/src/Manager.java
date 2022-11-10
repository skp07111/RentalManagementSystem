import java.io.*;
import java.util.*;

public class Manager {
	private ArrayList<Product> productList; // 상품 배열
	private int productCount = 0; // 상품 배열 인덱스 카운트
	private ArrayList<User> userList; // 대여 배열
	private int userCount = 0; // 대여 배열 인덱스 카운트
	private int revenue = 0; // 일일 매출 총액 변수
	// productCount, userCount 없애기
	
	/* 생성자 1 */
	Manager () {
		productList = new ArrayList<Product>(); // 상품 배열 크기 설정
		userList = new ArrayList<User>(); // 대여 배열 크기 설정
	}
	
	/* 생성자 2 */
	Manager (DataInputStream dis) throws Exception
	{ 
		productList = new ArrayList<Product>(); // 상품 배열 크기 설정
		userList = new ArrayList<User>(); // 대여 배열 크기 설정
		// 파일 정보 read
		try {
			int pCount = dis.readInt(); // 전체 상품 개수 read
			for (int i = 0; i < pCount; i++)
			{
				Product p = new Product(); // 상품 객체 p 생성
				add(p.readProduct(dis)); // 상품 정보 read
			}
			int uCount = dis.readInt(); // 전체 사용자 수 read
			for (int i = 0; i < uCount; i++)
			{
				User u = new User(); // 사용자 객체 u 생성
				checkInRead(u.readUser(dis)); // 사용자 정보 read
			}
			revenue = dis.readInt(); // 매출액 read
		} 
		catch (Exception e) {
			throw new Exception ("파일 read 오류");
		}
	}
	
	/* 파일 write 함수 */
	void writeFile(DataOutputStream dos) throws Exception {
		try {
			dos.writeInt(productCount); // 전체 상품 개수 write
			for (int i = 0; i < productCount; i++) 
			// for (int i = 0; i < productList.size(); i++) size 함수가 여러번 세야함 -> 틀린 코드(비효율적)
			
			// num = productList.size();
			// for (int i = 0; i < num; i++) -> 맞는 코드
			{
				productList.get(i).writeProduct(dos); // 상품 정보 write
			}
			dos.writeInt(userCount); // 전체 사용자 수 write
			for (int i = 0; i < userCount; i++)
			{
				userList.get(i).writeUser(dos); // user 정보 write
			}		
			dos.writeInt(revenue); // 매출액 write
		} catch (Exception e) {
			throw new Exception ("파일 write 오류");
		} 		
	}	
	
	// 상품 코드 중복 검색
	public void checkCode(Product p) throws Exception {
		int index = search(p.getCode()); // 중복된 키 검색
		if (index != -1)
		{
			throw new Exception ("잘못된 상품 등록입니다.");
		}
	}

	// 상품 배열에 원소 추가
	public void addProduct(Product p) {
		productList.add(p);
		productCount = productList.size();
	}
	
	// 상품 추가
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // 코드 중복 검색
			addProduct(p); // 상품 추가
		}
		catch (Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");
		}
	}
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // 상품 배열에서 검색하기
			Product p = productList.get(number);
			// productList.remove(number); // 인덱스로 삭제도 가능
			productList.remove(p); // 상품 배열에서 삭제하기
			// if(!remove(p)) throw Exception("~");
			productCount = productList.size();
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// 상품 객체 검색
	public int search(String productCode) {
		Product p = new Product(productCode);
		// Product 클래스에 productCode set함수 만들 것 --> p.setPcode(productCode); 
		int index = productList.indexOf(p);
		return index;
	}

	// 상품 배열 i번째 리턴
	public Product productAt(int i) {
		return productList.get(i); // 상품 배열 i번째 상품 객체 return
	}
	
	// productCount 값 반환
	public int getProductCount() {
		return productCount;
	}
	
	// 전화번호 중복 검색
	public void checkPhone(User u) throws Exception {
		int index = searchUser(u.getPhone()); // 중복된 전화번호 검색
		if(index != -1)
		{
			throw new Exception ("잘못된 방법의 체크인입니다.");
		}
	}
	
	// 대여 배열에 원소 추가
	public void addUser(User u) {
		userList.add(u);
		userCount = userList.size();
	}
	
	// 재고 개수에서 대여 개수 제외
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				searchNum = search(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			Product p = productList.get(searchNum); // 해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 체크인
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // 전화번호 중복 검색
			subStock(u); // 재고 개수에서 대여 개수 제외
			addUser(u); // 대여 배열에 대여 정보 넣기
		}
		catch(Exception e) {
			throw new Exception("잘못된 방법의 체크인입니다.");
		}
	}
	
	// 체크인 정보 read
	public void checkInRead(User u) {
		addUser(u); // 대여 배열에 대여 정보 넣기
	}
	
	// userCount 값 반환
	public int getUserCount() {
		return userCount;
	}
	
	// 대여 배열 i번째 리턴
	public User userAt(int i)
	{
		return userList.get(i); // 대여 배열 i번째 유저 객체 return
	}
	
	// 사용자 객체 검색
	public int searchUser(String phone) {
		User u = new User(phone);
		int index = userList.indexOf(u);
		return index;
	}
	
	// 상품 재고 다시 추가
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				int number = search(code); // productList에서 해당 코드의 인덱스 번호 검색
				productAt(number).addNumber(); //해당 인덱스의 product 객체의 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 대여 배열에 원소 삭제
	public void subUser(int number) {
		User u = userList.get(number);
		userList.remove(u);	
		userCount = userList.size();
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList.get(index).pay();// 금액 반환받기
			subUser(index); // userList에서 삭제, 배열 정리하기
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}
	
	// 매출 반환
	public int getRevenue() {
		return revenue;
	}

}