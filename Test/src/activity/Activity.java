package activity;
/**
* @author tangjifu
* @version 2016年1月21日
* 活动可能包含小活动，可能是相对于某些玩家（例如等级限制）参加，
* 可能是在指定的一些特殊时间段不会开发（例如开服期间等），可能
* 需要重复开启
*/
public class Activity {
	private int sysId;	//活动配置id
	private int startTime;
	private int endTime;
	private byte state;	//获得状态，ActivityConst
	private int version;	//活动版本号，同一个活动重开+1
	private boolean isParent;	//是否是大活动
	private int[] limit; //参与活动限制条件
	
	public Activity(int sysId){
		this.sysId = sysId;
		this.version = 0;
		Activity sys = ActivityCache.instance.getActivityConfig(sysId);
		this.limit = sys.limit;
		this.isParent = sys.isParent;
	}
	
	public int getSysId() {
		return sysId;
	}
	public void setSysId(int sysId) {
		this.sysId = sysId;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public int[] getLimit() {
		return limit;
	}
	public void setLimit(int[] limit) {
		this.limit = limit;
	}
}
