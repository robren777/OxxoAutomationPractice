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
 * DESCRIPCION: Validar que al dar clic en XXFC_HISTORICO_CARGAS se muestre la p�gina para cargas hist�ricas 
 * 				(historial de archivos cargados)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_017_ClicXXFCHistoricoCargas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_ClicXXFCHistoricoCargas_test(HashMap<String, String> data) throws Exception {
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
		
		rd.loginApexCargaArchivo();
		
		// PASO 2
		addStep("Clic en \"XXFC_HISTORICO_CARGAS\"");
		System.out.println("PASO 2");
		
		rd.clicXxfcHistoricoCargas();
		assertTrue(rd.validarPaginaCargasHistoricas(), "No cargo la pagina de cargas historicas");
		
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
		return "Validar que al dar clic en XXFC_HISTORICO_CARGAS"
				+ " se muestre la p�gina para cargas hist�ricas"
				+ " (historial de archivos cargados)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_017_ClicXXFCHistoricoCargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
