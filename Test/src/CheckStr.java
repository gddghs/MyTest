import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author tangjifu
* @date 2016年10月14日 上午11:36:54
*/
public class CheckStr {

	public static void main(String [] args){
		String str = "fdsaf1243中午";
		//str = str.replaceAll("[^0-9]","");
		str = str.replaceAll("[^0-9a-zA-Z]","");
		System.out.println(str);
		
		String s2 = "<Script>hello</Script>";
		s2 = dostring(s2);
		System.out.println(s2);
		System.out.println(echo(s2));
		
//		String s = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
//		System.out.println(s);
//		System.out.println(StringFilter(s));
		
	}
	
	public static String StringFilter(String str){
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String dostring(String str) {
		str = str.replaceAll("'", "");
		str = str.replaceAll(";", "");
		str = str.replaceAll("--", "");
		str = str.replaceAll("/", "");
		str = str.replaceAll("%", "");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}
	
	public static String echo(String str){
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		return str;
	}
}
