package njnu.det.newvision;
import java.io.File;
import java.io.StringWriter;

import njnu.det.newvision.Table.Row;
import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
	private Button btLogin;
	private TextView tvRegister;
	private TextView tvFindPwd;
	private EditText account;
	private EditText password;
	//ϵͳ��ʼ������  
	public void init() throws Exception{
		String init = Utility.getParameter("init");
		if("none".equals(init) || (!"1".equals(init))){//δ��ʼ��
			//�������ݾݿ⼰���ֱ������
			String dbPath = null;
			String dbName= null; //"NewVision.db"
			PackageManager pm = getApplication().getApplicationContext().getPackageManager();
			ApplicationInfo  inf = pm.getApplicationInfo(getPackageName(),0);
			String appName = (String)pm.getApplicationLabel(inf);
			String dataFiles = null;
			//���ж��Ƿ���SD�������д�SD�����޴�data�¡�
			if(Utility.existSD()){//����SD������洢��SD�У�û��������Ӧ�ó���Ŀ¼��
				dataFiles = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + appName +"/";
				dbPath =  dataFiles + "database/";
			}
			else {
				dataFiles = getApplication().getApplicationContext().getFilesDir().getAbsolutePath().replace("/files","/");
				dbPath = dataFiles + "databases/";
			}
			dbName = appName + ".db";
			try{
				DAO dao = new DAO(dbPath,dbName);
				//�����û���
				String sql1 = "CREATE TABLE IF NOT EXISTS user (" +
						"ID INTEGER PRIMARY KEY UNIQUE," + 
						"Student_ID INTEGER,Name TEXT,Account TEXT," +
						"Password TEXT,Class_ID INTEGER,Email TEXT,Question TEXT,Answer TEXT);";
				dao.CreateTable(sql1,"Admin");
				
//				String sql2 = "CREATE TABLE IF NOT EXISTS class (" +
//						"ID INTEGER PRIMARY KEY UNIQUE, " + 
//						" Name TEXT,Grade TEXT);";
//				dao.CreateTable(sql2,"Admin");
//				
				String sql3 = "CREATE TABLE IF NOT EXISTS writing (" +
						"ID Text PRIMARY KEY UNIQUE, " + 
						"Title TEXT,Keywords TEXT,Author TEXT,Date DATE,Content TEXT,EditDate DATE,Accessory_ID TEXT,Synctime DATE);";
					
				dao.CreateTable(sql3,"Admin");
				
				String sql4 = "CREATE TABLE IF NOT EXISTS essay (" +
						"ID Text PRIMARY KEY UNIQUE, " + 
						"Title TEXT,Keywords TEXT,Author TEXT,Date DATE,Content TEXT,EditDate DATE,Accessory_ID TEXT,Synctime DATE);";
					
				dao.CreateTable(sql4,"Admin");
				
				
//				
//				String sql4 = "CREATE TABLE IF NOT EXISTS comment (" +
//						"ID INTEGER PRIMARY KEY UNIQUE, " + 
//						" Number TEXT,Content TEXT,Author TEXT,Writing_number TEXT,Date DATE);";
//				sql4 +="CREATE UNIQUE INDEX IF NOT EXISTS idIndex ON comment (id);";	
//				dao.CreateTable(sql4,"Admin");
//				
//				String sql5 = "CREATE TABLE IF NOT EXISTS accessory (" +
//						"ID INTEGER PRIMARY KEY UNIQUE, " + 
//						" Size INTEGER,Location TEXT,Date DATE);";
//				sql5 +="CREATE UNIQUE INDEX IF NOT EXISTS idIndex ON accessory (id);";	
//				dao.CreateTable(sql5,"Admin");
//				
//				String sql6 = "CREATE TABLE IF NOT EXISTS category (" +
//						"ID INTEGER PRIMARY KEY UNIQUE, " + 
//						" Code TEXT,Name TEXT);";
//				sql6 +="CREATE UNIQUE INDEX IF NOT EXISTS idIndex ON accessory (id);";	
//				dao.CreateTable(sql6,"Admin");
//				
//				String sql7 = "CREATE TABLE IF NOT EXISTS modify(" +
//						"ID INTEGER PRIMARY KEY UNIQUE, " + 
//						" Number TEXT,Content TEXT,Author TEXT,Writing_number TEXT,Date DATE,Synctime DATE);";
//				sql7 +="CREATE UNIQUE INDEX IF NOT EXISTS idIndex ON modify (id);";	
//				dao.CreateTable(sql7,"Admin");
			//д����
			Utility.setParameter("DatabasePath", dbPath);
			Utility.setParameter("DatabaseName", dbName);
			//����ͼƬ�ļ���
			String imgPath= dataFiles + "/images";
			File imgFile = new File(imgPath);
			if(!imgFile.exists())
				if(!imgFile.mkdirs())
					throw new Exception("[Init]����ͼƬĿ¼ʧ�ܣ�");
			Utility.setParameter("Images",imgPath);//����ͼƬ�ļ���·��
			Utility.setParameter("init", "1"); //��ʼ����־
			
			//���Ǽǰ�ť������������ΪDisable��ֻ��ע�������
			btLogin.setEnabled(false);
		    tvFindPwd.setEnabled(false);
			}
		    catch(Exception e){
				throw new Exception("[Init]" +getString(R.string.initError) +e.getMessage());
			}
		}
		
		//��������ϵͳ
		//��ȡ���ݿ����
		NV_Host.setDbPath(Utility.getParameter("DatabasePath"));
		NV_Host.setDbName(Utility.getParameter("DatabaseName"));
		//��ȡͼƬ�ļ���ȡ·�� 
		NV_Host.setImgPath(Utility.getParameter("Images"));
		//��ȡ���ķ�������ַ
		NV_Host.setServer_IP(Utility.getParameter("ServerIP"));
		//��ǰ�������Ǳ�������
		NV_Host.isLocal = true;
		
		//��֤ע����
		String serialNo = Utility.getParameter("SerialNo");
		String regCode = Utility.getParameter("RegCode");
		if("none".equals(regCode) || (!regCode.equals(Utility.getRegCode(serialNo)))){
			NV_Host.Registflag = false;
			setTitle(getTitle() + getString(R.string.unregist));
		}
		else 
			NV_Host.Registflag = true;
		
		//Ԥ��ȡ�û�����
		Table userInfo = getUserInfo(null);
		String users=">";
		for(int i=0; i< userInfo.getRowSize();i++){
			users +=userInfo.getRow(i).getString(2) + ">"; //��3�����˺�
		}
		account.setHint(users); //��ʾ�����û�������¼����ʾ
		
		//���Բ���--------------------------------------------------
		Log.i("KBMP","Users:" + users);
		Log.v("KBMP",NV_Host.getDbName());
		Log.v("KBMP",NV_Host.getDbPath());
		Log.v("KBMP",NV_Host.getImgPath());
		Log.v("KBMP","ע�᣺"+ NV_Host.getRegistflag());
		Log.v("KBMP","Local:"+ NV_Host.isLocal);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		MyApplication.getInstance().addActivity(this);
		account = (EditText)findViewById(R.id.edtAccount);
		account.setSingleLine(true);
		account.setMaxWidth(120);
		account.setEllipsize(TruncateAt.valueOf("END"));
	    password= (EditText)findViewById(R.id.edtPasswd);
	    btLogin=(Button) findViewById(R.id.btLogin);	//��¼��ť	
		tvFindPwd=(TextView)findViewById(R.id.forgetPassword);	//	
		tvRegister=(TextView)findViewById(R.id.register);
	    
		try {
  			init();//��ʼ��
  		} catch (Exception e) {
  			 //TODO Auto-generated catch block
  			Log.v("KBMP",e.getMessage());
  		}
  		
  		//�������밴ť
  		tvFindPwd.setOnClickListener(new TextView.OnClickListener(){
			public void onClick(View v) {
				//���ж��û����Ƿ�Ϊ��
				String ac = account.getText().toString().trim();
	  	        if("".equals(ac)){ 
	                account.setError(getString(R.string.AccountPrompt)); 
	                return;
	            }
				//����Ƿ���ڸ��û�
	  	        try {
					Table table = getUserInfo(ac);
					if(table.getRowSize()<=0){
						account.setError(getText(R.string.NoAccount));
						return;
					}
					User user = new User();
					user.NikedName = table.getRow(0).getString(2); //˳���ܴ�
					user.Question = table.getRow(0).getString(6);
					NV_Host.setOwner(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(LoginActivity.this, "[Login]" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, FindpwdActivity.class);
				startActivity(intent);
			}
		});
  		
  		//ע������
		tvRegister.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
					
			}
		});
		
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String ac = account.getText().toString().trim();
  	            String pa = password.getText().toString().trim();
	  	        if("".equals(ac)){ 
	                account.setError(getString(R.string.AccountPrompt)); 
	                return;
	            }
	            
	  	        if("".equals(pa)){ 
	     	          password.setError(getString(R.string.PasswdPrompt)); 
	                return; 
	            } 
	  	        try {//����У������
	  	        	pa = Utility.getMD5(pa);
	  	        	Table table = getUserInfo(ac);
	  	        	boolean hasUser = false;
	  	        	Row row = null; 
	  	        	if(table.getRowSize() > 0){
	  	        		row = table.getRow(0);//��0��ΪStudnet_ID����2��3�зֱ�Ϊ�˺ź�����
	  	        		if(ac.equals(row.getString(2)) && pa.equals(row.getString(3)))
	  	        			hasUser = true;
	  	        	}
	  	        	
	  	        	if(!hasUser){
	  	        		//��ʾ����
	  	        		Toast.makeText(LoginActivity.this, getText(R.string.verifyUser), Toast.LENGTH_SHORT).show();
	  	        		return;
	  	        	}
	  	        	
	  	        	//��ȡ�û���Ϣ
		  	        //˳��Ϊ0:Student_ID/1:Name/2:Account/3:Password/4:Class_ID
		  	      	//5:Email/6:Question/7:Answer
	  	        	User user = new User();
	  	        	user.student_ID = row.getInt(0);
	  	        	user.userName = row.getString(1);
	  	        	user.NikedName = ac;
	  	        	user.class_ID=row.getInt(4);  
	  	        	user.E_Mail = row.getString(5);
	  	        	
	  	        	//�Ӱ༶���ж�ȡClassName��Ϣ
	  	        	//user.className = "";
	  	        	//��ȡ�������
	  	        	//user.mood="";
	  	        	
	  	        	NV_Host.setOwner(user);
	  	        	NV_Host.setLocalUser(user);
	  	        	NV_Host.isLocal=true;
	  	        	
	  	        	//��¼�ɹ�������������
	  	        	Intent intent=new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
	  	        } catch (Exception e) {
					// TODO Auto-generated catch block
	  	        	Toast.makeText(LoginActivity.this, "[Login]" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}//end onClick
		});
		
	}//end onCreate
	
	
	//˳��Ϊ0:Student_ID/1:Name/2:Account/3:Password/4:Class_ID
	//5:Email/6:Question/7:Answer
	public Table  getUserInfo(String findUser) throws Exception{
		DAO dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		//Begin Root node
		xml.startTag("","Table");
		xml.attribute("","name","user")
		;
		//Operation Section
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.SELECT.toString());
		xml.endTag("","Operation");
		
		//���ָ���˺����˺Ų���
		if(findUser != null && !"".equals(findUser)){
			xml.startTag("", "Condition");
			xml.text("Account='" + findUser + "'");
			xml.endTag("", "Condition");
		}
		
		//Table header section
		xml.startTag("","Theader");
		
		//��0�У�School_ID
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Student_ID");
		xml.endTag("", "col");
		
		//��1��:Name
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Name");
		
		//��2�У�Account
		xml.endTag("", "col");
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Account");
		xml.endTag("", "col");
		
		//��3�У�Password
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Password");
		xml.endTag("", "col");
		
		//��4�У�Class_ID
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Class_ID");
		xml.endTag("", "col");
		
		//��5�У�EMail
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Email");
		xml.endTag("", "col");
		
		//��6�У�Question
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Question");
		xml.endTag("", "col");
		
		//��7�У�Answer
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Answer");
		xml.endTag("", "col");
		
		xml.endTag("", "Theader");
		xml.endTag("", "Table");
		
		xml.flush();
		dao.SetXML(sw.toString());
		dao.Excute();
		return dao.table;
	}
}
