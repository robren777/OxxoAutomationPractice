package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiPage;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_001_ValidarSubPlazasVaciasEnPestanaCoberturas extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar como Categoria que al seleccionar una o todas las plazas estas se muestren con al menos una sub plaza seleccionada y no se muestren todas vacias
	 * @param data - data provider que contiene la url del portal y la accion a realizar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarSubPlazasVaciasEnPestanaCoberturas(HashMap<String, String> data) throws Exception {
		
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
			pom.accessCuanti(this, "", data.get("accion"));
		
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Completar la informacion de la promocion seleccionando en coberturas todas las plazas");			
			pom.selectPlazas();
			pom.selectProvedor();
			pom.selectAltaPromocion();
			pom.selectEventoYCaracteristicas();
			pom.selectPaqueteExistente();
			pom.selectSegmento();
			pom.selectAgregarArticulo();
			pom.selectDatosArticulo();
			pom.ligarConvenios();
			testCase.addScreenShotCurrentStep(u, "Se seleccionan todas las plazas y se completa la promocion");			
			assertTrue(pom.validateCapturaNuevaPromocion(), "Fallo la captura de la nueva promocion");
		
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Guardar y enviar promocion");			
			pom.guardarPromocion();
			pom.enviarPromocion();
			testCase.addScreenShotCurrentStep(u, "Se muestra el numero de folio creado");
			assertTrue(pom.validateGuardarPromocion(), "Fallo al guaradr la nueva promocion");
		
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Ingresar al menu Promociones - Cuanti Buscar");		
			pom.selectBuscarPromocion();
			pom.selectAceptarCambios();
			testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla de busqueda");
			assertTrue(pom.validateAccessBusquedaPromo(), "Fallo el ingreso a busqueda de promociones");
		
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Ingresar el folio a buscar");		
			pom.buscarFolio();
			testCase.addScreenShotCurrentStep(u, "Se muestra el folio en los resultados");
			assertTrue(pom.validateBusquedaFolio(), "Fallo la busqueda del folio");
		
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
		return "Terminado. Validar como Categoria que al seleccionar una o todas las plazas estas se muestren con al menos una sub plaza seleccionada y no se muestren todas vacias";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarSubPlazasVaciasEnPestanaCoberturas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}