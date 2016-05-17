
public class SitSoul {
	private int index;		//房号(1~6:紫灵气，7~18蓝灵气，19~42绿灵气)
	private long roleId;	//占有者：0无人，其他
	private int status;		//状态：0空状态，1有人占领， 2正在被抢夺
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
