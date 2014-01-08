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
	public ArrayList<Col> Theader;//��ͷ
	public ArrayList<Row> Tbody;//����
	
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
	//���ñ��һ��:colName������colType:������
	public void setCol(String colName, DataType dataType){
		Col col = new Col();
		col.Name = colName;
		col.Type = dataType;
		Theader.add(col);
		cols ++;
	}
	
	///��ȡ��ͷ��Ϣ
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
	
	//���һ�б�����
	public void addRow(Row row){
		Tbody.add(row);
		rows++;
	}
	
	public Row newRow(){
		return  new Row();
	}
	
	//ɾ�����еĵ�i������
	public void removeRow(int i) throws Exception{
		if(i >= rows)
			throw new Exception("û�п�ɾ�������ݣ�");
		else{
			Tbody.remove(i);
			rows--;
		}
	}
	//��ñ��һ������
	public Row getRow(int i){
		if(rows == 0)
			return null;
		else
			return Tbody.get(i);
	}
	
	//����ж��󣬰��������ƺ�����������
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
	
	//����ж�����Ӧ�����м����������Ͷ�Ӧ������Ϊ��������
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
		//i:��i�У�td:��i������
		public void setRow(int idx,Object td) throws Exception {
			//����λ��У��
			if(idx >= cols)
				throw new Exception("[Table]���õ��в����ڣ�");
			
			//��Ԫ�������Ӧ�����е�����һ�£������ж�Ϊ����
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
					throw new Exception("[Table]Ҫ�����������"+dt.toString() +"��������"+sType +"��ƥ�䣡");
			}
			Tr.add(idx, td);
		}
		
		//��ȡָ����Ԫ������
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
		//ת��ΪBoolean��
		public Boolean getBoolean(int i){
			String s = getTd(i).toString();
			//sֵΪ�ַ���"True"��"false"
			if("true".equalsIgnoreCase(s))
				return true;
			//sֵΪ"False"
			else if("false".equalsIgnoreCase(s))
				return false;
			//�������������ֵ
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
