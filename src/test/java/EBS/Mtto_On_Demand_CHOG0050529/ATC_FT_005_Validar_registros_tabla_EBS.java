package EBS.Mtto_On_Demand_CHOG0050529;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.Mtto_On_Demand_CHOG0050529.OM.Mtto_On_Demand;
import modelo.BaseExecution;
import utils.password.PasswordUtil;


public class ATC_FT_005_Validar_registros_tabla_EBS extends BaseExecution{
	@Test(dataProvider = "data-provider")
	/**
	 * DESCRIPCION: Validar que el FLAG de los registros procesados se actualiza de 0 en la tabla APPS.XXFC_EAM_OTMA_ALL en EBS 
	 * 
	 * PREREQUISITOS:
	 * Acceso a Control M
	 * Acceso a la BD de EBS
	 *
	 * INSUMOS:
	 * - IDSolicitud 
	 *
	 * @author Michelle Jesús Vásquez Vivar
	 * @date 01/Marzo/2025
	 */
	
	public void ATC_FT_005_Validar_registros_tabla_EBS_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Mtto_On_Demand");
		/*
		   * VARIABLES
		   */
		String userDB = data.get("userDB");
		String PassDB = PasswordUtil.decryptPassword(data.get("PassDB"));
		  /*
		   * UTILERIAS
		   * 
		   */
		Mtto_On_Demand mtoDemand = new Mtto_On_Demand(null, testCase);
		
		addStep("1.	Ingresar a la BD de EBS");
		
		addStep("Conectarse a la base de datos EBS 12.2.4");
		
		// PASO 2
		addStep("Ejecutar el query ");
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
		return "Validar que el FLAG de los registros procesados se actualiza de 0 en la tabla APPS.XXFC_EAM_OTMA_ALL en EBS ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "michelle.vasquez@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_Validar_registros_tabla_EBS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


}
