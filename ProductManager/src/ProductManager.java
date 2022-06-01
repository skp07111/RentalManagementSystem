/*렌탈 관리 클래스*/
public class ProductManager {
	private int sales; // 현재 매출액
	private int totalsales; // 총 매출액(누적 매출액)
	public int rentalfee; // 1일 대여료
	public int latefee; // 1일 연체료

	public Product[] plist; // 물품을 저장하는 배열 plist
	public Member[] mlist; // 회원을 저장하는 배열 mlist
	public int pidx; // 물품 index
	public int midx; // 회원 index
	
	public ProductManager() { // 생성자
		plist = new Product[4];
		mlist = new Member[4];
		pidx = 0;
		midx = 0;
	}
	
	/*물품 추가 함수*/
	public void insertProduct(int product_number, String product_name, int product_period, String product_status, String product_date) {
		Product p = new Product(); // 객체 p 생성
		// p에 물품 정보 입력
		p.setProductNumber(product_number);
		p.setProductName(product_name);
		p.setProductPeriod(product_period);
		p.setProductStatus(product_status);
		p.setProductDate(product_date);
		// p를 배열 plist에 추가
		plist[pidx++] = p;
	}
	
	/*물품 삭제 함수*/
	public void deleteProduct(int product_number) {
		for (int i = 0; i < pidx; i++) {
			if(plist[i].getProductNumber() == product_number) { // 물품 번호에 해당하는 물품이 있으면,
				for ( ; i < pidx; i++) {
					plist[i] = plist[i+1]; // 배열 plist에서 해당 물품 위치부터 물품들을 앞으로 한칸씩 당기기
				}
				pidx--; // 물품 인덱스 - 1
			}
		}
	}
	
	/*물품 수정 함수*/
	public void editProduct(int product_number, String product_name, int product_period, String product_status, String product_date) {
		for (int i = 0; i < pidx; i++) {
			if(plist[i].getProductNumber() == product_number) { // 물품 번호에 해당하는 물품이 있으면,
				// 물품 정보 수정하기
				plist[i].setProductName(product_name);
				plist[i].setProductPeriod(product_period);
				plist[i].setProductStatus(product_status);
				plist[i].setProductDate(product_date);
			}
		}
	}
	
	/*회원 추가 함수*/
	public void insertMember(int member_number, String member_name, String member_phone, String member_status, String member_date) {
		Member m = new Member(); // 객체 m 생성
		// m에 회원 정보 입력
		m.setMemberNumber(member_number);
		m.setMemberName(member_name);
		m.setMemberPhone(member_phone);
		m.setMemberStatus(member_status);
		m.setMemberDate(member_date);
		// m을 배열 mlist에 추가
		mlist[midx++] = m;
	}
	
	/*회원 삭제 함수*/
	public void deleteMember(int member_number) { 
		for (int i = 0; i < midx; i++) {
			if(mlist[i].getMemberNumber() == member_number) { // 회원 번호에 해당하는 회원이 있으면,
				for ( ; i < midx; i++) {
					mlist[i] = mlist[i+1]; // 배열 mlist에서 해당 회원 위치부터 회원들을 앞으로 한칸씩 당기기
				}
				midx--; // 회원 인덱스 - 1
			}
		}
	}
	
	/*회원 수정 함수*/
	public void editMember(int member_number, String member_name, String member_phone, String member_status, String member_date) {
		for (int i = 0; i < midx; i++) {
			if(mlist[i].getMemberNumber() == member_number) {  // 회원 번호에 해당하는 회원이 있으면,
				// 회원 정보 수정하기
				mlist[i].setMemberName(member_name);
				mlist[i].setMemberPhone(member_phone);
				mlist[i].setMemberStatus(member_status);
				mlist[i].setMemberDate(member_date);
			}
		}
	}
}
