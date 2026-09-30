package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiMecanica;
import WorkFlows.util.Cuanti.UtilsMecanicas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_007_ValidarCambioDeMecanicaPromopackArtDescuento extends BaseExecution {
	
	String tipoFolio;
	String tipoCambio; 
	String prueba;
	
	/**
	 * @author Jose Zarate
	 * Validar como categoria la edicion, clonacion y cambio de mecanica de una promocion Simple - Descuento que ya esta guardada pueda ser cambiada a una promocion Promopack - art = descuento art2
	 * @param data - data provider que contiene la url, folio y accion a realiar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarCambioDeMecanicaPromopackArtDescuento(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> Cuanti");
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_CATEGORIA;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		String folio;
		
		tipoFolio = data.get("tipoFolio");
		tipoCambio = data.get("tipoCambio");
		prueba = data.get("prueba");
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url"));	

		folio = new UtilsMecanicas().getFolio(this, tipoFolio);
		PomCuantiMecanica pom = new PomCuantiMecanica(u, userUltimus, userCuanti, passCuanti, folio, testCase);
		
		testCase.setTest_Description("Validar como categoria la "+prueba+" y cambio de mecanica de una promocion "+tipoFolio+" que ya esta guardada pueda ser cambiada a una promocion "+tipoCambio);
		
		try {
			/////////////////////////////////////////////////////////////////////////////////////////
			pom.accessCuanti(this, data.get("accion"));
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Ingresar el folio a buscar");
			pom.buscarFolio();		
			testCase.addScreenShotCurrentStep(u, "Se muestra el resultado con el folio correcto");
			assertTrue(pom.validateBusquedaFolioPromocion(), "Fallo la busqueda del folio");
			
			if(prueba.contains("edicion")) {
				/////////////////////////////////////////////////////////////////////////////////////////
				addStep("Dar click en Editar y validar que se conserven y sean editables los datos del apartado de caracteristicas de la promocion y medios de pago, asi como la variedad y cobertura, se limpian los campos de la mecanica y metodo");
				pom.selectEditPromo();
			}else {
				/////////////////////////////////////////////////////////////////////////////////////////
				addStep("Dar click en Clonar y validar que se conserven y sean editables los datos del apartado de caracteristicas de la promocion y medios de pago, asi como la variedad y cobertura, se limpian los campos de la mecanica y metodo");
				pom.selectClonarPromo();
			}
			pom.validateAccessEdit();
			pom.cambioMecanica(tipoCambio);
			testCase.addScreenShotCurrentStep(u, "Los datos del apartado de caracteristicas de la promocion y medios de pago, asi como la variedad y cobertura se conservan y se pueden editar, se limpian los campos de la mecanica y metodo");
			assertTrue(pom.validateCoberturas(), "No se pudo editar la promocion");
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Validar que los datos de la implementacion mecanica: Num de componentes y las categorias esten vacios");
			pom.implementacionMecanica();
			testCase.addScreenShotCurrentStep(u, "Los datos de implementacion mecanica se conservan vacios, El usuario debe llenar los datos de implementacion de la mecanica");
			assertTrue(pom.validateImplementacionMecanica(), "No se llenaron los datos de implementaion");
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Dar click en el boton de Activar");
			pom.selectActivarMecanica();
			testCase.addScreenShotCurrentStep(u, "Se borran los articulos del grid de articulos y se crean los tabs");
			assertTrue(pom.validateActivacion(), "No se activo la mecanica");
			
			if(prueba.contains("edicion")) {
				/////////////////////////////////////////////////////////////////////////////////////////
				addStep("Editar la promocion y hacer el cambio de mecanica y guardar los cambios");
			}else {
				/////////////////////////////////////////////////////////////////////////////////////////
				addStep("Clonar la promocion y hacer el cambio de mecanica y guardar los cambios");
			}
			pom.guardarPromocion();
			testCase.addScreenShotCurrentStep(u, "La promocion se crea correctamente y el sistema arroja un numero de folio.");
			assertTrue(pom.validateGuardar(), "Fallo al guaradr la nueva promocion");
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
		return "Construido. Validar como categoria la edicon, clonacion y cambio de mecanica de promociones a PromopackArtDescuento";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ValidarCambioDeMecanicaPromopackArtDescuento";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}