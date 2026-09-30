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
*DESCRIPCIÓN: Enviar datos utilizando TRN01 con agentconect15
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*- MTC-FT-019-Envio de datos del  servicio TRN01 con agent conect 15
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

public class ATC_FT_019_EnvioDatosTRN01v15 extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_019_EnvioDatosTRN01v15_test(HashMap<String, String> data) throws Exception {
			
			
	//Variables
	
	String BaseUrl = data.get("BaseUrl");
	String GetPath = data.get("GetPath");
	String TRN01 = data.get("TRN01");
	ArrayList<String> GetResponse;
	int GetExpectedCode = Integer.parseInt(data.get("GetExpectedCode"));
	String QweryDB = data.get("QweryInput");
	
	//Utilerias
	
	SQLUtil dbiCash = new SQLUtil(GlobalVariables.DB_HOST_ICASH, GlobalVariables.DB_USERQA_ICASH,
			GlobalVariables.DB_PASSQA_ICASH);
	TSFConsultasICash cic = new TSFConsultasICash(testCase, dbiCash);
	
	testCase.setProject_Name("iCash UpgradeMGI MVP1");
	
/* Paso 1 *****************************************************************************************/
		
	addStep("Se envian los datos AgentConnect15");
	
	// Se lanza TRN01
	
	GetResponse = cic.ejecutarConsulta(BaseUrl, GetPath, TRN01);
	assertEquals(Integer.parseInt(GetResponse.get(0)), GetExpectedCode);
		
/* Paso 2 *****************************************************************************************/
		
	addStep("Se verifica que la informacion este en BD correctamente");	
	
	assertTrue(cic.validarInfoDB(dbiCash, GetResponse, QweryDB), "No se encontro registro con el folio proporcionado");	
		
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
	return "Enviar datos utilizando TRN01 con agentconect15";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Antonio Gonzalez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_019_EnvioDatosTRN01v15";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}


}