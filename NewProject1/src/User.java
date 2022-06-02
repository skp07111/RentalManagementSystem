/*사용자 UI 클래스*/
import java.util.Scanner; // Scanner 클래스


public class User {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in); // scan 객체 생성
		ProductManager pm = new ProductManager(); // pm 객체 생성
		Product p = new Product();
		Member m = new Member();

		
		/* 시스템 안내창 출력 */
		while(true) {
			System.out.println("********고객용 시스템입니다********");
			System.out.println("1. 물품 리스트 출력");
			System.out.println("2. 물품 렌트");
			System.out.println("3. 시스템 종료");
			System.out.print("실행할 메뉴를 숫자로 입력해주세요: ");
	
			int menu = scan.nextInt(); // 메뉴 입력받기
			
			switch(menu) {
			/*물품 리스트 출력*/	
			case 1:
				for(int i = 0; i < pm.getPcount(); i++) { // 배열의 처음부터 끝까지 물품 정보 출력
					p = pm.productAt(i);
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("번호:" + p.getProductCode() + "\t\t이름:" + p.getProductName() +"\t\t재고:" + p.getProductStock() + " / " + p.getProductAmount() +"\t\t대여기간:" + p.getProductPeriod() + "\t\t대여일자:" + p.getProductDate());
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;

			/*물품 렌트*/
			case 2:
				System.out.print("회원 번호를 입력해주세요: ");
				int midx = -1;
				midx = pm.searchMember(scan.next());
				if (midx == -1) 
					System.out.println("입력하신 회원 번호가 존재하지 않습니다.");
				else {
					System.out.print("렌트할 물품 번호를 입력해주세요: ");
					String pcode = scan.next();
					if (pm.rentalProduct(pcode) == false) 
						 System.out.println("입력하신 물품 번호와 일치하는 물품이 없습니다.");
					else {
						m = pm.memberAt(midx);
						m.setMemberPcode(pcode);
						System.out.print(m.getToday());
						System.out.println(" 입력하신 물품의 대여가 완료되었습니다.");
						m.setMemberDate(m.getToday());
						int pidx = pm.searchProduct(pcode);
						p = pm.productAt(pidx);
						p.setProductDate(m.getToday());
					}
				}
				System.out.println("\n");
				break;
			
			/*시스템 종료*/
			case 3: 
				System.out.println("시스템을 종료합니다.");
				break;
				
			
			/*메뉴에 없는 숫자를 입력할 경우*/	
			default:
				System.out.println("잘못 입력하셨습니다. 숫자 1~4 중에서 입력해주세요.");
				System.out.println("\n");
				break;
			}
			if (menu == 3) // while문 탈출
				break;
		} 
		scan.close();
	}	
}
