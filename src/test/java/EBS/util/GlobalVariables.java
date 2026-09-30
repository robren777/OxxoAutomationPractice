package EBS.util;

import java.io.File;

import utils.password.PasswordUtil;

public class GlobalVariables {

	public static final String BASE_PATH = System.getProperty("user.dir");
	public static final String PATTERN_EBS = BASE_PATH + File.separator + "Patterns" + File.separator + "EBS"
			+ File.separator;

	// Usuarios #AVANTE
	// public static final String getAvanteServer =
	// "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?langCode=ESA&_logoutRedirect=y";
	public static final String getAvanteServer = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp";

	public static final String getJzarateaHexEbsUsr = "JZARATE_HEXAWARE";
	public static final String getJzarateaHexEbsPwd = PasswordUtil.decryptPassword("719ADE69A5C3F03ECDD9A2CDD4D81B2D");

	public static final String getJesusHexEbsUsr = "JM_PRUEBAS";
	public static final String getJesusHexEbsPwd = PasswordUtil.decryptPassword("528F07A3149B01045DFE344F2E8646DD");

	public static final String get5032838EbsUsr = "5032838";
	public static final String get5032838HexEbsPwd = PasswordUtil.decryptPassword("7EF237ABECE8F1E99F820A62CE5F220A");

	public static final String URL_PORTAL_PROVEEDORES_QA = "https://pruebasweb.oxxo.com/AuthFemcoProx";

	public static final String getJhernandezHexEbsUsr = "JHERNANDEZ_HEX";
	public static final String getJhernandezHexEbsPwd = PasswordUtil
			.decryptPassword("719ADE69A5C3F03ECDD9A2CDD4D81B2D");

	public static final String getJreyesHexEbsUsr = "JREYES_HEX";
	public static final String getJreyesHexEbsPwd = PasswordUtil.decryptPassword("EC411D653B92E0288FA1FFD6945A6727");

	public static final String USUARIOPORTALPROVEEDORES = "CPA9302232MA";
	public static final String CONTRASENAPORTALPROVEEDORES = PasswordUtil
			.decryptPassword("7604358789E636338211D1C0956CFCF8");

	public static final String getRicardoHexEbsUsr = "RROMERO_HEX";
	public static final String getRicardoHexEbsPwd = PasswordUtil.decryptPassword("500029473767D6752081EBA47D04F0EF");

	public static final String getRpalaciosNeoEbsUsr = "RPALACIOS_NEO";
	public static final String getRpalaciosNeoEbsPwd = PasswordUtil.decryptPassword("F6EA71F19CD3F79C5EFF38362B080A6F");

	public static final String getEramirezNeoEbsUsr = PasswordUtil.decryptPassword("");
	public static final String getEramirezNeoEbsPwd = PasswordUtil.decryptPassword("");

	public static final String getMduenasHexEbsUsr = "MDUENAS_HEX";
	public static final String getMduenasHexEbsPwd = PasswordUtil.decryptPassword("4DC393BA5FA497D7426800E3F77A2B32");

	public static final String get3076719EbsUsr = "3076719";
	public static final String get3076719EbsPwd = PasswordUtil.decryptPassword("AA0992AF5B459956C43CCAD03BAC9397");

	public static final String getgrupoti_ebsEbsUsr = "grupoti_ebs";
	public static final String getgrupoti_ebsEbsPwd = PasswordUtil.decryptPassword("4A35ECB90010CBA24DF002F06FE4FDBD");

	//FCWMQA
			public static final String DB_USER_FCWMQA = "QAVIEW";
			public static final String DB_PASSWORD_FCWMQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
			public static final String DB_HOST_FCWMQA = "oxzvmwmiracq01.femcom.net:1521/FCWM6QA.FEMCOM.NET";

			//FTPEQA
			public static final String DB_USER_FCTPEQA = "QAVIEW";
			public static final String DB_PASSWORD_FCTPEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
			public static final String DB_HOST_FCTPEQA = "fcdbwmq1.femcom.net:1521/FCTPEQA.FEMCOM.NET";
			
	// USUARIOS QA GTIM
	public static final String get6008201User = "6008201";
	public static final String get6008201Pswd = PasswordUtil.decryptPassword("A79A31BF1F6DC12F4781A5494D34D99B");

