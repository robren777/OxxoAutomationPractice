package IMA.Extraccion_ConexionDirecta.QAServicios_DiarioVsFuente_Hijo_51969974;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ValidarDatosEnTablasFuente extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarDatosEnTablasFuente_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true); u.close();
		  IMATools ima = new IMATools(u,testCase);
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIQN = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "SELECT *from (select tienda_key, producto_key, tiempo_key, tiempo_adm_key, servicio_amt, loaddate FROM BIDMMKT.FACT_SERVICIOS) where rownum<=15";
		
		String multijuegos="SELECT * FROM BIODSMKT.CNV_010_MULTIJUEGOS_POS";
		
		String servSem="SELECT * from (select tienda_key, producto_key, servicio_amt, loaddate FROM BIDMMKT.DM_FACT_SERVICIOS_SEMANA) where rownum<=15";
		
		String servMes="SELECT * from (select tienda_key, producto_key, servicio_amt, loaddate FROM BIDMMKT.DM_FACT_SERVICIOS_MES) where rownum<=15";
		
		String servicios="SELECT * FROM BIODSMKT.CNV_010_SERV_OTROS_INGRESOS";
		
		/*************************************** PASO 1 ***************************************/	
		  addStep("Verificar que existen datos en la tabla BIDMMKT.FACT_SERVICIOS");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN);
		  System.out.println(Query); 
		  
		  SQLResult ExecuteQuery = executeQuery(dbBIQN, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			  ima.validarContenidoTablas(ExecuteQuery, Query);
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIDMMKT.FACT_SERVICIOS."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  }
		  assertFalse(StatusQuery, "No se muestran los registros en la tabla BIDMMKT.FACT_SERVICIOS.");
		 
		/*************************************** PASO 2 ***************************************/	
		  addStep("Verificar que existen datos en la tabla BIDMMKT.FACT_SERVICIOS");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN);
		  System.out.println(multijuegos);
		  
		  SQLResult ExecuteQuery2 = executeQuery(dbBIQN, multijuegos);
		  
		  boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		  System.out.println(StatusQuery2); 
		  if (!StatusQuery2) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
			  ima.validarContenidoTablas(ExecuteQuery2, multijuegos);
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIODSMKT.CNV_010_MULTIJUEGOS_POS."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
		  }
		  assertFalse(StatusQuery2, "No se muestran los registros en la tabla BIODSMKT.CNV_010_MULTIJUEGOS_POS.");
		 
		  /*************************************** PASO 3 ***************************************/	
		  addStep("Verificar que existen datos en la tabla BIDMMKT.DM_FACT_SERVICIOS_SEMANA");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN);
		  System.out.println(servSem);
		  
		  SQLResult ExecuteQuery3 = executeQuery(dbBIQN, servSem);
		  
		  boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		  System.out.println(StatusQuery3); 
		  if (!StatusQuery3) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
			  ima.validarContenidoTablas(ExecuteQuery3, servSem);
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_SEMANA."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
		  }
		  assertFalse(StatusQuery3, "No se muestran los registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_SEMANA.");
		 
		  /*************************************** PASO 4 ***************************************/	
		  addStep("Verificar que existen datos en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN);
		  System.out.println(servMes);
		  
		  SQLResult ExecuteQuery4 = executeQuery(dbBIQN, servMes);
		  
		  boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		  System.out.println(StatusQuery4); 
		  if (!StatusQuery4) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
			  ima.validarContenidoTablas(ExecuteQuery4, servMes);
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery4); 
		  }
		  assertFalse(StatusQuery4, "No se muestran los registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES.");
		 
		  /*************************************** PASO 5 ***************************************/	
		  addStep("Verificar que existen datos en la tabla BIODSMKT.CNV_010_SERV_OTROS_INGRESOS");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN);
		  System.out.println(servicios);
		  
		  SQLResult ExecuteQuery5 = executeQuery(dbBIQN, servicios);
		  
		  boolean StatusQuery5 = ExecuteQuery5.isEmpty();
		  System.out.println(StatusQuery5); 
		  if (!StatusQuery5) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
			  ima.validarContenidoTablas(ExecuteQuery5, servicios);
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIODSMKT.CNV_010_SERV_OTROS_INGRESOS."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery5); 
		  }
		  assertFalse(StatusQuery4, "No se muestran los registros en la tabla BIODSMKT.CNV_010_SERV_OTROS_INGRESOS.");
	
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarDatosEnTablasFuente_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "CP002.- Validar datos en tablas fuente";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
	}
}
