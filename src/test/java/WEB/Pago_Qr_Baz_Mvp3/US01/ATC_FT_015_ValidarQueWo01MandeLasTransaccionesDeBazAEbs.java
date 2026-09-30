package WEB.Pago_Qr_Baz_Mvp3.US01;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WEB.om.PagoBazQrMvp3;
import azure.annotation.AzureTestInfo;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(
		areaPath = "I23013 - Pago con QR en OXXO",
		iterationPath = "I23013 - Pago con QR en OXXO",
		tags = "ATC_FC_001;QR_BAZ",
		userStoryId = 73503
)
/** DESCRIPCIÓN: Caso de prueba que valida el proceso de reversas SAFv2 fallido en el 3er intento con ITEM de proveedor CNKTA y validar su registro en la bitacora con los datos correctos (contador en 2)
* 
/**
 * DESCRIPCION: Validar que la informacion de las ventas se vea en IMA
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-007 Validar que la informacion se vea reflejada en IMA 
 *
 * PREREQUISITOS:
 * - Tener los accesos a control M
 * - Contar con el nombre de las carpteas y rutas de los Jobs
 *
 * INSUMOS:
 * - Usuario y Contrasena control M
 *
 * @author Antonio Martinez
 * @date 09/Abril/2025
 */


public class ATC_FT_015_ValidarQueWo01MandeLasTransaccionesDeBazAEbs extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_ValidarQueWo01MandeLasTransaccionesDeBazAEbs_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		
		//Comienza Script 	
		
		//Variables
		
		
		//Se ejecuta Job 1.-  runWO01_MX
		
		addStep("Ejecutar el job runSAFTagMty");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PagoBazQrMvp3 bJPrd = new PagoBazQrMvp3(u, testCase);
		
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		
		u.get(data.get("server"));
 
		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();
		
 
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job= new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		// /*
		assertTrue(flag);
		
		
 
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());
		assertEquals(resultado1, "Ended OK");
 		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runWO01_MX");
		testCase.addScreenShotCurrentStep(u, "EndOk");
		u.quit(); 
		
		
		
		//Se ejecuta Job 2.- runWO01_MX_TPG 
		addStep("Ejecutar el job runSAFTagQro");
		// Recrear SeleniumUtil
		u = new SeleniumUtil(new ChromeTest(), true);  // Nueva instancia
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted); 

		u.get(data.get("server")); 
		
		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		// 🔹 Volver a instanciar ControlM con la nueva instancia de SeleniumUtil
		obj = new JSONObject(data.get("job2"));
		job = new ControlM(u, testCase, obj);  // Nueva instancia de ControlM
		
		flag = job.searchJob();

		assertTrue(flag);

		String resultado2 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado2);
		System.out.println("Valor de output: " + job.getOutput());
		assertEquals(resultado2, "Ended OK");

		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runWO01_MX_TPG");
		bJPrd.validatePatternAndClick("EndOk2");

		//u.quit();  // Cerrar el navegador después de la segunda ejecucion.
		
				
	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Ejecutar los jobs para FEMSA_FT \r\n"
		+ "2. Contar con el nuevo servicio en ambiente QA.\r\n"
		+ "3. Contar con el ambiente de pruebas del proveedor.\r\n"
		+ "4.- Contar con las credenciales de Control M.\r\n"
		+ "5.- Contar con el Job en Hold.\r\n"
		+ "6.- Contar con el cambio en la url en la BD para el fallo en el reverso";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez Olivas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_015_ValidarQueWo01MandeLasTransaccionesDeBazAEbs";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "V validar que WO01 mande las transacciones de BAZ a EBS";
	}
}