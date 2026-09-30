package RDM.util;

import utils.password.PasswordUtil;

public class GlobalVariables {
	
	 //CONTROL M
	
	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";

	// Usuarios CONTROL M
	public static final String usuarioRicardoControlM = "rrompal";
	public static final String contrasenaRicardoControlM = PasswordUtil
			.decryptPassword("4434DB86406901FA7CA1AB950C2D2D84");
	public static final String serverControlM = "http://10.184.56.194:18080/ControlM/";

	public static final String USER_G_CONTROL_M = "jgvargar";
	public static final String PASS_G_CONTROL_M = PasswordUtil.decryptPassword("D7038E1F1C51B8097328C22E5D6148D1");

	// Usuarios CONECTA SFTP
	public static final String usuarioConecta = "brmsrepl";
	public static final String contrasenaConecta = PasswordUtil.decryptPassword("63AE98A7F45F58F3ED5F82FD88EF8BE9");
	public static final String hostConecta = "10.180.48.23";
	public static final int puerto = 22;

	// CONECTA BD
	public static final String DB_USER_FCREPQA = "reple_view";
	public static final String DB_PASSWORD_FCREPQA = PasswordUtil.decryptPassword("2CC9169DB588DB468491A614569CBAB9");
	public static final String DB_HOST_FCREPQA = "10.180.48.71:1521/FCREPQA.FEMCOM.NET";

	// RDM TLAJOMULCO
	// RDM_TLAJOULCO

	public static final String DB_USER_FCRD71CU = "6006118";
	public static final String DB_PASSWORD_FCRD71CU = PasswordUtil
			.decryptPassword("B7D663884A09DAB86E88F8A76D10D6B466219326134E4FDEEEF485EC0C147051");
	public static final String DB_HOST_FCRD71CU = "10.180.49.87:1535/FCRD71CU.FEMCOM.NET";

	// Usuarios RDM
	public static final String usuarioRicardo = "RDMAPPS";// "1328451";
	public static final String contrasenaRicardo = "Comercio.23*"; // PasswordUtil.decryptPassword("C58ACEA763FC1441370A8963D2A085FC");

	// USUARIOS RDM CAMBIOS MASIVOS
	public static final String usuarioCambiosMasivos = "TSTUSR001";
	public static final String contrasenaCambiosMasivos = PasswordUtil
			.decryptPassword("014F085DECDEBF1F630610F8730CDEBF");
	public static final String serverCambiosMasivosTlajomulco = "http://10.180.49.86:8071/";

	// USUARIOS RDM CAMBIOS MASIVOS
	public static final String usuarioCambiosMasivosInspectorCalidadFastFood = "3215644";
	public static final String contrasenaCambiosMasivosInspectorCalidadFastFood = PasswordUtil
			.decryptPassword("C0CE6F4B061B2214B006B313200AA419");

	public static final String usuarioAPPS = "RDMAPPS";
	public static final String contrasenaAPPS = PasswordUtil.decryptPassword("4AA4F6FC949AD630F64FEBF5E9D19151");



	// Usuario RDM Bara
	public static final String usuarioBara = "UserQA1";
	public static final String contrasenaBara = PasswordUtil.decryptPassword("AE71E7DC76807DE6E88C2A577DDE6BAA");
	public static final String usuarioBara2 = "USERQARE";
	public static final String contrasenaBara2 = PasswordUtil.decryptPassword("53EAFB52399547CF4DC60B3DE153E65F");

	//RDM BARA
	 public static final String DB_USER_RDM_BARA = "RDM100";
	 public static final String DB_PASSWORD_RDM_BARA =PasswordUtil.decryptPassword("29C2F0B848F7530511B5E7A0B8898E9B");
	 public static final String DB_HOST_RDM_BARA = "fcrdm05c.femcom.net:1535/FCRDM59Q";
	
	public static final String DB_USER_RDM = "RDM100";
	public static final String DB_PASSWORD_RDM = PasswordUtil.decryptPassword("C459C0FBA9A4F8462A94D95D24E976DB");
	public static final String DB_HOST_RDM = "10.180.56.86:1535/FCRD01CU.FEMCOM.NET";


	public static final String DB_USERVIEW_RDM = "RDMVIEW";
	public static final String DB_PASSWORDVIEW_RDM = PasswordUtil.decryptPassword("8F338A480F532676F0F11974F87B176A");
	public static final String DB_HOST_BARA_QA1 = "fcrdm05c.femcom.net:1535/FCRDM59Q";
	public static final String DB_USERVIEW_BARA_QA1 = "0032024";
	public static final String DB_PASSWORD_BARA_QA1 = PasswordUtil.decryptPassword("53EAFB52399547CF4DC60B3DE153E65F");
	


	public static final String DB_USER_RDM_RDMVIEW = "RDMVIEW";
	public static final String DB_PASSWORD_RDM_RDMVIEW = PasswordUtil.decryptPassword("8F338A480F532676F0F11974F87B176A");
	public static final String DB_HOST_RDM_RDMVIEW = "10.180.56.86:1535/FCRD01CU.FEMCOM.NET";