	public static final String userMiguelEscamilla = "MESCAMILLA_GTIM";
	public static final String pwdMiguelEscamilla = PasswordUtil.decryptPassword("49B87CF59639223DC133C9AE295378E6");

	// EBS QA
	public static final String DB_USER_QAEBS = "APPSVIEW";
	public static final String DB_PASSWORD_QAEBS = PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_QAEBS = "10.184.48.60:1532/AVEBQA";

	public static final String getOperacionesUsr = "OPERACIONES";
	public static final String getOperacionesPwd = PasswordUtil.decryptPassword("67560BF5F049956C8380C34B7CC5AA6F");

	// Usuarios #INTERNACIONAL (chile y peru)

	public static final String getEbsInternacional = "http://opvebsapq01.femcom.net:8001/OA_HTML/AppsLocalLogin.jsp?langCode=ESA&_logoutRedirect=y";
	public static final String getJhernandezHexEbsIntUsr = "JHERNANDEZ_HEX";
	public static final String getJhernandezHexEbsIntPwd = PasswordUtil
			.decryptPassword("07C20B1D2CA19B1E356D6AF1E025896A");

	public static final String getJreyesHexEbsIntUsr = "JREYES_HEX";
	public static final String getJreyesHexEbsIntPwd = PasswordUtil.decryptPassword("C9142140497EEBD8C2EF47A2EB5D88A9");

	public static final String getEbsMexico = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp";
	public static final String getGAGUAYOHexEbsUsr = "GAGUAYO_HEXAWARE";
	public static final String getGAGUAYOHexEbsPwd = PasswordUtil.decryptPassword("09B3BAF75FD8D858AA65BCFCF8357247");

	// Control- M
	public static final String CtrlM_URL = "https://172.26.177.68:8446/ControlM/";
	public static final String CtrlM_User = "gaguayo";
	public static final String CtrlM_Pss = PasswordUtil.decryptPassword("09B3BAF75FD8D858AA65BCFCF8357247");

	// Usuarios #IMMEX

	public static final String mhernandez_hex_EBS_USR = PasswordUtil.decryptPassword("");
	public static final String mhernandez_hex_EBS_PWD = PasswordUtil
			.decryptPassword("3F656AAB3CD21AD294EC2EE14B4821EE");

	// Usuarios #COLOMBIA

	// FTP KYRIBA INTERNACIONAL

	// host
	public static final String HOST_QA9 = PasswordUtil
			.decryptPassword("0ECEDE5C0CB1B29AA651F5740523DBCF922BD823772E99F2F5FF0E6BD63EC5B8");
	public static final String HOST_QA10_VIRTUAL = PasswordUtil
			.decryptPassword("727217CB5A1AAC85170D4AE70C1BC550C3B6B3D6A5A106622353563A266DD74E");
	public static final String HOST = PasswordUtil
			.decryptPassword("29A2094820E0289621958423760938E336BF9BBAD8AF44E915521C6E35BBD484");
	public static final String HOST_OLS = PasswordUtil
			.decryptPassword("8015BD39B842348D6CC6865F8CD0030DCF49885A6D645B5229C7D1E6043845A4");
	public static final String HOST_FAC = PasswordUtil
			.decryptPassword("29A2094820E0289621958423760938E3B8CB6D4D516D56D91F21CB67609C1221");
	public static final String HOST_RTP = PasswordUtil
			.decryptPassword("688ED399E2F293E247D3E4EC66703FFD957364B5BC4D9511A432FBBF42AF661F");
	public static final String HOST_PR2 = PasswordUtil
			.decryptPassword("7E007AAD84985F95EE8C2ABCAD5D7E9F6963A5146A4E4B410B1403E32AF5C3E9");
	public static final String HOST_PR2_CO = "10.182.32.22:7797";
	public static final String HOST_PR50v2 = PasswordUtil
			.decryptPassword("3A9C8BAE1396BD20ACE4D803F081EC15922BD823772E99F2F5FF0E6BD63EC5B8");

	// FCIAS/
	public static final String DB_USER_IAS = "portal_view";
	public static final String DB_PASSWORD_IAS = PasswordUtil.decryptPassword("FC040844B14947D896CF6A93CAFA66E0");
	public static final String DB_HOST_IAS = PasswordUtil.decryptPassword(
			"2B07C799DA60299CFAB5022371DD455BAA58B31C17DDB7489D01E819AB4D30C16717993EFECB5015E24E9A5CE874293F");

