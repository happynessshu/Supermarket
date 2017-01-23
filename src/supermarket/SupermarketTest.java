package supermarket;

import org.junit.Assert;
import org.junit.Test;
import supermarket.Cashier;
import supermarket.Customer;
import supermarket.Good;
import java.util.concurrent.LinkedBlockingQueue;

public class SupermarketTest {

	/*
	 * 测试Good类中程序：
	 * 1、验证getNum方法和初始数据 
	 * 2、验证reduce()方法
	 */
	@Test
	public void testGood() {
		System.out.println("Test Good Start--------");
		Good good = new Good(14);
		Assert.assertEquals(14, good.getGoodNum());
		good.reduce();
		Assert.assertEquals(13, good.getGoodNum());
		System.out.println("Test Good End--------");
	}

	/*
	 * 测试Customer类中程序：
	 * 1、验证setBuyTime方法和getBuyTime方法
	 * 2、验证setCashTime方法和getCashTime方法
	 */
	@Test
	public void testCustomer() {
		Customer customerTest = new Customer();
		System.out.println("Test Customer Start--------");
		customerTest.setBuyTime(11);
		Assert.assertEquals(11, customerTest.getBuyTime());
		customerTest.setCashTime(15);
		Assert.assertEquals(15, customerTest.getCashTime());
		System.out.println("Test Customer End--------");
	}

	/*
	 * 测试Cashier类中程序：
	 * 1、验证run()方法
	 * 2、验证setCount方法和getCount方法
	 */
	@Test
	public void testCashier() {
		// 验证run()方法
		System.out.println("Test Cashier Start--------");
		try {
			Cashier cashier = new Cashier();
			LinkedBlockingQueue<Customer> customersQueue = new LinkedBlockingQueue<Customer>();
			for (int i = 45; i > 0; i--) {
				Customer customer = new Customer();
				customer.setBuyTime(System.currentTimeMillis());
				customersQueue.put(customer);
			}
			cashier.setCustomers(customersQueue);
			cashier.run();
			Assert.assertEquals(1, cashier.threadEnd);
			Assert.assertEquals(45, cashier.getCount());

			// 验证setCount方法和getCount方法
			cashier.setCount(10);
			Assert.assertEquals(10, cashier.getCount());
			System.out.println("Test Cashier End--------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 测试Supermarket类中程序：
	 * 1、验证Apple, Macbook, Cookie三种 Good初始化15个到商店库存数据正确
	 * 2、验证售罄后Apple, Macbook, Cookie三种 Good的数据等于0 
	 * 3、验证售罄后每个统计数值是否小于0
	 * 4、验证3个Cashier接待的顾客总和等于45
	 */
	@Test
	public void testSupermarket() {
		System.out.println("Test Supermarket Start--------");
		// 验证Apple, Macbook, Cookie三种 Good初始化15个到商店库存数据正确
		Assert.assertEquals(15, Supermarket.apple.getGoodNum());
		Assert.assertEquals(15, Supermarket.macbook.getGoodNum());
		Assert.assertEquals(15, Supermarket.cookie.getGoodNum());

		Supermarket.main(null);

		// 验证售罄后Apple, Macbook, Cookie三种 Good的数据等于0
		Assert.assertEquals(0, Supermarket.apple.getGoodNum());
		Assert.assertEquals(0, Supermarket.macbook.getGoodNum());
		Assert.assertEquals(0, Supermarket.cookie.getGoodNum());

		// 验证售罄后每个统计数值是否小于0
		Assert.assertTrue("每个顾客平均等待时间大于0", Supermarket.cusWaitTimeAvg > 0);
		Assert.assertTrue("每个商品平均售出时间大于0", Supermarket.goodSaleTimeAvg > 0);
		Assert.assertTrue("从开始销售到售罄的总共时间大于0", Supermarket.saleTotalTime > 0);
		
		int cusToal=Supermarket.cashierA.getCount()+
				Supermarket.cashierB.getCount()+Supermarket.cashierC.getCount();
		Assert.assertEquals(45, cusToal);
		System.out.println("Test Supermarket End--------");
	}

}