package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;



/**
* DESCRIPCIÓN: Se valida la informacion en la tabla POS_SYB y POS_SYB_DETL para CR nube
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_133_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlNube
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 	- Acceso al aplicativo EBS
* 
* 
* INSUMOS:
* 	-Conexion a BD
*   - Query para consulta
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_133_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlNube extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_133_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlNube_test (HashMap<String, String> data) throws Exception {
		
	
		
		SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB maquina nube");
		Assert.assertTrue(sql.validateTablaPosSYBNube());
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB DETL maquina nube");
		Assert.assertTrue(sql.validateTablaPosSYBDetlNube());
	}
	
	
	

@Override
public String setTestFullName() {
	return "ATC_FT_133_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlNube_test";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el query correspondiente para validar tablas del archivo SYB";
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
