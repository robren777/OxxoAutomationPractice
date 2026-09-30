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
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc:Validar que se desplieguen todos los valores y nomenclatura adecuados para los campos de 
 * filtros con usuario plaza asociado a un perfil de acceso a SIMOTI: PLAZA.
* MTC_FT_002. Validar Filtros plaza
* @author Marichuy Due�as/ Bernardo Moya
* @date Octubre 2022
*/
public class ATC_FT_002_Validar_Filtros_plaza_PLAZA extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_Filtros_plaza_PLAZA_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiChile mp = new MenuPrincipal_SimotiChile(u, testCase);
		IndicadoresPorTotales_SimotiChile it = new IndicadoresPorTotales_SimotiChile (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
		System.out.println("url: " + url);
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrasena y da clic en el boton Iniciar Sesion.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opcion de SIMOTI.");
		
		pa.clickSimotiChile();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el menu: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"<<<Filtro Plaza>>>");

		boolean validarPlaza = it.validarFiltroPlaza();
		assertTrue(validarPlaza, "No se muestra el filtro de plaza");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"Mes, aplique lo siguiente:\r\n" + 
				"1.- Por default deberia estar seleccionado el mes actual.\r\n" + 
				"2.- Se deberian enlistar todos los meses del anio.");
		
		validacion = it.validarFiltroMes();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"A�o, aplique lo siguiente:\r\n" + 
				"1.- Se debe mostrar el anio actual por default.\r\n" + 
				"2.- Se debe permitir consultar un anio, \r\n" + 
				"3.- Los anios a mostrar deberian ser los correspondientes desde el anio 2010 hasta la fecha actual.");
		
		validacion = it.validarFiltroAnio();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mayor a mes actual>" + 
				"*Anio: <anio actual>. El sistema aplica la validacion:" + 
				"'Estimado usuario no se permite la seleccion de una fecha posterior a la actual'");
				
		validacion = it.selectMesMayor();
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el anio proporcionado");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mes>" + 
				"*Anio: <anio>. El sistema permite consultar informacion desde el anio.");
		
		
		if(data.get("Plaza")!=null) {
			validacion = it.selectPlaza(data.get("Plaza"));
			assertTrue(validacion, "No se puede seleccionar la plaza");
		}
		
		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el anio proporcionado");
		
		it.clickConsultarBtn();
		mp.waitLoading();
		
		boolean valResults = it.existenResultados();
		
		if(valResults == true) {
			validacion = true;
		}
		
		assertTrue(validacion, "El sistema no permite consultar informacion");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_002_Validar_Filtros_plaza_PLAZA";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_Filtros_plaza_PLAZA_test";
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
		//u.getDriver().quit();
	}
}