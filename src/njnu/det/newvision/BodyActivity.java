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
	
	
		private DiaryFragment diaryFragment;//用于展示消息的Fragment
		private ContactsFragment contactsFragment;//用于展示联系人的Fragment
		private WritingzoneFragment writingzoneFragment;//用于展示动态的Fragment
		//private SettingFragment settingFragment;//用于展示设置的Fragment
		private View messageLayout;//消息界面布局
		private View contactsLayout;//联系人界面布局
		private View newsLayout;//动态界面布局
		//private View settingLayout;//设置界面布局
		private ImageView messageImage;//在Tab布局上显示消息图标的控件
		private ImageView contactsImage;//在Tab布局上显示联系人图标的控件
		private ImageView newsImage;//在Tab布局上显示动态图标的控件
	//	private ImageView settingImage;//在Tab布局上显示设置图标的控件
		private TextView messageText;//在Tab布局上显示消息标题的控件
		private TextView contactsText;//在Tab布局上显示联系人标题的控件
		private TextView newsText;//在Tab布局上显示动态标题的控件
		//private TextView settingText;//在Tab布局上显示设置标题的控件
		private FragmentManager fragmentManager;//用于对Fragment进行管理

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);		
			setContentView(R.layout.activity_body);
			MyApplication.getInstance().addActivity(this);
			// 初始化布局元素
			/*//添加标签
			 bar = getActionBar();
			//设置为Tab模式
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			//新建2个Tab
			tabE = bar.newTab().setText(R.string.essay);
			tabC = bar.newTab().setText(R.string.composition);
			//绑定到Fragment
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
			
			
			// 第一次启动时选中第i个tab
			int i = getIntent().getExtras().getInt("chosenflag");
			setBottomLayoutSelection(i);
			
			//启动后台服务
			
			//读取数据 （专有类 填充）
			
			
			//读取网络地址
			
			
		}
		
		
		
		
		
		public void findViews(){
			
			messageLayout = findViewById(R.id.diary_layout); //日记
			contactsLayout = findViewById(R.id.contacts_layout); //学伴
			newsLayout = findViewById(R.id.writingzone_layout);//园地
			//settingLayout = findViewById(R.id.setting_layout);设置
			
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
				// 当点击了  写作园地   时，选中第1个layout
				setBottomLayoutSelection(0);
				break;
				
			case R.id.contacts_layout:
				// 当点击了网络学伴时，选中第2个layout
				setBottomLayoutSelection(1);
				break;
				
			case R.id.diary_layout:
				// 当点击了我的日记时，选中第3个layout
				setBottomLayoutSelection(2);
				break;
			
			default:
				break;
			}
		}

		/**
		 * 根据传入的index参数来设置选中的tab页。
		 * 
		 * @param index
		 *            每个layout对应的下标。0表示写作园地，1表示网络学伴，2表示我的日记。
		 */
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		private void setBottomLayoutSelection(int index) {
			// 每次选中之前先清楚掉上次的选中状态
			clearSelection();
			// 开启一个Fragment事务
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
			hideFragments(transaction);
			switch (index) {
			case 0:
				// 当点击了动态tab时，改变控件的图片和文字颜色
				newsImage.setImageResource(R.drawable.news_selected);
				newsText.setTextColor(Color.WHITE);
				/*if (writingzoneFragment == null) {
					// 如果NewsFragment为空，则创建一个并添加到界面上
					writingzoneFragment = new WritingzoneFragment();
					transaction.add(R.id.content, writingzoneFragment);*/
					//判断 如果actionBar没有tab  添加
				    bar = getActionBar();
					if(bar.getSelectedTab()==null){
					//设置为Tab模式
					bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
					//新建2个Tab
					tabE = bar.newTab().setText(R.string.essay);
					tabC = bar.newTab().setText(R.string.composition);
					//绑定到Fragment
					fEssy = new EssayFragment();
					
					fComposition = new CompositionFragmentTab();
					tabE.setTabListener(new MyTabsListener(fEssy));
					
					tabC.setTabListener(new MyTabsListener(fComposition));
					bar.addTab(tabE);
					bar.addTab(tabC);}
					
					
					
				/* else {
					// 如果NewsFragment不为空，则直接将它显示出来
					transaction.show(writingzoneFragment);
				}
				*/
				break;
			case 1:
				// 当点击了联系人tab时，改变控件的图片和文字颜色
				contactsImage.setImageResource(R.drawable.contacts_selected);
				contactsText.setTextColor(Color.WHITE);
				if (contactsFragment==null) {
					// 如果ContactsFragment为空，则创建一个并添加到界面上
					contactsFragment = new ContactsFragment();
					transaction.add(R.id.content, contactsFragment);
					
					
				} else {
					// 如果ContactsFragment不为空，则直接将它显示出来
					transaction.show(contactsFragment);
				}
				
				if(getActionBar().getSelectedTab()!=null){
					getActionBar().removeAllTabs();}
				
				break;
			case 2:
				// 当点击了消息tab时，改变控件的图片和文字颜色
				messageImage.setImageResource(R.drawable.message_selected);
				messageText.setTextColor(Color.WHITE);
				if (diaryFragment == null) {
					// 如果MessageFragment为空，则创建一个并添加到界面上
					diaryFragment = new DiaryFragment();
					transaction.add(R.id.content, diaryFragment);
					
					
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
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
		 * 清除掉所有的选中状态。
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
		 * 将所有的Fragment都置为隐藏状态。
		 * 
		 * @param transaction
		 *            用于对Fragment执行操作的事务
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
		        
		        menu.add(0,MENU_ABOUT,0,"关于");
				menu.add(0,MENU_Quit,0,"退出");
				
		        MenuItem addDiary = menu.add(1, 1, 1,R.string.writeDiary);  
			       addDiary.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			      MenuItem addEssay = menu.add(1, 2, 1, R.string.writeEssay);  
			       addEssay.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			       MenuItem addComposition = menu.add(1, 3, 1, R.string.writeComposition);  
			       addComposition.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		          
		        //给addMenuItem、deleteMenuItem和okMenuItem设置单击事件监听  
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
