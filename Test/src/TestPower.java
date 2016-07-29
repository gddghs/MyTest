import java.util.HashMap;
import java.util.Map;

/**
* @author tangjifu
* @date 2016年7月29日 下午8:07:11
*/
public class TestPower {
	private static int P1 = 1;//添加
	private static int P2 = 2;//修改
	private static int P3 = 3;//删除
	private static int P4 = 4;//查询
	private static int P5 = 5;//注册
	
	private static Map<String, Integer> userPurview = new HashMap<String, Integer>();
	
	static{
		userPurview.put("张三", (int)Math.pow(2, P2)+(int)Math.pow(2, P3)+(int)Math.pow(2, P4)); 
		userPurview.put("李四", (int)Math.pow(2, P1)+(int)Math.pow(2, P5));
	}
	
	/**
	 * 
	 * @param userPurview 总权值
	 * @param optPurview 要判断的权限
	 * @return
	 */
	public static boolean checkPower(int userPurview, int optPurview){
	  int purviewValue = (int)Math.pow(2, optPurview);
	  return (userPurview & purviewValue) == purviewValue;
	}
	
	public static void main(String [] args){
		System.out.println(checkPower(userPurview.get("张三"), P1));
		System.out.println(checkPower(userPurview.get("张三"), P2));
		System.out.println(checkPower(userPurview.get("张三"), P4));
		System.out.println(checkPower(userPurview.get("李四"), P1));
		System.out.println(checkPower(userPurview.get("李四"), P4));
	}
}
