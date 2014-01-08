/*
 *@author Tian Jun-hua
* @version 1.0
 *annotation:
 * 在manifest的<application>中加入android：name="<package>.ContextUtil",可在任何一个类下面获取Context
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
