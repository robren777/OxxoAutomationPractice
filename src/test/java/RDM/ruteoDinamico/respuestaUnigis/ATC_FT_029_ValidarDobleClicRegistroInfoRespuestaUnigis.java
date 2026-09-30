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
 * DESCRIPCION: Validar que la informaci�n que se presenta en la pantalla emergente Modificar Respuesta al hacer 
 * 				doble click a un registro sea la misma que se presenta en la tabla
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_029_ValidarDobleClicRegistroInfoRespuestaUnigis extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_029_ValidarDobleClicRegistroInfoRespuestaUnigis_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String columnaCveUnigisEstado = "lblCveUnigisEstadoNvaR";
		String datoTabla = "";
		
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
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		
		// PASO 6
		addStep("Doble Clic en el registro a modificar");
		System.out.println("PASO 6");
		
		datoTabla = rd.datoTabla(columnaCveUnigisEstado);
		rd.docleClickRegistro(columnaCveUnigisEstado);
		
		// PASO 7
		addStep("Validar que el dato de la tabla sea el mismo que est� en la pantalla emergente Modifica Respuesta");
		System.out.println("PASO 6");
		
		assertTrue(rd.validarDatoTablaConPantallaModificar(columnaCveUnigisEstado, datoTabla), 
				"El dato de la pantalla emergente Modifica Respuesta no es el mismo que el registro seleccionado");
		
		// PASO 8
		addStep("Clic en el bot�n \"Cancelar\"");
		System.out.println("PASO 8");
		
		rd.clickCancelar();
		
		// PASO 9
		addStep("- USANDO OTRO REGISTRO DIFERENTE DE LA TABLA -");
		addStep("Doble Clic en el registro a modificar");
		System.out.println("PASO 9");
		
		datoTabla = rd.dobleClicTextoRegistro(columnaCveUnigisEstado);
		System.out.println("DATO ----> " + datoTabla);
		
		// PASO 10
		addStep("Validar que el dato de la tabla sea el mismo que est� en la pantalla emergente Modifica Respuesta");
		System.out.println("PASO 10");
		
		assertTrue(rd.validarDatoTablaConPantallaModificar(columnaCveUnigisEstado, datoTabla), 
				"El dato de la pantalla emergente Modifica Respuesta no es el mismo que el registro seleccionado");

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
		return "Validar que la informaci�n que se presenta"
				+ " en la pantalla emergente Modificar Respuesta"
				+ " al hacer doble click a un registro sea la"
				+ " misma que se presenta en la tabla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_029_ValidarDobleClicRegistroInfoRespuestaUnigis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
