package njnu.det.newvision;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class CompositionFragmentTab extends ListFragment {
	Table sResult;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.composition_layout,null);		
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //setContentView(R.layout.listview);
	        //setContentView(R.layout.listview);
	        //设置一个Adapter,使用自定义的Adapter
	        setListAdapter(new TextImageAdapter(getActivity()));
	        try {
	         	getData();
			 } catch (Exception e) {
				// TODO Auto-generated catch block
			 	Log.i("NewVision","Error:" + e.getMessage());
			 }
	}
	
	private void getData() throws Exception{
		 sResult =null;
		if(NV_Host.isLocal){
			CompositionResource wr = new CompositionResource();
			sResult = wr.getXML();
		}
		//变量初始化
		/*sTitles = new String[sResult.getRowSize()];
		sComments = new String[sResult.getRowSize()];
		sDates = new String[sResult.getRowSize()];
		for(int i=0; i< sResult.getRowSize();i++){
			sTitles[i] =sResult.getRow(i).getString(1); 
			sDates[i] = sResult.getRow(i).getString(4);*/
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
			return sResult.getRowSize();
			
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
				convertView=LayoutInflater.from(mContext).inflate(R.layout.composition_listiewitem, null);
				ItemViewCache viewCache=new ItemViewCache();
				viewCache.mTextView=(TextView)convertView.findViewById(R.id.ctext);
				viewCache.mTextView1=(TextView)convertView.findViewById(R.id.ctext1);
				viewCache.mTextView2=(TextView)convertView.findViewById(R.id.ctext2);
				
				convertView.setTag(viewCache);
			}
			ItemViewCache cache=(ItemViewCache)convertView.getTag();
			cache.mTextView.setText(sResult.Tbody.get(position).getTd(1).toString());
			Log.d("abc--", sResult.Tbody.get(position).getTd(1).toString());  //标题列 
			cache.mTextView1.setText(texts1[position]);
			cache.mTextView2.setText(sResult.Tbody.get(position).getTd(4).toString()); // 日期列
			
			return convertView;
		}
    }
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ShowComposition.class);
		intent.putExtra("sTitle", sResult.Tbody.get(position).getTd(1).toString());
		intent.putExtra("sDate", sResult.Tbody.get(position).getTd(4).toString());
		intent.putExtra("sContent", sResult.Tbody.get(position).getTd(5).toString());
		intent.putExtra("sId", sResult.Tbody.get(position).getTd(0).toString());
		intent.putExtra("sKeywords", sResult.Tbody.get(position).getTd(2).toString());		
		intent.putExtra("sAccessoryId", sResult.Tbody.get(position).getTd(7).toString());
        intent.putExtra("sSynctime", sResult.Tbody.get(position).getTd(8).toString());
				
		startActivity(intent);
	}
	
    //元素的缓冲类,用于优化ListView
    private static class ItemViewCache{
		public TextView mTextView;
		public TextView mTextView1;
		public TextView mTextView2;
	}
  //展示的文字
//    private  String[] texts=new String[]{"春意盎然","夏日炎炎","秋高气爽","春意盎然","夏日炎炎","秋高气爽","春意盎然","夏日炎炎","秋高气爽","春意盎然","夏日炎炎","秋高气爽"};
    private  String[] texts1=new String[]{"评论1","评论2","评论7","评论1","评论2","评论7","评论1","评论2","评论7","评论1","评论2","评论7"};
//    private  String[] texts2=new String[]{"2013-8-17","2013-7-28","2013-8-18","2013-8-17","2013-7-28","2013-8-18","2013-8-17","2013-7-28","2013-8-18","2013-8-17","2013-7-28","2013-8-18"};
	
}



