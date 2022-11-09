import java.io.DataOutputStream;
import java.io.IOException;

public class Product {
	private String productName; // ��ǰ �̸�
	private String productCode; // ��ǰ �ڵ�
	private int productNumber; // ��ǰ ����
	private int price; // ��ǰ ����
	
	// �μ� �ִ� ������
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// ��ǰ �̸� ��ȯ
	public String getName()
	{
		return productName;
	}
	
	// ��ǰ �ڵ� ��ȯ
	public String getCode()
	{
		return productCode;
	}
	
	// ��ǰ ���� ��ȯ
	public int getNumber()
	{
		return productNumber;
	}
	
	// ��ǰ ���� �߰�
	public void addNumber()
	{
		productNumber++;
	}
	
	// ��ǰ �뿩 �������� Ȯ�� �� ��ǰ ���� 1�� ����
	public void subNumber() throws Exception{
		if(productNumber < 1) // ��� ������ 1���� ���� ��� 
			throw new Exception("�߸��� ����� üũ���Դϴ�."); // �ͼ��� �߻�
		else // ��� ���� ���ڰ� 1�̻��� ���
			productNumber--; // ��� �� 1�� ����
	}
	
	// ��ǰ ���� ��ȯ
	public int getPrice()
	{
		return price;
	}
	
	// ��� �˻� �Լ� (��� ������ true, �ƴϸ� false ��ȯ)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	/*product ���� write �Լ�*/
	public void saveProduct(DataOutputStream dos) { // public void saveProduct(DataOutputStream dos) throws Exception <- ���� ���� exception
		try {
			dos.writeUTF(productName); // product �̸� write
			dos.writeUTF(productCode); // product �ڵ� write
			dos.writeInt(productNumber); // product ��� write
			// dos.writeUTF(Integer.toString(price)); �̷��� ���� �� ��, ����(�ʵ�)�� �ڷ����� int�̸� writeInt�� �� ��
			dos.writeInt(price); // product ���� write
		}
		catch (IOException ioe) { // �ڵ����� ������ exception ó��
			
		}
//		catch (Exception e) { // ������ ���� ����(throw��) exception�� ���� ó���� �� �� throw �ʿ�
//		throw new Exception("��ǰ ������ ���Ͽ� �� �� �����ϴ�.");
//	}
	}
}