import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
* @author tangjifu
* @date 2016年11月2日 上午10:32:59
*/
public class TestMap {

	public static void main(String[] args) throws Exception {  
        Map<String, Integer> myMap = new LinkedHashMap(); 
        for(int i=1; i<=50; i++){
        	myMap.put(i+"", (i/10)*i-i);  
        }
        //(myMap);  
        
        myMap = sortMap(myMap);  

        for(int i=51; i<1000; i++){
        	insert(myMap, i+"", i*2);

        }
        printMap(myMap); 
    }  
	
	private static void insert(Map<String, Integer> map, String k, int v){
		long t1 = System.currentTimeMillis();
		
		Set<Entry<String, Integer>> entrySet = map.entrySet();
		Entry<String, Integer>[] array = entrySet.toArray(new Entry[entrySet.size()]);
		Entry<String, Integer> entry = array[array.length-1];
		if(v > entry.getValue()){
			map.remove(entry.getKey());
			map.put(k, v);
		}
		System.out.println("time:"+(System.currentTimeMillis()-t1));
	}

      
    private static void printMap(Map<String, Integer> map){  
        System.out.println("===================mapStart==================");
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
        	String k = iter.next();
        	Integer v = map.get(k);
        	System.out.println(k + ":" + v);  
        }
        System.out.println("===================mapEnd==================");  
    }   
  
    @SuppressWarnings("unchecked")
    public static Map<String, Integer> sortMap(Map<String, Integer> oldMap) {  
    	Set<Entry<String, Integer>> set = oldMap.entrySet();
		Entry<String, Integer>[] entry = set.toArray(new Entry[set.size()]);
		Arrays.sort(entry,new Comparator<Object>(){
			public int compare(Object o1, Object o2) {  
               Integer v1 = ((Map.Entry<String, Integer>)o1).getValue();  
			Integer v2 = ((Map.Entry<String, Integer>) o2).getValue();  
               return v1.compareTo(v2);
            } 
		}); 
		oldMap.clear();
        for (int i = 0; i < entry.length; i++) {  
        	oldMap.put(entry[i].getKey(), entry[i].getValue());  
        }  
        return oldMap;  
    }  
}
