package WEB.ruteoDinamico.PortalWebRuteoDinamico.CargaMasivaCatalogoRestriccionesTienda;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import config.Constants;
import modelo.BaseExecution;
import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que el tama�o maximo de carga de archivo sea 5MB
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_008_ValidarTamanoArchivoNoValido extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ValidarTamanoArchivoNoValido_test(HashMap<String, String> data) throws Exception {
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
		addStep("Validar mensaje de tama�o archivo no valido");
		System.out.println("PASO 6");
		
		assertTrue(pomCMCRT.validarMensajeTamanoArchivoNoValido(mensaje), "No se mostro mensaje de error de tama�o de archivo no valido");
		
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
		return "Validar que el tama�o maximo de carga de archivo sea 5MB";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_ValidarTama�oArchivoNoValido_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
