package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
* @author tangjifu
* @date 2017年2月23日 上午11:40:25
*/
public class ActorUtil {

	private static ActorSystem actorSystem = null;  
    
    public static void start() {  
        System.out.println("start actorSystem...");  
        actorSystem = ActorSystem.create();
    }  
      
    @SuppressWarnings("rawtypes")
	public static ActorRef actorOf(Class clazz) {  
        return actorSystem.actorOf(Props.create(clazz));  
    }  
      
    public static void shutdown() {  
        System.out.println("shutdown actorSystem...");  
        actorSystem.shutdown(); 
    }
    
    public static void main(String [] args){
    	start();  
        ActorRef student = actorOf(StudentActor.class);  
        ActorRef teacher = actorOf(TeacherActor.class);
        //学生向老师打招呼
        teacher.tell("hello! teacher!", student);  
    }
}
