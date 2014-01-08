package njnu.det.newvision;


import android.app.ListActivity;
import android.os.Bundle;


public class ListView extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.listview);
        //setContentView(R.layout.listview);
        //设置一个Adapter,使用自定义的Adapter
        setListAdapter(new TextImageAdapter(this));*/
    }
    /**
     * 自定义视图
     * @author 飞雪无情
     *
     */
   /* private class TextImageAdapter extends BaseAdapter{
        private Context mContext;
    	public TextImageAdapter(Context context) {
			this.mContext=context;
		}
        *//**
         * 元素的个数
         *//*
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
				convertView=LayoutInflater.from(mContext).inflate(R.layout.listiew_item, null);
				ItemViewCache viewCache=new ItemViewCache();
				viewCache.mImageView=(ImageView)convertView.findViewById(R.id.image);
				viewCache.mTextView=(TextView)convertView.findViewById(R.id.text);
				//viewCache.mImageView1=(ImageView)convertView.findViewById(R.id.image1);
				viewCache.mTextView1=(TextView)convertView.findViewById(R.id.text1);
				viewCache.mTextView2=(TextView)convertView.findViewById(R.id.text2);
				
				convertView.setTag(viewCache);
			}
			ItemViewCache cache=(ItemViewCache)convertView.getTag();
			//设置文本和图片，然后返回这个View，用于ListView的Item的展示
			cache.mImageView.setImageResource(images[position]);
			cache.mTextView.setText(texts[position]);
			//cache.mImageView1.setImageResource(images[position]);
			cache.mTextView1.setText(texts1[position]);
			cache.mTextView2.setText(texts2[position]);
			
			return convertView;
		}
    }
    //元素的缓冲类,用于优化ListView
    private static class ItemViewCache{
		public TextView mTextView;
		public ImageView mImageView;
		public TextView mTextView1;
		public TextView mTextView2;
		//public ImageView mImageView1;
	}
  //展示的文字
    private  String[] texts=new String[]{"王小明","李大壮","刘小强"};
    private  String[] texts1=new String[]{"快乐的一天","记一件小事","美丽的雪"};
    private  String[] texts2=new String[]{"评论3","评论2","评论4"};
    //展示的图片
    private int[] images=new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3};*/
}

	 
