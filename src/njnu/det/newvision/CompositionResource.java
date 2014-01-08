package njnu.det.newvision;
import java.io.StringWriter;
import njnu.det.newvision.Table.Col;
import njnu.det.newvision.Table.Row;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class CompositionResource extends ServerResource{
	@Get
	public Table getXML(){
		Table tb = null;		
		try {
			tb = readWriting();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("NewVision","Error:" + e.getMessage());
		}
		return tb;
	}
	

	public Table readWriting() throws Exception{
		DAO dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());
		//NV_Host 拿值
		User user = NV_Host.getLocalUser();
    	String author = user.NikedName;
		//构造xml查询字符
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		//Begin Root node
		xml.startTag("","Table");
		xml.attribute("","name","writing");
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
		
}
