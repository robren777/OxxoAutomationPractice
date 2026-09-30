package RDM.rdmBara.proyectoBaraCHG51952391;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import RDM.util.MetodoAdicional;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_EjecutarJOB_ProyectoBara extends BaseExecution{

	/**
	 * REREQUISITOS: 
	 *		1. Acceso a la base de datos Bara: 
	 *			Connection Name: BARA QA
	 *			Hostname: fcrdm05c.femcom.net
	 *			Port: 1535
	 *			SID: FCRDM59Q
	 * 		2. Acceso a control M
	 *      3. Solicitar el ordenamiento del JOB con estatus Hold: 
	 *          JOB NAME: fem_pick_confirm
	 *			Folder: TQA_CEDISBAR 
	 * 
	 * DESCRIPCION: El objetivo de la prueba es realizar validaciones en la BD: RDM BARA QA previas a la ejecución del JOB
	 * y posteriores a su ejecución
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_EjecutarJOB_ProyectoBara_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * 
		 * user = usuario de acceso a Control M
		 * password = contraseña encriptada para acceder a Control M
		 * registroContenedor = contenedor a validar
		 * facility = valor de facility
		 * distro = valor de distro
		 * job = información del job
	    */
		//String server = data.get("server");
		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String registroContenedor = data.get("registroContenedor");
		String facility = data.get("facility");
		String distro = data.get("distro");
		
		MetodoAdicional metodo= new MetodoAdicional();
		utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
		String job1=data.get("job1");
		String job2=data.get("job2");
		String job3=data.get("job3");
		String job4=data.get("job4");
		String job5=data.get("job5");
		SeleniumUtil u = new SeleniumUtil (new MEdgeTest(), true);
		
		u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.CtrlM_URL);
		u.getDriver().manage().window().maximize();
		
		String queryCONTAINER_ITEM1 = "Select FACILITY_ID, CONTAINER_ID,"
				+ " ITEM_ID, DISTRO_NBR, CONTAINER_QTY FROM RDM100.CONTAINER_ITEM"
	   			+ " WHERE CONTAINER_ID= '"+registroContenedor+"'";
		
		String queryPICK_DIRECTIVE1 = "Select FACILITY_ID, WAVE_NBR, "
				+ " DISTRO_NBR, PICK_FROM_CONTAINER_ID, DEST_ID, "
				+ "PICK_TO_CONTAINER_ID, ITEM_ID "
				+ "FROM RDM100.PICK_DIRECTIVE "
				+ "WHERE FACILITY_ID= '"+facility+"' "
				+ "AND DISTRO_NBR= '"+distro+"'";
		String queryPICK_CONFIRM1 = "Select DISTRO_NBR"
				+ " ,PICK_TO_CONTAINER_ID"
				+ " ,ITEM_ID"
				+ " ,FACILITY_ID"
				+ " ,WAVE_NBR"
				+ " ,ERROR_CODE"
				+ " ,ESTATUS"
				+ " ,CONFIRM_DATE_TS"
				+ " from RDM100.FEM_PICK_POR_CONFIRM"
				+ " WHERE DISTRO_NBR = '"+distro+"' "
				+ " AND PICK_TO_CONTAINER_ID = '"+registroContenedor+"'";

		String queryCONTAINER1="SELECT FACILITY_ID,CONTAINER_ID"
				+ " ,CONTAINER_TYPE"
				+ " ,CONTAINER_STATUS"
				+ " ,LOCATION_ID"
				+ " ,MASTER_CONTAINER_ID"
				+ " ,APPT_NBR"
				+ " ,APPT_LINE"
				+ " FROM RDM100.CONTAINER"
				+ " WHERE CONTAINER_ID IN"
				+ " (SELECT PICK_TO_CONTAINER_ID"
				+ " FROM RDM100.PICK_DIRECTIVE"
				+ " WHERE FACILITY_ID='"+facility+"'"
				+ " AND DISTRO_NBR IN('"+distro+"')) ";
		/*
		 * PASO 1:Consultar tabla: contenedor de la tabla: CONTAINER_ITEM
		*/
		
		addStep("Consultar tabla CONTAINER_ITEM para validar que no existan registros para el contenedor: "+registroContenedor);
		utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(queryCONTAINER_ITEM1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		assertTrue(resultadoContenedor1.isEmpty(), "Se encontraron datos en el contenedor:"+registroContenedor);
		
		
		/*
		 * PASO 2:Consultar tabla PICK_DIRECTIVE 
		*/
		
		addStep("Consultar tabla PICK_DIRECTIVE para obtener las tareas a confirmar");
		utils.sql.SQLResult resultadoPick1= dbBARAqa.executeQuery(queryPICK_DIRECTIVE1);
		testCase.addQueryEvidenceCurrentStep(resultadoPick1);
		//assertFalse(resultadoPick1.isEmpty(), "No se encontraron registros de tareas a confirmar");
		
		/*
		 * PASO 3:Consultar tabla FEM_PICK_POR_CONFIRME 
		*/
	

		addStep("Consultar tabla FEM_PICK_POR_CONFIRM para validar que los registros del contenedor: "+registroContenedor+" tengan STATUS y CONFIRM_DATE_TS con valor NULL");
		utils.sql.SQLResult resultadoPickConfirm1= dbBARAqa.executeQuery(queryPICK_CONFIRM1);
		testCase.addQueryEvidenceCurrentStep(resultadoPickConfirm1);
		boolean bandera= metodo.validarEstatus_Date(resultadoPickConfirm1);
		assertTrue(bandera, "Los registros del contenedor: "+registroContenedor+" muestran datos en STATUS y CONFIRM_DATE_TS");
		/*
		 * PASO 4:Consultar tabla CONTAINER
		*/

		addStep("Consultar estatus con valor P en la tabla CONTAINER del contenedor: "+registroContenedor);
		utils.sql.SQLResult resultadoContainer1= dbBARAqa.executeQuery(queryCONTAINER1);
		testCase.addQueryEvidenceCurrentStep(resultadoContainer1);
		bandera= metodo.validarEstatusContainer(resultadoContainer1);
		assertTrue(bandera, "El contenedor: "+registroContenedor+" no se encuentra en estatus P");
		/*
		 	* PASO 5:EJECUTAR JOB
		*/
		Control_mInicio cm = new Control_mInicio(u, user, password);
		JSONObject jobFEM1 = new JSONObject(job1);
		JSONObject jobFEM2 = new JSONObject(job2);
		JSONObject jobFEM3 = new JSONObject(job3);
		JSONObject jobFEM4 = new JSONObject(job4);
		JSONObject jobFEM5 = new JSONObject(job5);
		JSONObject jobArray[]= {jobFEM1, jobFEM2, jobFEM3, jobFEM4, jobFEM5};
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();
		u.hardWait(15);
		int contador=0;
		
		do {
			//JobManagement jm= new JobManagement(u,testCase, array);
			ControlM jobCM = new ControlM(u, testCase, jobArray[contador]);
			addStep("Ejecución del job FEM_PICK_POR_CONFIRM "+(contador+1));
			u.hardWait(4);
			bandera = jobCM.searchJob();
			assertTrue(bandera, "No fue posible encontrar el JOB");
			u.hardWait(3);
			
			String resultado = jobCM.executeJob();
			u.hardWait(120);
			
			addStep("Resultado de la ejecucion: " + resultado);
			System.out.println("Resultado de la ejecucion -> " + resultado);
			testCase.addBoldTextEvidenceCurrentStep("Output: ");
			System.out.println("Valor de output :" + jobCM.getOutput());
			testCase.addTextEvidenceCurrentStep(jobCM.getOutput());
			if (jobCM.getOutput().toLowerCase().contains("error")) {
				throw new AssertionError("Existe un error en el Output");
			}
			assertEquals(resultado, "Wait Condition");

			u.hardWait(3);
			
			jobCM.closeViewpoint();
			contador ++;
		}while(contador<5);
		
		/*
	 	* PASO 6:REVISAR TABLA CONTAINER
	 	*/
		addStep("Consultar tabla CONTAINER_ITEM para validar que se insertaron los contenedores con las unidades de los articulos surtidos");
		utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(queryCONTAINER_ITEM1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor2);
		assertFalse(!resultadoContenedor1.isEmpty(), "No se encontraron datos en el contenedor:"+registroContenedor);

		/*
		 * PASO 7:Consultar tabla PICK_DIRECTIVE 
		*/
		addStep("Consultar tabla PICK_DIRECTIVE para validar que se borraron los registros correspondientes a los artículos confirmados");
		utils.sql.SQLResult resultadoPick2= dbBARAqa.executeQuery(queryPICK_DIRECTIVE1);
		testCase.addQueryEvidenceCurrentStep(resultadoPick2);
		//assertFalse(resultadoPick1.isEmpty(), "No se encontraron registros de tareas a confirmar");
		/*
		 * PASO 8:Consultar tabla FEM_PICK_POR_CONFIRME 
		*/
		
		String queryPICK_CONFIRM5 = "Select DISTRO_NBR"
				+ " ,PICK_TO_CONTAINER_ID"
				+ " ,ITEM_ID"
				+ " ,FACILITY_ID"
				+ " ,WAVE_NBR"
				+ " ,ERROR_CODE"
				+ " ,ESTATUS"
				+ " ,CONFIRM_DATE_TS"
				+ " from RDM100.FEM_PICK_POR_CONFIRM"
				+ " WHERE DISTRO_NBR = '"+distro+"' "
				+ " AND PICK_TO_CONTAINER_ID = '"+registroContenedor+"'";
		
		addStep("Consultar tabla FEM_PICK_POR_CONFIRM para validar que los registros del contenedor tengan STATUS = 'SUCCESS' y CONFIRM_DATE_TS con fecha actual de la ejecución");
		utils.sql.SQLResult resultadoPickConfirm2= dbBARAqa.executeQuery(queryPICK_CONFIRM5);
		testCase.addQueryEvidenceCurrentStep(resultadoPickConfirm2);
		bandera= metodo.validarEstatus_DespuesDelJOB(resultadoPickConfirm2);
		assertTrue(bandera, "No se encontro estatus SUCCESS en todos los registros del contenedor: "+registroContenedor);
		bandera= metodo.validarFecha_DespuesDelJOB(resultadoPickConfirm2);
		assertTrue(bandera, "No se registro informacion en la fecha");	
		
		u.close();
	}
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la base de datos: BARA QA y al aplicativo Control M";
	}

	@Override
	public String setTestDescription() {
		return "Validar ejecucion de JOB sin interrupcion";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_EjecutarJOB_ProyectoBara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
