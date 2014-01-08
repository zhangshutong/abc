package njnu.det.newvision;

import android.R.bool;
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

public class EssayFragment extends ListFragment {
    Table  table;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.essay_layout, null);
		getData();        
		return view;
	}

	
	 private Boolean getData() {
		//获得从数据库中select出来的table对象。
			EssayResource er = new EssayResource();
	        try {
				table =  er.getTable();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置一个Adapter,使用自定义的Adapter

		setListAdapter(new TextImageAdapter(getActivity()));

	}

	private class TextImageAdapter extends BaseAdapter {
		private Context mContext;

		public TextImageAdapter(Context context) {
			this.mContext = context;
		}

		/**
		 * 元素的个数
		 */
		public int getCount() {
			return table.getRowSize();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// 用以生成在ListView中展示的一个个元素View
		public View getView(int position, View convertView, ViewGroup parent) {
			// 优化ListView
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.essay_listiewitem, null);
				ItemViewCache viewCache = new ItemViewCache();
				viewCache.TitleView = (TextView) convertView
						.findViewById(R.id.text);
				viewCache.CommentView = (TextView) convertView
						.findViewById(R.id.text1);
				viewCache.DateView = (TextView) convertView
						.findViewById(R.id.text2);
				convertView.setTag(viewCache);
			}
			ItemViewCache cache = (ItemViewCache) convertView.getTag();
			cache.TitleView.setText(table.Tbody.get(position).getTd(1).toString());
			                                                // title 是table中的第二列，显示到界面上
			cache.CommentView.setText(comments[position]);
			cache.DateView.setText(table.Tbody.get(position).getTd(4).toString());  //日期时间显示

			return convertView;
		}

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ShowEssay.class);
		intent.putExtra("sTitle", table.Tbody.get(position).getTd(1).toString());
		intent.putExtra("sDate", table.Tbody.get(position).getTd(4).toString());
		intent.putExtra("sContent", table.Tbody.get(position).getTd(5).toString());
		intent.putExtra("sId", table.Tbody.get(position).getTd(0).toString());
		intent.putExtra("sKeywords", table.Tbody.get(position).getTd(2).toString());		
		intent.putExtra("sAccessoryId", table.Tbody.get(position).getTd(7).toString());
        intent.putExtra("sSynctime", table.Tbody.get(position).getTd(8).toString());
		startActivity(intent);
	}

	// 元素的缓冲类,用于优化ListView
	private static class ItemViewCache {
		public TextView TitleView;
		public TextView CommentView;
		public TextView DateView;

	}

	// 展示的文字
//	private String[] texts = new String[] { "快乐的一天", "记一件小事", "美丽的雪——这是今年的第一场雪" };
	private String[] comments = new String[] { "评论3", "评论2", "评论4" };
//	private String[] texts2 = new String[] { "2013-6-7", "2013-7-8", "2013-8-8" };

}

