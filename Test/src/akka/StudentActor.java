package akka;

import akka.actor.UntypedActor;

/**
* @author tangjifu
* @date 2017年2月23日 下午2:24:19
*/
public class StudentActor extends UntypedActor{

	@Override
	public void onReceive(Object arg0) throws Exception {
		System.out.println("[StudentActor] receive message : " + arg0);  
        getSender().tell("hello! teacher", getSelf());  
	}

}
