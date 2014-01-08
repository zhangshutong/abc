package njnu.det.newvision;
import java.io.File;
import java.security.MessageDigest;
import java.util.UUID;

import njnu.det.newvision.ContextUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;

public class Utility {
	public static Context getAppContext(){
		Context ct = ContextUtil.getInstance().getApplicationContext();
		return ct;
	}
	
	//�������ò���
	public static void setParameter(String name,String value) throws Exception{
		try{
			Context ct = ContextUtil.getInstance().getApplicationContext();
			SharedPreferences sp = ct.getSharedPreferences(name,Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString(name,value);
			editor.commit();
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[Uitlity]�������ݴ���" + e.getMessage());
		}
	}
	
	//��ȡ���ò���
	public static String getParameter(String name){
		Context ct = ContextUtil.getInstance().getApplicationContext();
		SharedPreferences sp = ct.getSharedPreferences(name,Context.MODE_PRIVATE);
		String s = sp.getString(name,"none");
		return s;
	}
	//����Ƿ����SD��
	public static Boolean existSD(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else 
			return false;
	}
		
	//��ȡSD��ʣ��ռ��С
	public static long getSDFreeSize(){
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return 0;
		String filePath = Environment.getExternalStorageDirectory().getPath();
		StatFs statFs = new StatFs(filePath);
		long size = statFs.getBlockSize();
		long available = statFs.getAvailableBlocks()-4;
		return size * available;
	}
	
	//��ȡ�豸ID
	public static String getDeviceID(){
		return Secure.getString(getAppContext().getContentResolver(),Secure.ANDROID_ID);
	}
	
	//��ȡ���ڴ��С
	public static long getMemorySize(){
		String rootPath = Environment.getRootDirectory().getAbsolutePath();
		StatFs sFs = new StatFs(rootPath);
		long bSize = sFs.getBlockSize();
		long bCount = sFs.getBlockCount();
		return bSize * bCount;
	}
	
	//��ȡ����ʣ��ռ�
	public static long getFreeMemSize(){
		String rootPath = Environment.getRootDirectory().getAbsolutePath();
		StatFs sFs = new StatFs(rootPath);
		long bSize = sFs.getBlockSize();
		long bFree = sFs.getAvailableBlocks();
		return bSize * bFree;
	}
	
	//����MD5��
	public static String getMD5(String s) throws Exception{
		final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(s.getBytes());
		byte[] md5Bytes = md.digest();
		
		StringBuilder sb = new StringBuilder(md5Bytes.length * 2);    
	    for (int i = 0; i < md5Bytes.length; i++) {    
	        sb.append(HEX_DIGITS[(md5Bytes[i] & 0xf0) >>> 4]);    
	        sb.append(HEX_DIGITS[md5Bytes[i] & 0x0f]);    
	    }
	    char[] c = sb.toString().toCharArray();
	    
	    //��ż��λ����
	    char tc;
	    for(int i=0;i<c.length;i+=2){
	    	tc = c[i];
	    	if(i<c.length-1){
	    		c[i] = c[i+1];
	    		c[i+1] = tc;
	    	}
	    }
	    //��β����
	    for(int i=0;i<c.length/2;i++){
	    	tc=c[i];
	    	c[i]=c[c.length-1-i];
	    	c[c.length-1-i]=tc;
	    }
	    return String.valueOf(c);
	}
	
	//����Ψһ��ʶ��
	public static String getUID() throws Exception{
		UUID uid = UUID.randomUUID();
		return getMD5(uid.toString());
	}
	
	//����ע����
	public static String getSerialNo() throws Exception{
		String device = getDeviceID();
		String memSize = String.valueOf(getMemorySize());
		String s = getMD5(device + memSize);
		String code= "";
		for(int i=1;i<s.length();i+=3){
			code += s.charAt(i);
		}
		char[] c = code.toCharArray();
		if(c.length>=7){
			c[3] = '-';
			c[6] ='-';
		}
		return String.valueOf(c);
	}
	
	//����ע����
	protected  static String getRegCode(String code) throws Exception{
		String md5 = getMD5(code);
		String s = "";
		for(int i=0;i<md5.length();i+=3){
			s += md5.charAt(i);
		}
		StringBuilder sb = new StringBuilder();
		char[] c = s.toCharArray(); 
		for(int i=0;i<c.length; i=i+2){
			sb.append(c[i]);
		}
		for(int i=1;i<c.length;i+=2){
			sb.append(c[i]);
		}
		
		c =sb.toString().toCharArray();
		if(c.length>10){
			char[] x={'X','G','H','M','E','Q','J','P','D','F'};
			//1-5����
			char cc=c[5];
			c[5] = c[1];
			c[1] = cc;
			//2-6����
			cc = c[6];
			c[6] = c[2];
			c[2] = cc;
			//����0��2��5��6��9�û�Ϊx����ĸ
			if(c[0]>48 && c[0]<57)
				c[0] = x[c[0]-48];
			if(c[2]>48 && c[2]<57)
				c[2] = x[c[2]-48];
			if(c[5]>48 && c[5]<57)
				c[5] = x[c[5]-48];
			if(c[6]>48 && c[6]<57)
				c[6] = x[c[6]-48];
			if(c[9]>48 && c[9]<57)
				c[9] = x[c[9]-48];
			
			c[3] = '-';
			c[6] = '-';
		}
		return String.valueOf(c);
	}
	
	public static void delFile(String file) throws Exception{
		File f = new File(file);
		if(!f.exists())
			throw new Exception("[Utility]ָ�����ļ����ļ��в����ڣ�");
		
		if(f.isFile()){
			if(f.canWrite())
				f.delete();
			else 
				throw new Exception("Utilityָ�����ļ���Ȩɾ����");
		}
		
		if(f.isDirectory()){
			if(!f.canWrite())
				throw new Exception("[Utility]ָ�����ļ�����Ȩɾ����");
			else {
				File[] files = f.listFiles();
				for(int i=0;i<files.length;i++){
					files[i].delete();
				}
				f.delete();
			}
		}//end if
	}//end delfile
}
