package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Se valida la informacion en la tabla Inbound docs para la maquina delphi 
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_134_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlNube
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 
* 
* INSUMOS:
* 	-Conexion a BD WM6QA
*   -Query para consulta
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_134_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlNube extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_134_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlNube_test (HashMap<String, String> data) throws Exception{
		
SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB maquina nube");
		Assert.assertTrue(sql.validateTablaPosHEFNube());
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB DETL maquina nube");
		Assert.assertTrue(sql.validateTablaPosHEFDetlNube());
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_134_ValidarQueSeEncuentreLaInformacionEnLasTablasPosHefYPosHefDetlNube_test";
	}

	@Override
	public String setTestDescription() {
		return "Se Ejecuta el query correspondiente para validar el archivo HEF";
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
		return "Credenciales vigentes al portal de ebs";
	}

	@Override
	public void beforeTest() {
	// TODO Auto-generated method stub
	}
}
