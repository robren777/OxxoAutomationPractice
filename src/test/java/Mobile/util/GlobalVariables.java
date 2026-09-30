package Mobile.util;

import utils.password.PasswordUtil;


public class GlobalVariables {

	public static final String HOST_QA9 = PasswordUtil.decryptPassword("0ECEDE5C0CB1B29AA651F5740523DBCF922BD823772E99F2F5FF0E6BD63EC5B8");	
	public static final String HOST_QA10_VIRTUAL = PasswordUtil.decryptPassword("727217CB5A1AAC85170D4AE70C1BC550C3B6B3D6A5A106622353563A266DD74E"); 
	public static final String HOST = PasswordUtil.decryptPassword("29A2094820E0289621958423760938E336BF9BBAD8AF44E915521C6E35BBD484");	
	public static final String HOST_OLS = PasswordUtil.decryptPassword("8015BD39B842348D6CC6865F8CD0030DCF49885A6D645B5229C7D1E6043845A4");
	public static final String HOST_FAC = PasswordUtil.decryptPassword("29A2094820E0289621958423760938E3B8CB6D4D516D56D91F21CB67609C1221");
	public static final String HOST_FAC_2 = PasswordUtil.decryptPassword("AFFB124986714F4CA22216628884620CCBF16BE31AF52CD2F3A45E1331A1794E");
	public static final String HOST_RTP = PasswordUtil.decryptPassword("688ED399E2F293E247D3E4EC66703FFD957364B5BC4D9511A432FBBF42AF661F");
	public static final String HOST_PR2 = PasswordUtil.decryptPassword("7E007AAD84985F95EE8C2ABCAD5D7E9F6963A5146A4E4B410B1403E32AF5C3E9");
	public static final String HOST_PR2_CO = "10.182.32.22:7797";
	public static final String HOST_PR50v2 = PasswordUtil.decryptPassword("3A9C8BAE1396BD20ACE4D803F081EC15922BD823772E99F2F5FF0E6BD63EC5B8");
	public static final String HOST_RH = PasswordUtil.decryptPassword("8015BD39B842348D6CC6865F8CD0030DD132306926AD6E0A61CB73606655B2BC");
	public static final String HOST_FTC = PasswordUtil.decryptPassword("E66B1FDA691DD8C82BB18C8B284F288B01393D82DD17AD8F9854B3D1D741356A");

	/**
	 * CREDENCIALES BD PV 10.200.10.111
	 */
	public static final String urlPV10_111_XposStore = "jdbc:sqlserver://10.200.10.111:1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String urlPV10_111_Satelites = "jdbc:sqlserver://10.200.10.111:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true"; 
	public static final String userDB10_111 = "sa";
	public static final String passBD10_111 = "12345678";
	public static final String connPowerBi = "jdbc:sqlserver://zmetaprsrvr01.database.windows.net:1433;database=zmetaprsdb01;user=IMAZREADUSER;password={E(3Q7=B{9!cZNPpq};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";

	//PV 10.110/
	public static final String urlPV10_110_XposStore = "jdbc:sqlserver://10.200.10.110:1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String urlPV10_110_Satelites = "jdbc:sqlserver://10.200.10.110:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true";
	public static final String userDB10_110 = "sa";
	public static final String passDB10_110 ="12345678";

	//FCIAS/
	public static final String DB_USER_IAS = "portal_view";
	public static final String DB_PASSWORD_IAS = PasswordUtil.decryptPassword("FC040844B14947D896CF6A93CAFA66E0");
	public static final String DB_HOST_IAS = PasswordUtil.decryptPassword("2B07C799DA60299CFAB5022371DD455BAA58B31C17DDB7489D01E819AB4D30C16717993EFECB5015E24E9A5CE874293F");
 
	//TPEUSER(PE)/FCTDC
	public static final String DB_USER = "wmview";
	public static final String DB_PASSWORD = PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOST = PasswordUtil.decryptPassword("EC0A9E38554ABEA867741A048B57522499C48D25355B4E57D97615334A85DEEE6717993EFECB5015E24E9A5CE874293F");
	//PE1/FCTAE
	public static final String DB_USERPE1 = "wmview";
	public static final String DB_PASSWORDPE1 =PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOSTPE1 = PasswordUtil.decryptPassword("EC0A9E38554ABEA867741A048B575224D79CF7217416310C7AFFF14226AAD21E6717993EFECB5015E24E9A5CE874293F");
	//PE3
	//PE6
	public static final String DB_USERPE6 = "QAVIEW";
	public static final String DB_PASSWORDPE6 = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOSTPE6 = PasswordUtil.decryptPassword("92A9B1C1830A9C104662425C28C91152E15F2576DA9C0041B0CC41540D20A9CB6717993EFECB5015E24E9A5CE874293F");
	//TPE OLS
	public static final String DB_USERPE3 = "wmview";
	public static final String DB_PASSWORDPE3 = PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOSTPE3 = PasswordUtil.decryptPassword("EC0A9E38554ABEA867741A048B575224C2A35DD643DA19B696DAA38A568A15EA6717993EFECB5015E24E9A5CE874293F");
	//peru OIWMQA.FEMCOM.NET----------------scripts
	public static final String DB_USER_Oiwmqa = "WMVIEW";
	public static final String DB_PASSWORD_Oiwmqa = PasswordUtil.decryptPassword("D1814B879E2E546C68B5F126DBC7C5CD");
	public static final String DB_HOST_Oiwmqa = "10.184.40.134:1521/OIWMQA.FEMCOM.NET"; 
	//TABLAS DE USER FAC 
	public static final String DB_USER_FAC = "tpeuser";
	public static final String DB_PASSWORD_FAC = PasswordUtil.decryptPassword("F8B0444F186AADE9448BDB3C81BFE630");
	public static final String DB_HOST_FAC = PasswordUtil.decryptPassword("EC0A9E38554ABEA867741A048B575224EEA8E66C1C824471743A54C8C34B83BE9E41FA08A730B16BB0393CCE9709A415");
	public static final String DB_USER_FacQ = "TPEUSER";
	public static final String DB_PASSWORD_FacQ = PasswordUtil.decryptPassword("B1078AF4CE1BF0B567FA583C3F13FA73");
	public static final String DB_HOST_FacQ = PasswordUtil.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C71F44FC6BE133764097F901BCD1762E48");

	//Tablas de LOGWM PR2
	public static final String DB_USER_LOG = "wmlog";
	public static final String DB_PASSWORD_LOG = PasswordUtil.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
	public static final String DB_HOST_LOG = PasswordUtil.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7125322925092D89400467A6E96C00290");
	public static final String DB_USER_Data = "posuser";
	public static final String DB_PASSWORD_Data = PasswordUtil.decryptPassword("25AF5CE3DDE8A89AAE9DFCC87ED144A5");
	public static final String DB_HOST_Data = PasswordUtil.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7DB687953290CED79479D18EEFE2D9074");
	public static final String DB_USER_DataWmuser = "wmuser";
	public static final String DB_PASSWORD_DataWmuser = PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_DataWmuser = PasswordUtil.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7DB687953290CED79479D18EEFE2D9074");	
	//BDPOSUSER20
	public static final String DB_USER_Puser = "QAVIEW";
	public static final String DB_PASSWORD_Puser = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_Puser = PasswordUtil.decryptPassword("92A9B1C1830A9C104662425C28C91152761EE7F4954ACC6256E7B2BD21B2D0AC6717993EFECB5015E24E9A5CE874293F");
	public static final String DB_USER_FCWMQA_UPD ="Hexaware30";
    public static final String DB_PASSWORD_FCWMQA_UPD =  PasswordUtil.decryptPassword("A5D4E206919F323F53429EC8AEA1C18B");
    public static final String DB_HOST_FCWMQA_UPD = "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";
	//rms
	public static final String DB_USER_Rms = "RMS100";
	public static final String DB_PASSWORD_Rms = PasswordUtil.decryptPassword("5EED5CABD9338AE6EA71917A17F75E3F");
	public static final String DB_HOST_Rms = PasswordUtil.decryptPassword("04432E3694F0A229E7C95DEDB85D211509297D9B66AE4BF1C2790B50FB55B6CB");
	//rmsruebasPR1FCRMSCUT
	public static final String DB_USER_RmsP = "RMS100";
	public static final String DB_PASSWORD_RmsP = PasswordUtil.decryptPassword("5EED5CABD9338AE6EA71917A17F75E3F");
	public static final String DB_HOST_RmsP = "10.184.56.124:1525/FCRMSCUT.FEMCOM.NET";
	//EBSPruebaFCFINQA
	public static final String DB_USER_EBS = "wmuser";
	public static final String DB_PASSWORD_EBS =PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_EBS = "10.184.56.95:1521/FCFINQA.FEMCOM.NET";
	 //EBS FCEBC4Vante 
    public static final String DB_USER_EBS_FCEBC4= "WMUSER";
    public static final String DB_PASSWORD_EBS_FCEBC4= PasswordUtil.decryptPassword("82862214493D97A5794DF801269CBF9F");  
    public static final String DB_HOST_EBS_FCEBC4= "10.80.3.159:1602/fcebc4";
	//wmlostatusRunPR1FCWMLQA
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");

	public static final String DB_HOST_FCWMLQA = "oxzvmwmiracq01.femcom.net:1521/FCWMLQA.FEMCOM.NET";
	
