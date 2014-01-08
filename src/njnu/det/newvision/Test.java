package njnu.det.newvision;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.xmlpull.v1.XmlSerializer;

import njnu.det.newvision.DAO;
import njnu.det.newvision.Table;
import njnu.det.newvision.Utility;
import njnu.det.newvision.WIFIState;
import njnu.det.newvision.Table.Col;
import njnu.det.newvision.Table.Row;
import njnu.det.newvision.WIFIState.OnNetworkAvailableListener;
import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

public class Test extends AndroidTestCase implements OnNetworkAvailableListener{

	
	public void testNetworkState() throws Exception{
		WIFIState ws = new WIFIState();
		Context context = Utility.getAppContext();
		//以下代码自动获取网络状态，并通过以下的OnNetworkAvailable()等两个函数得到通知
		ws.bind(context);
		ws.detectWiFiState(context);
		String s = ws.NetName + " " + ws.Ntstate.toString() + " "+ ws.IP;
		Log.i("KBMP",s);
		ws.setNetworkAvailableListener(this);
		
		Thread.sleep(98000);
		ws.unbind(context);
//		String[] ips = ws.getNeighborHosts(context);
//		if(ips==null)
//			return;
//		for(int i=0;i<ips.length;i++)
//			Log.i("KBMP",ips[i]);
		
	}
	@Override
	public void OnNetworkAvailable() {
		// TODO Auto-generated method stub
		Log.i("KBMP","Wi-Fi Available!");
	}

	@Override
	public void OnNetworkUnavailable() {
		// TODO Auto-generated method stub
		Log.i("KBMP","Wi-Fi Unvailable!");
	}
	
	//测试Table类
	public void testTable() throws Exception{
		Table tb = new Table();
		tb.setTName("Student");
		
		tb.setCol("ID",Table.DataType.NUMBER);
		tb.setCol("Name",Table.DataType.TEXT);
		tb.setCol("Sex",Table.DataType.BOOLEAN);
		tb.setCol("Birthday",Table.DataType.DATE);
		
		Row r = tb.newRow();
		r.checkType(false);
		r.setRow(0,1);
		r.setRow(1,"John");
		r.setRow(2,1);
		Calendar cl = Calendar.getInstance();
		cl.set(1985,10,11);
		r.setRow(3,cl);
		tb.addRow(r);
		
		r = tb.newRow();
		r.checkType(true);
		r.setRow(0,2);
		r.setRow(1,"Smith");
		r.setRow(2,false);
		cl = Calendar.getInstance();
		cl.set(1981,11,20);
		r.setRow(3,cl.getTime());
		tb.addRow(r);
		
		for(int i = 0;i<tb.getRowSize();i++){
			Row row = tb.getRow(i);
			String s ="";
			for(int j=0;j<tb.getColSize();j++){
				s = row.getNumber(0) + "  " + row.getString(1) + " " + row.getBoolean(2) + "  ";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				s += df.format(row.getDate(3));
			}
			Log.i("KBMP",s);
		}
	}
	
