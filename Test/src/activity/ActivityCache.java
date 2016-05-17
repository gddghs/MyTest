package activity;

import java.util.HashMap;

/**
* @author tangjifu
* @version 2016年1月21日
*/
public class ActivityCache {
	public static final ActivityCache instance = new ActivityCache();
	
	private ActivityCache(){
		
	}
	//当前进行中的活动
	private HashMap<Integer, Activity> allActivityMap = new HashMap<Integer, Activity>();
	//活动配置信息
	private HashMap<Integer, Activity> activityConfig = new HashMap<Integer, Activity>();
	//大活动包含的小活动集合
	private HashMap<Integer, int[]> childActivityMap = new HashMap<Integer, int[]>();
	
	
	public HashMap<Integer, Activity> getAllActivity(){
		return allActivityMap;
	}
	
	public void addActivity(Activity activity){
		allActivityMap.put(activity.getSysId(), activity);
	}
	
	public Activity getActivityConfig(int aId){
		return activityConfig.get(aId);
	}
	
	public void addChildActivity(int aId, int[] childArr){
		childActivityMap.put(aId, childArr);
	}
	
	public int[] getChildActivity(int aId){
		return childActivityMap.get(aId);
	}
}
