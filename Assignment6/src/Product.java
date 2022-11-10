import java.io.*;

public class Product {
	private String productName; // ��ǰ �̸�
	private String productCode; // ��ǰ �ڵ�
	private int productNumber; // ��ǰ ����
	private int price; // ��ǰ ����
	
	/* ������ 1 */
	Product() {}
	
	/* ������ 2 */
	Product(String productCode) {
		this.productCode = productCode;
	}
	
	/* ������ 3 */
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	/* ���� read */
	Product readProduct(DataInputStream dis) throws Exception
	{	
		try {
			this.productName = dis.readUTF(); // ��ǰ �̸� read
			this.productCode = dis.readUTF(); // ��ǰ �ڵ� read
			this.productNumber = dis.readInt(); // ��ǰ ���� read
			this.price = dis.readInt(); // ��ǰ ���� read
			return this;
		}
		catch (Exception e){
			throw new Exception ("���� read ����");
		}
	}	
	
	/* ���� write */
	void writeProduct(DataOutputStream dos) throws Exception {	
		try {
			dos.writeUTF(productName); // ��ǰ �̸� write
			dos.writeUTF(productCode); // ��ǰ �ڵ� write
			dos.writeInt(productNumber); // ��ǰ ���� write
			dos.writeInt(price); // ��ǰ ���� write
		}
		catch (Exception e){
			throw new Exception ("���� write ����");
		}
	}	
	
	/* equals �Լ� */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Product)) return false;
		Product p = (Product) o;
		return p.getCode().equals(productCode);
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
}