	public static final String DB_HOST_FCWMLQA2 = "10.184.48.217:1535/FCWMLQA.FEMCOM.NET";

 
	//Tablas RMS mÃƒÂ©xico RMSVIEW
		public static final String DB_USER_MEX ="RMSVIEW";
		public static final String DB_PASSWORD_MEX = PasswordUtil.decryptPassword("6920831A3426764BB7E688FAA3F3F63F");
		public static final String DB_HOST_MEX = "10.184.56.124:1525/FCRMSCUT.FEMCOM.NET";

 
		
	//Tablas FCWMQA_NUEVA (host diferente)
		public static final String DB_USER_FCWMQA_NUEVA ="QAVIEW";
		public static final String DB_PASSWORD_FCWMQA_NUEVA =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
		public static final String DB_HOST_FCWMQA_NUEVA = "10.184.48.216:1535/fcwm6QA.femcom.net";

	//Tablas Chile RMs100 	
	public static final String DB_USER_RMSWMUSERChile ="RMS100"; //wmuser
	public static final String DB_PASSWORD_RMSWMUSERChile =  PasswordUtil.decryptPassword("1CD10F979189DA0768DA27AFB5970122"); //E023A372AD3B3237F098A0EC4D27ECE7
	public static final String DB_HOST_RMSWMUSERChile = "10.80.2.222:1541/BDCHRMSQ.FEMCOM.NET";	
	   //Tabla RMS100 COLOMBIA
    public static final String DB_USER_RMS_COL="RMS100";
    public static final String DB_PASSWORD_RMS_COL =PasswordUtil.decryptPassword("E294EC6DCC58CB4368980D8CCDD201BD");
    public static final String DB_HOST_RMS_COL = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";
  //RMS de COL QAVIEW
    public static final String DB_USER_RMS_COL_QAVIEW="QAVIEW";
    public static final String DB_PASSWORD_RMS_COL_QAVIEW =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_RMS_COL_QAVIEW = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";
  //FCWMLQA_WMLOG_COL
  	public static final String DB_USER_FCWMLQA_WMLOG_COL = "Wmlog";
  	public static final String DB_PASSWORD_FCWMLQA_WMLOG_COL =PasswordUtil.decryptPassword("06DB86BC1C7C3D11C9B9F98F2E5686BE");
  	public static final String DB_HOST_FCWMLQA_WMLOG_COL = "10.184.48.217:1535/FCWMLQA.FEMCOM.NET";
 
   
    //Tabla RMS100 MEXICO SID: FCRMSQA
    public static final String DB_USER_RMS_MEX="QAVIEW";
    public static final String DB_PASSWORD_RMS_MEX =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_RMS_MEX = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";
    //Tabla EBS COLOMBIA SID: FCEBSCO2
    public static final String DB_USER_EBS_COL="wmuser";
    public static final String DB_PASSWORD_EBS_COL =PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
    public static final String DB_HOST_EBS_COL = "10.80.2.184:1531/FCEBSCO2";

	//Tablas chile wmLog--
	public static final String DB_USER_LogChile ="Wmlog";
	public static final String DB_PASSWORD_LogChile = PasswordUtil.decryptPassword("B8BC60B8008B4BF2A04AA0F2EB9211AB");
	 public static final String DB_HOST_LogChile ="10.186.13.26:1521/OCHWMQA.FEMCOM.NET";
	//Tablas chile posuser--
	public static final String DB_USER_PosUserChile ="Posuser";
	public static final String DB_PASSWORD_PosUserChile =PasswordUtil.decryptPassword("2C7E5D93EAF7FF1140C6F1328B7A491A");
	public static final String DB_HOST_PosUserChile = "10.186.13.26:1533/OCHWMQA.FEMCOM.NET";	
	//Tabla ORAFIN/EBS
	public static final String DB_USER_Ebs ="APPSVIEW";
	public static final String DB_PASSWORD_Ebs =PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_Ebs = "10.184.56.95:1521/FCFINQA.FEMCOM.NET";	
 
	//TPUSER
    public static final String DB_USER_FCTPE = "QAVIEW";
    public static final String DB_PASSWORD_FCTPE =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_PASSWORD_FCTPE2 =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTPE = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";
 
    //Servicio electronicos 
    public static final String DB_USER_FCTAEQA = "QAVIEW";
    public static final String DB_PASSWORD_FCTAEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTAEQA = "10.184.80.120:1521/FCTAEQA.FEMCOM.NET";
    public static final String DB_USER_FCSWQA = "swview";
    public static final String DB_PASSWORD_FCSWQA = PasswordUtil.decryptPassword("7AE65940311F261353C76806C270E990");
    public static final String DB_HOST_FCSWQA = "10.184.80.120:1521/FCSWQA.FEMCOM.NET";
 
    public static final String DB_USER_FCTDCQA = "qaview";
    public static final String DB_PASSWORD_FCTDCQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTDCQA = "10.184.80.120:1521/FCTDCQA.FEMCOM.NET";
 
    //FCWMLTAEQA MTY
    public static final String DB_USER_FCWMLTAEQA_QAVIEW = "qaview";
    public static final String DB_PASSWORD_FCWMLTAEQA_QAVIEW = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");  
    //Usuario QAVIEW
    public static final String DB_USER_FCWMLTAEQA_MTY = "QAVIEW";  
    //FCMFSQA
    public static final String DB_USER_FCMFS = "qaview";
    public static final String DB_PASSWORD_FCMFS = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCMFS = "10.184.32.19:1521/FCMFSQA.FEMCOM.NET";
    //FCSWPRD 
    public static final String DB_USER_FCSWPRD_CRECI = "SWVIEW";
    public static final String DB_PASSWORD_FCSWPRD_CRECI= PasswordUtil.decryptPassword("CD418B1D0D38D7E401BDCC705BC09A7E");
    public static final String DB_HOST_FCSWPRD_CRECI = "10.182.56.97:1535/FCSWPRD";

    //FAC
    public static final String DB_USER_FCACQA = "qaview";
    public static final String DB_PASSWORD_FCACQA  = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCACQA = "10.184.80.120/FCACQA.FEMCOM.NET";
    //Queretaro
    public static final String DB_USER_FCSWQA_QRO = "swview";
    public static final String DB_PASSWORD_FCSWQA_QRO = PasswordUtil.decryptPassword("7AE65940311F261353C76806C270E990");
    public static final String DB_HOST_FCSWQA_QRO = "10.184.40.120:1521/FCSWQA.FEMCOM.NET";
    public static final String DB_USER_FCACQA_QRO = "qaview";
    public static final String DB_PASSWORD_FCACQA_QRO = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCACQA_QRO = "10.184.40.120/FCACQA.FEMCOM.NET";

    public static final String DB_USER_FCWMLTAEQA_QRO = "wmlog";
    public static final String DB_PASSWORD_FCWMLTAEQA_QRO = PasswordUtil.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
    public static final String DB_HOST_FCWMLTAEQA_QRO = "10.184.40.120:1521/FCWMLTAQ.FEMCOM.NET";
    public static final String DB_USER_FCWMLTAEQA_QA = "qaview";
    public static final String DB_PASSWORD_FCWMLTAEQA_QRO_QA = PasswordUtil.decryptPassword("0641623006468195175693CCCA34F71E");
    public static final String DB_USER_FCTDCQA_QRO = "QAVIEW";
    public static final String DB_PASSWORD_FCTDCQA_QRO = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTDCQA_QRO = "10.184.40.120:1521/FCTDCQA.FEMCOM.NET";
    public static final String DB_USER_FCTPE_QRO = "QAVIEW";
    public static final String DB_PASSWORD_FCTPE_QRO =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTPE_QRO = "10.184.40.120:1521/FCTPEQA.FEMCOM.NET";
    //MTY
    public static final String DB_HOST_FCWMLTAQ_MTY = "10.184.80.120:1521/FCWMLTAQ.FEMCOM.NET";
    public static final String DB_USER_FCWMLTAQ_MTY = "QAVIEW";
    public static final String DB_PASSWORD_FCWMLTAQ_MTY = PasswordUtil.decryptPassword("0641623006468195175693CCCA34F71E");

    //RDM 01CU
    public static final String DB_USER_RDM = "rdm100";
    public static final String DB_PASSWORD_RDM = PasswordUtil.decryptPassword("C459C0FBA9A4F8462A94D95D24E976DB");
    public static final String DB_HOST_RDM = "10.180.56.86:1535/FCRD01CU.FEMCOM.NET";
    //RDM RDM63CU
    public static final String DB_USER_RDM_63 = "rdm100";
    public static final String DB_PASSWORD_RDM_63 = PasswordUtil.decryptPassword("35B0A9998A32FE5AE0581E6F785E2F80");
    public static final String DB_HOST_RDM_63 = "10.186.13.15:1521/FCRD63CU.FEMCOM.NET";
 
    //BIETL
    public static final String DB_USER_BI = "BIETL";
    public static final String DB_PASSWORD_BI = PasswordUtil.decryptPassword("299D18CA0A0E2BFB4975E98E28A81B61");
    public static final String DB_HOST_BI = "10.184.80.26:1535/FCDW4QA.FEMCOM.NET";
  //EBS AVANTE
    public static final String DB_USER_AVEBQA = "appsview";
    public static final String DB_PASSWORD_AVEBQA =PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
    public static final String DB_HOST_AVEBQA = "10.184.48.60:1532/AVEBQA";
	public static final String DB_USER_AVEBQA_UPD = "wmuser";
	public static final String DB_PASSWORD_AVEBQA_UPD = PasswordUtil
			.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_AVEBQA_UPD = "10.184.48.60:1532/AVEBQA";
	  
