import java.io.*;
import java.util.*;

public class Manager {
	private ArrayList<Product> productList; // 상품 배열
	private ArrayList<User> userList; // 사용자 배열
	private int revenue; // 일일 매출 총액 변수
	
	/* 생성자 1 */
	Manager () {
		productList = new ArrayList<Product>(); // 상품 배열 생성
		userList = new ArrayList<User>(); // 사용자 배열 생성
	}
	
	/* 생성자 2 */
	Manager (ObjectInputStream ois) throws Exception
	{ 
		productList = new ArrayList<Product>(); // 상품 배열 생성
		userList = new ArrayList<User>(); // 사용자 배열 생성
		
		// 파일 정보 read
		try {
			Integer pCount = (Integer) ois.readObject(); // 전체 상품 수 read
			for (int i = 0; i < pCount.intValue(); i++)
			{
				Product p = (Product) ois.readObject(); // 상품 정보 read
				productList.add(p); // 상품 배열에 read한 상품 추가
			}
			Integer uCount = (Integer) ois.readObject(); // 전체 사용자 수 read
			for (int i = 0; i < uCount.intValue(); i++)
			{
				User u = (User) ois.readObject(); // 사용자 정보 read
				userList.add(u); // 사용자 배열에 read한 사용자 추가
			}
			Integer revenue = (Integer) ois.readObject(); // 매출액 read
			this.revenue = revenue.intValue();
		} 
		catch (FileNotFoundException fnfe) {
			throw new Exception ("파일이 존재하지 않습니다.");
		}
		catch (EOFException eofe) {
			throw new Exception ("끝났습니다.");
		}
		catch (IOException ioe) {
			throw new Exception ("파일 입출력 오류입니다.");
		}
	}
	
	/* 파일 write 함수 */
	void writeFile(ObjectOutputStream oos) throws Exception 
	{
		try {
			Integer pCount = new Integer(productList.size()); // 전체 상품 수
			oos.writeObject(pCount); // 전체 상품 수 write
			for (int i = 0; i < pCount.intValue(); i++)
			{
				oos.writeObject(productList.get(i)); // 상품 정보 write
			}
			Integer uCount = new Integer(userList.size()); // 전체 사용자 수
			oos.writeObject(uCount); // 전체 사용자 수 write
			for (int i = 0; i < uCount.intValue(); i++)
			{
				oos.writeObject(userList.get(i)); // user 정보 write
			}
			Integer revenue = new Integer(this.revenue);
			oos.writeObject(revenue); // 매출액 write
		} 
		catch (IOException ioe) {
			throw new Exception ("파일 입출력 오류입니다.");
		} 	
	}	
	
	// 상품 추가
	public void addProduct(Product p) throws Exception{
		int index = productList.indexOf(p); // 동일한 상품 코드를 가진 상품 유무 검색
		if (index != -1)
		{
			throw new Exception ("잘못된 상품 등록입니다.");
		}
		else productList.add(p); // 동일한 상품 코드가 없으면 상품 배열에 상품 추가
	}
	
	// 상품 삭제
	public void subProduct(String productCode) throws Exception {
		try{
			productList.remove(searchProduct(productCode)); // 상품 코드 검색하여 해당 상품 삭제
		}
		catch (Exception e) { 
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// 상품 객체 검색
	public int searchProduct(String productCode) throws Exception {
		Product p = new Product();
		p.setProductCode(productCode); // 상품 코드 설정
		int index = productList.indexOf(p); // 상품 검색
		if (index == -1)
			throw new Exception("일치하는 코드를 찾을 수 없습니다.");
		else return index; // 코드 일치 시 index 반환
	}
	
	// 체크인
	public void checkIn(User u) throws Exception {
		int index = userList.indexOf(u); // 동일한 전화번호를 가진 사용자 유무 검색
		if (index != -1) throw new Exception ("잘못된 방법의 체크인입니다.");
		else { // 동일한 전화번호가 없으면
		subStock(u); // 재고 개수에서 대여 개수 제외
		userList.add(u); // 사용자 배열에 해당 사용자 추가
		}
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList.get(index).pay();// 금액 반환받기
			userList.remove(index);	// userList에서 해당 사용자 삭제
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}

	// 사용자 객체 검색
	public int searchUser(String phone) throws Exception {
		User u = new User();
		u.setPhone(phone); // 전화번호 설정
		int index = userList.indexOf(u); // 사용자 검색
		if (index == -1)
			throw new Exception("일치하는 전화번호를 찾을 수 없습니다.");
		else return index; // 전화번호 일치 시 index 반환
	}

	
	// 재고 개수에서 대여 개수 제외
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				searchNum = searchProduct(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			Product p = productList.get(searchNum); // 해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 상품 재고 다시 추가
	public void addStock(int index) throws Exception {
		User u = userList.get(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				productList.get(searchProduct(code)).addNumber(); //해당 코드의 product 객체 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 상품 배열 i번째 리턴
	public Product productAt(int i) {
		return productList.get(i); // 상품 배열 i번째 상품 객체 return
	}
	
	// 대여 사용자 배열 i번째 리턴
	public User userAt(int i)
	{
		return userList.get(i); // 대여 배열 i번째 유저 객체 return
	}
	
	// productCount 값 반환
	public int getProductCount() {
		return productList.size();
	}
	
	// userCount 값 반환
	public int getUserCount() {
		return userList.size();
	}
	
	// 매출 반환
	public int getRevenue() {
		return revenue;
	}

}