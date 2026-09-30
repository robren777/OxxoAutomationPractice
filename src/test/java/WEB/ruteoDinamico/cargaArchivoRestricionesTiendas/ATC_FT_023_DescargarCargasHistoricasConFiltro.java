package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

import static org.testng.Assert.assertFalse;

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
 * DESCRIPCION: Validar que al dar clic en Actions -> Download, se descargue la lista de cargas hist�ricas con el 
 * 				filtro establecido
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_023_DescargarCargasHistoricasConFiltro extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_DescargarCargasHistoricasConFiltro_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String datoBusqueda = data.get("datoFiltro");
		
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
		
		// PASO 3
		addStep("Realizar alg�n tipo de filtrado");
		System.out.println("PASO 3");
		
		rd.establecerDatoBusqueda(datoBusqueda);
		
		// PASO 4
		addStep("Clic en el bot�n \"Go\"");
		System.out.println("PASO 4");
		
		rd.clicGo();
		assertFalse(rd.validarVacioPrimeraCelda(), "La tabla est� vac�a");
			
		// PASO 5
		addStep("Clic en \"Actions\"");
		System.out.println("PASO 5");
		
		rd.clicActions();
		
		// PASO 6
		addStep("Clic en \"Download\"");
		System.out.println("PASO 6");
		
		rd.clicDownloadCargasHist();
		
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
		return "Validar que al dar clic en Actions -> Download,"
				+ " se descargue la lista de cargas hist�ricas"
				+ " con el filtro establecido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_023_DescargarCargasHistoricasConFiltro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
