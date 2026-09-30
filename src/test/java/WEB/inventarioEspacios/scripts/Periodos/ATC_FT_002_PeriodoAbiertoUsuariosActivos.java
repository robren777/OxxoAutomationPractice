package WEB.inventarioEspacios.scripts.Periodos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.PeriodoAdministracionIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_PeriodoAbiertoUsuariosActivos extends BaseExecution {
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_PeriodoAbiertoUsuariosActivos_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 *********************************************************************/
		
		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		Boolean validacion;
		String queryxxine_cat_Periodos_V2;
		String query;
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		//									PASO 1
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		PeriodoAdministracionIEP perAdm = new PeriodoAdministracionIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		
		//        							PASO 2
				
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		
		//        							PASO 3
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
		//									PASO 4
		
		addStep("Validar que se visualicen los MENUS");
		
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
		
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
			
		}
		
		//									PASO 5
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		//									PASO 6
		
		addStep("Seleccionar Administraci�n --> Periodos");
		mp.clickAdministracion();
		mp.clickPeriodoOp();
		u.waitForLoadPage();
		
		//									PASO 7
		
		addStep("Ingresar a la BD XXINE y validar en la tabla XXINE_CAT_PERIODOS_V2  que coincida con el periodo actual mostrado en pantalla");
		String periodo_actual = perAdm.getTextPeriodoActual();
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_PERIODOS_V2 WHERE PERIODO = '" + periodo_actual + "'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS_PERIODO") == perAdm.getTextEstatus()) {
				testCase.addTextEvidenceCurrentStep("El registro en DB coincicide con lo mostrado en pantalla");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		perAdm.periodoEstatus();
		
		//									PASO 8
		
		addStep("Cerrar periodo abierto actual");
		perAdm.clickCerrarBtn();
		perAdm.clickConfirmarBtn();
		u.hardWait(40);
		
		//									PASO 9
		
		addStep("Validar mensaje de confirmacion y que se cierre el periodo correctamente insertando el nuevo registro en XXINE_CAT_PERIODOS_V2.");
		validacion = perAdm.mensajeConfirmacion("Cerrar");
		assertTrue(validacion, "No se cerro correctamente el periodo");
		
		perAdm.clickCerrarBtn2();
		
		periodo_actual = perAdm.getTextPeriodoActual();
		
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_PERIODOS_V2 WHERE PERIODO = '" + periodo_actual + "'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS_PERIODO").equals("Cerrado") && perAdm.getTextEstatus().equals("Cerrado")) {
				testCase.addTextEvidenceCurrentStep("El registro en DB coincicide con lo mostrado en pantalla");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		perAdm.periodoEstatus();
		
		//									PASO 10	
		
		addStep("Validar correo de apertura de periodo a los usuarios de sistema. (FUERA DE ALCANCE)");
		
		//									PASO 11	
		
		addStep("Validar las tablas que se actualizaron con la apertura del periodo descritas en el documento.");
		//Tabla XXINE_ESPACIOS_TIENDA_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_ESPACIOS_TIENDA_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla xxine.XXINE_ESPACIOS_TIENDA_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla xxine.XXINE_ESPACIOS_TIENDA_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_TIENDAS_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_TIENDAS_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_TIENDAS_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_TIENDAS_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_SUBPLAZAS_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_SUBPLAZAS_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_SUBPLAZAS_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_SUBPLAZAS_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_ESPACIOS_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_ESPACIOS_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_ESPACIOS_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_ESPACIOS_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_SEGMENTOS_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_SEGMENTOS_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_SEGMENTOS_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_SEGMENTOS_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_PLAZAS_CCM_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_PLAZAS_CCM_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_PLAZAS_CCM_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_PLAZAS_CCM_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//Tabla XXINE.XXINE_CAT_PLAZAS_HIST
		queryxxine_cat_Periodos_V2 = "SELECT * FROM " + db + ".XXINE_CAT_PLAZAS_HIST WHERE PERIODO = '" + periodo_actual + "' AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, queryxxine_cat_Periodos_V2);
		
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se actualizo la tabla XXINE.XXINE_CAT_PLAZAS_HIST con la apertura del periodo");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se actualizo la tabla XXINE.XXINE_CAT_PLAZAS_HIST con la apertura del periodo");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		//									PASO 12
		
		addStep("Validar que llegue correo a los usurios plaza de reglas no cumplidas. (FUERA DE ALCANCE)");
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido.  Periodo abierto usuarios activos envio de correo AdminOXXO";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_PeriodoAbiertoUsuariosActivos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}