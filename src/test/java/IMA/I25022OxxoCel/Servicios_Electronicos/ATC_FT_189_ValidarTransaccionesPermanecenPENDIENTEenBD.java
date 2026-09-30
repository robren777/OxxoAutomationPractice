package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_030_Validar las transacciones no procesadas permanecen en estado PENDIENTE para procesamiento posterior en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 04/Marzo/2026
 */
public class ATC_FT_189_ValidarTransaccionesPermanecenPENDIENTEenBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_189_ValidarTransaccionesPermanecenPENDIENTEenBD_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		addStep("Validamos en la tabla de auditoría las peticiones existentes que estan pendiente de procesar y posterior"
				+ "a una 2da consulta, validamos que el numero de peticiones coincida con la primer consulta.");
		boolean resultado = db.validarTransaccionesPendientesSinCambios();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		Assert.assertTrue(resultado, "No se encontraron registros en BD");
		
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_189_ValidarTransaccionesPermanecenPENDIENTEenBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar las transacciones no procesadas permanecen en estado PENDIENTE para procesamiento posterior en BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Leonardo Leonel Ramírez Escobedo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con acceso a la BD \r\n"
		+ "Tener ambiente en QA con FreedomPOP\r\n"
		+ "Contar con la API para enviar transacciones";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