	// TPEUSER(PE)/FCTDC
	public static final String DB_USER = "wmview";
	public static final String DB_PASSWORD = PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOST = PasswordUtil.decryptPassword(
			"EC0A9E38554ABEA867741A048B57522499C48D25355B4E57D97615334A85DEEE6717993EFECB5015E24E9A5CE874293F");

	// PE1/FCTAE
	public static final String DB_USERPE1 = "wmview";
	public static final String DB_PASSWORDPE1 = PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOSTPE1 = PasswordUtil.decryptPassword(
			"EC0A9E38554ABEA867741A048B575224D79CF7217416310C7AFFF14226AAD21E6717993EFECB5015E24E9A5CE874293F");

	// PE3

	// PE6
	public static final String DB_USERPE6 = "QAVIEW";
	public static final String DB_PASSWORDPE6 = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOSTPE6 = PasswordUtil.decryptPassword(
			"92A9B1C1830A9C104662425C28C91152E15F2576DA9C0041B0CC41540D20A9CB6717993EFECB5015E24E9A5CE874293F");

	// TPE OLS
	public static final String DB_USERPE3 = "wmview";
	public static final String DB_PASSWORDPE3 = PasswordUtil.decryptPassword("1959B95F48820415E1D2A66FAB851DB7");
	public static final String DB_HOSTPE3 = PasswordUtil.decryptPassword(
			"EC0A9E38554ABEA867741A048B575224C2A35DD643DA19B696DAA38A568A15EA6717993EFECB5015E24E9A5CE874293F");

	// peru OIWMQA.FEMCOM.NET----------------scripts
	public static final String DB_USER_Oiwmqa = "WMVIEW";
	public static final String DB_PASSWORD_Oiwmqa = PasswordUtil.decryptPassword("D1814B879E2E546C68B5F126DBC7C5CD");
	public static final String DB_HOST_Oiwmqa = "10.184.40.134:1521/OIWMQA.FEMCOM.NET";

	// TABLAS DE USER FAC
	public static final String DB_USER_FAC = "tpeuser";
	public static final String DB_PASSWORD_FAC = PasswordUtil.decryptPassword("F8B0444F186AADE9448BDB3C81BFE630");
	public static final String DB_HOST_FAC = PasswordUtil.decryptPassword(
			"EC0A9E38554ABEA867741A048B575224EEA8E66C1C824471743A54C8C34B83BE9E41FA08A730B16BB0393CCE9709A415");

	public static final String DB_USER_FacQ = "TPEUSER";
	public static final String DB_PASSWORD_FacQ = PasswordUtil.decryptPassword("B1078AF4CE1BF0B567FA583C3F13FA73");
	public static final String DB_HOST_FacQ = PasswordUtil
			.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C71F44FC6BE133764097F901BCD1762E48");

	// Tablas de LOGWM PR2

	public static final String DB_USER_LOG = "wmlog";
	public static final String DB_PASSWORD_LOG = PasswordUtil.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
	public static final String DB_HOST_LOG = PasswordUtil
			.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7125322925092D89400467A6E96C00290");

	public static final String DB_USER_Data = "posuser";
	public static final String DB_PASSWORD_Data = PasswordUtil.decryptPassword("25AF5CE3DDE8A89AAE9DFCC87ED144A5");
	public static final String DB_HOST_Data = PasswordUtil
			.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7DB687953290CED79479D18EEFE2D9074");

	public static final String DB_USER_DataWmuser = "wmuser";
	public static final String DB_PASSWORD_DataWmuser = PasswordUtil
			.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_DataWmuser = PasswordUtil
			.decryptPassword("8F913BB49AD9F060EEFFB8596C3E27C7DB687953290CED79479D18EEFE2D9074");

	// BDPOSUSER20
	public static final String DB_USER_Puser = "QAVIEW";
	public static final String DB_PASSWORD_Puser = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_Puser = PasswordUtil.decryptPassword(
			"92A9B1C1830A9C104662425C28C91152761EE7F4954ACC6256E7B2BD21B2D0AC6717993EFECB5015E24E9A5CE874293F");

