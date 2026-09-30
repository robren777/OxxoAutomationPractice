package WEB.ruteoDinamico.PortalWebRuteoDinamico.ConsultaCatalogoRestriccionesTienda;

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
 * DESCRIPCION: Validar que al dar clic en "Consultar" con algun cedis seleccionado sin registros asociados mande un mensaje de aviso
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_004_ValidarBotonConsultarConCedisSinRegistrosAsociados extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarBotonConsultarConCedisSinRegistrosAsociados_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String mensaje = data.get("mensajeAlerta");
		
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
		addStep("Clic en menu desplegable \"Lista de Cedis\" y seleccionar cedis");
		System.out.println("PASO 4");
		
		pomCCRT.seleccionarCedis(cedis);
		
		// PASO 5
		addStep("Clic en \"Consultar\"");
		System.out.println("PASO 5");
		
		pomCCRT.clickConsultar();
		
		// PASO 6
		addStep("Validar cerrar mensaje de cedis seleccionado sin registros asociados");
		System.out.println("PASO 6");
		
		assertTrue(pomCCRT.validarConsultarCedisSinRegistrosAsociados(mensaje), "No se mostro registros en la tabla");
		
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
		return "Validar que al dar clic en \"Consultar\" con algun cedis"
				+ " seleccionado sin registros asociados mande un mensaje de aviso";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarBotonConsultarConCedisSinRegistrosAsociados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
