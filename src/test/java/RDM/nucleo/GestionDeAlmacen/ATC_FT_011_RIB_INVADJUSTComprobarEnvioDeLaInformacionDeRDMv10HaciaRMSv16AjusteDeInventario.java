package RDM.nucleo.GestionDeAlmacen;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import RDM.util.Nucleo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_RIB_INVADJUSTComprobarEnvioDeLaInformacionDeRDMv10HaciaRMSv16AjusteDeInventario extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_RIB_INVADJUSTComprobarEnvioDeLaInformacionDeRDMv10HaciaRMSv16AjusteDeInventario_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String consultaPaso2 = "SELECT * FROM WMUSER.WM_RDM_CONECTION "
				+ "WHERE RETEK_CR = '9999999002'";
		String consultaPaso4 = "SELECT * FROM XXFC_CEDIS_ADJ_REASON_CODES_V "
				+ "WHERE USER_REASON_CODE IN ('101' , '102')";
		String consultaPaso5 = "SELECT * FROM LOCATION WHERE LOC_CLASS = 'ALMACENAJE CAJAS' AND ZONE = '3A' AND LOCATION_STATUS = 'OUT-SERVICE' AND LOCATION_ID = 'lOCATION_ID'";
		String consultaPaso7 = "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFING "
				+ "WHERE ID_CEDIS = '9999999002' "
				+ "AND V16 = 'Y'";
		String consultaPaso8 = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG "
				+ "WHERE INTERFASE = 'RR06' AND ENTIDAD = 'INVADJUST'";
		String consultaPaso9 = "SELECT * FROM WMUSER.RIB_FR_CONFIG "
				+ "WHERE FAMILY = 'INVADJUST'";
		String consultaPaso15 = "SELECT * FROM RDM100.REASON_CODE WHERE USER_REASON_CODE IN ('102')";
		String consultaPaso16 = "Select * from RDM100.Inv_Adjustment_to_upload";
		String consultaPaso17 = "SELECT * FROM RDM100.INV_ADJUSTMENT_TO_UPLOAD_HIS WHERE CREATE_DATE >= '14/02/202'";
		String consultaPaso21 = "SELECT * FROM WMLOG.RIB_LOG_ERROR_INT "
				+ "WHERE INTERFACE= 'RR06' "
				+ "AND CREATION_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY CREATION_DATE DESC";
		String consultaPaso22 = "SELECT * FROM WMLOG.RIB_LOG_ERROR "
				+ "WHERE ERROR_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY ERROR_DATE DESC";
		String consultaPaso23 = "SELECT * FROM WMUSER.RIB_INVADJUST_MSG_REP "
				+ "WHERE CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC";
		String consultaPaso24 = "SELECT * FROM WMUSER.RIB_INVADJUST_CONTROL "
				+ "WHERE WM_CODE=101 AND CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC";
		String consultaPaso25 = "SELECT * FROM WMUSER.RIB_FR_RETRY_MGR"
				+ "WHERE CRTL_ID IN ('CTRL'S ID's DE LA TABLA DE CONTROL') ORDER BY CREATION_DATE DESC";
		String consultaPaso27 = "SELECT * FROM RMS.INV_ADJ";
		String locationId = data.get("locationId");
		// CONTROL-M
		String controlMUser = GlobalVariables.usuarioRicardoControlM;
		String controlMPass = GlobalVariables.contrasenaRicardoControlM;
		String controlMServer = GlobalVariables.serverControlM;

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Nucleo n = new Nucleo(u, testCase, data.get("server"));
		SQLUtil FCWMFSIT = new SQLUtil(GlobalVariables.DB_HOST_FCWMFSIT, GlobalVariables.DB_USER_FCWMFSIT,
				GlobalVariables.DB_PASSWORD_FCWMFSIT);
		SQLUtil FCMOMPRF_RMS16 = new SQLUtil(GlobalVariables.DB_HOST_FCMOMPRF_RMS16, GlobalVariables.DB_USER_FCMOMPRF_RMS16,
				GlobalVariables.DB_PASSWORD_FCMOMPRF_RMS16);
		SQLUtil FCWMESIT = new SQLUtil(GlobalVariables.DB_HOST_FCWMESIT, GlobalVariables.DB_USER_FCWMESIT,
				GlobalVariables.DB_PASSWORD_FCWMESIT);
		SQLUtil FCRDMSIT = new SQLUtil(GlobalVariables.DB_HOST_FCRDMSIT, GlobalVariables.DB_USER_FCRDMSIT,
				GlobalVariables.DB_PASSWORD_FCRDMSIT);
		SQLUtil RDM = new SQLUtil(GlobalVariables.DB_HOST_CONECTARDM100, GlobalVariables.DB_USER_CONECTARDM100,
				GlobalVariables.DB_PASSWORD_CONECTARDM100);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Realizar conexion a la BD FCWMESIT de backoffice");
		System.out.println("PASO 1");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCWMESIT + " usuario: "
				+ GlobalVariables.DB_USER_FCWMESIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCWMESIT.getConn());
		// PASO 2
		addStep("Realizar la consulta de la conexcion al CEDIS a ocupar "
				+ "SELECT * FROM WMUSER.WM_RDM_CONECTION "
				+ "WHERE RETEK_CR = '9999999002'");
		System.out.println("PASO 2");
		System.out.println(consultaPaso2);
		SQLResult validat2 = FCWMFSIT.executeQuery(consultaPaso2);
		boolean valida2 = validat2.isEmpty();
		if (!valida2) {
			testCase.addQueryEvidenceCurrentStep(validat2);
		}
		assertFalse("No se encuentra la tabla ", valida2);
		System.out.println(valida2);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 3
		addStep("Realizar la conexion a la BD FCRDMSIT_MTY");
		System.out.println("PASO 3");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCRDMSIT + " usuario: "
				+ GlobalVariables.DB_USER_FCRDMSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCRDMSIT.getConn());
		// PASO 4
		addStep("Consultar que se tengn creados los 'USER_REASON_CODE' 101 Y 102 "
				+ "SELECT * FROM XXFC_CEDIS_ADJ_REASON_CODES_V "
				+ "WHERE USER_REASON_CODE IN ('101' , '102')");
		System.out.println("PASO 4");
		System.out.println(consultaPaso4);
		SQLResult validat4 = FCRDMSIT.executeQuery(consultaPaso4);
		boolean valida4 = validat4.isEmpty();
		if (!valida4) {
			testCase.addQueryEvidenceCurrentStep(validat4);
		}
		assertFalse("No se encuentra la tabla ", valida4);
		System.out.println(valida4);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 5
		addStep("Consultar las locaciones disponibles del para un ajuste de inventario"
				+ "SELECT * FROM LOCATION WHERE LOC_CLASS = 'ALMACENAJE CAJAS' AND ZONE = '3A' AND LOCATION_STATUS = 'OUT-SERVICE' AND LOCATION_ID = '<lOCATION_ID>'");
		System.out.println("PASO 5");
		System.out.println(consultaPaso5);
		SQLResult validat5 = FCRDMSIT.executeQuery(consultaPaso5);
		boolean valida5 = validat5.isEmpty();
		if (!valida5) {
			testCase.addQueryEvidenceCurrentStep(validat5);
		}
		assertFalse("No se encuentra la tabla ", valida5);
		System.out.println(valida5);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 6
		addStep("Realizar conexion a la BD FCWMFSIT de WM");
		System.out.println("PASO 6");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCWMFSIT + " usuario: "
				+ GlobalVariables.DB_USER_FCWMFSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCWMFSIT.getConn());
		// PASO 7
		addStep("Realizar la consulta del cedis a ocupar"
				+ "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFING "
				+ "WHERE ID_CEDIS = '9999999002' "
				+ "AND V16 = 'Y'");
		System.out.println("PASO 7");
		System.out.println(consultaPaso7);
		SQLResult validat7 = FCWMFSIT.executeQuery(consultaPaso7);
		boolean valida7 = validat7.isEmpty();
		if (!valida7) {
			testCase.addQueryEvidenceCurrentStep(validat7);
		}
		assertFalse("No se encuentra la tabla ", valida7);
		System.out.println(valida7);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 8
		addStep("Validar la configuracion de la ruta URL HTTP del Framework en la tabla en la tabla Wm_interfase_config en la base de datos  FCWMFSIT - Framework en WEB METHODS :"
				+ "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG "
				+ "WHERE INTERFASE = 'RR06' AND ENTIDAD = 'INVADJUST'");
		System.out.println("PASO 8");
		System.out.println(consultaPaso8);
		SQLResult validat8 = FCWMFSIT.executeQuery(consultaPaso8);
		boolean valida8 = validat8.isEmpty();
		if (!valida8) {
			testCase.addQueryEvidenceCurrentStep(validat8);
		}
		assertFalse("No se encuentra la tabla ", valida8);
		System.out.println(valida8);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		// PASO 9
		addStep("Validar que exista la configuracion para la interface RIB_FR con los valores de la familia RIB_INVADJUST en la tabla RIB_FR_CONFIG en el esquema WMUSER en la BD  FCWMFSIT - Framework  mediante el siguiente Query:"
				+ "SELECT * FROM WMUSER.RIB_FR_CONFIG "
				+ "WHERE FAMILY = 'INVADJUST'");
		System.out.println("PASO 9");
		System.out.println(consultaPaso9);
		SQLResult validat9 = FCWMFSIT.executeQuery(consultaPaso9);
		boolean valida9 = validat9.isEmpty();
		if (!valida9) {
			testCase.addQueryEvidenceCurrentStep(validat9);
		}
		assertFalse("No se encuentra la tabla ", valida9);
		System.out.println(valida9);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 10
		addStep(" - Entrar al aplicativo de RDMv10"
				+ "* Ingresar a la siguiente url en el navegador Internet Explorer: http://10.180.56.86:8011/rdm001.html"
				+ "*Capturar informacion en los siguientes campos:"
				+ "* Username <Usuario valido>"
				+ "* Password  <Password valido>"
				+ "* Facility <FR>"
				+ "Presionar [ Log in ]");
		System.out.print("PASO 10");
		System.out.println(dbRdm.getHost());
		n.logIn();
		// PASO 11
		addStep("Ingresar a la sigueinte ruta"
				+ "Support Functions > DC Setup > Location Setup > Forward Picking Location Editor");
		System.out.print("PASO 11");
		n.clickMenuSupportForwardPickingLocationEditor();
		// PASO 12
		addStep("Ingresar el LOCATION_ID (paso 5), Seleccionar el boton de la flecha");
		System.out.print("PASO 12");
		n.ingresarLocationId(locationId);
		n.clickCheck();
		// PASO 13
		addStep("Dar doble clic sobre el ID del articulo mostrado");
		System.out.print("PASO 13");
		// PASO 14
		addStep("Modificar el campo"
				+ "UNIT QTY > Poner cualquier cifra"
				+ "Seleccionar boton 'save'"
				+ "Seleccionar opcion '102 Inventario Fisico'"
				+ "Seleccionar boton OK");
		System.out.print("PASO 14");
		// PASO 15
		addStep("Consultar en la tabla RDM100.Reason_code de RDM la creacion del nuevo Ajuste de Inventario en RDM v10."
				+ "SELECT * FROM RDM100.REASON_CODE WHERE USER_REASON_CODE IN ('102')");
		System.out.print("PASO 15");
		System.out.println(consultaPaso15);
		SQLResult validat15 = RDM.executeQuery(consultaPaso15);
		boolean valida15 = validat15.isEmpty();
		if (!valida15) {
			testCase.addQueryEvidenceCurrentStep(validat15);
		}
		assertFalse("No se encuentra la tabla ", valida15);
		System.out.println(valida15);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 16
		addStep("Consultar en la tabla RDM100.Reason_code de RDM la creacion del nuevo Ajuste de Inventario en RDM v10."
				+ "Select * from RDM100.Inv_Adjustment_to_upload");
		System.out.print("PASO 16");
		System.out.println(consultaPaso16);
		SQLResult validat16 = RDM.executeQuery(consultaPaso16);
		boolean valida16 = validat16.isEmpty();
		if (!valida16) {
			testCase.addQueryEvidenceCurrentStep(validat16);
		}
		assertFalse("No se encuentra la tabla ", valida16);
		System.out.println(valida16);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 17
		addStep("-Consultar en la tabla RDM100.INV_ADJUSTMENT_TO_UPLOAD_HIS de RDM la creacion del nuevo Ajuste de Inventario"
				+ "SELECT * FROM RDM100.INV_ADJUSTMENT_TO_UPLOAD_HIS WHERE CREATE_DATE >= '14/02/202'");
		System.out.print("PASO 17");
		System.out.println(consultaPaso17);
		SQLResult validat17 = RDM.executeQuery(consultaPaso17);
		boolean valida17 = validat17.isEmpty();
		if (!valida17) {
			testCase.addQueryEvidenceCurrentStep(validat17);
		}
		assertFalse("No se encuentra la tabla ", valida17);
		System.out.println(valida17);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 18
		addStep("Consultar en la tabla RDM100.Transshipment_setup de RDM la creacion del nuevo Ajuste de Inventario en  la BD FCRDMSIT_RDM_MTY de RDMv10. L418");
		System.out.print("PASO 18");
		// PASO 19
		addStep("Solicitar a los Usu FC Operadores SITE via correo electronico <UsuFEMCOMOperadoresSITE@oxxo.com>,la ejecucion de la interfaz  XXFC_RDMMFM_INVADJUST.GETNXT (), con la siguiente informacion:"
				+ "JobName: runRR06_InvAdjust"
				+ "Command Line: runInterfase.sh RR06_InvAdjust"
				+ "Hostgroup: tqa_clu_int_ng (FCWMNG01 , FCWMNG03)"
				+ "Grupo: TQA_BO_105");
		System.out.print("PASO 19");
		// PASO 20
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job runRR06_InvAdjust");
		System.out.print("PASO 20");
		JSONObject obj1 = new JSONObject(data.get("runRR06_InvAdjust"));
		Control_mInicio cm1 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm1.logOn();

		ControlM control1 = new ControlM(u, testCase, obj1); 
		control1.searchJob(); // busca el job
		
		String resultadoEjecucion1 = control1.executeJob(); 

		assertTrue(resultadoEjecucion1.equals("Ended OK") || resultadoEjecucion1.equals("Wait Condition"), 
				"El job runRR06_InvAdjust no se ejecuto correctamente");
		// PASO 21
		addStep("Validar que no existan registros de error generados por la interface RR06 en la tabla RIB_LOG_ERROR_INT en el esquema WMLOG de la base de datos FCWMFSIT, mediante la siguiente consulta:"
				+ "SELECT * FROM WMLOG.RIB_LOG_ERROR_INT "
				+ "WHERE INTERFACE= 'RR06' "
				+ "AND CREATION_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY CREATION_DATE DESC;");
		System.out.print("PASO 21");
		SQLResult valida21 = executeQuery(FCWMFSIT, consultaPaso21);
		boolean statusQuery21 = valida21.isEmpty();
		statusQuery21 = valida21.isEmpty();
		if (statusQuery21) {
			testCase.addQueryEvidenceCurrentStep(valida21);
		}
		assertTrue(statusQuery21, "Se encontraron registros en la tabla RIB_LOG_ERROR_INT" );
		// PASO 22
		addStep("Validar que no existen mensajes de error referentes al Framework Femsa_Fr_Rib para la familia RIB_INVADJUST en la tabla RIB_LOG_ERROR en el esquema WMLOG de la base de datos FCWMFSIT, mediante la siguiente consulta:"
				+ "SELECT * FROM WMLOG.RIB_LOG_ERROR "
				+ "WHERE ERROR_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY ERROR_DATE DESC");
		System.out.print("PASO 22");
		// PASO 23
		addStep("Comprobar que se hayan respaldado los tipos de mensajes de la familia Inventory Adjustments en la tabla RIB_INVADJUST_MSG_REP, al crear un Ajuste de Inventario:"
				+ "SELECT * FROM WMUSER.RIB_INVADJUST_MSG_REP "
				+ "WHERE CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 23");
		// PASO 24
		addStep("Comprobar que la interface FR_RIB haya creado el registro control del procesamiento y envio del nuevo ajuste de inventario con el WM_CODE=101 en la tabla RIB_INVADJUST_CONTROL:"
				+ "SELECT * FROM WMUSER.RIB_INVADJUST_CONTROL"
				+ "WHERE WM_CODE = 101 AND CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 24");
		SQLResult validat24 = FCWMFSIT.executeQuery(consultaPaso24);
		String atributo24 = validat24.getData(0, "WM_CODE");
		boolean validar24 = atributo24.equals("101");
		System.out.print(atributo24);
		assertFalse(!validar24, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 25
		addStep("Comprobar que no existan registros pendientes por procesar en la tabla RIB_RETRY_FR_MGR  para el nuevo ajuste de inventario creado, mediante la siguiente consulta:"
				+ "SELECT * FROM WMUSER.RIB_FR_RETRY_MGR"
				+ "WHERE CRTL_ID IN ('CTRL'S ID's DE LA TABLA DE CONTROL') ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 25");
		SQLResult valida25 = executeQuery(FCWMFSIT, consultaPaso25);
		boolean statusQuery25 = valida25.isEmpty();
		statusQuery25 = valida25.isEmpty();
		if (statusQuery25) {
			testCase.addQueryEvidenceCurrentStep(valida25);
		}
		assertTrue(statusQuery25, "Se encontraron registros en la tabla RIB_LOG_ERROR_INT" );
		// PASO 26
		addStep("Realizar conexion a la BD FCMOMPRF");
		System.out.print("PASO 26");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCMOMPRF_RMS16 + " usuario: "
				+ GlobalVariables.DB_USER_FCMOMPRF_RMS16);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCMOMPRF_RMS16.getConn());
		// PASO 27
		addStep("Validar que la informacion de un Ajuste de inventario creado en RDM v10 este en las tablas de RMS v16 mediante la consulta del siguiente query:"
				+ "SELECT * FROM RMS.INV_ADJ");
		System.out.print("PASO 27");
		System.out.println(consultaPaso17);
		SQLResult validat27 = RDM.executeQuery(consultaPaso27);
		boolean valida27 = validat27.isEmpty();
		if (!valida27) {
			testCase.addQueryEvidenceCurrentStep(validat27);
		}
		assertFalse("No se encuentra la tabla ", valida27);
		System.out.println(valida27);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Usuario QA con acceso y permiso para crear un ajuste de inventario en el aplicativo RDM v10 o solicitar al area de soporte RDM mediante un ticket la creacion de una juste de inventario."
				+ "2. Usuario con acceso y permisos para las siguientes bases de datos FCRDMSIT_MTY, FCWMFSIT, FCWMESIT, FCMOMPFR."
				+ "3. Cedis  a probar configurado en la VM wm_Oxxo_Rollout_config_vm de la BD  FCWMFSIT con los siguientes datos LOC_TYPE=W y V16=Y y con ID_CEDIS=9999999002."
				+ "4.- Contar la configuracion para el cedis 999999002 (en el campo Retek_Cr de en la tabla Wm_rdm_connections de la BD FCWMESIT el campo Retek_Cr) y con el valor en el campo transactionType='LT '"
				+ "5. Contar con el nombre y grupo del nuevo Job de Control M para RR06 de WM10.";
	}

	@Override
	public String setTestDescription() {
		return "Prueba de regresion  para comprobar la no afectacion en la funcionalidad principal de la interfaz FEMSA_RIB_INVADJUST para validar el envio de la informacion cuando se crea un Ajuste de Inventario en RDM v10  hacia RMS v16 por medio de las interfaces RR06, FR_RIB y RIB_INVADJUST, al ser migradas estas interfaces de WM 9.9 a WM 10.5";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_RIB_INVADJUSTComprobarEnvioDeLaInformacionDeRDMv10HaciaRMSv16AjusteDeInventario_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}