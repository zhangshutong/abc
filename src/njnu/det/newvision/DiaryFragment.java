package njnu.det.newvision;


import java.text.SimpleDateFormat;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DiaryFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.diary_main,null);		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
	        //����һ��Adapter,ʹ���Զ����Adapter
	        setListAdapter(new TextImageAdapter(getActivity()));
	}
	
	private class TextImageAdapter extends BaseAdapter{
        private Context mContext;
    	public TextImageAdapter(Context context) {
			this.mContext=context;
		}
        
		public int getCount() {
			return texts.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}
		//����������ListView��չʾ��һ����Ԫ��View
		public View getView(int position, View convertView, ViewGroup parent) {
			//�Ż�ListView
			if(convertView==null){
				convertView=LayoutInflater.from(mContext).inflate(R.layout.diarymain_listviewitem, null);
				ItemViewCache viewCache=new ItemViewCache();
				//viewCache.mImageView=(ImageView)convertView.findViewById(R.id.dimage);
				viewCache.mTextView=(TextView)convertView.findViewById(R.id.dmText);
				viewCache.mTextView1=(TextView)convertView.findViewById(R.id.dmText1);
				
				convertView.setTag(viewCache);
			}
			ItemViewCache cache=(ItemViewCache)convertView.getTag();
			//�����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ
			//cache.mImageView.setImageResource(images[position]);
			cache.mTextView.setText(texts[position]);
			cache.mTextView1.setText(texts1[position]);
			return convertView;
		}
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		//super.onListItemClick(l, v, position, id);
		//Toast.makeText(getActivity(), "ѡ����", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		//intent.setClass(getActivity(), ShowActivity.class);
		intent.setClass(getActivity(), DiaryList.class);
		startActivity(intent);
	}
	
	
    //Ԫ�صĻ�����,�����Ż�ListView
    private static class ItemViewCache{
		public TextView mTextView;
		public TextView mTextView1;
		//public ImageView mImageView;
	
	}
  //չʾ������
    SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
   String   date   =   sDateFormat.format(new   java.util.Date());  
    private  String[] texts=new String[]{"�۲�ˮ�ɻ�","�۲��ճ�","�۲�����"};
    private  String[] texts1=new String[]{date,date,date};
    
   // private  String[] texts2=new String[]{"����3","����2","����4"};
    //չʾ��ͼƬ
   // private int[] images=new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3};
	
}




/*package njnu.det.newvision;


import java.text.SimpleDateFormat;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DiaryFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.diary_layout,null);		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //setContentView(R.layout.listview);
	        //setContentView(R.layout.listview);
	        //����һ��Adapter,ʹ���Զ����Adapter
	        setListAdapter(new TextImageAdapter(getActivity()));
	}
	
	private class TextImageAdapter extends BaseAdapter{
        private Context mContext;
    	public TextImageAdapter(Context context) {
			this.mContext=context;
		}
        
		public int getCount() {
			return texts.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}
		//����������ListView��չʾ��һ����Ԫ��View
		public View getView(int position, View convertView, ViewGroup parent) {
			//�Ż�ListView
			if(convertView==null){
				convertView=LayoutInflater.from(mContext).inflate(R.layout.diary_listiewitem, null);
				ItemViewCache viewCache=new ItemViewCache();
				//viewCache.mImageView=(ImageView)convertView.findViewById(R.id.dimage);
				viewCache.mTextView=(TextView)convertView.findViewById(R.id.dtext);
				//viewCache.mImageView1=(ImageView)convertView.findViewById(R.id.image1);
				viewCache.mTextView1=(TextView)convertView.findViewById(R.id.dtext1);
				//viewCache.mTextView2=(TextView)convertView.findViewById(R.id.dtext2);
				
				convertView.setTag(viewCache);
			}
			ItemViewCache cache=(ItemViewCache)convertView.getTag();
			//�����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ
			//cache.mImageView.setImageResource(images[position]);
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
		//Toast.makeText(getActivity(), "ѡ����", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(getActivity(), ShowActivity.class);
		startActivity(intent);
	}
	
	
    //Ԫ�صĻ�����,�����Ż�ListView
    private static class ItemViewCache{
		public TextView mTextView;
		//public ImageView mImageView;
		public TextView mTextView1;
		//public TextView mTextView2;
		//public ImageView mImageView1;
	}
  //չʾ������
    SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
    String   date   =   sDateFormat.format(new   java.util.Date());  
    private  String[] texts=new String[]{"�ڼ�","����Ϸ","����"};
    private  String[] texts1=new String[]{date+"  "+"��һ",date+"  "+"��һ",date+"  "+"��һ"};
    
   // private  String[] texts2=new String[]{"����3","����2","����4"};
    //չʾ��ͼƬ
   // private int[] images=new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3};
	
}


*/