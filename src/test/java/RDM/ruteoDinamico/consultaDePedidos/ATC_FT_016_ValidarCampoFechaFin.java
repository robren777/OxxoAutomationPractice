package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertFalse;
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
 * DESCRIPCION: Validar que el campo de Fecha creaci�n fin solo acepte formato DD-MON-YYYY (MON corresponde 
 * 				primeras tres letras del mes):
 * 				- Validar d�a (solo n�mero y d�as validos)
 * 				- Validar mes (solo letras y meses v�lidos)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_016_ValidarCampoFechaFin extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ValidarCampoFechaFin_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String fechaDiaNoNum = data.get("fechaDiaNoNum");
		String fechaDiaNoValido = data.get("fechaDiaNoValido");
		String fechaMesNum = data.get("fechaMesNum");
		String fechaMesNoValido = data.get("fechaMesNoValido");
		String campoFcreaIni = "FcreaIni";
		String campoFcreaFin = "FcreaFin";
		String errorFechaFormato = "errorFechaFormato_";
		String errorFechaDia = "errorFechaDia_";
		String errorFechaMes = "errorFechaMes_";
		String errorFechaIniFin = "errorFechaIniFin";
		String botonOK = "botonOK";
		String yesRD = "yesRD";
		String tablaInfoPedidos = "tablaConsultaPedidos";
		String fCreaIni = "";
		String fCreaFin = "";
		
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
		addStep("Establecer en el campo F. Crea Fin una fecha con el d�a no num�rico");
		System.out.println("PASO 4");
		
		fCreaIni = rd.obtenerFechaAnteriorConFormato(-90);
		rd.establecerFechaCampo(campoFcreaIni, fCreaIni);
		rd.establecerFechaCampo(campoFcreaFin, fechaDiaNoNum);
		
		// PASO 5
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaFormato, botonOK),"No se mostr� mensaje de error");
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");
		
		// PASO 6
		addStep("Establecer en el campo F. Crea Fin una fecha con el d�a no valido");
		System.out.println("PASO 6");
		
		rd.establecerFechaCampo(campoFcreaFin, fechaDiaNoValido);
		
		// PASO 7
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 7");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaDia, botonOK),"No se mostr� mensaje de error");
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");
		
		// PASO 8
		addStep("Establecer en el campo F. Crea Fin una fecha con el mes num�rico");
		System.out.println("PASO 8");
		
		rd.establecerFechaCampo(campoFcreaFin, fechaMesNum);
		
		// PASO 9
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 9");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaMes, botonOK),"No se mostr� mensaje de error");
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");
		
		// PASO 10
		addStep("Establecer en el campo F. Crea Fin una fecha con el mes no valido");
		System.out.println("PASO 10");
		
		rd.establecerFechaCampo(campoFcreaFin, fechaMesNoValido);
		
		// PASO 11
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 11");
		
		rd.clickExecute();
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaMes, botonOK),"No se mostr� mensaje de error");
		assertTrue(rd.validarMensajeCuadroDialogo(errorFechaIniFin, yesRD),"No se mostr� mensaje de error");
		//assertTrue(rd.validarTablaVacia(tablaInfoPedidos), "La tabla deber�a estar vac�a");
		
		// PASO 12
		addStep("Establecer en el campo F. Crea Fin una fecha valida");
		System.out.println("PASO 12");
		
		fCreaFin = rd.obtenerFechaActualConFormato();
		rd.establecerFechaCampo(campoFcreaFin, fCreaFin);
		
		// PASO 13
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 13");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");

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
		return "Validar que el campo de Fecha creaci�n fin "
				+ "solo acepte formato DD-MON-YYYY (MON corresponde "
				+ "primeras tres letras del mes):\r\n"
				+ "- Validar d�a (solo n�mero y d�as validos)\r\n"
				+ "- Validar mes (solo letras y meses v�lidos)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarCampoFechaFin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
