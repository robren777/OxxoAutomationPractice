package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_08_CargaDeInformacionALasTablasIntermedias extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_08_CargaDeInformacionALasTablasIntermedias_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String idFile = "23";//data.get("idFile");
		String estado = data.get("estado");
		String fileName = data.get("fileName");
		JSONObject obj = new JSONObject(data.get("job"));
		String tablaArcsBanca = data.get("tabla_KYRIBA_ARCS_BANCA");
		String tablaEdosBanca = data.get("tabla_KYRIBA_EDOS_BANCA");
		
		//String consultaArcaBanca1 = "SELECT ID_FILE, NAME_FILE FROM APPS."+tablaArcsBanca+" WHERE ID_FILE = "+idFile+"  ORDER BY CREATION_DATE DESC";
		//String consultaArcaBanca2 = "SELECT ID_MOVTO, CREATION_DATE FROM APPS."+tablaArcsBanca+" WHERE ID_FILE = "+idFile+"  ORDER BY CREATION_DATE DESC";
	
		String consultaArcaBanca1 = "SELECT ID_FILE, ID_MOVTO, CREATION_DATE FROM APPS."+tablaArcsBanca+" WHERE TRUNC(CREATION_DATE) = TRUNC(SYSDATE) ORDER BY CREATION_DATE DESC";
		String consultaArcaBanca2 = "SELECT NAME_FILE FROM APPS."+tablaArcsBanca+" WHERE TRUNC(CREATION_DATE) = TRUNC(SYSDATE) ORDER BY CREATION_DATE DESC";
	
		
		String consultaEdosBanca = "SELECT STATEMENT_NUMBER, ESTADO, CREATION_DATE FROM APPS."+tablaEdosBanca+" WHERE FILE_NAME LIKE '%s'";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
	    ControlM controlM = new ControlM(u, testCase, obj);
		
		utils.sql.SQLUtil EBSOIEBSBDQ = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_OIEBSBDQ, GlobalVariables.DB_USER_OIEBSBDQ, GlobalVariables.DB_PASSWORD_OIEBSBDQ);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: KYRIBA INTERNACIONAL
		 */
		
		// PASO 1
		addStep("Ejecutar el proceso 'Kyriba pre-carga de estados de cuenta' mediante el job '"+obj.getString("job")+"'");//XXPE_CE_KYRIBA_IN_ECTA_SH_PERU'");
		testCase.addTextEvidenceCurrentStep(obj.getString("job"));
        u.get(GlobalVariables.CONTROL_M_SERVER);
        u.hardWait(50);
        u.waitForLoadPage();
        CM.logOn();
        testCase.addScreenShotCurrentStep(u, "control m inicio");
        boolean flag = controlM.searchJob();
        assertFalse(!flag, "No se encontro el job");

        System.out.println("Job Status " + controlM.getCurrentStatus());
        //Ejecucion
        String resultadoDelJon = controlM.executeJob();
        testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " + resultadoDelJon);
        System.out.println("Resultado de la ejecucion " + resultadoDelJon);

        //Valor del output
        testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
        System.out.println("Output " + controlM.getOutput());

        //Validacion del caso
        if (controlM.getOutput().toLowerCase().contains("error")) {
        	testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job");
			throw new AssertionError("Existe un error en el Output");			
		}
        
		assertEquals(resultadoDelJon, "Wait Condition");
        controlM.closeViewpoint();
        u.hardWait(60);
		
		// PASO 2
		addStep("Ingresar a la base de datos de EBS INTERNACIONAL");
		u.close();
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Conexion a Base de Datos: " + GlobalVariables.DB_HOST_OIEBSBDQ);
        testCase.addTextEvidenceCurrentStep("Usuario: "+ GlobalVariables.DB_USER_OIEBSBDQ);
        testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + EBSOIEBSBDQ.getConn());
        
		// PASO 3
		addStep("Consultar la tabla "+tablaArcsBanca);
		System.out.println(consultaArcaBanca1);
		SQLResult validaQ1P1 = EBSOIEBSBDQ.executeQuery(consultaArcaBanca1);
		SQLResult validaQ1P2 = EBSOIEBSBDQ.executeQuery(consultaArcaBanca2);
		String atributo = "", atributo1 = "";
		if(!validaQ1P1.isEmpty()){
			atributo = validaQ1P1.getData(0, "ID_FILE");
			atributo1 = validaQ1P2.getData(0, "NAME_FILE");
		}		
		testCase.addQueryEvidenceCurrentStep(validaQ1P1);
		testCase.addQueryEvidenceCurrentStep(validaQ1P2);
		boolean validar = validaQ1P1.isEmpty();//atributo.equals(idFile);
		System.out.print(atributo);
		assertFalse(validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		// PASO 4
		addStep("Consultar la tabla "+tablaEdosBanca);
		consultaEdosBanca = String.format(consultaEdosBanca, atributo1);
		System.out.println(consultaEdosBanca);
		SQLResult validaQ2 = EBSOIEBSBDQ.executeQuery(consultaEdosBanca);
		String atributo2 = "", atributo3 = "";
		if(!validaQ2.isEmpty()){
			//atributo2 = validaQ2.getData(0, "FILE_NAME");
			atributo3 = validaQ2.getData(0, "ESTADO");
		}			
		testCase.addQueryEvidenceCurrentStep(validaQ2);
		boolean validar2 = validaQ2.isEmpty();//atributo2.equals(fileName);
		System.out.print(atributo2);
		assertFalse(validar2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		// PASO 5
		addStep("Validar estatus 'No Procesado' en la tabla "+tablaEdosBanca);
		//SQLResult validat3 = EBSOIEBSBDQ.executeQuery(consulta2);	
		testCase.addQueryEvidenceCurrentStep(validaQ2);
		boolean validacion = true;
		for(int i = 0; i < validaQ2.getRowCount(); i++ ) {
			String valor = validaQ2.getData(i, 1);		
			if(!valor.equals(estado)) {
				System.out.print(i);
				validacion = false;
			}
		}
		
		boolean validar3 = atributo3.equals(estado);
		System.out.print(atributo3);
		assertTrue(validar3 && validacion, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que no se permita carga de CR sin Codigo Postal";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Validar que no se permita carga de CR sin Codigo Postal";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}