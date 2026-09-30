package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

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
 * DESCRIPCION: Validar que al hacer clic al campo de Choose file se despliegue la ventana de explorar de archivos 
 * 				para seleccionar el archivo a cargar
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_003_CargaRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_CargaRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
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
		addStep("Clic en \"XXFC_CARGA_EXCEL\"");
		System.out.println("PASO 2");
		
		rd.clicXxfcCargaExcel();
				
		// PASO 3
		addStep("Clic en \"Choose file\"");
		System.out.println("PASO 3");
		
		rd.clicChooseFile();
		
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
		return "Validar que al hacer clic al campo de Choose file"
				+ " se despliegue la ventana de explorar de archivos"
				+ " para seleccionar el archivo a cargar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_CargaRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
