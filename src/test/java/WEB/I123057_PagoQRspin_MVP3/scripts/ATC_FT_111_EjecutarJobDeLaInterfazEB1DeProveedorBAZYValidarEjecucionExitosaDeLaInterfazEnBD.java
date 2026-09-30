package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
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
import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;

/**
 * MTC-FT-111-Ejecutar Job de la interfaz EB1 de proveedor BAZ y validar ejecucion exitosa de la interfaz en BD
 * 
 * DESCRIPCION: 
 * Se valida que la ejecución de los jobs creados para cada proveedor se ejecute correctamente
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-111-Ejecutar Job de la interfaz EB1 de proveedor BAZ y validar ejecucion exitosa de la interfaz en BD
 * 
 * PREREQUISITOS:
 *  1. Pedir a soporte L2 archivo de conciliación de ambiente productivo para pruebas más reales
2. Contar con archivo de conciliación de proveedor en el buzón FTP en la ruta correspondiente
3. Contar con acceso a la BD FCODS
4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecución de Jobs con soporte L3
5. Contar con acceso a BD WMINT
6. Contar con acceso a BD WMLOG
 *   *  
 *  INSUMOS
 *  Archivo de conciliación RESCOMPRA_OXXO
 *  
 * @author Monica Soto
 * @date 29/Agosto/2025 
 * 
 **/

