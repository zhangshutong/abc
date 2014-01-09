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
	//系统初始化部分  
	public void init() throws Exception{
		String init = Utility.getParameter("init");
		if("none".equals(init) || (!"1".equals(init))){//未初始化
			//创建数据据库及各种表和索引
			String dbPath = null;
			String dbName= null; //"NewVision.db"
			PackageManager pm = getApplication().getApplicationContext().getPackageManager();
			ApplicationInfo  inf = pm.getApplicationInfo(getPackageName(),0);
			String appName = (String)pm.getApplicationLabel(inf);
			String dataFiles = null;
			//先判断是否有SD卡，若有存SD，若无存data下。
			if(Utility.existSD()){//若有SD卡，则存储在SD中，没有则存放在应用程序目录中
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
				//创建用户表
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
			//写参数
			Utility.setParameter("DatabasePath", dbPath);
			Utility.setParameter("DatabaseName", dbName);
			//生成图片文件夹
			String imgPath= dataFiles + "/images";
			File imgFile = new File(imgPath);
			if(!imgFile.exists())
				if(!imgFile.mkdirs())
					throw new Exception("[Init]创建图片目录失败！");
			Utility.setParameter("Images",imgPath);//保存图片文件的路径
			Utility.setParameter("init", "1"); //初始化标志
			
			//将登记按钮和忘记密码置为Disable，只有注册可以用
			btLogin.setEnabled(false);
		    tvFindPwd.setEnabled(false);
			}
		    catch(Exception e){
				throw new Exception("[Init]" +getString(R.string.initError) +e.getMessage());
			}
		}
		
		//正常进入系统
		//读取数据库参数
		NV_Host.setDbPath(Utility.getParameter("DatabasePath"));
		NV_Host.setDbName(Utility.getParameter("DatabaseName"));
		//读取图片文件存取路径 
		NV_Host.setImgPath(Utility.getParameter("Images"));
		//读取中心服务器地址
		NV_Host.setServer_IP(Utility.getParameter("ServerIP"));
		//当前操作的是本地文章
		NV_Host.isLocal = true;
		
		//验证注册码
		String serialNo = Utility.getParameter("SerialNo");
		String regCode = Utility.getParameter("RegCode");
		if("none".equals(regCode) || (!regCode.equals(Utility.getRegCode(serialNo)))){
			NV_Host.Registflag = false;
			setTitle(getTitle() + getString(R.string.unregist));
		}
		else 
			NV_Host.Registflag = true;
		
		//预读取用户名称
		Table userInfo = getUserInfo(null);
		String users=">";
		for(int i=0; i< userInfo.getRowSize();i++){
			users +=userInfo.getRow(i).getString(2) + ">"; //第3列是账号
		}
		account.setHint(users); //显示已有用户，给登录以提示
		
		//测试参数--------------------------------------------------
		Log.i("KBMP","Users:" + users);
		Log.v("KBMP",NV_Host.getDbName());
		Log.v("KBMP",NV_Host.getDbPath());
		Log.v("KBMP",NV_Host.getImgPath());
		Log.v("KBMP","注册："+ NV_Host.getRegistflag());
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
	    btLogin=(Button) findViewById(R.id.btLogin);	//登录按钮	
		tvFindPwd=(TextView)findViewById(R.id.forgetPassword);	//	
		tvRegister=(TextView)findViewById(R.id.register);
	    
		try {
  			init();//初始化
  		} catch (Exception e) {
  			 //TODO Auto-generated catch block
  			Log.v("KBMP",e.getMessage());
  		}
  		
  		//忘记密码按钮
  		tvFindPwd.setOnClickListener(new TextView.OnClickListener(){
			public void onClick(View v) {
				//先判断用户名是否为空
				String ac = account.getText().toString().trim();
	  	        if("".equals(ac)){ 
	                account.setError(getString(R.string.AccountPrompt)); 
	                return;
	            }
				//检查是否存在该用户
	  	        try {
					Table table = getUserInfo(ac);
					if(table.getRowSize()<=0){
						account.setError(getText(R.string.NoAccount));
						return;
					}
					User user = new User();
					user.NikedName = table.getRow(0).getString(2); //顺序不能错
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
  		
  		//注册密码
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
	  	        try {//以下校验密码
	  	        	pa = Utility.getMD5(pa);
	  	        	Table table = getUserInfo(ac);
	  	        	boolean hasUser = false;
	  	        	Row row = null; 
	  	        	if(table.getRowSize() > 0){
	  	        		row = table.getRow(0);//第0列为Studnet_ID，第2、3列分别为账号和密码
	  	        		if(ac.equals(row.getString(2)) && pa.equals(row.getString(3)))
	  	        			hasUser = true;
	  	        	}
	  	        	
	  	        	if(!hasUser){
	  	        		//提示错误
	  	        		Toast.makeText(LoginActivity.this, getText(R.string.verifyUser), Toast.LENGTH_SHORT).show();
	  	        		return;
	  	        	}
	  	        	
	  	        	//读取用户信息
		  	        //顺序为0:Student_ID/1:Name/2:Account/3:Password/4:Class_ID
		  	      	//5:Email/6:Question/7:Answer
	  	        	User user = new User();
	  	        	user.student_ID = row.getInt(0);
	  	        	user.userName = row.getString(1);
	  	        	user.NikedName = ac;
	  	        	user.class_ID=row.getInt(4);  
	  	        	user.E_Mail = row.getString(5);
	  	        	
	  	        	//从班级表中读取ClassName信息
	  	        	//user.className = "";
	  	        	//读取心情短语
	  	        	//user.mood="";
	  	        	
	  	        	NV_Host.setOwner(user);
	  	        	NV_Host.setLocalUser(user);
	  	        	NV_Host.isLocal=true;
	  	        	
	  	        	//登录成功，启动主界面
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
	
	
	//顺序为0:Student_ID/1:Name/2:Account/3:Password/4:Class_ID
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
		
		//如果指定账号则按账号查找
		if(findUser != null && !"".equals(findUser)){
			xml.startTag("", "Condition");
			xml.text("Account='" + findUser + "'");
			xml.endTag("", "Condition");
		}
		
		//Table header section
		xml.startTag("","Theader");
		
		//第0列：School_ID
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Student_ID");
		xml.endTag("", "col");
		
		//第1列:Name
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Name");
		
		//第2列：Account
		xml.endTag("", "col");
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Account");
		xml.endTag("", "col");
		
		//第3列：Password
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Password");
		xml.endTag("", "col");
		
		//第4列：Class_ID
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Class_ID");
		xml.endTag("", "col");
		
		//第5列：EMail
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Email");
		xml.endTag("", "col");
		
		//第6列：Question
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("Question");
		xml.endTag("", "col");
		
		//第7列：Answer
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
