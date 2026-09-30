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
 * DESCRIPCION: Validar la paginacion de la tabla de resultados de la consulta a traves de los numeros (botones)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_012_ValidarPaginacionPorNumero extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidarPaginacionPorNumero_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String codClientePagAnt, codClientePagSig;
		
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
		addStep("Clic para pasar a la lista anterior de registros usando botones de numeros de la paginacion");
		System.out.println("PASO 6");
		
		codClientePagAnt = pomCCRT.obtenerDatoTabla(0, 4);
		pomCCRT.clickNumeroPaginacionTabla(4);
		codClientePagSig = pomCCRT.obtenerDatoTabla(0, 4);
		
		// PASO 7
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 7");
		
		assertTrue(pomCCRT.validarAvanzarPaginacion(codClientePagAnt, codClientePagSig), "No se mostro el cambio de paginacion en la tabla");
		
		// PASO 8
		addStep("Clic para regresar a la lista principal de registros usando botones de numeros de la paginacion");
		System.out.println("PASO 8");
		
		pomCCRT.clickNumeroPaginacionTabla(1);
		codClientePagSig = pomCCRT.obtenerDatoTabla(0, 4);
		
		// PASO 9
		addStep("Validar cambio de pagina en la tabla con el primer registro");
		System.out.println("PASO 9");
		
		assertTrue(pomCCRT.validarRegresarPaginacion(codClientePagAnt, codClientePagSig), "No se mostro el cambio de paginacion en la tabla");
		
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
		return "Validar la paginacion de la tabla de resultados de la consulta a traves de los numeros (botones)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_ValidarPaginacionPorNumero_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
