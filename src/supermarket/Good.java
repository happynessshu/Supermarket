package supermarket;

/*��Good��ʵ�֣�
 * 1������goodNum����
 * 2����д���캯��������ʵ����
 * 3��ʵ��getGoodNum����
 * 4��ʵ��reduce()���������ڹ�����Ʒ�󣬸���Ʒ���м���*/
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