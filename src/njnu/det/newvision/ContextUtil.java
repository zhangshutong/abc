/*
 *@author Tian Jun-hua
* @version 1.0
 *annotation:
 * ��manifest��<application>�м���android��name="<package>.ContextUtil",�����κ�һ���������ȡContext
 */
package njnu.det.newvision;

import njnu.det.newvision.ContextUtil;
import android.app.Application;

public class ContextUtil extends Application{
	static ContextUtil instance;
	public static  ContextUtil getInstance(){
		return instance;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}
	
}
