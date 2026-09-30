package WEB.portal_Remesas.scripts;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.portal_Remesas.om.Portal_Remesas;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_021_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Editar extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Editar_test(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Bases
		 *****************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
//		String user = data.get("User");
//		String password = data.get("Password");
//		String URL = data.get("Url");
		String query = "select * from XXREM.XXREM_CONF_PERFIL where DESCRIPCION = '%s'";
		String nombre_perfil = "abc";
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		Portal_Remesas portal = new Portal_Remesas(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password")); 
		String URL = data.get("Url");
		
//		// Paso 1
//		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
//		u = new SeleniumUtil(new MEdgeTest(), true);
//		u.get(URL);
//		Login pa = new Login(u, testCase);		
//		Portal_Remesas portal = new Portal_Remesas(u, testCase);
//		testCase.addScreenShotCurrentStep("Imagen de login");
//		MetodosExtra me = new MetodosExtra(u, testCase);
//		
//		// Paso 2
//		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
//		u.hardWait(7);
//		pa.logginPlatform(user, password);
		
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
		addStep("Ingresar a la pesta�a de Configuraci�n, Env�o y Retiro, Perfiles de Usuario");
		portal.seleccionaPortalRemesas();
		portal.Configuracion_EnvioyRetiro_Perfiles_Submenu();
		
		// Paso 4
		addStep("Presionar Editar");
		portal.EditarPerfilesUsuario("Prueba");
		
		// Paso 5
		addStep("Modificar los datos del campo Valor de atributo");
		portal.ModificaDatosEditar();
		
		// Paso 6
		addStep("Presionar Actualizar");
		portal.presionaGuardar();
		
		// Paso 7
		addStep("Ir a la tabla Resumen de atributos creados");
		portal.VerificaTablaNombrePerfil();
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
		return "ATC_FT_021_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Editar_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Editar";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}