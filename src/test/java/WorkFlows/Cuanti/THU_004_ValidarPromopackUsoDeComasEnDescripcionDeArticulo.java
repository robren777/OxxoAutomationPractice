package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiPage;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_004_ValidarPromopackUsoDeComasEnDescripcionDeArticulo extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar como Categoria que al clonar una promopack que usa comas en su descripcion de articulo esta no se separe en varias cajas de edicion dentro de la pestaña de alta de promocion
	 * @param data - data provider que contiene la url, folio y accion a realiar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarPromopackUsoDeComasEnDescripcionDeArticulo(HashMap<String, String> data) throws Exception {
		
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
			pom.buscaFolioPromopack();
			testCase.addScreenShotCurrentStep(u, "Se muestra el folio en los resultados");
			assertTrue(pom.validateBusquedaFolio(), "Fallo la busqueda del folio");			
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Dar click en clonar y validar que la descripcion cuente con comas de separacion");
			pom.selectClonarPromo();
			testCase.addScreenShotCurrentStep(u, "La informacion se muestra correctamente");
			assertTrue(pom.validateComas(), "La descripcion no cuenta con comas");	
			
			/////////////////////////////////////////////////////////////////////////////////////////
			pom.guardarYEnviarPromoClonada(this);
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Buscar la promocion anteriormente clonada usando los filtros de busqueda");
			pom.selectBuscarPromocion();
			pom.selectAceptarCambios();
			pom.validateAccessBusquedaPromo();
			pom.buscarFolioClonado();
			pom.buscaFolioPromopack();
			testCase.addScreenShotCurrentStep(u, "La aplicacion muestra la pantalla de busqueda con los resultados en base a los filtros usados");
			assertTrue(pom.validateBusquedaFolioPromocion(), "Fallo la busqueda de la promocion clonada");
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Validar que la informacion de descripcion de articulos se muestre correctamente");
			testCase.addScreenShotCurrentStep(u, "La descripcion se muestra de forma correcta");
			assertTrue(pom.validateDescripcionBusqueda(), "La informacion de la descripcion no es correcta");

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
		return "Construido. Validar como Categoria que al clonar una promopack que usa comas en su descripcion de articulo esta no se separe en varias cajas de edicion dentro de la pestaña de alta de promocion";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarPromopackUsoDeComasEnDescripcionDeArticulo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
