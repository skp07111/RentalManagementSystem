import java.util.Date;
/*물품 클래스*/
public class Product {
	private String productCode; // 물품 번호
	private String productName; // 물품 이름
	private int productStock;  // 물품 재고
	private int productAmount; // 물품 수량
	private int rentalFee; // 1일 대여료
	private int lateFee; // 1일 연체료
	
	public Product() {} // 생성자
	
	public void setProductCode(String productCode) { // 물품 번호 setter 메소드
		this.productCode = productCode;
	}
	
	public void setProductName(String productName) { // 물품 이름 setter 메소드
		this.productName = productName;
	}
	
	public void setProductStock(int productStock) { // 물품 재고 setter 메소드
		this.productStock = productStock;
	}
	
	public void setProductAmount(int productAmount) { // 물품 수량 setter 메소드
		this.productAmount = productAmount;
	}
	
	public void setRentalFee (int rentalFee) { // 1일 대여료 setter 메소드
		this.rentalFee = rentalFee;
	}
	
	public void setLateFee (int lateFee) { // 1일 연체료 setter 메소드
		this.lateFee = lateFee;
	}
	
	public String getProductCode() { // 물품 번호 getter 메소드
		return productCode;
	}
	
	public String getProductName() { // 물품 이름 getter 메소드
		return productName;
	}
	
	public int getProductStock() { // 물품 재고 getter 메소드
		return productStock;
	}
	
	public int getProductAmount() { // 물품 수량 getter 메소드
		return productAmount;
	}
	
	public int getRentalFee() { // 1일 대여료 getter 메소드
		return rentalFee;
	}
	
	public int getLateFee() { // 1일 연체료 getter 메소드
		return lateFee;
	}
	
	public void addStock() { // 물품 재고 + 1
		productStock++;
	}

	public void subtractStock() { // 물품 재고 - 1
		if (productStock > 0) {
			productStock--;
			setProductStock(productStock);
		}
	}
}


