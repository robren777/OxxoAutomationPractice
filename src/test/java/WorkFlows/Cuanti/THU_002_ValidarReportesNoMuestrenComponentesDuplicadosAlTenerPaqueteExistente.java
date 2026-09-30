package WorkFlows.Cuanti;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Cuanti.PomCuantiPage;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
 
public class THU_002_ValidarReportesNoMuestrenComponentesDuplicadosAlTenerPaqueteExistente extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Validar como Categoria que al clonar un folio que cuente con un paquete existente los reportes no muestren componentes duplicados
	 * @param data - data provider que contiene la url, folio y accion a realiar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarReportesNoMuestrenComponentesDuplicadosAlTenerPaqueteExistente(HashMap<String, String> data) throws Exception {
		
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
			addStep("Buscar promocion de un periodo anterior con paquete existente y clonar");
			pom.buscarFolio();		
			testCase.addScreenShotCurrentStep(u, "Se muestra el folio en los resultados");
			assertTrue(pom.validateBusquedaFolio(), "Fallo la busqueda del folio");
			
			/////////////////////////////////////////////////////////////////////////////////////////
			addStep("Clonar promocion");
			pom.selectClonarPromo();
			testCase.addScreenShotCurrentStep(u, "Se muestran los datos de la promocion");
			assertTrue(pom.validateClonarPromocion(), "Fallo el clonado de la promocion");

			/////////////////////////////////////////////////////////////////////////////////////////
			pom.guardarYEnviarPromoClonada(this);
			
			
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
		return "Construido. Validar como Categoria que al clonar un folio que cuente con un paquete existente los reportes no muestren componentes duplicados";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarReportesNoMuestrenComponentesDuplicadosAlTenerPaqueteExistente";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}