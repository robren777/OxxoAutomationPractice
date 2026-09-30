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
* 	-MTC_FT_135_ValidarQueSeEncuentreCargadaLaInformacionDelServicioCfeEnLaTablaXxfcPagoServiciosPre
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD AVEBQA
* 
* 
* INSUMOS:
* 	-Conexion a BD AVEBQA
*   - Query para consulta
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_135_ValidarQueSeEncuentreCargadaLaInformacionDelServicioCfeEnLaTablaXxfcPagoServiciosPre extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_135_ValidarQueSeEncuentreCargadaLaInformacionDelServicioCfeEnLaTablaXxfcPagoServiciosPre_test (HashMap<String, String> data) throws Exception{
		
SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla Servicios PRE");
		Assert.assertTrue(sql.validateTablaServiciosPRE());
		
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_135_ValidarQueSeEncuentreCargadaLaInformacionDelServicioCfeEnLaTablaXxfcPagoServiciosPre_test";
	}

	@Override
	public String setTestDescription() {
		return "Se Ejecuta el query correspondiente para validar la tabla pago de servicios pre";
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
		return "1.-Acceso a BD AVEBQA"
				+ "2.-Ejecución interfaz PO3";
	}

	@Override
	public void beforeTest() {
	// TODO Auto-generated method stub
	}
}
