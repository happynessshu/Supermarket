package supermarket;

import java.util.concurrent.LinkedBlockingQueue;

/*该Cashier类实现：
 * 1、使用LinkedBlockingQueue对接待Customer进行同步处理；
 * 2、定义setCustomers方法用于设置接待的Customer；
 * 3、统计Cashier总共接待Customer的数据；*/
public class Cashier implements Runnable {
	
   LinkedBlockingQueue<Customer> customers;
   //统计每个Cashier接待Customer数
   private int count=0;
   //用于判断线程结束
   int threadEnd=0;
   
	@Override
	public void run() {
		//若没有Customer购物则一直等待
		while (true) {  
			//对Customer进行Cashier
			while (!customers.isEmpty()) {
				try {
					count= count+1;
					Customer customer = new Customer();
					customer.setBuyTime(customers.take().getBuyTime());
					customer.setCashTime(System.currentTimeMillis());
					Supermarket.customersTotal.add(customer);
					int x = 5 + (int) (Math.random() * 5);	
					Thread.sleep(x * 1000);
				} catch (InterruptedException e) {
				}
			}
			if (Supermarket.customersTotal.size() == 45 ) {
				System.out.println(Thread.currentThread().getName() +"接待Customer数：" + count);
				setCount(count);
				threadEnd=1;
				break;
			}
		}		
	}
	
	public int getCount(){
		return count;
	}
	public void setCount(int count){
		this.count=count;
	}

	public void setCustomers(LinkedBlockingQueue<Customer> customers) {
		this.customers = customers;
	}

}
