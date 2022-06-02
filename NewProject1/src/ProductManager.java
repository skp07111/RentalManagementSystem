/*물품 관리 클래스*/
public class ProductManager {
	private final static int MAX_SIZE1 = 100;
	private final static int MAX_SIZE2 = 50;
	
	//private int sales; // 현재 매출액
	//private int totalsales; // 총 매출액(누적 매출액)
	//private int rentalfee; // 1일 대여료
	//private int latefee; // 1일 연체료

	private Product[] plist; // 물품을 저장하는 배열 plist
	private int pcount; // 물품 개수
	private Member[] mlist; // 회원을 저장하는 배열 mlist
	private int mcount; // 회원 수
		
	public ProductManager() { // 생성자
		plist = new Product[MAX_SIZE1];
		mlist = new Member[MAX_SIZE2];
		pcount = 0;
		mcount = 0;
	}
	
	public Product productAt(int i) {
		return plist[i];
	}
	
	public Member memberAt(int i) {
		return mlist[i];
	}
	
	public int getPcount() {
		return pcount;
	}
	
	public int getMcount() {
		return mcount;
	}
	
	/*물품코드 유무를 확인하는 함수*/
	public boolean isSameProduct(String productCode) {
		int index = -1;
		index = searchProduct(productCode);
		if (index == -1)
			return true;
		else return false;
	}
	
	/*회원코드 유무를 확인하는 함수*/
	public boolean isSameMember(String memberCode) {
		int index = -1;
		index = searchMember(memberCode);
		if (index == -1)
			return true;
		else return false;
	}
	
	/*물품 추가 함수*/
	public void insertProduct(Product p) {
		plist[pcount++] = p;
	}
	
	/*물품 검색 함수*/
	public int searchProduct(String productCode) {
		int sidx = -1; // 검색한 물품의 인덱스
		
		for (int i = 0; i < pcount; i++) {
			if(plist[i].getProductCode().equals(productCode)) {
				sidx = i;
			}
		} 
		return sidx;
	}
	
	/*물품 삭제 함수*/
	public void deleteProduct(String productCode) {
		int didx = searchProduct(productCode); // 삭제할 물품의 인덱스
	
		for (int i = didx; i < pcount; i++) { // 배열에서 해당 물품 위치부터 물품들을 앞으로 한칸씩 당기기
			plist[i] = plist[i+1]; 
		}
		pcount--; // 물품 개수 - 1
	}	

	/*물품 렌트 함수*/
	public boolean rentalProduct(String productCode) {
		int ridx = -1;
		ridx = searchProduct(productCode);
		if (ridx != -1) {
			int pstock = plist[ridx].getProductStock();
			pstock--;
			if(pstock >= 0) {
				plist[ridx].setProductStock(pstock);
				return true; // 물품 코드가 검색되고 재고가 0 이상이면 true
			}
			else return false; // 재고가 음수이면 false
		}
		else return false; // 물품 코드가 검색되지 않으면 false
	}
	
	/*회원 추가 함수*/
	public void insertMember(Member m) {
		mlist[mcount++] = m;
	}
	
	/*회원 검색 함수*/
	public int searchMember(String memberCode) {
		int sidx = -1; // 검색한 회원의 인덱스
		
		for (int i = 0; i < mcount; i++) {
			if(mlist[i].getMemberCode().equals(memberCode)) {
				sidx = i;
			}
		} 
		return sidx;
	}
	
	/*회원 삭제 함수*/
	public void deleteMember(String memberCode) {
		int didx = searchMember(memberCode); // 삭제할 회원의 인덱스
	
		for (int i = didx; i < mcount; i++) { // 배열에서 해당 회원 위치부터 회원들을 앞으로 한칸씩 당기기
			mlist[i] = mlist[i+1]; 
		}
		mcount--; // 회원 수 - 1
	}	
}