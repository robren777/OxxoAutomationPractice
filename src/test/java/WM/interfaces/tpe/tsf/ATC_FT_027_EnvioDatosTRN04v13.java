package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.TSFConsultasICash;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

/*
*DESCRIPCIÓN: Se genera una transaccion utilizando TRN04 con agent conect 13
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*- MTC-FT-027-Envio de datos del servicio TRN04 con agent conect 13
*
* PREREQUISITOS:
* - Acceso al servidor (VPN)
* - Agentconect13 debe estar encendido
*
*INSUMOS:
*- Folio
*
*@author Antonio Gonzalez
*@date 19/Marzo/2025
*/

public class ATC_FT_027_EnvioDatosTRN04v13 extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_027_EnvioDatosTRN04v13_test(HashMap<String, String> data) throws Exception {
			
			
	//Variables
	
	String BaseUrl = data.get("BaseUrl");
	String GetPath = data.get("GetPath");
	String QRY01 = data.get("QRY01");
	String QRY01Reset = data.get("QRY01Reset");
	String TRN01 = data.get("TRN01");
	String TRN02 = data.get("TRN02");
	String TRN03 = data.get("TRN03");
	String TRN04 = data.get("TRN04");
	String bodyTRN02, bodyTRN03, bodyTRN04;
	ArrayList<String> GetResponse, GetResponse2, GetResponse3, GetResponse4, GetResponse5, dataQRY01, dataTRN01;
	int GetExpectedCode = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode2 = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode3 = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode4 = Integer.parseInt(data.get("GetExpectedCode"));
	int GetExpectedCode5 = Integer.parseInt(data.get("GetExpectedCode"));
	String QweryDB = data.get("QweryInput");
	
	//Utilerias
	
	SQLUtil dbiCash = new SQLUtil(GlobalVariables.DB_HOST_ICASH, GlobalVariables.DB_USERQA_ICASH,
			GlobalVariables.DB_PASSQA_ICASH);
	TSFConsultasICash cic = new TSFConsultasICash(testCase, dbiCash);
	
	testCase.setProject_Name("iCash UpgradeMGI MVP1");
	
/* Se envia llamada para resetear folio ************************************************************/
	
	    addStep("Se Resetea el folio");
		
	    GetResponse = cic.ejecutarConsulta(BaseUrl, GetPath, QRY01Reset);
	    assertEquals(Integer.parseInt(GetResponse.get(0)), GetExpectedCode);
	
/* Paso 1 *****************************************************************************************/
		
		addStep("Se envian los datos AgentConnect13");	
				
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
		
		// Se lanza TRN03
		
		bodyTRN03 = cic.obtenerBodyTRN03(TRN03, dataQRY01, dataTRN01);
		
		GetResponse4 = cic.ejecutarConsulta(BaseUrl, GetPath, bodyTRN03);
		assertEquals(Integer.parseInt(GetResponse4.get(0)), GetExpectedCode4);
		
		// Se lanza TRN04
		
		bodyTRN04 = cic.obtenerBodyTRN04(TRN04, dataQRY01, dataTRN01);
		
		GetResponse5 = cic.ejecutarConsulta(BaseUrl, GetPath, bodyTRN04);
		assertEquals(Integer.parseInt(GetResponse5.get(0)), GetExpectedCode5);
		
/* Paso 2 *****************************************************************************************/
		
		addStep("Se verifica que la informacion este en BD correctamente");	
		
		assertTrue(cic.validarInfoDBTRN04(dbiCash, GetResponse5, QweryDB), "No se encontro registro con el folio proporcionado");	
		
}

@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Agentconect13 debe estar encendido";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se genera una transaccion utilizando TRN04 con agent conect 13";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Antonio Gonzalez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_027_EnvioDatosTRN04v13";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}


}