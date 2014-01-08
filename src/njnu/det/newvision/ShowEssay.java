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

public class ShowEssay extends Activity {
	String id,title,keywords,author,date,content,editdate,accessory_id,synctime;
	ActionBar actionBar;
	EditText showEditText;
	TextView dateTextView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_essay_layout);
		MyApplication.getInstance().addActivity(this);
		actionBar = getActionBar();
		actionBar.show();
		show();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy��MM��dd��     HH��mm��ss");
		Date curdate = new Date(System.currentTimeMillis());
		String dateString = dFormat.format(curdate);
		dateTextView = (TextView)findViewById(R.id.showEssayDate);
		dateTextView.setText(dateString);
	}
	private boolean show() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy��MM��dd��     HH��mm��ss");
		Date curdate = new Date(System.currentTimeMillis());
		String dateString = dFormat.format(curdate);
		dateTextView = (TextView)findViewById(R.id.showEssayDate);
		dateTextView.setText(dateString);
		
		// ҳ��ؼ�
		TextView tvTitle   =  (TextView) findViewById(R.id.showEssayTitle);
		TextView tvDate    =  (TextView) findViewById(R.id.showEssayDate);
		EditText edContent = (EditText) findViewById(R.id.showEssayContent);
		//����ֵ
		String sTitle   = getIntent().getStringExtra("sTitle");
		String sDate    = getIntent().getStringExtra("sDate");
		String sContent = getIntent().getStringExtra("sContent");
		
		
	     //��ʾ
		tvTitle.setText(sTitle);
		tvDate.setText(sDate);
		edContent.setText(sContent);
		
		
		
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		final MenuItem modify = menu.add(0,1,1,"�޸�");
		modify.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		final MenuItem save = menu.add(0,1,1,"����");
		save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		save.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {  
		         
		        case 1:   
					
		            {	
		            	TextView tvTitle   =  (TextView) findViewById(R.id.showEssayTitle);
		        		TextView tvDate    =  (TextView) findViewById(R.id.showEssayDate);
		        		EditText edContent = (EditText) findViewById(R.id.showEssayContent);
		        		
		        		title       = tvTitle.getText().toString().trim();
		        		date        = getIntent().getStringExtra("sDate");
		        		content     = edContent.getText().toString().trim();
		        		id          = getIntent().getStringExtra("sId");
		        		keywords    = getIntent().getStringExtra("sKeywords");		
		        		accessory_id= getIntent().getStringExtra("sAccessoryId");
		        		synctime    = getIntent().getStringExtra("sSynctime"); //���ݿ�ͬ��ʱ��
		            	
			        	User user   = NV_Host.getLocalUser();
			        	author      = user.NikedName;
			        	
			        	NV_Host.setOperation("UPDATE");   //����
			        	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
			            editdate    = sDateFormat.format(new java.util.Date()); 
			            
			        	try {
							UpdatetEssay();
							Toast.makeText(getApplicationContext(), "You clicked on ����", Toast.LENGTH_SHORT).show();	
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            Toast.makeText(getBaseContext(), "����ɹ�", Toast.LENGTH_SHORT).show();
		           
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
		        EditText edContent = (EditText) findViewById(R.id.showEssayContent);
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
	public void UpdatetEssay() throws Exception{		
		if(NV_Host.isLocal){						
			EssayResource er = new EssayResource();	
			er.id           = id;
			er.title        = title;
			er.author       = author;
			er.date         = date ;
			er.content      = content  ;
			er.keywords     = keywords ;
			er.accessory_id = accessory_id;
			er.editdate     = editdate;
			er.synctime     = synctime ;
			
			String xml=er.toXML();		//id,title,keywords,author,date,content,editdate, accessory_id,synctime	
			er.wirteXML(xml);
				
		}
		else{
			
		}

		
	}	 
	
}
