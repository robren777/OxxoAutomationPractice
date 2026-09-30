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
*	MTC-FT-01-Ejecutar SE_DEPURA cuando la nueva bitacora cuente con registros menores a 90 dias y validar depuracion de la tabla
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con las credenciales de la base de datos FCWML
* Contar con el nuevo servicio en ambiente QA
* 
* INSUMOS:
* Acceso a Conntrol M
* Job runDepuraSE
* 
* @author Gabriel J. Rubio
* @date 21/01/2024
*/

public class ATC_FT_62_EjecutarSE_DEPURACuandoLaNuevaBitacoraCuenteConRegistrosMenoresA90DiasYValidarDepuracionDeLaTabla extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_62_EjecutarSE_DEPURACuandoLaNuevaBitacoraCuenteConRegistrosMenoresA90DiasYValidarDepuracionDeLaTabla_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");
		String currentDate = now.format(formatter);
		SeleniumUtil u;
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm;
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job;
		
		addStep("Se carga la configuración necesaria en la tabla para el proceso de depurado en la tabla SE_TABLAS_DEPURA.");
		String qryDepuraConfig = data.get("QueryDepuraConfig");
		SQLResult resDepuraConfig = dbFCTPE.executeQuery(qryDepuraConfig);
		System.out.println(resDepuraConfig.getRowCount());
		System.out.println(resDepuraConfig.getColumnNames());
		System.out.println(resDepuraConfig.getFirstRow());
		Integer nDays = Integer.parseInt(resDepuraConfig.getData(0, "DEPURATE_DAYS"));
		assertTrue( nDays > 0);
		testCase.addQueryEvidenceCurrentStep(resDepuraConfig);
		String daysAgoDate = now.minusDays(nDays).format(formatter);//13
		String prefix= resDepuraConfig.getData(0, "PARTITION_PREFIX");
		System.out.println(daysAgoDate);
		System.out.println("Se revisa que se cuenten con registros y partición del día "+daysAgoDate
				+", ya que la prueba se ejecuta el día "+currentDate
				+" considerando que la configuración para la depuración es "+nDays
				+" días atrás.");
		
		addStep("Se revisa que se cuenten con registros y partición del día "+daysAgoDate
				+", ya que la prueba se ejecuta el día "+currentDate
				+" considerando que la configuración para la depuración es "+nDays
				+" días atrás.");
		String qryRegistros = "SELECT FECHA, REGISTROS FROM ("
				+ "	SELECT TO_CHAR(CREATION_DATETIME,'YYYYMMDD') AS FECHA,"
				+ "		COUNT(TO_CHAR(CREATION_DATETIME,'YYYYMMDD')) AS REGISTROS"
				+ "  FROM TPEUSER.TPE_FR_BITACORA_REV"
				+ "  GROUP BY TO_CHAR(CREATION_DATETIME,'YYYYMMDD'))"
				+ " WHERE FECHA = '"+daysAgoDate+"'"
				+ " ORDER BY TO_DATE(FECHA, 'YYYYMMDD') DESC";
		System.out.println(qryRegistros);
		SQLResult resRegistros = dbFCTPE.executeQuery(qryRegistros);
		System.out.println(resRegistros.getRowCount());
		System.out.println(resRegistros.getColumnNames());
		System.out.println(resRegistros.getFirstRow());
		Integer nRegistros = Integer.parseInt(resRegistros.getData(0, "Registros"));
		String partition = prefix + resRegistros.getData(0, "FECHA");
		assertTrue(nRegistros > 0);
		testCase.addQueryEvidenceCurrentStep(resRegistros);
		
		String qryPartition = "SELECT TABLE_OWNER, TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS"
				+ " WHERE TABLE_NAME= 'TPE_FR_BITACORA_REV'"
				+ " AND PARTITION_NAME = '"+partition+"'";
		SQLResult resPartition = dbFCTPE.executeQuery(qryPartition);
		System.out.println(resPartition.getRowCount());
		System.out.println(resPartition.getColumnNames());
		System.out.println(resPartition.getFirstRow());
		assertEquals(resPartition.getRowCount(), resRegistros.getRowCount());
		testCase.addQueryEvidenceCurrentStep(resPartition);
		
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
		
		addStep("Se valida que la depuración se haya realizado correctamente en SE_CONTROL_DEPURA");
		String qryControlDepura = "SELECT RUN_ID, ESTATUS, TABLA, MENSAJE_ERROR, FECHADEPURADA "
				+ "	FROM TPEUSER.SE_CONTROL_DEPURA"
				+ "	WHERE FECHADEPURADA = '" + daysAgoDate + "'";
		System.out.println(qryControlDepura);
		SQLResult resControlDepura = dbFCTPE.executeQuery(qryControlDepura);
		System.out.println(resControlDepura.getRowCount());
		System.out.println(resControlDepura.getColumnNames());
		System.out.println(resControlDepura.getFirstRow());
		assertTrue(resControlDepura.getRowCount() > 0);
		assertEquals(resControlDepura.getData(0, "ESTATUS"), "S");
		assertEquals(resControlDepura.getData(0, "MENSAJE_ERROR"), "OK");
		testCase.addQueryEvidenceCurrentStep(resControlDepura);
		
		addStep("Se verifica que los registros ya no se existan en la tabla TPE_FR_BITACORA_REV");
		resRegistros = dbFCTPE.executeQuery(qryRegistros);
		assertEquals(resRegistros.getRowCount(), 0);
		testCase.addQueryEvidenceCurrentStep(resRegistros);
		
		addStep("Se verifica que la particion ya no se exista en la tabla ALL_TAB_PARTITIONS");
		resPartition = dbFCTPE.executeQuery(qryPartition);
		assertEquals(resPartition.getRowCount(), 0);
		testCase.addQueryEvidenceCurrentStep(resPartition);

	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con las credenciales de la base de datos FCTPEQA \r\n"
				+ "2. Contar con las credenciales de la base de datos FCWML.\r\n"
				+ "3. Contar con el nuevo servicio en ambiente QA.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gabriel J. Rubio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_62_EjecutarSE_DEPURACuandoLaNuevaBitacoraCuenteConRegistrosMenoresA90DiasYValidarDepuracionDeLaTabla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecutar SE_DEPURA cuando la nueva bitacora cuente con registros menores a 90 dias y validar depuracion de la tabla";
	}
}