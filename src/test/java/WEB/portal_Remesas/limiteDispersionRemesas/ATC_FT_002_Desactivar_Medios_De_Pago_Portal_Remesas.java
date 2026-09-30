package WEB.portal_Remesas.limiteDispersionRemesas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Desactivar_Medios_De_Pago_Portal_Remesas extends BaseExecution{

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
		u = new SeleniumUtil(new MEdgeTest(), true); 
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar a portal de remesas"); 
			
		PortalApps pa = new PortalApps(u, testCase); 
		u.getDriver().manage().window().maximize();
		u.get(data.get("Url"));    
		u.hardWait(3);           
		
		pa.proceedToPortal();    // es para lo de la pagina no segura	
		pa.cicloLogin(user, password);
		
		rm.clickSeleccionarPortalRemesas();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Presionar \"Configuraci�n\""); 
	
		rm.clickMenuConfiguracion();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Presionar \"Atributos de proveedor \"");
		
		rm.clickAtributosProveedor();

		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		                              
		addStep("Presionar proveedor "+proveedor);	
		
		rm.seleccionarProveedor(proveedor);
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		                                 
		addStep("Presionar \"Formas de pago\"");
		
		boolean validarOpcion = rm.seleccionarOpcion(opcionPantalla);
		
		assertTrue(validarOpcion,"No se encontro la opcion "+opcionPantalla);
		
		rm.PresionarFormaDePago();
		                      
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		                                 
		addStep("Deshabilitar todos los medios de pago");
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		                                 
		addStep("Presionar bot�n \"Modificar\"");
		
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		                                 
		addStep("Ingresar cambios");
		
		/*
		 *                                             PASO 9
		 ******************************************************************************************/
		                                 
		addStep("Presionar bot�n \"Guardar\"");
		
		/*
		 *                                             PASO 10
		 ******************************************************************************************/
		                                 
		addStep("? Se deshabilitaron todos los medios de pago Efectivo, Pago a Tarjeta bancaria (total) y (mixto) ");
		

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar que se puedan realizar la deshabilitaci�n de todos los medios de pago en el portal de remesadores.\r\n" +
			   "MTC-FT-011-Desactivar todos los medios de pago Portal Remesas WesternUnion\r\n" + 
			   "MTC-FT-012-Desactivar todos los medios de pago Portal Remesas MoneyGram\r\n" + 
			   "MTC-FT-013-Desactivar todos los medios de pago Portal Remesas TransNetwork\r\n";
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
		return "Contar con la pantalla generada para la configuracion de los parametros";
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
