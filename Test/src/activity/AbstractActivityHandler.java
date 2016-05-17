package activity;
/**
* @author tangjifu
* @version 2016年1月21日
*/
public abstract class AbstractActivityHandler {

	/**
	 * 活动开始
	 * 需要初始化一些数据或其他操作可以在这里进行
	 */
	public void start(){};
	
	
	/**
	 * 活动结束
	 * 需要发放活动奖励或其他操作可以在这里进行
	 */
	public void end(){};
	
	
}
