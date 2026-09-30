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
* 	-MTC_FT_129_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsDelphi
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
* 	- Acceso al aplicativo EBS
* 
* 
* INSUMOS:
* 	-Conexion a BD
*   -Query para realizar la consulta
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_129_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsDelphi extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_129_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPosInboundDocsDelphi_test (HashMap<String, String> data) throws Exception {
        // Llamar al método que devuelve el String evidencia
       
        SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar tabla inbound docs maquina delphi");
		Assert.assertTrue(sql.validateTablaInboundDocs());
		 
	}


@Override
public String setTestFullName() {
	return "ATC_FT_129_ValidarQueSeEncuentreCargadaLaInformacionEnLaTablaPos_inbound_DocsDelphi_test";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el query correspondiente para validar informacion en POS_INBOUND_DOCS";
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