		//*************************************** BASES DE DATOS **************************************
	//XPOS/
	public static final String DB_USER_XPOS = "sa";
	public static final String DB_PASSWORD_XPOS = PasswordUtil.decryptPassword("3B93B9AEAA4D85DEF36A0BDAD00DED44");
	public static final String DB_XPOSSTORE = "jdbc:sqlserver://localhost:1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
	public static final String DB_DS50 = "jdbc:sqlserver://localhost:1433;databaseName=DS50;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
	public static final String DB_SATELITES = "jdbc:sqlserver://localhost:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
	public static final String DB_LOGGERDB = "jdbc:sqlserver://localhost:1433;databaseName=LoggerDB;encrypt=true;trustServerCertificate=true;loginTimeout=30;";	
	public static final String DB_XPOSCARRIER = "jdbc:sqlserver://localhost:1433;databaseName=XposCarrier;encrypt=true;trustServerCertificate=true;loginTimeout=30;";

	//SINERGIA- CNT
	public static final String DB_USER_FCIASQA_QA = "qaview";
	public static final String DB_PASSWORD_FCIASQA_QA =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCIASQA_QA = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
 
 
    //EBS OIEBSBDQ Peru y chile 
    public static final String DB_USER_OIEBSBDQ = "appsview";
    public static final String DB_PASSWORD_OIEBSBDQ =PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
    public static final String DB_HOST_OIEBSBDQ = "10.186.13.28:1522/OIEBSBDQ";				
  //EBSPruebaFCFINQA Usuario con privilegio consultas
  	public static final String DB_USER_EBS_Consulta = "APPSVIEW";
  	public static final String DB_PASSWORD_EBS_Consulta =PasswordUtil.decryptPassword("3F5BB92144C6B52AEEDEBEA236941122");
	//RMS_Mex servidor nuevo solo lectura
  	public static final String DB_USER_RmsMexNu = "NREADER";
	public static final String DB_PASSWORD_RmsMexNu = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RmsMexNu = "10.184.37.84:1521/FCRMSSIT";
	//Funciona para la TPE_LOT
	public static final String DB_USER_TPE_LOT = "QAVIEW";
	public static final String DB_PASSWORD_TPE_LOT = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_TPE_LOT = "10.184.40.120:1521/FCTPEQA.FEMCOM.NET";
	//DBS_BI_LT FCTICQA BI TICKET
	public static final String DB_USER_BiTicket = "QAVIEW";
	public static final String DB_PASSWORD_BiTicket = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_BiTicket = "10.184.80.26:1535/FCTICQA.FEMCOM.NET";
	//DBS_BI_LT FCWMREQA 
	public static final String DB_USER_FCWMREQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMREQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMREQA = "172.26.221.197:1521/FCWMREQA.FEMCOM.NET";
	
	public static final String DB_HOST_FCWMREQA2 = "10.185.177.17:1521/FCWMREQA.FEMCOM.NET";
	//CTNCHILE se agrego 10/21/2021
	public static final String DB_USER_CNTCHILE = "6006028";
	public static final String DB_PASSWORD_CNTCHILE = PasswordUtil.decryptPassword("51D38AD642AE42ABC70F62CE4245FB6D");
	public static final String DB_HOST_CNTCHILE = "10.186.13.27:1547/OCHCNTQ.FEMCOM.NET";
	// se agrego FCTDAQA 10/21/2021
	public static final String DB_USER_FCTDAQA = "QAVIEW";
	public static final String DB_PASSWORD_FCTDAQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTDAQA = "10.184.32.19:1521/FCTDAQA.FEMCOM.NET";
	// se agrego Posuser_peru 15/12/2021
	public static final String DB_USER_PosuserPeru = "posuser";
	public static final String DB_PASSWORD_PosuserPeru = PasswordUtil.decryptPassword("25AF5CE3DDE8A89AAE9DFCC87ED144A5");
	public static final String DB_HOST_PosuserPeru = "10.184.40.134:1521/OIWMQA.FEMCOM.NET";
	// se agrego RMS_Peru 16/12/2021
	public static final String DB_USER_RMSWMUSERPeru ="RMS100"; //wmuser
	public static final String DB_PASSWORD_RMSWMUSERPeru =  PasswordUtil.decryptPassword("5F83B010547B5DA3D27275203D3B31DA"); //E023A372AD3B3237F098A0EC4D27ECE7
	public static final String DB_HOST_RMSWMUSERPeru = "10.80.2.222:1521/BDPRMSQ.FEMCOM.NET";	
	//FCTPEQA_TPECOUSER
    public static final String DB_USER_FCTPE_CO = "TPECOUSER";
    public static final String DB_PASSWORD_FCTPE_CO =PasswordUtil.decryptPassword("C94BD0CC1BB6883BC6F6F971AE99995F");
    public static final String DB_HOST_FCTPE_CO = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";
    public static final String DB_USER_FCTPE_UPD = "Hexaware30";
	public static final String DB_PASSWORD_FCTPE_UPD = "Hexaware30";
	public static final String DB_HOST_FCTPE_UPD = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";
 
	//WMlog
	public static final String DB_USER_FCWMLQA_WMLOG = "Wmlog";
	public static final String DB_PASSWORD_FCWMLQA_WMLOG  =PasswordUtil.decryptPassword("06DB86BC1C7C3D11C9B9F98F2E5686BE");
	public static final String DB_HOST_FCWMLQA_WMLOG  = "10.184.48.215:1535/FCWMLQA.FEMCOM.NET";
 
	//Bases de datos desarrollo
	public static final String DB_USER_POSUSER_DEV = "posuser";
	public static final String DB_PASSWORD_POSUSER_DEV = "posuser";
	public static final String DB_HOST_POSUSER_DEV = "10.184.48.184:1535/FCWM6DEV";
	public static final String DB_USER_EBS_DEV= "wmuser";
	public static final String DB_PASSWORD_EBS_DEV = "wmuser";
	public static final String DB_HOST_EBS_DEV = "10.184.48.83:1536/FCEBSD";
	public static final String DB_USER_WMLOG_DEV = "wmlog";
	public static final String DB_PASSWORD_WMLOG_DEV = "wmlog";

	public static final String DB_HOST_WMLOG_DEV = "10.184.48.184:1535/FCWMLDES";
	
	public static final String DB_USER_TPEREP_DEV = "tperep";
	public static final String DB_PASSWORD_TPEREP_DEV = "tperep";
	public static final String DB_HOST_TPEREP_DEV = "10.80.8.204:1521/fctpedes.femcom.net";


	public static final String DB_HOST_WMLOG_DEV2 = "172.26.210.196:1535/fcwmldes.femcom.net";

	
	//Bases de datos nucleo
		public static final String DB_USER_FCRMSMGR = "nreader";
		public static final String DB_PASSWORD_FCRMSMGR =PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");; 
		public static final String DB_HOST_FCRMSMGR = "10.184.37.14:1521/FCRMSMGR";
		public static final String DB_USER_FCEBSSIT = "nreader";
		public static final String DB_PASSWORD_FCEBSSIT =PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");; 
		public static final String DB_HOST_FCEBSSIT = "10.184.17.27:1521/FCEBSSIT";
		public static final String DB_USER_fcwmesit = "nreader";
		public static final String DB_PASSWORD_fcwmesit =PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");; 
		public static final String DB_HOST_fcwmesit = "10.184.17.27:1521/fcwmesit";
		public static final String DB_USER_FCIASSIT = "nreader";
		public static final String DB_PASSWORD_FCIASSIT =PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");; 
		public static final String DB_HOST_FCIASSIT = "10.184.17.42:1535/FCIASSIT";

 
		// RR08
		public static final String DB_USER_FCWMFSIT = "nreader";
		public static final String DB_PASSWORD_FCWMFSIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCWMFSIT = "10.184.17.27:1521/FCWMFSIT";
		public static final String DB_USER_FCWMESIT = "nreader";
		public static final String DB_PASSWORD_FCWMESIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCWMESIT = "10.184.17.27:1521/FCWMESIT";
		public static final String DB_USER_FCRDMSIT_RDM_MTY_RDMVIEW = "nreader";
		public static final String DB_PASSWORD_FCRDMSIT_RDM_MTY_RDMVIEW = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCRDMSIT_RDM_MTY_RDMVIEW = "10.184.37.66:1535/FCRDMSIT";
		public static final String DB_USER_FCMOMUAT = "nreader";
		public static final String DB_PASSWORD_FCMOMUAT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCMOMUAT = "10.184.37.47:1521/FCMOMUAT";
		public static final String DB_USER_FCDASUAT = "nreader";
		public static final String DB_PASSWORD_FCDASUAT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCDASUAT = "10.184.37.67:1521/FCDASUAT";
		public static final String DB_USER_FCMOMSIT = "nreader"; // Se agrego user real
		public static final String DB_PASSWORD_FCMOMSIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");// Se agrego password real
		public static final String DB_HOST_FCMOMSIT = "10.184.37.55:1521/FCMOMSIT_RMS16_NGA"; // Se agrego host real
		public static final String DB_USER_FCMOMPFR_RMS = "nreader";
		public static final String DB_PASSWORD_FCMOMPFR_RMS = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
		public static final String DB_HOST_FCMOMPFR_RMS = "10.184.47.51:1537/FCMOMPFR";
    //RMS Peru
 
