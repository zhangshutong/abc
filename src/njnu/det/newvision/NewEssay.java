package njnu.det.newvision;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.sql.Date;

import org.restlet.resource.ClientResource;
import org.xmlpull.v1.XmlSerializer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
public class NewEssay extends Activity{	
	String id,title,keywords,author,date,content,editdate,accessory_id,synctime;
	private static final String ADDROW = null;
	ActionBar actionBar;
	private EditText titleEditText;
	private EditText keywordsEditText;
	private EditText contentEditText;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
	    setContentView(R.layout.writingdiary_layout); 
	    titleEditText = (EditText)findViewById(R.id.editText1);
	    keywordsEditText = (EditText)findViewById(R.id.EditText01);
	    contentEditText = (EditText)findViewById(R.id.editText2);    
	    actionBar = getActionBar();
	    actionBar.show();					
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuItem add = menu.add(0,0,0,"保存");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);		
		MenuItem del = menu.add(0,1,1,"放弃");
		del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 return true;	
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return MenuChoice(item);
	}
	private boolean MenuChoice(MenuItem item)
	{		
		switch(item.getItemId()){		
		case 0:	
		{
			 title = titleEditText.getText().toString();
             keywords = keywordsEditText.getText().toString();
             content = contentEditText.getText().toString();
            User user = NV_Host.getLocalUser();
        	 author = user.NikedName;
        	
        	String operation ="ADDROW";
        	NV_Host.setOperation(operation);
        	
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
             date = sDateFormat.format(new java.util.Date());
             synctime = "0";
             id = author+date;
             editdate = date;
             accessory_id = "0";
			try {
				InsertWriting();
			    Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
			
		}
		case 1:
		{
			titleEditText.setText("");
			keywordsEditText.setText("");
			contentEditText.setText("");			
			return true;
		}
		 
		}
		return false;
	}

	public void InsertWriting() throws Exception{		
		if(NV_Host.isLocal){						
			EssayResource er = new EssayResource();	
			er.id=id;
			er.author=author;
			er.accessory_id=accessory_id;
			er.content = content;
			er.editdate=editdate;
			er.date  = date;
			er.keywords=keywords;
			er.synctime=synctime;
			er.title=title;
			
			String xml=er.toXML();
			er.wirteXML(xml);
				
		}
		else{
			
		}

		
	}	                  
}
