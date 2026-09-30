package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.RuteoDinamicoWebApex;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: Acceso al servidor de Apex (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que se muestre la p�gina inicial
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_001_LoginApexCargaArchivo extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_LoginApexCargaArchivo_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		RuteoDinamicoWebApex rd = new RuteoDinamicoWebApex(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Hacer login en plataforma Apex");
		System.out.println("PASO 1");
		
		assertTrue(rd.loginApexCargaArchivo(), "No se pudo iniciar sesion");
		
		// CERRAR APEX
		System.out.println("Cerrando Plataforma Apex");
		
		u.quit();
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
		return "Validar que se muestre la p�gina inicial";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_LoginApexCargaArchivo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
