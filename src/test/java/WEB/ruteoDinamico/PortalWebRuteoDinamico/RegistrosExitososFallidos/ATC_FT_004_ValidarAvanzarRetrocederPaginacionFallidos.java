package WEB.ruteoDinamico.PortalWebRuteoDinamico.RegistrosExitososFallidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import WEB.util.PortalWebRuteoDinamico.POMRegistrosExitososFallidos;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar botones de flechas del cambio de pagina de la tabla de lista registros fallidos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_004_ValidarAvanzarRetrocederPaginacionFallidos extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarAvanzarRetrocederPaginacionFallidos_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		String mensajeModal = data.get("mensajeModal");
		int idCargaPrimerPag, idCargaSigPag;
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMCargaMasivaCatalogoRestTienda pomCMCRT = new POMCargaMasivaCatalogoRestTienda(u, testCase);
		POMRegistrosExitososFallidos pomREF = new POMRegistrosExitososFallidos(u, testCase);
		
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
		addStep("Realizar proceso de carga de archivo catalogo restriccion tiendas con registros fallidos");
		System.out.println("PASO 4");
		
		pomCMCRT.clickCargarArchivo();
		pomCMCRT.seleccionarCedis(cedis);		
		pomCMCRT.seleccionarArchivo(pathFile);
		pomCMCRT.clickGuardar();
		pomCMCRT.validarMensajeModal(mensajeModal);
		
		// PASO 5
		addStep("Clic en el icono de tachado del registro seleccionado de la tabla");
		System.out.println("PASO 5");
		
		pomCMCRT.clickRegistrosFallidos();
		
		// PASO 6
		addStep("Clic para pasar a la lista anterior de registros usando botones de numeros de la paginacion");
		System.out.println("PASO 6");
		
		idCargaPrimerPag = pomREF.obtenerIdCargaPrimerRegistro();
		pomREF.clickSiguientePaginacion();
		idCargaSigPag = pomREF.obtenerIdCargaPrimerRegistro();

		// PASO 7
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 7");
		
		assertTrue(pomREF.validarIdCargaSiguientePag(idCargaPrimerPag, idCargaSigPag), "No se mostro el cambio de paginacion en la tabla");
		
		// PASO 8
		addStep("Clic para regresar a la lista principal de registros usando botones de numeros de la paginacion");
		System.out.println("PASO 8");
		
		pomREF.clickAnteriorPaginacion();
		idCargaSigPag = pomREF.obtenerIdCargaPrimerRegistro();

		// PASO 9
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 9");
		
		assertTrue(pomREF.validarIdCargaAnteriorPag(idCargaPrimerPag, idCargaSigPag), "No se mostro el cambio de paginacion en la tabla");
		
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
		return "Validar botones de flechas del cambio de pagina de la tabla de lista registros fallidos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarAvanzarRetrocederPaginacionFallidos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
