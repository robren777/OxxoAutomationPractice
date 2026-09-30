package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertFalse;
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
 * DESCRIPCION: Validar que se realice filtraci�n usando m�s de un filtro y se muestre en la pantalla de 
 * 				Restricci�n de Tiendas
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_008_ValidarFiltrosCombinadosRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ValidarFiltrosCombinadosRestriccionTiendas_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String claveDistrito = data.get("claveDistrito");
		String claveCliente = data.get("claveCliente");
		String claveDeposito = data.get("claveDeposito");
		String campoDistrito = "distritoRD";
		String campoCliente = "clienteRD";
		String campoDeposito = "depositoRD";
		String tablaRestriccionTiendas = "tablaRestriccionTiendas";
		
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
		addStep("Clic en el bot�n \"Restric. Tiendas\"");
		System.out.println("PASO 4");
		
		rd.clickRestricTiendas();
		
		// PASO 5
		addStep("- USANDO DOS CAMPOS DE FILTRO -");
		addStep("Establecer en los campos Distrito y Cliente claves v�lidas");
		System.out.println("PASO 5");
		
		assertTrue(rd.validarExistenciaCampo(campoDistrito), "No existe el campo");
		rd.establecerDatoCampo(campoDistrito, claveDistrito);
		assertTrue(rd.validarExistenciaCampo(campoCliente), "No existe el campo");
		rd.establecerDatoCampo(campoCliente, claveCliente);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaRestriccionTiendas), "La tabla est� vac�a");
		
		// PASO 7
		addStep("Establecer en los campos Distrito y Deposito claves v�lidas");
		System.out.println("PASO 7");
		
		rd.clickClear();
		assertTrue(rd.validarExistenciaCampo(campoDistrito), "No existe el campo");
		rd.establecerDatoCampo(campoDistrito, claveDistrito);
		assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
		rd.establecerDatoCampo(campoDeposito, claveDeposito);
		
		// PASO 8
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 8");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaRestriccionTiendas), "La tabla est� vac�a");
		
		// PASO 9
		addStep("- USANDO TODOS LOS CAMPOS DE FILTRO -");
		addStep("Establecer en todos los campos claves v�lidas");
		System.out.println("PASO 9");
		
		rd.clickClear();
		assertTrue(rd.validarExistenciaCampo(campoDistrito), "No existe el campo");
		rd.establecerDatoCampo(campoDistrito, claveDistrito);
		assertTrue(rd.validarExistenciaCampo(campoCliente), "No existe el campo");
		rd.establecerDatoCampo(campoCliente, claveCliente);
		assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
		rd.establecerDatoCampo(campoDeposito, claveDeposito);
		
		// PASO 10
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 10");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaRestriccionTiendas), "La tabla est� vac�a");

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
		return "Validar que se realice filtraci�n usando"
				+ " m�s de un filtro y se muestre en la "
				+ "pantalla de Restricci�n de Tiendas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_ValidarFiltrosCombinadosRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
