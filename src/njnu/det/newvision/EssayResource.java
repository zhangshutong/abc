package njnu.det.newvision;
import java.io.StringWriter;

import njnu.det.newvision.Table.Col;
import njnu.det.newvision.Table.Row;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class EssayResource extends ServerResource{

	String id,title,keywords,author,date,content,editdate,accessory_id,synctime;
	@Get
	
	public String toXML() {   //update/ insert 都是插入数据库，只是condituion 和 operation 不同
		StringWriter sw = new StringWriter();
		try {						
		    XmlSerializer xml = Xml.newSerializer();		
		    xml.setOutput(sw);
		    xml.startDocument("UTF-8",true);
		    //Begin Root node
			xml.startTag("","Table");
			xml.attribute("","name","essay");
			//Operation Section
			if(NV_Host.getOperation() != "UPDATE"){
				xml.startTag("","Operation");
				xml.text(DAO.OperationType.ADDROW.toString());
				xml.endTag("","Operation");	
				}
			
			else{
				xml.startTag("","Operation");
				xml.text(DAO.OperationType.UPDATE.toString());
				xml.endTag("","Operation");
				xml.startTag("","Condition");
				xml.text(" Title='" + title +"' and Date='" + date + "'");
				xml.endTag("","Condition");
				}			
						
			//Table header section
			
			xml.startTag("","Theader");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("ID");
			xml.endTag("","Col");
					
	  		xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Title");
			xml.endTag("","Col");

			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Keywords");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Author");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.DATE.toString());
			xml.text("Date");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Content");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.DATE.toString());
			xml.text("EditDate");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Accessory_ID");
			xml.endTag("","Col");
			
			xml.startTag("","Col");
			xml.attribute("","type",Table.DataType.TEXT.toString());
			xml.text("Synctime");
			xml.endTag("","Col");
			
			xml.endTag("","Theader");
			//end table header
			
			//Table body Section is table body				
			xml.startTag("","Tbody");		
			xml.startTag("","Row");
			xml.startTag("","Td");
			xml.text(id);
			xml.endTag("","Td");
					
			xml.startTag("","Td");
			xml.text(title);
			xml.endTag("","Td");

			xml.startTag("","Td");
			xml.text(keywords);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(author);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(date);
			xml.endTag("","Td");
							
			xml.startTag("","Td");
			xml.text(content);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(editdate);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(accessory_id);
			xml.endTag("","Td");
			
			xml.startTag("","Td");
			xml.text(synctime);
			xml.endTag("","Td");
			
			xml.endTag("","Row");	
			xml.endTag("","Tbody");
			xml.endTag("","Table");
	        xml.flush();
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return sw.toString();
	}
	
	
	
	public Table getTable() throws Exception {	//String id,String title, String keywords, String author,String date,
		  //String content, String editdate,String accessory_id,String synctime		
		
		DAO dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());
		//NV_Host 拿值
		User user = NV_Host.getLocalUser();
    	author = user.NikedName;
		//构造xml查询字符
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		//Begin Root node
		xml.startTag("","Table");
		xml.attribute("","name","essay");
		//Operation Section
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.SELECT.toString());
		xml.endTag("","Operation");
		xml.startTag("", "Condition");
		xml.text("Author='" + author +"'");
		xml.endTag("", "Condition");
				
		xml.startTag("", "Theader");
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("ID");
		xml.endTag("", "col");
				
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.TEXT.toString());
		xml.text("Title");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.TEXT.toString());
		xml.text("Keywords");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.TEXT.toString());
		xml.text("Author");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.DATE.toString());
		xml.text("Date");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.TEXT.toString());
		xml.text("Content");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.DATE.toString());
		xml.text("EditDate");
		xml.endTag("", "col");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Accessory_ID");
		xml.endTag("", "col");
				
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.DATE.toString());
		xml.text("Synctime");
		xml.endTag("", "col");
		
		xml.endTag("", "Theader");
		xml.endTag("", "Table");
		xml.flush();
		dao.SetXML(sw.toString());
		dao.Excute();
		return dao.table;
	}	
		
	@Put
	public void wirteXML(String xml){
		DAO dao;
		try {
			dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());							
			dao.SetXML(xml);
			dao.Excute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}										
	}
	
	
		
}
