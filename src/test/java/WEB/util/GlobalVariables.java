package WEB.util;

import java.io.File;
import utils.password.PasswordUtil;

public class GlobalVariables { 
	
	public static final String BASE_PATH = System.getProperty("user.dir");
	public static final String REQBODY = BASE_PATH+File.separator+"Config"+File.separator+"Input"
	+File.separator+"DataProviders"+File.separator+"WEB"+File.separator+"repositorioCentral"+File.separator
	+File.separator+"JSONs"+File.separator;  
	public static final String FILES_WEB = BASE_PATH+File.separator+"src"+File.separator+"test"
			+File.separator+"java"+File.separator+"WEB"+File.separator;
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
	public static final String URL_PORTAL_APPS_PORTALES_PUBLICOS_QA = "https://portalespublicosqa.oxxo.com/AccessControl/pages/login_form.jsf";
	public static final String URL_PORTAL_APPS = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	public static final String URL_PORTAL_APPSF = "https://portalespublicosqa.oxxo.com/facturacionElectronica-web/views/layout/inicio.do"; //Facturacion
	public static final String URL_PORTAL_APPSF1 = "https://10.250.129.17:8443/facturacionElectronica-web/views/layout/misRFC.do"; //FacturacionQA
	public static final String URL_PORTAL_APPSG = "https://portalespublicosqa.oxxo.com/AccessControl/pages/login_form.jsf"; //Gestion de proveedores Funciona bien
	public static final String URL_PORTAL_APPS_SAR = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";	
	public static final String URL_PORTAL_APPS_SIMOTI_CH_PR_COL = "https://10.184.40.135:8443/AccessControl/pages/login_form.jsf";
		
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Oxxo
	 * SARv3 - Perfil SUPERUSUARIO/ADMIN
	 * */
	public static final String USER_PORTAL_APPS_CMOXXOHEXAWARE30 = "CMOXXOHEXAWARE30";
	public static final String PASSWORD_PORTAL_APPS_CMOXXOHEXAWARE30 = PasswordUtil.decryptPassword("B8C522B8E94F777D02D08F53881C3149");
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Marca
	 * SARv3 - Perfil SUPERUSUARIO/ADMIN
	 * SIMOTI Chile - Administrador
	 * SIMOTI Colombia
	 * */
	public static final String USER_PORTAL_APPS_6006020 = "6006020";
	public static final String PASSWORD_PORTAL_APPS_6006020 = PasswordUtil.decryptPassword("B02C1C59B65E103E0767FC452DBDEA1D");

	/* Aplicaci�n - Perfiles
	 * SARv3 - PROTECCION_PATRIMONIAL
	 * */
	public static final String USER_PORTAL_APPS_6006027 = "6006027";
	public static final String PASSWORD_PORTAL_APPS_6006027 = PasswordUtil.decryptPassword("5C101338C368677758B73AE83F7F2EE5");
	
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Administardor Bara
	 * SARv3 - CONTROL_TIENDA
	 * */
	public static final String USER_PORTAL_APPS_6006028 = "6006028";
	public static final String PASSWORD_PORTAL_APPS_6006028 = PasswordUtil.decryptPassword("EC82AB250FD2D892F5023947A58F9275");
	
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Plaza Bara
	 * SARv3 - DESPACHO_DENUNCIA
	 * */
	public static final String USER_PORTAL_APPS_6006030 = "6006030";
	public static final String PASSWORD_PORTAL_APPS_6006030 = PasswordUtil.decryptPassword("A0CCA89B9E2D4580441E4BD34B65CBDB");
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Plaza Oxxo
	 * */
	public static final String USER_PORTAL_APPS_6006015 = "6006015";
	public static final String PASSWORD_PORTAL_APPS_6006015 = PasswordUtil.decryptPassword("7B38076592E53BE313F9DF331EB554AE");
	
