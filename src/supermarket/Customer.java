package supermarket;

/*��Customer��ʵ�֣�
 * ����buyTime��cashTime�������Լ�������set��get����*/
public class Customer {

	//�õ���Ʒʱ��
	private long buyTime;	
	//����ʱ��
	private long cashTime;
	
	public long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}
	
	public long getCashTime() {
		return cashTime;
	}
	public void setCashTime(long cashTime) {
		this.cashTime = cashTime;
	}
	
}