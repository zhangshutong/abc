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
	//操作类型
	public	enum OperationType{
		CREATETABLE,SELECT,ADDROW,DELROW,UPDATE;
	}
	
	public DAO(String dbPath,String dbName) throws Exception{
		this.dbPath = dbPath; 
		this.dbName = dbName;
	}
	
	public void CreateTable(String sql,String passwd) throws Exception{
		if(!"Admin".equals(passwd))
			throw new Exception("[DAO]无权直接对数据库操作！");
		
		File dbp = new File(dbPath);
		if(!dbp.exists())
			if(!dbp.mkdirs())
				throw new Exception("[DAO]在SD卡创建目录失败！");
		File dbf = new File(dbPath + dbName);
		if(!dbf.exists()){
			Boolean f = dbf.createNewFile();
			if(!f)
				throw new Exception("[DAO]创建数据库文件失败！");
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
	
	//执行给定的SQL语句，比较灵活，需要口令！
	public  void Excute(String Sql,String passwd) throws Exception{
		if(!"Admin".equals(passwd)){
			throw new Exception("[DAO]非法创建数据表！");
		}
		try{
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.setLocale(Locale.getDefault());
//			sqliteDb.setLockingEnabled(true);
			//执行SQL创建表或索引
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
	
	
	//根据xml解析结果执行SQL语句
	public boolean Excute() throws Exception {
		if(xMLStr == null || xMLStr=="")
			throw new Exception("[DAO]没有可解析的操作字符串！");
		
		//解析XML，获得操作类型及数据
		parseXML();
		
		//创建表操作
		if(op == OperationType.CREATETABLE){
			return true;
		}
		//读取数据操作
		else if(op == OperationType.SELECT){
			//检查列名是否为空
			if(table.getColSize() == 0)
				throw new Exception("[DAO]没有指定检索的数据表列名称！");
			read();
			return true;
		}
		//添加行操作
		else if(op == OperationType.ADDROW){
			if(table.getColSize() ==0)
				throw new Exception("[DAO]没有指定数据表列名称！");
			if(table.getRowSize()==0)
				throw new Exception("[DAO]没有读取到可添加的数据！");
			write();
			return true;
		}
		//删除行操作
		else if(op == OperationType.DELROW){
			delete();
			return true;
		}
		//更新行操作
		else if(op == OperationType.UPDATE){
			update();
			return true;
		}
		else
			return false;
	}
	
	//读取记录
	private void read() throws Exception{
		Cursor cursor = null;
		String sql = "select ";
		//构造sql查询语句
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
		//从指定页处读取记录
		if(table.getPageSize()>0)
			sql += " limit " + String.valueOf(table.getPageSize()) + " offset " + 
		String.valueOf(table.getPageNo()*table.getPageSize());
		sql +=";";
		try{
			sqliteDb = SQLiteDatabase.openOrCreateDatabase(dbPath+dbName,null);
			sqliteDb.setLocale(Locale.getDefault());
//			sqliteDb.setLockingEnabled(false);
			//先计算页数
			if(table.getPageSize()>0){
				cursor = sqliteDb.rawQuery(sqlCount,null);
				int Count = cursor.getCount();
				//若没有查询到记录，则退出
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
			//读取记录
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
	//添加记录
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
			//顺次插入各条记录
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
						if(stype == DataType.NUMBER)//直接边接 
							sql_2 += row.getString(j) + ",";
						else if(stype == DataType.BOOLEAN){ 
							//将true和flase转化为0和1存储
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
				//去掉最后的","并组合成插入查询语名
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
			if(hasTransaction)//结束开启的事务
				sqliteDb.endTransaction();
			
			if(sqliteDb!=null && sqliteDb.isOpen())
				sqliteDb.close();
			throw new Exception("[DAO]"+ e.getMessage());
		}
	}
	//按指定条件删除记录
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
	//按指定条件删除记录
	public void update() throws Exception{
			if(table.getRowSize() <= 0)
				throw new Exception("[DAO]没有可更新的数据！");
			if(condition == null)
				throw new Exception("[DAO]没有指定更新条件！");
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
						if(stype == DataType.NUMBER)//直接连接字符串 
							sql += row.getString(i) + ",";
						else if(stype == DataType.BOOLEAN){ 
							//将true和flase转化为0和1存储
							if("true".equalsIgnoreCase(row.getString(i)))
								sql += "1,";
							else if("false".equalsIgnoreCase(row.getString(i)))
								sql += "0,";
							else 
								sql += row.getString(i) + ",";
						}
						else if(stype == DataType.DATE)
							sql += "datetime('" + row.getString(i) + "'),";
						else //Text或未指定类型
							sql += "'" + row.getString(i) + "',";
					}
				}
				//去掉最后的","
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
	//解析前需要设置xml
	public void SetXML(String xml) {
		xMLStr = xml;
	}
	private   void parseXML() throws Exception{
		//初始化变量
		table = new Table();
		op = null;
		condition = null;
		
		XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);//支持命名空间
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xMLStr));
		int evtType = parser.getEventType();
		Row newRow = null; //存在行记录
		int idx = 0;
		while (evtType != XmlPullParser.END_DOCUMENT) {
			//获取开始标记
			if(evtType == XmlPullParser.START_TAG){
				String tag = parser.getName();
				//得到表标记,name属性为其表名
				if("Table".equalsIgnoreCase(tag)){
					String tname = parser.getAttributeValue(null, "name");
					table.setTName(tname);
				}
				//得到操作类型
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
						throw new Exception("[DAO]读取的操作类型错误："  + opStr);
					}
				}
				// 得到查询条件
				else if(tag.equalsIgnoreCase("Condition")){
					condition = parser.nextText();
				}
				//得到列数据
				else if(tag.equalsIgnoreCase("col")){
					String colType = parser.getAttributeValue(null,"type");
					if(colType==null || colType=="")
						colType="text";
					DataType dt = null;
					String colName = parser.nextText();
					//获取列的数据类型
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
				
				//得到行记录:产生一新行
				if("row".equalsIgnoreCase(tag.toLowerCase())){
					newRow = table.newRow();
					idx = 0;
				}
				//设置新行数据
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