	/* Aplicaci�n - Perfiles
	 * Inventario Espacios Publicitarios - Perfil Mensajeria
	 * */
	public static final String USER_PORTAL_APPS_6006024 = "6006024";
	public static final String PASSWORD_PORTAL_APPS_6006024 = PasswordUtil.decryptPassword("");

	
	
	/* Aplicaci�n - Perfiles
	 * Portal Remesas - Perfil Administrador
	 * Portal Cobro Servicios Perfiles -
	 * */
	public static final String USER_PORTAL_APPS_6005105 = "6005105";
	public static final String PASSWORD_PORTAL_APPS_6005105 = PasswordUtil.decryptPassword("87368FF33EB7D26A9EAEB347C1B9FDED");
	
	/* Gestion de proveedores
	 * */
	public static final String USER_PORTAL_APPS_PDP_ADMINISTRADOR = "PDP_ADMINISTRADOR";
	public static final String PASSWORD_PORTAL_APPS_PDP_ADMINISTRADOR = PasswordUtil.decryptPassword("2AA4375CBEF4B98601D1FD6CFC25B1E2");
	
	/* Gestion de proveedores -> Avisos y Politicas, 
	 * */
	public static final String USER_PORTAL_APPS_BTM060817960 = "BTM060817960";
	public static final String PASSWORD_PORTAL_APPS_BTM060817960 = PasswordUtil.decryptPassword("48CBAC73AC72267C001180058B5DA0B9");

	
	
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
  
  	//----------------------------------------- Portal Web Ruteo Dinamico  ---------------------------------------
	
	public static final String DB_USER_RDM_RD = "RDM100";
    public static final String DB_PASSWORD_RDM_RDQA = PasswordUtil.decryptPassword("0E4265133E540C34BBB3074F726B7C47");
    public static final String DB_HOST_RDM_RDQA = "10.180.56.86:1535:FCRD01CU";
	public static final String URL_PORTAL_RD_DEV = "https://fcportaldes.femcom.net:8443/AuthFemcoProx";
	public static final String URL_PORTAL_RD_QA = "https://fcportalqa.femcom.net:8443/AuthFemcoProx";
	public static final String usuarioPortalWebRD_DEV = "RUTDINUSUARIO02";
	public static final String contrasenaPortalWebRD_DEV = PasswordUtil.decryptPassword("88888156187035A49C8BE73EE2C9504E");
	public static final String usuarioPortalWebRD_QA = "portal_rutdin_qa";
	public static final String contrasenaPortalWebRD_QA = PasswordUtil.decryptPassword("CC3A4E70082546BB91F8B091547F634F");
	
	
	//**************************************** BASES DE DATOS ***********************************
	
	
	/*BD FCIASQA
	 *Se usa para las aplicaciones: Inventario de Espacios Publicitarios, CNT
	 */
	public static final String DB_USER_FCIASQAS_PORTAL = "PORTAL_VIEW";
	public static final String DB_PASSWORD_FCIASQAS_PORTAL = PasswordUtil.decryptPassword("A3CA5C52DA0CC8F5DD999344B6D259EB");
	public static final String DB_HOST_FCIASQAS_PORTAL = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
	
	/*BD FCIASQA
	 *QAVIEW
	 */
	public static final String DB_USER_FCIASQA_QAVIEW = "QAVIEW";
	public static final String DB_PASSWORD_FCIASQA_QAVIEW = PasswordUtil.decryptPassword("723284744B777B52B684E41382922183");
	public static final String DB_HOST_FCIASQA_QAVIEW = "10.184.32.194:1535/FCIASQA.FEMCOM.NET";
		

	/*BD FCDW2QA
	 *Se usa para las aplicaciones: CNT
	 */
	public static final String DB_USER_FCDW2QA_PORTAL = "hexaware30";
	public static final String DB_PASSWORD_FCDW2QA_PORTAL =PasswordUtil.decryptPassword("4AA4F6FC949AD630F64FEBF5E9D19151");
	public static final String DB_HOST_FCDW2QA_PORTAL = "10.80.12.64:1535/FCDW2QA";

