/*물품 클래스*/
public class Product {
	private int product_number; // 물품 번호
	private String product_name; // 물품 이름
	private int product_period; // 물품 대여기간
	private String product_status; // 물품 대여상태
	private String product_date; // 물품 대여일자
	
	public Product() {} // 생성자
	
	public void setProductNumber(int product_number) { // 물품 번호 setter 메소드
		this.product_number = product_number;
	}
	
	public void setProductName(String product_name) { // 물품 이름 setter 메소드
		this.product_name = product_name;
	}
	
	public void setProductPeriod(int product_period) { // 물품 대여기간 setter 메소드
		this.product_period = product_period;
	}
	
	public void setProductStatus(String product_status) { // 물품 대여상태 setter 메소드
		this.product_status = product_status;
	}
	
	public void setProductDate(String product_date) { // 물품 대여일자 setter 메소드
		this.product_date = product_date;
	}
	
	public int getProductNumber() { // 물품 번호 getter 메소드
		return product_number;
	}
	
	public String getProductName() { // 물품 이름 getter 메소드
		return product_name;
	}
	
	public int getProductPeriod() { // 물품 대여기간 getter 메소드
		return product_period;
	}
	
	public String getProductStatus() { // 물품 대여상태 getter 메소드
		return product_status;
	}
	
	public String getProductDate() { // 물품 대여 일자 getter 메소드
		return product_date;
	}
}
