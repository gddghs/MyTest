import java.util.Comparator;

/**
* @author tangjifu
* @date 2016年3月17日 上午11:00:24
*/
public class PaperComparator implements Comparator<Paper>{
	
	public int compare(Paper o1, Paper o2) {
		int cmp = ((Integer)o2.id).compareTo(o1.id);
		if(cmp == 0){
			cmp = ((Integer)o1.lv).compareTo(o2.lv);
			if(cmp == 0){
				return o2.name.compareTo(o1.name);
			}else{
				return cmp;
			}
		}else{
			return cmp;
		}
	}

}
