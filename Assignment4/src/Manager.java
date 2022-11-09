import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Manager {
	private Product[]productList; // 상품 배열
	private int productCount = 0; // 상품 배열 인덱스 카운트
	private User[]userList; // 대여 배열
	private int userCount = 0; // 대여 배열 인덱스 카운트
	private int revenue = 0; // 일일 매출 총액 변수
	
	// 생성자 (상품 배열, 대여 배열 크기 설정)
	Manager (int maxProductCount, int maxUserCount){
		productList = new Product[maxProductCount]; // 상품 배열 크기 설정
		userList = new User[maxUserCount]; // 대여 배열 크기 설정
	}
	
	// 코드 중복 검색
	public void checkCode(Product p) throws Exception {
		for (int i = 0; i < productCount; i++)
		{
			Product p1 = productList[i];
			// 중복된 키 검색
			if(p1 != null && p1.getCode().equals(p.getCode()))
			{
				throw new Exception ("잘못된 상품 등록입니다.");
			}
		}
	}

	// 상품 배열에 원소 추가
	public void addProduct(Product p) {
		productList[productCount] = p;
		productCount++;
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
	
	// 상품 배열에 number번째 인덱스 원소 삭제
	public void subProduct(int number) {
		// 상품 삭제, 배열 정리하기	
		for (int i = number; i < productCount; i++)	
		{		
			productList[i] = productList[i+1];		
		}
		productCount--;
	}
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // 상품 배열에서 검색하기
			subProduct(number); // 상품 배열에서 삭제하기
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// 상품 객체 검색
	public int search(String productCode) throws Exception {	
		// 상품 검색
		for(int i = 0; i < productCount; i++)
		{
			Product p1 = productList[i]; // 객체 생성
			// 원소 코드와 인수 일치
			if((productCode.equals(p1.getCode())))
			{
				return i;
			}
		} throw new Exception("일치하는 코드를 찾을 수 없습니다."); // 코드가 일치하지 않으면 익셉션 발생
	}

	// 상품 배열 i번째 리턴
	public Product productAt(int i) {
		return productList[i]; // 상품 배열 i번째 상품 객체 return
	}
	
	// productCount 값 반환
	public int getProductCount() {
		return productCount;
	}
	
	// 전화번호 중복 검색
	public void checkPhone(User u) throws Exception {
		for (int i = 0; i < userCount; i++)
		{
			User u1 = userList[i];
			// 중복된 전화번호 검색
			if(u1 != null && u1.getPhone().equals(u.getPhone()))
			{
				throw new Exception ("잘못된 방법의 체크인입니다."); // 중복된 전화번호일 경우 익셉션 발생
			}
		}
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
			Product p = productList[searchNum]; //해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 대여 배열에 원소 추가
	public void addUser(User u) {
		userList[userCount] = u;
		userCount++;
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
	
	// userCount 값 반환
	public int getUserCount() {
		return userCount;
	}
	
	// 대여 배열 i번째 리턴
	public User userAt(int i)
	{
		return userList[i]; // 대여 배열 i번째 유저 객체 return
	}
	
	// 일치하는 회원번호 검색
	public int searchUser(String phone) throws Exception {
		for(int i = 0; i < userCount; i++)
		{
			User u = userList[i];
			// 일치하는 정보가 있으면 인덱스 번호 반환
			if (u.getPhone().equals(phone))
				return i;
		}throw new Exception ("회원정보가 없습니다."); // 일치하는 정보가 없으면 익셉션 발생
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
		for (int i = number; i < userCount; i++)	
		{		
			userList[i] = userList[i+1];		
		}
		userCount--;
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList[index].pay();// 금액 반환받기
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
	
	// 파일 생성 함수
	public void makeFile(DataOutputStream dos) throws Exception{
		try {
			for (int i = 0; i < productCount; i++) { // product 정보 write
				productList[i].saveProduct(dos); // read(~inputStream)할 때 고려해야함, productCount와 userCount를 먼저 출력
			}
			for (int i = 0; i < userCount; i++) { // user 정보 write
				userList[i].saveUser(dos);
			}
			dos.writeInt(revenue); // 매출액 write
		}
		catch (IOException ioe){ // catch(Exception e)
			throw new Exception ("입출력 오류");
		}
	}
}