public class ATC_FT_111_EjecutarJobDeLaInterfazEB1DeProveedorBAZYValidarEjecucionExitosaDeLaInterfazEnBD extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_111_EjecutarJobDeLaInterfazEB1DeProveedorBAZYValidarEjecucionExitosaDeLaInterfazEnBD_test (HashMap <String, String> data) throws Exception {
		testCase.setProject_Name("MVP3 Pago con QR Spin");

		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);

		u.quit();
		
		SQLUtil dbWMLOG = new SQLUtil(PagoQRspin_MVP3.DB_HOST_LOG, PagoQRspin_MVP3.DB_USER_LOG,PagoQRspin_MVP3.DB_PASSWORD_LOG);
		SQLUtil dbWMINT = new SQLUtil(PagoQRspin_MVP3.DB_HOST_WMINT, PagoQRspin_MVP3.DB_USER_WMINT,PagoQRspin_MVP3.DB_PASSWORD_WMINT);


		/*Definición de Variables*/
		String archivoConciliacion = data.get("archivoConciliacion");	
		String attName = data.get("attName");
		String Njob = data.get("job");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		
		/*pasos
		 * 1.		1"Validar que el pre-requisito de contar con el archivo de conciliación se encuentre en el buzón, mediante ticket a equipo L3 WM
NOTA: El archivo deberá de contar en un nombre la fecha actual de cuando se ejecutará la interface"
2.	Abrir ORACLE SQL e ingresar a la BD  WMINT
3.	"Ejecutar consulta de validación de no ejecución previas de interface EB1:
SELECT CASE COUNT(*)
WHEN 0  THEN 'FALSE'
ELSE 'TRUE'
END EXISTENTE
FROM WM_EB1_EXECUTION
WHERE NOMBRE_ARCHIVO = '‘ --Aquí se colocará el nombre del archivo que se subió al buzón y la entidad que especifica el caso de prueba."
4.	"Ingresar a Control-M y posteriormente ingresar a la pestaña Monitoring, para después agregar una vista para la ejecución del job colocando la carpeta (TQA_BAJO_DEMANDA) y el nombre del job (run_EB1_SPIN)
NOTA: para poder ejecutar el job es necesario pedir ticket para que ese job quede ordenado y pueda ser ejecutado bajo demanda"
5.	Ejecutar JOB
6.	"Ingresar a la BD WMINT en la aplicación ORACLE SQL y ejecutar query de validación de procesamiento del archivo:
SELECT *
FROM WMLOG.WM_EB1_EXECUTION
ORDER BY CREATION_DATE DESC"
7.	Ingresar a la BD FCWMLOG en la aplicación ORACLE SQL
8.	"Ejecutar consulta para validar la ejecución correcta de la interfaz en la BD:
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
		addStep("Entrar a la BD FCWMLOG y ejecutar consulta para validar la ejecución correcta de la interfaz en la BD");
		String query = "SELECT RUN_ID, INTERFACE, START_DT, END_DT FROM WMLOG.WM_LOG_RUN WHERE INTERFACE = 'EB1' AND ATT1 = '"+attName+"' AND START_DT>=trunc(sysdate) AND ROWNUM <= 1  ORDER BY START_DT DESC";
		SQLResult result = dbWMLOG.executeQuery(query);

		boolean validateEmpty  = true;
		validateEmpty  = result.isEmpty();
		
		assertFalse(validateEmpty, "La conexion no fue exitosa");
		testCase.addQueryEvidenceCurrentStep(result);
		
		//Se obtiene el valor de RUNID, necesario para el paso 04
		String runid = result.getData(0, "RUN_ID");
		
		query = "SELECT STATUS, SERVER, ATT1 FROM WMLOG.WM_LOG_RUN WHERE INTERFACE = 'EB1' AND ATT1 = '"+attName+"' AND START_DT>=trunc(sysdate) AND ROWNUM <= 1  ORDER BY START_DT DESC";
		result = dbWMLOG.executeQuery(query);
		validateEmpty  = true;
		validateEmpty  = result.isEmpty();
		
		assertFalse(validateEmpty, "La conexion no fue exitosa");
		testCase.addQueryEvidenceCurrentStep(result);
		
		
		System.out.println("***************PASO 04********************");

		addStep("Ingresar a la BD WMINT en la aplicación ORACLE SQL y ejecutar query de validación de procesamiento del archivo:");
		testCase.addTextEvidenceCurrentStep("SELECT * FROM WMUSER.WM_EB1_EXECUTION ORDER BY CREATION_DATE DESC");

		query = "SELECT ID, RUNID, ENTIDAD, NOMBRE_ARCHIVO FROM WMUSER.WM_EB1_EXECUTION WHERE CREATION_DATE>=trunc(sysdate) AND RUNID = '"+runid+"' ORDER BY CREATION_DATE DESC";
		result = dbWMINT.executeQuery(query);
		validateEmpty  = true;
		validateEmpty  = result.isEmpty();
		
		assertFalse(validateEmpty, "La conexion no fue exitosa");
		testCase.addQueryEvidenceCurrentStep(result);
		
		
		query = "SELECT MONTO_TOTAL, COMISION_TOTAL, NO_REG_TOTALES, CREATION_DATE FROM WMUSER.WM_EB1_EXECUTION WHERE CREATION_DATE>=trunc(sysdate) AND RUNID = '"+runid+"' ORDER BY CREATION_DATE DESC";
		result = dbWMINT.executeQuery(query);
		validateEmpty  = true;
		validateEmpty  = result.isEmpty();
		
		assertFalse(validateEmpty, "La conexion no fue exitosa");
		testCase.addQueryEvidenceCurrentStep(result);
		//Script Code Ends
		//Script Code Ends
	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Pedir a soporte L2 archivo de conciliación de ambiente productivo para pruebas más reales\r\n"
		+ "2. Contar con archivo de conciliación de proveedor en el buzón FTP en la ruta correspondiente\r\n"
		+ "3. Contar con acceso a la BD FCODS\r\n"
		+ "4. Contar con acceso a Control-M o pedir ticket para apoyo sobre ejecución de Jobs con soporte L3\r\n"
		+ "5. Contar con acceso a BD WMINT\r\n"
		+ "6. Contar con acceso a BD WMLOG"
		+ "7. Validar que el pre-requisito de contar con el archivo de conciliación se encuentre en el buzón, mediante ticket a equipo L3 WM\r\n"
		+ "NOTA: El archivo deberá de contar en un nombre la fecha actual de cuando se ejecutara la interfaz";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Monica Soto";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_111_EjecutarJobDeLaInterfazEB1DeProveedorBAZYValidarEjecucionExitosaDeLaInterfazEnBD";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida que la ejecución de los jobs creados para cada proveedor se ejecute correctamente";
	}
}