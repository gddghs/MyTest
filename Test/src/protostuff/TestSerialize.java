package protostuff;
/**
* @author tangjifu
* @date 2017年3月3日 下午6:03:27
*/
public class TestSerialize {
	public static void main(String[] args) {      
		User user = new User("1", "xiaobao", "123456");
		System.out.println("序列化");
		byte[] data = SerializeUtils.serialize(user, User.class);
		for (byte b : data) {
			System.out.print(b);
		}
		System.out.println();
		System.out.println("反序列化");
		User user2 = SerializeUtils.deSerialize(data, User.class);
		System.out.println(user2);
    }  
}