	//测试时DAO的Excute方法
	public void testSelect() throws Exception{
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		String xml ="<?xml version='1.0' encoding='UTF-8'?>"; 
		xml +="<table name='user'>\n";
		xml += "<operation pagesize='0' pageno='0'>"+DAO.OperationType.SELECT.toString()+"</operation>\n";
		//xml +="<condition>(id &gt;2 and id &lt;10) and name='Smith2'</condition>\n";
		xml +="<theader>\n";
		xml +="<col type='int'>ID</col>\n";
		xml +="<col type='string'>name</col>\n";
		xml +="<col type='bool'>Sex</col>\n";
		xml +="<col type='date'>Birthday</col>\n";
		xml +="</theader>\n";

		xml +="</table>";
		//Log.i("KBMP",xml);
		
		dao.SetXML(xml);
		try{
			dao.Excute();
			Table tb = dao.table;
			Log.i("KBMP","page:" + tb.getPageNo() + "/" + tb.getPageCount());
			Col[] cols = tb.getCols();
			for(int i=0 ; i<cols.length;i++){
				Log.i("KBMP",cols[i].getName() + " " + cols[i].getType());
			}
			
			for(int i =0; i<tb.getRowSize();i++){
				Row row = tb.getRow(i);
				String str = "";
				str += row.getInt(0) + " ";
				str += row.getString(1) + " ";
				str += row.getBoolean(2) + " ";
				str+= row.getString(3) + " ";
				Log.i("KBMP", str);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.i("KBMP","[TEST]Error:" + e.getLocalizedMessage());
		}
	}
	public void testInsert() throws Exception{
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		//Begin Root node
		xml.startTag("","Table");
		xml.attribute("","name","user");
		//Operation Section
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.ADDROW.toString());
		xml.endTag("","Operation");
		
		//Table header section
		xml.startTag("","Theader");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.NUMBER.toString());
		xml.text("ID");
		xml.endTag("","Col");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Name");
		xml.endTag("","Col");

		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.BOOLEAN.toString());
		xml.text("Sex");
		xml.endTag("","Col");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.DATE.toString());
		xml.text("Birthday");
		xml.endTag("","Col");
		
		xml.endTag("","Theader");
		//end table header
		
		//Table body Section is table body
		xml.startTag("","Tbody");
		for(int i=10;i<20;){
			xml.startTag("","Row");
			
			xml.startTag("","Td");
			xml.text(String.valueOf(i++));
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text("Mary" +i);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(String.valueOf(i%2==0?0:1));
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text("1998-05-02");
			xml.endTag("","Td");
			
			xml.endTag("","Row");
			
			xml.startTag("","Row");
			xml.startTag("","Td");
			xml.text(String.valueOf(i++));
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text("Hellen" +i);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(String.valueOf(i%2==0?0:1));
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text("1998-10-09");
			xml.endTag("","Td");
			
			xml.endTag("","Row");
		}
		
		xml.endTag("","Tbody");
		//End table body
		
		//End Root node
		xml.endTag("","Table");
		xml.flush();
		//xml.endDocument();
		
		
		Log.i("KBMP",sw.toString());
		
		dao.SetXML(sw.toString());
		try{
			dao.Excute();
			String s = "";
			for(int ii=0;ii<dao.table.getColSize();ii++){
				s += dao.table.getCols()[ii].getName() + "  " + dao.table.getCols()[ii].getType() + " ";
			}
			Log.i("KBMP",s);
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.i("KBMP","[TEST]Error:" + e.getLocalizedMessage());
		}
	}
	
	public void testUpdate() throws Exception{
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		xml.startTag("","Table");
		xml.attribute("","name","user");
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.UPDATE.toString());
		xml.endTag("","Operation");
		xml.startTag("","Condition");
		xml.text("id=31");
		xml.endTag("","Condition");
		
		//Table header section
		xml.startTag("","Theader");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.NUMBER.toString());
		xml.text("ID");
		xml.endTag("","Col");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Name");
		xml.endTag("","Col");

		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.BOOLEAN.toString());
		xml.text("Sex");
		xml.endTag("","Col");
		
		xml.startTag("","Col");
		xml.attribute("","type",Table.DataType.DATE.toString());
		xml.text("Birthday");
		xml.endTag("","Col");
		
		xml.endTag("","Theader");
		//end table header
		
		//Table body Section is table body
		xml.startTag("","Tbody");
		xml.startTag("","Row");
		
		xml.startTag("","Td");
		xml.text("101");
		xml.endTag("","Td");
		
		xml.startTag("","Td");
		xml.text("Mary-100");
		xml.endTag("","Td");
		
		xml.startTag("","Td");
		xml.text("true");
		xml.endTag("","Td");
		
		xml.startTag("","Td");
		xml.text("1998-05-02");
		xml.endTag("","Td");
		
		xml.endTag("","Row");
		xml.endTag("","Tbody");
		xml.endTag("","Table");
		xml.flush();
		
		Log.i("KBMP",sw.toString());
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		dao.SetXML(sw.toString());
		dao.Excute();
		Log.i("KBMP","更新成功！");

	}
	public void testDelete() throws Exception{
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		xml.startTag("","Table");
		xml.attribute("","name","user");
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.DELROW.toString());
		xml.endTag("","Operation");
		
		xml.startTag("","Condition");
		xml.text("id=30");
		xml.endTag("","Condition");
		
		xml.endTag("","Table");
		xml.flush();
		
		Log.i("KBMP",sw.toString());
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		dao.SetXML(sw.toString());
		dao.Excute();
	}
	public void  test_CreateTableOrIndex() throws Exception{
//		String sql = "Drop table user;";
		String sql = "CREATE TABLE IF NOT EXISTS user (" +
				"ID INTEGER PRIMARY KEY UNIQUE, " + 
				" Name TEXT,Sex INTEGER,Birthday Date);";
		sql +="CREATE UNIQUE INDEX IF NOT EXISTS idIndex ON user (id);";
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		dao.CreateTable(sql,"Admin");
		Log.i("KBMP","创建成功！");
	}
	public void test_DelTableOrIndex() throws Exception{
		String sql ="Drop Table IF EXISTS user; ";
		String dbPath = Utility.getParameter("DatabasePath");
		String dbName = Utility.getParameter("DatabaseName");
		DAO dao = new DAO(dbPath,dbName);
		dao.Excute(sql,"Admin");
	}
	public void testReg() throws Exception{
		String reg = Utility.getSerialNo();
		Log.i("KBMP","RegCode=" + reg);
		Log.i("KBMP","Decode=" + Utility.getRegCode(reg));
		Log.i("KBMP","MD5=" + Utility.getMD5("Just so so."));
	}
	
	public void testParam() throws Exception{
		Utility.setParameter("DatabaseName","KBMP.db");
		Utility.setParameter("DatabasePath",Environment.getExternalStorageDirectory().getAbsolutePath()+"/KBMP/");
		Utility.setParameter("IP","192.168.0.123");
		String s = Utility.getParameter("DatabasePath") + Utility.getParameter("DatabaseName");
		Log.i("KBMP","DatabaseName=" + s);
	}
	public void textSysInfo() throws Exception{
		 if(Utility.existSD()){
			 Log.i("KBMP","SD Card exists.");
			 Log.i("KBMP","SD Free Size= " + Utility.getSDFreeSize());
		 }
		 else 
			Log.i("KBMP","SD Card not exists.");
		Log.i("KBMP","DeviceID=" + Utility.getDeviceID());
		Log.i("KBMP","Free Memory=" + Utility.getFreeMemSize());
		Log.i("KBMP","MemorySize=" + Utility.getMemorySize());
		
	}
	
	public void testDelFile() throws Exception{
		Utility.delFile("/data/data/njnu.det.kbmp/databases/KBMP.db-journal");
		Log.i("KBMP","Delete files OK!");
	}

	
}
