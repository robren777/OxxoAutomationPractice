package EBS.cfeEnLinea.scripts;

import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.BaseCfeEnLinea;
import EBS.cfeEnLinea.om.SqlExecute;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;


/**
* DESCRIPCIÓN: Se valida la informacion en la tabla servicios pre y el correo que envia la OL9 con su ejecucion
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_136_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCbanco
*	
* 
* PREREQUISITOS:
* 	- Acceso a BD WM6QA
*   -Configuracion de correo
* 
* 
* INSUMOS:
* 	-Conexion a BD WM6QA
*   -Query para la consulta 
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/

public class ATC_FT_136_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCbanco extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_136_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCbanco_test (HashMap<String, String> data) throws Exception{
		SeleniumUtil uSelenium = new SeleniumUtil(new ChromeDriver());
SqlExecute sql = new SqlExecute(testCase);
BaseCfeEnLinea Base = new BaseCfeEnLinea(uSelenium, testCase, null, null);
		
		addStep("Se ejecuta el query correspondiente para validar la tabla Servicios PRE");
		Assert.assertTrue(sql.validateTablaServiciosPREStatusE());
	   addStep("Se ingresa al correo y se valida correo OL9");
		Assert.assertTrue(Base.validarCorreoPorRemitente("outlook.office365.com", "993", "alejandro.rocha@gtim.mx", "Jam28178", "OXXO-NoReply oxvwqbosq03.femcom.net <oxxo-noreply@oxxo.com>"));
	}
	
	@Override
	public String setTestFullName() {
		return " ATC_FT_136_ValidarEnvioDeCorreoDeNotificacionYGeneracionDeArchivosCbanco_test";
	}

	@Override
	public String setTestDescription() {
		return "Se Ejecuta el query para validar el estatus en la tabla de servicios pre";
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
		return "Ejecucion de query correspondiente"
				+ "ejecucion interfaz OL9"
				+"Acceso a BD AVEBQA"
				;
	}

	@Override
	public void beforeTest() {
	// TODO Auto-generated method stub
	}
}
