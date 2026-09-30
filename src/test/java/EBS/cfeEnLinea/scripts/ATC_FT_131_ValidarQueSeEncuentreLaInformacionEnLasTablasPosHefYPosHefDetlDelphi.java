package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;


/**
* DESCRIPCIÓN: Se valida la informacion en la tabla POS_HEF POS_HEF_DETL
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_131_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlDelphi 
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 	
* 
* INSUMOS:
* 	-Conexion a BD WM6QA
*   -Query para realizar consulta 
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_131_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlDelphi extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_131_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlDelphi_test (HashMap<String, String> data) throws Exception{
		
SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla POS HEF maquina delphi");
		Assert.assertTrue(sql.validateTablaPosHEF());
		addStep("Se ejecuta el query correspondiente para validar la tabla POS HEF DETL maquina delphi");
		Assert.assertTrue(sql.validateTablaPosHEFDetl());
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_131_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlDelphi_test";
	}

	@Override
	public String setTestDescription() {
		return "Se ejecuta el query correspondiente para validar las tablas del archivo HEF";
	}

	@Override
	public String setTestDesigner() {
		return "Alan Perez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a BD WM6QA";
	}

	@Override
	public void beforeTest() {
	// TODO Auto-generated method stub
	}
}