	public static final String DB_USER_FCWMQA_UPD = "Hexaware30";
	public static final String DB_PASSWORD_FCWMQA_UPD = PasswordUtil
			.decryptPassword("A5D4E206919F323F53429EC8AEA1C18B");
	public static final String DB_HOST_FCWMQA_UPD = "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";

	// rms
	public static final String DB_USER_Rms = "RMS100";
	public static final String DB_PASSWORD_Rms = PasswordUtil.decryptPassword("5EED5CABD9338AE6EA71917A17F75E3F");
	public static final String DB_HOST_Rms = PasswordUtil
			.decryptPassword("04432E3694F0A229E7C95DEDB85D211509297D9B66AE4BF1C2790B50FB55B6CB");

	// rmsruebasPR1FCRMSCUT
	public static final String DB_USER_RmsP = "RMS100";
	public static final String DB_PASSWORD_RmsP = PasswordUtil.decryptPassword("5EED5CABD9338AE6EA71917A17F75E3F");
	public static final String DB_HOST_RmsP = "10.184.56.124:1525/FCRMSCUT.FEMCOM.NET";

	// EBSPruebaFCFINQA
	public static final String DB_USER_EBS = "wmuser";
	public static final String DB_PASSWORD_EBS = PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_EBS = "10.184.56.95:1521/FCFINQA.FEMCOM.NET";

	// EBS FCEBC4Vante
	public static final String DB_USER_EBS_FCEBC4 = "WMUSER";
	public static final String DB_PASSWORD_EBS_FCEBC4 = PasswordUtil
			.decryptPassword("82862214493D97A5794DF801269CBF9F");
	public static final String DB_HOST_EBS_FCEBC4 = "10.80.3.159:1602/fcebc4";

	// wmlostatusRunPR1FCWMLQA
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQA = "10.184.48.217:1535/FCWMLQA.FEMCOM.NET";

	// Tablas RMS méxico RMSVIEW
	public static final String DB_USER_MEX = "RMSVIEW";
	public static final String DB_PASSWORD_MEX = PasswordUtil.decryptPassword("6920831A3426764BB7E688FAA3F3F63F");
	public static final String DB_HOST_MEX = "10.184.56.124:1525/FCRMSCUT.FEMCOM.NET";

	// Tablas FCWMQA_NUEVA (host diferente)
	public static final String DB_USER_FCWMQA_NUEVA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMQA_NUEVA = PasswordUtil
			.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMQA_NUEVA = "10.184.48.216:1535/fcwm6QA.femcom.net";

	// Tablas Chile RMs100
	public static final String DB_USER_RMSWMUSERChile = "wmuser";
	public static final String DB_PASSWORD_RMSWMUSERChile = PasswordUtil
			.decryptPassword("E023A372AD3B3237F098A0EC4D27ECE7");
	public static final String DB_HOST_RMSWMUSERChile = "10.80.2.222:1541/BDCHRMSQ.FEMCOM.NET";

	// Tabla RMS100 COLOMBIA

	public static final String DB_USER_RMS_COL = "RMS100";
	public static final String DB_PASSWORD_RMS_COL = PasswordUtil.decryptPassword("E294EC6DCC58CB4368980D8CCDD201BD");
	public static final String DB_HOST_RMS_COL = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";

	// Tabla RMS100 MEXICO SID: FCRMSQA

	public static final String DB_USER_RMS_MEX = "QAVIEW";
	public static final String DB_PASSWORD_RMS_MEX = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_RMS_MEX = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";

	// Tabla EBS COLOMBIA SID: FCEBSCO2

	public static final String DB_USER_EBS_COL = "wmuser";
	public static final String DB_PASSWORD_EBS_COL = PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_EBS_COL = "10.80.2.184:1531/FCEBSCO2";

	// Tablas chile wmLog--

	public static final String DB_USER_LogChile = "Wmlog";
	public static final String DB_PASSWORD_LogChile = PasswordUtil.decryptPassword("B8BC60B8008B4BF2A04AA0F2EB9211AB");
	public static final String DB_HOST_LogChile = "10.186.13.26:1533/OCHWMQA.FEMCOM.NET";

	// Tablas chile posuser--

