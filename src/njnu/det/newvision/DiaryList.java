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
		// ����һ��Adapter,ʹ���Զ����Adapter
		setListAdapter(new TextImageAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuItem addItem = menu.add(0, 1, 1, "�½���־");
		addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		addItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {

				case 1: {
					startActivity(new Intent(DiaryList.this, NewDiary.class));
				}
				}

				return true;
			}
		});

		return true;
	}

	private class TextImageAdapter extends BaseAdapter {
		private Context mContext;

		public TextImageAdapter(Context context) {
			this.mContext = context;
		}

		/**
		 * Ԫ�صĸ���
		 */
		public int getCount() {
			return diaryDateStr.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// ����������ListView��չʾ��һ����Ԫ��View
		public View getView(int position, View convertView, ViewGroup parent) {
			// �Ż�ListView
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.diary_listiewitem, null);
				ItemViewCache viewCache = new ItemViewCache();
				viewCache.tvDiaryDate = (TextView) convertView
						.findViewById(R.id.tvDiaryCreatTime);
				// viewCache.mTextView1=(TextView)convertView.findViewById(R.id.dtext1);

				convertView.setTag(viewCache);
			}
			ItemViewCache cache = (ItemViewCache) convertView.getTag();
			// �����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ

			// cache.mTextView.setText(texts[position]);
			// cache.mTextView1.setText(texts1[position]);
			cache.tvDiaryDate.setText(diaryDateStr[position]);

			return convertView;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent();
		intent.setClass(DiaryList.this, ShowDiary.class);
		startActivity(intent);
	}

	// Ԫ�صĻ�����,�����Ż�ListView
	private static class ItemViewCache {
		public TextView tvDiaryDate;

		// public TextView mTextView1;

	}

	// չʾ������
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm");
	String date = sDateFormat.format(new java.util.Date());
	// private String[] texts=new String[]{"�ڼ�","����Ϸ","����"};
	private String[] diaryDateStr = new String[] { date, "2013-11-05  06:24",
			"2013-10-22  16:32" };

}

/*
 * package njnu.det.newvision;
 * 
 * import android.app.ListFragment; import android.os.Bundle; import
 * android.view.LayoutInflater; import android.view.View; import
 * android.view.ViewGroup; import android.widget.ArrayAdapter;
 * 
 * public class DiaryMainFragment extends ListFragment {
 * 
 * private String[] text= new String[]{"�۲�ˮ�ɻ�","�۲�С����","�۲��ճ�","�۲�����"};
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) { // TODO Auto-generated method stub
 * View view = inflater.inflate(R.layout.diary_main, null); return view; }
 * 
 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onCreate(savedInstanceState);
 * ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
 * R.layout.diarymain_listviewitem,text); setListAdapter(arrayAdapter); }
 * 
 * }
 */