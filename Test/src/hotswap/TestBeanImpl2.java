package hotswap;
/**
* @author tangjifu
* @version 2015年12月2日
*/
public class TestBeanImpl2 implements TestBean{
	private static TestBeanImpl2 b2 = new TestBeanImpl2();
	
	public static TestBeanImpl2 getInstance(){
		if(HotswapStart2.isHotswap)
			return (TestBeanImpl2)HotswapStart2.getNewInstance(2);
		return b2;
	}
	
	public String getName() {  
       return "我是bean2___";  
	}
	
	public String getStr(){
		int i=10;
		if(i!=10){
			return "你是sb";
		}else{
			return "你不是sb";
		}
	}
	
	public void p(){
		System.out.println(getStr());
	}
}
