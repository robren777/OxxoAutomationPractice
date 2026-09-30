package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.TSFConsultasICash;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

/*
*DESCRIPCIÓN: Se llena solo el campo de Parentesco
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*- MTC-FT-008-Llenar solo el campo Parentesco
*
* PREREQUISITOS:
* - Acceso al servidor (VPN)
* - Agentconect15 debe estar encendido
*
*INSUMOS:
*- Folio
*
*@author Antonio Gonzalez
*@date 19/Marzo/2025
*/

public class ATC_FT_008_SoloCampoParentesco extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_008_SoloCampoParentesco_test(HashMap<String, String> data) throws Exception {
			
			
	//Variables
	
	String BaseUrl = data.get("BaseUrl");
	String GetPath = data.get("GetPath");
	String QRY01 = data.get("QRY01");
	String TRN01 = data.get("TRN01");
	String TRN02 = data.get("TRN02");
	String bodyTRN02;
	ArrayList<String> GetResponse, GetResponse2, GetResponse3, dataQRY01, dataTRN01;
	int GetExpectedCode = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode2 = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode3 = Integer.parseInt(data.get("GetExpectedCode"));
	
	//Utilerias

	SQLUtil dbiCash = new SQLUtil(GlobalVariables.DB_HOST_ICASH, GlobalVariables.DB_USERQA_ICASH,
			GlobalVariables.DB_PASSQA_ICASH);
	TSFConsultasICash cic = new TSFConsultasICash(testCase, dbiCash);
	
	testCase.setProject_Name("iCash UpgradeMGI MVP1");
	
/* Paso 1 *****************************************************************************************/
	
	addStep("Se envian los datos AgentConnect15");	
	
	// Se lanza QRY01
	
	GetResponse = cic.ejecutarConsulta(BaseUrl, GetPath, QRY01);
	assertEquals(Integer.parseInt(GetResponse.get(0)), GetExpectedCode);
	
	dataQRY01 = cic.obtenerDataResponseQRY01(GetResponse.get(1).toString());
	
	// Se lanza TRN01

	GetResponse2 = cic.ejecutarConsulta(BaseUrl, GetPath, TRN01);
	assertEquals(Integer.parseInt(GetResponse2.get(0)), GetExpectedCode2);
	
	dataTRN01 = cic.obtenerDataResponseTRN01(GetResponse2.get(1).toString());
	
	// Se lanza TRN02
	
	bodyTRN02 = cic.obtenerBodyTRN02(TRN02, dataQRY01, dataTRN01);
	
	GetResponse3 = cic.ejecutarConsulta(BaseUrl, GetPath, bodyTRN02);
	assertEquals(Integer.parseInt(GetResponse3.get(0)), GetExpectedCode3);
	
}

@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Agentconect15 debe estar encendido";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se llena solo el campo de Parentesco";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Antonio Gonzalez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_008_SoloCampoParentesco";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}


}