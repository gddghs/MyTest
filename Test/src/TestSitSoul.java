import java.util.concurrent.ConcurrentHashMap;


/**
* @author tangjifu
* @date 2016年3月16日 上午11:17:00
*/
public class TestSitSoul implements Runnable{
	private static boolean flag = true;
	private static ConcurrentHashMap<Integer, SitSoul> soulMap = new ConcurrentHashMap<Integer, SitSoul>();
	private static long r1 = 1l;
	private static long r2 = 2l;
	private static TestSitSoul o = new TestSitSoul();
	static{
		SitSoul soul = new SitSoul();
		soul.setIndex(1);
		SitSoul soul2 = new SitSoul();
		soul2.setIndex(2);
		SitSoul soul3 = new SitSoul();
		soul3.setIndex(3);
		soulMap.put(1, soul);
		soulMap.put(2, soul2);
		soulMap.put(3, soul3);
	}
	
	public static void main(String [] args){
		//r1先占领soul1
		o.zhan(r1, 1);
		
		Thread t1 = new Thread(o);
		Thread t2 = new Thread(o);
		
		t1.start();
		t2.start();
	}
	
	public synchronized void qiang(long r, int soulIndex){
		SitSoul soul = soulMap.get(soulIndex);
		synchronized (soul) {
			if(soul.getStatus()==2)
				return;
			soul.setStatus(2);
			try{
				Thread.sleep(5000);
			}catch(Exception e){
				System.out.println(e);
			}
			//抢夺成功
			soul.setStatus(1);
			soul.setRoleId(r);
		}
		System.out.println("R"+r+" 抢到了soul "+soulIndex);
	}
	
	public void zhan(long r, int soulIndex){
		SitSoul soul = soulMap.get(soulIndex);
		SitSoul s = soulMap.get(1);
		synchronized (s) {
			try{
				Thread.sleep(5000);
			}catch(Exception e){
				System.out.println(e);
			}
			if(soul.getStatus()!=0){
				System.out.println("soul "+soulIndex+"已经被占领了");
			}else{
				soul.setRoleId(r);
				soul.setStatus(1);
				System.out.println("R"+r +" 占领了soul "+soulIndex);
			}
		}
	}
	
	@Override
	public void run() {
		if(flag){
			flag = false;
			System.out.println("R2 开始抢soul 3");
			qiang(r2, 3);//r1抢soul2
		}else{
			flag = true;
			System.out.println("R1开始抢soul 3");
			qiang(r1, 3);//r2占领soul2
		}
	}
}