	public static final String DB_USER_PosUserChile = "Posuser";
	public static final String DB_PASSWORD_PosUserChile = PasswordUtil
			.decryptPassword("2C7E5D93EAF7FF1140C6F1328B7A491A");
	public static final String DB_HOST_PosUserChile = "10.186.13.26:1533/OCHWMQA.FEMCOM.NET";

	// Tabla ORAFIN/EBS

	public static final String DB_USER_Ebs = "APPSVIEW";
	public static final String DB_PASSWORD_Ebs = PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_Ebs = "10.184.56.95:1521/FCFINQA.FEMCOM.NET";

	// TPUSER
	public static final String DB_USER_FCTPE = "QAVIEW";
	public static final String DB_PASSWORD_FCTPE = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTPE = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";

	// Servicio electronicos
	public static final String DB_USER_FCTAEQA = "QAVIEW";
	public static final String DB_PASSWORD_FCTAEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTAEQA = "10.184.80.120:1521/FCTAEQA.FEMCOM.NET";

	public static final String DB_USER_FCSWQA = "swview";
	public static final String DB_PASSWORD_FCSWQA = PasswordUtil.decryptPassword("7AE65940311F261353C76806C270E990");
	public static final String DB_HOST_FCSWQA = "10.184.80.120:1521/FCSWQA.FEMCOM.NET";

	public static final String DB_USER_FCTDCQA = "qaview";
	public static final String DB_PASSWORD_FCTDCQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCTDCQA = "10.184.80.120:1521/FCTDCQA.FEMCOM.NET";

	public static final String DB_USER_FCWMLTAEQA = "wmlog";
	public static final String DB_PASSWORD_FCWMLTAEQA = PasswordUtil
			.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
	public static final String DB_HOST_FCWMLTAEQA = "10.184.80.120:1521/FCWMLTAQ.FEMCOM.NET";

	public static final String DB_USER_FCMFS = "qaview";
	public static final String DB_PASSWORD_FCMFS = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCMFS = "10.184.32.19:1521/FCMFSQA.FEMCOM.NET";

	// BD nueva
	public static final String DB_USER_FCSWPRD_CRECI = "SWVIEW";
	public static final String DB_PASSWORD_FCSWPRD_CRECI = PasswordUtil
			.decryptPassword("CD418B1D0D38D7E401BDCC705BC09A7E");
	public static final String DB_HOST_FCSWPRD_CRECI = "10.182.56.97:1535/FCSWPRD";

	// FAC
	public static final String DB_USER_FCACQA = "qaview";
	public static final String DB_PASSWORD_FCACQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCACQA = "10.184.80.120/FCACQA.FEMCOM.NET";

	// Queretaro
	public static final String DB_USER_FCSWQA_QRO = "swview";
	public static final String DB_PASSWORD_FCSWQA_QRO = PasswordUtil
			.decryptPassword("7AE65940311F261353C76806C270E990");
	public static final String DB_HOST_FCSWQA_QRO = "10.184.40.120:1521/FCSWQA.FEMCOM.NET";

	public static final String DB_USER_FCWMLTAEQA_QRO = "wmlog";
	public static final String DB_PASSWORD_FCWMLTAEQA_QRO = PasswordUtil
			.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E");
	public static final String DB_HOST_FCWMLTAEQA_QRO = "10.184.40.120:1521/FCWMLTAQ.FEMCOM.NET";
	public static final String DB_USER_FCWMLTAEQA_QA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLTAEQA_QRO_QA = PasswordUtil
			.decryptPassword("0641623006468195175693CCCA34F71E");

	// RDM 01CU
	public static final String DB_USER_RDM = "rdm100";
	public static final String DB_PASSWORD_RDM = PasswordUtil.decryptPassword("C459C0FBA9A4F8462A94D95D24E976DB");
	public static final String DB_HOST_RDM = "10.180.56.86:1535/FCRD01CU.FEMCOM.NET";
	// RDM RDM63CU
	public static final String DB_USER_RDM_63 = "rdm100";
	public static final String DB_PASSWORD_RDM_63 = PasswordUtil.decryptPassword("35B0A9998A32FE5AE0581E6F785E2F80");
	public static final String DB_HOST_RDM_63 = "10.186.13.15:1521/FCRD63CU.FEMCOM.NET";

