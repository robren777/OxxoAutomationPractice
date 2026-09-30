package WEB.ruteoDinamico.PortalWebRuteoDinamico.LoginPortal;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMConsultaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que al dar clic en "Consultar Catalogo Restriccion Tiendas" se muestre 
 * 				la pagina de Consultar Catalogo Restriccion Tiendas
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_006_ValidarOpcionMenuConsultarCatalogoRestriccionTiendas extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarOpcionMenuConsultarCatalogoRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMConsultaCatalogoRestTienda pomCCRT = new POMConsultaCatalogoRestTienda(u, testCase);
		
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
		addStep("Clic en \"Consultar Catalogo Restriccion Tiendas\"");
		System.out.println("PASO 3");
		
		pomCCRT.clickConsultarCatalogoRestriccionTiendas();
		
		// PASO 4
		addStep("Validar que se muestre la pagina de Consultar Catalogo Restriccion Tiendas");
		System.out.println("PASO 4");
		
		assertTrue(pomCCRT.validarConsultarCatalogoRestriccionTiendas(), "No se cargo la pagina de "
				+ "Consultar Catalogo Restriccion Tiendas");
		
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
		return "Validar que al dar clic en \"Consultar Catalogo Restriccion Tiendas\" "
				+ "se muestre la pagina de Consultar Catalogo Restriccion Tiendas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_ValidarOpcionMenuConsultarCatalogoRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
