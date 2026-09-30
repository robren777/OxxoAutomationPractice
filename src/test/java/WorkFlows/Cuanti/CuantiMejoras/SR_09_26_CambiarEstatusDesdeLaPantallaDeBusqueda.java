package WorkFlows.Cuanti.CuantiMejoras;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiMecanica;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class SR_09_26_CambiarEstatusDesdeLaPantallaDeBusqueda extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar que el usuario Categoria pueda realizar el cambio de estatus individual de Borrador a Enviada de promocion Comunicacion de imagen.
	 * @param data --> data provider que contiene la url del portal de ultimus y el tipo de folio necesario para la prueba
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_26_CambiarEstatusDesdeLaPantallaDeBusqueda(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_CATEGORIA;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);		
		PomCuantiMecanica pom = new PomCuantiMecanica(u, userUltimus, userCuanti, passCuanti, "", testCase);
		
		
		try {
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("Acceder a Ultimus con la siguiente liga:\r\n"
					+ "http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx");
			pom.deleteCookies();
			u.get(data.get("url"));
			testCase.addScreenShotCurrentStep(u,"Se muestra la pagina Ultimus correctamente");
			assertTrue(pom.validateAccessLoginUltimus(), "No se muestra la pagina de Ultimus");
			
			// Step 2.-  /////////////////////////////////////////////////////////
			addStep("Ingresar con usuario Categoria (6004860) y dar clic en el boton Aceptar");
			pom.loginUltimus();
			testCase.addScreenShotCurrentStep(u,"Se inicia se sesion correctamente");
			assertTrue(pom.validateAccessUltimusCategoria(), "No se pudo iniciar sesion en Ultimus");
			
			// Step 3.-  /////////////////////////////////////////////////////////
			addStep("Dar clic en la opcion: \"Iniciar\"");
			testCase.addScreenShotCurrentStep(u,"Se muestran las aplicaciones disponibles para el usuario");
			assertTrue(pom.validateUserAdminAppsUltimusCategoria(), "No se muestran las aplicaciones disponibles para el usuario");
			
			// Step 4.-  /////////////////////////////////////////////////////////
			addStep("Dar doble clic en la opcion: \"PromoxxoClone\"");
			pom.accessPromoxxoClone();
			pom.logCuanti();
			testCase.addScreenShotCurrentStep(u,"Se despliega la pantalla de CUANTI");
			assertTrue(pom.validateAccessPromoxxoClone(), "No se puedo acceder a la pagina de Cunati");
			pom.selectNuevoCuanti();
			
			// Step 5.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar del menu la opcion Promociones > CUANTI - BUSCAR");
			pom.accessBuscarPromocionCategoria();
			testCase.addScreenShotCurrentStep(u, "Se abre la pantalla de Busqueda de promociones");
			assertTrue(pom.validateAccessBusquedaPromo(), "Fallo el acceso a \"Buscar promocion\"");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Capturar el folio de la promocion Comunicacion de imagen en el filtro correspondiente y hacer clic en el boton Buscar");
			pom.buscarFolioPorFilrtros(data.get("tipoFolio"));
			testCase.addScreenShotCurrentStep(u,"La promocion se muestra en el resultado de busqueda");
			assertTrue(pom.validateBusquedaFolioPromocionPorFiltro(), "No se encontro la promocion");
			
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar la opcion Enviada del campo Estatus");
			pom.cambioEstatusCategoria();
			testCase.addScreenShotCurrentStep(u,"El sistema permite realizar el campo de estatus de la promocion");
			assertTrue(pom.validateSelectEstatus(), "No permite seleccionar la opcion Enviada del campo Estatus");

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
		return "Tener una promocion Comunicacion de imagen en estatus borrador con todos los campos capturados";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que el usuario Categoria pueda realizar el cambio de estatus individual de Borrador a Enviada de promocion Comunicacion de imagen";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_26_CambiarEstatusDesdeLaPantallaDeBusqueda";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
