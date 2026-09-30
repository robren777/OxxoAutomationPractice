package WM.interfaces.ol9;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import WM.util.GlobalVariables;

/**
* DESCRIPCIÓN: Test Case de ejecución OL9,PROCESO PARA VALIDACIÓN NO AFECTACIÓN prevalidacion de existencia insumos, ejecución y validación de tablas outout.
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -Verificación de No Afectación en el flujo de envío estándar para Socios Comerciales existentes.
*
* PREREQUISITOS:
*     - Tener insumos en tablas PRE
*     - Acceso al aplicativo Control M
*     - Usar VPN Femsa.
*
* INSUMOS:
*     -Servicios con Null en sus campos estatus
*
* @author Ernesto Rosas.
* @date 18/Septiembre/2025
*/
public class ATC_FT_002_VerificaciondeNoAfectacionProcesoEnvio extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_VerificaciondeNoAfectacionProcesoEnvio_test(HashMap<String, String> data) throws Exception {
		
		String user_controlm_oxxPay = GlobalVariables.OXXO_PAY_USER_CONTROLM;
		String pwd_controlm_oxxPay = GlobalVariables.OXXO_PAY_PWD_CONTROLM;
		String query1 = data.get("query1");
		String query2 = data.get("query2");
//
//		// 1) PASO 1: Validar existencia de datos en tabla PRE, para correcto consumo de OL9

		SQLUtil dbLog = new SQLUtil(GlobalVariables.OXXO_PAY_BD_HOST,
				GlobalVariables.OXXO_PAY_BD_USER, GlobalVariables.OXXO_PAY_BD_PWD);


		addStep("Validar existencia de datos en tabla PRE para correcto consumo de OL9");
		SQLResult pre = dbLog.executeQuery(query1);
		testCase.addQueryEvidenceCurrentStep(pre);
		assertFalse(pre.isEmpty(), "No hay datos para procesado de info en XXFC_PAGO_SERVICIOS_PRE.");

//		// 2) PASO 2: Re-Run Job Control M 

		addStep("Invocar job de Control-M OL9 via UI");
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(data.get("server"));
		u.hardWait(30);
		u.waitForLoadPage();
		


		Control_mInicio cm = new Control_mInicio(u, user_controlm_oxxPay, pwd_controlm_oxxPay);
		cm.logOn();


		// job1 viene como JSON en una sola celda de Excel
		JSONObject jobDef = new JSONObject(data.get("job_json"));
		ControlM job = new ControlM(u, testCase, jobDef);

		boolean found = job.searchJob();
		assertTrue(found, "No se encontró el job: " + jobDef);


		String result = job.executeJob(); // típico: "Ended OK"
		testCase.addTextEvidenceCurrentStep("Resultado: " + result + " | Output: " + job.getOutput());
		assertEquals(result, "Ended OK", "El job no terminó en OK.");

		job.closeViewpoint();
		u.hardWait(2);
		u.close();
//
//		 //3) PASO 3: Revisión de datos modificados y creados por la OL9
		addStep("Validar nuevos registros en tabla outbound para asegurar correcta creacion de archivos.");
		
		SQLResult post = dbLog.executeQuery(query2);
		testCase.addQueryEvidenceCurrentStep(post);
		assertFalse(post.isEmpty(), "No hay corrida registrada hoy en WM_LOG_RUN.");

		dbLog.close();

}

	@Override
	public void beforeTest() {
	    String path = "Config/Drivers/MEdge/Windows/140.exe";
	    System.setProperty("webdriver.edge.driver", new java.io.File(path).getAbsolutePath());
	}

	@Override
	public String setTestDescription() {
		return "OL9: - PROCESO PARA VALIDACIÓN NO AFECTACIÓN - Ejecutar job de Control-M y validar correcta ejecucion de OL9.";
	}

	@Override
	public String setTestDesigner() {
		return "Ernesto Rosas";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Verificación_de_No_Afectacion_Proceso_Envio";
	}

	@Override
	public String setTestInstanceID() {
		return "OL9-OL9-002";
	}

	@Override
	public String setPrerequisites() {
		return "1) Excel con columnas BD 2 VPN y accesos a BD Job configurado y 3 ordenado en Control-M.";
	}
	
}
