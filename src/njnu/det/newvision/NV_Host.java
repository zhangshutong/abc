package njnu.det.newvision;

public class NV_Host {
	static String local_IP="127.0.0.1";
	static String Server_IP="0.0.0.0";  //���ķ�����IP����ϵͳ�����ж�ȡ���ҿ���ͨ�����ñ��
	static Boolean Server_Linked=false; //���ӷ������󽫱�־����
	static String  owerner_IP = "127.0.0.1"; //���������ߵ�IP��ѡ��ѧ�����Ӧ����
	static Boolean Registflag = false;  //ע���־
	static User owner;					//��ǰ���µ�������
	static User localUser;				//ϵͳʹ����
	static String DbPath;				//���ݿ�·�� 
	static String DbName;				//���ݿ�����
	static  boolean isLocal;			//��ǰ�������Ƿ񱾵�����
	static String imgPath;				//ͼƬ�ļ���·�� 
    static String operation;
	
	public static void setOperation(String operation) {
		NV_Host.operation = operation;
	}
	
	public static String getOperation() {
		return operation;
	}
	
	public static String getImgPath() {
		return imgPath;
	}
	public static void setImgPath(String imgPath) {
		NV_Host.imgPath = imgPath;
	}
	
	public static User getLocalUser() {
		return localUser;
	}
	public static void setLocalUser(User localUser) {
		NV_Host.localUser = localUser;
	}
	
	public static String getDbName() {
		return DbName;
	}
	public static void setDbName(String dbName) {
		DbName = dbName;
	}
	public static String getDbPath() {
		return DbPath;
	}
	public static void setDbPath(String dbPath) {
		DbPath = dbPath;
	}
	public static Boolean getRegistflag() {
		return Registflag;
	}
	public static void setRegistflag(Boolean registflag) {
		Registflag = registflag;
	}
	public static String getLocal_IP() {
		return local_IP;
	}
	public static void setLocal_IP(String local_IP) {
		NV_Host.local_IP = local_IP;
	}
	public static String getServer_IP() {
		return Server_IP;
	}
	public static void setServer_IP(String server_IP) {
		Server_IP = server_IP;
	}
	public static Boolean getServer_Linked() {
		return Server_Linked;
	}
	public static void setServer_Linked(Boolean server_Linked) {
		Server_Linked = server_Linked;
	}
	public static User getOwner() {
		return owner;
	}
	public static void setOwner(User user) {
		NV_Host.owner = user;
	}

}
