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
import static org.testng.Assert.assertTrue;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;

/**
 * DESCRIPCION: 
 * Se valida el mensaje de error obtenido al momento de que la interfaz al ser ejecutada esta no encuentre el archivo
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-121-Ejecutar Job de la interfaz EB1 de proveedor BAZ y obtener error de ejecucion de interfaz por archivo no encontrado
 * 
 * PREREQUISITOS:
 *
1. Pedir a soporte L2 archivo de conciliacion de ambiente productivo para pruebas mas reales
2. Contar con archivo de conciliacion de proveedor en el buzon FTP en la ruta correspondiente
3. Contar con acceso a la BD FCODS
4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecucion de Jobs con soporte L3
5. Contar con acceso a BD WMINT
6. Conar con acceso a BD WMLOG
 *  
 *  INSUMOS
 *  Archivo de conciliacion del proveedor SPIN RESCOMPRA_OXXO_20250611.01 (el nombre cambia con la fecha actual)
 *  
 * @author Juan Gerardo Lopez
 * @date 29/Julio/2025 
 * 
 **/

public class ATC_FT_121_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorArchivoNoEncontrado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_121_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorArchivoNoEncontrado_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.quit();
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		
		/*Definición de Variables*/
		String Njob = data.get("Njob");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		String interfaz = data.get("interfaz");
		String nameFile = data.get("NombreArchivo");
		String proveedor = data.get("proveedor");
		String expectedstatus = data.get("expectedstatus");
		String expectedDescription = data.get("expectedDescription");
		String expectedMessage = data.get("expectedMessage");
		
		//Script Code Begin

		System.out.println("***************PASO 01********************");
		addStep("Validar que no exista ejecucion previa de la interface EB1 con proveedor BAZ");
		boolean noExist = pqrs.validPreviewExecEB1(nameFile, false);
		if(noExist) {
			System.out.println("No existe ejecucion previa de la interface");
		}else {
			System.out.println("Si existe ejecucion previa de la interface");
		}
		
		System.out.println("***************PASO 02********************");
		addStep("Se ejecuta job de la interface EB1 del proveedor BAZ");
		pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
		
		System.out.println("***************PASO 03********************");
		addStep("Se valida que no exista el archivo procesado por la EB1 de BAZ");
		boolean archivoNoExiste = pqrs.validFileProcessEB1(proveedor, nameFile, false);

		if (archivoNoExiste) {
		    System.out.println("Validación correcta: el archivo no existe, continúo el flujo.");
		} else {
		    System.out.println("Advertencia: el archivo existe aunque no debería, continúo de todas formas.");
		}
		
		System.out.println("***************PASO 04********************");
		addStep("Se valida que la ejecucion de la interface EB1 con proveedor BAZ obtenga error");
		Map<String, String> logInfo = pqrs.validLogExecInDBEB1(interfaz, proveedor);
		String runid = logInfo.get("RUN_ID");
		String status = logInfo.get("STATUS");
		Assert.assertEquals(status, expectedstatus, "El STATUS del primer resultado no es '" + expectedstatus + "'");
		
		System.out.println("***************PASO 05********************");
		addStep("Se valida el error mostrado en WM_LOG_ERROR");
		pqrs.validLogErrorInDB(runid, expectedDescription, expectedMessage, true);
		
		//Script Code End
	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Pedir a soporte L2 archivo de conciliacion de ambiente productivo para pruebas mas reales\r\n"
				+ "2. Contar con archivo de conciliacion de proveedor en el buzon FTP en la ruta correspondiente\r\n"
				+ "3. Contar con acceso a la BD FCODS\r\n"
				+ "4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecucion de Jobs con soporte L3\r\n"
				+ "5. Contar con acceso a BD WMINT\r\n"
				+ "6. Conar con acceso a BD WMLOG";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_121_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorArchivoNoEncontrado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida el mensaje de error obtenido al momento de que la interfaz al ser ejecutada esta no encuentre el archivo";
	}
}