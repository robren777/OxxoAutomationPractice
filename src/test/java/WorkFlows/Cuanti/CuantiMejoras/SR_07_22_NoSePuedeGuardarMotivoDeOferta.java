package WorkFlows.Cuanti.CuantiMejoras;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiMecanica;
import WorkFlows.util.Cuanti.UtilsMecanicas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class SR_07_22_NoSePuedeGuardarMotivoDeOferta extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar que el sistema permita guardar un motivo de oferta nuevo.
	 * @param data --> data provider que contiene la url del portal de ultimus y el motivo de oferta.
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_22_NoSePuedeGuardarMotivoDeOferta(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_ADMIN;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);		
		PomCuantiMecanica pom = new PomCuantiMecanica(u, userUltimus, userCuanti, passCuanti, "", testCase);
		UtilsMecanicas utils = new UtilsMecanicas();
		
		//Antes de iniciar la prueba verifica que no exita el motivo de oferta, en el caso que exista lo elimina
		if(utils.existNombreMotivo(this, data.get("motivo-oferta"))) {
			utils.deleteNombreMotivo(this, data.get("motivo-oferta"));
		}		
		
		try {
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("Acceder a Ultimus con la siguiente liga:\r\n"
					+ "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx");
			pom.deleteCookies();
			u.get(data.get("url"));
			testCase.addScreenShotCurrentStep(u,"Se muestra la pagina Ultimus correctamente");
			assertTrue(pom.validateAccessLoginUltimus(), "No se muestra la pagina de Ultimus");
			
			// Step 2.-  /////////////////////////////////////////////////////////
			addStep("Ingresar con usuario Administrador (6006063) y dar clic en el boton Aceptar");
			pom.loginUltimus();
			testCase.addScreenShotCurrentStep(u,"Se inicia se sesion correctamente");
			assertTrue(pom.validateAccessUltimus(), "No se pudo iniciar sesion en Ultimus");
			
			// Step 3.-  /////////////////////////////////////////////////////////
			addStep("Dar clic en la opcion: \"Iniciar\"");
			pom.selectIniciar();
			testCase.addScreenShotCurrentStep(u,"Se muestran las aplicaciones disponibles para el usuario");
			assertTrue(pom.validateUserAdminAppsUltimus(), "No se muestran las aplicaciones disponibles para el usuario");
			
			// Step 4.-  /////////////////////////////////////////////////////////
			addStep("Dar doble clic en la opcion: \"PromoxxoClone\"");
			pom.accessPromoxxoClone();
			pom.logCuanti();
			testCase.addScreenShotCurrentStep(u,"Se despliega la pantalla de CUANTI");
			assertTrue(pom.validateAccessPromoxxoClone(), "No se puedo acceder a la pagina de Cunati");
			
			// Step 5.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar del menu la opcion Administracion > Motivos de oferta");
			pom.selectNuevoCuanti();
			pom.accessMotivosDeOferta();
			testCase.addScreenShotCurrentStep(u,"Se abre la pantalla de Administracion de motivos de oferta");
			assertTrue(pom.validateAccessMotivosDeOferta(), "No se puedo acceder a la pantalla de Administracion de motivos de oferta");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Capturar un nombre para un motivo de oferta");
			pom.capturaNombreMotivo(data.get("motivo-oferta"));
			testCase.addScreenShotCurrentStep(u,"El campo permite la captura");
			assertTrue(pom.validateCapturaNombreMotivo(), "El campo no permitio la captura del nombre");
			
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Hacer clic en el boton 'Guardar'");
			pom.guardarMotivo();
			testCase.addScreenShotCurrentStep(u,"Se crea correctamente el motivo de oferta ");
			assertTrue(pom.validateGuardarMotivo(), "No se pudo crear la oferta");
			pom.clickAceptarGuardado();
			
			u.close();
			
		}catch(Exception ex) {
			testCase.addScreenShotCurrentStep(u, ex.getMessage()); 
			assertTrue(false, ex.getMessage());			
		}		
	}	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que el sistema permita guardar un motivo de oferta nuevo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_22_NoSePuedeGuardarMotivoDeOferta";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
