package njnu.det.newvision;

import android.app.Activity;
import android.os.Bundle;

public class UserZone extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userzone_layout);
		 MyApplication.getInstance().addActivity(this);
	}

}
