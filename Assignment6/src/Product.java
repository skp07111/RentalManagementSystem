import java.io.*;

public class Product {
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	/* 생성자 1 */
	Product() {}
	
	/* 생성자 2 */
	Product(String productCode) {
		this.productCode = productCode;
	}
	
	/* 생성자 3 */
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	/* 파일 read */
	Product readProduct(DataInputStream dis) throws Exception
	{	
		try {
			this.productName = dis.readUTF(); // 상품 이름 read
			this.productCode = dis.readUTF(); // 상품 코드 read
			this.productNumber = dis.readInt(); // 상품 개수 read
			this.price = dis.readInt(); // 상품 가격 read
			return this;
		}
		catch (Exception e){
			throw new Exception ("파일 read 오류");
		}
	}	
	
	/* 파일 write */
	void writeProduct(DataOutputStream dos) throws Exception {	
		try {
			dos.writeUTF(productName); // 상품 이름 write
			dos.writeUTF(productCode); // 상품 코드 write
			dos.writeInt(productNumber); // 상품 개수 write
			dos.writeInt(price); // 상품 가격 write
		}
		catch (Exception e){
			throw new Exception ("파일 write 오류");
		}
	}	
	
	/* equals 함수 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Product)) return false;
		Product p = (Product) o;
		return p.getCode().equals(productCode);
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
}