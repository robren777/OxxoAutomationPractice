package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;


/**
* DESCRIPCIÓN: Se valida la informacion en la tabla POS_SYB POS_SYB_DETL
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_130_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlDelphi
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 	- Acceso al aplicativo EBS
*   
* 
* 
* INSUMOS:
* 	-Conexion a BD WM6QA
*   -Query para consulta
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_130_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlDelphi extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_130_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlDelphi_test (HashMap<String, String> data) throws Exception {

SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB maquina delphi");
		Assert.assertTrue(sql.validateTablaPosSYB());
		addStep("Se ejecuta el query correspondiente para validar la tabla POS SYB DETL maquina delphi");
		Assert.assertTrue(sql.validateTablaPosSYBDetl());
	}
	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_130_ValidarQueSeEncuentreLaInformacionEnLasTablasPosSybYPosSybDetlDelphi_test";
	}

	@Override
	public String setTestDescription() {
		return "Se Ejecuta el query correspondiente para validar informacion en tablas SYB";
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
