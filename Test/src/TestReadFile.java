import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
* @author tangjifu
* @version 2015年12月28日
*/
public class TestReadFile {

	public static void main(String [] args){
		List<Integer> list = new ArrayList<Integer>();
		File f = new File("C:\\Users\\tangjifu\\Desktop\\new 1");
		try {
			Reader in = new FileReader(f);
			BufferedReader reader = new BufferedReader(in);
			String s = null;
			while((s = reader.readLine())!=null){
				int index = Integer.parseInt(s.substring(s.lastIndexOf("-")+1, s.length()));
				if(list.contains(index))
					System.out.println(index);
				else
					list.add(index);
			}
			reader.close();
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
	}
}
