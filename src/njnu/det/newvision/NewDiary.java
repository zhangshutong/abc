package njnu.det.newvision;

import java.text.SimpleDateFormat;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


public class NewDiary extends Activity{
	ActionBar actionBar;
	private TextView tvDiaryDate;
	private AutoCompleteTextView edtDiaryTitle;
	private String[] groups = new String[]{"观察水仙花","观察日出","观察日落"};
	protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);	
		    setContentView(R.layout.writingdiary_layout); 
		    MyApplication.getInstance().addActivity(this);
		    actionBar = getActionBar();
		    actionBar.show();
		    
		    tvDiaryDate = (TextView)findViewById(R.id.tvDiaryDate);
		    SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm");     
		    String   date   =   sDateFormat.format(new   java.util.Date());  
		    tvDiaryDate.setText(date);
		    
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, groups);
		    edtDiaryTitle = (AutoCompleteTextView)findViewById(R.id.edtDiaryTitle);
		    edtDiaryTitle.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuItem add = menu.add(0,1,1,"保存");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		MenuItem del = menu.add(0,2,1,"放弃");
		del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 return true;
		
	}


}
