package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCIÓN: Caso de prueba que valida el reverso en linea exitoso con proveedor CNKTA
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
*	MTC-FT-02- Ejecutar SE_DEPURA cuando la nueva tabla de bitacora NO cuente con registros y validar la no afectacion de la tabla
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con las credenciales de la base de datos FCWML
* Contar con el nuevo servicio en ambiente QA
* Que se haya ejecutado previamente el Job runDepuraSE
* 
* INSUMOS:
* Acceso a Conntrol M
* Job runDepuraSE
* 
* @author Gabriel J. Rubio
* @date 21/01/2024
*/

public class ATC_FT_63_EjecutarSE_DEPURACuandoLaNuevaTablaDeBitacoraNoCuenteConRegistrosYValidarLaNoAfectacionDeLaTabla extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_63_EjecutarSE_DEPURACuandoLaNuevaTablaDeBitacoraNoCuenteConRegistrosYValidarLaNoAfectacionDeLaTabla_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YY");
		String currentDate = now.format(formatter);
		SeleniumUtil u;
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm;
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job;
		
		addStep("Se carga la configuración necesaria en la tabla para el proceso de depurado en la tabla SE_TABLAS_DEPURA.");
		String qryDepuraConfig = data.get("QueryDepuraConfig");
		SQLResult resDepuraConfig = dbFCTPE.executeQuery(qryDepuraConfig);
		Integer nDays = Integer.parseInt(resDepuraConfig.getData(0, "DEPURATE_DAYS"));
		assertTrue( nDays > 0 );
		testCase.addQueryEvidenceCurrentStep(resDepuraConfig);
		String daysAgoDate = now.minusDays(nDays).format(formatter);
		
		System.out.println("Se revisa que se cuenten con registros y partición del día "+daysAgoDate
				+", ya que la prueba se ejecuta el día "+currentDate
				+" considerando que la configuración para la depuración es "+nDays
				+" días atrás.");
		
		addStep("Se revisa que No se cuenten con registros ni partición del día "+daysAgoDate
				+", ya que la prueba se ejecuta el día "+currentDate
				+" considerando que la configuración para la depuración es "+nDays
				+" días atrás.");
		String qryRegistros0 = "SELECT FECHA, REGISTROS FROM ("
				+ "	SELECT TO_CHAR(CREATION_DATETIME,'YYYYMMDD') AS FECHA,"
				+ "		COUNT(TO_CHAR(CREATION_DATETIME,'YYYYMMDD')) AS REGISTROS"
				+ "  FROM TPEUSER.TPE_FR_BITACORA_REV"
				+ "  GROUP BY TO_CHAR(CREATION_DATETIME,'YYYYMMDD'))"
				+ " WHERE FECHA = '"+daysAgoDate+"'"
				+ " ORDER BY TO_DATE(FECHA, 'YYYYMMDD') DESC";
		SQLResult resRegistros0 = dbFCTPE.executeQuery(qryRegistros0);
		assertEquals(resRegistros0.getRowCount(), 0);
		testCase.addQueryEvidenceCurrentStep(resRegistros0);
		
		addStep("Se toma evidencia de resgistros en la tabla TPE_FR_BITACORA_REV"
				+ " antes de la ejecucion del job TPE_FR_BITACORA_REV");
		String qryRegistros = "SELECT FECHA, REGISTROS FROM ("
				+ "	SELECT TO_CHAR(CREATION_DATETIME,'YYYYMMDD') AS FECHA,"
				+ "		COUNT(TO_CHAR(CREATION_DATETIME,'YYYYMMDD')) AS REGISTROS"
				+ "  FROM TPEUSER.TPE_FR_BITACORA_REV"
				+ "  GROUP BY TO_CHAR(CREATION_DATETIME,'YYYYMMDD'))"
				+ " ORDER BY TO_DATE(FECHA, 'YYYYMMDD') DESC";
		SQLResult resRegistros1 = dbFCTPE.executeQuery(qryRegistros);
		testCase.addQueryEvidenceCurrentStep(resRegistros1);
		
		addStep("Se corre el job runDepuraSE");
		u = new SeleniumUtil(new ChromeTest(), true);
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		job= new ControlM(u, testCase, obj);
		u.get(data.get("server"));
		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();
		boolean flag = job.searchJob();
		assertTrue(flag);
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());
		assertEquals(resultado1, "Ended OK");
 		job.closeViewpoint();
		testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runDepuraSE");
		testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit(); 
		
		addStep("Se verifica que los registros no se hayan modificado");
		SQLResult resRegistros2 = dbFCTPE.executeQuery(qryRegistros);
		assertEquals(resRegistros2.getRowCount(), resRegistros1.getRowCount());
		for (int i = 0; i <  resRegistros1.getRowCount(); i++) {
			assertEquals(resRegistros1.getData(i, "FECHA"), resRegistros2.getData(i, "FECHA"));
			assertEquals(resRegistros1.getData(i, "REGISTROS"), resRegistros2.getData(i, "REGISTROS"));
		}
		testCase.addBoldTextEvidenceCurrentStep("Resultado Antes de la ejecucion del Job runDepuraSE");
		testCase.addQueryEvidenceCurrentStep(resRegistros1);
		testCase.addBoldTextEvidenceCurrentStep("Resultado Despues de la ejecucion del Job runDepuraSE");
		testCase.addQueryEvidenceCurrentStep(resRegistros2);

	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con las credenciales de la base de datos FCTPEQA \r\n"
				+ "2. Contar con el nuevo servicio en ambiente QA.\r\n"
				+ "3. Que se haya ejecutado previamente el Job runDepuraSE.\r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gabriel J. Rubio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_63_EjecutarSE_DEPURACuandoLaNuevaTablaDeBitacoraNoCuenteConRegistrosYValidarLaNoAfectacionDeLaTabla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecutar SE_DEPURA cuando la nueva tabla de bitacora NO cuente con registros y validar la no afectacion de la tabla";
	}
}