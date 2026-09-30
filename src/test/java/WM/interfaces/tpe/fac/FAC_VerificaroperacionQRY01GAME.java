package WM.interfaces.tpe.fac;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.TPE_FAC;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

public class FAC_VerificaroperacionQRY01GAME extends BaseExecution {

@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_FAC_VerificaroperacionQRY01GAME(HashMap<String, String> data) throws Exception {
				
/* Utiler�as *********************************************************************/
//		SqlUtil db = new SqlUtil(GlobalVariables.DB_USERPE1, GlobalVariables.DB_PASSWORDPE1, GlobalVariables.DB_HOSTPE1);
//utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
	  SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCIASQA, GlobalVariables.DB_USER_FCIASQA, GlobalVariables.DB_PASSWORD_FCIASQA);
		TPE_FAC facUtil = new TPE_FAC(data, testCase, db);
	
		
/* Variables *********************************************************************/
			
	
		String wmCodeQR01 = "101";	
		String tdcQRY01GAME = "SELECT WM_CODE,APPLICATION,ENTITY,OPERATION,FOLIO FROM TPEUSER.TPE_FR_TRANSACTION"
								+ " WHERE APPLICATION = 'FAC'"					
								//+" AND ENTITY ="+ data.get("entity")
								+ " AND ENTITY = 'BANK'"					
								+ " AND OPERATION = 'QRY01'"
								+ " AND folio = %s "
								+ " AND CREATION_DATE >= (SYSDATE-1)";
		
		
	String wmCodeRequestQRY01;
						
	String folio;
	String wmCodeRequest;
	String wmCodeDb;
		
	
	
	/* Paso 1 *****************************************************************************************/
	
	//Paso 1
		addStep("Llamar al servicio de consulta QRY01");
				
					String respuestaQRY01GAME = facUtil.QRY01GAME();
				
					System.out.println("Respuesta: " + respuestaQRY01GAME);
				
				
				
					folio = RequestUtil.getFolioXml(respuestaQRY01GAME);
					wmCodeRequestQRY01 = RequestUtil.getWmCodeXml(respuestaQRY01GAME);		
				
			testCase.passStep();	
	/* Paso 2 *****************************************************************************************/
			
	//Paso 2	

			addStep("Verificar la respuesta generada por el servicio");
			
			
			boolean validationRequest = wmCodeQR01.equals(wmCodeRequestQRY01);
		
			System.out.println(validationRequest + " - wmCode request: " );
			
			

		testCase.validateStep(validationRequest);
		

}	
	
	
		
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_FAC_VerificaroperacionQRY01GAME";
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
