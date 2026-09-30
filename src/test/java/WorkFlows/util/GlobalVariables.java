package WorkFlows.util;

import java.io.File;

import utils.password.PasswordUtil;

public class GlobalVariables {

	public static final String BASE_PATH = System.getProperty("user.dir");

	// sembrado nexxo bloque 2
	public static final String FILE_PATH = BASE_PATH + File.separator
			+ "src\\test\\java\\utils\\Sembrado\\archivos\\Bloque2" + File.separator;

	public static final String FILES_WF= BASE_PATH+File.separator+"Config"+File.separator+"Input"
			+File.separator+"DataProviders"+File.separator+"WorkFlows"+File.separator;
	
	/**
	 * CREDENCIALES PARA SEMBRADO ADMIN
	 */
	public static final String urlSembrado = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String userAdminSembrado = "6006068";
	public static final String passSembrado = "Workflows19";

	/**
	 * CREDENCIALES MEGAWORKFLOWS
	 */
	public static final String urlPega = "http://172.26.142.209:7011/prweb/PRWebLDAP1/";
	public static final String userPega = "6006020";
	public static final String passPega = "";

	public static final String urlUltimus = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Main.aspx";
	public static final String url2Ultimus = "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx";
	public static final String urlUltimusNew = "http://172.26.160.121/UltWeb/UltThinClient/Login.aspx";
	public static final String userUltimus = "3119304";
	public static final String passUltimus = "";

	public static final String userMegaWF = "6006015";
	public static final String passMegaWF = "Comercio.38";

	/**
	 * CREDENCIALES DE USUARIOS ADMIN
	 */
	public static final String user = "6006027";
	public static final String ps = "72525BDEF703AD656B192FF6F30D5E9D";

	// Usuario ultimus Categoria con perfil de administrador
	public static final String ULTIMUS_USER = "6006068";
	public static final String ULTIMUS_USER_CEDIS = "1779064";
	public static final String ULTIMUS_USER_ADMIN = "6006063";
	public static final String ULTIMUS_USER_CATEGORIA = "6004860";

	// Usuario ultimus para acceso a Panp3
	public static final String ULTIMUS_USER_SELECT_PROVEEDOR = "6004860";
	public static final String ULTIMUS_PANP3_USER_MERCANCIAS = "6002938";
	public static final String ULTIMUS_PANP3_USER_ADMIN = "6005141";

	// Usuario para acceso a pega y el portal de aplicaciones
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
	public static final String DB_PASS_PLANOGRAMAS_FCPVEQA = PasswordUtil
			.decryptPassword("A5544A354006EE384994CACA76D6289B");
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
	public static final String DB_USER_RMS16_ = "nreader";
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

	// DB Cuanti
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

	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";
	// -
	public static final String CtrlM_User = "amcejgom";
	public static final String CtrlM_Pss = PasswordUtil.decryptPassword("90B44CAF1C27FFF60EFD787D57537BEB");
	// -
	public static final String CtrlM_User_EsmeG = "eggartor";
	public static final String CtrlM_Pss_EsmeG= PasswordUtil
			.decryptPassword("F70AF2BC27586B3F2A0928AC50EE8F9F");
	//-
	public static final String CtrlM_User_AndreaC = "48025";
	public static final String CtrlM_Pss_AndreaC= PasswordUtil
			.decryptPassword("9BBD27E12F48C0306EC05B016EDB3FF9");
	//-
	public static final String CtrlM_User_Neo10 = "6006068";
	public static final String CtrlM_Pss_Neo10 = PasswordUtil
			.decryptPassword("CCB7F64B7A4C8B841C43272E7D98D46A");
	

	/*
	 * CREDENCIALES DE CONTROL-M SIPRE OXXO
	 */
	public static final String CtrlMSipreOxxo_User = "mishermon";
	public static final String CtrlMSipreOxxo_Pss = "OxxoEne2023!";
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
	public static final String passPortalSipreOXXO = "Comercio.35";
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
	public static final String passPortalVariedOXXO = "Comercio.35";

	/**
	 * CREDENCIALES REPLENISHMENT OXXO
	 */
	public static final String urlRepleOXXO = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String userRedRepleOXXO = "6006060";
	public static final String passRedRepleOXXO = "Comercio*4";

	/**
	 * CREDENCIALES DEVOLUCIONES
	 */
	public static final String urlPEGA = "http://10.185.176.11:7011/prweb/PRWebLDAP1";
	public static final String userAdminDevoluciones = "6006026";
	public static final String userFFDevoluciones = "3119304";
	public static final String userCategoryDevoluciones = "3119304";

	// BD: FCPVESIT
	public static final String DB_USER_FCPVESIT = "ntester";
	public static final String DB_PASSWORD_FCPVESIT = PasswordUtil.decryptPassword("E6B84151DA1BD1CE20F2C8F942E9F15E");
	public static final String DB_HOST_FCPVESIT = "10.184.17.41:1535/FCPVESIT";

	// BD: FCPFACQA -- LOCAL ANGEL
	public static final String DB_USER_FCPFACQA = "6006026";
	public static final String DB_PASSWORD_FCPFACQA = PasswordUtil.decryptPassword("E0E42B758BACCF14EFF3A57D5A06B4AF");
	public static final String DB_HOST_FCPFACQA = "10.80.3.57:1522/FCPFACQA.FEMCOM.NET";

	// BD: FCPVEQA Nueva personalizada
	public static final String DB_USER_FCPVEQA2 = "6006026";
	public static final String DB_PASSWORD_FCPVEQA2 = PasswordUtil
			.decryptPassword("1CB2E9E506E39B67AE9E6CB991FDC2867AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
	public static final String DB_HOST_FCPVEQA2 = "172.26.174.134:1535/FCPVEQA.FEMCOM.NET";

	// BD: FCDASUAT
	public static final String DB_USER_FCDASUAT = "nreader";
	public static final String DB_PASSWORD_FCDASUAT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_FCDASUAT = "10.184.37.67:1521/FCDASUAT";

	 //Tabla RMS100 MEXICO SID: FCRMSQA
	   
    public static final String DB_USER_RMS_MEX="QAVIEW";
    public static final String DB_PASSWORD_RMS_MEX =PasswordUtil.decryptPassword("0A0733E127FD929E7DD488492E2C92AE");
    public static final String DB_HOST_RMS_MEX = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";
	
 // BD: FCMOMUAT
 	public static final String DB_USER_FCMOMUAT = "NREADER";
 	public static final String DB_PASSWORD_FCMOMUAT = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
 	public static final String DB_HOST_FCMOMUAT = "10.184.37.47:1521/FCMOMUAT";
 	
    

}
