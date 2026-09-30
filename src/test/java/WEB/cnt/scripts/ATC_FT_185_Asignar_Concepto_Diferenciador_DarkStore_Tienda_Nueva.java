package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_185_Asignar_Concepto_Diferenciador_DarkStore_Tienda_Nueva extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_185_Asignar_Concepto_Diferenciador_DarkStore_Tienda_Nueva_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String Plaza = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String diferenciador = data.get("Diferenciador");
		String bajo= "Desayuno_Bajo";
		String medio= "Desayuno_Medio";
		String alto= "Desayuno_Alto";

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a '" + URL +"' del portal de aplicaciones"); 
		
		u = new SeleniumUtil(new MEdgeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.getDriver().manage().window().maximize();
		u.hardWait(3); // tiempo en segundos
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		
		pa.clickSeleccionarPlaza(Plaza);
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario selecciona selecciona una tienda con estatus Nueva y da clic en el bot�n de Continuar.");
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();	

		/*
		 *                                             PASO 6
		 ******************************************************************************************/	     			
		addStep("Usuario selecciona la opci�n de \"Dark Store\" en el combo de Concepto Diferenciador.");
		
		fe.Framedatos();
		fe.Diferenciador(diferenciador);

		/*
		 *                                             PASO 7
		 ******************************************************************************************/	     			
		addStep("Usuario confirma que se cambien los valores en Autom�tico de los campos de Segmento de Tienda a NA");
		
		boolean validarValores = fe.ValidarCambioCampoSegmentoTiendaNA();
		testCase.addScreenShotCurrentStep(u, "CamposSegmentoTienda");
		
		assertTrue(validarValores, "No se cambiaron los valores en autom�tico a NA");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Asignar_Concepto_Diferenciador_DarkStore_Tienda_Nueva";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
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
		//u.getDriver().quit();
	}

}
