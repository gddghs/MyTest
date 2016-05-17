package hotswap;
/**
* @author tangjifu
* @version 2015年12月2日
*/
public class TestBeanImpl implements TestBean{
	private static TestBeanImpl b1 = new TestBeanImpl();
	
	public static TestBeanImpl getInstance(){
		return b1;
	}
	
	public String getOrderName() {  
       return TestBeanImpl2.getInstance().getName();  
	}
	
	public String getStr(){
		int i=10;
		if(i!=10){
			return "你是逗比_"+getOrderName();
		}else{
			return "你不是逗比_"+getOrderName();
		}
	}

	@Override
	public String getName() {
		return "我是bean1";
	}
	
	public void p(){
		System.out.println(getStr());
	}
}
