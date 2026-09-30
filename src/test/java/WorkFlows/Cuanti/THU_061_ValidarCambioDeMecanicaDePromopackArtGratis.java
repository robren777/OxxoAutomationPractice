package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiMecanica;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class THU_061_ValidarCambioDeMecanicaDePromopackArtGratis extends BaseExecution {
	
	String tipoCambio;
	String prueba;
	
	/**
	 * @author Jose Zarate
	 * Validar como categoria la edicion, clonacion y cambio de mecanica de una promocion Promopack - art gratis que ya esta guardada pueda ser cambiada a otro tipo de mecanica
	 * @param data - data provider que contiene la url, accion, folio, prueba y tipoFolio
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_061_ValidarCambioDeMecanicaDePromopackArtGratis(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> Cuanti");
		/*
		 * Variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_USER_CATEGORIA;
		String userCuanti = GlobalVariables.CUANTI_USER;
		String passCuanti = GlobalVariables.CUANTI_PASS;
		tipoCambio = data.get("tipoCambio");
		prueba = data.get("prueba");
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url"));	
		
		PomCuantiMecanica pom = new PomCuantiMecanica(u, userUltimus, userCuanti, passCuanti, data.get("folio"), testCase);
		
		testCase.setTest_Description("Validar como categoria la "+prueba+" y cambio de mecanica de una Promopack - art+precio=art2 que ya esta guardada pueda ser cambiada a un tipo de mecanica "+tipoCambio);
		
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
				addStep("Dar click en Editar y validar Cuando el usuario intente hacer el cambio en el combo de tipo de mecanica debe aparecer el siguiente mensaje \"No es posible cambiar de mecanica una vez que la promocion ha sido activada como promopack\"");
				pom.selectEditPromo();
			}else {
				/////////////////////////////////////////////////////////////////////////////////////////
				addStep("Dar click en Clonar y validar Cuando el usuario intente hacer el cambio en el combo de tipo de mecanica debe aparecer el siguiente mensaje \"No es posible cambiar de mecanica una vez que la promocion ha sido activada como promopack\"");
				pom.selectClonarPromo();
			}
			pom.validateAccessEdit();
			pom.editMecanicas(data.get("tipoCambio"));
			testCase.addScreenShotCurrentStep("El mensaje \"No es posible cambiar de mecanica una vez que la promocion ha sido activada como promopack\" aparece y no es posible cambiar la mecanica");
			testCase.addScreenShotCurrentStep(u, "El mensaje \"No es posible cambiar de mecanica una vez que la promocion ha sido activada como promopack\" aparece y no es posible cambiar la mecanica");
			assertTrue(pom.validateMSNError(), "No se mostro el mensaje de error");
			pom.aceptarError();
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
		return "Terminado. Validar como categoria la edicion, clonacion y cambio de mecanica de una promocion Promopack - art gratis que ya esta guardada pueda ser cambiada a otro tipo de mecanica";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_061_ValidarCambioDeMecanicaDePromopackArtGratis";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}