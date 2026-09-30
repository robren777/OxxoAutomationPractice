package WM.interfaces.pe4v2;

/*
 * 
 * 
 * Esta interfaz cubre la Consulta de tarjeta en TPEREP y Consulta de tarjeta en TPEUSERoxxo123
 * 
 */

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE4v2;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PE4v2QueryTdc extends BaseExecution {
	
	/*
	 * 
	 * @cp1 Consulta de tarjeta en TPEREP
	 * @cp2 Consulta de tarjeta en TPEUSER
	 * 
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_PE4V2_PE4v2QueryTdc(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,
				GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		
		String sqlQuery = "SELECT source, operation, wm_code, wm_desc, TO_CHAR(creation_date, 'YY-MM-DD HH24:MI:SS')creation_date\r\n"
				+ "FROM tpeuser.cfd_consulta_log where\r\n"
				+ "source = 'PORTAL'\r\n"
				+ "AND operation = 'TARJETA'\r\n"
				+ " AND TRUNC(creation_date) = TRUNC(SYSDATE)";
			//	+ "ORDER BY creation_date DESC";
		
		
		//Valores WMcode esperados:
		String expectedWMCodeQueryTdc = "101";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Solicitud de folio
	 *************************************************************************************************/
			
		/*
		 * Paso 1
		 *****************************************************************************************/
		addStep("Llamar al servico PE4v2.Pub:runQueryTdc" ) ;
		
    	String queryTDCResponse = pe4Obj.executeRunQueryTdc();
    	
    	String queryTDCWMCode = RequestUtil.getWmCodeHtmlResponse(queryTDCResponse);
    	
    	System.out.println("wm_code: "+queryTDCWMCode);  	
    	
    	/*
		 * Paso 2
		 *****************************************************************************************/
		addStep("Verificar la respuesta generada por el servicio");

		boolean validationRequest = queryTDCWMCode.equals(expectedWMCodeQueryTdc);

		System.out.println(validationRequest + " - wmCode request: " + queryTDCWMCode);

		testCase.addTextEvidenceCurrentStep("wm_code respuesta: " + queryTDCWMCode);
		testCase.addTextEvidenceCurrentStep("wm_code esperado: " + expectedWMCodeQueryTdc);
		testCase.addTextEvidenceCurrentStep(queryTDCResponse);

		assertTrue(validationRequest, "El campo wmCode no es el esperado");
		
		/*
		 * Paso 3
		 *****************************************************************************************/
		addStep("Verificar la insercion del registro que se consulto en la tabla TPEUSER.CFD_CONSULTA_LOG");
		
		SQLResult resultQuery = db.executeQuery(sqlQuery);
		
		System.out.print(sqlQuery);

		String wmCodeDb = resultQuery.getData(0, "WM_CODE");
		
		System.out.print(wmCodeDb);
		
		testCase.addQueryEvidenceCurrentStep(resultQuery);
		
		boolean validateWmCodeDb = wmCodeDb.equals(expectedWMCodeQueryTdc);
		
		assertTrue(validateWmCodeDb,"El campo wmCode no es el esperado");


  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_005_PE4V2_PE4v2QueryTdc" ;
  }

  @Override
  public String setTestDescription() {
    return "Verificar Consulta de tarjeta en TPEUSER\r\n"
    		+ "Verificar Consulta de tarjeta en TPEUSER\r\n" ;
  }

  @Override
  public String setTestDesigner() {
    return "Equipo de Automatizancion" ;
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

