public class TestLock implements Runnable{  
    private int flag = 1;  
    static Object o1 = new Object(), o2 = new Object(); 
    static int lock_count = 0;
    private int myLock;
    
    public TestLock(){
    	synchronized (TestLock.class) {
    		lock_count ++;
    		myLock = lock_count;
		}
    }
    
    
    public static void main(String[] argv){  
    	TestLock td1 = new TestLock();  
    	TestLock td2 = new TestLock();  
        td1.flag = 1;  
        td2.flag = 2;  
        Thread t1 = new Thread(td1);  
        Thread t2 = new Thread(td2);  
        t1.start();  
        t2.start();  
    }
    
    
    public void run(){  
    	System.out.println("flag = "+ flag);
    	if(flag == 1)
    		update(flag, o1, o2);
    	else
    		update(flag, o2, o1);
    }
    
    
    public void update(int flag, Object o1, Object o2){
    	Object obj1 = o1;
    	Object obj2 = o2;
    	if(flag==2 && myLock == 2){
    		obj1 = o2;
    		obj2 = o1;
    		System.out.println("---td2");
    	}else{
    		System.out.println("---td1");
    	}
    		
        synchronized (obj1){  
            try{  
                Thread.sleep(500);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            synchronized(obj2){  
                System.out.println(flag);  
            }  
        }  
    }
}  