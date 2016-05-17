import java.util.concurrent.atomic.AtomicInteger;


public class Counter { 

    public volatile static int count = 0; 
    public static AtomicInteger c2 = new AtomicInteger(0);

    public static void inc() { 

        //这里延迟1毫秒，使得结果明显 
        try { 
            Thread.sleep(1); 
        } catch (InterruptedException e) { 
        }
        //count = count +1;
        //System.out.println("------"+ count);
        System.out.println("------"+ c2.getAndIncrement());
    } 

    public static void main(String[] args) { 

        for (int i = 0; i < 1000; i++) { 
            new Thread(new Runnable() { 
                @Override
                public void run() { 
                    Counter.inc(); 
                } 
            }).start(); 
        } 
        try { 
            Thread.sleep(5000); 
        } catch (InterruptedException e) { 
        } 
        //这里每次运行的值都有可能不同,可能为1000 
        //System.out.println("运行结果:Counter.count=" + Counter.count); 
        System.out.println("运行结果:Counter.count=" + c2); 
    } 
}