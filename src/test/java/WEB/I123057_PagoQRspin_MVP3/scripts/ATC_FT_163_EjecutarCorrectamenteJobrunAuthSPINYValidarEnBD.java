package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;

/**
 * DESCRIPCION:
 * Se valida que los Jobs modificados funcionen correctamente y esta ejecución se muestre en la BD correcto. 
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
	MTC-FT-163-Ejecutar correctamente job runAuthSPIN y validar en BD
 * 
 * PREREQUISITOS:
 *1.	Contar con acceso a plataforma Control-M
2.	Contar con ordenamiento de job a validar
3.	Contar con acceso a la BD WMINT y FCWMLQA
 *  
 *   *  INSUMOS:
 *  --
 * @author Monica Soto
 * @date 17/Septiembre/2025
 * 
 **/

public class ATC_FT_163_EjecutarCorrectamenteJobrunAuthSPINYValidarEnBD extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_163_EjecutarCorrectamenteJobrunAuthSPINYValidarEnBD_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.quit();
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		
		SQLUtil dbFCTPEQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCTPEQA, PagoQRspin_MVP3.DB_USER_FCTPEQA,PagoQRspin_MVP3.DB_PASSWORD_FCTPEQA);
		
		
		/*Definición de Variables*/
		String Njob = data.get("job");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		
		/*
		 * 1.	"Ingresar a Control-M
		O
		Pedir ticket de ejecución de job a equipo WM L3"
		2.	Ingresar a job runAuthSPIN en modulo Monitoring
		3.	Ejecutar Job
		4.	Validar output de la ejecucion de JOB
		5.	Ingresar a SQL DEVELOPER y conectarse a la BD correspondiente de logs
		6.	Realizar consulta y validar la ejecución correcta del Job

		 */
		
		//Script Code Begin
		
		System.out.println("***************PASO 01********************");

		addStep("Ingresar a Control-M y posteriormente ingresar a la pestaña Monitoring, para después agregar una vista para la ejecución del job colocando la carpeta (TQA_TRANSAC_ELEC) y el nombre del job (runAuthSPIN)");
		pqrs.ExecJobStatusWait(Njob, ps, user, server, testCase);	
		
		System.out.println("***************PASO 02********************");
		
		addStep("Ejecutar consulta para validar la ejecución exitosa de la interface en la BD");
		String query = "SELECT * FROM TPEUSER.TPE_MCP02_RUN WHERE INTERFACE = 'MCP02-SBO' AND TRUNC(START_DT) >= TRUNC(SYSDATE) AND rownum <= 1 ORDER BY RUN_ID DESC"; 
		SQLResult result = dbFCTPEQA.executeQuery(query);

		boolean validateEmpty  = true;
		validateEmpty  = result.isEmpty();
		
		assertFalse(validateEmpty, "La conexion no fue exitosa");
		testCase.addQueryEvidenceCurrentStep(result);
		
		// se obtiene el valor de status 
		String status = result.getData(0, "STATUS");
		assertEquals(status, "S", "Se obtuvo un valor diferente a S");
		testCase.addTextEvidenceCurrentStep("Valor de STATUS: " +status);

		
		//Script Code End
	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.	Contar con acceso a plataforma Control-M\r\n"
				+ "2.	Contar con ordenamiento de job a validar\r\n"
				+ "3.	Contar con acceso a la BD WMINT y FCWMLQA\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_163_EjecutarCorrectamenteJobrunAuthSPINYValidarEnBD";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida que los Jobs modificados funcionen correctamente y esta ejecución se muestre en la BD correcto.";
	}
}