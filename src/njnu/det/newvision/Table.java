/*
 * @author Tian Jun-hua
 * Department of Education Technology,NanJing Normal University
 * 2013-11-09
 * Version:1.0
 * Annotation:
 *   The file is used for KBMP(Knowledge Building Mobile Platform).
 */

package njnu.det.newvision;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import njnu.det.newvision.Table;

public class Table {
	String TName;
	int cols = 0, rows = 0; 
	int pageSize = -1,pageCount = 0,pageNo = 0;
	public ArrayList<Col> Theader;//表头
	public ArrayList<Row> Tbody;//表体
	
	public enum DataType{NUMBER,TEXT,BOOLEAN,DATE,OBJECT};
	public Table(){
		Theader = new ArrayList<Table.Col>();
		Tbody = new ArrayList<Table.Row>();
	}
	public int getColSize(){
		return cols;
	}
	
	public int getRowSize(){
		return rows;
	}
	
	public void setPageSize(int i){
		pageSize = i;
	}
	public int getPageSize(){
		return pageSize;
	}
	
	protected void setPageNo(int i){
		pageNo = i;
	}
	public int getPageNo(){
		return pageNo;
	}
	protected  void setPageCount(int i){
		pageCount = i;
	}
	public int getPageCount(){
		return pageCount;
	}
	
	public void setTName(String name){
		TName = name;
	}
	
	public String getTName(){
		return TName;
	}
	//设置表的一列:colName列名；colType:列类型
	public void setCol(String colName, DataType dataType){
		Col col = new Col();
		col.Name = colName;
		col.Type = dataType;
		Theader.add(col);
		cols ++;
	}
	
	///获取表头信息
	public Col[] getCols(){
		if(cols == 0)
			 return null;
		else{
			Col[] cols = new Col[Theader.size()];
			for(int i =0; i<Theader.size();i++)
				cols[i] = (Col)Theader.get(i);
			return cols;
		}
	}
	
	//添加一行表数据
	public void addRow(Row row){
		Tbody.add(row);
		rows++;
	}
	
	public Row newRow(){
		return  new Row();
	}
	
	//删除表中的第i行数据
	public void removeRow(int i) throws Exception{
		if(i >= rows)
			throw new Exception("没有可删除的数据！");
		else{
			Tbody.remove(i);
			rows--;
		}
	}
	//获得表的一行数据
	public Row getRow(int i){
		if(rows == 0)
			return null;
		else
			return Tbody.get(i);
	}
	
	//表格列对象，包括列名称和列数据类型
	public class Col{
		private  String Name;
		private DataType Type;
		public String getName(){
			return Name;
		}
		public DataType getType(){
			return Type;
		}
		
	}
	
	//表格行对象。它应该与列及其数据类型对应，否则为错误数据
	public class Row{
		Boolean checkType = false;
		ArrayList<Object> Tr;
		private Row() {
			Tr = new ArrayList<Object>();
			for(int i =0;i<cols;i++)
				Tr.add(null);
		}
		public void checkType(Boolean b){
			checkType = b;
		}
		//i:第i列；td:第i列数据
		public void setRow(int idx,Object td) throws Exception {
			//索引位置校验
			if(idx >= cols)
				throw new Exception("[Table]设置的列不存在！");
			
			//单元格的数据应该与列的类型一致，否则判断为错误
			if(checkType){
				String sType = td.getClass().getName();
				sType = sType.substring(sType.lastIndexOf('.')+1).toLowerCase();
				DataType dt = Theader.get(idx).Type;
				Boolean error =false;
				switch (dt) {
				case NUMBER:
					if(!"number byte short int integer long float double".contains(sType.toLowerCase()))
						error = true;
					break;
				case TEXT:
					if(!"text string stringbuffer".contains(sType.toLowerCase()))
						error=true;
					break;
				case BOOLEAN:
					if(!"bool boolean".contains(sType.toLowerCase()))
						error = true;
					break;
				case DATE:
					if(!"date caledar gregoriancalendar".contains(sType.toLowerCase()))
						error = true;
				}
				
				if(error)
					throw new Exception("[Table]要求的数据类型"+dt.toString() +"与所给的"+sType +"不匹配！");
			}
			Tr.add(idx, td);
		}
		
		//获取指定单元格内容
		 public Object getTd(int i){
			if(Tr.size() == 0)
				return null;
			else
				return Tr.get(i);
		}
		
		public String getString(int i){
			return String.valueOf(getTd(i));
		}
		
		public int getInt(int i){
			Object object = getTd(i);
			if(object == null)
				return -0;
			else
				return Integer.parseInt(object.toString());
		}
		
		public Number getNumber(int i){
			Number n = (Number)getTd(i);
			return n;
		}
		//转化为Boolean型
		public Boolean getBoolean(int i){
			String s = getTd(i).toString();
			//s值为字符串"True"或"false"
			if("true".equalsIgnoreCase(s))
				return true;
			//s值为"False"
			else if("false".equalsIgnoreCase(s))
				return false;
			//如果存贮的是数值
			if(Integer.parseInt(s)>0)
				return true;
			else 
				return false;
		}
		
		public Date getDate(int i){
			Date date = null;
			if("java.util.GregorianCalendar".equalsIgnoreCase(getTd(i).getClass().getName())){
				Calendar gc  = (Calendar)getTd(i);
				date = gc.getTime();
			}
			else if("java.sql.Date".equalsIgnoreCase(getTd(i).getClass().getName())){
				java.sql.Date dt = (java.sql.Date)getTd(i);
				date = new Date(dt.getTime());
			}
			else {
				date = (Date)getTd(i);
			}
			return date;
		}
		
		public Object getObject(int i) {
			return getTd(i);
		}
	}
	
}
