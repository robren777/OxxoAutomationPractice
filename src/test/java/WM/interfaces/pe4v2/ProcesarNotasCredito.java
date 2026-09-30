package WM.interfaces.pe4v2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE4v2;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ProcesarNotasCredito extends BaseExecution {
	

	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_PE4V2_ProcesarNotasCredito(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,
				GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);		
		
		String sqlNotHave = "SELECT folio, TO_CHAR (creation_date, 'YYYYMMDDHH24MISS'), "
				+ "refid, plaza, tienda, caja,  ticket, TO_CHAR (admin_date, 'YYYYMMDDHH24MISS'), "
				+ "TO_CHAR (pv_date, 'YYYYMMDDHH24MISS'), subtotal, total, source, cfd_type, emi_rfc, "
				+ "emi_nombre, serie, file_xmlcfd, server, url FROM cfd_transaction "
				+ "WHERE creation_date >= TRUNC (SYSDATE - (SELECT value1 FROM tpe_config "
				+ "WHERE category = 'PE4_CONFIG' AND code = 'DEPTH_DATE_NC')) AND wm_code != '101' "
				+ "AND nota_credito = 'Y'  AND ROWNUM <= (SELECT value2 FROM tpe_config "
				+ "WHERE category = 'PE4_CONFIG' AND code = 'DEPTH_DATE_NC')";
		
		String sqlHave = "SELECT folio, TO_CHAR (creation_date, 'YYYYMMDDHH24MISS'), "
				+ "refid, plaza, tienda, caja,  ticket, TO_CHAR (admin_date, 'YYYYMMDDHH24MISS'), "
				+ "TO_CHAR (pv_date, 'YYYYMMDDHH24MISS'), subtotal, total, source, cfd_type, emi_rfc, "
				+ "emi_nombre, serie, file_xmlcfd, server, url FROM cfd_transaction "
				+ "WHERE creation_date >= TRUNC (SYSDATE - (SELECT value1 FROM tpe_config "
				+ "WHERE category = 'PE4_CONFIG' AND code = 'DEPTH_DATE_NC')) AND wm_code = '101' "
				+ "AND nota_credito = 'Y'  AND ROWNUM <= (SELECT value2 FROM tpe_config "
				+ "WHERE category = 'PE4_CONFIG' AND code = 'DEPTH_DATE_NC')";
				
		
		//Valores WMcode esperados:
		String expectedWMCodeQueryCFDI = "101";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Pasos
	 *************************************************************************************************/
		/*
		 * Paso 1
		 *****************************************************************************************/
		addStep("Validar que existan notas de cr�dito con wmCode distinto de 101 en la tabla cfd_transaction de tpeuser." ) ;
		
		System.out.println(sqlNotHave);

		SQLResult resultQuery = db.executeQuery(sqlNotHave);

		boolean resultQueryEmpty = resultQuery.isEmpty();
		
		
		if(!resultQueryEmpty) {
			testCase.addQueryEvidenceCurrentStep(resultQuery);
			testCase.addTextEvidenceCurrentStep("Obtener al menos un registro de nota de cr�dito con wm_code distinto a 101.");
		}
		
		assertTrue(!resultQueryEmpty, "No existen registros con notas de credito con wm_code distinto a 101");
    	
    	/*
		 * Paso 2
		 *****************************************************************************************/

		// No se encuentra el metodo de la interface PE4v2.Pub:runNCCFDI
		
		/*
		 * Paso 3
		 *****************************************************************************************/

		addStep("Validar que en la respuesta de la ejecuci�n de la interface sea con regStatus/isError = false.");

		// Es necesario el metodo de la interfaz para validar la respuesta
		
		/*boolean validationRequest = queryTDCWMCode.equals(expectedWMCodeQueryTdc);

		System.out.println(validationRequest + " - wmCode request: " + queryTDCWMCode);

		testCase.addTextEvidenceCurrentStep(queryTDCResponse);

		assertTrue(validationRequest, "Existieron errores en la ejecuci�n de la interface");*/
		
		/*
		 * Paso 4
		 *****************************************************************************************/
		addStep("Validar que las notas de cr�dito fueron procesadas y ahora tengan wm_code = '101'" ) ;
		
		System.out.println(sqlHave);

		resultQuery = db.executeQuery(sqlHave);

		resultQueryEmpty = resultQuery.isEmpty();
		
		
		if(!resultQueryEmpty) {
			testCase.addQueryEvidenceCurrentStep(resultQuery);
			testCase.addTextEvidenceCurrentStep("Las notas de cr�dito procesadas, ahora tendr�n wm_code = 101.");
		}
		
		assertTrue(!resultQueryEmpty, "No existen registros con notas de credito con wm_code igual a 101");

  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_016_PE4V2_ProcesarNotasCredito" ;
  }

  @Override
  public String setTestDescription() {
    return "Construido. PE4v2FacturaExitosa";
  }

  @Override
  public String setTestDesigner() {
    return "Equipo d Automatizaci�n" ;
  }

  @Override
  public String setTestInstanceID() {
    return null ;
  }


  @Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}


@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return null;
}
}

