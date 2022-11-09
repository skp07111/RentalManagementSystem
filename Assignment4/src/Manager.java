import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Manager {
	private Product[]productList; // ��ǰ �迭
	private int productCount = 0; // ��ǰ �迭 �ε��� ī��Ʈ
	private User[]userList; // �뿩 �迭
	private int userCount = 0; // �뿩 �迭 �ε��� ī��Ʈ
	private int revenue = 0; // ���� ���� �Ѿ� ����
	
	// ������ (��ǰ �迭, �뿩 �迭 ũ�� ����)
	Manager (int maxProductCount, int maxUserCount){
		productList = new Product[maxProductCount]; // ��ǰ �迭 ũ�� ����
		userList = new User[maxUserCount]; // �뿩 �迭 ũ�� ����
	}
	
	// �ڵ� �ߺ� �˻�
	public void checkCode(Product p) throws Exception {
		for (int i = 0; i < productCount; i++)
		{
			Product p1 = productList[i];
			// �ߺ��� Ű �˻�
			if(p1 != null && p1.getCode().equals(p.getCode()))
			{
				throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
			}
		}
	}

	// ��ǰ �迭�� ���� �߰�
	public void addProduct(Product p) {
		productList[productCount] = p;
		productCount++;
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
	
	// ��ǰ �迭�� number��° �ε��� ���� ����
	public void subProduct(int number) {
		// ��ǰ ����, �迭 �����ϱ�	
		for (int i = number; i < productCount; i++)	
		{		
			productList[i] = productList[i+1];		
		}
		productCount--;
	}
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // ��ǰ �迭���� �˻��ϱ�
			subProduct(number); // ��ǰ �迭���� �����ϱ�
		}
		catch (Exception e) {
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}
	
	// ��ǰ ��ü �˻�
	public int search(String productCode) throws Exception {	
		// ��ǰ �˻�
		for(int i = 0; i < productCount; i++)
		{
			Product p1 = productList[i]; // ��ü ����
			// ���� �ڵ�� �μ� ��ġ
			if((productCode.equals(p1.getCode())))
			{
				return i;
			}
		} throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�."); // �ڵ尡 ��ġ���� ������ �ͼ��� �߻�
	}

	// ��ǰ �迭 i��° ����
	public Product productAt(int i) {
		return productList[i]; // ��ǰ �迭 i��° ��ǰ ��ü return
	}
	
	// productCount �� ��ȯ
	public int getProductCount() {
		return productCount;
	}
	
	// ��ȭ��ȣ �ߺ� �˻�
	public void checkPhone(User u) throws Exception {
		for (int i = 0; i < userCount; i++)
		{
			User u1 = userList[i];
			// �ߺ��� ��ȭ��ȣ �˻�
			if(u1 != null && u1.getPhone().equals(u.getPhone()))
			{
				throw new Exception ("�߸��� ����� üũ���Դϴ�."); // �ߺ��� ��ȭ��ȣ�� ��� �ͼ��� �߻�
			}
		}
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
			Product p = productList[searchNum]; //�ش� �ε����� product ��ü
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// �뿩 �迭�� ���� �߰�
	public void addUser(User u) {
		userList[userCount] = u;
		userCount++;
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
	
	// userCount �� ��ȯ
	public int getUserCount() {
		return userCount;
	}
	
	// �뿩 �迭 i��° ����
	public User userAt(int i)
	{
		return userList[i]; // �뿩 �迭 i��° ���� ��ü return
	}
	
	// ��ġ�ϴ� ȸ����ȣ �˻�
	public int searchUser(String phone) throws Exception {
		for(int i = 0; i < userCount; i++)
		{
			User u = userList[i];
			// ��ġ�ϴ� ������ ������ �ε��� ��ȣ ��ȯ
			if (u.getPhone().equals(phone))
				return i;
		}throw new Exception ("ȸ�������� �����ϴ�."); // ��ġ�ϴ� ������ ������ �ͼ��� �߻�
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
		for (int i = number; i < userCount; i++)	
		{		
			userList[i] = userList[i+1];		
		}
		userCount--;
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			int money = userList[index].pay();// �ݾ� ��ȯ�ޱ�
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
	
	// ���� ���� �Լ�
	public void makeFile(DataOutputStream dos) throws Exception{
		try {
			for (int i = 0; i < productCount; i++) { // product ���� write
				productList[i].saveProduct(dos); // read(~inputStream)�� �� ����ؾ���, productCount�� userCount�� ���� ���
			}
			for (int i = 0; i < userCount; i++) { // user ���� write
				userList[i].saveUser(dos);
			}
			dos.writeInt(revenue); // ����� write
		}
		catch (IOException ioe){ // catch(Exception e)
			throw new Exception ("����� ����");
		}
	}
}