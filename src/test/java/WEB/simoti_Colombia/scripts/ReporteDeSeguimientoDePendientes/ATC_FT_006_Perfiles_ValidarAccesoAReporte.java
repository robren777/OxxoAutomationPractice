package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertFalse;
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
/**Desc: Validar que no se muestren el submen� Reporte Promociones con los siguientes perfiles
 * Cedis
 * Operador
 * Consulta
 * Administrador
 * Seguridad
 * Operaci�n
 * Caja Inteligente
 * MTC-FT-006 Perfiles_Reporte de Seguimiento de Pendientes_Validar Acceso a Reporte
 * Nota. Estan construidos todos los pasos pero no se ha probado con todos los perfiles
 * @author Andrea Contreras
 * @date 3 Marzo 2023
 */
public class ATC_FT_006_Perfiles_ValidarAccesoAReporte extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Perfiles_ValidarAccesoAReporte_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String userCedis = data.get("UserCedis");
		String passwordCedis = PasswordUtil.decryptPassword(data.get("PasswordCedis"));
		String userOperador = data.get("UserOperador");
		String passwordOperador = PasswordUtil.decryptPassword(data.get("PasswordOperador"));
		String userConsulta = data.get("UserConsulta");
		String passwordConsulta = PasswordUtil.decryptPassword(data.get("PasswordConsulta"));
		String userAdministrador = data.get("UserAdministrador");
		String passwordAdministrador = PasswordUtil.decryptPassword(data.get("PasswordAdministrador"));
		String userSeguridad = data.get("UserSeguridad");
		String passwordSeguridad = PasswordUtil.decryptPassword(data.get("PasswordSeguridad"));
		String userOperacion = data.get("UserOperacion");
		String passwordOperacion = PasswordUtil.decryptPassword(data.get("PasswordOperacion"));
		String userCaja = data.get("UserCaja");
		String passwordCaja = PasswordUtil.decryptPassword(data.get("PasswordCaja"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.getDriver().manage().window().maximize();	
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.cicloLogin(userCedis, passwordCedis);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI. El sistema muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transmisi�n de Datos \r\n" + 
				"- Integridad de Datos\r\n" + 
				"- �rdenes de Compra\r\n" + 
				"- Folio de Pago\r\n" + 
				"- Transacciones Electr�nicas \r\n" + 
				"El sistema deber� otorgar al usuario permisos para visualizar solo los reportes que corresponden a su perfil.");
		
		pa.clickSimotiColombia();
		
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Desplejar el men� Transmisi�n de Datos y validar que no se muestra el reporte: Reporte Promociones");
		
		mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		validacion = mp.validarReportePromociones();
		assertFalse(validacion, "Se muestra en pantalla Reporte de promociones");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Operador e iniciar sesi�n");

		pa.logoutSimoti();// no esta funcionando la opcion de cerrar sesion de simoti col
		u.get(url);
		pa.cicloLogin(userOperador, passwordOperador);
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transacciones Electr�nicas");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuOperador = mp.validarMenuUsuariosTE();	
		assertTrue(validarMenuOperador, "No se muestran los menus especificados");		
		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Consulta e iniciar sesi�n");

		pa.logoutSimoti();
		u.get(url);
		pa.cicloLogin(userConsulta, passwordConsulta);
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transacciones Electr�nicas");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuConsulta = mp.validarMenuUsuariosTE();	
		assertTrue(validarMenuConsulta, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Administrador e iniciar sesi�n");

		pa.logoutSimoti();
		u.get(url);
		pa.cicloLogin(userAdministrador, passwordAdministrador);
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transacciones Electr�nicas");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuAdministrador = mp.validarMenuUsuariosTE();	
		assertTrue(validarMenuAdministrador, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Seguridad e iniciar sesi�n");

		pa.logoutSimoti();
		u.get(url);
		pa.cicloLogin(userSeguridad, passwordSeguridad);
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transacciones Electr�nicas");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuSeguridad = mp.validarMenuUsuariosTE();	
		assertTrue(validarMenuSeguridad, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Operaci�n e iniciar sesi�n");

		pa.logoutSimoti();
		u.get(url);
		pa.cicloLogin(userOperacion, passwordOperacion);
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Transacciones Electr�nicas");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuOperacion = mp.validarMenuUsuariosTE();	
		assertTrue(validarMenuOperacion, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		
		addStep("Salir e ingresar usuario y contrase�a de un usuario Caja e iniciar sesi�n");

		pa.logoutSimoti();
		u.get(url);
		pa.cicloLogin(userCaja, passwordCaja);
		
		/**
		 *                                             PASO 16
		 ******************************************************************************************/
		
		addStep("Dar clic en opci�n de SIMOTI. El sistema solo muestra la aplicaci�n SIMOTI con el siguiente men�:\r\n" + 
				"- Caja Inteligente");
		
		pa.clickSimotiColombia();
		
		boolean validarMenuCajaInt = mp.validarMenuUsuarioCajaInteligente();	
		assertTrue(validarMenuCajaInt, "No se muestran los menus especificados");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que no se muestren el submen� Reporte Promociones con los siguientes perfiles\r\n" + 
				"Cedis\r\n" + 
				"Operador\r\n" + 
				"Consulta\r\n" + 
				"Administrador\r\n" + 
				"Seguridad\r\n" + 
				"Operaci�n\r\n" + 
				"Caja Inteligente";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Perfiles_ValidarAccesoAReporte_test";
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
