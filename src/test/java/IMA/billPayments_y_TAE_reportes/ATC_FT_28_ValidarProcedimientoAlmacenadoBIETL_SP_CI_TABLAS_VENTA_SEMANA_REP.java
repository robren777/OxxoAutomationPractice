package IMA.billPayments_y_TAE_reportes;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_28_ValidarProcedimientoAlmacenadoBIETL_SP_CI_TABLAS_VENTA_SEMANA_REP extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_28_ValidarProcedimientoAlmacenadoBIETL_SP_CI_TABLAS_VENTA_SEMANA_REP_test(HashMap<String, String> data)
			throws Exception {
		
		IMATools ima = new IMATools(null, testCase);
		
		SQLResult connectionResult;
/*		
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_ODS, GlobalVariables.DB_USER_ODS, GlobalVariables.DB_PASSWORD_ODS);


		//Paso 1.  //el paso 1 falla ya que en la tabla no existe ningun registro con NULL, no existe la segunda tabla
	        addStep("Buscar un producto en la tabla BIDMMKT.DM_FACT_VENTA_MES que haya terminado con ID_ZONA_DIGITAL = NULL. "
	        		+ "Verificar que este producto se encuentre en la tabla MIOXXO_ZD.FILE_TAE_CI");
	        
	     String query1 = "select * from BIDMMKT.DM_FACT_VENTA_MES";
	     String query2 = "select * from MIOXXO_ZD.FILE_TAE_CI where ID_ZONA_DIGITAL = null";
	        
	  	connectionResult = executeQuery(db, query1);
		ima.validarConexion(connectionResult);
		System.out.println("se valida conexion");
		ima.validarContenidoTablas(connectionResult, query1);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		 
		//Paso 2. 
        addStep("Ejecutar el SP: execute BIETL.SP_CI_TABLAS_VENTA_SEMANA_REP;");
        String query4 = "EXECUTE SP_CI_TABLAS_VENTA_SEMANA_REP;";
		 
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
        assertFalse(execute,"No se ejecuto correctamente el procedimiento BIETL.SP_CI_TABLAS_VENTA_SEMANA_REP;");

        //Paso 3.
        addStep("Verificar que se cargue la tabla BIDMMKT.TEMP_CI_FACT_VENTA_MES con el producto seleccionado");
        String query3 = "select * from BIDMMKT.TEMP_CI_FACT_VENTA_MES";
        
	  	connectionResult = executeQuery(db, query3);
		ima.validarConexion(connectionResult);
		System.out.println("se valida conexion");
		ima.validarContenidoTablas(connectionResult, query3);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
  */
		
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		//Paso 1.  //el paso 1 falla ya que en la tabla no existe ningun registro con NULL, no existe la segunda tabla
        addStep("Buscar un producto en la tabla BIDMMKT.DM_FACT_VENTA_MES que haya terminado con ID_ZONA_DIGITAL = NULL. "
        		+ "Verificar que este producto se encuentre en la tabla MIOXXO_ZD.FILE_TAE_CI");
        
     String query1 = "select * from MIOXXO_ZD.file_tae";
        
  	connectionResult = executeQuery(db, query1);
	ima.validarConexion(connectionResult);
	System.out.println("se valida conexion");
	ima.validarContenidoTablas(connectionResult, query1);
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
		return "ATC_FT_28_ValidarProcedimientoAlmacenadoBIETL_SP_CI_TABLAS_VENTA_SEMANA_REP_test";
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
