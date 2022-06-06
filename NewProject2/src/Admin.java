/*관리자 UI 클래스*/
import java.util.Scanner; // Scanner 클래스

public class Admin {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in); // scan 객체 생성
		ProductManager pm = new ProductManager(); // pm 객체 생성
		
		/* 시스템 안내창 출력 */
		while(true) {
			try {
			System.out.println("********렌탈 관리 시스템 메인입니다********");
			System.out.println("1. 관리자용 시스템\t2. 고객용 시스템\t3.시스템 종료");
			System.out.print("실행할 시스템을 선택해주세요(숫자로 입력): ");
			int system = scan.nextInt();
			if (system == 1) {
				System.out.println("\n");
				System.out.println("[관리자용 시스템]");
				System.out.println("1. 물품 추가");
				System.out.println("2. 물품 검색");
				System.out.println("3. 물품 삭제");
				System.out.println("4. 물품 리스트 출력");
				System.out.println("5. 회원 추가");
				System.out.println("6. 회원 검색");
				System.out.println("7. 회원 삭제");
				System.out.println("8. 회원 리스트 출력");
				System.out.println("9. 대여 목록 출력");
				System.out.println("10. 메인으로 돌아가기");
				System.out.print("실행할 메뉴를 선택해주세요(숫자로 입력): ");
				
				int menu = scan.nextInt(); // 메뉴 입력받기
				
				switch(menu) {
				/*물품 추가*/
				case 1: // 물품 정보 입력받기
					Product p = new Product();
					System.out.print("물품 코드: ");
					String pcode = scan.next();
					try {
						pm.searchProduct(pcode);
						System.out.println("이미 존재하는 물품 코드입니다.");
					} 
					catch(MyException e) {
						p.setProductCode(pcode);
						System.out.print("물품 이름: ");
						p.setProductName(scan.next());
						System.out.print("물품 수량: ");
						int amount = scan.nextInt();
						p.setProductStock(amount);
						p.setProductAmount(amount);
						System.out.print("물품 대여기간: ");
						p.setProductPeriod(scan.nextInt());
						// 입력한 물품을 배열에 추가
						pm.insertProduct(p); 
					}
					System.out.println("\n");
					break;
					
				/*물품 검색*/
				case 2:
					System.out.print("검색할 물품 코드를 입력해주세요: ");
					try {
						int idx = pm.searchProduct(scan.next());
						p = pm.productAt(idx);
						System.out.println("물품이 검색되었습니다.");
						System.out.println("코드:" + p.getProductCode() + "\t\t이름:" + p.getProductName() +"\t\t재고:" + p.getProductStock() + " / " + p.getProductAmount() +"\t\t대여기간:" + p.getProductPeriod());
					} 
					catch(MyException e) {
						System.err.println("해당 코드와 일치하는 물품이 없습니다.");
					}
					break;
					
				/*물품 삭제*/
				case 3:
					// 물품 코드 입력받기
					System.out.print("삭제할 물품 코드를 입력해주세요: ");
					try {
						int idx = pm.searchProduct(scan.next());
						pm.deleteProduct(idx);
					}
					catch(MyException e) {
						System.out.println("해당 코드와 일치하는 물품이 없습니다.");
					}
					System.out.println("\n");
					break;
					
				/*물품 리스트 출력*/	
				case 4:
					for(int i = 0; i < pm.getPcount(); i++) { // 배열의 처음부터 끝까지 물품 정보 출력
						p = pm.productAt(i);
						System.out.println("---------------------------------------------------------------------------------------------------------------------");
						System.out.println("코드:" + p.getProductCode() + "\t\t이름:" + p.getProductName() +"\t\t재고:" + p.getProductStock() + " / " + p.getProductAmount() +"\t\t대여기간:" + p.getProductPeriod());
					}
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("\n");
					break;
					
				/*회원 추가*/	
				case 5: // 회원 정보 입력받기
					Member m = new Member();
					System.out.print("회원 코드: ");
					String mcode = scan.next();
					try {
						pm.searchProduct(mcode);
						System.out.println("이미 존재하는 회원 코드입니다.");
					}
					catch(MyException e) {
						m.setMemberCode(mcode);
						System.out.print("회원 이름: ");
						m.setMemberName(scan.next());
						System.out.print("회원 전화번호: ");
						m.setMemberPhone(scan.next());
						m.setMemberDate(null);
						// 입력한 회원을 배열에 추가
						pm.insertMember(m); 
					}
					System.out.println("\n");
					break;
					
				/*회원 검색*/
				case 6:
					System.out.print("검색할 회원 코드를 입력해주세요: ");
					try {
						int idx = pm.searchMember(scan.next());
						m = pm.memberAt(idx);
						System.out.println("회원이 검색되었습니다.");
						System.out.print("회원코드:" + m.getMemberCode() + "\t\t이름:" + m.getMemberName() +"\t\t전화번호:" + m.getMemberPhone() + "\t\t대여일자:" + m.getMemberDate() + "\t\t대여물품코드:");
						for (int j = 0; j < 3; j++) {
							System.out.print("\t" + m.getRentArray(j));
						}
					} 
					catch(MyException e) {
						System.err.println("해당 코드와 일치하는 회원이 없습니다.");
					}
					System.out.println("\n");
					break;	
					
				/*회원 삭제*/
				case 7:
					// 회원 코드 입력받기
					System.out.print("삭제할 회원 코드를 입력해주세요: ");
					try {
						int idx = pm.searchMember(scan.next());
						pm.deleteMember(idx);
					}
					catch (MyException e) {
						System.out.println("해당 코드와 일치하는 회원이 없습니다.");
					}
					System.out.println("\n");
					break;

				
				/*회원 리스트 출력*/	
				case 8:
					for(int i = 0; i < pm.getMcount(); i++) { // 배열의 처음부터 끝까지 회원 정보 출력
						m = pm.memberAt(i);
						System.out.println("---------------------------------------------------------------------------------------------------------------------");
						System.out.print("회원코드:" + m.getMemberCode() + "\t\t이름:" + m.getMemberName() +"\t\t전화번호:" + m.getMemberPhone() + "\t\t대여일자:" + m.getMemberDate() + "\t\t대여물품코드:");
						for (int j = 0; j < 3; j++) {
							System.out.print("\t"+ m.getRentArray(j));
						}
						System.out.println("");
					}
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("\n");
					break;
			
				
				/*대여 리스트 출력*/	
				/*case 8:
					for(int i = 0; i < pm.getMcount(); i++) { // 배열의 처음부터 끝까지 회원 정보 출력
						m = pm.memberAt(i);
						if (m.getMemberDate() != null) {
							System.out.println("---------------------------------------------------------------------------------------------------------------------");
							System.out.println("대여한 회원코드:" + m.getMemberCode() + "\t\t대여한 물품코드:" + m.getMemberPcode() + "\t\t대여일자:" + m.getMemberDate());
						}
					}
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("\n");
					break;*/
				
				/*날짜 계산*/
				/*case 9:
					System.out.println("회원 코드를 입력하세요");
					System.out.println("회원님의 대여 기간은 "+ pm.calculate(scan.next()) + "일 입니다.");*/
					
					
				/*시스템 종료*/
				case 10: 
					System.out.println("시스템을 종료합니다.");
					break;
						
				
				/*메뉴에 없는 숫자를 입력할 경우*/	
				default:
					System.out.println("잘못 입력하셨습니다. 숫자 1~4 중에서 입력해주세요.");
					System.out.println("\n");
					break;			
				}	
				if (menu == 10) // while문 탈출
						break;
			}
			else if (system == 2) {
				System.out.println("\n");
				System.out.println("[고객용 시스템]");
				System.out.println("1. 물품 리스트 출력");
				System.out.println("2. 물품 대여");
				System.out.println("3. 물품 반납");
				System.out.println("4. 시스템 종료");
				System.out.print("실행할 메뉴를 숫자로 입력해주세요: ");
		
				int menu = scan.nextInt(); // 메뉴 입력받기
				
				switch(menu) {
				/*물품 리스트 출력*/	
				case 1:
					Product p = new Product();
					for(int i = 0; i < pm.getPcount(); i++) { // 배열의 처음부터 끝까지 물품 정보 출력
						p = pm.productAt(i);
						System.out.println("---------------------------------------------------------------------------------------------------------------------");
						System.out.println("코드:" + p.getProductCode() + "\t\t이름:" + p.getProductName() +"\t\t재고:" + p.getProductStock() + " / " + p.getProductAmount() +"\t\t대여기간:" + p.getProductPeriod());
					}
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("\n");
					break;
					
					/*물품 렌트*/
					case 2:
						System.out.print("회원 코드를 입력해주세요: ");
						try {
							Member m = new Member();
							int midx = pm.searchMember(scan.next()); // 회원 코드 검색
							m = pm.memberAt(midx);
							System.out.print("대여할 물품 코드를 입력해주세요: ");
							String pcode = scan.next();
							try {
									int pidx = pm.searchProduct(pcode);
									p = pm.productAt(pidx);
									if (p.getProductStock() == 0) System.out.println("해당 물품은 재고가 없습니다.");
									else {
										pm.rentalProduct(pcode, m, p);
										System.out.println("물품이 대여되었습니다.");
									}
								} 
							catch(MyException e) {
								System.err.println("해당 코드와 일치하는 물품이 없습니다.");
							}
						}
						catch(MyException e) {
							System.err.println("해당 코드와 일치하는 회원이 없습니다.");
						}
						System.out.println("\n");
						break;
						
						/*System.out.print("회원 코드를 입력해주세요: ");
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
								m.setRentDate(); // 오늘 날짜 설정
								System.out.print(m.getMemberDate()); // 오늘 날짜 출력
								System.out.println(" 입력하신 물품의 대여가 완료되었습니다.");
								m.setMemberDate(m.getMemberDate()); // 오늘 날짜 가져오기
								int pidx = pm.searchProduct(code); 
								p = pm.productAt(pidx); // 입력한 물품 코드에 해당하는 물품 p
								p.setProductDate(m.getMemberDate()); // 물품 p에 오늘 날짜를 대여일자로 저장
							}
						}
						System.out.println("\n");
						break;*/
						
					
				/*시스템 종료*/
				/*case 3: 
					System.out.println("시스템을 종료합니다.");
					break;*/
					
				
				/*메뉴에 없는 숫자를 입력할 경우*/	
				default:
					System.out.println("잘못 입력하셨습니다. 숫자 1~4 중에서 입력해주세요.");
					System.out.println("\n");
					break;
				
				} 
			}
			else if (system == 3) // 시스템 종료(while문 탈출)
				break;
			else {
				System.out.println("잘못 입력하셨습니다. 숫자 1~3 중에서 입력해주세요.");
				System.out.println("\n");
			}
			} catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("최대 입력 개수를 초과할 수 없습니다");
			}
		}
		scan.close();
	}
}
			