package doudizhu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author tangjifu
 * @date 2016年5月18日 下午5:11:53
 */
public class CardManager {
	
	public static byte cardSize = 54;
	public static List<Byte> cards = new ArrayList<Byte>(cardSize);
	
	static{
		cards.add((byte)66);
		cards.add((byte)99);
		for(byte i=1; i<=13; i++){
			for(byte j=0; j<4; j++)
				cards.add(i);
		}
		//System.out.println(cards);
	}
	
	
	private static void initCard(){
		List<Integer> tempCountList = new ArrayList<>();
		byte [] arr = new byte[3];
		List<Byte> l1 = new ArrayList<>(); 
		List<Byte> l2 = new ArrayList<>(); 
		List<Byte> l3 = new ArrayList<>();
		Random r = new Random();
		for(int i=0; i<37; i++){
			int index = r.nextInt(cardSize);
			while(tempCountList.contains(index)){
				index = r.nextInt(cardSize);
			}
			tempCountList.add(index);
			byte value = cards.get(index);
			//前面3张底牌
			if(i < 3){
				arr[i] = value;
				continue;
			}
			if(i==0 || i%2==0){
				l1.add(value);
			}else{
				l2.add(value);
			}
		}
		for(int i=0; i<cards.size(); i++){
			if(tempCountList.contains(i))
				continue;
			l3.add(cards.get(i));
		}
		Collections.sort(l1);
		Collections.sort(l2);
		Collections.sort(l3);
		System.out.println("arr:"+Arrays.toString(arr));
		System.out.println("init card:"+l1.toString());
		System.out.println("init card:"+l2.toString());
		System.out.println("init card:"+l3.toString());
		Integer t = 8;
		System.out.println(l1.indexOf(t));
	}
	
	
	public static void main(String[] args){
		initCard();
//		String[]cards=new String[]{
//		"黑桃A","黑桃2","黑桃3","黑桃4","黑桃5","黑桃6","黑桃7","黑桃8","黑桃9","黑桃10","黑桃J","黑桃Q","黑桃K",
//"红桃A","红桃2","红桃3","红桃4","红桃5","红桃6","红桃7","红桃8","红桃9","红桃10","红桃J","红桃Q","红桃K",
//"梅花A","梅花2","梅花3","梅花4","梅花5","梅花6","梅花7","梅花8","梅花9","梅花10","梅花J","梅花Q","梅花K",
//"方块A","方块2","方块3","方块4","方块5","方块6","方块7","方块8","方块9","方块10","方块J","方块Q","方块K","大王","小王"};
//Random random = new Random();
//for(int i=cards.length-1;i>=1;i--){int j= random.nextInt(i);String t= cards[i];cards[i]=cards[j];cards[j]=t;}
//for(int i=0;i<cards.length;i++){System.out.print(cards[i]+",");}
//		//2、顺序发牌 
//		System.out.println();//输出回车字符 
//		String[] players = new String[]{"张三","李四","王五",}; for(int i=0;i<cards.length-3; i++){
//String card = cards[i];//card 代表每一张牌 
//String player=players[i%players.length];//player 代表每个玩游戏的人 
//System.out.println(player+":"+card);}
//		System.out.print("底牌"+":");
//		for(int k=cards.length-3;k<cards.length;k++){     //String[] dipai  
//		    //dipai=new String[]{"cards[k]"};     System.out.print(cards[k]); } 
//		}    
//		}
	}
}
