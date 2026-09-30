package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.ReporteDeSeguimientoDePendientes_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se muestre el mensaje de error cuando se intenta exportar y no existen datos de la consulta.
 * MTC-FT-004 Plaza_Reporte de Seguimiento de Pendientes_Validar Sin Datos
 * @author Maria Due�as
 * @date 2022
 */
public class ATC_FT_004_ValidarSinDatos extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarSinDatos_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		String msg1 = data.get("Mensaje1");
		String msg2 = data.get("Mensaje2");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.getDriver().manage().window().maximize();
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.logginInventarioEspacios(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiColombia();
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();//tambien funciona para el usuario plaza	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		u.hardWait(2);
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		mp.clickReportePromociones();
		validacion = rsp.validarNombrePantalla();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Es necesario que no existan datos en la consulta:\r\n" + 
				"Plaza: <se muestra la Plaza del usuario>\r\n" + 
				"Fecha Inicio: <seleccionar valor>\r\n" + 
				"Fecha Fin: <Seleccionar valor>\r\n" + 
				"Dar clic en [Consultar].\r\n" +
				"El sistema no arroja resultados con los filtros seleccionados, se muestra mensaje:\r\n" + 
				"'No se ha encontrado informaci�n con los filtros seleccionados.'");

		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza"), data.get("FechaInicio"), data.get("FechaFin"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
		rsp.clickConsultar();
		
		validacion = rsp.validarMensaje(msg1);
		assertTrue(validacion, "No se muestran el mensaje de error: "+msg1);
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Exportar]. " +
				"Si no hay informaci�n en la secci�n de resultados, al seleccionar est� opci�n se deber� mostrar un mensaje emergente al usuario: \r\n" + 
				"'No hay informaci�n para exportar'");
		
		rsp.clickExportar();
		u.hardWait(2);
		validacion = rsp.validarMensaje(msg2);
		assertTrue(validacion, "No se muestran el mensaje de error: "+msg2);
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que se muestre el mensaje de error cuando se intenta exportar y no existen datos de la consulta. ";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarSinDatos_test";
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