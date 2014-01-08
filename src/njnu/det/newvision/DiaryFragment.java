package njnu.det.newvision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class DiaryFragment extends Fragment {

	MyExpandableListAdapter expandableListAdapter;
	ExpandableListView expandableListView;
	List<String> listDiaryGroup;
	Map<String, List<String>> listDiaryTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.diary_main, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		expandableListView = (ExpandableListView) getActivity().findViewById(
				R.id.lvDiaryMain);
		
		//填充数据准备
		prepareListData();

		expandableListAdapter = new MyExpandableListAdapter(getActivity(),
				listDiaryGroup, listDiaryTitle);

		expandableListView.setAdapter(expandableListAdapter);

		// 组展开响应
		expandableListView
				.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "展开", Toast.LENGTH_SHORT)
								.show();

					}
				});

		// 组折叠响应
		expandableListView
				.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						Toast.makeText(getActivity(), "折叠:", Toast.LENGTH_LONG)
								.show();
					}
				});

		// 子元素惦记响应
		expandableListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), ShowDiary.class);
						startActivity(intent);
						/*Toast.makeText(getActivity(), "我在这儿:",
								Toast.LENGTH_LONG).show();
						*/
						return false;
					}
				});

	}

	private void prepareListData() {
		listDiaryGroup = new ArrayList<String>();
		listDiaryTitle = new HashMap<String, List<String>>();
		// 此处数据可以从数据库获得

		// add diarygroup
		listDiaryGroup.add("观察水仙花");
		listDiaryGroup.add("观察蚂蚁");
		listDiaryGroup.add("观察星星");

		// add diaryTitle
		List<String> waterList = new ArrayList<String>();
		waterList.add("1");
		waterList.add("7");
		waterList.add("6");
		waterList.add("5");
		waterList.add("3");
		waterList.add("2");

		List<String> ant = new ArrayList<String>();
		ant.add("星期一");
		ant.add("星期六");
		ant.add("星期五");
		ant.add("星期四");
		ant.add("星期三");
		ant.add("星期二");

		List<String> star = new ArrayList<String>();
		star.add("北斗七星");
		star.add("大熊星座");
		star.add("天秤座");
		star.add("射手座");

		listDiaryTitle.put(listDiaryGroup.get(0), waterList);
		listDiaryTitle.put(listDiaryGroup.get(1), ant);
		listDiaryTitle.put(listDiaryGroup.get(2), star);

	}

}

/*
 * package njnu.det.newvision;
 * 
 * import java.util.ArrayList; import java.util.HashMap; import java.util.List;
 * import java.util.Map;
 * 
 * import android.app.ListFragment; import android.os.Bundle; import
 * android.view.LayoutInflater; import android.view.View; import
 * android.view.ViewGroup; import android.widget.ExpandableListView; import
 * android.widget.Toast;
 * 
 * public class DiaryFragment extends ListFragment {
 * 
 * MyExpandableListAdapter expandableListAdapter; ExpandableListView
 * expandableListView; List<String> listDiaryGroup; Map<String, List<String>>
 * listDiaryTitle;
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) { View view =
 * inflater.inflate(R.layout.diary_main, container); return view; }
 * 
 * 
 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onCreate(savedInstanceState);
 * 
 * 
 * 
 * expandableListView=(ExpandableListView)getActivity().findViewById(R.id.
 * lvDiaryMain);
 * 
 * prepareListData();
 * 
 * expandableListAdapter = new MyExpandableListAdapter(getActivity(),
 * listDiaryGroup, listDiaryTitle);
 * 
 * expandableListView.setAdapter(expandableListAdapter);
 * 
 * //组展开响应 expandableListView.setOnGroupExpandListener(new
 * ExpandableListView.OnGroupExpandListener() {
 * 
 * @Override public void onGroupExpand(int groupPosition) { // TODO
 * Auto-generated method stub Toast.makeText(getActivity(), "展开",
 * Toast.LENGTH_SHORT) .show();
 * 
 * } });
 * 
 * //组折叠响应 expandableListView.setOnGroupCollapseListener(new
 * ExpandableListView.OnGroupCollapseListener() {
 * 
 * @Override public void onGroupCollapse(int groupPosition) {
 * Toast.makeText(getActivity(), "折叠:", Toast.LENGTH_LONG).show(); } });
 * 
 * 
 * //子元素惦记响应 expandableListView.setOnChildClickListener(new
 * ExpandableListView.OnChildClickListener() {
 * 
 * @Override public boolean onChildClick(ExpandableListView parent, View v, int
 * groupPosition, int childPosition, long id) { Toast.makeText(getActivity(),
 * "我在这儿:", Toast.LENGTH_LONG).show(); return false; } });
 * 
 * }
 * 
 * 
 * 
 * 
 * private void prepareListData(){ listDiaryGroup =new ArrayList<String>();
 * listDiaryTitle = new HashMap<String, List<String>>(); //此处数据可以从数据库获得
 * 
 * //add diarygroup listDiaryGroup.add("观察水仙花"); listDiaryGroup.add("观察蚂蚁");
 * listDiaryGroup.add("观察星星");
 * 
 * //add diaryTitle List<String> waterList = new ArrayList<String>();
 * waterList.add("1"); waterList.add("7"); waterList.add("6");
 * waterList.add("5"); waterList.add("3"); waterList.add("2");
 * 
 * List<String> ant = new ArrayList<String>(); ant.add("星期一"); ant.add("星期六");
 * ant.add("星期五"); ant.add("星期四"); ant.add("星期三"); ant.add("星期二");
 * 
 * List<String> star = new ArrayList<String>(); star.add("北斗七星");
 * star.add("大熊星座"); star.add("天秤座"); star.add("射手座");
 * 
 * }
 * 
 * 
 * }
 */