	public static final String DB_USER_RDM_RDM100 = "RDM100";
	public static final String DB_PASSWORD_RDM_RDM100 = PasswordUtil.decryptPassword("7EF5531CE42BD780BF20F98EEFFB5F3902DA95C4818E8CF742E8F9CB7B06311D");
	public static final String DB_HOST_RDM_RDM100 = "172.26.8.148:1535/FCRD01QA.FEMCOM.NET";

	
	// RADIO FRECUENCIA
	public static final String USER_TERA = "rfcut01";
	public static final String PASS_TERA = "178A22F6743BA9AAD96B74441E93F8D7";

	public static final String USER_RF = "1328451";
	public static final String PASS_RF = "C58ACEA763FC1441370A8963D2A085FC";

	// wmlostatusRunPR1FCWMLQA
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQA = "10.184.48.217:1535/FCWMLQA.FEMCOM.NET";

	// RDM BASE DE DATOS
	public static final String DB_USER_CONECTARDM100 = "RDM100";
	public static final String DB_PASSWORD_CONECTARDM100 = PasswordUtil.decryptPassword("rdmcut17");
	public static final String DB_HOST_CONECTARDM100 = "10.184.48.24/oxrepledbq00.femcom.net";

	// WM
	public static final String DB_USER_WM = "QAVIEW";
	public static final String DB_PASSWORD_WM = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_WM = "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";

	// RMS fcrmsdb61
	public static final String DB_USER_FCRMSQA = "QAVIEW";
	public static final String DB_PASSWORD_FCRMSQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCRMSQA = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";

	// DAS
	public static final String DB_USER_DAS = "nreader";
	public static final String DB_PASSWORD_DAS = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_DAS = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";


	//RMS16 QA FCMOMSIT
	public static final String DB_USER_RMS16 = "nreader";
	public static final String DB_PASSWORD_RMS16 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RMS16 = "172.26.8.66:1521/FCMOMSIT";

	//RMS QA QAVIEW
	public static final String DB_USER_RMS = "6004010";
	public static final String DB_PASSWORD_RMS = PasswordUtil.decryptPassword("DDD6D51CA1FB46F206A25ED43D07E8F93813910C070EE79DD48CA9AD5BEA9416");
	public static final String DB_HOST_RMS = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";
	
	//AVEBQA
	public static final String DB_USER_EBS = "appsview";
	public static final String DB_PASSWORD_EBS = PasswordUtil.decryptPassword("3F5BB92144C6B52AEEDEBEA236941122");
	public static final String DB_HOST_EBS = "10.184.48.60:1532/AVEBQA";


	// BASE DE DATOS FCWMFSIT
	public static final String DB_USER_FCWMFSIT = "nreader";
	public static final String DB_PASSWORD_FCWMFSIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCWMFSIT = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";

	// BASE DE DATOS FCMOMPRF_RMS16
	public static final String DB_USER_FCMOMPRF_RMS16 = "nreader";
	public static final String DB_PASSWORD_FCMOMPRF_RMS16 = PasswordUtil
			.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCMOMPRF_RMS16 = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";

	// BASE DE DATOS FCRDMSIT
	public static final String DB_USER_FCRDMSIT = "nreader";
	public static final String DB_PASSWORD_FCRDMSIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCRDMSIT = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";

	// BASE DE DATOS FCWMESITBACKOFFICE
	public static final String DB_USER_FCWMESIT = "nreader";
	public static final String DB_PASSWORD_FCWMESIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCWMESIT = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";

	// Usuarios RDM Ruteo Dinamico
	public static final String usuarioRDQ = "RDMUNIGIS";
	public static final String usuarioRDD = "RDUNIGIS";
	public static final String contrasenaRD = PasswordUtil.decryptPassword("B354ED73FC84F745D8B47EE85481607F");
	public static final String urlRDDEV = "http://fcret3.femcom.net:8036/rdmDES36.html";
	public static final String urlRDQ = "http://fcrdmq01.femcom.net:8011/rdm001.html";
	public static final String urlRDQA = "http://10.180.56.86:8011/rdm001.html";
	public static final String DB_USER_RDM_RD= "RDM100";
    public static final String DB_PASSWORD_RDM_RDDEV = PasswordUtil.decryptPassword("F7AAEC2D82A8A357EAE0E184EC98047B");
    public static final String DB_HOST_RDM_RDDEV = "fcrdmd01.femcom.net:1521:RDMDES36";
    public static final String DB_PASSWORD_RDM_RDQA = PasswordUtil.decryptPassword("0E4265133E540C34BBB3074F726B7C47");
    public static final String DB_HOST_RDM_RDQA = "10.180.56.86:1535:FCRD01CU";
    
	//BI PUBLISHER
	public static final String USER_BI_PUBLISHER = "SISLOG";
	public static final String PASSWORD_BI_PUBLISHER = PasswordUtil.decryptPassword("605ADDFA3A739887912EFF069E6870D5");
	public static final String URL_BI_PUBLISHER = "http://fcorepo2.femcom.net:9705/xmlpserver/login.jsp";
 

	
}