	// BIETL
	public static final String DB_USER_BI = "BIETL";
	public static final String DB_PASSWORD_BI = PasswordUtil.decryptPassword("299D18CA0A0E2BFB4975E98E28A81B61");
	public static final String DB_HOST_BI = "10.184.80.26:1535/FCDW4QA.FEMCOM.NET";

	// EBS AVANTE
	public static final String DB_USER_AVEBQA = "appsview";
	public static final String DB_PASSWORD_AVEBQA = PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_AVEBQA = "172.26.42.170:1532/AVEBQA";
	
	// EBS AVANTE ACT_FIJO
	public static final String DB_USER_AVEBQA_ACTFIJO = PasswordUtil.decryptPassword("FFDA9A51FECA2EBAF4FF683DAE635D02");
	public static final String DB_PASSWORD_AVEBQA_ACTFIJO = PasswordUtil.decryptPassword("3854DFF9ED8E78012E7691BB8812349F");
	public static final String DB_HOST_AVEBQA_ACTFIJO = "172.26.42.170:1532/AVEBQA";

	// CNT
	public static final String DB_USER_FCIASQA = "wmuser";
	public static final String DB_PASSWORD_FCIASQA = PasswordUtil.decryptPassword("4CC61E5F09F4E484DAE869B7B058B2F8");
	public static final String DB_HOST_FCIASQA = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";

	// SINERGIA
	public static final String DB_USER_FCIASQAS = "qaview";
	public static final String DB_PASSWORD_FCIASQAS = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCIASQAS = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";

	// EBS OIEBSBDQ Peru y chile
	public static final String DB_USER_OIEBSBDQ = "APPSVIEW";
	public static final String DB_PASSWORD_OIEBSBDQ = PasswordUtil.decryptPassword("3F5BB92144C6B52AEEDEBEA236941122");
	public static final String DB_HOST_OIEBSBDQ = "10.186.13.28:1522/OIEBSBDQ";

	// EBSPruebaFCFINQA Usuario con privilegio consultas
	public static final String DB_USER_EBS_Consulta = "APPSVIEW";
	public static final String DB_PASSWORD_EBS_Consulta = PasswordUtil
			.decryptPassword("3F5BB92144C6B52AEEDEBEA236941122");

	// RMS_Mex servidor nuevo solo lectura
	public static final String DB_USER_RmsMexNu = "NREADER";
	public static final String DB_PASSWORD_RmsMexNu = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RmsMexNu = "10.184.37.84:1521/FCRMSSIT";

	// Funciona para la TPE_LOT
	public static final String DB_USER_TPE_LOT = "QAVIEW";
	public static final String DB_PASSWORD_TPE_LOT = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_TPE_LOT = "10.184.40.120:1521/FCTPEQA.FEMCOM.NET";

	// DBS_BI_LT FCTICQA BI TICKET
	public static final String DB_USER_BiTicket = "QAVIEW";
	public static final String DB_PASSWORD_BiTicket = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_BiTicket = "10.184.80.26:1535/FCTICQA";

	// KYRIBA INTERNACIONAL SFTP SERVER
	public static final String FTP_KYRIBA_INTERNACIONAL_HOST = "10.186.13.28";// PasswordUtil.decryptPassword("A6A54A62B1E2A562DBC40ECA124AD0EE");
	public static final int FTP_KYRIBA_INTERNACIONAL_PORT = 22;
	public static final String FTP_KYRIBA_INTERNACIONAL_USER = "oradev";// PasswordUtil.decryptPassword("C08AC54141A5238904854EC71F88CA5D");
	public static final String FTP_KYRIBA_INTERNACIONAL_PSW = "Ora.2022";// PasswordUtil.decryptPassword("9A51EEAD8CB4E2251CB2A23395BBEA66");
	
	//SFTP ACT FIJO
	public static final String SFTP_ACT_FIJO_HOST = "172.26.42.170";
	public static final int SFTP_ACT_FIJO_PORT = 22;
	public static final String SFTP_ACT_FIJO_USER = "usdload";
	public static final String SFTP_ACT_FIJO_PWD = PasswordUtil.decryptPassword("7EDEA5581565FA729861974E95E4263B");

