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
/**Desc: Se valida que se muestren los mensajes de error para filtros obligatorios que falten de captura o selecci�n de un valor con usuario plaza o megaplaza asociado a un perfil SIMOTI: OFICINA CENTRAL.
 * MTC-FT-005 Oficial Central_Reporte de Seguimiento de Pendientes_Validar Mensajes de Error
 * @author Marichuy Due�as
 * @date 2022
 */
public class ATC_FT_005_ValidarMensajesDeError extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarMensajesDeError_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String plaza = data.get("Plaza");
		String msg1 = data.get("Mensaje1");
		
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
		
		mp.clickTransmisionDatos();//mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		mp.clickReportePromociones();
		validacion = rsp.validarNombrePantalla();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza lo siguiente para el filtro que se indique:\n" +
				"*Plaza: <Seleccione una opci�n>, el usuario No selecciona \n" +
				"Dar clic en [Consultar]\n" +
				"Los filtros que deben marcar aviso para informaci�n faltante, son los siguientes:\r\n" + 
				"*Plaza: 'Estimado usuario, favor de seleccionar una Plaza en la secci�n de filtros'");

		
		rsp.clickConsultar();
		validacion = rsp.validarMensaje(msg1);
		assertTrue(validacion, "No se muestran el mensaje de error: "+msg1);

		/**
		 *                                             PASO 6
		 ******************************************************************************************/

		addStep("El usuario Selecciona una Plaza y da clic en [Consultar]\n");
		rsp.filtrosReporteSeguimiento(plaza, data.get("FechaInicio"), data.get("FechaFin"));
		rsp.clickConsultar();
		
		validacion = rsp.validarExistanResultados();
		//assertTrue(validacion, "No se muestran resultados en el grid");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se valida que se muestren los mensajes de error para filtros obligatorios que falten de captura o selecci�n de un valor con usuario plaza o megaplaza asociado a un perfil SIMOTI: OFICINA CENTRAL.";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarMensajesDeError_test";
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