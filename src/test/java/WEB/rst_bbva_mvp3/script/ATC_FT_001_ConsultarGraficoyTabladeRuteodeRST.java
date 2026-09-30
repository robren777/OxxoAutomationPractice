package WEB.rst_bbva_mvp3.script;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.rst_bbva_mvp3.om.portal_AccessControl;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
//import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Ingresar a la plataforma Access Control para consultar el
 * grafico de la tabla ruteo RST
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-001-Consultar grafico y
 * tabla de ruteo de RST
 * 
 * PREREQUISITOS: Contar con acceso al portal Contar con las credenciales del
 * portal Contar con las credenciales de la BD
 * 
 * INSUMOS: URL del portal y sus credenciales
 * 
 * @author Melba Solis
 * @date 28/03/2025
 */

public class ATC_FT_001_ConsultarGraficoyTabladeRuteodeRST extends BaseExecution {

//@AzureTestInfo(
//			areaPath = "Test-POCFrameworkOxxo\\test1",
//			iterationPath = "Test-POCFrameworkOxxo\\Iteration 1",
//			tags = "TestTag1;TestTag2",
//			userStoryId = 17086   //)

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ConsultarGraficoyTabladeRuteodeRST_test(HashMap<String, String> data) throws Exception {

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
		
		addStep("Se selecciona Operación");
		assertTrue(pac.SeleccionarOperacion(),"No se selecciono la operacion correctamente");
		
		addStep("Se selecciona la Operación RST");
		assertTrue(pac.SeleccionarOperacionComprasRST(),"No se selecciona la operacion COMPRAS correctamente o no se encuentra disponible");
		
		addStep("Se da clic en seleccion de Banco");
		assertTrue(pac.SeleccionarBanco(),"No se selecciono el banco correctamente");
		
		addStep("Se selecciona el Banco Bbva");
		assertTrue(pac.SeleccionarBancoBbva(),"No se selecciono el banco correctamente o no se encuentra disponible");
		
		addStep("Se da clic en seleccion Switch");
		assertTrue(pac.SeleccionarelSwitch(),"No se selecciono el Switch correctamente");
		
		addStep("Se selecciona Switch FEMCO_BBVA");
		assertTrue(pac.SeleccionarelSwitchBbva(),"No se selecciono el Switch correctamente o no se encuentra disponible");
		
		addStep("Se selecciona la Plaza");
		assertTrue(pac.SeleccionarFilaL8(),"No se selecciono la plaza correctamente");
		
		addStep("Se resalta la plaza de la linea seleccionada");
		assertTrue(pac.SeleccionarPlaza8(),"No se marco la plaza correctamente");
		
		addStep("Se da clic en actualizar para modificar el Switch");
		assertTrue(pac.Actualizar(),"No se actualizo correctamente");
		
		addStep("Se actualiza/confirma la modificion del Switch");
		assertTrue(pac.ConfirmarSwitch(),"No se actualizo correctamente");
		
		addStep("Se resalta la plaza de la linea seleccionada");
		assertTrue(pac.SeleccionarFila31(),"No se marco la plaza correctamente");
		
		// Llamar al método compararPlaza y pasar plazaText como argumento
		addStep("Se resalta plaza de la fila 31 y comparamos para confirmar su actualización");
		assertTrue(pac.compararPlaza31(), "Las plazas no coinciden");
				
		addStep("Se resalta el switch de la linea seleccionada");
		assertTrue(pac.validarCambiobbva31(),"El switch no coincide con el esperado");
		
		//Regresamos la fila a su estado inicial 
		addStep("Se da clic en seleccion Switch");
		assertTrue(pac.SeleccionarelSwitch(),"No se selecciono el Switch correctamente");
		
		addStep("Se selecciona Switch DEFAULT");
		assertTrue(pac.SeleccionarelSwitchDefault(),"No se selecciono el Switch correctamente o no se encuentra disponible");
		
		addStep("Se da clic en actualizar para modificar el Switch");
		assertTrue(pac.Actualizar(),"No se actualizo correctamente");
		
		addStep("Se ctualiza/confirma la modificion del Switch");
		assertTrue(pac.ConfirmarSwitch(),"No se cancelo correctamente");
	}  

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con acceso al portal \r\n" 
				+ "2. Contar con las credenciales del portal \r\n"
				+ "3. Contar con las credenciales de la BD \r\n" ;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ConsultarGraficoyTabladeRuteodeRST_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ingresar a la plataforma Access Control para consultar el grafico de la tabla ruteo RST";
	}
}
