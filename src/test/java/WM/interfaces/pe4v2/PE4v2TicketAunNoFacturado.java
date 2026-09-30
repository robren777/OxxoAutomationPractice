package WM.interfaces.pe4v2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static WM.util.RequestUtil.getWmCodeXml;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE4v2;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PE4v2TicketAunNoFacturado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_PE4V2_PE4v2TicketAunNoFacturado(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,	GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		
		//Valores WMcode esperados:
		final String expectedWMCodeQueryTdc = "101"; 
		String QueryDb = "SELECT * FROM cfd_consulta_log WHERE ticket = %s;";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Solicitud de folio
	 *************************************************************************************************/
			
		//Paso 1
		addStep("Ejecutar el servicio PE4v2.Pub:runGetCFDi" ) ;
		
    	String queryTDCResponse = pe4Obj.executeRunQueryYaFacturado();
    	
		String queryTDCWMCode = getWmCodeXml(queryTDCResponse);
		testCase.addTextEvidenceCurrentStep("Se ejecuto el servicio PE4v2.Pub:runGetCFDi");

    	//assertEquals(queryTDCWMCode,expectedWMCodeQueryTdc);
    	
    	 //* Paso 2
		 //*****************************************************************************************/
		addStep("Validar que la respuesta de la ejecuci�n sea 'Transaccion exitosa'.");

		
		boolean validationRequest = queryTDCWMCode.equals(expectedWMCodeQueryTdc);

		System.out.println(validationRequest + " - wmCode request: " + queryTDCWMCode);

		testCase.addTextEvidenceCurrentStep(queryTDCResponse);
		
		if(validationRequest) {
			testCase.addTextEvidenceCurrentStep("wmCode:"+queryTDCWMCode);
		}

		assertTrue(validationRequest, "El campo wmCode no es el esperado");
		
		//* Paso 3
		 //*****************************************************************************************/
		addStep("Verificar la inserci�n del registro en la tabla TPEUSER.CFD_CONSULTA_LOG");

		
		QueryDb = String.format(QueryDb, data.get("noTicket"));
		System.out.println(QueryDb);
		SQLResult resultQuery = db.executeQuery(QueryDb);
		
		boolean resultQueryEmpty = true;
		
		
		if(resultQuery.getRowCount() > 0) {
			resultQueryEmpty = false;
			testCase.addQueryEvidenceCurrentStep(resultQuery, false);
			testCase.addTextEvidenceCurrentStep("Se realiza la inserci�n en la tabla.");
		}
		
		assertFalse(resultQueryEmpty, "No se registra transacci�n en tabla.");

  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_013_PE4V2_PE4v2TicketAunNoFacturado" ;
  }

  @Override
  public String setTestDescription() {
    return "Realizar la factura de un ticket ya facturado" ;
  }

  @Override
  public String setTestDesigner() {
    return "Equipo de Automatizaci�n" ;
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

