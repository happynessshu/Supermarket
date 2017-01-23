package supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/*Supermarket实现：
 * 1、线程处理1-3秒随机产生一个顾客进行随机购物，直至售罄
 * 2、创建3个Cashier线程，同步接待Customer；
 * 3、统计数据*/
public class Supermarket{
	public static Cashier cashierA = new Cashier();
	public static Cashier cashierB = new Cashier();
	public static Cashier cashierC = new Cashier();
	public static List<Customer> customersTotal = new ArrayList<>();// 用于统计

	// 初始化3个Good实例
	public static Good apple = new Good(15);
	public static Good macbook = new Good(15);
	public static Good cookie = new Good(15);
	public static Map<String, Good> goods = new HashMap<String, Good>();
	
	public static long cusWaitTimeTotal = 0;// 顾客等待时间总和
	public static long goodSaleTimeTotal = 0;// 所有商品总的售出时间
	public static long saleStartTime;// 开始销售的时间
	public static long saleEndTime;// 售罄时间
	public static long saleTotalTime;// 从开始销售到售罄的总共时间
	public static long cusWaitTimeAvg;// 顾客平均等待时间
	public static long goodSaleTimeAvg;// 商品平均售出时间
	public static int  customerNum;//购买商品顾客数

	public static LinkedBlockingQueue<Customer> customers = new LinkedBlockingQueue<Customer>();

	public static void main(String[] args) {
		saleStartTime = System.currentTimeMillis();

		goods.put("apple", apple);
		goods.put("macbook", macbook);
		goods.put("cookie", cookie);

		/*创建一个线程：1-3秒随机产生一个顾客进行随机购物，直至售罄*/
		new Thread() {

			@Override
			public void run() {

				while (true) {
					int x = 1 + (int) (Math.random() * 3);
					try {
						// 随机获取商品
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
								System.out.println("所有商品已售罄。");
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
		//初始化3个Cashier
		cashierA.setCustomers(customers);
		cashierB.setCustomers(customers);
		cashierC.setCustomers(customers);

		/*创建3个Cashier线程，同步接待Customer；并统计数据*/
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
				
				System.out.println("每个顾客平均等待时间为：" + cusWaitTimeAvg + "毫秒");
				System.out.println("每个商品平均售出时间为：" + goodSaleTimeAvg + "毫秒");
				System.out.println("从开始销售到售罄总共时间为：" + saleTotalTime + "毫秒");
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
