package njnu.det.newvision;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowComposition extends Activity {
	ActionBar actionBar;
	EditText showEditText;
	TextView dateTextView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_composition_layout);
		MyApplication.getInstance().addActivity(this);
		actionBar = getActionBar();
		actionBar.show();
		save();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日     HH：mm：ss");
		Date curdate = new Date(System.currentTimeMillis());
		String dateString = dFormat.format(curdate);
		dateTextView = (TextView)findViewById(R.id.showCompositonDate);
		dateTextView.setText(dateString);
	}
	private boolean save() {
		TextView tvTitle   =  (TextView) findViewById(R.id.showCompsitionTitle);
		TextView tvDate    =  (TextView) findViewById(R.id.showCompositonDate);
		EditText edContent = (EditText) findViewById(R.id.showCompositionContent);
		
		String sTitle   = getIntent().getStringExtra("sTitle");
		String sDate    = getIntent().getStringExtra("sDate");
		String sContent = getIntent().getStringExtra("sContent");
		
		
				 
		tvTitle.setText(sTitle);
		tvDate.setText(sDate);
		edContent.setText(sContent);
		
		
		
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		//showEditText=(EditText)findViewById(R.id.showEditText);
		super.onCreateOptionsMenu(menu);
		final MenuItem modify = menu.add(0,1,1,"修改");
		modify.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		final MenuItem save = menu.add(0,1,1,"保存");
		save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		save.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {  
		         
		        case 1:   
					
		            {	
		            	TextView tvTitle   =  (TextView) findViewById(R.id.showCompsitionTitle);
		        		TextView tvDate    =  (TextView) findViewById(R.id.showCompositonDate);
		        		EditText edContent = (EditText) findViewById(R.id.showCompositionContent);
		        		
		        		String sTitle   = getIntent().getStringExtra("sTitle");
		        		String sDate    = getIntent().getStringExtra("sDate");
		        		String Content = getIntent().getStringExtra("sContent");
		        		String sContent=edContent.getText().toString().trim();
		        		String sId = getIntent().getStringExtra("sId");
		        		String sKeywords = getIntent().getStringExtra("sKeywords");		
		        		String sAccessoryId = getIntent().getStringExtra("sAccessoryId");
		        		String sSynctime = getIntent().getStringExtra("sSynctime");
		            	
			        	User user = NV_Host.getLocalUser();
			        	String author = user.NikedName;
			        	String operation ="UPDATE";
			        	NV_Host.setOperation(operation);
			        	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
			            String sEditDate = sDateFormat.format(new java.util.Date()); 
			            
			            /*String sId="a";
			            String sKeywords="a";
			            String sAccessoryId="a";
			            String sSynctime="a";*/
			        	try {
							UpdatetWriting(sId,sTitle,sKeywords,author,sDate,sContent,sEditDate, sAccessoryId,sSynctime);
							//Toast.makeText(this, "You clicked on 保存", Toast.LENGTH_SHORT).show();	
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	
		            }
		            Toast.makeText(getBaseContext(), "保存成功", Toast.LENGTH_SHORT).show();
		           
		            }
				
	
				return true;
				
			}
		});
		
		
		modify.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				
				switch (item.getItemId()) {  
		         
		        case 1: 
		        {
		        EditText edContent = (EditText) findViewById(R.id.showCompositionContent);
		        edContent.setFocusableInTouchMode(true);
		        edContent.setFocusable(true);
		        edContent.requestFocus();
	            
	            }
		        	
		            }
				
	
				return true;
			}
		});
		 return true;
		
	}
	public void UpdatetWriting(String id,String title, String keywords, String author,
            String date, String content, String editdate,String accessory_id,String synctime) throws Exception{		
		if(NV_Host.isLocal){						
			PaperResource pr = new PaperResource();	
			String xml=pr.toXML(id,title,keywords,author,date,content,editdate, accessory_id,synctime);			
			pr.wirteXMLRemote(xml);
				
		}
		else{
			
		}

		
	}	 
	
}
