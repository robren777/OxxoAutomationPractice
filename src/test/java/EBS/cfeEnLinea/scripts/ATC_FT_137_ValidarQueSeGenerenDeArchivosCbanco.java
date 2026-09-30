package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;



/**
* DESCRIPCIÓN: Se valida que se genere correctamente los archivos en BD 
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_137_ValidarQueSeGenerenDeArchivosCbanco 
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD AVEBQA
* 	- Acceso al aplicativo EBS
* 
* 
* INSUMOS:
* 	-Conexion a BD
*   -Query para validar el archivo en BD 
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/
public class ATC_FT_137_ValidarQueSeGenerenDeArchivosCbanco extends BaseExecution{

	
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_137_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCbanco_test (HashMap<String, String> data) throws Exception {
	
		
		
		SqlExecute sql = new SqlExecute(testCase);
		
		addStep("Se ejecuta el query correspondiente y se valida el archivo CBANCO");
		Assert.assertTrue(sql.validateArchivoCBanco());
		
	}
		@Override
		public String setTestFullName() {
			return "ATC_FT_137_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCBANCO_test";
		}

		@Override
		public String setTestDescription() {
			return "Se Valida el archivo CBANCO en BD";
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
			return "Conexion a la BD"
					+ "Query para validación";
		}

		@Override
		public void beforeTest() {
		// TODO Auto-generated method stub
		}
	
}
