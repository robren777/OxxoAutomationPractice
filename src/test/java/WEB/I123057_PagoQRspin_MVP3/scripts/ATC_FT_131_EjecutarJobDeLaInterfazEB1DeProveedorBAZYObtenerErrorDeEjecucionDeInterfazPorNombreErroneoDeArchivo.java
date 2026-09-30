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
 * Se valida el mensaje de error obtenido al momento de que la interfaz al ser ejecutada esta no encuentre el archivo
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
MTC-FT-131-Ejecutar Job de la interfaz EB1 de proveedor SPIN y obtener error de ejecucion de interfaz por nombre erróneo de archivo * 
 * PREREQUISITOS:
 *
 *1. Pedir a soporte L2 archivo de conciliacion de ambiente productivo para pruebas mas reales
 *2. Contar con archivo de conciliacion de proveedor en el buzon FTP en la ruta correspondiente
 *3. Contar con acceso a la BD FCODS
 *4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecucion de Jobs con soporte L3
 *5. Contar con acceso a BD WMINT
 *6. Conar con acceso a BD WMLOG
 *  
 *   *  
 *  INSUMOS
 *  Archivo de conciliación RESCOMPRA_OXXO con nombre incorrecto
 *  
 * @author Monica Soto
 * @date 05/Septiembre/2025 
 * 
 **/

public class ATC_FT_131_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorNombreErroneoDeArchivo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_131_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorNombreErroneoDeArchivo_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		
		u.quit();

		SQLUtil dbWMLOG = new SQLUtil(PagoQRspin_MVP3.DB_HOST_LOG, PagoQRspin_MVP3.DB_USER_LOG,PagoQRspin_MVP3.DB_PASSWORD_LOG);

		/*Definición de Variables*/
		String Njob = data.get("job");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		String archivoConciliacion = data.get("archivoConciliacion");	
		String attName = data.get("attName");
		
		/*pasos
		 * 1.	Cambiar el nombre del archivo de conciliación y pedir a equipo de WM L3 que lo deposite dentro del buzón
2.	Abrir ORACLE SQL e ingresar a la BD  WMINT
3.	"Ejecutar consulta de validación de no ejecución previas de interfaz EB1:
SELECT CASE COUNT(*)
WHEN 0  THEN 'FALSE'
ELSE 'TRUE'
END EXISTENTE
FROM WM_EB1_EXECUTION
WHERE NOMBRE_ARCHIVO = ''  --Aquí se colocara el nombre del archivo que se subio al buzon y la entidad que especifica el caso de prueba."
4.	"Ingresar a Control-M y posteriormente ingresar a la pestaña Monitoring, para despues agregar una vista para la ejecucion del job colocando la carpeta (TQA_BAJO_DEMANDA) y el nombre del job (run_EB1_SPIN)
NOTA: para poder ejecutar el job es necesario pedir ticket para que ese job quede ordenado y pueda ser ejecutado bajo demanda"
5.	Ejecutar JOB
6.	Validar correo enviado por la interface donde se mostrara el error ocurrido
7.	Ingresar a la BD FCWMLOG en la aplicación ORACLE SQL
8.	"Ejecutar consulta para validar la ejecucion erronea de la interface en la BD:
SELECT *
FROM WMLOG.WM_LOG_RUN
WHERE INTERFACE = 'EB1'
ORDER BY START_DT DESC"
		 */
		
		//Script Code Begin

				System.out.println("***************PASO 01********************");
				addStep("Ejecutar consulta de validación de no ejecución previas de interface EB1");
				assertFalse(pqrs.validateExistence(archivoConciliacion), "El resultado trae valor");

				System.out.println("***************PASO 02********************");

				addStep("Ingresar a Control-M y posteriormente ingresar a la pestaña Monitoring, para después agregar una vista para la ejecución del job colocando la carpeta (TQA_BAJO_DEMANDA) y el nombre del job (run_EB1_SPIN)");
				pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
				
				System.out.println("***************PASO 03********************");
				addStep("Ejecutar consulta para validar la ejecución erronea de la interface en la BD");
				String query = "SELECT RUN_ID, INTERFACE, START_DT, STATUS FROM WMLOG.WM_LOG_RUN WHERE INTERFACE = 'EB1' AND ATT1 = '"+attName+"' AND START_DT>=trunc(sysdate) AND ROWNUM <= 1 ORDER BY START_DT DESC";
				SQLResult result = dbWMLOG.executeQuery(query);

				boolean validateEmpty  = true;
				validateEmpty  = result.isEmpty();
				
				assertFalse(validateEmpty, "La conexion no fue exitosa");
				
				//Se obtiene el valor de RUNID, necesario para el paso 04
				String status = result.getData(0, "STATUS");
				assertEquals(status, "E", "Se obtuvo un valor diferente a E");
				testCase.addQueryEvidenceCurrentStep(result);
				
				//se obtienen los demas datos de la misma consulta
				query = "SELECT END_DT, SERVER, ATT1 FROM WMLOG.WM_LOG_RUN WHERE INTERFACE = 'EB1' AND ATT1 = '"+attName+"' AND START_DT>=trunc(sysdate) AND ROWNUM <= 1 ORDER BY START_DT DESC";
				result = dbWMLOG.executeQuery(query);
				validateEmpty  = true;
				validateEmpty  = result.isEmpty();
				
				assertFalse(validateEmpty, "La conexion no fue exitosa");
				testCase.addQueryEvidenceCurrentStep(result);
				
			
				//Script Code Ends
	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return " *1. Pedir a soporte L2 archivo de conciliacion de ambiente productivo para pruebas mas reales\r\n"
				+ " *2. Contar con archivo de conciliacion de proveedor en el buzon FTP en la ruta correspondiente\r\n"
				+ " *3. Contar con acceso a la BD FCODS\r\n"
				+ " *4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecucion de Jobs con soporte L3\r\n"
				+ " *5. Contar con acceso a BD WMINT\r\n"
				+ " *6. Conar con acceso a BD WMLOG";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Monica Soto";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_131_EjecutarJobDeLaInterfazEB1DeProveedorBAZYObtenerErrorDeEjecucionDeInterfazPorNombreErroneoDeArchivo";
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