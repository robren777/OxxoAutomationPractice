package WEB.ruteoDinamico.PortalWebRuteoDinamico.CargaMasivaCatalogoRestriccionesTienda;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que solo permita cargar archivos .xls o .xlsx, si se carga otro tipo de archivo 
 * 				(por ejemplo .PDF, .txt, .doc, ect) debe de lanzar mensaje de que el archivo no es valido
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_007_ValidarFormatoArchivoNoValido extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarFormatoArchivoNoValido_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		String mensaje = data.get("mensajeValidacion");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMCargaMasivaCatalogoRestTienda pomCMCRT = new POMCargaMasivaCatalogoRestTienda(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Hacer login en Portal Web Ruteo Dinamico");
		System.out.println("PASO 1");
		
		assertTrue(pomLP.loginAccessControl(), "No se pudo iniciar sesion");
		
		// PASO 2
		addStep("Clic en icono PORTAL RUTEO DINAMICO");
		System.out.println("PASO 2");
		
		pomLP.clickPortalRuteoDinamico();
		
		// PASO 3
		addStep("Clic en \"Carga Masiva CRT\"");
		System.out.println("PASO 3");
		
		pomCMCRT.clickCargaMasivaCRT();
		
		// PASO 4
		addStep("Clic en \"Cargar Archivo\"");
		System.out.println("PASO 4");
		
		pomCMCRT.clickCargarArchivo();
		
		// PASO 5
		addStep("Seleccionar archivo");
		System.out.println("PASO 5");
		
		pomCMCRT.seleccionarArchivo(pathFile);
		
		// PASO 6
		addStep("Validar mensaje de archivo no valido");
		System.out.println("PASO 6");
		
		assertTrue(pomCMCRT.validarMensajeArchivoNoValido(mensaje), "No se mostro mensaje de error de archivo no valido");
		
		// CERRAR WEB
		System.out.println("Cerrando Portal Web Ruteo Dinamico");
		
		u.quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)\r\n"
				+ "2.- Acceso al servidor WEB (VPN, Puertos)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que solo permita cargar archivos .xls o .xlsx,"
				+ " si se carga otro tipo de archivo (por ejemplo .PDF,"
				+ " .txt, .doc, ect) debe de lanzar mensaje de que el"
				+ " archivo no es valido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ValidarFormatoArchivoNoValido_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
