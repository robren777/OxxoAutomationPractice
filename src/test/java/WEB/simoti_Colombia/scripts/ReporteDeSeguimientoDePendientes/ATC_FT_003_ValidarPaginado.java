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
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se aplique correctamente el paginado de acuerdo a las opciones de visualizaci�n de cantidad de registros en el grid de resultados
 * MTC-FT-003 Plaza_Reporte de Seguimiento de Pendientes_Validar Paginado
 *
 * Nota. Falta terminar de construir el paso 6 y probar el script completo:
 * Falta agregar el localizador de la pagina donde se encuentra ubicado el usuario (xmlPaginaActual)
 * @author Andrea Contreras
 * @date 2 Marzo 2023
 */
public class ATC_FT_003_ValidarPaginado extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarPaginado_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
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
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		mp.clickReportePromociones();
		validacion = rsp.validarNombrePantalla();
		assertTrue(validacion, "No se muestran la pantalla Reporte de Consultar Promociones");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros:\r\n" + 
				"Plaza: <se muestra la Plaza del usuario>\r\n" + 
				"Fecha Inicio: <seleccionar valor>\r\n" + 
				"Fecha Fin: <Seleccionar valor>\r\n" + 
				"Dar clic en [Consultar]");

		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza"), data.get("FechaInicio"), data.get("FechaFin"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
		rsp.clickConsultar();
		
		validacion = rsp.validarExistanResultados();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario selecciona la siguiente p�gina de resultados, ir hacia atr�s, adelante de las p�ginas.\r\n" + 
				"Debe cumplir con lo siguiente:\r\n" + 
				"1. p�ginado con 10 registros por p�gina.\r\n" + 
				"2. Numeraci�n de p�ginas y p�gina donde se encuentra ubicado el usuario.\r\n" + 
				"3. Total de registros encontrados en la consulta del reporte. ");
		
		//falta agregar el localizador de la pagina actual (pagina del grid) donde se encuentra ubicado el usuario (xmlPaginaActual)
		boolean validacionPaginado = rsp.validacionesPaginado();
		
		assertTrue(validacionPaginado, "No se cumplen las validaciones del paginado");
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que se aplique correctamente el paginado de acuerdo a las opciones de visualizaci�n de cantidad de registros en el grid de resultados";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarPaginado_test";
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