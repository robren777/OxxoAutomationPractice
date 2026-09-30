package WM.interfaces.pe4v2;

/*
 *  Cubre Generar CFDi. Source PORTAL - ticket ya facturado y Generar CFDi. Source POS - ticket ya facturado
 */

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static WM.util.RequestUtil.getWmCodeXml;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE4v2;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

public class PE4v2TicketyaFacturado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_PE4V2_PE4v2TicketyaFacturado(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,	GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		
		
		
		//Valores WMcode esperados:
		final String expectedWMCodeQueryTdc = "117";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Solicitud de folio
	 *************************************************************************************************/
			
		//Paso 1
		addStep("Ejecutar el servicio PE4v2.Pub:runGetCFDi" ) ;
		
    	String queryTDCResponse = pe4Obj.executeRunQueryFecha();
    	
		String queryTDCWMCode = getWmCodeXml(queryTDCResponse);
    	
    	assertEquals(queryTDCWMCode,expectedWMCodeQueryTdc);
    	
    	 //* Paso 2
		 //*****************************************************************************************/
		addStep("Verificar la respuesta generada por el servicio");

		
		boolean validationRequest = queryTDCWMCode.equals(expectedWMCodeQueryTdc);

		System.out.println(validationRequest + " - wmCode request: " + queryTDCWMCode);

		testCase.addTextEvidenceCurrentStep(queryTDCResponse);

		assertTrue(validationRequest, "El campo wmCode no es el esperado");
		
	

  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_015_PE4V2_PE4v2TicketyaFacturado" ;
  }

  @Override
  public String setTestDescription() {
    return "Terminado. Realizar la factura de un ticket con la fecha administrativa invalida" ;
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

