import java.util.ArrayList;
import java.util.List;

/**
*/
public class TestReloader{
	private static List<IReloader> list = new ArrayList<>();
	static{//简易的注册
		//list.add(new R1());
		list.add(new R2());
		list.add(new R3());
		list.add(new R4());
	}
	
	public static void main(String []args){
		//模拟初始化
		for(IReloader r:list)
			r.reload();
		
	}
}


interface IReloader {
	public void reload();
}


class R1 implements IReloader, Cloneable{
	public Attr a;
	public R1(Attr a){
		this.a = a;
	}
	@Override
	public void reload() {
		System.out.println("init r1.");
	}
	
	
	@Override
	public R1 clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		R1 r= (R1) super.clone();
		return r;
	}
	
}
class R2 implements IReloader{
	
	@Override
	public void reload() {
		System.out.println("init r2.");
	}
	
}
class R3 implements IReloader{
	
	@Override
	public void reload() {
		System.out.println("init r3.");
	}
	
}
class R4 implements IReloader{
	
	@Override
	public void reload() {
		System.out.println("init r4.");
	}
	
}

class Attr{
	public int aId;
	public String aName;
	
	public Attr(int id, String name){
		aId = id;
		aName = name;
	}
}