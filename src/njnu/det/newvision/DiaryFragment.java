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
		
		//�������׼��
		prepareListData();

		expandableListAdapter = new MyExpandableListAdapter(getActivity(),
				listDiaryGroup, listDiaryTitle);

		expandableListView.setAdapter(expandableListAdapter);

		// ��չ����Ӧ
		expandableListView
				.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "չ��", Toast.LENGTH_SHORT)
								.show();

					}
				});

		// ���۵���Ӧ
		expandableListView
				.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						Toast.makeText(getActivity(), "�۵�:", Toast.LENGTH_LONG)
								.show();
					}
				});

		// ��Ԫ�ص����Ӧ
		expandableListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), ShowDiary.class);
						startActivity(intent);
						/*Toast.makeText(getActivity(), "�������:",
								Toast.LENGTH_LONG).show();
						*/
						return false;
					}
				});

	}

	private void prepareListData() {
		listDiaryGroup = new ArrayList<String>();
		listDiaryTitle = new HashMap<String, List<String>>();
		// �˴����ݿ��Դ����ݿ���

		// add diarygroup
		listDiaryGroup.add("�۲�ˮ�ɻ�");
		listDiaryGroup.add("�۲�����");
		listDiaryGroup.add("�۲�����");

		// add diaryTitle
		List<String> waterList = new ArrayList<String>();
		waterList.add("1");
		waterList.add("7");
		waterList.add("6");
		waterList.add("5");
		waterList.add("3");
		waterList.add("2");

		List<String> ant = new ArrayList<String>();
		ant.add("����һ");
		ant.add("������");
		ant.add("������");
		ant.add("������");
		ant.add("������");
		ant.add("���ڶ�");

		List<String> star = new ArrayList<String>();
		star.add("��������");
		star.add("��������");
		star.add("�����");
		star.add("������");

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
 * //��չ����Ӧ expandableListView.setOnGroupExpandListener(new
 * ExpandableListView.OnGroupExpandListener() {
 * 
 * @Override public void onGroupExpand(int groupPosition) { // TODO
 * Auto-generated method stub Toast.makeText(getActivity(), "չ��",
 * Toast.LENGTH_SHORT) .show();
 * 
 * } });
 * 
 * //���۵���Ӧ expandableListView.setOnGroupCollapseListener(new
 * ExpandableListView.OnGroupCollapseListener() {
 * 
 * @Override public void onGroupCollapse(int groupPosition) {
 * Toast.makeText(getActivity(), "�۵�:", Toast.LENGTH_LONG).show(); } });
 * 
 * 
 * //��Ԫ�ص����Ӧ expandableListView.setOnChildClickListener(new
 * ExpandableListView.OnChildClickListener() {
 * 
 * @Override public boolean onChildClick(ExpandableListView parent, View v, int
 * groupPosition, int childPosition, long id) { Toast.makeText(getActivity(),
 * "�������:", Toast.LENGTH_LONG).show(); return false; } });
 * 
 * }
 * 
 * 
 * 
 * 
 * private void prepareListData(){ listDiaryGroup =new ArrayList<String>();
 * listDiaryTitle = new HashMap<String, List<String>>(); //�˴����ݿ��Դ����ݿ���
 * 
 * //add diarygroup listDiaryGroup.add("�۲�ˮ�ɻ�"); listDiaryGroup.add("�۲�����");
 * listDiaryGroup.add("�۲�����");
 * 
 * //add diaryTitle List<String> waterList = new ArrayList<String>();
 * waterList.add("1"); waterList.add("7"); waterList.add("6");
 * waterList.add("5"); waterList.add("3"); waterList.add("2");
 * 
 * List<String> ant = new ArrayList<String>(); ant.add("����һ"); ant.add("������");
 * ant.add("������"); ant.add("������"); ant.add("������"); ant.add("���ڶ�");
 * 
 * List<String> star = new ArrayList<String>(); star.add("��������");
 * star.add("��������"); star.add("�����"); star.add("������");
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
 * //��չ����Ӧ expandableListView.setOnGroupExpandListener(new
 * ExpandableListView.OnGroupExpandListener() {
 * 
 * @Override public void onGroupExpand(int groupPosition) { // TODO
 * Auto-generated method stub Toast.makeText(getActivity(), "չ��",
 * Toast.LENGTH_SHORT) .show();
 * 
 * } });
 * 
 * //���۵���Ӧ expandableListView.setOnGroupCollapseListener(new
 * ExpandableListView.OnGroupCollapseListener() {
 * 
 * @Override public void onGroupCollapse(int groupPosition) {
 * Toast.makeText(getActivity(), "�۵�:", Toast.LENGTH_LONG).show(); } });
 * 
 * 
 * //��Ԫ�ص����Ӧ expandableListView.setOnChildClickListener(new
 * ExpandableListView.OnChildClickListener() {
 * 
 * @Override public boolean onChildClick(ExpandableListView parent, View v, int
 * groupPosition, int childPosition, long id) { Toast.makeText(getActivity(),
 * "�������:", Toast.LENGTH_LONG).show(); return false; } });
 * 
 * 
 * 
 * }
 * 
 * private void prepareListData(){ listDiaryGroup =new ArrayList<String>();
 * listDiaryTitle = new HashMap<String, List<String>>(); //�˴����ݿ��Դ����ݿ���
 * 
 * //add diarygroup listDiaryGroup.add("�۲�ˮ�ɻ�"); listDiaryGroup.add("�۲�����");
 * listDiaryGroup.add("�۲�����");
 * 
 * //add diaryTitle List<String> waterList = new ArrayList<String>();
 * waterList.add("1"); waterList.add("7"); waterList.add("6");
 * waterList.add("5"); waterList.add("3"); waterList.add("2");
 * 
 * List<String> ant = new ArrayList<String>(); ant.add("����һ"); ant.add("������");
 * ant.add("������"); ant.add("������"); ant.add("������"); ant.add("���ڶ�");
 * 
 * List<String> star = new ArrayList<String>(); star.add("��������");
 * star.add("��������"); star.add("�����"); star.add("������");
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
 * // ����һ��Adapter,ʹ���Զ����Adapter setListAdapter(new
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
 * // ����������ListView��չʾ��һ����Ԫ��View public View getView(int position, View
 * convertView, ViewGroup parent) { // �Ż�ListView if (convertView == null) {
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
 * convertView.getTag(); // �����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ //
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
 * // Ԫ�صĻ�����,�����Ż�ListView private static class ItemViewCache { public TextView
 * tvDiaryTitle; public TextView tvDiaryDate; public TextView tvDiaryNumber;
 * 
 * }
 * 
 * // չʾ������ SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
 * String date = sDateFormat.format(new java.util.Date()); private String[]
 * diaryTitleStr = new String[] { "�۲�ˮ�ɻ�", "�۲��ճ�", "�۲�����" }; private String[]
 * diaryDateStr = new String[] { date, date, date }; private String[]
 * diaryNumberStr = new String[] { "������8", "������4", "������2" };
 * 
 * 
 * }
 */

/**/

