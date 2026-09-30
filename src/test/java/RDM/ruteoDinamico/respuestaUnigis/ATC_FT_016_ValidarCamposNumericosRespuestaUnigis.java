package RDM.ruteoDinamico.respuestaUnigis;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator
 * 				  - Acceso al aplicativo RDM (VPN, Puertos)
 * 				  - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar campos num�ricos de pantalla emergente Modificar Respuesta
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_016_ValidarCamposNumericosRespuestaUnigis extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ValidarCamposNumericosRespuestaUnigis_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String campoDato = data.get("cveUnigisEstado");
		String datoTexto = data.get("datoTexto");
		String claveEstado = data.get("claveEstado");
		String btnYes = "btnYes";
		String errorCampoNum = "errorCampoNum";
		String campo = "cveUnigisEstadoNvaR";
		String campoFiltro = "claveEstadoRD";
		String campoVacio = "lblCveUnigisEstadoNvaR";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Acceder al enlace http://fcret3.femcom.net:8036/rdmDES36.html por Microsoft Edge");
		System.out.println("PASO 1");
		
		rd.logIn();
		
		// PASO 2
		addStep("Seguir la ruta dando Clic en Distribution Planning -> XXFC-Programacion y Balanceo -> XXFC-Ruteo Dinamico");
		System.out.println("PASO 2");
		
		rd.clickMenuDistPlanProgramBalanceoRuteoDinamico();
		
		// PASO 3
		addStep("Clic en el bot�n \"Ver TSF Unigis\"");
		System.out.println("PASO 3");
		
		rd.clickVerTSFUnigis();
		
		// PASO 4
		addStep("Clic en el bot�n \"Resp. UNIGIS\"");
		System.out.println("PASO 4");
		
		rd.clickRespUnigis();
		
		// PASO 5
		addStep("Establecer en el campo Clave Estado una clave de respuesta unigis v�lida (editando el campo)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(campoFiltro, claveEstado);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Clic en el bot�n \"Modifica Resp\"");
		System.out.println("PASO 7");
		
		rd.clickModificaResp();
		
		// PASO 6
		addStep("Llenar campo Cve Unigis Estado con dato no num�rico");
		System.out.println("PASO 6");
		
		rd.establecerVacioCampo(campoVacio);
		rd.establecerDatoCampo(campo, datoTexto);
		rd.nTAB(1);
		
		// PASO 7
		addStep("Validar mensaje de error de campo solo permite caracteres num�ricos y corregirlo");
		System.out.println("PASO 7");
		
		rd.screenshot();
		assertTrue(rd.validarMensajeCuadroDialogo(errorCampoNum, btnYes), "No se mostr� mensaje de error del campo Cve Unigis Estado");
		rd.nBackspace(1);
		rd.establecerDatoCampo(campo, campoDato);

		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");
		
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar campos num�ricos de pantalla emergente Modificar Respuesta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarCamposNumericosRespuestaUnigis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
