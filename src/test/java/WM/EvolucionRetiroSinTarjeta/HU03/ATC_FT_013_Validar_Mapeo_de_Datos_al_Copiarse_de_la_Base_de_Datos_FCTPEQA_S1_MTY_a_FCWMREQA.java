package WM.EvolucionRetiroSinTarjeta.HU03;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import WM.EvolucionRetiroSinTarjeta.migraciones.om.tablas.Tabla;
import WM.EvolucionRetiroSinTarjeta.migraciones.om.tablas.TableCompare;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class ATC_FT_013_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCTPEQA_S1_MTY_a_FCWMREQA extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCTPEQA_S1_MTY_a_FCWMREQA_test(HashMap<String, String> data)
	throws Exception{

		/**
		 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC
		 * Desc:
		 * Validar Mapeo de datos al copiarse de la base de datos FCTPEQA_S1_MTY a FCWMREQA
		 * @author Mónica Soto
		 * @date 22/01/2024
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

		// Variables
		String idTabla = "FOLIO";
		String idTabla1 = "FOLIO";
		
		ArrayList<String> listaColumnas = new ArrayList<>();
		List<String[]> totalfilaList = new ArrayList<>();
		
		ArrayList<String> listaColumnas1 = new ArrayList<>();
		List<String[]> totalfilaList1 = new ArrayList<>();
		
		/* variable Fecha_copiada y fecha_particiones */
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
		// y NEW_FORMAT_PARTITIONS es el formato necesario para las particiones
		final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
		final String NEW_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT_PARTITIONS = "yyyy-MM-dd";
 
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
				
		// fecha de particiones
		// se hace lo mismo pero ahora para el formato requerido para las particiones
		SimpleDateFormat sdf_particiones = new SimpleDateFormat(NEW_FORMAT);
		Date d_particiones = sdf_particiones.parse(fechaCopiada);
		sdf_particiones.applyPattern(NEW_FORMAT_PARTITIONS);
		oldDateString = sdf_particiones.format(d_particiones);
		
		// se reducen 60 dias para la fecha de particiones
		SimpleDateFormat format_particiones = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(format_particiones.parse(oldDateString));
		calendar.add(Calendar.DATE, -60);
		oldDateString = format_particiones.format(calendar.getTime());
		
		// se eliminan los - que no son necesarios
		oldDateString = oldDateString.replace("-", "");
		
		testCase.setTest_Description(data.get("id") + data.get("description"));
		testCase.setPrerequisites("La tabla 'TPEUSER.TPE_FR_TRANSACTION' debe de estar en la BD FCTPEQA_S1_MTY y la tabla 'TPEREP.TPE_FR_TRANSACTION_RST' debe de existir en la BD FCWMREQA");
		
		System.out.println(
				"*****************************************PASO 1 y 2************************************************");

		String consulta_FCTPEQA_S1_MTY_count = "SELECT COUNT(*) FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		
		String consulta_RBDTPE_FCTPEQA_S1_MTY = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, CREATION_DATE, PLAZA, TIENDA, RETEK_CR, \r\n"
				+ "COMPANY, STATE, MTI FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND rownum < 10 AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_RBDTPE_FCTPEQA_S1_MTY);
		
		SQLResult Execute_RBDTPE_Query_Count = executeQuery(dbFCTPEQA_S1_MTY, consulta_FCTPEQA_S1_MTY_count);
		SQLResult Execute_RBDTPE_Query = executeQuery(dbFCTPEQA_S1_MTY, consulta_RBDTPE_FCTPEQA_S1_MTY);

		boolean Execute_FCTPEQA_S1_MTY_QueryCount  = true;
		Execute_FCTPEQA_S1_MTY_QueryCount  = consulta_FCTPEQA_S1_MTY_count.isEmpty();

		addStep("Ingresar a la BD de FCTPEQA_S1_MTY");
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA_S1_MTY ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);
		
		addStep("Realizar la siguiente consulta para validar el total de registros MRC y MPC del 'dd/mm/yy' (fecha Solicita)");
		testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query_Count);
		
		assertFalse(Execute_FCTPEQA_S1_MTY_QueryCount, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 3************************************************");

		addStep("Realizar la siguiente consulta para Validar y guardar los datos de los registros MRC y MPC realizando la siguiente consulta en la tabla TPE_FR_TRANSACTION");
	
		testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query);		
		
		listaColumnas = (ArrayList<String>) Execute_RBDTPE_Query.getColumnNames();
		totalfilaList = Execute_RBDTPE_Query.getAllData();
		
		System.out.println(
				"*****************************************PASO 4 y 5 ************************************************");		
		
		String consulta_RBDREP_dbFCWMREQA = "SELECT COUNT(*) FROM TPEREP.TPE_FR_TRANSACTION_RST WHERE APPLICATION IN('MRC','MPC') AND WM_STATUS_FT IN('L') AND WM_STATUS_WO01 IN ('L') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA);

		SQLResult Execute_RDBREP_Query = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA);
		
		boolean conexiondbFCWMREQAREG  = true;
		conexiondbFCWMREQAREG  = Execute_RDBREP_Query.isEmpty();
		
		addStep("Ingresar a la BD de FCWMREQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
		
		addStep("Realizar la siguiente consulta para Validar que no haya registros MRC y MPC del 'dd/mm/yy' (fecha Solicita)");
		testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query);

		assertFalse(conexiondbFCWMREQAREG, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 6************************************************");

		String consulta_configdate_dbFCWMREQA = "SELECT FECHA_COPIADA, FECHA_TRUNCADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";
		System.out.println("Consulta: " + consulta_configdate_dbFCWMREQA);

		SQLResult Execute_Configdate_Query = executeQuery(dbFCWMREQA, consulta_configdate_dbFCWMREQA);
		
		boolean conexiondbFCWMREQA_configdate  = true;
		conexiondbFCWMREQA_configdate  = Execute_Configdate_Query.isEmpty();
		
		addStep("Realizar la siguiente consulta para Validar información de las fechas en los campos FECHA_COPIADA (Fecha Copiada Anterior SYSDATE-2) y FECHA_TRUNCADA (SYSDATE-63)");
		testCase.addQueryEvidenceCurrentStep(Execute_Configdate_Query);

		assertFalse(conexiondbFCWMREQA_configdate, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 7************************************************");

		String consulta_partition_dbFCWMREQA = "SELECT TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_"+oldDateString+"'";
		System.out.println("Consulta: " + consulta_partition_dbFCWMREQA);

		SQLResult Execute_partition_Query = executeQuery(dbFCWMREQA, consulta_partition_dbFCWMREQA);
		
		boolean conexiondbFCWMREQA_partition  = true;
		conexiondbFCWMREQA_partition  = Execute_partition_Query.isEmpty();
		
		addStep("Realizar la siguiente consulta para Validar que exista la partición RST_TRANS_(yyyymmdd) La fecha actual menos 60 días");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
		testCase.addQueryEvidenceCurrentStep(Execute_partition_Query);

		assertFalse(conexiondbFCWMREQA_partition, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 8, 9 y 10************************************************");
		
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
		addStep("Se ejecuta de manera exitosa el job runTPECopiaRST");
		addStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit();
				 
		System.out.println(
				"*****************************************PASO 11 y 12************************************************");

		String consulta_updated_dbFCWMREQA = "SELECT FECHA_COPIADA, FECHA_TRUNCADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";
		System.out.println("Consulta: " + consulta_updated_dbFCWMREQA);

		SQLResult Execute_updated_Query = executeQuery(dbFCWMREQA, consulta_updated_dbFCWMREQA);
					
		boolean conexiondbFCWMREQA_updated  = true;
		conexiondbFCWMREQA_updated  = Execute_updated_Query.isEmpty();
					
		addStep("Ingresar a la BD de FCWMREQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
		
		addStep("Realizar la siguiente consulta para Validar actualizacion de las fechas en los campos FECHA_COPIADA FECHA_COPIADA ( A la Fecha Copiada SYSDATE -1) y FECHA_TRUNCADA(A la Fecha Eliminada por el  depurado 60 días antes a la Fecha Copiada SYSDATE-60)");
		testCase.addQueryEvidenceCurrentStep(Execute_updated_Query);

		assertFalse(conexiondbFCWMREQA_updated, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 13************************************************");

		addStep("Realizar la siguiente consulta para Validar Validar que no exista la partición RST_TRANS_(yyyymmdd) La fecha actual menos 60 días");
		String consulta_partitiondep_dbFCWMREQA = "SELECT TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPEREP.TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_"+oldDateString+"'";
		System.out.println("Consulta: " + consulta_partitiondep_dbFCWMREQA);

		SQLResult Execute_partitiondep_Query = executeQuery(dbFCWMREQA, consulta_partitiondep_dbFCWMREQA);
		
		boolean conexiondbFCWMREQA_partitiondep  = true;
		conexiondbFCWMREQA_partitiondep  = Execute_partitiondep_Query.isEmpty();
		
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
		testCase.addQueryEvidenceCurrentStep(Execute_partitiondep_Query);

		System.out.println(
				"*****************************************PASO 14************************************************");

		addStep("Realizar la siguiente consulta despues de la ejecucion del job en la tabla TPE_CONTROL_COPIA para validar la informacion de los siguientes datos en los campos  STATUS: S (Successful), FECHAPROCESADA:(dd/mm/yy Debe ser la fecha Copiada), TABLA: TPE_FR_TRANSACTION, TOTALDESTINO y TOTALFUENTE: (debe ser el total de registros obtenidos de MRC y MPC del Paso 2).");
		String consulta_statusexe_dbFCWMREQA = "SELECT STATUS,TABLA, FECHAPROCESADA, TOTALDESTINO, TOTALFUENTE FROM TPEREP.TPE_CONTROL_COPIA WHERE TABLA IN('TPE_FR_TRANSACTION') AND TRUNC (FECHAPROCESADA) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_statusexe_dbFCWMREQA);

		SQLResult Execute_statusexe_Query = executeQuery(dbFCWMREQA, consulta_statusexe_dbFCWMREQA);
		
		boolean conexiondbFCWMREQA_statusexe  = true;
		conexiondbFCWMREQA_statusexe  = Execute_statusexe_Query.isEmpty();
		
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
		testCase.addTextEvidenceCurrentStep("Consulta: SELECT STATUS,TABLA, FECHAPROCESADA, TOTALDESTINO, TOTALFUENTE FROM TPEREP.TPE_CONTROL_COPIA WHERE TABLA IN('TPE_FR_TRANSACTION') AND TRUNC (FECHAPROCESADA) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')");

		assertFalse(conexiondbFCWMREQA_statusexe, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 15************************************************");
		
		addStep("Realizar la siguiente consulta para Validar que el total de registros MRC y MPC sean 19 del 'dd/mm/yy' (fecha Solicita)");
				
		String consulta_CountFCWMREQA = "SELECT COUNT(*) From TPEREP.TPE_FR_TRANSACTION_RST WHERE APPLICATION IN('MRC','MPC') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_CountFCWMREQA);

		SQLResult Execute_RBDTPE_Query2 = executeQuery(dbFCWMREQA, consulta_CountFCWMREQA);
		
		boolean conexiondbFCWMREQA2  = true;
		conexiondbFCWMREQA2  = Execute_RBDTPE_Query2.isEmpty();
		testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query2);
				
		assertFalse(conexiondbFCWMREQA2, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 16************************************************");

		addStep("Validar que existan los mismos datos de los registros que guardamos de tabla TPE_FR_ TRANSACTION en el paso 3 ahora en la en la tabla TPE_FR_TRANSACTION_RST despues de la ejecucion del job runTPECopiaRST.sh con la siguiente consulta");
		String consulta_TablaFCWMREQA = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, CREATION_DATE, PLAZA, TIENDA, RETEK_CR, \r\n"
				+ "COMPANY, STATE, MTI FROM TPEREP.TPE_FR_TRANSACTION_RST WHERE APPLICATION IN('MRC','MPC') AND rownum < 10 AND WM_STATUS_FT IN('L') \r\n"
				+ "AND WM_STATUS_WO01 IN ('L') AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		System.out.println("Consulta: " + consulta_TablaFCWMREQA);
				
		SQLResult ExecuteQueryRBDTPE_FCWMREQA = executeQuery(dbFCWMREQA, consulta_TablaFCWMREQA);
		boolean StatusQueryFCWMREQA = ExecuteQueryRBDTPE_FCWMREQA.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryRBDTPE_FCWMREQA);
		
		listaColumnas1 = (ArrayList<String>) ExecuteQueryRBDTPE_FCWMREQA.getColumnNames();
		totalfilaList1 = ExecuteQueryRBDTPE_FCWMREQA.getAllData();
		
		Tabla tabla = new Tabla(idTabla, listaColumnas, totalfilaList);
		Tabla tabla1 = new Tabla(idTabla1, listaColumnas1, totalfilaList1);
		TableCompare tc3 = new TableCompare();
		
		List<String[]> filasErroneas3 = tc3.compareTables(tabla, tabla1);
		filasErroneas3.forEach(a -> System.out.println(Arrays.toString(a)));

		boolean estruc3 = true;

		if (!filasErroneas3.isEmpty() == true) {
			testCase.addTextEvidenceCurrentStep("Filas que no coinciden debido a el filtro de BBVA de runTPECopiaRST");
			
			System.out.println("Filas que no coinciden ");
			filasErroneas3.forEach(a -> testCase.addTextEvidenceCurrentStep(Arrays.toString(a)));
			estruc3 = false;
		} else {
			testCase.addTextEvidenceCurrentStep("Filas si coinciden ");
			estruc3 = true;
		}
 
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Mapeo de datos al copiarse de la base de datos FCTPEQA_S1_MTY a FCWMREQA";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCTPEQA_S1_MTY_a_FCWMREQA_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "El TPECopiaRST debe estar instalado en QA para validar su primer ejecucion\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}