	/*BD FCDW2QA
	 *Se usa para las aplicaciones: CNT
	 * Añadido por Mario De Leon para Update Nomina
	 */
	public static final String DB_USER_FCDW2QA_USERAUTO = "USERAUTO";
	public static final String DB_PASSWORD_FCDW2QA_USERAUTO =PasswordUtil.decryptPassword("9E2AF8C906F9CDAD2FC3EA59D2245D15");
	public static final String DB_HOST_FCDW2QA_USERAUTO = "fcnewpd1.femcom.net:1535/FCDW2QA";

	/*BD FCWMLQA
	 *Se usa para revisar status de la ejecucion de un job
	 * Añadido por Mario De Leon para Update Nomina
	 */
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQA = "oxzvmwmiracq01.femcom.net:1521/FCWMLQA.FEMCOM.NET";

	/*BD EBS AVANTE
	 *Se usa para las aplicaciones: EBS Mexico
	 * Añadido por Mario De Leon para Update Nomina
	 */
	public static final String DB_USER_AVEBQA = "appsview";
	public static final String DB_PASSWORD_AVEBQA = PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_AVEBQA = "oxfdbfinq00.femcom.net:1535/AVEBQA";

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
	public static final String DB_HOST_FCWM6QA_QAVIEW = "10.184.48.217:1535/fcwm6QA.femcom.net";
	
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
	
