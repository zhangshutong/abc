package njnu.det.newvision;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context ctx;
	private List<String> listDiaryGroup;
	private Map<String,List<String>> listDiaryTitle;
	
	
	public MyExpandableListAdapter(Context ctx,List<String> listDiaryGroup,
			Map<String,List<String>> listDiaryTitle){
		super();
		this.ctx=ctx;
		this.listDiaryGroup = listDiaryGroup;
		this.listDiaryTitle = listDiaryTitle;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this.listDiaryTitle.get(this.listDiaryGroup.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final String childText = (String)getChild(groupPosition, childPosition);
		
		if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.diarymain_childitem,null);
			
		}
		TextView tvDiartTitle= (TextView) convertView.findViewById(R.id.tvDiaTitle);
		tvDiartTitle.setText(childText);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return this.listDiaryTitle.get(listDiaryGroup.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.listDiaryGroup.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.listDiaryGroup.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String groupText = this.listDiaryGroup.get(groupPosition);
		if(convertView == null){
			LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.diarymain_listviewitem, null);
		}
		
		TextView tvGroupTitle = (TextView)convertView.findViewById(R.id.diaryTitle);
		tvGroupTitle.setText(groupText);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
