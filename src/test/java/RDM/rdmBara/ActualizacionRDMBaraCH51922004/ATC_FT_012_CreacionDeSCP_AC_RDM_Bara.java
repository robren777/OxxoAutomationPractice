package RDM.rdmBara.ActualizacionRDMBaraCH51922004;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import modelo.BaseExecution;

public class ATC_FT_012_CreacionDeSCP_AC_RDM_Bara extends BaseExecution{
	
	/**
	 * REREQUISITOS: 
	 *		1.Creacion del SCP (Campo que valida la configuracion del usuario para mostrar informacion en la interface)
	 		requiere ordenes de compra con items en la base de datos para realizar el script.
	 * 
	 * DESCRIPCION: Se validara que se haya creado el SCP xxfc_rp_carrier, el cual servirá para que el campo carrier aparezca como habilitado o deshabilitado en la pantalla
	 * 
	 * @author franciscot2@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_CreacionDeSCP_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 * Creacion del SCP (Campo que valida la configuracion del usuario para mostrar informacion en la interface)
	    */
		
		/*
		 * PASO 1: Accesar a la base de datos RDM
		*/
		addStep("Accesar a la base de datos RDM");
		String querty1 = "select FACILITY_ID, SCP_NAME, SCP_CURRENT_VAL, SCP_TYPE,ALLOW_USER_EDIT from SCP where scp_name='xxfc_rp_carrier'";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    /*
		 * PASO 2: Validar que el SPC 'xxfc_rp_carrier' este creado. select * from SCP where scp_name='xxfc_rp_carrier';
		*/
	    addStep("Validar que el SPC 'xxfc_rp_carrier' este creado. select * from SCP where scp_name='xxfc_rp_carrier'");
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
	    boolean flag = false; 
	    if(!resultadoContenedor1.isEmpty())
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
	    
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "Creacion del SCP";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se haya creado el SCP xxfc_rp_carrier, el cual servirá para que el campo carrier aparezca como habilitado o deshabilitado en la pantalla";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_CreacionDeSCP_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
