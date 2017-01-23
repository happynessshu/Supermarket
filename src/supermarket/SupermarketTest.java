package supermarket;

import org.junit.Assert;
import org.junit.Test;
import supermarket.Cashier;
import supermarket.Customer;
import supermarket.Good;
import java.util.concurrent.LinkedBlockingQueue;

public class SupermarketTest {

	/*
	 * ����Good���г���
	 * 1����֤getNum�����ͳ�ʼ���� 
	 * 2����֤reduce()����
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
	 * ����Customer���г���
	 * 1����֤setBuyTime������getBuyTime����
	 * 2����֤setCashTime������getCashTime����
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
	 * ����Cashier���г���
	 * 1����֤run()����
	 * 2����֤setCount������getCount����
	 */
	@Test
	public void testCashier() {
		// ��֤run()����
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

			// ��֤setCount������getCount����
			cashier.setCount(10);
			Assert.assertEquals(10, cashier.getCount());
			System.out.println("Test Cashier End--------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/*
	 * ����Supermarket���г���
	 * 1����֤Apple, Macbook, Cookie���� Good��ʼ��15�����̵���������ȷ
	 * 2����֤������Apple, Macbook, Cookie���� Good�����ݵ���0 
	 * 3����֤������ÿ��ͳ����ֵ�Ƿ�С��0
	 * 4����֤3��Cashier�Ӵ��Ĺ˿��ܺ͵���45
	 */
	@Test
	public void testSupermarket() {
		System.out.println("Test Supermarket Start--------");
		// ��֤Apple, Macbook, Cookie���� Good��ʼ��15�����̵���������ȷ
		Assert.assertEquals(15, Supermarket.apple.getGoodNum());
		Assert.assertEquals(15, Supermarket.macbook.getGoodNum());
		Assert.assertEquals(15, Supermarket.cookie.getGoodNum());

		Supermarket.main(null);

		// ��֤������Apple, Macbook, Cookie���� Good�����ݵ���0
		Assert.assertEquals(0, Supermarket.apple.getGoodNum());
		Assert.assertEquals(0, Supermarket.macbook.getGoodNum());
		Assert.assertEquals(0, Supermarket.cookie.getGoodNum());

		// ��֤������ÿ��ͳ����ֵ�Ƿ�С��0
		Assert.assertTrue("ÿ���˿�ƽ���ȴ�ʱ�����0", Supermarket.cusWaitTimeAvg > 0);
		Assert.assertTrue("ÿ����Ʒƽ���۳�ʱ�����0", Supermarket.goodSaleTimeAvg > 0);
		Assert.assertTrue("�ӿ�ʼ���۵��������ܹ�ʱ�����0", Supermarket.saleTotalTime > 0);
		
		int cusToal=Supermarket.cashierA.getCount()+
				Supermarket.cashierB.getCount()+Supermarket.cashierC.getCount();
		Assert.assertEquals(45, cusToal);
		System.out.println("Test Supermarket End--------");
	}

}