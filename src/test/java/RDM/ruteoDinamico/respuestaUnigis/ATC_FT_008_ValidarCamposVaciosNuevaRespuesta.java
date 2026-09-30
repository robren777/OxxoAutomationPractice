package RDM.ruteoDinamico.respuestaUnigis;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
 * DESCRIPCION: Validar campos vac�os de pantalla emergente Nueva Respuesta
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_008_ValidarCamposVaciosNuevaRespuesta extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ValidarCamposVaciosNuevaRespuesta_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String btnYesConfirmacion = "btnYes";
		String btnYesError = "yesRD";
		String mensajeConfirmacion = "confirmacion";
		ArrayList<String> validacionesNvaRespuesta = new ArrayList<String>();
		validacionesNvaRespuesta.add("Cve Unigis Estado,cveUnigisEstadoNvaR," + data.get("cveUnigisEstado") + ",errorCveUnigisEstadoVacio"); 
		validacionesNvaRespuesta.add("Descripcion Corta,descripcionCortaNvaR," + data.get("descripcionCorta") + ",errorDescripcionCortaVacio");
		validacionesNvaRespuesta.add("Descripcion Extendida,descripcionExtendidaNvaR," + data.get("descripcionExtendida") + ",errorDescripcionExtendidaVacio");
		ArrayList<String> listaCampos = new ArrayList<String>();
		for (String validacion : validacionesNvaRespuesta) {
			String[] campo = validacion.split(",");
			listaCampos.add(campo[1]);
		}
		
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
		addStep("Clic en el bot�n \"Nva Respuesta\"");
		System.out.println("PASO 5");
		
		rd.clickNvaRespuesta();
		
		// PASO 6
		addStep("Validar existencia campos vacios");
		System.out.println("PASO 6");
		
		rd.validarExistenciaCampos(listaCampos);
		
		int i = 7;
		for (String validacion : validacionesNvaRespuesta){
			String[] dataNvoD = validacion.split(",");
			String nombreCampo = dataNvoD[0];
			String campo = dataNvoD[1];
			String campoDato = dataNvoD[2];
			String mensajeError = dataNvoD[3];
			
			// PASO 7
			addStep("Clic en el bot�n \"Guardar\" con el campo " + nombreCampo + " vacio");
			System.out.println("PASO " + i);
			
			rd.clickGuardar();
			i++;
			
			// PASO 8
			addStep("Clic en el bot�n \"Yes\"");
			System.out.println("PASO " + i);
			
			rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
			i++;
			
			// PASO 9
			addStep("Validar mensaje del campo " + nombreCampo + " vacio y llenarlo");
			System.out.println("PASO " + i);
			
			assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error del campo " + nombreCampo);
			rd.establecerDatoCampo(campo, campoDato);
			i++;
		}

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
		return "Validar campos vacios de pantalla emergente Nueva Respuesta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_ValidarCamposVaciosNuevaRespuesta_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
