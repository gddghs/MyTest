/**
* @author tangjifu
* @date 2016年3月17日 上午11:00:24
*/
public class Paper implements Comparable<Paper>, Cloneable{
	
	protected int id;
	protected int lv;
	protected String name;
	protected int[] arr;
	protected boolean online;
	
	public Paper(){
		System.out.println("dfdsf");
	}
	
	public Paper(int id, int lv, String name){
		this.id = id;
		this.lv = lv;
		this.name = name;
	}
	
	public void setLv(int lv){
		this.lv = lv;
	}
	
	protected Paper clone() throws CloneNotSupportedException {
		return (Paper)super.clone();
	}
	
	/*@Override
	public int compareTo(Paper o) {
		int cmp = ((Integer)this.id).compareTo(o.id);
		if(cmp == 0){
			cmp = ((Integer)this.lv).compareTo(o.lv);
			if(cmp == 0){
				return this.name.compareTo(o.name);
			}else{
				return cmp;
			}
		}else{
			return cmp;
		}
	}*/
	@Override
	public int compareTo(Paper o) {
		int cmp = ((Integer)o.id).compareTo(this.id);
		if(cmp == 0){
			cmp = ((Integer)o.lv).compareTo(this.lv);
			if(cmp == 0){
				return o.name.compareTo(this.name);
			}else{
				return cmp;
			}
		}else{
			return cmp;
		}
	}
	
	@Override
	public String toString() {
		return this.id+"_"+this.lv+"_"+this.name;
	}

}
