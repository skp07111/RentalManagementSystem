/*관리자 UI 클래스*/
import java.util.Scanner; // Scanner 클래스

public class Admin {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in); // scan 객체 생성
		ProductManager pm = new ProductManager(); // pm 객체 생성
		
		/* 시스템 안내창 출력 */
		while(true) {
			System.out.println("********관리자용 시스템입니다********");
			System.out.println("1. 물품 추가");
			System.out.println("2. 물품 삭제");
			System.out.println("3. 물품 리스트 출력");
			System.out.println("4. 회원 추가");
			System.out.println("5. 회원 삭제");
			System.out.println("6. 회원 리스트 출력");
			System.out.println("7. 물품 렌트");
			System.out.println("8. 대여 리스트 출력");
			System.out.println("9. 시스템 종료");
			System.out.print("실행할 메뉴를 숫자로 입력해주세요: ");
	
			int menu = scan.nextInt(); // 메뉴 입력받기

			switch(menu) {
			/*물품 추가*/
			case 1: // 물품 정보 입력받기
				Product p = new Product();
				System.out.print("물품 코드: ");
				p.setProductCode(scan.next());
				String pcode = p.getProductCode();
				if (pm.isSameProduct(pcode) == false) {
					System.out.println("이미 존재하는 물품 코드입니다.");
				}
				else {
					System.out.print("물품 이름: ");
					p.setProductName(scan.next());
					System.out.print("물품 재고: ");
					p.setProductStock(scan.nextInt());
					System.out.print("물품 수량: ");
					p.setProductAmount(scan.nextInt());
					System.out.print("물품 대여기간: ");
					p.setProductPeriod(scan.nextInt());
					p.setProductDate(null);
					// 입력한 물품을 배열에 추가
					pm.insertProduct(p); 
				}
				System.out.println("\n");
				break;
				
			/*물품 삭제*/
			case 2:
				// 물품 코드 입력받기
				System.out.print("삭제할 물품 코드를 입력해주세요: ");
				try {
					pm.deleteProduct(scan.next());
				}
				catch (ArrayIndexOutOfBoundsException e) 
				{
					System.out.println("해당 코드와 일치하는 물품이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*물품 리스트 출력*/	
			case 3:
				for(int i = 0; i < pm.getPcount(); i++) { // 배열의 처음부터 끝까지 물품 정보 출력
					p = pm.productAt(i);
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("코드:" + p.getProductCode() + "\t\t이름:" + p.getProductName() +"\t\t재고:" + p.getProductStock() + " / " + p.getProductAmount() +"\t\t대여기간:" + p.getProductPeriod() + "\t\t대여일자:" + p.getProductDate());
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;
				
			/*회원 추가*/	
			case 4: // 회원 정보 입력받기
				Member m = new Member();
				System.out.print("회원 코드: ");
				m.setMemberCode(scan.next());
				String mcode = m.getMemberCode();
				if (pm.isSameMember(mcode) == false) {
					System.out.println("이미 존재하는 회원 코드입니다.");
				}
				else {
					System.out.print("회원 이름: ");
					m.setMemberName(scan.next());
					System.out.print("회원 전화번호: ");
					m.setMemberPhone(scan.next());
					m.setMemberPcode(null);
					m.setMemberDate(null);
					// 입력한 회원을 배열에 추가
					pm.insertMember(m); 
				}
				System.out.println("\n");
				break;
				
			/*회원 삭제*/
			case 5:
				// 회원 코드 입력받기
				System.out.print("삭제할 회원 코드를 입력해주세요: ");
				try {
					pm.deleteMember(scan.next());
				}
				catch (ArrayIndexOutOfBoundsException e) 
				{
					System.out.println("해당 코드와 일치하는 회원이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*회원 리스트 출력*/	
			case 6:
				for(int i = 0; i < pm.getMcount(); i++) { // 배열의 처음부터 끝까지 회원 정보 출력
					m = pm.memberAt(i);
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("회원코드:" + m.getMemberCode() + "\t\t이름:" + m.getMemberName() +"\t\t전화번호:" + m.getMemberPhone() + "\t\t대여물품:" + m.getMemberPcode() + "\t\t대여일자:" + m.getMemberDate());
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;
		
			/*물품 렌트*/
			case 7:
				System.out.print("회원 코드를 입력해주세요: ");
				int midx = -1;
				midx = pm.searchMember(scan.next()); // 회원 코드 검색
				if (midx == -1) 
					System.out.println("입력하신 회원 코드가 존재하지 않습니다.");
				else {
					System.out.print("렌트할 물품 코드를 입력해주세요: ");
					String code = scan.next();
					if (pm.rentalProduct(code) == false)
						 System.out.println("입력하신 물품 코드와 일치하는 물품이 없습니다."); // 입력한 물품 코드에 해당하는 물품이 없는 경우
					else { // 입력한 물품 코드에 해당하는 물품이 있는 경우
						m = pm.memberAt(midx);
						m.setMemberPcode(code); // 회원 m에 해당 물품코드 저장
						System.out.print(m.getToday()); // 오늘 날짜 출력
						System.out.println(" 입력하신 물품의 대여가 완료되었습니다.");
						m.setMemberDate(m.getToday()); // 오늘 날짜 가져오기
						int pidx = pm.searchProduct(code); 
						p = pm.productAt(pidx); // 입력한 물품 코드에 해당하는 물품 p
						p.setProductDate(m.getToday()); // 물품 p에 오늘 날짜를 대여일자로 저장
					}
				}
				System.out.println("\n");
				break;
			
			/*대여 리스트 출력*/	
			case 8:
				for(int i = 0; i < pm.getMcount(); i++) { // 배열의 처음부터 끝까지 회원 정보 출력
					m = pm.memberAt(i);
					if (m.getMemberDate() != null) {
						System.out.println("---------------------------------------------------------------------------------------------------------------------");
						System.out.println("대여한 회원코드:" + m.getMemberCode() + "\t\t대여한 물품코드:" + m.getMemberPcode() + "\t\t대여일자:" + m.getMemberDate());
					}
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;
				
				
			/*시스템 종료*/
			case 9: 
				System.out.println("시스템을 종료합니다.");
				break;
					
			
			/*메뉴에 없는 숫자를 입력할 경우*/	
			default:
				System.out.println("잘못 입력하셨습니다. 숫자 1~4 중에서 입력해주세요.");
				System.out.println("\n");
				break;
			}
			if (menu == 9) // while문 탈출
					break;
		} 
		scan.close();
	}
}
