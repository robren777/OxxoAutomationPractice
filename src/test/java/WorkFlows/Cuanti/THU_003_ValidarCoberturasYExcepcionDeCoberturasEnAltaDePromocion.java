package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiPage;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_003_ValidarCoberturasYExcepcionDeCoberturasEnAltaDePromocion extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar usuario categoria requiero que al editar una promocion la informacion de coberturas y excepcion de coberturas se muestre correcta
	 * @param data - data provider que contiene la url, folio y accion a realiar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCoberturasYExcepcionDeCoberturasEnAltaDePromocion(HashMap<String, String> data) throws Exception {
			
		testCase.setProject_Name("Framework WorkFlows --> Cuanti");
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_CATEGORIA;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url"));	
		
		PomCuantiPage pom = new PomCuantiPage(u, userUltimus, userCuanti, passCuanti, testCase);
		
		try {
			/////////////////////////////////////////////////////////////////////////////////////////
			pom.accessCuanti(this, data.get("folioPromo"), data.get("accion"));
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Buscar una promocion anteriormente creada usando los filtros de busqueda");
			pom.buscarFolioPorFiltros();
			testCase.addScreenShotCurrentStep(u, "Se muestra el folio en los resultados");
			assertTrue(pom.validateBusquedaFolio(), "Fallo la busqueda del folio");			
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Validar la informacion de coberturas y excepcion de coberturas se muestre correcta");
			pom.selectEditPromo();
			testCase.addScreenShotCurrentStep(u, "La informacion se muestra correctamente");
			assertTrue(pom.validateCoberturasYExcepsiones(), "Fallo la visualizacion de coberturas y excepsiones");	
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Dar click en pestaña coberturas");
			pom.selectCoberturasCss();
			testCase.addScreenShotCurrentStep(u, "Las coberturas se muestran correctamente");
			assertTrue(pom.validateCoberturas(), "Las coberturas no estan seleccionadas");	
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Validar que la pestaña alta de promocion conserva la informacion de coberturas y excepciones");
			pom.selectAltaPromocionCss();
			testCase.addScreenShotCurrentStep(u, "La informacion se muestra correctamente");
			assertTrue(pom.validateCoberturasYExcepsiones(), "Fallo la visualizacion de coberturas y excepsiones");	
			
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
		return "Construido. Validar usuario categoria requiero que al editar una promocion la informacion de coberturas y excepcion de coberturas se muestre correcta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarCoberturasYExcepcionDeCoberturasEnAltaDePromocion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}