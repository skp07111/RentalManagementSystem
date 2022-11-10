import java.io.*;
import java.util.*;

public class Manager {
	private ArrayList<Product> productList; // ��ǰ �迭
	private int productCount = 0; // ��ǰ �迭 �ε��� ī��Ʈ
	private ArrayList<User> userList; // �뿩 �迭
	private int userCount = 0; // �뿩 �迭 �ε��� ī��Ʈ
	private int revenue = 0; // ���� ���� �Ѿ� ����
	// productCount, userCount ���ֱ�
	
	/* ������ 1 */
	Manager () {
		productList = new ArrayList<Product>(); // ��ǰ �迭 ũ�� ����
		userList = new ArrayList<User>(); // �뿩 �迭 ũ�� ����
	}
	
	/* ������ 2 */
	Manager (DataInputStream dis) throws Exception
	{ 
		productList = new ArrayList<Product>(); // ��ǰ �迭 ũ�� ����
		userList = new ArrayList<User>(); // �뿩 �迭 ũ�� ����
		// ���� ���� read
		try {
			int pCount = dis.readInt(); // ��ü ��ǰ ���� read
			for (int i = 0; i < pCount; i++)
			{
				Product p = new Product(); // ��ǰ ��ü p ����
				add(p.readProduct(dis)); // ��ǰ ���� read
			}
			int uCount = dis.readInt(); // ��ü ����� �� read
			for (int i = 0; i < uCount; i++)
			{
				User u = new User(); // ����� ��ü u ����
				checkInRead(u.readUser(dis)); // ����� ���� read
			}
			revenue = dis.readInt(); // ����� read
		} 
		catch (Exception e) {
			throw new Exception ("���� read ����");
		}
	}
	
	/* ���� write �Լ� */
	void writeFile(DataOutputStream dos) throws Exception {
		try {
			dos.writeInt(productCount); // ��ü ��ǰ ���� write
			for (int i = 0; i < productCount; i++) 
			// for (int i = 0; i < productList.size(); i++) size �Լ��� ������ ������ -> Ʋ�� �ڵ�(��ȿ����)
			
			// num = productList.size();
			// for (int i = 0; i < num; i++) -> �´� �ڵ�
			{
				productList.get(i).writeProduct(dos); // ��ǰ ���� write
			}
			dos.writeInt(userCount); // ��ü ����� �� write
			for (int i = 0; i < userCount; i++)
			{
				userList.get(i).writeUser(dos); // user ���� write
			}		
			dos.writeInt(revenue); // ����� write
		} catch (Exception e) {
			throw new Exception ("���� write ����");
		} 		
	}	
	
	// ��ǰ �ڵ� �ߺ� �˻�
	public void checkCode(Product p) throws Exception {
		int index = search(p.getCode()); // �ߺ��� Ű �˻�
		if (index != -1)
		{
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
	}

	// ��ǰ �迭�� ���� �߰�
	public void addProduct(Product p) {
		productList.add(p);
		productCount = productList.size();
	}
	
	// ��ǰ �߰�
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // �ڵ� �ߺ� �˻�
			addProduct(p); // ��ǰ �߰�
		}
		catch (Exception e) {
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
	}
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // ��ǰ �迭���� �˻��ϱ�
			Product p = productList.get(number);
			// productList.remove(number); // �ε����� ������ ����
			productList.remove(p); // ��ǰ �迭���� �����ϱ�
			// if(!remove(p)) throw Exception("~");
			productCount = productList.size();
		}
		catch (Exception e) {
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}
	
	// ��ǰ ��ü �˻�
	public int search(String productCode) {
		Product p = new Product(productCode);
		// Product Ŭ������ productCode set�Լ� ���� �� --> p.setPcode(productCode); 
		int index = productList.indexOf(p);
		return index;
	}

	// ��ǰ �迭 i��° ����
	public Product productAt(int i) {
		return productList.get(i); // ��ǰ �迭 i��° ��ǰ ��ü return
	}
	
	// productCount �� ��ȯ
	public int getProductCount() {
		return productCount;
	}
	
	// ��ȭ��ȣ �ߺ� �˻�
	public void checkPhone(User u) throws Exception {
		int index = searchUser(u.getPhone()); // �ߺ��� ��ȭ��ȣ �˻�
		if(index != -1)
		{
			throw new Exception ("�߸��� ����� üũ���Դϴ�.");
		}
	}
	
	// �뿩 �迭�� ���� �߰�
	public void addUser(User u) {
		userList.add(u);
		userCount = userList.size();
	}
	
	// ��� �������� �뿩 ���� ����
	public void subStock(User u) throws Exception {
		// ��� �������� �뿩 ���� �����ϱ�
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			int searchNum;
			try {
				searchNum = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
			} 
			catch (Exception e) {
				throw new Exception("�߸��� ����� üũ���Դϴ�.");
			}
			Product p = productList.get(searchNum); // �ش� �ε����� product ��ü
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// üũ��
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // ��ȭ��ȣ �ߺ� �˻�
			subStock(u); // ��� �������� �뿩 ���� ����
			addUser(u); // �뿩 �迭�� �뿩 ���� �ֱ�
		}
		catch(Exception e) {
			throw new Exception("�߸��� ����� üũ���Դϴ�.");
		}
	}
	
	// üũ�� ���� read
	public void checkInRead(User u) {
		addUser(u); // �뿩 �迭�� �뿩 ���� �ֱ�
	}
	
	// userCount �� ��ȯ
	public int getUserCount() {
		return userCount;
	}
	
	// �뿩 �迭 i��° ����
	public User userAt(int i)
	{
		return userList.get(i); // �뿩 �迭 i��° ���� ��ü return
	}
	
	// ����� ��ü �˻�
	public int searchUser(String phone) {
		User u = new User(phone);
		int index = userList.indexOf(u);
		return index;
	}
	
	// ��ǰ ��� �ٽ� �߰�
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			try {
				int number = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
				productAt(number).addNumber(); //�ش� �ε����� product ��ü�� ��� �߰��ϱ�
			}
			catch (Exception e) {
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
	}
	
	// �뿩 �迭�� ���� ����
	public void subUser(int number) {
		User u = userList.get(number);
		userList.remove(u);	
		userCount = userList.size();
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			int money = userList.get(index).pay();// �ݾ� ��ȯ�ޱ�
			subUser(index); // userList���� ����, �迭 �����ϱ�
			revenue += money; // ���⿡ �߰��ϱ�
		}
		catch(Exception e) {
			throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
		}
	}
	
	// ���� ��ȯ
	public int getRevenue() {
		return revenue;
	}

}