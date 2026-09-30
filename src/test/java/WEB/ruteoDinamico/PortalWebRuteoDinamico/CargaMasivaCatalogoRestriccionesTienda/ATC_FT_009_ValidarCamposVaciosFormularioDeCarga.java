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
 * DESCRIPCION: Validar que aparezca cuadro de texto con el mensaje de que faltan campos por llenar
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_009_ValidarCamposVaciosFormularioDeCarga extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidarCamposVaciosFormularioDeCarga_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		
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
		addStep("Validar boton \"Guardar\" desactivado");
		System.out.println("PASO 5");
		
		assertTrue(pomCMCRT.validarBotonDesactivado(), "No se mostro desactivado el boton Guardar");
		
		// PASO 6
		addStep("Validar mensajes de campos requeridos");
		System.out.println("PASO 6");
		
		pomCMCRT.hacerTab(4);
		assertTrue(pomCMCRT.validarCamposRequeridos(), "No se mostraron mensajes de campos requeridos");

		// PASO 7
		addStep("Seleccionar archivo sin cedis seleccionado");
		System.out.println("PASO 7");
		
		pomCMCRT.seleccionarArchivo(pathFile);
		
		// PASO 8
		addStep("Validar boton \"Guardar\" desactivado");
		System.out.println("PASO 8");
		
		assertTrue(pomCMCRT.validarLlenarSoloCampoArchivo(), "No se mostraron mensajes de campos requeridos");
		assertTrue(pomCMCRT.validarBotonDesactivado(), "No se mostro desactivado el boton Guardar");
		
		// PASO 9
		addStep("Seleccionar cedis sin archivo seleccionado");
		System.out.println("PASO 9");
		
		pomCMCRT.limpiarCampoSubirArchivo();
		pomCMCRT.seleccionarCedis(cedis);
		
		// PASO 10
		addStep("Validar boton \"Guardar\" desactivado");
		System.out.println("PASO 10");
		
		assertTrue(pomCMCRT.validarLlenarSoloCampoCedis(), "No se mostraron mensajes de campos requeridos");
		assertTrue(pomCMCRT.validarBotonDesactivado(), "No se mostro desactivado el boton Guardar");
		
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
		return "Validar que aparezca cuadro de texto con el mensaje de que faltan campos por llenar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_ValidarCamposVaciosFormularioDeCarga_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
