import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ui {
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		Calendar getToday = Calendar.getInstance();
		Manager act = new Manager(100, 100);
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
	
		
		/* 파일 read */
		
		// 아래 코드는 재사용이 불가한 코드이므로 객체 지향 프로그래밍과는 거리가 멉니다.
		// write할 때와 마찬가지로 read 역시 해당 데이터가 있는 각 클래스에서 진행하여 재사용이 가능하도록 변경해야합니다.
		
		try {
			fis = act.getFis(); // Manager 클래스의 FileInputStream
			dis = act.getDis(); // Manager 클래스의 DataInputStream
			
			int productCount = dis.readInt(); // 전체 물품 수 read
			for (int i = 0; i < productCount; i++) {
				String addName = dis.readUTF(); // 물품 이름 read
				String addCode = dis.readUTF(); // 물품 코드 read
				int addNumber = dis.readInt(); // 물품 개수 read
				int addPrice = dis.readInt(); // 물품 가격 read
				
				Product p1 = new Product(addName, addCode, addNumber, addPrice); // 물품 객체 생성
				
				act.add(p1); // 물품 추가
			}

			int userCount = dis.readInt(); // 전체 사용자 수 read
			for (int i = 0; i < userCount; i++) {
				String name = dis.readUTF(); // 사용자 이름 read
				String phone = dis.readUTF(); // 사용자 전화번호 read
				String rentalDay = dis.readUTF(); // 사용자 대여일자 read
				String returnDay = dis.readUTF(); // 사용자 반납일자 read

				User u1 = new User(name, phone, rentalDay, returnDay); // 사용자 객체 생성
				
				int rentalNum = dis.readInt(); // 대여 물품 코드 배열 개수 read
				for (int j = 0; j < rentalNum; j++) {
					String rentalCode = dis.readUTF(); // 물품 코드
					int index = act.search(rentalCode); // 물품코드와 일치하는 물품의 index
					int amount = act.productAt(index).getPrice(); // index가 가르키는 물품의 가격
					u1.addProduct(rentalCode, amount); // 대여 물품 코드 배열에 코드 추가
				}
				act.readCheckIn(u1); // 사용자 체크인 정보 read
			}
			act.setRenvenue(dis.readInt());	// 매출 정보 read
			
			System.out.println("파일을 읽었습니다.");
		} 
		catch (EOFException eofe) {
			System.out.println("파일 읽기가 끝났습니다.");
		} 
		catch (IOException ioe) {
			System.out.println("파일을 읽을 수 없습니다.");
		}
		finally {
			try {
				dis.close();
				fis.close();
			} 
			catch (Exception e) {
			}
		}

		while (true) {
			Menu.menu(); // 메뉴 보여주기
			int menu = scan.nextInt(); // 번호 입력받기

			// 번호에 따른 메소드 실행
			switch (menu) {
			case 1: // 상품 등록
				try {
					// 상품 이름 입력받기
					scan.nextLine();
					System.out.printf("등록할 상품의 이름을 입력하세요 : ");
					String addName = scan.nextLine();

					// 상품 코드 입력받기
					System.out.printf("등록할 상품의 코드를 입력하세요 : ");
					String addCode = scan.nextLine();

					// 상품 개수 입력받기
					System.out.printf("등록할 상품의 개수를 입력하세요 : ");
					int addNumber = scan.nextInt();

					// 상품 가격 입력받기
					System.out.printf("등록할 상품의 가격을 입력하세요 : ");
					int addPrice = scan.nextInt();

					// 새 상품 객체 생성
					Product p1;
					p1 = new Product(addName, addCode, addNumber, addPrice);

					// 상품 추가하기
					act.add(p1);
					System.out.println(" ");
					System.out.println("상품을 등록하였습니다.");
					System.out.println(" ");
				}

				catch (Exception e) { // 예외 발생
					System.out.println(" ");
					System.out.println("잘못된 상품 등록입니다.");
					System.out.println(" ");
				}

				break; // switch문 종료

			case 2: // 상품 삭제
				try {
					// 상품 코드 입력받기
					scan.nextLine();
					System.out.printf("삭제할 상품의 코드를 입력하세요 : ");
					String delCode = scan.nextLine();

					// 상품 삭제하기
					act.delete(delCode);
					System.out.println(" ");
					System.out.println("상품이 삭제되었습니다.");
					System.out.println(" ");
				}

				catch (Exception e) { // 예외 발생
					System.out.println(" ");
					System.out.println("존재하지 않는 상품입니다.");
					System.out.println(" ");
				}

				break; // switch문 종료

			case 3: // 전체 상품 보여주기
				// 상품 출력
				System.out.println(" ");

				if (act.productAt(0) == null) // 원소가 없을 경우
					System.out.println("상품이 없습니다.");

				else { // 원소가 있을 경우
					System.out.println("전체 상품을 출력합니다."); // 출력 안내 문구 출력

					for (int i = 0; i < act.getProductCount(); i++) {
						// product 객체에서 필요한 정보 추출
						String name = act.productAt(i).getName();
						String code = act.productAt(i).getCode();
						int number = act.productAt(i).getNumber();
						int price = act.productAt(i).getPrice();

						// 출력
						System.out.printf("제품명: %s, 코드: %s, 개수: %d, 가격: %d", name, code, number, price);
						System.out.println(" ");
					}
				}

				System.out.println(" ");
				break; // switch문 종료

			case 4: // 재고 상품 보여주기
				// 재고 상품 출력
				System.out.println(" ");

				if (act.productAt(0) == null) // 원소가 없을 경우
					System.out.println("재고 상품이 없습니다.");

				else {
					for (int i = 0; i < act.getProductCount(); i++) {
						if (act.productAt(i).isEmpty() == false) // 재고가 0일 경우
						{
							if (i == 0) { // i가 첫번째 index일 경우
								for (int k = 0; k < act.getProductCount(); k++) {
									if (act.productAt(k).isEmpty()) { // 재고가 하나라도 있는 경우
										System.out.println("재고 상품을 출력합니다."); // 출력 안내 문구 출력
										break; // 반복문 종료
									}

									else // i가 첫번째이자 마지막 인덱스일 경우
									{
										if (k == act.getProductCount() - 1) // 재고 물품이 하나도 없을 경우
											System.out.println("재고 상품이 없습니다.");
									}
								}
							}
							continue;
						}

						else { // 재고가 0이 아닐 경우
							if (i == 0)
								System.out.println("재고 상품을 출력합니다."); // 출력 안내 문구 출력

							// product 객체에서 필요한 정보 추출
							String name = act.productAt(i).getName();
							String code = act.productAt(i).getCode();
							int number = act.productAt(i).getNumber();
							int price = act.productAt(i).getPrice();

							// 출력
							System.out.printf("제품명: %s, 코드: %s, 개수: %d, 가격: %d", name, code, number, price);
							System.out.println(" ");
						}
					}
				}

				System.out.println(" ");
				break; // switch문 종료

			case 5: // 체크인 하기
				try {
					// 이름 입력받기
					scan.nextLine();
					System.out.printf("체크인 하실 분의 이름을 적어주세요 : ");
					String name = scan.nextLine();

					// 전화번호 입력받기
					System.out.printf("체크인 하실 분의 전화번호를 적어주세요 : ");
					String phone = scan.nextLine();

					// 대여 일자 입력하기
					String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime());

					// 반납 예정 일자 입력받기
					System.out.printf("반납 예정 일자를 적어주세요(yyyy-MM-dd 형식으로 기재) : ");
					String returnDay = scan.nextLine();

					// User 객체 생성
					User u1;
					u1 = new User(name, phone, rentalDay, returnDay);

					// 대여할 상품 개수 입력 받기
					System.out.printf("대여할 상품 개수를 적어주세요(최대 3개) : ");
					int rentalNum = scan.nextInt();

					// 대여할 상품 개수가 4이상일때 익셉션 발생
					if (rentalNum >= 4)
						throw new Exception("잘못된 방법의 체크인입니다.");

					scan.nextLine();
					// 대여할 상품 코드 입력 받기
					for (int i = 0; i < rentalNum; i++) {
						System.out.printf("대여할 상품 코드를 적어주세요 : ");
						String rentalCode = scan.nextLine();
						int index = act.search(rentalCode);
						int amount = act.productAt(index).getPrice();
						u1.addProduct(rentalCode, amount); // 대여 물품 코드 배열에 코드 추가
					}

					// 체크인 하기
					act.checkIn(u1);
					System.out.println(" ");
					System.out.println("대여가 완료되었습니다.");
				} catch (Exception e) {
					System.out.println(" ");
					System.out.println("잘못된 방법의 체크인입니다.");
				}
				System.out.println(" ");
				break; // switch문 종료

			case 6: // 체크인 정보 보여주기
				System.out.println(" ");
				if (act.userAt(0) == null) // 원소가 없을 경우
					System.out.println("체크인 정보가 없습니다.");

				else { // 원소가 있는 경우
					System.out.println("체크인 정보를 출력합니다."); // 출력 안내 문구 출력

					for (int i = 0; i < act.getUserCount(); i++) {
						// user 객체에서 필요한 정보 추출
						String name = act.userAt(i).getName();
						String phone = act.userAt(i).getPhone();
						String rentalDay = act.userAt(i).getRentalDay();
						String returnDay = act.userAt(i).getReturnDay();

						// 출력
						System.out.printf("이름: %s, 전화번호: %s, 대여 일자: %s, 반납 예정 일자: %s", name, phone, rentalDay,
								returnDay);
						for (int k = 0; k < act.userAt(i).getRentalCount(); k++) {
							String code = act.userAt(i).codeAt(k);
							System.out.printf(", %d번째 대여 물품 코드 : %s", k + 1, code);
						}
						System.out.println(" ");
					}
				}

				System.out.println(" ");
				break; // switch문 종료

			case 7: // 체크아웃 하기
				try {
					scan.nextLine();
					System.out.println(" ");
					System.out.printf("대여하신 분의 전화번호를 입력하세요 : ");
					String phone = scan.nextLine();

					// 일치하는 전화번호 확인
					int index = act.searchUser(phone);

					// 다시한번 체크아웃 확인하기
					System.out.printf("체크아웃 하시겠습니까? (맞으면 Y/y를 아니면 N/n을 입력하세요.) : ");
					String answer1 = scan.nextLine();
					System.out.println(" ");

					// 대답이 예스일 경우 (체크아웃 진행)
					if (answer1.equals("Y") || answer1.equals("y")) {
						// 돈 금액 계산하기
						int money = act.userAt(index).pay();
						System.out.printf("지불하실 금액은 %d원 입니다.", money);

						// 돈 지불여부 물어보기
						System.out.println(" ");
						System.out.printf("돈을 지불하시겠습니까? (맞으면 Y/y를 아니면 N/n을 입력하세요.) : ");
						String answer2 = scan.nextLine();
						System.out.println(" ");

						// 대답이 예스일 경우 (체크아웃 진행)
						if (answer2.equals("Y") || answer2.equals("y")) {

							act.checkOut(index);
							System.out.println("체크아웃이 완료되었습니다.");

						}

						// 대답이 노일 경우 (체크아웃 미진행)
						else if (answer2.equals("N") || answer2.equals("n"))
							break;

						// 잘못 대답했을 경우
						else
							throw new Exception("잘못된 방법의 체크아웃입니다.");
					}

					// 대답이 노일 경우 (체크아웃 미진행)
					else if (answer1.equals("N") || answer1.equals("n"))
						break;

					// 잘못 대답했을 경우
					else
						throw new Exception("잘못된 방법의 체크아웃입니다.");
				} catch (Exception e) {
					System.out.println(" ");
					System.out.println("잘못된 방법의 체크아웃입니다.");
				}
				System.out.println(" ");
				break; // switch문 종료

			case 8: // 일일 매출 기록 보기
				System.out.println(" ");
				System.out.println("일일 매출을 출력합니다."); // 일일 매출 출력 안내 멘트
				System.out.printf("일일 매출은 %d원입니다.", act.getRevenue()); // 일일 매출 출력
				System.out.println(" ");
				break; // switch문 종료

			case 9: // 파일 저장하기
				try {
					fos = new FileOutputStream("rental.dat"); // FileOutputStream 객체 fos 생성
					dos = new DataOutputStream(fos); // DataOutputStream 객체 dos 생성
					act.makeFile(dos); // Manager 클래스의 makeFile 메소드 콜
					System.out.println("파일로 저장되었습니다.");
				} catch (IOException ioe) {
					System.out.println("파일로 출력할 수 없습니다.");
				} finally {
					try {
						dos.close();
						fos.close();
					} 
					catch (Exception e) {
					}
				}
				break;

			case 10: // 종료
				System.out.println(" ");
				System.out.println("프로그램을 종료합니다.");
				break; // switch문 종료

			default: // 잘 못 입력할 경우
				System.out.println(" ");
				System.out.println("다시 입력하세요.");
				System.out.println(" ");
				break; // switch문 종료
			}

			if (menu == 10)
				break; // 반복문 종료
		}

		scan.close(); // 스캔 종료
	}
}