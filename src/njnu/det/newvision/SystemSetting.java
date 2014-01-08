package njnu.det.newvision;
import android.app.Activity;
import android.os.Bundle;


public class SystemSetting extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		MyApplication.getInstance().addActivity(this);
	}

}
