package annotation;
import java.lang.reflect.Method;

public class MyTargetTest {
	@MyTarget
	public void doSomething() {
		System.out.println("hello world");
	}

	public static void main(String[] args) throws Exception {
		Method method = MyTargetTest.class.getMethod("doSomething", null);
		if (method.isAnnotationPresent(MyTarget.class)) // 如果doSomething方法上存在注解@MyTarget，则为true
		{
			System.out.println(method.getAnnotation(MyTarget.class));
		}
	}
}
