package WM.EvolucionRetiroSinTarjeta.HU23;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;

public class ATC_FT_004_Validar_ejecucion_de_runTPECopia_y_que_no_se_genere_la_replica_del_banco_BBVA extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Validar_ejecucion_de_runTPECopia_y_que_no_se_genere_la_replica_del_banco_BBVA_test (HashMap<String, String> data)
	throws Exception{

		/**
		 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC
		 * Desc:
		 * Validar ejecución de runTPECopia y que no se genere la réplica del banco BBVA
		 * @author Mónica Soto
		 * @date 26/03/2024
		 **/
		
		/*
		 * Utilerias // conexiones a las bases de datos
		 *********************************************************************/
		//BD FCWMREQA
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
				GlobalVariables.DB_PASSWORD_FCWMREQA);
		//BD FCTPEQA_S1_MTY
		SQLUtil dbFCTPEQA_S1_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);

		/*
		 * Variables
		 ******************************************************************************************/
		/* variable Fecha_copiada */
		//por medio de una consulta, se obtiene el valor actual de la columna FECHA_COPIADA
		String consulta_FechaCopiada = "SELECT FECHA_COPIADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST')";
		SQLResult fechaResult = executeQuery(dbFCWMREQA, consulta_FechaCopiada);

		//en el string va a guardar el valor obtenido de la consulta 
		String fechaCopiada = null;
		boolean info = fechaResult.isEmpty();
		if (!info) {
			fechaCopiada = fechaResult.getData(0, "FECHA_COPIADA");
		} else {
			System.out.println("No se tiene información");
		}

		// estos son los formatos de fecha requeridos, OLD_FORMAT es el formato con el que estoy recibiendo la consulta
		// NEW_FORMAT es el formato que se ocupa para las consultas que ocupan la fecha a copiar
		final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
		final String NEW_FORMAT = "dd/MM/yyyy";

		// en este string se guardara la fecha_copiada anterior
		String oldDateString = fechaCopiada;

		// aqui se le cambiará el formato deseado a fechaCopiada
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		sdf.applyPattern(NEW_FORMAT);
		fechaCopiada = sdf.format(d);

		//sumatoria de 1 dia para el valor de "fechaCopiada"
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(fechaCopiada));
		calendar.add(Calendar.DATE, 1);
		fechaCopiada = format.format(calendar.getTime());

		testCase.setTest_Description(data.get("id") + data.get("description"));
		testCase.setPrerequisites("La tabla TPE_FR_TRANSACTION_RST debe de existir en FCWMREQA.");

		System.out.println(
				"*****************************************PASO 1 y 2, 3************************************************");
		String consulta_RBDTPE_FCTPEQA_S1_MTY = "SELECT COUNT (*) FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND TRUNC (CREATION_DATE) = TO_DATE('"+fechaCopiada+"', 'DD/MM/YY')";

		System.out.println("Consulta: " + consulta_RBDTPE_FCTPEQA_S1_MTY);

		SQLResult Execute_RBDTPE_Query = executeQuery(dbFCTPEQA_S1_MTY, consulta_RBDTPE_FCTPEQA_S1_MTY);

		boolean conexiondbFCTPEQA_S1_MTY  = true;
		conexiondbFCTPEQA_S1_MTY  = Execute_RBDTPE_Query.isEmpty();

		addStep("Ingresar a la BD de FCTPEQA_S1_MTY");
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA_S1_MTY ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);

		addStep("Realizar la siguiente consulta para validar el total de registros de MRC y MPC en Estatus OPERATION = 'TRN03', WM_CODE = '101' (Exitoso) del 'dd/mm/yy' (Fecha a Copiar)");
		testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query);
		assertFalse(conexiondbFCTPEQA_S1_MTY, "La conexion no fue exitosa");


		////////////////////// paso 003

		String consulta_RBDTPE_ENTITY = "SELECT ENTITY, count (*) FROM TPEUSER.TPE_FR_TRANSACTION WHERE application in ('MRC', 'mpc') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY') group by ENTITY";
		System.out.println("Consulta: " + consulta_RBDTPE_ENTITY);
		SQLResult Execute_RBDTPE_Query_ENTITY = executeQuery(dbFCTPEQA_S1_MTY, consulta_RBDTPE_ENTITY);

		boolean conexiondbFCTPEQA_S1_MTY_ENTITY  = true;
		conexiondbFCTPEQA_S1_MTY_ENTITY  = Execute_RBDTPE_Query_ENTITY.isEmpty();

		addStep("Realizar la siguiente consulta para validar el total de registros de MRC y MPC en Estatus OPERATION = 'TRN03', WM_CODE = '101' (Exitoso) del 'dd/mm/yy' (Fecha a Copiar)");
		testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query_ENTITY);
		assertFalse(conexiondbFCTPEQA_S1_MTY_ENTITY, "La conexion no fue exitosa");

		System.out.println(
				"*****************************************PASO 4 y 5************************************************");

		String consulta_RBDREP_dbFCWMREQA = "SELECT ID, TABLA, STATUS, TABLA_STATUS, TIPO, FECHA_COPIADA, OWNER, DBLINK, DIAS_ESPACIO_DEPURACION FROM TPEREP.TPE_TABLAS_COPIA WHERE status = 'P' and tabla_status = 'A'";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA);

		SQLResult Execute_RDBREP_Query = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA);

		boolean conexiondbFCWMREQAREG  = true;
		conexiondbFCWMREQAREG  = Execute_RDBREP_Query.isEmpty();

		addStep("Ingresar a la BD de FCWMREQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

		addStep("Se valida la configuracion de RST (TPE_FR_TRANSACTION)");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query);

		assertFalse(conexiondbFCWMREQAREG, "La conexion no fue exitosa");

		System.out.println(
				"*****************************************PASO 6, 7************************************************");
		addStep("Ejecutar el job runTPECopiaRST");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job= new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();

		assertTrue(flag);

		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());

		assertEquals(resultado1, "Ended OK");

		job.closeViewpoint();
		u.quit();

		addStep("Se ejecuta de manera exitosa el job runTPECopiaRST");

		System.out.println(
				"*****************************************PASO 8 y 9************************************************");

		String consulta_RBDREP_dbFCWMREQA2 = "select RUN_ID, STATUS, FECHAINI, FECHAFIN, TABLA, TOTALDESTINO, TOTALFUENTE, MENSAJE_ERROR from TPEREP.tpe_control_copia order by run_id desc fetch first 5 rows only";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA2);

		SQLResult Execute_RDBREP_Query2 = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA2);

		boolean conexiondbFCWMREQA  = true;
		conexiondbFCWMREQA  = Execute_RDBREP_Query2.isEmpty();

		addStep("Ingresar a la BD de FCWMREQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

		addStep("Se valida que el registro más reciente tiene el status en 'S'");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query2);

		assertFalse(conexiondbFCWMREQA, "La conexion no fue exitosa");

		System.out.println(
				"*****************************************PASO 10, 11 y 12************************************************");

		String consulta_RBDREP_dbFCWMREQA3 = "SELECT COUNT (*) From TPEREP.TPE_FR_TRANSACTION_RST WHERE  OPERATION = 'TRN03' AND WM_CODE = '101' AND WM_STATUS_WO01='L' AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA3);
		SQLResult Execute_RDBREP_Query3 = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA3);

		boolean conexiondbFCWMREQA3  = true;
		conexiondbFCWMREQA3  = Execute_RDBREP_Query3.isEmpty();

		addStep("Se valida que el total de registros copiados estén en estatus OPERATION = 'TRN03', WM_CODE = '101' (Exitoso)y WM_STATUS_WO01='L'(Listo)  del 'dd/mm/yy' (Fecha a Copiar).");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query3);

		assertFalse(conexiondbFCWMREQA3, "La conexion no fue exitosa");


		///////////// paso 011

		String consulta_RBDREP_dbFCWMREQA4 = "SELECT ENTITY, COUNT(*) FROM TPEREP.TPE_FR_TRANSACTION_RST WHERE application in ('MRC', 'mpc') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY') group by ENTITY";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA4);
		SQLResult Execute_RDBREP_Query4 = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA4);

		boolean conexiondbFCWMREQA4  = true;
		conexiondbFCWMREQA4  = Execute_RDBREP_Query4.isEmpty();

		addStep("Validar se copien los registros en la tabla TPEREP.TPE_FR_TRANSACTION_RST únicamente de los proveedores que se tienen configurados  de la fecha copiada.");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query4);

		assertFalse(conexiondbFCWMREQA4, "La conexion no fue exitosa");


		////////////////////// paso 012

		String consulta_RBDREP_dbFCWMREQA5 = "SELECT ID, TABLA, STATUS, TABLA_STATUS, TIPO, FECHA_COPIADA, OWNER, DBLINK, DIAS_ESPACIO_DEPURACION FROM TPEREP.TPE_TABLAS_COPIA WHERE status = 'P' and tabla_status = 'A'";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA5);
		SQLResult Execute_RDBREP_Query5 = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA5);

		boolean conexiondbFCWMREQA5  = true;
		conexiondbFCWMREQA5  = Execute_RDBREP_Query5.isEmpty();

		addStep("Se actualiza la fecha de copia");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query5);

		assertFalse(conexiondbFCWMREQA5, "La conexion no fue exitosa");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar ejecución de runTPECopia y que no se genere la réplica del banco BBVA";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Validar_ejecucion_de_runTPECopia_y_que_no_se_genere_la_replica_del_banco_BBVA_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla TPE_FR_TRANSACTION_RST debe de existir en FCWMREQA.";
	}

	@AfterMethod
	public void afterMethod() {

	}
}
