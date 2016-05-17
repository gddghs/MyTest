package createXml;
/**
* @author tangjifu
* @version 2015年11月27日
*/
public class SheetModel {

	private String sheetName;//工作表名称
	private String firstTagName;//
	private int startRowIndex;
	private String outputXml;
	private String sheetNodeName;
	
//	public SheetModel(String sheetName, String firstTagName, int startRow, 
//			String outputXml, String sheetNodeName){
//		this.sheetName = sheetName;
//		this.firstTagName = firstTagName;
//		this.startRowIndex = startRow;
//		this.outputXml = outputXml;
//		this.sheetNodeName = sheetNodeName;
//	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getFirstTagName() {
		return firstTagName;
	}
	public void setFirstTagName(String firstTagName) {
		this.firstTagName = firstTagName;
	}
	public int getStartRowIndex() {
		return startRowIndex;
	}
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}
	public String getOutputXml() {
		return outputXml;
	}
	public void setOutputXml(String outputXml) {
		this.outputXml = outputXml;
	}
	public String getSheetNodeName() {
		return sheetNodeName;
	}
	public void setSheetNodeName(String sheetNodeName) {
		this.sheetNodeName = sheetNodeName;
	}
	
}
