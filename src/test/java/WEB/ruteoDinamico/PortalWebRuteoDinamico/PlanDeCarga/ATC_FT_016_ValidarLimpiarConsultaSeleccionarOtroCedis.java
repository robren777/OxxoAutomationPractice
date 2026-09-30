package WEB.ruteoDinamico.PortalWebRuteoDinamico.PlanDeCarga;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import WEB.util.PortalWebRuteoDinamico.POMPlanDeCarga;
import WEB.util.PortalWebRuteoDinamico.SikuliMethodsPortalWebRuteoDinamico;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que al seleccionar otro cedis los campos de informacion de la consulta se limpien
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_016_ValidarLimpiarConsultaSeleccionarOtroCedis extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ValidarLimpiarConsultaSeleccionarOtroCedis_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String otroCedis = data.get("otroCedis");
		String deposito = data.get("deposito");
		String campo = data.get("campo");
		String campoCambio = data.get("campoCambio");
		String fechaInicial, fechaFinal;
		int diasAtras;
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMPlanDeCarga pomPDC = new POMPlanDeCarga(u, testCase);
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
		addStep("Clic en \"Plan de Carga\"");
		System.out.println("PASO 3");
		
		pomPDC.clickPlanDeCarga();
		
		// PASO 4
		addStep("Clic en menu desplegable \"Lista de Cedis\" y seleccionar cedis");
		System.out.println("PASO 4");
		
		pomPDC.seleccionarCedis(cedis);
		assertTrue(smPWRD.validarCampoLlenado(campo), "No se selecciono el cedis");
		
		// PASO 5
		addStep("Establecer fecha inicial y fecha final");
		System.out.println("PASO 5");
		
		diasAtras = 90;
		fechaInicial = pomPDC.obtenerFechaAnterior(diasAtras);
		pomPDC.establecerFechaInicial(fechaInicial);
		
		fechaFinal = pomPDC.obtenerFechaActual();
		pomPDC.establecerFechaFinal(fechaFinal);
		
		// PASO 6
		addStep("Clic en \"Cargar Depositos\"");
		System.out.println("PASO 6");
		
		pomPDC.clickCargarDepositos();
		
		// PASO 7
		addStep("Clic en el deposito a seleccionar");
		System.out.println("PASO 7");
		
		pomPDC.seleccionarDeposito(deposito);
		
		// PASO 8
		addStep("Clic en \"Consultar\"");
		System.out.println("PASO 8");
		
		pomPDC.clickConsultar();
		
		// PASO 9
		addStep("Seleccionar otro cedis");
		System.out.println("PASO 9");
		
		pomPDC.seleccionarCedis(otroCedis);

		// PASO 10
		addStep("Validar limpieza de consulta y campos");
		System.out.println("PASO 10");
		
		assertTrue(smPWRD.validarCampoLlenado(campoCambio), "No se selecciono otro cedis");
		assertTrue(pomPDC.validarLimpiarConsulta(), "No se limpiaron los campos de la consulta"
				+ " descargar sin cedis seleccionado");
		
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
		return "Validar que al seleccionar otro cedis los campos de informacion de la consulta se limpien";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarLimpiarConsultaSeleccionarOtroCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
