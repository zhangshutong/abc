/**
wws* @author Tian Jun-hua
* @version 1.0
 */
package njnu.det.newvision;
import java.io.File;
import java.io.StringReader;
import java.util.Locale;

import njnu.det.newvision.Table;
import njnu.det.newvision.Table.Col;
import njnu.det.newvision.Table.DataType;
import njnu.det.newvision.Table.Row;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public  class DAO {
	String xMLStr;
	//String sqlStr;
	OperationType op;
	String condition;
	String dbPath;
	String dbName;
	SQLiteDatabase sqliteDb;
	Table table;
	//��������
	public	enum OperationType{
		CREATETABLE,SELECT,ADDROW,DELROW,UPDATE;
	}
	
	public DAO(String dbPath,String dbName) throws Exception{
		this.dbPath = dbPath; 
		this.dbName = dbName;
	}
	
	public void CreateTable(String sql,String passwd) throws Exception{
		if(!"Admin".equals(passwd))
			throw new Exception("[DAO]��Ȩֱ�Ӷ����ݿ������");
		
		File dbp = new File(dbPath);
		if(!dbp.exists())
			if(!dbp.mkdirs())
				throw new Exception("[DAO]��SD������Ŀ¼ʧ�ܣ�");
		File dbf = new File(dbPath + dbName);
		if(!dbf.exists()){
			Boolean f = dbf.createNewFile();
			if(!f)
				throw new Exception("[DAO]�������ݿ��ļ�ʧ�ܣ�");
		}
		try {
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.execSQL(sql);
			sqliteDb.close();
		} catch (Exception e) {
			// TODO: handle exception
			if(sqliteDb !=null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]" + e.getMessage());
		}
	}
	
	//ִ�и�����SQL��䣬�Ƚ�����Ҫ���
	public  void Excute(String Sql,String passwd) throws Exception{
		if(!"Admin".equals(passwd)){
			throw new Exception("[DAO]�Ƿ��������ݱ�");
		}
		try{
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.setLocale(Locale.getDefault());
//			sqliteDb.setLockingEnabled(true);
			//ִ��SQL�����������
			sqliteDb.execSQL(Sql);
			
			if(sqliteDb.isOpen())
				sqliteDb.close();
		}catch (Exception e) {
			// TODO: handle exception
			if(sqliteDb !=null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]" + e.getMessage());
		}
	}
	
	
	//����xml�������ִ��SQL���
	public boolean Excute() throws Exception {
		if(xMLStr == null || xMLStr=="")
			throw new Exception("[DAO]û�пɽ����Ĳ����ַ�����");
		
		//����XML����ò������ͼ�����
		parseXML();
		
		//���������
		if(op == OperationType.CREATETABLE){
			return true;
		}
		//��ȡ���ݲ���
		else if(op == OperationType.SELECT){
			//��������Ƿ�Ϊ��
			if(table.getColSize() == 0)
				throw new Exception("[DAO]û��ָ�����������ݱ������ƣ�");
			read();
			return true;
		}
		//����в���
		else if(op == OperationType.ADDROW){
			if(table.getColSize() ==0)
				throw new Exception("[DAO]û��ָ�����ݱ������ƣ�");
			if(table.getRowSize()==0)
				throw new Exception("[DAO]û�ж�ȡ������ӵ����ݣ�");
			write();
			return true;
		}
		//ɾ���в���
		else if(op == OperationType.DELROW){
			delete();
			return true;
		}
		//�����в���
		else if(op == OperationType.UPDATE){
			update();
			return true;
		}
		else
			return false;
	}
	
	//��ȡ��¼
	private void read() throws Exception{
		Cursor cursor = null;
		String sql = "select ";
		//����sql��ѯ���
		Col[] cols = table.getCols();
		
		String sqlCount ="select " + cols[0].getName() + " from " + table.TName;
		for(int i=0;i<cols.length-1;i++)
			sql += cols[i].getName() + ",";
		sql += cols[cols.length-1].getName();
		sql += " from " + table.TName;
		
		if(condition != null){
			sql += " where " + condition;
			sqlCount += " where " + condition + ";";
		}
		//��ָ��ҳ����ȡ��¼
		if(table.getPageSize()>0)
			sql += " limit " + String.valueOf(table.getPageSize()) + " offset " + 
		String.valueOf(table.getPageNo()*table.getPageSize());
		sql +=";";
		try{
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.setLocale(Locale.getDefault());
//			sqliteDb.setLockingEnabled(false);
			//�ȼ���ҳ��
			if(table.getPageSize()>0){
				cursor = sqliteDb.rawQuery(sqlCount,null);
				int Count = cursor.getCount();
				//��û�в�ѯ����¼�����˳�
				if(Count==0){
					table.pageCount = 0;
					cursor.close();
					sqliteDb.close();
					return;
				}
				else{
					if(Count % table.getPageSize()==0)
						table.pageCount = Count / table.getPageSize();
					else 
						table.pageCount = Count / table.getPageSize()+1;
					cursor.close();
				}
			}
			//��ȡ��¼
			cursor= sqliteDb.rawQuery(sql,null);
			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToPosition(i);
				Row row = table.newRow();
				for(int j=0;j<cursor.getColumnCount();j++){
					if(!cursor.isNull(i));
					row.setRow(j,cursor.getString(j));
				}
				table.addRow(row);
			}
			cursor.close();
			sqliteDb.close();
		}catch (Exception e) {
			// TODO: handle exception
			if(cursor != null && !cursor.isClosed())
				cursor.close();
			if(sqliteDb != null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]" + e.getMessage());
		}
	}
	//��Ӽ�¼
	public void write() throws Exception{
		sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
		sqliteDb.setLocale(Locale.getDefault());
//		sqliteDb.setLockingEnabled(true);
		
		Boolean hasTransaction = false;
		try{
			String sql="insert into " +table.TName + "(";
			Col[] cols = table.getCols();
			
			if(table.getRowSize()>0){
				sqliteDb.beginTransaction();
				hasTransaction = true;
			}
			//˳�β��������¼
			for(int i=0;i<table.getRowSize();i++){
				Row row = table.getRow(i);
				String sql_1 = sql;
				String sql_2 = " values (";
				for(int j=0;j<cols.length;j++){
					sql_1 +=cols[j].getName() + ",";
					if("null".equalsIgnoreCase(row.getString(j)))
							sql_2 += "null,";
					else {
						DataType stype= cols[j].getType();
						if(stype == DataType.NUMBER)//ֱ�ӱ߽� 
							sql_2 += row.getString(j) + ",";
						else if(stype == DataType.BOOLEAN){ 
							//��true��flaseת��Ϊ0��1�洢
							if("true".equalsIgnoreCase(row.getString(j)))
								sql_2 += "1,";
							else if("false".equalsIgnoreCase(row.getString(j)))
								sql_2 +="0,";
							else 
								sql_2 += row.getString(j) + ",";
						}
						else if(stype == DataType.DATE)
							sql_2 += " datetime('" + row.getString(j) + "'),";
						else 
							sql_2 +="'" + row.getString(j) + "',";
					}
				}
				//ȥ������","����ϳɲ����ѯ����
				sql_1 = sql_1.substring(0,sql_1.length()-1) + ")";
				sql_2 = sql_2.substring(0,sql_2.length()-1) + ");";
				sql_1 +=  sql_2;
				Log.i("KBMP",sql_1);
				sqliteDb.execSQL(sql_1);
			}
			
			if(hasTransaction){
				sqliteDb.setTransactionSuccessful();
				sqliteDb.endTransaction();
			}
//			
		}catch (Exception e) {
			// TODO: handle exception
			if(hasTransaction)//��������������
				sqliteDb.endTransaction();
			
			if(sqliteDb!=null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]"+ e.getMessage());
		}
	}
	//��ָ������ɾ����¼
	public void delete() throws Exception{
		String sql = "delete from ";
		sql += table.TName;
		if(condition != null)
			sql += " where " + condition +";";
		else 
			sql += ";";
		try {
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.setLocale(Locale.getDefault());
//			sqliteDb.setLockingEnabled(true);
			
			Log.i("KBMP",sql);
			
			sqliteDb.execSQL(sql);
			sqliteDb.close();
		} catch (Exception e) {
			// TODO: handle exception
			if(sqliteDb != null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]" + e.getMessage());
		}
	}
	//��ָ������ɾ����¼
	public void update() throws Exception{
			if(table.getRowSize() <= 0)
				throw new Exception("[DAO]û�пɸ��µ����ݣ�");
			if(condition == null)
				throw new Exception("[DAO]û��ָ������������");
			//Construct update sql string
			String sql = "update " + table.TName + " set ";
			try{
				Col[] cols = table.getCols();
				Row row = table.getRow(0);
				for(int i=0; i<cols.length;i++){
					sql += cols[i].getName() + "=";
					if("null".equalsIgnoreCase(row.getString(i)))
							sql  += "null,";
					else {
						DataType stype= cols[i].getType();
						if(stype == DataType.NUMBER)//ֱ�������ַ��� 
							sql += row.getString(i) + ",";
						else if(stype == DataType.BOOLEAN){ 
							//��true��flaseת��Ϊ0��1�洢
							if("true".equalsIgnoreCase(row.getString(i)))
								sql += "1,";
							else if("false".equalsIgnoreCase(row.getString(i)))
								sql += "0,";
							else 
								sql += row.getString(i) + ",";
						}
						else if(stype == DataType.DATE)
							sql += "datetime('" + row.getString(i) + "'),";
						else //Text��δָ������
							sql += "'" + row.getString(i) + "',";
					}
				}
				//ȥ������","
				sql = sql.substring(0,sql.length()-1);
				sql += " where " + condition + ";";
				Log.i("KBMP",sql);
				
				sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
				sqliteDb.setLocale(Locale.getDefault());
//				sqliteDb.setLockingEnabled(true);
				
				sqliteDb.execSQL(sql);
				sqliteDb.close();
				}catch (Exception e) {
					// TODO: handle exception
					if(sqliteDb!=null && sqliteDb.isOpen())
						sqliteDb.close();
					throw new Exception("[DAO]"+ e.getMessage());
			}
		}
	//����ǰ��Ҫ����xml
	public void SetXML(String xml) {
		xMLStr = xml;
	}
	private   void parseXML() throws Exception{
		//��ʼ������
		table = new Table();
		op = null;
		condition = null;
		
		XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);//֧�������ռ�
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xMLStr));
		int evtType = parser.getEventType();
		Row newRow = null; //�����м�¼
		int idx = 0;
		while (evtType != XmlPullParser.END_DOCUMENT) {
			//��ȡ��ʼ���
			if(evtType == XmlPullParser.START_TAG){
				String tag = parser.getName();
				//�õ�����,name����Ϊ�����
				if("Table".equalsIgnoreCase(tag)){
					String tname = parser.getAttributeValue(null, "name");
					table.setTName(tname);
				}
				//�õ���������
				else if("Operation".equalsIgnoreCase(tag)){
					String pgsize = parser.getAttributeValue(null,"pagesize");
					String pgcount = parser.getAttributeValue(null,"pageno");
					
					String opStr = parser.nextText();
					if(OperationType.CREATETABLE.toString().equalsIgnoreCase(opStr))
						op = OperationType.CREATETABLE;
					else if("Select".equalsIgnoreCase(opStr)){
						op = OperationType.SELECT;
						
						if( pgsize != null && pgcount!=null){
							table.setPageSize(Integer.parseInt(pgsize));
							table.setPageNo(Integer.parseInt(pgcount));
						}
					}
					else if(OperationType.ADDROW.toString().equalsIgnoreCase(opStr))
						op = OperationType.ADDROW;
					else if(OperationType.DELROW.toString().equalsIgnoreCase(opStr))
						op = OperationType.DELROW;
					else if(OperationType.UPDATE.toString().equalsIgnoreCase(opStr))
						op = OperationType.UPDATE;
					else {
						throw new Exception("[DAO]��ȡ�Ĳ������ʹ���"  + opStr);
					}
				}
				// �õ���ѯ����
				else if(tag.equalsIgnoreCase("Condition")){
					condition = parser.nextText();
				}
				//�õ�������
				else if(tag.equalsIgnoreCase("col")){
					String colType = parser.getAttributeValue(null,"type");
					if(colType==null || colType=="")
						colType="text";
					DataType dt = null;
					String colName = parser.nextText();
					//��ȡ�е���������
					String numType="number short byte int long float double";
					
					if(numType.contains(colType.toLowerCase()))
						dt = DataType.NUMBER;
					else if("string text".contains(colType.toLowerCase()))
						dt = DataType.TEXT;
					else if("bool boolean".contains(colType.toLowerCase()))
						dt = DataType.BOOLEAN;
					else if("date calendar gregoriancalendar".contains(colType.toLowerCase()))
						dt =DataType.DATE;
					else 
						dt = DataType.OBJECT;
					table.setCol(colName,dt);
				}
				
				//�õ��м�¼:����һ����
				if("row".equalsIgnoreCase(tag.toLowerCase())){
					newRow = table.newRow();
					idx = 0;
				}
				//������������
				if("td".equalsIgnoreCase(tag.toLowerCase())){
					newRow.setRow(idx,parser.nextText());
					idx++;
				}
			}
			else if(evtType == XmlPullParser.END_TAG && "row".equalsIgnoreCase(parser.getName())){
				if(newRow != null){
					table.addRow(newRow);
					newRow = null;
				}
			}
			evtType = parser.next();
		}//end while
	}

}
