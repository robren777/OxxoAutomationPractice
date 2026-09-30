package WEB.cnt.scripts;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Carga_Masiva_del_Segmento_Tienda;
import WEB.cnt.om.Login;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_342_Generar_Reporte_de_CNT_para_confirmar_carga_recien_realizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_342_Generar_Reporte_de_CNT_para_confirmar_carga_recien_realizada_test(
			HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new ChromeTest(), true); 
		Login l = new Login(u, testCase); 
		Carga_Masiva_del_Segmento_Tienda cm = new Carga_Masiva_del_Segmento_Tienda(u, testCase);
		PortalApps pa = new PortalApps(u, testCase);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String user2 = data.get("User2");
		String password2 = PasswordUtil.decryptPassword(data.get("Password2"));
		
		String PlazaE = data.get("PlazaE");
		String URL = data.get("Url");
		Boolean validacion = false;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/

		addStep("Ingresar a la " + URL + "del portal de aplicaciones");
		
		u.get(data.get("Url")); 
		u.hardWait(3); 
		testCase.addScreenShotCurrentStep(u, "pantallaLogin");

		/**
		 *                                             PASO 2
		 ******************************************************************************************/

		addStep("Aparece la pantalla de Loggin");

		testCase.addScreenShotCurrentStep(u, "pantallaLogin");

		/**
		 *                                             PASO 3
		 ******************************************************************************************/

		addStep("Ingresar el usuario " + user + " y contrase�a " + password + " para despu�s seleccionar "
				+ "el bot�n de Continuar"); 

		/*l.logginInventarioEspacios(user, password);

		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Se abre la aplicaci�n donde se muestra la pantalla de Tiendas-Femsa con la el Combo "
				+ "de Plaza y el bot�n de Continuar");
		
		/*testCase.addScreenShotCurrentStep(u, "pantallaTiendasFemsa");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		
		/*l.clickSeleccionarPlaza(PlazaE);
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El sistema desplegara las opciones de Distrito y Tiendas que est�n relacionadas con "
				+ "la Plaza seleccionada por el usuario."); 
		
		/*testCase.addScreenShotCurrentStep(u, "pantallaDistritoTiendas");

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n \"Carga Masiva del Segmento Tienda\".");
		
		/*l.clickCargaMasivaSegmentoTienda();
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
	
		addStep("El sistema mostrara el mensaje de �La informaci�n fue actualizada correctamente. "
				+ "Total de registros actualizados: �N� � Donde N corresponde a la cantidad de registros que se cargaron.");
		
		/*validacion = cm.pantallaCargaMasivaSegTienda();
		assertTrue(validacion, "No se muestra la pantalla Carga Masiva del Segmento Tienda");
		cm.cargarArchivo("Prueba de Carga - 341.csv");
		validacion = cm.alerta();
		assertTrue(validacion, "No se puede realizar la carga");
		u.waitForLoadPage();
		validacion = cm.validarMensajeCorrecto("La informaci�n fue actualizada correctamente. Total de registros actualizados:");
		assertTrue(validacion, "No se muestra correctamente el mensaje");
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Se ingresar a la 'https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf' "
				+ "del portal de aplicaciones para acceder a la Generaci�n de Reporte de CNT");
		
		u.getDriver().navigate().to(pa.getUrl());
		u.hardWait(3);
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Aparece la pantalla de Loggin");
		
		pa.proceedToPortal();
		testCase.addScreenShotCurrentStep(u, "Loggin");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Ingresar el usuario '6005105' y contrase�a correspondiente para despu�s seleccionar el bot�n de Iniciar Sesi�n");
		
		//pa.logginInventarioEspacios(user2, password2);
		pa.cicloLogin(user2, password2);
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Se abre la aplicaci�n donde se muestran los accesos que puede entrar el usuario, se "
				+ "mostrara la opci�n de \"Portal de Aplicaciones\"");
		
		//validacion = pa.accesoCorrectoPortal(user2);
		//assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"Reportes Tiendas\"");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		/**
		 *                                             PASO 
		 ******************************************************************************************/
		
		addStep("");
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_342_Generar_Reporte_de_CNT_para_confirmar_carga_recien_realizada";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_342_Generar_Reporte_de_CNT_para_confirmar_carga_recien_realizada_test";
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
