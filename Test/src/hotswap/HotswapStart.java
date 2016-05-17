package hotswap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* @author tangjifu
* @version 2015年12月2日
*/
public class HotswapStart {
	private static TestBean bean = new TestBeanImpl();
	private static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) throws Exception {
		schedule.scheduleAtFixedRate(new TestThead(), 2, 5, TimeUnit.SECONDS);
        while (true) {
            BufferedReader br = null;
    		try {
    			System.out.println("请输入：");
    			br = new BufferedReader(new InputStreamReader(System.in));
    			String inputStr = br.readLine();
    			if(inputStr.equals("hotswap")){
    				String path = "E:\\workspace\\Test\\bin\\hotswap\\TestBeanImpl.class";  
		            byte[] b = getBytes(path);  
		            Class c = new DynamicClassLoader().findClass(b);
		            bean = (TestBean)c.newInstance();
		            System.out.println("----热更完成----");
    			}else if(inputStr.equals("exit")){
    				System.out.println("----退出jvm----");
    				System.exit(1);
    			}
    		} catch (IOException e) {
    			System.err.println("readline err."+e);
    			return;
    		}
//    		}finally{
//    			if(br!=null){
//    				try {
//    					br.close();
//    				} catch (IOException e) {}
//    			}
//    		}
        }
    }
	
	// 从本地读取文件  
    private static byte[] getBytes(String filename) throws IOException {  
        File file = new File(filename);  
        long len = file.length();  
        byte raw[] = new byte[(int) len];  
        FileInputStream fin = new FileInputStream(file);  
        fin.read(raw);  
        fin.close();  
        return raw;  
    }
	
	static class TestThead implements Runnable{
		@Override
		public void run() {
			TestBeanImpl impl = new TestBeanImpl();
			System.out.println(impl.getStr());
		}
	}
}
