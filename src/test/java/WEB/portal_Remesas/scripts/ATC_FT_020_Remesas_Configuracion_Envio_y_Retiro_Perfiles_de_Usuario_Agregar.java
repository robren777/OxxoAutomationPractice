package WEB.portal_Remesas.scripts;

import static org.testng.Assert.assertTrue;

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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_020_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Agregar extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Agregar_test(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Bases
		 *****************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String query = "select * from XXREM.XXREM_CONF_PERFIL where DESCRIPCION = '%s'";
		String nombre_perfil = "abc";
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		Portal_Remesas portal = new Portal_Remesas(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password")); 
		String URL = data.get("Url");
		//String valor = data.get("valor_atributo");
		
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
		addStep("Presionar Agregar");
		portal.Agregar_perfilesUsuario();
		
		// Paso 5
		addStep("Seleccionar Proveedores");
		portal.SeleccionaProveedores_perfilesUsuario();
		
		// Paso 6
		addStep("Escribir un nombre de perfil");
		portal.EscribeNombrePerfil(nombre_perfil);
		
		// Paso 7
		addStep("Seleccionar Opciones del perfil");
		portal.seleccionaOpcionesPerfil();
		
		// Paso 8
		addStep("Presionar Guardar");
		portal.seleccionaGuardarPerfil();
		
		// Paso 9
		addStep("Validar en la BD select * from XXREM.XXREM_CONF_PERFIL");
		query = String.format(query, nombre_perfil);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (StatusQuery) {
			assertTrue(false, "No se encontro el perfil en la base de datos.");
		}		
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
		return "ATC_FT_020_Remesas_Configuracion_Envio_y_Retiro_Perfiles_de_Usuario_Agregar_test";
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