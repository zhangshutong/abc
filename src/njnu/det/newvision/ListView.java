package njnu.det.newvision;


import android.app.ListActivity;
import android.os.Bundle;


public class ListView extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.listview);
        //setContentView(R.layout.listview);
        //����һ��Adapter,ʹ���Զ����Adapter
        setListAdapter(new TextImageAdapter(this));*/
    }
    /**
     * �Զ�����ͼ
     * @author ��ѩ����
     *
     */
   /* private class TextImageAdapter extends BaseAdapter{
        private Context mContext;
    	public TextImageAdapter(Context context) {
			this.mContext=context;
		}
        *//**
         * Ԫ�صĸ���
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
		//����������ListView��չʾ��һ����Ԫ��View
		public View getView(int position, View convertView, ViewGroup parent) {
			//�Ż�ListView
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
			//�����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ
			cache.mImageView.setImageResource(images[position]);
			cache.mTextView.setText(texts[position]);
			//cache.mImageView1.setImageResource(images[position]);
			cache.mTextView1.setText(texts1[position]);
			cache.mTextView2.setText(texts2[position]);
			
			return convertView;
		}
    }
    //Ԫ�صĻ�����,�����Ż�ListView
    private static class ItemViewCache{
		public TextView mTextView;
		public ImageView mImageView;
		public TextView mTextView1;
		public TextView mTextView2;
		//public ImageView mImageView1;
	}
  //չʾ������
    private  String[] texts=new String[]{"��С��","���׳","��Сǿ"};
    private  String[] texts1=new String[]{"���ֵ�һ��","��һ��С��","������ѩ"};
    private  String[] texts2=new String[]{"����3","����2","����4"};
    //չʾ��ͼƬ
    private int[] images=new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3};*/
}

	 