	/*BD FCWMREQA
	 *Se usa para las aplicaciones: SIMOTI Colombia
	 */
	public static final String DB_USER_Cometa ="admpocqa";
	public static final String DB_PASSWORD_Cometa =  PasswordUtil.decryptPassword("0FB4AE2E7F8604DEBA61F75602DD5F0595643E8F958EEABF7AFE2381E7961D0D7AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
	public static final String DB_URL_Cometa = "jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa";
	
	//----------------------------------------- APEX RuteoDinamico  ---------------------------------------
	//NOTA: Utileria y scripts descontinuados debido a creacion de portal web proyecto Ruteo Dinamico MVP2
		public static final String URL_APEX_CARGA_RESTR_TIENDAS = "https://fcrepvc01.femcom.net:7056/ords/f?p=352:LOGIN_DESKTOP:16459864627123:::::";
		public static final String usuarioRDApex = "temp";
		public static final String contrasenaRDApex = PasswordUtil.decryptPassword("ED1A5769622CF5571B7D16475F8E6371");
	 
		// BD: FCPFACQA --  VIRTUAL 
		public static final String DB_USER_FCPFACQA_VIRTUAL = "AUTOMATIZACION";
		public static final String DB_PASSW_FCPFACQA_VIRTUAL = PasswordUtil.decryptPassword("ABAFFB9604E24384AC42ECFE818B4C28DCC8BC4ADA57529688614ECB962C5617");
		public static final String DB_HOST_FCPFACQA_VIRTUAL = "10.80.3.57:1522/FCPFACQA.FEMCOM.NET";
		
		public static final String DB_USER_FCPFACQA = "6006030";
		public static final String DB_PAST_FCPFACQA = PasswordUtil.decryptPassword("EE0A7804DFF96479FADEEE6567A3C5F5");
		public static final String DB_HOST_FCPFACQA = "10.80.3.57:1522/FCPFACQA.FEMCOM.NET";
		
		public static final String DB_USER_FCPFACQA_MV = "AUTOMATIZACION";
		public static final String DB_PAST_FCPFACQA_MV = PasswordUtil.decryptPassword("ABAFFB9604E24384AC42ECFE818B4C28DCC8BC4ADA57529688614ECB962C5617");	

		//TPUSER
	    public static final String DB_USER_FCTPE = "QAVIEW";
	    public static final String DB_PASSWORD_FCTPE =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	    public static final String DB_PASSWORD_FCTPE2 =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	    public static final String DB_HOST_FCTPE = "10.184.80.120:1521/FCTPEQA.FEMCOM.NET";

	//ARCGIS FCGISQN 	
	public static final String DB_USER_FCGISQN = "\"UsuVistaI24073\"";
	public static final String DB_PASSWORD_FCGISQN = PasswordUtil.decryptPassword("E16102E56AC9425F487F94510F3AF1AF");
	public static final String DB_HOST_FCGISQN = "OXVGISDBQ01.femcom.net:1521/FCGISQN";
	

	public static final String URL_PORTAL_APPS_LABOR =  "https://oxxo-cloud-portal-incentivos-qa.apps.oxzaromdp01.southcentralus.aroapp.io/portalIncentivos";
	public static final String URL_PORTAL_APPS_LABOR2 = "https://oxxo-cloud-portal-incentivos-qa.apps.oxzaromdp01.southcentralus.aroapp.io/portalIncentivos/";
	
	/* Aplicacion - Perfiles
	 * Labor Managment - Perfil 1
	 * */
	public static final String USER_PORTAL_APPS_USU1_ACCESSCONTROLQA = "USU1_ACCESSCONTROLQA";
	public static final String PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA = PasswordUtil.decryptPassword("8E09F0DF4FDEA2038D6B7D8FB19FF79D");
	
	
	/* Aplicacion - Perfiles
	 * Labor Managment - Perfil 2
	 * */
	public static final String USER_PORTAL_APPS_USU2_ACCESSCONTROLQA = "USU2_ACCESSCONTROLQA";
	public static final String PASSWORD_PORTAL_APPS_USU2_ACCESSCONTROLQA = PasswordUtil.decryptPassword("5BDC61F7E639663FE8BB491FC3CF963E");
	
	/*BD XXMAI
	 *Se usa para las aplicaciones: Labor Managment
	 */
	public static final String DB_HOST_BDXXMAI_QAVIEW = "jdbc:postgresql://172.26.221.203:5432/xxmai";
	public static final String DB_USER__BDXXMAI_QAVIEW = "oxadmincloudlmsmaiqa";
	public static final String DB_PASSWORD_BDXXMAI_QAVIEW = PasswordUtil.decryptPassword("673AC0FBE33C4F2BAC40BB38E62D675F7AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
	
	/*BD FCDBWMQ2
	 * 
	 *  
	 */
	public static final String DB_USER_FCDBWMQ2 = "QAVIEW";
	public static final String DB_PASSWORD_FCDBWMQ2 =PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCDBWMQ2 = "fcdbwmq2.femcom.net:1521/FCTDCQA.FEMCOM.NET";

	/* Portal Cometa MVP4 -> Contraseñas de perfiles, 
	 * */
	public static final String PASSWORD_PORTAL_COMETA_MPV4_1111111 = PasswordUtil.decryptPassword("E47EBCD5602E80AF76E01F771D014844");
	public static final String PASSWORD_PORTAL_COMETA_MPV4_2222222 = PasswordUtil.decryptPassword("ABB846A0CEA8933619DCCABEA05E06FC");
	public static final String PASSWORD_PORTAL_COMETA_MPV4_3333333 = PasswordUtil.decryptPassword("73A8DA254880DE877B8F627320BC3094");
	public static final String PASSWORD_PORTAL_COMETA_MPV4_4444444 = PasswordUtil.decryptPassword("F9834E045E7C6EF76C59FC835C2B8C59");
	public static final String PASSWORD_PORTAL_COMETA_MPV4_5555555 = PasswordUtil.decryptPassword("08178D1D7D1088050F84233730FBC26B");
	public static final String PASSWORD_PORTAL_COMETA_MPV4_6666666 = PasswordUtil.decryptPassword("4F0019F2ED118B960ECCBF9BDD378C88");

	/* Aplicacion - SEGURIDAD SADEL
	 * */
	public static final String URL_PORTAL_APPS_SADEL = "https://oxzvmsdlqlap01.femcom.net:8443/AccessControl/pages/login_form.jsf";
}
