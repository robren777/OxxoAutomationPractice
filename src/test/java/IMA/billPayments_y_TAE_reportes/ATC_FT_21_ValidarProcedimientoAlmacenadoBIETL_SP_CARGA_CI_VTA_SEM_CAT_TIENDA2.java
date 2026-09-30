package IMA.billPayments_y_TAE_reportes;

import static org.testng.Assert.assertFalse;

import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_21_ValidarProcedimientoAlmacenadoBIETL_SP_CARGA_CI_VTA_SEM_CAT_TIENDA2 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_21_ValidarProcedimientoAlmacenadoBIETL_SP_CARGA_CI_VTA_SEM_CAT_TIENDA2_test(HashMap<String, String> data)
			throws Exception {
		
		IMATools ima = new IMATools(null, testCase);
		
		SQLResult connectionResult;
		
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA, GlobalVariables.DB_PASSWORD_FCDW4QA);
		
		 String query1 = "{call SP_DEL_CI_VENTA_SEM_PROD_PLAZA}";

		 System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ query1);
         
		 //Paso 1. No se puede automatizar el respaldar info a un excel
		 //Paso 2. No se puede automatizar validar coincidencias entre tablas 
		 
		 //Paso 3. Ejecutar SP 
		 addStep("Ejecutar el SP BIETL.SP_DEL_CI_VENTA_SEM_PROD_PLAZA;");
		 
         Statement stmt = db.getConn().createStatement();
         boolean execute= stmt.execute(query1);
         System.out.println("res execute: "+ execute);
         
        if(execute == false) {
                testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento completado exitosamente.");
                System.out.println("completado existosamente");
        }else {
            testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento no completado.");
            System.out.println("no completado");
        }
        assertFalse(execute,"No se ejecuto correctamente el procedimiento BIETL.SP_CI_DM_FACT_VENTA_MES");

        //Paso 4. Validar que los registros que coincidian se hayan eliminado de la tabla BiDmMkt.Dm_Fact_Venta_Sem_Prod_Plaza con ayuda de la consulta del paso 2
        addStep("Validar que los registros que coincidían se hayan eliminado de la tabla BiDmMkt.Dm_Fact_Venta_Sem_Prod_Plaza con ayuda de la consulta del paso 2");
        
      String query2 = "select * from  BiDmMkt.Dm_Fact_Venta_Sem_Prod_Plaza where rownum < 5";
        
  	connectionResult = executeQuery(db, query2);
	ima.validarConexion(connectionResult);
	System.out.println("se valida conexion");

	ima.validarContenidoTablas(connectionResult, query2);

	testCase.addQueryEvidenceCurrentStep(connectionResult);
	

  
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar Procedimiento Almacenado";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_21_ValidarProcedimientoAlmacenadoBIETL_SP_CARGA_CI_VTA_SEM_CAT_TIENDA2_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
