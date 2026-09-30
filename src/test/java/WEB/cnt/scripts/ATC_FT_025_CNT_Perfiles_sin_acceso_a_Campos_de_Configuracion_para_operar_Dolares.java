package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_025_CNT_Perfiles_sin_acceso_a_Campos_de_Configuracion_para_operar_Dolares extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_025_Perfiles_sin_acceso_a_Campos_de_Configuracion_para_operar_Dolares(
			HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String cheque = data.get("cheque");
		String dolares = data.get("dolares");
		String efectivo = data.get("efectivo");
		String TDD1 = data.get("TDD1");
		String TDC1 = data.get("TDC1");
		String vales = data.get("vales");

		String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

//		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

//		//------------------------------------PASO 4
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); // PENDIENTE
		po.PermCon();
//		//------------------------------------PASO 5
		addStep("Validar que el Perfil <<<Perfil>>> no pueda activar y desactivar los campos Dar Cambio, Saldo Inicial D�lares y Monto M�ximo de Saldo Inicial.");

		boolean msj = po.validar_C_SI_MIM();

		assertFalse(msj, "Checkboxs existen y estan habilitados");
		SQLResult queryResult = executeQuery(dbFCIAS, query);
		
		boolean empty = queryResult.isEmpty();

		if (empty) {
			
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		
		}						
		testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta

		assertFalse(empty, "No se guardo la direccion de envio en la tabla ");
		
//		//------------------------------------ PASO 6
//
//		addStep("Dar clic en el bot�n Actualizar.");
//		po.clickBotonActualizar();
//		boolean msj =po.validacion();
//		assertTrue(msj, "No se presento pantalla de cambios guardados");
////		//System.out.print(user);
//		dc.clickMenuPedidoAdmin(plaza,direcc,cp,calle,numero,entreCalle,yEntreCalle,nomContacto,telContacto,comentario);
//		
//		//								   PASO 7
//		addStep("Validar en la tabla que se registre el ultimo movimiento. XXINE_CAT_MOVIMIENTOS_SISTEMA");
//		po.ClaveLargaDistancia(ID_MUNICIPIO);
		// System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
//		System.out.println(query);
//		
//		SQLResult queryResult = executeQuery(dbFCIAS, query);
//		
//		boolean empty = queryResult.isEmpty();
//
//		if (empty) {
//			
//			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
//		
//		}						
//		testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//
//		assertFalse(empty, "No se guardo la direccion de envio en la tabla XXINE_CAT_MOVIMIENTOS_SISTEMA");
//		
//		//      						   PASO 8
//		addStep("Seleccionar menu Administrador : Consulta de movimientos");
//		dc.clickSeleccionMenuAdmon();
//		
//		//	  							   PASO 9	
//		AdmonConsultaMovtos cm = new AdmonConsultaMovtos(u, testCase);
//		addStep("Ingresar filtros y dar click en Buscar");
//		u.hardWait(5);
//		cm.clickIngresaFiltros(user);
//
//		//		                           PASO 10
//		addStep("Se muestran los resultados en pantalla con los campos:\r\n"
//				+ "ID\r\n"
//				+ "FECHA\r\n"
//				+ "NOMBRE TIPO DE MOVIMIENTO\r\n"
//				+ "CVE PLAZA\r\n"
//				+ "NOMBRE PLAZA\r\n"
//				+ "NOMBRE SUBPLAZA\r\n"
//				+ "CR TIENDA\r\n"
//				+ "NOMBRE TIENDA\r\n"
//				+ "DIRECCION\r\n"
//				+ "ESPACIO ID\r\n"
//				+ "NOMBRE ESPACIO\r\n"
//				+ "ELIMINADO ID\r\n"
//				+ "CAMPO\r\n"
//				+ "VALOR\r\n"
//				+ "USUARIO\r\n"
//				+ "NOMBRE\r\n"
//				+ "PATERNO\r\n"
//				+ "MATERNO\r\n"
//				+ "POSICION\r\n"
//				+ "");
//        cm.clickMuestraCamposPasoDiez();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_025_Perfiles_sin_acceso_a_Campos_de_Configuracion_para_operar_Dolares";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_025_Perfiles_sin_acceso_a_Campos_de_Configuracion_para_operar_Dolares";
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
