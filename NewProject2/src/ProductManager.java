import java.util.Date;
/*물품 관리 클래스*/
public class ProductManager {
	private final static int MAX_SIZE1 = 100;
	private final static int MAX_SIZE2 = 50;

	private Product[] plist; // 물품을 저장하는 배열 plist
	private int pcount; // 물품 개수
	private Member[] mlist; // 회원을 저장하는 배열 mlist
	private int mcount; // 회원 수
	private int sales; // 당일 매출액
		
	public ProductManager() { // 생성자
		plist = new Product[MAX_SIZE1];
		mlist = new Member[MAX_SIZE2];
		pcount = 0;
		mcount = 0;
	}
	
	public Product productAt(int i) { // plist[i]를 리턴하는 함수
		return plist[i];
	}
	
	public int getPcount() { // pcount를 리턴하는 함수
		return pcount;
	}
	
	/*물품 추가 함수*/
	public void insertProduct(Product p) {
		plist[pcount++] = p;
	}
	
	/*물품 검색 함수*/
	public int searchProduct(String productCode) throws MyException {
		int pidx = -1; // 검색한 물품의 인덱스
		for (int i = 0; i < pcount; i++) {
			if (plist[i].getProductCode().equals(productCode)) 
				pidx = i;
		} 
		if (pidx == -1) throw new MyException();
		return pidx;
	} 
	
	/*물품 삭제 함수*/
	public void deleteProduct(int index) {
		for (int i = index; i < pcount; i++) { // 배열에서 해당 물품 위치부터 물품들을 앞으로 한칸씩 당기기
			plist[i] = plist[i+1];
		}
		pcount--; // 물품 개수 - 1
	}
	
	public Member memberAt(int i) { // mlist[i]를 리턴하는 함수
		return mlist[i];
	}
	
	public int getMcount() { // mcount를 리턴하는 함수
		return mcount;
	}
	
	/*회원 추가 함수*/
	public void insertMember(Member m) {
		mlist[mcount++] = m;
	}
	
	/*회원 검색 함수*/
	public int searchMember(String memberCode) throws MyException {
		int midx = -1; // 검색한 회원의 인덱스
		for (int i = 0; i < mcount; i++) {
				if (mlist[i].getMemberCode().equals(memberCode)) 
					midx = i;
		} 
		if (midx == -1) throw new MyException();
		return midx;
	}
	
	/*회원 삭제 함수*/
	public void deleteMember(int index) {
		for (int i = index; i < mcount; i++) { // 배열에서 해당 회원 위치부터 회원들을 앞으로 한칸씩 당기기
			mlist[i] = mlist[i+1]; 
		}
		mcount--; // 회원 수 - 1
	}
	
	/*물품 대여 함수*/
	public void rentalProduct(int midx, int pidx, String productCode) {
		plist[pidx].subtractStock();
		mlist[midx].addRentArray(productCode);
		mlist[midx].setRentDate();
	}	
	
	/*물품 반납 함수*/
	public void returnProduct(int midx, int pidx, Date today) {
		plist[pidx].addStock();
		int period = mlist[midx].calculate(today) - mlist[midx].getMemberPeriod(); // 실제 대여 기간 - 설정한 대여 기간
		int fee, totalFee = 0;
		if (period > 0) {
			for (int j = 0; j < 3; j++) {
				fee = mlist[midx].getMemberPeriod() * plist[pidx].getRentalFee() + period * plist[pidx].getLateFee();
				totalFee = totalFee + fee;
			} 
			setSales(totalFee);
		}
		else {
			for (int j = 0; j < 3; j++) {
				fee = mlist[midx].calculate(today) * plist[pidx].getRentalFee();
				totalFee = totalFee + fee;
			}
			setSales(totalFee);
		}
	}
	
	public void setSales(int sales) { // 매출 setter 메소드
		this.sales = sales;
	}
	
	public int getSales() { // 매출 getter 메소드
		return sales;
	}
	
	
}