/*
 * package njnu.det.newvision;
 * 
 * import java.util.ArrayList; import java.util.HashMap; import java.util.List;
 * import java.util.Map;
 * 
 * import android.app.Fragment; import android.os.Bundle; import
 * android.view.LayoutInflater; import android.view.View; import
 * android.view.ViewGroup; import android.widget.ExpandableListView; import
 * android.widget.Toast;
 * 
 * public class DiaryFragment extends Fragment {
 * 
 * MyExpandableListAdapter expandableListAdapter; ExpandableListView
 * expandableListView; List<String> listDiaryGroup; Map<String, List<String>>
 * listDiaryTitle;
 * 
 * 
 * 
 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onCreate(savedInstanceState);
 * 
 * 
 * }
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) { View view =
 * inflater.inflate(R.layout.diary_main, null);
 * 
 * 
 * 
 * return view; }
 * 
 * @Override public void onActivityCreated(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onActivityCreated(savedInstanceState);
 * 
 * expandableListView=(ExpandableListView)getActivity().findViewById(R.id.
 * lvDiaryMain);
 * 
 * prepareListData();
 * 
 * expandableListAdapter = new MyExpandableListAdapter(getActivity(),
 * listDiaryGroup, listDiaryTitle);
 * 
 * expandableListView.setAdapter(expandableListAdapter);
 * 
 * //组展开响应 expandableListView.setOnGroupExpandListener(new
 * ExpandableListView.OnGroupExpandListener() {
 * 
 * @Override public void onGroupExpand(int groupPosition) { // TODO
 * Auto-generated method stub Toast.makeText(getActivity(), "展开",
 * Toast.LENGTH_SHORT) .show();
 * 
 * } });
 * 
 * //组折叠响应 expandableListView.setOnGroupCollapseListener(new
 * ExpandableListView.OnGroupCollapseListener() {
 * 
 * @Override public void onGroupCollapse(int groupPosition) {
 * Toast.makeText(getActivity(), "折叠:", Toast.LENGTH_LONG).show(); } });
 * 
 * 
 * //子元素惦记响应 expandableListView.setOnChildClickListener(new
 * ExpandableListView.OnChildClickListener() {
 * 
 * @Override public boolean onChildClick(ExpandableListView parent, View v, int
 * groupPosition, int childPosition, long id) { Toast.makeText(getActivity(),
 * "我在这儿:", Toast.LENGTH_LONG).show(); return false; } });
 * 
 * 
 * 
 * }
 * 
 * private void prepareListData(){ listDiaryGroup =new ArrayList<String>();
 * listDiaryTitle = new HashMap<String, List<String>>(); //此处数据可以从数据库获得
 * 
 * //add diarygroup listDiaryGroup.add("观察水仙花"); listDiaryGroup.add("观察蚂蚁");
 * listDiaryGroup.add("观察星星");
 * 
 * //add diaryTitle List<String> waterList = new ArrayList<String>();
 * waterList.add("1"); waterList.add("7"); waterList.add("6");
 * waterList.add("5"); waterList.add("3"); waterList.add("2");
 * 
 * List<String> ant = new ArrayList<String>(); ant.add("星期一"); ant.add("星期六");
 * ant.add("星期五"); ant.add("星期四"); ant.add("星期三"); ant.add("星期二");
 * 
 * List<String> star = new ArrayList<String>(); star.add("北斗七星");
 * star.add("大熊星座"); star.add("天秤座"); star.add("射手座");
 * 
 * }
 * 
 * 
 * }
 * 
 * 
 * 
 * /* package njnu.det.newvision;
 * 
 * import java.text.SimpleDateFormat;
 * 
 * import android.app.ListFragment; import android.content.Context; import
 * android.content.Intent; import android.os.Bundle; import
 * android.view.LayoutInflater; import android.view.View; import
 * android.view.ViewGroup; import android.widget.BaseAdapter; import
 * android.widget.ListView; import android.widget.TextView;
 * 
 * public class DiaryFragment extends ListFragment {
 * 
 * @Override public void onActivityCreated(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onActivityCreated(savedInstanceState);
 * 
 * }
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) { View view =
 * inflater.inflate(R.layout.diary_main, null);
 * 
 * return view;
 * 
 * }
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * 
 * // 设置一个Adapter,使用自定义的Adapter setListAdapter(new
 * TextImageAdapter(getActivity()));
 * 
 * }
 * 
 * private class TextImageAdapter extends BaseAdapter { private Context
 * mContext;
 * 
 * public TextImageAdapter(Context context) { this.mContext = context; }
 * 
 * public int getCount() { return diaryTitleStr.length; }
 * 
 * public Object getItem(int position) { return null; }
 * 
 * public long getItemId(int position) { return 0; }
 * 
 * // 用以生成在ListView中展示的一个个元素View public View getView(int position, View
 * convertView, ViewGroup parent) { // 优化ListView if (convertView == null) {
 * convertView = LayoutInflater.from(mContext).inflate(
 * R.layout.diarymain_listviewitem, null); ItemViewCache viewCache = new
 * ItemViewCache(); //
 * viewCache.mImageView=(ImageView)convertView.findViewById(R.id.dimage);
 * viewCache.tvDiaryTitle = (TextView) convertView
 * .findViewById(R.id.diaryTitle); viewCache.tvDiaryDate = (TextView)
 * convertView .findViewById(R.id.diaryDate); viewCache.tvDiaryNumber =
 * (TextView) convertView .findViewById(R.id.diaryNumber);
 * 
 * convertView.setTag(viewCache); } ItemViewCache cache = (ItemViewCache)
 * convertView.getTag(); // 设置文本和图片，然后返回这个View，用于ListView的Item的展示 //
 * cache.mImageView.setImageResource(images[position]);
 * cache.tvDiaryTitle.setText(diaryTitleStr[position]);
 * cache.tvDiaryDate.setText(diaryDateStr[position]);
 * cache.tvDiaryNumber.setText(diaryNumberStr[position]); return convertView; }
 * }
 * 
 * @Override public void onListItemClick(ListView l, View v, int position, long
 * id) {
 * 
 * Intent intent = new Intent(); // intent.setClass(getActivity(),
 * ShowActivity.class); intent.setClass(getActivity(), DiaryList.class);
 * startActivity(intent); }
 * 
 * // 元素的缓冲类,用于优化ListView private static class ItemViewCache { public TextView
 * tvDiaryTitle; public TextView tvDiaryDate; public TextView tvDiaryNumber;
 * 
 * }
 * 
 * // 展示的文字 SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
 * String date = sDateFormat.format(new java.util.Date()); private String[]
 * diaryTitleStr = new String[] { "观察水仙花", "观察日出", "观察日落" }; private String[]
 * diaryDateStr = new String[] { date, date, date }; private String[]
 * diaryNumberStr = new String[] { "文章数8", "文章数4", "文章数2" };
 * 
 * 
 * }
 */

/**/

