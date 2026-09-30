package EBS.neteos.NeteosCedis;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con devoluciones y cargos logisticos
 *
 * DESCRIPCION: realiza una depuracion de devoluciones mediante la ejecucion de un JOB en las tablas XXFC.XXFC_XP_INTERFACE_SALESCREDITS y XXFC.XXFC_XP_INTERFACE_LINES 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_011_DepuracionDevolucionesyCargosLogisticosCEDISJOB extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_DepuracionDevolucionesyCargosLogisticosCEDISJOB_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		// vars CONTROL-M
		String controlMUser = data.get("user");
		String controlMPass = data.get("ps");
		String controlMServer = data.get("server");
		
		// BASES DE DATOS
		
		String tabla1 = "select INTERFACE_SALESCREDIT_ID,INTERFACE_LINE_ID,INTERFACE_LINE_CONTEXT,INTERFACE_LINE_ATTRIBUTE1 from XXFC.XXFC_XP_INTERFACE_SALESCREDITS where INTERFACE_LINE_ATTRIBUTE1 = 'NC_15CMF_175437'";
		String tabla2 = "select INTERFACE_LINE_CONTEXT,INTERFACE_LINE_ATTRIBUTE1,INTERFACE_LINE_ATTRIBUTE2,INTERFACE_LINE_ATTRIBUTE5 from XXFC.XXFC_XP_INTERFACE_LINES where INTERFACE_LINE_ATTRIBUTE1 = 'NC_15CMF_175437'";
		

		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

        addStep("Prerrequisitos");
		
		System.out.println(tabla1);
		SQLResult validat0 = AVANTE.executeQuery(tabla1);
			testCase.addQueryEvidenceCurrentStep(validat0);
		
		//-------------------------------------------------------------------------------------------

		System.out.println(tabla2);
		SQLResult validat01 = AVANTE.executeQuery(tabla2);
			testCase.addQueryEvidenceCurrentStep(validat01);

		
		// PASO 1
		addStep("Ejecutar el siguiente job: XXFC_AP_DEPURACION_DEVS_SH");
	    System.out.println("PASO 1");
					
		JSONObject obj = new JSONObject(data.get("jobs"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
					
		u.get(controlMServer);
		u.waitForLoadPage();	
		u.hardWait(40);
		cm.logOn();
		u.hardWait(4);
		ControlM control = new ControlM(u, testCase, obj); 
		control.searchJob(); // busca el job
				
		String resultadoEjecucion = control.executeJob(); 
		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), "El job XXFC_AP_DEPURACION_DEVS_SH no se ejecuto correctamente");
					
		// VALIDAR TABLAS
		// PASO 2
		addStep("Validar Depuracion en tablas");
		
		System.out.println(tabla1);
		SQLResult validat1 = AVANTE.executeQuery(tabla1);

			testCase.addQueryEvidenceCurrentStep(validat1);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");

		
		//-------------------------------------------------------------------------------------------

		System.out.println(tabla2);
		SQLResult validat2 = AVANTE.executeQuery(tabla2);

			testCase.addQueryEvidenceCurrentStep(validat2);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");
		
		//-------------------------------------------------------------------------------------------
		
        u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_DepuracionDevolucionesyCargosLogisticosCEDISJOB_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se eliminen los registros que no son Cargos Logísticos de las tablas stage:";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Exista informacion de Cargos Logísticos y otros en las tablas:";
	}

	@Override
	public void beforeTest() {

	}
}
