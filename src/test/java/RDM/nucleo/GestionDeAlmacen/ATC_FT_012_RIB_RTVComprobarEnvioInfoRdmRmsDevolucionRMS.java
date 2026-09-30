package RDM.nucleo.GestionDeAlmacen;
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

public class ATC_FT_012_RIB_RTVComprobarEnvioInfoRdmRmsDevolucionRMS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_RIB_RTVComprobarEnvioInfoRdmRmsDevolucionRMS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String idContenedor = data.get("idContenedor");
		//String locationId = data.get("locationId");
		
		// CONTROL-M
		String controlMUser = GlobalVariables.USER_G_CONTROL_M;
		String controlMPass = GlobalVariables.PASS_G_CONTROL_M;
		String controlMServer = GlobalVariables.serverControlM;
		
		/*
		 * UTILERIAS
		 */
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

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Realizar conexion a la BD FCWMFSIT");
		System.out.println("PASO 1");

		testCase.addTextEvidenceCurrentStep("Conectado a la bd: " + FCWMFSIT.getConn());

		// PASO 2
		addStep("Validar la configuracion de la ruta URL HTTP del Framework en la tabla en la "
				+ "tabla Wm_interfase_config en la base de datos FCWMFSIT Framework en "
				+ "WEB METHODS");
		System.out.println("PASO 2");
		
		String validarRuta = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG "
				+ "WHERE INTERFASE = 'RR06' AND ENTIDAD ='RTV'";
		System.out.println(validarRuta);
		
		SQLResult executeQuery = FCWMFSIT.executeQuery(validarRuta);
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.WM_INTERFASE_CONFIG");
		
		// PASO 3
		addStep("Validar que exista la configuracion para la interface RIB_FR con los valores de la "
				+ "familia RIB_RTV en la tabla RIB_FR_CONFIG en el esquema WMUSER en la "
				+ "BD FCWMFSIT Framework");
		System.out.println("PASO 3");
		
		String validarConfig = "SELECT * FROM WMUSER.RIB_FR_CONFIG WHERE FAMILY = 'RTV";
		System.out.println(validarRuta);
		
		executeQuery = FCWMFSIT.executeQuery(validarConfig);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.RIB_FR_CONFIG");
		
		// PASO 4
		addStep("Consultar que el Cedis (a probar) este migrado a RMS v16 en la tabla "
				+ "WM_OXXO_ROLLOUT_CONFIG en el esquema WMUSER en la Base de Datos FCWMFSIT "
				+ "de Web Methods");
		System.out.println("PASO 4");

		String consultarCedis = "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG_MV "
				+ "WHERE ID_CEDIS= '9999999002' AND V16 = 'Y'";
		System.out.println(consultarCedis);
		
		executeQuery = FCWMFSIT.executeQuery(consultarCedis);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.WM_OXXO_ROLLOUT_CONFIG_MV");
		
		// PASO 5
		addStep("Ingresar a la base de datos de WM_QA: FCWMESIT");
		System.out.println("PASO 5");
		
		testCase.addTextEvidenceCurrentStep("Conectado a la bd: " + FCWMESIT.getConn());
		
		// PASO 6
		addStep("Consultar que las conexiones del Cedis (a probar) exista en la tabla "
				+ "WM_RDM_CONNECTIONS en el esquema WMUSER en la base de datos FCWMESIT");
		System.out.println("PASO 6");
		
		String consultarConexiones = "SELECT * FROM WMUSER.WM_RDM_CONNECTIONS "
				+ "WHERE RETEK_CR='9999999002' AND TRANSACTION_TYPE='LT'";
		System.out.println(consultarConexiones);
		
		executeQuery = FCWMESIT.executeQuery(consultarConexiones);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.WM_RDM_CONNECTIONS");
		
		// PASO 7
		addStep("Entrar a RDM");
		System.out.println("PASO 7");
		
		n.logIn();
	
		// PASO 8
		addStep("Entrar a la siguiente Ruta: Returns > Reports > XXFC Tarimas Devolucion a Proveedor");
		System.out.println("PASO 8");
	
		n.returnsReportsTarimas();
		
		// PASO 9
		addStep("Buscar un proveedor que tenga tarimas configuradas, seleccionar salir");
		System.out.println("PASO 9");
		
		//n.buscarContenedor();
		 
		// PASO 10
		addStep("Conectarse a la BD de FCMOMPFR");
		System.out.print("PASO 10");
		
		testCase.addTextEvidenceCurrentStep("Conectado a la bd: " + FCMOMPRF_RMS16.getConn());
		
		// PASO 11
		addStep("Ejecutar la siguiente consulta para verificar que los Items (<ID ITEM>, <ID ITEM>) "
				+ "se encuentra relacionados al proveedor <ID VENDOR> en RMS16 FCMOMPFR");
		System.out.print("PASO 11");
		
		String verificarItems = "SELECT * FROM RMS.ITEM_SUPPLIER "
				+ "WHERE SUPPLIER = '<ID VENDOR>' AND ITEM IN ('<ID ITEM>', '<ID ITEM>')";
		System.out.println(verificarItems);
		
		executeQuery = FCMOMPRF_RMS16.executeQuery(verificarItems);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RMS.ITEM_SUPPLIER");

		// PASO 12
		addStep(" Ejecutar la siguiente consulta para validar la relacion del proveedor "
				+ "con el item");
		System.out.print("PASO 12");
		
		String validarRelacion = "SELECT * FROM RMS.ITEM_SUPP_COUNTRY "
				+ "WHERE SUPPLIER='<ID VENDOR>' AND ITEM IN ('<ID ITEM>', '<ID ITEM>')";
		System.out.println(validarRelacion);
		
		executeQuery = FCMOMPRF_RMS16.executeQuery(validarRelacion);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RMS.ITEM_SUPP_COUNTRY");
		
		// PASO 13
		addStep("Ejecutar la siguiente consulta para validar la informacion del ITEM");
		System.out.print("PASO 13");
		
		String validarItem = "SELECT * FROM RMS.ITEM_SUPP_COUNTRY_DIM WHERE SUPPLIER='<ID VENDOR>' "
				+ "AND ITEM IN ('<ID ITEM>', '<ID ITEM>')";
		System.out.println(validarItem);
		
		executeQuery = FCMOMPRF_RMS16.executeQuery(validarItem);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RMS.ITEM_SUPP_COUNTRY_DIM");
		
		// PASO 14
		addStep("Conectarse a la BD de FCRDMSIT");
		System.out.print("PASO 14");
		
		testCase.addTextEvidenceCurrentStep("Conectado a la bd: " + FCRDMSIT.getConn());
		
		// PASO 15
		addStep("Ejecutar la siguiente consulta para obtener los Items (<ID ITEM>, <ID ITEM>) "
				+ "relacionados al Vendor <ID VENDOR> en FCRDMSIT");
		System.out.print("PASO 15");
		
		String obtenerItems = "SELECT * FROM RDM100.ITEM_SUPPLIER "
				+ "WHERE VENDOR_NBR = <ID VENDOR> AND PRIMARY_SUPP_IND = 'N'";
		System.out.println(obtenerItems);
		
		executeQuery = FCRDMSIT.executeQuery(obtenerItems);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RDM100.ITEM_SUPPLIER");
		
		// PASO 16
		addStep("Ejecutar la siguiente consulta para obtener los contenedores relacionados a los i"
				+ "tems de un proveedor en la tabla CONTAINER_ITEM de FCRDMSIT");
		System.out.print("PASO 16");
		
		String obtenerContenedores = "SELECT * FROM CONTAINER_ITEM WHERE ITEM_ID IN "
				+ "(SELECT ITEM_ID FROM RDM100.ITEM_SUPPLIER WHERE VENDOR_NBR = <ID VENDOR> "
				+ "AND PRIMARY_SUPP_IND = 'N');";
		System.out.println(obtenerContenedores);
		
		executeQuery = FCRDMSIT.executeQuery(obtenerContenedores);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER_ITEM");
		
		// PASO 17
		addStep("Ejecutar la siguiente consulta para obtener la cantidad de articulos "
				+ "relacionados a un contenedor en la tabla CONTAINER_ITEM de FCRDMSIT");
		System.out.print("PASO 17");
		
		String cantidadArticulos = "SELECT CONTAINER_ID, COUNT (*) "
				+ "FROM CONTAINER_ITEM WHERE CONTAINER_ID IN (SELECT CONTAINER_ID "
				+ "FROM CONTAINER WHERE RECEIPT_DATE IS NOT NULL AND CONTAINER_STATUS='T') "
				+ "AND ITEM_ID IN (SELECT ITEM_ID FROM RDM100.ITEM_SUPPLIER "
				+ "WHERE VENDOR_NBR = <ID VENDOR> AND PRIMARY_SUPP_IND = 'N') "
				+ "GROUP BY CONTAINER_ID;GROUP BY CONTAINER_ID;";
		System.out.println(cantidadArticulos);
		
		executeQuery = FCRDMSIT.executeQuery(cantidadArticulos);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER_ITEM");
		
		// PASO 18
		addStep("Ejecutar la siguiente consulta para ver la informacion de un contenedor que "
				+ "cumple con las caracteristicas necesarias para realizar un RTV");
		System.out.print("PASO 18");
		
		String infoContenedor = "SELECT * FROM CONTAINER WHERE CONTAINER_ID= '<ID del Contenedor>' "
				+ "AND RECEIPT_DATE IS NOT NULL AND CONTAINER_STATUS='T' ORDER BY RECEIPT_DATE DESC";
		System.out.println(infoContenedor);
		
		executeQuery = FCRDMSIT.executeQuery(infoContenedor);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER");
		
		// PASO 19
		addStep("Ejecutar la siguiente consulta para obtener el detalle de los articulos del "
				+ "contenedor en la tabla CONTAINER_ITEM de FCRDMSIT");
		System.out.print("PASO 19");
		
		String detalleArticulos = "SELECT * FROM CONTAINER_ITEM WHERE CONTAINER_ID IN ('<ID del Contenedor>');";
		System.out.println(detalleArticulos);
		
		executeQuery = FCRDMSIT.executeQuery(detalleArticulos);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER_ITEM");
		
		// PASO 20
		addStep("Ingresas a la pantalla de Return to vendor de RDM en la siguiente Ruta: "
				+ "Inventory Management > Return to Vendor");
		System.out.print("PASO 20");
		
		n.logIn();
		
		n.clickInventoryReturn();
		
		// PASO 21
		addStep("Capturar el ID del contenedor: <ID del Contenedor> y "
				+ "ejecutar la consulta presionando el boton de la palomita roja");
		System.out.println("PASO 21");
		
		n.capturarContenedor(idContenedor);
		
		// PASO 22
		addStep("Seleccionar el numero del proveedor <ID VENDOR> (que se consulto "
				+ "previamente en la pantalla {XXFC-Tarimas para devolucion a proveedor}) "
				+ "y que tiene tarimas configuradas para devolucion con LOCATION ID=DVPENENER");
		System.out.println("PASO 22");
		
		//rString vendor = n.obtenerVendor();
		
		// PASO 23
		addStep("Seleccionar el mes de la devolucion MONTH=DVPENERO "
				+ "(Mismo que se consulto en la pantalla {XXFC-Tarimas para devolucion a "
				+ "proveedor} con LOCATION ID=DVPENENER)");
		System.out.println("PASO 23");
		
		// TODO
		
		// PASO 24
		addStep("Ejecutar la siguiente consulta para obtener el costo de la ultima compra que llego "
				+ "a RDM de los articulos del contenedor en la tabla XXFC_ITEM_LOC_LAST_PO_COST de "
				+ "FCRDMSIT");
		System.out.println("PASO 24");
		
		String obtenerCosto = "SELECT * FROM RDM100.XXFC_ITEM_LOC_LAST_PO_COST "
				+ "WHERE ITEM_ID IN ('<ID ITEM>', '<ID ITEM>');";
		System.out.println(obtenerCosto);
		
		executeQuery = FCRDMSIT.executeQuery(obtenerCosto);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RDM100.XXFC_ITEM_LOC_LAST_PO_COST");
		
		// PASO 25
		addStep("Ejecutar la siguiente consulta para obtener el costo total haciendo un cruce "
				+ "entre las tablas CONTAINER_ITEM y XXFC_ITEM_LOC_LAST_PO_COST de FCRDMSIT");
		System.out.println("PASO 25");
		
		String ultimaCompra = "SELECT SUM((NVL(C.LAST_PO_UNIT_COST,0)*CI.UNIT_QTY)) TOTAL "
				+ "FROM CONTAINER_ITEM CI, XXFC_ITEM_LOC_LAST_PO_COST C "
				+ "WHERE CI.FACILITY_ID = 'PR' "
				+ "AND CI.CONTAINER_ID  = '<ID del Contenedor>' "
				+ "AND CI.ITEM_ID = C.ITEM_ID(+) "
				+ "AND C.SUPPLIER = '<ID VENDOR>' "
				+ "AND C.LOCATION = '9999999002'";
		System.out.println(ultimaCompra);
		
		executeQuery = FCRDMSIT.executeQuery(ultimaCompra);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER_ITEM");
		
		// PASO 26
		addStep("Ejecutar la siguiente consulta para verificar si tiene un descuento aplicado en la "
				+ "tabla XXFC_PTJE_FA que debe estar previamente configurado");
		System.out.println("PASO 26");
		
		String verificarDescuento = "SELECT (PORCENTAJE/100) FROM RDM100.XXFC_PTJE_FA "
				+ "WHERE FACILITY_ID='PR' AND VENDOR_NBR='<ID VENDOR>' AND HABILITADO='Y'";
		System.out.println(verificarDescuento);
		
		executeQuery = FCRDMSIT.executeQuery(verificarDescuento);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RDM100.XXFC_PTJE_FA");
		
		// PASO 27
		addStep("Ejecutar la siguiente consulta para comprobar el calculo de los campos "
				+ "CALCULAED y SUGGESTED COST de la ventana de RDM Retur To Vendor, "
				+ "para ello se utiliza el costo de la ultima compra que llego de RDM a la "
				+ "tabla XXFC_ITEM_LOC_LAST_PO_COST se cruza con la CONTAINER y la "
				+ "XXFC_PTJE_FA:Realizar el calculo con los datos obtenidos del "
				+ "TOTAL=8286.6 en el primer query * lo que se obtuvo en el segundo query "
				+ "PORCENTAJE=0.03 y el resultado debe ser igual al valor de los campos "
				+ "CALCULAED y SUGGESTED COST de la ventana de RDM Retur To Vendor"
				+ "SELECT (8286.6 * 0.03) CALCULAED FROM DUAL;");
		System.out.println("PASO 27");
		
		// TODO
		
		// PASO 28
		addStep("Capturar el numero de autorizacion del campo "
				+ "AUTHORIZATION=RTV-<No. de autorizacion>, presionar el boton [RTV], "
				+ "presionar el boton [YES] del mesaje emergente, Presionar el boton [Save] "
				+ "de la ventana emergente XXFC RTV ACTIVE SETUP para imprimir el reporte "
				+ "de la devolucion");
		System.out.println("PASO 28");
		
		// TODO
		
		// PASO 29
		addStep("Ejecutar las siguientes consultas para comprobar que el contenedor haya sido "
				+ "eliminado de las tablas CONTAINER y CONTAINER_ITEM de FCRDMSIT");
		System.out.println("PASO 29");
		
		String comprobarContenedor = "SELECT * FROM CONTAINER WHERE "
				+ "CONTAINER_ID='<ID del Contenedor>' "
				+ "SELECT * FROM CONTAINER_ITEM WHERE CONTAINER_ID IN ('<ID del Contenedor>')";
		System.out.println(comprobarContenedor);
		
		executeQuery = FCRDMSIT.executeQuery(comprobarContenedor);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en CONTAINER");
		
		// PASO 30
		addStep("Ejecutar las siguientes consultas para verificar que se haya generado la "
				+ "informacion de la devolucion RTV en las siguientes tablas de FCRDMSIT");
		System.out.println("PASO 30");
		
		// RTV
		String verificarRtv = "SELECT * FROM RTV WHERE RTV_ID = '<ID de RTV>'";
		System.out.println(verificarRtv);
		
		executeQuery = FCRDMSIT.executeQuery(verificarRtv);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RTV");
		
		// RTV_DETAIL
		String verificarRtvDetail = "SELECT * FROM RTV_DETAIL WHERE RTV_ID='<ID de RTV>'";
		System.out.println(verificarRtvDetail);
		
		executeQuery = FCRDMSIT.executeQuery(verificarRtvDetail);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RTV_DETAIL");
		
		// XXFC_RTV
		String verificarXxfcRtv = "SELECT * FROM RDM100.XXFC_RTV WHERE RTV_ID='<ID de RTV>'";
		System.out.println(verificarXxfcRtv);
		
		executeQuery = FCRDMSIT.executeQuery(verificarXxfcRtv);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en XXFC_RTV");
		
		// XXFC_RTV_COSTO
		String verificarXxfcRtvCosto = "SELECT * FROM RDM100.XXFC_RTV_COSTO_CONCEPTO "
				+ "WHERE RTV_ID='<ID de RTV>'";
		System.out.println(verificarXxfcRtvCosto);
		
		executeQuery = FCRDMSIT.executeQuery(verificarXxfcRtvCosto);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en XXFC_RTV_COSTO");
		
		// XXFC_RTV_STORAGE_DETAIL
		String verificarXxfcRtStorage = "SELECT * FROM RDM100.XXFC_RTV_STORAGE_DETAIL WHERE RTV_ID='<ID de RTV>';";
		System.out.println(verificarXxfcRtStorage);
		
		executeQuery = FCRDMSIT.executeQuery(verificarXxfcRtStorage);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en XXFC_RTV_STORAGE_DETAIL");
		
		// PASO 31
		addStep("Validar que en la vista XXFC_RTV_STORAGE_DETAIL_V de la BD FCRDMSIT "
				+ "se registro la transaccion RTV");
		System.out.println("PASO 31");
		
		String validarVista = "SELECT * FROM RDM100.XXFC_RTV_STORAGE_DETAIL_V "
				+ "WHERE RTV_ID = '<ID de RTV>'";
		System.out.println(validarVista);
		
		executeQuery = FCRDMSIT.executeQuery(validarVista);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RDM100.XXFC_RTV_STORAGE_DETAIL_V");
		
		// PASO 32
		addStep("Consulta para obtener el ajuste de inventario realizado por la devolucion RTV");
		System.out.println("PASO 32");
		
		String ajuste = "SELECT * FROM RDM100.INV_ADJUSTMENT_TO_UPLOAD "
				+ "WHERE ITEM_ID IN ('<ID ITEM>', '<ID ITEM>')";
		System.out.println(ajuste);
		
		executeQuery = FCRDMSIT.executeQuery(ajuste);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en INV_ADJUSTMENT_TO_UPLOAD");
		
		// PASO 33
		addStep("Ejecutar job runRR06_RTV");
		System.out.println("PASO 33");
		
		JSONObject obj = new JSONObject(data.get("runRR06_RTV"));
		Control_mInicio controlInicio = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		controlInicio.logOn();

		ControlM controlM = new ControlM(u, testCase, obj); 
		controlM.searchJob(); // busca el job
		
		String resultadoEjecucion = controlM.executeJob(); 

		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), 
				"El job runRR06_RTV no se ejecuto correctamente");
		
		// PASO 34
		addStep("Validar que no existan registros de error generados por la interface "
				+ "RR06 en la tabla RIB_LOG_ERROR_INT en el esquema WMLOG de la base de datos FCWMFSIT");
		System.out.println("PASO 34");
		
		String error = "SELECT * FROM WMLOG.RIB_LOG_ERROR_INT "
				+ "WHERE CREATION_DATE>='<ddmmaaaa>' AND INTERFACE = 'RR06' AND "
				+ "FAMILY_MSG='RTV' ORDER BY CREATION_DATE DESC;";
		System.out.println(error);
		
		executeQuery = FCWMFSIT.executeQuery(error);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMLOG.RIB_LOG_ERROR_INT");
		
		// PASO 35
		addStep("Validar que no existen mensajes de error referentes al Framework Femsa_Fr_Rib para la "
				+ "familia RIB_SOSTATUS en la tabla RIB_LOG_ERROR en el esquema WMLOG de la base de "
				+ "datos FCWMFSIT");
		System.out.println("PASO 35");
		
		String errorFw = "SELECT * FROM WMLOG.RIB_LOG_ERROR WHERE ERROR_DATE> = '<ddmmaaaa>' "
				+ "AND FAMILY='RTV' ORDER BY ERROR_DATE DESC;";
		System.out.println(errorFw);
		
		executeQuery = FCWMFSIT.executeQuery(errorFw);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMLOG.RIB_LOG_ERROR");
		
		// PASO 36
		addStep("Comprobar que no existan registros pendientes por procesar en la tabla "
				+ "RIB_RETRY_FR_MGR  para el pedido Stock Order Status");
		System.out.println("PASO 36");
		
		String pendientes = "SELECT * FROM WMUSER.RIB_FR_RETRY_MGR WHERE CREATION_DATE> = '<ddmmaaaa>' "
				+ "AND CTRL_ID IN ('<ID_CTRL>', '<ID_CTRL>') ORDER BY CREATION_DATE DESC;";
		System.out.println(pendientes);
		
		executeQuery = FCWMFSIT.executeQuery(pendientes);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.RIB_FR_RETRY_MGR");
		
		// PASO 37
		addStep("Ejecutar la siguiente consulta para comprobar que se genero un registro de respaldo"
				+ " de mensaje por Item de la devolucion en la tabla RIB_RTV_CONTROL de FCWMFSIT");
		System.out.println("PASO 37");
		
		String registroRespaldo = "SELECT * FROM WMUSER.RIB_RTV_MSG_REP "
				+ "ORDER BY CREATION_DATE DESC";
		System.out.println(registroRespaldo);
		
		executeQuery = FCWMFSIT.executeQuery(registroRespaldo);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.RIB_RTV_MSG_REP");
		
		// PASO 38
		addStep("Ejecutar la siguiente consulta para comprobar que se genero un registro por Item "
				+ "de la devolucion con WM_CODE=101 en la tabla RIB_RTV_CONTROL de FCWMFSIT");
		System.out.println("PASO 38");
		
		String registroDev = "SELECT * FROM WMUSER.RIB_RTV_CONTROL "
				+ "WHERE MSG_REP_ID IN ('<ID_CTRL>', '<ID_CTRL>') ORDER BY CREATION_DATE DESC;";
		System.out.println(registroDev);
		
		executeQuery = FCWMFSIT.executeQuery(registroDev);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en WMUSER.RIB_RTV_CONTROL");
	
		// PASO 39
		addStep("Ejecutar la siguiente consulta para comprobar que llego la devolucion RTV de "
				+ "RDM10 a RMS16 a la tabla RMS.RTV_HEAD de FCMOMPFR");
		System.out.println("PASO 38");
		
		String llegoDev = "SELECT * FROM RMS.RTV_HEAD WHERE EXT_REF_NO='<ID de RTV>';";
		System.out.println(llegoDev);
		
		executeQuery = FCMOMPRF_RMS16.executeQuery(llegoDev);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RMS.RTV_HEAD");
		
		// PASO 40
		addStep("Ejecutar la siguiente consulta para comprobar que llego el detalle de la devolucion "
				+ "RTV de RDM10 a RMS16 a la tabla RMS.RTV_DETAIL de FCMOMPFR");
		System.out.println("PASO 40");
		
		String llegoDetalle = "SELECT * FROM RMS.RTV_DETAIL WHERE RTV_ORDER_NO='100000400'; ";
		System.out.println(llegoDetalle);
		
		executeQuery = FCMOMPRF_RMS16.executeQuery(llegoDetalle);
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(statusQuery, "No se encontraron registros en RMS.RTV_DETAIL");
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Usuario QA con acceso y permiso al aplicativo de RDM v10 "
				+ "2. Usuario con acceso y permiso para la base de datos de RDM. "
				+ "3. Cedis  a probar configurado en la tabla Wm_Oxxo_Rollout_config de la "
				+ "BD  FCWMFSIT - Framework de WEB METHODS en el campo LOC_TYPE=W y V16=Y "
				+ "con el numero de Cedis en el campo ID_CEDIS y en la tabla "
				+ "Wm_rdm_connections de la BD FCWMESIT Backoffice de WEB METHODS el cedis a "
				+ "probar configurado en el campo Retek_Cr con el valor en el campo "
				+ "transactionType='LT '."
				+ "4. Contar con el acceso a las siguientes bases de datos "
				+ "FCRDMSIT_RDM_MTY, FCWMFSIT, FCMOMPFR, FCRMSDASUAT.";
	}

	@Override
	public String setTestDescription() {
		return "Prueba de regresion para comprobar la no afectacion en la funcionalidad "
				+ "principal de la interfaz FEMSA_RIB_RTV para validar cuando se crea una "
				+ "Devolucion a Proveedor en RDM v10 el envio de la informacion de los "
				+ "datos generales de la devolucion a traves de RIB hacia RMS v16 "
				+ "por medio de las interfaces RR06, FR_RIB y RIB_RTV, al ser migradas estas "
				+ "interfaces de WM 9.9 a WM 10.5";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_RIB_RTVComprobarEnvioInfoRdmRmsDevolucionRMS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}