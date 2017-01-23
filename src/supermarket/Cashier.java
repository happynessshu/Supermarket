package supermarket;

import java.util.concurrent.LinkedBlockingQueue;

/*��Cashier��ʵ�֣�
 * 1��ʹ��LinkedBlockingQueue�ԽӴ�Customer����ͬ������
 * 2������setCustomers�����������ýӴ���Customer��
 * 3��ͳ��Cashier�ܹ��Ӵ�Customer�����ݣ�*/
public class Cashier implements Runnable {
	
   LinkedBlockingQueue<Customer> customers;
   //ͳ��ÿ��Cashier�Ӵ�Customer��
   private int count=0;
   //�����ж��߳̽���
   int threadEnd=0;
   
	@Override
	public void run() {
		//��û��Customer������һֱ�ȴ�
		while (true) {  
			//��Customer����Cashier
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
				System.out.println(Thread.currentThread().getName() +"�Ӵ�Customer����" + count);
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
