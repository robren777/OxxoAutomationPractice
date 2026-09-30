package WEB.rst_bbva_mvp3.script;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.rst_bbva_mvp3.om.portal_AccessControl;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Validar que en la pestana de retiros con tarjeta este habilitada y que se puedan seleccionar los site de quererato y monterrey asi tambien los switch default y femco 
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-009-Validar que la pestana de retiros con tarjeta  se encuentre habilitada
 * 
 * PREREQUISITOS: Contar con acceso al portal Contar con las credenciales del
 * portal Contar con las credenciales de la BD
 * 
 * INSUMOS: URL del portal y sus credenciales
 * 
 * @author Melba Solis
 * @date 03/04/2025
 */

public class ATC_FT_009_ValidarquelaPestanadeRetirosconTarjetaseEncuentreHabilitada extends BaseExecution {

//@AzureTestInfo(
//			areaPath = "Test-POCFrameworkOxxo\\test1",
//			iterationPath = "Test-POCFrameworkOxxo\\Iteration 1",
//			tags = "TestTag1;TestTag2",
//			userStoryId = 17086   //)

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidarquelaPestanadeRetirosconTarjetaseEncuentreHabilitada_test(HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("ps"));
		String url = data.get("server");
		String correo = data.get("correo");

		u = new SeleniumUtil(new ChromeTest(), true);
		u.getDriver().manage().window().maximize();
		portal_AccessControl pac = new portal_AccessControl(u, testCase);

		/** Comienza el script *****/
		addStep("Ingresar al Portal Access Control e ingresar las credenciales"); // viene
		u.get(url);
		assertTrue(pac.LoginPortal(user, password), "El login no fue exitoso después de 3 intentos.");

		addStep("Autenticarse posterior de ingresar a la página");
		assertTrue(pac.autenticar(correo, password), "La autenticación falló en algún paso");

		addStep("Ingresar al sistema de Monitoreo de Tiendas");
		assertTrue(pac.SistemadeMonitoreo(), "No se ingreso al menú Sistema de Monitoreo de Tiendas correctamente");

		addStep("Ingresar a Ruteo TDC");
		assertTrue(pac.TransaccionesElectronicas(), "No se ingreso a Ruteo TDC correctamente");

		addStep("Se da clic en el Site");
		assertTrue(pac.SeleccionSite(),"No se selecciona correctamente el site");
		
		addStep("Se selecciona el Site Queretaro");
		assertTrue(pac.SeleccionSiteQRO(),"No se selecciona el site correctamente o no se encuentra disponible");
		
		addStep("Se selecciona la Operación");
		assertTrue(pac.SeleccionarOperacion(),"No se selecciono la operacion correctamente");
		
		addStep("Se selecciona Operación RCT");
		assertTrue(pac.SeleccionarOperacionComprasRCT(),"No se selecciona la operacion RCT correctamente o no se encuentra disponible");
		
		addStep("Se da clic en seleccion de Banco");
		assertTrue(pac.SeleccionarBanco(),"No se selecciono el banco correctamente");
		
		addStep("Se selecciona el Banco Bbva");
		assertTrue(pac.SeleccionarBancoBbva(),"No se selecciono el banco correctamente o no se encuentra disponible");
		
		addStep("Se da clic en seleccion Switch");
		assertTrue(pac.SeleccionarelSwitch(),"No se selecciono el Switch correctamente");
		
		addStep("Se selecciona Switch FEMCO_BBVA");
		assertTrue(pac.SeleccionarelSwitchBbva(),"No se selecciono el Switch correctamente o no se encuentra disponible");
		
		addStep("Se selecciona una Plaza");
		assertTrue(pac.SeleccionarFilaL8(),"No se selecciono la plaza correctamente");
		
		addStep("Se da clic en actualizar para modificar el Switch");
		assertTrue(pac.Actualizar(),"No se actualizo correctamente");
		
		addStep("Se cancela la modificion del Switch");
		assertTrue(pac.CancelarSwitch(),"No se cancelo correctamente");
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con acceso al portal \r\n"
				+ "2. Contar con las credenciales del portal \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_ValidarquelaPestanadeRetirosconTarjetaseEncuentreHabilitada_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en la pestana de retiros con tarjeta este habilitada y que se puedan seleccionar los site de quererato y monterrey asi tambien los switch default y femco ";
	}
}
