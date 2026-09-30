package WM.interfaces.PE1_D;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.SSHConnector;
import modelo.BaseExecution;

/**
 * MTC-FT-017-PE01_D SWITCH_PE01_D Registro de reversa de recarga de Tiempo aire electr�nico en el Switch<br>
 * Desc: En este escenario, se validara que se realice a tiempo el registro de una reversa de reversemanager de la PE1_D
 * 
 * @author Oliver Martinez
 * @date 04/21/2023
 */

public class ATC_FT_006_PE01_D_ReversaExitosaTAEenSwitch extends BaseExecution{
	@Test(dataProvider= "data-provider")
	public void ATC_FT_006_PE01_D_ReversaExitosaTAEenSwitch_test (HashMap<String, String> data) throws Exception{
		/*
		 * Utileria******************************************************/
	/*	SQLUtil dbFCTAEQA_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTAEQA, GlobalVariables.DB_USER_FCTAEQA,GlobalVariables.DB_PASSWORD_FCTAEQA);
		SQLUtil dbFCTAEQA_QRO = new SQLUtil(GlobalVariables.DB_HOST_FCTAEQA_QRO, GlobalVariables.DB_USER_FCTAEQA_QRO,GlobalVariables.DB_PASSWORD_FCTAEQA_QRO);
		SQLUtil dbFCSWQA_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCSWQA, GlobalVariables.DB_USER_FCSWQA,GlobalVariables.DB_PASSWORD_FCSWQA);
		SQLUtil dbFCSWQA_QRO = new SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO,GlobalVariables.DB_PASSWORD_FCSWQA_QRO);
		SQLUtil dbFCWMLTAEQA_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ_MTY,GlobalVariables.DB_USER_FCWMLTAQ_MTY, GlobalVariables.DB_PASSWORD_FCWMLTAQ_MTY);
		SQLUtil dbFCWMLTAEQA_QRO = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAEQA_QRO, GlobalVariables.DB_USER_FCWMLTAEQA_QA,GlobalVariables.DB_PASSWORD_FCWMLTAEQA_QRO_QA);
*/
		/*
		 * Variables*****************************************************/
		
		testCase.setTest_Description(data.get("Descripcion"));
//		testCase.setFullTestName(data.get("Name"));
		testCase.setPrerequisites(data.get("Pre- Requisitos"));
		/******************************Paso 1****************************/
		addStep("Reversa exitosa por reversemanager de recarga de Tiempo aire electronico.");
		ATC_FT_003_PE01_D_ReversaTAEporReverseManager reverseSwitch = new ATC_FT_003_PE01_D_ReversaTAEporReverseManager();
		reverseSwitch.setTestCase(this.testCase);
		reverseSwitch.ATC_FT_003_PE01_D_ReversaTAEporReverseManager_test(data);
		String folio = reverseSwitch.getFolio();
		
		testCase.setTest_Description(data.get("Descripcion"));
		testCase.setFullTestName(data.get("Name"));
		testCase.setPrerequisites(data.get("Pre- Requisitos"));
		/******************************Paso 2****************************/
		addStep("Establecer la conexion con el servidor FTP para acceder al repositorio del LOG DEL SWITCH con los siguientes datos: ");
		
//		String folio = "544824";
		Integer ftpPort = Integer.parseInt(data.get("ftpPort"));
		String ftpHost = data.get("ftpHost");
		String ftpUser = data.get("ftpUser");
		String ftpPass = data.get("ftpPass");
		String ftpPath = data.get("ftpPath") + data.get("fileName");
		SSHConnector ftpConnector = new SSHConnector();
		ftpConnector.connect(ftpUser, ftpPass, ftpHost, ftpPort);
		//Busca el folio en el archivo switch_oxxo.log
		String ss = ftpConnector.executeCommand("grep " + folio + " " + ftpPath);
		System.out.println("Folio encontrado: " + ss);
		boolean invoiceExists = ss.isEmpty();
		//Valida si existe el folio
		if(!invoiceExists) {
			System.out.println("Se encontro el folio: "+ folio + " en el Log Switch");
			testCase.addTextEvidenceCurrentStep("Se encontro el folio: "+ folio + " en el Log Switch");
		}else {
			System.out.println("No se encontro el folio: " + folio + " el Log Switch");
			testCase.addTextEvidenceCurrentStep("No se encontro el folio: "+ folio + " en el Log Switch");
		}
		ftpConnector.disconnect();	
		
		assertFalse(invoiceExists, "No se encontro folio de transaccion el el Log del Switch.");
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Celia Rubi Delgado";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_PE01_D_ReversaExitosaTAEenSwitch_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
