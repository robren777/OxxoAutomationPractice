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

public class SR_09_43_CambiarEstatusDesdeLaPantallaDeBusqueda extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar que el usuario Administrador pueda realizar el cambio de estatus multiple de Borrador a Enviada de promocion simple.
	 * @param data --> data provider que contiene la url del portal de ultimus y el tipo de folio necesario para la prueba
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_43_CambiarEstatusDesdeLaPantallaDeBusqueda(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_ADMIN;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		String folios;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);		
		folios = new UtilsMecanicas().getFolios(this, data.get("tipoFolio"));
		PomCuantiMecanica pom = new PomCuantiMecanica(u, userUltimus, userCuanti, passCuanti, folios, testCase);
		
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
			pom.selectNuevoCuanti();
			
			// Step 5.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar del menu la opcion Promociones > CUANTI - BUSCAR");
			pom.accessBuscarPromocion();
			testCase.addScreenShotCurrentStep(u, "Se abre la pantalla de Busqueda de promociones");
			assertTrue(pom.validateAccessBusquedaPromo(), "Fallo el acceso a \"Buscar promocion\"");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Capturar los tres folios separados por comas de las promociones Promopack Servicios en el filtro correspondiente y hacer clic en el boton Buscar");
			pom.buscarFolio();
			testCase.addScreenShotCurrentStep(u,"La promocion se muestra en el resultado de busqueda");
			assertTrue(pom.validateBusquedaFolioPromocionPorFiltro(), "No se encontro la promocion");
			
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar la opcion Enviada del campo Estatus");
			pom.cambioEstatusMultiple();
			testCase.addScreenShotCurrentStep(u,"El sistema permite realizar el campo de estatus de la promocion");
			assertTrue(pom.validateSelectEstatus(), "No permite seleccionar la opcion Enviada del campo Estatus");
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
		return "Tener tres promociones Simple en estatus borrador con todos los campos capturados";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que el usuario Administrador pueda realizar el cambio de estatus multiple de Borrador a Enviada de promocion simple";
	}	

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_43_CambiarEstatusDesdeLaPantallaDeBusqueda";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}