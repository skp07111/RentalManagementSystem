import java.io.*;
import java.util.*;

public class Manager {
	private ArrayList<Product> productList; // ��ǰ �迭
	private ArrayList<User> userList; // ����� �迭
	private int revenue; // ���� ���� �Ѿ� ����
	
	/* ������ 1 */
	Manager () {
		productList = new ArrayList<Product>(); // ��ǰ �迭 ����
		userList = new ArrayList<User>(); // ����� �迭 ����
	}
	
	/* ������ 2 */
	Manager (ObjectInputStream ois) throws Exception
	{ 
		productList = new ArrayList<Product>(); // ��ǰ �迭 ����
		userList = new ArrayList<User>(); // ����� �迭 ����
		
		// ���� ���� read
		try {
			Integer pCount = (Integer) ois.readObject(); // ��ü ��ǰ �� read
			for (int i = 0; i < pCount.intValue(); i++)
			{
				Product p = (Product) ois.readObject(); // ��ǰ ���� read
				productList.add(p); // ��ǰ �迭�� read�� ��ǰ �߰�
			}
			Integer uCount = (Integer) ois.readObject(); // ��ü ����� �� read
			for (int i = 0; i < uCount.intValue(); i++)
			{
				User u = (User) ois.readObject(); // ����� ���� read
				userList.add(u); // ����� �迭�� read�� ����� �߰�
			}
			Integer revenue = (Integer) ois.readObject(); // ����� read
			this.revenue = revenue.intValue();
		} 
		catch (FileNotFoundException fnfe) {
			throw new Exception ("������ �������� �ʽ��ϴ�.");
		}
		catch (EOFException eofe) {
			throw new Exception ("�������ϴ�.");
		}
		catch (IOException ioe) {
			throw new Exception ("���� ����� �����Դϴ�.");
		}
	}
	
	/* ���� write �Լ� */
	void writeFile(ObjectOutputStream oos) throws Exception 
	{
		try {
			Integer pCount = new Integer(productList.size()); // ��ü ��ǰ ��
			oos.writeObject(pCount); // ��ü ��ǰ �� write
			for (int i = 0; i < pCount.intValue(); i++)
			{
				oos.writeObject(productList.get(i)); // ��ǰ ���� write
			}
			Integer uCount = new Integer(userList.size()); // ��ü ����� ��
			oos.writeObject(uCount); // ��ü ����� �� write
			for (int i = 0; i < uCount.intValue(); i++)
			{
				oos.writeObject(userList.get(i)); // user ���� write
			}
			Integer revenue = new Integer(this.revenue);
			oos.writeObject(revenue); // ����� write
		} 
		catch (IOException ioe) {
			throw new Exception ("���� ����� �����Դϴ�.");
		} 	
	}	
	
	// ��ǰ �߰�
	public void addProduct(Product p) throws Exception{
		int index = productList.indexOf(p); // ������ ��ǰ �ڵ带 ���� ��ǰ ���� �˻�
		if (index != -1)
		{
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
		else productList.add(p); // ������ ��ǰ �ڵ尡 ������ ��ǰ �迭�� ��ǰ �߰�
	}
	
	// ��ǰ ����
	public void subProduct(String productCode) throws Exception {
		try{
			productList.remove(searchProduct(productCode)); // ��ǰ �ڵ� �˻��Ͽ� �ش� ��ǰ ����
		}
		catch (Exception e) { 
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}
	
	// ��ǰ ��ü �˻�
	public int searchProduct(String productCode) throws Exception {
		Product p = new Product();
		p.setProductCode(productCode); // ��ǰ �ڵ� ����
		int index = productList.indexOf(p); // ��ǰ �˻�
		if (index == -1)
			throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�.");
		else return index; // �ڵ� ��ġ �� index ��ȯ
	}
	
	// üũ��
	public void checkIn(User u) throws Exception {
		int index = userList.indexOf(u); // ������ ��ȭ��ȣ�� ���� ����� ���� �˻�
		if (index != -1) throw new Exception ("�߸��� ����� üũ���Դϴ�.");
		else { // ������ ��ȭ��ȣ�� ������
		subStock(u); // ��� �������� �뿩 ���� ����
		userList.add(u); // ����� �迭�� �ش� ����� �߰�
		}
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			int money = userList.get(index).pay();// �ݾ� ��ȯ�ޱ�
			userList.remove(index);	// userList���� �ش� ����� ����
			revenue += money; // ���⿡ �߰��ϱ�
		}
		catch(Exception e) {
			throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
		}
	}

	// ����� ��ü �˻�
	public int searchUser(String phone) throws Exception {
		User u = new User();
		u.setPhone(phone); // ��ȭ��ȣ ����
		int index = userList.indexOf(u); // ����� �˻�
		if (index == -1)
			throw new Exception("��ġ�ϴ� ��ȭ��ȣ�� ã�� �� �����ϴ�.");
		else return index; // ��ȭ��ȣ ��ġ �� index ��ȯ
	}

	
	// ��� �������� �뿩 ���� ����
	public void subStock(User u) throws Exception {
		// ��� �������� �뿩 ���� �����ϱ�
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			int searchNum;
			try {
				searchNum = searchProduct(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
			} 
			catch (Exception e) {
				throw new Exception("�߸��� ����� üũ���Դϴ�.");
			}
			Product p = productList.get(searchNum); // �ش� �ε����� product ��ü
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// ��ǰ ��� �ٽ� �߰�
	public void addStock(int index) throws Exception {
		User u = userList.get(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			try {
				productList.get(searchProduct(code)).addNumber(); //�ش� �ڵ��� product ��ü ��� �߰��ϱ�
			}
			catch (Exception e) {
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
	}
	
	// ��ǰ �迭 i��° ����
	public Product productAt(int i) {
		return productList.get(i); // ��ǰ �迭 i��° ��ǰ ��ü return
	}
	
	// �뿩 ����� �迭 i��° ����
	public User userAt(int i)
	{
		return userList.get(i); // �뿩 �迭 i��° ���� ��ü return
	}
	
	// productCount �� ��ȯ
	public int getProductCount() {
		return productList.size();
	}
	
	// userCount �� ��ȯ
	public int getUserCount() {
		return userList.size();
	}
	
	// ���� ��ȯ
	public int getRevenue() {
		return revenue;
	}

}