package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiPage;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_005_ValidarAmpersandSeMuestreSuSimboloCorrectoEnReportes extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar como Categoria, que al crear una promocion y se use la letra ampersand (&) en la descripcion de articulo esta se muestre de forma correcta (&) en los reportes
	 * @param data - data provider que contiene la url, folio y accion a realiar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarAmpersandSeMuestreSuSimboloCorrectoEnReportes(HashMap<String, String> data) throws Exception {
		
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
			addStep("Completar la promocion con la informacion requerida y en la descripcion de articulo usar simbolo & (ampersand)");
			pom.selectPlazas();
			pom.selectProvedor();
			pom.selectAltaPromocion();
			pom.selectEventoYCaracteristicas();
			pom.selectPaqueteExistente();
			pom.selectSegmento();
			pom.selectAgregarArticulo();
			pom.selectDatosArticulo();
			pom.ligarConvenios();
			testCase.addScreenShotCurrentStep(u, "Se captura correctamente la informacion requerida");			
			assertTrue(pom.validateCapturaNuevaPromocion(), "Fallo la captura de la nueva promocion");
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Guardar y enviar promocion");			
			pom.guardarPromocion();
			pom.enviarPromocion();
			testCase.addScreenShotCurrentStep(u, "Se muestra el numero de folio creado");
			assertTrue(pom.validateGuardarPromocion(), "Fallo al guaradr la nueva promocion");
			
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
		return "Construido. Validar como Categoria, que al crear una promocion y se use la letra ampersand (&) en la descripcion de articulo esta se muestre de forma correcta (&) en los reportes";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarAmpersandSeMuestreSuSimboloCorrectoEnReportes";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}