    public static final String DB_USER_RMS_QAVIEW_Peru ="QAVIEW";
    public static final String DB_PASSWORD_RMS_QAVIEW_Peru = PasswordUtil.decryptPassword("71611C46E0F816ED8968D3B412F086AE"); //E023A372AD3B3237F098A0EC4D27ECE7
    public static final String DB_HOST_RMS_QAVIEW_Peru = "10.80.2.222:1521/BDPRMSQ.FEMCOM.NET";
 
//    FCIMXQA - OEIMX
    public static final String DB_USER_FCIMXQA ="wmuser";
    public static final String DB_PASSWORD_FCIMXQA =  PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
    public static final String DB_HOST_FCIMXQA = "10.80.2.64:1521/FCIMXQA";
 
    //OXTPEQA
    public static final String DB_USER_OXTPEQA ="tpeview";
    public static final String DB_PASSWORD_OXTPEQA = PasswordUtil.decryptPassword("D4AE1E24D991C337B5AC61C752BB8DBB7AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
    public static final String DB_HOST_OXTPEQA = "10.188.18.20:1521/OXTPEQA.femcom.net";
	//Se agrgeo BD1
    public static final String DB_USER_FCTAEQA_QRO = "QAVIEW";
    public static final String DB_PASSWORD_FCTAEQA_QRO = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCTAEQA_QRO = "10.184.40.120:1521/FCTAEQA.FEMCOM.NET";
	//FCWMLQA con host diferente (2)
    public static final String DB_USER_FCWMLQA_WMLOG2 = "QAVIEW";
    public static final String DB_PASSWORD_FCWMLQA_WMLOG2 = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCWMLQA_WMLOG2 = "oxzvmwmiracq01.femcom.net:1521/FCWMLQA.FEMCOM.NET";

    public static final String DB_USER_FCWMLQA_WMLOG21 = "wmlog";
    public static final String DB_PASSWORD_FCWMLQA_WMLOG21 = PasswordUtil.decryptPassword("4FE8F6491B2AC658E1798FAFC1E1DE26");
    public static final String DB_HOST_FCWMLQA_WMLOG21 = "10.184.48.216:1535/FCWMLQA.FEMCOM.NET";
 
/****** EBS ******/
	//Usuarios #AVANTE
    public static final String getAvanteServer = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?langCode=ESA&_logoutRedirect=y";
    public static final String getJhernandezHexEbsUsr = "JHERNANDEZ_HEX";
    public static final String getJhernandezHexEbsPwd = PasswordUtil.decryptPassword("D17335CD8FE0585E69EB45EA4FB1B691");
 
    public static final String getJreyesHexEbsUsr = "JREYES_HEX";
    public static final String getJreyesHexEbsPwd = PasswordUtil.decryptPassword("EC411D653B92E0288FA1FFD6945A6727");
 
    public static final String getRpalaciosNeoEbsUsr = "RPALACIOS_NEO";
    public static final String getRpalaciosNeoEbsPwd = PasswordUtil.decryptPassword("F6EA71F19CD3F79C5EFF38362B080A6F");
 
    public static final String getEramirezNeoEbsUsr = PasswordUtil.decryptPassword("");
    public static final String getEramirezNeoEbsPwd = PasswordUtil.decryptPassword("");
 
    //Usuarios #INTERNACIONAL (chile y peru)

    public static final String getEbsInternacional = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp";
 
    public static final String getEbsInternacional2 = "http://opvebsapq01.femcom.net:8001/OA_HTML/AppsLocalLogin.jsp?langCode=ESA&_logoutRedirect=y";

    public static final String getJhernandezHexEbsIntUsr = "JHERNANDEZ_HEX";
    public static final String getJhernandezHexEbsIntPwd = PasswordUtil.decryptPassword("07C20B1D2CA19B1E356D6AF1E025896A");
 
    public static final String getJreyesHexEbsIntUsr = "JREYES_HEX";
    public static final String getJreyesHexEbsIntPwd = PasswordUtil.decryptPassword("C9142140497EEBD8C2EF47A2EB5D88A9");
 
    //Usuarios #IMMEX
 
    public static final String mhernandez_hex_EBS_USR = PasswordUtil.decryptPassword("");
    public static final String mhernandez_hex_EBS_PWD = PasswordUtil.decryptPassword("3F656AAB3CD21AD294EC2EE14B4821EE");
 
    public static final String DB_USER_FCWMLTAEQA = "wmlog";
    public static final String DB_PASSWORD_FCWMLTAEQA = PasswordUtil.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
    public static final String DB_HOST_FCWMLTAEQA = "10.184.80.120:1521/FCWMLTAQ.FEMCOM.NET";
 
    //CNT
    public static final String DB_USER_FCIASQA = "wmuser";
    public static final String DB_PASSWORD_FCIASQA = PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
    public static final String DB_HOST_FCIASQA = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
 
    //SINERGIA
    public static final String DB_USER_FCIASQAS = "qaview";
    public static final String DB_PASSWORD_FCIASQAS = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
    public static final String DB_HOST_FCIASQAS = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
 
    //EBS OIEBSBDQ Peru y chile 
    public static final String DB_USER_OIEBSBDQ1 = "APPSVIEW";
    public static final String DB_PASSWORD_OIEBSBDQ1 = PasswordUtil.decryptPassword("3F5BB92144C6B52AEEDEBEA236941122");
    public static final String DB_HOST_OIEBSBDQ1 = "10.186.13.28:1522/OIEBSBDQ";
    //KYRIBA INTERNACIONAL SFTP SERVER
    public static final String FTP_KYRIBA_INTERNACIONAL_HOST = PasswordUtil.decryptPassword("A6A54A62B1E2A562DBC40ECA124AD0EE");
    public static final int FTP_KYRIBA_INTERNACIONAL_PORT = 22;
    public static final String FTP_KYRIBA_INTERNACIONAL_USER = PasswordUtil.decryptPassword("C08AC54141A5238904854EC71F88CA5D");
    public static final String FTP_KYRIBA_INTERNACIONAL_PSW = PasswordUtil.decryptPassword("9A51EEAD8CB4E2251CB2A23395BBEA66");

    //KYRIBA amex SFTP SERVER
    public static final String FTP_AMEX__HOST = "10.188.48.13";
    public static final int FTP_AMEX_PORT = 21;
    public static final String FTP_AMEX_USER = PasswordUtil.decryptPassword("968FC18E7A0D01A14E80354AA3EC3D3E");
    public static final String FTP_AMEX_PSW = PasswordUtil.decryptPassword("0883DB03CBC6537DD2E90D84CE41498B");

    //CONTROL-M USER FOR MT940
 
    public static final String CONTROL_M_USER = PasswordUtil.decryptPassword("19E0190BF9A81A796E830D03466A0D51");
    public static final String CONTROL_M_PWD = PasswordUtil.decryptPassword("F92A7924C1FBD6C8738B9B2C5358125D");
    public static final String CONTROL_M_SERVER = "http://10.184.56.194:18080/ControlM/";

	// Usuarios QAGTIM

	public static final String userMiguelEscamilla = "MESCAMILLA_GTIM";
	public static final String pwdMiguelEscamilla = PasswordUtil.decryptPassword("49B87CF59639223DC133C9AE295378E6");

	public static final String get6008201User = "6008201";
	public static final String get6008201Pswd = PasswordUtil.decryptPassword("A79A31BF1F6DC12F4781A5494D34D99B");

	public static final String getMonicaSotoUser = "MONICA.SOTO_GTIM";
	public static final String getMonicaSotoPswd = PasswordUtil.decryptPassword("28AA35E202AB54EA77F99876AE7768C4");


	// Usuario Internacional  'OPERACIONES'
    public static final String getOperacionesEbsIntUsr = "OPERACIONES";
    public static final String getOperacionesEbsIntPwd = PasswordUtil.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");
 
/****** IMA ******/
	public static final String BASE_PATH = System.getProperty("user.dir");
	public static final String HOME_PATH = System.getProperty("user.home");
	
	//BD_IMA
	public static final String DB_HOST_IMA = "fcobidb1q00.femcom.net:1535/FCODSQA.FEMCOM.NET";
	public static final String DB_USER_IMA = "BIETL";
	public static final String DB_PASSWORD_IMA = PasswordUtil.decryptPassword("8D136AB701B3D22EE706D74A2408A9E4");
	//FCDW4QA
	public static final String DB_HOST_FCDW4QA = "10.184.80.26:1535/FCDW4QA.FEMCOM.NET";
	public static final String DB_USER_FCDW4QA = "BIETL";
	public static final String DB_PASSWORD_FCDW4QA = PasswordUtil.decryptPassword("299D18CA0A0E2BFB4975E98E28A81B61");	
 
	//BIQN
	public static final String DB_HOST_BIQN = "OXOBIQN1.FEMCOM.NET:1535/BIFINQA.FEMCOM.NET";
	public static final String DB_USER_BIQN = "VIEW_MEJORA";
	public static final String DB_PASSWORD_BIQN = PasswordUtil.decryptPassword("8AFBC644502CD416C70E5AD49CE32055");

	//Microstrategy
	public static final String URL_MICROSTRATEGY = "http://POSQA1@serviciosexternos.com.mx:Comercio.06@10.185.176.16/MicroStrategy/asp/Main.aspx";
	public static final String URL_USER = "qabi";
	public static final String URL_PASSWORD = PasswordUtil.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");
 
	
	//BIMTTO
	public static final String DB_USER_BIMTTO = "BIETL";
	public static final String DB_PASSWORD_BIMTTO = PasswordUtil.decryptPassword("BE13564940D7F1065337473492C14ABC");
	public static final String DB_HOST_BIMTTO = "10.184.80.26:1535/BIMTTOQA.femcom.net";
	//BIMTTOQA
	public static final String DB_USER_BIMTTOQA = "VIEW_MEJORA";
	public static final String DB_PASSWORD_BIMTTOQA = PasswordUtil.decryptPassword("8AFBC644502CD416C70E5AD49CE32055");
	public static final String DB_HOST_BIMTTOQA = "oxobiqn1.femcom.net:1535/bimttoqa.femcom.net";
 
	//FCIASQAS
	public static final String DB_USER_FCIASQAS2 = "PORTAL_VIEW";
	public static final String DB_PASSWORD_FCIASQAS2 =PasswordUtil.decryptPassword("A3CA5C52DA0CC8F5DD999344B6D259EB");
	public static final String DB_HOST_FCIASQAS2 = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
	//Usuario y password de Jorge Hernandez para Ctrl-M
	public static final String USER_CTRL_M_JH = "68915";
	public static final String PASSWORD_CTRL_M_JH = PasswordUtil.decryptPassword("A0034DF9EEE101291C681F8FCB842289");
	//ODS
	public static final String DB_USER_ODS = "BIETL";
	public static final String DB_PASSWORD_ODS ="biqa_cdabv13";
	public static final String DB_HOST_ODS = "10.184.40.85:1535/FCODSQA.FEMCOM.NET";

 
	/******  RDM  ******/
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
 
	// Usuarios RDM
	public static final String usuarioRicardo = "1328451";
	public static final String contrasenaRicardo = PasswordUtil.decryptPassword("C58ACEA763FC1441370A8963D2A085FC");
	// Usuarios RDM Ruteo Dinamico
	public static final String usuarioRD = "RDUNIGIS";
	public static final String contrasenaRD = PasswordUtil.decryptPassword("B354ED73FC84F745D8B47EE85481607F");
 
	// RADIO FRECUENCIA
	public static final String USER_TERA = "rfcut01";
	public static final String PASS_TERA = "178A22F6743BA9AAD96B74441E93F8D7";
 
	public static final String USER_RF = "1328451";
	public static final String PASS_RF = "C58ACEA763FC1441370A8963D2A085FC";
 
	// RDM BASE DE DATOS
	public static final String DB_USER_CONECTARDM100 = "RDM100";
	public static final String DB_PASSWORD_CONECTARDM100 = PasswordUtil.decryptPassword("rdmcut17");
	public static final String DB_HOST_CONECTARDM100 = "10.184.48.24/oxrepledbq00.femcom.net";
 
	// WM
	public static final String DB_USER_WM = "QAVIEW";
	public static final String DB_PASSWORD_WM = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");

	public static final String DB_HOST_WM = "oxzvmwmiracq01.femcom.net:1521/FCWM6QA.FEMCOM.NET";
			//"10.184.48.216:1535/FCWM6QA.FEMCOM.NET";

	public static final String DB_HOST_WM2 = "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";
 
	// RMS fcrmsdb61
	public static final String DB_USER_FCRMSQA = "QAVIEW";
	public static final String DB_PASSWORD_FCRMSQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCRMSQA = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";
 
	// DAS
	public static final String DB_USER_DAS = "nreader";
	public static final String DB_PASSWORD_DAS = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_DAS = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";
	// BASE DE DATOS FCWMFSIT
	public static final String DB_USER_FCWMFSIT1 = "nreader";
	public static final String DB_PASSWORD_FCWMFSIT1 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCWMFSIT1 = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";
	// BASE DE DATOS FCMOMPRF_RMS16
	public static final String DB_USER_FCMOMPRF_RMS16 = "nreader";
	public static final String DB_PASSWORD_FCMOMPRF_RMS16 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCMOMPRF_RMS16 = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";
	// BASE DE DATOS FCRDMSIT
	public static final String DB_USER_FCRDMSIT = "nreader";
	public static final String DB_PASSWORD_FCRDMSIT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCRDMSIT = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";
	// BASE DE DATOS FCWMESITBACKOFFICE
	public static final String DB_USER_FCWMESIT2 = "nreader";
	public static final String DB_PASSWORD_FCWMESIT2 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCWMESIT2 = "10.184.47.43:1535/FCDASPFR.FEMCOM.NET";

	
/****** RMS  ******/
	// RMS COL
	public static final String DB_USER_BDRMSCQA = "QAVIEW";
	public static final String DB_PASSWORD_BDRMSCQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_BDRMSCQA = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";
 
//	credenciales ControlMz
	public static final String CtrlM_User = "68915";
	public static final String CtrlM_Pss = "48DCC5A12E97F2F1144566301BF5E702";
	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";
//	public static final String CtrlM_User = "gconflo";
//	public static final String CtrlM_Pss = "AA8D0C7C58EDB654458BF76A7977E7EF";
//	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";
//	varables globales de RMS Colombia Conecta	
 
	public static final String DB_HOST_BDPRMSQ = "";
	public static final String DB_USER_BDPRMSQ = "";
	public static final String DB_PASSWORD_BDPRMSQ = "";
 
//	RMS Perú
	public static final String DB_USER_BDRMSPERQA = "RMS100";
	public static final String DB_PASSWORD_BDRMSPERQA = PasswordUtil
			.decryptPassword("5F83B010547B5DA3D27275203D3B31DA");
	public static final String DB_HOST_BDRMSPERQA = "10.186.3.53:1527/BDPRMSQ.FEMCOM.NET";
 
	// RMS Chile
	public static final String DB_USER_BDCHRMSCQA = "RMS100";
	public static final String DB_PASSWORD_BDCHRMSCQA = PasswordUtil
			.decryptPassword("1CD10F979189DA0768DA27AFB5970122");
	public static final String DB_HOST_BDCHRMSCQA = "10.186.3.56:1541/BDCHRMSQ.FEMCOM.NET";
 
	// credenciales RMS
	public static final String RMS_User = "RMS100";
	public static final String RMS_Pss = PasswordUtil.decryptPassword("99F7A0CB3DEA09AA207C2A33BC5B99C6");
	public static final String RMS_URL = "http://10.184.16.184:8029/rmsQA.html";
 
	// credenciales RMS
	public static final String RMS_User_CHILE = "RMS100";
	public static final String RMS_Pss_CHILE = PasswordUtil.decryptPassword("1CD10F979189DA0768DA27AFB5970122");
	public static final String RMS_URL_CHILE = "http://10.186.3.54:8032/RMSCHLQA.html";
 
	// credenciales RMS Col
	public static final String RMS_UserCol = "RMS100";
	public static final String RMS_PssCol = PasswordUtil.decryptPassword("E294EC6DCC58CB4368980D8CCDD201BD");
	public static final String RMS_URLCol = "http://10.185.48.23:8035";
 
	public static final String RMS_User2 = "6004010";
	public static final String RMS_Pss2 = PasswordUtil.decryptPassword("0B94EA65458E93BEF1EF9CBE1DB28A5C");

/******  WEB ******/

		//****************************************** APLICATIVOS **************************************
	//----------------------------------------- CONTROL-M  ---------------------------------------
	public static final String URL_CTRL_M = "http://10.184.56.194:18080/ControlM";
	public static final String USER_CTRL_M = "48025";
	public static final String PASSWORD_CTRL_M = PasswordUtil.decryptPassword("C742FB4262B8025AB80F63D82FB4AD6B");

	//----------------------------------------- ULTIMUS ---------------------------------------
	public static final String URL_ULTIMUS = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx";
	/*Tiene acceso a SARv3
	 */
	public static final String USER_ULTIMUS_6006020 = "6006020";
	//------------------------------------------ PEGA ---------------------------------------
	public static final String URL_PEGA = "http://10.185.176.11:7011/prweb/PRWebLDAP1";
	/*Tiene acceso a SARv3
	 */
	public static final String USER_PEGA_6006020 = "6006020";

	//----------------------------- PORTAL APLICACIONES (ACCESS CONTROL) --------------------------
	public static final String URL_PORTAL_APPS = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String URL_PORTAL_APPS_SAR = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";	
	public static final String URL_PORTAL_APPS_SIMOTI_CH_PR_COL = "https://10.184.40.135:8443/AccessControl/pages/login_form.jsf";
		
	//----------------------------------------- XPOS ---------------------------------------
	public static final String USER_CAJERO = "RUGAMA8502065";	
	public static final String PASS_CAJERO = "Comercio.1";
	
	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Oxxo
	 * SARv3 - Perfil SUPERUSUARIO/ADMIN
	 * */
	public static final String USER_PORTAL_APPS_CMOXXOHEXAWARE30 = "CMOXXOHEXAWARE30";
	public static final String PASSWORD_PORTAL_APPS_CMOXXOHEXAWARE30 = PasswordUtil.decryptPassword("B8C522B8E94F777D02D08F53881C3149");
	
	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Marca
	 * SARv3 - Perfil SUPERUSUARIO/ADMIN
	 * SIMOTI Chile - Administrador
	 * SIMOTI Colombia
	 * */
	public static final String USER_PORTAL_APPS_6006020 = "6006020";
	public static final String PASSWORD_PORTAL_APPS_6006020 = PasswordUtil.decryptPassword("B02C1C59B65E103E0767FC452DBDEA1D");

	/* Aplicación - Perfiles
	 * SARv3 - PROTECCION_PATRIMONIAL
	 * */
	public static final String USER_PORTAL_APPS_6006027 = "6006027";
	public static final String PASSWORD_PORTAL_APPS_6006027 = PasswordUtil.decryptPassword("5C101338C368677758B73AE83F7F2EE5");
	
	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Bara
	 * SARv3 - CONTROL_TIENDA
	 * */
	public static final String USER_PORTAL_APPS_6006028 = "6006028";
	public static final String PASSWORD_PORTAL_APPS_6006028 = PasswordUtil.decryptPassword("EC82AB250FD2D892F5023947A58F9275");

	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Plaza Bara
	 * SARv3 - DESPACHO_DENUNCIA
	 * */
	public static final String USER_PORTAL_APPS_6006030 = "6006030";
	public static final String PASSWORD_PORTAL_APPS_6006030 = PasswordUtil.decryptPassword("A0CCA89B9E2D4580441E4BD34B65CBDB");
	
	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Plaza Oxxo
	 * */
	public static final String USER_PORTAL_APPS_6006015 = "6006015";
	public static final String PASSWORD_PORTAL_APPS_6006015 = PasswordUtil.decryptPassword("2C9C94598AEA7304F497CBC8D41EF569");
	
	/* Aplicación - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Mensajeria
	 * */
	public static final String USER_PORTAL_APPS_6006024 = "6006024";
	public static final String PASSWORD_PORTAL_APPS_6006024 = PasswordUtil.decryptPassword("");
 
	/* Aplicación - Perfiles
	 * Portal Remesas - Perfil Administrador
	 * Portal Cobro Servicios Perfiles -
	 * */
	public static final String USER_PORTAL_APPS_6005105 = "6005105";
	public static final String PASSWORD_PORTAL_APPS_6005105 = PasswordUtil.decryptPassword("87368FF33EB7D26A9EAEB347C1B9FDED");
 
	
	//----------------------------- CNT (CATALOGO NACIONAL DE TIENDAS) --------------------------
	public static final String URL_CNT = "http://10.184.32.40:8000/tiendas/html/login.html";

	/* Perfil Administrador
	 * */
	public static final String USER_CNT_ADMIN = "pruebas.web";
	public static final String PASSWORD_CNT_ADMIN = PasswordUtil.decryptPassword("FB0A4C6FC23E17AC33298F311FFB07F7");
	/* Perfil Tesoreria
	 * */
	public static final String USER_CNT_TESORERIA = "CMOXXOHEXAWARE30";
	public static final String PASSWORD_CNT_TESORERIA = PasswordUtil.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");

	//----------------------------------------- APEX RuteoDinamico  ---------------------------------------
	public static final String URL_APEX_CARGA_RESTR_TIENDAS = "https://fcrepvc01.femcom.net:7056/ords/f?p=350:LOGIN_DESKTOP:7301672154925:::::";
	public static final String usuarioRDApex = "5328237";
	public static final String contrasenaRDApex = PasswordUtil.decryptPassword("86B098743FDAB935631CAD8348502087");
	//**************************************** BASES DE DATOS ***********************************

	/*BD FCIASQA
	 *Se usa para las aplicaciones: Inventario de Espacios Publicitarios, CNT
	 */
	public static final String DB_USER_FCIASQAS_PORTAL = "PORTAL_VIEW";
	public static final String DB_PASSWORD_FCIASQAS_PORTAL = PasswordUtil.decryptPassword("A3CA5C52DA0CC8F5DD999344B6D259EB");
	public static final String DB_HOST_FCIASQAS_PORTAL = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";

 
	/*BD FCDW2QA
	 *Se usa para las aplicaciones: CNT
	 */
	public static final String DB_USER_FCDW2QA_PORTAL = "hexaware30";
	public static final String DB_PASSWORD_FCDW2QA_PORTAL =PasswordUtil.decryptPassword("4AA4F6FC949AD630F64FEBF5E9D19151");
	public static final String DB_HOST_FCDW2QA_PORTAL = "10.80.12.64:1535/FCDW2QA";

 
	/*BD FCTDAQA 
	 *Se usa para las aplicaciones: SIMOTI Mexico
	 */
	public static final String DB_USER_FCTDAQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_FCTDAQA_QAVIEW =  PasswordUtil.decryptPassword("1BFF06F3DC3677874E5CFF0C15C182EC");
	public static final String DB_HOST_FCTDAQA_QAVIEW = "10.184.32.19:1521/FCTDAQA.FEMCOM.NET";
	/*BD FCTPEQA / Portal de Remesas
	 *Se usa para las aplicaciones: SIMOTI Mexico
	 */
	public static final String DB_USER_FCTPEQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_FCTPEQA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTPEQA_QAVIEW = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";
 
	
	/*BD FCWM6QA 
	 *Se usa para las aplicaciones: SIMOTI Colombia/Mexico
	 */
	public static final String DB_USER_FCWM6QA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_FCWM6QA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");

	public static final String DB_HOST_FCWM6QA_QAVIEW = "oxzvmwmiracq01.femcom.net:1521/FCWM6QA.FEMCOM.NET";
	
	public static final String DB_HOST_FCWM6QA_QAVIEW2 = "10.184.48.217:1535/fcwm6QA.femcom.net";

	/*BD FCTDCQA 
	 *Se usa para las aplicaciones: SIMOTI Colombia
	 */
	public static final String DB_USER_FCTDCQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_FCTDCQA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTDCQA_QAVIEW = "10.184.80.120:1521/FCTDCQA.FEMCOM.NET";
	/*BD OCHWMQA 
	 *Se usa para las aplicaciones: SIMOTI Chile
	 */
	public static final String DB_USER_OCHWMQA_POSUSER ="POSUSER";
	public static final String DB_PASSWORD_OCHWMQA_POSUSER =  PasswordUtil.decryptPassword("2C7E5D93EAF7FF1140C6F1328B7A491A");
	public static final String DB_HOST_OCHWMQA_POSUSER = "10.186.13.26:1533/OCHWMQA.FEMCOM.NET";
	/*BD OIWMQA
	 *Se usa para las aplicaciones: SIMOTI Peru
	 */
	public static final String DB_USER_OIWMQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_OIWMQA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_OIWMQA_QAVIEW = "10.184.40.134:1521/OIWMQA.FEMCOM.NET";
 
	/*BD FCWMREQA
	 *Se usa para las aplicaciones: SIMOTI Colombia/Mexico
	 */
	public static final String DB_USER_FCWMREQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_FCWMREQA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMREQA_QAVIEW = "10.185.177.17:1521/fcwmreqa.femcom.net";
 
	/*BD FCWMREQA
	 *Se usa para las aplicaciones: SIMOTI Colombia
	 */
	public static final String DB_USER_BDRMSCQA_QAVIEW ="QAVIEW";
	public static final String DB_PASSWORD_BDRMSCQA_QAVIEW =  PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_BDRMSCQA_QAVIEW = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";

	//BD FCODSQA
	public static final String DB_USER_FCODSQA ="QAVIEW";
	public static final String DB_PASSWORD_FCODSQA = "Passw0rd#20";
	public static final String DB_HOST_FCODSQA = "fcobidb1q00.femcom.net:1535/FCODSQA.FEMCOM.NET";

	
	//FCTPEQA_S1_MTY 	
    public static final String DB_HOST_FCTPEQA_S1_MTY = "fcdbwmq1.femcom.net:1521/FCTPEQA.FEMCOM.NET";
    public static final String DB_USER_FCTPEQA_S1_MTY = "QAVIEW";
    public static final String DB_PASSWORD_FCTPEQA_S1_MTY = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	
    //Usuarios #AVANTE
   // public static final String getAvanteServer = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?langCode=ESA&_logoutRedirect=y";
    
    public static final String getImartinezTiEbsUsr = "IRVIN_M";
    public static final String getImartinezTiEbsPwd = PasswordUtil.decryptPassword("836979FE655E91F38ED40048C75A0989");

	/*BD EBS QA
	 *Se usa para las aplicaciones EBS
	 */

	public static final String DB_USER_EBS_QA= "appsview";
	public static final String DB_PASSWORD_EBS_QA = "appsview";
	public static final String DB_HOST_EBS_QA = "10.184.48.61:1532/AVEBQA";


	public static final String DB_USER_WMLOG_QA = "QAVIEW";
	public static final String DB_PASSWORD_WMLOG_QA = "Passw0rd#20";
	public static final String DB_HOST_WMLOG_QA = "172.26.221.200:1521/FCWMLQA.femcom.net";

    //KYRIBA amex FTP SERVER QA
    public static final String FTP_AMEX__HOST_QA = "oxvwqbocq01.femcom.net";
    public static final int FTP_AMEX__PORT_QA = 22;
    public static final String FTP_AMEX_USER_QA = "posuser";
    public static final String FTP_AMEX_PSW_QA = "posuser";

	/*BD TGORPD
	 */
	public static final String DB_USER_TGO ="BIETL";
	public static final String DB_PASSWORD_TGO =  "biqa_irew13";
	public static final String DB_HOST_TGO = "oxobiqn1.femcom.net:1535/FCDW4QA.FEMCOM.NET";
	
	 /* CREDENCIALES BD PV 10.200.10.17
		 */
		public static final String urlXposStorePV10_17 = "jdbc:sqlserver://10.200.10.17:1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		public static final String urlSatelitesPV10_17 = "jdbc:sqlserver://10.200.10.17:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true";
		public static final String userPV10_17 = "sa";
		public static final String passwordPV10_17 = "12345678";
	/**
	 * CREDENCIALES PARA SEMBRADO ADMIN
	 */
	public static final String urlSembrado = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String userAdminSembrado = "6006068";
	public static final String passSembrado = "Workflows19";

	/**
	 * CREDENCIALES MEGAWORKFLOWS
	 */
	public static final String urlPega = "http://10.185.176.11:7011/prweb/PRWebLDAP1";
	public static final String url2Pega = "http://oxvbpmapq01.femcom.net:7011/prweb/PRWebLDAP1";
	public static final String userPega = "6006020";
	public static final String passPega = "";
	public static final String userPegaPlanchado = "6006068";
	public static final String passPegaPlanchado = "";

	public static final String urlUltimus = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Main.aspx";
	public static final String url2Ultimus = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx";
	public static final String userUltimus = "3119304";
	public static final String passUltimus = "";

	public static final String userMegaWF = "6006015";
	public static final String passMegaWF = "Comercio.38";
	
	/**
	 * CREDENCIALES DE USUARIOS ADMIN
	 */
	public static final String user = "6006027";
	public static final String ps = "72525BDEF703AD656B192FF6F30D5E9D";

	//Usuario ultimus Categoria con perfil de administrador
	public static final String ULTIMUS_USER = "6006068";
	public static final String ULTIMUS_USER_CEDIS = "1779064"; 
	public static final String ULTIMUS_USER_ADMIN = "6006063";
	public static final String ULTIMUS_USER_CATEGORIA = "6004860";
	
	//Usuario ultimus para acceso a Panp3
	public static final String ULTIMUS_USER_SELECT_PROVEEDOR = "6004860";
	public static final String ULTIMUS_PANP3_USER_MERCANCIAS = "6002938";
	public static final String ULTIMUS_PANP3_USER_ADMIN = "6005141";
	
	//Usuario para acceso a pega y el portal de aplicaciones 
	public static final String PEGA_USER = "6006020";
	
	/**
	 * FTP PLANOGRAMAS
	 */
	public static final String HOST_FTP_PLANOGRAMAS = "oxvctmpq1.femcom.net";
	public static final String USER_FTP_PLANOGRAMAS = "usradmpl";
	public static final String PASS_FTP_PLANOGRAMAS = PasswordUtil.decryptPassword("D3E26F3E3F47FDE3C42529B7E67FF5F4");
	
	/**
	 * BD SIV3/ PLANOGRAMAS
	 */
	public static final String DB_USER_PLANOGRAMAS_FCPVEQA = "QAVIEW";
	public static final String DB_PASS_PLANOGRAMAS_FCPVEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_PLANOGRAMAS_FCPVEQA = "10.184.32.156:1521/FCPVEQA.FEMCOM.NET";
	
	/**
	 * BD RMS10
	 */
	public static final String DB_USER_RMS10 = "nreader"; 
	public static final String DB_PASS_RMS10 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RMS10 = "10.184.37.14:1521/FCRMSMGR";
	
	/**
	 * BD RMS16
	 */
	public static final String DB_USER_RMS16_= "nreader";
	public static final String DB_PASS_RMS16 = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RMS16_ = "10.184.47.53:1537/FCMOMPFR";
	
	
	/**
	 * SIPRE_BARA
	 */
	public static final String DB_USER_SIPREBARA = "XXWF_SIPRE2_BARA";
	public static final String DB_PASSWORD_SIPREBARA = ("Xxsipre2bara_2014");
	public static final String DB_HOST_SIPREBARA = "oxfbdfciasq00.femcom.net:1535/FCIASQA.FEMCOM.NET";
	
	/**
	 * SIPRE_OXXO
	 */
	public static final String DB_USER_SIPREOXXO = "XXWF_SIPRE2";
	public static final String DB_PASSWORD_SIPREOXXO = ("qaSipre_2");
	public static final String DB_HOST_SIPREOXXO = "oxfbdfciasq00.femcom.net:1535/FCIASQA.FEMCOM.NET";

	/**
	 * SEMBRADO FCPVEQA
	 */
	public static final String DB_USER_FCPVEQA = "QAVIEW";
	public static final String DB_PASSWORD_FCPVEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCPVEQA = "10.184.32.158:1521/FCPVEQA.FEMCOM.NET";

	// usuario para accesos a Cuanti con domminio de Oxxo
	public static final String CUANTI_USER = "cadenaoxxo_mty1\\6006030";
	public static final String CUANTI_PASS = PasswordUtil.decryptPassword("2C9C94598AEA7304F497CBC8D41EF569");

	public static final String ULTIMUS_MEGA_USER = "3119304";
	public static final String MEGA_USER = "cadenaoxxo_mty1\\6006015";
	public static final String MEGA_PASS = "Comercio.38";
	
	//DB Cuanti
	public static final String DB_USERNAME_CUANTI = "XXWF_PRO";
	public static final String DB_PASS_CUANTI = "Xxwfpro#14";
	public static final String DB_HOST_CUANTI = "oxfbdfciasq00.femcom.net:1535/FCIASQA.FEMCOM.NET";
	
	public String RFC;
	
	public static final String SIPRE_BARA_USER = "6006015";
	public static final String SIPRE_BARA_PS = "Comercio.44";

	/*
	 * CREDENCIALES CONTROL-M ANTONIO
	 */
	public static final String CTRLM_USER = "jazaravi";
	public static final String CTRLM_PASS = PasswordUtil.decryptPassword("913F144D52553D0A97955FC8111D3985");
	
	/*
	 * CREDENCIALES DE CONTROL-M
	 */
	//-
	public static final String CtrlM_User_EsmeG = "eggartor";
	public static final String CtrlM_Pss_EsmeG= PasswordUtil
			.decryptPassword("3FB1FB572A581146AF3D876CCAF3A268");
	//-
	public static final String CtrlM_User_Neo10 = "6006068";
	public static final String CtrlM_Pss_Neo10 = PasswordUtil
			.decryptPassword("CCB7F64B7A4C8B841C43272E7D98D46A");
	
	/*
	 * CREDENCIALES DE CONTROL-M SIPRE OXXO
	 */
	public static final String CtrlMSipreOxxo_User = "mishermon";
	public static final String CtrlMSipreOxxo_Pss= "OxxoEne2023!";
	public static final String CtrlMSipreOxxo_URL = "http://10.184.56.194:18080/ControlM/";
	
	/**
	 * 
	 * XXVA_SIV3
	 *
	 */
	
	public static final String DB_USER_XXVA_SIV3 = "QAVIEW";
	public static final String DB_PASSWORD_XXVA_SIV3 = "Passw0rd#20";
	
	public static final String DB_USER2_XXVA_SIV3 = "XXVA_SIV3";
	public static final String DB_PASSWORD2_XXVA_SIV3 = "xxvasiv3#QA";
	
	public static final String DB_USER_ESMEG_FCPVEQA = "6006020";
	public static final String DB_PASS_ESMEG_FCPVEQA = "6006020_QA";
	
	public static final String DB_HOST_XXVA_SIV3 = "10.184.32.158:1521/FCPVEQA.FEMCOM.NET";
	
	
	/**
	 * 
	 * DAS_PERF
	 *
	 */
	
	public static final String DB_USER_DAS_PERF = "nreader";
	public static final String DB_PASSWORD_DAS_PERF = "pq$#sl74";
	public static final String DB_HOST_DAS_PERF = "10.184.37.67:1521/FCDASUAT";
	


	/**
	 * 
	 * RMS16
	 *
	 */
	
	public static final String DB_USER_RMS16 = "nreader";
	public static final String DB_PASSWORD_RMS16 = "pq$#sl74";
	public static final String DB_HOST_RMS16_2 = "FCMOMUAT:1521/10.184.37.67";
	public static final String DB_HOST_RMS16 = "10.184.37.47:1521/FCMOMUAT";


	/**
	 * CREDENCIALES PLANCHADO
	 */
	public static final String urlPlanchado = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx";
	public static final String userPlanchado = "107745";
	public static final String passPlanchado = "";
	public static final String DB_HOST_WF = "";
	public static final String DB_USER_WF = "";
	public static final String DB_PASSWORD_WF = "";
	
	
	/**
	 * CREDENCIALES SIPRE OXXO 
	 */
	public static final String urlSipreOXXO = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx";
	public static final String userSipreOXXOUltimusPlaza = "112016";
	public static final String passSipreOXXOUltimusPlaza = "";
	public static final String userSipreOXXOUltimusCategory = "6006021";
	public static final String passSipreOXXOUltimusCategory = "";
	public static final String userSipreOXXOUltimusAdmin = "6006032";
	public static final String passSipreOXXOUltimusAdmin = "";
	public static final String userPortalSipreOXXO = "6006028";
	public static final String passPortalSipreOXXO = "Comercio.32";
	public static final String urlSipreOXXOPEGA = "http://10.185.176.11:7011/prweb/PRWebLDAP1"; 
	public static final String userSipreOXXOAdminPEGA = "6006033";
	public static final String passSipreOXXOUAdminPEGA = "";
	public static final String userSipreOXXOPlazaPEGA = "6006028";
	public static final String passSipreOXXOPlazaPEGA = "";
	public static final String userSipreOXXOCategoryPEGA = "6006018";
	public static final String passSipreOXXOCategoryPEGA = "";
	
	
	/**
	 * CREDENCIALES VARIEDOXXO
	 */	
	
	public static final String userVariedOXXOCategory = "6007555";
	public static final String passVariedOXXOCategory = "";
	public static final String userVariedOXXOCatalogos = "6007718";
	public static final String passVariedOXXOCatalogos = "";
	public static final String userPortalVariedOXXO = "6006028";
	public static final String passPortalVariedOXXO = "Comercio.32";


	/**
	 * CREDENCIALES REPLENISHMENT OXXO 
	 */
	public static final String urlRepleOXXO = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String userRedRepleOXXO= "6006060";
	public static final String passRedRepleOXXO = "Comercio*4";
 
 /**
	 * CREDENCIALES DEVOLUCIONES
	 */
	public static final String urlPEGA = "http://10.185.176.11:7011/prweb/PRWebLDAP1";
	public static final String userAdminDevoluciones = "6006026";
	public static final String userFFDevoluciones = "3119304";
	public static final String userCategoryDevoluciones = "3119304";

	/*
	 * CREDENCIALES BASE DE DATOS DEL PUNTO DE VENTA
	 * SUSTITUIR IP DEL PUNTO DE VENTA A CONECTAR
	 */	
	public static final String DB_HOST_XPOS = "jdbc:sqlserver://%s;encrypt=true;trustServerCertificate=true";
	//public static final String DB_USER_XPOS = "sa";
	public static final String DB_PASS_XPOS = "12345678";
	
	/*
	 * CONEXION CON BASE DE DATOS DE EVOLUCION
	 * CREDENCIALES BD PV 10.200.10.212
	 */
	public static final String urlPV10_212_XposStore = "jdbc:sqlserver://10.200.10.212:1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String urlPV10_212_Satelites = "jdbc:sqlserver://10.200.10.212:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true";
	public static final String userDB10_212 = "sa";
	public static final String passDB10_212 = "12345678";

	 /* CREDENCIALES BD PV 10.200.10.17
	 */
	public static final String DB_HOST_CLOUD = "jdbc:sqlserver://oxzsqlmixposdev.529c1106f15d.database.windows.net;encrypt=true;trustServerCertificate=true;";

	/*
	 * CREDENCIALES NUBE EVOLUCION
	 */
	public static final String urlEvolucion = "jdbc:sqlserver://oxzsqlmixposdev.529c1106f15d.database.windows.net;databaseName=XposCloudItemQA;encrypt=true;trustServerCertificate=true";
	public static final String userEvolucion = "test_team";
	public static final String passEvolucion = "D35D5D4B809B73598049EA1BA11CAEB6";
	public static final String userEvolucion2 = "xposqa";	
	public static final String passEvolucion2 = PasswordUtil.decryptPassword("8ACE262E560B04E5B8AB5F5325624DB2");


	/*
	 * CREDENCIALES BD - Preinventarios Inteligentes
	 */

	public static final String urlPI_XposStore = "jdbc:sqlserver://10.75.6.86;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String urlPI_Satelites = "jdbc:sqlserver://10.75.6.86;databaseName=Satelites;encrypt=true;trustServerCertificate=true";
	public static final String userDB_PI = "sa";
	public static final String passDB_PI = "12345678";

	/**
	 * IMA ORACLE TGO
	 */
	public static final String hosttgoRPD = "10.184.80.26:1535/oxobiqn1.femcom.net";
	public static final String usertgoRPD = "BIETL";
	public static final String passtgoRPD = "biqa_irew13";
	
	public static final String urlPV_116_XposStore = "jdbc:sqlserver://10.217.24.116;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String userPV_116 = "sa";
	public static final String passPV_116 = "12345678";
	
	/*
	 * CREDENCIALES BD - POS Movil
	 */
	public static final String userPV = "sa";
	public static final String passPV = "12345678";
	
	public static final String urlPV_117_XposStore = "jdbc:sqlserver://10.217.24.117;databaseName=XposStore;encrypt=true;trustServerCertificate=true";
	public static final String userPV_117 = "sa";
	public static final String passPV_117 = "12345678";
	
	public static final String urlOxxoCoudSalesQA = "jdbc:sqlserver://oxzsqlmixposdev.529c1106f15d.database.windows.net;datebaseName=OxxoCloudSalesQA;encrypt=true;trustServerCertificate=true";
	public static final String userQANube = "cesar.guel";
	public static final String passwordQANube = "@VWZ2QwN*h-XLKRre6_i1";
	
	public static final String urlOxxoCoudQA = "jdbc:sqlserver://oxzsqlmixposdev.529c1106f15d.database.windows.net;datebaseName=XposCloudPromotionsQA;encrypt=true;trustServerCertificate=true";
	public static final String userQANubeP = "cesar.guel";
	public static final String passwordQANubeP = "@VWZ2QwN*h-XLKRre6_i1";
	
	public static final String urlOxxoCloudQASett = "jdbc:sqlserver://oxzsqlmixposdev.529c1106f15d.database.windows.net;datebaseName=XposCloudSettingQA;encrypt=true;trustServerCertificate=true";
	public static final String userQANubeG = "xposqa";
	public static final String passwordQANubeG = "OxX0.Xpos2022";
	
	public static final String passConfiguracionCorrecta = PasswordUtil.decryptPassword("09F9A0E0BA9B78410806754FCEEDB53E");
	public static final String passConfiguracionIncorrecta = PasswordUtil.decryptPassword("2D1D19E911BFCD19B4414209C09A376E");


	public static final String userPVGeneral = "sa";
	public static final String passPVGeneral = PasswordUtil.decryptPassword("3B93B9AEAA4D85DEF36A0BDAD00DED44");
	public static final String passCajero = PasswordUtil.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");
	public static final String passNoCajero = PasswordUtil.decryptPassword("D03E66D31554FC55596DB32C8C9A91EB");



	/**
	 * Credenciales MEP
	 */
	public static final String URL_Appium_Device = "http://127.0.0.1:4723";

	/*
	 * VARIABLES DINAMICAS PIPELINE
	 */
	public static String crTiendaD;
	public static String ipPvD;
	public static String nomTiendaD;
	
	public static final String urlMep = "http://127.0.0.1:4723";
	public static final String userMep = "1723163";
	public static final String passwordMep = "biqa_irew13";
	
	/**
	   * CREDENCIALES RDM MTY QA - Vanessa Torres - CEDIS
	   */
			public static final String urlRDMmty = "fcrdmq01.femcom.net:1535/FCRD01CU.FEMCOM.NET";
			public static final String usercRDMmty = "RDMVIEW"; 
			public static final String passwordRDMmty = PasswordUtil.decryptPassword("F23AF33B57C94F17BBF44A0A67985620");
	   
			/**
			* CREDENCIALES RDM Tradicional Cloud V11 (RDM100)
			*/
			public static final String urlRDMv11 = "172.26.8.148:1535/FCRD01QA.FEMCOM.NET";
			public static final String userRDMv11 = "RDM100";
			public static final String passwordRDMv11 = PasswordUtil.decryptPassword("7EF5531CE42BD780BF20F98EEFFB5F3902DA95C4818E8CF742E8F9CB7B06311D");
		
			/**
			    * CREDENCIALES RDM TLAJOMULCO 
			    */
			public static final String URLTlajo = "10.180.49.87:1535/FCRD71CU.FEMCOM.NET";
			public static final String UsercTlajo = "6006118";
			public static final String passwordTlajo = PasswordUtil.decryptPassword("8AA7D5A50DB6704318A17AFB36A987447AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");

			/**
			* CREDENCIALES RDM 100 TLAJOMULCO (RDM100)
			*/
			public static final String urlRDM100 = "10.180.49.87:1535/FCRD71CU.FEMCOM.NET";
			public static final String userRDM100 = "RDM100";
			public static final String passwordRDM100 = PasswordUtil.decryptPassword("F390207ED6EC2E6FCB56289BFDA0AF7E");
			
			//CREDENCIALES TablePlus STAGING- Vanessa Torres
			public static final String hosttp = "jdbc:mysql://movilidadentienda-staging.mysql.database.azure.com:3306/xxbdo?user=vanessa.torres@movilidadentienda-staging&password=VaTo2024!";
				

			/**
			   * CREDENCIALES IMA QA - Vanessa Torres - CEDIS
			   */
			public static final String url = "jdbc:sqlserver://zmetaprsrvr01.database.windows.net:1433;databaseName=zmetaprsdb01;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
			public static final String userc = "IMAZREADQAUSER"; 
			public static final String password = "Im4zR3s0924_usRq4_2NDa";
				
			/**
			* CREDENCIALES al XPOS
			*/			
			public static final String userXPOS = "sa";
			public static final String passwordXPOS = PasswordUtil.decryptPassword("3B93B9AEAA4D85DEF36A0BDAD00DED44");
		

}