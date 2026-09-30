package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
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

public class ATC_FT_049_Modificacion_de_Corresponsal_Cambiario_BancoNoCorresponsal_TiendaAutorizada
		extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_049_Modificacion_de_Corresponsal_Cambiario_BancoNoCorresponsal_TiendaAutorizada_test(
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
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String municipio = data.get("Municipio");
		String ACEPTA_DOLARES = "1";
		String ES_CORRESPONSAL = "";
		String bank = "";
		
		String queryOperaDolares = "SELECT ID_ESTADO,id_municipio, acepta_dolares, estado, mpio_del FROM tiendas.XX_CNT_MUNICIPIO_ACEP_DOLARES\r\n"
				+ "WHERE MPIO_DEL = 'MONTERREY'";
		String queryBancoCorresponsal = "SELECT ID_BANCO, banco_corresponsal, es_corresponsal FROM tiendas.T_BANCOS\r\n"
				+ "WHERE ES_CORRESPONSAL = 'S'";

		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + "");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		pa.logginInventarioEspacios(user, password);
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		addStep("Validar que la Tienda pertenezca a un Estado y Municipio con autorizaci�n para operar D�lares.\r\n"
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM XX_CNT_MUNICIPIO_ACEP_DOLARES\r\n"
				+ "WHERE ESTADO='ESTADO' AND MPIO_DEL='MUNICIPIO';\r\n"
				+ "Campo:ACEPTA_DOLARES=1 (Autorizado para Operar D�lares) ACEPTA_DOLARES=0 (No Autorizado para Operar D�lares)");

		SQLResult ExecuteQuery = executeQuery(dbFCIAS, queryOperaDolares);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.print("QUERY DOLAR: " + queryOperaDolares);
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			ACEPTA_DOLARES = ExecuteQuery.getData(0, "ACEPTA_DOLARES");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		boolean acepta = false;
		String aceptaq = "1";
		if (ACEPTA_DOLARES.equals(aceptaq)) {
			acepta = true;
		} else {
			acepta = false;
		}
		 assertTrue(acepta, "Acepta dolares");

		Datos_de_Operacion dO = new Datos_de_Operacion(u, testCase);
		dO.DatoOp();
		bank = dO.TextBancoDolares();
		System.out.print("String de metodo del banco: " + bank);
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		addStep("Validar que el Banco USD asignado a la Tienda est� como Corresponsal Cambiario."
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM T_BANCOS "
				+ "Campo: ES_CORRESPONSAL=S (Banco Corresponsal) ES_CORRESPONSAL=N (Banco No Corresponsal)"); // PENDIENTE


		System.out.print(queryBancoCorresponsal);
		queryBancoCorresponsal = String.format(queryBancoCorresponsal, bank);
		SQLResult ExecuteQuerybank = executeQuery(dbFCIAS, queryBancoCorresponsal);
		System.out.print(queryBancoCorresponsal);
		boolean StatusQuerybank = ExecuteQuerybank.isEmpty();

		if (!StatusQuerybank) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			ES_CORRESPONSAL = ExecuteQuerybank.getData(0, "ES_CORRESPONSAL");
			System.out.print("ES_CORRESPONSAL: " + ES_CORRESPONSAL + " ");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"No se encontro ninguna tabla o no tiene activado/seleccionado dolares");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
		boolean acepta1 = false;
		String corr = "S";
		if (ES_CORRESPONSAL.equals(corr)) {
			acepta1 = true;
		} else {
			acepta1 = false;
		}
		assertTrue(acepta1, "Es corresponsal");
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		
		addStep("Dar clic en el link Datos de Operacion");
		dO.DatoOp();
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Validar que los campos Banco USD y Cuenta USD se encuentren seleccionados y/o capturados");
		dO.TextBancoDolaresSC();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		Permisos_y_Contratos pO = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos");
		pO.PermCon();
		
		/*
		 * PASO 9
		 * se comenta para ahorrar un paso el step 10 hace este paso y el otro en un metodo
		 *****************************************************************************************/
		//addStep("Dar click en CONFIGURACION CUENTA DOLARES.");
		//pO.pantallaConfigCuentaDolares();
		
		/*
		 * PASO 10
		 *****************************************************************************************/
		addStep("Validar que los campos Referencia y D�gito Verificador se encuentren seleccionados y/o capturados.");

		Permisos_y_Contratos pc = new Permisos_y_Contratos(u, testCase);
		
		boolean vacio =	pc.validarCodVerificadorYRef();
		//assertFalse(vacio, "Estan vacios referencia y digito verificador");
		
		/*
		 * PASO 11
		 *****************************************************************************************/
		addStep("Cambiar el Banco Corresponsal.");
		pc.EligecheckBoxCorresponsalCambiario();
		boolean msj = pc.EligecheckBoxCorresponsalCambiario();
		assertTrue(msj, "Checkbox existe y No esta habilitado ");
		
		/*
		 * PASO 12
		 *****************************************************************************************/
		addStep("Dar clic en el bot�n Actualizar.");
		pc.clickBotonActualizar();
		
	

	
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_049_Modificacion_de_Corresponsal_Cambiario_BancoNoCorresponsal_TiendaAutorizada_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validar� que se permita cambiar el Banco Corresponsal desde la pantalla Configuraci�n Cuenta D�lares cuando el nuevo Banco no est� identificado como Corresponsal Cambiario pero que la Tienda se encuentra en el listado"
				+ " de Estados y Municipios para operar D�lares.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
