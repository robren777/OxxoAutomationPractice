package WEB.ruteoDinamico.PortalWebRuteoDinamico.RegistrosExitososFallidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import WEB.util.PortalWebRuteoDinamico.POMRegistrosExitososFallidos;
import WEB.util.PortalWebRuteoDinamico.SikuliMethodsPortalWebRuteoDinamico;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que al dar clic en "Descargar archivo" descargue un archivo con los registros exitosos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_001_ValidarBotonDescargarArchivoRegistrosExitosos extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarBotonDescargarArchivoRegistrosExitosos_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		String mensajeModal = data.get("mensajeModal");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMCargaMasivaCatalogoRestTienda pomCMCRT = new POMCargaMasivaCatalogoRestTienda(u, testCase);
		POMRegistrosExitososFallidos pomREF = new POMRegistrosExitososFallidos(u, testCase);
		SikuliMethodsPortalWebRuteoDinamico smPWRD = new SikuliMethodsPortalWebRuteoDinamico(u, testCase);
		
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
		addStep("Realizar proceso de carga de archivo catalogo restriccion tiendas con registros sin error");
		System.out.println("PASO 3");
		
		pomCMCRT.clickCargaMasivaCRT();
		pomCMCRT.clickCargarArchivo();
		pomCMCRT.seleccionarCedis(cedis);
		pomCMCRT.seleccionarArchivo(pathFile);
		pomCMCRT.clickGuardar();
		assertTrue(pomCMCRT.validarMensajeModal(mensajeModal), "No se mostro el modal de carga exitosa del archivo");
		
		// PASO 4
		addStep("Clic en el icono de verificado del registro seleccionado de la tabla");
		System.out.println("PASO 4");
		
		pomCMCRT.clickRegistrosExitosos();
		
		// PASO 5
		addStep("Clic en \"Descargar Registros Exitosos\"");
		System.out.println("PASO 5");
		
		pomREF.clickDescargarArchivo();

		// PASO 6
		addStep("Visualizar archivo descargado");
		System.out.println("PASO 6");
		
		assertTrue(smPWRD.clickOpenFileXLX(), "No se mostro el archivo descargado");
		
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
		return "Validar que al dar clic en \"Descargar archivo\" descargue un archivo con los registros exitosos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarBotonDescargarArchivoRegistrosExitosos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
