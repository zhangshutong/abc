package njnu.det.newvision;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

//Usage:
//��onCreate()��onResum()�����е���bind(this)��������onPause()�е���unbind(this)����
//��setNetworkAvailableListener(this),��ʵ��OnNetworkAvailableListener�ӿڣ�
//��ô��������״̬�����仯ʱ���ӿڷ������Զ�ִ��
public class WIFIState extends  BroadcastReceiver{
	public enum NetState{UNKNOWN,WIFI_CONNECTED,WIFI_CONNECTING,WIFI_CLOSING,WIFI_CLOSED}
	String IP="0.0.0.0";
	public String NetName=""; //�������� 
	WifiManager wm;
	NetState Ntstate = NetState.UNKNOWN;
	
	OnNetworkAvailableListener netListener;
	public static interface OnNetworkAvailableListener{
		public  void OnNetworkAvailable();
		public  void OnNetworkUnavailable();
	}
	
	//��������
	public void connectWiFi (Context context)throws Exception{
		try {
			detectWiFiState(context);
			if(NetState.WIFI_CLOSED == Ntstate){
				WifiManager wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				wm.setWifiEnabled(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[Wi-Fi State]�����������\n" + e.getMessage());
		}
		
	}
	//�ر�WiFi
	public void closeWiFi(Context context) throws Exception{
		try {
			detectWiFiState(context);
			if(NetState.WIFI_CONNECTED == Ntstate){
				WifiManager wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				wm.setWifiEnabled(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[Wi-Fi State]�ر��������\n" + e.getMessage());
		}
	}
	//��ȡ����״̬.����������������״̬ʱ��������ʱ�޷����IP��ַ
	public  NetState detectWiFiState(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm == null){
			Ntstate = NetState.UNKNOWN;
			return Ntstate;
		}
		else{
			//�����������״̬����ȡIP
			wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			if(wm == null){
				Ntstate = NetState.UNKNOWN;
				return Ntstate;
			}
			
			int st = wm.getWifiState();
			switch (st) {
			case WifiManager.WIFI_STATE_DISABLED://Wifi�ѹر�
				Ntstate = NetState.WIFI_CLOSED;
				NetName = "";
				break;
			case WifiManager.WIFI_STATE_DISABLING: //wifi���ڹر�
				Ntstate = NetState.WIFI_CLOSING;
				NetName = "";
				break;
			case WifiManager.WIFI_STATE_ENABLED: //wifi������
				Ntstate = NetState.WIFI_CONNECTED;
				//��ȡIP��ַ
				WifiInfo wi = wm.getConnectionInfo();
				NetName = wi.getSSID();
				int ipAddr =wi.getIpAddress(); 
				IP = String.valueOf(ipAddr & 0xff) + "." + String.valueOf((ipAddr>>8) & 0xff) + "." + 
							String.valueOf((ipAddr>>16) & 0xff) + "." + String.valueOf((ipAddr>>24) & 0xff);
				break;
			case WifiManager.WIFI_STATE_ENABLING: //wifi��������
				Ntstate = NetState.WIFI_CONNECTING;
				NetName = "";
				break;
			default: //wifiδ֪״̬
				NetName = "";
				Ntstate = NetState.UNKNOWN;
			}
		}
		return Ntstate;
	}
	public void bind(Context context){
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(this,iFilter);
		detectWiFiState(context);
	}
	
	public void unbind(Context context){
		context.unregisterReceiver(this);
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
			detectWiFiState(context);
			if(netListener !=null)
				if(NetState.WIFI_CONNECTED.equals(Ntstate))
					netListener.OnNetworkAvailable();
				else 
					netListener.OnNetworkUnavailable();
		}//end action
	}
	//����ʵ��ʱ��Ҫͬʱʵ�ֽӿڷ���
	public void setNetworkAvailableListener(OnNetworkAvailableListener listener){
		netListener = listener;
	}
	//��ȡ�����п��ܵ�IP��ַ��Ĭ��ΪC���ַ
	public String[] getNeighborHosts(Context context){
		detectWiFiState(context);//�������״̬
		if(IP.startsWith("0.0"))//û���IP��ַ
			return null;
		
		StringBuilder sb = new StringBuilder();
		String net = IP.substring(0,IP.lastIndexOf(".")+1);
		for(int i=1;i<255;i++){
			String host = net + i;
			if(IP.equals(host))
				continue;
			sb.append(host + "/");
		}
		return sb.toString().split("/");
	}
}