	// KYRIBA F2 Mexico
	public static final String FTP_KYRIBA_Mexico_HOST = "10.184.48.60";// PasswordUtil.decryptPassword("A6A54A62B1E2A562DBC40ECA124AD0EE");
	public static final int FTP_KYRIBA_Mexico_PORT = 22;
	public static final String FTP_KYRIBA_Mexico_USER = "usdload";// PasswordUtil.decryptPassword("C08AC54141A5238904854EC71F88CA5D");
	public static final String FTP_KYRIBA_Mexico_PSW = PasswordUtil.decryptPassword("7EDEA5581565FA729861974E95E4263B");

	// CONTROL-M USER FOR MT940

	public static final String CONTROL_M_USER = PasswordUtil.decryptPassword("19E0190BF9A81A796E830D03466A0D51");
	public static final String CONTROL_M_PWD = PasswordUtil.decryptPassword("F92A7924C1FBD6C8738B9B2C5358125D");
	public static final String CONTROL_M_SERVER = "http://10.184.56.194:18080/ControlM/";

	// Usuario Internacional 'OPERACIONES'
	public static final String getOperacionesEbsIntUsr = "OPERACIONES";
	public static final String getOperacionesEbsIntPwd = PasswordUtil
			.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");

	// FCWMLQA
	public static final String DB_USER_FCWMLQAv2 = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQAv2 = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQAv2 = "172.26.221.200:1521/FCWMLQA.FEMCOM.NET";

	// User Control M Jesus Moreno
	public static final String CONTROL_M_USER_JesusMoreno = "almordel";
	public static final String CONTROL_M_PWD_JesusMoreno = PasswordUtil
			.decryptPassword("B604BBEFAC7906C6AC5800F2F3A105A7");

	// FCWM6QA QAVIEW
	public static final String DB_USER_FCWM6QA = "QAVIEW";
	public static final String DB_PASSWORD_FCWM6QA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWM6QA = "172.26.221.200:1521/FCWM6QA.FEMCOM.NET";

	// EBS ComisionSpin
	public static final String Url_Ebs_Comision = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?requestUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FOA.jsp%3Fpage%3D%2Foracle%2Fapps%2Ffnd%2Fframework%2Fnavigate%2Fwebui%2FNewHomePG%26homePage%3DY%26OAPB%3DFWK_HOMEPAGE_BRAND%26transactionid%3D570987066%26oapc%3D2%26oas%3D4ppKB3RdwGUZ9ZtkXsf3GA..&cancelUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FAppsLogin&langCode=US#dummyAnchor";
	public static final String User_EBS_C = "QRSPIN";
	public static final String Password_EBS_C = "Pagospinqr0";

	// EBS Comision
	public static final String DB_USER_Comision_EBS = "appsview";
	public static final String DB_PASSWORD_Comision_EBS = "appsview";
	public static final String DB_HOST_Comision_EBS = "jdbc:oracle:thin:@//oxfdbfinq1-vip.femcom.net:1532/AVEBQA";

	public static final String User_Dir = System.getProperty("user.dir");

	// OXXO GAS BD QA
	public static final String DB_OXXOGAS_TURBO_NOM = "jdbc:sqlserver://10.52.34.23;databaseName=oxxogas_turbo_nom;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
	public static final String USER_OXXOGAS_TURBO_NOM = "wmuserqa";
	public static final String PASS_OXXOGAS_TURBO_NOM = "Comercio*1";

	// Portal de Proveedores
	public static final String getPDPQAUsr = PasswordUtil.decryptPassword("75041A93877D1ED4239B5A83DAF193E3");
	public static final String getPDPQAPwd = PasswordUtil.decryptPassword("7604358789E636338211D1C0956CFCF8");
	public static final String getPDPQAServer = PasswordUtil.decryptPassword(
			"AB0FFDD3424B5D5428476EA9A4CD244718F9B74E2CE6CC5C2F74193ACB2C6F7CC0F97919EBA3D50D06EB483CA8137FA9");

	// Usuario EBS
	// EBS MV2_RST
	public static final String User_EBS_M = "MARIANA_GTIM";
	public static final String Password_EBS_M = PasswordUtil.decryptPassword("0831AA2FCE02AF50D5B2FD8A77BD1E3B");

	// DBS_BI_LT FCWMREQA
	public static final String DB_USER_FCWMREQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMREQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMREQA = "172.26.221.197:1521/FCWMREQA.FEMCOM.NET";

