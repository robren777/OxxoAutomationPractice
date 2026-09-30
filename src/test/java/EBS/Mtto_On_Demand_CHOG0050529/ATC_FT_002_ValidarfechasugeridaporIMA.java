package EBS.Mtto_On_Demand_CHOG0050529;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.Mtto_On_Demand_CHOG0050529.OM.Mtto_On_Demand;
import modelo.BaseExecution;

public class ATC_FT_002_ValidarfechasugeridaporIMA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	/**
	 * DESCRIPCION: Validación fecha sugerida por IMA
	 * 
	 * PREREQUISITOS:
	 * Acceso a Control M
	 *Acceso a la BD de EBS
	 *
	 * INSUMOS:
	 * - IDSolicitud 
	 *
	 * @author Michelle Jesús Vásquez Vivar
	 * @date 01/Marzo/2025
	 */
	public void ATC_FT_002_ValidarfechasugeridaporIMA_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Mtto_On_Demand");
		/*
		   * VARIABLES
		   */
		  /*
		   * UTILERIAS
		   * 
		   */		
		Mtto_On_Demand mtoDemand = new Mtto_On_Demand(null, testCase);
		
		addStep("1.	Ingresar a la BD de EBS");
		
		addStep("Conectarse a la base de datos EBS 12.2.4");
		
		// PASO 2
		addStep("Ejecutar el query ");
		mtoDemand.validaFechaIMA();
		assertTrue(mtoDemand.validaFechaIMA(), "No encontro datos");
	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a control M, acceso a la BD de EBS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validación fecha sugerida por IMA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "michelle.vasquez@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarfechasugeridaporIMA_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
