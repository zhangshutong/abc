package njnu.det.newvision;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

public class AndroidPreferences extends Activity implements OnPreferenceClickListener {// 
	CheckBoxPreference openService;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragement()).commit();  
	}

	 public static class PrefsFragement extends PreferenceFragment{  
	        @Override  
	        public void onCreate(Bundle savedInstanceState) {  
	            // TODO Auto-generated method stub  
	            super.onCreate(savedInstanceState);  
	            addPreferencesFromResource(R.xml.preferences);  
	        }  
	    }  
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		Log.d("abc-open_service", "服务按钮被点击");
		return false;
	}

	
}
