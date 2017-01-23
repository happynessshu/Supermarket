package supermarket;

/*该Good类实现：
 * 1、定义goodNum变量
 * 2、重写构造函数，用于实例化
 * 3、实现getGoodNum方法
 * 4、实现reduce()方法，用于购买商品后，该商品进行减少*/
public class Good {
	public  int goodNum;
	
	public Good(int i) {
		this.goodNum = i;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public  void reduce(){
		goodNum -- ;
	}
	
}