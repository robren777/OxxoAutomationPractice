package WEB.portal_Remesas.limiteDispersionRemesas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_prueba extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_Revision_Pantalla_Configuracion_Portal_Remesas_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String proveedor = data.get("Proveedor");
		String opcionPantalla = data.get("OpcionPantalla");
		String valorParametro = data.get("ValorParametro");
		u = new SeleniumUtil(new MEdgeTest(), true); 
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		//Query forma de pago electronico
		String query1 = "select * from XXREM.XXREM_CONF_CAT_ATR A" + 
				" INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR" + 
				" WHERE A.ATRIBUTO = '"+proveedor+"' AND C.VALOR = 'L'";

		//Query forma de pago efectivo�
		String query2 = "select * from XXREM.XXREM_CONF_CAT_ATR A" + 
				" INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR" + 
				" WHERE A.ATRIBUTO = '"+proveedor+"' AND C.VALOR = 'F'";
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ir al portal de aplicaciones."); 
		
		PortalApps pa = new PortalApps(u, testCase); 
		u.getDriver().manage().window().maximize();
		u.get(data.get("Url"));    
		u.hardWait(3);           
		
		pa.proceedToPortal();    // es para lo de la pagina no segura	
		pa.cicloLogin(user, password);
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Visualizar la secci�n \"Finanzas y Administraci�n\" "); 
	
		boolean validacion = pa.validarModuloFinanzasAdministracion();
		
		assertTrue(validacion,"No se visualiza la seccion");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Presionar  enlace \"Portal Remesas\"");
		
		rm.clickSeleccionarPortalRemesas();

		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		                              
		addStep(" Posicionarse en \"Configuraci�n\"");	
		
		rm.clickMenuConfiguracion();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		                                 
		addStep("Posicionarse en \"Atributos De Proveedores\"");
		
		rm.clickAtributosProveedor();
		                      
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "MTC-FT-001-Revisi�n de Pantalla Configuraci�n -Portal Remesas -Remesador Western Uni�n. \r\n" + 
				"MTC-FT-002-Revisi�n de Pantalla Configuraci�n Portal Remesas -Remesador TransNetwork\r\n" + 
				"MTC-FT-003-Revisi�n de Pantalla Configuraci�n  -Portal Remesas -Remesador MoneyGram\r\n";
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
		u.getDriver().quit();
	}

}
