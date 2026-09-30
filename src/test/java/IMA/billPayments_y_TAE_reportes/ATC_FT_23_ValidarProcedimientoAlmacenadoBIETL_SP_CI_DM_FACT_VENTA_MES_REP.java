package IMA.billPayments_y_TAE_reportes;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_23_ValidarProcedimientoAlmacenadoBIETL_SP_CI_DM_FACT_VENTA_MES_REP extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_23_ValidarProcedimientoAlmacenadoBIETL_SP_CI_DM_FACT_VENTA_MES_REP_test(HashMap<String, String> data)
			throws Exception {
		
		IMATools ima = new IMATools(null, testCase);
		
		SQLResult connectionResult;
		
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA, GlobalVariables.DB_PASSWORD_FCDW4QA);
		
		// String query1 = "{call DBMS_MVIEW.REFRESH('BIQUERYMKT.VM_FILE_TAE_CI')}";

		//Paso 1. la tabla MIOXXO_ZD.FILE_TAE_CI no existe 
		//la tabla BIDMMKT.DM_FACT_VENTA_MES no contiene registros
	        addStep("Buscar un producto en la tabla BIDMMKT.DM_FACT_VENTA_MES que haya terminado con ID_ZONA_DIGITAL = NULL. Verificar que este producto se"
	        		+ " encuentre en la tabla MIOXXO_ZD.FILE_TAE_CI");
	        
	     String query2 = "select * from BIDMMKT.DM_FACT_VENTA_MES where ID_ZONA_DIGITAL = NULL";
	     // String query3 = "select * from MIOXXO_ZD.FILE_TAE_CI where ID_ZONA_DIGITAL = NULL";
	        
	  	connectionResult = executeQuery(db, query2);
		ima.validarConexion(connectionResult);
		System.out.println("se valida conexion");
		ima.validarContenidoTablas(connectionResult, query2);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
	/*	 
		//Paso 3. Ejecutar SP 
        addStep("Ejecutar el SP: execute BIETL.SP_CI_DM_FACT_VENTA_MES_REP;');");
        String query4 = "EXECUTE DBMS_MVIEW.REFRESH('BIQUERYMKT.VM_FILE_TAE_CI";
		 
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
        assertFalse(execute,"No se ejecuto correctamente el procedimiento BIETL.SP_CARGA_CI_VTA_SEM_PROD_PLAZA");

        //Paso 4. 
        addStep("Verificar que se cargue la tabla BIDMMKT.TEMP_CI_FACT_VENTA_MES con el producto seleccionado");
        
	
*/
  
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
		return "ATC_FT_23_ValidarProcedimientoAlmacenadoBIETL_SP_CI_DM_FACT_VENTA_MES_REP_test";
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
