package njnu.det.newvision;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindpwdActivity extends Activity {
	private EditText account;
	private EditText question;
    private EditText answer;
    private EditText passwd;
    private EditText passwd2;
    private Button btnConfirm;
    boolean verify = false;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd_layout);
		
		account = (EditText)findViewById(R.id.edtFindAccount);
		account.setText(NV_Host.owner.NikedName);
		account.setEnabled(false);
		
		question = (EditText)findViewById(R.id.edtFindQuestion);
		question.setText(NV_Host.owner.Question);
		question.setEnabled(false);
		
		passwd = (EditText) findViewById(R.id.edtPasswd);
		passwd.setEnabled(false);
		
		passwd2 = (EditText) findViewById(R.id.edtConfirm);
		passwd2.setEnabled(false);
		
		answer = (EditText)findViewById(R.id.edtFindAnswer);
		btnConfirm = (Button)findViewById(R.id.btFindPwd);
		
		btnConfirm.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!verify){
					String sAccount = account.getText().toString().trim();
					String sQuestion = question.getText().toString().trim();
					String sAnswer = answer.getText().toString().trim();
					if("".equals(sAnswer)){ 
		                answer.setError(getText(R.string.answer)); 
		                return;
		            }
					try {//以下校验密码
						sAnswer = Utility.getMD5(sAnswer);
		  	        	if(!Verify(sAccount,sQuestion,sAnswer)){
		  	        		//提示错误
		  	        		Toast.makeText(FindpwdActivity.this, getText(R.string.answerRong), Toast.LENGTH_SHORT).show();	  	        		
		  	        		return;
		  	        	}
		  	        	//通过校验，允许修改密码
		  	        	verify = true;
		  	        	answer.setEnabled(false);
		  	        	passwd.setHint(getText(R.string.newPasswd));
		  	        	passwd.setEnabled(true);
		  	        	passwd2.setEnabled(true);
		  	        	btnConfirm.setText(getText(R.string.update));
		  	        } catch (Exception e) {
						// TODO Auto-generated catch block
						Log.v("KBMP","[FindPasswd]" + e.getMessage());
					}
				}
				else{
					String passwd_1 = passwd.getText().toString().trim();
					String passwd_2 = passwd2.getText().toString().trim();
					if("".equals(passwd_1)){
						passwd.setError(getText(R.string.PasswdPrompt));
						return;
					}
					if(!passwd_1.equals(passwd_2)){
						passwd2.setError(getText(R.string.passwdVerify));
						return;
					}
					//写入数据库
					String sAccount = account.getText().toString().trim();
					try {
						updatePasswd(sAccount,Utility.getMD5(passwd_1));
						
						Toast.makeText(FindpwdActivity.this, getText(R.string.updatePassword), Toast.LENGTH_SHORT).show();
						
						//更新成功后转入登陆页面
						Intent intent=new Intent();
					    intent.setClass(FindpwdActivity.this, LoginActivity.class);
					    startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.v("KBMP","[FindPasswd]" + e.getMessage());
					}
				}//end if
			}//end Click
		});
	}
    //更新Passwd
    private void updatePasswd(String user,String passwd) throws Exception {
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
		xml.text(DAO.OperationType.UPDATE.toString());
		xml.endTag("","Operation");
		
		xml.startTag("", "Condition");
		xml.text(" Account='" + user + "'");
		xml.endTag("", "Condition");
		
		xml.startTag("", "Theader");
		xml.startTag("", "col");
		xml.attribute("", "type",Table.DataType.TEXT.toString());
		xml.text("Password");
		xml.endTag("", "col");
		xml.endTag("", "Theader");
		
		//Table body Section is table body
		xml.startTag("","Tbody");
		xml.startTag("","Row");
		
		xml.startTag("","Td");
		xml.text(passwd);
		xml.endTag("","Td");
		
		xml.endTag("","Row");
		xml.endTag("","Tbody");
		xml.endTag("", "Table");
		
		xml.flush();
		dao.SetXML(sw.toString());
		dao.Excute();
	}
    //检查是否存在
	private  boolean Verify(String user,String que,String answer) throws Exception{
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
		xml.text(" Account='" + user +"' and Question='" + que + "' and Answer='" +
				answer + "'");
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
}
