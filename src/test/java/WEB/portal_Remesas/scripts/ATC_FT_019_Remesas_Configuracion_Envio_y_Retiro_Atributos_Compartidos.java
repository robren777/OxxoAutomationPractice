package WEB.portal_Remesas.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.portal_Remesas.om.Portal_Remesas;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_019_Remesas_Configuracion_Envio_y_Retiro_Atributos_Compartidos extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_Remesas_Configuracion_Envio_y_Retiro_Atributos_Compartidos_test(HashMap<String, String> data) {
		
//		/*
//		 * Variables
//		 ******************************************************************************************/
//		String user = data.get("User");
//		String password = data.get("Password");
//		String URL = data.get("Url");
//		
//		// Paso 1
//		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
//		u = new SeleniumUtil(new MEdgeTest(), true);
//		u.get(URL);
//		u.hardWait(1);
//		Login pa = new Login(u, testCase);		
//		Portal_Remesas portal = new Portal_Remesas(u, testCase);
//		testCase.addScreenShotCurrentStep("Imagen de login");
//		MetodosExtra me = new MetodosExtra(u, testCase);
//		
//		// Paso 2
//		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
//		u.hardWait(5);
//		pa.logginPlatform(user, password);
		
		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		Portal_Remesas portal = new Portal_Remesas(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password")); 
		String URL = data.get("Url");
		String valor = data.get("valor_atributo");
		
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("El usuario ingresa a la aplicaci�n Access Control V3"); 
		
		u.getDriver().manage().window().maximize();
	    u.get(URL);
		u.hardWait(3); 
		pa.proceedToPortal();
		u.hardWait(2); 
		testCase.addScreenShotCurrentStep(u, "ingresarLiga");
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("Ingresar a la pesta�a de Configuraci�n, Env�o y Retiro, Atributos Compartidos");
		portal.seleccionaPortalRemesas();
		portal.Configuracion_EnvioyRetiro_Atributos_Submenu();
		
		// Paso 4
		addStep("Seleccionar un elemento de la tabla Resumen de atributos creados");
		portal.SeleccionaElementoResumen();
		
		// Paso 5
		addStep("Presionar Modificar");
		portal.ModificarElementoResumen();
		
		// Paso 6
		addStep("Modificar los datos del campo Valor de atributo");
		portal.ModificarDatos(valor);
	
		// Paso 7
		addStep("Presionar Actualizar");
		portal.PresionaActualizar(); 
		
		// Paso 8
		addStep("Ir a la tabla Resumen de atributos creados");
		portal.MuestraTablaResumen();
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
		return "ATC_FT_019_Remesas_Configuracion_Envio_y_Retiro_Atributos_Compartidos_test";
	}

	@Override
	public String setTestDesigner() {
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
}