package njnu.det.newvision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	
	View writingzone;
	View mydiary;
	View partener;
	View myzone; 
	View setting;
	
	protected void  onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.getInstance().addActivity(this);
		mydiary     =   findViewById(R.id.imageButtonwdrj);
		writingzone =   findViewById(R.id.imageButtonxzyd);
		myzone      =   findViewById(R.id.imageButtongrtd);
		setting     =   findViewById(R.id.imageButtonxtsz);
		partener    =   findViewById(R.id.imageButtonwlxb);
		
		mydiary.setOnClickListener(this);
		partener.setOnClickListener(this);
		writingzone.setOnClickListener(this);
		myzone.setOnClickListener(this);
		setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.imageButtonxzyd: //园地
			Goto(0);
			break;
		case R.id.imageButtonwlxb:  //网络学伴
			Goto(1);
			break;
		case R.id.imageButtonwdrj:  //日记
			Goto(2);
			break;		
		case R.id.imageButtongrtd: //个人天地
			startActivity(new Intent().setClass(MainActivity.this,UserZone.class));
			break;
		case R.id.imageButtonxtsz:  // 设置
			//Goto(4);
			startActivity(new Intent().setClass(MainActivity.this,AndroidPreferences.class));
			break;
			default:
				Goto(0);
	}
}

	private void Goto(int i) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, BodyActivity.class);
		intent.putExtra("chosenflag", i);
		startActivity(intent);
		
	}

}
