package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Se valida la informacion en la tabla Inbound docs para la maquina nube
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_132_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsNube
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 
* 
* INSUMOS:
* 	-Conexion a BD WM6QA
*   - 
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_132_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsNube extends BaseExecution {

	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_132_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsNube_test (HashMap<String, String> data) throws Exception
{
	

SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar tabla inbound docs maquina nube");
		Assert.assertTrue(sql.validateTablaInboundDocsNube());


}


@Override
public String setTestFullName() {
	return "ATC_FT_132_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsNube_test";
}

@Override
public String setTestDescription() {
	return "Se ejecuta el query correspondiente para validar tabla INBOUND_DOCS de tienda nube";
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
