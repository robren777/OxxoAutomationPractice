package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Datos_de_Operacion;
import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_019_Activacion_de_Corresponsal_Cambiario_BancoCorresponsal_TiendaNoAutorizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_019_Activacion_de_Corresponsal_Cambiario_BancoCorresponsal_TiendaNoAutorizada_Test(
			HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		SQLUtil dbFCIAS1 = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);
		/*
		 * Variables
		 ******************************************************************************************/
		String bank = "";
		String ACEPTA_DOLARES = "0";
		String ES_CORRESPONSAL = "";
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String Asesor = data.get("Asesor");

		String ESTADO = data.get("ESTADO");
		String MUN = data.get("MUN");
		String TIENDACC = data.get("TIENDACC");
		String PLAZACC = data.get("PLAZACC");
		String CORRESPONSAL_CAMBIARIO = "";

		String querydolar = "SELECT * FROM TIENDAS.XX_CNT_MUNICIPIO_ACEP_DOLARES WHERE ESTADO='" + ESTADO
				+ "' AND MPIO_DEL='" + MUN + "'";
		String querybank = "SELECT * FROM TIENDAS.T_BANCOS where banco_corresponsal like '%s'";

		String tiendaCC = "SELECT ID_TIENDA, CORRESPONSAL_CAMBIARIO FROM TIENDAS.T_OPERACION WHERE ID_TIENDA=(SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS\r\n"
				+ "WHERE CR_SUPERIOR='" + PLAZACC + "' AND CR_TIENDA='" + TIENDACC + "')";
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

		/*
		 * PASO 4
		 *****************************************************************************************/

		addStep("Validar que la Tienda no pertenezca a un Estado y Municipio con autorizaci�n para operar D�lares.\r\n"
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM XX_CNT_MUNICIPIO_ACEP_DOLARES\r\n"
				+ "WHERE ESTADO='ESTADO' AND MPIO_DEL='MUNICIPIO';\r\n"
				+ "Campo:ACEPTA_DOLARES=1 (Autorizado para Operar D�lares) ACEPTA_DOLARES=0 (No Autorizado para Operar D�lares)");

		SQLResult ExecuteQuery = executeQuery(dbFCIAS, querydolar);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.print("QUERY DOLAR: " + querydolar);
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			ACEPTA_DOLARES = ExecuteQuery.getData(0, "ACEPTA_DOLARES");
			System.out.print(ACEPTA_DOLARES+"  acepta dolares ");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		boolean acepta = false;
		String aceptaq = "0";
		if (ACEPTA_DOLARES.equals(aceptaq)) {
			acepta = true;
		} else {
			acepta = false;
		}
		assertTrue(acepta, "Acepta dolares");

		Datos_de_Operacion po = new Datos_de_Operacion(u, testCase);
		po.DatoOp();
		bank = po.TextBancoPesos();
		System.out.print("String de metodo del banco: " + bank);

//
////		//------------------------------------PASO 5

		addStep("Validar que el Banco USD asignado a la Tienda est� como Corresponsal Cambiario."
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM T_BANCOS "
				+ "Campo: ES_CORRESPONSAL=S (Banco Corresponsal) ES_CORRESPONSAL=N (Banco No Corresponsal)"); // PENDIENTE

//	querybank = " SELECT * FROM TIENDAS.T_BANCOS where banco_corresponsal like '%\" + bank + \"%'";
		System.out.print(querybank);
		querybank = String.format(querybank, bank);
		SQLResult ExecuteQuerybank = executeQuery(dbFCIAS, querybank);
		System.out.print(querybank);
		boolean StatusQuerybank = ExecuteQuerybank.isEmpty();

		if (!StatusQuerybank) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			ES_CORRESPONSAL = ExecuteQuerybank.getData(0, "ES_CORRESPONSAL");
			System.out.print("ES_CORRESPONSAL: " + ES_CORRESPONSAL + " ");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"No se encontro ninguna tabla o no tiene activado seleccionado dolares");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
		boolean acepta1 = false;
		String corr = "S";
		if (ES_CORRESPONSAL.equals(corr)) {
			acepta1 = true;
		} else {
			acepta1 = false;
		}
		assertTrue(acepta1, "No es corresponsal");

		// po.DatoOp();
////		//------------------------------------PASO 5
		addStep("Validar que los campos Banco y Cuenta  se encuentren seleccionados y/o capturados.");
		po.TextBancoDolaresSC();
//		po.capturaDatosOp(Asesor);
////		
//	//------------------------------------PASO 4
		Permisos_y_Contratos pc = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); // PENDIENTE
		pc.PermCon();
//		po.clickBotonActualizar();
//		boolean msj =po.validacion();
//		assertTrue(msj, "No se presento pantalla de cambios guardados");

		addStep("Validar que los campos Referencia y D�gito Verificador se encuentren seleccionados y/o capturados.");
		pc.validarCodVerificadorYRefPesos();
		//
		addStep("Activar el Checkbox Corresponsal Cambiario.");
		pc.clickcheckBoxCorresponsalCambiario();
		pc.clickBotonActualizar();
		boolean msj2=	pc.validacion1();
		assertTrue(msj2, "NO SE GUARDO CAMBIOS");
		Thread.sleep(3000);
		
		System.out.print(tiendaCC+"consulta tienda corr");
		SQLResult ExecuteQueryCC = executeQuery(dbFCIAS1, tiendaCC);
		boolean StatusQueryCC = ExecuteQueryCC.isEmpty();

		if (!StatusQueryCC) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			CORRESPONSAL_CAMBIARIO = ExecuteQueryCC.getData(0, "CORRESPONSAL_CAMBIARIO");
		System.out.print("CORRESPONSAL_CAMBIARIO"+CORRESPONSAL_CAMBIARIO);
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		
		Boolean e=false;
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryCC);
		if (CORRESPONSAL_CAMBIARIO == null || CORRESPONSAL_CAMBIARIO=="A") {
			 e=true;
			 
			 CORRESPONSAL_CAMBIARIO = "NO";
			System.out.print("CORRESPONSAL_CAMBIARIOENTRO IF");
			System.out.print("CORRESPONSAL_CAMBIARIO"+CORRESPONSAL_CAMBIARIO);
		}
		
		assertFalse(e, "NO ESTA EN STATUS I CORRESPONSAL CAMBIARIO");

		boolean aceptaCC = false;
		String aceptaqCC = "I"; // I
		System.out.print("CORRESPONSAL_CAMBIARIO"+CORRESPONSAL_CAMBIARIO);
		if (CORRESPONSAL_CAMBIARIO.equals(aceptaqCC)) {
			aceptaCC = true;
		} else {
			aceptaCC = false;

		}
//		if (CORRESPONSAL_CAMBIARIO.equals(aceptaqCC)  ) {
//		aceptaCC = false;
//		}
		System.out.print("CORRESPONSAL_CAMBIARIO"+CORRESPONSAL_CAMBIARIO);
		assertTrue(aceptaCC, "NO ESTA EN STATUS A CORRESPONSAL CAMBIARIO");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_019_Activacion_de_Corresponsal_Cambiario_BancoCorresponsal_TiendaNoAutorizada_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_Activacion_de_Corresponsal_Cambiario_BancoCorresponsal_TiendaNoAutorizada_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
