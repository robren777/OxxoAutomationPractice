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
*DESCRIPCIÓN: Se genera una transaccion por medio de agentconect15
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*- MTC-FT-016-Se puede configurar el encendido de agentconect15
*
* PREREQUISITOS:
* - Acceso al servidor (VPN)
* - Encender agent conect 15
*
*INSUMOS:
*- Folio
*
*@author Antonio Gonzalez
*@date 19/Marzo/2025
*/

public class ATC_FT_016_EncendidoACv15 extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_016_EncendidoACv15_test(HashMap<String, String> data) throws Exception {
			
			
	//Variables
	
	String BaseUrl = data.get("BaseUrl");
	String GetPath = data.get("GetPath");
	String QRY01 = data.get("QRY01");
	ArrayList<String> GetResponse;
	int GetExpectedCode = Integer.parseInt(data.get("GetExpectedCode"));
	
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
		
}

@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Encender agent conect 15";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se genera una transaccion por medio de agentconect15";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Antonio Gonzalez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_016_EncendidoACv15";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}


}