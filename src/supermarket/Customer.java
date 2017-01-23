package supermarket;

/*该Customer类实现：
 * 定义buyTime和cashTime变量，以及变量的set和get方法*/
public class Customer {

	//拿到商品时间
	private long buyTime;	
	//收银时间
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