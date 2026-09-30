package RDM.nucleo.GestionDeAlmacen;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import RDM.util.GlobalVariables;
import RDM.util.Nucleo;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_009_RIB_SOSTATUSComprobarEnvioDeLaInformacionDeRDMv10haciaRMSv16 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_RIB_SOSTATUSComprobarEnvioDeLaInformacionDeRDMv10haciaRMSv16_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String consultaPaso1 = "SELECT * FROM WMUSER.RIB_FR_CONFIG "
				+ "WHERE FAMILY = 'SOStatusCre'";
		String consultaPaso3 = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG "
				+ "WHERE INTERFASE = 'RR06' AND ENTIDAD = 'SOSTATUS'";
		String consultaPaso4 = "SELECT * FROM WMUSER.RIB_FR_CONFIG "
				+ "WHERE MSG_TYPE = 'SOStatusCre'";
		String consultaPaso5 = "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG_MV "
				+ "WHERE ID_CEDIS= '9999999002'";
		String consultaPaso7 = "SELECT * FROM WMUSER.WM_RDM_CONNECTIONS "
				+ "WHERE RETEK_CR = '9999999002' AND TRANSACTION_TYPE = 'LT'";
		String consultaPaso13 = "SELECT * FROM RDM.XXFC_BASE_TIPO_DISTRO WHERE DISTRO_NBR IN ('No. de transferencia')";
		String consultaPaso14 = "SELECT * FROM RMS.XXFC_BASE_PROGRAMACION WHERE DISTRO_NBR IN ('No. de transferencia')";
		String consultaPaso15 = "SELECT * FROM RMS.XXFC_BASE_PROGRAMACION_ZONA WHERE DISTRO_NBR IN ('No. de transferencia')";
		String consultaPaso22 = "SELECT * FROM STOCK_ALLOCATION_SELECT WHERE DISTRO_NBR = 'No transferencia'";
		String consultaPaso23 = "SELECT * FROM DISTRIBUTION_QUEUE WHERE USER_ID = 'id del usuario que realizo la operacion' AND WAVE_NBR = 'No. de ola seleccionado en RDM'";
		String consultaPaso24 = "SELECT * FROM WAVE WHERE WAVE_NBR = 'No. de ola seleccionado en RDM'";
		String consultaPaso25 = "SELECT * FROM STOCK_ORDER_INFO_UPLOAD WHERE FACILITY_ID = 'PR' AND DISTRO_NBR = 'No. de transferencia'";
		String consultaPaso28 = "SELECT * FROM STOCK_ALLOCATION_SELECT WHERE DISTRO_NBR = 'No. de transferencia'";
		String consultaPaso29 = "SELECT * FROM DISTRIBUTION_QUEUE WHERE USER_ID = 'ID del usuario que realizo la transaccion' AND WAVE_NBR = 'No. de ola seleccionado en RDM'";
		String consultaPaso30 = "SELECT * FROM WAVE WHERE WAVE_NBR = 'No de ola seleccionado en RDM'";
		String consultaPaso31 = "SELECT * FROM STOCK_ORDER_INFO_UPLOAD WHERE FACILITY_ID = 'PR' AND DISTRO_NBR = 'No. de transferencia' AND STATUS = 'DU'";
		String consultaPaso34 = "SELECT * FROM WMLOG.RIB_LOG_ERROR_INT "
				+ "WHERE INTERFACE= 'RR06' "
				+ "AND CREATION_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY CREATION_DATE DESC";
		String consultaPaso35 = "SELECT * FROM WMLOG.RIB_LOG_ERROR "
				+ "WHERE ERROR_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY ERROR_DATE DESC";
		String consultaPaso36 = "SELECT * FROM WMUSER.RIB_SOSTATUS_MSG_REP "
				+ "WHERE CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC";
		String consultaPaso37 = "SELECT * FROM WMUSER.RIB_SOSTATUS_CONTROL "
				+ "WHERE WM_CODE = 101 AND CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC";
		String consultaPaso38 = "SELECT * FROM WMUSER.RIB_FR_RETRY_MGR "
				+ "WHERE CRTL_ID IN ('CTRL'S ID's DE LA TABLA DE CONTROL') ORDER BY CREATION_DATE DESC";
		String fInicial = data.get("fInicial");
		String fFinal = data.get("fFinal");
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
		SQLUtil FCRDMSIT = new SQLUtil(GlobalVariables.DB_HOST_FCRDMSIT, GlobalVariables.DB_USER_FCRDMSIT,
				GlobalVariables.DB_PASSWORD_FCRDMSIT);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Realizar conexion a la BD FCWMFSIT");
		System.out.println("PASO 1");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCWMFSIT + " usuario: "
				+ GlobalVariables.DB_USER_FCWMFSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCWMFSIT.getConn());
		// PASO 2
		addStep("Validar que exista la configuracion para la interface RIB_FR con los valores de la familia RIB_SOSTATUS en la tabla RIB_FR_CONFIG en el esquema WMUSER en la BD  FCWMFSIT - Framework  mediante el siguiente Query:"
				+ "SELECT * FROM WMUSER.RIB_FR_CONFIG"
				+ "WHERE FAMILY = 'SOStatusCre'");
		System.out.println("PASO 2");
		System.out.println(consultaPaso1);
		SQLResult validat1 = FCWMFSIT.executeQuery(consultaPaso1);
		boolean valida1 = validat1.isEmpty();
		if (!valida1) {
			testCase.addQueryEvidenceCurrentStep(validat1);
		}
		assertFalse("No se encuentra la tabla ", valida1);
		System.out.println(valida1);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 3
		addStep("Validar la configuracion de la ruta URL HTTP del Framework en la tabla en la tabla Wm_interfase_config en la base de datos  FCWMFSIT - Framework en WEB METHODS :"
				+ "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG"
				+ "WHERE INTERFASE = 'RR06' AND ENTIDAD = 'SOSTATUS'");
		System.out.println("PASO 3");
		System.out.println(consultaPaso3);
		SQLResult validat3 = FCWMFSIT.executeQuery(consultaPaso3);
		boolean valida3 = validat3.isEmpty();
		if (!valida3) {
			testCase.addQueryEvidenceCurrentStep(validat3);
		}
		assertFalse("No se encuentra la tabla ", valida3);
		System.out.println(valida3);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 4
		addStep("Validar que exista la configuracion para la interface RIB_FR con los valores de la familia RIB_SOSTATUS en la tabla RIB_FR_CONFIG en el esquema WMUSER en la BD  FCWMFSIT - Framework  mediante el siguiente Query:"
				+ "SELECT * FROM WMUSER.RIB_FR_CONFIG "
				+ "WHERE MSG_TYPE = 'SOStatusCre'");
		System.out.println("PASO 4");
		System.out.println(consultaPaso4);
		SQLResult validat4 = FCWMFSIT.executeQuery(consultaPaso4);
		boolean valida4 = validat4.isEmpty();
		if (!valida4) {
			testCase.addQueryEvidenceCurrentStep(validat4);
		}
		assertFalse("No se encuentra la tabla ", valida4);
		System.out.println(valida4);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 5
		addStep("Consultar que el Cedis (a probar) este migrado a RMS v16 en la tabla WM_OXXO_ROLLOUT_CONFIG en el esquema WMUSER en la Base de Datos FCWMFSIT de Web Methods  mediante la siguiente consulta:"
				+ "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG_MV "
				+ "WHERE ID_CEDIS = '9999999002'");
		System.out.println("PASO 5");
		System.out.println(consultaPaso5);
		SQLResult validat5 = FCWMFSIT.executeQuery(consultaPaso5);
		boolean valida5 = validat5.isEmpty();
		if (!valida5) {
			testCase.addQueryEvidenceCurrentStep(validat5);
		}
		assertFalse("No se encuentra la tabla ", valida5);
		System.out.println(valida5);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 6
		addStep("Ingresar a la base de datos de WM_QA: FCWMESIT");
		System.out.println("PASO 6");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCWMFSIT + " usuario: "
				+ GlobalVariables.DB_USER_FCWMFSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCWMFSIT.getConn());
		// PASO 7
		addStep("Consultar que las conexiones del Cedis (a probar) exista en la tabla WM_RDM_CONNECTIONS en el esquema WMUSER en la base de datos FCWMESIT mediante la siguiente consulta:"
				+ "SELECT * FROM WMUSER.WM_RDM_CONNECTIONS "
				+ "WHERE RETEK_CR= '9999999002' AND TRANSACTION_TYPE = 'LT'");
		System.out.println("PASO 7");
		System.out.println(consultaPaso7);
		SQLResult validat7 = FCWMFSIT.executeQuery(consultaPaso7);
		boolean valida7 = validat7.isEmpty();
		if (!valida5) {
			testCase.addQueryEvidenceCurrentStep(validat7);
		}
		assertFalse("No se encuentra la tabla ", valida7);
		System.out.println(valida7);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 8
		addStep("Realizar el CP @MTC-FT-010 RIB_SO Comprobar el envio de la informacion de RMSv16 hacia RDMv10 a traves del framework RIB al crear un pedido de Transferencia Stock Order en RMSv16");
		System.out.println("PASO 8");
		// PASO 9
		addStep("- Solicitar a los Usu FC Operadores SITE via correo electronico <UsuFEMCOMOperadoresSITE@oxxo.com>,l con la siguiente informacion:"
				+ "JobName: xxfc_base_programacion_MTY"
				+ "Command Line: xxfc_base_programacion.sh"
				+ "JobName: XXFC_Select_Stock_Order_ab_MTY"
				+ "Command Line: XXFC_Select_Stock_Order_ab_MTY"
				+ "JobName: runDistribution.sh_MTY"
				+ "Command Line: run_distribution.sh PR"
				+ "Hostgroup: fcrdmapn"
				+ "Grupo: UPT_CEDISMTY");
		System.out.println("PASO 9");
		// PASO 10
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job  xxfc_base_programacion_MTY");
		System.out.print("PASO 10");
		JSONObject obj1 = new JSONObject(data.get("xxfc_base_programacion_MTY"));
		Control_mInicio cm1 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm1.logOn();

		ControlM control1 = new ControlM(u, testCase, obj1); 
		control1.searchJob(); // busca el job
		
		String resultadoEjecucion1 = control1.executeJob(); 

		assertTrue(resultadoEjecucion1.equals("Ended OK") || resultadoEjecucion1.equals("Wait Condition"), 
				"El job xxfc_base_programacion_MTY no se ejecuto correctamente");
		// PASO 11
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job  xxfc_actualiza_base_programacion_MTY");
		System.out.print("PASO 11");
		JSONObject obj2 = new JSONObject(data.get("xxfc_actualiza_base_programacion_MTY"));
		Control_mInicio cm2 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm2.logOn();

		ControlM control2 = new ControlM(u, testCase, obj2); 
		control1.searchJob(); // busca el job
		
		String resultadoEjecucion2 = control2.executeJob(); 

		assertTrue(resultadoEjecucion2.equals("Ended OK") || resultadoEjecucion2.equals("Wait Condition"), 
				"El  xxfc_actualiza_base_programacion_MTY no se ejecuto correctamente");
		// PASO 12
		addStep("Ingresar a la base de datos de FCMOMPRF_RMS16");
		System.out.print("PASO 12");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCMOMPRF_RMS16 + " usuario: "
				+ GlobalVariables.DB_USER_FCMOMPRF_RMS16);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCMOMPRF_RMS16.getConn());
		// PASO 13
		addStep("Realizar la consulta a la tabla Xxfc_base_tipo_distro en la BD FCMOMPRF_RMS16 con el esquema RMS:"
				+ "SELECT * FROM RDM.XXFC_BASE_TIPO_DISTRO WHERE DISTRO_NBR IN (No. de transferencia)");
		System.out.print("PASO 13");
		System.out.println(consultaPaso13);
		SQLResult validat13 = FCMOMPRF_RMS16.executeQuery(consultaPaso13);
		boolean valida13 = validat13.isEmpty();
		if (!valida13) {
			testCase.addQueryEvidenceCurrentStep(validat13);
		}
		assertFalse("No se encuentra la tabla ", valida13);
		System.out.println(valida13);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 14
		addStep("Realizar la consulta a la tabla Xxfc_base_programacion en la BD FCMOMPRF_RMS16 con el esquema RMS:"
				+ "SELECT * FROM RMS.XXFC_BASE_PROGRAMACION WHERE DISTRO_NBR IN (No. de transferencia)");
		System.out.print("PASO 14");
		System.out.println(consultaPaso14);
		SQLResult validat14 = FCMOMPRF_RMS16.executeQuery(consultaPaso14);
		boolean valida14 = validat14.isEmpty();
		if (!valida14) {
			testCase.addQueryEvidenceCurrentStep(validat14);
		}
		assertFalse("No se encuentra la tabla ", valida14);
		System.out.println(valida14);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 15
		addStep("Realizar la consulta a la tabla Xxfc_base_programacion_zona en la BD FCMOMPRF_RMS16 con el esquema RMS:"
				+ "SELECT * FROM RMS.XXFC_BASE_PROGRAMACION_ZONA WHERE DISTRO_NBR IN (No. de transferencia)");
		System.out.print("PASO 15");
		System.out.println(consultaPaso15);
		SQLResult validat15 = FCMOMPRF_RMS16.executeQuery(consultaPaso15);
		boolean valida15 = validat15.isEmpty();
		if (!valida15) {
			testCase.addQueryEvidenceCurrentStep(validat15);
		}
		assertFalse("No se encuentra la tabla ", valida15);
		System.out.println(valida15);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 16
		addStep("Ingresar a la Aplicacion RDM con User y Pass validos para realizar la programacion por Ruta");
		System.out.print("PASO 16");
		System.out.println(dbRdm.getHost());
		n.logIn();
		// PASO 17
		addStep("Ingresar a la siguiente Ruta Distribution Planning > XXFC Programacion y balanceo > XXFC Mod. Programacion p. Ruta");
		System.out.print("PASO 17");
		n.clickDistributionPlanningBalanceoPorRuta();
		n.screenshot();
		// PASO 18
		addStep("Completar los campos F.Inicio, F Final y seleccionar el boton de ejecutar ubicado en la parte superior con el simbolo de una palomita en rojo");
		System.out.print("PASO 18");
		n.ingresarParametrosFechas(fInicial, fFinal);
		n.screenshot();
		n.clickCheck();
		// PASO 19
		addStep("Seleccionar la transferencia a procesar, marcando el check del registro y seleccionar el boton { Distribute}");
		System.out.print("PASO 19");
		n.clickDistribute();
		// PASO 20
		addStep("Seleccionar Wave que este con un estatus Avail y darle clic en el boton {Process}"
				+ "Seleccionar boton Yes de pantalla Confirm y boton Yes de pantalla Confirm Alert");
		System.out.print("PASO 20");
		// PASO 21
		addStep("Realizar conexion a la BD FCRDMSIT");
		System.out.print("PASO 21");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCWMFSIT + " usuario: "
				+ GlobalVariables.DB_USER_FCWMFSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + FCRDMSIT.getConn());
		// PASO 22
		addStep("Realizar consulta en la BD FCRDMSIT en la tabla STOCK_ALLOCATION_SELECT que se muestren los registros de la transferencia"
				+ "SELECT * FROM STOCK_ALLOCATION_SELECT WHERE DISTRO_NBR = No transferencia");
		System.out.print("PASO 22");
		System.out.println(consultaPaso22);
		SQLResult validat22 = FCRDMSIT.executeQuery(consultaPaso22);
		boolean valida22 = validat22.isEmpty();
		if (!valida22) {
			testCase.addQueryEvidenceCurrentStep(validat22);
		}
		assertFalse("No se encuentra la tabla ", valida22);
		System.out.println(valida22);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 23
		addStep("Realizar consulta en la BD FCRDMSIT en la tabla DISTRIBUTION_QUEUE que se muestren los registros de la transferencia"
				+ "SELECT * FROM DISTRIBUTION_QUEUE WHERE USER_ID = 'id del usuario que realizo la operacion' AND WAVE_NBR = 'No. de ola seleccionado en RDM'");
		System.out.print("PASO 23");
		System.out.println(consultaPaso23);
		SQLResult validat23 = FCRDMSIT.executeQuery(consultaPaso23);
		boolean valida23 = validat23.isEmpty();
		if (!valida23) {
			testCase.addQueryEvidenceCurrentStep(validat23);
		}
		assertFalse("No se encuentra la tabla ", valida23);
		System.out.println(valida23);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 24
		addStep("Consultar el estaus de la ola realizando la consulta en la BD FCRDMSIT en la tabla WAVE que se muestren los registros de la transferencia"
				+ "SELECT * FROM WAVE WHERE WAVE_NBR = No. de ola seleccionado en RDM");
		System.out.print("PASO 24");
		System.out.println(consultaPaso24);
		SQLResult validat24 = FCRDMSIT.executeQuery(consultaPaso24);
		boolean valida24 = validat24.isEmpty();
		if (!valida24) {
			testCase.addQueryEvidenceCurrentStep(validat24);
		}
		assertFalse("No se encuentra la tabla ", valida24);
		System.out.println(valida24);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 25
		addStep("Consultar el detalle de la SO en la bd FCRDMSIT en la tabla STOCK_ORDER_INFO_UPLOAD"
				+ "SELECT * FROM STOCK_ORDER_INFO_UPLOAD WHERE FACILITY_ID = 'PR' AND DISTRO_NBR = No. de transferencia");
		System.out.print("PASO 25");
		System.out.println(consultaPaso25);
		SQLResult validat25 = FCRDMSIT.executeQuery(consultaPaso25);
		boolean valida25 = validat25.isEmpty();
		if (!valida25) {
			testCase.addQueryEvidenceCurrentStep(validat25);
		}
		assertFalse("No se encuentra la tabla ", valida25);
		System.out.println(valida25);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 26
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job  XXFC_Select_Stock_Order_ab_MTY");
		System.out.print("PASO 26");
		JSONObject obj3 = new JSONObject(data.get("xxfc_select_stock_order_ab_mty"));
		Control_mInicio cm3 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm3.logOn();

		ControlM control3 = new ControlM(u, testCase, obj3); 
		control3.searchJob(); // busca el job
		
		String resultadoEjecucion3 = control3.executeJob(); 

		assertTrue(resultadoEjecucion3.equals("Ended OK") || resultadoEjecucion3.equals("Wait Condition"), 
				"El job XXFC_Select_Stock_Order_ab_MTY no se ejecuto correctamente");
		// PASO 27
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job  runDistribution.sh_MTY");
		System.out.print("PASO 27");
		JSONObject obj4 = new JSONObject(data.get("runDistribution.sh_mty"));
		Control_mInicio cm4 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm4.logOn();

		ControlM control4 = new ControlM(u, testCase, obj4); 
		control4.searchJob(); // busca el job
		
		String resultadoEjecucion4 = control4.executeJob(); 

		assertTrue(resultadoEjecucion4.equals("Ended OK") || resultadoEjecucion4.equals("Wait Condition"), 
				"El job runDistribution.sh_MTY no se ejecuto correctamente");
		// PASO 28
		addStep("Validar en la BD FCRDMSIT en la tabla Stock_allocation_Select que no se muestren los registros"
				+ "SELECT * FROM STOCK_ALLOCATION_SELECT WHERE DISTRO_NBR = No. de transferencia");
		System.out.print("PASO 28");
		SQLResult valida28 = executeQuery(FCRDMSIT, consultaPaso28);
		boolean statusQuery = valida28.isEmpty();
		statusQuery = valida28.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(valida28);
		}
		assertTrue(statusQuery, "Se encontraron registros en la tabla Stock_allocation_Select" );
		// PASO 29
		addStep("Validar que en la BD FCRDMSIT en la tabla Distribution_que no se muestre registros por procesar"
				+ "SELECT * FROM DISTRIBUTION_QUEUE WHERE USER_ID = ID del usuario que realizo la transaccion AND WAVE_NBR = No. de ola seleccionado en RDM");
		System.out.print("PASO 29");
		SQLResult valida29 = executeQuery(FCRDMSIT, consultaPaso29);
		boolean statusQuery2 = valida29.isEmpty();
		statusQuery2 = valida29.isEmpty();
		if (statusQuery2) {
			testCase.addQueryEvidenceCurrentStep(valida29);
		}
		assertTrue(statusQuery2, "Se encontraron registros en la tabla Distribution_que");
		// PASO 30
		addStep("Validar que en la BQ FCRDMSIT en la tabla WAVE el campo WAVE_STATUS cambio a PLANNED"
				+ "SELECT * FROM WAVE WHERE WAVE_NBR = No de ola seleccionado en RDM");
		System.out.print("PASO 30");
		System.out.println(consultaPaso30);
		SQLResult validat30 = FCRDMSIT.executeQuery(consultaPaso30);
		String atributo301 = validat30.getData(0, "WAVE_STATUS");
		boolean validar3 = atributo301.equals("PLANNED");
		System.out.print(atributo301);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 31
		addStep("Validar que en la BD FCRDMSIT en la tabla STOCK_ORDER_INFO_UPLOAD cambio de estatus el campo STATUS = 'DU'"
				+ "SELECT * FROM STOCK_ORDER_INFO_UPLOAD WHERE FACILITY_ID = 'PR' AND DISTRO_NBR = 'No. de transferencia' AND STATUS = 'DU'");
		System.out.print("PASO 31");
		System.out.println(consultaPaso31);
		SQLResult validat31 = FCRDMSIT.executeQuery(consultaPaso31);
		String atributo311 = validat31.getData(0, "WAVE_STATUS");
		boolean validar4 = atributo311.equals("PLANNED");
		System.out.print(atributo311);
		assertFalse(!validar4, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 32
		addStep("Solicitar a los Usu FC Operadores SITE via correo electronico <UsuFEMCOMOperadoresSITE@oxxo.com>,l con la siguiente informacion:"
				+ "JobName: runRR06_SOStatus"
				+ "Command Line: runInterfase.sh RR06_SOStatus"
				+ "Hostgroup: tqa_clu_int_ng (FCWMNG01 , FCWMNG03)"
				+ "Grupo: TQA_BO_105");
		System.out.print("PASO 32");
		// PASO 33
		addStep("Consultar la ejecucion del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecucion se realizo de forma exitosa para el Job  runRR06_SOStatus"
				+ "Grupo: TQA_BO_105");
		System.out.print("PASO 33");
		JSONObject obj5 = new JSONObject(data.get("runRR06_SOStatus"));
		Control_mInicio cm5 = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm5.logOn();

		ControlM control5 = new ControlM(u, testCase, obj5); 
		control5.searchJob(); // busca el job
		
		String resultadoEjecucion5 = control5.executeJob(); 

		assertTrue(resultadoEjecucion5.equals("Ended OK") || resultadoEjecucion5.equals("Wait Condition"), 
				"El job runRR06_SOStatusY no se ejecuto correctamente");
		// PASO 34
		addStep("Validar que no existan registros de error generados por la interface RR06 en la tabla RIB_LOG_ERROR_INT en el esquema WMLOG de la base de datos FCWMFSIT, mediante la siguiente consulta:"
				+ "SELECT * FROM WMLOG.RIB_LOG_ERROR_INT "
				+ "WHERE INTERFACE= 'RR06' "
				+ "AND CREATION_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 34");
		SQLResult valida34 = executeQuery(FCWMFSIT, consultaPaso34);
		boolean statusQuery34 = valida34.isEmpty();
		statusQuery34 = valida34.isEmpty();
		if (statusQuery34) {
			testCase.addQueryEvidenceCurrentStep(valida34);
		}
		assertTrue(statusQuery34, "Se encontraron registros en la tabla RIB_LOG_ERROR_INT" );
		// PASO 35
		addStep("Validar que no existen mensajes de error referentes al Framework Femsa_Fr_Rib para la familia RIB_SOSTATUS en la tabla RIB_LOG_ERROR en el esquema WMLOG de la base de datos FCWMFSIT, mediante la siguiente consulta:"
				+ "SELECT * FROM WMLOG.RIB_LOG_ERROR "
				+ "WHERE ERROR_DATE >= 'DD/MM/AAAA' "
				+ "ORDER BY ERROR_DATE DESC");
		System.out.print("PASO 35");
		SQLResult valida35 = executeQuery(FCWMFSIT, consultaPaso35);
		boolean statusQuery35 = valida35.isEmpty();
		statusQuery35 = valida35.isEmpty();
		if (statusQuery35) {
			testCase.addQueryEvidenceCurrentStep(valida35);
		}
		assertTrue(statusQuery35, "Se encontraron registros en la tabla RIB_LOG_ERROR" );
		// PASO 36
		addStep("Comprobar que se hayan respaldado los tipos de mensajes de la familia RIB_SOSTATUS en la tabla RIB_SOSTATUS_MSG_REP, al crear una devolucion de proveedor:"
				+ "SELECT * FROM WMUSER.RIB_SOSTATUS_MSG_REP "
				+ "WHERE CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 36");
		System.out.println(consultaPaso36);
		SQLResult validat36 = FCWMFSIT.executeQuery(consultaPaso36);
		boolean valida36 = validat36.isEmpty();
		if (!valida36) {
			testCase.addQueryEvidenceCurrentStep(validat36);
		}
		assertFalse("No se encuentra la tabla ", valida36);
		System.out.println(valida36);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 37
		addStep("Comprobar que la interface FR_RIB haya creado el registro control del procesamiento y envIo del nuevo Pedido Stock Order Status con el WM_CODE=101 en la tabla RIB_SOSTATUS_CONTROL:"
				+ "SELECT * FROM WMUSER.RIB_SOSTATUS_CONTROL "
				+ "WHERE WM_CODE = 101 AND CREATION_DATE >= 'DD/MM/AAAA' ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 37");
		System.out.println(consultaPaso37);
		SQLResult validat37 = FCWMFSIT.executeQuery(consultaPaso37);
		String atributo37 = validat37.getData(0, "WM_CODE");
		boolean validar5 = atributo37.equals("101");
		System.out.print(atributo37);
		assertFalse(!validar5, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 38
		addStep("Comprobar que no existan registros pendientes por procesar en la tabla RIB_RETRY_FR_MGR  para  el pedido Stock Order Status, mediante la siguiente consulta:"
				+ "SELECT * FROM WMUSER.RIB_FR_RETRY_MGR "
				+ "WHERE CRTL_ID IN ('CTRL'S ID's DE LA TABLA DE CONTROL') ORDER BY CREATION_DATE DESC");
		System.out.print("PASO 38");
		SQLResult valida38 = executeQuery(FCWMFSIT, consultaPaso38);
		boolean statusQuery38 = valida38.isEmpty();
		statusQuery38 = valida38.isEmpty();
		if (statusQuery38) {
			testCase.addQueryEvidenceCurrentStep(valida38);
		}
		assertTrue(statusQuery38, "Se encontraron registros en la tabla RIB_RETRY_FR_MGR  para  el pedido Stock Order Status" );
		u.close();
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Precondiciones:"
				+ "1. Usuario con acceso y permiso al aplicativo de RDM v10"
				+ "2. Cedis a probar configurado en la tabla Wm_Oxxo_Rollout_config en el campo LOC_TYPE=W y V16=Y con el numero de Cedis en el campo ID_CEDIS Virtual  y en la tabla Wm_rdm_connections el cedis a probar configurado en el campo Retek_Cr con el valor en el campo  transactionType='LT '"
				+ "3. Acceso a la BD siguientes:FCRDMSIT_RDM_MTY, FCWMFSIT, FCMOMPFR, FCRMSDASUAT"
				+ "4. Conocer el flujo para realizar un pedido Stock Order Status"
				+ "5. Haber realizado el CP 10"
				+ "6. Acceso a las BD de RDM"
				+ "7. Contar con acceso a las bases de datos de FCRDMSIT_RDM_MTY, FCWMFSIT, FCMOMSIT, FCRMSDASUAT";
	}

	@Override
	public String setTestDescription() {
		return "Prueba de regresion para comprobar la no afectacion en la funcionalidad principal de la interfaz FEMSA_RIB_SOSTATUS para validar cuando se realizar un pedido Stock Order Status en RDM v10 el envio de la informacion de los datos generales del pedido stock Order Status a traves de RIB hacia RMS v16 por medio de las interfaces RR06, FR_RIB y RIB_SOSTATUS, al ser migradas estas interfaces de WM 9.9 a WM 10.5";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_RIB_SOSTATUSComprobarEnvioDeLaInformacionDeRDMv10haciaRMSv16_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}