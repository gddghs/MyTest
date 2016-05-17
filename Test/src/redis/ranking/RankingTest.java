package redis.ranking;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Set;  
import redis.clients.jedis.ShardedJedis;  
import redis.clients.jedis.ShardedJedisPool;  
 
/**
* @author tangjifu
* @date 2016年3月25日 下午4:42:53
*/
public class RankingTest {  
  
    //private ApplicationContext context;  
    private ShardedJedisPool shardedJedisPool;  
    private ShardedJedis jedis;  
  
    public RankingTest() {  
  
    }  
  
    public void init() throws Exception {  
  
        /*String config[] = { "applicationContext.xml" };  
        context = new ClassPathXmlApplicationContext(config);  
  
        shardedJedisPool = (ShardedJedisPool) context.getBean("shardedJedisPool");  
        jedis = (ShardedJedis) shardedJedisPool.getResource();*/  
          
    }  
      
    public void rankAdd() {  
        User user1 = new User("12345", "常少鹏", 99.9);  
        User user2 = new User("12346", "王卓卓", 99.8);  
        User user3 = new User("12347", "邹雨欣", 96.8);  
        User user4 = new User("12348", "郑伟山", 98.8);  
        User user5 = new User("12349", "李超杰", 99.6);  
        User user6 = new User("12350", "董明明", 99.0);  
        User user7 = new User("12351", "陈国峰", 100.0);  
        User user8 = new User("12352", "楚晓丽", 99.6);  
        jedis.zadd("game".getBytes(), user1.getScore(), ObjectSer.ObjectToByte(user1));  
        jedis.zadd("game".getBytes(), user2.getScore(), ObjectSer.ObjectToByte(user2));  
        jedis.zadd("game".getBytes(), user3.getScore(), ObjectSer.ObjectToByte(user3));  
        jedis.zadd("game".getBytes(), user4.getScore(), ObjectSer.ObjectToByte(user4));  
        jedis.zadd("game".getBytes(), user5.getScore(), ObjectSer.ObjectToByte(user5));  
        jedis.zadd("game".getBytes(), user6.getScore(), ObjectSer.ObjectToByte(user6));  
        jedis.zadd("game".getBytes(), user7.getScore(), ObjectSer.ObjectToByte(user7));  
        jedis.zadd("game".getBytes(), user8.getScore(), ObjectSer.ObjectToByte(user8));  
    }  
          

    public void gameRankShow() {  
        Set<byte[]> set = jedis.zrevrange("game".getBytes(), 0, -1);  
        Iterator<byte[]> iter = set.iterator();  
      
        int i = 1;  
        List<User> list = new ArrayList<User>();  
        while(iter.hasNext()) {  
            User user = (User) ObjectSer.ByteToObject(iter.next());  
            user.setRank(i++);  
            list.add(user);  
        }  
          
        for(User user : list)   
            System.out.println(user);  
    }
    
    /*out~~
    User [id=12351, name=陈国峰, score=100.0, rank=1]  
	User [id=12345, name=常少鹏, score=99.9, rank=2]  
	User [id=12346, name=王卓卓, score=99.8, rank=3]  
	User [id=12352, name=楚晓丽, score=99.6, rank=4]  
	User [id=12349, name=李超杰, score=99.6, rank=5]  
	User [id=12350, name=董明明, score=99.0, rank=6]  
	User [id=12348, name=郑伟山, score=98.8, rank=7]  
	User [id=12347, name=邹雨欣, score=96.8, rank=8]
	*/
      
}  

