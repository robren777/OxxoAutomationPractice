package RDM.articulosDeValor;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import RDM.util.ArticulosValor;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_ValidarInformacionArticulosDeValorQueNoSeActualicenEnLasTablas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarInformacionArticulosDeValorQueNoSeActualicenEnLasTablas_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String item = data.get("item");
		String uda10 = data.get("uda10");
		String consultarTiendas = "Select * From Wmuser.WM_OXXO_ROLLOUT_CONFIG " 
		        + "Where id_cedis = 'Cedis migrado' "
				+ "AND LOC_TYPE='S' " 
		        + "AND V16 = 'Y' "
				+ "AND V10 = 'N'";
		String consultaRMS10 = "select * from rms100.ITEM_LOC_TRAITS "
				+ "where item = 'ID del Articulo' "
				+ "and loc in ('Cedis en Transicion') "
				+ "order by create_datetime desc";
		String consultarRDM100 = "select * from RDM100.XXFC_CRITICAL_ITEMS "
				+ "where loc in ('Cedis en Transicion') "
				+ "and item ='ID del Articulo' "
				+ "order by create_datetime desc";
		String consultaDas = "select * from XXFC.XXFC_ITEM_LOC_TRAITS "
				+ "where item = 'ID del Articulo' "
				+ "and loc in ('Cedis en Transicion') "
				+ "order by create_datetime desc";
		String consultaDas2="select * from XXFC.XXFC_POS_MODS_STG "
				+ "where store in ('Tiendas Migradas Paso 13') "
				+ "and item ='ID del Articulo' "
				+ "and tran_type = 99 "
				+ "order by create_datetime desc";
		String permisosRMS10 = "Select * from table_privileges where TABLE_NAME = 'ITEM_LOC_TRAITS' ";
		String permisosRMS102 = "Select * from table_privileges where TABLE_NAME = 'XXFC_ITEM_LOC_TRAITS'";
		String insercionRMS10CEDIS = "select item,LOC, in_store_market_basket, create_datetime, last_update_id, last_update_datetime from rms100.ITEM_LOC_TRAITS "
				+ "where loc= 'Cedis en Transicion' "
				+ "and in_store_market_basket = 1";
		String RMS10FINAL = "select * from rms100.POS_MODS "
				+ "where Store in ('Tiendas Paso 12') "
				+ "and item = 'ID del Articulo' "
				+ "and tran_type = 99 "
				+ "order by create_datetime desc;";
		
		// CONTROL-M
		String controlMUser = GlobalVariables.usuarioRicardoControlM;
		String controlMPass = GlobalVariables.contrasenaRicardoControlM;
		String controlMServer = GlobalVariables.serverControlM;

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		ArticulosValor a = new ArticulosValor(u, testCase, data.get("server"));
		SQLUtil dbWebMetods = new SQLUtil(GlobalVariables.DB_HOST_WM, GlobalVariables.DB_USER_WM,
				GlobalVariables.DB_PASSWORD_WM);
		SQLUtil dbDas = new SQLUtil(GlobalVariables.DB_HOST_DAS, GlobalVariables.DB_USER_DAS,
				GlobalVariables.DB_PASSWORD_DAS);
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SQLUtil dbRms10 = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Entrar al aplicativo de RDMv10 del cedis en transicion "
				+ "-Ingresar a la siguiente URL en el navegador Internet Explorer " + "<URL valida>"
				+ "Ingresar usuario y password");
		System.out.println("PASO 1");
		a.logIn();
		// PASO 2
		addStep("En la pantalla Retek Distribution Management Menu " + "Ingresar a la opcion Support Functions "
				+ "En el submenu seleccionar la opcion Item Setup "
				+ "En el submenu seleccionar la opcion Item Master Editor o " + "XXFC Item Master Editor");
		System.out.println("PASO 2");
		a.supportFunctionsItemSetupMaster();
		// PASO 3
		addStep("Ingresar el articulo en el campo: " + "Item_id [ID del Articulo] " + "Dar click en \"Execute\"");
		System.out.println("PASO 3");
		a.ingresarArticuloExecute(item);
		// PASO 4
		addStep("Activar la configuracion del articulo de valor en el Campo Item_master_uda10 con 'Y', y Dar Click en el boton Save"
				+ " Item_master_uda10 [   Y   ] ");
		System.out.println("PASO 4");
		boolean configuroCorrectamente = a.activarConfigArticulo(uda10);
		assertTrue(configuroCorrectamente, "Item_master_uda10 no se configuro correctamente");
		String uda10Value = a.validarModificacionUda(uda10);
		assertEquals(uda10, uda10Value, "No se encontro " + uda10 + " se encontro " + uda10Value);
		u.hardWait(3);
		// PASO 5
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de WM"
				+ "- Conection name Nombre para la conexion" + "- User: Usuario Web Methods QA"
				+ "- Password Password Valido" + "- Hostname Host valido" + "- Puerto Puerto valido"
				+ "- Sid SID si es requerido" + "- Service Name <Servicio valido" + "Y presionar Entrar");
		System.out.println("PASO 5");
		// PASO 6
		addStep("Consultar las tiendas del Cedis migrado a probar");
		System.out.println("PASO 6");
		System.out.println(consultarTiendas);
		SQLResult validat = dbWebMetods.executeQuery(consultarTiendas);
		String atributo = validat.getData(0, "V10");
		boolean validar = atributo.equals("N");
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 7
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de RMS 10"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario RMS 10 QA"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 7");
		// PASO 8
		addStep("Consultar la insercion del registro del articulo de valor en la tabla ITEM_LOC_TRAITS en RMS 10  para el Cedis en transicion, ejecutar el query:");
		System.out.println("PASO 8");
		System.out.println(consultaRMS10);
		SQLResult validarRMS = executeQuery(dbRms10,consultaRMS10);
		boolean validarTablaRMS = validarRMS.isEmpty();
		assertFalse(!validarTablaRMS, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 9
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de RDM 10"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario RDM QA"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 9");
		// PASO 10
		addStep("Consultar la informacion en la tabla XXFC_CRITICAL_ITEMS en RDM del registro que no se inserto/actualizo en la tabla ITEM_LOC_TRAITS");
		System.out.println("PASO 10");
		SQLResult validarRDM = executeQuery(dbRdm,consultarRDM100);
		System.out.println(consultarRDM100);
		boolean validarTabla = validarRDM.isEmpty();
		if (!validarTabla) {
			testCase.addQueryEvidenceCurrentStep(validarRDM);
		}
		assertFalse("No se encuentra la tabla ", validarTabla);
		System.out.println(validarTabla);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 11
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos del DAS"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario DAS"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 11");
		// PASO 12
		addStep("Consultar la insercion del registro del articulo de valor en la tabla XXFC_ITEM_LOC_TRAITS en el esquema XXFC del DAS  para el Cedis en transicion, ejecutar el query:");
		System.out.println("PASO 12");
		String last_update_date2 = "";
		SQLResult validarDas2 = executeQuery(dbDas, consultaDas);
		last_update_date2 = validarDas2.getData(0, "last_update_date");
		boolean statusQuery2 = last_update_date2.isEmpty();
		assertFalse(!statusQuery2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 13
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de RDM 10"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario RDM QA"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 13");
		// PASO 14
		addStep("Validar que la tabla  ITEM_LOC_TRAITS tenga permisos de escritura y actualizacion en RMS 10:");
		System.out.print("PASO 14");
		SQLResult validarpermisos = dbRms10.executeQuery(permisosRMS10);
		boolean validap = validarpermisos.isEmpty();
		if (!validap) {

			testCase.addQueryEvidenceCurrentStep(validarpermisos);
		}
		assertFalse("No se encuentra la tabla ", validap);
		System.out.println(validap);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 15
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos del DAS"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario DAS"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 15");
		// PASO 16
		addStep("Validar que la tabla  XXFC_ITEM_LOC_TRAITS tenga permisos de escritura y actualizacion en RMS 10:");
		System.out.print("PASO 16");
		SQLResult validarpermisos2 = dbRms10.executeQuery(permisosRMS102);
		boolean validap2 = validarpermisos2.isEmpty();
		if (!validap2) {

			testCase.addQueryEvidenceCurrentStep(validarpermisos2);
		}
		assertFalse("No se encuentra la tabla ", validap2);
		System.out.println(validap2);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		u.close();
		// PASO 17
		addStep("Ingresar a Control M y ejecutar el JOB XXFC_CRITICAL_ITEMS_MTY");
		System.out.print("PASO 17");
		JSONObject obj = new JSONObject(data.get("xxfc_critical_items_mty"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm.logOn();

		ControlM control = new ControlM(u, testCase, obj); 
		control.searchJob(); // busca el job
		
		String resultadoEjecucion = control.executeJob(); 

		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), 
				"El job XXFC_CRITICAL_ITEMS_MTY no se ejecuto correctamente");
		u.hardWait(3);
		// PASO 18
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de RMS 10"
				+ "- Conection name Nombre para la conexion"
				+ "- User: Usuario RMS 10 QA"
				+ "- Password Password Valido"
				+ "- Hostname Host valido"
				+ "- Puerto Puerto valido"
				+ "- Sid SID si es requerido"
				+ "- Service Name Servicio valido"
				+ "Y presionar Entrar");
		System.out.println("PASO 18");
		// PASO 19
		addStep("Consultar la insercion del registro del articulo de valor en la tabla ITEM_LOC_TRAITS en RMS 10  para el Cedis en transicion, ejecutar el query:");
		System.out.println("PASO 19");
		System.out.println(insercionRMS10CEDIS);
		SQLResult validarms = dbRms10.executeQuery(insercionRMS10CEDIS);
		String atributorms = validarms.getData(0, "in_store_market_basket");
		boolean validarrms1 = atributorms.equals("1");
		System.out.print(validarrms1);
		assertFalse(!validarrms1, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 20
		addStep("Consultar la actualizacion de los registros del articulo de valor en la tabla POS_MODS en RMS 10 para las tiendas del Paso 12 del Cedis en transicion con tran_type=99 cuando el articulo es marcado como Y, ejecutar el query:");
		System.out.println("PASO 20");
		SQLResult validardbRms10 = executeQuery(dbRms10,RMS10FINAL);
		String atributodas1 = validardbRms10.getData(0, "tran_type");
		String atributodas2 = validardbRms10.getData(0, "Elect_Mtk_clubs");
		boolean vdbRms101 = atributodas1.equals("99");
		boolean vdbRms102 = atributodas2.equals("1");
		System.out.print(vdbRms101);
		System.out.print(vdbRms102);
		assertFalse(!vdbRms101, "La respuesta no tiene el el resultado esperado.");
		assertFalse(!vdbRms102, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 21
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos del DAS"
				+ "- Conection name Nombre para la conexion" + "- User: Usuario Web DAS " + "- Password Password Valido"
				+ "- Hostname Host valido" + "- Puerto Puerto valido" + "- Sid SID si es requerido"
				+ "- Service Name Servicio valido" + "Y presionar Entrar");
		System.out.println("PASO 21");
		// PASO 22
		addStep("Consultar la insercion del registro del articulo de valor en la tabla XXFC_ITEM_LOC_TRAITS en DAS en el esquema XXFC para el Cedis migrado, ejecutar el query:");
		System.out.println("PASO 22");
		System.out.println(consultaDas);
		SQLResult validarDasStatus1 = dbDas.executeQuery(consultaDas);
		String atributovalidarDas1 = validarDasStatus1.getData(0, "In_store_market_basquet");
		String atributovalidarDas21 = validarDasStatus1.getData(0, "Status");
		boolean validarDas1 = atributovalidarDas1.equals("1");
		boolean validarDas21 = atributovalidarDas21.equals("S");
		System.out.print(atributovalidarDas1);
		System.out.print(atributovalidarDas21);
		assertFalse(!validarDas1, "La respuesta no tiene el el resultado esperado.");
		assertFalse(!validarDas21, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		// PASO 23
		addStep("Consultar la insercion de los registros del articulo de valor en la tabla XXFC_POS_MODS_STG en DAS en el esquema XXFC de las tiendas Migradas del Paso 13 con tran_type=99, ejecutar el query:");
		System.out.print("PASO 23");
		SQLResult validarDas3 = executeQuery(dbDas, consultaDas2);
		String atributodas12 = validarDas3.getData(0, "tran_type");
		String atributodas22 = validarDas3.getData(0, "Elect_Mtk_clubs");
		String atributodas32 = validarDas3.getData(0, "Status");
		boolean vdas1 = atributodas12.equals("99");
		boolean vdas2 = atributodas22.equals("1");
		boolean vdas3 = atributodas32.equals("L");
		System.out.print(vdas1);
		System.out.print(vdas2);
		System.out.print(vdas3);
		assertFalse(!vdas1, "La respuesta no tiene el el resultado esperado.");
		assertFalse(!vdas2, "La respuesta no tiene el el resultado esperado.");
		assertFalse(!vdas3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.hardWait(3);
		u.close();
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso a aplicativo RDM (Cedis) con al menos una plaza en V16"
				+ "2. Articulos con Status diferente a Y en la tabla XXFC_ITEM_LOC_TRAITS, para poder enviar una actualizacion sobre estos registros existentes"
				+ "3. Exista la tabla XXFC_ITEM_LOC_TRAITS en el DAS Operativo"
				+ "4. Bloquear Tablas  ITEM_LOC_TRAITS y XXFC_ITEM_LOC_TRAITS temporalmente para escritura"
				+ "5. Ejecutar el JOB XXFC_CRITICAL_ITEMS_XXX bajo demanda, no en automatico.";
	}

	@Override
	public String setTestDescription() {
		return "Se valida que la informacion de los articulos de valor que no se actualizaron en las tablas  ITEM_LOC_TRAITS  y XXFC_ITEM_LOC_TRAITS  se inserten en la tabla XXFC_CRITICAL_ITEMS para un Cedis en Transicion"
				+ "Tabla donde no se debe insertar la informacion de los articulos de valor de las plazas no migradas:"
				+ "ITEM_LOC_TRAITS"
				+ "Tabla donde no se debe insertar la informacion de los articulos de valor de las plazas migradas:"
				+ "XXFC_ITEM_LOC_TRAITS ";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarInformacionArticulosDeValorQueNoSeActualicenEnLasTablas_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}