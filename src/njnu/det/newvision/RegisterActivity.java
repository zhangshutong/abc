package njnu.det.newvision;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private ImageButton btRegister;
	private EditText account;
	private EditText password;
	private EditText confirm;
	private EditText email;	
	private EditText question;
	private EditText answer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		MyApplication.getInstance().addActivity(this);// 退出
		btRegister=(ImageButton)findViewById(R.id.btRegister);
		account = (EditText)findViewById(R.id.edtAccount);
	    password= (EditText)findViewById(R.id.edtPasswd);
	    confirm = (EditText)findViewById(R.id.edtConfirm);
	    email= (EditText)findViewById(R.id.edtEmail);
	    question= (EditText)findViewById(R.id.edtQuestion);
	    answer = (EditText)findViewById(R.id.edtAnswer);
	    
		btRegister.setOnClickListener(new ImageButton.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String sAcctount = account.getText().toString().trim();
  	            String sPasswd = password.getText().toString().trim();
  	            String sConfirm = confirm.getText().toString().trim();
	            String sEMail = email.getText().toString().trim();
	            String sQuestion = question.getText().toString().trim();
  	      	  	String sAnswer = answer.getText().toString().trim();
  	      	  	//数据校验
	           if( "".equals(sAcctount.trim())){ 
                    account.setError(getText(R.string.AccountPrompt)); 
                    return;                              
                }                                     
                if("".equals(sPasswd.trim())){ 
                	 password.setError(getText(R.string.PasswdPrompt)); 
                    return; 
                } 
                if("".equals(sConfirm.trim())){ 
                	 confirm.setError(getText(R.string.PasswdPrompt)); 
                    return; 
                } 
                
                if(!sConfirm.equals(sPasswd)){ 
                	confirm.setError(getText(R.string.passwdVerify)); 
                    return; 
                }
                if(sEMail!=null && "".equals(sEMail.trim())){ 
                	email.setError(getText(R.string.emailError)); 
                    return; 
                } 
                if( "".equals(sQuestion)){
                	question.setError(getText(R.string.questionError));
                	return;
                }
                if("".equalsIgnoreCase(sAnswer.trim())){
                	answer.setError(getText(R.string.answerError));
                	return;
                }

	            try {//以下校验
	  	        	if(hasUser(sAcctount)){
	  	        		//提示错误，存在该用户
	  	        		account.setError(getText(R.string.hasUser));
	  	        		return;
	  	        	}
	  	        	else{//校验成功
	            	     sPasswd = Utility.getMD5(sPasswd);
	            	     sAnswer = Utility.getMD5(sAnswer);
	  	        		 Insert(sAcctount, sPasswd, sEMail, sQuestion,sAnswer);	
	  	        		 Toast.makeText(RegisterActivity.this, getText(R.string.regSucess), Toast.LENGTH_SHORT).show();
	  	        	     
	  	        		 User user = new User();
	  	        	     user.NikedName = sAcctount;
	  	        	     NV_Host.setOwner(user);
	  	        	     //转登录页面
	  	        	     Intent intent=new Intent();
					     intent.setClass(RegisterActivity.this, LoginActivity.class);
					     startActivity(intent);}
	  	        } catch (Exception e) {
					// TODO Auto-generated catch block
					Log.v("KBMP",e.getMessage());
				}				
			}
		});
		
	}
	//
	public  boolean hasUser(String user) throws Exception{
		DAO dao = new DAO(NV_Host.getDbPath(),NV_Host.getDbName());
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		//Begin Root node
		xml.startTag("","Table");
		xml.attribute("","name","user");
		//Operation Section
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.SELECT.toString());
		xml.endTag("","Operation");
		xml.startTag("", "Condition");
		xml.text(" Account='" + user +"'");
		xml.endTag("", "Condition");
		xml.startTag("", "Theader");
		
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.NUMBER.toString());
		xml.text("ID");
		xml.endTag("", "col");
		
		xml.endTag("", "Theader");
		xml.endTag("", "Table");
		xml.flush();
		dao.SetXML(sw.toString());
		dao.Excute();
		if(dao.table.getRowSize()>0)
			return true;
		else 
			return false;
	}	
	//写入记录
	//顺序为0:Student_ID/1:Name/2:Account/3:Password/4:Class_ID
	//5:Email/6:Question/7:Answer
	public void Insert(String ancount,String pw,String email,String question,String answer) throws Exception{
		DAO dao = new DAO(NV_Host.DbPath,NV_Host.getDbName());
		XmlSerializer xml = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		xml.setOutput(sw);
		xml.startDocument("UTF-8",true);
		xml.startTag("","Table");
		xml.attribute("","name","user");
		xml.startTag("","Operation");
		xml.text(DAO.OperationType.ADDROW.toString());
		xml.endTag("","Operation");	
		//Table header section
		xml.startTag("","Theader");
		
		//0:Student_ID
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.NUMBER.toString());
		xml.text("Student_ID");
		xml.endTag("","col");
		
		//1:Name
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Name");
		xml.endTag("","col");
		
		//2:Ancount
  		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Account");
		xml.endTag("","col");
		
		//3:Password
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Password");
		xml.endTag("","col");
		
		//4:Class_ID
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.NUMBER.toString());
		xml.text("Class_ID");
		xml.endTag("","col");
		
		//5:Email
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Email");
		xml.endTag("","col");
		
		//6:Question
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Question");
		xml.endTag("","col");
		
		//7:Answer
		xml.startTag("","col");
		xml.attribute("","type",Table.DataType.TEXT.toString());
		xml.text("Answer");
		xml.endTag("","col");
		
		xml.endTag("","Theader");
		//end table header
		
		//Table body Section			
		xml.startTag("","Tbody");
		xml.startTag("","Row");
		
		//0.Student_ID
		xml.startTag("","Td");
		xml.text("0");
		xml.endTag("","Td");
		
		//1.Name
		xml.startTag("","Td");
		xml.text("");
		xml.endTag("","Td");
		
		//2.Ancount
		xml.startTag("","Td");
		xml.text(ancount);
		xml.endTag("","Td");
		
		//3.Password
		xml.startTag("","Td");
		xml.text(pw);
		xml.endTag("","Td");
		
		//4.Class_ID
		xml.startTag("","Td");
		xml.text("0");
		xml.endTag("","Td");
		
		//5.Email
		xml.startTag("","Td");
		xml.text(email);
		xml.endTag("","Td");
		
		//6.Question				
		xml.startTag("","Td");
		xml.text(question);
		xml.endTag("","Td");
		
		//7.Answer
		xml.startTag("","TD");
		xml.text(answer);
		xml.endTag("","TD");
		
		xml.endTag("","Row");
		xml.endTag("","Tbody");
		xml.endTag("","Table");
		xml.flush();
		
		dao.SetXML(sw.toString());
		dao.Excute();
		Log.i("KBMP","注册成功！");
	}	                  
}
