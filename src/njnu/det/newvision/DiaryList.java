package njnu.det.newvision;


import java.text.SimpleDateFormat;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DiaryList extends ListActivity {

	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.diary_layout);
	        //设置一个Adapter,使用自定义的Adapter
	        setListAdapter(new TextImageAdapter(this));
	    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 MenuItem addItem = menu.add(0, 1, 1, "新建日志");
		 addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 addItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {  
		         
		        case 1:   
		        {
		        	startActivity(new Intent(DiaryList.this,NewComposition.class));
		        }
		            }
				
				return true;
			}
		});
		 
		 return true;
	}
	
	    private class TextImageAdapter extends BaseAdapter{
	        private Context mContext;
	    	public TextImageAdapter(Context context) {
				this.mContext=context;
			}
	        /**
	         * 元素的个数
	         */
			public int getCount() {
				return texts.length;
			}

			public Object getItem(int position) {
				return null;
			}

			public long getItemId(int position) {
				return 0;
			}
			//用以生成在ListView中展示的一个个元素View
			public View getView(int position, View convertView, ViewGroup parent) {
				//优化ListView
				if(convertView==null){
					convertView=LayoutInflater.from(mContext).inflate(R.layout.diary_listiewitem, null);
					ItemViewCache viewCache=new ItemViewCache();
					//viewCache.mImageView=(ImageView)convertView.findViewById(R.id.image);
					viewCache.mTextView=(TextView)convertView.findViewById(R.id.dtext);
					//viewCache.mImageView1=(ImageView)convertView.findViewById(R.id.image1);
					viewCache.mTextView1=(TextView)convertView.findViewById(R.id.dtext1);
					//viewCache.mTextView2=(TextView)convertView.findViewById(R.id.text2);
					
					convertView.setTag(viewCache);
				}
				ItemViewCache cache=(ItemViewCache)convertView.getTag();
				//设置文本和图片，然后返回这个View，用于ListView的Item的展示
				
				cache.mTextView.setText(texts[position]);
				//cache.mImageView1.setImageResource(images[position]);
				cache.mTextView1.setText(texts1[position]);
				//cache.mTextView2.setText(texts2[position]);
				
				return convertView;
			}
	    }
	    @Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			//super.onListItemClick(l, v, position, id);
			//Toast.makeText(getActivity(), "选中了", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(DiaryList.this, ShowComposition.class);
			startActivity(intent);
		}
		
		
	    //元素的缓冲类,用于优化ListView
	    private static class ItemViewCache{
			public TextView mTextView;
			//public ImageView mImageView;
			public TextView mTextView1;
			//public TextView mTextView2;
			//public ImageView mImageView1;
		}
	  //展示的文字
	    SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
	    String   date   =   sDateFormat.format(new   java.util.Date());  
	    private  String[] texts=new String[]{"在家","玩游戏","做饭"};
	    private  String[] texts1=new String[]{date+"  "+"周一",date+"  "+"周一",date+"  "+"周一"};
	    
	    
	 
	
}














/*package njnu.det.newvision;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DiaryMainFragment extends ListFragment {
	
	private String[] text= new String[]{"观察水仙花","观察小兔子","观察日出","观察日落"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.diary_main, null);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.diarymain_listviewitem,text);
		setListAdapter(arrayAdapter);
	}
	
}
*/