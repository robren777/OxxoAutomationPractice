package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.RuteoDinamicoWebApex;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: Acceso al servidor de Apex (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que se pueda regresar a la p�gina principal con un archivo cargado
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_005_RegresarPaginaPrincipal extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_RegresarPaginaPrincipal_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		
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
		addStep("Seleccionar archivo a cargar");
		System.out.println("PASO 3");
		
		rd.cargarArchivo(pathFile);
		
		// PASO 4
		addStep("Clic en \"Home\" -> \"Abandonar\"");
		System.out.println("PASO 4");
		
		rd.clicHomeAbandonarCarga();
		assertTrue(rd.validarPaginaInicio(), "No se pudo regresar a pagina de incio");
				
		
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
		return "Validar que se pueda regresar a la p�gina"
				+ " principal con un archivo cargado";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_RegresarPaginaPrincipal_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
