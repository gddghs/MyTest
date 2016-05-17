import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sun.net.www.content.image.png;

public class TestComparable {
	public static void main(String[] args) {
		Paper p1 = new Paper(2, 20, "red");
		Paper p2 = new Paper(1, 20, "blue");
		Paper p3 = new Paper(2, 10, "green");
		Paper p4 = new Paper(1, 10, "name");
		List<Paper> l = new ArrayList<>();
		for(int i= 0; i<4; i++){
			Paper p = new Paper(i, i+10, "name"+i);
			l.add(p);
		}
		
		System.out.println(l);
		/*//List<Paper> list1 = l.subList(0, 1);
		List<Paper> list1 = new ArrayList<>();
		list1.addAll(l.subList(0, 1));
		System.out.println(list1);
		System.out.println("----------");
		Collections.sort(l, new PaperComparator());//comparator排序
		System.out.println(l);
		List<Paper> list2 = l.subList(0, 2);
		
		System.out.println(list1);
		System.out.println(list2);*/
	}
	
	static void test(Paper p4){
		Paper p = new Paper(1, 10, "name");
		Paper pp = null;
		try {
			pp = p.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setLv(10);
		System.out.println(p.compareTo(null));
	}
	
}