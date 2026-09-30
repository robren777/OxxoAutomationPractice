package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;
/**Desc: Validar que se desplieguen todos los valores y nomenclatura adecuados para los campos de 
* filtros con usuario plaza asociado a un perfil de acceso a SIMOTI: PLAZA.
* MTC_FT_196.- Indicadores por Totales_Validar Filtros_plaza_PLAZA
* @author Herbe Cauich
* @date Octubre 2022
*/
public class ATC_FT_196_Indicadores_por_Totales_Validar_Filtros_Plaza_Plaza extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_196_Indicadores_por_Totales_Validar_Filtros_Plaza_Plaza_test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		/*
		 * Utilerías
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		String mes = data.get("mes");	
		String anio = data.get("anio");	
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		


		 /**
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones e Ingresar el usuario, password y dar clic en entrar"); // viene //
																											// excel
		u.get(url); 
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal(); // es para lo de la pagina no segura
		
		 /**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickPortalSIMOTI_PERU();
		 /**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario despliega el menú: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores por Totales.");
		it.MAX();
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:" + 
				"<<<Filtro Plaza>>>");

		boolean validarPlaza = it.validarFiltroPlaza();
		assertTrue(validarPlaza, "No se muestra el filtro de plaza");		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:" + 
				"Mes, aplique lo siguiente:" + 
				"1.- Por default deber� est�r seleccionado el mes actual." + 
				"2.- Se deber�n enlistar todos los meses del a�o.");
		
		validacion = it.validarFiltroMes();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:" + 
				"A�o, aplique lo siguiente:" + 
				"1.- Se debe mostrar el a�o actual por default." + 
				"2.- Se debe permitir consultar un a�o," + 
				"3.- Los a�os a mostrar deber�n ser los correspondientes desde el a�o 2010 hasta la fecha actual.");
		
		validacion = it.validarFiltroAnio();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores:" + 
				"*Mes: <mayor a mes actual>" + 
				"*A�o: <2016>");
		it.selectMesMayor();
		it.selectAnio("2016");
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("El usuario selecciona los siguientes valores:" + 
				"*Mes: <Enero>" + 
				"*A�o: <2010> " + 
				"Da clic en [Consultar]");
		it.selectMes("Enero");
		it.selectAnio("2010");
		it.clickBtnConsultar();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_196_Indicadores_por_Totales_Validar_Filtros_Plaza_Plaza_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_196_Indicadores_por_Totales_Validar_Filtros_Plaza_Plaza";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
