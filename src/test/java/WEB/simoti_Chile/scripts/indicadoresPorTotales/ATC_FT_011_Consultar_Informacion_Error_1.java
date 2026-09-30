package WEB.simoti_Chile.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Chile.om.IndicadoresPorTotales_SimotiChile;
import WEB.simoti_Chile.om.MenuPrincipal_SimotiChile;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc:Validar que se  exporte excel sin datos
* 205.- Indicadores por Totales_consultar_informaci�n_error_1
* @author Marichuy Due�as/Yair L
* @date Noviembre 2022
*/
public class ATC_FT_011_Consultar_Informacion_Error_1 extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Consultar_Informacion_Error_1_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new MEdgeTest(), true);	
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiChile mp = new MenuPrincipal_SimotiChile(u, testCase);
		IndicadoresPorTotales_SimotiChile it = new IndicadoresPorTotales_SimotiChile (u, testCase);
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
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiChile();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros: " + 
				"Plaza: <se muestra la Plaza del usuario> " + 
				"Mes: <seleccionar valor> " + 
				"A�o: <Seleccionar valor> " + 
				"Dar clic en [Consultar]. " +
				"El sistema est� procesando la consulta, detecta que no hay conexi�n a la BD y muestra el mensaje: " + 
				"'Ha ocurrido un error al consultar la informaci�n del reporte, favor de reportar al �rea de soporte'");

		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		mp.waitLoading();
				
		boolean validacionMsj = it.validarMensajeError();
		assertTrue(validacionMsj, "No se mostro el mensaje 'Ha ocurrido un error al consultar la informaci�n del reporte, favor de reportar al �rea de soporte'");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_011_Consultar_Informacion_Error_1";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_Consultar_Informacion_Error_1_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Solicitar configuracion al equipo de Soporte para mostrar error de conexi�n a la BD o un time out al consultar informaci�n";
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
