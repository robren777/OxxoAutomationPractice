package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

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
import utils.sql.SQLUtil;

public class ATC_FT_051_Calculo_de_Digito_Verificador_Dos_Digitos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_051_Calculo_de_Digito_Verificador_Dos_Digitos_Test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String PlazaP = data.get("PlazaP");
		String TiendaP = data.get("TiendaP");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String cheque = data.get("cheque");
		String dolares = data.get("dolares");
		String efectivo = data.get("efectivo");
		String TDD1 = data.get("TDD1");
		String TDC1 = data.get("TDC1");
		String vales = data.get("vales");
		String REFERENCIA = "";
		String t1 = data.get("t1");
		String t2 = data.get("t2");
		String t3 = data.get("t3");
		String t4 = data.get("t4");
		String t5 = data.get("t5");

		String query = "SELECT REFERENCIA FROM T_OPERACION where id_tienda  "
				+ " IN (SELECT ID_TIENDA FROM t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + " AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
//																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

////		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

////		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
////		//------------------------------------PASO 3
//		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

////		//------------------------------------PASO 4
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); // PENDIENTE
		po.PermCon();
////		//------------------------------------PASO 5
		addStep("Dar clic en el link Configuraci�n Cuenta D�lares.");
////		boolean ref = po.validarCodVerificadorYRef();
////		System.out.println(ref);
////
////		
////		assertTrue(ref, "no esta referencia y cof verificador");
////		
		String digito = po.ExtraerCodVerificador();
		System.out.println("digito  :" + digito);
		int number = Integer.parseInt(digito);
		int digitoExcel = po.Formula_digito_Verificador(t1, t2, t3, t4, t5);
		boolean si = true;
		if (digitoExcel == number) {
			si = true;
			testCase.addTextEvidenceCurrentStep("Digito verificador segun formula de excel y del portal son iguales");

		} else {
			si = false;
			testCase.addTextEvidenceCurrentStep(
					"Digito verificador segun formula de excel y del portal NO son iguales");
		}
		assertTrue(si, "el digito verificador segun formula excel no es igual a la del portal");
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
		return "ATC_FT_051_Calculo_de_Digito_Verificador_Dos_Digitos_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_051_Calculo_de_Digito_Verificador_Dos_Digitos_Test";
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
