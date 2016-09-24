

/**
* @author tangjifu
* @date 2016年3月16日 上午11:17:00
*/
public class TestSitSoul implements Runnable{
	private static final SitSoul sit = new SitSoul();
	
	public static void main(String [] args){
		TestSitSoul obj = new TestSitSoul();
		Thread t1 = new Thread(obj);
		Thread t2 = new Thread(obj);
		t1.setName("t1");
		t2.setName("t2");
		
		t1.start();
		t2.start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sit.getIndex());
		System.out.println(sit.getStatus());
	}
	
	
	@Override
	public void run() {
		try{
			String name = Thread.currentThread().getName();
			if(name.equals("t1")){
				System.out.println("线程t1开始执行");
				Thread.sleep(1000);
				for(int i=0; i<50000; i++)
					sit.incIndex();
				System.out.println("线程t1结束");
			}else{
				System.out.println("线程t2开始执行");
				Thread.sleep(1000);
				for(int i=0; i<50000; i++)
					sit.incIndex();
				System.out.println("线程t2结束");
			}
		}catch(Exception e){
			
		}
	}
}
