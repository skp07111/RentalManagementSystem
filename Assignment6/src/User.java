import java.io.*;

import java.util.*;
import java.text.SimpleDateFormat;

public class User {
	private String name; // �̸�
	private String phone; // ��ȭ��ȣ
	private String rentalDay; // �뿩 ����
	private String returnDay; // �ݳ� ���� ����
	private String[] codeList = new String[3]; // �뿩 ��ǰ �ڵ� �迭
	private int[] payList = new int[3]; // �뿩 �ݾ� �迭
	private int rentalCount = 0; // �뿩 ��ǰ ī��Ʈ
	Calendar getToday = Calendar.getInstance();
	
	/* ������ 1 */
	User() {}
	
	/* ������ 2 */
	User(String phone) {
		this.phone = phone;
	}
	
	/* ������ 3 */
	User(String name, String phone, String rentalDay, String returnDay)
	{
		this.name = name;
		this.phone = phone;
		this.rentalDay = rentalDay;
		this.returnDay = returnDay;
	}
	
	/* ���� read */
	User readUser(DataInputStream dis) throws Exception
	{
		try {
			this.name = dis.readUTF(); // �̸� read
			this.phone = dis.readUTF(); // ��ȭ��ȣ read
			this.rentalDay = dis.readUTF(); // �뿩 ���� read
			this.returnDay = dis.readUTF(); // �ݳ� ���� ���� read
			int readRentalCounts = dis.readInt(); // �뿩�� ��ǰ �ڵ� read
			for (int j = 0; j < readRentalCounts; j++) {
				String pCode = dis.readUTF(); // �뿩 ��ǰ �ڵ� read
				int pPay = dis.readInt(); // �뿩 �ݾ� read
				this.addProduct(pCode, pPay);
			}
			return this;
		}
		catch (Exception e){
			throw new Exception ("���� read ����");
		}
	}	
		
	/* ���� write */
	void writeUser(DataOutputStream dos) throws Exception
	{	
		try {
			dos.writeUTF(name); // �̸� write
			dos.writeUTF(phone); // ��ȭ��ȣ write
			dos.writeUTF(rentalDay); // �뿩 ���� write
			dos.writeUTF(returnDay); // �ݳ� ���� ���� write
			dos.writeInt(rentalCount); // �뿩 ��ǰ ���� write
			for (int i = 0; i < rentalCount; i++) {
				dos.writeUTF(codeList[i]); // �뿩 ��ǰ �ڵ� write
				dos.writeInt(payList[i]); // �뿩 �ݾ� write
			}
		}
		catch (Exception e){
			throw new Exception ("���� write ����");
		}
	}
	
	/* equals �Լ� */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) return false;
		User u = (User) o;
		return u.getPhone().equals(phone);
	}

	// �̸� ��ȯ
	public String getName()
	{
		return name;
	}
	
	// ��ȭ��ȣ ��ȯ
	public String getPhone()
	{
		return phone;
	}
	
	// �뿩 ���� ��ȯ
	public String getRentalDay()
	{
		return rentalDay;
	}
	
	// �ݳ� ���� ���� ��ȯ
	public String getReturnDay()
	{
		return returnDay;
	}
	
	// �뿩 ��ǰ �ڵ� �迭 �ε��� ī��Ʈ ��ȯ
	public int getRentalCount() {
		return rentalCount;
	}
	
	// �뿩 ��ǰ �ڵ�/�ݾ� �迭�� �ڵ� �߰�
	public void addProduct(String code, int money)
	{
		codeList[rentalCount] = code;
		payList[rentalCount] = money;
		rentalCount++;
	}
	
	// �뿩 ��ǰ �ڵ� �迭 i��° ����
	public String codeAt(int i)
	{
		// �뿩 ��ǰ �ڵ� �迭 i��° ��ǰ ��ü return
		return codeList[i];
	}
	
	// �뿩 �ݾ� �迭 i��° ����
	public int payAt(int i)
	{
		// �뿩 �ݾ� �迭 i��° ��ǰ ��ü return
		return payList[i];
	}
	
	// ���� �Լ�
	public int pay() throws Exception
	{
		// ���� �ݾ� �� ����ϱ�
		int money = 0;
		for(int i = 0; i < rentalCount; i++)
		{
			money += payList[i];
		}
		
		// �ݳ� ���� �Է��ϱ�
		getToday.setTime(new Date());
		
		// �뿩 ���� �ҷ�����
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDay);
		Calendar rentalDate = Calendar.getInstance();
		rentalDate.setTime(date1);
		
		// �ݳ� ���� ���� �ҷ�����
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(returnDay);
		Calendar returnDate = Calendar.getInstance();
		returnDate.setTime(date2);
		
		// �뿩�ϱ�� �� �Ⱓ ����ϱ� (�ݳ� ���� ���� - �뿩 ����)
		// �и��ʸ� ���� ���� �� �� ���� ��ȯ
		int day1 = (int) ((returnDate.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));

		// �뿩 �Ⱓ ����ϱ� (�ݳ� ���� - �뿩 ����)
		// �и��ʸ� ���� ���� �� �� ���� ��ȯ
		int day2 = (int) ((getToday.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		/*�ݾ� ���� ��Ģ ����
		 * ��¥ ���Ѽ� �ݳ� => ��ǰ �ݾ� �� �� x �뿩 �Ⱓ
		 * ���� �ݳ� => ��ǰ �ݾ� �� �� x ���� �뿩 �Ⱓ(���� �Ⱓ�� ���� å�� x)
		 * ���߿� �ݳ� => ��ǰ �ݾ� �� �� x ���� �뿩 �Ⱓ + ��ü��(�Ϸ� �뿩��)*/
		
		// �� �ݾ� ����ϱ�
		if (day1 == day2) // �ݳ� ���� ��¥�� �ݳ�
			return money *= (day1 + 1); // ���� �뿩 �ݳ��� 1�Ϸ� ó��
		
		else if (day1 > day2)// �ݳ� ���� ��¥���� ���� �ݳ�
			return money *= (day2 + 1); // ���� �뿩 �ݳ��� 1�Ϸ� ó��
		
		else // �ݳ� ���� ��¥���� �ʰ� �ݳ�
			return money *= (day2 + 2); // ��ü�� (�Ϸ� �뿩��) �߰��Ͽ��� ��ȯ
	}
}