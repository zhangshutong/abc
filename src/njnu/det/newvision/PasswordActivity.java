package njnu.det.newvision;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PasswordActivity extends Activity {
	Button btnConfirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_layout);
		MyApplication.getInstance().addActivity(this);
		
		btnConfirm = (Button)findViewById(R.id.pwdConfirm);
		btnConfirm.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(PasswordActivity.this, "«Î»•” œ‰»∑»œ", Toast.LENGTH_SHORT)
				.show();
				
			}
			
		});
	}

}
