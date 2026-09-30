package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_264_Modificar_el_Segmento_a_una_Tienda_con_estatus_Operativa_al_tener_un_Concepto_diferenciador 
extends BaseExecution{

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_264_Modificar_el_Segmento_a_una_Tienda_con_estatus_Operativa_al_tener_un_Concepto_diferenciador_test
	(HashMap<String, String> data) throws SQLException{
		
		/*
		 * Utilerias
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String id_tienda = data.get("ID_Tienda");
		String distrito = data.get("Distrito");
		String diferenciador = data.get("Diferenciador");
		String diferenciadorT = data.get("DiferenciadorTexto");
		String query1 = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA='"+id_tienda+"'";

		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar a la '" + URL ); 
		
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Paso 1");
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
	
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");


		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");

		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("El usuario selecciona una tienda con estatus �Operativa� que corresponda a Oxxo M�xico.");
		
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Se confirmara que la tienda que se selecciono cuenta con un Concepto diferenciador de "+ diferenciadorT + " y da clic en el bot�n de Continuar.");

		System.out.print(query1);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query1);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertFalse(StatusQuery, "No se ha encontrado ningun resultado en la consulta de la tabla");
		
		if(!StatusQuery) {
			
			String diferenciadorBD = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");
			
	        if(!diferenciadorBD.equals(diferenciadorT)) {
	              Assert.fail("No tiene el concepto diferenciador "+diferenciadorT);
	        }
			
		}
		
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"Formato y Entorno\".");
		Formato_y_Entorno fe =  new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		String texto = fe.diferenciadorTexto();
		Boolean textoIgual = texto.equals(diferenciadorT);
		assertTrue(textoIgual, "la tienda no cuenta con un concepto diferenciador "+ diferenciadorT);
		
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		
		addStep("Usuario intenta cambiar la opci�n de \"Concepto Diferenciador\" que se tiene seleccionado para la tienda.");
		
		fe.Diferenciador(diferenciador);
		u.hardWait(5);
		
		String difActual = fe.diferenciadorTexto();	
		boolean validacion =  difActual.equals(diferenciador);
		assertTrue(validacion, "El sistema no permite modificar la informacion del concepto diferenciador");
		
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que Permita modificar la informaci�n �Segmento de Tienda� al ser un Concepto Diferenciador de Excepci�n.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
}
