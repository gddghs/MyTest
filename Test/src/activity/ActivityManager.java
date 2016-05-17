package activity;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* @author tangjifu
* @version 2016年1月21日
*/
public class ActivityManager {
	private static ScheduledExecutorService schedule = null;
	
	public static void main(String [] args){
		schedule = Executors.newSingleThreadScheduledExecutor();
		schedule.scheduleAtFixedRate(new ActivityManager().new FixedRate(), 0, 5, TimeUnit.SECONDS);
	}
	
	class FixedRate implements Runnable{
		@Override
		public void run() {
			run();
		}
	}
	/**
	 * 注册一个活动
	 * 后台控制添加一个待开启的活动
	 * @param sysId
	 */
	public void register(int sysId, int startTime, int endTime){
		Activity a = new Activity(sysId);
		a.setStartTime(startTime);
		a.setEndTime(endTime);
		
		ActivityCache.instance.addActivity(a);
		System.out.println("register activity:"+sysId);
	}
	
	/**
	 * 轮询所有的活动，该结束的结束，改开启的开启
	 */
	public void run(){
		HashMap<Integer, Activity> allActivity = ActivityCache.instance.getAllActivity();
		for(Activity a : allActivity.values()){
			
		}
	}
	
	/**
	 * 加载活动exl配置信息
	 */
	public void loadActivityConfig(){
		//ActivityCache.instance.addChildActivity(aId, childArr);
	}
	
	
	/**
	 * 服务器启动时
	 * 从数据库查询所有的活动
	 */
	public void loadAllActivityForDB(){
		
	}
	
	
	/**
	 * 关服时，保存所有活动
	 */
	public void saveAllActivity(){
		
	}
	
}
