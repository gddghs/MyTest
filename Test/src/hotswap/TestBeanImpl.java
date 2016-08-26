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
	

	@Override
	public String getName() {
		//return "name:TestBeanImpl";
		newFunc();
		return "name:TestBeanImpl====new";
	}
	
	public void p(){
		System.out.println("aaaaaaaaaa");
	}
	
	public void newFunc(){
		System.out.println("my is new function");
	}
}
