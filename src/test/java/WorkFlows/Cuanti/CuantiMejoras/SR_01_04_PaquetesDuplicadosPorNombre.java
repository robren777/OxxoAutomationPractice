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

public class SR_01_04_PaquetesDuplicadosPorNombre extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Borrar un componente que se encuentra en uso para validar que no se permite su eliminacion en las tablas XXPRO_COMPONENTES_CV, XXPRO_PAQUETES_CV y XXPRO_PAQUETE_COMPONENTES_CV
	 * @param data --> data provider que contiene la url del portal de ultimus
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_PaquetesDuplicadosPorNombre(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		String componente;
		String componenteID;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);	
		componenteID = new UtilsMecanicas().getIdComponente(this);
		componente = new UtilsMecanicas().getDscComponente(this, componenteID);
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
			addStep("Seleccionar del menu la opcion Administracion > Paquetes Promocionales");
			pom.accessPaquetesPromocionales();
			testCase.addScreenShotCurrentStep(u, "Se abre la pantalla de Administracion de paquetes y componentes");
			assertTrue(pom.validateAccessAdminPaquetes(), "No se pudo acceder a la pantalla de Administracion de paquetes y componentes");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar la pestaña Componentes");
			pom.selectComponentes();
			testCase.addScreenShotCurrentStep(u,"Se muestra la tabla de los Componentes");
			assertTrue(pom.validateAccessComponents(), "No se muestra la tabla de los componentes");
			
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Seleccionar un Componente y hacer clic en el icono Eliminar (Bote de basura)");
			pom.buscaComponente(componente);
			testCase.addScreenShotCurrentStep(u,"Se muestra un mensaje confirmando el borrado del componente");
			assertTrue(pom.validateBotonConfirmBorrado(), "No muestra el mensaje de la confirmacion del borrado");
			
			// Step 8.-  /////////////////////////////////////////////////////////
			addStep("Aceptar el mensaje");
			pom.clickConfirmBorrado();
			testCase.addScreenShotCurrentStep(u,"Se elimina el registro de la tabla de Componentes");
			assertTrue(pom.validateMsnConfirmBorrados(), "No se elimina el registro de la tabla de componentes");
			pom.clickAceptarBorrado();
			
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
		return "Revisar los componentes actuales para validar que se tenga relacion en las tablas XXPRO_COMPONENTES_CV, XXPRO_PAQUETES_CV y XXPRO_PAQUETE_COMPONENTES_CV\r\n"
				+ "Estar en un Evento Activo y tener al menos una promocion en estatus enviada utilizando un Tipo de paquete promocional con el componente a eliminar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Borrar un componente que se encuentra en uso para validar que no se permite su eliminacion en las tablas XXPRO_COMPONENTES_CV, XXPRO_PAQUETES_CV y XXPRO_PAQUETE_COMPONENTES_CV";
	}	

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_04_PaquetesDuplicadosPorNombre";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
