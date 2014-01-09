package njnu.det.newvision;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BodyActivity extends Activity implements OnClickListener, OnMenuItemClickListener{
	
	
	
	Fragment fEssy;
	Fragment fComposition;
	ActionBar.Tab tabE;
	ActionBar.Tab tabC;
	ActionBar bar;
	
	
		private DiaryFragment diaryFragment;//����չʾ��Ϣ��Fragment
		private ContactsFragment contactsFragment;//����չʾ��ϵ�˵�Fragment
		private WritingzoneFragment writingzoneFragment;//����չʾ��̬��Fragment
		//private SettingFragment settingFragment;//����չʾ���õ�Fragment
		private View messageLayout;//��Ϣ���沼��
		private View contactsLayout;//��ϵ�˽��沼��
		private View newsLayout;//��̬���沼��
		//private View settingLayout;//���ý��沼��
		private ImageView messageImage;//��Tab��������ʾ��Ϣͼ��Ŀؼ�
		private ImageView contactsImage;//��Tab��������ʾ��ϵ��ͼ��Ŀؼ�
		private ImageView newsImage;//��Tab��������ʾ��̬ͼ��Ŀؼ�
	//	private ImageView settingImage;//��Tab��������ʾ����ͼ��Ŀؼ�
		private TextView messageText;//��Tab��������ʾ��Ϣ����Ŀؼ�
		private TextView contactsText;//��Tab��������ʾ��ϵ�˱���Ŀؼ�
		private TextView newsText;//��Tab��������ʾ��̬����Ŀؼ�
		//private TextView settingText;//��Tab��������ʾ���ñ���Ŀؼ�
		private FragmentManager fragmentManager;//���ڶ�Fragment���й���

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);		
			setContentView(R.layout.activity_body);
			MyApplication.getInstance().addActivity(this);
			// ��ʼ������Ԫ��
			/*//��ӱ�ǩ
			 bar = getActionBar();
			//����ΪTabģʽ
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			//�½�2��Tab
			tabE = bar.newTab().setText(R.string.essay);
			tabC = bar.newTab().setText(R.string.composition);
			//�󶨵�Fragment
			fEssy = new EssayFragment();
			fComposition = new CompositionFragmentTab();
			tabE.setTabListener(new MyTabsListener(fEssy));
			tabC.setTabListener(new MyTabsListener(fComposition));
			bar.addTab(tabE);
			bar.addTab(tabC);*/
			
			findViews();
			
			messageLayout.setOnClickListener(this);
			contactsLayout.setOnClickListener(this);
			newsLayout.setOnClickListener(this);
			//settingLayout.setOnClickListener(this);
			
			fragmentManager = getFragmentManager();
			
			
			// ��һ������ʱѡ�е�i��tab
			int i = getIntent().getExtras().getInt("chosenflag");
			setBottomLayoutSelection(i);
			
			//������̨����
			
			//��ȡ���� ��ר���� ��䣩
			
			
			//��ȡ�����ַ
			
			
		}
		
		
		
		
		
		public void findViews(){
			
			messageLayout = findViewById(R.id.diary_layout); //�ռ�
			contactsLayout = findViewById(R.id.contacts_layout); //ѧ��
			newsLayout = findViewById(R.id.writingzone_layout);//԰��
			//settingLayout = findViewById(R.id.setting_layout);����
			
			messageImage = (ImageView) findViewById(R.id.message_image);
			contactsImage = (ImageView) findViewById(R.id.contacts_image);
			newsImage = (ImageView) findViewById(R.id.news_image);
			//settingImage = (ImageView) findViewById(R.id.setting_image);
			
			messageText = (TextView) findViewById(R.id.message_text);
			contactsText = (TextView) findViewById(R.id.contacts_text);
			newsText = (TextView) findViewById(R.id.news_text);
			//settingText = (TextView) findViewById(R.id.setting_text);
		}
		
		
		protected class MyTabsListener implements ActionBar.TabListener
		{
			private Fragment fragment;
			public MyTabsListener(Fragment fragment)
			{
				this.fragment = fragment;
			}
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft)
			{
				
				ft.add(R.id.content, fragment, null);
			}
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction ft) {
				// TODO Auto-generated method stub				
				
			}
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				ft.remove(fragment);
			}
		}
			
		

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.writingzone_layout:
				// �������  д��԰��   ʱ��ѡ�е�1��layout
				setBottomLayoutSelection(0);
				break;
				
			case R.id.contacts_layout:
				// �����������ѧ��ʱ��ѡ�е�2��layout
				setBottomLayoutSelection(1);
				break;
				
			case R.id.diary_layout:
				// ��������ҵ��ռ�ʱ��ѡ�е�3��layout
				setBottomLayoutSelection(2);
				break;
			
			default:
				break;
			}
		}

		/**
		 * ���ݴ����index����������ѡ�е�tabҳ��
		 * 
		 * @param index
		 *            ÿ��layout��Ӧ���±ꡣ0��ʾд��԰�أ�1��ʾ����ѧ�飬2��ʾ�ҵ��ռǡ�
		 */
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		private void setBottomLayoutSelection(int index) {
			// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
			clearSelection();
			// ����һ��Fragment����
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
			hideFragments(transaction);
			switch (index) {
			case 0:
				// ������˶�̬tabʱ���ı�ؼ���ͼƬ��������ɫ
				newsImage.setImageResource(R.drawable.news_selected);
				newsText.setTextColor(Color.WHITE);
				/*if (writingzoneFragment == null) {
					// ���NewsFragmentΪ�գ��򴴽�һ������ӵ�������
					writingzoneFragment = new WritingzoneFragment();
					transaction.add(R.id.content, writingzoneFragment);*/
					//�ж� ���actionBarû��tab  ���
				    bar = getActionBar();
					if(bar.getSelectedTab()==null){
					//����ΪTabģʽ
					bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
					//�½�2��Tab
					tabE = bar.newTab().setText(R.string.essay);
					tabC = bar.newTab().setText(R.string.composition);
					//�󶨵�Fragment
					fEssy = new EssayFragment();
					
					fComposition = new CompositionFragmentTab();
					tabE.setTabListener(new MyTabsListener(fEssy));
					
					tabC.setTabListener(new MyTabsListener(fComposition));
					bar.addTab(tabE);
					bar.addTab(tabC);}
					
					
					
				/* else {
					// ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
					transaction.show(writingzoneFragment);
				}
				*/
				break;
			case 1:
				// ���������ϵ��tabʱ���ı�ؼ���ͼƬ��������ɫ
				contactsImage.setImageResource(R.drawable.contacts_selected);
				contactsText.setTextColor(Color.WHITE);
				if (contactsFragment==null) {
					// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
					contactsFragment = new ContactsFragment();
					transaction.add(R.id.content, contactsFragment);
					
					
				} else {
					// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
					transaction.show(contactsFragment);
				}
				
				if(getActionBar().getSelectedTab()!=null){
					getActionBar().removeAllTabs();}
				
				break;
			case 2:
				// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
				messageImage.setImageResource(R.drawable.message_selected);
				messageText.setTextColor(Color.WHITE);
				if (diaryFragment == null) {
					// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
					diaryFragment = new DiaryFragment();
					transaction.add(R.id.content, diaryFragment);
					
					
				} else {
					// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
					transaction.show(diaryFragment);
				}
				if(getActionBar().getSelectedTab()!=null){
					getActionBar().removeAllTabs();}
				break;			
			
			default:
			}
			transaction.commit();
		}

		/**
		 * ��������е�ѡ��״̬��
		 */
		private void clearSelection() {
			messageImage.setImageResource(R.drawable.message_unselected);
			messageText.setTextColor(Color.parseColor("#82858b"));
			contactsImage.setImageResource(R.drawable.contacts_unselected);
			contactsText.setTextColor(Color.parseColor("#82858b"));
			newsImage.setImageResource(R.drawable.news_unselected);
			newsText.setTextColor(Color.parseColor("#82858b"));
			/*settingImage.setImageResource(R.drawable.setting_unselected);
			settingText.setTextColor(Color.parseColor("#82858b"));*/
		}

		/**
		 * �����е�Fragment����Ϊ����״̬��
		 * 
		 * @param transaction
		 *            ���ڶ�Fragmentִ�в���������
		 */
		private void hideFragments(FragmentTransaction transaction) {
			if (diaryFragment != null) {
				transaction.hide(diaryFragment);
			}
			if (contactsFragment != null) {
				transaction.hide(contactsFragment);
			}
			if (writingzoneFragment != null) {
				transaction.hide(writingzoneFragment);
			}
			/*if (settingFragment != null) {
				transaction.hide(settingFragment);
			}*/
		}
	
		protected static final int MENU_ABOUT = Menu.FIRST;
		protected static final int MENU_Quit = Menu.FIRST + 1;
		
		 @Override  
		    public boolean onCreateOptionsMenu(Menu menu) {  
		        // Inflate the menu; this adds items to the action bar if it is present.  
		        getMenuInflater().inflate(R.menu.main, menu);  
		        
		        menu.add(0,MENU_ABOUT,0,"����");
				menu.add(0,MENU_Quit,0,"�˳�");
				
		        MenuItem addDiary = menu.add(1, 1, 1,R.string.writeDiary);  
			       addDiary.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			      MenuItem addEssay = menu.add(1, 2, 1, R.string.writeEssay);  
			       addEssay.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			       MenuItem addComposition = menu.add(1, 3, 1, R.string.writeComposition);  
			       addComposition.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		          
		        //��addMenuItem��deleteMenuItem��okMenuItem���õ����¼�����  
			       addDiary.setOnMenuItemClickListener(this);
			       addEssay.setOnMenuItemClickListener(this);
			       addComposition.setOnMenuItemClickListener(this);
		    
		         return true;  
		           
		        
		    }

		 public boolean onOptionsItemSelected(MenuItem item) {

				super.onOptionsItemSelected(item);
				switch (item.getItemId()) {
				case MENU_ABOUT:
					//startActivity(new Intent(this, NewDiary.class)); 
					{
						AlertDialog.Builder builder = new Builder(BodyActivity.this);
						builder.setMessage(R.string.aboutus)
						.setTitle(R.string.about)
						.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.show();
						
						
					}
					break;
				case MENU_Quit:
				{
					AlertDialog.Builder builder = new Builder(BodyActivity.this);
					builder.setMessage(R.string.quitconfirm)
					
					.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MyApplication.getInstance().exit();
						}
					})
					
					.setNegativeButton(R.string.giveup, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					})
					.show();
					
					
				}
					
					//MyApplication.getInstance().exit();
					
					break;
				case R.id.action_settings:
					startActivity(new Intent(this, AndroidPreferences.class));
				}
				return true;

			}


		 @Override  
		    public boolean onMenuItemClick(MenuItem item) {  
		        // TODO Auto-generated method stub  
			 
		        switch (item.getItemId()) {  
		        case 1:  
		            startActivity(new Intent(this, NewDiary.class));  
		            break;   
		        case 2:
		        	 startActivity(new Intent(this, NewEssay.class));  
			            break;  
		        case 3:
		        	 startActivity(new Intent(this, NewComposition.class));  
			            break; 
			            
		        }  
		        return true;  
		    }  
		 
		 

}