	public static final String DB_USER_TPEREP_DEV = "tperep";
	public static final String DB_PASSWORD_TPEREP_DEV = "tperep";
	public static final String DB_HOST_TPEREP_DEV = "10.80.8.204:1521/fctpedes.femcom.net";

	/*
	 * BD FCWM6QA Se usa para las aplicaciones: SIMOTI Colombia/Mexico
	 */
	public static final String DB_USER_FCWM6QA_QAVIEW = "QAVIEW";
	public static final String DB_PASSWORD_FCWM6QA_QAVIEW = PasswordUtil
			.decryptPassword("A5544A354006EE384994CACA76D6289B");

	public static final String DB_HOST_FCWM6QA_QAVIEW = "oxzvmwmiracq01.femcom.net:1521/FCWM6QA.FEMCOM.NET";

	public static final String DB_HOST_FCWM6QA_QAVIEW2 = "10.184.48.217:1535/fcwm6QA.femcom.net";

	// FCWMLQA con host diferente (2)
	public static final String DB_USER_FCWMLQA_WMLOG2 = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA_WMLOG2 = PasswordUtil
			.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQA_WMLOG2 = "oxzvmwmiracq01.femcom.net:1521/FCWMLQA.FEMCOM.NET";

	// WM
	public static final String DB_USER_WM = "QAVIEW";
	public static final String DB_PASSWORD_WM = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");

	public static final String DB_HOST_WM = "oxzvmwmiracq01.femcom.net:1521/FCWM6QA.FEMCOM.NET";
	// "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";

	public static final String DB_HOST_WM2 = "10.184.48.216:1535/FCWM6QA.FEMCOM.NET";

	// BD_IMA
	public static final String DB_HOST_IMA = "fcobidb1q00.femcom.net:1535/FCODSQA.FEMCOM.NET";
	public static final String DB_USER_IMA = "BIETL";
	public static final String DB_PASSWORD_IMA = PasswordUtil.decryptPassword("8D136AB701B3D22EE706D74A2408A9E4");

	// BD FCODSQA
	public static final String DB_USER_FCODSQA = "QAVIEW";
	public static final String DB_PASSWORD_FCODSQA = "Passw0rd#20";
	public static final String DB_HOST_FCODSQA = "fcobidb1q00.femcom.net:1535/FCODSQA.FEMCOM.NET";

	// Service Now QA
	public static final String USER_ServiceNowQA = PasswordUtil.decryptPassword("6A65CE462D5E2569B0736E2820E26E3C8BF29F6BF1626F98CAB64FBB853E72AE");
	public static final String PASSWORD_ServiceNowQA = PasswordUtil.decryptPassword("4402B6A809D21D422C0CD336FE5E19CC7AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
	public static final String HOST_ServiceNowQA = "https://snoxxoqa.service-now.com/login.do";

 		//EBS CFE
 	    public static final String DB_USER_CFE_EBS = "appsview";
 	    public static final String DB_PASSWORD_CFE_EBS = "appsview";
 	    public static final String DB_HOST_CFE_EBS = "jdbc:oracle:thin:@//172.26.42.170:1532/AVEBQA";

 	 //FCWM6QA CFE
 		public static final String DB_USER_FCWMQA_CFE = "QAVIEW";
 		public static final String DB_PASSWORD_FCWMQA_CFE = "Passw0rd#20";
 		public static final String DB_HOST_FCWMQA_CFE = "jdbc:oracle:thin:@//oxzvmwmiracq02.femcom.net:1521/FCWM6QA.FEMCOM.NET";

 		//EBS CFE En Linea portal
 	    public static final String Url_EBS_CFE="http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?requestUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FOA.jsp%3Fpage%3D%2Foracle%2Fapps%2Ffnd%2Fframework%2Fnavigate%2Fwebui%2FNewHomePG%26homePage%3DY%26OAPB%3DFWK_HOMEPAGE_BRAND%26transactionid%3D570987066%26oapc%3D2%26oas%3D4ppKB3RdwGUZ9ZtkXsf3GA..&cancelUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FAppsLogin&langCode=US#dummyAnchor";
 	    public static final String User_EBS_CFE_Web="CFEQA";
 	    public static final String Password_EBS_CFE_Web=PasswordUtil.decryptPassword("7D92C8D6A22ADB61D2C9CCD3E361FF66");
 	    
}
