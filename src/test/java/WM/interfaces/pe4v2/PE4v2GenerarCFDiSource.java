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

public class PE4v2GenerarCFDiSource extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_PE4V2_PE4v2GenerarCFDiSource(HashMap<String, String> data) throws Exception {
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE,
				GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		
		String sqlQuery = "SELECT * FROM tpeuser.cfd_transaction where "
				+ "plaza = %s AND tienda = %s AND caja = %s AND ticket = %s AND TRUNC (creation_date) = TRUNC (SYSDATE) AND source = '%s' AND wm_code = 101";
		
		String sqlQuery2 = "SELECT * FROM tpeuser.cfd_transaction where "
				+ "plaza = %s AND tienda = %s AND caja = %s AND ticket = %s AND source = '%s' AND TRUNC (pv_date) = TO_DATE (%s, 'DD/MM/YYYY')";
		
		
		String expectedWMCodeQueryCFDI = "101";
		
		PE4v2 pe4Obj = new PE4v2(data, testCase, db);
	/**************************************************************************************************
	 * Solicitud de folio
	 *************************************************************************************************/
			
		/*
		 * Paso 1
		 *****************************************************************************************/
		addStep("Ejecutar la interface PE4v2.Pub:runGetCFDi y validar que su respuesta del XML sea Transaccion Completada Exitosamente, wmCode=101." ) ;
		
    	String queryTDCResponse = pe4Obj.executeRunGetCFDI();
    	
    	String actualWMCode = RequestUtil.getWmCodeXml(queryTDCResponse);
    	testCase.addTextEvidenceCurrentStep("-El servicio PE4v2.Pub:runGetCFDi se ejecut�");
    	
    	boolean validationRequest = actualWMCode.equals(expectedWMCodeQueryCFDI);

		System.out.println(validationRequest + " - wmCode request: " + actualWMCode);

		testCase.addTextEvidenceCurrentStep(queryTDCResponse);
		
    	testCase.addTextEvidenceCurrentStep("-La Respuesta WM_CODE es:"+actualWMCode);
    	
		assertTrue(validationRequest, "El campo wmCode no es el esperado");
		
		/*
		 * Paso 2
		 *****************************************************************************************/
		addStep("Verificar la inserci�n del ticket consultado en la tabla TPEUSER.CFD_TRANSACTION");
		
		sqlQuery = String.format(sqlQuery, data.get("plaza"), data.get("tienda"), data.get("caja"), data.get("noTicket"), data.get("source"));
		
		SQLResult resultQuery = db.executeQuery(sqlQuery);
		
		System.out.print(sqlQuery);
		
		boolean ValidateRegis = false;
		if(resultQuery.getRowCount() > 0)
			ValidateRegis = true;
		
		testCase.addQueryEvidenceCurrentStep(resultQuery);
		
		assertTrue(ValidateRegis,"No se encontraron registros en la tabla");

		/*
		 * Paso 3
		 *****************************************************************************************/
		addStep("Una vez que el ticket fue facturado, se inserta una referencia en la tabla cfd_tickets_facturados");
		
		sqlQuery2 = String.format(sqlQuery2, data.get("plaza"), data.get("tienda"), data.get("caja"), data.get("ticket"), data.get("source"), data.get("movDate"));
		
		SQLResult resultQuery2 = db.executeQuery(sqlQuery2);
		
		System.out.print(sqlQuery2);
		
		ValidateRegis = false;
		if(resultQuery2.getRowCount() > 0)
			ValidateRegis = true;
		
		testCase.addQueryEvidenceCurrentStep(resultQuery2);
		
		assertTrue(ValidateRegis,"No se encontraron registros en la tabla");

  }
	

  @Override
  public String setTestFullName() {
    return "ATC_FT_011_PE4V2_PE4v2GenerarCFDiSource" ;
  }

  @Override
  public String setTestDescription() {
    return "Construido. FEMSA_PE4V2_PE4v2QueryTdc" ;
  }

  @Override
  public String setTestDesigner() {
    return "Equipo de Automatizanci�n" ;
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

