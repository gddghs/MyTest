package createXml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
* @author tangjifu
* @version 2015年11月27日
*/
public class TestCreateXml {

	public static void main(String [] args){
		System.out.println("请输入excel名字：");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputName = "";
		try {
			inputName = br.readLine();
		} catch (IOException e) {
			System.err.println("readline err."+e);
			return;
		}
		String filePath = "E:\\projectfun\\project\\tool\\excel2xml\\toexport\\worldBoss\\"+inputName+".xls";
		createXml(readXls(filePath), inputName);
		System.out.println("create "+inputName+" xml ok!");
	}

	/**
	 * 解析excel文件
	 * 循环读取各个工作表的第一行信息，然后对该行各个单元格读取，
	 * @param filePath
	 * @return
	 */
	private static Map<SheetModel, List<ColumnModel>> readXls(String filePath){
		Map<SheetModel, List<ColumnModel>> map = null;
		String sheetName = "";
		int cellNum = 0;
		
		try{
	        InputStream is = new FileInputStream(filePath);
	        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	        map = new HashMap<SheetModel, List<ColumnModel>>(hssfWorkbook.getNumberOfSheets());
	       
	        //循环工作表Sheet
	        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	            HSSFSheet sheet = hssfWorkbook.getSheetAt(numSheet);
	            if (sheet == null) 
	                continue;
	            sheetName = sheet.getSheetName();
	            
	            SheetModel sm = null;
	            ColumnModel cm = null;
	            List<ColumnModel> list = new ArrayList<ColumnModel>();
	            //第一行，并循环行所有列，获得ColumnModel & SheetModel属性
	            HSSFRow row = sheet.getRow(0);
	            if(row==null)
	            	continue;
	            for (short i=0; i<row.getPhysicalNumberOfCells(); i++) {
	                HSSFCell cell = row.getCell(i);
	                if (cell == null)
	                    continue;
	                cellNum = i+1;
	                //拿到某一个单元格内容
	                String value = cell.getStringCellValue();
	                String [] arr = value.split("\\|");
	                if(arr.length<=1)
	                	continue;
	                
	                //最后一个单元格是特殊的吗，这个单元格是自己额外加的，三个参数分别表示"SheetNodeName|FirstTagName|OutputXml"
	                //例如：EquipSuits|EquipSuit|m_equip.xml
	                if(arr.length==3){// if(i==row.getPhysicalNumberOfCells()-1)
	                	sm = new SheetModel();
	                	sm.setSheetName(sheetName);
	                	sm.setSheetNodeName(arr[0]);
	                	sm.setFirstTagName(arr[1]);
	                	sm.setOutputXml(arr[2]);
	                	sm.setStartRowIndex(2);
	                }else{
	                	//普通单元格分别表示"字段名称中文|英文名称|是否需要填充|是否合并|是否包含子单元格|如果包含子单元格，表示包含的长度"
	                	//例如："等级|level|0|0|0|"表示等级level，不需要填充，不合并，不包含子单元格
	                	//"装备ID|equipId|1|1|1|名称-目标属性"表示装备id,需要填充，需要合并，包含子单元格，长度是从名称列~目标属性列
	                	cm = new ColumnModel();
	                	cm.setColName(arr[0]);
	                	cm.setName(arr[1]);
	                	//cm.setFill(arr[2].equals("1")?"true":"false");
	                	//cm.setCombine(arr[3].equals("1")?"true":"false");
	                	//cm.setHasChild(arr[4].equals("1")?"true":"false");
	                	cm.setFill("false");
	                	cm.setCombine("false");
	                	cm.setHasChild("false");
	                	if(arr.length>=6)
	                		cm.setChildNodes(arr[5].equals("")?"":arr[5]);
	                	
	                	list.add(cm);
	                }
	            }
	            if(sm!=null)
	            	map.put(sm, list);
	        }
	        return map;
		 }catch(Exception e){
			 System.out.println("sheet="+sheetName+", cellNem="+cellNum+", exception="+e);
			 return null;
		 }
	 }
	 
	 
	/**
	 * 生产xml文件
	 * @param map
	 */
	 public static void createXml(Map<SheetModel, List<ColumnModel>> map, String inputName){
		 if(map==null || map.isEmpty())
			 return;
		 // 创建根节点 并设置sheet个数;     
		 Element root = new Element("config").setAttribute("sheet", ""+map.size());     
		 
		 Iterator<SheetModel> itor = map.keySet().iterator();
		 while(itor.hasNext()){
			 SheetModel sm = itor.next();
			 //创建sheet节点，并设置属性
			 Element sheetNode = new Element("sheet");
			 sheetNode.setAttribute("sheetName", sm.getSheetName());
			 sheetNode.setAttribute("firstTagName", sm.getFirstTagName());
			 sheetNode.setAttribute("startRowIndex", sm.getStartRowIndex()+"");
			 sheetNode.setAttribute("outputXml", sm.getOutputXml());
			 sheetNode.setAttribute("sheetNodeName", sm.getSheetNodeName());
			 
			 //添加sheet的子节点，并赋值
			 List<ColumnModel> list = map.get(sm);
			 for(ColumnModel cm : list){
				 Element columnNode = new Element("column");
				 columnNode.setAttribute("colName", cm.getColName());
				 columnNode.setAttribute("name", cm.getName());
				 columnNode.setAttribute("fill", cm.getFill());
				 columnNode.setAttribute("combine", cm.getCombine());
				 columnNode.setAttribute("hasChild", cm.getHasChild());
				 if(cm.getChildNodes()!=null)
					 columnNode.setAttribute("childNodes", cm.getChildNodes());
				 sheetNode.addContent(columnNode);
			 }
			 //将sheet节点添加到根节点
			 root.addContent(sheetNode);
		 }
		 // 使xml文件 缩进效果并输出
		 Format format = Format.getPrettyFormat();  
		 XMLOutputter XMLOut = new XMLOutputter(format);  
		 Document doc = new Document(root);
		 try {
			 XMLOut.output(doc, new FileOutputStream("E:/projectfun/project/tool/excel2xml/toexport/worldBoss/"+inputName+"_export_server_config.xml"));
			 //XMLOut.output(doc, new FileOutputStream("E:/projectfun/project/tool/excel2xml/toexport/runBusiness/"+inputName+"_export_client_config.xml"));
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
	 }
}
