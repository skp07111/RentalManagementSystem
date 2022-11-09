import java.io.DataOutputStream;
import java.io.IOException;

public class Product {
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	// 인수 있는 생성자
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// 상품 이름 반환
	public String getName()
	{
		return productName;
	}
	
	// 상품 코드 반환
	public String getCode()
	{
		return productCode;
	}
	
	// 상품 개수 반환
	public int getNumber()
	{
		return productNumber;
	}
	
	// 상품 개수 추가
	public void addNumber()
	{
		productNumber++;
	}
	
	// 상품 대여 가능한지 확인 후 상품 개수 1개 삭제
	public void subNumber() throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("잘못된 방법의 체크인입니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber--; // 재고 수 1개 감소
	}
	
	// 상품 가격 반환
	public int getPrice()
	{
		return price;
	}
	
	// 재고 검색 함수 (재고가 있으면 true, 아니면 false 반환)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	/*product 정보 write 함수*/
	public void saveProduct(DataOutputStream dos) { // public void saveProduct(DataOutputStream dos) throws Exception <- 내가 만든 exception
		try {
			dos.writeUTF(productName); // product 이름 write
			dos.writeUTF(productCode); // product 코드 write
			dos.writeInt(productNumber); // product 재고 write
			// dos.writeUTF(Integer.toString(price)); 이렇게 쓰지 말 것, 변수(필드)의 자료형이 int이면 writeInt로 쓸 것
			dos.writeInt(price); // product 가격 write
		}
		catch (IOException ioe) { // 자동으로 나오는 exception 처리
			
		}
//		catch (Exception e) { // 위에서 내가 만든(throw한) exception에 대한 처리를 할 때 throw 필요
//		throw new Exception("상품 정보를 파일에 쓸 수 없습니다.");
//	}
	}
}