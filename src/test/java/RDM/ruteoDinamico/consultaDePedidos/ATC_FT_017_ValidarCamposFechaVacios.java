package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator
 * 				  - Acceso al aplicativo RDM (VPN, Puertos)
 * 				  - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar que se muestre mensaje de error cuando el campo de fecha inicio o fin est�n vac�os al consultar
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_017_ValidarCamposFechaVacios extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_ValidarCamposFechaVacios_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String campoFcreaIni = "FcreaIni";
		String campoFcreaFin = "FcreaFin";
		String errorFechaIniFin = "errorFechaIniFin";
		String yesRD = "yesRD";
		String tablaInfoPedidos = "tablaConsultaPedidos";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, url);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		/*
		 * Filtro editando el campo
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
		addStep("Establecer vacio el campo F. Crea Ini");
		System.out.println("PASO 4");
		
		rd.establecerFechaCampo(campoFcreaIni, "");
		
		// PASO 5
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");
		
		// PASO 6
		addStep("Establecer vacio el campo F. Crea Fin");
		System.out.println("PASO 6");
		
		rd.clickClear();
		rd.establecerFechaCampo(campoFcreaFin, "");
		
		// PASO 7
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 7");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");

		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");
		
		u.quit();
		
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
		return "Validar que se muestre mensaje de error"
				+ " cuando el campo de fecha inicio o "
				+ "fin est�n vac�os al consultar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_017_ValidarCamposFechaVacios_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
