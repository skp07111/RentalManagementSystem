import java.util.Date;
/*물품 관리 클래스*/
public class ProductManager {
	private final static int MAX_SIZE1 = 100;
	private final static int MAX_SIZE2 = 50;
	
	private int sales; // 현재 매출액
	private int totalsales; // 총 매출액(누적 매출액)
	private int rentalfee; // 1일 대여료
	private int latefee; // 1일 연체료

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
	
	/*물품 렌트 함수*/
	public void rentalProduct(String productCode, Member m, Product p) {
		p.subtractStock();
		m.setRentArray(productCode);
		m.setRentDate();
		m.setMemberDate(m.getMemberDate());
	}
	
	/*날짜 계산 함수*/
	/*public long calculate(String memberCode) {
		int index = -1;
		index = searchMember(memberCode);
		if (index != -1 || mlist[index].getMemberDate() != null) {
			Date rentDate = mlist[index].getMemberDate();
			Date returnDate = new Date();
			long rentSec = returnDate.getTime() - rentDate.getTime();
			long rentDays = rentSec / (24*60*60);
			return rentDays;
		}
		else return 0;
	}*/

}