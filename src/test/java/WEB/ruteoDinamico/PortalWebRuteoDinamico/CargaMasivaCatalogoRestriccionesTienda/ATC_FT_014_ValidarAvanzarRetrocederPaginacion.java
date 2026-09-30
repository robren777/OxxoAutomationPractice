package WEB.ruteoDinamico.PortalWebRuteoDinamico.CargaMasivaCatalogoRestriccionesTienda;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar botones de flechas del cambio de pagina de la tabla de archivos cargados
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_014_ValidarAvanzarRetrocederPaginacion extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ValidarAvanzarRetrocederPaginacion_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		int codigoReciente, codigoAntiguo;
		
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
		addStep("Clic para pasar a la lista anterior de registros usando boton siguiente de la paginacion");
		System.out.println("PASO 4");
		
		codigoReciente = pomCMCRT.obtenerCodigoPrimerRegistro();
		pomCMCRT.clickSiguientePaginacion();
		codigoAntiguo = pomCMCRT.obtenerCodigoPrimerRegistro();
		
		// PASO 5
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 5");
		
		assertTrue(pomCMCRT.validarPaginacion(codigoReciente, codigoAntiguo), "No se mostro el cambio de paginacion en la tabla");
		
		// PASO 6
		addStep("Clic para regresar a la lista principal de registros usando boton anterior de la paginacion");
		System.out.println("PASO 6");
		
		codigoAntiguo = pomCMCRT.obtenerCodigoPrimerRegistro();
		pomCMCRT.clickAnteriorPaginacion();
		codigoReciente = pomCMCRT.obtenerCodigoPrimerRegistro();
		
		// PASO 7
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 7");
		
		assertTrue(pomCMCRT.validarPaginacion(codigoReciente, codigoAntiguo), "No se mostro el cambio de paginacion en la tabla");
		
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
		return "Validar botones de flechas del cambio de pagina de la tabla de archivos cargados";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_ValidarAvanzarRetrocederPaginacion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
