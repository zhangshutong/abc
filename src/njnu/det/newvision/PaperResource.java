package njnu.det.newvision;

import java.io.StringWriter;
import java.sql.Date;

import njnu.det.newvision.DAO.OperationType;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class PaperResource {
	@Get
	public String toXML(String id,String title, String keywords, String author,
			               String date, String content, String editdate,String accessory_id,String synctime) {			
		StringWriter sw = new StringWriter();
		try {						
		    XmlSerializer xml = Xml.newSerializer();		
		    xml.setOutput(sw);
		    xml.startDocument("UTF-8",true);
		    //Begin Root node
			xml.startTag("","Table");
			xml.attribute("","name","writing");
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
	@Put
	public void wirteXMLLocal(String id,String title, String keywords, String author,
            String date, String content, String editdate,String accessory_id,String synctime){
		DAO dao;
		try {
			dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());				
			String xml=toXML(id,title,keywords,author,date,content,editdate, accessory_id,synctime);
			dao.SetXML(xml);
			dao.Excute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}										
	}
	
	public void wirteXMLRemote(String xml){
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
