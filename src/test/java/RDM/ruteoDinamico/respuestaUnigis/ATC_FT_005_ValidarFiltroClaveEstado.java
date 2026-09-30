package RDM.ruteoDinamico.respuestaUnigis;

import static org.testng.Assert.assertFalse;

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
 * DESCRIPCION: Validar que se realice filtraci�n por respuesta unigis y se muestre en la pantalla de respuestas UNIGIS
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_005_ValidarFiltroClaveEstado extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarFiltroClaveEstado_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String claveEstado = data.get("claveEstado");
		String campo = "claveEstadoRD";
		String dataRegistro = "statusPendienteEnvio";
		String tablaRespuestaUnigis = "tablaRespuestaUnigis";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		/*
		 * Filtro editando el campo
		 */
		// PASO 1
		addStep("FILTRADO EDITANDO EL CAMPO");
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
		
		rd.establecerDatoCampo(campo, claveEstado);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		/*
		 * Filtro usando el catalogo
		 */
		addStep("FILTRADO USANDO EL CATALOGO");
		
		rd.clickExit();
		
		// PASO 1
		addStep("Clic en el bot�n \"Resp. UNIGIS\"");
		System.out.println("PASO 1");
		
		rd.clickRespUnigis();
		
		// PASO 2
		addStep("Establecer en el campo Clave Estado una clave de respuesta unigis v�lida (seleccionando un registro del cat�logo de Clave Estado)");
		System.out.println("PASO 2");
		
		rd.establecerDatoPorCatalogo(campo, dataRegistro);
		
		// PASO 3
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 3");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaRespuestaUnigis), "La tabla est� vac�a");

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
		return "Validar que se realice filtraci�n por"
				+ " respuesta unigis y se muestre en "
				+ "la pantalla de respuestas UNIGIS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarFiltroClaveEstado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
