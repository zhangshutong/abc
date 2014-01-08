package njnu.det.newvision;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

//Usage:
//在onCreate()和onResum()方法中调用bind(this)方法；在onPause()中调用unbind(this)方法
//用setNetworkAvailableListener(this),并实现OnNetworkAvailableListener接口，
//那么，当网络状态发生变化时，接口方法会自动执行
public class WIFIState extends  BroadcastReceiver{
	public enum NetState{UNKNOWN,WIFI_CONNECTED,WIFI_CONNECTING,WIFI_CLOSING,WIFI_CLOSED}
	String IP="0.0.0.0";
	public String NetName=""; //网络名称 
	WifiManager wm;
	NetState Ntstate = NetState.UNKNOWN;
	
	OnNetworkAvailableListener netListener;
	public static interface OnNetworkAvailableListener{
		public  void OnNetworkAvailable();
		public  void OnNetworkUnavailable();
	}
	
	//连接网络
	public void connectWiFi (Context context)throws Exception{
		try {
			detectWiFiState(context);
			if(NetState.WIFI_CLOSED == Ntstate){
				WifiManager wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				wm.setWifiEnabled(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[Wi-Fi State]开启网络错误！\n" + e.getMessage());
		}
		
	}
	//关闭WiFi
	public void closeWiFi(Context context) throws Exception{
		try {
			detectWiFiState(context);
			if(NetState.WIFI_CONNECTED == Ntstate){
				WifiManager wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				wm.setWifiEnabled(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("[Wi-Fi State]关闭网络错误！\n" + e.getMessage());
		}
	}
	//获取网络状态.当网络正处于连接状态时，可能暂时无法获得IP地址
	public  NetState detectWiFiState(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm == null){
			Ntstate = NetState.UNKNOWN;
			return Ntstate;
		}
		else{
			//如果处于连接状态，获取IP
			wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			if(wm == null){
				Ntstate = NetState.UNKNOWN;
				return Ntstate;
			}
			
			int st = wm.getWifiState();
			switch (st) {
			case WifiManager.WIFI_STATE_DISABLED://Wifi已关闭
				Ntstate = NetState.WIFI_CLOSED;
				NetName = "";
				break;
			case WifiManager.WIFI_STATE_DISABLING: //wifi正在关闭
				Ntstate = NetState.WIFI_CLOSING;
				NetName = "";
				break;
			case WifiManager.WIFI_STATE_ENABLED: //wifi已连接
				Ntstate = NetState.WIFI_CONNECTED;
				//获取IP地址
				WifiInfo wi = wm.getConnectionInfo();
				NetName = wi.getSSID();
				int ipAddr =wi.getIpAddress(); 
				IP = String.valueOf(ipAddr & 0xff) + "." + String.valueOf((ipAddr>>8) & 0xff) + "." + 
							String.valueOf((ipAddr>>16) & 0xff) + "." + String.valueOf((ipAddr>>24) & 0xff);
				break;
			case WifiManager.WIFI_STATE_ENABLING: //wifi正在连接
				Ntstate = NetState.WIFI_CONNECTING;
				NetName = "";
				break;
			default: //wifi未知状态
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
	//声明实例时需要同时实现接口方法
	public void setNetworkAvailableListener(OnNetworkAvailableListener listener){
		netListener = listener;
	}
	//获取网络中可能的IP地址，默认为C类地址
	public String[] getNeighborHosts(Context context){
		detectWiFiState(context);//检测网络状态
		if(IP.startsWith("0.0"))//没获得IP地址
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
