package supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/*Supermarketʵ�֣�
 * 1���̴߳���1-3���������һ���˿ͽ���������ֱ������
 * 2������3��Cashier�̣߳�ͬ���Ӵ�Customer��
 * 3��ͳ������*/
public class Supermarket{
	public static Cashier cashierA = new Cashier();
	public static Cashier cashierB = new Cashier();
	public static Cashier cashierC = new Cashier();
	public static List<Customer> customersTotal = new ArrayList<>();// ����ͳ��

	// ��ʼ��3��Goodʵ��
	public static Good apple = new Good(15);
	public static Good macbook = new Good(15);
	public static Good cookie = new Good(15);
	public static Map<String, Good> goods = new HashMap<String, Good>();
	
	public static long cusWaitTimeTotal = 0;// �˿͵ȴ�ʱ���ܺ�
	public static long goodSaleTimeTotal = 0;// ������Ʒ�ܵ��۳�ʱ��
	public static long saleStartTime;// ��ʼ���۵�ʱ��
	public static long saleEndTime;// ����ʱ��
	public static long saleTotalTime;// �ӿ�ʼ���۵��������ܹ�ʱ��
	public static long cusWaitTimeAvg;// �˿�ƽ���ȴ�ʱ��
	public static long goodSaleTimeAvg;// ��Ʒƽ���۳�ʱ��
	public static int  customerNum;//������Ʒ�˿���

	public static LinkedBlockingQueue<Customer> customers = new LinkedBlockingQueue<Customer>();

	public static void main(String[] args) {
		saleStartTime = System.currentTimeMillis();

		goods.put("apple", apple);
		goods.put("macbook", macbook);
		goods.put("cookie", cookie);

		/*����һ���̣߳�1-3���������һ���˿ͽ���������ֱ������*/
		new Thread() {

			@Override
			public void run() {

				while (true) {
					int x = 1 + (int) (Math.random() * 3);
					try {
						// �����ȡ��Ʒ
						int y = 1 + (int) (Math.random() * 3);
						Good goodBuy = null;
						switch (y) {
						case 1:
							goodBuy = goods.get("apple");
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("macbook");
							}
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("cookie");
							}
							break;
						case 2:
							goodBuy = goods.get("macbook");
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("apple");
							}
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("cookie");
							}
							break;
						case 3:
							goodBuy = goods.get("cookie");
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("macbook");
							}
							if (goodBuy.goodNum == 0) {
								goodBuy = goods.get("apple");
							}
							break;
						}

						synchronized (goodBuy) {	
							if (goodBuy.getGoodNum() > 0) {
								Customer customer = new Customer();
								goodBuy.reduce();
								customer.setBuyTime(System.currentTimeMillis());
								customers.put(customer);
							} else {
								System.out.println("������Ʒ��������");
								saleEndTime = System.currentTimeMillis();
								break;
							}
						}
						Thread.sleep(x * 1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
		//��ʼ��3��Cashier
		cashierA.setCustomers(customers);
		cashierB.setCustomers(customers);
		cashierC.setCustomers(customers);

		/*����3��Cashier�̣߳�ͬ���Ӵ�Customer����ͳ������*/
		Thread c1 = new Thread(cashierA, "cashier A");
		Thread c2 = new Thread(cashierB, "cashier B");
		Thread c3 = new Thread(cashierC, "cashier C");
		c1.start();
		c2.start();
		c3.start();

		while (true) {
			if (cashierA.threadEnd==1 && cashierB.threadEnd==1  && cashierC.threadEnd==1 ) {
				customerNum = Supermarket.customersTotal.size();
				while (!Supermarket.customersTotal.isEmpty()) {
					Customer customer = Supermarket.customersTotal.get(0);
					cusWaitTimeTotal = cusWaitTimeTotal + (customer.getCashTime() - customer.getBuyTime());
					goodSaleTimeTotal = goodSaleTimeTotal + (customer.getCashTime() - saleStartTime);
					Supermarket.customersTotal.remove(0);
				}

				cusWaitTimeAvg = cusWaitTimeTotal / customerNum;
				goodSaleTimeAvg = goodSaleTimeTotal / customerNum;
				saleTotalTime = saleEndTime - saleStartTime;
				
				System.out.println("ÿ���˿�ƽ���ȴ�ʱ��Ϊ��" + cusWaitTimeAvg + "����");
				System.out.println("ÿ����Ʒƽ���۳�ʱ��Ϊ��" + goodSaleTimeAvg + "����");
				System.out.println("�ӿ�ʼ���۵������ܹ�ʱ��Ϊ��" + saleTotalTime + "����");
				break;
			}
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
