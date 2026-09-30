package WM.interfaces.pe4v2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE4v2;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PE4v2SourcePOSMES extends BaseExecution {
	

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_PE4V2_PE4v2SourcePOSMES(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,
				GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);		
		
		String sqlQuery = "SELECT * FROM cfd_transaction WHERE plaza = %s AND tienda = %s AND caja = %s AND ticket = %s AND TRUNC (creation_date) = TRUNC (SYSDATE) AND source = 'POSMES' AND wm_code = 101";
				
		
		//Valores WMcode esperados:
		String expectedWMCodeQueryCFDI = "101";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Pasos
	 *************************************************************************************************/
		/*
		 * Paso 1
		 *****************************************************************************************/
		addStep("Llamar al servico PE4v2.Pub:runGetCFDi" ) ;
		
    	String queryTDCResponse = pe4Obj.executeRunGetCFDI();
    	
    	String actualWMCode = RequestUtil.getWmCodeXml(queryTDCResponse);
    	testCase.addTextEvidenceCurrentStep("-El servicio PE4v2.Pub:runGetCFDi se ejecut�");
    	
    	/*
		 * Paso 2
		 *****************************************************************************************/
		addStep("Verificar la respuesta generada por el servicio");

		boolean validationRequest = actualWMCode.equals(expectedWMCodeQueryCFDI);

		System.out.println(validationRequest + " - wmCode request: " + actualWMCode);

		testCase.addTextEvidenceCurrentStep(queryTDCResponse);
		
    	testCase.addTextEvidenceCurrentStep("-La Respuesta WM_CODE es:"+actualWMCode);
    	
		assertTrue(validationRequest, "El campo wmCode no es el esperado");
		
		
		/*
		 * Paso 3
		 *****************************************************************************************/
		addStep("Verificar la inserci�n del ticket consultado en la tabla TPEUSER.CFD_TRANSACTION");
		
		
		String CFDI = String.format(sqlQuery, data.get("plaza"), data.get("tienda"), data.get("caja"), data.get("noTicket"));
		System.out.println(CFDI);

		SQLResult resultQuery = db.executeQuery(CFDI);

		boolean resultQueryEmpty = resultQuery.isEmpty();
		
		
		if(!resultQueryEmpty) {
		testCase.addQueryEvidenceCurrentStep(resultQuery);
    	testCase.addTextEvidenceCurrentStep("Se genero el registro");

		}
		
		assertTrue(!resultQueryEmpty, "El campo wmCode no es el esperado");

  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_012_PE4V2_PE4v2SourcePOSMES" ;
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

