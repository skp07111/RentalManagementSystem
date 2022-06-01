/*관리자용 UI 클래스*/
import java.util.Scanner; // Scanner 클래스

public class Admin {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in); // scan 객체 생성
		ProductManager pm = new ProductManager(); // pm 객체 생성
		
		int pnumber; 		// 물품 번호
		String pname; 		// 물품 이름
		int pperiod;		// 물품 대여기간
		String pstatus;		// 물품 대여상태
		String pdate; 		// 물품 대여일자
		int mnumber; 		// 회원 번호
		String mname;		// 회원 이름
		String mphone; 		// 회원 전화번호
		String mstatus;		// 회원 대여상태
		String mdate; 		// 회원 상태
		
		/* 시스템 안내창 출력 */
		while(true) {
			System.out.println("********관리자용 시스템입니다********");
			System.out.println("1. 물품 추가");
			System.out.println("2. 물품 삭제");
			System.out.println("3. 물품 수정");
			System.out.println("4. 물품 검색");
			System.out.println("5. 물품 리스트 출력");
			System.out.println("6. 회원 추가");
			System.out.println("7. 회원 삭제");
			System.out.println("8. 회원 수정");
			System.out.println("9. 회원 검색");
			System.out.println("10. 회원 리스트 출력");
			System.out.println("11. 시스템 종료");
			System.out.print("실행할 메뉴를 숫자로 입력해주세요: ");
	
			int menu = scan.nextInt(); // 메뉴 입력받기
			int count = 0; // 검색 물품 및 회원 유무를 count 해주는 변수
			
			switch(menu) {
			/*물품 추가*/
			case 1: // 물품 정보 입력받기
				System.out.print("물품 번호: "); 
				pnumber = scan.nextInt();
				System.out.print("물품 이름: ");
				pname = scan.next();
				System.out.print("물품 대여기간: ");
				pperiod = scan.nextInt();
				System.out.print("물품 대여상태: ");
				pstatus = scan.next();
				System.out.print("물품 대여일자: ");
				pdate = scan.next();
				// 입력한 물품을 배열 plist에 추가
				pm.insertProduct(pnumber, pname, pperiod, pstatus, pdate); 
				System.out.println("\n");
				break;
				
			/*물품 삭제*/
			case 2:
				// 물품 번호 입력받기
				System.out.print("삭제할 물품 번호를 입력해주세요: ");
				pnumber = scan.nextInt();
				// 물품 검색
				for (int i = 0; i < pm.pidx; i++) {
					if(pm.plist[i].getProductNumber() == pnumber) { // 검색한 물품이 있는 경우,
						// 해당 물품 삭제	
						pm.deleteProduct(pnumber);		
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 물품이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 물품이 없습니다.");
				}
				System.out.println("\n");
				break;
				
			/*물품 수정*/
			case 3: 
				// 물품 번호 입력받기
				System.out.print("수정할 물품 번호를 입력해주세요: ");	
				pnumber = scan.nextInt();
				// 물품 검색
				for (int i = 0; i < pm.pidx; i++) {
					if(pm.plist[i].getProductNumber() == pnumber) { // 검색한 물품이 있는 경우,
						// 수정할 물품 정보 입력받기
						System.out.print("수정할 물품 이름: ");
						pname = scan.next();
						System.out.print("수정할 물품 대여기간: ");
						pperiod = scan.nextInt();						
						System.out.print("수정할 물품 대여상태: ");
						pstatus = scan.next();						
						System.out.print("수정할 물품 대여일자: ");
						pdate = scan.next();
						// 입력받은 값으로 해당 물품 정보 수정
						pm.editProduct(pnumber, pname, pperiod, pstatus, pdate);
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 물품이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 물품이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*물품 검색*/
			case 4: 
				// 물품 번호 입력받기
				System.out.print("검색할 물품 번호를 입력해주세요: ");
				pnumber = scan.nextInt();
				// 물품 검색
				for (int i = 0;i < pm.pidx; i++) {
					if(pm.plist[i].getProductNumber() == pnumber) { // 검색한 물품이 있는 경우, 해당 물품 정보 출력
						System.out.println("번호:" + pm.plist[i].getProductNumber() + "\t\t이름:" + pm.plist[i].getProductName() +"\t\t대여기간:" + pm.plist[i].getProductPeriod() + "\t\t대여상태:" + pm.plist[i].getProductStatus() + "\t\t대여일자:" + pm.plist[i].getProductDate());
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 물품이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 물품이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*물품 리스트 출력*/	
			case 5:
				for(int i = 0; i < pm.pidx; i++) { // 배열 plist의 처음부터 끝까지 물품 정보 출력
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("번호:" + pm.plist[i].getProductNumber() + "\t\t이름:" + pm.plist[i].getProductName() +"\t\t대여기간:" + pm.plist[i].getProductPeriod() + "\t\t대여상태:" + pm.plist[i].getProductStatus() + "\t\t대여일자:" + pm.plist[i].getProductDate());
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;
			
			/*회원 추가*/
			case 6: // 회원 정보 입력받기
				System.out.print("회원 번호: ");
				mnumber = scan.nextInt();
				System.out.print("회원 이름: ");
				mname = scan.next();
				System.out.print("회원 전화번호: ");
				mphone = scan.next();
				System.out.print("회원 대여상태: ");
				mstatus = scan.next();
				System.out.print("회원 대여일자: ");
				mdate = scan.next();
				// 입력한 회원을 배열 mlist에 추가
				pm.insertMember(mnumber, mname, mphone, mstatus, mdate);
				System.out.println("\n");
				break;
				
			/*회원 삭제*/
			case 7: 
				// 회원 번호 입력받기
				System.out.print("삭제할 회원 번호를 입력해주세요: ");
				mnumber = scan.nextInt();
				// 회원 검색
				for (int i = 0; i < pm.midx; i++) {
					if(pm.mlist[i].getMemberNumber() == mnumber) { // 검색한 회원이 있는 경우,
						// 해당 회원 삭제
						pm.deleteMember(mnumber);			
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 회원이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 회원이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*회원 수정*/
			case 8: 
				// 회원 번호 입력받기
				System.out.print("수정할 회원 번호를 입력해주세요: ");	
				mnumber = scan.nextInt();
				// 회원 검색
				for (int i = 0; i < pm.midx; i++) {
					if(pm.mlist[i].getMemberNumber() == mnumber) { // 검색한 회원이 있는 경우,
						// 수정할 회원 정보 입력받기
						System.out.print("수정할 회원 이름: ");
						mname = scan.next();
						System.out.print("수정할 회원 전화번호: ");
						mphone = scan.next();
						System.out.print("수정할 회원 대여상태: ");
						mstatus = scan.next();		
						System.out.print("수정할 회원 대여일자: ");
						mdate = scan.next();
						// 입력받은 값으로 해당 회원 정보 수정
						pm.editMember(mnumber, mname, mphone, mstatus, mdate);
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 회원이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 회원이 없습니다.");
				}
				System.out.println("\n");
				break;
		
			/*회원 검색*/
			case 9: 
				// 회원 번호 입력받기
				System.out.print("검색할 회원 번호를 입력해주세요: ");
				mnumber = scan.nextInt();
				// 회원 검색
				for (int i = 0;i < pm.midx; i++) {
					if(pm.mlist[i].getMemberNumber() == mnumber) { // 검색한 회원이 있는 경우, 해당 회원 정보 출력
						System.out.println("번호:" + pm.mlist[i].getMemberNumber() + "\t\t이름:" + pm.mlist[i].getMemberName() +"\t\t전화번호:" + pm.mlist[i].getMemberPhone() + "\t\t대여상태:" + pm.mlist[i].getMemberStatus() + "\t\t대여일자:" + pm.mlist[i].getMemberDate());
						count++; // count != 0
					}
				}
				if(count == 0) { // 검색한 회원이 없는 경우, 해당 메시지 출력
					System.out.print("해당 번호와 일치하는 회원이 없습니다.");
				}
				System.out.println("\n");
				break;
			
			/*회원 리스트 출력*/	
			case 10: 
				for(int i = 0; i < pm.midx; i++) { // 배열 mlist의 처음부터 끝까지 회원 정보 출력
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("번호:" + pm.mlist[i].getMemberNumber() + "\t\t이름:" + pm.mlist[i].getMemberName() +"\t\t전화번호:" + pm.mlist[i].getMemberPhone() + "\t\t대여상태:" + pm.mlist[i].getMemberStatus() + "\t\t대여일자:" + pm.mlist[i].getMemberDate());
				}
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.println("\n");
				break;
			
			/*시스템 종료*/
			case 11: 
				System.out.println("시스템을 종료합니다.");
				break;
			
			/*메뉴에 없는 숫자를 입력할 경우*/	
			default: // 해당 메시지 출력
				System.out.println("잘못 입력하셨습니다. 숫자 1~11 중에서 입력해주세요.");
				System.out.println("\n");
				break;
			}
			// while문 탈출
			if (menu == 11)
				break;
		} 
		scan.close();
	}	
}
