package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_021_ActivaciondeCorresponsalCambiario_BancoNoCorresponsal_TiendaNoAutorizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_021_ActivaciondeCorresponsalCambiario_BancoNoCorresponsal_TiendaNoAutorizada_Test(
			HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new ChromeTest(), true); 
		Login pa = new Login(u, testCase); 
		Datos_de_Operacion po = new Datos_de_Operacion(u, testCase);
		Permisos_y_Contratos pc = new Permisos_y_Contratos(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		
		String bank = "";
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String Asesor = data.get("Asesor");

		String ESTADO = data.get("ESTADO");
		String MUN = data.get("MUN");
		String TIENDACC = data.get("TIENDACC");
		String PLAZACC = data.get("PLAZACC");

		String querydolar = "SELECT * FROM TIENDAS.XX_CNT_MUNICIPIO_ACEP_DOLARES WHERE ESTADO='" + ESTADO
				+ "' AND MPIO_DEL='" + MUN + "'";
		String querybank = "SELECT * FROM TIENDAS.T_BANCOS where banco_corresponsal = '"+ data.get("BANCO") +"'";

		String tiendaCC = "SELECT ID_TIENDA, CORRESPONSAL_CAMBIARIO "
				+ "FROM TIENDAS.T_OPERACION "
				+ "WHERE ID_TIENDA=(SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS "
				+ "WHERE CR_SUPERIOR='" + PLAZACC + "' AND CR_TIENDA='" + TIENDACC + "')";
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); 
		
		u.get(data.get("Url")); 
		u.hardWait(3); 

		pa.logginInventarioEspacios(user, password);
		
		pa.clickSeleccionarPlaza(PlazaE);

		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		/**
		 *                                             PASO 2
		 ******************************************************************************************/

		addStep("Validar que la Tienda no pertenezca a un Estado y Municipio con autorizaci�n para operar D�lares.\r\n"
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM XX_CNT_MUNICIPIO_ACEP_DOLARES\r\n" 
				+ "WHERE ESTADO='ESTADO' AND MPIO_DEL='MUNICIPIO';"
				+ "Campo:ACEPTA_DOLARES=1 (Autorizado para Operar D�lares) ACEPTA_DOLARES=0 (No Autorizado para Operar D�lares)");

		SQLResult ExecuteQuery = executeQuery(dbFCIAS, querydolar);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ACEPTA_DOLARES").equals("0")) {
				testCase.addTextEvidenceCurrentStep("No tiene autorizaci�n para operar dolares");
			} else {
				assertTrue(false, "Tiene autorizaci�n para operar dolares");
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		/**
		 *                                             PASO 3
		 ******************************************************************************************/

		addStep("Validar que el Banco USD asignado a la Tienda no est� como Corresponsal Cambiario."
				+ "Ejecutar el siguiente query en la BD FCIASQA en el Esquema de Tiendas:\r\n"
				+ "SELECT * FROM T_BANCOS "
				+ "Campo: ES_CORRESPONSAL=S (Banco Corresponsal) ES_CORRESPONSAL=N (Banco No Corresponsal)"); 

		SQLResult ExecuteQuerybank = executeQuery(dbFCIAS, querybank);
		boolean StatusQuerybank = ExecuteQuerybank.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
		
		if (!StatusQuerybank) {
			if (ExecuteQuerybank.getData(0, "ES_CORRESPONSAL").equals("N")) {
				testCase.addTextEvidenceCurrentStep("El Banco USD asignado a la Tienda no est� como Corresponsal Cambiario.");
			} else {
				assertTrue(false, "El Banco USD asignado a la Tienda est� como Corresponsal Cambiario.");
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}

		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Dar clic en el link Datos de Operaci�n.");
		
		po.DatoOp();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que los campos Banco USD y Cuenta USD se encuentren seleccionados y/o capturados.");
		
		po.TextBancoDolaresSC();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Dar clic en el link Permisos y Contratos"); 
		
		pc.PermCon();

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Validar que los campos Referencia y D�gito Verificador se encuentren seleccionados y/o capturados.");
		
		boolean vacio =	pc.validarCodVerificadorYRef();
		assertFalse(vacio, "Estan vacios referencia y digito verificador");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
	
		addStep("Activar el Checkbox Corresponsal Cambiario.");
		
		pc.clickcheckBoxCorresponsalCambiario();

		

		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Dar clic en el bot�n SI.");
		
		
		
		SQLResult ExecuteQueryCC = executeQuery(dbFCIAS, tiendaCC);
		boolean StatusQueryCC = ExecuteQueryCC.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryCC);
		
		if (!StatusQueryCC) {
			if (ExecuteQueryCC.getData(0, "CORRESPONSAL_CAMBIARIO") != null) {
				if (ExecuteQueryCC.getData(0, "CORRESPONSAL_CAMBIARIO").equals("A")) {
					testCase.addTextEvidenceCurrentStep("Se permite la activaci�n del Campo Corresponsal Cambiario.");
				} else {
					assertTrue(false, "No se permite la activaci�n del Campo Corresponsal Cambiario.");
				}
			} else {
				assertTrue(false, "No se permite la activaci�n del Campo Corresponsal Cambiario.");
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_021_ActivaciondeCorresponsalCambiario_BancoNoCorresponsal_TiendaNoAutorizada";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_021_ActivaciondeCorresponsalCambiario_BancoNoCorresponsal_TiendaNoAutorizada";
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
