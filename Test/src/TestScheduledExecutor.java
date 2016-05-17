import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* @author tangjifu
* @date 2016年3月3日 下午12:43:58
*/
public class TestScheduledExecutor {
	private static  ScheduledExecutorService scheduler  = Executors.newScheduledThreadPool(2);
	
	public synchronized void start(int threadNum) {
		if (scheduler == null) {
			scheduler = Executors.newScheduledThreadPool(threadNum);
		}
	}

	public static synchronized void shutdown() {
		if (scheduler != null && !scheduler.isShutdown()) {
			scheduler.shutdown();
		}
	}
	
	public static void main(String [] args){
		scheduler.scheduleAtFixedRate(new CommandJob(), 0, 5, TimeUnit.SECONDS);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.shutdown();
	}
	
}

class CommandJob implements Runnable{
	public void run() {
		System.out.println("++++"+System.currentTimeMillis());
	}
}